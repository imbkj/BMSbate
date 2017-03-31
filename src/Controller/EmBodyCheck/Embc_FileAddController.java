package Controller.EmBodyCheck;

import java.io.InputStream;
import java.util.Date;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zul.A;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;

import bll.EmBodyCheck.EmBcFile_OperateBll;
import bll.EmBodyCheck.EmBcFile_selectBll;
import bll.EmBodyCheck.Embc_SetupSellectBll;

import Model.EmBcSetupAddressModel;
import Model.EmBcSetupModel;
import Model.EmbodyCheckFileModel;
import Util.DateStringChange;
import Util.FileOperate;
import Util.UserInfo;

public class Embc_FileAddController {
	private Embc_SetupSellectBll bll = new Embc_SetupSellectBll();
	private List<EmBcSetupModel> setuplist = bll.getEmBcSetupname("");
	private Media media;
	private String filename = "";
	private InputStream inputStream = null;
	private EmbodyCheckFileModel model = new EmbodyCheckFileModel();
	// 机构地址
	private List<EmBcSetupAddressModel> addresslist = bll
			.getEmBcSetupAddress("");
	private EmBcFile_selectBll filebll = new EmBcFile_selectBll();

	@Command
	public void upfile(@ContextParam(ContextType.TRIGGER_EVENT) UploadEvent ev,
			@BindingParam("btn") Button btn, @BindingParam("bel") Label bel,
			@BindingParam("cancel") A cancel) {
		media = ev.getMedia();
		filename = media.getName();
		String filefomat = media.getFormat();// 后缀
		if (!filefomat.equals("txt")) {
			this.inputStream = media.getStreamData();
			if (filename != null && !filename.equals("")) {
				bel.setValue(filename);
				bel.setVisible(true);
				cancel.setVisible(true);
				btn.setVisible(false);
			} else {
				bel.setVisible(false);
				cancel.setVisible(false);
				btn.setVisible(true);
			}
		}
	}

	@Command
	public void cancel(@BindingParam("btn") Button btn,
			@BindingParam("bel") Label bel, @BindingParam("cancelv") A cancelv) {
		bel.setVisible(false);
		cancelv.setVisible(false);
		btn.setVisible(true);
	}

	@Command
	@NotifyChange("addresslist")
	public void setup(@BindingParam("cmb") Combobox cmb) {
		if (cmb.getValue() != null && !cmb.getValue().equals("")) {
			Integer id = cmb.getSelectedItem().getValue();
			addresslist = bll.getEmBcSetupAddress(" and ebsa_ebcs_id=" + id);
		}
	}

	// 文件上传提交事件
	@Command
	public void summit(@BindingParam("setup") Combobox setup,
			@BindingParam("address") Combobox address) {
		if (setup.getValue() == null || setup.getValue().equals("")) {
			Messagebox.show("请选择体检医院", "提示", Messagebox.OK, Messagebox.ERROR);
		} else if (address.getValue() == null || address.getValue().equals("")) {
			Messagebox.show("请选择体检医院地址", "提示", Messagebox.OK, Messagebox.ERROR);
		} else if (filename == null || filename.equals("")) {
			Messagebox.show("请选择文件", "提示", Messagebox.OK, Messagebox.ERROR);
		} else {
			String name = "note"
					+ UserInfo.getUserid()
					+ DateStringChange
							.DatetoSting(new Date(), "yyyyMMddhhmmss") + "."
					+ media.getFormat();
			String realPath = "EmBodyCheck/file/";
			String file_url = realPath + name;
			model.setFile_url(file_url);
			model.setFile_filename(filename);
			model.setFile_ebcs_id(Integer.parseInt(setup.getSelectedItem()
					.getValue().toString()));
			model.setFile_ebsa_id(Integer.parseInt(address.getSelectedItem()
					.getValue().toString()));
			EmBcFile_OperateBll bll = new EmBcFile_OperateBll();
			if (media != null) {

				FileOperate.upload(media, file_url);
			}
			List<EmbodyCheckFileModel> flist = filebll
					.getEmbodyCheckFileList(" and file_state=1 and file_ebcs_id="
							+ Integer.parseInt(setup.getSelectedItem()
									.getValue().toString())
							+ " and file_ebsa_id="
							+ Integer.parseInt(address.getSelectedItem()
									.getValue().toString()));
			if (flist.size() > 0) {
				Messagebox.show("该医院已有须知信息，不能再添加", "提示", Messagebox.OK,
						Messagebox.ERROR);
			} else {
				Integer k = bll.EmbodyCheckFileAdd(model);
				if (k > 0) {
					Messagebox.show("提交成功", "提示", Messagebox.OK,
							Messagebox.INFORMATION);
					Executions.sendRedirect("/EmBodyCheck/Embc_FileAdd.zul");
				} else {
					Messagebox.show("提交失败", "提示", Messagebox.OK,
							Messagebox.ERROR);
				}
			}
		}
	}

	public List<EmBcSetupModel> getSetuplist() {
		return setuplist;
	}

	public void setSetuplist(List<EmBcSetupModel> setuplist) {
		this.setuplist = setuplist;
	}

	public List<EmBcSetupAddressModel> getAddresslist() {
		return addresslist;
	}

	public void setAddresslist(List<EmBcSetupAddressModel> addresslist) {
		this.addresslist = addresslist;
	}

	public EmbodyCheckFileModel getModel() {
		return model;
	}

	public void setModel(EmbodyCheckFileModel model) {
		this.model = model;
	}

}
