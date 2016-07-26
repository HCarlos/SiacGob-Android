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
import logydes.com.mx.centroenlinea.Pojos.Lista_Elementos;
import logydes.com.mx.centroenlinea.R;
import logydes.com.mx.centroenlinea.Utils.Utilidades;

/**
 * Created by devch on 24/06/16.
 */
public class Adapter_Lista_Elementos extends RecyclerView.Adapter<Adapter_Lista_Elementos.AdapterElementosViewHolder> {
        private static final String TAG = "RESPUESTA MM";
        private ProgressDialog pDialog;
        private Utilidades Utl;


        private ArrayList<Lista_Elementos> MM;
        private Activity activity;
        private Context context;
        // private dbElementos cm;

        public Adapter_Lista_Elementos(Activity activity){
            this.MM = Singleton.getRsElementos(); // new ArrayList<Lista_Elementos>();
            this.activity = activity;
            // Log.e(TAG, String.valueOf(MM.size()));
        }

        // Infla el Layout y lo pasa a cada elemento para que obtenga los view
        @Override
        public AdapterElementosViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_elementos, parent, false);
            return new AdapterElementosViewHolder(v);
        }

        @Override
        public void onBindViewHolder(final AdapterElementosViewHolder cvh, int position) {

            final Lista_Elementos mm = MM.get(position);

            cvh.tvElemento.setText(mm.getLabel() );
            cvh.IdElemento = mm.getIdElemento();

            /*
            cvh.lyElementos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(activity, Elemento.class);
                    intent.putExtra(activity.getString(R.string.elemento), mm.getLabel() );
                    intent.putExtra(activity.getString(R.string.idelemento), Singleton.getIdAlu() );
                    activity.startActivity(intent);

                }
            });
            */


        }

        public int getItemCount() {

            // Log.e(TAG+" TOTAL",String.valueOf(MM.size()));
            return MM.size();
        }

        public static class AdapterElementosViewHolder extends RecyclerView.ViewHolder{

            LinearLayout lyElementos;
            TextView tvElemento;
            int IdElemento;

            public AdapterElementosViewHolder(View itemView) {
                super(itemView);
                IdElemento = 0;
                // tvElemento = (TextView) itemView.findViewById(R.id.tvElemento);
                lyElementos = (LinearLayout) itemView.findViewById(R.id.lyElementos);

            }

        }

    }