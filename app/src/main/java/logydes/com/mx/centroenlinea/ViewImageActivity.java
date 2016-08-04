package logydes.com.mx.centroenlinea;

import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

import logydes.com.mx.centroenlinea.Utils.PhotoUtils;

public class ViewImageActivity extends AppCompatActivity {
    PhotoUtils photoUtils;
    ImageView srcImgVItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_image);

        srcImgVItem = (ImageView) findViewById(R.id.srcImgVItem);

        Bundle params = getIntent().getExtras();
        String imgUrl = params.getString("imagen");

        File tempDir = this.getExternalCacheDir();
        String url = tempDir.getAbsolutePath() + "/temp/"+imgUrl;
        tempDir = new File(url);
        Uri uri = Uri.fromFile(tempDir);
        Bitmap bounds = photoUtils.getImage(uri);
        if (bounds != null) {
            srcImgVItem.setImageBitmap(bounds);
        } else {
            Toast.makeText(this, "Ha ocurrido un error", Toast.LENGTH_SHORT).show();
        }



    }
}
