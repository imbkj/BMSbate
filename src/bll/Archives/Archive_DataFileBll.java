package bll.Archives;

import impl.SysMessageImpl;
import impl.WorkflowCore.WfOperateImpl;

import java.util.List;

import org.zkoss.zul.ListModelList;

import service.SysMessageService;
import service.WorkflowCore.WfBusinessService;
import service.WorkflowCore.WfOperateService;

import Model.EmArchiveDatumModel;
import Model.SysMessageModel;

import dal.Archives.EmArchiveDatumDal;

public class Archive_DataFileBll {

	public List<EmArchiveDatumModel> getemArchiveDatumInfo(Integer id) {
		List<EmArchiveDatumModel> list = new ListModelList<EmArchiveDatumModel>();
		EmArchiveDatumDal dal = new EmArchiveDatumDal();
		try {
			list = dal.getInfoById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public Integer passFlow(Integer id, Integer taprId, String username) {
		Integer i = 0;
		WfBusinessService wfbs = new Archive_DataFileImpl();
		WfOperateService wfs = new WfOperateImpl(wfbs);
		Object[] obj = { id };
		Integer cid=0;
		List<EmArchiveDatumModel> list= getemArchiveDatumInfo(id);
		if(list.size()>0)
		{
			if(list.get(0).getCid()!=null)
			{
				cid=list.get(0).getCid();
			}
		}
		String[] str = wfs.PassToNext(obj, taprId, username, "", cid, "");

		if (str[0].equals("1"))
			i = Integer.valueOf(str[2]);
		return i;
	}

	public Integer sendMessage(Integer userid, String username) {
		SysMessageService s = new SysMessageImpl();
		SysMessageModel smm = new SysMessageModel();
		List<SysMessageModel> list = new ListModelList<SysMessageModel>();
		smm.setSymr_log_id(userid);
		smm.setSymr_name(username);
		list.add(smm);
		Integer i = 0;
		try {
			i = s.add("档案调入", "档案已调档成功,请提交员工签名的《接转人事档案协议书》到人事组.", 1, 0, list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return i;
	}
}
