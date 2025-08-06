package vo;

import entity.Grade;
import entity.Student;

import java.io.Serializable;
import java.util.List;

public class GradeStudentVo implements Serializable {
    // Grade
    private Grade grade;
    // Student
    private List<Student> student;

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    public List<Student> getStudent() {
        return student;
    }

    public void setStudent(List<Student> student) {
        this.student = student;
    }

    @Override
    public String toString() {
        return "GradeStudentVo{" +
                "grade=" + grade +
                ", student=" + student +
                '}';
    }
}
