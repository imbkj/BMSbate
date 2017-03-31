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
import dal.CoHousingFund.CoHousingFund_ListDal;
import dal.EmHouse.EmHouseChangeDal;
import dal.EmHouse.EmHouseCompanyIdDal;
import dal.EmHouse.EmHouseUpdateDal;
import dal.Embase.CoglistDal;
import dal.Embase.Embasedal;
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

public class EmHouse_addBll {

	// 获取员工基本信息
	public List<EmbaseModel> getembaseInfo(Integer id) {
		List<EmbaseModel> list = new ListModelList<>();
		Embasedal dal = new Embasedal();

		list = dal.getEmBaseById(id);

		return list;
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
	public boolean houseupdate(Integer gid, String idcard) {
		List<EmHouseUpdateModel> list = new ListModelList<>();
		EmHouseUpdateDal dal = new EmHouseUpdateDal();
		dal.flashData(idcard);
		list = dal.getListByIdcard(gid, idcard);
		if (list.size() > 0) {
			return true;
		} else {
			return false;
		}
	}

	// 查询员工是否有在册数据
	public boolean houseupdate(Integer gid, String idcard, Integer ownmonth) {
		List<EmHouseUpdateModel> list = new ListModelList<>();
		EmHouseUpdateDal dal = new EmHouseUpdateDal();
		dal.flashData(idcard);
		list = dal.getListByIdcard(gid, idcard);
		if (list.size() > 0) {
			if (ownmonth > Integer.valueOf(DateStringChange.getOwnmonth())
					&& !list.get(0).getOwnmonth().equals(ownmonth)) {
				return false;
			}else {
				return true;
			}
			
		} else {
			return false;
		}
	}

	// 判断员工是否有公积金变更数据
	public boolean housechange(Integer gid) {
		List<EmHouseChangeModel> list = new ListModelList<>();
		EmHouseChangeDal dal = new EmHouseChangeDal();
		list = dal.getlist(gid);
		if (list.size() > 0) {
			return true;
		} else {
			return false;
		}
	}

	// 获取员工报价单公积金服务起始月
	public Integer getownmonth(Integer gid) {
		Integer ownmonth = 0;
		List<CoglistModel> list = new ListModelList<>();
		CoglistDal dal = new CoglistDal();
		list = dal.coglistGjj(gid);
		if (list.size() > 0) {
			ownmonth = list.get(0).getCgli_startdate2();
		}
		return ownmonth;
	}

	// 获取所属月份列表
	public List<String> getOwnmonthList() {
		List<String> list = new ArrayList<>();
		EmHouseUpdateDal dal = new EmHouseUpdateDal();
		MonthListUtil mu = new MonthListUtil();
		Date nowDate = new Date(); // 获取当前时间
		String nowmonth = DateStringChange.DatetoSting(nowDate, "yyyyMM");
		String[] s_ownmonth = new String[3];

		String ownmonth = dal.getOwnmonth();
		if (Integer.valueOf(ownmonth) < Integer.valueOf(nowmonth)) {
			s_ownmonth = MonthListUtil.getMonthList(true, nowmonth, "f", 3);
		} else {
			s_ownmonth = MonthListUtil.getMonthList(true, ownmonth, "f", 3);
		}

		for (int i = 0; i < s_ownmonth.length; i++) {
			list.add(s_ownmonth[i]);
		}

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
			String s = clList.get(0).getCoco_house();
			if (s != null && !s.equals("") && s.equals("独立开户")) {
				i = 1;
			} else {
				i = 0;
			}
		} else {
			i = null;
		}

		return i;
	}

