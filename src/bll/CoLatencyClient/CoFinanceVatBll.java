package bll.CoLatencyClient;

import java.sql.SQLException;
import java.util.List;

import Model.CoFinanceVATModel;
import Model.SystLogModel;
import dal.CoLatencyClient.CoFinanceVatDal;

/**
 * 
 * @author 苏宏远
 * 
 */
public class CoFinanceVatBll {
	CoFinanceVatDal bll = new CoFinanceVatDal();

	// 转公司管理基本信息添加
	public int addCoFinanceVat(CoFinanceVATModel mode) {
		return bll.CoFinanceVatAdd(mode);
	}

	// 转公司管理基本信息修改
	public int updateCoFinanceVat(CoFinanceVATModel mode) {
		return bll.CoFinanceVatUpd(mode);
	}

	// 转公司管理基本信息
	public List<CoFinanceVATModel> getCoFinanceVatDat(Integer cid)
			throws SQLException {
		return bll.getCoFinanceVAT(cid);
	}

	public List<SystLogModel> getModLog(Integer cid) {
		List<SystLogModel> list = bll.getlog(cid);
		String[][] s = { { "cid", "公司编号" }, { "cfva_number", "cfva_number" },
				{ "cfva_company", "公司名称" }, { "cfva_reg_add", "注册地址" },
				{ "cfva_title", "发票抬头" }, { "cfva_tel", "电话" },
				{ "cfva_taxpayers", "增值税一般纳税人" }, { "cfva_number1", "纳税人识别号" },
				{ "cfva_number2", "三证合一号码" }, { "cfva_bank_acc", "银行账号" },
				{ "cfva_bank", "银行" }, { "cfva_contact_tel", "发票联系人电话" },
				{ "cfva_contact", "发票联系人" }, { "cfva_vat_add", "发票接收地址" },
				{ "cfva_remark", "备注" }, { "cfva_addname", "添加人" },
				{ "cfva_addtime", "添加时间" }, { "cfva_only", "专票" },
				{ "cfva_simple", "普票" }, { "cfva_sp", "特殊数据" },
				{ "cfva_confirm", "已确认数据" } };
		for (SystLogModel m : list) {
			for (String[] s1 : s) {
				if (m.getContent().contains(s1[0])) {
					m.setContent(m.getContent().replace(s1[0], s1[1]));
				}
			}
		}
		return list;
	}
}
