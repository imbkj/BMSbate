package bll.EmExpress;

import service.WorkflowCore.WfBusinessService;
import service.WorkflowCore.WfOperateService;
import dal.EmExpress.EmExpressInfoOperateDal;
import Model.EmExpressContactInfoModel;
import Model.EmExpressInfoModel;
import Util.UserInfo;
import impl.EmExpressImpl;
import impl.WorkflowCore.WfOperateImpl;

public class EmExpressInfoOperateBll {
	
	private EmExpressInfoOperateDal dal=new EmExpressInfoOperateDal();
	
	/*
	 * @Methodname:新增个人快件信息
	 * 
	 * @input:
	 * expr_exct_id：收件地址信息表id；cid:单位id；gid：员工编号；expr_content:邮寄内容(邮寄物件，如：公积金卡)；
	 * expr_rank:快件等级(普通或者紧急;为空时默认为普通);
	 * 
	 * 
	 * @out: 大于0：成功；小于0:失败；
	 */
	public Integer addExpress(Integer expr_exct_id, Integer gid,
			String expr_content, String expr_rank)
	{
		EmExpressImpl impl=new EmExpressImpl();
		return impl.addEmbaseExpress(expr_exct_id, gid, expr_content, expr_rank);
	}
	
	/*
	 * @Methodname:新增公司快件信息
	 * 
	 * @input:
	 * expr_exct_id：收件地址信息表id；cid:单位id；gid：员工编号；expr_content:邮寄内容(邮寄物件，如：公积金卡)；
	 * expr_rank:快件等级(普通或者紧急)；为空时默认为普通;
	 * 
	 * 
	 * @out: 大于0：成功；小于0:失败；
	 */
	public Integer addCobaseExpress(Integer expr_exct_id, Integer cid,
			String expr_content, String expr_rank)
	{
		EmExpressImpl impl=new EmExpressImpl();
		return impl.addCobaseExpress(expr_exct_id, cid, expr_content, expr_rank);
	}
	
	
	//快递信息更新
	public String[] updateExpress(String sql,EmExpressInfoModel model)
	{
		WfBusinessService wfbs = new EmExpressAdd_Impl();
		WfOperateService wfs = new WfOperateImpl(wfbs);
		Object[] obj = {"2",model,sql};
		String[] str = wfs.PassToNext(obj,model.getExpr_tarpid(), UserInfo.getUsername(), "", 0, "");;
		return str;
	}
	
	//修改快递信息
	public Integer EmExpressInfoupdate(EmExpressInfoModel m) {
		return dal.EmExpressInfoupdate(m);
	}
	
	//新增快递收件地址信息
	public Integer EmExpressContactInfoAdd(EmExpressContactInfoModel m) {
		return dal.EmExpressContactInfoAdd(m);
	}
	
	//新增公司快递收件地址信息
	public Integer EmExpressContactInfoCobaseAdd(EmExpressContactInfoModel m) {
		return dal.EmExpressContactInfoCobaseAdd(m);
	}
	
	//修改快递收件地址信息
	public Integer EmExpressContactInfoEdit(EmExpressContactInfoModel m) {
		return dal.EmExpressContactInfoEdit(m);
	}

}
