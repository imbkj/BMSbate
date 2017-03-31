package bll.EmBenefit;

import java.util.List;

import org.zkoss.zul.ListModelList;

import dal.CoBase.CoBase_SelectDal;
import dal.EmBenefit.EmActy_GiftInfoSelectDal;
import dal.EmBenefit.EmBenefitDal;
import dal.EmBenefit.EmWelfareDal;

import Model.EmActySuppilerGiftInfoModel;
import Model.EmActyWarehouseHistoryModel;
import Model.EmActyWarehouseModel;
import Model.EmActyWarehouseNoteModel;
import Model.EmBenefitModel;
import Model.EmWelfareModel;

public class EmBenefit_comitListBll {

	public List<EmWelfareModel> getCompanyList(String name) {
		List<EmWelfareModel> list = new ListModelList<>();
		EmWelfareDal dal = new EmWelfareDal();
		list = dal.getCompanyList(name);
		return list;
	}

	public List<EmWelfareModel> getClientList(String name) {
		List<EmWelfareModel> list = new ListModelList<>();
		EmWelfareDal dal = new EmWelfareDal();
		list = dal.getClientList(name);
		return list;
	}

	public List<EmWelfareModel> getNameList(String name) {
		List<EmWelfareModel> list = new ListModelList<>();
		EmWelfareDal dal = new EmWelfareDal();
		list = dal.getNameList(name);
		return list;
	}

	public List<EmBenefitModel> getItemList(String name) {
		List<EmBenefitModel> list = new ListModelList<>();
		EmBenefitDal dal = new EmBenefitDal();
		list = dal.getNamelist(name);
		return list;
	}

	public List<EmWelfareModel> getList(EmWelfareModel ewfm) {
		List<EmWelfareModel> list = new ListModelList<>();
		EmWelfareDal dal = new EmWelfareDal();
		list = dal.getList(ewfm);
		return list;
	}

	public List<EmWelfareModel> getLists(String str) {
		List<EmWelfareModel> list = new ListModelList<>();
		EmWelfareDal dal = new EmWelfareDal();
		list = dal.getLists(str);
		return list;
	}

	public List<EmWelfareModel> getEmwfList(String emwf_sortid) {
		EmWelfareDal dal = new EmWelfareDal();
		return dal.getEmwfList(emwf_sortid);
	}

	// 财务审核查询
	public List<EmWelfareModel> getEmwfListAudit(String emwf_sortid) {
		EmWelfareDal dal = new EmWelfareDal();
		return dal.getEmwfListAudit(emwf_sortid);
	}

	public List<EmWelfareModel> getWfList(String str, String strcon) {
		EmWelfareDal dal = new EmWelfareDal();
		return dal.getWfList(str, strcon);
	}

	// 查询客服
	public List<String> clientlist(String str) {
		EmWelfareDal dal = new EmWelfareDal();
		return dal.clientlist(str);
	}

	public List<EmWelfareModel> getWfLists(String str, String strcon) {
		EmWelfareDal dal = new EmWelfareDal();
		return dal.getWfLists(str, strcon);
	}

	public List<EmWelfareModel> getEmWelfareList(String str) {
		EmWelfareDal dal = new EmWelfareDal();
		return dal.getEmWelfareList(str);
	}

	// 获取福利项目
	public List<String> getEmbfnameList() {
		EmWelfareDal dal = new EmWelfareDal();
		return dal.getEmbfnameList();
	}

	// 查询是否还有没有支付费用的员工
	public Integer getIfPay() {
		EmWelfareDal dal = new EmWelfareDal();
		return dal.getIfPay();
	}

	// 获取福利类型
	public String getMold(String sorid) {
		EmWelfareDal dal = new EmWelfareDal();
		return dal.getMold(sorid);
	}

	// 获取需审核的数据
	public List<EmWelfareModel> getWfListAudit() {
		EmWelfareDal dal = new EmWelfareDal();
		return dal.getWfListAudit();
	}

	public EmActySuppilerGiftInfoModel getEmActySuppilerGiftInfo(String str) {
		EmWelfareDal dal = new EmWelfareDal();
		return dal.getEmActySuppilerGiftInfo(str);
	}

	// 获取福利项目
	public List<String> getEmbfname() {
		EmWelfareDal dal = new EmWelfareDal();
		return dal.getEmbfname();
	}

	// 获取统计数据
	public String getWfCount(List<EmWelfareModel> list) {
		String tjstr = "", sql = "";
		String str = getStrId(list);
		EmActy_GiftInfoSelectDal dals = new EmActy_GiftInfoSelectDal();
		if (str != null && !str.equals("")) {
			sql = " and emwf_id in(" + str + ")";

		}
		List<EmWelfareModel> countlist = dals.getWfCounts(sql
				+ " and (emwf_state=3 or emwf_state=11)");
		if (countlist.size() > 0) {
			for (int j = 0; j < countlist.size(); j++) {
				if (j == 0) {
					tjstr = countlist.get(0).getEmbf_name() + "["
							+ countlist.get(0).getNum() + "]";
				} else {
					tjstr = tjstr + "　　" + countlist.get(j).getEmbf_name()
							+ "[" + countlist.get(j).getNum() + "]";
				}
			}
		}
		return tjstr;
	}

	// 根据list拼接id成字符串
	private String getStrId(List<EmWelfareModel> list) {
		String idstr = "";
		for (int i = 0; i < list.size(); i++) {
			if (i == 0) {
				idstr = list.get(i).getEmwf_id() + "";
			} else {
				idstr = idstr + "," + list.get(i).getEmwf_id();
			}
		}
		return idstr;
	}

	// 获取福利内容
	public List<String> getEmwfnameList() {
		EmWelfareDal dal = new EmWelfareDal();
		return dal.getEmwfnameList();
	}

	// 获取统计数据
	public List<EmWelfareModel> getWfCount(String str) {
		EmWelfareDal dal = new EmWelfareDal();
		return dal.getWfCount(str);
	}

	// 查询库存信息
	public List<EmActyWarehouseModel> getEmActyWarehouse(String str) {
		EmWelfareDal dal = new EmWelfareDal();
		return dal.getEmActyWarehouse(str);
	}

	public EmActyWarehouseModel getEmActyWarehouseModel(String name) {
		String sql = "";
		EmActyWarehouseModel model = new EmActyWarehouseModel();
		if (name != null) {
			sql = " and wase_name='" + name + "'";
		}
		List<EmActyWarehouseModel> list = getEmActyWarehouse(sql);
		if (list.size() > 0) {
			model = list.get(0);
		}
		return model;
	}

	// 获取客服
	public static List<String> getClientList() {
		EmWelfareDal dal = new EmWelfareDal();
		return dal.getClientList();
	}

	// 获取库存历史数据
	public List<EmActyWarehouseHistoryModel> getEmActyWarehouseHistory(
			String sqlstr) {
		EmWelfareDal dal = new EmWelfareDal();
		return dal.getEmActyWarehouseHistory(sqlstr);
	}

	// 获取库存记录数据
	public List<EmActyWarehouseNoteModel> getEmActyWarehouseNote(String sqlstr) {
		EmWelfareDal dal = new EmWelfareDal();
		return dal.getEmActyWarehouseNote(sqlstr);
	}

	public boolean ifBackCase(String idstr) {
		EmWelfareDal dal = new EmWelfareDal();
		return dal.ifBackCase(idstr);
	}
}
