package bll.SystemControl;

import java.sql.SQLException;
import java.util.List;

import org.zkoss.zul.ListModelList;

import dal.EmBaseCompact.EmBaseCompact_BaseDal;
import dal.EmCommercialInsurance.CI_Insurant_ListDal;
import dal.EmCommissionOut.EmCommissionOutDal;
import dal.EmHouse.EmHouseUpdateDal;
import dal.EmSheBao.EmSheBao_DSelectDal;
import dal.Embase.EmOnBoardListDal;
import dal.Embase.Embasedal;
import dal.SystemControl.EmBuCenter_SelectDal;

import Model.CoCompactModel;
import Model.EmOnBoardListModel;
import Model.EmbaseBusinessCenterModel;
import Model.EmbaseModel;

public class EmBuCenter_SelectBll {

	// 获取业务中心菜单名称
	public List<EmbaseBusinessCenterModel> getEmbaseBusinessCenterInfo(
			String str) {
		EmBuCenter_SelectDal dal = new EmBuCenter_SelectDal();
		return dal.getEmbaseBusinessCenterInfo(str);
	}

	// 获取业务中心菜单名称
	public List<EmbaseBusinessCenterModel> getEmbaseBusinessCenterInfos(
			String str) {
		EmBuCenter_SelectDal dal = new EmBuCenter_SelectDal();
		return dal.getEmbaseBusinessCenterInfos(str);
	}

	// 菜单角色分配表的某条信息
	public List<EmbaseBusinessCenterModel> getbumenupub(String str) {
		EmBuCenter_SelectDal dal = new EmBuCenter_SelectDal();
		return dal.getbumenupub(str);
	}

	// 菜单角色分配表的某条信息
	public List<EmbaseBusinessCenterModel> getEmOnBoardList(int gid) {
		EmBuCenter_SelectDal dal = new EmBuCenter_SelectDal();
		return dal.getEmOnBoardList(gid);
	}

	// 获取员工的gid
	public Integer getgid(int id) {
		/*
		 * EmBuCenter_SelectDal dal=new EmBuCenter_SelectDal();
		 */
		Embasedal dal = new Embasedal();

		return dal.getEmBaseById(id).get(0).getGid();
	}

	// 根据员工编号查询员工基本信息表是否有电脑号和个人公积金号
	public EmbaseModel getEmbaseID(Integer gid) {
		EmBuCenter_SelectDal dal = new EmBuCenter_SelectDal();
		return dal.getEmbaseID(gid);
	}

	public List<EmbaseModel> getEmbaseList(Integer gid) {
		List<EmbaseModel> list = new ListModelList<>();
		Embasedal dal = new Embasedal();

		list = dal.getEmBaseByGid(gid);

		return list;
	}

	/**
	 * @Title: getentry
	 * @Description: TODO(判断员工业务信息是否已经录入完整)
	 * @param gid
	 * @return
	 * @return boolean 返回类型
	 * @throws
	 */
	public boolean getentry(Integer gid) {
		boolean b = true;
		List<EmOnBoardListModel> list = new ListModelList<>();
		EmOnBoardListDal eobdal = new EmOnBoardListDal();
		list = eobdal.getInfoByGid(gid);
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				switch (list.get(i).getEmbo_class()) {
				case "社会保险服务":
					if (list.get(i).getEmba_sb_place() != null
							&& list.get(i).getEmba_sb_place().equals("本地")) {
						if (list.get(i).getEmba_emsb_foreigner() != null
								&& list.get(i).getEmba_emsb_foreigner()
										.equals("是")) {

						} else {
							EmSheBao_DSelectDal dal = new EmSheBao_DSelectDal();
							if (!dal.shebaoOnboard(gid)) {
								b = false;
							}
							if (list.get(i).getEmba_emsb_m1() != null
									&& !list.get(i).getEmba_emsb_m1()
											.equals("")) {
								if (!dal.shebaobjOnboard(gid)) {
									b = false;
								}
							}
						}

					} else {
						EmCommissionOutDal dal = new EmCommissionOutDal();
						if (!dal.getupdateInfo(gid)) {
							b = false;
						}
					}
					break;
				case "住房公积金服务":
					if (list.get(i).getEmba_house_place() != null
							&& list.get(i).getEmba_house_place().equals("本地")) {

						EmHouseUpdateDal dal = new EmHouseUpdateDal();
						if (!dal.houseOnboard(gid)) {
							b = false;
						}
						if (list.get(i).getEmba_emhb_startdate() != null
								&& !list.get(i).getEmba_emhb_startdate()
										.equals("")) {
							if (!dal.housebjOnboard(gid)) {
								b = false;
							}
						}

					} else {
						EmCommissionOutDal dal = new EmCommissionOutDal();
						if (!dal.getupdateInfo(gid)) {
							b = false;
						}
					}
					break;
				case "劳动用工手续办理":
					EmBaseCompact_BaseDal dal = new EmBaseCompact_BaseDal();
					if (!dal.getupdateInfo(gid)) {
						b = false;
					}
					break;
				default:
					if (list.get(i).getCopr_cpac_id().equals(6)) {
						CI_Insurant_ListDal dal2 = new CI_Insurant_ListDal();
						if (!dal2
								.getupdateInfo(gid, list.get(i).getCopr_name())) {
							b = false;
						}
					}
					break;
				}
			}
		}

		return b;
	}

	// 根据员工编号查询员工合同信息
	public CoCompactModel getEmBaseCompact(Integer gid) {
		Embasedal dal = new Embasedal();
		return dal.getEmBaseCompact(gid);
	}
}
