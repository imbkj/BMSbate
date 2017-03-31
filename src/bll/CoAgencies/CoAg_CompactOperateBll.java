package bll.CoAgencies;

import dal.CoAgencies.CoAg_CompactOperateDal;
import impl.WorkflowCore.WfOperateImpl;

import service.WorkflowCore.WfBusinessService;
import service.WorkflowCore.WfOperateService;
import Model.CoAgencyCompactModel;
import Util.UserInfo;

public class CoAg_CompactOperateBll {
	private CoAg_CompactOperateDal dal = new CoAg_CompactOperateDal();

	// 委托机构合同新增
	public String[] CoAg_CompactAdd(CoAgencyCompactModel m, String type) {
		String tasktitle = "委托机构合同新增";
		if (type.equals("2")) {
			tasktitle = "受托机构合同新增";
		}
		Object[] obj = { "1", m };
		WfBusinessService wfbs = new CoAgencyCompactImpl();
		WfOperateService wfs = new WfOperateImpl(wfbs);
		String[] str = wfs.AddTaskToNext(obj, tasktitle, m.getCoct_coagname()
				+ "合同新增", 107, UserInfo.getUsername(), "", m.getCoct_coagid(),
				"");
		return str;
	}

	// 合同流程更新
	public String[] CoAg_CompactEdit(CoAgencyCompactModel m,
			String operateinfo, String username) {
		Object[] obj = { "2", m, operateinfo };
		String[] str = new String[5];
		CoAgencyCompactImpl wfbs = new CoAgencyCompactImpl();
		WfOperateService wfs = new WfOperateImpl(wfbs);
		str = wfs.PassToNext(obj, m.getCoct_tarpid(), username, "",
				m.getCoct_coagid(), "");
		return str;
	}

	// 合同退回
	public String[] CoAg_CompactBack(CoAgencyCompactModel m,String username) {
		Object[] obj = { "2", m };
		String[] str = new String[5];
		CoAgencyCompactImpl wfbs = new CoAgencyCompactImpl();
		WfOperateService wfs = new WfOperateImpl(wfbs);
		str = wfs.ReturnToPrev(obj, m.getCoct_tarpid(), username, "");
		return str;
	}

	// 新增合同和机构城市关系信息
	public Integer CoAgencyCompactCityAdd(Integer ctcy_coct_id,
			Integer ctcy_cabc_id, String addname) {
		return dal.CoAgencyCompactCityAdd(ctcy_coct_id, ctcy_cabc_id, addname);
	}

	// 合同制作
	public String[] updateComapctFilename(CoAgencyCompactModel model) {
		int k = 0;
		String[] str = new String[5];
		//k = dal.updateComapctFilename(model);
		//if (k > 0) {
			String operateinfo = "制作合同";
			str = CoAg_CompactEdit(model, operateinfo, model.getCoct_modname());
		//}
		return str;
	}
	
	// 合同制作
		public Integer SaveComapctFilename(CoAgencyCompactModel model) {
			int k = 0;
			String[] str = new String[5];
			k = dal.updateComapctFilename(model);
			return k;
		}

	// 审核合同
	public String[] AuditComapct(CoAgencyCompactModel model) {
		int k = 0;
		String[] str = new String[5];
		k = dal.AuditComapct(model);
		if (k > 0) {
			String operateinfo = "审核合同";
			str = CoAg_CompactEdit(model, operateinfo, model.getCoct_modname());
		}
		return str;
	}

	// 合同签回
	public String[] SignBackComapct(CoAgencyCompactModel model) {
		int k = 0;
		String[] str = new String[5];
		k = dal.SignBackComapct(model);
		if (k > 0) {
			String operateinfo = "签回合同";
			str = CoAg_CompactEdit(model, operateinfo, UserInfo.getUsername());
		}
		return str;
	}

	// 合同归档
	public String[] ArchiveComapct(CoAgencyCompactModel model) {
		int k = 0;
		String[] str = new String[5];
		k = dal.ArchiveComapct(model);
		if (k > 0) {
			String operateinfo = "合同归档";
			str = CoAg_CompactEdit(model, operateinfo, UserInfo.getUsername());
		}
		return str;
	}
}
