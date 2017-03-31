package bll.EmCommissionOut;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.zkoss.zul.ListModelList;

import Model.EmCommissionOutZYTModel;
import Model.CoAgencyBaseCityRelViewModel;
import Model.CoAgencyLinkmanModel;
import Model.EmCommissionOutChangeModel;
import Model.EmCommissionOutFeeDetailChangeModel;
import Model.EmCommissionOutFeeDetailHistoryModel;
import Model.EmCommissionOutFeeDetailModel;
import Model.EmCommissionOutHistoryModel;
import Model.EmCommissionOutModel;
import Model.EmCommissionOutPayUpdateCRTModel;
import Model.EmCommissionOutPayUpdateFeeDetailModel;
import Model.EmCommissionOutPayUpdateModel;
import Model.EmCommissionOutStandardModel;
import Model.PubStateModel;
import Model.PubStateRelModel;
import Model.SocialInsuranceClassModel;
import Util.RegexUtil;
import dal.EmCommercialInsurance.CI_Insurant_ListDal;
import dal.EmCommissionOut.EmCommissionOut_ListDal;

public class EmCommissionOutListBll {
	EmCommissionOut_ListDal dal = new EmCommissionOut_ListDal();

	// 获取空社保、公积金详情列表
	public List<EmCommissionOutFeeDetailChangeModel> getNullSoClassList(
			Integer soin_id) {
		List<SocialInsuranceClassModel> list = new ListModelList<>();
		List<EmCommissionOutFeeDetailChangeModel> list1 = new ListModelList<>();
		list = dal.getNullSoClassList(soin_id);

		// 企业、个人合并为一条数据
		for (int i = 0; i < list.size(); i++) {
			EmCommissionOutFeeDetailChangeModel m2 = new EmCommissionOutFeeDetailChangeModel();
			SocialInsuranceClassModel m = list.get(i);
			if (i + 1 < list.size()) {
				SocialInsuranceClassModel m1 = list.get(i + 1);
				if (m.getSicl_name().equals(m1.getSicl_name())) {
					i++;
				}
			}
			m2.setEofc_name(m.getSicl_name());
			m2.setEofc_sicl_id(m.getSicl_id());
			m2.setSicl_class(m.getSicl_class());
			m2.setEofc_em_base(new BigDecimal(0));
			m2.setEofc_co_base(new BigDecimal(0));
			m2.setEofc_em_sum(new BigDecimal(0));
			m2.setEofc_co_sum(new BigDecimal(0));
			m2.setEofc_month_sum(new BigDecimal(0));
			m2.setEofc_state(1);

			list1.add(m2);
		}
		return list1;
	}

	/**
	 * 比例处理
	 * 
	 */
	public List<String> CppHandle(String cpp) {
		List<String> list = new ListModelList<>();
		if (cpp != null && !cpp.isEmpty()) {
			if (RegexUtil.isExists(",", cpp)) {
				String[] strings = cpp.split(",");
				for (String str : strings) {
					list.add(str);
				}
			} else if (RegexUtil.isExists("-", cpp)) {
				String[] strs = cpp.split("-");
				Integer step;
				String[] strs1 = strs[0].split("\\.");
				String[] strs2 = strs[1].split("\\.");
				if (strs1[1].length() > 1 || strs2[1].length() > 1) {
					step = 1;
				} else {
					step = 10;
				}
				for (Double i = Double.parseDouble(strs[0]) * 100; i <= Double
						.parseDouble(strs[1]) * 100; i = i + step) {
					list.add((i / 100) + "");
				}
			} else {
				list.add(cpp);
			}
		} else {
			list.add("");
		}
		return list;
	}

	// 根据年调获取员工列表委托出在册数据

	public List<EmCommissionOutModel> getEmCommOutListnt(String str) {
		return dal.getEmCommOutListnt(str);
	}
	
	// 根据年调获取员工列表委托出在册数据

	public List<EmCommissionOutModel> getEmCommOutListntzd(String str) {
		return dal.getEmCommOutListntzd();
	}


	// 获取该员工基本信息
	public EmCommissionOutChangeModel getEmbase(Integer gid) {
		return dal.getEmbase(gid);
	}

	// 获取该公司标准信息
	public List<EmCommissionOutStandardModel> getStardList(Integer cid,
			Integer gid) throws SQLException {
		return dal.getStardList(cid, gid);
	}

