package com.dhee.action;

import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.SessionAware;

import com.dhee.dao.BookDao;
import com.dhee.vo.BooksVo;
import com.dhee.vo.Cart;
import com.dhee.vo.UsersVo;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 
 * ��ͼ����������ﳵ
 * @author wwr
 *
 */
public class BuyAction extends ActionSupport implements RequestAware,SessionAware{

	//private Cart cart;
	private BookDao bookDao;
	private Map<String,Object> request;
	private Map<String,Object> session;
	private String bookid;
	
	

	public BookDao getBookDao() {
		return bookDao;
	}

	public void setBookDao(BookDao bookDao) {
		this.bookDao = bookDao;
	}

	public String getBookid() {
		return bookid;
	}

	public void setBookid(String bookid) {
		this.bookid = bookid;
	}
	
	public void setRequest(Map<String, Object> request) {
		
		this.request = request;
		
	}

	public void setSession(Map<String, Object> session) {
		
		this.session = session;
		
	}
	
	/*
	 * ��������ﳵ
	 */
	public String buy() throws Exception {
		
		//��֤�û��Ƿ��¼
		UsersVo user = (UsersVo) session.get("user");
		if(user == null) {
			request.put("message", "�Բ������ȵ�¼������");
			return "nologin";
		}
		
		//��ѯ��ָ����ͼ����Ϣ
		int id = Integer.parseInt(bookid);
		List<BooksVo> list = bookDao.findById(id);

		//������ͼ����Ϣ
		BooksVo book = null;
		for(BooksVo b : list) {
			book = new BooksVo();
			book = b;
		}
		
		
		
		//��ȡ�û����ﳵ
		Cart cart = (Cart) session.get("cart");
		if(cart == null) {
			cart = new Cart();
			session.put("cart", cart);
		}
		
		cart.add(book);
		
		return SUCCESS;
	}
	
	/**
	 * ɾ�����ﳵ�е���Ʒ
	 * @return
	 */
	public String delete() {
		
		Cart cart = (Cart) session.get("cart");
		int id = Integer.parseInt(bookid);
		cart.deleteBook(id);
		
		
		return SUCCESS;
	}


}
