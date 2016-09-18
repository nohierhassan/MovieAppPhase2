package movieapp.nohier.com.movieapp2;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by mohamed k on 15/08/2016.
 */

public class myAdapter extends BaseAdapter {
    private Context c;                // the context to be able to access the layouts
    private ArrayList<Image> images;  // the array list to populate the layout with
    public myAdapter(Context c, ArrayList<Image> Images) {
        this.c= c;
        this.images = Images;
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public Object getItem(int position) {
        return images.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        /*
        this method is being called automatically  agter setting the adapter to the fetched array list
        so no need to make a for loop
         */

        LayoutInflater inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(convertView == null){
         convertView = inflater.inflate(R.layout.model,parent,false);
        }
        ImageView imageView = (ImageView) convertView.findViewById(R.id.imageView);

        Picasso.with(c).load("http://image.tmdb.org/t/p/w185/"+images.get(position).getPath()).into(imageView);
Log.v(">>>>>>>>>>>>>>",images.get(position).getPath());
        return convertView;
    }
}
