package nz.ac.massey.caigwatkin.simplegallery;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.provider.MediaStore;
import android.util.AttributeSet;
import android.widget.GridView;

import java.util.ArrayList;

/**
 * Created by Cai on 25/09/2017.
 */

public class ImageGridView extends GridView {
    /**
     * Stores paths to images shown as thumbnails.
     */
    private String[] imagePaths;

    public ImageGridView(Context context) {
        super(context);

        init();
    }

    public ImageGridView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);

        init();
    }

    private void init() {
        Object[] temp = getImages();
        this.imagePaths = (String[]) temp[0];
        ArrayList<Bitmap> bitmapList = (ArrayList<Bitmap>) temp[1];
        setAdapter(new ImageAdapter(getContext(), bitmapList));
    }

    /**
     * Gets a all images from device as thumbnails and paths.
     *
     * @return Array of objects: String[] imagePaths, ArrayList<Bitmap> thumbBitmapList
     */
    private Object[] getImages() {
        final String[] columns = new String[]{ MediaStore.Images.Media.DATA };
        final String orderBy = MediaStore.Images.Media.DATE_TAKEN + " DESC";
        final Cursor cursor = getContext().getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                columns, null, null, orderBy);
        final int THUMB_SIZE = 64;
        final String[] imagePaths = new String[cursor.getCount()];
        final ArrayList<Bitmap> thumbBitmapList = new ArrayList<>();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    int length = imagePaths.length;
                    for (int i = 0; i < length; i++) {
                        cursor.moveToPosition(i);
                        int dataColumnIndex = cursor.getColumnIndex(MediaStore.Images.Media.DATA);
                        imagePaths[i] = cursor.getString(dataColumnIndex);
                        thumbBitmapList.add(ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(imagePaths[i]),
                                THUMB_SIZE, THUMB_SIZE));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        try {
            thread.join();
            cursor.close();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new Object[]{imagePaths, thumbBitmapList};
    }

    public String[] getImagePaths() {
        return this.imagePaths;
    }
}
