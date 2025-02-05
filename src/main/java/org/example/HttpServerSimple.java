package org.example;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.HashMap;

public class HttpServerSimple {
    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/healthcheck", new HealthCheckHandler());
        server.setExecutor(null);

        System.out.println("Starting HTTP Server");
        // start port 8080
        server.start();
    }

    static class HealthCheckHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            HashMap response = new HashMap();
            response.put("status", "200");
            response.put("message", "Up");

            exchange.getResponseHeaders().add("Content-Type", "application/json");
            exchange.sendResponseHeaders(200, FetchResult.Json(response).getBytes().length);

            OutputStream os = exchange.getResponseBody();
            os.write(FetchResult.Json(response).getBytes());
            os.close();
        }
    }
}
