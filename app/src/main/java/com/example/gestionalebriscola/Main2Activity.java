package com.example.gestionalebriscola;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Main2Activity extends AppCompatActivity {


    ListView lv;
    DatabaseReference dbreference;
    Button Btn_Ins;
    EditText EditTxT_Name, Num_Players;
    CustomArrayAdapter adapter;
    private FireBaseReadData fireBaseReadData;
    Data data;
    Preference preference;
    String nameTournament;
    int num_player;
    MediaPlayer song;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Show(true, false);
                    return true;
                case R.id.navigation_dashboard:
                    Show(false, true);

                    return true;

            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        lv = (ListView) findViewById(R.id.list);
        Btn_Ins = (Button) findViewById(R.id.Ins_Btn);
        EditTxT_Name = (EditText) findViewById(R.id.name);
        Num_Players = (EditText) findViewById(R.id.Num_players);
        lv.setVisibility(View.GONE);
        dbreference = FirebaseDatabase.getInstance().getReference();
        fireBaseReadData = new FireBaseReadData(dbreference);
        preference = new Preference();
        data = new Data();
        adapter = new CustomArrayAdapter(this, fireBaseReadData.retrive());
        lv.setAdapter(adapter);
        Audio();

    }

    public void Show(Boolean list, Boolean others) {

        if (list && !others) {
            Btn_Ins.setEnabled(true);
            Btn_Ins.setVisibility(View.VISIBLE);
            EditTxT_Name.setEnabled(true);
            EditTxT_Name.setVisibility(View.VISIBLE);
            Num_Players.setEnabled(true);
            Num_Players.setVisibility(View.VISIBLE);
            lv.setVisibility(View.GONE);
            lv.setEnabled(false);
        } else if (!list && others) {
            Btn_Ins.setEnabled(false);
            Btn_Ins.setVisibility(View.GONE);
            EditTxT_Name.setEnabled(false);
            EditTxT_Name.setVisibility(View.GONE);
            Num_Players.setEnabled(false);
            Num_Players.setVisibility(View.GONE);
            lv.setVisibility(View.VISIBLE);
            lv.setEnabled(true);
        }
    }

    public void SetNameTournament(View v) {

        if (!EditTxT_Name.getText().toString().equals(" ") && !Num_Players.getText().toString().equals("0")) {
            nameTournament = EditTxT_Name.getText().toString();
          //  fireBaseReadData.ControlInsertTournamentName(nameTournament);
            num_player = Integer.parseInt(String.valueOf(Num_Players.getText()));
            data.setTournamentName(nameTournament);
            data.setNumberOfPlayer(num_player);
            data.setPlayerAvailable(1);
            data.setNameCreator(preference.ReturnNameCreator(getApplicationContext()));
            data.setName_Player(preference.ReturnNameCreator(getApplicationContext()));


            if (nameTournament != null && nameTournament.length() > 0) {

                if (num_player > 11 || num_player < 1) {
                    Toast.makeText(this, "Numero compreso tra 2 e 10", Toast.LENGTH_LONG).show();

                } else {

                    if (fireBaseReadData.SaveData(data)) {
                        EditTxT_Name.setText("Inserisci nome torneo");

                        adapter = new CustomArrayAdapter(this, fireBaseReadData.retrive());
                        lv.setAdapter(adapter);
                    }

                  /*  if (preference.isResults()) {
                        Toast.makeText(this, "Questo nome esiste già", Toast.LENGTH_LONG).show();
                        preference.setResults(false);
                    } else {

                    }*/


                }
            }
        } else {
            Toast.makeText(getApplicationContext(), "Dati Inseriti in modo errato!", Toast.LENGTH_SHORT).show();
        }
    }

    public void settings(View view) {

        final Dialog dialog = new Dialog(Main2Activity.this);
        dialog.setContentView(R.layout.settings_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.show();


        FloatingActionButton close, Btn_close, Btn_play, Btn_pause;

        Btn_close = dialog.findViewById(R.id.Btn_logOut);
        close = dialog.findViewById(R.id.Btn_close);
        Btn_play = dialog.findViewById(R.id.Btn_play);
        Btn_pause = dialog.findViewById(R.id.Btn_pause);

        Btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent logOut = new Intent(Main2Activity.this, MainActivity.class);
                startActivity(logOut);
                finish();
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        Btn_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (song != null) {
                    song.seekTo(song.getCurrentPosition());
                    song.start();
                }
            }
        });

        Btn_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                song.pause();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        song.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (song != null) {
            song.seekTo(song.getCurrentPosition());
            song.start();
        }
    }

    public void Audio() {
        song = MediaPlayer.create(getApplicationContext(), R.raw.background);
        song.start();
    }
}