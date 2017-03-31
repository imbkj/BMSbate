package bll.EmFinance;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bll.CoFinanceManage.EmFinanceWt_SelBll;

import dal.CoFinanceManage.EmFinanceWt_SelDal;
import dal.EmFinance.EmFinance_SelectDal;
import dal.EmFinance.Finance_SetupDal;

import Model.CoFinanceWtModel;
import Model.EmFinanceWtModel;
import Model.EmFinanceZYTModel;

public class Finance_SetupBll {
	private Finance_SetupDal dal = new Finance_SetupDal();

	// 查询委托进数据信息
	public List<EmFinanceZYTModel> getEmFinanceList(String str) {
		List<EmFinanceZYTModel> list = dal.getEmFinanceList(str);
		return getList(list);
	}

	// 查询委托进数据信息
	public List<EmFinanceZYTModel> getEmFinanceLists(String str) {
		List<EmFinanceZYTModel> list = dal.getEmFinanceLists(str);
		return getList(list);
	}

	// 获取系统应收总金额
	private List<EmFinanceZYTModel> getList(List<EmFinanceZYTModel> list) {
		List<EmFinanceZYTModel> lists = new ArrayList<EmFinanceZYTModel>();
		EmFinanceWt_SelBll bll = new EmFinanceWt_SelBll();
		for (int i = 0; i < list.size(); i++) {
			EmFinanceZYTModel model = list.get(i);
			if (model.getCabc_id() != null) {
				Integer state = 0, s = 0, t = 0;
				int[] ints = getCocoID(model.getName(), model.getCoab_name());
				for (int y = 0; y < ints.length; y++) {
					state = bll.existsBillConfirmByCp(model.getOwnmonth(),
							ints[y]);
					if (state == 2) {
						model.setStateid(2);
						break;
					} else if (state == 1) {
						s = s + 1;
					} else if (state == 0) {
						t = t + 1;
					}
				}
				if (s == ints.length) {
					model.setStateid(1);
				} else if (t == ints.length) {
					model.setStateid(0);
				}
				// BigDecimal ml=new BigDecimal("0.00");
				BigDecimal[] ml = new BigDecimal[2];
				ml = bll.getEmFinanceWtTotalByCP(model.getOwnmonth(), ints);
				model.setEmfi_total(ml[0]);
			}
			lists.add(model);
		}
		return lists;
	}

	public List<EmFinanceZYTModel> getDataList(Integer ownmonth) {
		CoFinanceWtModel model = new CoFinanceWtModel();
		EmFinance_SelectBll bll = new EmFinance_SelectBll();
		model = bll.getModels(ownmonth);
		Map<String, EmFinanceWtModel> map = new HashMap<String, EmFinanceWtModel>();
		// 获取所有的员工
		for (EmFinanceWtModel m : model.getEfWtList()) {
			if(m.getOwnmonth()==null)
			{
				m.setOwnmonth(ownmonth);
			}
			if (!map.containsKey(m.getCid() + m.getOwnmonth())) {
				map.put(m.getCid() + m.getOwnmonth() + "", m);
			}
		}
		return getInfo(map, model.getEfWtList());
	}

	public List<EmFinanceZYTModel> getInfo(Map<String, EmFinanceWtModel> map,
			List<EmFinanceWtModel> flist) {
		String cidstr = "", sql = "";
		try {
			Collection<EmFinanceWtModel> collection = map.values();
			for (EmFinanceWtModel m : collection) {
				if (cidstr.equals("")) {
					cidstr = "" + m.getCid();
				} else {
					cidstr = cidstr + "," + m.getCid();
				}
			}
		} catch (Exception e) {
		}
		if (!cidstr.equals("")) {
			sql = " and cid in(" + cidstr + ")";
		}
		List<EmFinanceZYTModel> list = dal.getCoabList(sql);
		List<EmFinanceZYTModel> lists = new ArrayList<EmFinanceZYTModel>();
		for (EmFinanceZYTModel ml : list) {
			BigDecimal emfz_total = BigDecimal.ZERO;
			BigDecimal emfi_total = BigDecimal.ZERO;
			Integer ownmonth = null;
			List<Integer> cidlist = dal.getCidList(" and cabc_id="
					+ ml.getCabc_id());
			for (int j = 0; j < cidlist.size(); j++) {
				for (EmFinanceWtModel mol : flist) {
					ownmonth = mol.getOwnmonth();
					if (cidlist.get(j) != null
							&& mol.getCid() == cidlist.get(j)) {
						if (mol.getEmfi_total() != null) {
							emfi_total = emfi_total.add(mol.getEmfi_total());
						}
						if (mol.getEmfi_total() != null) {
							emfz_total = emfz_total.add(mol.getEmfz_total());
						}
					}
				}
			}
			ml.setEmfi_total(emfi_total);
			ml.setTotal(emfz_total);
			ml.setOwnmonth(ownmonth);
			ml.setFifztotal(ml.getEmfi_total().subtract(ml.getEmfz_total()));
			lists.add(ml);
		}
		return lists;
	}

	// 根据cid和机构查询合同id
	public int[] getCocoID(String city, String coab_name) {
		EmFinance_SelectDal dals = new EmFinance_SelectDal();
		return dals.getCocoID(city, coab_name);
	}

	// 根据委托地区和所属月份查找委托机构
	public List<String> getSetupList(String str) {
		return dal.getSetupList(str);
	}

	// 根据所属月份查询委托地区
	public List<String> getWtAreaList(String str) {
		return dal.getWtAreaList(str);
	}

	// 根据所属月份查询机构信息
	public List<EmFinanceZYTModel> getEmFinanceSetupList(Integer ownmonth) {
		List<EmFinanceZYTModel> list = dal.getEmFinanceSetupListn(ownmonth);
		
		return list;
	}

	class MyThread extends Thread {
		List<EmFinanceZYTModel> list;
		int ownmonth;
		public MyThread(List<EmFinanceZYTModel> list,int ownmonth) {
			this.list = list;
			this.ownmonth=ownmonth;
		}

		public void run() {
			for (EmFinanceZYTModel m : list) {
				m.setOwnmonth(ownmonth);
				//m.setTotal(dal.getZytTotal(ownmonth, m.getCabc_id()));
				m.setEmfi_total(getEmFinanceWtTotalByCP(ownmonth, m.getCabc_id()));
			}
		}
	}

	// 根据所属月份查询机构信息
	public List<EmFinanceZYTModel> getEmFinanceSetupListf(Integer ownmonth) {
		return dal.getEmFinanceSetupList(ownmonth);
	}

	// 根据月份获取城市
	public List<String> getCoabCity(Integer ownmonth) {
		return dal.getCoabCity(ownmonth);
	}

	// 根据月份和城市获取机构
	public List<String> getCoabSetup(Integer ownmonth, String city) {
		return dal.getCoabSetup(ownmonth, city);
	}

	// 按合同编号采集台账数据应收总额
	public BigDecimal getEmFinanceWtTotalByCP(int ownmonth, Integer cabc_id) {
		int cocoid[] = dal.getCocoId(ownmonth, cabc_id);
		BigDecimal emfi_total = BigDecimal.ZERO;// 系统总额
		EmFinanceWt_SelDal dl = new EmFinanceWt_SelDal();
		try {
			for (int coco_id : cocoid) {
				BigDecimal[] bd = new BigDecimal[2];
				bd = dl.getEmFinanceTotalByCP(ownmonth, coco_id);
				if (bd[0] != null) {
					emfi_total = emfi_total.add(bd[0]);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return emfi_total;
	}
}
