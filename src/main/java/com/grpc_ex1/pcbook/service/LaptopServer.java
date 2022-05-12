package com.grpc_ex1.pcbook.service;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class LaptopServer {
    // creates logger scope to the current class
    private static final Logger logger = Logger.getLogger(LaptopServer.class.getName());

    private final int port;
    private final Server server;

    public LaptopServer(int port, LaptopStore store) {
        // calls serverBuilder and assigns port and server in super
        this(ServerBuilder.forPort(port), port,store);

    }

    public LaptopServer(ServerBuilder serverBuilder, int port, LaptopStore store) {
        this.port = port;
        LaptopService laptopService = new LaptopService(store);
        server = serverBuilder.addService(laptopService).build();
    }

    public void start() throws IOException {
        server.start();
        logger.info("server started on port " + port);

        /*
        Lamda runnable
        Generate a new thread and attempts to shut down the program gracefully by calling the stop method
         */
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.err.println("shut down gRPC serve because JVM shuts down");
            try {
                LaptopServer.this.stop();
            } catch (InterruptedException e) {
                e.printStackTrace(System.err);
            }
            System.err.println("server shut down");
        }));
        // class based runnable on new thread
//        Runtime.getRuntime().addShutdownHook(new Thread() {
//            @Override
//            public void run() {
//                System.err.println("shut down gRPC serve because JVM shuts down");
//                try {
//                    LaptopServer.this.stop();
//                } catch (InterruptedException e) {
//                    e.printStackTrace(System.err);
//                }
//                System.err.println("server shut down");
//            }
//        });
    }

    public void stop() throws InterruptedException {
        if (server != null) {
            // shutdown server with await to protect
            server.shutdown().awaitTermination(30, TimeUnit.SECONDS);
        }
    }

    /**
     * Blocks the main thread until the server shuts down because the GRPC server uses daemon threads
     * @throws InterruptedException
     */
    private void blockUntilShutdown() throws InterruptedException {
        if (server != null)
            server.awaitTermination();
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        InMemoryLaptopStore store = new InMemoryLaptopStore();
        LaptopServer server = new LaptopServer(8080, store);
        server.start();
        server.blockUntilShutdown();
    }
}
