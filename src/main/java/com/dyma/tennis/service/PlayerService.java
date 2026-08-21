package com.dyma.tennis.service;

import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;

import com.dyma.tennis.Player;
import com.dyma.tennis.PlayerList;
import com.dyma.tennis.PlayerToSave;

@Service
public class PlayerService {

    public List<Player> getAllPlayers() {
        return PlayerList.ALL.stream()
                .sorted(Comparator.comparing(player -> player.rank().position()))
                .toList();
    }



    public Player getByLastName(String lastName) {
            return PlayerList.ALL.stream()
                    .filter(player -> player.lastName().equalsIgnoreCase(lastName))
                    .findFirst()
                    .orElseThrow(() ->new PlayerNotFoundException(lastName));
    }


    public Player create(PlayerToSave playerToSave) {
            // Calculer le nouveau classement en ajoutant le joueur
            RankingCalculator rankingCalculator = new RankingCalculator(PlayerList.ALL, playerToSave);
            PlayerList.ALL = rankingCalculator.getNewPlayersRanking();

            // Retourner le joueur créé
            return PlayerList.ALL.stream()
                .filter(player -> player.lastName().equals(playerToSave.lastName()))
                .findFirst()
                .orElseThrow();
    }


    public Player update(PlayerToSave playerToSave) {
            // Vérifier que le joueur existe
            getByLastName(playerToSave.lastName());

            // Retirer l'ancien joueur de la liste
            List<Player> playersWithoutPlayerToUpdate = PlayerList.ALL.stream()
                .filter(player -> !player.lastName().equals(playerToSave.lastName()))
                .toList();

            // Recalculer le classement avec les nouvelles données du joueur
            RankingCalculator rankingCalculator = new RankingCalculator(playersWithoutPlayerToUpdate, playerToSave);
            PlayerList.ALL = rankingCalculator.getNewPlayersRanking();

            // Retourner le joueur mis à jour
            return PlayerList.ALL.stream()
                .filter(player -> player.lastName().equals(playerToSave.lastName()))
                .findFirst()
                .orElseThrow();
    }

    public void delete(String lastName) {
            // Vérifier que le joueur existe (lance une exception si non trouvé)
            Player playerToDelete = getByLastName(lastName);

            // Filtrer le joueur à supprimer de la liste
            List<Player> playersWithoutDeleted = PlayerList.ALL.stream()
                .filter(player -> !player.lastName().equals(playerToDelete.lastName()))
                .toList();

            // Recalculer les rangs avec RankingCalculator
            PlayerList.ALL = RankingCalculator.recalculateRanks(playersWithoutDeleted);
    }

}
