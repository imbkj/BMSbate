package bll.EmSocialinPaper;

import java.util.ArrayList;
import java.util.List;

import dal.EmSocialinPaper.EmSocialinPaperListDal;

import Model.EmSocialinPaperModel;
import Model.EmbaseModel;

public class EmSocialinPaperListBll {

	// 获取员工列表
	public List<EmbaseModel> getEmbaseList(Object... objs) {
		List<EmbaseModel> list = new ArrayList<EmbaseModel>();
		EmSocialinPaperListDal dal = new EmSocialinPaperListDal();
		list = dal.getEmbaseList(objs);
		return list;
	}

	// 获取员工详情
	public EmbaseModel getEmbaseInfo(Object... objs) {
		EmSocialinPaperListDal dal = new EmSocialinPaperListDal();
		return dal.getEmbaseInfo(objs);
	}

	// 是否存在
	public boolean isExists(String str, Object... objs) {
		EmSocialinPaperListDal dal = new EmSocialinPaperListDal();
		return dal.isExists(str, objs);
	}

	// 获取EmSocialinPaperState的数据
	public List<EmSocialinPaperModel> getEmSocialinPaperState(String str) {
		List<EmSocialinPaperModel> list = new ArrayList<EmSocialinPaperModel>();
		EmSocialinPaperListDal dal = new EmSocialinPaperListDal();
		list = dal.getEmSocialinPaperState(str);
		return list;
	}

	// 获取社保卡前道列表
	public List<EmSocialinPaperModel> getqdEmSocailinPaperList(Object... objs) {
		List<EmSocialinPaperModel> list = new ArrayList<EmSocialinPaperModel>();
		EmSocialinPaperListDal dal = new EmSocialinPaperListDal();
		list = dal.getqdEmSocailinPaperList();
		return list;
	}

	// 获取社保卡后道列表
	public List<EmSocialinPaperModel> gethdEmSocailinPaperList(Object... objs) {
		List<EmSocialinPaperModel> list = new ArrayList<EmSocialinPaperModel>();
		EmSocialinPaperListDal dal = new EmSocialinPaperListDal();
		list = dal.gethdEmSocailinPaperList();
		return list;
	}

	// 获取社保卡列表
	public List<EmSocialinPaperModel> getEmSocailinPaperList(Object... objs) {
		List<EmSocialinPaperModel> list = new ArrayList<EmSocialinPaperModel>();
		EmSocialinPaperListDal dal = new EmSocialinPaperListDal();
		list = dal.getEmSocailinPaperList();
		return list;
	}

	// 根据主键id获取详情
	public EmSocialinPaperModel getEmSocailinPaperInfo(Object... objs) {
		List<EmSocialinPaperModel> list = new ArrayList<EmSocialinPaperModel>();
		EmSocialinPaperListDal dal = new EmSocialinPaperListDal();
		list = dal.getEmSocailinPaperInfo(objs);
		return list.get(0);
	}

	// 获取修改状态历史记录
	public List<EmSocialinPaperModel> getStateRelList(Object... objs) {
		List<EmSocialinPaperModel> list = new ArrayList<>();
		EmSocialinPaperListDal dal = new EmSocialinPaperListDal();
		list = dal.getStateRelList(objs);
		return list;
	}
}
