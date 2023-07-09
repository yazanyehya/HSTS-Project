package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Message;

public class ShowQuestionsEvent
{
    private Message message;

    public Message getMessage() {
        return message;
    }

    public ShowQuestionsEvent(Message message) {
        this.message = message;
    }
}
