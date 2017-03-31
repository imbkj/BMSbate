package bll.Archives;

import impl.WorkflowCore.WfOperateImpl;

import java.util.List;

import org.zkoss.zul.ListModelList;

import service.WorkflowCore.WfBusinessService;
import service.WorkflowCore.WfOperateService;

import dal.Archives.EmArchiveDatumDal;

import Model.EmArchiveDatumModel;

public class Archive_CommitChargeBll {

	// 获取档案业务信息
	public List<EmArchiveDatumModel> getInfo(Integer id) {
		List<EmArchiveDatumModel> list = new ListModelList<>();
		EmArchiveDatumDal dal = new EmArchiveDatumDal();
		try {
			list = dal.getInfoById(id);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return list;
	}

	// 人事确认费用并反馈客服
	public Integer updateCharge(Integer id, String type, String charge,
			String remark, Integer tapr_id, String username) {
		Integer i = 0;
		EmArchiveDatumDal dal = new EmArchiveDatumDal();
		WfBusinessService wfbs = new Archive_CommitChargeImpl();
		WfOperateService wfs = new WfOperateImpl(wfbs);
		Object[] obj = { 1, type, charge, remark, id };
		List<EmArchiveDatumModel> list=getInfo(id);
		Integer cid=0;
		if(list.size()>0)
		{
			cid=list.get(0).getCid();
		}
		// 选择档案类型
		String[] str = wfs.PassToNext(obj, tapr_id, username, "", cid, "");
		i=Integer.valueOf(str[0].toString());
		
		if (str[0].equals("1")) {
			if (type.equals("否")) {

				Object[] o = { 0, type, id };
				try {
					tapr_id = dal.getTaprId(id);
					// 执行流程
					wfs.PassToNext(o, tapr_id, "系统", "", cid, "");
					tapr_id = dal.getTaprId(id);
					wfs.PassToNext(o, tapr_id, "系统", "",cid, "");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return i;
	}
}
