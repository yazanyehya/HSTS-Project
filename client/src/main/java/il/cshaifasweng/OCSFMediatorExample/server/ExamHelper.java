package il.cshaifasweng.OCSFMediatorExample.server;

import il.cshaifasweng.OCSFMediatorExample.entities.Question;

import java.io.Serializable;
import java.util.HashMap;

public class ExamHelper implements Serializable
{
    String examPeriod;
    String nameOfTeacher;
    HashMap<Question, Integer> questionHashMap;

    public ExamHelper(String examPeriod, String nameOfTeacher, HashMap<Question, Integer> questionHashMap)
    {
        this.examPeriod = examPeriod;
        this.nameOfTeacher = nameOfTeacher;
        this.questionHashMap = questionHashMap;
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
