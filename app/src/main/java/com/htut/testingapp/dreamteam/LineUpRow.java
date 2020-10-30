package com.htut.testingapp.dreamteam;

import java.util.List;

public class LineUpRow {

    private List<Player> players = null;

    public LineUpRow(List<Player> players) {
        this.players = players;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }
}