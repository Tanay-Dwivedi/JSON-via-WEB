package com.example.jsonviaweb;

import android.app.ProgressDialog;
import android.os.*;
import android.widget.ArrayAdapter;
import androidx.appcompat.app.AppCompatActivity;
import com.example.jsonviaweb.databinding.ActivityMainBinding;
import org.json.*;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding activityMainBinding;
    ArrayList<String> al;
    ArrayAdapter<String> arrayAdapter;
    Handler handler = new Handler();
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inflate the layout using ViewBinding
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());

        // Initialize the user interface
        initialUser();

        // Set an onClick listener for the fetch button
        activityMainBinding.fetchBtn.setOnClickListener(v -> new fetchData().start());
    }

    private void initialUser() {
        // Initialize the ArrayList and ArrayAdapter for the user list
        al = new ArrayList<>();
        arrayAdapter = new ArrayAdapter<>(this, R.layout.item_custom, R.id.item_text, al);
        activityMainBinding.userList.setAdapter(arrayAdapter);
    }

    // Custom thread for fetching data from a web API
    class fetchData extends Thread {
        String data = "";

        @Override
        public void run() {
            // Show a progress dialog while fetching data
            handler.post(() -> {
                progressDialog = new ProgressDialog(MainActivity.this);
                progressDialog.setMessage("Fetched successfully");
                progressDialog.setCancelable(false);
                progressDialog.show();
            });

            try {
                // Create a URL for the API endpoint
                URL url = new URL("https://api.npoint.io/ad7418864a4c75a029ac");

                // Open an HTTPS connection to the URL
                HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpsURLConnection.getInputStream()));
                String line;

                // Read data from the API response
                while ((line = bufferedReader.readLine()) != null) data += line;

                // Parse JSON data if it's not empty
                if (!data.isEmpty()) {
                    JSONObject jsonObject = new JSONObject(data);
                    JSONArray jsonArray = jsonObject.getJSONArray("employeesList");
                    al.clear();

                    // Extract and add employee names, ages, and cities to the ArrayList
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject details = jsonArray.getJSONObject(i);
                        al.add(details.getString("name") + "\n" + details.getString("age") + "\n" + details.getString("city"));
                    }
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException | JSONException e) {
                // Handle exceptions by throwing a runtime exception
                throw new RuntimeException(e);
            }

            // Update the UI on the main thread after data is fetched
            handler.post(() -> {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                arrayAdapter.notifyDataSetChanged(); // Refresh the ListView
            });
        }
    }
}
