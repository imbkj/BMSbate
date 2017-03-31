/*
 * 创建人：林少斌
 * 创建时间：2013-12-26
 * 用途：社保变更数据查询Bll
 */
package bll.EmSheBao;

import java.util.ArrayList;
import java.util.List;

import dal.LoginDal;
import dal.EmSheBao.EmSheBao_DSelectDal;

import Model.CoCompactModel;
import Model.EmSheBaoChangeModel;
import Model.EmSheBaoFinanceModel;
import Model.EmShebaoBJModel;
import Model.EmShebaoChangeForeignerModel;
import Model.EmShebaoChangeMAModel;
import Model.EmShebaoChangeSZSIModel;
import Model.EmShebaoDeclareOKModel;
import Model.EmShebaoErrModel;
import Model.EmShebaoSZSIFileModel;
import Model.EmShebaoUpdateModel;

public class EmSheBao_DSelectBll {
	private EmSheBao_DSelectDal dal = new EmSheBao_DSelectDal();

	// 获取社保独立开户的公司数据的数据列表
	public List<CoCompactModel> getSCompany() {
		return dal.getSCompany();
	}

	// 社保IT负责人
	public String getSheBaoIT() {
		return dal.getSheBaoIT();
	}

	// 获取台账后档案文件名称
	public String getSZSIFeeFileName(Integer shebaoid) {
		return dal.getSZSIFeeFileName(shebaoid);
	}

	// 获取未申报完成的独立开户公司
	public List<CoCompactModel> getNDSCompany(String ownmonth) {
		return dal.getNDSCompany(ownmonth);
	}

	// 获取未申报完成的独立开户公司(外籍人新参保)
	public List<CoCompactModel> getForNDSCompany(String ownmonth) {
		return dal.getForNDSCompany(ownmonth);
	}

	// 获取未申报完成的独立开户公司(交单变更)
	public List<CoCompactModel> getSZSINDSCompany(String ownmonth) {
		return dal.getSZSINDSCompany(ownmonth);
	}

	// 获取社保变更数据(多表)
	public List<EmSheBaoChangeModel> getEmSCData(String top, String str,
			String order, String bjOwnmonth) {
		return dal.getEmSCData(top, str, order, bjOwnmonth);
	}

	// 获取社保交单变更数据(交单变更 多表)
	public List<EmShebaoChangeSZSIModel> getEmSCSZSIData(String top,
			String str, String order) {
		return dal.getEmSCSZSIData(top, str, order);
	}

	// 获取社保交单变更数据(交单变更 单表)
	public List<EmShebaoChangeSZSIModel> getEmSCSZSIData(String str,
			String order) {
		return dal.getEmSCSZSIData(str, order);
	}

	// 获取社保变更数据(单表)
	public List<EmSheBaoChangeModel> getEmSCData(String str, String order) {
		return dal.getEmSCData(str, order);
	}

	// 根据GID获取员工历史社保表的信息
	public List<EmShebaoUpdateModel> getAllShebaoByGid(int gid) {
		return dal.getAllShebaoByGid(gid);
	}

	// 根据GID获取员工社保所有变更信息
	public List<EmSheBaoChangeModel> getAllChangListByGid(int gid) {
		return dal.getAllChangListByGid(gid);
	}

	// 获取社保外籍人新参保数据(多表)
	public List<EmShebaoChangeForeignerModel> getEmSForCData(String top,
			String str, String order) {
		return dal.getEmSForCData(top, str, order);
	}

	// 获取社保生育津贴申请数据(多表)
	public List<EmShebaoChangeMAModel> getEscmMACData(String top, String str,
			String order) {
		return dal.getEscmMACData(top, str, order);
	}

	// 获取社保外籍人新参保数据(单表)
	public List<EmShebaoChangeForeignerModel> getEmSForCData(String str,
			String order) {
		return dal.getEmSForCData(str, order);
	}

