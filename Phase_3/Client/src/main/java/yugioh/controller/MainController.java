package yugioh.controller;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class MainController {
    public static Socket socket;
    public static DataOutputStream dataOutputStream;
    public static DataInputStream dataInputStream;

    public static void initializeNetwork(){
        try {
            socket = new Socket("localhost", 7777);
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataInputStream = new DataInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void write(String message){
        try{
            dataOutputStream.writeUTF(message);
            dataOutputStream.flush();
        } catch (IOException ignored){}
    }

    public static String read(){
        try{
            return dataInputStream.readUTF();
        } catch (IOException e){
            return null;
        }
    }
}
