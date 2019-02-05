package joseocampo.VTSBD.com;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private Button btnActivarUbicacion;
    private TextView txtDireccion,txtCoordenadas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //preguntamos si el permiso está denegado
        //si de seamos mostrar un mensaje de activacion del permiso.

        btnActivarUbicacion = (Button)findViewById(R.id.btnActivarUbicacion);

        txtDireccion = (TextView)findViewById(R.id.txtDireccion);
        txtCoordenadas = (TextView)findViewById(R.id.txtCoordenadas);

        int permissionCheck =
                ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION);

        if(permissionCheck == PackageManager.PERMISSION_DENIED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)){

            }else{
                //si el permiso no está denegado, hacemos la solicitud del permiso.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
            }
        }

        btnActivarUbicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocationManager locationManager =
                        (LocationManager)MainActivity.this.getSystemService(Context.LOCATION_SERVICE);

               // Toast.makeText(getBaseContext(),"Hola",Toast.LENGTH_LONG).show();
                final boolean gpsActivado = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
                if(!gpsActivado){

                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("Para que la aplicacion funcione es recomandable activar GPS !");
                    builder.setMessage("Desea activar GPS ?");
                    builder.setCancelable(false);
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(getApplicationContext(), "Activando GPS", Toast.LENGTH_SHORT).show();
                            Intent settingsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivity(settingsIntent);
                        }
                    });

                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(getApplicationContext(), "La aplicacion no funciona sin GPS", Toast.LENGTH_SHORT).show();
                        }
                    });

                    builder.show();



                }

                LocationListener locationListener = new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        txtCoordenadas.setText("Latitud:  "+location.getLatitude()+"    Longitud:  "
                                +location.getLongitude()
                        );
                        obtenerDireccion(location);

                    }

                    @Override
                    public void onStatusChanged(String provider, int status, Bundle extras) {

                    }

                    @Override
                    public void onProviderEnabled(String provider) {

                    }

                    @Override
                    public void onProviderDisabled(String provider) {
                        ///Intent settingsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                       // startActivity(settingsIntent);


                    }
                };
                int permissionCheck = ContextCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.ACCESS_FINE_LOCATION);
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,locationListener);
            }
        });


    }
    public void verMenu(View view){
        Intent intent = new Intent(this,Main2Activity.class);

        startActivity(intent);

    }
    public  void obtenerDireccion(Location location)   {
        if(location!= null){
            if(location.getLongitude()!= 0 && location.getLatitude()!=0){
                Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                List<Address> addressList = null;
                try {
                    addressList = geocoder.getFromLocation(
                            location.getLatitude(),location.getLongitude(),1);
                    if(!addressList.isEmpty()){
                        Address address = addressList.get(0);
                        txtDireccion.setText("Direccion:  "+address.getAddressLine(0));
                    }
                } catch (IOException e) {
                   Toast.makeText(this,"Error: "+e.getMessage(),Toast.LENGTH_LONG).show();
                }

            }
        }
    }
}
