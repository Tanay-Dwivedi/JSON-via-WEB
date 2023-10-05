package com.example.jsonviaweb;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.example.jsonviaweb.databinding.ActivityMainBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
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
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());
        initialUser();
        activityMainBinding.fetchBtn.setOnClickListener(v -> new fetchData().start());

    }

    private void initialUser() {

        al = new ArrayList<>();
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, al);
        activityMainBinding.userList.setAdapter(arrayAdapter);
    }


    class fetchData extends Thread {

        String data = "";

        @Override
        public void run() {

            handler.post(() -> {
                progressDialog = new ProgressDialog(MainActivity.this);
                progressDialog.setMessage("Fetched successfully");
                progressDialog.setCancelable(false);
                progressDialog.show();
            });

            try {
                URL url = new URL("https://api.npoint.io/ad7418864a4c75a029ac");
                HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpsURLConnection.getInputStream()));
                String line;

                while((line=bufferedReader.readLine())!= null) {

                    data += line;

                }

                if(!data.isEmpty()) {
                    JSONObject jsonObject = new JSONObject(data);
                    JSONArray jsonArray = jsonObject.getJSONArray("employeesList");
                    al.clear();
                    for(int i=0; i<jsonArray.length(); i++) {
                        JSONObject details = jsonArray.getJSONObject(i);
                        String name = details.getString("name");
                        al.add(name);
                    }

                }

            }catch (MalformedURLException e) {
                e.printStackTrace();
            }
            catch (IOException | JSONException e) {
                throw new RuntimeException(e);
            }
            //super.run();
            handler.post(new Runnable() {
                @Override
                public void run() {
                    if(progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    arrayAdapter.notifyDataSetChanged();
                }
            });
        }
    }

}
