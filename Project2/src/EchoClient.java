import java.io.*;
import java.net.*;

import static java.lang.System.*;

public class EchoClient {
    public static void main(String[] args){

        if (args.length != 2) {
            err.println(
                    "Usage: java EchoClient <host name: csa1.bu.edu> <port number: 58000-58999>");
            exit(1);
        }

        String hostName = args[0];
        int portNumber = Integer.parseInt(args[1]);

        // 1. start connection: s rtt/tput <NUMBER OFPROBES><WS><MESSAGE SIZE><WS><SERVER DELAY>\n

        // 2. m <PROBE SEQUENCE NUMBER: start from 1><WS><PAYLOAD>\n

        // 3. Connection terminate: t\n

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
            System.out.println("Connect successfully with the server "+ hostName + ": " + portNumber);

            // Get parameters
            System.out.println("Please enter the measure type(rtt/tput): ");
            String type = stdIn.readLine();
            System.out.println("Please enter the number of probe messages to send(more than 10)");
            int probeNum = Integer.parseInt(stdIn.readLine());
            System.out.println("Please enter the message size: \n" +
                    "(rtt: 1, 100, 200, 400, 800, 1000)\n" +
                    "(tput: 1000, 2000, 4000, 8000, 16000, 32000)");
            int size = Integer.parseInt(stdIn.readLine());
            System.out.println("Please enter the expected server delay(ms)");
            int serverDelay = Integer.parseInt(stdIn.readLine());

            if(connectionPhase(type, probeNum, size, serverDelay, out, in)){
                long res = measurementPhase(probeNum, size, out, in);
                if(res != -1){
                    if(type.equals("rtt")){
                        System.out.println("The RTT of this test is " + res +"ms.");
                    }else{
                        long tput = (long) (size/1000 / (res * 0.001)); // kbp/s
                        System.out.println("The throughput of this test is " + tput +"kbps");
                    }
                }else{
                    stdIn.close();
                    out.close();
                    in.close();
                    err.println("Measurement phase fail.");
                    exit(1);
                }
                terminatePhase(out, in);
            }else{
                stdIn.close();
                out.close();
                in.close();
                err.println("Connection phase fail.");
                exit(1);
            }
            stdIn.close();
            out.close();
            in.close();
            exit(1);
        } catch (UnknownHostException e) {
            err.println("Don't know about host " + hostName);
            exit(1);
        } catch (IOException e) {
            err.println("Couldn't get I/O for the connection to " +
                    hostName);
            exit(1);
        }
    }

    // 1. s rtt/tput <NUMBER OFPROBES><WS><MESSAGE SIZE><WS><SERVER DELAY>\n
    private static boolean connectionPhase(String type, int probes, int size, int serverDelay,
                                           PrintWriter out, BufferedReader in){

        try {
            // '\n' will send a empty message to the server and make the program crash.
            String message1 = "s " + type + " " + probes + " " + size + " " + serverDelay;
            System.out.println("Phase 1 send: " + message1);
            out.println(message1);
            String received1 = in.readLine();
            System.out.println("Phase 1 received: " + received1);
            if(received1.equals("200 Ready")){
                return true;
            }
            return false;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    // 2. m <PROBE SEQUENCE NUMBER: start from 1><WS><PAYLOAD>\n
    private static long measurementPhase(int probeNum, int size, PrintWriter out, BufferedReader in){
        try {
            long mean = 0;
            String payload = "";
            for(int n = 0; n < size; n++){
                payload += "a";
            }
            String message2;
            for(int i = 1; i <= probeNum; i++){
                message2 = "m "+ i + " " + payload;
                System.out.println("Phase 2 (" + i + ") send: " + message2);
                long start = System.currentTimeMillis();
                out.println(message2);
                String receive = in.readLine();
                long end = System.currentTimeMillis();
                System.out.println("Phase 2 (" + i + ") receive: " + receive);
                if(receive.equals(message2)){
                    mean += end - start;
                }else{
                    return -1;
                }
            }
            return mean/probeNum;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }

    // 3. Connection terminate: t\n
    private static void terminatePhase(PrintWriter out, BufferedReader in){
        try {
            String message3 = "t\n";
            System.out.println("Phase 3 send: " + message3);
            out.println(message3);
            String receive = in.readLine();
            // No judge, just end the connection
            System.out.println("Phase 3 receive: " + receive);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
