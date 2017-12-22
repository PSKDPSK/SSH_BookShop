package com.dhee.dao;

import java.util.List;

import org.springframework.orm.hibernate3.HibernateTemplate;

import com.dhee.vo.OrdersVo;

public class OrderDao {

	private HibernateTemplate hibernateTemplate;

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}
	
	/**
	 * ��Ӷ���
	 * @param order
	 */
	public void addOrder(OrdersVo order) throws Exception{
		
		this.hibernateTemplate.save(order);
	}

	/**
	 * �鿴����
	 * idΪָ���û��ı��
	 * @param id
	 * @return
	 */
	public List<OrdersVo> findOrder(int id) throws Exception{
		
		String hql = "from OrdersVo where user_id=?";
		List<OrdersVo> list = this.hibernateTemplate.find(hql, new Object[] {id});
		
		return list;
	}
}
