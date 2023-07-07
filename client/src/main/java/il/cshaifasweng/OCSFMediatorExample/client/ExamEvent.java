package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Message;

import java.io.Serializable;

public class ExamEvent implements Serializable
{
    private Message message;

    public Message getMessage() {
        return message;
    }
    ExamEvent(Message message){this.message = message;}
}
