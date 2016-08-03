package logydes.com.mx.centroenlinea.Utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by devch on 3/08/16.
 */
public class TaskGetImage extends AsyncTask<Context, Void, Bitmap>{

    private String URL;
    private int type;

    TaskGetImage(String Url, int Type)
    {
        URL = Url;
        type = Type;
    }

    @Override
    protected Bitmap doInBackground(Context... arg0) {

        AssetManager assetMgr = arg0[0].getAssets();
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(assetMgr.open(URL));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    @Override
    protected void onPostExecute( Bitmap result )  {
        super.onPostExecute(result);

        if (type == 1)
            Inst1 = result;
        else if (type == 2)
            Inst2 = result;
        else if (type == 3)
            Inst3 = result;
    }
}