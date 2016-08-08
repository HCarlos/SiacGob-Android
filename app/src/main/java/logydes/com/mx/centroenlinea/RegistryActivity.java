package logydes.com.mx.centroenlinea;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import logydes.com.mx.centroenlinea.Helper.SQLiteHandler;
import logydes.com.mx.centroenlinea.Helper.SessionManager;
import logydes.com.mx.centroenlinea.Utils.Singleton;
import logydes.com.mx.centroenlinea.Utils.AppConfig;
import logydes.com.mx.centroenlinea.Utils.AppController;
import logydes.com.mx.centroenlinea.Utils.Utilidades;

public class RegistryActivity extends AppCompatActivity {
    //private static final String TAG = RegisterActivity.class.getSimpleName();
    private static final String TAG = "RESPUESTA";
    private Button btnRegistry;
    private EditText inputEmail;
    private EditText inputPassword;
    private EditText re_inputEmail;
    private EditText re_inputPassword;
    private ProgressDialog pDialog;
    private SessionManager session;
    private SQLiteHandler db;
    private String uuid;
    private LocationManager lm;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registry);

        if (!Utilidades.isNetworkConnected(this)) {
            Toast.makeText(getApplicationContext(), "Por favor, conéctese a internet!", Toast.LENGTH_LONG).show();
            return;
        }

        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        try {
            Utilidades.GetGPS(this, lm, 0);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Utilidades.GetSMSData(this, lm, 1);

        // GetGPS();

        uuid = Settings.Secure.getString(getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);

        inputEmail = (EditText) findViewById(R.id.email);
        re_inputEmail = (EditText) findViewById(R.id.re_email);
        inputPassword = (EditText) findViewById(R.id.password);
        re_inputPassword = (EditText) findViewById(R.id.re_password);
        btnRegistry = (Button) findViewById(R.id.btnRegistry);

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        // Registry button Click Event
        btnRegistry.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();

                String re_email = re_inputEmail.getText().toString().trim();
                String re_password = re_inputPassword.getText().toString().trim();

                // Check for empty data in the form
                if (!email.isEmpty() && !password.isEmpty() && !re_email.isEmpty() && !re_password.isEmpty()) {

                    if (!email.equals(re_email)) {
                        Toast.makeText(getApplicationContext(),
                                "No coinciden los correos electrónicos." + email + "|" + re_email + ".", Toast.LENGTH_LONG)
                                .show();
                    } else {
                        if (!password.equals(re_password)) {
                            Toast.makeText(getApplicationContext(),
                                    "No coinciden los passwords.", Toast.LENGTH_LONG)
                                    .show();

                        } else {
                            checkRegistry(email, password);
                        }
                    }

                } else {
                    // Prompt user to enter credentials
                    Toast.makeText(getApplicationContext(),
                            "Por favor, ingrese los datos que se requieren.", Toast.LENGTH_LONG)
                            .show();
                }
            }

        });

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    /**
     * function to verify login details in mysql db
     */
    private void checkRegistry(final String email, final String password) {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        pDialog.setMessage("Registry in ...");
        showDialog();

        StringRequest strReq = new StringRequest(Method.POST,
                AppConfig.URL_REGISTER, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Registry Response: " + response.toString());
                hideDialog();

                try {
                    JSONArray jObj = new JSONArray(response);
                    JSONObject rec = jObj.getJSONObject(0);

                    boolean error = false;

                    String msg = rec.getString("msg");
                    if (!msg.equals("OK")) error = true;

                    // Check for error node in json
                    if (!error) {


                        new AlertDialog.Builder(RegistryActivity.this)
                            .setTitle("Gracias por registrarse")
                            .setMessage("Se ha enviado un correo electrónico a la cuenta que acaba de proporcionar para que valide sus datos y pueda ingresar a la Plataforma.")
                            .setCancelable(false)
                            .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(RegistryActivity.this,
                                            MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }).create().show();


                    } else {
                        // Error in login. Get the error message

                        Toast.makeText(getApplicationContext(),
                                msg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Registry Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url

                Log.e(TAG, "ANDROID ID: " + uuid);

                Map<String, String> params = new HashMap<String, String>();
                params.put("username", email);
                params.put("password", password);

                params.put("nombre", "Android");
                params.put("celular", "Android");

                params.put("idF", uuid);

                params.put("latitud", Double.toString(Singleton.getLatitude()));
                params.put("longitud", Double.toString(Singleton.getLongitude()));
                params.put("message", Build.USER);

                // params.put("tokenuser", uuid);
                // params.put("tD", "2");
                // params.put("device_token", uuid);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    public boolean SendUsEmail(View view) {
        Intent email = new Intent(Intent.ACTION_SEND);
        email.putExtra(Intent.EXTRA_EMAIL, new String[]{"centroenlinea@villahermosa.gob.mx"});
        email.putExtra(Intent.EXTRA_SUBJECT, "Correo de " + Singleton.getUsername());
        email.putExtra(Intent.EXTRA_TEXT, "Mensaje: ");
        email.setType("message/rfc822");
        startActivity(Intent.createChooser(email, "Elija un cliente de correo electrónico :"));
        return true;
    }

}

