package DB;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
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

import Adapter.Adapter_Lista_Elementos;
import Helper.Singleton;
import Pojos.Lista_Elementos;
import logydes.com.mx.centroenlinea.R;
import Utils.AppController;
import Utils.Utilidades;

/**
 * Created by devch on 24/06/16.
 */

public class dbLista_Elementos {
    private static final String TAG = "RESPUESTA";
    private ProgressDialog pDialog;
    private Utilidades Utl;
    private Context context;
    private ArrayList<Lista_Elementos> mm;
    private RecyclerView listaMM;
    private Activity activity;
    private Adapter_Lista_Elementos mad;
    private int Type;

    public dbLista_Elementos(Context context, Activity act) {
        this.context = context;
        this.activity = act;

        /*
        listaMM = (RecyclerView) act.findViewById(R.id.rvListaElementos);
        LinearLayoutManager llm = new LinearLayoutManager(context);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        listaMM.setLayoutManager(llm);
        */

    }

    public void obtenerDatos(String strURL, int _Type){

        Type = _Type;

        String tag_string_req = "req_login";

        pDialog = new ProgressDialog(activity);
        pDialog.setCancelable(false);
        pDialog.setMessage("Cargando...");
        Utl = new Utilidades(pDialog);
        Utl.showDialog();

        // Log.e(TAG, AppConfig.URL_GET_HIJOS );

        StringRequest strReq = new StringRequest(Request.Method.POST,
                strURL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Utl.hideDialog();
                Log.e(TAG, response );
                try {
                    JSONArray jObj = new JSONArray(response);
                    JSONObject rec = jObj.getJSONObject(0);

                    boolean error = false;

                    String msg = rec.getString("msg");
                    if (!msg.equals("OK")) error = true;

                    if (!error) {
                        ArrayList<Lista_Elementos> MM = new ArrayList<Lista_Elementos>();
                        for (int i = 0;  i < jObj.length(); i++ ) {
                            rec = jObj.getJSONObject(i);
                            int idelemento = 0;
                            String label = "";
                            switch (Type){
                                case 0:
                                    idelemento = rec.getInt("idtarea");
                                    label = rec.getString("titulo_tarea");
                                    break;
                                case 1:
                                    idelemento = rec.getInt("idcommensajedestinatario");
                                    label = rec.getString("titulo_mensaje");
                                    break;
                            }

                            MM.add( new Lista_Elementos( idelemento,label) );
                        }

                        Singleton.setRsElementos(MM);

                        mad = new Adapter_Lista_Elementos(activity);  // new AdapterLista_Elementos(MM,activity);
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
                /// Log.e(TAG,String.valueOf(Type));
                Log.e(TAG,String.valueOf(Singleton.getIdAlu()));
                Log.e(TAG,Singleton.getUsername());
                if (Type == 0){
                    params.put("username", Singleton.getUsername());
                    params.put("sts", "0");
                    params.put("iduseralu", String.valueOf( Singleton.getIdAlu() ) );
                    params.put("tipoconsulta", String.valueOf( Type ) );
                }
                if (Type == 1){
                    params.put("username", Singleton.getUsername());
                    params.put("sts", "0");
                    params.put("iduseralu", String.valueOf( Singleton.getIdAlu() ) );
                    params.put("tipoconsulta", String.valueOf( Type ) );
                }
                return params;

            }

        };

        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);

    }

    public void resfresh_Lista_Elementos(){
        mad = new Adapter_Lista_Elementos(activity);  // new AdapterLista_Elementos(MM,activity);
        listaMM.setAdapter(mad);
    }

}
