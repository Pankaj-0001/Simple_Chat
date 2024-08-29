import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Chat_Client {
    Socket soc;
    Scanner in;
    PrintWriter out;
    public Chat_Client(){
        try{
            System.out.println("Client has started");
            soc = new Socket("localhost" , 2004);
            System.out.println("Connection established");
            in = new Scanner(soc.getInputStream());
            out = new PrintWriter(soc.getOutputStream(), true);

            readData();
            writeData();

        }catch (Exception e){
            e.printStackTrace();
        }
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
                    out.println("Client: "+msg);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        };
        new Thread(r2).start();
    }
    public static void main(String[] args) {
        System.out.println("Server started");
        new Chat_Client();
    }

}
