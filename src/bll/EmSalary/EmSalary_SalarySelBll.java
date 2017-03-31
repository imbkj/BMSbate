package bll.EmSalary;

import impl.CheckStringImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.zkoss.zhtml.Strong;

import service.CheckStringService;

import dal.PubNationalityDal;
import dal.EmSalary.EmSalary_SalarySelDal;
import dal.EmSalary.ItemFormula_OperateDal;

import Model.CoBaseModel;
import Model.CoSalaryItemFormulaModel;
import Model.EmSalaryBaseAddItemModel;
import Model.EmSalaryBaseAdd_viewModel;
import Model.EmSalaryBaseSel_viewModel;
import Model.EmSalaryDataModel;
import Model.EmSalaryInfoModel;
import Model.EmSalaryPayInfoModel;
import Model.EmbaseModel;
import Model.PubCodeConversionModel;
import Model.PubNationalityModel;
import Util.DateStringChange;
import Util.MonthListUtil;

public class EmSalary_SalarySelBll {
	EmSalary_SalarySelDal dal = new EmSalary_SalarySelDal();

	// 获取工资用途的数据列表
	public List<PubCodeConversionModel> getCodeConversion() {
		return dal.getCodeConversion();
	}

	// 获取员工信息
	public EmSalaryBaseAdd_viewModel getEmSalaryDateAdd(int gid, int cid) {
		return dal.getEmSalaryDateAdd(gid, cid);
	}

	// 获取可批量新增工资的员工信息(既无工资数据并已分配算法的员工)
	public List<EmSalaryDataModel> getEmSalaryDateBase(int cid) {
		return dal.getEmSalaryDateBase(cid);
	}

	// 获取可批量导入工资的员工信息
	public List<EmSalaryDataModel> getEmSalaryBase(int cid) {
		return dal.getEmSalaryBase(cid);
	}

	// 根据工资数据ID获取员工工资信息
	public EmSalaryBaseSel_viewModel getSalaryEmBase(int esda_id) {
		return dal.getSalaryEmBase(esda_id);
	}

	// 获取所属月份列表
	public List<String> getOwnmonth(int cid, int gid, String esin_stopmonth) {
		return dal.getOwnmonth(cid, gid, esin_stopmonth);
	}

	// 获取当前月份的前后3个月份列表
	public List<String> getOwnmonthByNow() {
		List<String> list = new ArrayList<String>();
		try {
			String nowMonth = DateStringChange
					.DatetoSting(new Date(), "yyyyMM");
			String[] fmonth = MonthListUtil
					.getMonthList(true, nowMonth, "f", 2);
			String[] hmonth = MonthListUtil.getMonthList(false, nowMonth, "h",
					2);
			for (String str : fmonth) {
				list.add(0, str);
			}
			for (String str : hmonth) {
				list.add(str);
			}
		} catch (Exception e) {

		}
		return list;

	}

