package bll.EmFinance;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import bll.CoFinanceManage.EmFinanceWt_SelBll;

import Model.CoFinanceWtModel;
import Model.EmFinanceDataByCPModel;
import Model.EmFinanceWtModel;
import Model.EmFinanceZYTModel;
import dal.CoFinanceManage.EmFinanceWt_SelDal;
import dal.EmFinance.EmFinance_SelectDal;

public class EmFinance_SelectBll {
	private EmFinance_SelectDal dal = new EmFinance_SelectDal();
	private final DecimalFormat df = new DecimalFormat("#.00");

	// 查询委托进数据信息_直接查询出所有数据
	public List<EmFinanceZYTModel> getEmFinanceZYTn(int ownmonth, String city,
			String coab_id, String str) {
		EmFinanceWt_SelDal sdal = new EmFinanceWt_SelDal();
		
		List<EmFinanceZYTModel> cplist = sdal.getEmFinanceTotalByCP_cyj(
				ownmonth, city, coab_id, str);
		
		//cplist = RemovedRepeat(cplist);
		return cplist;
	}

	// 循环把cid和cabc_id一样的数据合并
	private List<EmFinanceZYTModel> RemovedRepeat(List<EmFinanceZYTModel> lists) {
		List<EmFinanceZYTModel> list = new ArrayList<EmFinanceZYTModel>();
		LinkedHashMap<String, EmFinanceZYTModel> map = new LinkedHashMap<String, EmFinanceZYTModel>();
		for (EmFinanceZYTModel m : lists) {
			m.setStateid(m.getCfmb_PersonnelConfirm());
			if (map.containsKey(m.getCid() + "" + m.getCabc_id())) {
				
				EmFinanceZYTModel em = map
						.get(m.getCid() + "" + m.getCabc_id());
				em.setEmfi_total(em.getEmfi_total().add(m.getEmfi_total()));// 把系统台帐相加
				em.setTotal(em.getTotal().add(m.getTotal()));// 把智翼通台帐相加
				if (em.getTotal().compareTo(BigDecimal.ZERO) == 0) {
					em.setTotal(m.getTotal());
				}
				if (em.getStateid() == 1 && m.getStateid() == 1) {
					em.setStateid(1);
				} else if ((em.getStateid() == 0 && m.getStateid() == 1)
						|| (em.getStateid() == 1 && m.getStateid() == 0)) {
					em.setStateid(0);
				} else if ((em.getStateid() == 1 && m.getStateid() == -1)
						|| (em.getStateid() == -1 && m.getStateid() == 1)) {
					em.setStateid(1);
				} else if ((em.getStateid() == 0 && m.getStateid() == -1)
						|| (em.getStateid() == -1 && m.getStateid() == 0)) {
					em.setStateid(0);
				} else if ((em.getStateid() == -1 && m.getStateid() == -1)
						|| (em.getStateid() == -1 && m.getStateid() == -1)) {
					em.setStateid(-1);
				}
			} else {
				map.put(m.getCid() + "" + m.getCabc_id(), m);
			}
		}
		
		for (String key : map.keySet()) {
			EmFinanceZYTModel newem = map.get(key);
			list.add(newem);
		}
		return list;
	}
	
	// 根据合同编号和所属月份查询委托进数据信息_智翼通数据(导出数据用)
	public List<EmFinanceZYTModel> getEmFinanceOut(Integer ownmonth,
			int cid) {
		List<EmFinanceZYTModel> list = new ArrayList<EmFinanceZYTModel>();
		list = dal.getEmFinanceListOut(ownmonth,cid);
		return list;
	}


	// 获取委托对账详细信息
	public CoFinanceWtModel getModel(String city, int ownmonth, int cid) {
		EmFinanceWt_SelBll bll = new EmFinanceWt_SelBll();
		int[] ints = dal.getCocoID(" and coab_city='" + city + "' and cid="
				+ cid);
		CoFinanceWtModel model = bll.getEmWtDataList(ownmonth, ints);
		return model;
	}

	// 获取委托对账详细信息
	public CoFinanceWtModel getModelOut(String city, int ownmonth, int cid) {
		EmFinanceWt_SelBll bll = new EmFinanceWt_SelBll();
		int[] ints = dal.getCocoID(" and coab_city='" + city + "' and cid="
				+ cid);
		CoFinanceWtModel model = bll.getEmWtDataListOut(ownmonth,ints, cid);
		return model;
	}

	// 获取委托对账详细信息
	public CoFinanceWtModel getModels(Integer ownmonth) {
		EmFinanceWt_SelBll bll = new EmFinanceWt_SelBll();
		CoFinanceWtModel model = new CoFinanceWtModel();
		List<EmFinanceWtModel> flist = new ArrayList<EmFinanceWtModel>();
		if (ownmonth != null && ownmonth.equals(0)) {
			List<Integer> list = getOwnmonth();
			for (int i = 0; i < list.size(); i++) {
				int[] ints = dal
						.getCocoID("and cid in(select distinct(cid) from EmFinanceBase where ownmonth="
								+ list.get(i) + ")");
				CoFinanceWtModel ml = bll.getEmWtDataList(list.get(i), ints);
				if (ml != null && ml.getEfWtList() != null
						&& ml.getEfWtList().size() > 0) {
					flist.addAll(ml.getEfWtList());
				}
			}
			model.setEfWtList(flist);
		} else {
			int[] ints = dal
					.getCocoID("and cid in(select distinct(cid) from EmFinanceBase where ownmonth="
							+ ownmonth + ")");
			model = bll.getEmWtDataList(ownmonth, ints);
		}
		return model;
	}

