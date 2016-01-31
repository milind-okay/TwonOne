package in.magamestheory.twonone;


import android.annotation.TargetApi;
import android.app.Fragment;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class Intro extends Fragment implements View.OnClickListener{
    fragmnetInter comm;
    private Button playonline,playoffline,offlineMode,onlineMode,rules;
    public Intro() {
        // Required empty public constructor
    }
    public void init(){
        playoffline = (Button) getActivity().findViewById(R.id.playoffline);
        playonline = (Button)getActivity().findViewById(R.id.playonline);
        offlineMode  = (Button)getActivity().findViewById(R.id.offlineMode);
        onlineMode = (Button)getActivity().findViewById(R.id.onlineMode);
        rules  = (Button)getActivity().findViewById(R.id.rules);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_intro, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
        comm = (fragmnetInter) getActivity();
        playoffline.setOnClickListener(this);
        playonline.setOnClickListener(this);
        onlineMode.setOnClickListener(this);
        offlineMode.setOnClickListener(this);
        rules.setOnClickListener(this);

    }

    private void startOnlineActivity() {
        Bundle dataBundle = new Bundle();
        dataBundle.putInt("id", 1);
        Intent intent =  new Intent(getActivity().getApplicationContext(),GCMActivity.class);
        //intent.putExtras(dataBundle);
        startActivity(intent);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rules:
                showDialog("Rules", R.string.game_details, true);
                break;
            case R.id.offlineMode:
                showDialog("Offline Mode", R.string.offline_mode, true);
                break;
            case R.id.onlineMode:
                showDialog("Online Mode", R.string.online_mode, true);
                break;
            case R.id.playoffline:
                comm.fragment_selector(2);
                break;
            case R.id.playonline:
                startOnlineActivity();
                break;
        }
    }
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void showDialog(String title,int sms,boolean iconType){
        GameAlert gameAlert= new GameAlert();
        gameAlert.setM(title,sms, iconType,false);
        gameAlert.show(getActivity().getFragmentManager(), "Intro");
    }

    private void showRule() {
        
    }
}
