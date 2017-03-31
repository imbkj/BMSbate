package bll.EmFinanceManage;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.zkoss.zul.ListModelList;

import dal.CoBase.CoBase_SelectDal;
import dal.SystemControl.SystLogDal;

import Model.CoBaseModel;
import Model.SystLogModel;

public class Finance_CFMLogBll {

	// 查询发票修改日志记录
	public List<SystLogModel> getLogList(String company, String client,
			String developer, Date d1, Date d2) {
		List<SystLogModel> list = new ListModelList<>();
		SystLogDal dal = new SystLogDal();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String date1 = null;
		String date2 = null;
		if (d1 != null) {
			date1 = sdf.format(d1);
		}
		if (d2 != null) {
			date2 = sdf.format(d2);
		}
		list = dal.getLogList(company, client, developer, date1, date2);
		return list;
	}

	// 查询发票修改日志详情
	public List<SystLogModel> getLogInfo(Integer cid, Date d) {
		List<SystLogModel> list = new ListModelList<>();

		return list;
	}

	public List<CoBaseModel> getclient() {
		List<CoBaseModel> list = new ListModelList<>();
		CoBase_SelectDal dal = new CoBase_SelectDal();
		CoBaseModel m = new CoBaseModel();
		list = dal.getCoBaseInfo(m, "coba_client", true, null, "coba_client");
		return list;
	}

	public List<CoBaseModel> getdeveloper() {
		List<CoBaseModel> list = new ListModelList<>();
		CoBase_SelectDal dal = new CoBase_SelectDal();
		CoBaseModel m = new CoBaseModel();
		list = dal.getCoBaseInfo(m, "coba_developer", true, null,
				"coba_developer");
		return list;
	}

	public List<SystLogModel> getModLog(Integer cid, String addtime) {
		SystLogDal dal = new SystLogDal();
		List<SystLogModel> list = dal.getlog(cid, addtime);
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
