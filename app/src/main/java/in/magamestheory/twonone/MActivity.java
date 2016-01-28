package in.magamestheory.twonone;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.facebook.appevents.AppEventsLogger;

public class MActivity extends AppCompatActivity implements fragmnetInter,DialogComm{
    int fragment_id = 1,backPress = 0,level = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_m);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        fragment_home();
    }

    private void startlog() {
        Intent login = new Intent(this, OnlinePlayLog.class);
        startActivity(login);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_m, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_about) {
            fragment_selector(3);
            return true;
        }else if(id == R.id.help){
            showHelp();
            return true;
        }else if(id  == R.id.rate_us){
            rate_us();
            return true;
        }else if(id == R.id.statistics){
            fragment_selector(4);
            return true;
        }else if(id == R.id.action_contactus){
            sendEmail();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void fragment_selector(int a) {
        Fragment fragment;
        fragment_id = a;
        String frag_tag;
        switch (a){
            case 1:
                fragment = new Intro();
                frag_tag = "intro";
                break;
            case 2:
                fragment = new offlineEntryPoint();
                frag_tag = "offlineEntryPoint";
               ;
                break;
            case  3:
                fragment = new Scoreboard();
                frag_tag = "scoreboard";
                break;
            case 4:
                fragment = MActivityFragment.newInstance(level);
                frag_tag = "Mfrag";
                break;
            default:
                fragment = new Intro();
                frag_tag = "intro";
        }

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.layout_main, fragment, frag_tag);
        fragmentTransaction.commit();
    }

    @Override
    public void setLevel(int a) {
        level  = a+3;
    }

    public void fragment_home(){
        Fragment fragment = new Intro();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.layout_main, fragment, "dfsdf");
        fragmentTransaction.commit();
    }
    @Override
    public void onBackPressed() {

        if(fragment_id == 1){
            backPress++;
            if(backPress == 1)  Toast.makeText(this, "press again to exit", Toast.LENGTH_SHORT).show();
            else super.onBackPressed();
        }else{
            fragment_selector(1);
        }
    }



    @Override
    public void DialogButtonAction(int a) {
        FragmentManager manager = getFragmentManager();
        MActivityFragment Mplay = (MActivityFragment) manager.findFragmentByTag("Mfrag");
        switch (a){
            case 1:
                Mplay.replayGame();
                break;
            case 0:
                Mplay.newGame();
                break;
            case 3:
                Mplay.moreTime();
                break;
            default:
                Mplay.moreTime();
                break;

        }


    }
    private void showHelp() {
        String str = "https://www.milindkrohit.wordpress.com/twonone";
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(str)));
    }

    private void rate_us() {
        String str = "https://play.google.com/store/apps/details?id=in.magamestheory.twonon";
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(str)));
    }
    private void sendEmail(){

        String info = "set your message here ",emailAdd;
        emailAdd = "milind0359@gmail.com";
        Log.i("Send email", "");
        String emailaddress[] = {emailAdd};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("plane/text");
        emailIntent.putExtra(Intent.EXTRA_EMAIL,emailaddress);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "SudokuSolverAdvanced : state your subject here");
        emailIntent.putExtra(Intent.EXTRA_TEXT, info);


        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));

            Log.i("Finished sending email", "");
        }
        catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(MActivity.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }
}
