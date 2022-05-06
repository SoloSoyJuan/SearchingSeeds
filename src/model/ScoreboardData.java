package model;

import com.google.gson.Gson;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class ScoreboardData {
    private ArrayList<Player> players;

    public ScoreboardData() {
        players = new ArrayList<>();
    }

    public void addPlayer(Player player) {
        int playerIndex = binarySearchPlayer(player);

        if (playerIndex != -1) {
            Player foundPlayer = players.get(playerIndex);
            foundPlayer.setScore(foundPlayer.getScore() + player.getScore());
        } else {
            players.add(player);
        }

        updateScoreboard();
    }

    /**
     * Searches the position of a player that may have already played by means
     * of a binary search. It orders the list taking into account the lexicographical factor
     * of the player names.
     * @param player the player to find
     * @return the index of the found player (-1 if not found)
     */
    public int binarySearchPlayer(Player player) {
        Collections.sort(players, (A, B) -> {
            return A.getPName().compareTo(B.getPName());
        });

        int right = players.size() - 1;
        int left = 0;

        while(right - left >= 0) {
            int mid = (right + left) / 2;

            if(players.get(mid).getPName().compareTo(player.getPName()) > 0) {
                right = mid - 1;
            } else if (players.get(mid).getPName().compareTo(player.getPName()) < 0) {
                left = mid + 1;
            } else if(players.get(mid).getPName().compareTo(player.getPName()) == 0) {
                return mid;
            }
        }

        return -1;
    }

    public void orderPlayers() {
        Collections.sort(players);
    }

    // Prints the top 5
    public String print() {
        String scoreBoard = "Top 5\n\n";

        for (int i = players.size() - 1, j = 0; j < 5 && i >= 0; i--, j++) {
            scoreBoard += players.get(i).getPName() + " | " + players.get(i).getScore() + "\n";
        }

        return scoreBoard;
    }
    
    public void updateScoreboard() {
        orderPlayers();
        saveTextFile();
        saveJSON();
    }
    
    public void saveTextFile() {
        try {
            File file = new File("data/scoreBoard.txt");
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));

            bw.write(print());
            bw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveJSON() {
        try {
            Gson gson = new Gson();
            String json = gson.toJson(this);

            File file = new File("data/scoreData.json");
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(json.getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadJSON() {
        try {
            FileInputStream fis = new FileInputStream(new File("data/scoreData.json"));
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));

            String json = "";
            String line;

            while((line = reader.readLine()) != null) {
                json += line;
            }

            Gson gson = new Gson();
            ScoreboardData data = gson.fromJson(json, ScoreboardData.class);

            if (data != null) {
                players = data.players;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
