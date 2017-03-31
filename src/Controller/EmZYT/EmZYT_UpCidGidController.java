package Controller.EmZYT;

import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Messagebox.ClickEvent;
import org.zkoss.zul.Window;

import bll.EmZYT.EmZYT_OperateBll;
import bll.EmZYT.EmZYT_SelectBll;

import Model.CoBaseModel;
import Model.EmZYTModel;
import Model.EmbaseModel;
import Util.UserInfo;

public class EmZYT_UpCidGidController {
	private EmZYT_SelectBll sbll = new EmZYT_SelectBll();
	private EmZYT_OperateBll obll = new EmZYT_OperateBll();

	private List<EmbaseModel> emList;
	private List<CoBaseModel> coList;

	private int id = ((EmZYTModel) Executions.getCurrent().getArg()
			.get("emztM")).getId();
	private int gid = ((EmZYTModel) Executions.getCurrent().getArg()
			.get("emztM")).getGid();
	private int cid = ((EmZYTModel) Executions.getCurrent().getArg()
			.get("emztM")).getCid();
	private String emzt_name = ((EmZYTModel) Executions.getCurrent().getArg()
			.get("emztM")).getEmzt_name();
	private String emzt_idcard = ((EmZYTModel) Executions.getCurrent().getArg()
			.get("emztM")).getEmzt_idcard();
	private String emzt_company = ((EmZYTModel) Executions.getCurrent()
			.getArg().get("emztM")).getEmzt_company();
	private String emzt_mobile = ((EmZYTModel) Executions.getCurrent().getArg()
			.get("emztM")).getEmzt_mobile();

	public EmZYT_UpCidGidController() {
		// 可能符合的公司
		coList = sbll.getCobaseinfo(emzt_company);

		// 可能符合的员工
		emList = sbll.getembaList(emzt_idcard);
	}

	//提交
	@Command("submit")
	public void submit(@BindingParam("win") final Window win,@BindingParam("gid") Intbox gid,
			@BindingParam("cid") Intbox cid,
			@BindingParam("emzt_name") Textbox emzt_name,
			@BindingParam("emzt_idcard") Textbox emzt_idcard,
			@BindingParam("emzt_company") Textbox emzt_company,
			@BindingParam("emzt_mobile") Textbox emzt_mobile) {
		if (cid.getValue() != 0) {
			String[] message;

			EmZYTModel m = new EmZYTModel();
			m.setId(id);
			m.setCid(cid.getValue());
			if (gid.getValue() != 0) {
				m.setGid(gid.getValue());
			}
			m.setEmzt_name(emzt_name.getValue());
			m.setEmzt_idcard(emzt_idcard.getValue());
			m.setEmzt_company(emzt_company.getValue());
			m.setEmzt_mobile(emzt_mobile.getValue());
			m.setEmzt_addname(UserInfo.getUsername());

			message = obll.upEmZYTCidGid(m);
			if (message[0].equals("1")) {
				EventListener<ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
					public void onEvent(ClickEvent event) throws Exception {
						if (Messagebox.Button.OK.equals(event.getButton())) {
							// //跳转页面
							win.detach();
						}
					}
				};
				// 弹出提示
				Messagebox.show(message[1], "操作提示",
						new Messagebox.Button[] { Messagebox.Button.OK },
						Messagebox.INFORMATION, clickListener);
			} else {
				// 弹出提示
				Messagebox.show(message[1], "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}
		} else {
			Messagebox
					.show("请输入公司编号！", "操作提示", Messagebox.OK, Messagebox.ERROR);
		}
	}
	
	//公司选择
	@Command("chooseCid")
	@NotifyChange("cid")
	public void chooseCid(@BindingParam("data") int data){
		cid=data;
	}
	
	//员工选择
	@Command("chooseGid")
	@NotifyChange({"gid","cid"})
	public void chooseGid(@BindingParam("data") EmbaseModel data){
		if (cid== 0) {
			cid=data.getCid();
		}
		gid=data.getGid();
	}

	public List<EmbaseModel> getEmList() {
		return emList;
	}

	public void setEmList(List<EmbaseModel> emList) {
		this.emList = emList;
	}

	public List<CoBaseModel> getCoList() {
		return coList;
	}

	public void setCoList(List<CoBaseModel> coList) {
		this.coList = coList;
	}

	public int getGid() {
		return gid;
	}

	public void setGid(int gid) {
		this.gid = gid;
	}

	public int getCid() {
		return cid;
	}

	public void setCid(int cid) {
		this.cid = cid;
	}

	public String getEmzt_name() {
		return emzt_name;
	}

	public void setEmzt_name(String emzt_name) {
		this.emzt_name = emzt_name;
	}

	public String getEmzt_idcard() {
		return emzt_idcard;
	}

	public void setEmzt_idcard(String emzt_idcard) {
		this.emzt_idcard = emzt_idcard;
	}

	public String getEmzt_company() {
		return emzt_company;
	}

	public void setEmzt_company(String emzt_company) {
		this.emzt_company = emzt_company;
	}

	public String getEmzt_mobile() {
		return emzt_mobile;
	}

	public void setEmzt_mobile(String emzt_mobile) {
		this.emzt_mobile = emzt_mobile;
	}

}
