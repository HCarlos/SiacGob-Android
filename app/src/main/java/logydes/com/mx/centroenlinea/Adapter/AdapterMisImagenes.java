package logydes.com.mx.centroenlinea.Adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
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
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import logydes.com.mx.centroenlinea.Helper.Singleton;
import logydes.com.mx.centroenlinea.Pojos.Imagenes;
import logydes.com.mx.centroenlinea.R;
import logydes.com.mx.centroenlinea.Utils.Utilidades;

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

        Imagenes mm = MM.get(position);

        String img = mm.getImagen_s();

        try {
            /*
            URL thumb_u = new URL("http://siac.tabascoweb.com/upload/"+img);
            Drawable thumb_d = Drawable.createFromStream(thumb_u.openStream(), "src");
            cvh.iv_imagen.setImageDrawable(thumb_d);
            */


            // cvh.iv_imagen.setImageURI(Uri.parse("http://siac.tabascoweb.com/upload/"+img));

            /*
            URL thumb_u = new URL(rutaImage);

            cvh.iv_imagen.setImageBitmap( getRemoteImage(thumb_u) );
            */
            String rutaImage = "http://siac.tabascoweb.com/upload/"+img;

            downloadFile(rutaImage, cvh.iv_imagen);

        }
        catch (Exception e) {
            Log.e("OCURRIO un MEGA ERROR", e.toString());
        }

        /*
        cvh.lyImagenes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Singleton.setIdAlu(mm.getData());
                Intent intent = new Intent(activity, MenuImagenes.class);
                intent.putExtra(activity.getString(R.string.nombreAlumno), mm.getLabel() );
                intent.putExtra(activity.getString(R.string.idalu), Singleton.getIdAlu() );
                intent.putExtra("grupo",mm.getGrupo());
                activity.startActivity(intent);

            }
        });
        */


    }

    public int getItemCount() {
        return MM.size();
    }

    public static class AdapterImagenesViewHolder extends RecyclerView.ViewHolder{

        ImageView iv_imagen;


        public AdapterImagenesViewHolder(View itemView) {
            super(itemView);
            iv_imagen = (ImageView) itemView.findViewById(R.id.iv_imagen);

        }

    }

    private void downloadFile(String imageHttpAddress, ImageView IV) {
        URL imageUrl = null;
        try {
            imageUrl = new URL(imageHttpAddress);
            HttpURLConnection conn = (HttpURLConnection) imageUrl.openConnection();
            conn.connect();
            loadedImage = BitmapFactory.decodeStream(conn.getInputStream());
            IV.setImageBitmap(loadedImage);
        } catch (IOException e) {
            Toast.makeText(activity, "Error cargando la imagen: "+e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }
}