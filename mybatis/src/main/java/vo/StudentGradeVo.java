package vo;

import entity.Grade;
import entity.Student;

import java.io.Serializable;

public class StudentGradeVo implements Serializable{
    //学生
    private Student student;
    //年级
    private Grade grade;

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    @Override
    public String toString() {
        return "StudentGradeVo{" +
                "student=" + student +
                ", grade=" + grade +
                '}';
    }
}

