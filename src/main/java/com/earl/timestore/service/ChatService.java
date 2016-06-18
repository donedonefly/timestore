package com.earl.timestore.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.earl.timestore.dao.GetEntityDao;
import com.earl.timestore.dao.GetReportDao;
import com.earl.timestore.dao.SetEntityDao;
import com.earl.timestore.entity.TbChat;
import com.earl.timestore.entity.TbComment;
import com.earl.timestore.entity.TbDemand;
import com.earl.timestore.entity.TbLike;
import com.earl.timestore.entity.TbRequest;
import com.earl.timestore.entity.TbSystem;
import com.earl.timestore.entity.TbUser;

@Service
public class ChatService {

	@Autowired
	private GetEntityDao getEntityDao;
	
	@Autowired
	private SetEntityDao setEntityDao;
	
	@Autowired
	private GetReportDao getReportDao;
	
	public String getChatMessageByChatId(int chatId){
		return getEntityDao.getChatMessageByChatId(chatId);
	}
	
	/**
	 * 添加点赞和评论
	 * @param userIds  点赞或评论用户
	 * @param comments 评论内容
	 * @param demandId 需求id
	 * @return
	 */
	public boolean addLikesAndComments(List<Integer> userIds, String[] comments, int demandId){
		setEntityDao.addLikes(userIds, demandId);
		setEntityDao.addComments(comments, demandId);
		return false;
	}
	
	/**
	 * 获得所有假用户和其点赞信息
	 * @param demandId
	 * @return
	 */
	public Map<String, Object> getFakeUsersWithLike(int demandId) {
		Map<String, Object> fakeUserWithLike = new HashMap<String, Object>();
		List<TbUser> fakeUsers = getEntityDao.getFakeUsers();
		List<TbLike> likes = getEntityDao.getLikesByDemandId(demandId);
		List<TbComment> comments = getEntityDao.getCommentsByDemandId(demandId);
		List<Integer> isLikes = getLikesLinkToFakeUsers(fakeUsers, likes);
		List<String> commentsLinkToFakeUsers = getCommentsLinkToFakeUsers(fakeUsers,comments);
		fakeUserWithLike.put("users", fakeUsers);
		fakeUserWithLike.put("isLike", isLikes);
		fakeUserWithLike.put("comments", commentsLinkToFakeUsers);
		return fakeUserWithLike;
	}
	
	/**
	 * 获得所有假用户
	 * @return
	 */
	public List<TbUser> getAllFakeUsers(){
		return getEntityDao.getFakeUsers();
	}

	/**
	 * 返回与假用户关联的评论信息
	 * @param fakeUsers
	 * @param comments
	 * @return
	 */
	private List<String> getCommentsLinkToFakeUsers(List<TbUser> fakeUsers, List<TbComment> comments) {
		List<String> commentsLinkToFakeUsers = new ArrayList<String>();
		for(TbUser user : fakeUsers){
			for(int i = 0;i<comments.size();i++){
				TbComment comment = comments.get(i);
				if(user.getUserId()==comment.getTbUserByCommentUser().getUserId()){
					commentsLinkToFakeUsers.add(comment.getCommentContent());
					break;
				}
				if(i==(comments.size()-1))
					commentsLinkToFakeUsers.add("");
				
			}
		}
		return commentsLinkToFakeUsers;
	}

	/**
	 * 返回与假用户关联的点赞信息
	 * @param fakeUsers
	 * @param likes
	 * @return
	 */
	private List<Integer> getLikesLinkToFakeUsers(List<TbUser> fakeUsers, List<TbLike> likes) {
		List<Integer> isLikes= new ArrayList<Integer>();
		for(TbUser user : fakeUsers){
			for(int i=0;i<likes.size();i++){
				TbLike like = likes.get(i);
				if(user.getUserId()==like.getTbUser().getUserId() && like.getLikeDeletestatus()==0){
					isLikes.add(1);
					break;
				}
				if(i==(likes.size()-1)){
					isLikes.add(0);
				}
			}
		}
		return isLikes;
	}
	
	public boolean saveDemand(TbDemand demand, List<MultipartFile> photos){
		if(!setEntityDao.saveDemand(demand, photos))
			return false;
		else
			return true;
	}
	
	/**
	 * 根据需求id删除需求信息
	 * @param demandId
	 */
	public void deleteDemandByDemandId(int demandId){
		setEntityDao.deleteDemandByDemandId(demandId);
	}
	
	/**
	 * 根据日期获得需求报表 ,若date为null则返回所有需求报表
	 * @param date (yyyy-MM-dd)格式
	 * @return List<Object> [0] 帖子总数 [1] 完成数量 [2] 等待中数量 [3] 流通金额 [4] 流通时间
	 */
	public List<Object> getDemandReport(String date) {
		if(date==null)
			return getReportDao.getDemandReport();
		else
			return getReportDao.getDemandReport(date);
	}
	
	/**
	 * 根据用户名获得聊天报表
	 * @param userName
	 * @return
	 */
	public long getChatReport(String userName){
		long chatNum;
		if(userName==null){
			chatNum = getReportDao.getChatReport();
		}else{
			chatNum = getReportDao.getChatReport(userName);
		}
		return chatNum;
	}
	
	/**
	 * 根据需求id获得该需求的请求详情
	 * @param demandId
	 * @return
	 */
	public List<TbRequest> getRequestByDemandId(int demandId){
		return getEntityDao.getRequestByDemandId(demandId);
	}
	
	/**
	 * 根据需求id获得该需求的点赞详情
	 * @param demandId
	 * @return
	 */
	public List<TbLike> getLikesByDemandId(int demandId){
		return getEntityDao.getLikesByDemandId(demandId);
	}
	
	/**
	 * 根据需求id获得该需求的评论详情
	 * @param demandId
	 * @return
	 */
	public List<TbComment> getCommentsByDemandId(int demandId) {
		return getEntityDao.getCommentsByDemandId(demandId);
	}
	
	/**
	 * 获得所有需求详情
	 * @return
	 */
	public List<TbDemand> getDemands(){
		return getEntityDao.getDemands();
	}
	
	/**
	 * 获得所有需求信息，包括评论数、点赞数、请求数
	 * key:demands,requestNum
	 * @return Map<String, Object>
	 */
	public Map<String, Object> getDemandsAndRequestNum() {
		return getEntityDao.getDemandsAndRequestNum();
	}
	
	/**
	 * 获得系统信息
	 * @return
	 */
	public TbSystem getSystem(){
		return getEntityDao.getSystem();
	}
	
	public void setSystem(String systemName, String systemVersion, String systemUpdateContent,
			String systemAboutUs, String systemContactUs){
		setEntityDao.setSystem(systemName, systemVersion, systemUpdateContent, systemAboutUs, systemContactUs);
	}

	/**
	 * 获得所有聊天信息
	 * @return List<TbChat> 聊天信息集合
	 */
	public List<TbChat> getChats(){
		return getEntityDao.getChats();
	}
	
	/**
	 * 通过用户名或账号获得聊天信息
	 * @param accountOrUserName 用户名或账号
	 * @return
	 */
	public List<TbChat> getChatsByAccountOrUserName(String accountOrUserName){
		return getEntityDao.getChatsByAccountOrUserName(accountOrUserName);
	}
	
}
