package com.dyma.tennis.web;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dyma.tennis.Player;
import com.dyma.tennis.PlayerList;
import com.dyma.tennis.PlayerToSave;
import com.dyma.tennis.service.PlayerNotFoundException;
import com.dyma.tennis.service.PlayerService;
import com.dyma.tennis.service.RankingCalculator;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;

@Tag(name="Tennis Players API")
@RestController
public class PlayerController {

    @Autowired
    private  PlayerService playerService;

        @Operation(summary = "Get All Players", description = "Get all the players")
        @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Players List",
                content = {@Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = Player.class)))})
        })

        @GetMapping("/players")
        public List<Player> getAllPlayers() {
            return playerService.getAllPlayers();
        }




        @Operation(summary = "Get Player by Last Name", description = "Get a player by their last name")
        @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Player",
                content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Player.class))}),
        @ApiResponse(responseCode = "404", description = "Player not found",
                content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Player.class))}),
                
        })

        @GetMapping("/players/{lastName}")
        public Player getByLastName(@PathVariable String lastName) {
            return playerService.getByLastName(lastName);
        }




        @Operation(summary = "Create Player", description = "Create a new player")
        @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Created Player",
                content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = PlayerToSave.class))})
        })

        @PostMapping("/players")
        public Player createPlayer(@RequestBody @Valid PlayerToSave playerToSave) {
            return playerService.create(playerToSave);
        }


        @Operation(summary = "Updates a player", description = "Updates a player")
        @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Updated player",
                    content = {@Content(mediaType = "application/json",
                        schema = @Schema(implementation = PlayerToSave.class))}),
            @ApiResponse(responseCode = "404", description = "Player with specified last name was not found.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Error.class))})
        })

        @PutMapping("/players")
        public Player updatePlayer(@RequestBody @Valid PlayerToSave playerToSave) {
            return playerService.update(playerToSave);
        }



        @Operation(summary = "Delete a Player", description = "Delete an existing player")
        @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Deleted Player"),
            @ApiResponse(responseCode = "404", description = "Player with specified last name was not found.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Error.class))})
        })
        @DeleteMapping("/players/{lastName}")
        public void deletePlayerByLastName(@PathVariable("lastName") String lastName) {
            playerService.delete(lastName);
        }
}
