package layout;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import in.magamestheory.twonone.BuildConfig;
import in.magamestheory.twonone.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AboutUs extends Fragment {

    ImageButton rate,facebook;
    private TextView ver;

    public AboutUs() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_about_us, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        rate = (ImageButton) getActivity().findViewById(R.id.rateus);
        facebook = (ImageButton)getActivity().findViewById(R.id.facebook);
        rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = "https://play.google.com/store/apps/details?id=com.wordpress.milindkrohit.sudokusolveradvanced";
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(str)));

            }
        });
        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = "https://www.facebook.com/Tic-Tac-Toe-Xs-and-Os-1511132872515136/";
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(str)));

            }
        });
        String versionName = BuildConfig.VERSION_NAME;
        ver = (TextView)getActivity().findViewById(R.id.appv);
        ver.setText(versionName);
    }
}
