package bll.EmSocialinPaper;

import org.zkoss.zul.Grid;

import dal.EmSocialinPaper.EmSocialinPaperOperateDal;
import Controller.DocumentsInfo.DocumentsInfo_OperationController;
import Model.EmSocialinPaperModel;
import service.WorkflowCore.WfBusinessService;

public class EmSocialinPaperImpl implements WfBusinessService {

	@Override
	public String[] Operate(Object[] obj) {
		String[] str = new String[5];
		int id = 0;
		int i = 0;
		if (obj[0].toString().equals("1")) {
			DocumentsInfo_OperationController docOC = new DocumentsInfo_OperationController();
			try {
				id = add((EmSocialinPaperModel) obj[1]);
				try {
					if (id > 0) {
						String[] str1 = docOC.AddsubmitDoc((Grid) obj[2], id
								+ "");

						if (str1[0].equals("1")) {
							str[0] = "1";
							str[1] = "提交成功!";
							str[2] = id + "";
							str[3] = "EmSocialinPaper";
							str[4] = ((EmSocialinPaperModel) obj[1])
									.getEspa_type() + "社保卡";
						} else {
							str[0] = "0";
							str[1] = str1[1];
						}
					}
				} catch (Exception e) {
					str[0] = "0";
					str[1] = "资料提交失败,请联系IT部门!";
				}
			} catch (Exception e) {
				str[0] = "0";
				str[1] = "提交失败,请联系IT部门!";
			}
		} else if (obj[0].toString().equals("2")) {
			try {
				i = Next((EmSocialinPaperModel) obj[1]);
				if (i == 2) {
					String statename = new EmSocialinPaperOperateDal()
							.getStatename(((EmSocialinPaperModel) obj[1])
									.getEspa_state() + 1);
					str[0] = "1";
					str[1] = "提交成功!";
					str[2] = ((EmSocialinPaperModel) obj[1]).getEspa_id() + "";
					str[3] = "EmSocialinPaper";
					str[4] = "社保卡状态变为" + statename;
				} else {
					str[0] = "0";
					str[1] = "提交失败,请联系IT部门!";
				}
			} catch (Exception e) {
				str[0] = "0";
				str[1] = "提交出错,请联系IT部门!";
			}
		}
		return str;
	}

	@Override
	public String[] ReturnOperate(Object[] obj) {
		String[] str = new String[5];
		int i = 0;
		try {
			i = Back((EmSocialinPaperModel) obj[0]);
			addLog((EmSocialinPaperModel) obj[0]);
			if (i == 1) {
				str[0] = "1";
				str[1] = "退回成功!";
				str[2] = ((EmSocialinPaperModel) obj[0]).getEspa_id() + "";
				str[3] = "EmSocialinPaper";
				str[4] = "退回";
			} else {
				str[0] = "0";
				str[1] = "提交失败,请联系IT部门!";
			}
		} catch (Exception e) {
			str[0] = "0";
			str[1] = "提交出错,请联系IT部门!";
		}
		return str;
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
		EmSocialinPaperOperateDal dal = new EmSocialinPaperOperateDal();
		return dal.updateTaprid(dataId, tapr_id);
	}

	@Override
	public Boolean ErrOperate(int dataId) {
		// TODO Auto-generated method stub
		return null;
	}

	// 社保卡新增，返回主键id
	public int add(EmSocialinPaperModel m) {
		Integer i = 0;
		EmSocialinPaperOperateDal dal = new EmSocialinPaperOperateDal();
		i = dal.add(m);
		return i;
	}

	// 转下一步
	public Integer Next(EmSocialinPaperModel m) {
		Integer i = 0;
		EmSocialinPaperOperateDal dal = new EmSocialinPaperOperateDal();
		i = dal.Next(m);
		return i;
	}

	// 退回
	public int Back(EmSocialinPaperModel m) {
		int i = 0;
		EmSocialinPaperOperateDal dal = new EmSocialinPaperOperateDal();
		i = dal.Back(m);
		return i;
	}

	public void addLog(EmSocialinPaperModel m) {
		EmSocialinPaperOperateDal dal = new EmSocialinPaperOperateDal();
		dal.addLog(m);
	}
}
