package yugioh.model.duel.response;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;


public class Response {
    final static private DataOutputStream[] dataOutputStream = new DataOutputStream[2];

    public static void setSockets(Socket[] sockets){
        try{
            for (int i = 0; i < 2; i++)
                dataOutputStream[i] = new DataOutputStream(sockets[i].getOutputStream());
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public static boolean writeMessage(int player, String message){
        try{
            dataOutputStream[player].writeUTF("<$alert$>" + message);
            dataOutputStream[player].flush();
            return true;
        } catch (IOException e){
            return false;
        }
    }
}
