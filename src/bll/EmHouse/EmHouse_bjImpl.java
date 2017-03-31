package bll.EmHouse;



import dal.EmHouse.Emhouse_BjDal;
import Model.EmHouseBJModel;
import service.WorkflowCore.WfBusinessService;

public class EmHouse_bjImpl implements WfBusinessService {

	@Override
	public String[] Operate(Object[] obj) {
		// TODO Auto-generated method stub
		String[] message = new String[5];
		if (obj[0].equals("新增补缴")) {
			EmHouseBJModel em = new EmHouseBJModel();
			em = (EmHouseBJModel) obj[1];
			Emhouse_BjBll bll = new Emhouse_BjBll();
			Integer i = bll.emhousebjAdd(em);
		
			if (i > 0) {
				message[0] = "1";
				message[1] = "提交成功!";
				message[2] = i.toString();
				message[3] = "EmHousebj";
				message[4] = "新增补缴";
			} else {
				message[0] = "2";
				message[1] = "新增补缴数据出错。";
			}
		}else if (obj[0].equals("确认数据")) {
			EmHouseBJModel em = new EmHouseBJModel();
			em = (EmHouseBJModel) obj[1];
			message[0] = "1";
			message[1] = "提交成功!";
			message[2] = em.getEmhb_id().toString();
			message[3] = "EmHousebj";
			message[4] = "确认数据";
		}

		return message;
	}

	@Override
	public String[] ReturnOperate(Object[] obj) {
		// TODO Auto-generated method stub
		String[] message = new String[5];
		if (obj[0].equals("补缴退回")) {
			EmHouseBJModel em = new EmHouseBJModel();
			em = (EmHouseBJModel) obj[1];
			EmHouse_EditBll bll = new EmHouse_EditBll();
			Integer i = bll.returnBJ(em);
		
			if (i > 0) {
				message[0] = "1";
				message[1] = "操作成功!";
				message[2] = em.getEmhb_id().toString();
				message[3] = "EmHousebj";
				message[4] = "退回补缴";
			} else {
				message[0] = "2";
				message[1] = "退回补缴数据出错。";
			}
		}
		
		return message;
	}

	@Override
	public String[] SkipOperate(Object[] obj) {
		// TODO Auto-generated method stub
		String[] message = new String[5];
		if (obj[0].equals("跳过确认")) {
			EmHouseBJModel em = new EmHouseBJModel();
			em = (EmHouseBJModel) obj[1];
			message[0] = "1";
			message[1] = "提交成功!";
			message[2] = em.getEmhb_id().toString();
			message[3] = "EmHousebj";
			message[4] = "跳过确认";
		}
		return message;
	}

	@Override
	public String[] StopOperate(Object[] obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean UpdateTaprid(int dataId, int tapr_id, int state) {
		// TODO Auto-generated method stub
		Emhouse_BjDal dal = new Emhouse_BjDal();
		Integer i = dal.updateTaprId(dataId, tapr_id);
		if (i > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public Boolean ErrOperate(int dataId) {
		// TODO Auto-generated method stub
		return null;
	}

}
