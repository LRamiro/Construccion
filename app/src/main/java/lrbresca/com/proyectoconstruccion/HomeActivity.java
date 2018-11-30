package lrbresca.com.proyectoconstruccion;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity{

    private static double cteCoefAporteCemento = 0.47;
    private static double cteCoefAporteAridoFino = 0.57;
    private static double cteCoefAporteAridoGrueso = 0.60;
    private static double cteCoefAporteAgua = 1;

    private ArrayList<Double> cttesCoefAportes;
    private ArrayList<Double> valoresACalcular;
    private ArrayList<Double> eArray;

    EditText dosajeCementoPortland, dosajeAridoFino, dosajeAridoGrueso;
    Button hacerCalculo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        dosajeCementoPortland = findViewById(R.id.dosajeCemento);
        dosajeAridoFino = findViewById(R.id.dosajeAridoFino);
        dosajeAridoGrueso = findViewById(R.id.dosajeAridoGrueso);
        hacerCalculo = findViewById(R.id.button);
        hacerCalculo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                coordinadorCalculo();
            }
        });
    }

    public void coordinadorCalculo(){
        iniciarArrays();
        calcularEs(valoresACalcular, cttesCoefAportes);
        double valorE = calcularSumaArray(eArray);
        double valorF = calcularC(valoresACalcular);
        double f17 = calcularF(valorF,  valorE);
        double[] penultimosValores = realizarCalculoFinal(f17, valorF, valoresACalcular);
        Intent intent = new Intent(this, ResultActivity.class);
        intent.putExtra("DatosAMostrar", penultimosValores);
        startActivity(intent);

    }

    public void iniciarArrays(){
        obtenerValoresConstantes();
        obtenerValoresCampos();
    }

    public void obtenerValoresConstantes(){
        cttesCoefAportes = new ArrayList<Double>();
        cttesCoefAportes.add(0, cteCoefAporteCemento);
        cttesCoefAportes.add(1, cteCoefAporteAridoFino);
        cttesCoefAportes.add(2, cteCoefAporteAridoGrueso);
        cttesCoefAportes.add(3, cteCoefAporteAgua);
    }

    public void obtenerValoresCampos(){
        valoresACalcular = new ArrayList<>();
        valoresACalcular.add(0, Double.parseDouble(dosajeCementoPortland.getText().toString()));
        valoresACalcular.add(1, Double.parseDouble(dosajeAridoFino.getText().toString()));
        valoresACalcular.add(2, Double.parseDouble(dosajeAridoGrueso.getText().toString()));
        valoresACalcular.add(3, calculoDosajeAgua(valoresACalcular));
    }

    public void calcularEs(ArrayList<Double> dosajesElegidos, ArrayList<Double> cttesCoefAportes){
        eArray = new ArrayList<>();
        double e = 0;
        for(int i=0; i<4; i++){
            if(i==3){
                e = calculoDosajeAgua(valoresACalcular) * cttesCoefAportes.get(3);
                eArray.add(i, e);
                break;
            }
            e = dosajesElegidos.get(i) * cttesCoefAportes.get(i);
            eArray.add(i, e);
        }
    }

    public double calcularSumaArray(ArrayList<Double> vector){
        double rdo = 0;
        for(int i=0; i<vector.size();i++){
            rdo = rdo + vector.get(i);
        }
        return rdo;
    }

    public double calcularC(ArrayList<Double> valoresACalcular){
        double c17 = (valoresACalcular.get(0) + valoresACalcular.get(1) + valoresACalcular.get(2)) * 1.1;
        return c17;
    }

    public double calcularF(double C, double E){
        double rdo = C / E;
        return rdo;
    }

    public double[] realizarCalculoFinal(double f17, double valorParcial, ArrayList<Double> valoresACalcular){
        double[] rdosParcialesEcuacion2 = new double[4];
        DecimalFormat df = new DecimalFormat("#,00");
        for(int i=0; i<valoresACalcular.size();i++){
            if(i==3){
                rdosParcialesEcuacion2[i]= Double.parseDouble(String.format("%.2f", (f17 / valorParcial * calculoDosajeAgua(valoresACalcular))));
                break;
            }
            rdosParcialesEcuacion2[i]= Double.parseDouble(String.format("%.2f", (f17 / valorParcial * valoresACalcular.get(i))));
        }
        return rdosParcialesEcuacion2;
    }

    public double calculoDosajeAgua(ArrayList<Double> valoresACalcular){
        double dosajeAgua = (valoresACalcular.get(0) + valoresACalcular.get(1) + valoresACalcular.get(2)) * 0.1;
        return dosajeAgua;
    }

    public ArrayList<Double> calculoResultado(ArrayList<Double> Fs, ArrayList<Double> cttesCoefAportes, double C, double dosajeAgua){
        double rdo = 0;
        ArrayList<Double> resultados = new ArrayList<>();
        for(int i=0; i<3; i++){
            if(i==3){
                rdo = (Fs.get(i)/C) * dosajeAgua;
                resultados.add(i, rdo);
                break;
            }
            rdo = (Fs.get(i)/C) * cttesCoefAportes.get(i);
            resultados.add(i, rdo);
        }
        return resultados;
    }
    
}
