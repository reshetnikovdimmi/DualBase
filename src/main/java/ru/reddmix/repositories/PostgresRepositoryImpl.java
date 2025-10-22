package ru.reddmix.repositories;

import ru.reddmix.DualBase.proto.DatabaseProtos;
import ru.reddmix.interfaces.PostgresRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PostgresRepositoryImpl implements PostgresRepository {
    private final String url = "jdbc:postgresql://localhost:5432/poker_game_db";
    private final String user = "poker_user";
    private final String pass = "secret";

    public List<DatabaseProtos.Game> fetchGamesFromPostgres() {
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
