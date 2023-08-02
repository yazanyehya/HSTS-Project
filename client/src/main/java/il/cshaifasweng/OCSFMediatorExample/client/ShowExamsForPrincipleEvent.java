package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Message;

public class ShowExamsForPrincipleEvent
{
    private Message message;
    public Message getMessage() {
        return message;
    }

    ShowExamsForPrincipleEvent(Message message){
        this.message = message;
    }
}
