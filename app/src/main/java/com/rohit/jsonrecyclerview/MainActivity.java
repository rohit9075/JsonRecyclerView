package com.rohit.jsonrecyclerview;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<Model> mUserList = new ArrayList<>();

    Model mUser;
    RecyclerView mRecyclerView;
    RecyclerAdapter mRecyclerAdapter;
    private String TAG = MainActivity.class.getSimpleName();
    ProgressDialog progressDialog;

    // Server url to get the Json data
    String reqUrl = "http://pusuluribalaji66.000webhostapp.com/RetrofitExample/public/user";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initViews() method call
        initViews();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mUserList = new ArrayList<>();


        new GetUsers().execute();
    }

    public void initViews() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
    }

    @SuppressLint("StaticFieldLeak")
    public class GetUsers extends AsyncTask<Void, Void, Void> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setMessage("please wait");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (progressDialog.isShowing())
                progressDialog.dismiss();
            mRecyclerAdapter = new RecyclerAdapter(getApplicationContext(), mUserList);
            mRecyclerView.setAdapter(mRecyclerAdapter);

        }

        @Override
        protected Void doInBackground(Void... voids) {

            // HttpHandler object
            HttpHandler httpHandler = new HttpHandler();

            // making the method call in httpHandler class.
            String jsonString = httpHandler.makeServiceCall(reqUrl);

            if (jsonString != null) {
                try {

                    // jsonObject to handle the received json file.
                    JSONObject jsonObject = new JSONObject(jsonString);

                    // getting the json array from the json object
                    JSONArray article = jsonObject.getJSONArray("users");

                    // traversing the json array
                    for (int i = 0; i < article.length(); i++) {
                        // getting the json object from the array.
                        JSONObject jsonObject1 = article.optJSONObject(i);

                        // model class to store the user data
                        Model mUser = new Model();
                        mUser.setId(jsonObject1.getString("id"));
                        mUser.setEmail(jsonObject1.getString("email"));
                        mUser.setName(jsonObject1.getString("name"));
                        mUser.setGender(jsonObject1.getString("gender"));

                        // add the user object in the userList.
                        mUserList.add(mUser);
                    }
                } catch (JSONException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "parser error", Toast.LENGTH_LONG).show();
                        }
                    });
                    e.printStackTrace();
                }
            } else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "can't get json file", Toast.LENGTH_LONG).show();
                    }
                });
            }
            return null;
        }
    }

}
