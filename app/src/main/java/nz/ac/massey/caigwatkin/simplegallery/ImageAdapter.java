package nz.ac.massey.caigwatkin.simplegallery;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Image Adapter class.
 *
 * Allows views to be dynamically created/updated from array of bitmaps.
 */
class ImageAdapter extends BaseAdapter {

    /**
     * Inflater used to inflate new image views.
     */
    private LayoutInflater layoutInflater;

    /**
     * List of thumbnail bitmaps.
     */
    private ArrayList<Bitmap> thumbBitmapList;

    /**
     * Constructs object using context and bitmap list.
     *
     * @param context The current context of the image adapter.
     * @param bitmapList An array list of bitmap images.
     */
    ImageAdapter(Context context, ArrayList<Bitmap> bitmapList) {

        this.layoutInflater = LayoutInflater.from(context);
        this.thumbBitmapList = bitmapList;
    }

    /**
     * How many items are in the thumbnail bitmap list.
     *
     * @return Count of items.
     */
    @Override
    public int getCount() {

        return this.thumbBitmapList.size();
    }

    /**
     * Get the data item associated with the specified position in the data set.
     *
     * @param position The position of the thumbnail within the thumbnail bitmap list.
     * @return The bitmap at the specified position from the thumbnail bitmap list.
     */
    @Override
    public Object getItem(int position) {

        return this.thumbBitmapList.get(position);
    }

    /**
     * Get the row id associated with the specified position in the list.
     *
     * @param position The position of the thumbnail within the thumbnail bitmap list.
     * @return The id of the item at the specified position; same as position.
     */
    @Override
    public long getItemId(int position) {

        return position;
    }

    /**
     * Get a View that displays the image at the specified position in the thumbnail bitmap list.
     *
     * @param position    The position of the thumbnail within the thumbnail bitmap list.
     * @param convertView The old view to reuse, if possible.
     * @param parent      The parent that this view will eventually be attached to.
     * @return A View corresponding to the data at the specified position.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ImageView imageView;
        if (convertView == null) {
            convertView = this.layoutInflater.inflate(R.layout.grid_cell, null);
            imageView = (ImageView) convertView.findViewById(R.id.image_thumb);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setImageBitmap(this.thumbBitmapList.get(position));
        }
        else {
            imageView = (ImageView) convertView.findViewById(R.id.image_thumb);
            imageView.setImageBitmap(this.thumbBitmapList.get(position));
        }
        return convertView;
    }
}
