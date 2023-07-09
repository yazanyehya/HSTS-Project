package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Message;

public class EditSelectedQuestionEvent
{
    private Message message;

    public Message getMessage() {
        return message;
    }

    public EditSelectedQuestionEvent(Message message) {
        this.message = message;
    }
}
