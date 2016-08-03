package logydes.com.mx.centroenlinea;

// import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.loopj.android.http.Base64;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import logydes.com.mx.centroenlinea.Helper.Singleton;
import logydes.com.mx.centroenlinea.Utils.AppConfig;
import logydes.com.mx.centroenlinea.Utils.AppController;
import logydes.com.mx.centroenlinea.Utils.PhotoUtils;
import logydes.com.mx.centroenlinea.Utils.Utilidades;

public class ReportarActivity extends AppCompatActivity {
    // private static final String TAG = ReportarActivity.class.getSimpleName();
    private static final String TAG = "RESPUESTA";
    private Button btnReportar;
    private EditText txtReporte;
    private ProgressDialog pDialog;
    private String uuid;
    private Activity activity;
    private ImageView foto;

    private AlertDialog _photoDialog;
    private Uri mImageUri;
    private static final int ACTIVITY_SELECT_IMAGE = 1020,
            ACTIVITY_SELECT_FROM_CAMERA = 1040, ACTIVITY_SHARE = 1030;
    private PhotoUtils photoUtils;
    private Button photoButton;
    private ImageView photoViewer;
    private Button cmdTomarFoto;
    private Button cmdFototeca;
    private String pathphoto;

    public FileInputStream fileInputStream;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reportar);
        activity = this;

        Utilidades.GetCamera(activity,2);

        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        try {
            Utilidades.GetGPS(this, lm, 0);
        } catch (IOException e) {
            e.printStackTrace();
        }

        uuid = Settings.Secure.getString(getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);

        Singleton.setUUID(uuid);

        txtReporte = (EditText) findViewById(R.id.txtReporte);
        foto = (ImageView) findViewById(R.id.Foto);
        btnReportar = (Button) findViewById(R.id.btnReportar);
        cmdTomarFoto = (Button) findViewById(R.id.cmdFoto);
        cmdFototeca = (Button) findViewById(R.id.cmdFototeca);
        fileInputStream = null;

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        PackageManager pm = this.getPackageManager();

        if (!pm.hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            Toast.makeText(getApplicationContext(),
                    "La Cámara no esta habilitada.", Toast.LENGTH_LONG)
                    .show();
            return;
        }

        cmdTomarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(
                        "android.media.action.IMAGE_CAPTURE");
                File photo = null;
                try {
                    // place where to store camera taken picture
                    photo = PhotoUtils.createTemporaryFile("picture", ".jpg", getBaseContext());
                    photo.delete();
                } catch (Exception e) {
                    Log.v(getClass().getSimpleName(),
                            "No se puedo crear la imagen!");
                }
                mImageUri = Uri.fromFile(photo);
                pathphoto = mImageUri.toString();
                intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageUri);
                startActivityForResult(intent, ACTIVITY_SELECT_FROM_CAMERA);

            }
        });

        cmdFototeca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, ACTIVITY_SELECT_IMAGE);
            }
        });

        btnReportar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mImageUri != null) {
                    if (!pathphoto.trim().equalsIgnoreCase("")) {

                        if (isNetworkConnected())
                            checkReportar();
                        else
                            Toast.makeText(getApplicationContext(), "Por favor, conéctese a internet!", Toast.LENGTH_LONG).show();


                    }
                }else{
                    Toast.makeText(getApplicationContext(), "Ha ocurrido un error, intente de nuevo!", Toast.LENGTH_LONG).show();
                }
            }
        });

        photoUtils = new PhotoUtils(this);
        // Get intent, action and MIME type
        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();
        if (Intent.ACTION_SEND.equals(action) && type != null) {
            boolean fromShare;
            if ("text/plain".equals(type)) {
                fromShare = true;
            } else if (type.startsWith("image/")) {
                fromShare = true;
                mImageUri = (Uri) intent
                        .getParcelableExtra(Intent.EXTRA_STREAM);
                getImage(mImageUri);
            }
        }
        photoButton = (Button) findViewById(R.id.cmdFoto);
        photoViewer = (ImageView) findViewById(R.id.Foto);
        // getPhotoDialog();
        // setPhotoButton();


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }



    // DESDE AQUI EMPUEZA EL CODIGO CON PHOTOUTILS

    private void setPhotoButton(){
        photoButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                if(!getPhotoDialog().isShowing() && !isFinishing())
                    getPhotoDialog().show();
            }
        });
    }

    private AlertDialog getPhotoDialog() {
        if (_photoDialog == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(
                    this);
            builder.setTitle(R.string.photo_source);
            builder.setPositiveButton(R.string.camara, new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(
                            "android.media.action.IMAGE_CAPTURE");
                    File photo = null;
                    try {
                        // place where to store camera taken picture
                        photo = PhotoUtils.createTemporaryFile("picture", ".jpg", getBaseContext());
                        photo.delete();
                    } catch (Exception e) {
                        Log.v(getClass().getSimpleName(),
                                "Can't create file to take picture!");
                    }
                    mImageUri = Uri.fromFile(photo);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageUri);
                    startActivityForResult(intent, ACTIVITY_SELECT_FROM_CAMERA);

                }

            });
            builder.setNegativeButton(R.string.galeria, new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                    galleryIntent.setType("image/*");
                    startActivityForResult(galleryIntent, ACTIVITY_SELECT_IMAGE);
                }

            });
            _photoDialog = builder.create();

        }
        return _photoDialog;

    }


    public static File createTemporaryFile(String part, String ext,
                                           Context myContext) throws Exception {
        File tempDir = myContext.getExternalCacheDir();
        tempDir = new File(tempDir.getAbsolutePath() + "/temp/");
        if (!tempDir.exists()) {
            tempDir.mkdir();
        }
        // pathphoto = tempDir.
        return File.createTempFile(part, ext, tempDir);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mImageUri != null) {
            outState.putString("Uri", mImageUri.toString());
            pathphoto = mImageUri.toString();
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState.containsKey("Uri")) {
            mImageUri = Uri.parse(savedInstanceState.getString("Uri"));
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ACTIVITY_SELECT_IMAGE && resultCode == RESULT_OK) {
            mImageUri = data.getData();
            getImage(mImageUri);
            pathphoto = mImageUri.toString();
            Log.e("U    R     I:", pathphoto);
        } else if (requestCode == ACTIVITY_SELECT_FROM_CAMERA
                && resultCode == RESULT_OK) {
            pathphoto = mImageUri.toString();
            getImage(mImageUri);

        }
    }

    public void getImage(Uri uri) {
        Bitmap bounds = photoUtils.getImage(uri);
        if (bounds != null) {
            setImageBitmap(bounds);
        } else {
            //showErrorToast();
            Toast.makeText(this, "Ha ocurrido un error", Toast.LENGTH_SHORT).show();

        }
    }


