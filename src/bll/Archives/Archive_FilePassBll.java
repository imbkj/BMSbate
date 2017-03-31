package bll.Archives;

import dal.Archives.EmArchiveDatum_OperateDal;
import impl.WorkflowCore.WfOperateImpl;
import service.WorkflowCore.WfBusinessService;
import service.WorkflowCore.WfOperateService;
import Model.EmArchiveDatumModel;
import Util.UserInfo;

public class Archive_FilePassBll {
	EmArchiveDatum_OperateDal dal = new EmArchiveDatum_OperateDal();

	// 转下一步
	public String[] Archive_FilePassAccepted(EmArchiveDatumModel m, String sql) {
		Integer cid = 0;
		if (m.getCid() != null) {
			cid = m.getCid();
		}
		String[] str = new String[5];
		Object[] obj = { "2", m, "", sql };
		WfBusinessService wfbs = new EmArchiveDatumDataImpl();
		WfOperateService wfs = new WfOperateImpl(wfbs);
		if (m.getEada_tapr_id() != null && !m.getEada_tapr_id().equals("")) {
			str = wfs.PassToNext(obj, m.getEada_tapr_id(),
					UserInfo.getUsername(), "", cid, "");
		}
		else
		{
			int k = dal.EmArchiveUpdateData(m, "", sql);
			if(k>0)
			{
				str[0]="1";
			}
		}
		return str;
	}
}
