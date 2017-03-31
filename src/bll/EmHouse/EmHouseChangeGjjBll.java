package bll.EmHouse;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.zkoss.zul.ListModelList;

import dal.PubCodeConversionDal;
import dal.EmHouse.EmHouseChangeGjjDal;
import dal.EmHouse.EmHouseUpdateDal;
import dal.Embase.CoglistDal;
import dal.Embase.Embasedal;

import Model.CoglistModel;
import Model.EmHouseChangeGJJModel;
import Model.EmHouseUpdateModel;
import Model.EmbaseModel;
import Model.PubCodeConversionModel;
import Util.DateStringChange;
import Util.MonthListUtil;
import Util.UserInfo;

public class EmHouseChangeGjjBll {

	// 获取员工基本信息
	public List<EmbaseModel> getembaseInfo(Integer id) {
		List<EmbaseModel> list = new ListModelList<>();
		Embasedal dal = new Embasedal();

		list = dal.getEmBaseById(id);

		return list;
	}

	// 查询信息
	public List<EmHouseChangeGJJModel> getInfoByGid(Integer gid) {
		List<EmHouseChangeGJJModel> list = new ListModelList<>();
		EmHouseChangeGjjDal dal = new EmHouseChangeGjjDal();
		list = dal.getInfoByGid(gid);

		return list;
	}

	// 查询在册信息
	public List<EmHouseUpdateModel> getEmhouseupdateInfoByGid(Integer gid) {
		List<EmHouseUpdateModel> list = new ListModelList<>();
		EmHouseUpdateDal dal = new EmHouseUpdateDal();

		list = dal.getListByGid(gid);

		return list;

	}

	// 查询变更类型列表
	public List<PubCodeConversionModel> getchangeModel(Integer id, String name) {
		List<PubCodeConversionModel> list = new ListModelList<>();
		PubCodeConversionDal dal = new PubCodeConversionDal();

		list = dal.getListInfo(id, name);

		return list;
	}

	// 获取所属月份列表
	public List<String> getOwnmonthList(Integer gid) {
		List<String> list = new ArrayList<>();
		MonthListUtil mu = new MonthListUtil();
		String[] s_ownmonth = new String[3];
		EmHouseSetBll bll = new EmHouseSetBll();
		Integer ownmonth = bll.nowmonth2(gid);
		s_ownmonth = mu.getMonthList(true, ownmonth.toString(), "f", 3);
		for (String s : s_ownmonth) {
			list.add(s);
		}
		return list;
	}

	// 获取所属月份列表
	public List<String> getOwnmonthList() {
		List<String> list = new ArrayList<>();
		MonthListUtil mu = new MonthListUtil();
		String[] s_ownmonth = new String[3];
		EmHouseSetBll bll = new EmHouseSetBll();
		Integer ownmonth = bll.nowmonth();
		s_ownmonth = mu.getMonthList(true, ownmonth.toString(), "f", 3);
		for (String s : s_ownmonth) {
			list.add(s);
		}
		return list;
	}

	public Integer addData(EmHouseChangeGJJModel ehg) {
		Integer i = 0;
		/*
		 * WfBusinessService wfbs = new EmHouseChangeGjjImpl(); WfOperateService
		 * wfs = new WfOperateImpl(wfbs);
		 * 
		 * Object[] obj = { "新增", ehg };
		 * 
		 * String[] str = wfs.AddTaskToNext(obj, "员工公积金交单变更", ehg.getOwnmonth()
		 * + ehg.getEhcg_name() + "(" + ehg.getGid() + ")变更公积金信息", 56,
		 * ehg.getEhcg_addname(), "", ehg.getCid(), ""); i =
		 * Integer.valueOf(str[3]);
		 */
		EmHouseChangeGjjDal dal = new EmHouseChangeGjjDal();
		i = dal.add(ehg);
		return i;
	}

