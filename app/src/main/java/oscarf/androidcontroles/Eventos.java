package oscarf.androidcontroles;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Eventos extends AppCompatActivity {

    TextView initialText;
    Spinner spinner;
    CheckBox estudiante;
    RadioButton mujer;
    RadioButton hombre;
    EditText nombre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventos);

        spinner = (Spinner)findViewById(R.id.spinnerEdad);
        initialText = (TextView)findViewById(R.id.textViewCompleto);
        estudiante = (CheckBox)findViewById(R.id.checkBoxEstudiante);
        mujer = (RadioButton)findViewById(R.id.radioButtonMujer);
        hombre = (RadioButton)findViewById(R.id.radioButtonHombre);
        nombre = (EditText)findViewById(R.id.editTextNombre);
        Button goToControlesButton = (Button)findViewById(R.id.buttonGoToControles);
        Button enviar = (Button)findViewById(R.id.buttonEnviar);
        Button borrar = (Button)findViewById(R.id.buttonBorrar);

        mujer.setChecked(true);
        hombre.setChecked(false);

        List<Integer> age = new ArrayList<>();
        for (int i = 18; i <= 90; i++) {
            age.add(i);
        }
        ArrayAdapter<Integer> spinnerArrayAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, age);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerArrayAdapter);

        goToControlesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open new activity
                Intent intent = new Intent(Eventos.this, controles.class);
                startActivity(intent);
            }
        });

        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Spinner spinner = (Spinner)findViewById(R.id.spinnerEdad);
                TextView initialText = (TextView)findViewById(R.id.textViewCompleto);
                CheckBox estudiante = (CheckBox)findViewById(R.id.checkBoxEstudiante);
                RadioButton hombre = (RadioButton)findViewById(R.id.radioButtonHombre);
                EditText nombre = (EditText)findViewById(R.id.editTextNombre);

                String sexo = "mujer";
                if (hombre.isChecked()){
                    sexo = "hombre";
                }
                String name = nombre.getText().toString();
                String age = spinner.getSelectedItem().toString();
                String estudianteText = " ";
                if (!estudiante.isChecked()){
                    estudianteText = " no ";
                }

                initialText.setText(name + " es " + sexo + ", tiene " + age + " años y" + estudianteText+ "es estudiante");
            }
        });

        mujer.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    hombre.setChecked(false);
                }
                else{
                    hombre.setChecked(true);
                }
            }
        });

        hombre.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    mujer.setChecked(false);
                }
                else{
                    mujer.setChecked(true);
                }
            }
        });

        borrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mujer.setChecked(true);
                hombre.setChecked(false);
                initialText.setText("");
                estudiante.setChecked(false);
                spinner.setSelection(0);
                nombre.setText("");
            }
        });
    }
}
