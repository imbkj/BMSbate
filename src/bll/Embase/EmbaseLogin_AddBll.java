package bll.Embase;

import impl.WorkflowCore.WfOperateImpl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;

import bll.EmSheBao.Emsi_OperateBll;

import service.WorkflowCore.WfBusinessService;
import service.WorkflowCore.WfOperateService;

import dal.CoHousingFund.CoHousingFund_ListDal;
import dal.EmHouse.EmHouseCompanyIdDal;
import dal.EmHouse.EmHouseUpdateDal;
import dal.EmSheBao.Emsi_OperateDal;
import dal.EmSheBao.Emsi_SelDal;
import dal.Embase.CoglistDal;
import dal.Embase.EmBaseLogin_AddDal;
import dal.Embase.Embasedal;

import Model.CoHousingFundModel;
import Model.CoglistModel;
import Model.EmBaseRequiredModel;
import Model.EmBcSetupAddressModel;
import Model.EmBcSetupModel;
import Model.EmHouseCpp;
import Model.EmHouseUpdateModel;
import Model.EmShebaoBJModel;
import Model.EmShebaoFormulaModel;
import Model.EmShebaoSetupModel;
import Model.EmShebaoUpdateModel;
import Model.EmbaseModel;
import Model.Emcontactinfo;
import Model.PubCodeConversionModel;
import Util.DateStringChange;
import Util.IdcardUtil;
import Util.MonthListUtil;
import Util.UserInfo;

public class EmbaseLogin_AddBll {

	/**
	 * 查询确认姓名，身份证，户籍
	 * 
	 * @param gid
	 * @return emcontactinfo
	 */
	public Emcontactinfo getEmzt(int gid) {
		EmBaseLogin_AddDal dal = new EmBaseLogin_AddDal();
		return dal.getEmzt(gid);
	}

	// 获取公用信息列表
	public List<PubCodeConversionModel> getPubCodeList(Object... objs) {
		List<PubCodeConversionModel> list = new ArrayList<PubCodeConversionModel>();
		EmBaseLogin_AddDal dal = new EmBaseLogin_AddDal();
		list = dal.getPubCodeList(objs);
		return list;
	}

	// 获取体检医院列表
	public List<EmBcSetupModel> getBcSetupList(Object... objs) {
		List<EmBcSetupModel> list = new ArrayList<EmBcSetupModel>();
		EmBaseLogin_AddDal dal = new EmBaseLogin_AddDal();
		list = dal.getBcSetupList(objs);
		return list;
	}

	// 获取体检医院地址列表
	public List<EmBcSetupAddressModel> getBcSetupAddressList(Object... objs) {
		List<EmBcSetupAddressModel> list = new ArrayList<EmBcSetupAddressModel>();
		EmBaseLogin_AddDal dal = new EmBaseLogin_AddDal();
		list = dal.getBcSetupAddressList(objs);
		return list;
	}

	// 获取民族列表
	public List<String> getFolkList(Object... objs) {
		List<String> list = new ArrayList<String>();
		EmBaseLogin_AddDal dal = new EmBaseLogin_AddDal();
		list = dal.getFolkList(objs);
		return list;
	}

	// 根据id判断是否存在
	public boolean isExists(int emba_id) {
		boolean is = false;
		EmBaseLogin_AddDal dal = new EmBaseLogin_AddDal();
		is = dal.isExists(emba_id);
		return is;
	}

	// 获取必填字段列表
	public List<String> getRequiredList(int emba_id) {
		List<String> list = new ArrayList<String>();
		EmBaseLogin_AddDal dal = new EmBaseLogin_AddDal();
		list = dal.getRequiredList(emba_id);
		return list;
	}

	// 获取员工信息
	public List<EmbaseModel> getEmbaseLoginInfo(Object... objs) {
		List<EmbaseModel> list = new ArrayList<EmbaseModel>();
		EmBaseLogin_AddDal dal = new EmBaseLogin_AddDal();
		list = dal.getEmbaseLoginInfo(objs);
		return list;
	}

	public int EmbaseloginUpdatestate(int embastate, int emba_id) {
		EmBaseLogin_AddDal dal = new EmBaseLogin_AddDal();
		return dal.EmbaseloginUpdatestate(embastate, emba_id);
	}

