package com.earl.timestore.dao.impl;

import java.util.Collection;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.earl.timestore.entity.TbAdmin;


//管理员
public class AdminDao extends BaseDao{
          
	public  List<TbAdmin>getAlladmin(){  //获取所有数据
		String hql="FROM TbAdmin a";
		return getSession().createQuery(hql).list();
	}

	public  void Delect(Integer id){  //删除
		String hql="delete FROM TbAdmin e where e.adminid=?";
		getSession().createQuery(hql).setInteger(0, id).executeUpdate();
	}
	
	public  TbAdmin getAdmin(Integer id){  //获取一个数据
		return (TbAdmin)getSession().get(TbAdmin.class, id);
	}
	
	public  void UpdadeAdmin(TbAdmin tbAdmin){  //更新
		getSession().saveOrUpdate(tbAdmin);
		getSession().flush();
	}
	
	public int Pass(String a,String b){
		String hql="FROM TbAdmin a where a.adminAccount=? and a.adminPassword=?";
		TbAdmin admin=(TbAdmin) getSession().createQuery(hql).setString(0, a).setString(1, b).uniqueResult();
		if(admin!=null){
			return 1;
		}else{
			return 0;
			
		}
	}
}