	// 根据所属月份查询委托地区
	public List<String> getWtAreaLists(Integer ownmonth) {
		return dal.getWtAreaLists(ownmonth);
	}

	// 根据委托地区和所属月份查找委托机构
	public List<String> getSetupLists(String city) {
		return dal.getSetupLists(city);
	}

	// 获取委托地区列表
	public List<String> getCity() {
		return dal.getCity();
	}

	// 根据cid和机构查询合同id
	public int[] getCocoID(String str) {
		return dal.getCocoID(str);
	}

	// 查询台帐表的所有的所属月份
	public List<Integer> getOwnmonth() {
		return dal.getOwnmonth();
	}

	// 根据合同编号和所属月份查询委托进数据信息_智翼通数据
	public List<EmFinanceZYTModel> getEmFinance(Integer ownmonth, int[] coco_id) {
		List<EmFinanceZYTModel> list = new ArrayList<EmFinanceZYTModel>();
		String cocoidstr = "";
		for (int cocoid : coco_id) {
			if (cocoidstr == null || cocoidstr.equals("")) {
				cocoidstr = cocoid + "";
			} else {
				cocoidstr = cocoidstr + "," + cocoid;
			}
		}
		if (cocoidstr != null && !cocoidstr.equals("")) {
			list = dal.getEmFinanceList2(ownmonth, cocoidstr);
		}
		return list;
	}

	// 根据合同编号和所属月份查询委托进系统数据信息
	private CoFinanceWtModel getfilist(int ownmonth, int[] coco_id) {
		CoFinanceWtModel model = new CoFinanceWtModel();
		EmFinanceWt_SelBll selbll = new EmFinanceWt_SelBll();
		List<EmFinanceZYTModel> list = new ArrayList<EmFinanceZYTModel>();
		model = selbll.getCoFinanceWtModel(
				selbll.getEmFinanceDataByCP(ownmonth, coco_id), list);
		return model;
	}

	

	// 智翼通的数据与系统数据对比得出智翼通多出的员工
	public List<EmFinanceZYTModel> getWtZYTList(String city, int ownmonth,
			int cid) {
		int[] ints = dal.getCocoID(" and coab_city='" + city + "' and cid="
				+ cid);
		List<EmFinanceZYTModel> list = new ArrayList<EmFinanceZYTModel>();
		List<EmFinanceZYTModel> zytlist = dal.getEmFinanceInfoList(ownmonth,
				cid);// 获取智翼通的员工
		CoFinanceWtModel fimodel = getfilist(ownmonth, ints);// 系统的员工
		List<EmFinanceWtModel> filist = fimodel.getEfWtList();
		List<Integer> gidlist = new ArrayList<Integer>();
		for (EmFinanceWtModel model : filist) {// 把系统的员工存到gidlist中
			gidlist.add(model.getGid());
		}
		for (EmFinanceZYTModel model : zytlist) {
			if (!gidlist.contains(model.getGid())) {
				Integer k = hebing(model, list);
				if (k == 0) {
					list.add(model);
				}
			}
		}
		return list;
	}

	// 把gid相同的员工合并
	private Integer hebing(EmFinanceZYTModel ml, List<EmFinanceZYTModel> list) {
		Integer k = 0;
		for (EmFinanceZYTModel m : list) {
			if (ml.getGid() != null && m.getGid() != null) {
				if (ml.getGid() == m.getGid() || ml.getGid().equals(m.getGid())) {
					m.setEmfz_total(m.getEmfz_total().add(ml.getEmfz_total()));
					k = 1;
				}
			}
		}
		return k;
	}

	// 智翼通的数据与系统数据对比得出系统多出的员工
	public List<EmFinanceWtModel> getWtFiList(String city, int ownmonth, int cid) {
		int[] ints = dal.getCocoID("and coab_city='" + city + "' and cid="
				+ cid);
		List<EmFinanceWtModel> list = new ArrayList<EmFinanceWtModel>();
		List<EmFinanceZYTModel> zytlist = dal.getEmFinanceInfoList(ownmonth,
				cid);// 获取智翼通员工
		CoFinanceWtModel fimodel = getfilist(ownmonth, ints);// 系统的员工
		List<EmFinanceWtModel> filist = fimodel.getEfWtList();// 系统的员工列表
		List<Integer> gidlist = new ArrayList<Integer>();
		for (EmFinanceZYTModel model : zytlist)// 存智翼通已有的员工
		{
			gidlist.add(model.getGid());
		}
		for (EmFinanceWtModel model : filist) {
			if (!gidlist.contains(model.getGid())) {// 如果系统的员工不在智翼通则添加到列表——系统多出
				list.add(model);
			}
		}
		return list;
	}

	// 查询客服
	public List<String> getLoginlist(String str) {
		return dal.getLoginlist(str);
	}

	// 根据合同编号获取cid
	public Integer getCidByCocoId(String str) {
		return dal.getCidByCocoId(str);
	}

	// 根据委托地区和cid查询智翼通员工数
	public Integer getEmbaseCount(String city, Integer cid, Integer ownmonth,
			String coab_name) {
		return dal.getEmbaseCount(city, cid, ownmonth, coab_name);
	}

	// 根据客服查cid
	public List<Integer> getCidByClient(String client) {
		return dal.getCidByClient(client);
	}

	// 根据账单号获取账单备注
	public String getBillRemark(String billno) {
		return dal.getBillRemark(billno);
	}
}
