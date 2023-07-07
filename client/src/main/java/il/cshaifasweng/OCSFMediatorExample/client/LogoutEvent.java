package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Message;

public class LogoutEvent
{
    private Message message;

    public Message getMessage() {
        return message;
    }
    public LogoutEvent(Message message){this.message = message;}
}
