package yugioh.model;

public class Message {
    private String senderUsername;
    private String senderPhoto;
    private String messageString;

    public Message(String senderUsername, String senderPhoto, String messageString) {
        setSenderUsername(senderUsername);
        setSenderPhoto(senderPhoto);
        setMessageString(messageString);
    }

    public String getSenderUsername() {
        return senderUsername;
    }

    public void setSenderUsername(String senderUsername) {
        this.senderUsername = senderUsername;
    }

    public String getSenderPhoto() {
        return senderPhoto;
    }

    public void setSenderPhoto(String senderPhoto) {
        this.senderPhoto = senderPhoto;
    }

    public String getMessageString() {
        return messageString;
    }

    public void setMessageString(String messageString) {
        this.messageString = messageString;
    }

    @Override
    public String toString() {
        return "Message{" +
                "senderUsername='" + senderUsername + '\'' +
                ", messageString='" + messageString + '\'' +
                '}';
    }
}
