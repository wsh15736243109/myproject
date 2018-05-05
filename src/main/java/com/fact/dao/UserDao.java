package com.fact.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.fact.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;


@Repository
public class UserDao {
	String table_nameForUser = "t_user";
	String table_nameForLog = "t_log";
	@Autowired
	private JdbcTemplate jdbcTemplate;
	String sqlStr = " SELECT COUNT(*) FROM " + table_nameForUser + " WHERE user_name=? and password =?";

	String sqlStrForUser = " SELECT * FROM " + table_nameForUser + " WHERE user_name=?";
	String sqlForAllUser = "SELECT * FROM " + table_nameForUser ;
	// String sqlStr_query_all = " SELECT * FROM "+table_name+" WHERE
	// user_name=? and password =?";

	String updateLoginLogInfoSql = " UPDATE " + table_nameForLog
			+ " SET last_visit= ?,last_ip= ?,credits= ? WHERE user_id= ? ";

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public Integer getMatchCount(String userName, String password) {
		System.out.println("userName" + userName + "password" + password);
		return jdbcTemplate.queryForObject(sqlStr, new Object[] { userName, password }, Integer.class);
	}

	public User findUserByUserName(String userName) {
		final User user = new User();
		jdbcTemplate.query(sqlStrForUser, new Object[] { userName }, new RowCallbackHandler() {
			public void processRow(ResultSet resultSet) throws SQLException {
				user.setUserId(resultSet.getLong("user_id"));
				user.setUserName(resultSet.getString("user_name"));
				user.setCredits(resultSet.getInt("credits"));
			}
		});
		return user;
	}

	public void updateLoginInfo(User user) {
		jdbcTemplate.update(updateLoginLogInfoSql,
				new Object[] { user.getLastVisit(), user.getLastIp(), user.getCredits(), user.getUserId() });
	}
	
	public ArrayList<User> queryAllUser(){
		List<Map<String,Object>> lists=jdbcTemplate.queryForList(sqlForAllUser);
		return toObject(lists);
	}
	
	public User toObject(Map map) {  
		User userInfo = new User();  
	    userInfo.setUserId((Long) map.get("user_id"));  
	    userInfo.setUserName((String) map.get("user_name"));  
	    userInfo.setCredits((Integer) map.get("credits"));  
	    userInfo.setLastVisit((Date) map.get("last_visit"));  
	    return userInfo;  
	}  
	  
	public ArrayList<User> toObject(List<Map<String,Object>> lists){  
		ArrayList<User> userInfos = new ArrayList();  
	    for (Map map : lists) {  
	        User userInfo =  toObject(map);  
	        if (userInfo != null) {  
	            userInfos.add(userInfo);  
	        }  
	    }  
	    return userInfos;  
	} 
}
