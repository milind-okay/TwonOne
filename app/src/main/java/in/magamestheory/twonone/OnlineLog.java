package in.magamestheory.twonone;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphRequestAsyncTask;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;



import org.json.JSONArray;
import org.json.JSONException;



public class OnlineLog extends AppCompatActivity {
    private DBHelper mydb;
    private Button button;
    int id_To=1;
    Cursor rs;
    String[] mobileArray = {"Milind"};
    protected AccessTokenTracker accessTokenTracker;
    protected ProfileTracker profileTracker;
    private LoginButton loginButton;
    CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_online_log);
        Bundle extras = getIntent().getExtras();
        if(extras != null)
        id_To = extras.getInt("id");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        callbackManager = CallbackManager.Factory.create();
        init_val();
        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {

            }
        };
        profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {

            }
        };
        accessTokenTracker.startTracking();
        profileTracker.startTracking();
        loginButton.setReadPermissions("user_friends");
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Toast.makeText(getApplicationContext(), "User ID: "
                        + loginResult.getAccessToken().getUserId()
                        + "\n" +
                        "Auth Token: "
                        + loginResult.getAccessToken().getToken(), Toast.LENGTH_SHORT).show();
                // AccessToken accessToken = loginResult.getAccessToken();
                Profile profile = Profile.getCurrentProfile();
                save_profile(profile);
                GraphRequestAsyncTask graphRequestAsyncTask = new GraphRequest(
                        loginResult.getAccessToken(),
                        //AccessToken.getCurrentAccessToken(),
                        "/me/friends",
                        null,
                        HttpMethod.GET,
                        new GraphRequest.Callback() {
                            public void onCompleted(GraphResponse response) {

                                try {

                                    JSONArray rawName = response.getJSONObject().getJSONArray("data");
                                     mydb.updateFriendsList(1, rawName.toString());
                                    mobileArray[0] = rawName.toString();
                                    // friendListFragment();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                ).executeAsync();


            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });
        if(mobileArray!=null) {
            ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.listviewlayout, mobileArray);
            ListView listView = (ListView) findViewById(R.id.listView);
            listView.setAdapter(adapter);
        }

    }

    private void save_profile(Profile profile) {
        if(profile != null) {
            if (mydb.insertContact(profile.getName(), profile.getId(),  "")) {
                Toast.makeText(getApplicationContext(), "Hi! " + profile.getName(), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "not done", Toast.LENGTH_SHORT).show();
            }
            if(id_To == 0){
                Bundle dataBundle = new Bundle();
                dataBundle.putInt("fId", 4);
            Intent intent = new Intent(getApplicationContext(),MActivity.class);
                intent.putExtras(dataBundle);
            startActivity(intent);
            finish();}else{

            }
        }
    }



    private void init_val(){
        mydb = new DBHelper(this);

        loginButton = (LoginButton) findViewById(R.id.signup_button);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
    @Override
    protected void onResume() {
        super.onResume();
        Profile profile = Profile.getCurrentProfile();
        save_profile(profile);
        // newUser.setText(mData);
    }
    @Override
    protected void onStop() {
        super.onStop();
        accessTokenTracker.startTracking();
        profileTracker.startTracking();
    }

}
