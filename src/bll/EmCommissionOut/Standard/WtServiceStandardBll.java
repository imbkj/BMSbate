package bll.EmCommissionOut.Standard;

import java.util.List;

import org.zkoss.zul.ListModelList;

import Model.CoBaseModel;
import Model.WtServiceStandardrelationModel;
import dal.EmCommissionOut.Standard.WtServiceStandardDal;;

public class WtServiceStandardBll {
	
	WtServiceStandardDal wtsdal =new WtServiceStandardDal();
	
	// 获取公司信息
		public CoBaseModel getCobaInfo(Integer cid) {
			return wtsdal.getCobaInfo(cid);
		}
		
		//根据cid获取list
		public List<WtServiceStandardrelationModel> getmodellist(String strwhere)
		{
			return wtsdal.getmodelList(strwhere);
			
		}
		
		//根据cid获取list
		public List<WtServiceStandardrelationModel> getmodelListonly(String strwhere)
		{
			return wtsdal.getmodelListonly(strwhere);
			
		}
		
		
		
		
	//插入数据
	public Integer WtServiceStandardadd(WtServiceStandardrelationModel m) {
			return wtsdal.WtServiceStandardadd(m);
		}
	
	

	//修改
	public Integer WtServiceStandardupdate(WtServiceStandardrelationModel m)
	{
		return wtsdal.WtServiceStandardupdate(m);
	}
	
	//删除
	public Integer WtServiceStandarddelete(int dataid)
	{
		return wtsdal.WtServiceStandarddelete(dataid);
	}
	
	
	//判断是否已经关联到服务费
	public boolean checkrlation(WtServiceStandardrelationModel m)
	{
		return wtsdal.checkrlation(m);
		
		
		 
	}


}
