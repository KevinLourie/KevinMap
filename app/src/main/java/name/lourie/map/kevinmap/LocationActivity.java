package name.lourie.map.kevinmap;

import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

public class LocationActivity extends Activity {

    private static final String TAG = "LocationActivity";
    Button btnFusedLocation;
    TextView tvLocation;
    FusedLocationService fusedLocationService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //show error dialog if GooglePlayServices not available
        if (!isGooglePlayServicesAvailable()) {
            finish();
        }
        fusedLocationService = new FusedLocationService(this);

        setContentView(R.layout.activity_my);
        tvLocation = (TextView) findViewById(R.id.tvLocation);

        btnFusedLocation = (Button) findViewById(R.id.btnGPSShowLocation);
        btnFusedLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Location location = fusedLocationService.getLocation();
                String locationResult = "";
                if (null != location) {
                    Log.i(TAG, location.toString());
                    locationResult = String.format(
                            "Latitude: %s\n" +
                                    "Longitude: %s\n" +
                                    "Altitude: %s\n" +
                                    "Accuracy: %s\n" +
                                    "Elapsed Time: %s secs\n" +
                                    "Provider: %s\n",
                            location.getLatitude(),
                            location.getLongitude(),
                            location.getAltitude(),
                            location.getAccuracy(),
                            location.getElapsedRealtimeNanos() / 1E9,
                            location.getProvider());
                } else {
                    locationResult = "Location Unavailable!";
                }
                tvLocation.setText(locationResult);
            }
        });
    }

    private boolean isGooglePlayServicesAvailable() {
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (ConnectionResult.SUCCESS == status) {
            return true;
        } else {
            GooglePlayServicesUtil.getErrorDialog(status, this, 0).show();
            return false;
        }
    }
}