package com.example.alfonso.storytelling;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class SequenceActivity extends AppCompatActivity {

    public ListView listView;
    public Button confirm;
    public ArrayList<Album> albums =new ArrayList<Album>();
    public ArrayList<Vignetta> vignette =new ArrayList<Vignetta>();
    public ArrayList<Vignetta> vignetteAlbum =new ArrayList<Vignetta>();
    public ArrayList<Album> albumTipo0;
    public int temp;
    private SQLiteHandler db;


    CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sequence);

        db=new SQLiteHandler(getApplicationContext());

        listView = (ListView)findViewById(R.id.albumList);
        confirm=(Button)findViewById(R.id.confirm);

        albums=new ArrayList<>();
        vignette=new ArrayList<>();
        vignetteAlbum=new ArrayList<>();

        albums=db.getAlbumDetails();
        vignette=db.getVignettaDetails();

        albumTipo0= new ArrayList<>();
        customAdapter = new CustomAdapter(this, R.layout.list_layout, new ArrayList<Album>());

        listView.setAdapter(customAdapter);

        for(Album a:albums){
            //seleziono solo gli album destinati allo storyTelling
            if(a.getTipo()==2)
                albumTipo0.add(new Album(a.getId(),a.getNome(),a.getPath(),a.getTipo()));
        }
        for(Album b: albumTipo0)
            customAdapter.add(b);

        //###### Listner to element for get position ######
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Show Toast
                temp=position;
                Log.i(TAG,"----------------------->"+temp);
                Log.i(TAG,"----------------------->"+albumTipo0.get(temp).getId());

            }
        });

    }


    public void startAlbum(View v){

        int idAlbum= albumTipo0.get(temp).getId();
        vignetteAlbum=new ArrayList<>();
        for (Vignetta b:vignette)
            if (b.getIdAlbum()==idAlbum) {
                vignetteAlbum.add(new Vignetta(b.getIdAlbum(), b.getPath(), b.getOrdine()));
                Log.i(TAG,".......................................>"+vignetteAlbum.size());
            }
        if(vignetteAlbum.size()>3) {
            Intent intent = new Intent(getApplicationContext(), SequenceOrder.class);
            intent.putParcelableArrayListExtra("vignetteAlbum", vignetteAlbum);

            startActivity(intent);
        }else {
            Toast.makeText(getApplicationContext(),"L'album selezionato non contiene vignette",Toast.LENGTH_LONG).show();
        }
    }


}
