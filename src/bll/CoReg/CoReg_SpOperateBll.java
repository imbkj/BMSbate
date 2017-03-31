package bll.CoReg;

import dal.CoReg.CoReg_Dal;
import impl.WorkflowCore.WfOperateImpl;
import Model.ResponsbilityBookModel;
import Util.UserInfo;
import service.WorkflowCore.WfBusinessService;
import service.WorkflowCore.WfOperateService;

public class CoReg_SpOperateBll implements WfBusinessService {

	/**
	 * 计生责任书流程
	 * 
	 * @return
	 */
	public String[] reSponStep(ResponsbilityBookModel m, int tapr_id, int cid) {
		Object[] obj = { "2", m };
		WfOperateService wfs = new WfOperateImpl(new CoReg_SpOperateBll());
		String[] str = wfs.PassToNext(obj, tapr_id, UserInfo.getUsername(), "",
				cid, "");

		return str;
	}

	/**
	 * 单独签订计生责任书
	 * 
	 * @return
	 */
	public String[] addRbb(ResponsbilityBookModel m, String company, int coriid) {
		Object[] obj = { "1", m, coriid };
		int cid = 0;
		if (m.getCid() != 0) {
			cid = m.getCid();
		}
		WfOperateService wfs = new WfOperateImpl(new CoReg_SpOperateBll());
		String[] str = wfs.AddTaskToNext(obj, "签订计生责任书", company + "签订计划责任书",
				111, UserInfo.getUsername(), "", cid, "");

		return str;
	}
	
	//单纯生成责任书流程，不添加责任书
	public  String[] addRbbTask(ResponsbilityBookModel m, String company, int coriid) {
		Object[] obj = { "3", m, coriid };
		int cid = 0;
		if (m.getCid() != 0) {
			cid = m.getCid();
		}
		WfOperateService wfs = new WfOperateImpl(new CoReg_SpOperateBll());
		String[] str = wfs.AddTaskToNext(obj, "签订计生责任书", company + "签订计划责任书",
				111, UserInfo.getUsername(), "", cid, "");
		return str;
	}

	@Override
	public String[] Operate(Object[] obj) {
		String[] str = new String[5];
		if (obj[0].equals("1")) {
			// 单独录入计划生育责任书流程
			ResponsbilityBookModel m = (ResponsbilityBookModel) obj[1];
			// 插入数据到ResponsbilityBook表
			int rebkid = new CoReg_Dal().InsertRbb(m);
			if (rebkid > 0) {
				str[0] = "1";
				str[1] = "提交成功!";
				str[2] = rebkid + "";
				str[3] = "ResponsibilityBook";
				str[4] = "客服提交申报";
			} else {
				str[0] = "0";
				str[1] = "提交失败!";
			}
		} else if (obj[0].equals("2")) {
			ResponsbilityBookModel m = (ResponsbilityBookModel) obj[1];
			// 更新数据
			int row = new CoReg_Dal().modReSponStep(m);
			if (row > 0) {
				str[0] = "1";
				str[1] = "提交成功!";
				str[2] = m.getRebk_id() + "";
				str[3] = "ResponsibilityBook";
				str[4] = "人事领取材料与表格";
			} else {
				str[0] = "0";
				str[1] = "提交失败!";
			}
		}
		else if (obj[0].equals("3")) {
			ResponsbilityBookModel m = (ResponsbilityBookModel) obj[1];
			// 更新数据
			//int row = new CoReg_Dal().modReSponStep(m);
			str[0] = "1";
			str[1] = "提交成功!";
			str[2] = m.getRebk_id() + "";
			str[3] = "ResponsibilityBook";
			str[4] = "客服提交申报";
		} 
		return str;
	}

	@Override
	public String[] ReturnOperate(Object[] obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] SkipOperate(Object[] obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] StopOperate(Object[] obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean UpdateTaprid(int dataId, int tapr_id, int state) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean ErrOperate(int dataId) {
		// TODO Auto-generated method stub
		return null;
	}

}
