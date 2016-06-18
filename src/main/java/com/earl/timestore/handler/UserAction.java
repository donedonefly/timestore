package com.earl.timestore.handler;

import java.text.ParseException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.earl.timestore.entity.TbAdmin;
import com.earl.timestore.entity.TbCarousel;
import com.earl.timestore.entity.TbUser;
import com.earl.timestore.service.AdminServlet;
import com.earl.timestore.service.CarouselServlet;
import com.earl.timestore.service.UserServlet;

@Controller
public class UserAction {

	@Autowired
	private UserServlet userServlet;

	@Autowired
	private CarouselServlet carouselServlet;

	@Autowired
	private AdminServlet adminServlet;

	// ��¼
	@ResponseBody
	@RequestMapping("Pass")
	public int Pass(@RequestParam(value = "user", required = true) String user,
			@RequestParam(value = "password", required = false) String password) {
		int i = adminServlet.getPass(user, password);
		System.out.println(i);
		return i;
	}

	@ResponseBody
	@RequestMapping("getAlladmin")
	public List<TbAdmin> getAlladmin() { // ��ȡ���й���Ա
		return adminServlet.getAlladmin();
	}

	@ResponseBody
	@RequestMapping("deleteadmin") // ɾ������Ա
	public List<TbAdmin> Deleteadmin(@RequestParam("id") Integer id) {
		adminServlet.Deleteadmin(id);
		return adminServlet.getAlladmin();
	}

	@RequestMapping("/haha") // �õ�����Ա
	public String getadmin(@RequestParam("id") int id, Map<String, Object> map) {
		map.put("admin", adminServlet.getadmin(id));
		return "NewFile";
	}

	@ResponseBody
	@RequestMapping("/updatead")
	public List<TbAdmin> Updateadd(@RequestBody TbAdmin tbAdmin) {// ������ӹ���Ա
		System.out.println("s");
		System.out.println(tbAdmin);
		adminServlet.Updateadd(tbAdmin);
		return adminServlet.getAlladmin();
	}

	@ResponseBody
	@RequestMapping("/getAllCarousel")
	public List<TbCarousel> getAllCarousel() { // ��ȡ���������
		return carouselServlet.getAllCarousel();
	}

	@ResponseBody
	@RequestMapping("/addCarousel")
	public List<TbCarousel> addCarousel(@RequestBody TbCarousel tbCarousel) {// ������������
		System.out.println("haha");
		System.out.println(tbCarousel);
		carouselServlet.addCarousel(tbCarousel);
		return carouselServlet.getAllCarousel();
	}

	@ResponseBody
	@RequestMapping("deleteCarousel")
	public List<TbCarousel> deleteCarousel(@RequestParam("id") int id) {// ɾ����������
		System.out.println(id);
		carouselServlet.DeleteCarousel(id);
		return carouselServlet.getAllCarousel();
	}

	@ResponseBody
	@RequestMapping("upCarousel")
	public List<TbCarousel> upCarousel(@RequestParam("id") int id) {// �ϵ���������
		carouselServlet.upCarousel(id);
		return carouselServlet.getAllCarousel();
	}

	@ResponseBody
	@RequestMapping("downCarousel")
	public List<TbCarousel> downCarousel(@RequestParam("id") int id) {// �ϵ���������
		carouselServlet.downCarousel(id);
		return carouselServlet.getAllCarousel();
	}

	/*@ResponseBody
	@RequestMapping("/getAllTbUser")
	public List<TbUser> getAllUser() { // �õ������û�
		return userServlet.getAllUser();
	}*/

	@ResponseBody
	@RequestMapping("getTbUser") // ��ѯ�û�
	public List<TbUser> getTbUser(@RequestParam("name") String name) {
		System.out.println(name);
		System.out.println(userServlet.geTbUser(name));
		return userServlet.geTbUser(name);
	}

	@ResponseBody
	@RequestMapping("deleteUser") // ɾ���û�
	public List<TbUser> DeleteUser(@RequestParam("id") Integer id) {
		System.out.println(id);
		userServlet.DeleteUser(id);
		return userServlet.getAllUser();
	}

	@ResponseBody
	@RequestMapping("getUserbyID") // �õ��û�
	public String getUser(@RequestParam("id") int id, Map<String, Object> map) {
		map.put("admin", userServlet.getUser(id));
		return "NewFile";
	}

	@ResponseBody
	@RequestMapping("getUserAccount") // ��ȡ�û�����
	public long getUserAccount() {
		System.out.println(userServlet.getUserAccount());
		return userServlet.getUserAccount();
	}

	@ResponseBody
	@RequestMapping("getUserAccountByschool") // ����ѧУȥ��ȡ�û�����
	public long getUserAccountByschool(@RequestParam("name") String name) {
		System.out.println(userServlet.getSchoolAccount(name));
		return userServlet.getSchoolAccount(name);
	}

	@ResponseBody
	@RequestMapping("getUserByTime") // ����ʱ���ȡ�û�
	public List<TbUser> getUserByTime(@RequestParam("name") String time) {
		System.out.println(userServlet.getUserbyDate(time));
		return userServlet.getUserbyDate(time);
	}

	@RequestMapping("getUserCount") // ����ʱ���ȡ�û�����
	public long getUserCount(@RequestParam("time") String time) throws ParseException {
		System.out.println(userServlet.getDayAccount(time));
		return userServlet.getDayAccount(time);
	}

	
	
	@ResponseBody
	@RequestMapping("/getAllTbUser")
	public Map<String,Object> getAllUser() { // �õ������û�
		Map<String,Object>haha=new HashMap<String, Object>();
		List<TbUser>hehe=userServlet.getAllUser();//�û�����
		List<Long> yaya=null;//�û�����
		List<Long> yoyo=null;//�û�����
		List<Long> momo=null;//�û�����
		List<Long> popo=null;//�û�����
		haha.put("AllUser", userServlet.getAllUser());
		for(TbUser user:hehe){
			int userID=user.getUserId();
			yaya.add(userServlet.CountCommentByid(userID));
			yoyo.add(userServlet.CountTbRequestByid(userID));
			momo.add(userServlet.CountTbChatByid(userID));
			popo.add(userServlet.CountTbDemandByid(userID));
		}
		haha.put("CountCommentByid", yaya);
		haha.put("CountTbRequestByid", yoyo);
		haha.put("CountTbChatByid", momo);
		haha.put("CountTbDemandByid", popo);
		return haha; 
	}
	////
		@ResponseBody
		@RequestMapping("getAccountByschool") // ����ѧУȥ��ȡ�û�����
		public  Map<String,Object> getAccountByschool() {
			Map<String,Object>haha=new HashMap<String, Object>();
			List<TbUser>hehe=userServlet.getAllUser();//�û�����
			List<String>fu=null;
			List<Long>yoyo=null;//�û�ѧУ����
			for(TbUser a:hehe){
				  fu.add(a.getUserSchool());
			}
			 HashSet<String> h = new HashSet(fu);
			 for(String a:h){
				 yoyo.add(userServlet.getSchoolAccount(a));
			 }
			haha.put("School",h);
			haha.put("SchoolAccount", yoyo);
			return haha;
		}

	
}
