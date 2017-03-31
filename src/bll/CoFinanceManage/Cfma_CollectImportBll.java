package bll.CoFinanceManage;

import java.util.List;

import Model.CoFinanceCollectImportErrModel;
import Model.CoFinanceCollectImportModel;
import dal.CoFinanceManage.Cfma_CollectImportDal;

public class Cfma_CollectImportBll {
	Cfma_CollectImportDal dal;

	public Cfma_CollectImportBll() {
		dal = new Cfma_CollectImportDal();
	}

	// 查询导入的收款纪录
	public List<CoFinanceCollectImportModel> getCollectImportList() {
		return dal.getCollectImportList();
	}

	// 查询导入的收款纪录
	public CoFinanceCollectImportModel getCollectImportModel(int cfci_id) {
		return dal.getCollectImportModel(cfci_id);
	}

	// 导入收款数据
	public String[] addImportToCompany(
			List<CoFinanceCollectImportModel> ciList, String username) {
		int sum = ciList.size();
		int success = 0;
		int up = 0;
		String fail = "";
		String company = "";
		String[] message = new String[3];
		try {
			for (CoFinanceCollectImportModel m : ciList) {
				try {
					up = dal.addImportToCompany(m, username);
					if (up == 1) {
						success = success + 1;
					} else {
						if ("".equals(m.getCfci_transactionNo())
								|| m.getCfci_transactionNo() == null) {
							company = company + m.getCfci_company() + ";";
						} else {
							fail = fail + m.getCfci_transactionNo() + ";";
						}
					}
				} catch (Exception e) {
					fail = fail + m.getCfci_transactionNo() + ";";
					e.printStackTrace();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		if (success == sum) {
			message[0] = "1";
			message[1] = "共导入数据" + sum + "条，全部成功。";
		} else {
			message[0] = "0";
			message[1] = "共导入数据" + sum + "条，其中有" + (sum - success)
					+ "条数据导入失败。请复制以下信息!";
			if (!"".equals(fail)) {
				message[1] = message[1] + "失败流水号:" + fail;
			}
			if (!"".equals(company)) {
				message[1] = message[1] + "对方账户名称:" + company;
			}
			// 写入出错情况至DB
			dal.addImportErr(username, message[1]);
		}
		return message;
	}

	// 导入收款数据入账
	public int addImportRecorded(CoFinanceCollectImportModel m, String username) {
		return dal.addImportRecorded(m, username);
	}

	// 查询导入收款的出错情况
	public List<CoFinanceCollectImportErrModel> getErrList() {
		return dal.getErrList();
	}

}
