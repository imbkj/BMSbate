package Controller.SocialInsurance;

import impl.MessageImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Include;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import service.MessageService;
import dal.LoginDal;

import C.F;
import Model.EmCommissionOutModel;
import Model.SocialInsuranceChangeModel;
import Model.SysMessageModel;
import Util.LoginInfoStatic;
import Util.UserInfo;
import bll.SocialInsurance.AlgorithmChangeBll;
import bll.SocialInsurance.Algorithm_ChangeOperateBll;
import bll.SocialInsurance.Algorithm_InfoBll;
import bll.SocialInsurance.Algorithm_RegisteredDataBll;


public class Algorithm_ExaminedController {
	private final int sich_id = Integer.parseInt(Executions.getCurrent()
			.getArg().get("daid").toString());
	private boolean submitVis;
	private AlgorithmChangeBll selBll;
	private SocialInsuranceChangeModel sicModel;
	String[] message = new String[2];
	
	public Algorithm_ExaminedController() {
		selBll = new AlgorithmChangeBll();
		sicModel = selBll.getSocialInsuranceChange(sich_id);
		try {
			submitVis = Boolean.valueOf(Executions.getCurrent().getArg()
					.get("submitVis").toString());
		} catch (Exception e) {
			submitVis = true;
		}
	}
	
