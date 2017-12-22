package com.dhee.action;

import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.SessionAware;

import com.dhee.dao.UserDao;
import com.dhee.vo.UsersVo;
import com.opensymphony.xwork2.ActionSupport;

/**
 * �����:����ܰ�����ٲ�
 * �����û��Ĵ���
 * @author wwr
 *
 */
public class UserAction extends ActionSupport implements RequestAware,SessionAware{

	private UserDao userDao;
	private UsersVo user;
	private String repassword;
	private Map<String,Object> request;
	private Map<String,Object> session;
	

	public String getRepassword() {
		return repassword;
	}

	public void setRepassword(String repassword) {
		this.repassword = repassword;
	}

	public UsersVo getUser() {
		return user;
	}

	public void setUser(UsersVo user) {
		this.user = user;
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	
	public void setRequest(Map<String, Object> request) {
		
		this.request = request;
	}

	public void setSession(Map<String, Object> session) {
		
		this.session = session;
		
	}
	
	/**
	 * �û�ע��
	 * @return
	 * @throws Exception
	 */
	public String add() throws Exception{
		
		//���벻һ��
		if(!repassword.equals(user.getPassword())) {
			return "registerError";
		}
		
		
		userDao.addUser(user);
		//request.put("message", "ע��ɹ�������");
		
		return SUCCESS;
	}
	
	/**
	 * �û���¼
	 * @return
	 * @throws Exception
	 */
	public String login() throws Exception{
		
		List<UsersVo> list = userDao.login(user);
		
		UsersVo u = null;
		for(UsersVo user : list) {
			u = new UsersVo();
			u = user;
		}
		
		//���û�и��û�
		if(u == null) {
			request.put("message", "�û�����������󣡣���");
			return "nouser";
		}
		
		//����ǹ���Ա
		if(u != null) {
			if(u.getUlimit() == 1) {
				request.put("message", "��ӭ��������Ա������");
				return "admin";
			}
		}
		//System.out.println(u.size());
		session.put("user", u);
		return SUCCESS;
	}
	
	/**
	 * �û�ע��
	 * @return
	 * @throws Exception
	 */
	public String loginOut() throws Exception{
		
		session.remove("user");
		session.remove("cart");
		
		return SUCCESS;
	}

	
}
