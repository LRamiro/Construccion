package lrbresca.com.proyectoconstruccion;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Aquí puedes poner una petición de permisos o lo que necesites previo a iniciar HomeActiviy.
        //Luego iniciamos nuestra home activity
        startActivity(new Intent(SplashActivity.this, HomeActivity.class));
        //Teminamos la SplashActivity
        finish();
    }
}