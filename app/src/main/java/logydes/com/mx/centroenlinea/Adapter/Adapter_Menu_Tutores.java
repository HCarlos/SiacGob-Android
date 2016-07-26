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

import logydes.com.mx.centroenlinea.Pojos.Menu_Tutores;
import logydes.com.mx.centroenlinea.R;
import logydes.com.mx.centroenlinea.Utils.Utilidades;

/**
 * Created by devch on 24/06/16.
 */

    public class Adapter_Menu_Tutores extends RecyclerView.Adapter<Adapter_Menu_Tutores.Adapter_Menu_TutoresViewHolder> {
        private static final String TAG = "RESPUESTA MM";
        private ProgressDialog pDialog;
        private Utilidades Utl;

        private ArrayList<Menu_Tutores> MM;
        private Activity activity;
        private Context context;
        // private db_Menu_Tutores cm;

        public Adapter_Menu_Tutores(Activity _activity){
            this.activity = _activity;
            // Log.e(TAG, String.valueOf(MM.size()));
            MM = new ArrayList<Menu_Tutores>();
            MM.add(new Menu_Tutores(0,"Tareas"));
            MM.add(new Menu_Tutores(1,"Circulares"));
            MM.add(new Menu_Tutores(2,"Facturas"));
            MM.add(new Menu_Tutores(3,"Pagos"));
            MM.add(new Menu_Tutores(4,"Boletas"));


        }

        // Infla el Layout y lo pasa a cada elemento para que obtenga los view
        @Override
        public Adapter_Menu_TutoresViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_menu_tutores, parent, false);
            return new Adapter_Menu_TutoresViewHolder(v);
        }

        @Override
        public void onBindViewHolder(final Adapter_Menu_TutoresViewHolder cvh, int position) {

            final Menu_Tutores mm = MM.get(position);

            cvh.tvMenu.setText(mm.getMenu() );
            cvh.idmenu = mm.getIdmenu();

            // Log.e(TAG,mm.getGrupo());en

            /*
            cvh.lyTutores.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(activity, ListaElementos.class);
                    intent.putExtra(activity.getString(R.string.menu), mm.getMenu() );
                    intent.putExtra(activity.getString(R.string.idmenu), cvh.idmenu );
                    activity.startActivity(intent);

                }
            });
            */


        }

    /*
    public String getLikes(_Menu_Tutores mm){
        cm = new db_Menu_Tutores(activity);
        String likes = String.valueOf( cm.getLikes(mm) );
        return String.valueOf( cm.getLikes(mm) );
    }
    */

        public int getItemCount() {

            // Log.e(TAG+" TOTAL",String.valueOf(MM.size()));
            return MM.size();
        }

        public static class Adapter_Menu_TutoresViewHolder extends RecyclerView.ViewHolder{

            LinearLayout lyTutores;
            TextView tvMenu;
            int idmenu;

            public Adapter_Menu_TutoresViewHolder(View itemView) {
                super(itemView);
                idmenu = 0;
                /*
                tvMenu = (TextView) itemView.findViewById(R.id.tvMenu);
                lyTutores = (LinearLayout) itemView.findViewById(R.id.lyTutores);
                */

            }

        }

    }