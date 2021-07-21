package yugioh.model.duel.response;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;


public class Response {
    final private DataOutputStream[] dataOutputStreams = new DataOutputStream[2];
    final private DataInputStream[] dataInputStreams = new DataInputStream[2];

    public Response(Socket[] sockets){
        try{
            for (int i = 0; i < 2; i++) {
                dataOutputStreams[i] = new DataOutputStream(sockets[i].getOutputStream());
                dataInputStreams[i] = new DataInputStream(sockets[i].getInputStream());
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public boolean writeMessage(int player, String message){
        try{
            dataOutputStreams[player].writeUTF(message);
            dataOutputStreams[player].flush();
            return true;
        } catch (IOException e){
            return false;
        }
    }

    public String listen(int player){
        try{
            return dataInputStreams[player].readUTF();
        } catch (IOException e){
            return "fail";
        }
    }
}
