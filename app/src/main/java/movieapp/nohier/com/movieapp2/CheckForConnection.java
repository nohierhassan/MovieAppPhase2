package movieapp.nohier.com.movieapp2;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

/**
 * Created by mohamed k on 23/08/2016.
 */
public class CheckForConnection {
    Context c;
    public  CheckForConnection(Context c)
    {

        this.c = c;
    }

    boolean isConnected() {
        boolean wifiConnected = false;
        boolean mobileConnected = false;
        ConnectivityManager connMgr =
                (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeInfo = connMgr.getActiveNetworkInfo();
        if (activeInfo != null && activeInfo.isConnected()) {
            wifiConnected = activeInfo.getType() == ConnectivityManager.TYPE_WIFI;
            mobileConnected = activeInfo.getType() == ConnectivityManager.TYPE_MOBILE;
            Log.v("XXXXXXXXXXXXXXX", String.valueOf(wifiConnected));
            Log.v("XXXXXXXXXXXXXXX", String.valueOf(mobileConnected));
            return true;
        } else {
            wifiConnected = false;
            mobileConnected = false;
            return false;
        }

    }
}

