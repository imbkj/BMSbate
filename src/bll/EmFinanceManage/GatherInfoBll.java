package bll.EmFinanceManage;

import java.util.ArrayList;
import java.util.List;

import Model.CoBaseModel;
import Model.CoFinanceCollectModel;
import Model.CoFinanceMonthlyBillSortAccountModel;
import Model.CollectAmountModel;
import Model.GatherInfoModel;
import dal.CoFinanceManage.Cfma_SelDal;
import dal.EmFinanceManage.GatherInfoDal;

public class GatherInfoBll {
	private GatherInfoDal dal = new GatherInfoDal();
	private Cfma_SelDal cfdal = new Cfma_SelDal();

	// 将amount的纵向数据加到账单列表中（新）
	public List<CoBaseModel> setAmountToCollectss(String sql) {
		List<CoBaseModel> coList = dal.getCoinfoListssNew(sql);
		/*
		List<CoBaseModel> coList = dal.getCoinfoListss(sql);
		
		for (CoBaseModel m : coList) {
			List<CoFinanceCollectModel> list = dal.getCollectListss(
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
					} // 把金额set到对应账单的字段中
					m.getAmount().setAddtime(cm.getCfta_addtime());
					m.getAmount().setAddname(cm.getCfta_addname());
					m.getAmount().setModname(cm.getCfta_modname());
					m.getAmount().setRemark(cm.getCfmb_remark());
				 
				}
			}
		}*/
		return coList;
	}

	// 将amount的纵向数据加到账单列表中(旧)
	public List<CoBaseModel> setAmountToCollect(String sql, String ownmonth,
			String colsql, String sqls) {
		List<CoBaseModel> coList = dal.getCoinfoList(sql, ownmonth, sqls);
		List<CoFinanceCollectModel> collects = dal.getCollectList(colsql);
		for (CoBaseModel m : coList) {
			List<CoFinanceCollectModel> l = new ArrayList<CoFinanceCollectModel>();
			// 循环的查询账单号
			for (CoFinanceCollectModel m1 : collects) {
				if (m.getCid() == m1.getCid()
						&& m.getOwnmonth() == m1.getOwnmonth()) {
					l.add(m1);
					String remark = "";
					if (m.getCfmb_remark() != null) {
						remark = m.getCfmb_remark();
					}
					if (m1.getCfmb_remark() != null
							&& !m1.getCfmb_remark().equals("")) {
						if (remark != null && !remark.equals("")) {
							remark = remark + "。" + m1.getCfmb_remark();
						}
					}
					m.setCfmb_remark(remark);
					m.setCfta_addtime(m1.getCfta_addtime());
				}
			}
			m.setCfcms(l);
			if (m.getCfcms().size() > 0) {
				for (int i = 0; i < m.getCfcms().size(); i++) {
					List<CoFinanceMonthlyBillSortAccountModel> amounts = dal
							.getAmountList(m.getCfcms().get(i)
									.getCfco_cfmb_number());
					for (CoFinanceMonthlyBillSortAccountModel m2 : amounts) {

						if (m2.getCfsa_cpac_name().equals("档案保管费")) {
							m.getCfcms().get(i)
									.setFileManageFee(m2.getCfsa_PaidIn());
						} else if (m2.getCfsa_cpac_name().equals("服务费")) {
							m.getCfcms().get(i)
									.setServiceFee(m2.getCfsa_PaidIn());
						} else if (m2.getCfsa_cpac_name().equals("社保费")) {
							m.getCfcms().get(i)
									.setSheBaoFee(m2.getCfsa_PaidIn());
						} else if (m2.getCfsa_cpac_name().equals("活动费")) {
							m.getCfcms().get(i)
									.setActivityFee(m2.getCfsa_PaidIn());
						} else if (m2.getCfsa_cpac_name().equals("体检费")) {
							m.getCfcms().get(i)
									.setBodyTestFee(m2.getCfsa_PaidIn());
						} else if (m2.getCfsa_cpac_name().equals("商保费")) {
							m.getCfcms().get(i)
									.setBusinessProtectFee(m2.getCfsa_PaidIn());
						} else if (m2.getCfsa_cpac_name().equals("书报费")) {
							m.getCfcms().get(i).setBookFee(m2.getCfsa_PaidIn());
						} else if (m2.getCfsa_cpac_name().equals("税后工资")) {
							m.getCfcms().get(i)
									.setSalaryOfAfterTax(m2.getCfsa_PaidIn());
						} else if (m2.getCfsa_cpac_name().equals("个调税")) {
							m.getCfcms().get(i)
									.setoMoveFee(m2.getCfsa_PaidIn());
						} else if (m2.getCfsa_cpac_name().equals("住房返还")) {
							m.getCfcms().get(i)
									.setHouseRestore(m2.getCfsa_PaidIn());
						} else if (m2.getCfsa_cpac_name().equals("财务服务费")) {
							m.getCfcms().get(i)
									.setFinanServiceFee(m2.getCfsa_PaidIn());
						} else if (m2.getCfsa_cpac_name().equals("商务服务费")) {
							m.getCfcms().get(i)
									.setBusinessServiceFee(m2.getCfsa_PaidIn());
						} else if (m2.getCfsa_cpac_name().equals("招聘服务费")) {
							m.getCfcms().get(i)
									.setRecruitServiceFee(m2.getCfsa_PaidIn());
						} else if (m2.getCfsa_cpac_name().equals("居住证")) {
							m.getCfcms().get(i)
									.setResidencePermitFee(m2.getCfsa_PaidIn());
						} else if (m2.getCfsa_cpac_name().equals("劳动保障卡")) {
							m.getCfcms().get(i)
									.setLasscFee(m2.getCfsa_PaidIn());
						} else if (m2.getCfsa_cpac_name().equals("残保金")) {
							m.getCfcms().get(i)
									.setDeformityFee(m2.getCfsa_PaidIn());
						} else if (m2.getCfsa_cpac_name().equals("其它")) {
							m.getCfcms().get(i).setOther(m2.getCfsa_PaidIn());
						} else if (m2.getCfsa_cpac_name().equals("住房公积金")) {
							m.getCfcms().get(i)
									.setHouseGjj(m2.getCfsa_PaidIn());
						} // 把金额set到对应账单的字段中
					}
				}
			}
		}
		for (int i = 0; i < coList.size(); i++) {
			if (coList.get(i).getCfcms().size() > 0) {
				coList.get(i).setAmount(coList.get(i).getCfcms());
			}
		}
		return coList;
	}

	public List<GatherInfoModel> getOwnmonthBillList(String str) {
		return dal.getOwnmonthBillList(str);
	}
	
	public List<GatherInfoModel> getOwnmonthBillListssnew(String str) {
		return dal.getOwnmonthBillListssnew(str);
	}
	
	public GatherInfoModel getOwnmonthBillListss2(String str) {
		return dal.getOwnmonthBillListss2(str);
	}
	
	public String getCocompact(Integer cid) {
		return dal.getCocompact(cid);
	}

	public List<GatherInfoModel> getOwnmonthBillListByCid(Integer cid) {
		String str = " and c.cid=" + cid;
		return dal.getOwnmonthBillList(str);
	}

	public CoBaseModel getClientClass(Integer cid) {
		return dal.getClientClass(cid);
	}

	public Integer updateCoba(CoBaseModel m) {
		return dal.updateCoba(m);
	}

	public Integer updateYyOutState(String cfss_cfso_id,int cid,int ownmonth) {
		return dal.updateYyOutState(cfss_cfso_id,cid,ownmonth);
	}
}
