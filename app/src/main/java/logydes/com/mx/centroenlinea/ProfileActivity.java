package logydes.com.mx.centroenlinea;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import logydes.com.mx.centroenlinea.Adapter.AdapterMisImagenes;
import logydes.com.mx.centroenlinea.Helper.SQLiteHandler;
import logydes.com.mx.centroenlinea.Helper.SessionManager;
import logydes.com.mx.centroenlinea.Utils.AppConfig;
import logydes.com.mx.centroenlinea.Utils.AppController;
import logydes.com.mx.centroenlinea.Utils.Singleton;
import logydes.com.mx.centroenlinea.Utils.TaskGetImage;
import logydes.com.mx.centroenlinea.Utils.Utilidades;

public class ProfileActivity extends AppCompatActivity {

    private SQLiteHandler db;
    private SessionManager session;

    private AutoCompleteTextView fullName;
    private AutoCompleteTextView domicilio;
    private AutoCompleteTextView numCell;
    private Button btnSaveProfile;

    private static final String TAG = "RESPUESTA";
    private ProgressDialog pDialog;
    private Utilidades Utl;
    private Activity activity;
    private Context context;
    private TaskGetImage task;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        fullName = (AutoCompleteTextView) findViewById(R.id.fullName);
        domicilio = (AutoCompleteTextView) findViewById(R.id.domicilio);
        numCell = (AutoCompleteTextView) findViewById(R.id.numCell);
        btnSaveProfile = (Button) findViewById(R.id.btnSaveProfile);

        fullName.setText(Singleton.getFullName());
        domicilio.setText(Singleton.getDomicilio());
        numCell.setText(Singleton.getNumCell());

        this.activity = this;
        context = this;

        pDialog = new ProgressDialog(activity);
        pDialog.setCancelable(false);
        Utl = new Utilidades(this.pDialog);

        btnSaveProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String _fullName = fullName.getText().toString();
                String _domicilio = domicilio.getText().toString();
                String _numCell = numCell.getText().toString();

                modProfile(AppConfig.URL_SAVE_DATA_PROFILE, _fullName, _domicilio, _numCell );

            }

        });

    }

    public void modProfile(final String AppConfig, final String fullName, final String domicilio, final String numCell) {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        if (!Utilidades.isNetworkConnected(this)) {
            Toast.makeText(this.getApplicationContext(), "Por favor, conéctese a internet!", Toast.LENGTH_LONG).show();
            return;
        }

        this.pDialog.setMessage("Guardando...");
        this.Utl.showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "DOWNLOAD IMAGES Response: " + response.toString());
                Utl.hideDialog();

                try {
                    JSONArray jObj = new JSONArray(response);
                    JSONObject rec = jObj.getJSONObject(0);

                    boolean error = false;

                    String msg = rec.getString("msg");
                    if (!msg.equals("OK")) error = true;

                    if (!error) {

                        new AlertDialog.Builder(activity)
                                .setTitle("Atención")
                                .setMessage("Datos guardados con éxito.")
                                .setCancelable(false)
                                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Singleton.setFullName(fullName);
                                        Singleton.setDomicilio(domicilio);
                                        Log.e("DOMICILIO: ", domicilio);
                                        Singleton.setNumCell(numCell);

                                        db = new SQLiteHandler(getApplicationContext());
                                        session = new SessionManager(getApplicationContext());

                                        db.updateUsers();

                                        Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
                                        startActivity(intent);
                                        finish();

                                    }
                                }).create().show();

                    } else {
                        Toast.makeText(activity,
                                msg, Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(activity, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Delete Imagen Error: " + error.getMessage());
                Toast.makeText(activity,
                        error.getMessage(), Toast.LENGTH_LONG).show();
                Utl.hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();
                params.put("username", Singleton.getUsername());
                params.put("celular", numCell);
                params.put("domicilio", domicilio);
                params.put("fullname", fullName);
                return params;

            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

}
