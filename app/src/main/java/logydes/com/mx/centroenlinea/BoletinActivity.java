package logydes.com.mx.centroenlinea;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.WebView;
import android.webkit.WebViewClient;
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

import logydes.com.mx.centroenlinea.Utils.AppController;
import logydes.com.mx.centroenlinea.Utils.Utilidades;

public class BoletinActivity extends AppCompatActivity {

    private static final String TAG = "RESPUESTA";
    private Activity context;
    private ProgressDialog pDialog;
    private Utilidades Utl;
    private WebView webview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*
        setContentView(R.layout.activity_boletin);

        webview = (WebView) findViewById(R.id.webview);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.setWebViewClient(new Callback());

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        Utl = new Utilidades(pDialog);

        getDocument(AppConfig.URL_BOLETIN,AppConfig.URL_BOLETIN_TYPE);
        */

    }


    private void getDocument(String AppConfig, final int Type) {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        pDialog.setMessage("Cargando...");
        Utl.showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Login Response: " + response.toString());
                Utl.hideDialog();

                try {
                    JSONArray jObj = new JSONArray(response);
                    JSONObject rec = jObj.getJSONObject(0);

                    boolean error = false;

                    String msg = rec.getString("msg");
                    if ( !msg.equals("OK") ) error = true;

                    // Check for error node in json
                    if (!error) {

                        String ruta = "";
                        switch (Type) {
                            case 0:
                                ruta = rec.getString("ruta");
                                break;
                        }

                        Log.e(TAG,ruta);

                        // Now store the user in SQLite
                        webview.loadUrl("http://docs.google.com/gview?embedded=true&url=" + ruta);
                        // setContentView(webview);

                        //


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
                Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
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


    // To handle "Back" key press event for WebView to go back to previous screen.
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webview.canGoBack()) {
            webview.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}
