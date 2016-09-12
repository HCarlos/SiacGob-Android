package logydes.com.mx.centroenlinea.Inside;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.webkit.WebView;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import logydes.com.mx.centroenlinea.Adapter.AdapterMisImagenes;
import logydes.com.mx.centroenlinea.Utils.Singleton;
import logydes.com.mx.centroenlinea.Pojos.Imagenes;
import logydes.com.mx.centroenlinea.R;
import logydes.com.mx.centroenlinea.Utils.AppController;
import logydes.com.mx.centroenlinea.Utils.TaskGetImage;
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
    private Context context;
    private TaskGetImage task;

    public getImagenes(Activity activity) {
        this.activity = activity;
        context = this.activity.getBaseContext();
        pDialog = new ProgressDialog(activity);
        pDialog.setCancelable(false);
        Utl = new Utilidades(this.pDialog);
        listaMM = (RecyclerView) activity.findViewById(R.id.rvImagenes);
    }

    public void getImageList(final String AppConfig, final int Parameter) {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        if (!Utilidades.isNetworkConnected(activity)) {
            Toast.makeText(activity.getApplicationContext(), "Por favor, conéctese a internet!", Toast.LENGTH_LONG).show();
            return;
        }

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

                                rec = jObj.getJSONObject(i);

                                int _idmdenuncia = Integer.valueOf(rec.getString("idmdenuncia"));
                                String _denuncia = String.valueOf(rec.getString("denuncia"));
                                String _idF = String.valueOf(rec.getString("idF"));
                                final String _imagen = String.valueOf(rec.getString("imagen"));
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

                                /*
                                String[] arr_imagen_s = _imagen.split("\\.");
                                String _imagen_s = arr_imagen_s[0]+"-s."+arr_imagen_s[1];
                                if ( saveImageInternalTemp(_imagen_s) ){
                                    MM.add( new Imagenes( _idmdenuncia, _denuncia, _idF, _imagen, _nombre, _celular, _so_mobile, _latitud, _longitud, _modulo, _cmodulo, _megusta, _status_reparacion, _domicilio, _creado_el, _cfecha    ) );
                                }
                                */

                                MM.add( new Imagenes( _idmdenuncia, _denuncia, _idF, _imagen, _nombre, _celular, _so_mobile, _latitud, _longitud, _modulo, _cmodulo, _megusta, _status_reparacion, _domicilio, _creado_el, _cfecha    ) );

                            }
                            Log.w("ARRAY IMAGENES", MM.toString());
                            Singleton.setArrImagenes(MM);
                            if (Parameter == 1) {
                                AmI = new AdapterMisImagenes(activity);  // new AdapterHijos(MM,activity);
                                listaMM.setAdapter(AmI);
                            }


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

    private boolean creaBitMap(String imageName){

        Bitmap bitmap = null;
        String _URL = imageName;
        boolean retorno = false;

        try {

            String src = "http://siac.tabascoweb.com/upload/"+_URL;
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            bitmap = BitmapFactory.decodeStream(input);

            String filename = imageName;
            File tempDir = context.getExternalCacheDir();
            tempDir = new File(tempDir.getAbsolutePath() + "/temp/");
            File dest = new File(tempDir, filename);

            String path = context.getExternalCacheDir().toString();
            OutputStream fOut = null;
            File file = new File(path + "/temp/", filename); // the File to save to
            fOut = new FileOutputStream(file);
            if ( fOut != null ) {
                Bitmap pictureBitmap = bitmap; // obtaining the Bitmap
                pictureBitmap.compress(Bitmap.CompressFormat.JPEG, 85, fOut); // saving the Bitmap to a file compressed as a JPEG with 85% compression rate
                fOut.flush();
                fOut.close(); // do not forget to close the stream

                // MediaStore.Images.Media.insertImage(context.getContentResolver(),file.getAbsolutePath(),file.getName(),file.getName());
                Log.w("IMAGEN:", dest.getPath());
                retorno = true;
            }

        } catch (IOException e) {
            e.printStackTrace();
            Log.e("doInBackground","Error");
        }
        return retorno;


    }

    /// AQUI FINALIZA EL METODO PARA OBTENER EL LISTADO DE IMAGENES


    // INICIA EL MODULO PARA ELIMINAR UNA IMAGEN

    public void deleteImage(final String AppConfig, final int IdMDenuncia) {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        if (!Utilidades.isNetworkConnected(activity)) {
            Toast.makeText(activity.getApplicationContext(), "Por favor, conéctese a internet!", Toast.LENGTH_LONG).show();
            return;
        }

        this.pDialog.setMessage("Eliminando...");
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
                                .setMessage("Imagen eliminada con éxito.")
                                .setCancelable(false)
                                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        getImageList(logydes.com.mx.centroenlinea.Utils.AppConfig.URL_DOWNLOAD_IMAGES, 1);
                                        return;
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
                params.put("idmdenuncia", String.valueOf(IdMDenuncia));
                return params;

            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }







}
