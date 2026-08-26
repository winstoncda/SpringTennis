package com.dyma.tennis.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.dyma.tennis.Player;
import com.dyma.tennis.data.PlayerEntity;
import com.dyma.tennis.data.PlayerEntityList;
import com.dyma.tennis.data.PlayerRepository;

public class PlayerServiceTest {

    @Mock
    private PlayerRepository playerRepository;

    private PlayerService playerService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        playerService = new PlayerService(playerRepository);
    }

    @Test
    public void shouldReturnPlayersRanking() {
        Mockito.when(playerRepository.findAll()).thenReturn(PlayerEntityList.ALL);

        List<Player> players = playerService.getAllPlayers();

        Assertions.assertThat(players).extracting("firstName")
                .containsExactly("Nadal", "Djokovic", "Federer", "Murray");
    }

        @Test
        public void shouldRetrievePlayer() {
            // Given
            String playerToRetrieve = "nadal";
            Mockito.when(playerRepository.findOneByLastNameIgnoreCase(playerToRetrieve)).thenReturn(Optional.of(PlayerEntityList.RAFAEL_NADAL));

            // When
            Player retrievedPlayer = playerService.getByLastName(playerToRetrieve);

            // Then
            Assertions.assertThat(retrievedPlayer.firstName()).isEqualTo("Nadal");
        }

        @Test
        public void shouldFailToRetrievePlayer_WhenPlayerDoesNotExist() {
            // Given
            String unknownPlayer = "doe";
            Mockito.when(playerRepository.findOneByLastNameIgnoreCase(unknownPlayer)).thenReturn(Optional.empty());

            // When / Then
            Exception exception = assertThrows(PlayerNotFoundException.class, () -> {
                playerService.getByLastName(unknownPlayer);
            });
            Assertions.assertThat(exception.getMessage()).isEqualTo("Player with last name 'doe' not found.");
        }

}
