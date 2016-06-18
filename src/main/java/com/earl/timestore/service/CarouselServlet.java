package com.earl.timestore.service;

import java.util.List;
import com.earl.timestore.dao.impl.CarouselDao;
import com.earl.timestore.entity.TbCarousel;

public class CarouselServlet {

	private CarouselDao carouselDao;

	public CarouselDao getCarouselDao() {
		return carouselDao;
	}

	public void setCarouselDao(CarouselDao carouselDao) {
		this.carouselDao = carouselDao;
	}

	public TbCarousel geTbCarousel(int id){
		return carouselDao.getTbCarousel(id);
	}
	
	public void addCarousel(TbCarousel tbCarousel){
		carouselDao.Update(tbCarousel);
	}
	public void DeleteCarousel(int id){
		carouselDao.Delete(id);
	}
	
	public void upCarousel(int i){
		carouselDao.UpCarousel(i);
	}
	
	public void downCarousel(int i){
		carouselDao.DownCarousel(i);
	}
	public List<TbCarousel>getAllCarousel(){
		return carouselDao.getAllCarousel();
	}
	
}
