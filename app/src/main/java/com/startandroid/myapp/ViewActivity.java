package com.startandroid.myapp;


import android.content.Intent;
import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;



public class ViewActivity extends AppCompatActivity {
   private ListView listView;
    SimpleCursorAdapter dataAdapter;
    DBHelper db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        listView = (ListView) findViewById(R.id.listView);
        db = new DBHelper(this);
        populateListView();

    }

    private void populateListView(){
        Cursor data = db.getData();
        ArrayList<String> listData = new ArrayList<>();
            while(data.moveToNext()){
                listData.add(data.getString(0));
        }

        ArrayList<String> secondArrayList = new ArrayList<>();
        secondArrayList.addAll(listData);
        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, secondArrayList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String id = adapterView.getItemAtPosition(i).toString();
                Cursor data = db.getItemID(id);
                int itemID = -1;
                while(data.moveToNext()){
                    itemID = data.getInt(0);
                }
                if(itemID > -1){
                    Intent editScreenIntent = new Intent(ViewActivity.this, ActivityFill.class);
                    editScreenIntent.putExtra("id",itemID);
                    startActivity(editScreenIntent);
                }
            }
        });
    }
}






