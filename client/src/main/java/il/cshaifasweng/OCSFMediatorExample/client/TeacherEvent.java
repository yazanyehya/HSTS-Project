package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Message;

public class TeacherEvent
{
    private Message message;

    public Message getMessage() {
        return message;
    }
    TeacherEvent(Message message){this.message = message;}
}
