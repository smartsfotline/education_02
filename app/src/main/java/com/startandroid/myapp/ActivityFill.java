package com.startandroid.myapp;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ActivityFill extends AppCompatActivity implements View.OnClickListener{
    EditText idEditText, nameEditText, agEditText, LstnameEditText;
    Button btnFillInf;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill);
        LayoutElements();
        DBConnection();
        btnFillInf.setOnClickListener(this);

    }
    public void onClick(View v) {
       switch(v.getId()){
           case R.id.btnFillInf:
               insertButtonClick(btnFillInf);
               ClearFields();
       }
    }

    private void LayoutElements(){
        idEditText = (EditText) findViewById(R.id.idEditText);
        nameEditText = (EditText) findViewById(R.id.nameEditText);
        agEditText = (EditText) findViewById(R.id.agEditText);
        LstnameEditText = (EditText) findViewById(R.id.LstnameEditText);
        btnFillInf = (Button) findViewById(R.id.btnFillInf);
    }

    private void DBConnection(){
        try{
            db = openOrCreateDatabase("TestDB", MODE_PRIVATE, null);
            String CreateTableQuery = "CREATE TABLE IF NOT EXISTS FirstTable("+"id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"+"name TEXT,"+"age TEXT,"+"lastname TEXT"+");";
            db.execSQL(CreateTableQuery);
        }
        catch (Exception e){
            Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void ClearFields(){
        idEditText.setText("");
        nameEditText.setText("");
        agEditText.setText("");
        LstnameEditText.setText("");
    }

    public void setFields(int id, String name,  String age, String lastname) {
        if (id != 0)
            idEditText.setText(String.valueOf(id));
        if (name != null)
            nameEditText.setText(name);
        if (age != null)
            agEditText.setText(age);
        if (lastname != null)
            LstnameEditText.setText(name);
    }

    public void insertButtonClick(View v){
        int id = 0;
        String name = "";
        String lastname = "";
        String age = "";
        try{
            if(idEditText.getText().toString().length() > 0){
                id = Integer.parseInt(idEditText.getText().toString());
            }
            if(nameEditText.getText().toString() != null){
                name = nameEditText.getText().toString();
            }
            if(agEditText.getText().toString() != null){
                age = agEditText.getText().toString();
            }
            if(LstnameEditText.getText().toString() != null){
                lastname = LstnameEditText.getText().toString();
            }
            if(name == null || age == null || lastname == null ){
                Toast.makeText(getBaseContext(),"fill empty fields.", Toast.LENGTH_SHORT).show();
            }
            ContentValues contentValues = new ContentValues();
            if(id != 0){
                contentValues.put("id", id);
            }
            contentValues.put("name", name);
            contentValues.put("age", age);
            contentValues.put("lastname", lastname);
            long rowId = db.insert("FirstTable", null, contentValues);
            setFields((int) rowId, name, age, lastname);
            if (rowId > 0)
                Toast.makeText(getBaseContext(), "Row inserted with rowId: " + String.valueOf(rowId), Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(getBaseContext(), "Incorrect ID.", Toast.LENGTH_SHORT).show();

        }
        catch(Exception e){
            Toast.makeText(getBaseContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
        }

    }

}

