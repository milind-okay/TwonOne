package in.magamestheory.twonone;;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
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
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Arrays;

import layout.FriendList;

public class OnlinePlayLog extends AppCompatActivity implements DataDisplay{
    protected AccessTokenTracker accessTokenTracker;
    protected ProfileTracker profileTracker;
    CallbackManager callbackManager;
    private  DBHelper mydb;
    TextView serverMessage;
    Thread m_objThreadClient;
    Socket clientSocket;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        facebookSDKInitialize();
        setContentView(R.layout.activity_online_play_log);
        mydb = new DBHelper(this);
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
        serverMessage=(TextView)findViewById(R.id.textView);
        accessTokenTracker.startTracking();
        profileTracker.startTracking();
        LoginButton loginButton = (LoginButton) findViewById(R.id.login_button);
       // loginButton.setReadPermissions("user_friends");
        getLoginDetails(loginButton);

    }

    /*
     * Register a callback function with LoginButton to respond to the login result.
     */
    protected void getLoginDetails(LoginButton login_button){

        // Callback registration
        login_button.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult login_result) {
                AccessToken accessToken = login_result.getAccessToken();
                Profile profile = Profile.getCurrentProfile();
                if (profile != null)
                    mydb.insertContact(profile.getName(), profile.getId(), profile.getMiddleName());
                else
                    Toast.makeText(getApplicationContext(), "login error Try again", Toast.LENGTH_LONG).show();
                GraphRequestAsyncTask graphRequestAsyncTask = new GraphRequest(
                        login_result.getAccessToken(),
                        //AccessToken.getCurrentAccessToken(),
                        "/me/friends",
                        null,
                        HttpMethod.GET,
                        new GraphRequest.Callback() {
                            public void onCompleted(GraphResponse response) {

                                try {

                                    JSONArray rawName = response.getJSONObject().getJSONArray("data");
                                    mydb.updateFriendsList(1, rawName.toString());
                                    friendListFragment();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                ).executeAsync();

            }

            @Override
            public void onCancel() {
                // code for cancellation
            }

            @Override
            public void onError(FacebookException exception) {
                //  code to handle error
            }
        });
    }
    public void friendListFragment(){
        Fragment new_fragment = new layout.FriendList();

        FragmentManager manager = getSupportFragmentManager();

        FragmentTransaction fragmentTransaction= manager.beginTransaction();
        fragmentTransaction.add(R.id.layout_friendList, new_fragment, "okay");
        fragmentTransaction.commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    protected void facebookSDKInitialize() {

        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Logs 'install' and 'app activate' App Events.
       // AppEventsLogger.activateApp(this);
        Profile profile = Profile.getCurrentProfile();
        if(profile != null)
            mydb.insertContact(profile.getName(), profile.getId(), profile.getMiddleName());
        else
            Toast.makeText(getApplicationContext(),"login error Try again",Toast.LENGTH_LONG).show();

    }

    @Override
    protected void onPause() {
        super.onPause();

        // Logs 'app deactivate' App Event.
       // AppEventsLogger.deactivateApp(this);

    }

    @Override
    protected void onStop() {
        accessTokenTracker.startTracking();
        profileTracker.startTracking();
        super.onStop();

    }
    public void connect(View view)
    {
        MyServer server= new MyServer();
        server.setEventListener(this);
        server.startListening();

    }

    @Override
    public void Display(String message) {
        serverMessage.setText("" + message);
    }
    ////////
    public void Start(View view)
    {
        m_objThreadClient=new Thread(new Runnable() {
            public void run()
            {
                try
                {
                    clientSocket= new Socket("127.0.0.1",2001);
                    ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream());
                    oos.writeObject("Hellow there");
                    Message serverMessage= Message.obtain();
                    ObjectInputStream ois =new ObjectInputStream(clientSocket.getInputStream());
                    String strMessage = (String)ois.readObject();
                    serverMessage.obj=strMessage;
                    mHandler.sendMessage(serverMessage);
                    oos.close();
                    ois.close();
                }
                catch (Exception e)
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        });

        m_objThreadClient.start();

    }
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            messageDisplay(msg.obj.toString());
        }
    };
    public void messageDisplay(String servermessage)
    {
        serverMessage.setText(""+servermessage);
    }
}