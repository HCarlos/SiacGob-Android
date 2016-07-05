package logydes.com.mx.centroenlinea;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import DB.dbHijos;
import DB.dbLista_Elementos;
import Utils.AppConfig;

public class ListaElementos extends AppCompatActivity {
    private int IdMenu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*
        setContentView(R.layout.activity_lista_elementos);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle params = getIntent().getExtras();

        String Menu = params.getString(getResources().getString(R.string.menu));
        IdMenu = params.getInt(getResources().getString(R.string.idmenu));
        this.setTitle(Menu);

        dbLista_Elementos cm = new dbLista_Elementos(this, this);

        String strURL = "";
        switch (IdMenu){
            case 0:
                strURL = AppConfig.URL_TAREAS;
                break;
            case 1:
                strURL = AppConfig.URL_TAREAS;
                break;

        }
        cm.obtenerDatos(strURL,IdMenu);
        */

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
