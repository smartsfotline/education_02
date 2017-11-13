package com.startandroid.myapp;


import android.Manifest;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import static com.startandroid.myapp.R.id.image;
import static com.startandroid.myapp.R.id.imageView;

public class ActivityFill  extends FragmentActivity implements  UpdateDialogFragment.UpdateDialogListener, DeleteDialogFragment.NoticeDialogListener{
    EditText idEditText, nameEditText, agEditText, LastnameEditText;
    Button btnSaveInf, btnDeleteInf, btnShowInf, btnClearInf, btnViewList, imgLoad;
    ImageView imageView2;
    DBHelper db;
    private int selectedID;
    private static final int PICK_IMG = 1;


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
                    imageView2.setDrawingCacheEnabled(true);
                    imageView2.buildDrawingCache();
                    Bitmap bitmap = imageView2.getDrawingCache();
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                    byte[] data = outputStream.toByteArray();
                    boolean result =  db.insertData(id, name, age, lastname, data);
                    if(result == true)
                        Toast.makeText(getBaseContext()," Row has been inserted successfully", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(getBaseContext()," Row hasnt been inserted", Toast.LENGTH_SHORT).show();
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
                        byte[] image = cursor.getBlob(4);
                        Bitmap bmp = BitmapFactory.decodeByteArray(image, 0, image.length);
                        imageView2.setImageBitmap(Bitmap.createScaledBitmap(bmp, 200, 200, false));
                        setFields(id, name, age, lastname);

                    }
                }else
                    Toast.makeText(getBaseContext(), "Couldt get access to cursor", Toast.LENGTH_SHORT).show();
            }
        });

        imgLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, PICK_IMG);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
            if(resultCode == RESULT_OK) {
                try {
                    final Uri imageUri = data.getData();
                    final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                    final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                    imageView2.setImageBitmap(selectedImage);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
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
        imgLoad = (Button) findViewById(R.id.imgLoad);
        imageView2 = findViewById(R.id.imageView2);
    }

    private void ClearFields(){
        idEditText.setText("");
        nameEditText.setText("");
        agEditText.setText("");
        LastnameEditText.setText("");
        imageView2.setImageBitmap(null);
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
            imageView2.setDrawingCacheEnabled(true);
            imageView2.buildDrawingCache();
            Bitmap bitmap = imageView2.getDrawingCache();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            byte[] data = outputStream.toByteArray();
           boolean res = db.updateData(id, name, age, lastname, data);
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




