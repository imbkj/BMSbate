package bll.EmFinanceManage;


import java.util.List;

import Model.CoBaseModel;
import Model.CoFinanceCollectModel;
import Model.CollectAmountModel;

import Model.CollectAmountNewModel;

import dal.EmFinanceManage.GatherInfoNewDal;

public class GatherInfoNewBll {

	// 将amount的纵向数据加到账单列表中（新）
	public List<CoBaseModel> setAmountToCollectssNew(String sql) {
		GatherInfoNewDal dal = new GatherInfoNewDal();
		List<CoBaseModel> coList = dal.getCoinfoListssNew(sql);
		for (CoBaseModel m : coList) {
			List<CoFinanceCollectModel> list = dal.getCollectListssNew(
					m.getOwnmonth(), m.getCid(), m.getCfss_cfso_id());
		
			CollectAmountModel am=new CollectAmountModel();
			if(m.getTotal()!=null)
				am.setCfmb_TotalPaidIn(m.getTotal());
			m.setAmount(am);
			for (CoFinanceCollectModel cm : list) {
				if (cm.getCfsa_PaidIn() != null) {
					if (cm.getCfsa_cpac_name().equals("档案保管费")) {
						m.getAmount().setFileManageFee(cm.getCfsa_PaidIn());
					} else if (cm.getCfsa_cpac_name().equals("服务费")) {
						m.getAmount().setServiceFee(cm.getCfsa_PaidIn());
					} else if (cm.getCfsa_cpac_name().equals("社保费")) {
						m.getAmount().setSheBaoFee(cm.getCfsa_PaidIn());
					} else if (cm.getCfsa_cpac_name().equals("活动费")) {
						m.getAmount().setActivityFee(cm.getCfsa_PaidIn());
					} else if (cm.getCfsa_cpac_name().equals("体检费")) {
						m.getAmount().setBodyTestFee(cm.getCfsa_PaidIn());
					} else if (cm.getCfsa_cpac_name().equals("商保费")) {
						m.getAmount()
								.setBusinessProtectFee(cm.getCfsa_PaidIn());
					} else if (cm.getCfsa_cpac_name().equals("书报费")) {
						m.getAmount().setBookFee(cm.getCfsa_PaidIn());
					} else if (cm.getCfsa_cpac_name().equals("税后工资")) {
						m.getAmount().setSalaryOfAfterTax(cm.getCfsa_PaidIn());
					} else if (cm.getCfsa_cpac_name().equals("个调税")) {
						m.getAmount().setoMoveFee(cm.getCfsa_PaidIn());
						
					} else if (cm.getCfsa_cpac_name().equals("户口")) {
						m.getAmount().setAccountfee(cm.getCfsa_PaidIn());
						
					} else if (cm.getCfsa_cpac_name().equals("住房返还")) {
						m.getAmount().setHouseRestore(cm.getCfsa_PaidIn());
					} else if (cm.getCfsa_cpac_name().equals("财务服务费")) {
						m.getAmount().setFinanServiceFee(cm.getCfsa_PaidIn());
					} else if (cm.getCfsa_cpac_name().equals("商务服务费")) {
						m.getAmount()
								.setBusinessServiceFee(cm.getCfsa_PaidIn());
					} else if (cm.getCfsa_cpac_name().equals("招聘服务费")) {
						m.getAmount().setRecruitServiceFee(cm.getCfsa_PaidIn());
					} else if (cm.getCfsa_cpac_name().equals("居住证")) {
						m.getAmount()
								.setResidencePermitFee(cm.getCfsa_PaidIn());
					} else if (cm.getCfsa_cpac_name().equals("劳动保障卡")) {
						m.getAmount().setLasscFee(cm.getCfsa_PaidIn());
					} else if (cm.getCfsa_cpac_name().equals("残保金")) {
						m.getAmount().setDeformityFee(cm.getCfsa_PaidIn());
					} else if (cm.getCfsa_cpac_name().equals("其它")) {
						m.getAmount().setOther(cm.getCfsa_PaidIn());
					} else if (cm.getCfsa_cpac_name().equals("住房公积金")) {
						m.getAmount().setHouseGjj(cm.getCfsa_PaidIn());
					} else if(cm.getCfsa_cpac_name().equals("税金")){
						m.getAmount().setTaxes(cm.getCfsa_PaidIn());
					}
					// 把金额set到对应账单的字段中
					m.getAmount().setAddtime(cm.getCfta_addtime());
					m.getAmount().setAddname(cm.getCfta_addname());
					m.getAmount().setRemark(cm.getCfmb_remark());
				    //CoFinanceSortAccountssNew 表主键值放入CollectAmountModel中
					m.getAmount().setCfss_id(cm.getCfss_id());
					
				}
			}
		}
		return coList;
	}

	public void insertCollectListssNew(String cfss_cfso_id) {
		GatherInfoNewDal dal = new GatherInfoNewDal();
		dal.insertCollectListssNew(cfss_cfso_id);
	}
	
	//修改新旧账单表开票状态
	public int updateCfss_fpstate(String cfss_id,int cfss_fpstate){
		GatherInfoNewDal dal = new GatherInfoNewDal();
		return dal.upCfss_fpstate(cfss_id, cfss_fpstate);
	}
}
