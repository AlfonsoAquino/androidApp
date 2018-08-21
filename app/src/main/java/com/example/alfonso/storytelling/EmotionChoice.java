package com.example.alfonso.storytelling;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import java.util.Random;

public class EmotionChoice extends AppCompatActivity {

    private ImageView img1;
    private ImageView img2;
    private ArrayList<Vignetta> vignetta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emotion_choice);


        img1=(ImageView)findViewById(R.id.vignettaChoice1);
        img2=(ImageView)findViewById(R.id.vignettaChoice2);

        //##### Array with the img #####
        vignetta = getIntent().getParcelableArrayListExtra("scelte");

        //###### set result code ######
        setResult(0);

        //###### get first picture path0 ######
        Uri path0= checkUri(0);
        Uri path1= checkUri(1);//corretta


        //##### Generate random int #####
        Random random= new Random();
        int randCorr= random.nextInt();

        Log.d("", "onCreate: ------------------------------------------------------------> la prima posizione dell'array è sbagliata");
        //##### with even number the correct picture goes into img1 container otherwise in the img2 container
        if(randCorr%2==0) {
            Log.d("", "onCreate: ------------------------------------------------------------> numero pari quindi corretta in seconda posizione");

            //##### Load picture with Glide #####
            Glide.with(this).load(path0).into(img1);
            Glide.with(this).load(path1).into(img2);
            //##### img1 is the correct choise #####
            img1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showWrong();
                }
            });
            img2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showCorrect();
                }
            });

        }else {
            Log.d("", "onCreate: ------------------------------------------------------------> numero dispari quindi corretta in prima posizione");

            //###### Load picture with Glide #####
            Glide.with(this).load(path0).into(img2);
            Glide.with(this).load(path1).into(img1);
            //##### img1 is the wrong choise #####
            img1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showCorrect();
                }
            });
            img2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showWrong();
                }
            });
        }
    }

    public void showCorrect() {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        //Yes button clicked
                        Toast.makeText(getApplicationContext(),"Corretto!",Toast.LENGTH_LONG).show();
                        Intent intent=new Intent(getApplicationContext(), EmotionActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                        startActivity(intent);
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
    }


    public void showWrong(){
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        //Yes button clicked
                        Toast.makeText(getApplicationContext(),"Sbagliato!",Toast.LENGTH_LONG).show();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        break;
                }
            }
        };


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
    }

    public Uri checkUri(int temp){

        final String fileName = vignetta.get(temp).getIdAlbum()+""+vignetta.get(temp).getOrdine()+".jpg";
        String completePath = Environment.getExternalStorageDirectory()+"/"+Config.destVignette+"/"+fileName;

        File file = new File(completePath);
        Uri imageUri = Uri.fromFile(file);

        if(!file.exists()){
            Toast.makeText(getApplicationContext(),"immagine non trovata contatta l'assistenza",Toast.LENGTH_LONG).show();
            finish();
        }

        return imageUri;
    }

}
