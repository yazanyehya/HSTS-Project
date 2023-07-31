package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Message;

public class GetSubjectsForTeacherEventAE
{
    private Message message;

    public Message getMessage() {
        return message;
    }

    public GetSubjectsForTeacherEventAE(Message message) {
        this.message = message;
    }
}
