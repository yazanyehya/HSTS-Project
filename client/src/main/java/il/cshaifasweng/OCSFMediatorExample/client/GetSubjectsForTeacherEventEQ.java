package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Message;

public class GetSubjectsForTeacherEventEQ
{
    private Message message;
    public Message getMessage() {
        return message;
    }

    GetSubjectsForTeacherEventEQ(Message message){
        this.message = message;
    }
}
