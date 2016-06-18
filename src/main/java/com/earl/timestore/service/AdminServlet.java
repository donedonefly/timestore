package com.earl.timestore.service;

import java.util.List;

import com.earl.timestore.dao.impl.AdminDao;
import com.earl.timestore.entity.TbAdmin;

public class AdminServlet {

	private AdminDao adminDao;

	public AdminDao getAdminDao() {
		return adminDao;
	}

	public void setAdminDao(AdminDao adminDao) {
		this.adminDao = adminDao;
	}
	
	public List<TbAdmin> getAlladmin(){
		return adminDao.getAlladmin();
	}
	
    public void Deleteadmin(Integer id){
    	adminDao.Delect(id);
    }
    
   public void Updateadd(TbAdmin tbAdmin){
	   adminDao.UpdadeAdmin(tbAdmin);
   }
   
   
   public TbAdmin getadmin(Integer id){
	   return adminDao.getAdmin(id);
   }
   
   public int getPass(String a,String b){
	   return adminDao.Pass(a, b);
   }
}