	// 获取员工在册信息
	public List<EmbaseModel> embaseinfo(Integer id) {
		List<EmbaseModel> list = new ListModelList<>();
		EmBaseLogin_AddDal dal = new EmBaseLogin_AddDal();
		list = dal.getembaseInfo(id);
		return list;
	}

	// 检查身份证是否存在
	public boolean haveIDcard(Integer cid, Integer gid, String idcard,
			String idcardclass) {
		boolean b = false;
		Embasedal dal = new Embasedal();
		if (idcardclass.equals("身份证")) {
			if (idcard.length() == 15) {
				idcard = IdcardUtil.conver15CardTo18(idcard);
			}

		}
		b = dal.haveIDCard(cid, gid, idcard, idcardclass);
		return b;
	}

	// 获取社保医疗类型信息(传入是否为外籍人模板)
	public List<EmShebaoFormulaModel> getFormula(int ifForeign) {
		Emsi_SelDal dal = new Emsi_SelDal();
		return dal.getFormula(ifForeign);
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

	// 获取员工报价单公积金收费起始月
	public Integer getownmonth2(Integer gid) {
		Integer ownmonth = 0;
		List<CoglistModel> list = new ListModelList<>();
		CoglistDal dal = new CoglistDal();
		list = dal.coglistGjj(gid);
		if (list.size() > 0) {
			ownmonth = list.get(0).getCgli_startdate();
		}
		return ownmonth;
	}

	// 计算补缴起始月和终止月

	// 获取员工报价单社保服务起始月
	public Integer getsbownmonth(Integer gid) {
		Integer ownmonth = 0;
		List<CoglistModel> list = new ListModelList<>();
		CoglistDal dal = new CoglistDal();
		list = dal.coglistsb(gid);
		if (list.size() > 0) {
			ownmonth = list.get(0).getCgli_startdate2();
		}
		return ownmonth;
	}

	// 获取员工报价社保收费起始月
	public Integer getsbownmonth2(Integer gid) {
		Integer ownmonth = 0;
		List<CoglistModel> list = new ListModelList<>();
		CoglistDal dal = new CoglistDal();
		list = dal.coglistsb(gid);
		if (list.size() > 0) {
			ownmonth = list.get(0).getCgli_startdate();
		}
		return ownmonth;
	}

	// 获取当前公积金月份
	public Integer houseOwnmonth() {
		Integer i = 0;

		EmHouseUpdateDal dal = new EmHouseUpdateDal();
		String ownmonth = dal.getOwnmonth();
		i = Integer.valueOf(ownmonth);
		return i;
	}

	// 获取社保公积金报价信息
	public List<CoglistModel> cgInfo(Integer gid, String type) {
		List<CoglistModel> list = new ListModelList<>();
		CoglistDal dal = new CoglistDal();
		if (type.equals("社保")) {
			list = dal.coglistsb(gid);
		} else {
			list = dal.coglistGjj(gid);
		}

		return list;
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
				list.add(chp);
			} else {
				if (clList.get(0).getCoco_cpp() != null
						&& !clList.get(0).getCoco_cpp().equals("")) {
					if (clList.get(0).getCoco_cpp().equals("浮动比例")) {
						/*
						 * double z = 0.00; for (Integer i = 5; i < 21; i++) {
						 * EmHouseCpp chp = new EmHouseCpp(); z = i / 100.0;
						 * chp.setCp(z); chp.setCpName(i.toString() + "%");
						 * list.add(chp); }
						 */
						CoHousingFund_ListDal cpdal = new CoHousingFund_ListDal();
						list = cpdal.getcpplist();
					} else {
						if (clList.get(0).getCoco_cpp() != null
								&& !clList.get(0).getCoco_cpp().equals("")) {

							EmHouseCpp chp = new EmHouseCpp();
							chp.setCp(Double.valueOf(clList.get(0)
									.getCoco_cpp()));
							chp.setCpName((int) (Double.valueOf(clList.get(0)
									.getCoco_cpp()) * 100) + "%");

							list.add(chp);
						}
					}
				} else {
					Messagebox.show("公司合同公积金比例缺失.", "操作提示", Messagebox.OK,
							Messagebox.ERROR);
					EmHouseCpp chp = new EmHouseCpp();
					list.add(chp);
				}

			}
		}

