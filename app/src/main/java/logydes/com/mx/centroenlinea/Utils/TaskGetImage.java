package logydes.com.mx.centroenlinea.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by devch on 3/08/16.
 */
public class TaskGetImage extends AsyncTask<Context, Void, Bitmap>{

    private String cURL;
    private int type;
    private Bitmap Inst1;
    private Bitmap Inst2;
    private Bitmap Inst3;
    private Context context;
    private Uri mImageUri;


    public TaskGetImage(String Url, int Type, Context _context) {

        cURL = Url;
        type = Type;
        context = _context;
    }

    @Override
    protected Bitmap doInBackground(Context... arg0) {

        return creaBitMap();
    }

    @Override
    protected void onPostExecute( Bitmap result )  {
        super.onPostExecute(result);

        if (type == 1)
            Inst1 = result;
        else if (type == 2)
            Inst2 = result;
        else if (type == 3) {
            // Inst3 = result;
        }
    }

    public Bitmap creaBitMap(){

        Bitmap bitmap = null;
        String _URL = cURL;

        try {

            String src = "http://siac.tabascoweb.com/upload/"+_URL;
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            bitmap = BitmapFactory.decodeStream(input);

            String filename = cURL;
            File tempDir = context.getExternalCacheDir();
            tempDir = new File(tempDir.getAbsolutePath() + "/temp/");
            File dest = new File(tempDir, filename);

            String path = context.getExternalCacheDir().toString();
            OutputStream fOut = null;
            File file = new File(path + "/temp/", filename); // the File to save to
            fOut = new FileOutputStream(file);
            if ( fOut != null ) {
                Bitmap pictureBitmap = bitmap; // obtaining the Bitmap
                pictureBitmap.compress(Bitmap.CompressFormat.JPEG, 85, fOut); // saving the Bitmap to a file compressed as a JPEG with 85% compression rate
                fOut.flush();
                fOut.close(); // do not forget to close the stream

                MediaStore.Images.Media.insertImage(context.getContentResolver(),file.getAbsolutePath(),file.getName(),file.getName());
                Log.w("IMAGEN:", dest.getPath());
            }

        } catch (IOException e) {
            e.printStackTrace();
            Log.e("doInBackground","Error");
        }
        return bitmap;


    }

}