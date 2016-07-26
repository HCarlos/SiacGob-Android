package logydes.com.mx.centroenlinea;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import logydes.com.mx.centroenlinea.Helper.SQLiteHandler;
import logydes.com.mx.centroenlinea.Helper.SessionManager;
import logydes.com.mx.centroenlinea.Helper.Singleton;
import logydes.com.mx.centroenlinea.Inside.DocumentInside;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "BLUE";
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private SQLiteHandler db;
    private SessionManager session;
    private WebView webview;

    private GoogleApiClient client;
    private Singleton singleton;
    private TextView tv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        singleton = new Singleton();

        db = new SQLiteHandler(getApplicationContext());
        session = new SessionManager(getApplicationContext());
        tv = (TextView) findViewById(R.id.bienvenida);

        if (session.isLoggedIn() && singleton.getRsHijosSize() <= 0) {
            // db.deleteUsers();
            db.getUserDetails();
            String nc = singleton.getUsername();
            Log.d(TAG,nc);
            tv.setText( "Bienvenid@ " + nc );
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // webview = (WebView) findViewById(R.id.webview);

        /*
        HashMap<String, String> user = db.getUserDetails();
        String username = user.get("label");
        tv.setText( "Bienvenido " + username );
        */

        // String email = user.get("email");


/*
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        // Esto cambio apartir de las nuevas versiones
        //drawer.setDrawerListener(toggle);
        drawer.addDrawerListener(toggle);

        toggle.syncState();
*/


        // navigationView = (NavigationView) findViewById(R.id.nav_view);
        // navigationView.setNavigationItemSelectedListener(this);


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public void onBackPressed() {
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        Log.e("getIdUserNivelAcceso:", String.valueOf(singleton.getIdUserNivelAcceso() ) );

        if ( session.isLoggedIn() && singleton.getIdUserNivelAcceso() == 7) { // Tutores
            //getMenuInflater().inflate(R.menu.menu_tutores, menu);
            tv.setText( "Bienvenid@ " + singleton.getNombreCompletoUsuario() );
            // dbHijos cm = new dbHijos(this, this);

            if ( singleton.getRsHijosSize() <= 0) {
                // cm.obtenerDatos();
            }else{
                // cm.resfreshHijos();
            }

        }else if ( session.isLoggedIn() && singleton.getIdUserNivelAcceso() == 5) { // Alu
            // getMenuInflater().inflate(R.menu.menu_alumnos, menu);
        }

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_tareas) {
            Toast.makeText(this, "Lista_Elementos", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        DocumentInside bi = new DocumentInside(webview,this);
        String URL = "";
        int Type = 0;
        switch (id){
            case R.id.nav_inicio:
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                return true;
            case R.id.nav_cerrar_session:
                logoutUser();
                return true;
            case R.id.nav_boletin:
                drawer.closeDrawer(GravityCompat.START);
                return  bi.onCreateBoletin();
        }
        drawer.closeDrawer(GravityCompat.START);
        return  bi.onCreateObject(URL, Type);

    }

    private void logoutUser() {
        session.setLogin(false);
        db.deleteUsers();
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
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
                "Main Page", // TODO: Define a title for the content shown.
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


    @Override
    protected void onResume() {
        super.onResume();
        // webview.reload();
    }


}
