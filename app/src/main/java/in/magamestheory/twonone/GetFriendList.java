/*package in.magamestheory.twonone;


import android.os.AsyncTask;
import android.util.Log;

import com.facebook.FacebookRequestError;
import com.facebook.HttpMethod;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by milind on 25/1/16.
 */
/*
public class GetFriendList extends AsyncTask<FacebookFriend.Type, Boolean, Boolean> {
    private final String TAG = GetFriendList.class.getSimpleName();
    GraphObject graphObject;
    ArrayList<FacebookFriend> myList = new ArrayList<FacebookFriend>();

    @Override
    protected Boolean doInBackground(FacebookFriend.Type... pickType) {
        //
        //Determine Type
        //
        String facebookRequest;
        if (pickType[0] == FacebookFriend.Type.AVAILABLE) {
            facebookRequest = "/me/friends";
        } else {
            facebookRequest = "/me/invitable_friends";
        }

        //
        //Launch Facebook request and WAIT.
        //
        new LoginClient.Request(
                Session.getActiveSession(),
                facebookRequest,
                null,
                HttpMethod.GET,
                new LoginClient.Request.Callback() {
                    public void onCompleted(Response response) {
                        FacebookRequestError error = response.getError();
                        if (error != null && response != null) {
                            Log.e(TAG, error.toString());
                        } else {
                            graphObject = response.getGraphObject();
                        }
                    }
                }
        ).executeAndWait();

        //
        //Process Facebook response
        //
        //
        if (graphObject == null) {
            return false;
        }

        int numberOfRecords = 0;
        JSONArray dataArray = (JSONArray) graphObject.getProperty("data");
        if (dataArray.length() > 0) {

            // Ensure the user has at least one friend ...
            for (int i = 0; i < dataArray.length(); i++) {

                JSONObject jsonObject = dataArray.optJSONObject(i);
                FacebookFriend facebookFriend = new FacebookFriend(jsonObject, pickType[0]);

                if (facebookFriend.isValid()) {
                    numberOfRecords++;

                    myList.add(facebookFriend);
                }
            }
        }

        //make sure there are records to process
        if (numberOfRecords > 0){
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected void onProgressUpdate(Boolean... booleans) {
        //no need to update this, wait until the whole thread finishes.
    }

    @Override
    protected void onPostExecute(Boolean result) {
        if (result) {
                /*
                User the array "myList" to create the adapter which will control showing items in the list.
                 */
/*
        } else {
            Log.i(TAG, "Facebook Thread unable to Get/Parse friend data. Type = " + pickType);
        }
    }
}*/