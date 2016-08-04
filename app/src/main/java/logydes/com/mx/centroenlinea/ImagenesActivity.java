package logydes.com.mx.centroenlinea;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import org.apache.http.impl.conn.SingleClientConnManager;

import logydes.com.mx.centroenlinea.Adapter.AdapterMisImagenes;
import logydes.com.mx.centroenlinea.Helper.Singleton;
import logydes.com.mx.centroenlinea.Inside.getImagenes;
import logydes.com.mx.centroenlinea.Utils.AppConfig;

public class ImagenesActivity extends AppCompatActivity {
    private Context context;
    private RecyclerView listaMM;
    private AdapterMisImagenes mad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_magenes);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle params = getIntent().getExtras();

        listaMM = (RecyclerView) findViewById(R.id.rvImagenes);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        listaMM.setLayoutManager(llm);

        Log.w("USERNAME for Query:", Singleton.getUsername());

        getImagenes bi = new getImagenes(this);
        bi.getImageList(AppConfig.URL_DOWNLOAD_IMAGES, 1);

    }



}
