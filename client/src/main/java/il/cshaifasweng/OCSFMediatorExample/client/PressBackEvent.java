package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Message;

public class PressBackEvent
{
    private Message message;
    public Message getMessage() {
        return message;
    }

    PressBackEvent(Message message){
        this.message = message;
    }
}
