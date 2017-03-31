package bll.EmHouse;

import impl.WorkflowCore.WfOperateImpl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.zkoss.zul.ListModelList;

import service.WorkflowCore.WfBusinessService;
import service.WorkflowCore.WfOperateService;

import Conn.dbconn;
import Model.CoglistModel;
import Model.EmHouseChangeModel;

import Model.CoHousingFundModel;
import Model.EmHouseCpp;
import Model.EmHouseUpdateModel;
import Model.EmbaseModel;
import Model.PubCodeConversionModel;
import Model.SocialInsuranceClassInfoViewModel;
import Util.DateStringChange;
import Util.MonthListUtil;
import Util.SocialInsuranceCalculator;
import Util.UserInfo;
import Util.pinyin4jUtil;
import dal.PubCodeConversionDal;
import dal.CoHousingFund.CoHousingFund_ListDal;
import dal.EmHouse.EmHouseChangeDal;
import dal.EmHouse.EmHouseCompanyIdDal;
import dal.EmHouse.EmHouseUpdateDal;
import dal.Embase.CoglistDal;
import dal.Embase.Embasedal;

public class EmHouse_MoveInBll {
	// 获取员工基本信息
	public List<EmbaseModel> getembaseInfo(Integer id) {
		List<EmbaseModel> list = new ListModelList<>();
		Embasedal dal = new Embasedal();
		list = dal.getEmBaseById(id);
		return list;
	}

	// 获取开户类型状态
	public Integer getsingle(Integer gid) {
		Integer i = null;
		List<CoglistModel> clList = new ListModelList<>();
		CoglistDal clDal = new CoglistDal();
		Date nowDate = new Date(); // 获取当前时间
		Integer ownmonth = Integer.valueOf(DateStringChange.DatetoSting(
				nowDate, "yyyyMM"));
		clList = clDal.getCompactInfoByGid(gid, ownmonth);
		if (clList.size() > 0) {
			if (clList.get(0).getCoco_house().equals("独立开户")) {
				i = 1;
			} else {
				i = 0;
			}

		}

		return i;
	}

	// 获取比例信息
	public List<EmHouseCpp> getCpp(Integer cid, Integer gid) {
		List<EmHouseCpp> list = new ListModelList<>();
		List<CoHousingFundModel> ecList = new ListModelList<>();
		List<CoglistModel> clList = new ListModelList<>();
		EmHouseCompanyIdDal dal = new EmHouseCompanyIdDal();
		CoglistDal clDal = new CoglistDal();
		Date nowDate = new Date(); // 获取当前时间
		Integer ownmonth = Integer.valueOf(DateStringChange.DatetoSting(
				nowDate, "yyyyMM"));

		ecList = dal.getlistByCid(cid);
		clList = clDal.getCompactInfoByGid(gid, ownmonth);
		if (clList.size() > 0) {
			if (clList.get(0).getCoco_house().equals("独立开户")
					&& ecList.size() > 0) {
				EmHouseCpp chp = new EmHouseCpp();
				chp.setCp(ecList.get(0).getCohf_cpp());
				chp.setCompanyid(ecList.get(0).getCohf_houseid());
				chp.setCpName((int) (Double
						.valueOf(ecList.get(0).getCohf_cpp()) * 100) + "%");
				chp.setJc(ecList.get(0).getCohf_bankjc());
				list.add(chp);
			} else if (clList.get(0).getCoco_house().equals("中智开户")) {

				if (clList.get(0).getCoco_cpp().equals("浮动比例")) {
					/*
					 * Double z = 0.00; for (Integer i = 5; i < 21; i++) {
					 * EmHouseCpp chp = new EmHouseCpp(); z = i / 100.0;
					 * chp.setCp(z); chp.setCpName(i.toString() + "%");
					 * list.add(chp); }
					 */
					CoHousingFund_ListDal cpdal = new CoHousingFund_ListDal();
					list = cpdal.getcpplist();
				} else {
					EmHouseCpp chp = new EmHouseCpp();
					chp.setCp(Double.valueOf(clList.get(0).getCoco_cpp()));

					chp.setCpName((int) (Double.valueOf(clList.get(0)
							.getCoco_cpp()) * 100) + "%");
					chp.setCompanyid(clList.get(0).getCoco_houseid());
					chp.setJc(clList.get(0).getJc());
					list.add(chp);
				}
			}
		}
		return list;
	}

