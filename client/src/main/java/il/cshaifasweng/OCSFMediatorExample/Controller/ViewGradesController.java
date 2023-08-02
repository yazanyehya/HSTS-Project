package il.cshaifasweng.OCSFMediatorExample.Controller;

import il.cshaifasweng.OCSFMediatorExample.client.*;
import il.cshaifasweng.OCSFMediatorExample.entities.Message;
import il.cshaifasweng.OCSFMediatorExample.entities.Principle;
import il.cshaifasweng.OCSFMediatorExample.entities.Student;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.util.List;

public class ViewGradesController {
    private ViewGradesBoundry viewGradesBoundry;

    public ViewGradesController(ViewGradesBoundry viewGradesBoundry)
    {
        EventBus.getDefault().register(this);
        this.viewGradesBoundry = viewGradesBoundry;
    }
    public void setViewGradesBoundry(ViewGradesBoundry viewGradesBoundry) {
        this.viewGradesBoundry = viewGradesBoundry;
    }
    public ViewGradesBoundry getViewGradesBoundry() {
        return viewGradesBoundry;
    }

    public void getStudents() throws IOException
    {
        Principle principle = (Principle) SimpleClient.getClient().getUser();
        Message message = new Message("getStudentsGradesForPrinciple", principle);
        SimpleClient.getClient().sendToServer(message);
    }
    @Subscribe
    public void handleGetStudentsGradesForPrincipleEvent(GetStudentsGradesForPrincipleEvent getStudentsGradesForPrincipleEvent)
    {
        List<Student> students = (List<Student>)getStudentsGradesForPrincipleEvent.getMessage().getBody();

        Platform.runLater(() -> {
            // Set the items for the ComboBox
            ObservableList<Student> studentObservableList = FXCollections.observableArrayList(students);
            viewGradesBoundry.getSelectStudent().setItems(studentObservableList);
        });
    }
}
