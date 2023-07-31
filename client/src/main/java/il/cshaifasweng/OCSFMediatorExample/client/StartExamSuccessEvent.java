package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Message;

public class StartExamSuccessEvent
{
    private Message message;

    public Message getMessage() {
        return message;
    }
    StartExamSuccessEvent(Message message){this.message = message;}
}
