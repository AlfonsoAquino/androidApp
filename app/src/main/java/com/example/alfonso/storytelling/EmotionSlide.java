package com.example.alfonso.storytelling;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;

public class EmotionSlide extends AppCompatActivity {

    public ImageView imgV;
    public ImageView imgNext;
    public ImageView imgBack;
    private ArrayList<Vignetta> vignetteAlbum;
    int temp=0;
    int size;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emotion_slide);

        vignetteAlbum=getIntent().getParcelableArrayListExtra("vignetteAlbum");

        imgV = (ImageView)findViewById(R.id.vignettaSlide);
        imgNext = (ImageView)findViewById(R.id.rightArrow);
        imgBack = (ImageView)findViewById(R.id.leftArrow);

        //###### get first picture path ######
        Uri imageUri= checkUri(0);

        //###### Load picture with Glide ######
        Glide.with(this).load(imageUri).into(imgV);

        checkImgView();

        //###### The last two are the options ######
        size=vignetteAlbum.size() - 2;

        //###### next image button ######
        imgNext.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                temp++;
                if(temp==size){

                    Intent intent = new Intent(getApplicationContext(),EmotionChoice.class);

                    ArrayList<Vignetta> scelte= new ArrayList<>();
                    scelte.add(vignetteAlbum.get(size));
                    scelte.add(vignetteAlbum.get(size+1));
                    Log.i("---------->","vignette scleta---<>"+vignetteAlbum.get(size).getOrdine()+","+vignetteAlbum.get(size-1).getOrdine() );
                    intent.putParcelableArrayListExtra("scelte", scelte);
                    startActivityForResult(intent,0);
                }
                else if(temp<size) {
                    Glide.with(getApplicationContext())
                            .load(checkUri(temp))
                            .into(imgV);
                    checkImgView();
                }
            }
        });

        //###### back image button ######
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(temp>0) {
                    temp--;
                    Glide.with(getApplicationContext()).load(checkUri(temp)).into(imgV);
                    checkImgView();
                }
            }
        });

    }

    public Uri checkUri(int temp){

        String fileName = vignetteAlbum.get(temp).getIdAlbum()+""+vignetteAlbum.get(temp).getOrdine()+".jpg";
        String completePath = Environment.getExternalStorageDirectory()+"/"+Config.destVignette+"/"+fileName;

        File file = new File(completePath);
        Uri imageUri = Uri.fromFile(file);

        if(!file.exists()){
            Toast.makeText(getApplicationContext(),"immagine non trovata contatta l'assistenza",Toast.LENGTH_LONG).show();
            finish();
        }
        return imageUri;
    }

    public void checkImgView(){
        if(temp==0) {
            imgBack.setVisibility(View.INVISIBLE);
        }
        else if(temp==1){
            imgBack.setVisibility(View.VISIBLE);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==0){
            temp=size-1;
        }
    }
}