package nz.ac.massey.caigwatkin.simplegallery;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.GridView;

import java.util.ArrayList;

/**
 * Simple Gallery class.
 *
 * Entry point for app. Displays a gallery of images stored on the device.
 *
 * @see
 */
public class SimpleGallery extends Activity {

    /**
     * Used to determine which permission set is being referenced.
     */
    static private enum PermissionTypes {
        ESSENTIAL_PERMISSIONS(0), NON_ESSENTIAL_PERMISSIONS(1);

        private final int value;
        private PermissionTypes(int value) {
            this.value = value;
        }

        public int toInt() {
            return value;
        }
    }

    /**
     * Stores paths to images shown as thumbnails in simple gallery view.
     */
    private String[] imagePaths;

    /**
     * Starts the app.
     *
     * @param savedInstanceState Bundle from previous instance.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.simple_gallery);

        checkPermissionsAndInit();
    }

    /**
     * Checks that required permissions are granted before either requesting those permissions or initialising the app.
     */
    private void checkPermissionsAndInit() {
        if (Build.VERSION.SDK_INT > 23 && checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PermissionTypes.ESSENTIAL_PERMISSIONS.toInt());
        }
        else {
            init();
        }
    }

    /**
     * Initialises the app.
     *
     * Gets the images to be displayed in the gallery and sets up the adapter to display those images in the grid view.
     */
    private void init() {
//        ArrayList<Bitmap> bitmapList = new ArrayList<>();
//        //getImages();
//        GridView imageGridView = (GridView) findViewById(R.id.grid_view_images);
//        imageGridView.setAdapter(new ImageAdapter(this, bitmapList));
        Object[] temp = getImages();
        this.imagePaths = (String[]) temp[0];
        ArrayList<Bitmap> bitmapList = (ArrayList<Bitmap>) temp[1];
        GridView imageGridView = (GridView) findViewById(R.id.grid_view_images);
        imageGridView.setAdapter(new ImageAdapter(this, bitmapList));
    }

    /**
     * Checks if required permissions were granted after they have been requested.
     *
     * Ensures that the all necessary permissions are granted. A dialogue box will be displayed if the permission was
     * not granted and a new request for that permission will be made.
     *
     * @param requestCode The code of the permission request.
     * @param permissions The array of Strings relating t
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if(permissions.length == 0) {
            finish();
        }
        else if (requestCode == PermissionTypes.ESSENTIAL_PERMISSIONS.toInt()) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                init();
            }
            else {
                showPermissionRequiredDialogue(this.getString(R.string.essential_permission_message));
            }
        }
    }

    /**
     * Shows a dialogue box to user.
     *
     * To be called when permission is required but was not granted.
     *
     * @param message The message to be displayed.
     */
    private void showPermissionRequiredDialogue(String message) {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= 21) {
            builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Light_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(this);
        }
        builder.setTitle(this.getString(R.string.essential_permission_required))
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing, permission will be requested when call button clicked again.
                        checkPermissionsAndInit();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    /**
     * Gets a all images from device as thumbnails and paths.
     *
     * @return Array of objects: String[] imagePaths, ArrayList<Bitmap> thumbBitmapList
     */
    private Object[] getImages() {
        final String[] columns = new String[]{MediaStore.Images.Media.DATA, MediaStore.Images.Media.DISPLAY_NAME};
        final String orderBy = MediaStore.Images.Media.DATE_TAKEN;
        final Cursor cursor = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
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
//
//    private Bitmap urlImageToBitmap(String imageUrl) throws Exception {
//        Bitmap result = null;
//        URL url = new URL(imageUrl);
//        if(url != null) {
//            result = BitmapFactory.decodeStream(url.openConnection().getInputStream());
//        }
//        return result;
//    }
}
