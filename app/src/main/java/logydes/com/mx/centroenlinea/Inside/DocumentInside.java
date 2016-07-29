package logydes.com.mx.centroenlinea.Inside;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Build;
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

import java.util.HashMap;
import java.util.Map;

import logydes.com.mx.centroenlinea.Utils.AppConfig;
import logydes.com.mx.centroenlinea.Utils.AppController;
import logydes.com.mx.centroenlinea.Utils.Utilidades;

/**
 * Created by devch on 17/06/16.
 */
public class DocumentInside {

    private static final String TAG = "RESPUESTA";
    private ProgressDialog pDialog;
    private Utilidades Utl;
    private WebView webview;
    private Activity context;

    public DocumentInside(final WebView webview, Activity context) {
        this.webview = webview;
        this.context = context;
    }

    public boolean onCreateBoletin(){

        this.webview.getSettings().setJavaScriptEnabled(true);
        this.webview.setWebViewClient(new Callback());
        this.pDialog = new ProgressDialog(this.context);
        this.pDialog.setCancelable(false);
        this.Utl = new Utilidades(this.pDialog);
        getDocument(AppConfig.URL_LOGIN,0);
        return true;

    }

    public boolean onCreateObject(String URL, int Type){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
            webview.getSettings().setAllowUniversalAccessFromFileURLs(true);
        webview.getSettings().setJavaScriptEnabled(true);

        this.pDialog = new ProgressDialog(this.context);
        this.pDialog.setCancelable(false);
        webview.loadUrl(URL);
        webview.setWebViewClient( new WebViewClient(){

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                // TextView tv = (TextView) context.findViewById(R.id.bienvenida);
                LinearLayout.LayoutParams lpView = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                // tv.setVisibility(0);
                webview.setLayoutParams(lpView);
                pDialog.setMessage("Cargando...");
                Utl = new Utilidades(pDialog);
                Utl.showDialog();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                pDialog.setMessage("...");
                Utl = new Utilidades(pDialog);
                Utl.hideDialog();
            }

        });
        return true;

    }

    private void getDocument(final String AppConfig, final int Type) {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        this.pDialog.setMessage("Cargando...");
        this.Utl.showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Login Response: " + response.toString());
                Utl.hideDialog();

                try {
                    if (Type == 0) {
                        JSONArray jObj = new JSONArray(response);
                        JSONObject rec = jObj.getJSONObject(0);

                        boolean error = false;

                        String msg = rec.getString("msg");
                        if (!msg.equals("OK")) error = true;

                        if (!error) {

                            if ( Type == 0 ){
                                String ruta = "";
                                Log.e(TAG, ruta);
                                ruta = "http://docs.google.com/gview?embedded=true&url=" + rec.getString("ruta");
                                onCreateObject(ruta, Type);
                            }

                        } else {
                            Toast.makeText(context,
                                    msg, Toast.LENGTH_LONG).show();
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(context, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(context,
                        error.getMessage(), Toast.LENGTH_LONG).show();
                Utl.hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();
                return params;

            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }


    private class Callback extends WebViewClient {
        /*
        @Override
        public boolean shouldOverrideUrlLoading(
                WebView view, String url) {
            return(false);
        }
        */
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            // TODO Auto-generated method stub
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // TODO Auto-generated method stub

            view.loadUrl(url);
            return true;

        }

    }


}
