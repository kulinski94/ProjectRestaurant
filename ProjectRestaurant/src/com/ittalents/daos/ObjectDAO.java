package com.ittalents.daos;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public abstract class ObjectDAO {
	protected JdbcTemplate jdbc;

	@Autowired
	public void setDataSource(DataSource ds) {
		this.jdbc = new JdbcTemplate(ds);
	}

}
