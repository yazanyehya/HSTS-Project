package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Message;

public class ShowExamsEvent
{
    private Message message;
    public Message getMessage() {
        return message;
    }

    ShowExamsEvent(Message message){
        this.message = message;
    }
}
