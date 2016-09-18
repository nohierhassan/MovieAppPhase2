package movieapp.nohier.com.movieapp2;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;

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
 * Created by mohamed k on 30/08/2016.
 */
public class FetchReviews extends AsyncTask  <Void,Void,ArrayList<Review>>{
    private String URL_BASE1 = "https://api.themoviedb.org/3/movie/";
    private String URL_BASE2 = "/reviews?";
    private String API = "api_key";
    private String API_KEY = "019be2a57857d388c65d464c870471b7";
    private String rootelement = "results";
    private String id;
    private Context context;
    private ListView ls;

    public FetchReviews(String id,Context context,ListView ls) {
        this.id = id;
        URL_BASE1+=id+URL_BASE2;
        this.context = context;
        this.ls = ls;

    }

    @Override
    protected ArrayList<Review> doInBackground(Void... params) {
        HttpURLConnection urlConnection = null;
        BufferedReader bufferedReader = null;
        String finalresult;
        try {
            Uri builturl = Uri.parse(URL_BASE1).buildUpon()
                    .appendQueryParameter(API,API_KEY).build();
            Log.v("************", String.valueOf(builturl));



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

        return jsonParsing(finalresult);


    }
    public ArrayList<Review> jsonParsing(String result){
        ArrayList<Review> finalArrayList = new ArrayList<>(); // this is the array to be passed

        // first get the movie posters and the title from the String result passed

        // 1- craate the json object giving the costructor the passed String.

        try {
            JSONObject jsonObject = new JSONObject(result);


            // 2- crate the a JsonArray object to be able to access the jason elements
            JSONArray jsonArray = jsonObject.getJSONArray(rootelement);

            Review review = null;
            String author;
            String reviewText;

            for(int i =0;i<jsonArray.length();i++) {
                JSONObject oneMovie = jsonArray.getJSONObject(i);
                author = oneMovie.getString("author");
                reviewText = oneMovie.getString("content");
                review = new Review(author,reviewText);
                finalArrayList.add(review);

            }



        } catch (JSONException e) {

            e.printStackTrace();
        }

        return finalArrayList; // here should be the ArrayList<Images>
    }


    @Override
    protected void onPostExecute(ArrayList<Review> reviews) {
        ReviewAdapter adapter = new ReviewAdapter(reviews,context);
        ls.setAdapter(adapter);

        super.onPostExecute(reviews);
    }
}
