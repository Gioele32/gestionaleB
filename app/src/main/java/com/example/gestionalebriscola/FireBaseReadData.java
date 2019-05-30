package com.example.gestionalebriscola;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class FireBaseReadData {

    private DatabaseReference databaseReference;
    private Boolean IsSaved;
    private ArrayList<Data> data = new ArrayList<>();

    private Preference preference = new Preference();
    private ArrayList<UpdateDataBase> ReadFromDataBase = new ArrayList<>();
    private static final String Name_player = "name_Player";
    private static final String player_Available = "playerAvailable";
    private static final String player_Max = "numberOfPlayer";
    private static final String Tournament = "Torneo";
    private static final String Creator_Tournament = "nameCreator";
    private static final String name_Tournament = "tournamentName";
    private boolean state = false;


    FireBaseReadData(DatabaseReference db) {
        this.databaseReference = db;
    }

    boolean SaveData(Data data) {
        if (data == null) {
            IsSaved = false;
        } else {
            try {
                databaseReference.child(Tournament).push().setValue(data);
                IsSaved = true;
            } catch (DatabaseException e) {
                e.printStackTrace();
                IsSaved = false;
            }
        }

        return IsSaved;
    }




    void readFromDatabase(final int position, final Context c) {
        databaseReference.child(Tournament).addListenerForSingleValueEvent(new ValueEventListener() {
            int i = 0;

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot DS : dataSnapshot.getChildren()) {

                    if (i <= position) {
                        ReadFromDataBase.add(i, new UpdateDataBase(DS.child(Creator_Tournament).getValue(String.class), DS.child(Name_player).getValue(String.class), DS.child(player_Max).getValue(Integer.class), DS.child(player_Available).getValue(Integer.class), DS.getRef().getKey()));
                        i++;
                    }
                }

                preference.setNamePlayers(ReadFromDataBase.get(position).NamePlayers);
                preference.setPlayerAvailable(ReadFromDataBase.get(position).PlayerAvailable);
                preference.setPlayerMax(ReadFromDataBase.get(position).PlayerMax);
                if (ReadFromDataBase.get(position).Name.equals(preference.ReturnNameCreator(c))) {
                    preference.setState(true);
                } else if (ReadFromDataBase.get(position).Name.equals(preference.ReturnNameCreator(c)) && ReadFromDataBase.get(position).NamePlayers.equals(preference.ReturnNameCreator(c))) {
                    preference.setState(true);
                } else {
                    SetRegistration(ReadFromDataBase, position, c);
                    preference.setState(false);
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }


    void DeleteItem(final int position, final Context c) {

        databaseReference.child(Tournament).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (final DataSnapshot DS : dataSnapshot.getChildren()) {

                    for (int i = 0; i <= position; i++) {
                        ReadFromDataBase.add(i, new UpdateDataBase(DS.child(Creator_Tournament).getValue(String.class), DS.child(name_Tournament).getValue(String.class)));
                    }
                }

                if (ReadFromDataBase.get(position).NamePlayers.equals(preference.ReturnNameCreator(c))) {
                    databaseReference = FirebaseDatabase.getInstance().getReference();
                    Query applesQuery = databaseReference.child(Tournament).orderByChild(name_Tournament).equalTo(ReadFromDataBase.get(position).Name);
                    applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot appleSnapshot : dataSnapshot.getChildren()) {
                                appleSnapshot.getRef().removeValue();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                } else {
                    Toast.makeText(c, "Non sei il creatore del torneo", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

   /* void ControlInsertTournamentName(final String Name) {

        databaseReference.child(Tournament).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int i = 0;
                for (DataSnapshot DS : dataSnapshot.getChildren()) {

                        if (state) {
                            Log.d("provaSettaggioFunzione", Boolean.toString(state));
                            preference.setResults(true);
                            break;

                        } else {
                            ReadFromDataBase.add(i, new UpdateDataBase(DS.child(name_Tournament).getValue(String.class)));
                            if (ReadFromDataBase.get(i).Name.equals(Name)) {
                                state = true;
                            }
                            i++;
                        }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    } */

    void SetRegistration(ArrayList<UpdateDataBase> array, int position, final Context c) {
        final Map<String, Object> childUpdates = new HashMap<>();

        if (array.get(position).PlayerAvailable < array.get(position).PlayerMax) {
            childUpdates.put(Name_player, preference.ReturnNameCreator(c) + " " + array.get(position).Name);
            array.get(position).PlayerAvailable++;
            childUpdates.put(player_Available, array.get(position).PlayerAvailable);
            databaseReference.child(Tournament).child(array.get(position).Key).updateChildren(childUpdates);
        } else {
            Toast.makeText(c, "Numero massimo di Iscrizioni Raggiunte", Toast.LENGTH_SHORT).show();
        }

    }

    private void FillArray(DataSnapshot dataS) {

        data.clear();
        for (DataSnapshot ds : dataS.getChildren()) {
            Data DATA = ds.getValue(Data.class);
            data.add(DATA);
        }

    }


    ArrayList<Data> retrive() {

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot Ds, @Nullable String s) {
                FillArray(Ds);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                FillArray(dataSnapshot);
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return data;
    }


}

