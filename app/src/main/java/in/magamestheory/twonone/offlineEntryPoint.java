package in.magamestheory.twonone;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;


/**
 * A simple {@link Fragment} subclass.
 */
public class offlineEntryPoint extends Fragment implements AdapterView.OnItemSelectedListener{
    fragmnetInter comm;
    private  Spinner level_spinner;
    private Button play;
    int level_no;
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
        level_spinner = (Spinner) getActivity().findViewById(R.id.level);
        level_spinner.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> levelAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.level_str, android.R.layout.simple_spinner_item);

        levelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        level_spinner.setAdapter(levelAdapter);
        play = (Button)getActivity().findViewById(R.id.play);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comm.fragment_selector(4);
            }
        });
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        Spinner spinner = (Spinner) parent;
        if (spinner.getId() == R.id.level) {
             comm.setLevel(position);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // TODO Auto-generated method stub
    }
}
