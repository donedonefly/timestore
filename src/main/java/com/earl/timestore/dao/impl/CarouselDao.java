package com.earl.timestore.dao.impl;

import java.util.List;

import org.springframework.orm.hibernate5.HibernateTemplate;

import com.earl.timestore.entity.TbCarousel;




public class CarouselDao extends BaseDao{

	public List<TbCarousel>getAllCarousel(){  //获取所有数据
	   String hql="From TbCarousel c ";
	   return getSession().createQuery(hql).list();
	}
	
	public void Update(TbCarousel tbCarousel){ //更新数据
		getSession().saveOrUpdate(tbCarousel);
	}
	
    public void Delete(int id){  //删除跑马灯
    	System.out.println(id);
    	String hql="delete from TbCarousel c where c.carouselid=?";
    	getSession().createQuery(hql).setInteger(0, id);
    }
    
    public void UpCarousel(Integer id){  //上调跑马灯
    	TbCarousel tbCarousel=getSession().get(TbCarousel.class, id);
    	System.out.println(tbCarousel);
    	int sequence=tbCarousel.getCarouselSequence();
    	System.out.println(sequence);
    	if(sequence!=1){
    		int a=sequence-1;
    		String hql="FROM TbCarousel c where c.carouselSequence=?";
    		TbCarousel tbCarouse2=(TbCarousel) getSession().createQuery(hql).setInteger(0,a).uniqueResult();
    		System.out.println(tbCarouse2);
    		if(tbCarouse2!=null){
    		tbCarouse2.setCarouselSequence(sequence);
    		tbCarousel.setCarouselSequence(sequence-1);
    		System.out.println(tbCarousel);
    		System.out.println(tbCarouse2);
    		getSession().saveOrUpdate(tbCarousel);
    		getSession().saveOrUpdate(tbCarouse2);
    		getSession().flush();
    		}
    		else{
    			tbCarousel.setCarouselSequence(sequence-1);
    			getSession().saveOrUpdate(tbCarousel);
    			getSession().flush();
    		}
    		
    	}
    }
    public void DownCarousel(Integer id){   //下调跑马灯
    	TbCarousel tbCarousel=getSession().get(TbCarousel.class, id);
    	int sequence=tbCarousel.getCarouselSequence();
    	if(sequence!=5){
    		int a=sequence+1;
    		String hql="FROM TbCarousel c where c.carouselsequence=?";
    		TbCarousel tbCarouse2=(TbCarousel) getSession().createQuery(hql).setInteger(0,a).uniqueResult();
    		if(tbCarouse2!=null){
    		tbCarouse2.setCarouselSequence(sequence);
    		tbCarousel.setCarouselSequence(sequence+1);
    		getSession().saveOrUpdate(tbCarousel);
    		getSession().saveOrUpdate(tbCarouse2);
    		getSession().flush();
    		}
    		else{
    			tbCarousel.setCarouselSequence(sequence+1);
    			getSession().saveOrUpdate(tbCarousel);
    			getSession().flush();
    		}
    	}
    }
    	
    public TbCarousel getTbCarousel(Integer id){
    	return getSession().get(TbCarousel.class, id);
    }
    
   
}
