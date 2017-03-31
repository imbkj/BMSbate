package bll.CoFinanceManage;

import java.util.ArrayList;
import java.util.List;

import Model.CoFinanceMonthlyBillModel;
import Model.CoFinanceMonthlyBillTempletConModel;
import dal.CoFinanceManage.cfma_OperateDal;

public class cfma_OperateBll {
	private cfma_OperateDal dal;

	public cfma_OperateBll() {
		dal = new cfma_OperateDal();
	}

	// 同步台账
	public void synchronousFinance(int cid, int ownmonth) {
		dal.synchronousFinance(cid, ownmonth);
	}

	// 生成账单
	public String[] createBill(String prefix, int cfta_id, int cid,
			int ownmonth, String username,
			List<CoFinanceMonthlyBillTempletConModel> templetList, String remark) {
		String[] message = new String[2];
		try {
			if ("CO".equals(prefix)) {
				if (dal.existsNoBill(cid, ownmonth) == 1) {
					message = createBillByCo(prefix, cfta_id, cid, ownmonth,
							username, remark);
				} else {
					message[0] = "0";
					message[1] = "该公司已无业务可生成账单。";
				}
			} else {
				message = createBillByCon(prefix, cfta_id, cid, ownmonth,
						username, getCheckList(templetList), remark);
			}
		} catch (Exception e) {
			e.printStackTrace();
			message[0] = "2";
			message[1] = "生成账单出错。";
		}
		return message;
	}

	// 按合同生成账单（委托入）
	public String[] createBillByCp(int cid, int ownmonth, int coco_id,
			String username, String remark, boolean ifConfirm) {
		String[] message = new String[2];
		try {
			// 同步台账
			synchronousFinance(cid, ownmonth);
			if (dal.existsNoBillByCp(cid, ownmonth, coco_id) == 1) {
				String str = dal.createBill("CP", coco_id, "", "委托入账单", 0, cid,
						ownmonth, username, remark);
				if (!"0".equals(str)) {
					if (ifConfirm) {
						int i = confirmBill(str, 1, username);
						if (i == 0) {
							message[0] = "1";
							message[1] = "账单已确认。";
						} else {
							message[0] = "0";
							message[1] = "账单已生成，确认账单失败。";
						}
					} else {
						message[0] = "1";
						message[1] = "账单已生成。";
					}
				} else {
					message[0] = "0";
					message[1] = "账单生成失败。";
				}
			} else {
				message[0] = "0";
				message[1] = "未找到该合同的台账数据。";
			}
		} catch (Exception e) {
			e.printStackTrace();
			message[0] = "2";
			message[1] = "生成账单出错。";
		}
		return message;
	}

	// 按公司生成账单
	private String[] createBillByCo(String prefix, int cfta_id, int cid,
			int ownmonth, String username, String remark) {
		String[] message = new String[2];
		try {
			String str = dal.createBill(prefix, 0, "", "公司账单", cfta_id, cid,
					ownmonth, username, remark);
			if (!"0".equals(str)) {
				message[0] = "1";
				message[1] = "账单已生成。";
			} else {
				message[0] = "0";
				message[1] = "账单生成失败。";
			}
		} catch (Exception e) {
			e.printStackTrace();
			message[0] = "2";
			message[1] = "账单生成出错。";
		}
		return message;
	}

	// 根据勾选内容生成账单
	private String[] createBillByCon(String prefix, int cfta_id, int cid,
			int ownmonth, String username,
			List<CoFinanceMonthlyBillTempletConModel> checkList, String remark) {
		String[] message = new String[2];
		try {
			int sum = checkList.size();
			int success = 0;
			if (sum > 0) {
				for (CoFinanceMonthlyBillTempletConModel m : checkList) {
					if (!"0".equals(dal.createBill(prefix, m.getId(),
							m.getName(), getBillName(prefix, m.getName()),
							cfta_id, cid, ownmonth, username, remark)))
						success = success + 1;
				}
				if (success == sum) {
					message[0] = "1";
					message[1] = "账单已生成，共生成" + success + "张账单。";
				} else if (success == 0) {
					message[0] = "0";
					message[1] = "账单生成失败";
				} else {
					message[0] = "1";
					message[1] = "已生成" + success + "张账单，有" + (sum - success)
							+ "张账单生成失败。";
				}
			} else {
				message[0] = "0";
				message[1] = "请选择您需要生成的内容。";
			}
		} catch (Exception e) {
			e.printStackTrace();
			message[0] = "2";
			message[1] = "账单生成出错。";
		}
		return message;
	}

