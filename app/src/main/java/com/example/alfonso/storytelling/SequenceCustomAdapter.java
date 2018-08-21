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

public class SequenceCustomAdapter extends ArrayAdapter<Vignetta>{
    private int resource;
    private LayoutInflater inflater;

    public SequenceCustomAdapter(Context context, int resourceId, List<Vignetta> objects) {
        super(context, resourceId, objects);
        resource = resourceId;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View v, ViewGroup parent) {
        if (v == null) {
            Log.d("DEBUG","Inflating view");
            v = inflater.inflate(R.layout.sequence_list_layout, null);
        }

        Vignetta a = getItem(position);

        Log.d("DEBUG","contact c="+a);

        ImageView cover;


        cover = (ImageView) v.findViewById(R.id.coverSequence);

        String fileName = a.getIdAlbum()+""+a.getOrdine()+".jpg";
        String completePath = Environment.getExternalStorageDirectory()+"/"+Config.destVignette+"/"+fileName;

        File file = new File(completePath);
        Uri imageUri = Uri.fromFile(file);
        if(!file.exists())
            return null;

        Glide.with(getContext()).load(imageUri).into(cover);


        return v;
    }

}
