package bll.CoPolicyNotice;

import dal.CoPolicyNotice.PoNo_OperateDal;
import Model.CoPolicyNoticeFileModel;
import Model.CoPolicyNoticeModel;

public class PoNo_OperateBll {
	private PoNo_OperateDal dal=new PoNo_OperateDal();
	//新增政策通知
	public Integer CoPolicyNoticeAdd(CoPolicyNoticeModel model) {
		return dal.CoPolicyNoticeAdd(model);
	}

	//修改政策通知
	public Integer CoPolicyNoticeUpdate(CoPolicyNoticeModel model) {
		return dal.CoPolicyNoticeUpdate(model);
	}
		
	//新增政策通知文件
	public Integer CoPolicyNoticeFileAdd(CoPolicyNoticeFileModel model) {
		return dal.CoPolicyNoticeFileAdd(model);
	}
	
	//修改政策通知文件
	public Integer CoPolicyNoticeFileUpdate(CoPolicyNoticeFileModel model) {
		return dal.CoPolicyNoticeFileUpdate(model);
	}
	
	//根据Id把CoPolicyNotice表的状态改为0
	public Integer updateCoPolicyNoticeState(Integer id)
	{
		return dal.updateCoPolicyNoticeState(id);
	}
	
	//根据Id把CoPolicyNoticeFile表的状态改为0
	public Integer updateCoPolicyNoticeFileState(Integer id)
	{
		return dal.updateCoPolicyNoticeFileState(id);
	}
	
	// 业务与通知信息关联
	public Integer CoPolicyNoticeRelation(CoPolicyNoticeModel model) {
		return dal.CoPolicyNoticeRelation(model);
	}
	
	// 根据业务类型和业务表id删除信息
	public Integer delNoticeRelation(String type, Integer id) {
		return dal.delNoticeRelation(type, id);
	}

}
