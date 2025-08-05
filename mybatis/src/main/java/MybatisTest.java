import dao.StudentDao;
import entity.Student;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MybatisTest {
    //1.将sqlSessionFactory声明为静态变量
    private static SqlSessionFactory sqlSessionFactory;
    //2.在静态代码块里使用sqlSessionFactoryBuilder创建sqlSessionFactory
    static {
        try {
            //2.1使用Resources类加载mybatis核心配置文件
            InputStream resourceAsStream =Resources.getResourceAsStream("mybatis-config.xml");
            //2.2将读取的输入流放到sqlSessionFactoryBuilder里创建sqlSessionFactory
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
            System.out.println("sqlSessionFactory创建成功");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //获取mapper(写了test1-test3，发现前面获取mapper的代码都是一样的，酒疯装起来)
    public StudentDao getStudetnMapper(){
        SqlSession sqlSession = MybatisTest.sqlSessionFactory.openSession(true);
        StudentDao mapper = sqlSession.getMapper(StudentDao.class);
        return mapper;
    }


//传统调用
    @Test
    public void testGetStudentById() {
        SqlSession sqlSession = MybatisTest.sqlSessionFactory.openSession();
        Student student = sqlSession.selectOne("dao.StudentDao.getStudentById", 100006);
        System.out.println(student);
        sqlSession.close();
    }

//    dao层映射（mapper）
    @Test
    public void test2(){
//        1.获取会话
        SqlSession sqlSession = MybatisTest.sqlSessionFactory.openSession();
//        2. 获取dao层
        StudentDao mapper = sqlSession.getMapper(StudentDao.class);
//        3.调用方法
        Student student = mapper.getStudentById(100007l);//要加一个 l 因为参数是长整型
//        4.打印
        System.out.println(student);
//        5.关闭
        sqlSession.close();
    }


    //dao层注解
    @Test
    public void test3(){
        SqlSession sqlSession = MybatisTest.sqlSessionFactory.openSession();
        StudentDao mapper = sqlSession.getMapper(StudentDao.class);
        System.out.println(mapper.getStudentByIdComment(100010l));
        sqlSession.close();
    }

    //实体入参
    @Test
    public void test4(){
        Student student = new Student();
        student.setStudentId(100010l);
        System.out.println(getStudetnMapper().getStudentByStudent(student));
    }

    //增加学生
//    @Test
//    public void test5(){
//        Student student = new Student();
//        student.setStudentName("小王");
//        student.setStudentBirth("1999-09-09");
//        student.setStudentAddress("上海");
//        student.setStudentNo("9526");
//        student.setGradeId(6l);
//        student.setStudentAge(20);
//
//        StudentDao mapper = getStudetnMapper();
//        int i = mapper.insertStudent(student);
//
//        if(i>0){
//            System.out.println("添加成功");
//        }else{
//            System.out.println("添加失败");
//        }
//    }

    //修改学生
    @Test
    public void test6(){
        StudentDao mapper = getStudetnMapper();

        Student student = new Student();
        student.setStudentId(100180l);
        student.setStudentAddress("成都");
        int i = mapper.updateStudentAddressById(student);
        if(i>0){
            System.out.println("修改成功");
        }else{
            System.out.println("修改失败");
        }
    }

    //删除学生
    @Test
    public void test7(){
        StudentDao mapper = getStudetnMapper();
        int i = mapper.deleteStudentById(100222l);
        if(i>0){
            System.out.println("删除成功");
        }else{
            System.out.println("删除失败");
        }
    }

    /*需求：根据学生主键id修改某学生的地址，姓名
     * 需求1：学生的姓名需要实体类输入
     * 需求2：学生的地址以及学生的id需要使用独立参数
     * */
    @Test
    public void test8(){
        StudentDao mapper = getStudetnMapper();

        Student student = new Student();
        student.setStudentName("小陈");
        int i = mapper.updateStudentParam(student,"重庆",100014l);
        if(i>0){
            System.out.println("修改成功");
        }else{
            System.out.println("修改失败");
        }
    }

    //查询名字中包含“小”字的所有学生，要求实体类入参
    @Test
    public void test9(){
        StudentDao mapper = getStudetnMapper();
        Student student = new Student();
        student.setStudentName("小");

        mapper.getStudentList(student).forEach(System.out::println);
//        相当于：
//        List<Student> list = mapper.getStudentList(student);
//        list.forEach(stu -> System.out.println(stu));
    }

    //查询名字中包含“小”字且地址中包含“道”的所有学生，要求map入参
    @Test
    public void test10(){
        StudentDao mapper = getStudetnMapper();

        Map<String,Object> map = new HashMap<>();
        map.put("name","小");
        map.put("add","道");

        mapper.getStudentListByMap(map).forEach(System.out::println);
    }

    //返回map
    @Test
    public void test11(){
        StudentDao mapper = getStudetnMapper();
        Map<String, Object> map = mapper.getStudentMapById(100014l);
        System.out.println(map);
    }

    //返回map集合
    @Test
    public void test12(){
        StudentDao mapper = getStudetnMapper();
        List<Map<String, Object>> list = mapper.getStudentListMapByName("张");
        list.forEach(System.out::println);
    }

}

