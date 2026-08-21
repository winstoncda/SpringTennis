package com.dyma.tennis.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.dyma.tennis.Player;
import com.dyma.tennis.PlayerToSave;
import com.dyma.tennis.Rank;

/**
 * Classe utilitaire pour calculer et recalculer les classements des joueurs.
 * Le classement est basé sur les points : plus de points = meilleur rang (rang 1 = le meilleur).
 */
public class RankingCalculator {

    // Liste des joueurs actuellement classés
    private final List<Player> currentPlayersRanking;

    // Le joueur à ajouter au classement (peut être null si on veut juste recalculer)
    private final PlayerToSave playerToRegister;

    /**
     * Constructeur utilisé pour ajouter un nouveau joueur au classement.
     *
     * @param currentPlayersRanking La liste actuelle des joueurs classés
     * @param playerToRegister Le nouveau joueur à ajouter
     */
    public RankingCalculator(List<Player> currentPlayersRanking, PlayerToSave playerToRegister) {
        this.currentPlayersRanking = currentPlayersRanking;
        this.playerToRegister = playerToRegister;
    }

    /**
     * Calcule le nouveau classement en ajoutant le nouveau joueur (si présent)
     * et en recalculant tous les rangs.
     *
     * @return La liste complète des joueurs avec leurs rangs mis à jour
     */
    public List<Player> getNewPlayersRanking() {
        // 1. Copier la liste actuelle pour ne pas la modifier directement
        List<Player> newRankingList = new ArrayList<>(currentPlayersRanking);

        // 2. Si un nouveau joueur doit être ajouté, on le crée et on l'ajoute à la liste
        if (playerToRegister != null) {
            // On crée un objet Player à partir du PlayerToSave
            // On lui donne un rang temporaire très élevé (999999999) qui sera recalculé après
            Player newPlayer = new Player(
                    playerToRegister.firstName(),
                    playerToRegister.lastName(),
                    playerToRegister.dateOfBirth(),
                    new Rank(999999999, playerToRegister.points())
            );
            newRankingList.add(newPlayer);
        }

        // 3. Recalculer tous les rangs en fonction des points
        return recalculateRanks(newRankingList);
    }

    /**
     * Méthode statique qui recalcule les rangs d'une liste de joueurs.
     * Utilisée pour la création, mise à jour et suppression de joueurs.
     *
     * Algorithme :
     * 1. Trier les joueurs par points décroissants (plus de points en premier)
     * 2. Attribuer le rang 1 au premier, 2 au deuxième, etc.
     * 3. Conserver les points d'origine de chaque joueur
     *
     * @param players La liste des joueurs à reclasser
     * @return La liste des joueurs avec leurs rangs mis à jour
     */
    public static List<Player> recalculateRanks(List<Player> players) {
        // Étape 1 : Trier par points décroissants
        // Le joueur avec le PLUS de points sera en premier (meilleur joueur)
        List<Player> sortedPlayers = players.stream()
                .sorted(Comparator.comparing((Player player) -> player.rank().points()).reversed())
                .toList();

        // Étape 2 : Créer une nouvelle liste avec les rangs mis à jour
        List<Player> updatedPlayers = new ArrayList<>();

        // Étape 3 : Parcourir la liste triée et attribuer les rangs
        for (int i = 0; i < sortedPlayers.size(); i++) {
            Player player = sortedPlayers.get(i);

            // Créer un nouveau Player avec le même contenu mais un rang mis à jour
            // Rang = position dans la liste + 1 (car les indices commencent à 0)
            // On conserve les mêmes points que le joueur avait
            Player updatedPlayer = new Player(
                    player.firstName(),
                    player.lastName(),
                    player.dateOfBirth(),
                    new Rank(i + 1, player.rank().points()) // Nouveau rang, mêmes points
            );
            updatedPlayers.add(updatedPlayer);
        }

        // Retourner la liste complète avec les rangs recalculés
        return updatedPlayers;
    }
}
