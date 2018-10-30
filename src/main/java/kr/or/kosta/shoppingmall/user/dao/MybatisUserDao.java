package kr.or.kosta.shoppingmall.user.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;

import kr.or.kosta.shoppingmall.common.web.Params;
import kr.or.kosta.shoppingmall.user.domain.User;

public class MybatisUserDao implements UserDao {
	
	SqlSessionFactory sqlSessionFactory;
	private static final String NAMESPACE = "kr.or.kosta.shoppingmall.user.";
	Logger logger = Logger.getLogger(MybatisUserDao.class);
	
	public SqlSessionFactory getSqlSessionFactory() {

		return sqlSessionFactory;
	}

	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
	}

	public void create(User user) throws Exception {
		SqlSession sqlSession = sqlSessionFactory.openSession(); 
		sqlSession.insert(NAMESPACE+"create", user);
		sqlSession.close();
	}

	public User read(String id) throws Exception {
		SqlSession sqlSession = sqlSessionFactory.openSession(); 
		User user = sqlSession.selectOne(NAMESPACE+"read", id);
		sqlSession.close();
		return user;
	}

	public void update(User user) throws Exception {
		SqlSession sqlSession = sqlSessionFactory.openSession(); 
		sqlSession.update(NAMESPACE+"update", user);
		sqlSession.close();
	}

	public void delete(String id) throws Exception {
		SqlSession sqlSession = sqlSessionFactory.openSession(); 
		sqlSession.delete(NAMESPACE+"delete", id);
		sqlSession.close();
	}

	public List<User> listAll() throws Exception {
		SqlSession sqlSession = sqlSessionFactory.openSession(); 
		List<User> users = sqlSession.selectList(NAMESPACE+"listAll");
		sqlSession.close();
		return users;
	}
	

	public User certify(String id, String passwd) throws Exception {
		Map<String, String> params = new HashMap<String, String>();
		params.put("id", id);
		params.put("passwd", passwd);
		SqlSession sqlSession = sqlSessionFactory.openSession(); 
		User user = sqlSession.selectOne(NAMESPACE+"read", params);
		sqlSession.close();
		return user;
	}
	
	
	public List<User> listByPage(int page) throws Exception {
		Map<String, Integer> params = new HashMap<String, Integer>();
		params.put("page", page);
		SqlSession sqlSession = sqlSessionFactory.openSession(); 
		List<User> users = sqlSession.selectList(NAMESPACE+"listByPage", params);
		sqlSession.close();
		return users;
		
	}

	public List<User> listByPage(int page, int listSize) throws Exception {
		Map<String, Integer> params = new HashMap<String, Integer>();
		params.put("page", page);
		params.put("listSize", listSize);
		SqlSession sqlSession = sqlSessionFactory.openSession(); 
		List<User> users = sqlSession.selectList(NAMESPACE+"listByPage", params);
		sqlSession.close();
		return users;
	}

	public List<User> listByPage(int page, int listSize, String searchType, String searchValue) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("page", page);
		params.put("listSize", listSize);
		params.put("searchType", searchType);
		params.put("searchValue", searchValue);
		SqlSession sqlSession = sqlSessionFactory.openSession(); 
		List<User> users = sqlSession.selectList(NAMESPACE+"listByPage", params);
		sqlSession.close();
		return users;
	}

	public List<User> listByPage(Params params) throws Exception {
		return listByPage(params.getPage(), params.getListSize(),  params.getSearchType(), params.getSearchValue());
	}

	public int countBySearch(String searchType, String searchValue) throws Exception {
		Map<String, String> params = new HashMap<String, String>();
		params.put("searchType", searchType);
		params.put("searchValue", searchValue);
		SqlSession sqlSession = sqlSessionFactory.openSession(); 
		int result = sqlSession.selectOne(NAMESPACE+"listByPage", params);
		sqlSession.close();
		return result;
	}

	public int countBySearch(Params params) throws Exception {
		return countBySearch(params.getSearchType(), params.getSearchValue());
	}
	
	
}










