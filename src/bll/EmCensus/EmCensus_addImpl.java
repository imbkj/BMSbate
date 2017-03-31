package bll.EmCensus;

import bll.Back.BackAddBll;
import dal.EmCensus.EmCensus_OperateDal;
import Model.BackCauseInfoModel;
import Model.EmArchiveDatumModel;
import Model.EmCensusModel;
import service.WorkflowCore.WfBusinessService;

public class EmCensus_addImpl implements WfBusinessService {
	EmCensus_OperateDal dal = new EmCensus_OperateDal();

	@Override
	public String[] Operate(Object[] obj) {
		// TODO Auto-generated method stub
		String[] str = new String[5];
		try {
			String type = (String) obj[0];
			EmCensusModel model = (EmCensusModel) obj[1];
			if (type == "1" || type.equals("1")) {
				// 落入申请新增
				int k = dal.EmCensusAdd(model);
				if (k > 0) {
					str[0] = "1";
					str[1] = "提交成功";
					str[2] = k + "";
					str[3] = "EmCensus";
					str[4] = "落户申请";
				} else {
					str[0] = "0";
					str[1] = "提交失败";
				}
			}

			// 调户后自动落户
			else if (type == "0" || type.equals("0")) {

				// 落入申请新增
				int k = dal.EmCensusInfoAdd(model);
				if (k > 0) {
					str[0] = "1";
					str[1] = "提交成功";
					str[2] = k + "";
					str[3] = "EmCensus";
					str[4] = "落户申请";
				} else {
					str[0] = "0";
					str[1] = "提交失败";
				}
			} else if (type == "2" || type.equals("2")) {
				// 落入申请修改
				String sql = (String) obj[2];
				int k = dal.UpdateEmCensusInfo(model, sql);
				if (k > 0) {
					str[0] = "1";
					str[1] = "提交成功";
					str[2] = model.getEmhj_id() + "";
					str[3] = "EmCensus";
					str[4] = model.getOperateinfo();
				} else {
					str[0] = "0";
					str[1] = "提交失败";
				}
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
		String[] str = new String[5];
		EmCensusModel model = (EmCensusModel) obj[1];
		String sql = (String) obj[2];
		int k = dal.UpdateEmCensusInfo(model, sql);
		if (k > 0) {
			str[0] = "1";
			str[1] = "提交成功";
			str[2] = model.getEmhj_id() + "";
			str[3] = "EmCensus";
			str[4] = "退回";
		} else {
			str[0] = "0";
			str[1] = "提交失败";
		}
		return str;
	}

	@Override
	public String[] SkipOperate(Object[] obj) {
		// TODO Auto-generated method stub
		String[] str = new String[5];

		String type = (String) obj[0];
		EmCensusModel model = (EmCensusModel) obj[1];
		if (type.equals("2")) {
			// 落入申请修改
			String sql = (String) obj[2];
			int k = dal.UpdateEmCensusInfo(model, sql);
			if (k > 0) {
				str[0] = "1";
				str[1] = "提交成功";
				str[2] = model.getEmhj_id() + "";
				str[3] = "EmCensus";
				str[4] = model.getOperateinfo();
			} else {
				str[0] = "0";
				str[1] = "提交失败";
			}
		}
		return str;
	}

	@Override
	public String[] StopOperate(Object[] obj) {
		String[] str = new String[5];
		EmCensusModel model = (EmCensusModel) obj[1];
		String sql = (String) obj[2];
		int k = dal.UpdateEmCensusInfo(model, sql);
		if (k > 0) {
			str[0] = "1";
			str[1] = "提交成功";
			str[2] = model.getEmhj_id() + "";
			str[3] = "EmCensus";
			str[4] = "退回";
		} else {
			str[0] = "0";
			str[1] = "提交失败";
		}
		return str;
	}

	@Override
	public Boolean UpdateTaprid(int dataId, int tapr_id, int state) {
		// TODO Auto-generated method stub
		return dal.updateTaprid(dataId, tapr_id);
	}

	@Override
	public Boolean ErrOperate(int dataId) {
		// TODO Auto-generated method stub
		return null;
	}

}
