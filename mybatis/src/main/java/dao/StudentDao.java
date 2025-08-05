package dao;
import entity.Student;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

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
    * 需求1：学生的姓名需要实体类输入
    * 需求2：学生的地址以及学生的id需要使用独立参数
    * param：给参数起别名
    * */
    int updateStudentParam(@Param("stu") Student student,
                           @Param("StuAddress") String studentAddress,
                           @Param("id") Long studentId);


    //查询名字中包含“小”字的所有学生，要求实体类入参（也就是声明一个Student类，set他的student_name为“小”）
    List<Student> getStudentList(Student student);

    //查询名字中包含“小”字且地址中包含“道”的所有学生，要求map入参（因为map类型是最底层的参数，直接传入map类型会提高效率）
    List<Student> getStudentListByMap(Map<String,Object> map);

    //返回map
    //需求：根据学生主键id查询学生的姓名，学号，地址
    //要求1：返回值为地址
    //要求2：返回map的key为姓名，学号，地址
    Map<String,Object> getStudentMapById(Long studentId);

    //返回list<map>
    //需求：查询学生姓名中含有“张”字的学生姓名，学号，地址
    //要求1：返回值为地址
    //要求2：返回map的key为姓名，学号，地址
    List<Map<String,Object>> getStudentListMapByName(String studentName);
}


