package com.startandroid.myapp;


import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ActivityFill  extends FragmentActivity implements  UpdateDialogFragment.UpdateDialogListener, DeleteDialogFragment.NoticeDialogListener{
    EditText idEditText, nameEditText, agEditText, LastnameEditText;
    Button btnSaveInf, btnDeleteInf, btnShowInf, btnClearInf, btnViewList;
    DBHelper db;
    private int selectedID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill);
        db = new DBHelper(this);
        LayoutElements();
//        Intent receivedIntent = getIntent();
//        selectedID = receivedIntent.getIntExtra("id",-1); //NOTE: -1 is just the default value
//        idEditText.setText(selectedID);

        btnViewList.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityFill.this, ViewActivity.class);
                startActivity(intent);
            }
        });

        btnSaveInf.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                int id = 0;
                String name = "", age = "", lastname = "";
                if (idEditText.getText().toString().length() > 0) {
                    id = Integer.parseInt(idEditText.getText().toString());
                }
                boolean res = db.checkID(id);
                if (res == true) {
                    showUpdateDialog();
                }else{
                    if (idEditText.getText().toString().length() > 0) {
                        id = Integer.parseInt(idEditText.getText().toString());
                    }
                    if (nameEditText.getText().toString() != null) {
                        name = nameEditText.getText().toString();
                    }
                    if (agEditText.getText().toString() != null) {
                        age = agEditText.getText().toString();
                    }
                    if (LastnameEditText.getText().toString() != null) {
                        lastname = LastnameEditText.getText().toString();
                    }
                    if (name == null || age == null || lastname == null) {
                        Toast.makeText(getBaseContext(), "fill empty fields.", Toast.LENGTH_SHORT).show();
                    }
                    db.insertData(id, name, age, lastname);
                }
            }
        });

        btnClearInf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClearFields();
            }
        });

        btnDeleteInf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showNoticeDialog();
            }
        });

        btnShowInf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = 0;
                String name = "", age = "", lastname = "";
                if(idEditText.getText().toString().length()>0) {
                    id = Integer.parseInt(idEditText.getText().toString());
                    Cursor cursor = db.showDataById(id);
                    if (cursor.moveToFirst()) {
                        name = cursor.getString(1);
                        age = cursor.getString(2);
                        lastname = cursor.getString(3);
                        setFields(id, name, age, lastname);
                    }
                }else
                    Toast.makeText(getBaseContext(), "Couldt get access to cursor", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void LayoutElements(){
        idEditText = (EditText) findViewById(R.id.idEditText);
        nameEditText = (EditText) findViewById(R.id.nameEditText);
        agEditText = (EditText) findViewById(R.id.agEditText);
        LastnameEditText = (EditText) findViewById(R.id.LastnameEditText);
        btnSaveInf = (Button) findViewById(R.id.btnSaveInf);
        btnDeleteInf = (Button) findViewById(R.id.btnDeleteInf);
        btnShowInf = (Button) findViewById(R.id.btnShowInf);
        btnClearInf = (Button) findViewById(R.id.btnClearInf);
        btnViewList = (Button) findViewById(R.id.btnViewList);
    }

    private void ClearFields(){
        idEditText.setText("");
        nameEditText.setText("");
        agEditText.setText("");
        LastnameEditText.setText("");
    }

    public void setFields(int id, String name,  String age, String lastname) {
        if (id != 0)
            idEditText.setText(String.valueOf(id));
        if (name != null)
            nameEditText.setText(name);
        if (age != null)
            agEditText.setText(age);
        if (lastname != null)
            LastnameEditText.setText(name);
    }

    public void showNoticeDialog(){
        DialogFragment dialog = new DeleteDialogFragment();
        dialog.show(getSupportFragmentManager(), "NoticeDialogFragment");
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        int id = 0;
        try {
            if (idEditText.getText().length() > 0) {
                id = Integer.parseInt(idEditText.getText().toString());
                db.deleteData(id);
                ClearFields();
            } else Toast.makeText(getBaseContext(), "Row doesnt exist", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        dialog.dismiss();
    }


    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        dialog.dismiss();
    }

    public void showUpdateDialog(){
        DialogFragment dialog = new UpdateDialogFragment();
        dialog.show(getSupportFragmentManager(), "UpdateDialogFragment");
    }


    @Override
    public void setPositiveButton(DialogFragment dialog) {
        int id = 0;
        String name = "", age = "", lastname = "";
        try {
            if (idEditText.getText().toString().length() > 0)
                id = Integer.parseInt(idEditText.getText().toString());
            if (nameEditText.getText().toString() != null)
                name = nameEditText.getText().toString();
            if (agEditText.getText().toString() != null)
                age = agEditText.getText().toString();
            if (LastnameEditText.getText().toString() != null)
                lastname = LastnameEditText.getText().toString();
           boolean res = db.updateData(id, name, age, lastname);
            if(res == true)
                Toast.makeText(getBaseContext()," Row has been updated", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(getBaseContext()," Row hasnt been updated", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        dialog.dismiss();
    }


    @Override
    public void setNegativeButton(DialogFragment dialog) {
        dialog.dismiss();

    }

}




