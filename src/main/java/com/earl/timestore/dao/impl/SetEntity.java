package com.earl.timestore.dao.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import com.earl.timestore.dao.GetEntityDao;
import com.earl.timestore.dao.SetEntityDao;
import com.earl.timestore.entity.TbComment;
import com.earl.timestore.entity.TbDemand;
import com.earl.timestore.entity.TbLike;
import com.earl.timestore.entity.TbSystem;
import com.earl.timestore.entity.TbUser;

public class SetEntity extends SessionFactoryDaoImpl implements SetEntityDao {

	@Autowired
	private GetEntityDao getEntityDao;

	public void setSystem(String systemName, String systemVersion, String systemUpdateContent, String systemAboutUs,
			String systemContactUs) {
		String hql = "from TbSystem";
		TbSystem system = (TbSystem) getSession().createQuery(hql).uniqueResult();
		system.setSystemName(systemName);
		system.setSystemVersion(systemVersion);
		system.setSystemUpdatecontent(systemUpdateContent);
		system.setSystemAboutus(systemAboutUs);
		system.setSystemContactus(systemContactUs);

		getSession().saveOrUpdate(system);
	}

	public void deleteDemandByDemandId(int demandId) {
		String setCommentHQL = "update TbComment comment set comment.tbUserByCommentParent=null where comment.tbDemand.demandid=?";
		String deleteCommentHQL = "delete from TbComment comment where comment.tbDemand.demandid=?";
		String deleteLikeHQL = "delete from TbLike tbLike where tbLike.likeDemand.demandid=?";
		String deleteRequestHQL = "delete from TbRequest request where request.tbDemand.demandid=?";
		String deleteDemandHQL = "delete from TbDemand demand where demand.demandid=?";

		getSession().createQuery(setCommentHQL).setInteger(0, demandId).executeUpdate();
		getSession().createQuery(deleteCommentHQL).setInteger(0, demandId).executeUpdate();
		getSession().createQuery(deleteLikeHQL).setInteger(0, demandId).executeUpdate();
		getSession().createQuery(deleteRequestHQL).setInteger(0, demandId).executeUpdate();
		getSession().createQuery(deleteDemandHQL).setInteger(0, demandId).executeUpdate();

	}

	/**
	 * 添加帖子功能
	 */
	public boolean saveDemand(TbDemand demand, List<MultipartFile> photos) {
		if (!savePhotosTolocal(photos))
			return false;
		demand.setDemandPhotourljson(photosToJson(photos));
		demand.setDemandTime(new Date());
		BigDecimal demandPay = demand.getDemandPay();
		if (demand.getDemandType() == 1)
			demand.setDemandPay(demandPay.setScale(0, BigDecimal.ROUND_DOWN));
		getSession().saveOrUpdate(demand);
		return true;
	}

	/**
	 * 为指定帖子添加点赞
	 * 
	 * @param userIds
	 *            点赞用户id
	 * @param demandId
	 *            帖子id
	 */
	public boolean addLikes(List<Integer> userIds, int demandId) {
		if (userIds == null) {
			cancelLikeExists(userIds, demandId);
			return false;
		}
		cancelLikeExists(userIds, demandId);
		updateLikeExists(userIds, demandId);

		TbLike like = null;
		TbUser user = new TbUser();
		TbDemand demand = new TbDemand();
		demand.setDemandid(demandId);
		for (int i = 0; i < userIds.size(); i++) {
			user.setUserId(userIds.get(i));

			like = new TbLike();
			like.setLikeIssaw(0);
			like.setLikeDeletestatus(0);
			like.setLikeTime(new Date());
			like.setTbUser(user);
			like.setLikeDemand(demand);
			getSession().save(like);
		}

		updateLikeNumOnDemand(demandId);

		return false;
	}

	/**
	 * 取消赞
	 * 
	 * @param userIds
	 * @param demandId
	 */
	private void cancelLikeExists(List<Integer> userIds, int demandId) {
		List<TbLike> likes = getEntityDao.getLikesByDemandId(demandId);
		for (TbLike like : likes) {
			if (userIds == null) {
				like.setLikeDeletestatus(1);
			} else {
				for (int i = 0; i < userIds.size(); i++) {
					if (userIds.get(i) == like.getTbUser().getUserId()) {
						break;
					}
					if (i == (userIds.size() - 1)) {
						like.setLikeDeletestatus(1);
					}
				}
			}
		}

	}

	/**
	 * 为指定帖子添加评论
	 */
	public boolean addComments(String[] comments, int demandId) {
		List<TbUser> users = getEntityDao.getFakeUsers();

		TbComment comment = null;
		TbDemand demand = new TbDemand();
		demand.setDemandid(demandId);
		for (int i = 0; i < comments.length; i++) {
			if (comments[i].trim() != "") {
				comment = getExistsComment(users.get(i).getUserId(), demandId);
				if (comment == null) {
					comment = new TbComment();
					comment.setCommentTime(new Date());
					comment.setCommentIssaw(1);
					comment.setTbDemand(demand);
					comment.setTbUserByCommentUser(users.get(i));
					comment.setCommentContent(comments[i]);
				} else {
					comment.setCommentContent(comments[i]);
				}
				getSession().saveOrUpdate(comment);
			}
		}

		updateCommentNumInDemand(demandId);

		return false;
	}

