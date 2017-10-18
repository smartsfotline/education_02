package com.startandroid.myapp;


import android.app.Activity;
import android.app.Dialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

public class UpdateDialogFragment extends DialogFragment{


    public interface UpdateDialogListener{
        public void setPositiveButton(DialogFragment dialog);
        public void  setNegativeButton(DialogFragment dialog);
    }

    UpdateDialogListener listener;

    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("This item exists! Do you want to overwrite this?")
                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        listener.setPositiveButton(UpdateDialogFragment.this);
                    }
                })
                .setNegativeButton("no", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        listener.setNegativeButton(UpdateDialogFragment.this);
                    }
                });
        return builder.create();
    }

    public void onAttach(Activity activity){
        super.onAttach(activity);
        try{
            listener = (UpdateDialogListener) activity;
        }catch (ClassCastException e){
            throw new ClassCastException(activity.toString() + " must implement UpdateDialogListener");
        }
    }
}
