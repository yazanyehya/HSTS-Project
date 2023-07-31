package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Message;

public class ShowStudentsEvent
{
    private Message message;

    public Message getMessage() {
        return message;
    }
    ShowStudentsEvent(Message message){this.message = message;}
}
