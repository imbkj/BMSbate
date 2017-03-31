package bll.Embase;

import impl.WorkflowCore.WfOperateImpl;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;

import bll.Archives.EmArchiveDatum_OperateBll;
import bll.EmCommercialInsurance.CI_Insurant_ListBll;
import bll.EmCommercialInsurance.CI_Insurant_OperateBll;
import bll.EmHouse.EmHouse_EditBJImpl;
import bll.EmHouse.EmHouse_EditBll;
import bll.EmHouse.EmHouse_EditImpl;
import bll.EmSalary.EmSalary_SalarySelBll;
import bll.EmSheBao.Emsc_DeclareOperateBll;
import bll.EmSheBao.Emsi_SelBll;
import service.WorkflowCore.WfBusinessService;
import service.WorkflowCore.WfOperateService;

import dal.CoCompact.CoCompactCoofferDal;
import dal.CoCompact.CocompactDal;
import dal.CoQuotation.CoofferDal;
import dal.CoQuotation.CoofferlistDal;
import dal.EmBaseCompact.EmBaseCompact_BaseDal;
import dal.EmCommercialInsurance.CI_Insurant_ListDal;
import dal.EmCommissionOut.EmCommissionOutDal;
import dal.EmHouse.EmHouseUpdateDal;
import dal.EmSalary.EmSalaryListInfoDal;
import dal.Embase.CoglistDal;
import dal.Embase.EmOnBoardListDal;
import dal.Embase.EmbaseNotdal;
import dal.Embase.Embasedal;
import dal.Taskflow.EmBaseMenulistDal;
import dal.Taskflow.TaskBatchRelBusinessDal;

import Util.CategoryModel;
import Util.DateStringChange;
import Util.IdcardUtil;
import Util.UserInfo;
import Model.CI_Insurant_ListModel;
import Model.CoCompactCoofferModel;
import Model.CoCompactModel;
import Model.CoOfferListModel;
import Model.CoOfferModel;
import Model.CoglistModel;
import Model.EmArchiveDatumModel;
import Model.EmHouseBJModel;
import Model.EmHouseChangeModel;
import Model.EmOnBoardListModel;
import Model.EmSalaryInfoModel;
import Model.EmbaseBusinessCenterModel;
import Model.EmbaseModel;
import Model.EmbaseNotInModel;
import Model.Emcontactinfo;
import Util.ConstantsUtil;

public class EmBase_OnBoardBll {

	/**
	 * 根据embaid查询gid
	 * 
	 * @return
	 */
	public int getGidForEmba(int emba_id) {
		Embasedal dal = new Embasedal();
		return dal.getGidForEmba(emba_id);
	}

	public boolean judgeEmba(String idcard) {
		Embasedal dal = new Embasedal();

		return dal.embaInfo(idcard);
	}

	/**
	 * 添加是否有档案委托服务
	 * 
	 * @param emcont
	 */
	public void addEmcont(Emcontactinfo emcont) {
		Embasedal dal = new Embasedal();
		dal.addEmcont(emcont);
	}

	/**
	 * 更新是否有档案委托服务
	 * 
	 * @param emcont
	 */
	public void modifyEmcont(Emcontactinfo emcont) {
		Embasedal dal = new Embasedal();
		dal.modifyEmcont(emcont);
	}

	/**
	 * 
	 * @return int 记录条数
	 */
	public int isShowGjj(int gid) {
		Embasedal dal = new Embasedal();
		return dal.isShowGjj(gid);
	}

	public int isShowShebao(int gid) {
		Embasedal dal = new Embasedal();
		return dal.isShowShebao(gid);
	}

	/**
	 * @Title: embaseinfo
	 * @Description: TODO(新增员工时读取未录入完整的员工基本信息)
	 * @param id
	 * @return
	 * @return List<EmbaseModel> 返回类型
	 * @throws
	 */
	public List<EmbaseModel> embaseinfo(Integer id) {
		List<EmbaseModel> list = new ListModelList<>();
		Embasedal dal = new Embasedal();
		list = dal.getEmBaseById(id);
		return list;
	}

