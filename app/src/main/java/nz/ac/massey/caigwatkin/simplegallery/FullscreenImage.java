package nz.ac.massey.caigwatkin.simplegallery;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

/**
 * Fullscreen Image class.
 *
 * Displays an image in fullscreen, high resolution.
 */
public class FullscreenImage extends Activity {

    /**
     * Creates the view.
     *
     * Sets the content view and adds bitmap to image view.
     *
     * @param savedInstanceState Bundle from previous instance.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen_image);
        String path = getIntent().getStringExtra("path");
        ImageView imageView = (ImageView) findViewById(R.id.image);
        imageView.setImageBitmap(BitmapFactory.decodeFile(path));
    }
}
