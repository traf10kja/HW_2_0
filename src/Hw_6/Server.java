package Hw_6;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
    public static void main(String[] args) {
        Socket clientSoc = null;
        Scanner sc = new Scanner(System.in);

        try (ServerSocket serverSoc = new ServerSocket(8189)) {
            System.out.println("Server on");
            clientSoc = serverSoc.accept();
            System.out.println("Client connect: " + clientSoc.getRemoteSocketAddress());
            DataInputStream inputStream = new DataInputStream(clientSoc.getInputStream());
            DataOutputStream outputStream = new DataOutputStream(clientSoc.getOutputStream());


            Thread reader = new Thread(() -> {
                try {
                    while (true) {
                        outputStream.writeUTF(sc.nextLine());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            reader.setDaemon(true);
            reader.start();

            while (true) {
                String str = inputStream.readUTF();
                if (str.equals("/close")) {
                    System.out.println("Client leave server");
                    outputStream.writeUTF("/close");
                    break;
                } else {
                    System.out.println("Client: " + str);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                clientSoc.close();
            } catch (IOException | NullPointerException e) {
                e.printStackTrace();
            }
        }
    }
}
