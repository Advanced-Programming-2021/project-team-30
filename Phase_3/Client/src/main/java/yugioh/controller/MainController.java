package yugioh.controller;

import java.io.IOException;
import java.net.Socket;

public class MainController {
    public static Socket socket;

    public static void initializeNetwork(){
        try {
            socket = new Socket("localhost", 7777);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