	/**
	 * @Title: getCompactByCid
	 * @Description: TODO(查询公司所属合同)
	 * @param cid
	 * @return
	 * @return List<CoCompactModel> 返回类型
	 * @throws
	 */
	public List<CoCompactModel> getCompactByCid(Integer cid) {
		List<CoCompactModel> list = new ListModelList<CoCompactModel>();
		CocompactDal dal = new CocompactDal();
		try {
			list = dal.getCompactListByCid(cid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * @Title: getCoofferByCoId
	 * @Description: TODO(根据合同表ID获取报价单)
	 * @param coid
	 * @return
	 * @return List<CoOfferModel> 返回类型
	 * @throws
	 */
	public List<CoOfferModel> getCoofferByCoId(Integer cid, Integer coid) {
		List<CoOfferModel> list = new ListModelList<CoOfferModel>();
		CoofferDal dal = new CoofferDal();
		try {
			list = dal.getCooffer(cid, coid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * @Title: getCoofferByCid
	 * @Description: TODO(根据CID获取报价单)
	 * @param cid
	 * @return
	 * @return List<CoOfferModel> 返回类型
	 * @throws
	 */
	public List<CoOfferModel> getCoofferByCid(Integer cid) {
		List<CoOfferModel> list = new ListModelList<CoOfferModel>();
		CoofferDal dal = new CoofferDal();
		try {
			list = dal.getCoofferByCid(cid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * @Title: coofferlistInfo
	 * @Description: TODO(根据GID获取员工报价单列表)
	 * @param coofId
	 * @return
	 * @return List<CoOfferListModel> 返回类型
	 * @throws
	 */
	public List<CoglistModel> coglistInfo(Integer gid) {
		List<CoglistModel> list = new ListModelList<>();
		CoglistDal dal = new CoglistDal();
		list = dal.coglistinfo(gid);
		return list;
	}

	/**
	 * @Title: getCoofferlistByCofId
	 * @Description: TODO(根据报价单表ID获取项目列表)
	 * @param coofid
	 * @return
	 * @return List<CoOfferListModel> 返回类型
	 * @throws
	 */
	public List<CoOfferListModel> getCoofferlistBylist(Integer cid,
			Integer coid, List<CoOfferModel> list2,
			List<CoCompactCoofferModel> list3, String city, String name) {
		List<CoOfferListModel> list = new ListModelList<CoOfferListModel>();
		CoofferlistDal dal = new CoofferlistDal();
		List<Integer> l = new ArrayList<>();
		List<Integer> l2 = new ArrayList<>();
		List<String> l3 = new ArrayList<>();
		if (coid != null) {
			l.add(coid);
		}
		if (list2 != null) {
			if (list2.size() > 0) {
				for (CoOfferModel m : list2) {
					l2.add(m.getCoof_id());
				}
			}
		}
		if (list3 != null) {
			if (list3.size() > 0) {
				for (CoCompactCoofferModel m : list3) {
					l3.add(m.getColi_pclass());
				}
			}
		}

		list = dal.getCoofferlistByList(cid, l, l2, l3, city, name);

		return list;
	}

	public List<CoOfferListModel> itemCity(Integer cid,
			List<CoCompactModel> list1, List<CoOfferModel> list2,
			List<CoCompactCoofferModel> list3) {
		List<CoOfferListModel> list = new ListModelList<>();
		CoofferlistDal dal = new CoofferlistDal();
		List<Integer> l = new ArrayList<>();
		List<Integer> l2 = new ArrayList<>();
		List<String> l3 = new ArrayList<>();
		if (list1 != null) {
			if (list1.size() > 0) {
				for (CoCompactModel m : list1) {
					l.add(m.getCoco_id());
				}
			}
		}

		if (list2 != null) {
			if (list2.size() > 0) {
				for (CoOfferModel m : list2) {
					l2.add(m.getCoof_id());
				}
			}
		}
		if (list3 != null) {
			if (list3.size() > 0) {
				for (CoCompactCoofferModel m : list3) {
					l3.add(m.getColi_pclass());
				}
			}
		}
		list = dal.getlistcity(cid, l, l2, l3);

		return list;
	}

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

	/**
	 * @Title: getcoofferMenu
	 * @Description: TODO(根据勾选的报价单生成全选项目列表)
	 * @param l
	 * @return
	 * @return List<CoOfferListModel> 返回类型
	 * @throws
	 */
	public List<CoCompactCoofferModel> getcoofferMenu(List<CoOfferModel> l) {
		List<CoCompactCoofferModel> list = new ListModelList<CoCompactCoofferModel>();
		CoofferlistDal dal = new CoofferlistDal();

		list = dal.getCoofferMenuByCoofId(l);

		return list;
	}

	/**
	 * @Title: getCompactCoofferByCid
	 * @Description: TODO(获取合同分组数据)
	 * @param cid
	 * @return
	 * @return List<CoCompactCoofferModel> 返回类型
	 * @throws
	 */
	public List<CoCompactCoofferModel> getCompactCoofferByCid(Integer cid) {
		List<CoCompactCoofferModel> list = new ListModelList<CoCompactCoofferModel>();
		CoCompactCoofferDal dal = new CoCompactCoofferDal();
		try {
			list = dal.getCoCompactCoofferList(cid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * @Title: getEmbaseInfo
	 * @Description: TODO(查询员工信息)
	 * @param embaId
	 * @return
	 * @return List<EmbaseModel> 返回类型
	 * @throws
	 */
	public List<EmbaseModel> getEmbaseInfo(Integer embaId) {
		List<EmbaseModel> list = new ListModelList<EmbaseModel>();
		Embasedal dal = new Embasedal();
		try {
			list = dal.getEmBaseById(embaId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 查询员工入职树形顶层菜单list列表
	public List<EmbaseBusinessCenterModel> tree(Integer type, Integer gid) {
		List<EmbaseBusinessCenterModel> list = new ListModelList<>();
		EmBaseMenulistDal dal = new EmBaseMenulistDal();
		list = dal.treeList(type, gid);
		return list;
	}

	// 生成目录
	public CategoryModel treeNode(List<EmbaseBusinessCenterModel> list1,
			List<EmbaseBusinessCenterModel> list2, CategoryModel category) {
		Collections.sort(list2, new EmbaseBusinessCenterModel());

		for (EmbaseBusinessCenterModel m1 : list1) {
			for (EmbaseBusinessCenterModel m2 : list2) {
				if (m1.getEmce_id().equals(m2.getEmce_id())
						&& (m2.getEmce_onboard().equals(1) || m2
								.getEmce_onboard().equals(2))) {
					m1.setSel(true);
				}
			}
		}

		for (EmbaseBusinessCenterModel m : list1) {
			if (m.getSel() != null && m.getSel() == true) {
				infectUp(list1, m);
				infectDown(list1, m);
			}
		}

		for (int i = 0; i < list1.size(); i++) {
			if (list1.get(i).getSel() == null || list1.get(i).getSel() == false) {
				list1.remove(i);
				i--;
			}
		}

		category = tree(list1, category);
		return category;
	}

	public void infectUp(List<EmbaseBusinessCenterModel> list,
			EmbaseBusinessCenterModel m) {

		for (EmbaseBusinessCenterModel m1 : list) {
			if (!m.getEmce_pid().equals(0)
					&& m.getEmce_pid().equals(m1.getEmce_id())) {
				m1.setSel(true);
				infectUp(list, m1);
			}
		}

	}

	public void infectDown(List<EmbaseBusinessCenterModel> list,
			EmbaseBusinessCenterModel m) {
		for (EmbaseBusinessCenterModel m1 : list) {
			if (m.getEmce_id().equals(m1.getEmce_pid())
					&& (m1.getEmce_onboard().equals(1) || m1.getEmce_onboard()
							.equals(2))) {
				m1.setSel(true);
				infectDown(list, m1);
			}
		}
	}

	// 生成树
	public CategoryModel tree(List<EmbaseBusinessCenterModel> list,
			CategoryModel category) {
		for (int i = 0; i < list.size(); i++) {
			if (category.getId().equals(list.get(i).getEmce_pid())) {
				category.addChild(tree(list, new CategoryModel(list.get(i)
						.getEmce_id(), list.get(i).getEmce_menuname(), null,
						list.get(i).getEmce_menuurl())));
				list.remove(i);
				i--;
			}
		}
		return category;
	}

	// 员工入职新增信息
	public Integer addEmbase(EmbaseModel ebm, String id, String sd1, String sd2) {
		Integer i = 0;
		Embasedal dal = new Embasedal();
		i = dal.addbaseInfo(ebm, id, sd1, sd2);

		return i;
	}

	// 修改员工入职信息
	public Integer modEmbase(EmbaseModel ebm, String id, String sd1, String sd2) {
		Integer i = 0;
		Embasedal dal = new Embasedal();
		i = dal.modbaseinfo(ebm, id, sd1, sd2);
		return i;
	}

	// 修改员工基本信息
	public Integer modInfo(EmbaseModel ebm) {
		Integer i = 0;
		Embasedal dal = new Embasedal();
		i = dal.modInfo(ebm);
		return i;
	}

	/**
	 * @throws SQLException
	 * @Title: getentry
	 * @Description: TODO(判断员工业务信息是否已经录入完整)
	 * @param gid
	 * @return
	 * @return boolean 返回类型
	 * @throws
	 */
	public boolean getentry(Integer gid) throws SQLException {
		boolean b = true;
		List<EmOnBoardListModel> list = new ListModelList<>();

		EmOnBoardListDal eobdal = new EmOnBoardListDal();

		list = eobdal.getInfoByGid(gid);

		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				switch (list.get(i).getEmbo_class()) {
				case "社会保险服务":
					if (list.get(i).getEmba_sb_place() != null) {
						if (list.get(i).getEmba_sb_place().equals("本地")) {

							// 判断该身份证号或电脑号下是否已存在社保在册数据
							Emsi_SelBll sbBll = new Emsi_SelBll();
							String[] chkStr = sbBll.chkIfShebao(gid);
							if (chkStr != null) {
								b = false;
								Messagebox.show("提交失败，该员工在另一家公司-(" + chkStr[1]
										+ ")" + chkStr[2]
										+ "-存在在册社保数据，请先操作停交再操作社保！", "ERROR",
										Messagebox.OK, Messagebox.ERROR);
								return b;
							}

							if (list.get(i).getEmba_emsb_foreigner() != null
									&& list.get(i).getEmba_emsb_foreigner()
											.equals("是")) {

							} else {
								if (list.get(i).getEmba_emsb_ownmonth() == null
										|| list.get(i).getEmba_emsb_ownmonth()
												.equals("")) {
									b = false;
									Messagebox.show("请保存社保信息", "操作提示",
											Messagebox.OK, Messagebox.ERROR);
									return b;
								}
								if (list.get(i).getEmba_sb_radix() == null
										|| list.get(i).getEmba_formula() == null) {
									b = false;
									Messagebox.show("请录入社保基数/医疗类型", "操作提示",
											Messagebox.OK, Messagebox.ERROR);
									return b;
								}

								/*
								 * EmSheBao_DSelectDal dal = new
								 * EmSheBao_DSelectDal(); if
								 * (!dal.getupdateInfo(gid)) { b = false; }
								 */
							}

						} else if (list.get(i).getEmba_sb_place().equals("外地")) {
							EmCommissionOutDal dal = new EmCommissionOutDal();
							if (!dal.getupdateInfo(gid)) {
								b = false;
								Messagebox.show("请录入委托数据", "操作提示",
										Messagebox.OK, Messagebox.ERROR);
								return b;
							}
						}
					}
					break;
				case "住房公积金服务":
					if (list.get(i).getEmba_house_place() != null) {
						if (list.get(i).getEmba_house_place().equals("本地")) {
							if (list.get(i).getEmba_house_radix() == null
									|| list.get(i).getEmba_house_cpp() == null) {
								b = false;
								Messagebox.show("请录入公积金基数/比例.", "操作提示",
										Messagebox.OK, Messagebox.ERROR);
								return b;
							}
							if (list.get(i).getEmba_emhb_ownmonth() == null
									|| list.get(i).getEmba_emhb_ownmonth()
											.equals("")) {
								b = false;
								Messagebox.show("请保存公积金信息", "操作提示",
										Messagebox.OK, Messagebox.ERROR);
								return b;
							}
							/*
							 * EmHouseUpdateDal dal = new EmHouseUpdateDal(); if
							 * (!dal.getupdateInfo(gid)) { b = false; }
							 */
						} else if (list.get(i).getEmba_house_place()
								.equals("外地")) {
							EmCommissionOutDal dal = new EmCommissionOutDal();
							if (!dal.getupdateInfo(gid)) {
								b = false;
								Messagebox.show("请录入委托数据.", "操作提示",
										Messagebox.OK, Messagebox.ERROR);
								return b;
							}
						}
					}
					break;
				case "签订劳动合同":
					EmBaseCompact_BaseDal dal = new EmBaseCompact_BaseDal();
					if (!dal.getupdateInfo(gid)) {
						b = false;
						Messagebox.show("请录入劳动合同.", "操作提示", Messagebox.OK,
								Messagebox.ERROR);
						return b;
					}
					break;
				default:
					// 商保
					if (list.get(i).getCopr_copc_id() != null
							&& list.get(i).getCopr_copc_id().equals(4)) {
						CI_Insurant_ListDal dal2 = new CI_Insurant_ListDal();
						if (!dal2
								.getupdateInfo(gid, list.get(i).getCopr_name())) {
							b = false;
							Messagebox.show("请录入商保数据["
									+ list.get(i).getCopr_name() + "].",
									"操作提示", Messagebox.OK, Messagebox.ERROR);
							return b;
						}
						// 财税
					} else if (list.get(i).getCopr_copc_id() != null
							&& list.get(i).getCopr_copc_id().equals(8)) {
						if (list.get(i).getEmba_nationality() == null
								|| list.get(i).getEmba_nationality().equals("")) {
							b = false;
							Messagebox.show("请选择国籍.", "操作提示", Messagebox.OK,
									Messagebox.ERROR);
							return b;
						}
						EmSalaryListInfoDal dal2 = new EmSalaryListInfoDal();
						List<EmSalaryInfoModel> list2 = dal2.getSalaryList(list
								.get(i).getCid(), list.get(i).getGid());

						// 判断报价单信息是否含工资个税项目
						EmSalary_SalarySelBll esBll = new EmSalary_SalarySelBll();
						boolean chkgz = false;
						boolean chkgs = false;
						Integer[] chkgzgs = esBll.getEmSalaryCoGlist(list
								.get(i).getCid(), list.get(i).getGid());
						if (chkgzgs[0] > 0) {// 工资
							chkgz = true;
						}
						if (chkgzgs[1] > 0) {// 个税
							chkgs = true;
						}

						if (list2.size() > 0) {
							if ((list2.get(0).getEsin_taxplace() == null || list2
									.get(0).getEsin_taxplace().equals(""))
									&& chkgs) {
								b = false;
								Messagebox.show("请录入个税申报地.", "操作提示",
										Messagebox.OK, Messagebox.ERROR);
								return b;
							}
							if ((list2.get(0).getEsin_salaryplace() == null || list2
									.get(0).getEsin_salaryplace().equals(""))
									&& chkgz) {
								b = false;
								Messagebox.show("请录入工资发放地.", "操作提示",
										Messagebox.OK, Messagebox.ERROR);
								return b;
							}
							if (list2.get(0).getEsin_hpro() == null
									|| list2.get(0).getEsin_hpro().equals("")) {
								b = false;
								Messagebox.show("请录入雇佣性质.", "操作提示",
										Messagebox.OK, Messagebox.ERROR);
								return b;
							}

						} else {
							b = false;
							Messagebox.show("请录入薪酬服务信息.", "操作提示",
									Messagebox.OK, Messagebox.ERROR);
							return b;
						}
					}
					break;
				}
			}
		}
		// 执行缤纷服务方法
		eobdal.getbffwforgid(gid);
		return b;
	}

	// 报价单合并项目时读取同分组项目列表
	public List<CoOfferListModel> getcoofflistInfo(Integer id) {
		List<CoOfferListModel> list = new ListModelList<>();
		CoofferlistDal dal = new CoofferlistDal();
		list = dal.getcoofferInfoGroup(id);
		return list;
	}

	/**
	 * @Title: addCoglist
	 * @Description: TODO(员工入职)
	 * @param embaid
	 * @param intime
	 * @param coliIdList
	 * @param startdateList1
	 * @param startdateList2
	 * @param addname
	 * @return
	 * @return Integer 返回类型
	 * @throws
	 */

	public Integer addCoglist(Integer embaid, String intime, String coliIdList,
			String startdateList1, String startdateList2, String addname,
			Integer emba_tapr_id) {
		String[] message = new String[5];
		int i = 0;
		int tabaId = 0;
		EmbaseNotdal dal = new EmbaseNotdal();
		TaskBatchRelBusinessDal taskdal = new TaskBatchRelBusinessDal();
		CoofferlistDal cofDal = new CoofferlistDal();
		List<EmbaseNotInModel> list = new ListModelList<>();
		List<CoOfferListModel> coflist = new ListModelList<>();
		WfBusinessService wfbs = new Embase_OnBoardImpl();
		WfOperateService wfs = new WfOperateImpl(wfbs);
		String ownmonth = ConstantsUtil.ownmonth;
		Object[] obj = { embaid, intime, coliIdList, startdateList1,
				startdateList2, addname };
		message = wfs.PassToNext(obj, emba_tapr_id, addname, "", 0, "");
		list = dal.getEmBaseNotInById(embaid);
		if (message[0] == "1") {
			// 判断是否有业务
			boolean b = false;
			i = addEmOnboardList(list.get(0).getGid(), list.get(0).getCid(),
					coliIdList, addname, Integer.valueOf(ownmonth));
			if (i > 0) {
				tabaId = addTaskBatch(list.get(0).getGid(), list.get(0)
						.getEmba_name(), addname);
			}
		}
		return tabaId;
	}

	// 添加批量任务
	public Integer addTaskBatch(Integer gid, String name, String addname) {
		Integer i = 0;
		String[] message = new String[5];
		WfBusinessService wfbs = new EmBase_DistributeImpl();
		WfOperateService wfs = new WfOperateImpl(wfbs);
		Object[] obj = { 1, "员工入职", name + "(" + gid + ")分配业务", addname, gid };
		message = wfs.AddTaskToNext(obj, "员工入职业务分配", name + "(" + gid + ")入职",
				57, addname, "", 0, "");
		i = Integer.valueOf(message[3]);
		return i;
	}

	public Integer addEmOnboardList(Integer gid, Integer cid, String id,
			String addname, Integer ownmonth) {
		Integer i = 0;
		EmOnBoardListDal dal = new EmOnBoardListDal();
		try {

			i = dal.add(cid, gid, ownmonth, id, addname);
		} catch (NumberFormatException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return i;
	}

	public Integer completeFlow(Integer dataid, Integer tapr_Id, String tbName,
			String addname) {
		Integer i = 0;
		EmOnBoardListDal dal = new EmOnBoardListDal();
		if (dal.getTask(tapr_Id)) {

			i = dal.completeFlow(dataid, tapr_Id, tbName);
			/*
			 * if (dal.getFinish(i)>0) { String[] message = new String[5];
			 * WfBusinessService wfbs = new EmBase_DistributeImpl();
			 * WfOperateService wfs = new WfOperateImpl(wfbs); Integer taprId =
			 * dal.getTaprId(i).get(0).getTaba_tapr_id(); Integer Id =
			 * dal.getTaprId(i).get(0).getTaba_id(); Object[] obj = { 2, Id };
			 * 
			 * message = wfs.PassToNext(obj, taprId, "系统", "", 0, "");
			 * 
			 * }
			 */
		}

		return i;
	}

	/**
	 * @Title: cancelOnboard
	 * @Description: TODO(取消入职)
	 * @param gid
	 * @return
	 * @return boolean 返回类型
	 * @throws
	 */
	public boolean cancelOnboard(Integer id, Integer gid, Integer taprId) {
		boolean b = false;
		EmbaseModel em = new EmbaseModel();
		em.setEmba_id(id);
		em.setGid(gid);
		em.setEmba_state(4);
		em.setEmba_modname(UserInfo.getUsername());
		em.setEmba_tapr_id(taprId);

		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		Integer nowmonth = Integer.valueOf(sdf.format(d));

		// 更新公积金
		EmHouse_EditBll housebll = new EmHouse_EditBll();
		boolean b2 = housebll.gjjQuery(gid, nowmonth);
		if (b2) {
			Messagebox.show("该员工有处理中公积金数据,不允许撤销入职.", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
			return false;
		} else {
			WfBusinessService wfbs = new EmHouse_EditImpl();
			WfOperateService wfs = new WfOperateImpl(wfbs);
			List<EmHouseChangeModel> houselist = new ListModelList<>();
			EmHouseChangeModel ecm = new EmHouseChangeModel();
			ecm.setGid(gid);
			houselist = housebll.getChangeList(ecm);
			if (houselist.size() > 0) {
				for (EmHouseChangeModel m : houselist) {
					Object[] obj = { "删除数据", m };
					String[] str = null;
					if (m.getEmhc_tapr_id() != null && m.getEmhc_tapr_id() > 0) {
						str = wfs.StopTask(obj, m.getEmhc_tapr_id(),
								UserInfo.getUsername(), "");
					} else {
						str = wfbs.StopOperate(obj);
					}
					if (!str[0].equals("1")) {

						Messagebox.show("公积金取消操作失败.", "操作提示", Messagebox.OK,
								Messagebox.ERROR);
						return false;
					} else {
						EmHouseUpdateDal dal = new EmHouseUpdateDal();
						dal.delByGid(gid);
					}
				}
			} else {
				EmHouseUpdateDal dal = new EmHouseUpdateDal();
				dal.delByGid(gid);
			}

			WfBusinessService wfbs2 = new EmHouse_EditBJImpl();
			WfOperateService wfs2 = new WfOperateImpl(wfbs2);
			List<EmHouseBJModel> bjlist = new ListModelList<>();
			EmHouseBJModel ejm = new EmHouseBJModel();
			ejm.setGid(gid);

			bjlist = housebll.getbjChangeList(ejm);
			if (bjlist.size() > 0) {

				for (EmHouseBJModel m : bjlist) {
					Object[] obj = { "补缴删除", m };
					String[] str = null;

					if (m.getEmhb_tapr_id() != null && m.getEmhb_tapr_id() > 0) {
						str = wfs2.StopTask(obj, m.getEmhb_tapr_id(),
								UserInfo.getUsername(), "");
					} else {
						str = new String[5];
						Integer i = housebll.delbj(m.getEmhb_id());
						str[0] = i > 0 ? "1" : "0";
					}
					if (!str[0].equals("1")) {
						Messagebox.show("公积金取消操作失败.", "操作提示", Messagebox.OK,
								Messagebox.ERROR);
						return false;
					}
				}
			} else {
				housebll.delbjByGid(gid);
			}

		}

		// 更新社保
		Emsc_DeclareOperateBll sbll = new Emsc_DeclareOperateBll();
		String[] s = sbll.delEntryShebao(gid);
		if (s[0].equals("2")) {
			Messagebox.show(s[1], "操作提示", Messagebox.OK, Messagebox.ERROR);
			return false;
		}

		// 更新商保
		CI_Insurant_ListBll sbbll = new CI_Insurant_ListBll();
		List<CI_Insurant_ListModel> sblist = new ListModelList<>();
		try {
			sblist = sbbll.ci_insurant_editlist(gid.toString());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (sblist.size() > 0) {
			for (CI_Insurant_ListModel m : sblist) {
				if (m.getEcin_state2().equals("0")) {
					CI_Insurant_OperateBll ccsaBll = new CI_Insurant_OperateBll();
					try {
						String[] message = ccsaBll.del_insurantByOnboard(
								String.valueOf(m.getEcin_id()),
								String.valueOf(m.getEcin_tapr_id()));
						if (!message[0].equals("1")) {
							Messagebox.show("商保数据取消失败.请与IT部人员联系", "操作提示",
									Messagebox.OK, Messagebox.ERROR);
							return false;
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						Messagebox.show("商保数据取消失败.请与IT部人员联系", "操作提示",
								Messagebox.OK, Messagebox.ERROR);
						return false;
					}

				} else {
					Messagebox.show("商保数据当前状态不允许取消.", "操作提示", Messagebox.OK,
							Messagebox.ERROR);
					return false;
				}
			}

		}

		// 更新档案
		EmArchiveDatum_OperateBll filebll = new EmArchiveDatum_OperateBll();
		List<EmArchiveDatumModel> flist = filebll.searchList(gid, nowmonth);

		if (flist.size() > 0) {
			for (EmArchiveDatumModel m : flist) {
				boolean fb = filebll.stopFlow(m.getEada_id(),
						m.getEada_tapr_id());
				if (!fb) {
					Messagebox.show("档案业务数据终止失败,数据编号:" + m.getEada_id(),
							"操作提示", Messagebox.OK, Messagebox.ERROR);
					return false;
				}
			}
		}
		// 更新员工状态
		boolean b1 = stopEmbaseOnboard(em);

		if (b1) {
			CoglistDal dal = new CoglistDal();
			dal.delupdate(em.getGid());
			b = true;
		} else {
			return false;
		}

		return b;
	}

	// 终止入职流程
	public boolean stopEmbaseOnboard(EmbaseModel em) {
		boolean b = false;
		WfBusinessService wfbs = new Embase_OnBoardImpl();
		WfOperateService wfs = new WfOperateImpl(wfbs);
		Object[] obj = { "取消入职", em };
		String[] str = wfs.StopTask(obj, em.getEmba_tapr_id(),
				UserInfo.getUsername(), "");
		if (str[0].equals("1")) {
			b = true;
		} else {
			b = false;
			Messagebox.show("取消入职失败!", "操作提示", Messagebox.OK, Messagebox.ERROR);
		}
		return b;
	}

	// 只有薪酬服务的时候更新任务状态,使流程不走雇员服务中心
	public boolean updateMission(Integer id, Integer gid, Integer taprId) {
		boolean b = false;
		EmOnBoardListDal dal = new EmOnBoardListDal();

		Integer ownmonth = Integer.valueOf(DateStringChange
				.Datestringnow("yyyyMM"));
		List<EmOnBoardListModel> list = dal.judgecs(gid, ownmonth);
		if (list.size() > 0) {
			for (EmOnBoardListModel m : list) {
				if (!m.getEmbo_pid().equals(82) && m.getEmbo_state().equals(0)) {
					b = true;
				}
			}

			if (b) {
				// 多种项目时只更新财税任务,不结束任务单
				Integer i = dal.updateFlow(gid, ownmonth, 82);
				if (i > 0) {
					return true;
				} else {
					return false;
				}
			} else {
				// 只有财税项目
				WfBusinessService wfbs = new Embase_OnBoardImpl();
				WfOperateService wfs = new WfOperateImpl(wfbs);
				Object[] obj = { "薪酬", id, gid, ownmonth };
				String[] str = wfs.PassToNext(obj, taprId, "系统", "", 0, "");
				if (str[0].equals("1")) {
					return true;
				} else {
					return false;
				}
			}
		} else {
			return true;
		}

	}

	public boolean updateInfo(Integer id, Integer gid, Integer ownmonth) {
		EmOnBoardListDal dal = new EmOnBoardListDal();
		Integer i = dal.updateFlow(gid, ownmonth, 82);
		if (i > 0) {
			Embasedal dal2 = new Embasedal();
			EmbaseModel em = new EmbaseModel();
			em.setEmba_id(id);
			em.setEmba_state(1);
			em.setEmba_modname(UserInfo.getUsername());
			Integer j = dal2.modInfo(em);
			if (j > 0) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

}
