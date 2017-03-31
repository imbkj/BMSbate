package Controller.EmSheBao;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import Model.EmShebaoBJModel;
import Util.UserInfo;
import bll.EmSheBao.Emsc_DeclareOperateBll;
import bll.EmSheBao.Emsc_DeclareSelBll;
import bll.EmSheBao.Emsi_SelBll;
import bll.Embase.EmBase_gdBll;
import bll.Taskflow.Task_controlBll;

public class Emsi_BjReSendController {
	private final int id = Integer.parseInt(Executions.getCurrent().getArg()
			.get("daid").toString());
	private EmShebaoBJModel bjModel;
	private Emsc_DeclareSelBll selbll;
	private String[] ownmonthList;
	private String ownmonth;
	
	private boolean ifStop;
 
	private Emsi_SelBll bll = new Emsi_SelBll();
	
	public Emsi_BjReSendController() {
		selbll = new Emsc_DeclareSelBll();
		bjModel = selbll.getBjInfoById(id);
		
		ifStop = bll.ifStop();
		// 判断是否停止当月操作社保
		if (ifStop) {
			ownmonthList = bll.getOwnmonthByNow(false);
		} else {
			ownmonthList = bll.getOwnmonthByNow(true);
		}
		
		ownmonth=String.valueOf(bjModel.getOwnmonth());
	}

	@Command("reSend")
	public void confirmBj(@BindingParam("win") Window win) {
		if (Messagebox.show("是否确认重新发送该社保补缴信息？", "操作提示", Messagebox.YES
				| Messagebox.NO, Messagebox.QUESTION) == Messagebox.YES) {
			bjModel.setOwnmonth(Integer.parseInt(ownmonth));
			
			try {
				
				// 修改状态为未申报
				
				if (Integer.parseInt(UserInfo.getDepID().toString())==16 || Integer.parseInt(UserInfo.getDepID().toString())==8) //雇员服务中心重新提交
				{
					if (bjModel.getEmsb_single() == 1) {
						
						bjModel.setEmsb_ifdeclare(0);
						
					} else {
						bjModel.setEmsb_ifdeclare(8);
					}
					
					bjModel.setEmsb_addname(UserInfo.getUsername());
				}
				else //客服重新提交
				{
					 
					bjModel.setEmsb_ifdeclare(4);
					bjModel.setEmsb_addname(bjModel.getEmsb_addname());
	
				}
				
				
				
				bjModel.setEmsb_ifdeclarestr("重新发送社保补缴");
				Emsc_DeclareOperateBll opbll = new Emsc_DeclareOperateBll();
				
				
				
				String[] mes = opbll.BjDeclareUpState(bjModel,
						UserInfo.getUsername());
				if ("1".equals(mes[0])) {
					// 成功提示
					
					if (Integer.parseInt(UserInfo.getDepID().toString())!=16 && Integer.parseInt(UserInfo.getDepID().toString())!=8)
					{
						Task_controlBll tbll =new Task_controlBll();
						
						if (tbll.setOpName(bjModel.getEmsb_tapr_id(),bjModel.getEmsb_addname())==1)
						{
							//清除embasegd 退回状态
							EmBase_gdBll gdbll = new EmBase_gdBll();	
						 
							gdbll.modinfo(4,bjModel.getId(),"");
							 
							// 弹出提示
							Messagebox.show(mes[1], "操作提示", Messagebox.OK,
									Messagebox.NONE);
						}
						else
						{
							Messagebox.show("权限异常，请联系IT部", "操作提示", Messagebox.OK,
									Messagebox.NONE);
						}
						 
					}
					
					
					
				 
					win.detach();
				} else {
					// 错误提示
					Messagebox.show(mes[1], "操作提示", Messagebox.OK,
							Messagebox.ERROR);
				}
			} catch (Exception e) {
				Messagebox.show("重新发送社保补缴出错。", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}
		}
	}

	public EmShebaoBJModel getBjModel() {
		return bjModel;
	}

	public String[] getOwnmonthList() {
		return ownmonthList;
	}

	public void setOwnmonthList(String[] ownmonthList) {
		this.ownmonthList = ownmonthList;
	}

	public String getOwnmonth() {
		return ownmonth;
	}

	public void setOwnmonth(String ownmonth) {
		this.ownmonth = ownmonth;
	}

	public boolean isIfStop() {
		return ifStop;
	}

	public void setIfStop(boolean ifStop) {
		this.ifStop = ifStop;
	}

}
