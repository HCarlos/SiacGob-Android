package logydes.com.mx.centroenlinea;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import logydes.com.mx.centroenlinea.R;
import Adapter.AdapterHijos;
import Adapter.Adapter_Menu_Tutores;

public class MenuHijos extends AppCompatActivity {

    private Context context;
    private RecyclerView listaMM;
    private AdapterHijos mad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*
        setContentView(R.layout.activity_menu_hijos);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle params = getIntent().getExtras();

        listaMM = (RecyclerView) findViewById(R.id.rvMenu);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        listaMM.setLayoutManager(llm);

        Adapter_Menu_Tutores mad = new Adapter_Menu_Tutores(this);
        listaMM.setAdapter(mad);

        String Alumno = params.getString(getResources().getString(R.string.nombreAlumno));
        int IdAlu = params.getInt(getResources().getString(R.string.idalu));
        this.setTitle(Alumno);
        */

    }

    @Override
    protected void onResume() {
        super.onResume();
    }


}