		return list;
	}

	// 获取未来6个月的所属月份数组
	public String[] getOwnmonthByNow(boolean exNow) {
		String[] month = null;
		try {
			String nowMonth = DateStringChange
					.DatetoSting(new Date(), "yyyyMM");
			month = MonthListUtil.getMonthList(exNow, nowMonth, "f", 6);
		} catch (Exception e) {

		}
		return month;
	}

	// 判断当月是否可操作社保
	public boolean ifStop() {
		boolean b = false;
		try {
			if (ifNowLessSbUpdateOwnmonth()) {
				int stopDay = getSbSetup().getLastday();
				if (compareNowDay(stopDay)) {
					return true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return b;
	}

	// 判断社保在册的月份是否小于当前月份
	private boolean ifNowLessSbUpdateOwnmonth() {
		boolean b = false;
		try {
			int ownmonth = getSbUpdateOwnmonth();
			if (ownmonth != 0) {
				Date now = new Date();
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMM");
				String nowStr = dateFormat.format(now);
				if (ownmonth <= Integer.parseInt(nowStr)) {
					return true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return b;
	}

	// 与当前天数比较大小
	private boolean compareNowDay(int day) {
		boolean b = false;
		try {
			Calendar c = Calendar.getInstance();
			int now = c.get(Calendar.DATE);
			// 小于返回TRUE
			if (day < now) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return b;
	}

	// 获取社保在册数据的所属月份
	public int getSbUpdateOwnmonth() {
		Emsi_SelDal dal = new Emsi_SelDal();
		return dal.getSbUpdateOwnmonth();
	}

	// 获取社保设置
	public EmShebaoSetupModel getSbSetup() {
		Emsi_SelDal dal = new Emsi_SelDal();
		return dal.getSbSetup();
	}

	/**
	 * 员工信息必填字段新增
	 * 
	 * @param m
	 * @return
	 */
	public int EmbaseRequiredAdd(EmBaseRequiredModel m) {
		Integer i = 0;
		EmBaseLogin_AddDal dal = new EmBaseLogin_AddDal();
		i = dal.EmbaseRequiredAdd(m);
		return i;
	}

	/**
	 * 员工信息新增
	 * 
	 * @param em
	 * @return 新增id
	 */
	public String[] EmbaseloginAdd(Object... objs) {
		Object[] obj = new Object[5];
		obj[0] = "1";
		obj[1] = objs[0];
		obj[2] = objs[2];
		WfBusinessService wfbs = new EmBase_NotinAddImpl();
		WfOperateService wfs = new WfOperateImpl(wfbs);
		String[] str = wfs.AddTaskToNext(obj, "员工入职(预增)",
				((EmbaseModel) obj[1]).getEmba_name() + "入职", 5,
				UserInfo.getUsername(), "", 0, "");
		return str;
	}

	/**
	 * 员工信息新增
	 * 
	 * @param em
	 * @return 新增id
	 */
	// public int EmbaseNotinAdd(EmbaseModel em) {
	// int i = 0;
	// EmBaseLogin_AddDal dal = new EmBaseLogin_AddDal();
	// i = dal.EmbaseloginAdd(em);
	// return i;
	// }

	// 员工信息修改
	public Integer modInfo(EmbaseModel em) {
		Integer i = 0;
		Embasedal dal = new Embasedal();
		i = dal.modInfo(em);
		return i;
	}

	// 员工入职生成社保公积金在册数据
	public Integer createInfo(EmbaseModel m, EmShebaoFormulaModel formula) {
		Integer i = 1;
		boolean sb = false;
		boolean sbjlbj = false;
		Integer house = 0;
		EmHouseUpdateDal dalh = new EmHouseUpdateDal();
		Emsi_OperateDal dalsb = new Emsi_OperateDal();

		dalh.delupdate(m.getGid());
		dalh.delbj(m.getGid());
		dalsb.delupdate(m.getGid());
		dalsb.delbj(m.getGid());
		dalsb.deljlbj(m.getGid());

		if (m.getEmba_house_place().equals("本地")) {

			if (m.getEmba_emhb_ownmonth() != null
					&& Integer.valueOf(m.getEmba_emhb_ownmonth()) > 0) {

				house = dalh.addData(m.getGid(), UserInfo.getUsername(),
						Integer.valueOf(m.getEmba_emhb_ownmonth()));
				if (!(house > 0)) {
					i = 0;
				}
			}
			// 生成公积金补缴数据
			if (m.getEmba_emhb_startdate() != null
					&& Integer.valueOf(m.getEmba_emhb_startdate()) > 0) {

				dalh.addBjData(m.getGid(),
						Integer.valueOf(m.getEmba_emhb_ownmonth()),
						UserInfo.getUsername());
			}
		}
		// 生成社保在册数据
		if (m.getEmba_emsb_ownmonth() != null
				&& Integer.valueOf(m.getEmba_emsb_ownmonth()) > 0) {

			sb = insertShebaoUpdate(m.getGid(),
					Integer.valueOf(m.getEmba_emsb_ownmonth()),
					m.getEmba_computerid(), m.getEmba_sb_radix().intValue(),
					formula);
			if (!sb) {
				i = 0;
			}
		}
		// 生成社保补缴数据
		if (m.getEmba_emsb_m1() != null
				&& Integer.valueOf(m.getEmba_emsb_m1()) > 0) {
			sbjlbj = false;
			sbjlbj = insertShebaoBj(m.getGid(),
					Integer.valueOf(m.getEmba_emsb_ownmonth()),
					Integer.valueOf(m.getEmba_emsb_feeownmonth()),
					Integer.valueOf(m.getEmba_emsb_m1()), m.getEmba_emsb_r1(),
					formula.getEmsf_soin_title());
			// 生成医疗补交数据
			if (sbjlbj && m.isChk_jlbj1()) {
				insertShebaoJLBj(m.getGid(),
						Integer.valueOf(m.getEmba_emsb_ownmonth()),
						Integer.valueOf(m.getEmba_emsb_feeownmonth()),
						Integer.valueOf(m.getEmba_emsb_m1()),
						m.getEmba_emsb_r1(), formula.getEmsf_soin_title());
			}
			if (m.getEmba_emsb_m2() != null
					&& Integer.valueOf(m.getEmba_emsb_m2()) > 0) {
				sbjlbj = false;
				sbjlbj = insertShebaoBj(m.getGid(),
						Integer.valueOf(m.getEmba_emsb_ownmonth()),
						Integer.valueOf(m.getEmba_emsb_feeownmonth()),
						Integer.valueOf(m.getEmba_emsb_m2()),
						m.getEmba_emsb_r2(), formula.getEmsf_soin_title());
				// 生成医疗补交数据
				if (sbjlbj && m.isChk_jlbj2()) {
					insertShebaoJLBj(m.getGid(),
							Integer.valueOf(m.getEmba_emsb_ownmonth()),
							Integer.valueOf(m.getEmba_emsb_feeownmonth()),
							Integer.valueOf(m.getEmba_emsb_m2()),
							m.getEmba_emsb_r2(), formula.getEmsf_soin_title());
				}
			}
			if (m.getEmba_emsb_m3() != null
					&& Integer.valueOf(m.getEmba_emsb_m3()) > 0) {
				sbjlbj = false;
				sbjlbj = insertShebaoBj(m.getGid(),
						Integer.valueOf(m.getEmba_emsb_ownmonth()),
						Integer.valueOf(m.getEmba_emsb_feeownmonth()),
						Integer.valueOf(m.getEmba_emsb_m3()),
						m.getEmba_emsb_r3(), formula.getEmsf_soin_title());
				// 生成医疗补交数据
				if (sbjlbj && m.isChk_jlbj3()) {
					insertShebaoJLBj(m.getGid(),
							Integer.valueOf(m.getEmba_emsb_ownmonth()),
							Integer.valueOf(m.getEmba_emsb_feeownmonth()),
							Integer.valueOf(m.getEmba_emsb_m3()),
							m.getEmba_emsb_r3(), formula.getEmsf_soin_title());
				}
			}
		}
		return i;
	}

	// 插入社保在册数据
	public boolean insertShebaoUpdate(int gid, int ownmonth, String computerid,
			int radix, EmShebaoFormulaModel formula) {
		boolean bool = false;
		try {
			EmShebaoUpdateModel m = new EmShebaoUpdateModel();
			m.setGid(gid);
			m.setFormulaid(formula.getId());
			m.setEsiu_yl(formula.getEmsf_yl());
			m.setEsiu_gs(formula.getEmsf_gs());
			m.setEsiu_sye(formula.getEmsf_sye());
			m.setEsiu_syu(formula.getEmsf_syu());
			m.setEsiu_yltype(formula.getEmsf_yltype());
			m.setEmsf_soin_title(formula.getEmsf_soin_title());
			m.setOwnmonth(ownmonth);
			m.setEsiu_radix(radix);
			m.setEsiu_addname(UserInfo.getUsername());
			Emsi_OperateBll bll = new Emsi_OperateBll();
			bll.sumSbItemFee(m);
			if (bll.insertShebaoupdate(m))
				bool = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bool;
	}

	// 插入社保补缴数据
	public boolean insertShebaoBj(int gid, int ownmonth, int feeOwnmonth,
			int bjOwnmonth, int radix, String Soin_title) {
		boolean bool = false;
		try {
			Emsi_OperateBll bll = new Emsi_OperateBll();
			EmShebaoBJModel m = new EmShebaoBJModel();
			// 计算养老保险费用
			BigDecimal[] yl = bll.sumYlFee(radix, bjOwnmonth, Soin_title);
			if (yl.length > 0) {
				m.setEmsb_totalcp(yl[0]);
				m.setEmsb_totalop(yl[1]);
			}
			m.setGid(gid);
			m.setOwnmonth(ownmonth);
			m.setEmsb_radix(radix);
			m.setEmsb_startmonth(bjOwnmonth);
			m.setEmsb_stopmonth(bjOwnmonth);
			m.setEmsb_feeownmonth(feeOwnmonth);
			m.setEmsb_addname(UserInfo.getUsername());
			m.setEmsb_ifdeclare(4);
			m.setSoin_title(Soin_title);
			if (bll.insertShebaoBj(m))
				bool = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bool;
	}

	// 插入社保医疗补缴数据
	public boolean insertShebaoJLBj(int gid, int ownmonth, int feeOwnmonth,
			int bjOwnmonth, int radix, String Soin_title) {
		boolean bool = false;
		try {
			Emsi_OperateBll bll = new Emsi_OperateBll();
			EmShebaoBJModel m = new EmShebaoBJModel();
			// 计算医疗保险费用
			BigDecimal[] jl = bll.sumJlFee(radix, bjOwnmonth, Soin_title);
			if (jl.length > 0) {
				m.setEmsb_totalcp(jl[0]);
				m.setEmsb_totalop(jl[1]);
			}
			m.setGid(gid);
			m.setOwnmonth(ownmonth);
			m.setEmsb_radix(radix);
			m.setEmsb_startmonth(bjOwnmonth);
			m.setEmsb_stopmonth(bjOwnmonth);
			m.setEmsb_feeownmonth(feeOwnmonth);
			m.setEmsb_addname(UserInfo.getUsername());
			m.setEmsb_ifdeclare(4);
			m.setSoin_title(Soin_title);
			if (bll.insertShebaoBjJL(m))
				bool = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bool;
	}

	/**
	 * 员工信息修改
	 * 
	 * @param em
	 * @return 新增id
	 */
	public String[] EmbaseloginUpdate(Object... objs) {
		Object[] obj = new Object[5];
		obj[0] = "2";
		obj[1] = objs[0];
		obj[2] = objs[2];
		WfBusinessService wfbs = new EmBase_NotinAddImpl();
		WfOperateService wfs = new WfOperateImpl(wfbs);
		String[] str = wfs.PassToNext(obj,
				Integer.parseInt(objs[1].toString()), UserInfo.getUsername(),
				"", 0, "");
		return str;
	}

	/**
	 * 员工信息暂存
	 * 
	 * @param em
	 * @return 新增id
	 */
	public int EmbaseloginUpdatezc(EmbaseModel m) {

		EmBaseLogin_AddDal dal = new EmBaseLogin_AddDal();

		return dal.EmbaseloginUpdate(m);

	}

	/**
	 * 更新notin表emba_type
	 * 
	 * @param em
	 * @return 新增id
	 */
	public int EmbaseloginUpdatezc(int emba_type, int emba_id) {

		EmBaseLogin_AddDal dal = new EmBaseLogin_AddDal();

		return dal.EmbaseloginUpdateembatype(emba_type, emba_id);

	}

	/**
	 * 员工信息新增
	 * 
	 * @param em
	 * @return 新增id
	 */
	public String[] EmbaseAdd(EmbaseModel em) {
		Object[] objs = { "3", em };
		WfBusinessService wfbs = new EmBase_NotinAddImpl();
		WfOperateService wfs = new WfOperateImpl(wfbs);
		String[] str = wfs.AddTaskToNext(objs, "员工入职(无预增)", em.getEmba_name()
				+ "入职", 87, UserInfo.getUsername(), "", em.getCid(), "");
		
		
		

		return str;
	}
}
