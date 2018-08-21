package com.example.alfonso.storytelling;

import java.io.File;
import java.util.List;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class CustomAdapter extends ArrayAdapter<Album> {
    private int resource;
    private LayoutInflater inflater;

    public CustomAdapter(Context context, int resourceId, List<Album> objects) {
        super(context, resourceId, objects);
        resource = resourceId;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View v, ViewGroup parent) {
        if (v == null) {
            Log.d("DEBUG","Inflating view");
            v = inflater.inflate(R.layout.list_layout, null);
        }

        Album a = getItem(position);

        Log.d("DEBUG","contact c="+a);

        ImageView cover;
        TextView nomeAlbum;

        cover = (ImageView) v.findViewById(R.id.cover);
        nomeAlbum= (TextView)v.findViewById(R.id.nomeAlbum);

        String fileName = String.valueOf(a.getId())+".jpg";
        String completePath = Environment.getExternalStorageDirectory()+"/"+Config.destAlbumPrev+"/"+fileName;

        File file = new File(completePath);
        Uri imageUri = Uri.fromFile(file);
        if(!file.exists()){
            return null;
        }

        Glide.with(getContext()).load(imageUri).into(cover);
        nomeAlbum.setText(a.getNome());

        return v;
    }

}

