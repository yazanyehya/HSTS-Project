package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Message;

public class GetCoursesForSubjectsEvent
{
    private Message message;

    public Message getMessage() {
        return message;
    }

    public GetCoursesForSubjectsEvent(Message message) {
        this.message = message;
    }
}
