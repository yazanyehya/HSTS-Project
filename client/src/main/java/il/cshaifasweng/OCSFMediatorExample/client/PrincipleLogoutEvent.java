package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Message;

public class PrincipleLogoutEvent
{
    private Message message;
    public Message getMessage() {
        return message;
    }
    PrincipleLogoutEvent(Message message){this.message = message;}
}
