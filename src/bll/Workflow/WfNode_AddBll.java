package bll.Workflow;

import Model.WfNodeModel;
import dal.Workflow.WfNodeOperateDal;

public class WfNode_AddBll {
	// 任务单管理新增节点，传入参数，返回message数组(0失败1成功2出错3节点名称有误4节点步骤有误)
	public String[] AddNode(int wfno_wfde_id, String wfno_name,
			String wfno_selectuser, String wfno_runprocedure, String wfno_url,
			String wfno_remark, String wfno_addname, int step, int maxday,
			int runtype, int ifview, int ifskip, int ifreturn, int ifstop,
			int ifrevoke, int ifhavechild, int state) {
		String[] message = new String[2];
		try {
			message = existAddNode(wfno_name, step);
			if (message[0] == null) {
				WfNodeModel m = new WfNodeModel();
				int result = 0;
				WfNodeOperateDal opDal = new WfNodeOperateDal();

				m.setWfno_wfde_id(wfno_wfde_id);
				m.setWfno_name(wfno_name);
				m.setWfno_selectuser(wfno_selectuser);
				m.setWfno_runprocedure(wfno_runprocedure);
				m.setWfno_url(wfno_url);
				m.setWfno_remark(wfno_remark);
				m.setWfno_addname(wfno_addname);
				m.setWfno_step(step);
				m.setWfno_maxday(maxday);
				m.setWfno_runtype(runtype);
				m.setWfno_ifview(ifview);
				m.setWfno_ifskip(ifskip);
				m.setWfno_ifreturn(ifreturn);
				m.setWfno_ifstop(ifstop);
				m.setWfno_ifrevoke(ifrevoke);
				m.setWfno_ifhavechild(ifhavechild);
				m.setWfno_state(state);
				result = opDal.AddNode(m);
				if (result == 0) {
					message[0] = "1";
					message[1] = "新增节点成功!";
				} else if (result == -110) {
					message[0] = "0";
					message[1] = "无法提交！该流程已有相同的步骤启用。";
				} else {
					message[0] = "0";
					message[1] = "新增节点失败!";
				}
			}
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "新增节点出错。";
		}
		return message;

	}

	// 检测新增节点，录入数据的正确性
	private String[] existAddNode(String wfno_name, int step) {
		String[] message = new String[2];
		try {
			if ("".equals(wfno_name)) {
				message[0] = "3";
				message[1] = "请录入节点名称。";
			} else if (step == 0) {
				message[0] = "4";
				message[1] = "请输入正确的节点步骤。";
			}
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "新增节点出错。";
		}
		return message;
	}

	// 新增业务
	public int AddBusiness(String name, String remark, String addname) {
		WfNodeOperateDal opDal = new WfNodeOperateDal();
		return opDal.AddBusiness(name, remark, addname);
	}
}