	// 获取该公司标准信息
	public List<EmCommissionOutStandardModel> getStardListed(Integer cid,
			Integer gid) throws SQLException {
		return dal.getStardListedit(cid, gid);
	}

	// 获取该公司标准信息
	public List<EmCommissionOutStandardModel> getStardListbyfeeid(
			Integer wtot_feeid) throws SQLException {
		return dal.getStardListbyfeeid(wtot_feeid);
	}

	// 获取服务费
	public List<EmCommissionOutStandardModel> getfuwu_fee(String city,
			String fee_title) throws SQLException {
		return dal.getfuwu_fee(city, fee_title);
	}

	// 获取城市
	public List<EmCommissionOutChangeModel> getCityList(Integer cid)
			throws SQLException {
		return dal.getCityList(cid);
	}

	// 获取委托标准详情
	public EmCommissionOutStandardModel getStardInfo(Integer ecos_id)
			throws SQLException {
		return dal.getStardInfo(ecos_id);
	}

	// 获取该标准信息
	public List<EmCommissionOutChangeModel> getTitleList(Integer ecos_id)
			throws SQLException {
		return dal.getTitleList(ecos_id);
	}

	// 获取服务费
	public List<EmCommissionOutStandardModel> getfuwulist(Integer cid,
			Integer title) throws SQLException {
		return dal.getfuwulist(cid, title);
	}

	// 获取服务费 赵敏捷修改
	public List<EmCommissionOutStandardModel> getfuwulist(Integer wtot_feeid)
			throws SQLException {
		return dal.getfuwulist(wtot_feeid);
	}

	// 获取档案费
	public List<EmCommissionOutStandardModel> getfilelist(Integer gid,
			Integer cabc_id) throws SQLException {
		return dal.getfilelist(gid, cabc_id);
	}

	// 获取福利列表
	public List<EmCommissionOutFeeDetailChangeModel> getflList(Integer gid,
			String city, String coab_name) {
		List<EmCommissionOutFeeDetailChangeModel> list = new ArrayList<>();
		list = dal.getflList(gid, city, coab_name);
		for (EmCommissionOutFeeDetailChangeModel m : list) {
			m.setEofc_em_base(new BigDecimal(0));
			m.setEofc_co_base(new BigDecimal(0));
		}
		return list;
	}

	// 获取福利列表
	public List<EmCommissionOutFeeDetailModel> getflList1(Integer gid,
			String city, String coab_name) {
		List<EmCommissionOutFeeDetailChangeModel> list = new ArrayList<>();
		list = dal.getflList(gid, city, coab_name);
		List<EmCommissionOutFeeDetailModel> list1 = new ArrayList<>();
		for (EmCommissionOutFeeDetailChangeModel m : list) {
			EmCommissionOutFeeDetailModel m1 = new EmCommissionOutFeeDetailModel();
			m1.setEofd_em_base(new BigDecimal(0));
			m1.setEofd_co_base(new BigDecimal(0));
			m1.setEofd_name(m.getEofc_name());
			m1.setEofd_content(m.getEofc_content());
			m1.setEofd_month_sum(m.getEofc_month_sum());
			m1.setSicl_class(m.getSicl_class());
			m1.setEofd_ecop_id(m.getEofc_ecop_id());

			list1.add(m1);
		}
		return list1;
	}

	// 获取excel数据
	public List<EmCommissionOutZYTModel> ci_excel(String id)
			throws SQLException {
		List<EmCommissionOutZYTModel> list = dal.getci_excel(id);
		return list;
	}

	// 获取excel数据
	public List<EmCommissionOutZYTModel> ci_exceldim(String id)
			throws SQLException {
		List<EmCommissionOutZYTModel> list = dal.getci_exceldim(id);
		return list;
	}

	// 获取状态列表
	public List<PubStateModel> getStateList(String str) {
		return dal.getStateList(str);
	}

	// 获取委托变更详情
	public EmCommissionOutChangeModel getEmCommOutChangeInfo(Integer ecoc_id,
			String str) {
		return dal.getEmCommOutChangeInfo(ecoc_id, str);
	}

	// 获取委托变更列表
	public List<EmCommissionOutChangeModel> getEmCommOutChangeList(String str) {
		return dal.getEmCommOutChangeList(str);
	}

