package movieapp.nohier.com.movieapp2;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainFragment extends Fragment
        {
    GridView gv ;
    private static final long serialVersionUID = 1L;
            Context context = getContext();


    public MainFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // inflate the whole xml file then get the grid view by id

        View rootView =  inflater.inflate(R.layout.fragment_main, container, false);
        gv = (GridView) rootView.findViewById(R.id.gridView);
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Image object = (Image) gv.getAdapter().getItem(position);
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                String tag =  null;
                fragmentTransaction.addToBackStack(tag);
                Fragment detailedFragment = new DetailedFragment();
                Bundle bundle = new Bundle();
                bundle.putParcelable("Image", object);
                Log.v("55555555555555555555","object will be passed"+object.getPath());
                detailedFragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.fragmentToChange,detailedFragment);
                fragmentTransaction.commit();

            }
        });

        return rootView;
    }

    @Override
    public void onStart() {
        // check for the connection first;
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        String s = sharedPrefs.getString(getString(R.string.sortingOrderKey), getString(R.string.sortingOrderDefualtValue));
        Log.v("()()()()()()()", s);

        // check if the value is favourit to make query form the database
        if (s.equals("Fav")) {
            Log.v("TTTTTTTTTTTTtt","TTTTTTTTTTTTTTTT");
            OpenHelper openHelper = new OpenHelper(getContext());
            Cursor res = openHelper.returnData();
            if (res.getCount() == 0) {
                Toast.makeText(getContext(), "No Data to View", Toast.LENGTH_LONG).show();

            }
            String title;
            String path;
            String overview;
            double vote;
            String year;
            String id;
            Image images = null;
            ArrayList<Image> finalArrayList = new ArrayList<>();
            StringBuffer stringBuffer = new StringBuffer();
            while (res.moveToNext()) {             // moveToNext represents the single record

                id = res.getString(0);
                title = res.getString(1);
                path = res.getString(2);
                overview = res.getString(3);
                vote = Double.parseDouble(res.getString(4));
                year = res.getString(5);
                images = new Image(title, path, overview, vote, year, id);
                finalArrayList.add(images);
            }


            myAdapter adapter = new myAdapter(getContext(), finalArrayList);
            gv.setAdapter(adapter);

        } else {
            FetchData fetch = new FetchData(getContext(), gv, s);
            fetch.execute();
        }
            super.onStart();

    }
            public interface OnFragmentInteractionListener {
                void onFragmentInteraction(Uri uri);
            }

}

