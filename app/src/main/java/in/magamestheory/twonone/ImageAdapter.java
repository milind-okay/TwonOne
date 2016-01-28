package in.magamestheory.twonone;

/**
 * Created by milind on 27/1/16.
 */

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
        import android.widget.BaseAdapter;
        import android.widget.GridView;
        import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter{
    private Context mContext;
    int mThumbIds[],mk[],smk[],mLength;
    // Constructor
    public ImageAdapter(Context c) {
        mContext = c;
        mk = new int[10];
        smk = new int[10];

        mk[0] = R.drawable.ic_blank;
        mk[1] = R.drawable.ic_1;
        mk[2] = R.drawable.ic_2;
        mk[3] = R.drawable.ic_3;
        mk[4] = R.drawable.ic_4;
        mk[5] = R.drawable.ic_5;
        mk[6] = R.drawable.ic_6;
        mk[7] = R.drawable.ic_7;
        mk[8] = R.drawable.ic_8;
        mk[9] = R.drawable.ic_9;


    }

    public int getCount() {
        return mThumbIds.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;

        if (convertView == null) {
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(80, 80));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(3, 3, 3, 3);
            imageView.setBackgroundResource(R.drawable.gradient);
        }
        else
        {
            imageView = (ImageView) convertView;
        }
        imageView.setImageResource(mThumbIds[position]);
        return imageView;
    }

    public void setArray(int[] arr,int boardSize) {
        mLength = boardSize;
        mThumbIds = new int [mLength];
     for(int i=0;i<boardSize;i++){

             mThumbIds[i] = mk[arr[i]];

     }
    }
}
