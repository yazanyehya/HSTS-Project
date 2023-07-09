package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Message;

public class GetAllQuestionEvent
{
    private Message message;

    public Message getMessage() {
        return message;
    }

    public GetAllQuestionEvent(Message message) {
        this.message = message;
    }
}
