package com.dhee.dao;

import java.util.List;

import org.springframework.orm.hibernate3.HibernateTemplate;

import com.dhee.vo.BooksVo;

public class BookDao {

	private HibernateTemplate hibernateTemplate;

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}
	
	/**
	 * ��ѯͼ��
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public List<BooksVo> findById(int id) throws Exception{
		
		String hql = "from BooksVo where id=?";
		List<BooksVo> list = this.hibernateTemplate.find(hql, new Object[] {id});
		return list;
	}
	
	/**
	 * ���ͼ��
	 * @param book
	 * @throws Exception
	 */
	public void add(BooksVo book) throws Exception{
		
		this.hibernateTemplate.save(book);
	}
	
	public List<BooksVo> list() throws Exception{
		
		String hql = "from BooksVo";
		List<BooksVo> list = this.hibernateTemplate.find(hql);
		return list;
	}

	/**
	 * ɾ��ָ��ͼ���ͼ����Ϣ
	 * @param bookid
	 */
	public void delete(BooksVo book) {
		
		this.hibernateTemplate.delete(book);
		
	}

	/**
	 * �޸�ͼ��
	 * @param book
	 */
	public void update(BooksVo book) {
		
		this.hibernateTemplate.update(book);
		
	}
}
