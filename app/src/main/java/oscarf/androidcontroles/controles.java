package oscarf.androidcontroles;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.ToggleButton;

public class controles extends AppCompatActivity implements View.OnTouchListener, AdapterView.OnItemSelectedListener, View.OnFocusChangeListener {

    Spinner tipoTarjeta;
    EditText numeroTarjeta;
    Button sendButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_controles);

        sendButton = (Button)findViewById(R.id.buttonEnviar);
        Button clearButton = (Button)findViewById(R.id.buttonBorrar);
        Button goToEventosButton = (Button)findViewById(R.id.buttonGoToEventos);
        numeroTarjeta = (EditText) findViewById(R.id.numeroTarjeta);
        tipoTarjeta = (Spinner)findViewById(R.id.dropdownSelector);

        sendButton.setEnabled(false);
        sendButton.setTextColor(Color.GRAY);
        tipoTarjeta.setOnTouchListener(this);
        tipoTarjeta.setOnItemSelectedListener(this);

        numeroTarjeta.setOnTouchListener(this);
        numeroTarjeta.setOnFocusChangeListener(this);

        goToEventosButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open new activity
                Intent intent = new Intent(controles.this, Eventos.class);
                startActivity(intent);
            }
        });

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText expiraText = (EditText) findViewById(R.id.expira);
                EditText numTarjeta = (EditText) findViewById(R.id.numeroTarjeta);
                EditText ccv = (EditText) findViewById(R.id.ccvPassword);
                ToggleButton promoCheck = (ToggleButton)findViewById(R.id.toggleButton);
                Spinner tipoTarjeta = (Spinner)findViewById(R.id.dropdownSelector);

                expiraText.setText("");
                numTarjeta.setText("");
                ccv.setText("");
                promoCheck.setChecked(false);
                tipoTarjeta.setSelection(0);
            }
        });
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        activateSendButton();
        return false;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        activateSendButton();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        activateSendButton();
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        activateSendButton();
    }

    private void activateSendButton(){
        String ccType = getCCType(numeroTarjeta.getText().toString());

        if(ccType != null && tipoTarjeta.getSelectedItem().equals(ccType)){
            sendButton.setEnabled(true);
            sendButton.setTextColor(Color.GREEN);
        }
        else{
            sendButton.setEnabled(false);
            sendButton.setTextColor(Color.GRAY);
        }
    }

    private String getCCType(String ccNumber){
        String visaRegex        = "^4[0-9]{12}(?:[0-9]{3})?$"; // 4916338506082832
        String masterRegex      = "^5[1-5][0-9]{14}$"; // 5280934283171080
        String amexRegex        = "^3[47][0-9]{13}$"; // 345936346788903
        String discoverRegex    = "^6(?:011|5[0-9]{2})[0-9]{12}$"; // 6011894492395579

        try {
            ccNumber = ccNumber.replaceAll("\\D", "");
            return (ccNumber.matches(visaRegex) ? "Visa" : ccNumber.matches(masterRegex) ? "Master Card" :ccNumber.matches(amexRegex) ? "American Express" :ccNumber.matches(discoverRegex) ? "Discover" : null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
