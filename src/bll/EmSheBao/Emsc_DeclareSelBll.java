package bll.EmSheBao;

import java.util.List;

import Model.CoCompactModel;
import Model.EmSheBaoChangeModel;
import Model.EmShebaoBJModel;
import Model.EmShebaoChangeForeignerModel;
import Model.EmShebaoChangeMAModel;
import Model.EmShebaoSetupModel;

import dal.EmSheBao.Emsc_DeclareSelDal;

public class Emsc_DeclareSelBll {
	private Emsc_DeclareSelDal dal = new Emsc_DeclareSelDal();

	// 获取社保独立开户的公司数据的数据列表
	public List<CoCompactModel> getSCompany() {
		return dal.getSCompany();
	}

	// 获取补缴的所有所属月份
	public List<String> getBjOwnmonthList() {
		return dal.getBjOwnmonthList();
	}

	// 获取社保变更的所有所属月份
	public List<String> getChangeOwnmonthList() {
		return dal.getChangeOwnmonthList();
	}

	// 获取社保设置
	public EmShebaoSetupModel getSetup() {
		return dal.getSetup();
	}

	// 统计当月补缴数据
	public String[] getBjDataCount(int ownmonth) {
		return dal.getBjDataCount(ownmonth);
	}

	// 根据搜索条件查询补缴数据
	public List<EmShebaoBJModel> getBjList(String where, int ownmonth) {
		return dal.getBjList(where, ownmonth);
	}

	// 获取社保补缴备注信息
	public EmShebaoBJModel getBjFlag(int id) {
		return dal.getBjFlag(id);
	}

	// 根据ID获取社保补缴信息
	public EmShebaoBJModel getBjInfoById(int id) {
		return dal.getBjInfoById(id);
	}

	// 根据ID获取当月所有社保养老补缴信息
	public List<EmShebaoBJModel> getBjAllInfoById(int id, String str) {
		return dal.getBjAllInfoById(id, str);
	}

	// 根据ID获取社保补缴信息(医疗)
	public EmShebaoBJModel getBjJLInfoById(int id) {
		return dal.getBjJLInfoById(id);
	}

	// 根据ID获取社保补缴信息(养老)
	public List<EmShebaoBJModel> getBjInfoByStr(String str) {
		return dal.getBjInfoByStr(str);
	}

	// 根据ID获取社保补缴信息(医疗)
	public List<EmShebaoBJModel> getBjJLInfoByStr(String str) {
		return dal.getBjJLInfoByStr(str);
	}

	// 获取社保变更数据
	public List<EmSheBaoChangeModel> getEmscData(String where) {
		return dal.getEmscData(where);
	}

	// 根据ID获取社保变更信息
	public EmSheBaoChangeModel getEmSbChange(int id) {
		return dal.getEmSbChange(id);
	}

	// 根据ID获取外籍人社保变更信息
	public EmShebaoChangeForeignerModel getForeignerChangeById(int id) {
		return dal.getForeignerChangeById(id);
	}
	
	// 根据ID获取社保生育津贴申请
	public EmShebaoChangeMAModel getMAChangeById(int id) {
		return dal.getMAChangeById(id);
	}

	// 获取社保申报分配信息
	public List<String[]> getAssigenment() {
		return dal.getAssigenment();
	}

	// 获取雇员福利部用户
	public List<String> getGyflbUser() {
		return dal.getGyflbUser();
	}
}