	// 获取社保补交数据(单表)
	public List<EmShebaoBJModel> getEmSBjData(String str, String order) {
		return dal.getEmSBjData(str, order);
	}

	// 社保变更数据统计
	public String[] getChangeDataCount(String ownmonth) {
		return dal.getChangeDataCount(ownmonth);
	}

	// 社保变更数据统计(交单变更)
	public String[] getSZSIChangeDataCount(String ownmonth) {
		return dal.getSZSIChangeDataCount(ownmonth);
	}

	// 社保变更数据统计(外籍人新参保)
	public String[] getForeignerDataCount(String ownmonth) {
		return dal.getForeignerDataCount(ownmonth);
	}

	// 社保生育津贴变更数据统计
	public String[] getEscmMADataCount(String ownmonth) {
		return dal.getEscmMADataCount(ownmonth);
	}

	// 社保变更生成单项模板数据
	public List<EmSheBaoChangeModel> getEmSCExcelData(String filename) {
		return dal.getEmSCExcelData(filename);
	}

	// 社保外籍人新参保批量申报数据(外籍人新参保)
	public List<EmShebaoChangeForeignerModel> getEmForSCExcelData(
			String filename) {
		return dal.getEmForSCExcelData(filename);
	}

	// 社保变更已通过审核上传文件数据
	public List<EmShebaoDeclareOKModel> getEsdoData(String filename) {
		return dal.getEsdoData(filename);
	}

	// 根据已通过审核上传文件数据获取tapr_id
	public int getTapr_id(int esdo_id) {
		return dal.getTapr_id(esdo_id);
	}

	// 获取台账前数据
	public List<EmSheBaoFinanceModel> getSZSIFinanceMonth(String ownmonth) {
		return dal.getSZSIFinanceMonth(ownmonth);
	}

	// 获取独立户台账前数据
	public List<EmSheBaoFinanceModel> getSZSIFinanceSingleMonth(String ownmonth) {
		return dal.getSZSIFinanceSingleMonth(ownmonth);
	}

	// 获取台账后数据
	public List<EmSheBaoFinanceModel> getSZSIFinance(String ownmonth) {
		return dal.getSZSIFinance(ownmonth);
	}

	// 获取独立户台账后数据
	public List<EmSheBaoFinanceModel> getSZSIFinanceSingle(String ownmonth) {
		return dal.getSZSIFinanceSingle(ownmonth);
	}

	// 台账前逻辑检查数据
	public List<EmShebaoErrModel> getShebaoErrMonth(String ownmonth) {
		return dal.getShebaoErrMonth(" and a.ownmonth=" + ownmonth);
	}

	// 台账前数据统计
	public String[] getSZSIMonthCount(String ownmonth) {
		return dal.getSZSIMonthCount(ownmonth);
	}

	// 台账后数据统计
	public String[] getSZSICount(String ownmonth) {
		return dal.getSZSICount(ownmonth);
	}

	// 台账前逻辑检查数据
	public List<EmShebaoErrModel> getShebaoErrMonth() {
		return dal.getShebaoErrMonth("");
	}

	// 台账后逻辑检查数据
	public List<EmShebaoErrModel> getShebaoErr(String ownmonth) {
		return dal.getShebaoErr(" and a.ownmonth=" + ownmonth);
	}

	// 台账后逻辑检查数据
	public List<EmShebaoErrModel> getShebaoErr() {
		return dal.getShebaoErr("");
	}

	// 获取在册表数据
	public EmShebaoUpdateModel getShebaoUpdateInfo(String sql) {
		return dal.getShebaoUpdateInfo(sql);
	}

	// 获取在册备份表数据
	public EmShebaoUpdateModel getShebaoUpdateSimInfo(String sql) {
		return dal.getShebaoUpdateSimInfo(sql);
	}

	// 获取台账前数据
	public EmShebaoUpdateModel getShebaoSZSIMonthInfo(String sql) {
		return dal.getShebaoSZSIMonthInfo(sql);
	}

