 package il.cshaifasweng.OCSFMediatorExample.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Extra_Time")
public class ExtraTime implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name = "examId") // The foreign key column in the Ready_Exams table
    private ReadyExam readyExam;

    private String extraTimeApproved;

    private String timeAmount;

    private String explanation;

    private String course;

    private Integer exam_id;

    private String teacherId;

    public ExtraTime(ReadyExam readyExam, String timeAmount, String explanation)
    {
        this.readyExam = readyExam;
        this.timeAmount = timeAmount;
        this.explanation = explanation;
        this.extraTimeApproved = "";
        this.course = readyExam.getCourse();
        this.exam_id = readyExam.getReadyExamOriginalID();
        this.teacherId = readyExam.getUsername();
    }
    public ExtraTime()
    {
        this.extraTimeApproved = "";
    }

    public ReadyExam getReadyExam() {
        return readyExam;
    }

    public int getId() {
        return id;
    }

    public String getExplanation() {
        return explanation;
    }

    public String getExtraTimeApproved() {
        return extraTimeApproved;
    }

    public String getTimeAmount() {
        return timeAmount;
    }

    public void setReadyExam(ReadyExam readyExam) {
        this.readyExam = readyExam;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public void setExtraTimeApproved(String extraTimeApproved) {
        this.extraTimeApproved = extraTimeApproved;
    }

    public void setTimeAmount(String timeAmount) {
        this.timeAmount = timeAmount;
    }

    public String getCourse() {
        return course;
    }

    public Integer getExam_id() {
        return exam_id;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public void setExam_id(Integer exam_id) {
        this.exam_id = exam_id;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }
}
 