package layout;

import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import in.magamestheory.twonone.DBHelper;
import in.magamestheory.twonone.R;

/**
 * Created by milind on 18/1/16.
 */
public class FriendList extends ListFragment {
    private List<ListViewItem> mItems;        // ListView items list
    String jsondata;
    private DBHelper mydb;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mydb = new DBHelper(getActivity());
        Cursor rs = mydb.getMyData(1);
        rs.moveToFirst();
        jsondata = rs.getString(rs.getColumnIndex(DBHelper.ME));
        if(!rs.isClosed())
            rs.close();
        JSONArray friendslist;
        //ArrayList<String> friends = new ArrayList<String>();
        mItems = new ArrayList<ListViewItem>();
        try {
            friendslist = new JSONArray(jsondata);
            for (int l=0; l < friendslist.length(); l++) {
                //friends.add(friendslist.getJSONObject(l).getString("name"));
                mItems.add(new ListViewItem(friendslist.getJSONObject(l).getString("name"),"name"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // adapter which populate the friends in listview
       // ArrayAdapter adapter = new ArrayAdapter<String>(getActivity(), R.layout.fragment_friend_list, friends);

        //ListView listView = (ListView) getActivity().findViewById(R.id.listView);
       // listView.setAdapter(adapter);
        mydb = new DBHelper(getActivity());
        // initialize the items list




        // initialize and set the list adapter
        setListAdapter(new ListViewAdapter(getActivity(), mItems));
    }
    public void getJsonObgect(String obj){
        jsondata = obj;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // remove the dividers from the ListView of the ListFragment
        getListView().setDivider(null);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        // retrieve theListView item
        ListViewItem item = mItems.get(position);

        // do something
        Toast.makeText(getActivity(), item.description, Toast.LENGTH_SHORT).show();
    }
}

