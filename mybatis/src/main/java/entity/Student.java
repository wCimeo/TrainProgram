package entity;
import java.io.Serializable;

public class Student implements Serializable {
    //学生id
    private Long studentId;
    //学生姓名
    private String studentName;
    //学生生日
    private String studentBirth;
    //学生地址
    private String studentAddress;
    //学生学号
    private String studentNo;
    //年级外键id
    private Long gradeId;
    //学生年龄
    private Integer studentAge;

    public Student() {
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentBirth() {
        return studentBirth;
    }

    public void setStudentBirth(String studentBirth) {
        this.studentBirth = studentBirth;
    }

    public String getStudentAddress() {
        return studentAddress;
    }

    public void setStudentAddress(String studentAddress) {
        this.studentAddress = studentAddress;
    }

    public String getStudentNo() {
        return studentNo;
    }

    public void setStudentNo(String studentNo) {
        this.studentNo = studentNo;
    }

    public Long getGradeId() {
        return gradeId;
    }

    public void setGradeId(Long gradeId) {
        this.gradeId = gradeId;
    }

    public Integer getStudentAge() {
        return studentAge;
    }

    public void setStudentAge(Integer studentAge) {
        this.studentAge = studentAge;
    }

    @Override
    public String toString() {
        return "Student{" +
                "studentId=" + studentId +
                ", studentName='" + studentName + '\'' +
                ", studentBirth='" + studentBirth + '\'' +
                ", studentAddress='" + studentAddress + '\'' +
                ", studentNo='" + studentNo + '\'' +
                ", gradeId=" + gradeId +
                ", studentAge=" + studentAge +
                '}';
    }
}