	// 获取委托变更列表
	public List<EmCommissionOutChangeModel> getEmCommOutChangeList2(String str) {
		return dal.getEmCommOutChangeList2(str);
	}

	// 获取委托变更列表（退回）
	public List<EmCommissionOutChangeModel> getEmCommOutChangeOutList(String str) {
		return dal.getEmCommOutChangeOutList(str);
	}

	// 获取委托服务项目费用列表
	public List<EmCommissionOutFeeDetailModel> getFeeDetailFwList(String str) {
		return dal.getFeeDetailFwList(str);
	}

	// 获取委托服务项目费用列表
	public List<EmCommissionOutFeeDetailModel> getFeeDetailFwListall(String str) {
		return dal.getFeeDetailFwListall(str);
	}

	public List<EmCommissionOutFeeDetailChangeModel> getFeeDetailChangeFwList(
			String str) {
		return dal.getFeeDetailChangeFwList(str);
	}

	// 获取委托福利项目费用列表
	public List<EmCommissionOutFeeDetailModel> getFeeDetailFlList(String str) {
		return dal.getFeeDetailFlList(str);
	}

	public List<EmCommissionOutFeeDetailChangeModel> getFeeDetailChangeFlList(
			String str) {
		return dal.getFeeDetailChangeFlList(str);
	}

	// 通过时间获取所跨越的算法列表
	public List<EmCommissionOutChangeModel> getSoInAl(Integer soin_id,
			String date1, String date2) {
		List<EmCommissionOutChangeModel> list = new ArrayList<>();
		list = dal.getSoInAl(soin_id, date1, date2);
		return list;
	}

	// 获取委托地列表
	public List<CoAgencyBaseCityRelViewModel> getCityList(String typ,
			String typ2) {
		if (!typ.equals("")) {
			typ = " and ecoc_addtype='" + typ + "'";
		}
		if (!typ2.equals("")) {
			if (typ2.equals("未确认")) {
				typ2 = " and ecoc_state=1";
			}
			if (typ2.equals("一次确认")) {
				typ2 = " and ecoc_state=2";
			}
			if (typ2.equals("二次确认")) {
				typ2 = " and ecoc_state=3";
			}
		}
		return dal.getCityList(typ, typ2);
	}

	// 获取委托机构列表
	public List<CoAgencyBaseCityRelViewModel> getCoagencyList() {
		return dal.getCoagencyList();
	}

	// 获取客服列表
	public List<EmCommissionOutChangeModel> getClientList() {
		return dal.getClientList();
	}

	// 获取添加时间—年
	public List<EmCommissionOutChangeModel> getAddtimeyList() {
		return dal.getAddtimeyList(); 
	}

	// 获取添加时间—月
	public List<EmCommissionOutChangeModel> getAddtimemList() {
		return dal.getAddtimemList();
	}

	// 获取添加时间—日
	public List<EmCommissionOutChangeModel> getAddtimedList() {
		return dal.getAddtimedList();
	}

	// 获取项目费用明细列表
	public List<EmCommissionOutFeeDetailChangeModel> getFeeDetailChangeList(
			String str) {
		return dal.getFeeDetailChangeList(str);
	}

	// 获取联系人信息
	public List<CoAgencyLinkmanModel> getLinkManList(Integer cabc_id) {
		return dal.getLinkManList(cabc_id);
	}

	// 新增检查
	public boolean addCheck(Integer gid, Integer ecos_id) {
		return dal.addCheck(gid, ecos_id);
	}

	// 获取历史记录
	public List<PubStateRelModel> getHosList(Integer daid, String str) {
		return dal.getHosList(daid, str);
	}

	// 获取计数列表
	public List<EmCommissionOutChangeModel> getCountList() {
		return dal.getCountList();
	}

	// 获取委托单在册列表
	public List<EmCommissionOutModel> getEmCommOutList(String str) {
		return dal.getEmCommOutList(str);
	}

	// 获取委托外地变更列表

	public List<EmCommissionOutChangeModel> getemcommoutchangelist(String str) {
		return dal.getemcommoutchangelist(str);
	}

	// 获取委托单在册详情
	public EmCommissionOutModel getEmCommOutInfo(Integer ecou_id) {
		return dal.getEmCommOutInfo(ecou_id);
	}

	// 根据ecou_id获取委托单详情
	public EmCommissionOutModel getEmCommOutInfoByEcouid(Integer ecou_id,
			String str) {
		return dal.getEmCommOutInfoByEcouid(ecou_id, str);
	}

