package bll.EmCommissionOut;

import java.util.ArrayList;
import java.util.List;
import dal.EmCommissionOut.EmCommissionyearchangetitleDal;

import Model.EmCommissionyearchangetitleModel;

import service.WorkflowCore.WfBusinessService;

public class EmCommissionyearchangetitleOperateImpl implements WfBusinessService  {

	private EmCommissionyearchangetitleDal dal =new EmCommissionyearchangetitleDal();
	@Override
	public String[] Operate(Object[] obj) {
		
		String[] message = new String[5];
		
		switch (obj[0].toString()) {
		//新增委托
		case "1":
			int size =((ArrayList<EmCommissionyearchangetitleModel>) obj[1]).size();
			//int taba_id = dal.addTaskBatch(obj[2].toString(), "委托基数采集");
			if (dal.changetitleadd((ArrayList<EmCommissionyearchangetitleModel>) obj[1],Integer.parseInt(obj[3].toString()))==1)
			{
				message[0] = "1";
				message[2] = String.valueOf(Integer.parseInt(obj[3].toString()));
				message[3] = "TaskBatch";
				message[4] = "采集"+ size +"家机构的数据";
			}
			else 
			{
				message[0] = "2";
				message[2] = "基础采集新增出错";
			}
				break;
		//审核委托去下一步
		case "2":
			message[0] = "1";
			message[1] = "提交成功!";
			//需要记录数据ID
			message[2] = obj[3]+"";
			message[3] = "TaskBatch";
			message[4] = "年调采集审核";
			
	
			break;

		default:
			break;
		}
		
//		//新增年调
//		if (obj[0].toString() == "1") {
//			
//
//		}
		//审核年调
//		if if (obj[0].toString() == "2") {
//		}
//		}

		return message;
		
		// TODO Auto-generated method stub
		
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
		return dal.upTaskBatchTaprId(tapr_id, dataId);
	}

	@Override
	public Boolean ErrOperate(int dataId) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @param args
	 */
	

}
