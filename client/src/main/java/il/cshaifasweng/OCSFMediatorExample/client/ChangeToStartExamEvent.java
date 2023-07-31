package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Message;

public class ChangeToStartExamEvent
{
    private Message message;

    public Message getMessage() {
        return message;
    }

    public ChangeToStartExamEvent(Message message) {
        this.message = message;
    }
}
