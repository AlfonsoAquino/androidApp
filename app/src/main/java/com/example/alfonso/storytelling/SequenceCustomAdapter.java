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
import android.widget.NumberPicker;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.List;

public class SequenceCustomAdapter extends ArrayAdapter<Vignetta>{
    private int resource;
    private LayoutInflater inflater;
    private int max;
    public SequenceCustomAdapter(Context context, int resourceId, List<Vignetta> objects, int max) {
        super(context, resourceId, objects);
        resource = resourceId;
        inflater = LayoutInflater.from(context);
        this.max=max;
    }

    @Override
    public View getView(int position, View v, ViewGroup parent) {
        if (v == null) {
            Log.d("DEBUG","Inflating view");
            v = inflater.inflate(R.layout.sequence_list_layout, null);
        }

        final Vignetta a = getItem(position);

        Log.d("DEBUG","1contact c="+a);

        ImageView cover;
        final NumberPicker picker;

        cover = (ImageView) v.findViewById(R.id.coverSequence);
        picker=(NumberPicker) v.findViewById(R.id.numberPicker);

        picker.setMinValue(1);
        picker.setMaxValue(max);

        String fileName = a.getIdAlbum()+""+a.getOrdine()+".jpg";
        String completePath = Environment.getExternalStorageDirectory()+"/"+Config.destVignette+"/"+fileName;

        File file = new File(completePath);
        Uri imageUri = Uri.fromFile(file);

        if(!file.exists())
            return null;

        Glide.with(getContext()).load(imageUri).into(cover);

        picker.setTag(position);
        picker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                a.setOrdineProvvisorio(picker.getValue());
            }
        });

        return v;
    }

}
