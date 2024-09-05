package com.pl.premier_zone.player;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;

@Component
public class PlayerService {

    private final PlayerRepository playerRepository;

    @Autowired
    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public List<Player> getAllPlayers() {
        return playerRepository.findAll();
    }

    public List<Player> getPlayersByAge(int age) {
        return playerRepository.findByAge(age);
    }

    public List<Player> getPlayersFromTeam(String teamName) {
        return playerRepository.findAll().stream()
                .filter(player -> teamName.equals(player.getTeam_name()))
                .collect(Collectors.toList());
    }

    public List<Player> getPlayersByName(String searchText) {
        return playerRepository.findPlayersByName(searchText);
    }
    
    public List<Player> getPlayersByPos(String searchText) {
        return playerRepository.findByPosition(searchText);
    }

    public List<Player> getPlayersByNation(String searchText) {
        return playerRepository.findAll().stream()
        .filter(player -> player.getNation().toLowerCase().contains(searchText.toLowerCase()))
        .collect(Collectors.toList());
    }

    public List<Player> getPlayersByTeamAndPos(String team, String position) {
        return playerRepository.findAll().stream()
                .filter(player -> team.equals(player.getTeam_name()) && position.equals(player.getPosition()))
                .collect(Collectors.toList());
    }
    
    public Player addPlayer(Player player) {
        playerRepository.save(player);
        return player;
    }

    public Player updatePlayer(Player player) {

        Optional<Player> existingPlayer = playerRepository.findByName(player.getName());

        if (existingPlayer.isPresent()) {
            Player playerToUpdate = existingPlayer.get();
            playerToUpdate.setName(player.getName());
            playerToUpdate.setTeam_name(player.getTeam_name());
            playerToUpdate.setPosition(player.getPosition());
            playerToUpdate.setNation(player.getNation());
            playerRepository.save(playerToUpdate);
            return playerToUpdate;
        }
        return null;
    }

    @Transactional
    public void deletePlayer(String playerName) {
        playerRepository.deleteByName(playerName);;
    }
}