	/**
	 * 更新需求表的评论数
	 * 
	 * @param demandId
	 */
	public void updateCommentNumInDemand(int demandId) {
		String getCommentNumHQL = "select count(*) from TbComment comment where comment.tbDemand.demandid=?";
		String setCommentNumHQL = "update TbDemand demand set demand.demandCommentcount=? where demand.demandid=?";

		long commentNum = (Long) getSession().createQuery(getCommentNumHQL).setLong(0, demandId).uniqueResult();
		getSession().createQuery(setCommentNumHQL).setLong(0, commentNum).setInteger(1, demandId).executeUpdate();
	}

	public TbComment getExistsComment(int userId, int demandId) {
		String hql = "from TbComment comment where comment.tbDemand.demandid=? and comment.tbUserByCommentUser.userId=?";
		TbComment comment = (TbComment) getSession().createQuery(hql).setInteger(0, demandId).setInteger(1, userId)
				.uniqueResult();
		return comment;
	}

	/**
	 * 更新点赞表已存在的点赞信息，同时去掉userIds中已存在在点赞表用户
	 * 
	 * @param userIds
	 * @param demandId
	 */
	public void updateLikeExists(List<Integer> userIds, int demandId) {
		String sql = "from TbLike liker LEFT OUTER JOIN FETCH liker.tbUser LEFT OUTER JOIN FETCH liker.likeDemand demand where demand.demandid=?";
		List<TbLike> likes = getSession().createQuery(sql).setInteger(0, demandId).list();
		for (int i = 0; i < likes.size(); i++) {
			TbLike like = likes.get(i);
			for (int j = 0; j < userIds.size(); j++) {
				if (like.getTbUser().getUserId() == userIds.get(j)) {
					if (like.getLikeDeletestatus() == 1) {
						like.setLikeDeletestatus(0);
					}
					userIds.remove(j);
					j--;
				}
			}
		}
	}

	/**
	 * 更新需求表的点赞数
	 * 
	 * @param demandId
	 */
	public void updateLikeNumOnDemand(int demandId) {
		String getLikeNumHQL = "select count(*) from TbLike likes where likes.likeDemand.demandid=?";
		String updateDemandHQL = "update TbDemand demand set demand.demandLikecount=? where demand.demandid=?";

		long likeNum = (Long) getSession().createQuery(getLikeNumHQL).setInteger(0, demandId).uniqueResult();
		getSession().createQuery(updateDemandHQL).setLong(0, likeNum).setInteger(1, demandId).executeUpdate();
	}

	/**
	 * 将帖子图片转换成json格式
	 * 
	 * @param photos
	 * @return
	 */
	public String photosToJson(List<MultipartFile> photos) {

		int photoNum = photos.size();
		String json = "[";
		String UrlPrefix = "/" + getPropertyFromPropertiesFile("pathOfPhotoJson") + "/";
		for (int i = 0;i<photoNum; i++) {
			if (photos.get(i).getOriginalFilename() != "") {
				String photoUrl = UrlPrefix + photos.get(i).getOriginalFilename();
				json = json + "\"" + photoUrl + "\",";
			}
		}
		json = json.substring(0, json.length() - 1) + "]";

		return json;
	}

	/**
	 * 将图片存入本地webapp\demandImage
	 * 
	 * @param photos
	 * @return
	 */
	public boolean savePhotosTolocal(List<MultipartFile> photos) {
		int photoNum = photos.size();
		for (int i = 0; i < photoNum; i++) {
			if (photos.get(i).getOriginalFilename() != "") {
				String localPath = getPropertyFromPropertiesFile("localPath");
				File file = new File(localPath + photos.get(i).getOriginalFilename());
				// System.out.println(file.getAbsolutePath());
				// File file = new
				// File(parent,photos.get(i).getOriginalFilename());
				// parent.mkdirs();
				try {
					InputStream in = photos.get(i).getInputStream();
					FileOutputStream out = new FileOutputStream(file);
					int len = 0;
					byte[] buffer = new byte[1024];
					while ((len = in.read(buffer)) != -1) {
						out.write(buffer, 0, len);
					}

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return false;
				}
			}
		}
		return true;
	}

	public String getPropertyFromPropertiesFile(String key) {
		Properties prop = new Properties();
		InputStream inputStream = SetEntity.class.getClassLoader()
				.getResourceAsStream("com/earl/timestore/dao/impl/myProperties.properties");
		try {
			prop.load(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		String value = prop.getProperty(key);
		try {
			inputStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return value;
	}

}
