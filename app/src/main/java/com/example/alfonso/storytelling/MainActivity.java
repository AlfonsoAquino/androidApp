package com.example.alfonso.storytelling;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private TextView txtAlbum;
    private TextView txtSequence;
    private TextView txtEmotion;
    private ArrayList<Album> albums;
    private ArrayList<Vignetta> vignette;
    private SQLiteHandler db;

    final String PREFS_NAME = "MyPrefsFile";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        db=new SQLiteHandler(getApplicationContext());
        vignette=new ArrayList<Vignetta>();
        albums=new ArrayList<>();
        txtAlbum = (TextView) findViewById(R.id.txtAlbum);
        txtSequence = (TextView) findViewById(R.id.txtSequence);
        txtEmotion = (TextView) findViewById(R.id.txtEmotion);



        //controllo per il primo accesso
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);

        if (settings.getBoolean("my_first_time", true)) {
            //the app is being launched for first time, do something
            Log.d("Comments", "First time");
             //first time task
            Intent intent= new Intent(getApplicationContext(),LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

            startActivity(intent);

             //record the fact that the app has been started at least once
            settings.edit().putBoolean("my_first_time", false).commit();
        }else{

           albums=db.getAlbumDetails();
           vignette=db.getVignettaDetails();
        }
//        creare bottone per scaricare nuovamente tutti gli album(quindi collegarsi direttamente alla pagina LoadingActivity)


        txtAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getApplicationContext(), ActivityAlbum.class);
                intent.putParcelableArrayListExtra("albums",albums);
                intent.putParcelableArrayListExtra("vignette",vignette);
                startActivity(intent);
            }
        });

        txtEmotion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getApplicationContext(), EmotionActivity.class);
                intent.putParcelableArrayListExtra("albums",albums);
                intent.putParcelableArrayListExtra("vignette",vignette);
                startActivity(intent);
            }
        });

        txtSequence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getApplicationContext(), SequenceActivity.class);
                intent.putParcelableArrayListExtra("albums",albums);
                intent.putParcelableArrayListExtra("vignette",vignette);
                startActivity(intent);
            }
        });
    }
}