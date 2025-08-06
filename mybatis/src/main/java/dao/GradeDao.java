package dao;

import entity.Grade;
import vo.StudentGradeVo;

public interface GradeDao {

    Grade getGradeById(Long gradeId);

}
