package com.earl.timestore.dao.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.earl.timestore.entity.TbChat;
import com.earl.timestore.entity.TbComment;
import com.earl.timestore.entity.TbDemand;
import com.earl.timestore.entity.TbRequest;
import com.earl.timestore.entity.TbUser;

public class UserDao extends BaseDao {

	public List<TbUser> getAllUser() { // ��ȡ�����û���Ϣ
		String hql = "FROM TbUser";
		List<TbUser> users = getSession().createQuery(hql).list();
		for (TbUser user : users) {
			user.setTbLikes(null);
		}
		return users;
	}

	public TbUser getUserByid(int id) { // ͨ��id�õ���Ϣ
		return getSession().get(TbUser.class, id);
	}

	public List<TbUser> getUserByname(String name) { // ͨ���û����ֽ���ģ������
		String hql = "FROM TbUser a where a.userName like ?";
		Query query = getSession().createQuery(hql).setString(0, "%" + name + "%");
		List<TbUser> user = (List<TbUser>) query.list();
		return user;
	}

	public void DeleteUser(int id) { // ɾ���û�������Ϣ����
		String deleteByUesrIdHQL = "update TbComment comment set comment.tbUserByCommentParent=null where comment.tbUserByCommentUser.userId=?";
		String hql2 = "delete FROM TbComment b where b.tbUserByCommentUser=?";
		String hql5 = "delete FROM TbRequest b where b.tbUser=?";
		String hql3 = "delete FROM TbChat c where c.tbUserByChatTo=? or tbUserByChatFrom=?";
		String hql6 = "delete FROM TbLike b where b.tbUser.userId=?";
		String hql4 = "delete FROM TbDemand d where d.tbUserByDemandUser.userId=? or tbUserByDemandClient.userId=?";
		String hql1 = "delete FROM TbUser a where a.userId=?";
		
		getSession().createQuery(deleteByUesrIdHQL).setInteger(0, id).executeUpdate();
		getSession().createQuery(hql2).setInteger(0, id).executeUpdate();
		getSession().createQuery(hql5).setInteger(0, id).executeUpdate();
		getSession().createQuery(hql6).setInteger(0, id).executeUpdate();
		getSession().createQuery(hql3).setInteger(0, id).setInteger(1, id).executeUpdate();
		getSession().createQuery(hql4).setInteger(0, id).setInteger(1, id).executeUpdate();
		getSession().createQuery(hql1).setInteger(0, id).executeUpdate();
	}

	public long getAccount() { // ��ȡ�û�����
		String hql = "select count(userId) from TbUser";
		return (Long) getSession().createQuery(hql).uniqueResult();
	}

	public List<TbUser> getUserbyDate(String time) {
		String start = time + " 00:00:00";
		String end = time + " 23:59:59";
		String hql = "FROM TbUser c where c.userLastLoginTime >= '" + start + "' and c.userLastLoginTime <='" + end
				+ "'";
		return (List<TbUser>) getSession().createQuery(hql).list();
	}

	public long getSchoolAccount(String userSchool) { // ͳ��ÿ��ѧУ���û���
		String hql = "select count(userId) from TbUser c where c.userSchool=?";
		return (Long) getSession().createQuery(hql).setString(0, userSchool).uniqueResult();
	}

	public long getDayAccount(String time) { // ͳ�Ƶ�����û���
		String start = time + " 00:00:00";
		String end = time + " 23:59:59";
		String hql = "select count(userId) FROM TbUser c where c.userLastLoginTime >= '" + start
				+ "' and c.userLastLoginTime <='" + end + "'";
		return (Long) getSession().createQuery(hql).uniqueResult();
	}
	
	
	public List<TbComment> getCommentByid(int id) {// ͨ��id�õ�����
		   String hql="FROM TbComment b where b.tbUserByCommentUser=?";
			return getSession().createSQLQuery(hql).setInteger(0, id).list();
		}
		
		public long CountCommentByid(int id) {// ͨ��id�õ�������
			   String hql="select count(*) FROM TbComment";
				return (Long) getSession().createSQLQuery(hql).uniqueResult();
			}
		
		
		public List<TbRequest> getTbRequestByid(int id) {// ͨ��id�õ�����
			   String hql="FROM TbRequest b where b.tbUser=?";
				return getSession().createSQLQuery(hql).setInteger(0, id).list();
			}
		
		public long CountTbRequestByid(int id) {// ͨ��id�õ�������
			   String hql="select count(*)FROM TbRequest b where b.tbUser=?";
				return (Long) getSession().createSQLQuery(hql).setInteger(0, id).uniqueResult();
			}
		
		
		public List<TbChat> getTbChatByid(int id) {// ͨ��id������
			   String hql="FROM TbChat b where b.tbUserByChatTo=? or b.tbUserByChatFrom=?";
				return getSession().createSQLQuery(hql).setInteger(0, id).setInteger(1,id).list();
			}
		
		public long CountTbChatByid(int id) {// ͨ��id�õ�������
			   String hql="select count(*)FROM TbChat b where b.tbUserByChatTo=? or b.tbUserByChatFrom=?";
				return (Long) getSession().createSQLQuery(hql).setInteger(0, id).setInteger(1,id).uniqueResult();
			}
		
		public List<TbDemand> getTbDemandByid(int id) {// ͨ��id�õ�����
			   String hql="FROM TbDemand b where b.tbUserByDemandUser=?";
				return getSession().createSQLQuery(hql).setInteger(0, id).list();
			}
		
		public long CountTbDemandByid(int id) {// ͨ��id�õ�������
			   String hql="select count(*)FROM TbDemand b where b.tbUserByDemandUser=?";
				return (Long) getSession().createSQLQuery(hql).setInteger(0, id).uniqueResult();
			}
}
