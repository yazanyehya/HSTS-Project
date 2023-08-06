package il.cshaifasweng.OCSFMediatorExample.Controller;

import il.cshaifasweng.OCSFMediatorExample.entities.Course;
import il.cshaifasweng.OCSFMediatorExample.entities.Question;
import il.cshaifasweng.OCSFMediatorExample.entities.Subject;

import java.io.Serializable;
import java.util.HashMap;

public class ExamHelper implements Serializable
{
    String examPeriod;
    String username;
    String teacherFullName;
    HashMap<Question, Integer> questionHashMap;
    String subject;
    String course;
    String teacherComments;
    String allComments;

    public ExamHelper(String examPeriod, String username,String teacherFullName, HashMap<Question, Integer> questionHashMap, String subject, String course, String teacherComments, String allComments)
    {
        this.examPeriod = examPeriod;
        this.username = username;
        this.teacherFullName=teacherFullName;
        this.questionHashMap = questionHashMap;
        this.subject = subject;
        this.course =course;
        this.teacherComments = teacherComments;
        this.allComments = allComments;
    }

    public String getCourse() {
        return course;
    }

    public void setExamPeriod(String examPeriod) {
        this.examPeriod = examPeriod;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getExamPeriod() {
        return examPeriod;
    }

    public String getSubject() {
        return subject;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setQuestionHashMap(HashMap<Question, Integer> questionHashMap) {
        this.questionHashMap = questionHashMap;
    }

    public String getTeacherFullName() {
        return teacherFullName;
    }

    public void setTeacherFullName(String teacherFullName) {
        this.teacherFullName = teacherFullName;
    }

    public HashMap<Question, Integer> getQuestionHashMap() {
        return questionHashMap;
    }
}
