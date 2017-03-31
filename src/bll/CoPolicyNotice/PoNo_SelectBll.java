package bll.CoPolicyNotice;

import java.util.List;

import Model.CoPolicyNoticeFileModel;
import Model.CoPolicyNoticeModel;
import dal.CoPolicyNotice.PoNo_SelectDal;

public class PoNo_SelectBll {
	private PoNo_SelectDal dal=new PoNo_SelectDal();
	// 查询政策通知基本信息
	public List<CoPolicyNoticeModel> getCoPolicyNoticeList(String str){
		return dal.getCoPolicyNoticeList(str);
	}
	
	// 查询政策通知文件信息
	public List<CoPolicyNoticeFileModel> getCoPolicyNoticeFileList(String str){
		return dal.getCoPolicyNoticeFileList(str);
	}
	
	//获取政策通知基本信息并根据id获取文件信息
	public List<CoPolicyNoticeModel> getList(String str){
		List<CoPolicyNoticeModel> list=getCoPolicyNoticeList(str);
		for(int i=0;i<list.size();i++)
		{
			CoPolicyNoticeModel m=list.get(i);
			List<CoPolicyNoticeFileModel> flist=getCoPolicyNoticeFileList(" and file_pono_id="+m.getPono_id());
			m.setFilelist(flist);
		}
		return list;
	}
	
	//根据文件名查询是否已有该文件
	public boolean isExistFile(String filename)
	{
		return dal.isExistFile(filename);
	}
	
	//查询政策通知与业务关联信息
	public List<CoPolicyNoticeModel> getNoticeRelationList(String str){
		List<CoPolicyNoticeModel> list=dal.getNoticeRelationList(str);
		for(int i=0;i<list.size();i++)
		{
			CoPolicyNoticeModel m=list.get(i);
			List<CoPolicyNoticeFileModel> flist=getCoPolicyNoticeFileList(" and file_pono_id="+m.getPono_id());
			m.setFilelist(flist);
		}
		return list;
	}
	
	public String getEmail(Integer log_id, String log_name) {
		return dal.getEmail(log_id, log_name);
	}
}
