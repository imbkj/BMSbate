package bll.Archives;

import Model.EmArchiveModel;
import Model.EmArchiveRemarkModel;
import dal.Archives.EmArchive_OperateDal;

public class EmArchive_OperateBll {
	EmArchive_OperateDal dal=new EmArchive_OperateDal();
	
	//修改档案信息
	public int updateEmArchive(EmArchiveModel m){
		return dal.updateEmArchive(m);
	}
	//添加档案备注信息
	public int addRemark(EmArchiveRemarkModel model){
		return dal.addReamrk(model);
	}
	
	//修改档案
	public int EmArchiveEdit(EmArchiveModel m)
	{
		return dal.EmArchiveEdit(m);
	}
}
