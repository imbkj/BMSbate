package bll.EmSalary;

import java.util.List;
import java.util.Map;
import Model.EmPayModel;
import Model.EmSalaryDataModel;
import Model.EmSalaryHistoryDataModel;
import dal.EmSalary.EmSalary_HisSelDal;

public class EmSalary_HisSelBll {
	private EmSalary_HisSelDal dal;

	public EmSalary_HisSelBll() {
		dal = new EmSalary_HisSelDal();
	}

	// 查询工资历史数据
	public Map<Integer, EmSalaryHistoryDataModel> getHisData(String date,
			int batch) {
		Map<Integer, EmSalaryHistoryDataModel> map = null;
		try {
			// 获取工资历史数据
			map = dal.getHisData(date, batch);
			// 统计每个公司的总计
			dal.getHisDataTotal(map, date, batch);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	// 查询电汇审核工资数据
	public Map<Integer, EmSalaryHistoryDataModel> getTTVData(String date,
			String state, String gzaddname) {
		Map<Integer, EmSalaryHistoryDataModel> map = null;
		try {
			String str = "";
			if (!"".equals(state) && !"null".equals(state)
					&& !"全部".equals(state) && state != null) {
				if ("未审核".equals(state)) {
					str = str + " and co.ttv_state<>co.cou";
				} else if ("已审核".equals(state)) {
					str = str + " and co.ttv_state=co.cou";
				}
			}

			if (!"".equals(gzaddname) && !"null".equals(gzaddname)
					&& !"全部".equals(gzaddname) && gzaddname != null) {
				str = str + " and co.coba_gzaddname='" + gzaddname + "'";
			}

			// 获取工资电汇审核数据
			map = dal.getTTVData(date, str);
			// 统计每个公司的总计
			dal.getTTVDataTotal(map, date, str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	// 个人支付木块模块数据
	public List<EmPayModel> getEmPayList(String date) {
		return dal.getEmPayList(date);
	}

	// 个人支付木块模块总计
	public EmPayModel getEmPayTotal(String date) {
		return dal.getEmPayTotal(date);
	}

	// 更新支付模块数据
	public String[] upEmPay(String date) {
		String[] message = new String[2];
		int result = 0;
		try {
			result = dal.upEmPay(date);

			if (result == 0) {
				message[0] = "1";
				message[1] = "操作成功!";
			} else {
				message[0] = "0";
				message[1] = "操作失败!";
			}
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "操作出错。";
		}
		return message;
	}

	// 历史记录工资加支付模块金额总计
	public EmPayModel getEmGZandPayTotal(String date) {
		return dal.getEmGZandPayTotal(date);
	}

	// 导出历史用友工资+个税金额数据
	public List<EmSalaryDataModel> getHistoryGZUftxt(String date) {
		return dal.getHistoryGZUftxt(date);
	}

	// 导出历史用户个税数据
	public List<EmSalaryDataModel> getHistoryTaxUftxt(String date) {
		return dal.getHistoryTaxUftxt(date);
	}
}
