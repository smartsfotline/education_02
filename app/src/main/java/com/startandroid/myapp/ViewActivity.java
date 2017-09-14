package com.startandroid.myapp;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class ViewActivity extends AppCompatActivity implements OnClickListener{
    EditText editID;
    Button ShowInf;
    TextView fieldDB, textView3, textView4;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        LayoutElements();
        DBConnection();
        ShowInf.setOnClickListener(this);
    }

    private void LayoutElements(){
        editID = (EditText) findViewById(R.id.editID);
        ShowInf = (Button) findViewById(R.id.ShowInf);
        fieldDB = (TextView) findViewById(R.id.fieldDB);
        textView3 = (TextView) findViewById(R.id.textView3);
        textView4 = (TextView) findViewById(R.id.textView4);

    }

    public void onClick(View v) {
        switch(v.getId()){
            case R.id.ShowInf:
                selectById(ShowInf);
        }
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

    public void setFields(int id, String name,  String age, String lastname) {
        if (id != 0)
            editID.setText(String.valueOf(id));
        if (name != null)
            fieldDB.setText(name);
        if (age != null)
            textView3.setText(age);
        if (lastname != null)
            textView4.setText(lastname);
    }

    public void selectById(View v){
        String selectQuery = "SELECT * FROM FirstTable WHERE id = ?";
        int id = 0;
        String name, age, lastname;
        try{
            if(editID.getText().toString().length()>0)
                id = Integer.parseInt(editID.getText().toString());
            if(id<0){
                Toast.makeText(getBaseContext(), "Incorrect value in ID field", Toast.LENGTH_SHORT).show();
            }else{
                Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(id)});
                if(cursor.moveToFirst()){
                    id = cursor.getInt(0);
                    name =cursor.getString(1);
                    age = cursor.getString(2);
                    lastname = cursor.getString(3);
                    setFields(id, name, age, lastname);
                }else{
                    Toast.makeText(getBaseContext(), "Smth went wrong", Toast.LENGTH_SHORT).show();
                }
            }
        }catch (Exception e){
            Toast.makeText(getBaseContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }



}

