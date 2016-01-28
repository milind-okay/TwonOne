package in.magamestheory.twonone;


import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Random;


/**
 * A simple {@link Fragment} subclass.
 */
public class offlineEntryPoint extends Fragment implements AdapterView.OnItemSelectedListener{
    fragmnetInter comm;
    private  Spinner level_spinner;
    private TextView login;
    private Button play;
    private DBHelper mydb;
    int level_no,gameBoxValue[];
    public offlineEntryPoint() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_offline_entry_point, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        comm = (fragmnetInter) getActivity();
        mydb = new DBHelper(getActivity());
        level_spinner = (Spinner) getActivity().findViewById(R.id.level);
        level_spinner.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> levelAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.level_str, android.R.layout.simple_spinner_item);

        levelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        level_spinner.setAdapter(levelAdapter);
        play = (Button)getActivity().findViewById(R.id.play);
        login = (TextView)getActivity().findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startLoginActivity();
            }
        });
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comm.fragment_selector(4);
            }
        });
    }

    private void startLoginActivity() {
        Bundle dataBundle = new Bundle();
        dataBundle.putInt("id", 0);
        Intent intent =  new Intent(getActivity().getApplicationContext(), OnlineLog.class);
        intent.putExtras(dataBundle);
        startActivity(intent);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        Spinner spinner = (Spinner) parent;
        if (spinner.getId() == R.id.level) {
            int a = position + 3;
            a = a*a;
            gameBoxValue = new int[a];
            String str = genrateArray(a);
            mydb.updateGameBoard(1, str);
             comm.setLevel(position);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // TODO Auto-generated method stub
    }
    private String genrateArray(int a){
        String str = "";
        Random generator = new Random();

        for(int i=0;i<a;i++){

            gameBoxValue[i] = generator.nextInt(9) + 1;
            str+=Integer.toString(gameBoxValue[i]);

        }
        return str;
    }
}
