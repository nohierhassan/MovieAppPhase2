package movieapp.nohier.com.movieapp2;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailedFragment extends Fragment {
    ListView ls;
    Context c;
    private String id;
    private String title;
    private String path;
    private String overview;
    private double vote;
    private String year;
    OpenHelper openHelper = new OpenHelper(c);
     static Image image2;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    @Override
    public void onStart() {
        FetchReviews fetchReviews = new FetchReviews(id,c,ls);
        fetchReviews.execute();
        super.onStart();
    }

    @Override
    public void onAttach(Activity activity) {
        this.c = activity.getApplication();
        super.onAttach(activity);
    }

    public DetailedFragment() {


    }
    public static DetailedFragment newInstance(String param1, String param2) {
        DetailedFragment fragment = new DetailedFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.new_detailed, container, false);
        ls = (ListView) rootView.findViewById(R.id.listView);
        final OpenHelper openHelper  = new OpenHelper(c);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
           final Image image;
            if(savedInstanceState != null){
                image = (Image) savedInstanceState.getParcelable("Image");
                Log.v("%%%%%%%%%%%%%%%% Second",image2.getTitle());


            }
            else {
                image = bundle.getParcelable("Image");
                Log.v("%%%%%%%%%%%%%%%% First",image.getTitle());
            }
            image2 = image;
            Log.v("IMAGE 2",image2.getTitle());
            TextView moviename = (TextView)  rootView.findViewById(R.id.movie_name);
            id = image.getId();
            title = image.getTitle();
            path = image.getPath();
            overview = image.getOverview();
            vote = image.getVote();
            year = image.getYear();

            ImageView imageView = (ImageView) rootView.findViewById(R.id.imageView);
            TextView year = (TextView) rootView.findViewById(R.id.year);
            TextView vote = (TextView) rootView.findViewById(R.id.vote);
            TextView overview = (TextView) rootView.findViewById(R.id.overview);
            moviename.setText(image.getTitle());
            Picasso.with(c).load("http://image.tmdb.org/t/p/w185/" + image.getPath()).into(imageView);
            year.setText(image.getYear());
            String votee = String.valueOf(image.getVote());
            vote.setText(votee);
            overview.setText(image.getOverview());
            Button btn = (Button) rootView.findViewById(R.id.button);
            btn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    youtubeConnection youtubeConnection1 = new youtubeConnection(image.getId(), c);
                    youtubeConnection1.execute();


                }
            });
        }
        FloatingActionButton fav_fab = (FloatingActionButton) rootView.findViewById(R.id.fav_fab);
        fav_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openHelper.insertData(id,title,path,overview, String.valueOf(vote),year);

            }
        });
        FloatingActionButton home_fab = (FloatingActionButton) rootView.findViewById(R.id.home_fab);
        home_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Fragment fragment = new MainFragment();
                fragmentTransaction.replace(R.id.fragmentToChange,fragment);
                fragmentTransaction.commit();
            }
        });
            return rootView;


        }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        Log.v("&&&&&&&&&&&&&&","in on saved instance state");
        Log.v("^^^^^^^^^^^^^^^",image2.getTitle());
        outState.putParcelable("Image",image2);

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
