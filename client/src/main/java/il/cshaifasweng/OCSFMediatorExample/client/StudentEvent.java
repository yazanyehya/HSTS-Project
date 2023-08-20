package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Message;

public class StudentEvent
{
    private Message message;

    public Message getMessage() {
        return message;
    }
    StudentEvent(Message message){this.message = message;}
}
