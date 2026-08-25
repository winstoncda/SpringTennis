package com.dyma.tennis.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.dyma.tennis.Player;
import com.dyma.tennis.PlayerToSave;
import com.dyma.tennis.Rank;
import com.dyma.tennis.data.PlayerEntity;

/**
 * Classe utilitaire pour calculer et recalculer les classements des joueurs.
 * Le classement est basé sur les points : plus de points = meilleur rang (rang 1 = le meilleur).
 */
public class RankingCalculator {

    // Liste des joueurs actuellement classés
    private final List<PlayerEntity> currentPlayersRanking;


    public RankingCalculator(List<PlayerEntity> currentPlayersRanking) {
        this.currentPlayersRanking = currentPlayersRanking;
    }

    public List<PlayerEntity> getNewPlayersRanking() {
        currentPlayersRanking.sort((player1, player2) -> Integer.compare(player2.getPoints(), player1.getPoints()));

        List<PlayerEntity> updatedPlayers = new ArrayList<>();
        for (int i = 0; i < currentPlayersRanking.size(); i++) {
            PlayerEntity updatedPlayer = currentPlayersRanking.get(i);
            updatedPlayer.setRank(i + 1); // Le rang est basé sur l'index + 1
            updatedPlayers.add(updatedPlayer);
        }
        return updatedPlayers;
    }

}
