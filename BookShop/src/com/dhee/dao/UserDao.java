package com.dhee.dao;

import java.util.List;

import org.springframework.orm.hibernate3.HibernateTemplate;

import com.dhee.vo.UsersVo;

public class UserDao {

	private HibernateTemplate hibernateTemplate;

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}
	
	/**�û�ע��**/
	public void addUser(UsersVo user) throws Exception{
		
		this.hibernateTemplate.save(user);
	}
	
	/**�û���¼**/
	public List<UsersVo> login(UsersVo user) throws Exception{
		
		String hql = "from UsersVo where username=? and password=?";
		Object[] values = {user.getUsername(),user.getPassword()};
		List<UsersVo> list = this.hibernateTemplate.find(hql, values);
		return list;
	}
}
