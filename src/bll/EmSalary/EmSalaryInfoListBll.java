package bll.EmSalary;

import java.sql.SQLException;
import java.util.List;

import Model.CoBaseModel;
import Model.CoFinanceMonthlyBillSortAccountModel;
import Model.EmSalaryBaseAddItemModel;
import Model.EmSalaryDataModel;
import Model.EmSalaryInfoModel;
import Model.LoginModel;
import dal.EmSalary.EmSalaryListInfoDal;

public class EmSalaryInfoListBll {

	EmSalaryListInfoDal emsdal = new EmSalaryListInfoDal();

	// 根据cid获取客服login信息
	public LoginModel getLoginByCid(Integer cid) {
		return emsdal.getLoginByCid(cid);
	}

	// 获取工资数据列表
	public List<EmSalaryDataModel> getsalaryList(String str) {
		return emsdal.getsalaryList(str);
	}

	// 获取工资数据表头
	public List<EmSalaryInfoModel> getcobtList(String str) {
		return emsdal.gecobt(str);
	}

	// 根据Cid及所属月份获取工资信息
	public List<EmSalaryDataModel> getSalaryDataByCidMonth(int d_id)
			throws Exception {
		return emsdal.getSalaryDataByCidMonth(d_id);
	}

	// 根据Cid及所属月份获取工资信息
	public List<EmSalaryDataModel> getSalaryDataByCidMonth(int cid,
			int ownmonth, String wherestr) throws Exception {
		return emsdal.getSalaryDataByCidMonth(cid, ownmonth, wherestr);
	}

	// 查询工资历史数据
	public List<EmSalaryDataModel> getSalaryDataByCidMonth(int cid,
			int startownmonth, int endownmonth) throws Exception {
		return emsdal.getSalaryDataByCidMonth(cid, startownmonth, endownmonth);
	}

	// 根据Cid及所属月份获取项目信息
	public List<EmSalaryBaseAddItemModel> getItemInfoByCidMonth(int cid,
			int ownmonth, int type) {
		return emsdal.getItemInfoByCidMonth(cid, ownmonth, type);
	}

	// 根据Cid及所属月份获取项目信息email
	public List<EmSalaryBaseAddItemModel> getItemInfoByCidMonthforemail(
			int cid, int ownmonth) {
		return emsdal.getItemInfoByCidMonthforemail(cid, ownmonth);
	}

	// 获取工资查询统计数据

	public List<EmSalaryDataModel> getSalaryDataByCidMonth(int cid, int ownmonth)
			throws Exception {
		return emsdal.getSalaryDataByCidMonth(cid, ownmonth);
	}

	// 根据Cid及所属月份获取项目信息
	public List<EmSalaryBaseAddItemModel> getItemInfoByCidMonth(int d_id)
			throws Exception {
		return emsdal.getItemInfoByCidMonth(d_id);
	}

	// 获取员工详细工资数据
	public List<EmSalaryInfoModel> getsalaryinfoList(int str, int type) {
		return emsdal.geemsalaryinfo(str, type);
	}

	// 更新grid显示状态

	public int updatevisbel(int cid, String col, int state, int type)
			throws SQLException {
		return emsdal.updatevisbel(cid, col, state, type);

	}

	// 根据d_id获取cid

	public int getcid(int d_id) throws SQLException {
		return emsdal.getcid(d_id);

	}

	// 所属月份列表
	public List getownmonth(String str) throws SQLException {
		return emsdal.getownmonth(str);

	}

	public List getcobases(int ownmonth) throws SQLException {
		return emsdal.getcobases(ownmonth);

	}

	// check是否完全审核

	public int checkstate(int d_id, int state) {
		int i = 0;

		i = emsdal.checkstate(d_id, state);

		return i;

	}

	// 获取公司当月的财务收款
	public List<CoFinanceMonthlyBillSortAccountModel> getCwReceivable(
			int ownmonth, int cid) {
		return emsdal.getCwReceivable(ownmonth, cid);
	}

	// 获取公司信息
	public CoBaseModel getCobaseByCid(int cid) {
		return emsdal.getCobaseByCid(cid);
	}

	// 获取工资数据和支付模块数据(重发页面使用)
	public List<EmSalaryDataModel> getRepayData(String str) {
		return emsdal.getRepayData(str);
	}
}
