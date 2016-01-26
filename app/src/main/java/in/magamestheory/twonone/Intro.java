package in.magamestheory.twonone;


import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class Intro extends Fragment {
    fragmnetInter comm;
    private Button playonline,playoffline;
    public Intro() {
        // Required empty public constructor
    }
    public void init(){
        playoffline = (Button) getActivity().findViewById(R.id.playoffline);
        playonline = (Button)getActivity().findViewById(R.id.playonline);
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
        playoffline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comm.fragmnet_selector(2);
            }
        });
        playonline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startOnlineActivity();
            }
        });
    }

    private void startOnlineActivity() {
        Intent intent =  new Intent(getActivity().getApplicationContext(), OnlinePlayLog.class);
        startActivity(intent);
    }
}
