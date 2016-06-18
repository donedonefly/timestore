package com.earl.timestore.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.earl.timestore.entity.TbChat;
import com.earl.timestore.entity.TbComment;
import com.earl.timestore.entity.TbDemand;
import com.earl.timestore.entity.TbLike;
import com.earl.timestore.entity.TbRequest;
import com.earl.timestore.entity.TbSystem;
import com.earl.timestore.entity.TbUser;
import com.earl.timestore.service.ChatService;

@Controller
public class ChatHandler {

	@Autowired
	private ChatService chatService;
	
	@ResponseBody
	@RequestMapping("/getChatMessageByChatId")
	public String getChatMessageByChatId(@RequestParam("chatId")int chatId){
		return null;
	}

	/**
	 * ��ӵ��޻�������Ϣ
	 * @param userIds
	 * @param comments
	 * @param demandId
	 */
	@RequestMapping("/addLikesAndComments")
	public void addLikesAndComments(@RequestParam(value = "userId", required = false) List<Integer> userIds,
			@RequestParam("comment") String[] comments, @RequestParam("demandId") int demandId) {
		chatService.addLikesAndComments(userIds, comments, demandId);
	}
	
	/**
	 *  ������м��û�
	 */
	@ResponseBody
	@RequestMapping("getAllFakeUsers")
	public List<TbUser> getAllFakeUsers(){
		return chatService.getAllFakeUsers();
	}

	/**
	 * ������м��û�
	 * 
	 * @param map
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/toAddLikesAndComments")
	public Map<String, Object> toAddLikesAndComments(@RequestParam("demandId") int demandId) {
		Map<String, Object> fakeUserWithLike = chatService.getFakeUsersWithLike(demandId);
		fakeUserWithLike.put("demandId", demandId);

		return fakeUserWithLike;
	}

	/**
	 * �������
	 * 
	 * @param demand
	 * @param photos
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/addDemand")
	public boolean addDemand(TbDemand demand,
			@RequestParam(value = "photos", required = false) List<MultipartFile> photos) {
		System.out.println("hello world");
		if (chatService.saveDemand(demand, photos))
			return true;
		else
			return false;
	}

	@RequestMapping("/deleteDemand")
	public String deleteDemandByDemandId(@RequestParam("demandId") int demandId) {
		chatService.deleteDemandByDemandId(demandId);

		return "index";
	}

	/**
	 * �������ڻ�����󱨱� ,��dateΪnull�򷵻��������󱨱�
	 * 
	 * @param date
	 *            (yyyy-MM-dd)��ʽ
	 * @return List<Object> [0] �������� [1] ������� [2] �ȴ������� [3] ��ͨ��� [4] ��ͨʱ��
	 */
	@ResponseBody
	@RequestMapping("/getDemandDetails")
	public List<Object> getDemandDetails(@RequestParam(value = "date", required = false) String date) {
		return chatService.getDemandReport(date);
	}

	@ResponseBody
	@RequestMapping("/getChatReport")
	public long getChatReport(@RequestParam(value = "userName", required = false) String userName) {
		System.out.println("uesrName:" + userName);
		long chatNum = chatService.getChatReport(userName);
		return chatNum;
	}

	/**
	 * ��������id�����������
	 * 
	 * @param demandId
	 * @param map
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/requestDetails")
	public List<TbRequest> getRequestByDemandId(@RequestParam("demandId") int demandId) {
		List<TbRequest> requests = chatService.getRequestByDemandId(demandId);

		return requests;
	}

	/**
	 * ��������id��õ��޵�����
	 * 
	 * @param demandId
	 * @param map
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/likerDetails")
	public List<TbLike> getLikersByDemandId(@RequestParam("demandId") int demandId) {
		List<TbLike> likes = chatService.getLikesByDemandId(demandId);

		return likes;
	}

	/**
	 * ��������id��ø������������Ϣ
	 * 
	 * @param demandId
	 * @param map
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/commentDetails")
	public List<TbComment> getCommentsByDemandId(@RequestParam("demandId") int demandId) {
		List<TbComment> comments = chatService.getCommentsByDemandId(demandId);

		return comments;
	}

	/**
	 * ����������Ϣ
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getDemandsAndRequestNum")
	public Map<String, Object> getDemandsAndRequestNum() {
		return chatService.getDemandsAndRequestNum();
	}

	/**
	 * ����ϵͳ��Ϣ
	 * 
	 * @param systemName
	 * @param systemVersion
	 * @param systemUpdateContent
	 * @param systemAboutUs
	 * @param systemContactUs
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/setSystem")
	public boolean setSystem(@RequestParam("systemName") String systemName,
			@RequestParam("systemVersion") String systemVersion,
			@RequestParam("systemUpdateContent") String systemUpdateContent,
			@RequestParam("systemAboutUs") String systemAboutUs,
			@RequestParam("systemContactUs") String systemContactUs) {
		System.out.println(systemName);
		chatService.setSystem(systemName, systemVersion, systemUpdateContent, systemAboutUs, systemContactUs);
		return true;
	}

	/**
	 * ����ϵͳ����Ϣ
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getSystem")
	public TbSystem getSystem() {
		TbSystem system = chatService.getSystem();
		return system;
	}

	/**
	 * �������е�������Ϣ
	 * 
	 * @param accountOrUserName
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getAllChat")
	public List<TbChat> getAllChat(
			@RequestParam(value = "check_chat_by_account_or_username", required = false) String accountOrUserName) {
		List<TbChat> chats = null;
		if (accountOrUserName == null) {
			chats = chatService.getChats();
		} else {
			chats = chatService.getChatsByAccountOrUserName(accountOrUserName);
		}
		return chats;
	}

}
