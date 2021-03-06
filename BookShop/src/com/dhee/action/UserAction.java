package com.dhee.action;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.SessionAware;

import com.dhee.dao.UserDao;
import com.dhee.vo.UsersVo;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 
 * 关于用户的处理
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
	 * 用户注册
	 * @return
	 * @throws Exception
	 */
	public String add() throws Exception{
		
		//密码不一致
		if(!repassword.equals(user.getPassword())) {
			return "registerError";
		}
		
		
		userDao.addUser(user);
		//request.put("message", "注册成功！！！");
		
		return SUCCESS;
	}
	
	/**
	 * 用户登录
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
		
		//如果没有该用户
		if(u == null) {
			request.put("message", "用户名或密码错误！！！");
			return "nouser";
		}
		
		//如果是管理员
		if(u != null) {
			if(u.getUlimit() == 1) {
				session.put("user", u);
				return "admin";
			}
		}
		//System.out.println(u.size());
		session.put("user", u);
		return SUCCESS;
	}
	
	/**
	 * 用户注销
	 * @return
	 * @throws Exception
	 */
	public String loginOut() throws Exception{
		
		session.remove("user");
		session.remove("cart");
		
		return SUCCESS;
	}
	
	/**
	 * 查询所有用户
	 * @return
	 * @throws Exception
	 */
	public String findAll() throws Exception{
		
		List<UsersVo> list = userDao.findAll();
		request.put("users", list);
		
		return SUCCESS;
	}
	
	/**
	 * 验证用户名是否存在
	 * @return
	 * @throws Exception
	 */
	public String checkName() throws Exception{
		
		UsersVo u = userDao.checkName(user.getUsername());
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		if(u != null) {
			writer.print("no");
		}else {
			writer.print("yes");
		}
		
		return NONE;
	}

	
}
