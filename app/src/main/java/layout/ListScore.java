package layout;

import android.database.Cursor;
import android.os.Bundle;
import android.app.ListFragment;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import in.magamestheory.twonone.DBHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by milind on 26/1/16.
 */
public class ListScore extends ListFragment {
    private List<ListViewItem> mItems;        // ListView items list
    String players_name,players_score;
    private  DBHelper mydb;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mydb = new DBHelper(getActivity());
        // initialize the items list
        mItems = new ArrayList<ListViewItem>();
        int pairNumber = mydb.numberOfRows();

            Cursor rs = mydb.getMyData(1);
            rs.moveToFirst();
            players_name = rs.getString(rs.getColumnIndex(DBHelper.ME)) + "    Max Score: " +  rs.getString(rs.getColumnIndex(DBHelper.MSCORE));

            mItems.add(new ListViewItem(players_name,""));
            if(!rs.isClosed())
                rs.close();




        // initialize and set the list adapter
        setListAdapter(new ListViewAdapter(getActivity(), mItems));
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

