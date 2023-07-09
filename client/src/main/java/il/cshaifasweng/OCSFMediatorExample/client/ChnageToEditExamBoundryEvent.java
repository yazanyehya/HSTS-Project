package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Message;

public class ChnageToEditExamBoundryEvent
{
    private Message message;
    public Message getMessage() {
        return message;
    }

    ChnageToEditExamBoundryEvent(Message message){
        this.message = message;
    }
}
