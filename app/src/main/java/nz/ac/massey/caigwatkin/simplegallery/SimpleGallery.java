package nz.ac.massey.caigwatkin.simplegallery;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

/**
 * Simple Gallery class.
 *
 * Entry point for app. Displays a gallery of images stored on the device.
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
     * Creates the app.
     *
     * Checks that permissions are granted before initialising activity.
     *
     * @param savedInstanceState Bundle from previous instance.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkPermissions();
    }

    /**
     * Checks that required permissions are granted before either requesting those permissions or initialising the app.
     */
    private void checkPermissions() {
        if (Build.VERSION.SDK_INT > 23 && checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    PermissionTypes.ESSENTIAL_PERMISSIONS.toInt());
        }
        else {
            initGridView();
        }
    }

    private void initGridView() {
        setContentView(R.layout.activity_simple_gallery);
        ((ImageGridView) findViewById(R.id.grid_view_images)).init();
    }

//    /**
//     * Checks if required permissions were granted after they have been requested.
//     *
//     * Ensures that the all necessary permissions are granted. A dialogue box will be displayed if the permission was
//     * not granted and a new request for that permission will be made.
//     *
//     * @param requestCode The code of the permission request.
//     * @param permissions The array of Strings relating to which permissions were requested.
//     * @param grantResults The list of ints specifying whether the permission was granted.
//     */
//    @Override
//    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
//        if (requestCode == PermissionTypes.ESSENTIAL_PERMISSIONS.toInt()) {
//            if (permissions.length > 0 && grantResults[requestCode] == PackageManager.PERMISSION_GRANTED) {
//                // Loops back to
//                //initGridView();
//            }
////            else {
////                //showPermissionRequiredDialogue(this.getString(R.string.essential_permission_message));
////            }
//        }
//    }

//    /**
//     * Shows a dialogue box to user.
//     *
//     * To be called when permission is required but was not granted.
//     *
//     * @param message The message to be displayed.
//     */
//    private void showPermissionRequiredDialogue(String message) {
//        AlertDialog.Builder builder;
//        if (Build.VERSION.SDK_INT >= 21) {
//            builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Light_Dialog_Alert);
//        } else {
//            builder = new AlertDialog.Builder(this);
//        }
//        builder.setTitle(this.getString(R.string.essential_permission_required))
//                .setMessage(message)
//                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        checkPermissions();
//                    }
//                })
//                .setIcon(android.R.drawable.ic_dialog_alert)
//                .show();
//    }
}
