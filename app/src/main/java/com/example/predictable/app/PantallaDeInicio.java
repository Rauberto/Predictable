package com.example.predictable.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by Robert on 01/05/2014.
 */
public class PantallaDeInicio extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantalladeinicio);

        //Vamos a declarar un nuevo thread
        Thread timer = new Thread(){
            //El nuevo Thread exige el metodo run
            public void run(){
                try{
                    sleep(2000);
                }catch(InterruptedException e){
                    //Si no puedo ejecutar el sleep muestro el error
                    e.printStackTrace();
                }finally{
                    //Llamo a la nueva actividad
                    //startActivity recibe por parametro un objeto del tipo Intent
                    //El Intent recibibe por parametro el NAME de la actividad que vamos a invocar
                    //Es el mismo que colocamos en el manifiesto
                    Intent actividadPrincipal = new Intent("com.example.predictable.app.MainActivity.PRINCIPAL");
                    startActivity(actividadPrincipal);
                    finish();
                }
            }
        };
        //ejecuto el thread
        timer.start();
    }
}
