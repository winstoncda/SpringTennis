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
        Optional<PlayerEntity> existingPlayer = playerRepository.findOneByLastNameIgnoreCase(playerToSave.lastName());
        if (existingPlayer.isPresent()) {
            throw new PlayerAlreadyExistsException(playerToSave.lastName());
        };

        PlayerEntity playerEntity = new PlayerEntity(
                playerToSave.lastName(), 
            playerToSave.firstName(), 
            playerToSave.dateOfBirth(), 
            playerToSave.points(), 
            9999999);

        playerRepository.save(playerEntity);

        RankingCalculator rankingCalculator = new RankingCalculator(playerRepository.findAll());
        List<PlayerEntity> updatedPlayers = rankingCalculator.getNewPlayersRanking();
        playerRepository.saveAll(updatedPlayers);

        return getByLastName(playerToSave.lastName());
    }


    public Player update(PlayerToSave playerToSave) {
        return null;
    }

    public void delete(String lastName) {

    }

}
