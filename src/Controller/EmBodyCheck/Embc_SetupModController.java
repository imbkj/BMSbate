package Controller.EmBodyCheck;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.zkoss.bind.BindContext;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zul.Menuitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;
import org.zkoss.zul.Messagebox.ClickEvent;

import Model.EmBcSetupAddressModel;
import Model.EmBcSetupModel;
import Util.FileOperate;
import Util.UserInfo;
import bll.EmBodyCheck.EmBcSetup_OperateBll;
import bll.EmBodyCheck.Embc_SetupSellectBll;

public class Embc_SetupModController {

	private EmBcSetupModel esm;
	private List<EmBcSetupAddressModel> alist;
	private Embc_SetupSellectBll bll1 = new Embc_SetupSellectBll();
	private EmBcSetup_OperateBll bll2 = new EmBcSetup_OperateBll();
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	private String addName;
	private String uploadFlieName;
	private String uploadfolder;

	private Window win;

	public Embc_SetupModController() throws Exception {
		if (Executions.getCurrent().getArg().get("model") != null) {
			EmBcSetupModel m = (EmBcSetupModel) Executions.getCurrent()
					.getArg().get("model");
			List<EmBcSetupModel> list = bll1.getEmBcSetupList(m);
			if (list.size() > 0) {
				esm = list.get(0);
				if (esm.getEbcs_inuredate() != null
						&& !esm.getEbcs_inuredate().equals("")) {
					esm.setInuredate(sdf.parse(esm.getEbcs_inuredate()));
				}
				if (esm.getEbcs_indate() != null
						&& !esm.getEbcs_indate().equals("")) {
					esm.setIndate(sdf.parse(esm.getEbcs_indate()));
				}

				esm.setPstate(esm.getEbcs_pstate().equals(1) ? "生效" : "取消");
				esm.setStateName(esm.getEbcs_state().equals(1) ? "生效" : "终止");
				alist = bll1.getEmBcSetupAddressInfo(esm.getEbcs_id());
			}
		}

	}

	@Command
	public void winC(@BindingParam("a") Window w) {
		this.win = w;
	}

	@Command
	@NotifyChange("alist")
	public void searchAdd() {
		if (addName != null && !addName.equals("")) {
			for (EmBcSetupAddressModel m : alist) {
				if (m.getEbsa_address().contains(addName)) {
					m.setDisplay(true);
				} else {
					m.setDisplay(false);
				}
			}
		} else {
			for (EmBcSetupAddressModel m : alist) {
				m.setDisplay(true);
			}
		}
	}

	@Command
	public void menu(@ContextParam(ContextType.BIND_CONTEXT) BindContext ul,
			@BindingParam("a") Menuitem mi,
			@BindingParam("b") EmBcSetupAddressModel am) {
		switch (mi.getLabel()) {
		case "上传":

			UploadEvent upEvent = (UploadEvent) ul.getTriggerEvent();
			Media media = upEvent.getMedia();
			if ("doc".equals(media.getFormat())
					|| "docx".equals(media.getFormat())) {
				uploadFlieName = media.getName();
				String uploadName = mosaicFileName();
				uploadfolder = "/OfficeFile/UpLoad/EmbodyCheck/";
				if (FileOperate.upload(media, uploadfolder + uploadName)) {
					if (bll2.UpdateEmBcSetupAddressFile(uploadfolder
							+ uploadName, am.getEbsa_id()) > 0) {
						am.setEbsa_doc(uploadfolder + uploadName);
					}
					Messagebox.show("上传成功", "操作提示", Messagebox.OK,
							Messagebox.INFORMATION);
				} else {
					Messagebox.show("上传失败", "操作提示", Messagebox.OK,
							Messagebox.ERROR);
				}

			}
			break;
		case "下载":

			if (am.getEbsa_doc() != null && !am.getEbsa_doc().equals("")) {
				FileOperate.download(am.getEbsa_doc());
			} else {
				Messagebox.show("该地址还没有介绍信模版", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}

			break;
		default:
			break;
		}
	}

	@Command
	public void submit() {
		if (esm.getPstate() != null) {
			if (esm.getPstate().equals("生效")) {
				esm.setEbcs_pstate(1);
			} else {
				esm.setEbcs_pstate(0);
			}
		}
		if (esm.getStateName() != null) {
			if (esm.getStateName().equals("生效")) {
				esm.setEbcs_state(1);
			} else {
				esm.setEbcs_state(0);
			}
		}
		if (esm.getInuredate() != null && !esm.getInuredate().equals("")) {
			esm.setEbcs_inuredate(sdf.format(esm.getInuredate()));
		}
		if (esm.getIndate() != null && !esm.getIndate().equals("")) {
			esm.setEbcs_indate(sdf.format(esm.getIndate()));
		}

		if (esm.getEbcs_hospital() == null || esm.getEbcs_hospital().equals("")) {
			Messagebox.show("请输入机构名称", "操作提示", Messagebox.OK, Messagebox.ERROR);
			return;
		} else {
			if (bll2.checkEmbcSetUp(esm.getEbcs_hospital(), esm.getEbcs_id())) {
				Messagebox.show("该机构已存在.", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
				return;
			}
		}
		Messagebox.show("确认变更数据?", "操作提示", new Messagebox.Button[] {
				Messagebox.Button.OK, Messagebox.Button.CANCEL },
				Messagebox.QUESTION,
				new EventListener<Messagebox.ClickEvent>() {
					@Override
					public void onEvent(ClickEvent event) throws Exception {
						// TODO Auto-generated method stub

						if (Messagebox.Button.OK.equals(event.getButton())) {

							int k = bll2.EmBcSetupUpdate(esm);
							int j = 0;
							Date d = new Date();
							for (EmBcSetupAddressModel m : alist) {
								m.setEbsa_modtime(sdf.format(d));
								m.setEbsa_modname(UserInfo.getUsername());
								m.setEbsa_state(m.getStateName().equals("有效") ? 1
										: 0);
								m.setEbsa_ystate(m.isEbsa_ychecked() ? 1 : 0);
								m.setEbsa_istate(m.isEbsa_ichecked() ? 1 : 0);
								m.setEbsa_w1(m.isW1() ? 1 : 0);
								m.setEbsa_w2(m.isW2() ? 1 : 0);
								m.setEbsa_w3(m.isW3() ? 1 : 0);
								m.setEbsa_w4(m.isW4() ? 1 : 0);
								m.setEbsa_w5(m.isW5() ? 1 : 0);
								m.setEbsa_w6(m.isW6() ? 1 : 0);
								m.setEbsa_w7(m.isW7() ? 1 : 0);
								j = bll2.UpdateEmBcSetupAddress(m);
							}

							if (k > 0 && j > 0) {
								Messagebox.show("更新成功", "提示", Messagebox.OK,
										Messagebox.INFORMATION);
								if (win != null) {
									win.detach();
								}

							} else {
								Messagebox.show("更新失败", "提示", Messagebox.OK,
										Messagebox.ERROR);
							}
						}
					}
				});
	}

	public EmBcSetupModel getEsm() {
		return esm;
	}

	public void setEsm(EmBcSetupModel esm) {
		this.esm = esm;
	}

	public List<EmBcSetupAddressModel> getAlist() {
		return alist;
	}

	public void setAlist(List<EmBcSetupAddressModel> alist) {
		this.alist = alist;
	}

	public String getAddName() {
		return addName;
	}

	public void setAddName(String addName) {
		this.addName = addName;
	}

	// 拼接上传文件的名称
	private String mosaicFileName() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date();
		String nowtime = sdf.format(date);
		String name = nowtime;
		return name;
	}
}
