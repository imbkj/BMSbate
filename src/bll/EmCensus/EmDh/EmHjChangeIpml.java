package bll.EmCensus.EmDh;

import Model.EmSheBaoChangeHjModel;
import dal.EmCensus.EmDh.EmDh_OperateDal;
import service.WorkflowCore.WfBusinessService;

public class EmHjChangeIpml implements WfBusinessService {
	private EmDh_OperateDal dal = new EmDh_OperateDal();

	@Override
	public String[] Operate(Object[] obj) {
		String[] str = new String[5];
		try {
			String type = (String) obj[0];
			
			if (type == "1" || type.equals("1")) {
				EmSheBaoChangeHjModel model=(EmSheBaoChangeHjModel) obj[1];
				Integer id=dal.EmsheBaoChange_Add(model);
				if(id>0)
				{
					str[0]="1";
					str[1]="提交成功";
					str[2]=id+"";
					str[3]="EmSheBaoChangeHj";
					str[4]="社保户籍变更申请";
				}
				else
				{
					str[0]="0";
					str[1]="提交失败";
				}
			} else if (type == "2" || type.equals("2")) {
				EmSheBaoChangeHjModel model=(EmSheBaoChangeHjModel) obj[1];
				str[0]="1";
				str[1]="提交成功";
				str[2]=model.getSbci_id()+"";
				str[3]="EmSheBaoChangeHj";
				str[4]="客服确认社保户籍变更";
			}
		} catch (Exception e) {
			System.out.println("错误：" + e.getMessage());
			str[0] = "2";
			str[1] = "业务操作出错";
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
		return dal.updateHjTaprid(dataId, tapr_id);
	}

	@Override
	public Boolean ErrOperate(int dataId) {
		// TODO Auto-generated method stub
		return null;
	}

}
