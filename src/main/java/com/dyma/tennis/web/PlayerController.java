package com.dyma.tennis.web;

import java.util.Collections;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dyma.tennis.HealthCheck;
import com.dyma.tennis.Player;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name="Tennis Players API")
@RestController
public class PlayerController {

        @Operation(summary = "Get All Players", description = "Get all the players")
        @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Players List",
                content = {@Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = Player.class)))})
        })
        @GetMapping("/players")
        public List<Player> getAllPlayers() {
            return Collections.emptyList();
        }

        @Operation(summary = "Get Player by Last Name", description = "Get a player by their last name")
        @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Player",
                content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Player.class))})
        })
        @GetMapping("/players/{lastName}")
        public Player getByLastName(@PathVariable("lastName") String lastName) {
            return null;
        }

        @Operation(summary = "Create Player", description = "Create a new player")
        @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Created Player",
                content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Player.class))})
        })
        @PostMapping("/players")
        public Player createPlayer(@RequestBody Player player) {
            return player;
        }

        @Operation(summary = "Update a Player", description = "Update an existing player")
        @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Updated Player",
                content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Player.class))})
        })
        @PutMapping("/players")
        public Player updatePlayer(@RequestBody Player player) {
            return player;
        }

        @Operation(summary = "Delete a Player", description = "Delete an existing player")
        @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Deleted Player")
        })
        @DeleteMapping("/players/{lastName}")
        public void deletePlayerByLastName(@PathVariable("lastName") String lastName) {
            // Implementation for deleting a player by last name
        }
}
