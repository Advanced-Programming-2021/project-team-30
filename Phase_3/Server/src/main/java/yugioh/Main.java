package yugioh;

import yugioh.controller.MainController;
import yugioh.controller.RegisterAndLoginController;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;


public class Main {
    public static void main(String[] args){
        RegisterAndLoginController.readPlayers();
        try {
            ServerSocket serverSocket = new ServerSocket(7777);
            while (true) {
                Socket socket = serverSocket.accept();
                new Thread(() -> {
                    try {
                        DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
                        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
                        while (true) {
                            String input = dataInputStream.readUTF();
                            if (input.equals("Exit")) break;
                            String result = MainController.process(input, socket);
                            dataOutputStream.writeUTF(result);
                            dataOutputStream.flush();
                        }
                        dataInputStream.close();
                        socket.close();
                        serverSocket.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }).start();
            }
        } catch (Exception ignored){
        }


    }

}
