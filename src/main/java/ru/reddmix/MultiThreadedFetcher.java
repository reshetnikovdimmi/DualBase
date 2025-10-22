package ru.reddmix;

import ru.reddmix.DualBase.proto.DatabaseProtos;
import ru.reddmix.repositories.ClickHouseRepositoryImpl;
import ru.reddmix.repositories.PostgresRepositoryImpl;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MultiThreadedFetcher {
    private final ExecutorService service = Executors.newFixedThreadPool(2);

    public void fetchAndPrintGames(PostgresRepositoryImpl postgresRepo, ClickHouseRepositoryImpl clickHouseRepo) {
        CompletableFuture<List<DatabaseProtos.Game>> postgresFuture =
                CompletableFuture.supplyAsync(() -> postgresRepo.fetchGamesFromPostgres(), service);

        CompletableFuture<List<DatabaseProtos.Game>> clickHouseFuture =
                CompletableFuture.supplyAsync(() -> clickHouseRepo.fetchGamesFromClickHouse(), service);

        CompletableFuture.allOf(postgresFuture, clickHouseFuture)
                .thenAccept(v -> {
                    printGames(postgresFuture.join(), "PostgreSQL Games:");
                    printGames(clickHouseFuture.join(), "ClickHouse Games:");
                })
                .join();

        service.shutdown();
        try {
            service.awaitTermination(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void printGames(List<DatabaseProtos.Game> games, String title) {
        System.out.println(title);
        games.forEach(g -> System.out.printf("%s Bet:%f Win:%f%n", g.getPlayerId(), g.getBetAmount(), g.getWinAmount()));
    }
}