	// 遍历templetList获取勾选项
	private List<CoFinanceMonthlyBillTempletConModel> getCheckList(
			List<CoFinanceMonthlyBillTempletConModel> templetList) {
		List<CoFinanceMonthlyBillTempletConModel> list = new ArrayList<CoFinanceMonthlyBillTempletConModel>();
		for (CoFinanceMonthlyBillTempletConModel m : templetList) {
			if (m.isCheck()) {
				list.add(m);
			}
		}
		return list;
	}

	// 获取账单描述
	private String getBillName(String prefix, String name) {
		String billName = "";
		switch (prefix) {
		case "CP":
			billName = name + "合同账单";
			break;
		case "AD":
			billName = name + "地区账单";
			break;
		case "DP":
			billName = name + "部门账单";
			break;
		case "EM":
			billName = name + "雇员账单";
			break;
		case "SP":
			billName = name + "项目账单";
			break;
		default:
			break;
		}

		return billName;
	}

	// 删除账单
	public String[] delBill(String cfmb_number) {
		String[] message = new String[2];
		try {
			message = checkBillConfirm(cfmb_number);
			if ("1".equals(message[0])) {
				int i = dal.delBill(cfmb_number);
				if (i > 0) {
					message[0] = "1";
					message[1] = "已删除账单";
				} else {
					message[0] = "0";
					message[1] = "删除账单失败";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			message[0] = "2";
			message[1] = "删除账单出错";
		}
		return message;
	}

	// 合并账单
	public String[] mergeBill(List<CoFinanceMonthlyBillModel> mgList,
			int cfta_id, int cid, int ownmonth, String username) {
		String[] message = new String[2];
		try {
			// 新建账单
			String cfmb_number = dal.AddBill("MG", cfta_id, cid, ownmonth,
					username);
			if (!"0".equals(cfmb_number)) {
				for (CoFinanceMonthlyBillModel m : mgList) {
					// 更改需合并台账数据的账单号
					dal.UpBillNumber(cfmb_number, m.getCfmb_number());
				}
				// 新账单应收统计
				dal.UpBillSortAccount(cfmb_number);
				message[0] = "1";
				message[1] = "账单已合并。";
			} else {
				message[0] = "0";
				message[1] = "账单合并失败。";
			}
		} catch (Exception e) {
			e.printStackTrace();
			message[0] = "2";
			message[1] = "账单合并出错。";
		}
		return message;
	}

	// 账单应收确认(type:1.人事确认2.财务确认)
	public int confirmBill(String cfmb_number, int type, String username) {
		return dal.confirmBill(cfmb_number, type, username);
	}

	// 判断账单确认情况
	public String[] checkBillConfirm(String cfmb_number) {
		String[] message = new String[2];
		int[] checkConfirm = dal.checkBillConfirm(cfmb_number);
//		if (checkConfirm[2] == 1) {
//			message[0] = "0";
//			message[1] = "该账单已确认,无法删除。";
//		} else if (checkConfirm[0] == 1) {
//			message[0] = "0";
//			message[1] = "该账单人事应收已确认,无法删除。";
//		} else if (checkConfirm[1] == 1) {
//			message[0] = "0";
//			message[1] = "该账单财务应收已确认,无法删除。";
//		} else {
//			message[0] = "1";
//		}
		
		if (checkConfirm[3] == 1) {
			message[0] = "0";
			message[1] = "该账单已收款,无法删除。";
		}
		else
		{
			message[0] = "1";
		}
		
		return message;
	}

	// 获取用户部门名称
	public String getUserDept(String username) {
		return dal.getUserDept(username);
	}

	// 根据账单号对账单备注
	public Integer billRemarkAdd(String billno, String remark) {
		return dal.billRemarkAdd(billno, remark);
	}
}
