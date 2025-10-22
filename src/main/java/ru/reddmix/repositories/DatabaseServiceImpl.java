package ru.reddmix.repositories;

import io.grpc.stub.StreamObserver;
import ru.reddmix.DualBase.proto.DatabaseProtos;
import ru.reddmix.DualBase.proto.DatabaseServiceGrpc;


import java.util.List;
import java.util.stream.Collectors;



public class DatabaseServiceImpl extends DatabaseServiceGrpc.DatabaseServiceImplBase {

    @Override
    public void fetchAllGames(DatabaseProtos.FetchRequest request, StreamObserver<DatabaseProtos.FetchReply> responseObserver) {
        PostgresRepositoryImpl postgresRepo = new PostgresRepositoryImpl();
        ClickHouseRepositoryImpl clickHouseRepo = new ClickHouseRepositoryImpl();

        List<DatabaseProtos.Game> games;
        if (request.getUsePostgres()) {
            games = postgresRepo.fetchGamesFromPostgres();
        } else {
            games = clickHouseRepo.fetchGamesFromClickHouse();
        }

        DatabaseProtos.FetchReply reply = DatabaseProtos.FetchReply.newBuilder()
                .addAllGames(games.stream()
                        .map(g -> DatabaseProtos.Game.newBuilder()
                                .setPlayerId(g.getPlayerId())
                                .setBetAmount(g.getBetAmount())
                                .setWinAmount(g.getWinAmount())
                                .build())
                        .collect(Collectors.toList()))
                .build();

        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }
}
