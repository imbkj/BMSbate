package bll.Archives;

import dal.Archives.EmArchiveDatum_OperateDal;
import Model.EmArchiveDatumModel;
import Util.UserInfo;
import service.WorkflowCore.WfBusinessService;

public class EmArchiveDatumDataImpl implements WfBusinessService {
	EmArchiveDatum_OperateDal dal = new EmArchiveDatum_OperateDal();

	@Override
	public String[] Operate(Object[] obj) {
		// TODO Auto-generated method stub
		String[] str = new String[5];
		try {

			String type = (String) obj[0];
			EmArchiveDatumModel model = (EmArchiveDatumModel) obj[1];
			String remark = (String) obj[2];
			if (type == "1" || type.equals("1")) {
				// 新增查借材料
				int k = dal.EmArchiveAddData(model, remark);
				if (k > 0) {
					str[0] = "1";
					str[1] = "提交成功";
					str[2] = k + "";
					str[3] = "EmArchiveDatum";
					str[4] = "受理申请";
				} else {
					str[0] = "0";
					str[1] = "提交失败";
				}
			} else if (type == "2" || type.equals("2")) {
				// 更新业务信息
				String sql = (String) obj[3];
				int k = dal.EmArchiveUpdateData(model, remark, sql);
				if (k > 0) {
					str[0] = "1";
					str[1] = "提交成功";
					str[2] = model.getEada_id() + "";
					str[3] = "EmArchiveDatum";
					str[4] = model.getEada_type();
				} else {
					str[0] = "0";
					str[1] = "提交失败";
				}
			}
			// type=3表示退回
			else if (type.equals("3")) {
				Integer k = dal.EmArchiveReturn(model);
				if (k > 0) {
					str[0] = "1";
					str[1] = "提交成功";
					str[2] = model.getEada_id() + "";
					str[3] = "EmArchiveDatum";
					str[4] = model.getEada_type();
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
		String[] str = new String[5];
		EmArchiveDatumModel model = (EmArchiveDatumModel) obj[1];
		Integer k = dal.EmArchiveReturn(model);
		if (k > 0) {
			str[0] = "1";
			str[1] = "提交成功";
			str[2] = model.getEada_id() + "";
			str[3] = "EmArchiveDatum";
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
		EmArchiveDatumModel model = (EmArchiveDatumModel) obj[1];
		String sql = (String) obj[3];
		int k = dal.EmArchiveUpdateData(model, "", sql);
		if (k > 0) {
			str[0] = "1";
			str[1] = "提交成功";
			str[2] = model.getEada_id() + "";
			str[3] = model.getEada_id() + "";
			str[4] = "更改状态";
		} else {
			str[0] = "0";
			str[1] = "提交失败";
		}
		return str;
	}

	@Override
	public String[] StopOperate(Object[] obj) {
		// TODO Auto-generated method stub
		String[] str = new String[5];
		if (obj[0].equals("2")) {
			EmArchiveDatumModel model = (EmArchiveDatumModel) obj[1];
			str[0] = "1";
			str[1] = "提交成功";
			str[2] = model.getEada_id().toString();
			str[3] = "EmArchiveDatum";
			str[4] = "结束流程";
		} else if (obj[0].equals("终止流程")) {
			EmArchiveDatumModel em = (EmArchiveDatumModel) obj[1];
			EmArchiveDatum_OperateDal dal = new EmArchiveDatum_OperateDal();
			Integer i = dal.updateInfo(em, em.getEada_id());
			if (i > 0) {
				str[0] = "1";
				str[1] = "提交成功";
				str[2] = em.getEada_id().toString();
				str[3] = "EmArchiveDatum";
				str[4] = "结束流程";
			} else {
				str[0] = "0";
				str[1] = "提交失败";
			}
		}else if (obj[0].equals("3")) {
			EmArchiveDatumModel em = (EmArchiveDatumModel) obj[1];
			EmArchiveDatum_OperateDal dal = new EmArchiveDatum_OperateDal();
			Integer i = dal.updateInfoEnd(em.getEada_id());
			if (i > 0) {
				str[0] = "1";
				str[1] = "提交成功";
				str[2] = em.getEada_id().toString();
				str[3] = "EmArchiveDatum";
				str[4] = "结束流程";
			} else {
				str[0] = "0";
				str[1] = "提交失败";
			}
		}

		return str;
	}

	@Override
	public Boolean UpdateTaprid(int eadaid, int taprid, int state) {
		// TODO Auto-generated method stub
		return dal.updateTaprid(eadaid, taprid);
	}

	@Override
	public Boolean ErrOperate(int dataId) {
		// TODO Auto-generated method stub
		return null;
	}

}
