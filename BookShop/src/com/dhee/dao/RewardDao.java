package com.dhee.dao;

import java.util.List;

import org.springframework.orm.hibernate3.HibernateTemplate;

import com.dhee.vo.RewardVo;

public class RewardDao {

	private HibernateTemplate hibernateTemplate;

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}
	
	/**��ѯָ���û������е�ַ**/
	public List<RewardVo> findAddress(int userid) throws Exception{
		
		String hql = "from RewardVo where user_id=?";
		List<RewardVo> list = this.hibernateTemplate.find(hql, new Object[] {userid});
		
		return list;
	}

	/**
	 * ��ӵ�ַ
	 * @param reward
	 * @throws Exception
	 */
	public void addAddress(RewardVo reward) throws Exception{
		
		this.hibernateTemplate.save(reward);
		
	}
}
