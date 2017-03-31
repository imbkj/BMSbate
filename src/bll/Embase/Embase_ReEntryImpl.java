package bll.Embase;

import Util.UserInfo;
import service.WorkflowCore.WfBusinessService;

public class Embase_ReEntryImpl  implements WfBusinessService{

	@Override
	public String[] Operate(Object[] obj) {
		// TODO Auto-generated method stub
		String[] str = new String[5];
		String tablename=(String) obj[0];
		String id=(String) obj[1];
		str[0]="1";
		str[1]="提交成功";
		str[2]=id;
		str[3]=tablename;
		str[4]=UserInfo.getUsername()+"：恢复入职时终止流程";
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
		String[] str = new String[5];
		String tablename=(String) obj[0];
		String id=(String) obj[1];
		str[0]="1";
		str[1]="提交成功";
		str[2]=id;
		str[3]=tablename;
		str[4]="终止流程";
		return str;
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
