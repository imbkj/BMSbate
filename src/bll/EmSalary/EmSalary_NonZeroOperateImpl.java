package bll.EmSalary;

import java.util.List;

import Model.EmSalaryBaseSel_viewModel;
import Model.EmSalaryDataModel;

import dal.EmSalary.EmSalary_NonZeroOperateDal;
import dal.EmSalary.EmSalary_SalaryOperateDal;
import dal.EmSalary.EmSalary_SalarySelDal;
import service.WorkflowCore.WfBusinessService;

public class EmSalary_NonZeroOperateImpl implements WfBusinessService {

	@SuppressWarnings("unchecked")
	@Override
	public String[] Operate(Object[] obj) {
		String[] message = new String[5];
		if ("1".equals(obj[0].toString())) {
			message[0] = "1";
			message[1] = "已提交审核。";
			message[2] = String.valueOf(obj[1].toString());
			message[3] = "TaskBatch";
			message[4] = "修改工资非清零字段。";
		} else if ("2".equals(obj[0].toString())) {
			message = addEmSalaryDataTemp(
					(List<EmSalaryBaseSel_viewModel>) obj[1],
					String.valueOf(obj[2]));
		} else if ("3".equals(obj[0].toString())) {
			message = updateNonZeroData(Integer.parseInt(obj[1].toString()),
					(List<EmSalaryDataModel>) obj[2]);
		}else if ("4".equals(obj[0].toString())) {
			message[0] = "1";
			message[1] = "已退回。";
			message[2] = String.valueOf(obj[1].toString());
			message[3] = "TaskBatch";
			message[4] = "退回修改工资非清零字段。";
		}
		
		return message;
	}

	@Override
	public String[] ReturnOperate(Object[] obj) {
		String[] message = new String[5];
		message[0] = "1";
		message[1] = "已退回成功。";
		message[2] = String.valueOf(obj[1].toString());
		message[3] = "TaskBatch";
		message[4] = "退回至修改工资非清零字段。";
		return message;
	}

	@Override
	public String[] SkipOperate(Object[] obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] StopOperate(Object[] obj) {
		String[] message = new String[5];
		try {
			EmSalary_NonZeroOperateDal opdal = new EmSalary_NonZeroOperateDal();
			int i = opdal.UpdateIfNonZeroState(Integer.parseInt(obj[1]
					.toString()));
			if (i == 0) {
				message[0] = "1";
				message[1] = "任务单已终止。";
				message[2] = String.valueOf(obj[1].toString());
				message[3] = "TaskBatch";
				message[4] = "终止工资修改非清零字段任务。";
			} else {
				message[0] = "0";
				message[1] = "任务单终止失败。";
			}
		} catch (Exception e) {
			e.printStackTrace();
			message[0] = "2";
			message[1] = "任务单终止出错。";
		}
		return message;
	}

	@Override
	public Boolean UpdateTaprid(int dataId, int tapr_id, int state) {
		EmSalary_SalaryOperateDal dal = new EmSalary_SalaryOperateDal();
		return dal.upTaskBatchTaprId(tapr_id, dataId);
	}

	@Override
	public Boolean ErrOperate(int dataId) {
		// TODO Auto-generated method stub
		return null;
	}

	private String[] addEmSalaryDataTemp(
			List<EmSalaryBaseSel_viewModel> emSalaryList, String username) {
		EmSalary_SalaryOperateDal dal = new EmSalary_SalaryOperateDal();
		String[] message = new String[5];
		int sum = emSalaryList.size();
		int success = 0;
		try {
			int taba_id = dal.addTaskBatch(username, "确认工资信息");
			if (taba_id > 0) {
				for (EmSalaryBaseSel_viewModel m : emSalaryList) {
					try {
						success = success
								+ dal.addEmSalaryDataTemp(taba_id,
										m.getEsda_id(), username);
					} catch (Exception e) {

					}
				}
				if (success == sum) {
					message[1] = "共选择" + sum + "条数据，全部成功。";
				} else {
					message[1] = "共选择" + sum + "条数据，成功生成" + success + "条数据。";
				}
				message[0] = "1";
				message[2] = String.valueOf(taba_id);
				message[3] = "TaskBatch";
				message[4] = "生成" + sum + "条数据。";
			} else {
				message[0] = "0";
				message[1] = "生成需变动非清零工资项目的工资数据失败";
			}
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "生成需变动非清零工资项目的工资数据出错";
		}
		return message;

	}

	// 审核工资修改非清零字段的数据完成(将修改数据重新赋值计算再更新至数据库)
	public String[] updateNonZeroData(int taba_id,
			List<EmSalaryDataModel> salaryList) {
		String[] message = new String[5];
		try {
			EmSalary_SalarySelDal seldal = new EmSalary_SalarySelDal();
			EmSalary_SalaryOperateDal opdal = new EmSalary_SalaryOperateDal();
			EmSalary_NonZeroOperateDal nonZeroDal = new EmSalary_NonZeroOperateDal();
			int sum = salaryList.size();
			int success = 0;
			// 从工资表获取数据
			List<EmSalaryDataModel> list = seldal
					.getSalaryDataByTabaIdFormEsdt(taba_id);
			// 插入修改的数据并计算
			for (EmSalaryDataModel m : list) {
				for (EmSalaryDataModel upModel : salaryList) {
					if (m.getEsda_id() == upModel.getEsda_id()) {
						m.insertItemList(upModel.getItemList());
						m.setItemListToModel();
						m.sumItemInfo();
						salaryList.remove(upModel);
						break;
					}
				}
				// 更新修改后数据至DB
				if (opdal.UpSalary(m) == 0) {
					success = success + 1;
				}
				// 修改非清零状态
				nonZeroDal.UpdateIfNonZeroState(taba_id);
			}
			if (success == sum) {
				message[0] = "1";
				message[1] = "共修改工资数据" + sum + "条，全部成功。";
				message[2] = String.valueOf(taba_id);
				message[3] = "TaskBatch";
				message[4] = "共修改工资数据" + sum + "条，全部成功。";
			} else {
				message[0] = "0";
				message[1] = "共修改工资数据" + sum + "条，有" + (sum - success)
						+ "条数据修改失败。";
				message[2] = String.valueOf(success);
			}
		} catch (Exception e) {
			e.printStackTrace();
			message[0] = "2";
			message[1] = "更新出错。";
		}
		return message;

	}
}
