package logydes.com.mx.centroenlinea.Adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import logydes.com.mx.centroenlinea.Helper.Singleton;
import logydes.com.mx.centroenlinea.Pojos.Hijos;
import logydes.com.mx.centroenlinea.R;
import logydes.com.mx.centroenlinea.Utils.Utilidades;

/**
 * Created by devch on 23/06/16.
 */

public class AdapterHijos extends RecyclerView.Adapter<AdapterHijos.AdapterHijosViewHolder> {
    private static final String TAG = "RESPUESTA MM";
    private ProgressDialog pDialog;
    private Utilidades Utl;


    private ArrayList<Hijos> MM;
    private Activity activity;
    private Context context;
    // private dbHijos cm;

    public AdapterHijos(Activity activity){
        this.MM = Singleton.getRsHijos(); // new ArrayList<Hijos>();
        this.activity = activity;
        // Log.e(TAG, String.valueOf(MM.size()));
    }

    // Infla el Layout y lo pasa a cada elemento para que obtenga los view
    @Override
    public AdapterHijosViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_hijos, parent, false);
        return new AdapterHijosViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final AdapterHijosViewHolder cvh, int position) {

        final Hijos mm = MM.get(position);

        cvh.tvHijo.setText(mm.getLabel());
        cvh.data = mm.getData();
        cvh.tvGrupo.setText(mm.getGrupo());

        /*
        cvh.lyHijos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Singleton.setIdAlu(mm.getData());
                Intent intent = new Intent(activity, MenuHijos.class);
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

    public static class AdapterHijosViewHolder extends RecyclerView.ViewHolder{

        LinearLayout lyHijos;
        TextView tvHijo;
        TextView tvGrupo;
        int data;


        public AdapterHijosViewHolder(View itemView) {
            super(itemView);
            data = 0;
            /*
            tvHijo = (TextView) itemView.findViewById(R.id.tvHijo);
            tvGrupo = (TextView) itemView.findViewById(R.id.tvGrupo);
            lyHijos = (LinearLayout) itemView.findViewById(R.id.lyHijos);
            */

        }

    }

}