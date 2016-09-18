package movieapp.nohier.com.movieapp2;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by mohamed k on 29/08/2016.
 */
// this class to play the trailer
    // after generating the movie key from the movie id we got;

public class youtubeConnection extends AsyncTask <Void,Void,String>{
    private String id;
    private  static String key;
    private String URL_BASE1 = "https://api.themoviedb.org/3/movie/";
    private String URL_BASE2 = "/videos?";
    private String API = "api_key";
    private String API_KEY = "019be2a57857d388c65d464c870471b7";
    private String rootelement = "results";
Context context;

    public youtubeConnection(String id,Context context){
        this.id = id;
        URL_BASE1+=id+URL_BASE2;
        this.context = context;
        if(context== null)
        {
            Log.v("$$$$$$$$$$$$$","the context is null");
        }
        Log.v("************",URL_BASE1);
    }

    @Override
    protected String doInBackground(Void... params) {
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

        return jsonParsing(finalresult);   // this is the actuall parsing (getting the results)
        // this will return the ArrayList<Images>

    }
    public String jsonParsing(String result){

        try {
            JSONObject jsonObject = new JSONObject(result);

            JSONArray jsonArray = jsonObject.getJSONArray(rootelement);

                JSONObject oneObject = jsonArray.getJSONObject(0);
            key = oneObject.getString("key");

        } catch (JSONException e) {

            e.printStackTrace();
        }

        return key;
    }

    @Override
    protected void onPostExecute(String key) {
        // key is the key of the film

            Intent intent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://www.youtube.com/watch?v=" + key));
        Log.v("***&*&*&*&*&&&***","http://www.youtube.com/watch?v=" + key);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
           context.startActivity(intent);


        super.onPostExecute(key);
    }
}
