package bll.EmSalary;

import Model.EmSocialinPaperModel;
import dal.EmSalary.EmSalary_EditDal;
import service.WorkflowCore.WfBusinessService;

public class EmSalary_Editimpl implements WfBusinessService {
	private EmSalary_EditDal emsdal =new EmSalary_EditDal();
	/**
	 * @param args
	 */
//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//
//	}
//	
	

	@Override
	public String[] Operate(Object[] obj) {
		// TODO Auto-generated method stub
		String[] str = new String[5];
//		int id = 0;
//		int i = 0;
		if (obj[0].toString().equals("1")) {
			System.out.println(obj[0]);
			
				str[0] = "1";
				str[1] = "提交成功!";
				//需要记录数据ID
				str[2] = obj[3]+"";
				str[3] = "TaskBatch";
				str[4] = "工资审核";
			
		}
		if (obj[0].toString().equals("2")) {
			System.out.println(obj[0]);
			
				str[0] = "1";
				str[1] = "提交成功!";
				//需要记录数据ID
				str[2] = obj[3]+"";
				str[3] = "TaskBatch";
				str[4] = "工资发放";
			
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
		return emsdal.updateTaprid(dataId, tapr_id);
		
	}

	@Override
	public Boolean ErrOperate(int dataId) {
		// TODO Auto-generated method stub
		return null;
	}
	
	//

}
