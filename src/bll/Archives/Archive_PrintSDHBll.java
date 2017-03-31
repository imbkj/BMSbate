package bll.Archives;

import java.sql.SQLException;
import java.util.List;

import org.zkoss.zul.ListModelList;

import Model.EmArchiveDatumModel;
import dal.Archives.EmArchiveDatumDal;
import impl.WorkflowCore.WfOperateImpl;
import service.WorkflowCore.WfBusinessService;
import service.WorkflowCore.WfOperateService;

public class Archive_PrintSDHBll {
	public Integer updateSDH(Integer eadaId,Integer taprId,Integer sdh,String username){
		Integer i=0;
		WfBusinessService wfbs = new Archive_PrintSDHImpl();
		WfOperateService wfs = new WfOperateImpl(wfbs);
		Object[] obj = { sdh,eadaId };
		List<EmArchiveDatumModel> list = new ListModelList<EmArchiveDatumModel>();
		EmArchiveDatumDal dal = new EmArchiveDatumDal();
		Integer cid=0;
		try {
			list = dal.getInfoById(eadaId);
			if(list.size()>0)
			{
				if(list.get(0).getCid()!=null)
				{
					cid=list.get(0).getCid();
				}
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String[] str =wfs.PassToNext(obj, taprId, username, "", cid, "");
				
		if (str[0].equals("1"))
			i = Integer.valueOf(str[2]);
		return i;
	}
}
