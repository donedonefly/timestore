package com.earl.timestore.service;

import java.util.List;
import com.earl.timestore.dao.impl.UserDao;
import com.earl.timestore.entity.TbChat;
import com.earl.timestore.entity.TbComment;
import com.earl.timestore.entity.TbDemand;
import com.earl.timestore.entity.TbRequest;
import com.earl.timestore.entity.TbUser;

public class UserServlet {
  
	private UserDao userDao;

	
	
	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}


	public List<TbUser> getAllUser(){ //获取所有用户信息
		return userDao.getAllUser();
	}
	
	public TbUser getUser(Integer id){
		return userDao.getUserByid(id);
	}
	
	public void DeleteUser(int id){
		userDao.DeleteUser(id);
	}
	
	public long getUserAccount(){
		return userDao.getAccount();
	}
	
	public long getSchoolAccount(String school){
		return userDao.getSchoolAccount(school);
	}
	
	public long getDayAccount(String time){
		return userDao.getDayAccount(time);
	}
	
	public List<TbUser> geTbUser(String name){
		return userDao.getUserByname(name);
	}
	
	public List<TbUser> getUserbyDate(String time){
		return userDao.getUserbyDate(time);
	}
	
	public List<TbComment> getCommentByid(int id) {
		return userDao.getCommentByid(id);
	}
	
	public List<TbRequest> getTbRequestByid(int id) {
		return userDao.getTbRequestByid(id);
	}
	
	public List<TbChat> getTbChatByid(int id) {
		return userDao.getTbChatByid(id);
	}
	
	public List<TbDemand> getTbDemandByid(int id) {
		return userDao.getTbDemandByid(id);
	}
	
	public long CountCommentByid(int id) {
		return userDao.CountCommentByid(id);
	}
	
	public long CountTbRequestByid(int id) {
		return userDao.CountTbRequestByid(id);
	}
	
	public long CountTbChatByid(int id) {
		return userDao.CountTbChatByid(id);
	}
	
	public long CountTbDemandByid(int id) {
		return userDao.CountTbDemandByid(id);
	}
	
}
