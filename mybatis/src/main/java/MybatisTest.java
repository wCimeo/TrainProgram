import dao.StudentDao;
import dto.StudentDto;
import entity.Student;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;
import uitls.PageUtils;
import vo.GradeStudentVo;
import vo.StudentGradeVo;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;


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
    //3.获取mapper(写了test1-test3，发现前面获取mapper的代码都是一样的，就封装起来)
    public StudentDao getStudentMapper(){
        //参数取为true，表示将一级缓存推进数据库
        SqlSession sqlSession = MybatisTest.sqlSessionFactory.openSession(true);
        StudentDao mapper = sqlSession.getMapper(StudentDao.class);
        return mapper;
    }

    //传统调用
    @Test
    public void testGetStudentById() {
        SqlSession sqlSession = MybatisTest.sqlSessionFactory.openSession();
//        sqlsession中包含大量对数据库进行操作的函数
        Student student = sqlSession.selectOne("dao.StudentDao.getStudentById", 100006);
        System.out.println(student);
        sqlSession.close();
    }

    //dao层映射（mapper）
    @Test
    public void test2(){
//        1.获取会话
        SqlSession sqlSession = MybatisTest.sqlSessionFactory.openSession();
//        2. 获取dao层
        StudentDao mapper = sqlSession.getMapper(StudentDao.class);
//        3.调用方法
        Student student = mapper.getStudentById(100007L);//要加一个 l 因为参数是长整型
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
        System.out.println(mapper.getStudentByIdComment(100010L));
        sqlSession.close();
    }

    //实体 student 类入参
    @Test
    public void test4(){
        Student student = new Student();
        student.setStudentId(100010L);
        System.out.println(getStudentMapper().getStudentByStudent(student));
    }

    //增加学生
    @Test
    public void test5(){
        Student student = new Student();
        student.setStudentName("小王");
        student.setStudentBirth("1999-09-09");
        student.setStudentAddress("上海");
        student.setStudentNo(9526L);
        student.setGradeId(6L);
        student.setStudentAge(20);

        StudentDao mapper = getStudentMapper();
        int i = mapper.insertStudent(student);

        if(i>0){
            System.out.println("添加成功");
        }else{
            System.out.println("添加失败");
        }
    }

    //修改学生
    @Test
    public void test6(){
        StudentDao mapper = getStudentMapper();
        Student student = new Student();
        student.setStudentId(100180L);
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
        StudentDao mapper = getStudentMapper();
        int i = mapper.deleteStudentById(100222L);
        if(i>0){
            System.out.println("删除成功");
        }else{
            System.out.println("删除失败");
        }
    }

    /*需求：根据学生主键id修改某学生的地址，姓名
     需求1：学生的姓名需要实体类输入
     需求2：学生的地址以及学生的id需要使用独立参数*/
    @Test
    public void test8(){
        StudentDao mapper = getStudentMapper();

        Student student = new Student();
        student.setStudentName("小陈");
        int i = mapper.updateStudentParam(student,"重庆", 100014L);
        if(i>0){
            System.out.println("修改成功");
        }else{
            System.out.println("修改失败");
        }
    }

    //查询名字中包含“小”字的所有学生，要求实体类入参
    @Test
    public void test9(){
        StudentDao mapper = getStudentMapper();
        Student student = new Student();
        student.setStudentName("小");

        mapper.getStudentList(student).forEach(System.out::println);
        /*相当于：
        List<Student> list = mapper.getStudentList(student);
        list.forEach(stu -> System.out.println(stu));*/
    }

    //查询名字中包含“小”字且地址中包含“道”的所有学生，要求map入参
    @Test
    public void test10(){
        StudentDao mapper = getStudentMapper();

        Map<String,Object> map = new HashMap<>();
        map.put("name","小");
        map.put("add","道");

        mapper.getStudentListByMap(map).forEach(System.out::println);
    }

    //返回map
    @Test
    public void test11(){
        StudentDao mapper = getStudentMapper();
        Map<String, Object> map = mapper.getStudentMapById(100014L);
        System.out.println(map);
    }

    //返回map集合
    @Test
    public void test12(){
        StudentDao mapper = getStudentMapper();
        List<Map<String, Object>> list = mapper.getStudentListMapByName("张");
        list.forEach(System.out::println);
    }

    //需求：根据学生id查询学生的基本信息，包括年级的名称
    @Test
    public void test13(){
        StudentDao mapper = getStudentMapper();
        StudentGradeVo studentGradeVo = mapper.getStudentGradeByStudentId(100007L);
        System.out.println(studentGradeVo);
    }

    //并且查询学生姓名中包含“陈”字的学生基本信息，包括年级名称
    @Test
    public void test14(){
        StudentDao mapper = getStudentMapper();
        List<StudentGradeVo> list = mapper.getStudentGradeListByName("陈");
        list.forEach(System.out::println);
    }

    //根据年级主键id查询该年级下的所有学生的基本信息,包括年级信息
    @Test
    public void test15(){
        StudentDao mapper = getStudentMapper();
        GradeStudentVo gradeStudentVo = mapper.getStudentByGradeId(2L);
        gradeStudentVo.getStudent().forEach(System.out::println);
    }

    //查询所有年级下学生姓名中包含”张”字的所有学生的基本信息,包括年级信息
    @Test
    public void test16(){
        //获取mapper
        StudentDao studentMapper = getStudentMapper();
        List<GradeStudentVo> gradeStudentVos = studentMapper.getStudentListByName("张");
        gradeStudentVos.forEach(gradeStudentVo -> {
            System.out.println(gradeStudentVo.getGrade());
            gradeStudentVo.getStudent().forEach(System.out::println);
        });
    }

    //查询学生姓名中包含”张”字的所有学生的基本信息,包括年级的名称
    @Test
    public void test17(){
        StudentDao studentMapper = getStudentMapper();
        List<StudentGradeVo> list = studentMapper.getStudentGradeListByNameSubQuery("张");
        list.forEach(System.out::println);
    }

    //where-if  根据学生姓名,学生地址,学生学号为条件查询学生
    @Test
    public void test18(){
        StudentDao mapper = getStudentMapper();
        Student student = new Student();
//        通过对数据库查询参数的设置实现动态查询
        student.setStudentName("赵");
//        student.setStudentAddress("大道");
        student.setGradeId(2L);
        List<StudentGradeVo> list = mapper.getStudentListByWhereIf(student);
        list.forEach(System.out::println);
    }

    //set-where-if
    @Test
    public void test19(){
        //获取mapper
        StudentDao studentMapper = getStudentMapper();
        //封装修改参数
        Student student = new Student();
        student.setStudentName("张小桑");
        student.setStudentId(100001L);
        student.setStudentAddress("自贡");
        int i = studentMapper.updateStudentSetIfWhere(student);
        if ( i > 0){
            System.out.println("修改成功");
        }else {
            System.out.println("修改失败");
        }
    }

    //foreach-array 复杂查询 根据学生的N个主键id查询学生的基本信息
    @Test
    public void test20(){
        StudentDao studentMapper = getStudentMapper();
        //封装参数
        Long[] ids = {100001L, 100002L, 100004L, 8989L};
        studentMapper.getStudentGradeVoByArrayIds(ids).forEach(System.out::println);
    }

    //foreach-list 复杂查询 根据学生的N个主键id查询学生的基本信息
    @Test
    public void test21(){
        StudentDao studentMapper = getStudentMapper();
        //封装参数
        List<Long> list = Arrays.asList(100001l, 100002l, 100004l, 8989l);
        studentMapper.getStudentGradeVoByListIds(list).forEach(System.out::println);
    }

    //foreach-map 复杂查询 根据学生的N个主键id查询学生的基本信息
    @Test
    public void test22(){
        StudentDao studentMapper = getStudentMapper();
        //封装参数
        Map<String, Object> map = new HashMap<>();
        //1.数组
        Long[] ids = {100001L, 100002L, 100004L, 8989L};
        map.put("arrIds", ids);
        //2.集合
        List<Long> list = Arrays.asList(100015L, 100023L, 100101L, 100169L);
        map.put("listIds", list);
        studentMapper.getStudentGradeVoByMapIds(map).forEach(System.out::println);
    }

    //批量新增
    @Test
    public void test23(){
        StudentDao studentMapper = getStudentMapper();
        //封装参数
        List<Student> student = new ArrayList<Student>();
        //第一个学生
        Student student1 = new Student();
        student1.setStudentName("王老七");
        student1.setStudentBirth("1995-01-01");
        student1.setStudentAddress("二仙桥101号");
        student1.setStudentNo(9525L);
        student1.setGradeId(6L);
        student1.setStudentAge(18);
        student.add(student1);
        //第二个学生
        Student student2 = new Student();
        student2.setStudentName("王老八");
        student2.setStudentBirth("1995-01-01");
        student2.setStudentAddress("二仙桥102号");
        student2.setStudentNo(9524L);
        student2.setGradeId(6L);
        student2.setStudentAge(18);
        student.add(student2);
        int i = studentMapper.insertStudentBatch(student);
        System.out.println("批量新增执行成功: 成功添加了" + i + "条数据");
    }

    //choose 根据学生的id查询学生的基本信息
    @Test
    public void test24(){
        StudentDao studentMapper = getStudentMapper();
        System.out.println(studentMapper.getStudentGradeVoChoose(100006L));
    }

    //注解开发
    @Test
    public void test25(){
        StudentDao studentMapper = getStudentMapper();
        studentMapper.getStudentByStudentNameComment("张").forEach(System.out::println);
    }

    //分页查询
    @Test
    public void test26(){
        StudentDao studentMapper = getStudentMapper();
        //封装参数
        StudentDto studentDto = new StudentDto();
        Student student = new Student();
        student.setStudentName("张");
        studentDto.setStudent(student);
        //当前页码
        int pageNo = 2;
        //每页条数
        int pageSize = 10;
        studentDto.setPageSize(pageSize);
        studentDto.setPageNo(PageUtils.getStartPage(pageNo, pageSize));
        studentMapper.getStudentGradeVoLimit(studentDto).forEach(System.out::println);
    }



}


