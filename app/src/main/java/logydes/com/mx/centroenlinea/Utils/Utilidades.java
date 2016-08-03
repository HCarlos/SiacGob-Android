package logydes.com.mx.centroenlinea.Utils;

import android.Manifest;
import android.app.Activity;
import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;


import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import logydes.com.mx.centroenlinea.Helper.Singleton;

/**
 * Created by devch on 17/06/16.
 */
public class Utilidades {

    private static final String TAG = "RESPUESTA";
    private ProgressDialog pDialog;
    private static Activity activity;
    private static LocationManager lm;
    private static int tipo;
    private static double current_lattitude;
    private static double current_longitude;
    private static int TAKE_PICTURE = 1;
    private static int SELECT_PICTURE = 2;

    public Utilidades(ProgressDialog pDialog) {
        this.pDialog = pDialog;
    }

    public void showDialog() {
        if (!pDialog.isShowing())
            this.pDialog.show();
    }

    public void hideDialog() {
        if (pDialog.isShowing())
            this.pDialog.dismiss();
    }

    public static void GetGPS(Activity _activity, LocationManager _lm, int _tipo) throws IOException {

        activity = _activity;
        lm = _lm;
        tipo = _tipo;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && (ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    123);
        } else{
            if ( tipo == 0 ){
                getLatLon();
            }
        }
    }

    public static void GetSMSData(Activity _activity, LocationManager _lm, int _tipo) {

        activity = _activity;
        lm = _lm;
        tipo = _tipo;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && (ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_SMS},
                    123);
        } else{
            if ( tipo == 1 ){
                getSMSData();
            }
        }
    }

    public static void GetCamera(Activity _activity, int _tipo) {

        activity = _activity;
        tipo = _tipo;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && (ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CAMERA},
                    123);
        } else{
            if ( tipo == 2 ){
                Singleton.setIsCameraPresent(true);
            }
        }
    }

    public void onRequestPermissionsResult(final int requestCode, @NonNull final String[] permissions, @NonNull final int[] grantResults) throws IOException {
        // super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 123) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted.
                if (tipo == 0) {
                    getLatLon();
                }
                if (tipo == 1) {
                    getSMSData();
                }
                if (tipo == 2) {
                    // setFoto();
                    Singleton.setIsCameraPresent(true);
                }

            } else {
                Toast.makeText(activity, "No tiene los permisos necesarios, para utilizar esta App.", Toast.LENGTH_SHORT).show();
            }
        }
    }
/*
    public static void getLatLon(LocationManager lm2) {
        LocationManager lm = (LocationManager) activity.getBaseContext().getSystemService(activity.getBaseContext().LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            Singleton.setLongitude(location.getLongitude());
            Singleton.setLatitude(location.getLatitude());
            Log.e("LATITUDDDD: ", Double.toString(location.getLatitude()) );
        }
    }
*/

    public static void getLatLon() throws IOException {

        GPSTracker gps = new GPSTracker(activity);
        int status = 0;
        if (!gps.canGetLocation() ) {
            return;
        }
        status = GooglePlayServicesUtil
                .isGooglePlayServicesAvailable(activity);

        if (status == ConnectionResult.SUCCESS) {
            current_lattitude = gps.getLatitude();
            current_longitude = gps.getLongitude();

            Log.d("LAT LON", "" + current_lattitude + "-"
                    + current_longitude);

            if (current_lattitude == 0.0 && current_longitude == 0.0) {
                current_lattitude = 22.22;
                current_longitude = 22.22;

            }

        } else {
            current_lattitude = 22.22;
            current_longitude = 22.22;
        }

        Singleton.setLatitude(current_lattitude);
        Singleton.setLongitude(current_longitude);

        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(activity, Locale.getDefault());

        addresses = geocoder.getFromLocation(current_lattitude, current_longitude, 1);

        String address = addresses.get(0).getAddressLine(0);
        String city = addresses.get(0).getLocality();
        String state = addresses.get(0).getAdminArea();
        String country = addresses.get(0).getCountryName();
        String postalCode = addresses.get(0).getPostalCode();
        Singleton.setDireccion(address + " " + city + " " + state + " " + country + " " + postalCode );

    }

    public static void getSMSData() {
        TelephonyManager tManager = (TelephonyManager) activity.getBaseContext().getSystemService(Context.TELEPHONY_SERVICE);
        Singleton.setNombre( tManager.getSimOperatorName() );
        Singleton.setCelular( tManager.getLine1Number() );
    }

    public static void setFoto(Activity activity) {
        try {
            Log.e("ENTRO","SI");
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
            int code = TAKE_PICTURE;
            activity.startActivityForResult(intent, code);
        } catch(Exception e) {
            Toast.makeText(activity, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


}