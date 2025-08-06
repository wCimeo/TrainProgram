package dao;
import dto.StudentDto;
import entity.Student;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import vo.GradeStudentVo;
import vo.StudentGradeVo;

import java.util.List;
import java.util.Map;

public interface StudentDao {
    // 根据id查询学生
    Student getStudentById(Long studentId);

    // 使用注解，本来应该在Mapper.xml中写，这里为了方便，直接在接口中写。在大型项目中不适用，应该在Mapper.xml中写
    @Select("select * from student where student_id = #{id}")
    Student getStudentByIdComment(Long id);

    //实体类入参
    Student getStudentByStudent(Student studentId);

    //新增学生
    int insertStudent(Student student);

    //根据学生主键id，修改学生地址
    int updateStudentAddressById(Student student);

    //根据学生主键id，删除学生
    int deleteStudentById(Long studentId);

    /*需求：根据学生主键id修改某学生的地址，姓名
    需求1：学生的姓名需要实体类输入
    需求2：学生的地址以及学生的id需要使用独立参数
    param：给参数起别名*/
    int updateStudentParam(@Param("stu") Student student,
                           @Param("StuAddress") String studentAddress,
                           @Param("id") Long studentId);

    //查询名字中包含“小”字的所有学生，要求实体类入参（也就是声明一个Student类，set他的student_name为“小”）
    List<Student> getStudentList(Student student);

    //查询名字中包含“小”字且地址中包含“道”的所有学生，要求map入参（因为map类型是最底层的参数，直接传入map类型会提高效率）
    List<Student> getStudentListByMap(Map<String,Object> map);

    /*返回map
    需求：根据学生主键id查询学生的姓名，学号，地址
    要求1：返回值为地址
    要求2：返回map的key为姓名，学号，地址*/
    Map<String,Object> getStudentMapById(Long studentId);

    /*返回list<map>
    需求：查询学生姓名中含有“张”字的学生姓名，学号，地址
    要求1：返回值为地址
    要求2：返回map的key为姓名，学号，地址*/
    List<Map<String,Object>> getStudentListMapByName(String studentName);

   /* 高级映射，一对一
    需求：根据学生id查询学生的基本信息，包括年级的名称*/
    StudentGradeVo getStudentGradeByStudentId(Long studentId);

    /*高级映射，一对一，返回的数据类型为List（集合）
    需求：并且查询学生姓名中包含“陈”字的学生基本信息，包括年级名称*/
    List<StudentGradeVo> getStudentGradeListByName(String studentName);

    /*高级映射，一对多
    需求：根据年级主键id查询该年级下的所有学生的基本信息,包括年级信息*/
    GradeStudentVo getStudentByGradeId(Long gradeId);

    /*高级映射，一对多，返回的数据类型为List（集合）
    需求：查询所有年级下学生姓名中包含”张”字的所有学生的基本信息,包括年级信息*/
    List<GradeStudentVo> getStudentListByName(String studentName);

    /*高级映射，子查询
    需求: 查询学生姓名中包含”张”字的所有学生的基本信息,包括年级的名称*/
    List<StudentGradeVo> getStudentGradeListByNameSubQuery(String studentName);

    /*动态SQL：where-if
    需求: 根据学生姓名,学生地址,学生学号为条件查询学生
    要求: 以上3个字段都为可选项,如果用户传入了该条件就需要使用该条件进行查询(用户不传则不使用)*/
    List<StudentGradeVo> getStudentListByWhereIf(Student student);

    /*动态SQL：set-where-if
    需求: 修改学生姓名,学生地址,学生学号
    要求: 以上3个字段都为可选项,如果用户传入了该条件就需要使用该条件进行修改(用户不传则不使用)*/
    int updateStudentSetIfWhere(Student student);

    /*foreach 复杂查询
    需求: 根据学生的N个主键id查询学生的基本信息
    要求: 学生N的id需要使用数组入参*/
    List<StudentGradeVo> getStudentGradeVoByArrayIds(Long[] ids);

    /*foreach 复杂查询
    需求: 根据学生的N个主键id查询学生的基本信息
    要求: 学生N的id需要使用List入参*/
    List<StudentGradeVo> getStudentGradeVoByListIds(List<Long> ids);

    /*foreach 复杂查询
    需求: 根据学生的N个主键id查询学生的基本信息
    要求: 学生N的id需要使用Map入参*/
    List<StudentGradeVo> getStudentGradeVoByMapIds(Map<String, Object> map);

    //批量新增学生
    int insertStudentBatch(List<Student> students);

    /*choose
    需求: 根据学生的id查询学生的基本信息
    要求1: 如果传入的id为100001则将学号 + 1后进行查询
    要求2: 如果传入的id为100002则将学号 + 2后进行查询
    要求3: 其他id不变*/
    StudentGradeVo getStudentGradeVoChoose(Long studentId);

    /*@注解开发
    需求: 查询学生姓名中包含”张”字的所有学生的基本信息
    要求1: 使用dao层注解的方式编写动态sql (学生姓名如果用户不传则不使用)*/
    @Select("<script>" +
                "select * from student" +
                "<where>" +
                    "<if test='_parameter neq null and _parameter neq \"\"'>" +
                         "and student_name like concat('%', #{studentName}, '%' )" +
                     "</if>" +
                 "</where>" +
            "</script>")
    List<Student> getStudentByStudentNameComment(String studentName);



    /*需求: 分页查询学生姓名中包含”张”字的所有学生的基本信息
    要求1: 学生姓名如果用户不传则不使用
    要求2: 需要分页*/
    List<StudentGradeVo> getStudentGradeVoLimit(StudentDto studentDto);

}


