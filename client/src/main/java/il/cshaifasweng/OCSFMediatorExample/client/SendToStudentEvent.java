package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Message;

public class SendToStudentEvent
{
    private Message message;

    public Message getMessage() {
        return message;
    }
    SendToStudentEvent(Message message){this.message = message;}
}
