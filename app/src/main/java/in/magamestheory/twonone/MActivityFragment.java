package in.magamestheory.twonone;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * A placeholder fragment containing a simple view.
 */
public class MActivityFragment extends Fragment implements View.OnClickListener {
    private static final String ARG_PARAM1 = "gridSize";
    private static final String ARG_PARAM2 = "param2";
    private Button mdelete,mscore,movesCount;
    private TextView mclock;
    String strRow[];
    boolean gameState[];
    int boardSize,numscore,nummovecount,numSelected = 0,colWidth,time;
    int selectedP[],GameBoxValue[];
    View selectedView[];
    MyCount counter;
    GridView gridview;
    ImageAdapter imageAdapter;
    public MActivityFragment() {
        selectedP = new int[2];
        selectedView = new View[2];
        // gameBoard = new String[boardSize][boardSize];




    }
    public static MActivityFragment newInstance(int param1) {
        MActivityFragment fragment = new MActivityFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);

        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            colWidth = getArguments().getInt(ARG_PARAM1);
            boardSize = colWidth*colWidth;
            time = colWidth*15;
            //Toast.makeText(getActivity().getApplicationContext(), String.format("%d",colWidth), Toast.LENGTH_SHORT).show();
            //mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_m, container, false);

       /* if(savedInstanceState != null){
            for(int i=0;i<boardSize;i++)
            gameBoard[i] = savedInstanceState.getStringArray(strRow[i]);
            nummovecount = savedInstanceState.getInt("movecount");
            numscore = savedInstanceState.getInt("numscore");
        }else{
            genrateArray(boardSize);
            initgameState(boardSize);
            nummovecount =0;
            numscore = 0;
        }*/
    }



    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        init();
        counter = new MyCount(time*1000,1000);
         gridview = (GridView) getActivity().findViewById(R.id.grid_view);
        ImageAdapter imageAdapter = new ImageAdapter(getActivity());
        imageAdapter.setArray(GameBoxValue,boardSize);
        gridview.setNumColumns(colWidth);
        gridview.setAdapter(imageAdapter);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                if (gameState[position]) {
                    if (position == selectedP[0]) {
                        unselect(v);
                        selectedP[0] = -1;

                    } else if (position == selectedP[1]) {
                        unselect(v);
                        selectedP[1] = -1;

                    } else if (numSelected == 2) {
                        Toast.makeText(getActivity().getApplicationContext(), "you can select atmost two boxs", Toast.LENGTH_SHORT).show();
                    } else {

                        if (selectedP[0] == -1) {
                            selectedP[0] = position;
                            selectedView[0] = v;
                        } else if (selectedP[1] == -1) {
                            selectedP[1] = position;
                            selectedView[1] = v;
                        }
                        numSelected++;
                        v.setBackgroundResource(R.drawable.side_nav_bar);
                    }
                }
            }
        });
        mdelete.setOnClickListener(this);
        counter.start();

    }
    public void unselect(View v){
        v.setBackgroundResource(R.drawable.gradient);
        numSelected--;
    }



    public  void init(){
        mdelete = (Button) getActivity().findViewById(R.id.button_delete);
        mscore = (Button)getActivity().findViewById(R.id.button_score);
        movesCount = (Button)getActivity().findViewById(R.id.button_move);
        mclock = (TextView)getActivity().findViewById(R.id.timer);
        strRow = new String[boardSize];
        gameState = new boolean[boardSize];

        selectedView[0] = null;
        selectedView[1] = null;
        GameBoxValue = new int [boardSize];
        setSelectedP();
        genrateArray(boardSize);
        initgameState(boardSize);
        //colWidth = 7;

    }

    @Override
    public void onClick(View v) {
       if(v.getId() == R.id.button_delete){
           deleteBox();
       }
    }
    public class MyCount extends CountDownTimer {

        public MyCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
          checkResult();
        }

        @Override
        public void onTick(long millisUntilFinished) {
            mclock.setText(String.format("%d",millisUntilFinished/1000));

        }

    }
    private void deleteBox(){
        if((selectedP[0] == -1 || selectedP[1] == -1)|| (GameBoxValue[selectedP[0]] != GameBoxValue[selectedP[1]])){
        nummovecount++;
        if(selectedP[0] != -1){
            selectedView[0].setBackgroundResource(R.drawable.ic_blank);
            gameState[selectedP[0]] = false;
            selectedP[0] = -1;
            numscore++;
            numSelected--;

        }
        if(selectedP[1] != -1){
            selectedView[1].setBackgroundResource(R.drawable.ic_blank);
            gameState[selectedP[1]] = false;
            selectedP[1] = -1;
            numscore++;
            numSelected--;
        }
        mscore.setText(String.format("%d",numscore));
        movesCount.setText(String.format("%d", nummovecount));
        if(numscore == boardSize){
            checkResult();
        }
        }else{
            Toast.makeText(getActivity().getApplicationContext(), "can't delete same Numbers", Toast.LENGTH_LONG).show();
        }

    }
    public void checkResult(){
        if(nummovecount == GetOptimalValue()){
           showDialog("Congo You Do It !\n Total Move " + nummovecount+ "\nYour Score : " + numscore + colWidth*100, true);
        }else{
            showDialog("Ohh You miss it.\n Total Move by you " + nummovecount + "\n Required : " + GetOptimalValue()
                    + "\nYour Score : " + numscore, false);
        }
    }
    public int GetOptimalValue() {
        int n=boardSize;
        int max1=0;
        Map<Integer, Integer> hm = new HashMap<Integer, Integer>();
        for(int i=0;i<boardSize;i++)
        {
            if(hm.containsKey(GameBoxValue[i])) {
                hm.put(GameBoxValue[i], hm.get(GameBoxValue[i]) + 1);
            }
            else {
                hm.put(GameBoxValue[i], 1);
            }
        }

        for(int key: hm.keySet()) {
            if(hm.get(key) > max1) {
                max1=hm.get(key);
            }
        }

        if(max1 >= (n+1)/2)
            //  System.out.println("Correct answer is :"+ max1);
            return max1;
        else
            //  System.out.println("correct answer is :"+(n+1)/2);
            return (n+1)/2;
    }
    public void replayGame(){
        resetVar();
        ImageAdapter imageAdapter = new ImageAdapter(getActivity());
        imageAdapter.setArray(GameBoxValue, boardSize);
        gridview.setAdapter(imageAdapter);
        counter.start();
        //setGameBoard();
    }
    public void newGame() {
        genrateArray(boardSize);
        replayGame();

    }
    public void moreTime(){
        counter.start();
    }
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void showDialog(String sms,boolean iconType){
        GameAlert gameAlert= new GameAlert();
        gameAlert.setMS("Game Resuilt",sms, iconType,true);
        gameAlert.show(getActivity().getFragmentManager(), "gameAlert");
    }
    private void genrateArray(int a){
        Random generator = new Random();
        for(int i=0;i<a;i++){

                GameBoxValue[i] = generator.nextInt(9) + 1;

        }
    }
    private void initgameState(int boardSize) {
        for(int i=0;i<boardSize;i++){

                gameState[i] = true;

        }
    }


    public void resetVar(){
        numSelected = 0;
        numscore = 0;
        nummovecount = 0;
        movesCount.setText("0");
        mscore.setText("0");
        selectedP[0] = -1;
        selectedP[1] = -1;
        selectedView[0] = null;
        selectedView[1] = null;
        initgameState(boardSize);
        time = colWidth*20;


    }

    public void setSelectedP() {
        selectedP[0] = -1;
        selectedP[1] = -1;

    }
    public void setColWidth(int a){
        colWidth = a;
        boardSize = a*a;
    }
}
