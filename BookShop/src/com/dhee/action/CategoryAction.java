package com.dhee.action;

import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.RequestAware;

import com.dhee.dao.CategoryDao;
import com.dhee.vo.CategorysVo;
import com.opensymphony.xwork2.ActionSupport;

public class CategoryAction extends ActionSupport implements RequestAware{

	/**
	 * ͼ�����Ͳ���
	 */
	private static final long serialVersionUID = 6723460971449932420L;
	private CategoryDao categoryDao;
	private Map<String,Object> request;
	private CategorysVo category;
	private String id;
	
	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public CategorysVo getCategory() {
		return category;
	}

	public void setCategory(CategorysVo category) {
		this.category = category;
	}

	public void setCategoryDao(CategoryDao categoryDao) {
		this.categoryDao = categoryDao;
	}
	
	public void setRequest(Map<String, Object> request) {
		this.request = request;	
	}

	/**
	 * ���ͼ��
	 * @return
	 * @throws Exception
	 */
	public String add() throws Exception{
		
		categoryDao.add(category);
		request.put("message", "��ӳɹ�!!!");
		
		return "add";
	}
	
	/**
	 * �ṩ���ͼ��Ľ���
	 * @return
	 * @throws Exception
	 */
	public String addUI() throws Exception{
		
		int max = Integer.parseInt(categoryDao.maxSize());
		max = max + 1;
		request.put("max", max);
		
		return "addUI";
	}
	
	/**
	 * ��ѯ����ͼ������
	 * @return
	 * @throws Exception
	 */
	public String findAll() throws Exception{
		
		List<CategorysVo> list = categoryDao.findAll();
		request.put("categorys", list);
		
		return "findAll";
	}
	
	/**
	 * ͼ������ɾ��
	 * @return
	 * @throws Exception
	 */
	public String delete() throws Exception{
		
		categoryDao.delete(id);
		request.put("message", "ɾ���ɹ�!!!");
		
		return "delete";
	}
	
	/**
	 * ��ѯָ��ͼ������
	 * @return
	 * @throws Exception
	 */
	public String findById() throws Exception{
		
		CategorysVo ca = categoryDao.findById(id);
		request.put("ca", ca);
		
		return "findById";
	}
	
	public String update() throws Exception{
		
		categoryDao.update(category);
		request.put("message", "�޸ĳɹ�!!!");
		
		return "update";
	}

}
