package bll.CoFinanceManage;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import bll.EmFinance.EmFinance_SelectBll;

import dal.CoFinanceManage.EmFinanceWt_SelDal;
import dal.EmFinance.EmFinance_SelectDal;

import Model.CoFinanceMonthlyBillModel;
import Model.CoFinanceWtModel;
import Model.EmFinanceDataByCPModel;
import Model.EmFinanceWtModel;
import Model.EmFinanceZYTModel;

public class EmFinanceWt_SelBll {
	private final DecimalFormat df = new DecimalFormat("#.00");
	private EmFinanceWt_SelDal dal;

	public EmFinanceWt_SelBll() {
		dal = new EmFinanceWt_SelDal();
	}

	// 按合同编号采集台账数据应收总额
	public BigDecimal[] getEmFinanceWtTotalByCP(int ownmonth, int[] coco_id) {
		BigDecimal[] bd = new BigDecimal[4];
		BigDecimal fbd = BigDecimal.ZERO;// 系统总额
		BigDecimal zbd = BigDecimal.ZERO;// 智翼通总额
		try {
			Integer state = 0, s = 0, t = 0;
			for (int id : coco_id) {
				BigDecimal[] bg = new BigDecimal[4];
				bg = dal.getEmFinanceTotalByCP(ownmonth, id);// 按合同编号采集台账数据应收总额
				state = existsBillConfirmByCp(ownmonth, id);// 根据合同查询账单是否已确认
				if (state == 2 && s == 0) {
					s = s + 1;
				} else if (state == 1 && s == 0) {
					s = s + 1;
				} else if (state == 0) {
					t = t + 1;
				}
				if (bg[0] != null) {
					fbd = fbd.add(bg[0]);
				}
				if (bg[1] != null) {
					zbd = zbd.add(bg[1]);
				}
			}
			bd[2] = new BigDecimal(t.toString());
			bd[3] = new BigDecimal(state.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		bd[0] = fbd;// 系统总额
		bd[1] = zbd;// 智翼通总额
		return bd;
	}

	// 按合同编号获取员工台账数据列表
	public CoFinanceWtModel getEmWtDataList(int ownmonth, int[] coco_id) {
		EmFinance_SelectBll bll = new EmFinance_SelectBll();
		return getCoFinanceWtModel(getEmFinanceDataByCP(ownmonth, coco_id),
				bll.getEmFinance(ownmonth, coco_id));
	}

	// 按合同编号获取员工台账数据列表
	public CoFinanceWtModel getEmWtDataListOut(int ownmonth, int[] coco_id,int cid) {
		EmFinance_SelectBll bll = new EmFinance_SelectBll();
		return getCoFinanceWtModel(getEmFinanceDataByCP(ownmonth, coco_id),
				bll.getEmFinanceOut(ownmonth, cid));
	}

	// 根据合同查询账单是否已确认（委托入账单）
	// 返回值（0未找到账单1账单已确认2账单未确认3出错）
	public int existsBillConfirmByCp(int ownmonth, int coco_id) {
		return dal.existsBillConfirmByCp(ownmonth, coco_id);
	}

	// 根据合同获取账单号（找不到账单时返回0）
	public String getBillNumberByCp(int ownmonth, int coco_id) {
		return dal.getBillNumberByCp(ownmonth, coco_id);
	}

	// 按合同编号采集台账数据
	public List<EmFinanceDataByCPModel> getEmFinanceDataByCP(int ownmonth,
			int[] coco_id) {
		List<EmFinanceDataByCPModel> list = new ArrayList<EmFinanceDataByCPModel>();
		try {
			for (int id : coco_id) {
				list.addAll(dal.getEmFinanceDataByCP(ownmonth, id));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 拼接数据
	/** 
	* @Title: getCoFinanceWtModel 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param list
	* @param zlist
	* @return
	* @return CoFinanceWtModel    返回类型 
	* @throws 
	*/
	public CoFinanceWtModel getCoFinanceWtModel(
			List<EmFinanceDataByCPModel> list, List<EmFinanceZYTModel> zlist) {
		Map<String, EmFinanceWtModel> map = new HashMap<String, EmFinanceWtModel>();
		CoFinanceWtModel cm = new CoFinanceWtModel();
		EmFinanceWtModel em;
		try {
			/*************** 系统台帐数据 *******************/
			for (EmFinanceDataByCPModel m : list) {
				if (m.getEmba_name() != null && !m.getEmba_name().equals("")) {
					if (map.containsKey(m.getGid() + "" + m.getOwnmonth())) {
						em = (EmFinanceWtModel) map.get(m.getGid() + ""
								+ m.getOwnmonth());
					} else {
						em = new EmFinanceWtModel();
						em.setGid(m.getGid());
						em.setCid(m.getCid());
						em.setEmfi_name(m.getEmba_name());
						em.setEmfi_idcard(m.getEmba_idcard());
						em.setCoba_company(m.getCoba_company());
						em.setOwnmonth(m.getOwnmonth());
						em.setEmba_state(1);
						map.put(m.getGid() + "" + m.getOwnmonth(), em);
					}
					if ("公司收费".equals(m.getItemname())) {
						cm.setCid(m.getGid());
						cm.setCofi_fee(cm.getCofi_fee().add(m.getReceivable()));
					} else {
						insertEmFinanceWtModel(m, em);
					}
				}
			}
			/*************** 系统台帐数据End *******************/

			/*************** 获取系统台帐人数 *******************/
			Set keys = map.keySet();
			int num = 0;// 系统台帐人数
			if (keys != null) {
				Iterator iterator = keys.iterator();
				while (iterator.hasNext()) {
					Object key = iterator.next();
					num++;
				}
			}
			/*************** 获取系统台帐人数End *******************/

			/*************** 智翼通数据 *******************/
			List<Integer> gidlist = new ArrayList<Integer>();
			for (EmFinanceZYTModel model : zlist) {
				if (model.getGid().equals(232762)) {
					System.out.println(model.getGid());
					System.out.println(model.getEmfz_servertotal());
				}
				if (map.containsKey(model.getGid() + "" + model.getOwnmonth())) {
					em = (EmFinanceWtModel) map.get(model.getGid() + ""
							+ model.getOwnmonth());
				} else {
					em = new EmFinanceWtModel();
					em.setGid(model.getGid());
					em.setCid(model.getCid());
					em.setEmfi_name(model.getEmba_name());
					em.setEmfi_idcard(model.getEmba_idcard());
					em.setCoba_company(model.getEmfz_company());
					em.setOwnmonth(model.getOwnmonth());
					em.setEmba_state(1);
					map.put(model.getGid() + "" + model.getOwnmonth(), em);// 把em存在map中
				}
				addfzdata(model, em, gidlist, map);
			}
			/*************** 智翼通数据End *******************/

			cm.setEfWtList(changeMapToList(map));
			cm.setEmFiCount(num);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cm;

	}

	// 插入数据
	private void insertEmFinanceWtModel(EmFinanceDataByCPModel em,
			EmFinanceWtModel wm) {
		try {
			switch (em.getItemname()) {
			case "社保合计":
				wm.setEmfi_sbtotal(wm.getEmfi_sbtotal().add(em.getReceivable()));
				wm.setEmfi_yltotal(wm.getEmfi_yltotal().add(em.getOtherEx()));
				wm.setEfsb_yliao(wm.getEfsb_yliao().add(em.getEfsb_yliao()));
				wm.setEfsb_gs(wm.getEfsb_gs().add(em.getEfsb_gs()));
				wm.setEfsb_sye(wm.getEfsb_sye().add(em.getEfsb_sye()));
				wm.setEfsb_syu(wm.getEfsb_syu().add(em.getEfsb_syu()));
				wm.setEfsb_ylao(wm.getEfsb_ylao().add(em.getEfsb_ylao()));
				break;
			case "公积金合计":
				wm.setEmfi_housetotal(wm.getEmfi_housetotal().add(
						em.getReceivable()));
				break;
			case "档案保管费":
				wm.setEmfi_filefee(wm.getEmfi_filefee().add(em.getReceivable()));
				break;
			case "服务费":
				wm.setEmfi_fee(wm.getEmfi_fee().add(em.getReceivable()));
				break;
			case "其它":
				wm.setEmfi_elsefee(wm.getEmfi_elsefee().add(em.getReceivable()));
				break;
			case "非标准":
				wm.setEmfi_n_total(wm.getEmfi_n_total().add(em.getReceivable()));
				break;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 将MAP转为LIST,并计算每条数据合计
	private List<EmFinanceWtModel> changeMapToList(
			Map<String, EmFinanceWtModel> map) {
		List<EmFinanceWtModel> list = new ArrayList<EmFinanceWtModel>();
		try {
			Collection<EmFinanceWtModel> collection = map.values();
			for (EmFinanceWtModel m : collection) {
				m.sumEmfi_total();
				if (m.getEmfi_total() == null) {
					m.setEmfi_total(BigDecimal.ZERO);
				}
				if (m.getEmfz_total() == null) {
					m.setEmfz_total(BigDecimal.ZERO);
				}
				m.setFifztotal(m.getEmfi_total().subtract(m.getEmfz_total()));//系统台帐减智翼通台帐（不包含调整费用）emfi_total-each.emfz_total
				m.setFifztotal(m.getFifztotal().add(m.getEmfi_n_total()));//差额加系统调整差额 emfi_total+each.emfi_n_total
				m.setFifztotal(m.getFifztotal().subtract(m.getEmfz_other()));//差额减智翼通调整差额 emfi_total-each.emfz_other
				m.setFifztotal(m.getFifztotal().subtract(m.getEmfz_fee2()));//差额减智翼通调整差额 emfi_total-each.emfz_fee2
				m.setFifz_total(m.getFifztotal());
				list.add(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 插入智翼通数据
	/** 
	* @Title: addfzdata 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param model
	* @param wm
	* @param list
	* @param map
	* @return void    返回类型 
	* @throws 
	*/
	private void addfzdata(EmFinanceZYTModel model, EmFinanceWtModel wm,
			List<Integer> list, Map<String, EmFinanceWtModel> map) {
		
		if (model.getGid().equals(232762)) {
			System.out.println("***");
		}
		if (list.contains(model.getGid()))// 如果已经存在该员工的数据
		{
			EmFinanceWtModel em = (EmFinanceWtModel) map.get(model.getGid()
					+ "" + model.getOwnmonth());
			if (model.getEmfz_sbtotal() != null) {
				em.setEmfz_sbtotal(em.getEmfz_sbtotal().add(
						model.getEmfz_sbtotal()));
			}
			if (model.getEmfz_yltotal() != null) {
				em.setEmfz_yltotal(em.getEmfz_yltotal().add(
						model.getEmfz_yltotal()));
			}
			if (model.getEmfz_housetotal() != null) {
				em.setEmfz_housetotal(em.getEmfz_housetotal().add(
						model.getEmfz_housetotal()));
			}
			if (model.getEmfz_fee() != null) {
				em.setEmfz_fee(em.getEmfz_fee().add(model.getEmfz_fee()));
			}
			if (model.getEmfz_feex() != null) {
				em.setEmfz_feex(em.getEmfz_feex().add(model.getEmfz_feex()));
			}
			if (model.getEmfz_fee2() != null) {
				em.setEmfz_fee2(em.getEmfz_fee2().add(model.getEmfz_fee2()));
			}
			if (model.getEmfz_filefee() != null) {
				em.setEmfz_filefee(em.getEmfz_filefee().add(
						model.getEmfz_filefee()));
			}
			if (model.getEmfz_elsefee() != null) {
				em.setEmfz_elsefee(em.getEmfz_elsefee().add(
						model.getEmfz_elsefee()));
			}
			if (model.getEmfz_total() != null) {
				em.setEmfz_total(em.getEmfz_total().add(model.getEmfz_total()));
			}
			if (model.getEmfz_other() != null) {
				em.setEmfz_other(em.getEmfz_other().add(model.getEmfz_other()));
			}
			
			if (model.getEmfz_jltotal() != null) {
				em.setEmfz_jltotal(em.getEmfz_jltotal().add(model.getEmfz_jltotal()));
			}
			if (model.getEmfz_syutotal() != null) {
				em.setEmfz_syutotal(em.getEmfz_syutotal().add(model.getEmfz_syutotal()));
			}
			if (model.getEmfz_syetotal() != null) {
				em.setEmfz_syetotal(em.getEmfz_syetotal().add(model.getEmfz_syetotal()));
			}
			if (model.getEmfz_gstotal() != null) {
				em.setEmfz_gstotal(em.getEmfz_gstotal().add(model.getEmfz_gstotal()));
			}
			if (model.getEmfz_servertotal()!=null) {
				em.setEmfz_servertotal(em.getEmfz_servertotal().add(model.getEmfz_servertotal()));
			}
		} else {
			list.add(model.getGid());
			if (model.getEmfz_sbtotal() != null) {
				wm.setEmfz_sbtotal(model.getEmfz_sbtotal());
			}
			if (model.getEmfz_yltotal() != null) {
				wm.setEmfz_yltotal(model.getEmfz_yltotal());
			}
			if (model.getEmfz_housetotal() != null) {
				wm.setEmfz_housetotal(model.getEmfz_housetotal());
			}
			if (model.getEmfz_fee() != null) {
				wm.setEmfz_fee(model.getEmfz_fee());
			}
			if (model.getEmfz_feex() != null) {
				wm.setEmfz_feex(model.getEmfz_feex());
			}
			if (model.getEmfz_fee2() != null) {
				wm.setEmfz_fee2(model.getEmfz_fee2());
			}
			if (model.getEmfz_filefee() != null) {
				wm.setEmfz_filefee(model.getEmfz_filefee());
			}
			if (model.getEmfz_elsefee() != null) {
				wm.setEmfz_elsefee(model.getEmfz_elsefee());
			}
			if (model.getEmfz_total() != null) {
				wm.setEmfz_total(model.getEmfz_total());
			}
			if (model.getEmfz_other() != null) {
				wm.setEmfz_other(model.getEmfz_other());
			}
			
			if (model.getEmfz_jltotal() != null) {
				wm.setEmfz_jltotal(model.getEmfz_jltotal());
			}
			if (model.getEmfz_syutotal() != null) {
				wm.setEmfz_syutotal(model.getEmfz_syutotal());
			}
			if (model.getEmfz_syetotal() != null) {
				wm.setEmfz_syetotal(model.getEmfz_syetotal());
			}
			if (model.getEmfz_gstotal() != null) {
				wm.setEmfz_gstotal(model.getEmfz_gstotal());
			}
			if (model.getEmfz_servertotal()!=null) {
				wm.setEmfz_servertotal(model.getEmfz_servertotal());
			}
		}

	}

	// 根据合同获取账单号（找不到账单时返回0）
	public CoFinanceMonthlyBillModel getBillNumberByCpModel(int ownmonth,
			int coco_id) {
		return dal.getBillNumberByCpModel(ownmonth, coco_id);
	}
}
