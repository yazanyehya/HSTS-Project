package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Message;

public class GetSubjectsEvent
{
    private Message message;

    public Message getMessage() {
        return message;
    }

    public GetSubjectsEvent(Message message) {
        this.message = message;
    }
}
