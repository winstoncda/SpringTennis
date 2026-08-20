package com.dyma.tennis.service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.dyma.tennis.Player;
import com.dyma.tennis.PlayerList;
import com.dyma.tennis.PlayerToRegister;

@Service
public class PlayerService {

    public List<Player> getAllPlayers() {
        return PlayerList.ALL.stream()
                .sorted(Comparator.comparing(player -> player.rank().position()))
                .collect(Collectors.toList());
    }

    public Player getByLastName(String lastName) {
            return PlayerList.ALL.stream()
                    .filter(player -> player.lastName().equalsIgnoreCase(lastName))
                    .findFirst()
                    .orElseThrow(() ->new PlayerNotFoundException(lastName));
    }

    public Player createPlayer(PlayerToRegister playerToRegister) {
        RankingCalculator rankingCalculator = new RankingCalculator(PlayerList.ALL, playerToRegister);
        List<Player> updatedPlayers = rankingCalculator.getNewPlayersRanking();
        return updatedPlayers.stream()
                .filter(player -> player.lastName().equalsIgnoreCase(playerToRegister.lastName()))
                .findFirst().get();
    }

}
