package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Message;

public class EditSelectedExamEvent
{
    private Message message;

    public Message getMessage() {
        return message;
    }

    public EditSelectedExamEvent(Message message) {
        this.message = message;
    }
}
