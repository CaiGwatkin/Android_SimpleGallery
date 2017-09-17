package nz.ac.massey.caigwatkin.simplegallery;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.widget.GridView;
import android.widget.ImageView;

import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends Activity {

    private GridView mImageGridView;
    private ArrayList<Bitmap> mBitmapList;
    private Handler mThreadHandler;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mImageGridView = (GridView) findViewById(R.id.grid_view_images);
        mBitmapList = new ArrayList<>();
        mThreadHandler = new Handler();
        mContext = this;

        getImages();
        mImageGridView.setAdapter(new ImageAdapter(mContext, mBitmapList));


        //MediaStore.Images.Media.EXERNAL_CONTENT_URI // URI for internal storage on device
    }

    private void getImages() {

        Thread thread = new Thread(new Runnable() {
            public void run() {
                try {
                    for(int i = 0; i < 10; i++) {
                        mBitmapList.add(urlImageToBitmap("http://placehold.it/150x150"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private Bitmap urlImageToBitmap(String imageUrl) throws Exception {
        Bitmap result = null;
        URL url = new URL(imageUrl);
        if(url != null) {
            result = BitmapFactory.decodeStream(url.openConnection().getInputStream());
        }
        return result;
    }
}
