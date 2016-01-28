package in.magamestheory.twonone;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;

/**
 * Created by milind on 20/1/16.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class GameAlert extends DialogFragment {
    DialogComm dialogComm;
    String title,displayMessageS;
    int displayMessage;
    boolean iconType,buttonType;

    @Override
    public void onAttach(Activity activity) {

        super.onAttach(activity);
        dialogComm = (DialogComm) getActivity();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new  AlertDialog.Builder(getActivity());
        builder.setTitle(title);
        if(buttonType)
            builder.setMessage(displayMessageS);
        else
        builder.setMessage(displayMessage);
        //if(iconType)
       // builder.setIcon(R.drawable.ic_happy);
       // else
       // builder.setIcon(R.drawable.ic_sad);
      if(buttonType){
        builder.setPositiveButton(R.string.new_game, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialogComm.DialogButtonAction(0);
            }
        });
        builder.setNegativeButton(R.string.replay, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialogComm.DialogButtonAction(1);
            }
        });
        builder.setNeutralButton(R.string.need_more_time, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialogComm.DialogButtonAction(2);
            }
        });
      }else{
          builder.setPositiveButton(R.string.dismiss, new DialogInterface.OnClickListener() {
              @Override
              public void onClick(DialogInterface dialog, int which) {

              }
          });
      }
        Dialog dialog = builder.create();
        return dialog;
    }
    public void setM(String title,int sms,boolean iconType,boolean buttonType){
        displayMessage = sms;
        this.iconType = iconType;
        this.buttonType = buttonType;
        this.title = title;

    }
    public void setMS(String title,String sms,boolean iconType,boolean buttonType){
        displayMessageS = sms;
        this.iconType = iconType;
        this.buttonType = buttonType;
        this.title = title;

    }
}

interface DialogComm{
        void DialogButtonAction(int a);
        }