	@Command
	public void frashFrame(
			)  {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				 
			
				//更新在册数据getSial_execdate
				//插入更新数据库的情况
				LoginInfoStatic.setSetupdatestate(true);
				System.out.println(sicModel.getSich_soin_id());
				Algorithm_RegisteredDataBll bll = new Algorithm_RegisteredDataBll();
				message=bll.upRegData(sicModel.getSich_soin_id(), sicModel.getSich_sial_id(), sicModel.getSial_execdate());
				//插入变更表
				Algorithm_InfoBll bllbg = new Algorithm_InfoBll();
				 List<EmCommissionOutModel> ecouList;
				ecouList = bllbg.getRegDataBySoinId(sicModel.getSich_soin_id());
				for(EmCommissionOutModel m:ecouList)
				{
					System.out.println(m.getGid());
					m.setEcou_addname("系统");
					bll.insertchangeDate(m.getEcou_id());	
				}
	
				LoginInfoStatic.setSetupdatestate(false);
				
			}
		}).start();
	}

	// 审核通过并生效
	@Command("confirm")
	public void confirm(@BindingParam("win") Window win) {
		try {
			if (Messagebox.show("审核通过后，该算法变更将即刻生效，确认此操作吗？", "操作提示",
					Messagebox.YES | Messagebox.NO, Messagebox.QUESTION) == Messagebox.YES) {
				Algorithm_ChangeOperateBll opbll = new Algorithm_ChangeOperateBll();
				String[] message = opbll.ConfirmSiChange(sicModel,
						UserInfo.getUsername());
				
				
				if ("1".equals(message[0])) {
				
					frashFrame();
				
					if (Integer.parseInt(message[0])!=1)
					{
					
					// 弹出提示
					Messagebox.show(message[1], "操作提示", Messagebox.OK,
							Messagebox.NONE);
					
					// 发邮件和系统短信
					MessageService msgservice = new MessageImpl("SocialInsuranceChange",
							sicModel.getSich_id());
					LoginDal d = new LoginDal();
					SysMessageModel sysm = new SysMessageModel();
					String msgstr = "城市:" + sicModel.getCity() + "机构:"
							+ sicModel.getCoab_name()   + "字典库:"
									+ sicModel.getSich_title()+":"+sicModel.getSich_soin_id()+ "操作变更，请注意核查在册表！";
					sysm.setSyme_title("字典库在册数据更新核查");
					sysm.setSyme_content(msgstr);// 短信内容
					sysm.setSyme_log_id(d.getUserIDByname(UserInfo
							.getUsername()));// 发件人id
					sysm.setEmail(1);
					sysm.setEmailtitle("字典库在册数据更新核查");
					sysm.setSymr_name("赵敏捷");// 收件人姓名
					sysm.setSymr_log_id(d.getUserIDByname("赵敏捷"));// ;
					msgservice.Add(sysm);
					sysm.setSymr_name("张志强");// 收件人姓名
					sysm.setSymr_log_id(d.getUserIDByname("张志强"));// ;
					msgservice.Add(sysm);
					}
					win.detach();

				} else {
					// 弹出提示
					Messagebox.show(message[1], "操作提示", Messagebox.OK,
							Messagebox.ERROR);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox.show("操作出错。", "操作提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	// 查看算法
	@Command("searchAl") 
	public void searchAl() {
		try {
			Map<String, String> map = new HashMap<String, String>();
			Window window;
			switch (sicModel.getSich_change_type()) {
			case 1:
				map.put("sich_id", String.valueOf(sicModel.getSich_id()));
				map.put("submitVis", Boolean.toString(submitVis));
				window = (Window) Executions.createComponents(
						"../SocialInsurance/Algorithm_EditChange.zul", null,
						map);
				window.doModal();
				break;
			case 2:
			case 3:
				map.put("sich_id", String.valueOf(sicModel.getSich_id()));
				map.put("submitVis", Boolean.toString(submitVis));
				window = (Window) Executions.createComponents(
						"../SocialInsurance/Algorithm_EditUpdateChange.zul", null,
						map);
				window.doModal();
				break;
			case 4:
			case 5:
				map.put("sial_id", String.valueOf(sicModel.getSich_sial_id()));
				window = (Window) Executions.createComponents(
						"../SocialInsurance/Algorithm_Search.zul", null, map);
				window.doModal();
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 终止流程
	@Command("StopTask")
	public void StopTask(@BindingParam("win") Window win) {
		try {
			if (Messagebox.show("确认终止该任务吗？", "操作提示", Messagebox.YES
					| Messagebox.NO, Messagebox.QUESTION) == Messagebox.YES) {
				Algorithm_ChangeOperateBll opbll = new Algorithm_ChangeOperateBll();
				String[] message = opbll.StopTask(sicModel,
						UserInfo.getUsername());
				if ("1".equals(message[0])) {
					// 弹出提示
					Messagebox.show(message[1], "操作提示", Messagebox.OK,
							Messagebox.NONE);
					win.detach();

				} else {
					// 弹出提示
					Messagebox.show(message[1], "操作提示", Messagebox.OK,
							Messagebox.ERROR);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox.show("操作出错。", "操作提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	// 重新提交
	@Command("resubmit")
	public void resubmit(@BindingParam("win") Window win) {
		try {
			if (Messagebox.show("确认重新提交该算法吗？", "操作提示", Messagebox.YES
					| Messagebox.NO, Messagebox.QUESTION) == Messagebox.YES) {
				Algorithm_ChangeOperateBll opbll = new Algorithm_ChangeOperateBll();
				String[] message = opbll.resubmit(sicModel,
						UserInfo.getUsername());
				if ("1".equals(message[0])) {
					// 弹出提示
					Messagebox.show(message[1], "操作提示", Messagebox.OK,
							Messagebox.NONE);
					win.detach();

				} else {
					// 弹出提示
					Messagebox.show(message[1], "操作提示", Messagebox.OK,
							Messagebox.ERROR);
				}
			}
		} catch (Exception e) {
			// 弹出提示
			Messagebox.show("操作出错。", "操作提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	// 退回
	@Command("returnPre")
	public void returnPre(@BindingParam("win") Window win) {
		try {
			if (sicModel.getSich_ReturnReason() != null
					&& !"".equals(sicModel.getSich_ReturnReason().trim())) {
				if (Messagebox.show("确认退回该算法变更吗？", "操作提示", Messagebox.YES
						| Messagebox.NO, Messagebox.QUESTION) == Messagebox.YES) {
					Algorithm_ChangeOperateBll opbll = new Algorithm_ChangeOperateBll();
					String[] message = opbll.returnPre(sicModel,
							UserInfo.getUsername());
					if ("1".equals(message[0])) {
						// 弹出提示
						Messagebox.show(message[1], "操作提示", Messagebox.OK,
								Messagebox.NONE);
						win.detach();

					} else {
						// 弹出提示
						Messagebox.show(message[1], "操作提示", Messagebox.OK,
								Messagebox.ERROR);
					}
				}
			} else {
				Messagebox.show("请先填写退回原因。", "操作提示", Messagebox.OK,
						Messagebox.INFORMATION);
			}
		} catch (Exception e) {
			// 弹出提示
			Messagebox.show("操作出错。", "操作提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	public int getSich_id() {
		return sich_id;
	}

	public SocialInsuranceChangeModel getSicModel() {
		return sicModel;
	}

	public boolean isSubmitVis() {
		return submitVis;
	}

}
