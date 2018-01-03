package com.dhee.dao;

import java.util.List;

import org.springframework.orm.hibernate3.HibernateTemplate;

import com.dhee.vo.OrderItemVo;
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
	 * ��Ӷ�����
	 * @param itemVo
	 * @throws Exception
	 */
	public void addOrderItem(OrderItemVo itemVo) throws Exception{
		
		this.hibernateTemplate.save(itemVo);
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
	
	/**
	 * ��ѯָ��������ŵĶ�����Ϣ
	 * @param orderId
	 * @return
	 * @throws Exception
	 */
	public OrdersVo findOrderByOrderId(int orderId) throws Exception{
		
		String hql = "from OrdersVo where id=?";
		List<OrdersVo> list = this.hibernateTemplate.find(hql, orderId);
		OrdersVo order = null;
		for(OrdersVo or : list) {
			order = or;
		}
		
		return order;
	}

	/**
	 * ��ѯָ��������
	 * @param id
	 * @return
	 */
	public List<OrderItemVo> findOrderItem(int id) {
		
		String hql = "from OrderItemVo where order_id=?";
		List<OrderItemVo> list = this.hibernateTemplate.find(hql, id);
		return list;
	}

	/**
	 * ��̨��ѯ���ж���
	 * @param st
	 */
	public List<OrdersVo> listAll(boolean st) {
		
		String hql = "from OrdersVo where state=?";
		List<OrdersVo> list = this.hibernateTemplate.find(hql, st);
		return list;
	}
	
	public void ship(int id) throws Exception{
		
		OrdersVo order = this.findOrderByOrderId(id);
		order.setState(true);
		
		this.hibernateTemplate.update(order);
	}

	/**
	 * �û�ɾ��������Ϣ,�Ͷ�������Ϣ
	 * @param id
	 */
	public void delete(int id) throws Exception{
		
		OrdersVo order = this.findOrderByOrderId(id);
		this.hibernateTemplate.delete(order);
		
		//ɾ��������
		List<OrderItemVo> list = this.findOrderItem(id);
		this.hibernateTemplate.deleteAll(list);
	}
}
