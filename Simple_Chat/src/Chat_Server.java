import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Chat_Server {
    ServerSocket sr ;
    Socket soc;
    Scanner in;
    PrintWriter out;
    public Chat_Server(){
        try{
            sr = new ServerSocket(2004);
            System.out.println("Server has started");
            soc=sr.accept();
            System.out.println("Connection established");
            in = new Scanner(soc.getInputStream());
            out = new PrintWriter(soc.getOutputStream(), true);
            readData();
            writeData();

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void writeData() {
        Runnable r2 = ()->{
            System.out.println("Writer has been started");
            try{
                Scanner write = new Scanner(System.in);
                while(!soc.isClosed()){
                    String msg = write.nextLine();
                    if(msg.equals("exit")){
                        out.println(msg);
                        soc.close();
                        break;
                    }
                    out.println("Server: "+msg);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        };
        new Thread(r2).start();
    }
    public void readData() {
        Runnable r1=()->{
            System.out.println("Reader has started...");
            try {
                while(!soc.isClosed()){
                    String msg = in.nextLine();
                    if(msg.equals("exit")){
                        System.out.println("Server has terminated the chat");
                        soc.close();
                        break;
                    }
                    System.out.println( msg);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        };
        new Thread(r1).start();
    }
    public static void main(String[] args) {
        System.out.println("Starting the server...");
        new Chat_Server();
    }
}
