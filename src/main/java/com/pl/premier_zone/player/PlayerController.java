package com.pl.premier_zone.player;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/player")
public class PlayerController {

    private final PlayerService playerService;

    @Autowired
    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping
    public List<Player> getPlayers(
        @RequestParam(value = "team_name", required = false) String team,
        @RequestParam(value = "player_name", required = false) String name,
        @RequestParam(value = "position", required = false) String position,
        @RequestParam(value = "nation", required = false) String nation,
        @RequestParam(value = "age", required = false) Integer age) {
            if (team != null && position != null) {
                return playerService.getPlayersByTeamAndPos(team, position);
            }
            else if(team != null) {
                return playerService.getPlayersFromTeam(name);
            }
            else if(position != null) {
                return playerService.getPlayersByPos(position);
            }
            else if (name != null) {
                return playerService.getPlayersByName(name);
            }
            else if(nation != null) {
                return playerService.getPlayersByNation(nation);
            }
            else if (age != null) {
                return playerService.getPlayersByAge(age);
            }
            else {
                return playerService.getAllPlayers();
            }
        }

    @PostMapping
    public ResponseEntity<Player> addPlayer(@RequestBody Player player) {
        Player createdPlayer = playerService.addPlayer(player);
        return new ResponseEntity<>(createdPlayer, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Player> updatePlayer(@RequestBody Player player) {
        Player resultPlayer = playerService.updatePlayer(player);
        if (resultPlayer != null) {
            return new ResponseEntity<>(resultPlayer, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{playerName}")
    public ResponseEntity<String> deletePlayer(@PathVariable String playerName) {
        playerService.deletePlayer(playerName);
        return new ResponseEntity<>("Player deleted successfully", HttpStatus.OK);
    }
}
