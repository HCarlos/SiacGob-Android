package logydes.com.mx.centroenlinea;

import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import java.lang.reflect.Field;

import logydes.com.mx.centroenlinea.Adapter.AdapterMisImagenes;
import logydes.com.mx.centroenlinea.Inside.getImagenes;
import logydes.com.mx.centroenlinea.Utils.AppConfig;
import logydes.com.mx.centroenlinea.Utils.Utilidades;

public class ImagenesActivity extends AppCompatActivity {
    private Context context;
    private RecyclerView listaMM;
    private AdapterMisImagenes mad;
    private ImageView Img;
    private getImagenes bi;
    private CoordinatorLayout CL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_magenes);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_imagenes);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // if (Build.VERSION.SdkInt >= BuildVersionCodes.Lollipop)

            if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
            // window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark,setTheme(R.style.AppTheme_NoActionBar));
        }

        if (!Utilidades.isNetworkConnected(this)) {
            Toast.makeText(getApplicationContext(), "Por favor, conéctese a internet!", Toast.LENGTH_LONG).show();
            return;
        }

        Bundle params = getIntent().getExtras();

        listaMM = (RecyclerView) findViewById(R.id.rvImagenes);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        listaMM.setLayoutManager(llm);

        bi = new getImagenes(this);
        bi.getImageList(AppConfig.URL_DOWNLOAD_IMAGES, 1);

        Img = (ImageView) findViewById(R.id.refreshlImages);
        Img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bi.getImageList(AppConfig.URL_DOWNLOAD_IMAGES, 1);
            }
        });



    }

}