	// 获取职称信息
	public List<PubCodeConversionModel> getpcInfo() {
		List<PubCodeConversionModel> list = new ListModelList<>();
		PubCodeConversionDal dal = new PubCodeConversionDal();
		list = dal.getListInfo(35, "职称代码");

		return list;
	}

	// 获取单位公积金编号
	public String getcompanyid(Integer gid) {
		String i = null;
		Integer s = getsingle(gid);
		if (s == null) {

		} else if (s == 1) {

			Embasedal ebdal = new Embasedal();
			EmHouseCompanyIdDal dal = new EmHouseCompanyIdDal();
			List<EmbaseModel> list = ebdal.getEmBaseByGid(gid);
			Integer cid = 0;
			if (list.size() > 0) {
				cid = list.get(0).getCid();
			}
			List<CoHousingFundModel> chList = dal.getlistByCid(cid);
			if (chList.size() > 0) {
				i = chList.get(0).getCohf_houseid();
			}

		} else {
			List<CoglistModel> clList = new ListModelList<>();
			CoglistDal clDal = new CoglistDal();
			Date nowDate = new Date(); // 获取当前时间
			Integer ownmonth = Integer.valueOf(DateStringChange.DatetoSting(
					nowDate, "yyyyMM"));
			clList = clDal.getCompactInfoByGid(gid, ownmonth);
			i = clList.get(0).getCoco_houseid();
		}
		return i;
	}

	// 查询员工是否有在册数据
	public boolean houseupdate(Integer gid, Integer stop) {
		List<EmHouseUpdateModel> list = new ListModelList<>();
		EmHouseUpdateDal dal = new EmHouseUpdateDal();
		list = dal.houseupdateInfoByGid(gid, stop);
		if (list.size() > 0) {
			return true;
		} else {
			return false;
		}
	}

	// 查询员工是否有在册数据
	public boolean houseupdate2(Integer gid, String idcard) {
		List<EmHouseUpdateModel> list = new ListModelList<>();
		EmHouseUpdateDal dal = new EmHouseUpdateDal();
		list = dal.getListByIdcard(gid, idcard);
		if (list.size() > 0) {
			return true;
		} else {
			return false;
		}
	}

	public Integer moveinEmhouse(EmHouseChangeModel ehcm) {
		Integer i = 0;
		EmHouseChangeDal dal = new EmHouseChangeDal();
		SocialInsuranceCalculator sm = new SocialInsuranceCalculator();
		Integer id = sm.getSionId("深户员工", "深圳", "深圳中智经济技术合作有限公司");
		Date d = new Date();
		sm.setBcgjj(false);
		List<SocialInsuranceClassInfoViewModel> list = sm.getGjjItemFee(id,
				new BigDecimal(ehcm.getEmhc_radix()),
				new BigDecimal(ehcm.getEmhc_cpp()), d);
		BigDecimal fee = list.get(0).getFee();
		BigDecimal radix = list.get(0).getRadix();

		fee = fee.setScale(2, BigDecimal.ROUND_HALF_UP);
		radix = radix.setScale(2, BigDecimal.ROUND_HALF_UP);

		Object[] obj = { ehcm.getOwnmonth(), ehcm.getGid(), ehcm.getCid(),
				ehcm.getEmhc_companyid(), ehcm.getEmhc_houseid(),
				ehcm.getEmhc_excompanyid(), ehcm.getEmhc_excompany(),
				ehcm.getEmhc_hj(), ehcm.getEmhc_radix(), radix,
				ehcm.getEmhc_cpp(), ehcm.getEmhc_opp(), fee, fee,
				ehcm.getEmhc_title(), ehcm.getEmhc_degree(),
				ehcm.getEmhc_wifename(), ehcm.getEmhc_wifeidcard(),
				ehcm.getEmhc_addname(), "调入", ehcm.getEmhc_mobile(),
				ehcm.getEmhc_ifdeclare(), ehcm.getEmhc_single(),
				ehcm.getEmhc_remark(), ehcm.getEmhc_computerid() };

		if (ehcm.isConfirm()) {
			try {
				i = dal.moveinEmhouse(obj);

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			WfBusinessService wfbs = new EmHouse_MoveInImpl();
			WfOperateService wfs = new WfOperateImpl(wfbs);

			String[] str = wfs.AddTaskToNext(obj, "员工公积金变更", ehcm.getOwnmonth()
					+ ehcm.getEmhc_name() + "(" + ehcm.getGid() + ")调入公积金", 35,
					UserInfo.getUsername(), "", ehcm.getCid(), "");
			i = Integer.valueOf(str[3]);
		}

		return i;
	}

}
