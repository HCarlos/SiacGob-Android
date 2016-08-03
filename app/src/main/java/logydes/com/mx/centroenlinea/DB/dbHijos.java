package logydes.com.mx.centroenlinea.DB;

/**
 * Created by devch on 23/06/16.
 */

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import logydes.com.mx.centroenlinea.Pojos.Hijos;
import logydes.com.mx.centroenlinea.Utils.AppConfig;
import logydes.com.mx.centroenlinea.Utils.AppController;
import logydes.com.mx.centroenlinea.Utils.Utilidades;

/**
 * Created by devch on 2/06/16.
 */
public class dbHijos {
    private static final String TAG = "RESPUESTA";
    private ProgressDialog pDialog;
    private Utilidades Utl;
    private Context context;
    private ArrayList<Hijos> mm;
    private RecyclerView listaMM;
    private Activity  activity;
    private AdapterMisImagenes mad;

    public dbHijos(Context context, Activity act) {
        this.context = context;
        this.activity = act;

        /*
        listaMM = (RecyclerView) act.findViewById(R.id.rvHijos);
        LinearLayoutManager llm = new LinearLayoutManager(context);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        listaMM.setLayoutManager(llm);
        */

    }

    public void obtenerDatos(){
        String tag_string_req = "req_login";
        pDialog = new ProgressDialog(activity);
        pDialog.setCancelable(false);
        pDialog.setMessage("Cargando...");
        Utl = new Utilidades(pDialog);
        Utl.showDialog();

        // Log.e(TAG, AppConfig.URL_GET_HIJOS );

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_LOGIN, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Utl.hideDialog();

                try {
                    JSONArray jObj = new JSONArray(response);
                    JSONObject rec = jObj.getJSONObject(0);

                    boolean error = false;

                    String msg = rec.getString("msg");
                    if (!msg.equals("OK")) error = true;

                    if (!error) {
                        ArrayList<Hijos> MM = new ArrayList<Hijos>();
                        for (int i = 0;  i < jObj.length(); i++ ) {
                            rec = jObj.getJSONObject(i);
                            int idalu = rec.getInt("data");
                            String nombreAlu = rec.getString("label");
                            String grupo = rec.getString("grupo");
                            msg = rec.getString("msg");

                            MM.add( new Hijos( idalu,nombreAlu,grupo,msg) );
                        }

                        // Singleton.setRsHijos(MM);

                        mad = new AdapterMisImagenes(activity);  // new AdapterMisImagenes(MM,activity);
                        listaMM.setAdapter(mad);


                    } else {
                        Toast.makeText(context,
                                msg, Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(context, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Data Error: " + error.getMessage());
                Toast.makeText(context,
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

        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);

    }

    public void resfreshHijos(){
        mad = new AdapterMisImagenes(activity);  // new AdapterMisImagenes(MM,activity);
        listaMM.setAdapter(mad);
    }

}
