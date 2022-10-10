import java.net.*;
import java.io.*;

public class EchoServer {
    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.err.println("Usage: java EchoServer <port number: 58000-58999>");
            System.exit(1);
        }

        int portNumber = Integer.parseInt(args[0]);

        // 1. Start connection: 1. 200 OK; 2. 404 ERROR: Invalid Connection Setup Message

        // 2. m <PROBE SEQUENCE NUMBER: start from 1><WS><PAYLOAD>\n

        // 3. Connection terminate: 1. 200 OK: Closing Connection; 2. 404 ERROR: Invalid Connection Termination Message

        try {
            ServerSocket serverSocket = new ServerSocket(portNumber);
            System.out.println("Server ready on port " + portNumber);
            // Use multi-threads to keep server activate
            while (true){
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client join");
                new MultiThread(clientSocket).start();
            }
        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port "
                    + portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
        }
    }
}