	public Integer mod(EmHouseChangeGJJModel em) {
		Integer i = 0;
		EmHouseChangeGjjDal dal = new EmHouseChangeGjjDal();
		em.setEhcg_content("");
		if (em.getEhcg_name_n() != null && !em.getEhcg_name_n().equals("")) {
			em.setEhcg_content(",姓名由\"" + em.getEhcg_name_p() + "\"修改为\""
					+ em.getEhcg_name_n() + "\"");
		} else {
			em.setEhcg_name_p(null);
			em.setEhcg_name_n(null);
		}
		if (em.getEhcg_idcardclass_n() != null
				&& !em.getEhcg_idcardclass_n().equals("")) {
			em.setEhcg_content(em.getEhcg_content() + ",证件类型由\""
					+ em.getEhcg_idcardclass_p() + "\"修改为\""
					+ em.getEhcg_idcardclass_n() + "\"");
		} else {
			em.setEhcg_idcardclass_p(null);
			em.setEhcg_idcardclass_n(null);
		}
		if (em.getEhcg_idcard_n() != null && !em.getEhcg_idcard_n().equals("")) {
			em.setEhcg_content(em.getEhcg_content() + ",证件号由\""
					+ em.getEhcg_idcard_p() + "\"修改为\"" + em.getEhcg_idcard_n()
					+ "\"");
		} else {
			em.setEhcg_idcard_p(null);
			em.setEhcg_idcard_n(null);
		}

		if (em.getEhcg_sex_n() != null && !em.getEhcg_sex_n().equals("")) {
			em.setEhcg_content(em.getEhcg_content() + ",性别由\""
					+ em.getEhcg_sex_p() + "\"修改为\"" + em.getEhcg_sex_n()
					+ "\"");
		} else {
			em.setEhcg_sex_p(null);
			em.setEhcg_sex_n(null);
		}
		if (em.getEhcg_hj_n() != null && !em.getEhcg_hj_n().equals("")) {
			em.setEhcg_content(em.getEhcg_content() + ",户籍由\""
					+ em.getEhcg_hj_p() + "\"修改为\"" + em.getEhcg_hj_n() + "\"");
		} else {
			em.setEhcg_hj_p(null);
			em.setEhcg_hj_n(null);
		}
		if (em.getEhcg_sbid_n() != null && !em.getEhcg_sbid_n().equals("")) {
			em.setEhcg_content(em.getEhcg_content() + ",社保电脑号由\""
					+ em.getEhcg_sbid_p() + "\"修改为\"" + em.getEhcg_sbid_n()
					+ "\"");
		} else {
			em.setEhcg_sbid_p(null);
			em.setEhcg_sbid_n(null);
		}

		if (em.getEhcg_marry_n() != null && !em.getEhcg_marry_n().equals("")) {
			em.setEhcg_content(em.getEhcg_content() + ",婚姻状况由\""
					+ em.getEhcg_marry_p() + "\"修改为\"" + em.getEhcg_marry_n()
					+ "\"");
		} else {
			em.setEhcg_marry_p(null);
			em.setEhcg_marry_n(null);
		}
		if (em.getEhcg_wifename_n() != null
				&& !em.getEhcg_wifename_n().equals("")) {
			em.setEhcg_content(em.getEhcg_content() + ",配偶姓名号由\""
					+ em.getEhcg_wifename_p() + "\"修改为\""
					+ em.getEhcg_wifename_n() + "\"");
		} else {
			em.setEhcg_wifename_p(null);
			em.setEhcg_wifename_n(null);
		}
		if (em.getEhcg_wifeidcard_n() != null
				&& !em.getEhcg_wifeidcard_n().equals("")) {
			em.setEhcg_content(em.getEhcg_content() + ",配偶证件号由\""
					+ em.getEhcg_wifeidcard_p() + "\"修改为\""
					+ em.getEhcg_wifeidcard_n() + "\"");
		} else {
			em.setEhcg_wifeidcard_p(null);
			em.setEhcg_wifeidcard_n(null);
		}

		if (em.getEhcg_title_n() != null && !em.getEhcg_title_n().equals("")) {
			em.setEhcg_content(em.getEhcg_content() + ",职称由\""
					+ em.getEhcg_title_p() + "\"修改为\"" + em.getEhcg_title_n()
					+ "\"");
		} else {
			em.setEhcg_title_p(null);
			em.setEhcg_title_n(null);
		}
		if (em.getEhcg_degree_n() != null && !em.getEhcg_degree_n().equals("")) {
			em.setEhcg_content(em.getEhcg_content() + ",最高学位由\""
					+ em.getEhcg_degree_p() + "\"修改为\"" + em.getEhcg_degree_n()
					+ "\"");
		} else {
			em.setEhcg_degree_p(null);
			em.setEhcg_degree_n(null);
		}
		if (em.getEhcg_email_n() != null && !em.getEhcg_email_n().equals("")) {
			em.setEhcg_content(em.getEhcg_content() + ",电子邮箱由\""
					+ em.getEhcg_email_p() + "\"修改为\"" + em.getEhcg_email_n()
					+ "\"");
		} else {
			em.setEhcg_email_p(null);
			em.setEhcg_email_n(null);
		}
		if (em.getEhcg_phone_n() != null && !em.getEhcg_phone_n().equals("")) {
			em.setEhcg_content(em.getEhcg_content() + ",移动电话由\""
					+ em.getEhcg_phone_p() + "\"修改为\"" + em.getEhcg_phone_n()
					+ "\"");
		} else {
			em.setEhcg_phone_p(null);
			em.setEhcg_phone_n(null);
		}
		em.setEhcg_content(em.getEhcg_content().substring(1));
		em.setEhcg_modname(UserInfo.getUsername());

		i = dal.Mod(em, em.getEhcg_id());
		return i;
	}
}
