package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Message;

public class SaveEditedQuestionEvent
{
    private Message message;

    public Message getMessage() {
        return message;
    }

    public SaveEditedQuestionEvent(Message message) {
        this.message = message;
    }
}