	// 获取台账后数据
	public EmShebaoUpdateModel getShebaoSZSIInfo(String sql) {
		return dal.getShebaoSZSIInfo(sql);
	}

	// 返回操作类型的文件名称
	public String getFileName(String change) {
		String filename = "";
		switch (change) {
		case "新增":
			filename = "newps";
			break;
		case "调入":
			filename = "movein";
			break;
		case "停交":
			filename = "stop";
			break;
		case "修改工资":
			filename = "salary";
			break;
		case "档案修改":
			filename = "modify";
			break;
		}

		return filename;
	}

	// 社保字段代码转换
	public String dm(String str) {
		String result = "";
		if (str.equals("参加")) {
			result = "1";
		} else {
			result = "0";
		}
		return result;
	}

	public String hj(String str) {
		String result = "";
		switch (str) {
		case "市内城镇":
			result = "1";
			break;
		case "市内农村":
			result = "2";
			break;
		case "市外城镇":
			result = "3";
			break;
		case "市外农村":
			result = "4";
			break;
		}

		return result;
	}

	public String ylt(String str) {
		String result = "";

		switch (str) {
		case "不参加":
			result = "0";
			break;
		case "一档":
			result = "1";
			break;
		case "二档":
			result = "2";
			break;
		case "三档":
			result = "4";
			break;
		}

		return result;
	}

	public String zg(String str) {
		String result = "";

		switch (str) {
		case "全民":
			result = "1";
			break;
		case "合同制":
			result = "2";
			break;
		case "劳务":
			result = "3";
			break;
		}

		return result;
	}

	public String zb(String str) {
		String result = "";

		switch (str) {
		case "干部":
			result = "1";
			break;
		case "工人":
			result = "2";
			break;
		}

		return result;
	}

	public String ls(String str) {
		String result = "";

		switch (str) {
		case "左":
			result = "0";
			break;
		case "右":
			result = "1";
			break;
		}

		return result;
	}

	public String folk(String str) {
		String result = "";
		result = dal.folk(str);
		return result;
	}

	public String sr(String str) {
		String result = "";

		switch (str) {
		case "本人意愿中断就业":
			result = "S317";
			break;
		case "非本人意愿中断就业":
			result = "S318";
			break;
		}

		return result;
	}

	// 获取当前社保月份
	public String getShebaoNowmonth() {
		return dal.getShebaoNowmonth();
	}

	// 检查是否已导入自定单位编号的台账前数据
	public int getFinanceMonthCount(String shebaoid) {
		return dal.getFinanceMonthCount(shebaoid);
	}

	public String getSingle(String shebaoid) {
		String result = "";

		switch (shebaoid) {
		case "167120":
			result = "2";
			break;
		case "391055":
			result = "0";
			break;
		case "464780":
			result = "3";
			break;
		case "464781":
			result = "4";
			break;
		default:
			result = "1";
		}

		return result;
	}

	// 网上申报变动情况
	public List<String> getSChange(String sql) {
		List<String> change = new ArrayList<String>();

		if (!"".equals(sql)) {
			change = dal.getSChange(sql);
		} else {
			change.add("全部");
			change.add("新增");
			change.add("调入");
			change.add("停交");
			change.add("修改工资");
			change.add("档案修改");
			change.add("全部但不含新增");
		}

		return change;
	}

	// 获取loginID
	public Integer getUserIDByname(String username) {
		Integer userID = 0;
		LoginDal loginDal = new LoginDal();
		userID = loginDal.getUserIDByname(username);
		return userID;
	}

	// 获取社保局最新台账数据
	public List<EmShebaoSZSIFileModel> downSZSI(String cid) {
		List<EmShebaoSZSIFileModel> result = new ArrayList<EmShebaoSZSIFileModel>();
		result = dal.downSZSI(cid);
		return result;
	}
}
