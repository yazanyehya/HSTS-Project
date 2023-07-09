package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Message;

public class SaveEditedExamEvent
{
    private Message message;
    public Message getMessage() {
        return message;
    }

    SaveEditedExamEvent(Message message){
        this.message = message;
    }
}
