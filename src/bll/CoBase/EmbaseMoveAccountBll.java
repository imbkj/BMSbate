package bll.CoBase;

import impl.WorkflowCore.WfOperateImpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zul.ListModelList;

import service.WorkflowCore.WfBusinessService;
import service.WorkflowCore.WfOperateService;
import bll.EmHouse.EmHouse_EditImpl;
import bll.EmSheBao.Emsi_OperateBll;
import bll.EmSheBao.Emsi_SelBll;

import dal.CoCompact.CocompactDal;
import dal.CoHousingFund.CoHousingFund_ListDal;
import dal.EmHouse.EmHouseChangeDal;
import dal.EmHouse.EmHouseSetupDal;
import dal.EmSheBao.EmSheBao_DSelectDal;
import dal.EmSheBao.Emsi_SelDal;
import dal.Embase.CoglistDal;

import Controller.EmSheBaocard.newExcelImpl;
import Model.CoCompactModel;
import Model.CoHousingFundModel;
import Model.CoOfferListModel;
import Model.CoOfferModel;
import Model.CoglistModel;
import Model.EmHouseChangeModel;
import Model.EmHouseSetupModel;
import Model.EmShebaoSetupModel;
import Model.EmShebaoUpdateModel;
import Util.DateUtil;
import Util.UserInfo;

public class EmbaseMoveAccountBll {

	/**
	 * @Title: getembaseList
	 * @Description: TODO(所属项目员工列表)
	 * @param id
	 * @return
	 * @return List<CoglistModel> 返回类型
	 * @throws
	 */
	public List<CoglistModel> getembaseList(Integer id) {
		List<CoglistModel> list = new ListModelList<>();
		CoglistDal dal = new CoglistDal();
		list = dal.getembaseList(id);
		return list;
	}

	/**
	 * @Title: getcompactList
	 * @Description: TODO(获取合同列表)
	 * @param cid
	 * @param name
	 * @return
	 * @return List<CoCompactModel> 返回类型
	 * @throws
	 */
	public List<CoCompactModel> getcompactList(Integer cid, String name,
			String single, Boolean b) {
		List<CoCompactModel> list = new ListModelList<>();
		CocompactDal dal = new CocompactDal();
		list = dal.getCompactList(cid, name, single, b);
		return list;
	}

	/**
	 * @Title: getcoofferList
	 * @Description: TODO(获取报价单列表)
	 * @param coId
	 * @param name
	 * @return
	 * @return List<CoOfferModel> 返回类型
	 * @throws
	 */
	public List<CoOfferModel> getcoofferList(Integer coId, String name,
			String single, Boolean b) {
		List<CoOfferModel> list = new ListModelList<>();
		CocompactDal dal = new CocompactDal();
		list = dal.getcoofferList(coId, name, single, b);
		return list;
	}

	/**
	 * @Title: getitemList
	 * @Description: TODO(获取报价单项目列表(社保,公积金))
	 * @param coofId
	 * @return
	 * @return List<CoOfferListModel> 返回类型
	 * @throws
	 */
	public List<CoOfferListModel> getitemList(Integer coofId, String name,
			String single, Boolean b) {
		List<CoOfferListModel> list = new ListModelList<>();
		CocompactDal dal = new CocompactDal();
		list = dal.getitemList(coofId, name, single, b);
		return list;
	}

	public boolean aduitData(String type, Integer gid, Integer ownmonth) {
		boolean b = false;
		if (type.equals("社会保险服务")) {
			Emsi_SelDal dal = new Emsi_SelDal();
			EmShebaoUpdateModel em = new EmShebaoUpdateModel();
			em = dal.getShebaoUpdateByGid(gid);
			if (em != null) {
				b = true;
			}

		} else if (type.equals("住房公积金服务")) {
			EmHouseChangeDal dal = new EmHouseChangeDal();
			EmHouseChangeModel em = new EmHouseChangeModel();
			em.setGid(gid);
			em.setDataState(3);
			em.setOwnmonth(ownmonth);
			List<EmHouseChangeModel> list = dal.getList(em);
			if (list.size() > 0) {
				if (list.size() == 0) {

					if (list.get(0).getEmhc_change().equals("停交")) {
						b = true;
					}
				}
			} else {
				b = true;
			}
		}
		return b;
	}

