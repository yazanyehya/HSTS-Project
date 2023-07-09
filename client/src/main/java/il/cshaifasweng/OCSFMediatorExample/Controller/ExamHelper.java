package il.cshaifasweng.OCSFMediatorExample.Controller;

import il.cshaifasweng.OCSFMediatorExample.entities.Course;
import il.cshaifasweng.OCSFMediatorExample.entities.Question;
import il.cshaifasweng.OCSFMediatorExample.entities.Subject;

import java.io.Serializable;
import java.util.HashMap;

public class ExamHelper implements Serializable
{
    String examPeriod;
    String nameOfTeacher;
    HashMap<Question, Integer> questionHashMap;
    Subject subject;
    Course course;

    public ExamHelper(String examPeriod, String nameOfTeacher, HashMap<Question, Integer> questionHashMap, Subject subject, Course course)
    {
        this.examPeriod = examPeriod;
        this.nameOfTeacher = nameOfTeacher;
        this.questionHashMap = questionHashMap;
        this.subject = subject;
        this.course =course;
    }

    public Course getCourse() {
        return course;
    }

    public void setExamPeriod(String examPeriod) {
        this.examPeriod = examPeriod;
    }

    public void setNameOfTeacher(String nameOfTeacher) {
        this.nameOfTeacher = nameOfTeacher;
    }

    public String getExamPeriod() {
        return examPeriod;
    }

    public String getNameOfTeacher() {
        return nameOfTeacher;
    }

    public HashMap<Question, Integer> getQuestionHashMap() {
        return questionHashMap;
    }
}
