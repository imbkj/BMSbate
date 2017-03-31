package Controller.CoAgencies;

import Model.CoAgencyCompactModel;
import Util.UserInfo;
import bll.CoAgencies.CoAg_CompactOperateBll;
import bll.CoAgencies.CoAg_CompactSelectBll;

public class MakeCompactBean {
	private CoAg_CompactSelectBll bll = new CoAg_CompactSelectBll();
	private CoAgencyCompactModel model = new CoAgencyCompactModel();

	public CoAgencyCompactModel getModel(Integer coct_id) {
		this.model = bll.getCoAgencyCompactModel(coct_id);
		return model;
	}

	public void setModel(CoAgencyCompactModel model) {
		this.model = model;
	}

	// 保存合同文件名称
	public String[] updateComapctFilename(CoAgencyCompactModel model,
			String username) {
		model.setCoct_filename(model.getCoct_filename());
		model.setCoct_modname(username);
		CoAg_CompactOperateBll obll = new CoAg_CompactOperateBll();
		String[] str = obll.updateComapctFilename(model);
		return str;
	}

	// 保存合同文件名称
	public Integer SaveComapctFilename(CoAgencyCompactModel model,
			String username, String newfilename) {
		model.setCoct_filename(newfilename);
		model.setCoct_modname(username);
		CoAg_CompactOperateBll obll = new CoAg_CompactOperateBll();
		Integer k = obll.SaveComapctFilename(model);
		return k;
	}

	// 合同审核
	public String[] AuditComapct(CoAgencyCompactModel model, String username) {
		model.setCoct_modname(username);
		CoAg_CompactOperateBll obll = new CoAg_CompactOperateBll();
		String[] str = obll.AuditComapct(model);
		return str;
	}

	// 合同审核
	public String[] BackComapct(CoAgencyCompactModel model, String username) {
		model.setCoct_modname(username);
		CoAg_CompactOperateBll obll = new CoAg_CompactOperateBll();
		String[] str = obll.AuditComapct(model);
		return str;
	}

	// 合同退回
	public String[] CoAg_CompactBack(CoAgencyCompactModel m, String username) {
		model.setCoct_modname(username);
		CoAg_CompactOperateBll obll = new CoAg_CompactOperateBll();
		String[] str = obll.CoAg_CompactBack(model, username);
		return str;
	}
}
