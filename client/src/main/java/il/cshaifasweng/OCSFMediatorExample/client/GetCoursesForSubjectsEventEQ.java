package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Message;

public class GetCoursesForSubjectsEventEQ
{
    private Message message;
    public Message getMessage() {
        return message;
    }

    GetCoursesForSubjectsEventEQ(Message message){
        this.message = message;
    }
}
