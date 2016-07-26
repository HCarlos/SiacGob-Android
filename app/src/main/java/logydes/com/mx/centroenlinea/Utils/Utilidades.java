package logydes.com.mx.centroenlinea.Utils;

import android.app.ProgressDialog;

/**
 * Created by devch on 17/06/16.
 */
public class Utilidades {

    private static final String TAG = "RESPUESTA";
    private ProgressDialog pDialog;

    public Utilidades(ProgressDialog pDialog) {
        this.pDialog = pDialog;
    }

    public void showDialog() {
        if (!pDialog.isShowing())
            this.pDialog.show();
    }

    public void hideDialog() {
        if (pDialog.isShowing())
            this.pDialog.dismiss();
    }
}
