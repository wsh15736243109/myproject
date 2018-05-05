package com.fact.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.fact.domain.LoginLog;

@Repository
public class LoginLogDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;


    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private String inset_login_log_sql = "INSERT INTO t_log (user_id,last_ip) values(?,?)";

    public void insertLoginLog(LoginLog loginLog) {
        Object[] objects = new Object[]{loginLog.getUserId(), loginLog.getIp()};
        jdbcTemplate.update(inset_login_log_sql, objects);
    }
}
