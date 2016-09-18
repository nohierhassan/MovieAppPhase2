package movieapp.nohier.com.movieapp2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

public class NoConnectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_connection);
CheckForConnection checkForConnection = new CheckForConnection(this);

        Log.v("XXXXXXXXXXXXXXX", String.valueOf(checkForConnection.isConnected()));
        Log.v("XXXXXXXXXXXXXXX", String.valueOf(checkForConnection.isConnected()));
        Toast.makeText(this, "No Connection", Toast.LENGTH_LONG).show();
    }
}
