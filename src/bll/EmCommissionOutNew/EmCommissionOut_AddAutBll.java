package bll.EmCommissionOutNew;

import java.sql.SQLException;
import java.util.List;

import Model.CoAgencyLinkmanModel;
import Model.EmCommissionOutChangeModel;
import Model.EmCommissionOutZYTModel;
import dal.EmCommissionOutNew.EmCommissionOut_AddAutDal;
import Model.EmCommissionOutFeeDetailChangeModel;

public class EmCommissionOut_AddAutBll {
	EmCommissionOut_AddAutDal dal = new EmCommissionOut_AddAutDal();

	// 获取委托费用明细
	public List<EmCommissionOutFeeDetailChangeModel> getfeedetail(String ecoc_id)
			throws SQLException {
		List<EmCommissionOutFeeDetailChangeModel> list = dal
				.getfeedetail(ecoc_id);
		return list;
	}

	// 获取委托福利费用明细
	public List<EmCommissionOutFeeDetailChangeModel> getflfeedetail(
			String ecoc_id) throws SQLException {
		List<EmCommissionOutFeeDetailChangeModel> list = dal
				.getflfeedetail(ecoc_id);
		return list;
	}

	// 获取委托主表明细
	public EmCommissionOutChangeModel getwt_list(String ecoc_id)
			throws SQLException {
		return dal.getwt_list(ecoc_id);
	}

	// 获取委托费用明细
	public List<EmCommissionOutFeeDetailChangeModel> getqdfeedetail(
			String ecoc_id) throws SQLException {
		List<EmCommissionOutFeeDetailChangeModel> list = dal
				.getqdfeedetail(ecoc_id);
		return list;
	}

	// 获取委托福利费用明细
	public List<EmCommissionOutFeeDetailChangeModel> getqdflfeedetail(
			String ecoc_id) throws SQLException {
		List<EmCommissionOutFeeDetailChangeModel> list = dal
				.getqdflfeedetail(ecoc_id);
		return list;
	}

	// 获取委托主表明细
	public EmCommissionOutChangeModel getqdwt_list(String ecoc_id)
			throws SQLException {
		return dal.getqdwt_list(ecoc_id);
	}

	// 获取excel数据
	public List<EmCommissionOutZYTModel> ci_excel(String ecoc_id)
			throws SQLException {
		List<EmCommissionOutZYTModel> list = dal.getci_excel(ecoc_id);
		return list;
	}

	// 获取联系人信息
	public List<CoAgencyLinkmanModel> getLinkManList(String cabc_id) {
		return dal.getLinkManList(cabc_id);
	}

	// 获取项目费用明细列表
	public List<EmCommissionOutFeeDetailChangeModel> getFeeDetailChangeList(
			String str) {
		return dal.getFeeDetailChangeList(str);
	}

	// 获取tapr_id
	public List<EmCommissionOutChangeModel> gettaprid(String ecoc_id)
			throws SQLException {
		return dal.gettaprid(ecoc_id);
	}

	// 获取历史记录
	public List<EmCommissionOutChangeModel> getchange(String ecoc_id)
			throws SQLException {
		List<EmCommissionOutChangeModel> list = dal.getchange(ecoc_id);
		return list;
	}

	// 获取历史记录
	public List<EmCommissionOutChangeModel> getcontent(String ecoc_id)
			throws SQLException {
		List<EmCommissionOutChangeModel> list = dal.getcontent(ecoc_id);
		return list;
	}

	// 添加备注
	public int remark(String ecoc_id, String remark,String addname,int email) throws SQLException {
		return dal.remark(ecoc_id, remark,addname,email);
	}

	// 获取委托备注
	public List<EmCommissionOutChangeModel> wtout_remarklist(String ecoc_id)
			throws SQLException {
		List<EmCommissionOutChangeModel> list = dal.wtout_remarklist(ecoc_id);
		return list;
	}
}
