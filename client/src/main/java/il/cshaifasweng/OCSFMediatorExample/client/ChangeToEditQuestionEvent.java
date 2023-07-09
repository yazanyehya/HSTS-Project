package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Message;

public class ChangeToEditQuestionEvent
{
    private Message message;
    public Message getMessage() {
        return message;
    }

    ChangeToEditQuestionEvent(Message message){
        this.message = message;
    }
}
