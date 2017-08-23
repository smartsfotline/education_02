package com.startandroid.myapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    Button btnFill, btnView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LayoutElements();
        btnFill.setOnClickListener(this);
        btnView.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnView:
                Intent intent = new Intent(this, ViewActivity.class);
                startActivity(intent);
                break;
            case R.id.btnFill:
                Intent intent2 = new Intent(this, ActivityFill.class);
                startActivity(intent2);
                break;
        }
    }

    private void LayoutElements(){
        btnFill = (Button) findViewById(R.id.btnFill);
        btnView = (Button) findViewById(R.id.btnView);
    }
}
