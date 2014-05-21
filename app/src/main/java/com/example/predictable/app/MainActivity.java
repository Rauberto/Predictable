package com.example.predictable.app;

//http://www.youtube.com/watch?v=vjAgGkLNMwM


import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity implements SensorEventListener {

    private Button restart;
    private ImageView start;
    private SensorManager sensorManager;
    private ImageView imagen;
    private int i=0;
    private int j=0;
    private float[] valor;
    private float valorFinal;
    private MediaPlayer mp;
    ScaleAnimation scale;
    AlphaAnimation alpha;
    RotateAnimation rotate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //restart = (Button) findViewById(R.id.restart);
        start = (ImageView) findViewById(R.id.start);
        imagen = (ImageView) findViewById(R.id.imagen);
        mp = MediaPlayer.create(MainActivity.this, R.raw.sonido);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);


        alpha = new AlphaAnimation(0, 1);
        alpha.setDuration(1000);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Vibrator v = (Vibrator) getSystemService(getApplicationContext().VIBRATOR_SERVICE);

                if(j>0) {
                    Toast.makeText(getApplicationContext(), "Parametrizando datos...", Toast.LENGTH_LONG).show();
//                    rotate = new RotateAnimation(0,360);
//                    rotate.setDuration(2000);
//                    imagen.startAnimation(rotate);
                    start.setImageResource(R.drawable.lamparacinco);
//                    imagen.setImageResource(R.drawable.geniotrans);
                    alpha = new AlphaAnimation(1.00f, 0.00f);
                    alpha.setDuration(3500);
                    imagen.startAnimation(alpha);
                    mp.start();
                    imagen.setImageResource(R.drawable.genio);

                    // Vibrar durante 2 segundos
                    v.vibrate(2000);
                    //Fade in
                    alpha = new AlphaAnimation(0, 1);
                    alpha.setDuration(1000);

                    Handler handler = new Handler();

                    handler.postDelayed(new Runnable() {
                        public void run() {
                            imagen.startAnimation(alpha);
                            start.setImageResource(R.drawable.lamparauno);
                            valorFinal = Math.abs(valor[1]) + Math.abs(valor[2]) + Math.abs(valor[0]);
                            if (valorFinal > 200 && valorFinal < 300) {
                                imagen.setImageResource(R.drawable.genio2);
                            }
                            else if (valorFinal < 60) {
                                imagen.setImageResource(R.drawable.genio6);
                            }
                            else if (valorFinal > 300 && valorFinal < 400) {
                                imagen.setImageResource(R.drawable.genio3);
                            } else if (valorFinal > 700) {
                                imagen.setImageResource(R.drawable.genio5);
                            } else if (valorFinal > 400 && valorFinal < 700) {
                                imagen.setImageResource(R.drawable.genio4);
                            }else
                                imagen.setImageResource(R.drawable.genio3);
                        }
                    }, 3000);
                }
                else {
                    imagen.setImageResource(R.drawable.genio1);
                    imagen.startAnimation(alpha);
                    j++;
                }
            }
        });


//        restart.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                imagen.setImageResource(R.drawable.genio);
//            }
//        });
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            valor=getMagnetic(event);
            verMagnetic(event,i);
        }
        if (i<6)
            i++;
        else
            i=0;
    }

    private float[] getMagnetic(SensorEvent event) {
        float[] values = event.values;
        // Magnetic
        float x = values[0];
        float y = values[1];
        float z = values[2];

        String xf = "x: " + x + " ";
        String yf = "y: " + y + " ";
        String zf = "z: " + z + " ";

        return values;
    }

    private void verMagnetic(SensorEvent event, int i) {
        float[] values = event.values;
        int[] valorMedio = new int[7];
        float paso;
        final TextView textoMedio = (TextView) findViewById(R.id.textoMedio);
        float x = values[0];
        float y = values[1];
        float z = values[2];

        paso = Math.abs(x)+Math.abs(y)+Math.abs(z);
        valorMedio[i] = (int) paso;
        textoMedio.setText(""+ valorMedio[i]);
    }

    static int[] suma(int[] datos){
        int suma=0;

        for (int i=0; i< datos.length ; i++)
            suma+=datos[i];
        return datos;
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Escucha el sensor electromagnetico
        sensorManager.registerListener(this,sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD),
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        // unregister listener
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    //Se implementa porque se hace uso de exteds SensorEventListener
    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
    public void lanzarAcercaDe(View view){
//        onPause();
        Intent ea = new Intent(this, AcercaDe.class);
        startActivity(ea);
    }

    public void salir(View view){
        finish();
    }
}