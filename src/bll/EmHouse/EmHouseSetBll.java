package bll.EmHouse;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.zkoss.zul.ListModelList;

import Model.CoHousingFundModel;
import Model.CoglistModel;
import Model.EmHouseChangeModel;
import Model.EmHouseCpp;
import Model.EmHouseSetupModel;
import Model.SocialInsuranceClassInfoViewModel;
import Util.DateStringChange;
import Util.DateUtil;
import Util.MonthListUtil;
import Util.SocialInsuranceCalculator;
import dal.CoHousingFund.CoHousingFund_ListDal;
import dal.EmHouse.EmHouseChangeDal;
import dal.EmHouse.EmHouseCompanyIdDal;
import dal.EmHouse.EmHouseSetupDal;
import dal.EmHouse.EmHouseUpdateDal;
import dal.Embase.CoglistDal;

public class EmHouseSetBll {

	// 获取当前公积金所属月
	public Integer nowmonth() {
		Integer i = 0;
		String s = "";
		EmHouseUpdateDal dal = new EmHouseUpdateDal();
		s = dal.getOwnmonth();
		if (!s.equals("")) {
			i = Integer.valueOf(s);
		}
		return i;
	}

	// 按自然月判断当前所属月份
	public Integer nowmonth2(Integer gid) {
		Integer i = Integer.valueOf(DateStringChange.getOwnmonth());
		CoglistDal dal = new CoglistDal();
		List<CoglistModel> list = dal.gjjOwnmonth(gid);

		if (list.size() > 0) {
			if (list.get(0).getGjjlastday() != null) {
				if (list.get(0).getGjjlastday().equals(0)) {
					i = 0;
				} else {

					Integer d1 = list.get(0).getGjjlastday();
					if (list.get(0).getCoco_house() != null
							&& list.get(0).getCoco_house().equals("中智开户")) {
						d1 = list.get(0).getGjjzzlastday();
					}
					Calendar calendar = new GregorianCalendar();
					Date trialTime = new Date();
					calendar.setTime(trialTime);
					Integer d2 = calendar.get(Calendar.DATE);
					if (d1 - d2 <= 0) {
						i = Integer.valueOf(DateStringChange.ownmonthAdd(
								i.toString(), 1));
					}
				}
			}
		}
		return i;
	}

	// 获取员工账户类型
	public Integer houseSingle(Integer gid, Integer ownmonth) {
		Integer i = null;
		if (gid != null && ownmonth != null) {
			EmHouseChangeDal dal = new EmHouseChangeDal();
			i = dal.emhouseSingle(gid, ownmonth);
		}
		return i;
	}

	// 获取上下限
	public List<BigDecimal> getLimit(Double r, Double cpp) {
		List<BigDecimal> rlist = new ArrayList<>();
		SocialInsuranceCalculator sm = new SocialInsuranceCalculator();
		Integer id = sm.getSionId("深户员工", "深圳", "深圳中智经济技术合作有限公司");
		Date d = new Date();
		sm.setBcgjj(false);
		List<SocialInsuranceClassInfoViewModel> list = sm.getGjjItemFee(id,
				new BigDecimal(r), new BigDecimal(cpp), d);
		BigDecimal fee = list.get(0).getFee();
		BigDecimal radix = list.get(0).getRadix();

		fee = fee.setScale(2, BigDecimal.ROUND_HALF_UP);
		radix = radix.setScale(2, BigDecimal.ROUND_HALF_UP);
		rlist.add(radix);
		rlist.add(fee);
		return rlist;
	}

	// 判断当月变更数据是否禁止提交,返回TRUE表示禁止提交
	public boolean gjjOnair(Integer cid, Integer gid, Integer ownmonth,
			Integer singel) {
		boolean b = false;
		Integer nownmonth = nowmonth();
		if (ownmonth == null) {
			ownmonth = nownmonth;
		}
		if (ownmonth<= nowmonth()) {

			if (singel == null) {
				singel = houseSingle(gid, ownmonth);
			}
			Calendar calendar = new GregorianCalendar();
			Date nowDate = new Date(); // 获取当前时间
			calendar.setTime(nowDate);
			if (Integer.valueOf(ownmonth.toString().substring(0, 4)) < calendar
					.get(Calendar.YEAR)) {
				b = true;
			} else {
				if (Integer.valueOf(ownmonth.toString().substring(4, 6)) < (calendar
						.get(Calendar.MONTH) + 1)) {
					b = true;
				}
			}

			if (!b) {

				if (singel != null) {
					List<EmHouseSetupModel> list = new ListModelList<>();
					EmHouseSetupDal dal = new EmHouseSetupDal();
					list = dal.getList();

					if (list.size() > 0) {
						// 禁止当月数据数据提交
						if (list.get(0).getOnair().equals(1)) {
							b = true;
						} else {
							if (singel.equals(1)) {

								// 独立户判断
								List<CoHousingFundModel> list2 = new ListModelList<>();
								EmHouseCompanyIdDal dal2 = new EmHouseCompanyIdDal();
								list2 = dal2.getlistByCid(cid);

								if (list2.size() > 0) {
									if (list2.get(0).getCohf_lastday() != null
											&& list2.get(0).getCohf_lastday() < calendar
													.get(Calendar.DATE)) {
										b = true;
									}
								}
							} else {
								// 中智户
								if (list.get(0).getLastday() != null
										&& Integer.valueOf(list.get(0)
												.getLastday()) < calendar
												.get(Calendar.DATE)) {// 中智户最后截单日
									b = true;
								}

							}
						}
					} else {
						b = true;
					}
				}
			}
		}
		return b;
	}

