package il.cshaifasweng.OCSFMediatorExample.server;
import org.hibernate.Session;


public class SimpleChatServer {
    public static Session session;
    private static SimpleServer server;


    public static void main(String[] args) throws Exception {
        server = new SimpleServer(3000);
        System.out.println("Server is listening");
        server.listen();


    }}
