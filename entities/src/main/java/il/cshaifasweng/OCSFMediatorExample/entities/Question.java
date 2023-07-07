package il.cshaifasweng.OCSFMediatorExample.entities;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;


import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Questions")
public class Question implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String qText;
    @Column
    private String answer1;
    @Column
    private String answer2;
    @Column
    private String answer3;
    @Column
    private String answer4;


    private transient IntegerProperty score;

    @Column
    private String correctAnswer;

    @Column(name = "selected")
    private Boolean selected = false;

    @ManyToMany(mappedBy = "listOfQuestions", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Exam> exams;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "subject_id")
    private Subject subject;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "course_id")
    private Course course;

    public Question(String qText, String answer1, String answer2, String answer3, String answer4, Subject subject, Teacher teacher, String correctAnswer, Course course)
    {
        this.qText = qText;
        this.answer1 = answer1;
        this.answer2 = answer2;
        this.answer3 = answer3;
        this.answer4 = answer4;
        this.subject = subject;
        this.teacher = teacher;
        this.course = course;
        this.correctAnswer = correctAnswer;
        this.score = new SimpleIntegerProperty();

    }
    public int getScore() {
        return score.get();
    }

    public Boolean getSelected() {
        return selected;
    }
    public void setScore(int score) {
        this.score.set(score);
    }
    public IntegerProperty scoreProperty() {
        if (score == null) {
            score = new SimpleIntegerProperty();
        }
        return score;
    }
    public BooleanProperty selectedProperty() {
        if (selected == null) {
            selected = false; // Set a default value if null
        }
        return new SimpleBooleanProperty(selected);
    }


    public void setSelected(Boolean selected) {
        this.selected = selected;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Question()
    {
        exams = new ArrayList<Exam>();
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public int getId() {
        return id;
    }

    public String getAnswer1() {
        return answer1;
    }

    public String getAnswer2() {
        return answer2;
    }

    public String getAnswer3() {
        return answer3;
    }

    public String getAnswer4() {
        return answer4;
    }

    public String getQText() {
        return qText;
    }


    public void setId(int id) {
        this.id = id;
    }

    public void setAnswer1(String answer1) {
        this.answer1 = answer1;
    }

    public void setAnswer2(String answer2) {
        this.answer2 = answer2;
    }

    public void setAnswer3(String answer3) {
        this.answer3 = answer3;
    }

    public void setAnswer4(String answer4) {
        this.answer4 = answer4;
    }

    public void setqText(String qText) {
        this.qText = qText;
    }
}
