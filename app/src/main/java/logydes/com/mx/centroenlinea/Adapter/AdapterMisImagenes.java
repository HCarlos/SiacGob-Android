package logydes.com.mx.centroenlinea.Adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import logydes.com.mx.centroenlinea.Helper.Singleton;
import logydes.com.mx.centroenlinea.Pojos.Imagenes;
import logydes.com.mx.centroenlinea.R;
import logydes.com.mx.centroenlinea.Utils.PhotoUtils;
import logydes.com.mx.centroenlinea.Utils.Utilidades;
import logydes.com.mx.centroenlinea.ViewImageActivity;

/**
 * Created by devch on 23/06/16.
 */

public class AdapterMisImagenes extends RecyclerView.Adapter<AdapterMisImagenes.AdapterImagenesViewHolder> {
    private static final String TAG = "RESPUESTA MM";
    private ProgressDialog pDialog;
    private Utilidades Utl;
    private Bitmap loadedImage;

    private ArrayList<Imagenes> MM;
    private Activity activity;
    private Context context;
    // private dbImagenes cm;

    public AdapterMisImagenes(Activity activity){
        MM = Singleton.getArrImagenes(); // new ArrayList<Imagenes>();

        this.activity = activity;
        // Log.e(TAG, String.valueOf(MM.size()));
    }

    // Infla el Layout y lo pasa a cada elemento para que obtenga los view
    @Override
    public AdapterImagenesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_images_list, parent, false);
        return new AdapterImagenesViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final AdapterImagenesViewHolder cvh, int position) {

        final Imagenes mm = MM.get(position);

        final String imgPaso;
        final String img = mm.getImagen_s();
        imgPaso = getImage(img, cvh.iv_imagen);

        int tiposo = mm.getSo_mobile();
        String uri = "@mipmap/ic_android_logo";

        switch (tiposo){
            case 0:
                cvh.iv_celular.setImageResource(R.mipmap.ic_ios_logo);
                break;
            case 1:
                cvh.iv_celular.setImageResource(R.mipmap.ic_android_logo);
                break;
            default:
                cvh.iv_celular.setImageResource(R.mipmap.ic_web_logo);
                break;
        }

        cvh.txt_cfecha.setText(mm.getCfecha());
        cvh.txt_denuncia.setText(mm.getDenuncia());



        cvh.cmdFABIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Singleton.setIdAlu(mm.getData());
                Intent intent = new Intent(activity, ViewImageActivity.class);
                intent.putExtra("imagen", img );
                //intent.putExtra(activity.getString(R.string.idalu), Singleton.getIdAlu() );
                //intent.putExtra("grupo",mm.getGrupo());
                activity.startActivity(intent);

            }
        });



    }

    public int getItemCount() {
        return MM.size();
    }

    public static class AdapterImagenesViewHolder extends RecyclerView.ViewHolder{

        ImageView iv_imagen;
        ImageView iv_celular;
        TextView txt_cfecha;
        TextView txt_denuncia;
        FloatingActionButton cmdFABIV;


        public AdapterImagenesViewHolder(View itemView) {
            super(itemView);
            iv_imagen = (ImageView) itemView.findViewById(R.id.iv_imagen);
            iv_celular = (ImageView) itemView.findViewById(R.id.iv_celular);
            txt_cfecha = (TextView) itemView.findViewById(R.id.txt_cfecha);
            txt_denuncia = (TextView) itemView.findViewById(R.id.txt_denuncia);
            cmdFABIV = (FloatingActionButton) itemView.findViewById(R.id.cmdFABIV);

        }

    }

    public String getImage(String ImageName, ImageView photoViewer) {
        PhotoUtils photoUtils = new PhotoUtils(activity);
        File tempDir = activity.getExternalCacheDir();
        String url = tempDir.getAbsolutePath() + "/temp/"+ImageName;
        tempDir = new File(url);
        Uri uri = Uri.fromFile(tempDir);
        Bitmap bounds = photoUtils.getImage(uri);
        if (bounds != null) {
            photoViewer.setImageBitmap(bounds);
        } else {
            Toast.makeText(activity, "Ha ocurrido un error", Toast.LENGTH_SHORT).show();
        }
        return url;
    }



}