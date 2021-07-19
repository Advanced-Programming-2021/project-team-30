package yugioh.model;

public class Message {
    private Player sender;
    private String messageString;

    public Message(Player sender, String messageString) {
        setSender(sender);
        setMessageString(messageString);
    }

    public Player getSender() {
        return sender;
    }

    public void setSender(Player sender) {
        this.sender = sender;
    }

    public String getMessageString() {
        return messageString;
    }

    public void setMessageString(String messageString) {
        this.messageString = messageString;
    }
}
