package yugioh.controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

public class MainController {
    public static Socket socket;
    public static ObjectInputStream objectInputStream;
    public static ObjectOutputStream objectOutputStream;

    public static void initializeNetwork(){
        try {
            socket = new Socket("localhost", 7777);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static Object writeOnOutput(Object object){
        new Thread(() -> {
            try {
                objectInputStream = new ObjectInputStream(socket.getInputStream());
                objectOutputStream = new ObjectOutputStream(socket.getOutputStream());

            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
        try {
            objectOutputStream.writeObject(object);
            objectOutputStream.flush();
            return objectInputStream.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }
    public static Object readObject(){
        ArrayList<Object> objects = new ArrayList<>();
        new Thread(() -> {
            try {
                objectInputStream = new ObjectInputStream(socket.getInputStream());
                objects.add(objectInputStream.readObject());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
        while (objects.isEmpty()){

        }
        return objects.get(0);
    }
}
