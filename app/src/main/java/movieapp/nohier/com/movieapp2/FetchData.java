package movieapp.nohier.com.movieapp2;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.GridView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by mohamed k on 13/08/2016.
 */
public class FetchData extends AsyncTask<Void,Void,ArrayList<Image>> {

    /*
    Before defining the values you should define th parts of the url itself
     */
    private String   SORTING_VALUE = null;
    GridView gridView;
    private Context context;
    private String BASE_URL= "https://api.themoviedb.org/3/discover/movie?";
    private String SORTING = "sort_by";
    private String API_KEY = "api_key";
    private String APPID = "019be2a57857d388c65d464c870471b7";
    // define the first element to parse from
    private String rootelement = "results";
    // this is the constructor

    @Override
    protected void onPreExecute() {

    }

    public FetchData (Context context, GridView gridView,String SORTING_VALUE)
    {
        this.context = context;
        this.gridView = gridView;
        this.SORTING_VALUE =SORTING_VALUE;


    }

    @Override
    protected ArrayList<Image> doInBackground(Void... params)
    {

        // check for the preferences before build the url every time
        HttpURLConnection urlConnection = null;
        BufferedReader bufferedReader = null;
        String finalresult;
        try {
            Uri builturl = Uri.parse(BASE_URL).buildUpon()
                    .appendQueryParameter(SORTING, SORTING_VALUE)
                    .appendQueryParameter(API_KEY, APPID).build();
            Log.v("BUILT URL", String.valueOf(builturl));



            // declaring the url
            URL url = new URL(builturl.toString());
            urlConnection = (HttpURLConnection)url.openConnection();
            urlConnection .setRequestMethod("GET");

            urlConnection .connect();



            // getting the data

            InputStream inputstream = urlConnection .getInputStream();

            StringBuffer stringbuffer = new StringBuffer();
            if(inputstream == null)
            // here we return null in order to goto the catch block
            {

                return null;

            }

            // now we stroing the data
            bufferedReader = new BufferedReader(new InputStreamReader(inputstream));

            String line;
            while((line = bufferedReader.readLine())!= null){
                stringbuffer.append(line+"\n");
            }
            // check if the stringbuffer length is 0
            // which means error alos
            if(stringbuffer.length()==0)
            {

                return null; // return null if the buffer was empty;
            }
            finalresult = stringbuffer.toString();


        } catch (IOException e) {
            Log.e("Fragment","Error",e);
            e.printStackTrace();

            return null;
        }

        // after making the try and catch block you have to make the finally block


        /*
        try to make another connection to get the overview of the movie
         */









        finally {
            // usually the finally block includes the resource closing

            // also we will check if there null there is no need to close them


            /*
            close the HttpURLConnection resource
            * */
            if (urlConnection != null) {
                urlConnection.disconnect();

            }
             /*
             close the BufferedReader object

              */
            if (bufferedReader != null) {

                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


        }

        return jsonParsing(finalresult);   // this is the actuall parsing (getting the results)
        // this will return the ArrayList<Images>

    }
    public ArrayList<Image> jsonParsing(String result){
        ArrayList<Image> finalArrayList = new ArrayList<>(); // this is the array to be passed

        // first get the movie posters and the title from the String result passed

        // 1- craate the json object giving the costructor the passed String.

        try {
            JSONObject jsonObject = new JSONObject(result);


            // 2- crate the a JsonArray object to be able to access the jason elements
            JSONArray jsonArray = jsonObject.getJSONArray(rootelement);

            Image images = null;

            for(int i =0;i<jsonArray.length();i++) {
                JSONObject oneMovie = jsonArray.getJSONObject(i);
                String imagePath = oneMovie.getString("poster_path");
                String title  = oneMovie.getString("original_title");
                String overview = oneMovie.getString("overview");
                double vote = oneMovie.getDouble("vote_average");
                String year = (String) oneMovie.get("release_date");
                String id = oneMovie.getString("id");
                Log.v("XXXXXXXXXX",id);
                images = new Image(title,imagePath,overview,vote,year,id);
                finalArrayList.add(images);

            }



        } catch (JSONException e) {

            e.printStackTrace();
        }

        return finalArrayList; // here should be the ArrayList<Images>
    }

    @Override  // doPostExecute should make the adapter and update the UI
    protected void onPostExecute(ArrayList<Image> images) {
      myAdapter adapter = new myAdapter(context,images);
       gridView.setAdapter(adapter);



        super.onPostExecute(images);
    }

}
