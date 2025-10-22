package ru.reddmix;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import ru.reddmix.repositories.DatabaseServiceImpl;

public class GRPCServer {

    private final int port;
    private Server server;

    public GRPCServer(int port) {
        this.port = port;
    }

    public void start() throws Exception {
        server = ServerBuilder.forPort(port)
                .addService(new DatabaseServiceImpl())
                .build()
                .start();
        System.out.println("GRPC Server started at port:" + port);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Stopping gRPC server");
            GRPCServer.this.stop();
            System.out.println("gRPC server stopped");
        }));
    }

    public void stop() {
        if (server != null) {
            server.shutdown();
        }
    }

    public void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    public static void main(String[] args) throws Exception {
        final GRPCServer server = new GRPCServer(50051);
        server.start();
        server.blockUntilShutdown();
    }
}
