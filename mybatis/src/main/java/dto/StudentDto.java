package dto;

import entity.Student;

import java.io.Serializable;

/**
 * @author 陈梦
 * @Package: dto
 * @Description:
 * @date 2025/8/7 1:15
 */
public class StudentDto implements Serializable {
    private Student student;
    private int pageNo;//当前页码
    private int pageSize;//每页条数

    @Override
    public String toString() {
        return "StudnetDto{" +
                "student=" + student +
                ", pageNo=" + pageNo +
                ", pageSize=" + pageSize +
                '}';
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}

