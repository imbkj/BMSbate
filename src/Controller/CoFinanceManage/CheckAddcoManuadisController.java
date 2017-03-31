package Controller.CoFinanceManage;

import java.util.HashMap;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import Model.CoBaseModel;
import Model.CoFinanceManualDisposableModel;
import Model.EmbaseModel;
import bll.CoFinanceManage.Cfma_ManualDisSpOperateBll;
import bll.CoFinanceManage.Cfma_ManualDisposableBll;

/**
 * 手动添加公司非标审核
 * 
 * @author Administrator
 * 
 */
public class CheckAddcoManuadisController {
	private int daid = Integer.parseInt(Executions.getCurrent().getArg()
			.get("daid").toString());
	private int taprid = Integer.parseInt(Executions.getCurrent().getArg()
			.get("id").toString());
	private Cfma_ManualDisposableBll bll = new Cfma_ManualDisposableBll();
	private CoBaseModel com;
	private CoFinanceManualDisposableModel m;
	private EmbaseModel em;

	public CheckAddcoManuadisController() {
		com = bll.getCoInfo(daid); // 根据daid获得公司信息
		m = bll.getCheckCoInfo(daid); // 根据daid获取审核信息
		if(m.getGid() != 0){   //如果gid不等于0 ，查询员工信息
			em = bll.getEminfo(m.getGid());
		}
		
	}

	// 退回
	@Command
	public void back(@BindingParam("checkwin") Window window) {
		Map<String,Integer> map = new HashMap<String,Integer>();
		map.put("taprid", taprid);
		map.put("daid", daid);
		Window win = (Window) Executions.createComponents(
				"../CoFinanceManage/Cfma_checkManualDisbackReason.zul", null, map);
		win.doModal();
		window.detach();
	}

	// 审核
	@Command
	public void check(@BindingParam("checkwin") Window window) {
		// 进入任务单下一步
		Cfma_ManualDisSpOperateBll operabll = new Cfma_ManualDisSpOperateBll();
		String[] str = operabll.cheackCoDispo(m, com);
		if (str[0].equals("1")) {
			Messagebox.show(str[1], "操作提示", Messagebox.OK,
					Messagebox.INFORMATION);
			window.detach();
		} else {
			Messagebox.show(str[1], "操作提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	
	public EmbaseModel getEm() {
		return em;
	}

	public void setEm(EmbaseModel em) {
		this.em = em;
	}

	public CoFinanceManualDisposableModel getM() {
		return m;
	}

	public void setM(CoFinanceManualDisposableModel m) {
		this.m = m;
	}

	public CoBaseModel getCom() {
		return com;
	}

	public void setCom(CoBaseModel com) {
		this.com = com;
	}
}
