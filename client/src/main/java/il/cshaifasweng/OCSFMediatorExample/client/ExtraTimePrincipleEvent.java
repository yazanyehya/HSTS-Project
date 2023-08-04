package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Message;

public class ExtraTimePrincipleEvent
{
    private Message message;

    public Message getMessage() {
        return message;
    }

    public ExtraTimePrincipleEvent(Message message) {
        this.message = message;
    }

}