	public Integer changeAccount(String type, Integer cid, Integer gid,
			String name, Integer oldmonth, Integer ownmonth, Integer coliId1,
			Integer coliId2, Integer groupId, Integer groupCount) {
		Integer i = 0;
		CoglistDal cdal = new CoglistDal();
		// 修改旧数据
		CoglistModel cm = new CoglistModel();
		cm.setCgli_stopdate(oldmonth);
		cm.setCgli_state(0);
		cdal.updateCoglist(cm, null, coliId1, gid, null, 1);
		// 新增
		CoglistModel cm2 = new CoglistModel();
		cm2.setCid(cid);
		cm2.setGid(gid);
		cm2.setCgli_coli_id(coliId2);
		cm2.setCgli_startdate(ownmonth);
		cm2.setCgli_startdate2(ownmonth);
		cm2.setCgli_group_id(groupId);
		cm2.setCgli_group_count(groupCount);
		cdal.add(cm2);
		// 修改业务数据
		if (type.equals("社会保险服务")) {
			Emsi_SelBll bll = new Emsi_SelBll();
			EmShebaoUpdateModel em = bll.getShebaoUpdateByGid(gid);
			if (em != null) {
				Emsi_OperateBll obll = new Emsi_OperateBll();
				if (em.getEsiu_ifstop() == 1) {

					em.setEsiu_addname(UserInfo.getUsername());
					em.setOwnmonth(ownmonth);
					em.setEsiu_remark("社保转移");
					String[] str = obll.movein(em);
					if (str[0].equals("1")) {
						i = Integer.valueOf(str[3]);
					}
				} else {
					em.setIfdeclare(0);
					em.setEsiu_addname(UserInfo.getUsername());
					em.setOwnmonth(ownmonth);
					em.setEsiu_remark("社保转移");
					em.setEsiu_stopreason("非本人意愿中断就业");
					String[] str = obll.stop(em);
					if (str[0].equals("1")) {
						em.setEsiu_stopreason(null);
						em.setEsiu_remark("社保转移");
						String[] s = obll.movein(em);
						if (s[0].equals("1")) {
							i = Integer.valueOf(s[3]);
						}
					}
				}
			}

		} else if (type.equals("住房公积金服务")) {
			WfBusinessService wfbs = new EmHouse_EditImpl();
			WfOperateService wfs = new WfOperateImpl(wfbs);
			EmHouseChangeModel em = new EmHouseChangeModel();
			em.setGid(gid);
			em.setOwnmonth(ownmonth);
			em.setEmhc_ifdeclare(0);
			em.setEmhc_addname(UserInfo.getUsername());
			Object[] obj = { "调整账户", em };
			Map<String, String> map = new HashMap();
			map.put("cid", cid.toString());
			map.put("gid", gid.toString());
			String s[] = wfs.AddTaskToNext(obj, "员工公积金变更", ownmonth + name
					+ "(" + gid + ")转移账户", 106, UserInfo.getUsername(), "",
					cid, "", map);
			if (s[0].equals("1")) {
				i = Integer.valueOf(s[3]);
			}
		}
		return i;
	}

	/**
	 * @Title: judgeDate
	 * @Description: TODO(判断当前月份是否允许提交转账户操作)
	 * @param cm
	 * @param type
	 * @return
	 * @return boolean 返回类型
	 * @throws
	 */
	public boolean judgeDate(Integer cid, String type) {
		boolean b = true;
		Date d = new Date();
		if (type.equals("社会保险服务")) {
			if (shebaoStop()) {
				b = false;
			}
		} else if (type.equals("住房公积金服务")) {
			if (houseStop(cid)) {
				b = false;
			}
		}
		return b;
	}

	/**
	 * @Title: shebaoStop
	 * @Description: TODO(判断当前时间是否允许提交社保变更)
	 * @return
	 * @return boolean 返回类型
	 * @throws
	 */
	public boolean shebaoStop() {
		boolean b = false;
		EmSheBao_DSelectDal dal = new EmSheBao_DSelectDal();
		List<EmShebaoSetupModel> list = dal.getList();
		if (list.size() > 0) {

			if (list.get(0).getOnair() == 0) {

				Date d = new Date();
				Date lastday = null;

				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				if (list.get(0).getLastdate() != null) {
					try {
						lastday = sdf.parse(list.get(0).getLastdate());
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
				if (lastday != null) {
					if (DateUtil.datediff(d, lastday, "M") >= 0) {
						if (DateUtil.datediff(lastday, d, "d") > 0) {
							b = true;
						}
					} else {
						b = true;
					}
				}
			} else {
				b = true;
			}
		}

		return b;
	}

	/**
	 * @Title: houseStop
	 * @Description: TODO(判断当前时间是否允许提交公积金变更)
	 * @param cm
	 * @return
	 * @return boolean 返回类型
	 * @throws
	 */
	public boolean houseStop(Integer cid) {
		boolean b = false;
		EmHouseSetupDal dal = new EmHouseSetupDal();
		CoHousingFund_ListDal cdal = new CoHousingFund_ListDal();
		List<EmHouseSetupModel> list = dal.getList();
		if (list.size() > 0) {
			Date d = new Date();
			Date lastday = null;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

			if (list.get(0).getOnair().equals(1)) {
				b = true;
			} else {
				// 判断中智户是否限制提交
				if (list.get(0).getLastdate() != null) {
					try {
						lastday = sdf.parse(list.get(0).getLastdate());
						if (lastday != null) {
							if (DateUtil.datediff(d, lastday, "M") >= 0) {
								if (DateUtil.datediff(lastday, d, "d") > 0) {
									b = true;
								}
							} else {
								b = true;
							}
						}
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			// 判断独立户是否限制提交
			CoHousingFundModel chm = new CoHousingFundModel();
			chm.setCid(cid);
			chm.setCohf_state(1);
			List<CoHousingFundModel> hList = cdal.getlist(chm);
			if (hList.size() > 0) {
				if (hList.get(0).getCohf_if_edit() != null
						&& !hList.get(0).getCohf_if_edit().equals("")) {

					if (hList.get(0).getLastdate() != null) {
						try {
							lastday = sdf.parse(hList.get(0).getLastdate());
							if (lastday != null) {
								if (DateUtil.datediff(d, lastday, "M") >= 0) {
									if (DateUtil.datediff(lastday, d, "d") > 0) {
										b = true;
									}
								} else {
									b = true;
								}
							}
						} catch (ParseException e) {
							e.printStackTrace();
						}
					}
				} else {
					b = true;
				}
			}
		}

		return b;
	}

}
