package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Message;

public class LogoutForStudentEvent
{
    private Message message;

    public Message getMessage() {
        return message;
    }

    public LogoutForStudentEvent(Message message) {
        this.message = message;
    }
}
