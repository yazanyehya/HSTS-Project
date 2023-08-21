package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Message;

public class PrincipleEvent
{
    private Message message;
    public Message getMessage() {
        return message;
    }
    PrincipleEvent(Message message){this.message = message;}
}
