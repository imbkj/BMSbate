package bll.EmFinance;

import dal.EmFinance.EmFinance_OperateDal;
import Model.EmFinanceZYTModel;

public class EmFinance_OperateBll {
	private EmFinance_OperateDal dal = new EmFinance_OperateDal();

	// 新增智翼通台帐数据
	public Integer EmFinanceZYTAdd(EmFinanceZYTModel model) {
		return dal.EmFinanceZYTAdd(model);
	}

	// 修改智翼通台帐信息
	public void EmFinanceZYTEdit(Integer ownmonth, String filename) {
		dal.EmFinanceZYTEdit(ownmonth, filename);
	}

	// 根据所属月份和委托地区删除EmFinanceZYT表数据
	public Integer delEmFinanceZYT(Integer ownmonth, String city,
			String filename) {
		return dal.delEmFinanceZYT(ownmonth, city, filename);
	}

	// 调整报价单项目月份
	public String[] changeCoGlist(String cgli_id, String startdate,
			String startdate2, String stopdate, String username) {
		String[] message = new String[5];
		int result = 0;
		try {

			result = dal.changeCoGlist(cgli_id, startdate, startdate2,
					stopdate, username);

			if (result > 0) {
				message[0] = "1";
				message[1] = "操作报价单项目日期调整成功!";
				message[2] = "";
				message[3] = "EmZYT";
				message[4] = "员工报价单项目日期调整";
			} else {
				message[0] = "0";
				message[1] = "操作报价单项目日期调整失败!";
			}
			return message;

		} catch (Exception e) {
			message[0] = "2";
			message[1] = "报价单项目日期调整，操作出错。";
		}
		return message;
	}

	// 调整社保补缴收费月份
	public String[] changeSBBJFeemonth(String id, String feemonth,
			String username) {
		String[] message = new String[5];
		int result = 0;
		try {
			result = dal.changeSBBJFeemonth(id, feemonth, username);

			if (result > 0) {
				message[0] = "1";
				message[1] = "操作社保补缴收费月份调整成功!";
				message[2] = "";
				message[3] = "EmZYT";
				message[4] = "员工社保补缴收费月份调整";
			} else {
				message[0] = "0";
				message[1] = "操作社保补缴收费月份调整失败!";
			}
			return message;

		} catch (Exception e) {
			message[0] = "2";
			message[1] = "社保补缴收费月份调整，操作出错。";
		}
		return message;
	}

	// 调整社保补缴收费月份(医疗)
	public String[] changeSBBJJLFeemonth(String id, String feemonth,
			String username) {
		String[] message = new String[5];
		int result = 0;
		try {
			result = dal.changeSBBJJLFeemonth(id, feemonth, username);

			if (result > 0) {
				message[0] = "1";
				message[1] = "操作社保补缴收费月份调整成功!";
				message[2] = "";
				message[3] = "EmZYT";
				message[4] = "员工社保补缴收费月份调整";
			} else {
				message[0] = "0";
				message[1] = "操作社保补缴收费月份调整失败!";
			}
			return message;

		} catch (Exception e) {
			message[0] = "2";
			message[1] = "社保补缴收费月份调整，操作出错。";
		}
		return message;
	}

	// 调整公积金补缴收费月份
	public String[] changeGJJBJFeemonth(String id, String feemonth,
			String username) {
		String[] message = new String[5];
		int result = 0;
		try {
			result = dal.changeGJJBJFeemonth(id, feemonth, username);

			if (result > 0) {
				message[0] = "1";
				message[1] = "操作公积金补缴收费月份调整成功!";
				message[2] = "";
				message[3] = "EmZYT";
				message[4] = "员工公积金补缴收费月份调整";
			} else {
				message[0] = "0";
				message[1] = "操作公积金补缴收费月份调整失败!";
			}
			return message;

		} catch (Exception e) {
			message[0] = "2";
			message[1] = "公积金补缴收费月份调整，操作出错。";
		}
		return message;
	}

	// 获取公司最新已确认台账月份
	public Integer getFinanceOwnmonth(Integer cid) {
		Integer fm;
		fm = dal.getFinanceOwnmonth(cid);
		return fm;
	}
}