	// 判断所属公司是否存在上月当月下月工资项目组合
	public void existItem(int cid, int ownmonth, String username) {
		try {
			if (!dal.existItem(cid, ownmonth)) {
				AddItemId(cid, ownmonth, username);
			}
			String month = (MonthListUtil.getMonthList(false,
					String.valueOf(ownmonth), "h", 1))[0];
			if (!dal.existItem(cid, Integer.parseInt(month))) {
				AddItemId(cid, Integer.parseInt(month), username);
			}
			month = (MonthListUtil.getMonthList(false,
					String.valueOf(ownmonth), "f", 1))[0];
			if (!dal.existItem(cid, Integer.parseInt(month))) {
				AddItemId(cid, Integer.parseInt(month), username);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 返回指定月份的默认项目ID
	public String AddItemId(int cid, int ownmonth, String username) {
		ItemFormula_OperateDal itemDal = new ItemFormula_OperateDal();
		CoSalaryItemFormulaModel itemModel = new CoSalaryItemFormulaModel();
		String itemid = "";
		try {
			itemModel.setCid(cid);
			itemModel.setOwnmonth(ownmonth);
			itemModel.setCsii_addname(username);
			itemid = itemDal.addSalaryItemId(itemModel)[0];
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
		return itemid;
	}

	// 获取项目信息(csii_ifzero: -1获取所有项目;0获取不清零字段；1获取清零字段;2暂时清零字段)
	public List<EmSalaryBaseAddItemModel> getCSIIInfo(int cid, int ownmonth,
			int csii_ifzero) {
		return dal.getCSIIInfo(cid, ownmonth, csii_ifzero);
	}

	// 获取工资非清零字段修改列表（根据taba_id获取工资信息，批量任务单）
	public List<EmSalaryDataModel> getSalaryDataByTabaIdToNonZero(int taba_id) {
		return dal.getSalaryDataByTabaIdToNonZero(taba_id);
	}

	// 根据Cid及所属月份获取工资信息
	public List<EmSalaryDataModel> getSalaryDataByCidMonth(int cid, int ownmonth) {
		return dal.getSalaryDataByCidMonth(cid, ownmonth);
	}

	// 根据Cid及所属月份获取工资信息
	public List<EmSalaryDataModel> getSalaryDataByCidMonth(int cid,
			int ownmonth, int esda_payment_state) {
		return dal.getSalaryDataByCidMonth(cid, ownmonth, esda_payment_state);
	}

	// 根据taba_id获取工资信息(批量任务单)
	public List<EmSalaryDataModel> getSalaryDataByTabaId(int taba_id) {
		return dal.getSalaryDataByTabaId(taba_id);
	}

	// 根据taba_id获取工资信息(批量任务单待发放)
	public List<EmSalaryDataModel> getSalaryDataByTabaIdToPay(int taba_id) {
		return dal.getSalaryDataByTabaIdToPay(taba_id);
	}

	// 获取工资合计
	public List<EmSalaryDataModel> getSalaryDataSUM(String str, Integer taba_id) {
		return dal.getSalaryDataSUM(str, taba_id);
	}

	// 根据业务esda_id获取taba_id等信息
	public EmSalaryDataModel getEmSalaryTaskInfo(int esda_id) {
		return dal.getEmSalaryTaskInfo(esda_id);
	}

	// 根据Cid及所属月份获取项目信息
	public List<EmSalaryBaseAddItemModel> getItemInfoByCidMonth(int cid,
			int ownmonth) {
		return dal.getItemInfoByCidMonth(cid, ownmonth);
	}

	// 根据Cid及所属月份获取项目信息(不获取实发工资)
	public List<EmSalaryBaseAddItemModel> getItemInfoByCidMonthNoPay(int cid,
			int ownmonth) {
		return dal.getItemInfoByCidMonthNoPay(cid, ownmonth);
	}

	// 根据Cid及所属月份获取项目信息(仅获取可修改的数据)
	public List<EmSalaryBaseAddItemModel> getItemInfoByCidMonthCanEdit(int cid,
			int ownmonth) {
		return dal.getItemInfoByCidMonthCanEdit(cid, ownmonth);
	}

	// 根据Cid及所属月份获取项目信息(获取非清零的数据)
	public List<EmSalaryBaseAddItemModel> getItemInfoByCidMonthNoZero(int cid,
			int ownmonth) {
		return dal.getItemInfoByCidMonthNoZero(cid, ownmonth);
	}

	// 获取工资所属月份列表
	public List<String> getOwnmonth() {
		return dal.getOwnmonth();
	}

	// 根据Cid获取工资所属月份列表
	public List<String> getOwnmonth(int cid) {
		return dal.getOwnmonth(cid);
	}

	// 根据所属月份获取工资信息的公司简称及CID
	public List<String[]> getCompany(int ownmonth) {
		return dal.getCompany(ownmonth);
	}

	public List<EmSalaryDataModel> getSalaryDataIfhold() {
		return dal.getSalaryDataIfhold();
	}

	// 根据Cid获取公司简称
	public String getCoShortName(int cid) {
		return dal.getCoShortName(cid);
	}

	// 查询服务中的所有公司基本信息
	public List<CoBaseModel> getCoBaseAll() {
		return dal.getCoBaseAll();
	}

	// 获取所有公司基本信息
	public List<CoBaseModel> getCoBase() {
		return dal.getCoBase();
	}

	// 获取所有公司基本信息
	public List<CoBaseModel> getCoBase(String top, String str) {
		return dal.getCoBase(top, str);
	}

	// 根据所属月份查询工资表的公司信息
	public List<CoBaseModel> getCoBaseAll(int ownmonth) {
		return dal.getCoBaseAll(ownmonth);
	}

	// 根据Cid获取员工信息
	public List<EmbaseModel> getEmbaseByCid(int cid) {
		return dal.getEmbaseByCid(cid);
	}

	// 根据CID及所属月份获取工资表的员工姓名及用途
	public List<EmSalaryBaseSel_viewModel> getEmbaseByCidOwnmonth(int cid,
			int ownmonth, int createmonth) {
		return dal.getEmbaseByCidOwnmonth(cid, ownmonth, createmonth);
	}

	// 获取需要清零的字段
	public List<String> getIfZero(int cid, int ownmonth) {
		return dal.getIfZero(cid, ownmonth);
	}

	// 根据GID查CID
	public int getCidByGid(int gid) {
		return dal.getCidByGid(gid);
	}

	// 获取财务部用户
	public List<String> getCwUser() {
		return dal.getCwUser();
	}

	// 获取薪酬负责人列表
	public List<String> getGzUser() {
		return dal.getGzUser();
	}

	// 判断员工是否分配算法
	public boolean existCfin(int cid, int gid) {
		return dal.existCfin(cid, gid);
	}

	// 国籍下拉框
	public List<PubNationalityModel> getPubNationalityList(String str) {
		PubNationalityDal pDal = new PubNationalityDal();
		return pDal.getPubNationalityList(str);
	}

	// 获取员工薪酬信息
	public List<EmSalaryInfoModel> getEmSalaryInfo(Integer cid, Integer gid) {
		return dal.getEmSalaryInfo(" AND b.cid=" + cid + " AND b.gid=" + gid);
	}

	// 获取员工薪酬报价单是否含有工资或个税
	public Integer[] getEmSalaryCoGlist(Integer cid, Integer gid) {
		return dal
				.getEmSalaryCoGlist(" AND a.cid=" + cid + " AND a.gid=" + gid);
	}

	// 获取员工薪酬信息(根据esin_id)
	public List<EmSalaryInfoModel> getEmSalaryInfo(Integer esin_id) {
		return dal.getEmSalaryInfo(" AND a.esin_id=" + esin_id);
	}

	// 根据所属月份获取工资发放情况
	public List<EmSalaryPayInfoModel> getEmSalaryPayInfo(int ownmonth) {
		return dal.getEmSalaryPayInfo(ownmonth);
	}

	// 根据公司编号获取工资款收款情况
	public List<EmSalaryPayInfoModel> getEmSalaryGatheringInfo(int cid) {
		return dal.getEmSalaryGatheringInfo(cid);
	}

	// 查找台账应收的cfma_id
	public int getCfmaId(int cid, int gid, int ownmonth) {
		return dal.getCfmaId(cid, gid, ownmonth);
	}

	// 获取公司信息
	public CoBaseModel getCobaseByCid(int cid) {
		return dal.getCobaseByCid(cid);
	}

	// 判断员工是否分配工资或个税项的报价单
	public boolean checkCoOfferList(int gid) {
		return dal.checkCoOfferList(gid);
	}

	// 员工工资数据查询
	public List<EmSalaryDataModel> getSalaryDataCoEm(String s_month,
			String e_month, String em, String co) {
		String str = "";

		str = str + " AND ed.ownmonth between " + s_month + " and " + e_month;

		CheckStringService ch = new CheckStringImpl();
		if (!"".equals(em) && em != null) {
			if (ch.isNum(em)) {
				str = str + " AND em.gid=" + em;
			} else {
				str = str + " AND em.emba_name LIKE '%" + em + "%'";
			}
		}

		if (!"".equals(co) && co != null) {
			if (ch.isNum(co)) {
				str = str + " AND co.cid=" + co;
			} else {
				str = str + " AND co.coba_shortname LIKE '%" + co + "%'";
			}
		}

		return dal.getSalaryDataCoEm(str);
	}

	// 获取个税报价单项目城市
	public List<String> getTaxCity(Integer gid) throws Exception {
		List<String> city = dal.getTaxCity(gid);
		return city;
	}

	// 获取个税报价单项目城市
	public List<String> getTaxCityByCid(Integer cid) throws Exception {
		List<String> city = dal.getTaxCityByCid(cid);
		return city;
	}

	// 获取所有个税报价单项目城市
	public List<String> getTaxCityAll() throws Exception {
		List<String> city = dal.getTaxCityAll();
		return city;
	}

	// 根据月份获取个税申报地
	public String getTaxPlace(Integer gid, Integer cid, Integer ownmonth)
			throws Exception {
		return dal.getTaxPlace(gid, cid, ownmonth);
	}
}
