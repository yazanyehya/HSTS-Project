package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Message;

public class ShowReadyExamsAPPEvent
{
    private Message message;

    public Message getMessage() {
        return message;
    }
    ShowReadyExamsAPPEvent(Message message){this.message = message;}
}
