package logydes.com.mx.centroenlinea.Inside;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import logydes.com.mx.centroenlinea.Adapter.AdapterMisImagenes;
import logydes.com.mx.centroenlinea.Helper.Singleton;
import logydes.com.mx.centroenlinea.Pojos.Imagenes;
import logydes.com.mx.centroenlinea.R;
import logydes.com.mx.centroenlinea.Utils.AppConfig;
import logydes.com.mx.centroenlinea.Utils.AppController;
import logydes.com.mx.centroenlinea.Utils.Utilidades;

/**
 * Created by devch on 17/06/16.
 */
public class getImagenes {

    private static final String TAG = "RESPUESTA";
    private ProgressDialog pDialog;
    private Utilidades Utl;
    private WebView webview;
    private Activity activity;
    private AdapterMisImagenes AmI;
    private RecyclerView listaMM;

    public getImagenes(Activity activity) {
        this.activity = activity;
        pDialog = new ProgressDialog(activity);
        pDialog.setCancelable(false);
        Utl = new Utilidades(this.pDialog);
        listaMM = (RecyclerView) activity.findViewById(R.id.rvImagenes);
    }

    public void getImageList(final String AppConfig) {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        this.pDialog.setMessage("Cargando...");
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

                            ArrayList<Imagenes> MM = new ArrayList<Imagenes>();
                            for (int i = 0;  i < jObj.length(); i++ ) {
                                int _idmdenuncia = Integer.valueOf(rec.getString("idmdenuncia"));
                                String _denuncia = String.valueOf(rec.getString("denuncia"));
                                String _idF = String.valueOf(rec.getString("idF"));
                                String _imagen = String.valueOf(rec.getString("imagen"));
                                String _nombre = String.valueOf(rec.getString("nombre"));
                                String _celular = String.valueOf(rec.getString("celular"));
                                int _so_mobile = Integer.valueOf(rec.getString("so_mobile"));
                                double _latitud = Double.valueOf(rec.getString("latitud"));
                                double _longitud = Double.valueOf(rec.getString("longitud"));
                                int _modulo = Integer.valueOf(rec.getString("modulo"));
                                String _cmodulo = String.valueOf(rec.getString("cmodulo"));
                                int _megusta = Integer.valueOf(rec.getString("megusta"));
                                int _status_reparacion = Integer.valueOf(rec.getString("status_reparacion"));
                                String _domicilio = String.valueOf(rec.getString("domicilio"));
                                String _creado_el = String.valueOf(rec.getString("creado_el"));
                                String _cfecha = String.valueOf(rec.getString("cfecha"));
                                MM.add( new Imagenes( _idmdenuncia, _denuncia, _idF, _imagen, _nombre, _celular, _so_mobile, _latitud, _longitud, _modulo, _cmodulo, _megusta, _status_reparacion, _domicilio, _creado_el, _cfecha    ) );
                            }
                            Log.w("ARRAY IMAGENES", MM.toString());
                            Singleton.setArrImagenes(MM);

                            AmI = new AdapterMisImagenes(activity);  // new AdapterHijos(MM,activity);
                            listaMM.setAdapter(AmI);


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
                Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(activity,
                        error.getMessage(), Toast.LENGTH_LONG).show();
                Utl.hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();
                params.put("username", Singleton.getUsername());
                return params;

            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }


}
