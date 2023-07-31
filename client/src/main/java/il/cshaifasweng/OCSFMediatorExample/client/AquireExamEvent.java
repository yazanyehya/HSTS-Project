package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Message;

public class AquireExamEvent
{
    private Message message;

    public Message getMessage() {
        return message;
    }
    AquireExamEvent(Message message){this.message = message;}
}
