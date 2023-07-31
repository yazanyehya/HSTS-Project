package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Message;

public class StartExamFailedEvent
{
    private Message message;

    public Message getMessage() {
        return message;
    }
    StartExamFailedEvent(Message message){this.message = message;}
}
