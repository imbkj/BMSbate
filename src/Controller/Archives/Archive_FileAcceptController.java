package Controller.Archives;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import Controller.DocumentsInfo.DocumentsInfo_OperationController;
import Model.EmArchiveDatumModel;
import Model.EmArchiveModel;
import Model.EmGatheringModel;
import Util.UserInfo;
import bll.Archives.EmArchiveDatum_OperateBll;
import bll.Archives.EmArchive_SelectBll;
import bll.EmFinanceManage.emgt_selectBll;

public class Archive_FileAcceptController {
	private String id = (String) Executions.getCurrent().getArg().get("daid");
	private String tperid =null;
	private EmArchive_SelectBll bll = new EmArchive_SelectBll();
	private List<EmArchiveDatumModel> list = bll
			.getEmArchiveDatumInfo(" and eada_id=" + id);
	EmArchiveDatumModel model = new EmArchiveDatumModel();
	private String stopdate = "";
	private List<EmGatheringModel> gatlist = new ArrayList<EmGatheringModel>();

	EmArchiveModel amodel = new EmArchiveModel();
	List<EmArchiveModel> archivelist = new ArrayList<EmArchiveModel>();

	public Archive_FileAcceptController() {
		if(Executions.getCurrent().getArg().get("id")!=null)
		{
			tperid =Executions.getCurrent().getArg().get("id").toString();
		}
		if (list.size() > 0) {
			model = list.get(0);
		}
		archivelist = bll.getEmArchiveInfo(" and emar_state=1 and a.gid="
				+ model.getGid()+" and emar_fid='"+model.getEada_fid()+"'");
		if (archivelist.size() > 0) {
			amodel = archivelist.get(0);
			setAmodel(amodel);
		}
		if(model.getEada_fileplace()==null||model.getEada_fileplace().equals(""))
		{
			model.setEada_fileplace(amodel.getEmar_archiveplace());
		}
		stopdate = bll.getstopdate(Integer.parseInt(id));
		emgt_selectBll gatbll = new emgt_selectBll();
		gatlist = gatbll.getEmGatheringList(" and a.gid=" + model.getGid());
	}

	@Command
	public void summit(@BindingParam("gd") Grid gd,
			@BindingParam("win") Window win) {
		DocumentsInfo_OperationController docOC = new DocumentsInfo_OperationController();
		try {
			// 调用内联页方法chkHaveTo(Grid gd)
			String[] message = docOC.AddchkHaveTo(gd);
			EmArchiveDatum_OperateBll blloper = new EmArchiveDatum_OperateBll();
			// 判断材料必选项是否已选
			if (message[0].equals("1")) {
				EmArchiveDatumModel model = new EmArchiveDatumModel();
				model.setGid(model.getGid());
				model.setCid(model.getCid());
				model.setEada_type("受理材料");
				model.setEada_addname(UserInfo.getUsername());
				model.setEada_tapr_id(Integer.parseInt(tperid));
				model.setEada_id(Integer.parseInt(id));
				String sql = ",eada_final=2";
				// 修改业务数据，并返回业务表ID
				String[] str = blloper
						.EmArchiveCheckOut(model, "", "1", sql, 0);
				// 判断业务id是否为空
				if (str[0].equals("1")) {
					// 调用内联页方法submitDoc(Grid gd)
					message = docOC.AddsubmitDoc(gd, id);
					Messagebox.show("提交成功", "操作提示", Messagebox.OK,
							Messagebox.INFORMATION);
					win.detach();
				} else {
					Messagebox.show("提交失败", "操作提示", Messagebox.OK,
							Messagebox.ERROR);
				}
			}
		} catch (Exception e) {
			System.out.println("错误：" + e.getMessage());
			Messagebox.show("操作失败。", "操作提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	// 弹出备注
	@Command
	public void addremark(@BindingParam("win") final Window win) {
		Map map = new HashMap<>();
		map.put("id", id.toString());
		map.put("typeid", "2");
		map.put("gid",model.getGid());
		Window window = (Window) Executions.createComponents(
				"../Archives/Remark_AddList.zul", null, map);
		window.doModal();
	}

	public EmArchiveModel getAmodel() {
		return amodel;
	}

	public void setAmodel(EmArchiveModel amodel) {
		this.amodel = amodel;
	}

	public EmArchiveDatumModel getModel() {
		return model;
	}

	public void setModel(EmArchiveDatumModel model) {
		this.model = model;
	}

	public String getStopdate() {
		return stopdate;
	}

	public void setStopdate(String stopdate) {
		this.stopdate = stopdate;
	}

	public List<EmGatheringModel> getGatlist() {
		return gatlist;
	}

	public void setGatlist(List<EmGatheringModel> gatlist) {
		this.gatlist = gatlist;
	}

}
