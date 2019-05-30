package com.example.gestionalebriscola;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

public class Preference {

    private static String NamePlayers;
    private static int PlayerAvailable;
    private static int PlayerMax;
    private static boolean state = false;
    private static boolean results;


    Preference(){

    }


    void SetNameCreator(Context ctx, String Name) {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(ctx);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("name", Name);
        editor.commit();
    }

    String ReturnNameCreator(Context ctx) {

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(ctx);
        String name = settings.getString("name", "");
        return name;
    }

    String getNamePlayers() {
        return NamePlayers;
    }

    void setNamePlayers(String namePlayers) {
        NamePlayers = namePlayers;
    }

    int getPlayerAvailable() {
        return PlayerAvailable;
    }

    void setPlayerAvailable(Integer playerAvailable) {
        PlayerAvailable = playerAvailable;
    }


    int getPlayerMax() {
        return PlayerMax;
    }

    void setPlayerMax(Integer playerMax) {
        PlayerMax = playerMax;
    }

    boolean isState() {
        return state;
    }

    void setState(boolean State) {
        state = State;
    }

    void setResults(boolean Results) {

        Log.d("provaSet", Boolean.toString(Results));
        results = Results;
    }

    boolean isResults() {
        Log.d("provaGet", Boolean.toString(results));
        return results;
    }


}
