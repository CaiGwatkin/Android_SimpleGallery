package nz.ac.massey.caigwatkin.simplegallery;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Image Adapter class.
 *
 * Some code sampled from <a href="https://www.thepolyglotdeveloper.com/2015/05/make-a-gallery-like-image-grid-using-native-android/" >
 * Nic Raboy on thepolyglotdeveloper.com</a>
 *
 * @author C. Gwatkin <a href="mailto:caigwatkin@gmail.com">caigwatkin@gmail.com</a>
 */
public class ImageAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<Bitmap> mBitmapList;

    /**
     * Constructs object using context and bitmap list.
     *
     * @param context The current context of the image adapter.
     * @param bitmapList An array list of bitmap images.
     */
    public ImageAdapter(Context context, ArrayList<Bitmap> bitmapList) {

        mContext = context;
        mBitmapList = bitmapList;
    }

    /**
     * How many items are in the data set represented by this Adapter.
     *
     * @return Count of items.
     */
    @Override
    public int getCount() {

        return mBitmapList.size();
    }

    /**
     * Get the data item associated with the specified position in the data set.
     *
     * @param position Position of the item whose data we want within the adapter's
     *                 data set.
     * @return The data at the specified position.
     */
    @Override
    public Object getItem(int position) {

        return null;
    }

    /**
     * Get the row id associated with the specified position in the list.
     *
     * @param position The position of the item within the adapter's data set whose row id we want.
     * @return The id of the item at the specified position.
     */
    @Override
    public long getItemId(int position) {

        return 0;
    }

    /**
     * Get a View that displays the data at the specified position in the data set. You can either
     * create a View manually or inflate it from an XML layout file. When the View is inflated, the
     * parent View (GridView, ListView...) will apply default layout parameters unless you use
     * {@link LayoutInflater#inflate(int, ViewGroup, boolean)}
     * to specify a root view and to prevent attachment to the root.
     *
     * @param position    The position of the item within the adapter's data set of the item whose view
     *                    we want.
     * @param convertView The old view to reuse, if possible.
     * @param parent      The parent that this view will eventually be attached to.
     * @return A View corresponding to the data at the specified position.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = new ImageView(mContext);
            convertView.setLayoutParams(new GridView.LayoutParams(148, 148));
            ((ImageView) convertView).setScaleType(ImageView.ScaleType.CENTER_CROP);
//            LayoutInflater inflater = LayoutInflater.from(mContext);
//            convertView = inflater.inflate(R.layout.image, parent, false);
//            ((ImageView) convertView).setScaleType(ImageView.ScaleType.CENTER_CROP);
        }

        ((ImageView) convertView).setImageBitmap(mBitmapList.get(position));
        return convertView;
    }
}
