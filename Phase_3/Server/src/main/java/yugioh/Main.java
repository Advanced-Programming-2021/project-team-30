package yugioh;

import yugioh.controller.MainController;
import yugioh.controller.RegisterAndLoginController;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static boolean isObjectExit(Object object){
        if (object instanceof String){
            String str = (String) object;
            return str.equals("Exit");
        }
        return false;
    }
    public static void main(String[] args){
        RegisterAndLoginController.readPlayers();
        try {
            ServerSocket serverSocket = new ServerSocket(7777);
            while (true) {
                Socket socket = serverSocket.accept();
                new Thread(() -> {
                    try {
                        ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
                        ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                        while (true) {
                            Object input = objectInputStream.readObject();
                            if (isObjectExit(input)) break;
                            Object result = MainController.process(input);
                            objectOutputStream.writeObject(result);
                            objectOutputStream.flush();
                        }
                        objectInputStream.close();
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
