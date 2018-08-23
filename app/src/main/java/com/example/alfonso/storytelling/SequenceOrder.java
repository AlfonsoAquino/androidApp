package com.example.alfonso.storytelling;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.ScrollView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

import static android.content.ContentValues.TAG;

public class SequenceOrder extends AppCompatActivity {

    private ListView listView;
    private Button button;
    public ArrayList<Vignetta> vignetteAlbum =new ArrayList<>();
    public int temp;
    private SequenceCustomAdapter customAdapter;
    private ImageView image;
    private NumberPicker picker;
    public Integer[] order;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sequence_order);

        button=(Button) findViewById(R.id.sendOrder);
        listView = (ListView)findViewById(R.id.listOrder);

        vignetteAlbum=new ArrayList<>();
        vignetteAlbum=getIntent().getParcelableArrayListExtra("vignetteAlbum");


        //immagini disposte casualmente
        ArrayList<Integer> list = getRandomNonRepeatingIntegers(vignetteAlbum.size(), 0, vignetteAlbum.size());

        int max=vignetteAlbum.size();
        customAdapter = new SequenceCustomAdapter(this, R.layout.list_layout, new ArrayList<Vignetta>(),max);

        listView.setAdapter(customAdapter);

        for(int i=0;i<list.size();i++) {
            customAdapter.add(vignetteAlbum.get(list.get(i)));
        }



        //###### Listner to element for get position ######
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Show Toast
                temp=position;
                Log.i(TAG,"----------------------->"+temp);

            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean x=true;
                for(int i=0;i<vignetteAlbum.size();i++){
                    int ordine=customAdapter.getItem(i).getOrdine();
                    int ordineProv=customAdapter.getItem(i).getOrdineProvvisorio();
                    if(ordine!=ordineProv)
                        x=false;
                    Log.i(TAG,"sdadjsodaisduaosduoaduoaduoiaduoiaudoiaudoaudo.............>"+ordine+","+ordineProv);
                }
                if(x) {
                    AlertDialog.Builder alertadd = new AlertDialog.Builder(SequenceOrder.this);
                    LayoutInflater factory = LayoutInflater.from(SequenceOrder.this);
                    final View v = factory.inflate(R.layout.success_layout, null);
                    alertadd.setView(v);
                    alertadd.setNeutralButton("Here!", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dlg, int sumthin) {
                            Intent intent=new Intent(getApplicationContext(), SequenceAlbumList.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                            startActivity(intent);
                        }
                    });

                    alertadd.show();
                }else{
                    AlertDialog.Builder alertadd = new AlertDialog.Builder(SequenceOrder.this);
                    LayoutInflater factory = LayoutInflater.from(SequenceOrder.this);
                    final View v = factory.inflate(R.layout.failur_layout, null);
                    alertadd.setView(v);
                    alertadd.setNeutralButton("Here!", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dlg, int sumthin) {

                        }
                    });

                    alertadd.show();
                }
            }
        });
    }

    public Uri checkUri(int temp){

        final String fileName = vignetteAlbum.get(temp).getIdAlbum()+""+vignetteAlbum.get(temp).getOrdine()+".jpg";
        String completePath = Environment.getExternalStorageDirectory()+"/"+Config.destVignette+"/"+fileName;

        File file = new File(completePath);
        Uri imageUri = Uri.fromFile(file);

        if(!file.exists()){
            Toast.makeText(getApplicationContext(),"immagine non trovata contatta l'assistenza",Toast.LENGTH_LONG).show();
            finish();
        }

        return imageUri;
    }
    public static int getRandomInt(int min, int max) {
        Random random = new Random();

        return random.nextInt((max - min) ) + min;
    }

    public static ArrayList<Integer> getRandomNonRepeatingIntegers(int size, int min,
                                                                   int max) {
        ArrayList<Integer> numbers = new ArrayList<Integer>();

        while (numbers.size() < size) {
            int random = getRandomInt(min, max);

            if (!numbers.contains(random)) {
                numbers.add(random);
            }
        }

        return numbers;
    }

}
