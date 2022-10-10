import java.net.*;
import java.io.*;

public class EchoServer {
    public static void main(String[] args){
        if (args.length != 1) {
            System.err.println("Usage: java EchoServer <port number>");
            System.exit(1);
        }

        int portNumber = Integer.parseInt(args[0]);

        try (
                ServerSocket serverSocket =
                        new ServerSocket(portNumber);
                Socket clientSocket = serverSocket.accept();
                PrintWriter out =
                        new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(clientSocket.getInputStream()));
        ) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                System.out.println("receive: " + inputLine);
                out.println(inputLine);
            }
        } catch (IOException e) {
            System.out.println("Connection terminate.");
            System.out.println(e.getMessage());
        }
    }
}