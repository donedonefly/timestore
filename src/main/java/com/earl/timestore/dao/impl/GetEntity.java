package com.earl.timestore.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.earl.timestore.dao.GetEntityDao;
import com.earl.timestore.entity.TbChat;
import com.earl.timestore.entity.TbComment;
import com.earl.timestore.entity.TbDemand;
import com.earl.timestore.entity.TbLike;
import com.earl.timestore.entity.TbRequest;
import com.earl.timestore.entity.TbSystem;
import com.earl.timestore.entity.TbUser;

public class GetEntity extends SessionFactoryDaoImpl implements GetEntityDao {

	public TbUser getUser() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<TbUser> getFakeUsers() {
		String fakeUserHQL = "from TbUser user where user.isFake = 1";
		List<TbUser> users = getSession().createQuery(fakeUserHQL).list();
		for(TbUser user :users)
			user.setTbLikes(null);
		
		return users;
	}
	
	public TbChat getChat() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public String getChatMessageByChatId(int chatId) {
		String hql = "from TbChat chat where chat.chatid=?";
		TbChat chat = (TbChat) getSession().createQuery(hql).setInteger(0, chatId).uniqueResult();
		String charMessage = chat.getChatMessage();
		return charMessage;
	}

	public TbRequest getRequest() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public List<TbRequest> getRequestByDemandId(int demandId){
		String hql = "from TbRequest request LEFT OUTER JOIN FETCH request.tbUser where request.tbDemand.demandid=?";
		List<TbRequest> requests = getSession().createQuery(hql).setInteger(0, demandId).list();
		for(TbRequest request : requests){
			request.getTbUser().setTbLikes(null);
			request.setTbDemand(null);
		}
		
		return requests;
	}

	public TbSystem getSystem() {
		String hql = "from TbSystem";
		TbSystem system = (TbSystem) getSession().createQuery(hql).uniqueResult();

		return system;
	}

	public TbComment getComment() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<TbComment> getCommentsByDemandId(int demandId) {
		String hql = "from TbComment comment LEFT OUTER JOIN FETCH comment.tbUserByCommentUser where comment.tbDemand.demandid=?";
		List<TbComment> comments = getSession().createQuery(hql).setInteger(0, demandId).list();
		for(TbComment comment : comments){
			comment.setTbUserByCommentParent(null);
			comment.setTbDemand(null);
			comment.getTbUserByCommentUser().setTbLikes(null);
		}

		return comments;
	}

	public TbLike getLike() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public List<TbLike> getLikesByDemandId(int demandId){
		String hql = "from TbLike liker LEFT OUTER JOIN FETCH liker.tbUser LEFT OUTER JOIN FETCH liker.likeDemand demand where demand.demandid=? and liker.likeDeletestatus=0";
		List<TbLike> likes = getSession().createQuery(hql).setInteger(0, demandId).list();
		for(TbLike like : likes){
			like.setLikeDemand(null);
			like.getTbUser().setTbLikes(null);
		}
		
		return likes;
	}

	public TbDemand getDemand() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 获得所有需求信息
	 * 
	 * @return List<TbComment>
	 */
	public List<TbDemand> getDemands() {
		String hql = "from TbDemand demand LEFT OUTER JOIN FETCH demand.tbUserByDemandUser LEFT OUTER JOIN FETCH demand.tbUserByDemandClient";
		List<TbDemand> demands = getSession().createQuery(hql).list();

		for (TbDemand demand : demands) {
			demand.setTbComments(null);
			demand.setTbRequests(null);
			demand.getTbUserByDemandUser().setTbLikes(null);
			if(demand.getTbUserByDemandClient()!=null)
				demand.getTbUserByDemandClient().setTbLikes(null);
		}

		return demands;
	}
	
	/**
	 * 获得所有需求信息，包括请求数
	 * key:demands,requestNum
	 * @return Map<String, Object>
	 */
	public Map<String, Object> getDemandsAndRequestNum() {
		// 带有 请求数 和 需求集合的map
		Map<String, Object> demandAndList = new HashMap<String, Object>();
		
		List<TbDemand> demands = getDemands();
		List<Long> requestNum = new ArrayList<Long>();
		Long list = (long) 0;
		String requestNumHQL = "select count(*) from TbRequest request where request.tbDemand.demandid=?";
		
		// 获得请求数的集合
		int demandId = 0;
		for(TbDemand demand : demands){
			demandId = demand.getDemandid();
			list = (Long) getSession().createQuery(requestNumHQL).setInteger(0, demandId).uniqueResult();
			requestNum.add(list);
		}
		demandAndList.put("demands", demands);
		demandAndList.put("requestNum", requestNum);
		
		return demandAndList;
	}

	public List<TbUser> getUsers(int page, int num) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 获得所有聊天信息
	 * 
	 * @return List<TbChat> 聊天信息集合
	 */
	public List<TbChat> getChats() {
		String hql = "from TbChat chat LEFT OUTER JOIN FETCH chat.tbUserByChatTo LEFT OUTER JOIN FETCH chat.tbUserByChatFrom";
		List<TbChat> chats = getSession().createQuery(hql).list();
		for (TbChat chat : chats) {
			chat.getTbUserByChatTo().setTbLikes(null);
			chat.getTbUserByChatFrom().setTbLikes(null);
		}

		return chats;
	}

	/**
	 * 通过用户名或账号获得聊天信息
	 * 
	 * @param accountOrUserName
	 *            用户名或账号
	 * @return
	 */
	public List<TbChat> getChatsByAccountOrUserName(String accountOrUserName) {
		String hql = "from TbChat chat " + "LEFT OUTER JOIN FETCH chat.tbUserByChatTo "
				+ "LEFT OUTER JOIN FETCH chat.tbUserByChatFrom " + "where chat.tbUserByChatTo.userAccount=? "
				+ "or chat.tbUserByChatTo.userName=? " + "or chat.tbUserByChatFrom.userAccount=? "
				+ "or chat.tbUserByChatFrom.userName=?";
		List<TbChat> chats = getSession().createQuery(hql).setString(0, accountOrUserName)
				.setString(1, accountOrUserName).setString(2, accountOrUserName).setString(3, accountOrUserName).list();
		for (TbChat chat : chats) {
			chat.getTbUserByChatTo().setTbLikes(null);
			chat.getTbUserByChatFrom().setTbLikes(null);
		}

		return chats;
	}

}
