# 8月5日
主要讲的是mybatis的框架搭建
实体类，Dao层对应Mapper.xml文件的映射，使用Mybatis对数据库的增删改查
```
Student getStudentById(Long studentId);

@Select("select * from student where student_id = #{id}")
Student getStudentByIdComment(Long id);

Student getStudentByStudent(Student studentId);
int insertStudent(Student student);
int updateStudentAddressById(Student student);
int deleteStudentById(Long studentId);

int updateStudentParam(@Param("stu") Student student,
                       @Param("StuAddress") String studentAddress,
                       @Param("id") Long studentId);
                       
List<Student> getStudentList(Student student);
List<Student> getStudentListByMap(Map<String,Object> map);
Map<String,Object> getStudentMapById(Long studentId);
List<Map<String,Object>> getStudentListMapByName(String studentName);
```

