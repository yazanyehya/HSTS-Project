package il.cshaifasweng.OCSFMediatorExample.server;

import il.cshaifasweng.OCSFMediatorExample.client.LoginBoundry;
import il.cshaifasweng.OCSFMediatorExample.client.StudentBoundry;
import org.greenrobot.eventbus.EventBus;

public class StudentController
{
    private StudentBoundry studentBoundry;
    public StudentController(StudentBoundry studentBoundry)
    {
        EventBus.getDefault().register(this);
        this.studentBoundry = studentBoundry;
    }

}
