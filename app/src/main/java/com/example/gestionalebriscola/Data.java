package com.example.gestionalebriscola;

public class Data {
    String tournamentName;
    String nameCreator;
    int numberOfPlayer;
    int playerAvailable;
    String name_Player;

    Data(){

    }

    /***********************************************/
    private int getPlayerAvailable() {
        return playerAvailable;
    }

    void setPlayerAvailable(int PlayerAvailable) {
        playerAvailable = PlayerAvailable;
    }
    /***********************************************/
    public int getNumberOfPlayer() {
        return numberOfPlayer;
    }

    void setNumberOfPlayer(int numbPlayer) {
        numberOfPlayer = numbPlayer;
    }
    /***********************************************/
    public String getName_Player() {
        return name_Player;
    }

    void setName_Player(String name_player) {
        name_Player = name_player;
    }
    /***********************************************/
    String getTournamentName() {
        return tournamentName;
    }

    void setTournamentName(String NameTournament) {
        tournamentName = NameTournament;
    }
    /***********************************************/
    String getNameCreator() {
        return nameCreator;
    }

    void setNameCreator(String Name) {
        nameCreator = Name;
    }

}
