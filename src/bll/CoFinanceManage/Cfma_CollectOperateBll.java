package bll.CoFinanceManage;

import java.math.BigDecimal;
import java.util.List;

import Model.CoFinanceMonthlyBillSortAccountModel;

import Model.CoFinanceSortAccountssModel;
import dal.CoFinanceManage.Cfma_CollectOperateDal;


public class Cfma_CollectOperateBll {
	private Cfma_CollectOperateDal dal = new Cfma_CollectOperateDal();

	// 添加公司收款
	public int addCollectToCompany(int cid, BigDecimal paidin, String username,
			String remark,int ownmonth,Boolean kpfrist) {
		return dal.addCollectToCompany(cid, "0", paidin, username, remark,ownmonth,kpfrist);
	}
	// 添加公司收款
	public int addCollectToCompanys(int cid, BigDecimal paidin, String username,
			String remark,int ownmonth) {
		return dal.addCollectToCompanys(cid, "0", paidin, username, remark,ownmonth);
	}
	
	// 添加财务实收收款
	public int addcogathering(List<CoFinanceSortAccountssModel> m,int cfco_id) {
		int y=0;
		for (int i=0;i<m.size();i++)
		{
			y=y+dal.addcogathering(m.get(i),cfco_id);
		}
		
		return y;
	}
	
	
	//修改公司收款
	public int updateCollectToCompany(int cfco_id,int cid, BigDecimal paidin, String username,
			String remark,int ownmonth,Boolean f) {
		return dal.updateCollectToCompany(cfco_id,cid, "0", paidin, username, remark,ownmonth,f);
	}
	
	// 修改财务实收收款
		public int modcogathering(List<CoFinanceSortAccountssModel> m,String remark,String ownmonth,String cfss_type,Boolean f,Boolean all) {
			int y=0;
			for (int i=0;i<m.size();i++)
			{
				
				m.get(i).setRemark(remark);
				m.get(i).setOwnmonth(Integer.parseInt(ownmonth));
				m.get(i).setCfss_type(cfss_type);
				m.get(i).setCfss_fpfrist(f);
				m.get(i).setCfss_allin(all);
				y=y+dal.modcogathering(m.get(i));
			}
			
			return y;
		}


	// 账单领款
	public String[] drawMoneyToBill(String cfmb_number, int cid,
			BigDecimal drawEx, String username, String remark,
			List<CoFinanceMonthlyBillSortAccountModel> sortList) {
		String[] message = new String[2];
		int i = 0;
		try {
			int cfdm_id = dal.addDrawMoneyToBill(cfmb_number, cid, drawEx,
					username, remark);
			if (cfdm_id > 0) {
				for (CoFinanceMonthlyBillSortAccountModel m : sortList) {
					if (m.getCollect().compareTo(BigDecimal.ZERO) == 1) {
						if (dal.disMoneyToBillSort(cfdm_id, m.getCfsa_id(),
								m.getCollect()) == 0)
							i++;
					}
				}
				dal.upBillPaidin(cfmb_number);
				if (i == 0) {
					message[0] = "1";
					message[1] = "提交成功。";
				} else {
					message[0] = "1";
					message[1] = cfmb_number
							+ "数据已提交，部分金额分配有误，请将这条信息复制并与计算机部门联系。";
				}
			} else {
				message[0] = "0";
				message[1] = cfmb_number + "提交失败，领取的金额有误，请将这条信息复制并与计算机部门联系。";
			}
		} catch (Exception e) {
			e.printStackTrace();
			message[0] = "2";
			message[1] = cfmb_number + "提交出错，错误情况未知，请将这条信息复制并与计算机部门联系。";
		}
		return message;
	}

	// 账单收款
	public String[] addCollectToBill(int cid, BigDecimal paidin,
			String username, String remark, String cfmb_number,int ownmonth,
			BigDecimal drawEx,
			List<CoFinanceMonthlyBillSortAccountModel> sortList) {
		String[] message = new String[2];
		int i = 0;
		try {
			// 添加收款
			if (dal.addCollectToCompany(cid, cfmb_number, paidin, username,
					remark,ownmonth,false) == 1) {
				// 账单领款
				int cfdm_id = dal.addDrawMoneyToBill(cfmb_number, cid, drawEx,
						username, remark);
				if (cfdm_id > 0) {
					for (CoFinanceMonthlyBillSortAccountModel m : sortList) {
						if (m.getCollect().compareTo(BigDecimal.ZERO) == 1) {
							if (dal.disMoneyToBillSort(cfdm_id, m.getCfsa_id(),
									m.getCollect()) == 0)
								i++;
						}
					}
					dal.upBillPaidin(cfmb_number);
					if (i == 0) {
						message[0] = "1";
						message[1] = "提交成功。";
					} else {
						message[0] = "1";
						message[1] = cfmb_number
								+ "出错，收款领款已添加，科目分配有误，请将这条信息复制并与计算机部门联系。";
						;
					}
				} else {
					message[0] = "0";
					message[1] = cfmb_number
							+ "出错，收款已添加，领款有误，请将这条信息复制并与计算机部门联系。";
				}
			} else {
				message[0] = "0";
				message[1] = cfmb_number + "提交失败，添加收款有误，请将这条信息复制并与计算机部门联系。";
			}
		} catch (Exception e) {
			e.printStackTrace();
			message[0] = "2";
			message[1] = cfmb_number + "提交出错，错误情况未知，请将这条信息复制并与计算机部门联系。";
		}
		return message;
	}
	
	// 账单收款
		public String[] addCollectToBills(int cid, BigDecimal paidin,
				String username, String remark, String cfmb_number,int ownmonth,
				BigDecimal drawEx,
				List<CoFinanceMonthlyBillSortAccountModel> sortList) {
			String[] message = new String[2];
			int i = 0;
			try {
				// 添加收款
				if (dal.addCollectToCompanys(cid, cfmb_number, paidin, username,
						remark,ownmonth) == 1) {
					// 账单领款
					int cfdm_id = dal.addDrawMoneyToBill(cfmb_number, cid, drawEx,
							username, remark);
					if (cfdm_id > 0) {
						for (CoFinanceMonthlyBillSortAccountModel m : sortList) {
							if (m.getCollect().compareTo(BigDecimal.ZERO) == 1) {
								if (dal.disMoneyToBillSort(cfdm_id, m.getCfsa_id(),
										m.getCollect()) == 0)
									i++;
							}
						}
						dal.upBillPaidin(cfmb_number);
						if (i == 0) {
							message[0] = "1";
							message[1] = "提交成功。";
						} else {
							message[0] = "1";
							message[1] = cfmb_number
									+ "出错，收款领款已添加，科目分配有误，请将这条信息复制并与计算机部门联系。";
							;
						}
					} else {
						message[0] = "0";
						message[1] = cfmb_number
								+ "出错，收款已添加，领款有误，请将这条信息复制并与计算机部门联系。";
					}
				} else {
					message[0] = "0";
					message[1] = cfmb_number + "提交失败，添加收款有误，请将这条信息复制并与计算机部门联系。";
				}
			} catch (Exception e) {
				e.printStackTrace();
				message[0] = "2";
				message[1] = cfmb_number + "提交出错，错误情况未知，请将这条信息复制并与计算机部门联系。";
			}
			return message;
		}

	// 销账
	public int BillWriteOffs(String cfmb_number, String username) {
		return dal.BillWriteOffs(cfmb_number, username);
	}

	// 结转
	public int BillCarryForward(String cfmb_number, int cid, int ownmonth,
			String remark, String username) {
		return dal.BillCarryForward(cfmb_number, cid, ownmonth, remark,
				username);
	}
}
