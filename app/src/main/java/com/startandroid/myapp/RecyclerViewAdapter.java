package com.startandroid.myapp;


import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    Context ctx;
    Cursor mCursor;


    public RecyclerViewAdapter(Context context, Cursor cursor) {

        ctx = context;
        mCursor = cursor;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        mCursor.moveToPosition(position);
        byte[] image = mCursor.getBlob(4);
        Bitmap bmp = BitmapFactory.decodeByteArray(image, 0, image.length);
        holder.imageView.setImageBitmap(Bitmap.createScaledBitmap(bmp, 200, 200, false));
        holder.bindCursor(mCursor);

    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{
        CardView cv;
        TextView idPerson, name, age, lastname;
        ImageView imageView;
        public ViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            idPerson = (TextView)itemView.findViewById(R.id.idPerson);
            name = (TextView)itemView.findViewById(R.id.name);
            age = (TextView)itemView.findViewById(R.id.age);
            lastname = (TextView)itemView.findViewById(R.id.lastname);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
        }
        public void bindCursor(Cursor cursor){
            idPerson.setText(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.ID_COLUMN)));
            name.setText(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.NAME_COLUMN)));
            age.setText(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.AGE_COLUMN)));
            lastname.setText(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.LASTNAME_COLUMN)));
        }
    }



}
