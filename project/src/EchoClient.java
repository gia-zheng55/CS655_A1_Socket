import java.io.*;
import java.net.*;

public class EchoClient {
    public static void main(String[] args){

        if (args.length != 2) {
            System.err.println(
                    "Usage: java EchoClient <(csa1/csa2/csa3).bu.edu> <port number:58000-58999>");
            System.exit(1);
        }

        String hostName = args[0];
        int portNumber = Integer.parseInt(args[1]);

        try (
                Socket echoSocket = new Socket(hostName, portNumber);
                PrintWriter out =
                        new PrintWriter(echoSocket.getOutputStream(), true);
                BufferedReader in =
                        new BufferedReader(
                                new InputStreamReader(echoSocket.getInputStream()));
                BufferedReader stdIn =
                        new BufferedReader(
                                new InputStreamReader(System.in))
        ) {
            System.out.println("Connects with server starts in " + hostName + "on the port " + portNumber);
            String userInput;
            while ((userInput = stdIn.readLine()) != null) {
                out.println(userInput);
                System.out.println("echo: " + in.readLine());
                System.exit(1);
            }
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " +
                    hostName);
            System.exit(1);
        }
    }
}
