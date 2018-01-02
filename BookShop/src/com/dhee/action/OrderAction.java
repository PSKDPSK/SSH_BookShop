package com.dhee.action;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.SessionAware;

import com.dhee.dao.OrderDao;
import com.dhee.dao.RewardDao;
import com.dhee.vo.Cart;
import com.dhee.vo.CartItem;
import com.dhee.vo.OrderItemVo;
import com.dhee.vo.OrdersVo;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 
 * �������
 * @author wwr
 *
 */
public class OrderAction extends ActionSupport implements SessionAware,RequestAware{

	private OrderDao orderDao;
	private String userid;
	private Map<String,Object> session;
	private Map<String,Object> request;
	private int order_id = 27;	//������ţ�����һ��bug��Ӧ���ö�������Զ����ɣ����������ݿ����Զ�����
	//���ֵ���ŷ�������������ʱ����Ҫ��
	private String orderid;
	

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public OrderDao getOrderDao() {
		return orderDao;
	}

	public void setOrderDao(OrderDao orderDao) {
		this.orderDao = orderDao;
	}
	
	public void setSession(Map<String, Object> session) {
		
		this.session = session;
		
	}
	
	public void setRequest(Map<String, Object> request) {
		
		this.request = request;
		
	}
	
	/**
	 * ��Ӷ���
	 * @return
	 * @throws Exception
	 */
	public String confirm() throws Exception{
		
		Cart cart = (Cart) session.get("cart");
		
		int id = Integer.parseInt(userid);
		
		OrdersVo order = new OrdersVo();
		order.setPrice(cart.getPrice());
		order.setState(false);
		order.setUser_id(id);
		order.setOrdertime(new Date());
		
		orderDao.addOrder(order);
		
		order_id = order_id + 1;
		//������
		for(Map.Entry<Integer, CartItem> me : cart.getMap().entrySet()) {
			
			//�õ�һ�������������һ��������
			CartItem citem = me.getValue();
			OrderItemVo item = new OrderItemVo();
			//item.setBook(citem.getBook());
			item.setPrice(citem.getPrice());
			item.setQuantity(citem.getQuantity());
			item.setBook_id(citem.getBook().getId());
			item.setOrder_id(order_id);
			
			orderDao.addOrderItem(item);
			//order.getOrderitems().add(item);
		}
		
		
		request.put("message", "���������ɣ���ȴ�����������");
		session.remove("cart");
		
		return SUCCESS;
	}
	
	/**
	 * �鿴����
	 * @return
	 * @throws Exception
	 */
	public String findOrder() throws Exception{
		
		int id = Integer.parseInt(userid);
		List<OrdersVo> list = orderDao.findOrder(id);
		
		request.put("orders", list);
		
		return SUCCESS;
	}
	
	/**
	 * �鿴������
	 * @return
	 * @throws Exception
	 */
	public String orderItemFind() throws Exception{
		
		int id = Integer.parseInt(orderid);
		List<OrderItemVo> list = orderDao.findOrderItem(id);
		
		request.put("item", list);
		
		for(OrderItemVo item : list) {
			System.out.println(item.getBook_id());
		}
		
		return NONE;
	}

}
