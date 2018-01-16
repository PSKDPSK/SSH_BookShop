package com.dhee.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.SessionAware;

import com.dhee.dao.BookDao;
import com.dhee.dao.OrderDao;
import com.dhee.dao.RewardDao;
import com.dhee.dao.UserDao;
import com.dhee.vo.BooksVo;
import com.dhee.vo.Cart;
import com.dhee.vo.CartItem;
import com.dhee.vo.OrderItemVo;
import com.dhee.vo.OrdersVo;
import com.dhee.vo.RewardVo;
import com.dhee.vo.UsersVo;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 
 * �������
 * @author wwr
 *
 */
public class OrderAction extends ActionSupport implements SessionAware,RequestAware{

	private OrderDao orderDao;
	private BookDao bookDao;
	private UserDao userDao;
	private RewardDao rewardDao;
	private String userid;
	private Map<String,Object> session;
	private Map<String,Object> request;
	private int order_id = 40;	//������ţ�����һ��bug��Ӧ���ö�������Զ����ɣ����������ݿ����Զ�����
	//���ֵ���ŷ�������������ʱ����Ҫ�ģ������ݿ��ж������������ͬ
	private String orderid;
	private String state;	//��ȡ����״̬
	
	private String address;
	
	
	

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setRewardDao(RewardDao rewardDao) {
		this.rewardDao = rewardDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public void setBookDao(BookDao bookDao) {
		this.bookDao = bookDao;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

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
		
		String newAddress = new String(address.getBytes("iso-8859-1"), "UTF-8");
		if(newAddress == null || newAddress == "") {
			request.put("message", "��ѡ���ջ���ַ������");
			return "addressError";
		}
		order.setAddress(newAddress);
		
		
		orderDao.addOrder(order);
		
		order_id = order_id + 1;
		//������
		for(Map.Entry<Integer, CartItem> me : cart.getMap().entrySet()) {
			
			//�õ�һ�������������һ��������
			CartItem citem = me.getValue();
			OrderItemVo item = new OrderItemVo();
			item.setPrice(citem.getPrice());
			item.setQuantity(citem.getQuantity());
			item.setBook_id(citem.getBook().getId());
			item.setOrder_id(order_id);
			
			orderDao.addOrderItem(item);
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
		OrdersVo order = orderDao.findOrderByOrderId(id);
		
		request.put("item", list);
		request.put("order", order);
		
		//��ѯ�û���Ϣ
		UsersVo user = userDao.findById(order.getUser_id());
		request.put("user", user);
		
		//��ѯ�û���ַ
		List<RewardVo> rewardList = rewardDao.findAddress(user.getId());
		RewardVo reward = null;
		for(RewardVo re : rewardList) {
			reward = re;
		}
		request.put("reward", reward);
		
		return SUCCESS;
	}
	
	/**
	 * ��ѯ���ж���
	 * @return
	 * @throws Exception
	 */
	public String listAll() throws Exception{
		
		boolean st = Boolean.parseBoolean(state);
		List<OrdersVo> list = orderDao.listAll(st);
		
		request.put("orders", list);
		
		return "listOrders";
	}
	
	/**
	 * ����
	 * @return
	 * @throws Exception
	 */
	public String ship() throws Exception{
	
		int id = Integer.parseInt(orderid);
		orderDao.ship(id);
		request.put("message", "��ȷ�Ϸ���!!!");
		return "ship";
	}
	
	public String delete() throws Exception{
		
		int id = Integer.parseInt(orderid);
		orderDao.delete(id);
		request.put("message", "ɾ���ɹ�!!!");
		
		return "delete";
	}

}
