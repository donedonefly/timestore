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

	// 登录
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
	public List<TbAdmin> getAlladmin() { // 获取所有管理员
		return adminServlet.getAlladmin();
	}

	@ResponseBody
	@RequestMapping("deleteadmin") // 删除管理员
	public List<TbAdmin> Deleteadmin(@RequestParam("id") Integer id) {
		adminServlet.Deleteadmin(id);
		return adminServlet.getAlladmin();
	}

	@RequestMapping("/haha") // 得到管理员
	public String getadmin(@RequestParam("id") int id, Map<String, Object> map) {
		map.put("admin", adminServlet.getadmin(id));
		return "NewFile";
	}

	@ResponseBody
	@RequestMapping("/updatead")
	public List<TbAdmin> Updateadd(@RequestBody TbAdmin tbAdmin) {// 更新添加管理员
		System.out.println("s");
		System.out.println(tbAdmin);
		adminServlet.Updateadd(tbAdmin);
		return adminServlet.getAlladmin();
	}

	@ResponseBody
	@RequestMapping("/getAllCarousel")
	public List<TbCarousel> getAllCarousel() { // 获取所有跑马灯
		return carouselServlet.getAllCarousel();
	}

	@ResponseBody
	@RequestMapping("/addCarousel")
	public List<TbCarousel> addCarousel(@RequestBody TbCarousel tbCarousel) {// 更新添加跑马灯
		System.out.println("haha");
		System.out.println(tbCarousel);
		carouselServlet.addCarousel(tbCarousel);
		return carouselServlet.getAllCarousel();
	}

	@ResponseBody
	@RequestMapping("deleteCarousel")
	public List<TbCarousel> deleteCarousel(@RequestParam("id") int id) {// 删除添加跑马灯
		System.out.println(id);
		carouselServlet.DeleteCarousel(id);
		return carouselServlet.getAllCarousel();
	}

	@ResponseBody
	@RequestMapping("upCarousel")
	public List<TbCarousel> upCarousel(@RequestParam("id") int id) {// 上调添加跑马灯
		carouselServlet.upCarousel(id);
		return carouselServlet.getAllCarousel();
	}

	@ResponseBody
	@RequestMapping("downCarousel")
	public List<TbCarousel> downCarousel(@RequestParam("id") int id) {// 上调添加跑马灯
		carouselServlet.downCarousel(id);
		return carouselServlet.getAllCarousel();
	}

	/*@ResponseBody
	@RequestMapping("/getAllTbUser")
	public List<TbUser> getAllUser() { // 得到所有用户
		return userServlet.getAllUser();
	}*/

	@ResponseBody
	@RequestMapping("getTbUser") // 查询用户
	public List<TbUser> getTbUser(@RequestParam("name") String name) {
		System.out.println(name);
		System.out.println(userServlet.geTbUser(name));
		return userServlet.geTbUser(name);
	}

	@ResponseBody
	@RequestMapping("deleteUser") // 删除用户
	public List<TbUser> DeleteUser(@RequestParam("id") Integer id) {
		System.out.println(id);
		userServlet.DeleteUser(id);
		return userServlet.getAllUser();
	}

	@ResponseBody
	@RequestMapping("getUserbyID") // 得到用户
	public String getUser(@RequestParam("id") int id, Map<String, Object> map) {
		map.put("admin", userServlet.getUser(id));
		return "NewFile";
	}

	@ResponseBody
	@RequestMapping("getUserAccount") // 获取用户数量
	public long getUserAccount() {
		System.out.println(userServlet.getUserAccount());
		return userServlet.getUserAccount();
	}

	@ResponseBody
	@RequestMapping("getUserAccountByschool") // 根据学校去获取用户数量
	public long getUserAccountByschool(@RequestParam("name") String name) {
		System.out.println(userServlet.getSchoolAccount(name));
		return userServlet.getSchoolAccount(name);
	}

	@ResponseBody
	@RequestMapping("getUserByTime") // 根据时间获取用户
	public List<TbUser> getUserByTime(@RequestParam("name") String time) {
		System.out.println(userServlet.getUserbyDate(time));
		return userServlet.getUserbyDate(time);
	}

	@RequestMapping("getUserCount") // 根据时间获取用户数量
	public long getUserCount(@RequestParam("time") String time) throws ParseException {
		System.out.println(userServlet.getDayAccount(time));
		return userServlet.getDayAccount(time);
	}

	
	
	@ResponseBody
	@RequestMapping("/getAllTbUser")
	public Map<String,Object> getAllUser() { // 得到所有用户
		Map<String,Object>haha=new HashMap<String, Object>();
		List<TbUser>hehe=userServlet.getAllUser();//用户数量
		List<Long> yaya=null;//用户评论
		List<Long> yoyo=null;//用户请求
		List<Long> momo=null;//用户聊天
		List<Long> popo=null;//用户帖子
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
		@RequestMapping("getAccountByschool") // 根据学校去获取用户数量
		public  Map<String,Object> getAccountByschool() {
			Map<String,Object>haha=new HashMap<String, Object>();
			List<TbUser>hehe=userServlet.getAllUser();//用户数量
			List<String>fu=null;
			List<Long>yoyo=null;//用户学校数量
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
