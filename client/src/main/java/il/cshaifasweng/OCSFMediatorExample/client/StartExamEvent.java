package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Message;

public class StartExamEvent
{
    private Message message;

    public Message getMessage() {
        return message;
    }
    StartExamEvent(Message message){this.message = message;}
}
