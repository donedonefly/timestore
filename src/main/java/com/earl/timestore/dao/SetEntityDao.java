package com.earl.timestore.dao;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.earl.timestore.entity.TbDemand;

public interface SetEntityDao {

	public abstract void setSystem(String systemName, String systemVersion, String systemUpdateContent,
			String systemAboutUs, String systemContactUs);

	public abstract void deleteDemandByDemandId(int demandId);

	/**
	 * 添加帖子功能
	 * 
	 * @param demand
	 * @param photos
	 * @return
	 */
	public abstract boolean saveDemand(TbDemand demand, List<MultipartFile> photos);

	public abstract boolean addLikes(List<Integer> userIds, int demandId);

	public abstract boolean addComments(String[] comments, int demandId);

}
