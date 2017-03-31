package bll.EmCommissionOutNew;

import java.sql.SQLException;
import java.util.List;

import dal.EmCommissionOutNew.EmCommissionOut_ChangeReDal;

import Model.EmCommissionOutChangeModel;
import Model.EmCommissionOutFeeDetailChangeModel;
import Model.EmCommissionOutStandardModel;

public class EmCommissionOut_ChangeReBll {
	EmCommissionOut_ChangeReDal dal = new EmCommissionOut_ChangeReDal();

	// 获取委托主表明细
	public EmCommissionOutChangeModel getwt_list(String ecou_id)
			throws SQLException {
		return dal.getwt_list(ecou_id);
	}

	// 获取委托合同标准
	public List<EmCommissionOutStandardModel> getwtout_stand(String ecou_id)
			throws SQLException {
		List<EmCommissionOutStandardModel> list = dal.getwtout_stand(ecou_id);
		return list;
	}

	// 获取委托字典库标准
	public List<EmCommissionOutStandardModel> getwtout_title(String ecos_id)
			throws SQLException {
		List<EmCommissionOutStandardModel> list = dal.getwtout_title(ecos_id);
		return list;
	}

	// 获取委托费用明细
	public List<EmCommissionOutFeeDetailChangeModel> getfeedetail(
			String ecoc_id, int soin_id, String sb_base, int x_st,
			String house_base, String abase, String aname, String cp,
			String op, String cop, String cur,String sb_pay,String gjj_pay) throws SQLException {
		List<EmCommissionOutFeeDetailChangeModel> list = dal.getfeedetail(
				ecoc_id, soin_id, sb_base, x_st, house_base, abase, aname, cp,
				op, cop, cur,sb_pay,gjj_pay);
		return list;
	}

	// 获取委托费用明细
	public List<EmCommissionOutFeeDetailChangeModel> getfeedetailc(
			String ecoc_id, int soin_id, String sb_base, int x_st,
			String house_base, String abase, String aname) throws SQLException {
		List<EmCommissionOutFeeDetailChangeModel> list = dal.getfeedetailc(
				ecoc_id, soin_id, sb_base, x_st, house_base, abase, aname);
		return list;
	}

	// 显示委托住房企业比例
	public List<EmCommissionOutFeeDetailChangeModel> gethousecp(String soin_id)
			throws SQLException {
		List<EmCommissionOutFeeDetailChangeModel> list = dal
				.gethousecp(soin_id);
		return list;
	}

	// 显示委托住房企业比例
	public List<EmCommissionOutFeeDetailChangeModel> getbchousecp(String soin_id)
			throws SQLException {
		List<EmCommissionOutFeeDetailChangeModel> list = dal
				.getbchousecp(soin_id);
		return list;
	}

	// 获取档案费
	public List<EmCommissionOutStandardModel> getfilelist(Integer gid,
			Integer cabc_id) throws SQLException {
		return dal.getfilelist(gid, cabc_id);
	}

	// 更新委托明细
	public int change_feeadd(Integer ecou_id,
			EmCommissionOutFeeDetailChangeModel wtfeelist,String start_date) throws SQLException {
		return dal.change_feeadd(ecou_id,wtfeelist,start_date);
	}
}
