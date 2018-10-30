package kr.or.kosta.shoppingmall;

import java.io.IOException;
import java.io.Reader;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import kr.or.kosta.shoppingmall.employee.domain.Employee;
import kr.or.kosta.shoppingmall.user.dao.MybatisUserDao;
import kr.or.kosta.shoppingmall.user.dao.UserDao;
import kr.or.kosta.shoppingmall.user.domain.User;

public class UserDaoTest {
	private static final String NAMESPACE = "kr.or.kosta.shoppingmall.user.";

	UserDao userDao;
	String resource = "mybatis-config.xml";
	SqlSessionFactory sqlSessionFactory;
	Logger logger = Logger.getLogger(MybatisTest.class);

	@Before
	public void setUp() {
		Reader reader = null;
		try {
			reader = Resources.getResourceAsReader(resource);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader, "development");
		userDao = new MybatisUserDao();
		((MybatisUserDao)userDao).setSqlSessionFactory(sqlSessionFactory);
		logger.debug("sqlSessionFactory 생성 완료");
		
	}

	@Test
	public void testUser() {
		SqlSession sqlSession = sqlSessionFactory.openSession(); 
		List<User> users = sqlSession.selectList(NAMESPACE+"listAll");
		for (User user : users) {
			logger.debug(user);
		}
		sqlSession.close();
	}
//	@Test
	public void testSelectOne() {
		int num = 100;
		SqlSession sqlSession = sqlSessionFactory.openSession();
		Employee employee = sqlSession.selectOne(NAMESPACE+"selectEmployeeById", num);
		logger.debug(employee);
		sqlSession.close();
	}
//	@Test
	public void testSelectOne2() {
		int num = 100;
		SqlSession sqlSession = sqlSessionFactory.openSession();
		int salary = sqlSession.selectOne(NAMESPACE+"selectSalaryById", num);
		logger.debug(salary);
		sqlSession.close();
	}
//	@Test
	public void testSelectList2() {
		Map<String, Integer> params = new HashMap<String, Integer>();
		params.put("min", 3000);
		params.put("max", 4000);
		SqlSession sqlSession = sqlSessionFactory.openSession();
		List<Employee> employees = sqlSession.selectList(NAMESPACE+"selectEmployeesBySalary", params);
		for (Employee employee : employees) {
			logger.debug(employee);
		}
		sqlSession.close();
	}
//	@Test
	public void testSelectListLike() {
		String name = "%g%";
		SqlSession sqlSession = sqlSessionFactory.openSession();
		List<Employee> employees = sqlSession.selectList(NAMESPACE+"selectEmployeesByLastName", name);
		for (Employee employee : employees) {
			logger.debug(employee);
		}
		sqlSession.close();
	}
//	@Test
	public void testSelectListJoin() {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		List<Map<String,Object>> employees = sqlSession.selectList(NAMESPACE+"selectEmployeesWithDepartment");
		for (Map<String, Object> map : employees) {
			logger.debug(map);
			BigDecimal id = (BigDecimal) map.get("id");
			String firstName = (String) map.get("firstName");
			logger.debug(id);
			logger.debug(firstName);

		}
		sqlSession.close();
	}

//	@Test
	public void testUpdate() {
		Employee emp = new Employee();
		emp.setId(100);
		emp.setSalary(7777);
		SqlSession sqlSession = sqlSessionFactory.openSession(true);
		sqlSession.update(NAMESPACE+"updateEmployee",emp);
		sqlSession.commit();
		logger.debug("업데이트 완료");
		sqlSession.close();
	}
	
//	@Test
	public void testDynamic() {
		Map<String, String> params = new HashMap<String, String>();
		params.put("searchType", "name");
		params.put("searchValue", "%s%");
		SqlSession sqlSession = sqlSessionFactory.openSession();
		List<Employee> employees = sqlSession.selectList(NAMESPACE+"dynamicSQL", params);
		for (Employee employee : employees) {
			logger.debug(employee);
		}
		sqlSession.close();
	}
}
