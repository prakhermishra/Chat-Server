package server;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class Message implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long messageId;

    private String message;

    private String userName;

    private Long timeStamp;

    private boolean isSent;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public boolean getIsSent(){
        return isSent;
    }

    public void setIsSent(boolean isSent){
        this.isSent = isSent;
    }

    public long getMessageId(){ return this.messageId;}

    @Override
    public String toString(){
        return this.getMessage() + "-" +
                this.getTimeStamp() + "-" +
                this.getUserName() + "-" +
                this.getIsSent();
    }

}