	// 获取在册费用项目列表
	public List<EmCommissionOutFeeDetailModel> getFeeDetailList(String str) {
		return dal.getFeeDetailList(str);
	}

	// 获取在册费用项目列表:不关注状态
	public List<EmCommissionOutFeeDetailModel> getFeeDetailListanyway(String str) {
		return dal.getFeeDetailListanyway(str);
	}

	// 获取所有有委托机构的城市
	public List<CoAgencyBaseCityRelViewModel> getCoCityList() {
		return dal.getCoCityList();
	}

	// 获取所有委托机构列表
	public List<CoAgencyBaseCityRelViewModel> getCoagList() {
		return dal.getCoagList();
	}

	// 获取变更表合同标准列表
	public List<String> getStardList() {
		return dal.getStardList();
	}

	// 获取导入系统字段
	public List<EmCommissionOutPayUpdateCRTModel> getFieldList(String str) {
		return dal.getFieldList(str);
	}

	// 获取模板列表
	public List<EmCommissionOutPayUpdateCRTModel> getEmOutPayUpdateT(String str) {
		return dal.getEmOutPayUpdateT(str);
	}

	// 获取模板详情列表
	public List<EmCommissionOutPayUpdateCRTModel> getEmOutPayUpdateC(String str) {
		return dal.getEmOutPayUpdateC(str);
	}

	// 获取外地excel账单数据列表
	public List<EmCommissionOutPayUpdateModel> getEmOutPayUpdateList(String str) {
		return dal.getEmOutPayUpdateList(str);
	}

	// 获取外地excel账单费用详情列表
	public List<EmCommissionOutPayUpdateFeeDetailModel> getEmOutPayUpdateFeeDetailList(
			String str) {
		return dal.getEmOutPayUpdateFeeDetailList(str);
	}

	// 获取委托外地账单表的字段列表
	public List<EmCommissionOutPayUpdateCRTModel> getTableFieldList(
			String tableName, Integer ecut_id) {
		return dal.getTableFieldList(tableName, ecut_id);
	}

	public List<EmCommissionOutPayUpdateCRTModel> getTableFieldList(
			String tableName) {
		return dal.getTableFieldList(tableName);
	}

	public List<EmCommissionOutPayUpdateCRTModel> getTableFieldList1(
			String tableName, Integer ecut_id) {
		return dal.getTableFieldList1(tableName, ecut_id);
	}

	// 获取外地账单中委托城市的列表
	public List<CoAgencyBaseCityRelViewModel> getPayUpdateCityList() {
		return dal.getPayUpdateCityList();
	}

	// 获取外地账单中委托机构的列表
	public List<CoAgencyBaseCityRelViewModel> getPayUpdateCoagencyList() {
		return dal.getPayUpdateCoagencyList();
	}

	// 获取外地账单所属月份列表
	public List<String> getPayUpdateOwnmonthList() {
		return dal.getPayUpdateOwnmonthList();
	}

	// 委托对账 - 委托机构列表
	public List<EmCommissionOutHistoryModel> getEmOutCoabCompareList(
			String ownmonth, String str) {
		return dal.getEmOutCoabCompareList(ownmonth, str);
	}

	// 委托对账 - 公司列表
	public List<EmCommissionOutHistoryModel> getEmOutCompanyCompareList(
			String ownmonth, Integer cabc_id, String str) {
		return dal.getEmOutCompanyCompareList(ownmonth, cabc_id, str);
	}

	// 委托对账 - 员工列表
	public List<EmCommissionOutHistoryModel> getEmOutEmCompareList(
			String ownmonth, Integer cid, String str) {
		List<EmCommissionOutHistoryModel> list = dal.getEmOutEmCompareList(
				ownmonth, cid, str);
		for (EmCommissionOutHistoryModel m : list) {
			CalcEmDiff(m);
		}
		return list;
	}

	// 年调--获取委托历史数据
	public EmCommissionOutHistoryModel getemouthistoryModel(String ownmonth,
			Integer gid, String str)

	{
		EmCommissionOutHistoryModel model = dal.getEmOutEmHistorymodel(
				ownmonth, gid, str).get(0);

		return model;
	}

