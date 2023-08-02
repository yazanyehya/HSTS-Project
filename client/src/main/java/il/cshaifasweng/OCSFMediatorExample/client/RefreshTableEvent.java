package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Message;

public class RefreshTableEvent
{
    private Message message;
    public Message getMessage() {
        return message;
    }
    RefreshTableEvent(Message message){this.message = message;}
}
