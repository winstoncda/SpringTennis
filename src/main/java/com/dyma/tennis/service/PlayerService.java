package com.dyma.tennis.service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dyma.tennis.Player;
import com.dyma.tennis.PlayerList;
import com.dyma.tennis.PlayerToSave;
import com.dyma.tennis.Rank;
import com.dyma.tennis.data.PlayerEntity;
import com.dyma.tennis.data.PlayerRepository;

@Service
public class PlayerService {

    @Autowired
    private PlayerRepository playerRepository;

    public List<Player> getAllPlayers() {
        return playerRepository.findAll().stream().map(playerEntity -> new Player(playerEntity.getFirstName(), playerEntity.getLastName(), playerEntity.getBirthDate(), new Rank(playerEntity.getRank(), playerEntity.getPoints())))
                .sorted(Comparator.comparing(player -> player.rank().position()))
                .toList();
    }



    public Player getByLastName(String lastName) {
        Optional<PlayerEntity> player = playerRepository.findOneByLastNameIgnoreCase(lastName);
        if (player.isEmpty()) {
            throw new PlayerNotFoundException(lastName);
        }
        PlayerEntity playerEntity = player.get();
        return new Player(playerEntity.getFirstName(), playerEntity.getLastName(), playerEntity.getBirthDate(), new Rank(playerEntity.getRank(), playerEntity.getPoints()));

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
