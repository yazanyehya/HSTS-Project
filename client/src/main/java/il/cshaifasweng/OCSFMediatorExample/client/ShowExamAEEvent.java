package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Message;

public class ShowExamAEEvent
{
    private Message message;

    public Message getMessage() {
        return message;
    }
    ShowExamAEEvent(Message message){this.message = message;}
}
