package com.example.alfonso.storytelling;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class SequenceOrder extends AppCompatActivity {

    private ListView listView;
    private Button button;
    public ArrayList<Album> albums =new ArrayList<>();
    public ArrayList<Vignetta> vignette =new ArrayList<>();
    public ArrayList<Vignetta> vignetteAlbum =new ArrayList<>();
    public ArrayList<Album> albumTipo0;
    public int temp;
    private SQLiteHandler db;
    private SequenceCustomAdapter customAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sequence_order);

        db=new SQLiteHandler(getApplicationContext());

        listView = (ListView)findViewById(R.id.listOrder);
        button=(Button)findViewById(R.id.sendOrder);

        albums=new ArrayList<>();
        vignetteAlbum=new ArrayList<>();
        vignette=new ArrayList<>();

        albums=db.getAlbumDetails();
        vignette=db.getVignettaDetails();

        albumTipo0= new ArrayList<>();
        customAdapter = new SequenceCustomAdapter(this, R.layout.list_layout, new ArrayList<Vignetta>());

        listView.setAdapter(customAdapter);

        vignetteAlbum=getIntent().getParcelableArrayListExtra("vignetteAlbum");

        for(Vignetta v:vignetteAlbum)
            customAdapter.add(v);

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
}
