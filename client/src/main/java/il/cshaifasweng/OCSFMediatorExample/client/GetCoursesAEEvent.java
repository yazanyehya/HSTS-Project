package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Message;

public class GetCoursesAEEvent
{
    private Message message;

    public Message getMessage() {
        return message;
    }

    public GetCoursesAEEvent(Message message) {
        this.message = message;
    }
}
