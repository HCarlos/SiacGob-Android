package logydes.com.mx.centroenlinea;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import logydes.com.mx.centroenlinea.Helper.SQLiteHandler;
import logydes.com.mx.centroenlinea.Helper.SessionManager;
import logydes.com.mx.centroenlinea.Helper.Singleton;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "BLUE";
    private DrawerLayout drawer;
    private SQLiteHandler db;
    private SessionManager session;
    private Context context;

    private GoogleApiClient client;
    private Singleton singleton;

    private LinearLayout llInicio;
    private LinearLayout llOpciones;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        llInicio = (LinearLayout) findViewById(R.id.llInicio);
        llOpciones = (LinearLayout) findViewById(R.id.llOpciones);
        // rlayout1.setVisibility(2);

        singleton = new Singleton();

        db = new SQLiteHandler(getApplicationContext());
        session = new SessionManager(getApplicationContext());

        if ( session.isLoggedIn() ) {
            // db.deleteUsers();
            db.getUserDetails();
            String nc = singleton.getUsername();
            llInicio.setVisibility(View.INVISIBLE);
            llOpciones.setVisibility(View.VISIBLE);

        }else{
            llOpciones.setVisibility(View.INVISIBLE);
            llInicio.setVisibility(View.VISIBLE);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null){
            setSupportActionBar(toolbar);
        }

        // getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }


    public boolean LoginUser(View view){
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        return true;
    }

    public boolean RegistryUser(View view){
        Intent intent = new Intent(MainActivity.this, RegistryActivity.class);
        startActivity(intent);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return getMenu(item);
    }


    public boolean getMenu(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_close_session:
                Alert(0);
                break;
        }
        return true;
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


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        return  true;

    }

    private void logoutUser() {
        session.setLogin(false);
        db.deleteUsers();
        // Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        // startActivity(intent);

        llOpciones.setVisibility(View.INVISIBLE);
        llInicio.setVisibility(View.VISIBLE);

    }

    private void Alert(final int Tipo){

        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        //Yes button clicked
                        switch (Tipo){
                            case 0:
                                logoutUser();
                                break;
                        }
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder( this );
        builder.setCancelable(false);
        builder.setTitle("Atención");
        builder.setMessage("Desea cerrar su sesión?").setPositiveButton(R.string.option_yes, dialogClickListener)
                .setNegativeButton(R.string.option_no, dialogClickListener).show();
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



