package Controller.CoBase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.bouncycastle.asn1.cms.CMSAttributes;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Messagebox.ClickEvent;

import bll.CoBase.CoBase_OperateBll;

import Model.CoBaseModel;
import R.CM;
import Util.UserInfo;

public class CoBase_StopContoller {
	private CoBaseModel cbm = new CoBaseModel();
	private String errMessage = "";
	private boolean display = true;
	private Date st;
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	public Date getSt() {
		return st;
	}

	public void setSt(Date st) {
		this.st = st;
	}

	private CoBase_OperateBll bll = new CoBase_OperateBll();

	public CoBase_StopContoller() {
		if (Executions.getCurrent().getArg().get("cid") != null) {
			cbm.setCid(Integer.valueOf(Executions.getCurrent().getArg()
					.get("cid").toString()));
			cbm=bll.search(cbm.getCid());
			if (cbm.getCoba_stoptime()!=null && !cbm.getCoba_stoptime().equals("")) {
				try {
					st=sdf.parse(cbm.getCoba_stoptime());
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
		if (cbm.getCoba_servicestate()==0) {
			display = false;
			errMessage = "公司已解约";
		}

		if (bll.embaseInfo(cbm.getCid())) {
			display = false;
			errMessage = "有员工未操作离职";
		}
	}

	@Command
	public void submit() {

		if (st == null || st.equals("")) {
			Messagebox.show("请选择解约时间", "操作提示", Messagebox.OK, Messagebox.ERROR);
			return;
		}else {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			cbm.setCoba_stoptime(sdf.format(st));
		}
		if (cbm.getCoba_stopreason() == null
				|| cbm.getCoba_stopreason().equals("")) {
			Messagebox.show("请输入解约原因", "操作提示", Messagebox.OK, Messagebox.ERROR);
			return;
		}

		Messagebox.show("确认提交数据?", "操作提示", new Messagebox.Button[] {
				Messagebox.Button.OK, Messagebox.Button.CANCEL },
				Messagebox.QUESTION,
				new EventListener<Messagebox.ClickEvent>() {
					@Override
					public void onEvent(ClickEvent event) throws Exception {
						// TODO Auto-generated method stub

						if (Messagebox.Button.OK.equals(event.getButton())) {
							Integer i = bll.stopCobase(cbm.getCid(),
									cbm.getCoba_stoptime(),
									cbm.getCoba_stopreason(),
									cbm.getCoba_stopremark(),
									UserInfo.getUsername());
							if (i > 0) {
								Messagebox.show("修改成功.", "操作提示", Messagebox.OK,
										Messagebox.INFORMATION);

							} else {
								Messagebox.show("修改失败.", "操作提示", Messagebox.OK,
										Messagebox.ERROR);

							}
						}
					}
				});
	}

	public CoBaseModel getCbm() {
		return cbm;
	}

	public void setCbm(CoBaseModel cbm) {
		this.cbm = cbm;
	}

	public String getErrMessage() {
		return errMessage;
	}

	public void setErrMessage(String errMessage) {
		this.errMessage = errMessage;
	}

	public boolean isDisplay() {
		return display;
	}

	public void setDisplay(boolean display) {
		this.display = display;
	}

}
