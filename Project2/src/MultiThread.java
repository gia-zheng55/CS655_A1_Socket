import java.io.*;
import java.net.Socket;

public class MultiThread extends Thread{

    private Socket socket;
    boolean terminate = false;
    private int serverDelay;
    private int probeNum;
    private int currentNum = 1;
    private int messageSize;

    public MultiThread(Socket socket){
        this.socket = socket;
    }

    private boolean isInteger(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // 1. Start connection: 1. 200 OK; 2. 404 ERROR: Invalid Connection Setup Message
    private void checkPhase1(String[] messageArr, PrintWriter out){
        if(messageArr.length != 5 ||
                (!messageArr[1].equals("rtt") && !messageArr[1].equals("tput")) ||
                !isInteger(messageArr[2]) ||
                !isInteger(messageArr[3]) ||
                !isInteger(messageArr[4])){
            out.println("404 ERROR: Invalid Connection Setup Message");
            terminate = true;
        }else{
            this.probeNum = Integer.parseInt(messageArr[2]);
            this.messageSize = Integer.parseInt(messageArr[3]);
            this.serverDelay = Integer.parseInt(messageArr[4]);
            out.println("200 Ready");
        }
    }

    // 2. Echo back; 404 ERROR: Invalid Measurement Message
    private void checkPhase2(String[] messageArr, PrintWriter out){
        try {
            if(!isInteger(messageArr[1]) || Integer.parseInt(messageArr[1]) != currentNum ||
                    messageArr[2].length() != messageSize || currentNum > probeNum){
                out.println("404 ERROR: Invalid Measurement Message");
                terminate = true;
            }else{
                sleep(serverDelay);
                out.println(messageArr[0] + " " + currentNum + " " + messageArr[2]);
                currentNum++;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // 3. Connection terminate: 1. 200 OK: Closing Connection; 2. 404 ERROR: Invalid Connection Termination Message
    private void checkPhase3(String[] messageArr, PrintWriter out){
        if(messageArr.length != 1){
            out.println("404 ERROR: Invalid Connection Termination Message");
        }else{
            out.println("200 OK: Closing Connection");
        }
        terminate = true;
    }

    public void run(){
        try {
            PrintWriter out =
                    new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null && !terminate) {
                String[] messageArr = inputLine.split(" ");
                System.out.println("Server receives: " + inputLine);
                String type = messageArr[0];
                if(type.equals("s")){
                    // Phase1 message
                    checkPhase1(messageArr, out);
                }else if(type.equals("m")){
                    // Phase2 message
                    checkPhase2(messageArr, out);
                }else if(type.equals("t")){
                    // Phase3 message
                    checkPhase3(messageArr, out);
                }else{
                    terminate = true;
                }
            }
            System.out.println("Close connection");
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
