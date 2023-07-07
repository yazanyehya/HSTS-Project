package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Message;

public class ChangeToExamBoundry
{
    private Message message;
    public Message getMessage() {
        return message;
    }

    ChangeToExamBoundry(Message message){
        this.message = message;
    }
}
