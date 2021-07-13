package yugioh.controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class MainController {
    public static Socket socket;
    public static ObjectInputStream objectInputStream;
    public static ObjectOutputStream objectOutputStream;

    public static void initializeNetwork(){
        try {
            socket = new Socket("localhost", 7777);
            objectInputStream = new ObjectInputStream(socket.getInputStream());
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
