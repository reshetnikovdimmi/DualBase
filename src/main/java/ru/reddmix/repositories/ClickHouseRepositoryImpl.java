package ru.reddmix.repositories;

import ru.reddmix.DualBase.proto.DatabaseProtos;
import ru.reddmix.interfaces.ClickHouseRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClickHouseRepositoryImpl implements ClickHouseRepository {
    private final String url = "jdbc:clickhouse://localhost:8123/default";
    private final String user = "default";
    private final String pass = "";

    public List<DatabaseProtos.Game> fetchGamesFromClickHouse() {
        List<DatabaseProtos.Game> games = new ArrayList<>();
        try (Connection con = DriverManager.getConnection(url, user, pass)) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM games ORDER BY game_date DESC LIMIT 10");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                DatabaseProtos.Game game = DatabaseProtos.Game.newBuilder()
                        .setPlayerId(rs.getLong("player_id"))
                        .setBetAmount(rs.getBigDecimal("bet_amount").floatValue())
                        .setWinAmount(rs.getBigDecimal("win_amount").floatValue())
                        .build();
                games.add(game);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return games;
    }
}
