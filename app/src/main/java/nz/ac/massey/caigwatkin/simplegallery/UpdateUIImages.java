package nz.ac.massey.caigwatkin.simplegallery;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.GridView;

import java.util.ArrayList;

/**
 * Created by cai on 13/09/2017.
 */

public class UpdateUIImages implements Runnable {

    private Context mContext;
    private GridView mImageGridView;
    private ArrayList<Bitmap> mBitmapList;

    public UpdateUIImages(Context context, GridView imageGridView, ArrayList<Bitmap> bitmapList) {

        mContext = context;
        mImageGridView = imageGridView;
        mBitmapList = bitmapList;
    }

    @Override
    public void run() {
        mImageGridView.setAdapter(new ImageAdapter(mContext, mBitmapList));
    }
}
