package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Message;

public class saveExamEvent
{
    private Message message;
    public Message getMessage() {
        return message;
    }
    saveExamEvent(Message message){this.message = message;}
}
