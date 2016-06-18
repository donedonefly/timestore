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
	 * ��ӵ��޺�����
	 * @param userIds  ���޻������û�
	 * @param comments ��������
	 * @param demandId ����id
	 * @return
	 */
	public boolean addLikesAndComments(List<Integer> userIds, String[] comments, int demandId){
		setEntityDao.addLikes(userIds, demandId);
		setEntityDao.addComments(comments, demandId);
		return false;
	}
	
	/**
	 * ������м��û����������Ϣ
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
	 * ������м��û�
	 * @return
	 */
	public List<TbUser> getAllFakeUsers(){
		return getEntityDao.getFakeUsers();
	}

	/**
	 * ��������û�������������Ϣ
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
	 * ��������û������ĵ�����Ϣ
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
	 * ��������idɾ��������Ϣ
	 * @param demandId
	 */
	public void deleteDemandByDemandId(int demandId){
		setEntityDao.deleteDemandByDemandId(demandId);
	}
	
	/**
	 * �������ڻ�����󱨱� ,��dateΪnull�򷵻��������󱨱�
	 * @param date (yyyy-MM-dd)��ʽ
	 * @return List<Object> [0] �������� [1] ������� [2] �ȴ������� [3] ��ͨ��� [4] ��ͨʱ��
	 */
	public List<Object> getDemandReport(String date) {
		if(date==null)
			return getReportDao.getDemandReport();
		else
			return getReportDao.getDemandReport(date);
	}
	
	/**
	 * �����û���������챨��
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
	 * ��������id��ø��������������
	 * @param demandId
	 * @return
	 */
	public List<TbRequest> getRequestByDemandId(int demandId){
		return getEntityDao.getRequestByDemandId(demandId);
	}
	
	/**
	 * ��������id��ø�����ĵ�������
	 * @param demandId
	 * @return
	 */
	public List<TbLike> getLikesByDemandId(int demandId){
		return getEntityDao.getLikesByDemandId(demandId);
	}
	
	/**
	 * ��������id��ø��������������
	 * @param demandId
	 * @return
	 */
	public List<TbComment> getCommentsByDemandId(int demandId) {
		return getEntityDao.getCommentsByDemandId(demandId);
	}
	
	/**
	 * ���������������
	 * @return
	 */
	public List<TbDemand> getDemands(){
		return getEntityDao.getDemands();
	}
	
	/**
	 * �������������Ϣ����������������������������
	 * key:demands,requestNum
	 * @return Map<String, Object>
	 */
	public Map<String, Object> getDemandsAndRequestNum() {
		return getEntityDao.getDemandsAndRequestNum();
	}
	
	/**
	 * ���ϵͳ��Ϣ
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
	 * �������������Ϣ
	 * @return List<TbChat> ������Ϣ����
	 */
	public List<TbChat> getChats(){
		return getEntityDao.getChats();
	}
	
	/**
	 * ͨ���û������˺Ż��������Ϣ
	 * @param accountOrUserName �û������˺�
	 * @return
	 */
	public List<TbChat> getChatsByAccountOrUserName(String accountOrUserName){
		return getEntityDao.getChatsByAccountOrUserName(accountOrUserName);
	}
	
}
