package dal.CoLatencyClient;

import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.zkoss.zul.ListModelList;

import Conn.dbconn;
import Model.CoFinanceVATModel;
import Model.SystLogModel;

/**
 * 
 * @author 苏宏远
 * 
 */
public class CoFinanceVatDal {
	private dbconn conn = new dbconn();

	// 初始化公司基本信息修改页面
	@SuppressWarnings("static-access")
	public List<CoFinanceVATModel> getCoFinanceVAT(Integer cid)
			throws SQLException {
		List<CoFinanceVATModel> list = new ArrayList<CoFinanceVATModel>();
		String sql = "select cfva_id,cid,cfva_company,cfva_reg_add,cfva_title,cfva_tel,cfva_taxpayers,cfva_number1,cfva_number2,cfva_bank_acc,cfva_bank,cfva_contact,cfva_contact_tel,cfva_vat_add,cfva_remark,cfva_addname,cfva_addtime,"
				+ "cfva_only,cfva_simple,cfva_sp,cfva_confirm from CoFinanceVAT where 1=1 ";
		if (!cid.equals("") && cid != null) {
			list = conn.find(sql + " and cid=" + cid, CoFinanceVATModel.class,
					null);
		}
		return list;
	}

	// 转公司管理基本信息添加
	public int CoFinanceVatAdd(CoFinanceVATModel m) {
		Integer i = 0;
		dbconn db = new dbconn();
		Integer only = 0;
		Integer simple = 0;
		if (m.getCfva_only() != null) {
			only = m.getCfva_only() ? 1 : 0;
		}
		if (m.getCfva_simple() != null) {
			simple = m.getCfva_simple() ? 1 : 0;
		}
		try {
			i = Integer
					.valueOf(db
							.callWithReturn(
									"{?=call CoFinanceVAT_Add_shy(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}",
									Types.INTEGER, m.getCid(),
									m.getCfva_company(), m.getCfva_reg_add(),
									m.getCfva_title(), m.getCfva_tel(),
									m.getCfva_taxpayers(), m.getCfva_number1(),
									m.getCfva_number2(), m.getCfva_bank_acc(),
									m.getCfva_bank(), m.getCfva_contact(),
									m.getCfva_contact_tel(),
									m.getCfva_vat_add(), m.getCfva_remark(),
									only, simple, m.getCfva_addname(),
									m.getCfva_addtime()).toString());
		} catch (NumberFormatException | SQLException e) {
			return -1;
		}
		return i;
	}

	// 转公司管理基本信息修改
	public int CoFinanceVatUpd(CoFinanceVATModel m) {
		Integer i = 0;
		dbconn db = new dbconn();
		try {
			i = Integer
					.valueOf(db
							.callWithReturn(
									"{?=call CoFinanceVAT_update_p_shy(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}",
									Types.INTEGER, m.getCid(),
									m.getCfva_company(), m.getCfva_reg_add(),
									m.getCfva_title(), m.getCfva_tel(),
									m.getCfva_taxpayers(), m.getCfva_number1(),
									m.getCfva_number2(), m.getCfva_bank_acc(),
									m.getCfva_bank(), m.getCfva_contact(),
									m.getCfva_contact_tel(),
									m.getCfva_vat_add(), m.getCfva_remark(),
									(m.getCfva_only() ? 1 : 0),
									(m.getCfva_simple() ? 1 : 0),
									(m.getCfva_sp() ? 1 : 0),
									(m.getCfva_confirm() ? 1 : 0),
									m.getCfva_addname(), m.getCfva_addtime())
							.toString());
		} catch (NumberFormatException | SQLException e) {
			return -1;
		}
		return i;
	}

	// 读取发票修改日志
	public List<SystLogModel> getlog(Integer cid) {
		dbconn db = new dbconn();
		String sql = "select convert(varchar(10),cid)cid,content,addname,addtime from systLog"
				+ " where Class='发票信息' and cid=?" +
				" order by addtime desc";
		List<SystLogModel> list = new ListModelList<>();
		try {
			list = db.find(sql, SystLogModel.class, null, cid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	// 读取发票修改日志
	public List<SystLogModel> getlogById(Integer id) {
		dbconn db = new dbconn();
		String sql = "select cid,content,addname,addtime from systLog"
				+ " where Class='发票信息' and tid=?";
		List<SystLogModel> list = new ListModelList<>();
		try {
			list = db.find(sql, SystLogModel.class, null, id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
}
