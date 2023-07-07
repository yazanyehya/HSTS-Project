package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Message;

public class QuestionEvent
{
    private Message message;

    public Message getMessage() {
        return message;
    }
    QuestionEvent(Message message){this.message = message;}
}
