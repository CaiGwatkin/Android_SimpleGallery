package nz.ac.massey.caigwatkin.simplegallery;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

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
     * Starts the app.
     *
     * Checks that permissions are granted before initialising application.
     *
     * @param savedInstanceState Bundle from previous instance.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkPermissionsAndInit();
    }

    /**
     * Resumes the app.
     *
     * Checks that permissions are granted before re-initialising application.
     */
    @Override
    protected void onResume() {
        super.onResume();
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
        setContentView(R.layout.activity_simple_gallery);
    }

    /**
     * Checks if required permissions were granted after they have been requested.
     *
     * Ensures that the all necessary permissions are granted. A dialogue box will be displayed if the permission was
     * not granted and a new request for that permission will be made.
     *
     * @param requestCode The code of the permission request.
     * @param permissions The array of Strings relating to which permissions were requested.
     * @param grantResults The list of ints specifying whether the permission was granted.
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
                        checkPermissionsAndInit();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}
