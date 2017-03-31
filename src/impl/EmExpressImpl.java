package impl;

import impl.WorkflowCore.WfOperateImpl;
import bll.EmCensus.EmCensus_addImpl;
import bll.EmExpress.EmExpressAdd_Impl;
import Model.EmExpressInfoModel;
import Util.UserInfo;
import dal.EmExpress.EmExpressInfoOperateDal;
import dal.EmExpress.EmExpressInfoSelectDal;
import service.EmExpressService;
import service.WorkflowCore.WfBusinessService;
import service.WorkflowCore.WfOperateService;

public class EmExpressImpl implements EmExpressService{
	private EmExpressInfoOperateDal dal=new EmExpressInfoOperateDal();
	
	/*
	 * @Methodname:新增个人快件信息
	 * 
	 * @input:
	 * expr_exct_id：收件地址信息表id；gid：员工编号；expr_content:邮寄内容(邮寄物件)；expr_rank:快件等级(普通或者紧急);
	 * 
	 * 
	 * @out: 大于0：成功；小于0:失败；
	 */
	@Override
	public Integer addEmbaseExpress(Integer expr_exct_id, Integer gid,
			String expr_content, String expr_rank) {
		// TODO Auto-generated method stub
		EmExpressInfoModel m=new EmExpressInfoModel();
		m.setExpr_exct_id(expr_exct_id);
		m.setGid(gid);
		m.setExpr_content(expr_content);
		m.setExpr_rank(expr_rank);
		String name="",clseename="";

		EmExpressInfoSelectDal dal=new EmExpressInfoSelectDal();
		name=dal.getEmbaseName(" and gid="+gid)+"（"+gid+"）";
		clseename="个人快递发送";
		Object[] obj = {"1",m,"emba"};
		WfBusinessService wfbs = new EmExpressAdd_Impl();
		WfOperateService wfs = new WfOperateImpl(wfbs);
		String[] str = wfs.AddTaskToNext(obj, clseename,
				name+"快递发送",85, UserInfo.getUsername(), "", 0, "");
		Integer k=0;
		if(str[0]=="1")
		{
			k=1;
		}
		return k;
	}


	/*
	 * @Methodname:新增公司快件信息
	 * 
	 * @input:
	 * expr_exct_id：收件地址信息表id；cid:单位id；expr_content:邮寄内容(邮寄物件)；expr_rank:快件等级(普通或者紧急);
	 * expr_class:快件类型（公司或者个人）
	 * 
	 * @out: 大于0：成功；小于0:失败；
	 */
	@Override
	public Integer addCobaseExpress(Integer expr_exct_id, Integer cid,
			String expr_content, String expr_rank) {
		// TODO Auto-generated method stub
		EmExpressInfoModel m=new EmExpressInfoModel();
		m.setExpr_exct_id(expr_exct_id);
		m.setCid(cid);
		m.setExpr_content(expr_content);
		m.setExpr_rank(expr_rank);
		String name="",clseename="";
		EmExpressInfoSelectDal dal=new EmExpressInfoSelectDal();
		name=dal.getCobaseName(" and cid="+cid)+"（"+cid+"）";
		clseename="公司快递发送";
		Object[] obj = {"1",m,"coba"};
		WfBusinessService wfbs = new EmExpressAdd_Impl();
		WfOperateService wfs = new WfOperateImpl(wfbs);
		String[] str = wfs.AddTaskToNext(obj, clseename,
				name+"快递发送",85, UserInfo.getUsername(), "", 0, "");
		Integer k=0;
		if(str[0]=="1")
		{
			k=1;
		}
		return k;
	}

}
