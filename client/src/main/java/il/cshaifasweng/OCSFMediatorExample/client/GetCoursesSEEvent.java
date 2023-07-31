package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Message;

public class GetCoursesSEEvent
{
    private Message message;

    public Message getMessage() {
        return message;
    }

    public GetCoursesSEEvent(Message message) {
        this.message = message;
    }
}
