package bll.Embase;

import java.util.ArrayList;
import java.util.List;

import Model.EmBaseRequiredModel;
import Model.EmbaseModel;
import dal.Embase.EmBaseLogin_AddDal;
import service.WorkflowCore.WfBusinessService;

public class EmBase_NotinAddImpl implements WfBusinessService {

	@SuppressWarnings("unchecked")
	@Override
	public String[] Operate(Object[] obj) {
		String[] str = new String[5];
		int id = 0;
		int row = 0;
		EmbaseLogin_AddBll bll = new EmbaseLogin_AddBll();

		if (obj[0].toString().equals("1")) {
			id = EmbaseNotinAdd((EmbaseModel) obj[1]);
			if (id > 0) {
				List<String> requiredList = new ArrayList<String>();
				requiredList = (List<String>) obj[2];
				for (int i = 0; i < requiredList.size(); i++) {
					EmBaseRequiredModel rm = new EmBaseRequiredModel();
					rm.setEmbr_emba_id(id);
					rm.setEmbr_column(requiredList.get(i));

					row += bll.EmbaseRequiredAdd(rm);
				}
				if (row == requiredList.size()) {
					str[0] = "1";
					str[1] = "提交成功!";
					str[2] = id + "";
					str[3] = "EmBaseNotIn";
					str[4] = "员工信息预增";
				} else {
					str[0] = "0";
					str[1] = "必填项设置失败,请联系IT部门!";
				}
			} else {
				str[0] = "0";
				str[1] = "基本信息提交失败,请联系IT部门!";
			}
		} else if (obj[0].toString().equals("2")) {
			row = EmbaseloginUpdate((EmbaseModel) obj[1]);
			if (row > 0) {
				str[0] = "1";
				str[1] = "提交成功!";
				str[2] = ((EmbaseModel) obj[1]).getEmba_id() + "";
				str[3] = "EmBaseNotIn";
				str[4] = "员工信息补充";

			} else {
				str[0] = "0";
				str[1] = "补充信息失败,请联系IT部门!";
			}
		} else if (obj[0].toString().equals("3")) {
			id = EmbaseNotinAdd((EmbaseModel) obj[1]);
			if (id > 0) {
				str[0] = "1";
				str[1] = "提交成功!";
				str[2] = id + "";
				str[3] = "EmBaseNotIn";
				str[4] = "员工信息新增";

			} else {
				str[0] = "0";
				str[1] = "补充信息失败,请联系IT部门!";
			}
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
		String[] str = new String[5];
		str[0] = "1";
		str[1] = "提交成功!";
		str[2] = ((EmbaseModel) obj[1]).getEmba_id() + "";
		str[3] = "EmBaseNotIn";
		str[4] = "员工信息新增";
		return str;
	}

	@Override
	public String[] StopOperate(Object[] obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean UpdateTaprid(int dataId, int tapr_id, int state) {
		EmBaseLogin_AddDal dal = new EmBaseLogin_AddDal();
		dal.UpdateTaprid(dataId, tapr_id);
		return true;
	}

	@Override
	public Boolean ErrOperate(int dataId) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 员工信息新增
	 * 
	 * @param em
	 * @return 新增id
	 */
	public int EmbaseNotinAdd(EmbaseModel em) {
		int i = 0;
		EmBaseLogin_AddDal dal = new EmBaseLogin_AddDal();
		i = dal.EmbaseloginAdd(em);
		return i;
	}

	/**
	 * 员工信息修改
	 * 
	 * @param em
	 * @return 新增id
	 */
	public int EmbaseloginUpdate(EmbaseModel em) {
		int i = 0;
		EmBaseLogin_AddDal dal = new EmBaseLogin_AddDal();
		i = dal.EmbaseloginUpdate(em);
		return i;
	}

}