	// 年调--获取委托历史数据明细
	public List<EmCommissionOutFeeDetailHistoryModel> getEmOutEmFeeDetailHistoryModel(
			Integer ecoh_id) {
		List<EmCommissionOutFeeDetailHistoryModel> list = dal
				.getEmOutEmFeeDetailHistorydetail(ecoh_id);
		return list;
	}

	// 委托对账 - 项目明细列表
	public List<EmCommissionOutFeeDetailHistoryModel> getEmOutEmFeeDetailCompareList(
			Integer ecoh_id, Integer ecpu_id, Integer efco_id, Integer efch_id) {
		List<EmCommissionOutFeeDetailHistoryModel> list = dal
				.getEmOutEmFeeDetailCompareList(ecoh_id, ecpu_id, efco_id,
						efch_id);
		for (EmCommissionOutFeeDetailHistoryModel m : list) {
			CalcEmFeeDetailDiff(m);
		}
		return list;
	}

	// 计算员工费用的差额
	public void CalcEmDiff(EmCommissionOutHistoryModel m) {
		if (m.getEcoh_sb_sum() != null) {
			if (m.getEfco_ecou_sb_sum() != null) {
				m.setYf_ys_sbsum_diff(m.getEcoh_sb_sum().subtract(
						m.getEfco_ecou_sb_sum()));
			}
			if (m.getEcpu_sb_total() != null) {
				m.setYf_sf_sbsum_diff(m.getEcoh_sb_sum().subtract(
						m.getEcpu_sb_total()));
			}
		}

		if (m.getEcoh_gjj_sum() != null) {
			if (m.getEfco_ecou_gjj_sum() != null) {
				m.setYf_ys_gjjsum_diff(m.getEcoh_gjj_sum().subtract(
						m.getEfco_ecou_gjj_sum()));
			}
			if (m.getEcpu_gjj_total() != null) {
				m.setYf_sf_gjjsum_diff(m.getEcoh_gjj_sum().subtract(
						m.getEcpu_gjj_total()));
			}
		}

		if (m.getEcoh_welfare_sum() != null) {
			if (m.getEfco_ecou_welfare_sum() != null) {
				m.setYf_ys_welsum_diff(m.getEcoh_welfare_sum().subtract(
						m.getEfco_ecou_welfare_sum()));
			}
			if (m.getEcpu_welfare_total() != null) {
				m.setYf_sf_welsum_diff(m.getEcoh_welfare_sum().subtract(
						m.getEcpu_welfare_total()));
			}
		}

		if (m.getEcoh_sum() != null) {
			if (m.getEfco_receivable() != null) {
				m.setYf_ys_sum_diff(m.getEcoh_sum().subtract(
						m.getEfco_receivable()));
			}
			if (m.getEcpu_total() != null) {
				m.setYf_sf_sum_diff(m.getEcoh_sum().subtract(m.getEcpu_total()));
			}
		}
	}

	// 计算明细费用的差额
	public void CalcEmFeeDetailDiff(EmCommissionOutFeeDetailHistoryModel m) {
		if (m.getEofh_month_sum() != null) {
			if (m.getEfcd_eofd_month_sum() != null) {
				m.setYf_ys_diff(m.getEofh_month_sum().subtract(
						m.getEfcd_eofd_month_sum()));
			}
			if (m.getEpfd_month_sum() != null) {
				m.setYf_sf_diff(m.getEofh_month_sum().subtract(
						m.getEpfd_month_sum()));
			}
		}
	}

	// 获取历史表和外地账单表所有的委托城市
	public List<CoAgencyBaseCityRelViewModel> getEmOutHisPutCityList() {
		return dal.getEmOutHisPutCityList();
	}

	// 获取历史表和外地账单表所有的委托机构
	public List<CoAgencyBaseCityRelViewModel> getEmOutHisPutCoabList() {
		return dal.getEmOutHisPutCoabList();
	}

	// 获取社保字典库类别列表
	public List<SocialInsuranceClassModel> getSoClassList() {
		return dal.getSoClassList();
	}

	// 根据sicl_id获取sicl_name
	public String getSoClassNameBySiclId(Integer sicl_id) {
		List<SocialInsuranceClassModel> list = getSoClassList();
		String sicl_name = "";
		for (SocialInsuranceClassModel m : list) {
			if (m.getSicl_id().equals(sicl_id)) {
				sicl_name = m.getSicl_name();
				break;
			}
		}
		return sicl_name;
	}
}
