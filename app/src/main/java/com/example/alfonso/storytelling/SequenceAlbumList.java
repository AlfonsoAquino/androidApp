package com.example.alfonso.storytelling;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.List;

public class SequenceAlbumList extends ArrayAdapter <Album>{

    private int resource;
    private LayoutInflater inflater;

    public SequenceAlbumList(Context context, int resourceId, List<Album> objects) {
        super(context, resourceId, objects);
        resource = resourceId;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View v, ViewGroup parent) {
        if (v == null) {
            Log.d("DEBUG","Inflating view");
            v = inflater.inflate(R.layout.sequence_album_list, null);
        }

        Album a = getItem(position);

        Log.d("DEBUG","contact c="+a);

        ImageView cover;
        TextView nomeAlbum;

        cover = (ImageView) v.findViewById(R.id.coverSequenceList);
        nomeAlbum= (TextView)v.findViewById(R.id.nomeAlbumSequenceList);

        String fileName = String.valueOf(a.getId())+".jpg";
        String completePath = Environment.getExternalStorageDirectory()+"/"+Config.destAlbumPrev+"/"+fileName;

        File file = new File(completePath);
        Uri imageUri = Uri.fromFile(file);


        Glide.with(getContext()).load(imageUri).into(cover);
        nomeAlbum.setText(a.getNome());

        return v;
    }


}
