package com.sergio.locateapp;

import android.Manifest;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.PowerManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;


public class MapaActivity extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_LOCATION= 1;

    Context context;
    Button btnIniciar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);


        context = this;
        btnIniciar = findViewById(R.id.btnIniciar);



        if (Build.VERSION.SDK_INT >= 23) {


            Log.d("MapaActivity","Prueba Build.VERSION.SDK_INT >= 23 ");

            Intent intent = new Intent();
            String packageName = context.getPackageName();
            PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);

            if (pm.isIgnoringBatteryOptimizations(packageName))
                intent.setAction(Settings.ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS);
            else {
                intent.setAction(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
                intent.setData(Uri.parse("package:" + packageName));
            }

            context.startActivity(intent);

        }

        validateService();

        btnIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startService(new Intent(context, LocationService.class));

                validateService();

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        validarPermisos();

    }

    public void validateService() {

        Boolean isRunning = isMyServiceRunning(LocationService.class);

        if (isRunning)
            btnIniciar.setEnabled(false);
    }


    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    public void validarPermisos() {


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {




            int permissionCheckFineLocation = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
            int permissionCheckCoaeseLocation = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);


            if (permissionCheckFineLocation != PackageManager.PERMISSION_GRANTED &&
                    permissionCheckCoaeseLocation != PackageManager.PERMISSION_GRANTED) {

                if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.ACCESS_COARSE_LOCATION)) {

                    // El permiso ya fue rechazado antes
                    // Mostrar alerta de explicacion

                } else {

                    //De lo contrario carga la ventana para autorizar el permiso
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                            MY_PERMISSIONS_REQUEST_LOCATION);

                }
            }


        }


    }



    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    //Permiso aceptado

                } else {

                    //Permiso denegado
                }
                return;
            }
        }
    }

}