/*

    interface PhotoSetter(){
        onPhotoDownloaded(Bitmap bitmap);
    }

*/


    private void setImageBitmap(Bitmap bitmap){
        photoViewer.setImageBitmap(bitmap);

    }



// AQUI FINALIZA EL CODIGO CON PHOTOUTILS

    /**
     * function to verify login details in mysql db
     */
    private void checkReportar() {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        pDialog.setMessage("Envio de reporte");
        showDialog();

        StringRequest strReq = new StringRequest(Method.POST,
                AppConfig.URL_SEND_FOTO, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Reportar Response: " + response.toString());
                hideDialog();

                try {
                    JSONArray jObj = new JSONArray(response);
                    JSONObject rec = jObj.getJSONObject(0);

                    boolean error = false;

                    String msg = rec.getString("msg");
                    if (!msg.equals("OK")) error = true;

                    // Check for error node in json
                    if (!error) {

                        WebView webviewer = (WebView) findViewById(R.id.webviewer);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
                            webviewer.getSettings().setAllowUniversalAccessFromFileURLs(true);
                        webviewer.getSettings().setJavaScriptEnabled(true);

                        webviewer.loadUrl(AppConfig.URL_SOCKETIO);

                        new AlertDialog.Builder(ReportarActivity.this)
                                .setTitle("Gracias por tu reporte")
                                .setMessage("En breve nos comunicaremos contigo vía correo electrónico.")
                                .setCancelable(false)
                                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent(ReportarActivity.this,
                                                MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                        }).create().show();

                        // Launch menu_tutores activity

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
                Log.w(TAG, "Reportar Error::: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }

        }) {

            // @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url

                Log.e(TAG, "ANDROID ID: " + uuid);

                Bitmap bitmap = ((BitmapDrawable)foto.getDrawable()).getBitmap();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream);
                byte[] byte_arr = stream.toByteArray();
                String image_str = Base64.encodeToString(byte_arr, Base64.DEFAULT);

                String txtDenuncia = txtReporte.getText().toString().trim();
                if (txtDenuncia.equals(""))
                    txtDenuncia = "Sin Comentario";

                // Log.e("BASE 64",image_str);

                String[] separated = Singleton.getUsername().split("@");

                Map<String, String> params = new HashMap<String, String>();
                params.put("username", Singleton.getUsername());
                params.put("denuncia", txtDenuncia);

                params.put("namex", separated[0]);
                params.put("phone", "Android");

                params.put("iD", uuid);

                params.put("la", Double.toString(Singleton.getLatitude()));
                params.put("lo", Double.toString(Singleton.getLongitude()));

                params.put("modulo", String.valueOf(Singleton.getModulo()));
                params.put("domicilio", Singleton.getDireccion());

                params.put("userfile", image_str);

                Log.w("PARAMS", params.toString());

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }


    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if (ni == null) return false;
        else return true;
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
        Intent txtReporte = new Intent(Intent.ACTION_SEND);
        txtReporte.putExtra(Intent.EXTRA_EMAIL, new String[]{"centroenlinea@villahermosa.gob.mx"});
        txtReporte.putExtra(Intent.EXTRA_SUBJECT, "Correo de " + Singleton.getUsername());
        txtReporte.putExtra(Intent.EXTRA_TEXT, "Mensaje: ");
        txtReporte.setType("message/rfc822");
        startActivity(Intent.createChooser(txtReporte, "Elija un cliente de correo electrónico :"));
        return true;
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Reportar Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://logydes.com.mx.centroenlinea/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Reportar Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://logydes.com.mx.centroenlinea/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}

