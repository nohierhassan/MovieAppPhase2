package movieapp.nohier.com.movieapp2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by mohamed k on 30/08/2016.
 */
public class ReviewAdapter extends BaseAdapter {
    private ArrayList<Review> reviews;
    private Context context;

    public ReviewAdapter(ArrayList<Review> reviews, Context context) {
        this.reviews = reviews;
        this.context = context;
    }

    @Override
    public int getCount() {
        return reviews.size();
    }

    @Override
    public Object getItem(int position) {
        return reviews.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(convertView ==null)
        {
            convertView = inflater.inflate(R.layout.reviews_layout,parent,false);
        }
        TextView author = (TextView) convertView.findViewById(R.id.author);
        author.setText(reviews.get(position).getAuthor());  // this is the author name;
        TextView reviewText = (TextView) convertView.findViewById(R.id.reviewText);
        reviewText.setText(reviews.get(position).getReview());  // this is the review itself

        return convertView;
    }
}
