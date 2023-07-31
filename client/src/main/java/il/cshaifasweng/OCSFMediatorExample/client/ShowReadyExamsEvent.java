package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Message;

public class ShowReadyExamsEvent
{
    private Message message;

    public Message getMessage() {
        return message;
    }
    ShowReadyExamsEvent(Message message){this.message = message;}
}
