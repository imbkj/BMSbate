package bll.Archives;

import java.util.List;

import org.zkoss.zul.ListModelList;

import dal.Archives.EmArchiveDatumDal;
import dal.Archives.EmArchiveLinkModelDal;

import Model.EmArchiveDatumModel;
import Model.EmArchiveLinkModel;

public class Archive_ClientCommitBll {

	//查询联系信息
	public List<EmArchiveLinkModel> getLinkInfo(Integer id){
		List<EmArchiveLinkModel> list = new ListModelList<>();
		EmArchiveLinkModelDal dal = new EmArchiveLinkModelDal();
		try {
			list = dal.getInfoById(id);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return list;
	}
	
	//删除联系信息
	public Integer delMessage(Integer id){
		Integer i=0;
		EmArchiveLinkModelDal dal = new EmArchiveLinkModelDal();
		i=dal.del(id);
		return i;
	}
	
	//查询档案业务信息
	public List<EmArchiveDatumModel> getEmarchiveDatumInfoById(Integer id){
		List<EmArchiveDatumModel> list = new ListModelList<>();
		EmArchiveDatumDal dal =new EmArchiveDatumDal();
		try {
			list = dal.getInfoById(id);
		} catch (Exception e) {
			// TODO: handle exception.
			e.printStackTrace();
		}
		return list;
	}
}
