package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Message;

public class ApproveExamEvent
{
    private Message message;

    public Message getMessage() {
        return message;
    }

    public ApproveExamEvent(Message message) {
        this.message = message;
    }
}
