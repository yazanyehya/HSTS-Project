package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Message;

public class SendToPreviewEvent
{
    private Message message;

    public Message getMessage() {
        return message;
    }
    SendToPreviewEvent(Message message){this.message = message;}
}
