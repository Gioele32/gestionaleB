package com.example.gestionalebriscola;

public class UpdateDataBase {

    String Name;
    String Key;
    String NamePlayers;
    Integer PlayerMax;
    Integer PlayerAvailable;

    UpdateDataBase(){

    }

    UpdateDataBase(String name){
      this.Name = name;
    }

    UpdateDataBase(String namePlayers, String NameTournament){
        this.NamePlayers = namePlayers;
        this.Name = NameTournament;
    }


    /*UpdateDataBase(String nameCreator, String keyDatabase, int Pl_Max, int Pl_Av, String namePlayers){

        this.Name = nameCreator;
        this.Key = keyDatabase;
        this.PlayerMax = Pl_Max;
        this.PlayerAvailable = Pl_Av;
        this.NamePlayers = namePlayers;
    } */


    UpdateDataBase(String name, String Players, int Pl_MAX, int Pl_Available,  String keyDatabase){
        this.Name = name;
        this.NamePlayers = Players;
        this.PlayerMax = Pl_MAX;
        this.PlayerAvailable = Pl_Available;
        this.Key = keyDatabase;
    }

}