	// 获取比例信息
	public List<EmHouseCpp> getCpp(Integer cid, Integer gid, Integer ownmonth) {
		List<EmHouseCpp> list = new ListModelList<>();
		List<CoHousingFundModel> ecList = new ListModelList<>();
		List<CoglistModel> clList = new ListModelList<>();
		EmHouseCompanyIdDal dal = new EmHouseCompanyIdDal();
		CoglistDal clDal = new CoglistDal();

		if (ownmonth == null) {
			Date nowDate = new Date(); // 获取当前时间
			ownmonth = Integer.valueOf(DateStringChange.DatetoSting(nowDate,
					"yyyyMM"));
		}
		ecList = dal.getlistByCid(cid);

		clList = clDal.getCompactInfoByGid(gid, ownmonth);
		if (clList.size() > 0) {

			if (clList.get(0).getCoco_house().equals("独立开户")
					&& ecList.size() > 0) {
				EmHouseCpp chp = new EmHouseCpp();
				chp.setCp(ecList.get(0).getCohf_cpp());
				chp.setCpName((int) (Double
						.valueOf(ecList.get(0).getCohf_cpp()) * 100) + "%");
				chp.setCompanyid(clList.get(0).getCoco_houseid());
				chp.setJc(clList.get(0).getJc());
				list.add(chp);
			} else if (clList.get(0).getCoco_house().equals("中智开户")) {

				if (clList.get(0).getCoco_cpp().equals("浮动比例")) {
					/*
					 * double z = 0.00; for (Integer i = 5; i < 21; i++) {
					 * EmHouseCpp chp = new EmHouseCpp(); z = i / 100.0;
					 * chp.setCp(z); chp.setCpName(i.toString() + "%");
					 * list.add(chp); }
					 */
					CoHousingFund_ListDal cpdal = new CoHousingFund_ListDal();
					list = cpdal.getcpplist(cid);
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
		dbconn db = new dbconn();
		String sql = "select * from PubCodeConversion where pucl_id=35 and pcco_name='职称代码'";
		try {
			list = db.find(sql, PubCodeConversionModel.class,
					dbconn.parseSmap(PubCodeConversionModel.class));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	// 获取单位公积金编号
	public String getcompanyid(Integer gid) {
		String i = null;
		Integer s = getsingle(gid);
		// System.out.println("s:" + s.toString());
		if (s != null && s.equals(1)) {

			Embasedal ebdal = new Embasedal();
			EmHouseCompanyIdDal dal = new EmHouseCompanyIdDal();
			List<EmbaseModel> eblist = new ListModelList<>();
			List<CoHousingFundModel> eclist = new ListModelList<>();
			eblist = ebdal.getEmBaseByGid(gid);
			if (eblist.size() > 0) {
				Integer cid = eblist.get(0).getCid();
				eclist = dal.getlistByCid(cid);
				if (eclist.size() > 0) {
					i = eclist.get(0).getCohf_houseid();
				}
			}

		} else {
			List<CoglistModel> clList = new ListModelList<>();
			CoglistDal clDal = new CoglistDal();
			Date nowDate = new Date(); // 获取当前时间
			Integer ownmonth = Integer.valueOf(DateStringChange.DatetoSting(
					nowDate, "yyyyMM"));
			clList = clDal.getCompactInfoByGid(gid, ownmonth);
			if (clList.size() > 0) {
				i = clList.get(0).getCoco_houseid();

			}

		}
		return i;
	}

	public Integer addEmhouse(EmHouseChangeModel ehcm) {
		Integer i = 0;
		EmHouseChangeDal dal = new EmHouseChangeDal();

		// 计算上下限
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
				ehcm.getEmhc_companyid(), ehcm.getEmhc_hj(),
				ehcm.getEmhc_radix(), radix, ehcm.getEmhc_cpp(),
				ehcm.getEmhc_opp(), fee, fee, ehcm.getEmhc_title(),
				ehcm.getEmhc_degree(), ehcm.getEmhc_wifename(),
				ehcm.getEmhc_wifeidcard(), ehcm.getEmhc_addname(), "新增",
				ehcm.getEmhc_mobile(), ehcm.getEmhc_ifdeclare(),
				ehcm.getEmhc_single(), ehcm.getEmhc_remark() };

		if (ehcm.isConfirm()) {
			try {
				i = dal.addEmhouse(obj);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			WfBusinessService wfbs = new EmHouse_addImpl();
			WfOperateService wfs = new WfOperateImpl(wfbs);
			String[] str = wfs.AddTaskToNext(obj, "员工公积金变更", ehcm.getOwnmonth()
					+ ehcm.getEmhc_name() + "(" + ehcm.getGid() + ")新增公积金", 34,
					UserInfo.getUsername(), "", ehcm.getCid(), "");
			i = Integer.valueOf(str[3]);
		}

		return i;
	}
}
