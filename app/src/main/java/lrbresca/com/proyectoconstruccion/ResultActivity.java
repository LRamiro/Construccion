package lrbresca.com.proyectoconstruccion;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import java.util.ArrayList;

public class ResultActivity extends AppCompatActivity{

    private double[] resultado;
    EditText dosajeCementoPortland, dosajeAridoFino, dosajeAridoGrueso, dosajeAgua;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Intent intent = getIntent();
        resultado = intent.getDoubleArrayExtra("DatosAMostrar");
        dosajeCementoPortland = findViewById(R.id.dosajeCemento);
        dosajeAridoFino = findViewById(R.id.dosajeAridoFino);
        dosajeAridoGrueso = findViewById(R.id.dosajeAridoGrueso);
        dosajeAgua = findViewById(R.id.dosajeAgua);
        dosajeCementoPortland.setText(String.valueOf(calcularValorCemento(resultado[0])));
        dosajeAridoFino.setText(String.valueOf(resultado[1]));
        dosajeAridoGrueso.setText(String.valueOf(resultado[2]));
        dosajeAgua.setText(String.valueOf(resultado[3]*1000));

    }

    public double calcularValorCemento(double valorACalcular){
        return (valorACalcular * 1400 / 50);
    }

}
