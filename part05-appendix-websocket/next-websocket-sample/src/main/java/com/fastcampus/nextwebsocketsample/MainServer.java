package com.fastcampus.nextwebsocketsample;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.CLStaticHttpHandler;
import org.glassfish.tyrus.server.Server;

public class MainServer {
    public static void main(String[] args) {
        HttpServer server = HttpServer.createSimpleServer("/web", 8080);
        server.getServerConfiguration().addHttpHandler(
                new CLStaticHttpHandler(MainServer.class.getClassLoader(), "/"),
                "/");

        Server websocketServer = new Server("localhost", 8025, "/", null, com.fastcampus.nextwebsocketsample.WebSocketServer.class);

        try {
            server.start();
            websocketServer.start();

            System.out.println("Server started. Press any key to stop...");
            System.in.read();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            server.shutdownNow();
            websocketServer.stop();
        }
    }
}
