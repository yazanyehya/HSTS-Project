package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Message;

public class GetSubjectsForTeacherSEEvent
{
    private Message message;

    public Message getMessage() {
        return message;
    }

    public GetSubjectsForTeacherSEEvent(Message message) {
        this.message = message;
    }
}
