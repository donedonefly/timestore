package com.earl.timestore.dao;

import java.util.List;
import java.util.Map;

import com.earl.timestore.entity.TbChat;
import com.earl.timestore.entity.TbComment;
import com.earl.timestore.entity.TbDemand;
import com.earl.timestore.entity.TbLike;
import com.earl.timestore.entity.TbRequest;
import com.earl.timestore.entity.TbSystem;
import com.earl.timestore.entity.TbUser;

public interface GetEntityDao {

	public abstract TbUser getUser();
	
	public abstract List<TbUser> getFakeUsers();

	public abstract TbChat getChat();
	
	public abstract String getChatMessageByChatId(int chatId);

	public abstract TbRequest getRequest();
	
	public abstract List<TbRequest> getRequestByDemandId(int demandId);

	public abstract TbSystem getSystem();
	
	public abstract TbComment getComment();
	
	public abstract List<TbComment> getCommentsByDemandId(int demandId);
	
	public abstract TbLike getLike();
	
	public abstract List<TbLike> getLikesByDemandId(int demandId);

	public abstract TbDemand getDemand();
	
	public abstract List<TbDemand> getDemands();
	
	public abstract Map<String, Object> getDemandsAndRequestNum();

	public abstract List<TbUser> getUsers(int page, int num);

	/**
	 * 获得所有聊天信息
	 * @return List<TbChat> 聊天信息集合
	 */
	public abstract List<TbChat> getChats();
	
	/**
	 * 通过用户名或账号获得聊天信息
	 * @param accountOrUserName 用户名或账号
	 * @return
	 */
	public abstract List<TbChat> getChatsByAccountOrUserName(String accountOrUserName);

}
