package nz.ac.massey.caigwatkin.simplegallery;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.provider.MediaStore;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;

/**
 * Image Grid View class.
 *
 * Displays a grid view of images sourced from device folders.
 */
public class ImageGridView extends GridView {
    /**
     * Stores paths to images shown as thumbnails.
     */
    private String[] imagePaths;

    /**
     * Constructor from context.
     *
     * Constructs view based on context, then calls init function.
     *
     * @param context The context in which the view is created.
     */
    public ImageGridView(Context context) {
        super(context);

        init();
    }

    /**
     * Constructor from context and attributes.
     *
     * Constructs view based on context and attributes, then calls init function.
     *
     * @param context The context in which the view is created.
     * @param attributeSet Attributes of the view.
     */
    public ImageGridView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);

        init();
    }

    /**
     * Initialises the view.
     *
     * Adds thumbnail image views to grid view. Sets up click listener to start new activity when thumbnail clicked.
     */
    private void init() {
        Object[] temp = getImages();
        this.imagePaths = (String[]) temp[0];
        ArrayList<Bitmap> thumbBitmapList = (ArrayList<Bitmap>) temp[1];
        setAdapter(new ImageAdapter(getContext(), thumbBitmapList));
        setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                openFullscreenImageActivity(position);
            }
        });
    }

    /**
     * Opens a fullscreen image activity.
     *
     * Starts intent to display a new fullscreen image activity based on the path to the image at position.
     *
     * @param position The position of the image from the adapter.
     */
    private void openFullscreenImageActivity(int position) {
        Intent intent = new Intent(getContext(), FullscreenImage.class);
        intent.putExtra("path", imagePaths[position]);
        getContext().startActivity(intent);
    }

    /**
     * Gets a all images from device as thumbnails and paths.
     *
     * @return Array of objects: String[] imagePaths, ArrayList<Bitmap> thumbBitmapList
     */
    private Object[] getImages() {
        final String[] columns = new String[]{ MediaStore.Images.Media.DATA };
        final String orderBy = MediaStore.Images.Media.DATE_ADDED + " DESC";
        final int THUMB_SIZE = 148;
        final Object[] objects = new Object[2];
        Thread thread = new Thread(new Runnable() {
            /**
             * Attempts to load images.
             */
            @Override
            public void run() {
                try {
                    loadImages();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            /**
             * Loads images from device folders.
             *
             * Stores image paths. Creates and stores thumbnails.
             */
            private void loadImages() {
                Cursor cursor = getContext().getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        columns, null, null, orderBy);
                String[] imagePaths = new String[cursor.getCount()];
                ArrayList<Bitmap> thumbBitmapList = new ArrayList<>();
                int length = imagePaths.length;
                for (int i = 0; i < length; i++) {
                    cursor.moveToPosition(i);
                    int dataColumnIndex = cursor.getColumnIndex(MediaStore.Images.Media.DATA);
                    imagePaths[i] = cursor.getString(dataColumnIndex);
                    thumbBitmapList.add(ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(imagePaths[i]),
                            THUMB_SIZE, THUMB_SIZE));
                }
                objects[0] = imagePaths;
                objects[1] = thumbBitmapList;
                cursor.close();
            }
        });
        thread.start();
        try {
            thread.join();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return objects;
    }
}
