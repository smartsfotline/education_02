package com.startandroid.myapp;


import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;


public class ViewActivity extends AppCompatActivity implements View.OnClickListener{
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
//        listView = (ListView) findViewById(R.id.listView);
        RecyclerView rv = (RecyclerView)findViewById(R.id.rv);
        db = new DBHelper(this);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
        Cursor cursor = db.getData();
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, cursor);
        rv.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {

    }
}

//
//     private void populateListView(){
//        Cursor data = db.getData();
//        ArrayList<String> listData = new ArrayList<>();
//            while(data.moveToNext()){
//                listData.add(data.getString(0));
//        }
//
//        ArrayList<String> secondArrayList = new ArrayList<>();
//        secondArrayList.addAll(listData);
//        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, secondArrayList);
//        listView.setAdapter(adapter);
//
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                String id = adapterView.getItemAtPosition(i).toString();
//                Cursor data = db.getItemID(id);
//                int itemID = -1;
//                while(data.moveToNext()){
//                    itemID = data.getInt(0);
//                }
//                if(itemID > -1){
//                    Intent editScreenIntent = new Intent(ViewActivity.this, ActivityFill.class);
//                    editScreenIntent.putExtra("id",itemID);
//                    startActivity(editScreenIntent);
//                }
//            }
//        });







