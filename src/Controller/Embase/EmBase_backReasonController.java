package Controller.Embase;

import impl.MessageImpl;
import impl.WorkflowCore.WfOperateImpl;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import dal.LoginDal;

import service.MessageService;
import service.WorkflowCore.WfBusinessService;
import service.WorkflowCore.WfOperateService;
import bll.CoBase.CoBase_SelectBll;
import bll.EmZYT.EmZYT_OperateBll;
import bll.Embase.Embase_OnBoardImpl;

import Model.EmZYTModel;
import Model.EmbaseModel;
import Model.SysMessageModel;
import Util.UserInfo;
import bll.Embase.EmBase_OnBoardBll;
import bll.Taskflow.Task_controlBll;
public class EmBase_backReasonController {

	private int taprid = (Integer) Executions.getCurrent().getArg()
			.get("taprid");
	private int daid = (Integer) Executions.getCurrent().getArg().get("daid");
	
	private Window win;
	private String backReason = null;
	private EmbaseModel emmsg = new EmbaseModel();

	private String winId = "winAdd"
			+ java.util.concurrent.ThreadLocalRandom.current().nextLong(4000,
					4999);

	public EmBase_backReasonController() {
		EmBase_OnBoardBll emb=new EmBase_OnBoardBll();
		emmsg=emb.getEmbaseInfo(daid).get(0);

	}

	@Command
	public void WinD(@BindingParam("a") Window w) {
		win = w;
	}

	@Command
	public void submit() {
		if (backReason != null) {

			EmbaseModel em = new EmbaseModel();
			em.setEmba_state(3);
			em.setEmba_remark(backReason);
			em.setEmba_id(daid);
			em.setEmba_modname(UserInfo.getUsername());
			WfBusinessService wfbs = new Embase_OnBoardImpl();
			WfOperateService wfs = new WfOperateImpl(wfbs);
			Object[] obj = { "退回上一步", em };
			String[] str = wfs.ReturnToPrev(obj, taprid,
					UserInfo.getUsername(), backReason);
			
		
			
			if (str[0].equals("1")) {
				
				Task_controlBll tbll =new Task_controlBll();
				//CoBase_SelectBll cobll = new CoBase_SelectBll();
				//修改退回后节点权限（客服）
			if (tbll.setOpName(Integer.parseInt(str[2].toString()),emmsg.getCoba_client())==1)
				{
				// 发邮件和系统短信
			      MessageService msgservice=new MessageImpl("embase",emmsg.getEmba_id());
			      LoginDal d =new LoginDal();
				      SysMessageModel sysm =new SysMessageModel();
			      String msgstr="公司:"+emmsg.getCoba_company()+"的"+emmsg.getEmba_name()+"入职被退回";
			      sysm.setSyme_title(msgstr);
			      sysm.setSyme_content(msgstr);//短信内容
			      sysm.setSyme_log_id(d.getUserIDByname(UserInfo.getUsername()));//发件人id
			      sysm.setSymr_name(emmsg.getCoba_client());//收件人姓名
			      sysm.setSymr_log_id(d.getUserIDByname(emmsg.getCoba_client()));//;
			      sysm.setEmail(1);
			      sysm.setEmailtitle(msgstr);
			      msgservice.Add(sysm);
				

				Messagebox.show(str[1], "操作提示", Messagebox.OK,
						Messagebox.INFORMATION);
				win.detach();
				}
				else
				{
					Messagebox
					.show("退回失败，请联系IT部门!", "操作提示", Messagebox.OK, Messagebox.ERROR);
				}

			} else {
				Messagebox
						.show(str[1], "操作提示", Messagebox.OK, Messagebox.ERROR);
			}

		} else {
			Messagebox
					.show("请填写退回原因!", "操作提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	public String getBackReason() {
		return backReason;
	}

	public void setBackReason(String backReason) {
		this.backReason = backReason;
	}

	public String getWinId() {
		return winId;
	}

	public void setWinId(String winId) {
		this.winId = winId;
	}

}