	// 是否禁止提交补缴数据
	public boolean gjjOnairbj(Integer cid, Integer gid, Integer ownmonth,
			Integer singel) {
		boolean b = false;
		if (singel == null) {
			singel = houseSingle(gid, ownmonth);
		}
		List<EmHouseSetupModel> list = new ListModelList<>();
		List<CoHousingFundModel> list2 = new ListModelList<>();
		if (singel != null) {
			Calendar calendar = new GregorianCalendar();
			Date nowDate = new Date(); // 获取当前时间
			calendar.setTime(nowDate);

			EmHouseSetupDal dal = new EmHouseSetupDal();
			list = dal.getList();
			if (list.size() > 0) {
				// 禁止当月数据数据提交
				if (list.get(0).getOnairbj() != null
						&& list.get(0).getOnairbj().equals(1)) {
					b = true;
				} else {

					if (singel.equals(1)) {

						// 独立户判断
						EmHouseCompanyIdDal dal2 = new EmHouseCompanyIdDal();
						list2 = dal2.getlistByCid(cid);
						if (list2.size() > 0) {
							if (list2.get(0).getCohf_if_bj() != null
									&& list2.get(0).getCohf_if_bj().equals(1)) {
								b = true;
							} else {
								if (list2.get(0).getCohf_lastday() != null
										&& list2.get(0).getCohf_lastday() < calendar
												.get(Calendar.DATE)) {
									b = true;
								}
							}

						} else {
							b = true;
						}

					} else {
						// 中智户截单日
						if (list.get(0).getLastdaybj() != null
								&& list.get(0).getLastdaybj() < calendar
										.get(Calendar.DATE)) {
							b = true;
						}
					}
				}
			} else {
				b = true;
			}
		}

		return b;
	}

	// 获取所属月份列表
	public List<String> ownmonthlist(String type, Integer cid, Integer gid,
			Integer ownmonth) {
		String[] s = new String[3];
		List<String> list = new ArrayList<>();

		if (ownmonth == null) {
			ownmonth = nowmonth();
		}

		if (type.equals("update")) {
			if (gjjOnair(cid, gid, ownmonth, null)) {
				ownmonth = Integer.valueOf(DateStringChange
						.ownmonthAddoneMonth(ownmonth.toString()));
			}

			s = MonthListUtil.getMonthList(true, ownmonth.toString(), "f", 3);

		} else if (type.equals("bj")) {

			if (gjjOnairbj(cid, gid, ownmonth, null)) {
				ownmonth = Integer.valueOf(DateStringChange
						.ownmonthAddoneMonth(ownmonth.toString()));
			}
			s = MonthListUtil.getMonthList(true, ownmonth.toString(), "f", 3);

		} else if (type.equals("fee")) {
			s = MonthListUtil.getMonthList(
					true,
					DateUtil.dateAdd(DateStringChange.StringtoDate(
							ownmonth.toString(), "yyyyMM"), "M", -2), "f", 5);
		}

		for (int i = 0; i < s.length; i++) {
			list.add(s[i]);
		}
		return list;
	}

	// 判断是否允许提交比例基数调整数据
	public boolean aduitData(Integer gid, Integer ownmonth) {
		boolean b = false;
		EmHouseChangeDal dal = new EmHouseChangeDal();
		EmHouseChangeModel em = new EmHouseChangeModel();
		em.setGid(gid);
		em.setDataState(3);
		em.setOwnmonth(ownmonth);
		List<EmHouseChangeModel> list = dal.getList(em);
		if (list.size() > 0) {
			if (list.size() == 1) {
				if (!list.get(0).getEmhc_change().equals("停交")) {
					b = true;
				}
			}
		}

		return b;
	}

	public List<EmHouseCpp> cplist(Integer gid) {
		List<EmHouseCpp> list = new ListModelList<>();
		CoHousingFund_ListDal dal = new CoHousingFund_ListDal();
		list = dal.getcpList(gid);
		return list;
	}

	// 判断是否要审核
	public boolean gjjaudit(Integer cid, Integer gid, Integer ownmonth,
			Integer singel) {
		boolean b = false;
		List<EmHouseSetupModel> list = new ListModelList<>();
		List<CoHousingFundModel> list2 = new ListModelList<>();
		if (singel == null) {
			singel = houseSingle(gid, ownmonth);
		}

		if (singel != null) {
			Calendar calendar = new GregorianCalendar();
			Date nowDate = new Date(); // 获取当前时间
			calendar.setTime(nowDate);

			if (singel.equals(0) || singel.equals(2)) {
				EmHouseSetupDal dal = new EmHouseSetupDal();
				list = dal.getList();
				if (list.size() > 0) {
					if (!gjjOnair(cid, gid, ownmonth, singel)) {
						if (calendar.get(Calendar.DATE) >= list.get(0)
								.getAuditday()) {
							b = true;
						}
					}
				}

			} else {

				EmHouseCompanyIdDal dal = new EmHouseCompanyIdDal();
				list2 = dal.getlistByCid(cid);
				if (list2.size() > 0) {
					if (!gjjOnair(cid, gid, ownmonth, singel)) {
						if (calendar.get(Calendar.DATE) >= list2.get(0)
								.getCohf_tsday()) {
							b = true;
						}
					}
				}

			}

		}

		return b;
	}

	// 判断是否超限
	public boolean boolLimit(BigDecimal d) {
		Double radix = radixLimit(d);
		if (d.equals(radix)) {
			return false;
		} else {
			return true;
		}

	}

	// 返回公积金超范围限额
	public Double radixLimit(BigDecimal d) {
		BigDecimal radix = new BigDecimal(0);
		SocialInsuranceCalculator si = new SocialInsuranceCalculator();
		radix = si.checkGjjBasic(d);

		return radix.doubleValue();
	}

}
