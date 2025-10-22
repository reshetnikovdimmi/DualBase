package ru.reddmix;


import ru.reddmix.repositories.ClickHouseRepositoryImpl;
import ru.reddmix.repositories.PostgresRepositoryImpl;

public class Main {
    public static void main(String[] args) throws Exception {
        // Создание экземпляров репозиториев
        PostgresRepositoryImpl postgresRepo = new PostgresRepositoryImpl();
        ClickHouseRepositoryImpl clickHouseRepo = new ClickHouseRepositoryImpl();

        // Инициализация менеджера асинхронных операций
        MultiThreadedFetcher fetcher = new MultiThreadedFetcher();
        fetcher.fetchAndPrintGames(postgresRepo, clickHouseRepo);

        // Запуск gRPC-сервера
        GRPCServer grpcServer = new GRPCServer(50051);
        grpcServer.start();
        grpcServer.blockUntilShutdown();
    }
}