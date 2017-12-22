package com.dhee.action;

import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.RequestAware;

import com.dhee.dao.IndexDao;
import com.dhee.vo.BooksVo;
import com.opensymphony.xwork2.ActionSupport;

/**
 * �����:١���£�������
 * ��ҳ���������ͼ��
 * @author wwr
 *
 */
public class IndexAction extends ActionSupport implements RequestAware {

	private IndexDao indexDao;
	
	private List<BooksVo> books;
	
	private Map<String,Object> request;
	
	public void setRequest(Map<String, Object> request) {
	
		this.request = request;
		
	}
	
	public List<BooksVo> getBooks() {
		return books;
	}



	public void setBooks(List<BooksVo> books) {
		this.books = books;
	}



	public IndexDao getIndexDao() {
		return indexDao;
	}



	public void setIndexDao(IndexDao indexDao) {
		this.indexDao = indexDao;
	}



	/**
	 * �������ͼ��
	 * @return
	 * @throws Exception
	 */
	public String index() throws Exception{
		
		books = indexDao.selectAllBooks();
		request.put("books", books);
		
		return SUCCESS;
	}

}
