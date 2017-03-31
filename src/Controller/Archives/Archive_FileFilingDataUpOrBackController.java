package Controller.Archives;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import Controller.DocumentsInfo.DocumentsInfo_OperationController;
import Model.EmArchiveDatumModel;
import Model.EmArchiveRemarkModel;
import Model.EmDhFileModel;
import Model.TaskProcessViewModel;
import Util.UserInfo;
import bll.Archives.EmArchiveDatum_OperateBll;
import bll.Archives.EmArchive_SelectBll;
import bll.CoLatencyClient.CoLatencyClient_AddBll;
import bll.EmCensus.EmDh.EmDh_SelectBll;

public class Archive_FileFilingDataUpOrBackController {
	List<EmArchiveRemarkModel> listr;
	String id = (String) Executions.getCurrent().getArg().get("daid");
	String tperid =null;
	EmArchive_SelectBll bll = new EmArchive_SelectBll();
	EmArchiveRemarkModel modelr = new EmArchiveRemarkModel();
	List<EmArchiveDatumModel> mlists = new ArrayList<EmArchiveDatumModel>();
	EmArchiveDatumModel models = new EmArchiveDatumModel();
	String remarks = "";
	List<TaskProcessViewModel> tlist =new ArrayList<TaskProcessViewModel>();
	TaskProcessViewModel tmodel = new TaskProcessViewModel();
	CoLatencyClient_AddBll bll2 = new CoLatencyClient_AddBll();
	List<String> modell = bll2.getLoginInfo();
	private Integer gid;
	private Integer cid;
	private EmDh_SelectBll filebll = new EmDh_SelectBll();
	private List<EmDhFileModel> filelist = filebll.getDhFile(Integer
			.parseInt(id));

	public Archive_FileFilingDataUpOrBackController() {
		if(Executions.getCurrent().getArg().get("id")!=null)
		{
			tperid=Executions.getCurrent().getArg().get("id").toString();
			tlist=bll.getLastId(tperid);
		}
		mlists = bll.getEmArchiveDatumInfo(" and eada_id=" + id);
		if (mlists.size() > 0) {
			models = mlists.get(0);
		}
		gid = models.getGid();
		cid = models.getCid();
		listr = bll.getEmArchiveRemarkInfo(" and eare_trid=" + id
				+ " and eare_tid=1 order by eare_id desc");
		if (!listr.isEmpty()) {
			remarks = listr.get(0).getEare_content();
		}
		if (!tlist.isEmpty()) {
			tmodel = tlist.get(0);
		}
	}

	// 归档材料
	@Command
	public void summit(@BindingParam("win") final Window win) {
		EmArchiveDatum_OperateBll bll = new EmArchiveDatum_OperateBll();
		EmArchiveDatumModel model = new EmArchiveDatumModel();
		model.setGid(gid);
		model.setCid(cid);
		model.setEada_id(Integer.parseInt(id));
		model.setEada_type(tmodel.getWfno_name());
		model.setEada_tapr_id(Integer.parseInt(tperid));
		model.setEada_addname(UserInfo.getUsername());
		String sql = ",eada_filedate='" + getStringDate() + "',eada_final=3";
		// 新增业务数据，并返回业务表ID
		String[] str = bll.EmArchiveDataFile(model, "", "1", sql);
		// 判断业务id是否为空
		if (str[0].equals("1")) {

			Messagebox.show(str[1], "操作提示", Messagebox.OK,
					Messagebox.INFORMATION);
			win.detach();
		} else {
			Messagebox.show(str[1], "操作提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	//
	@Command
	public void summitback(@BindingParam("docGrid") final Grid docGrid,
			@BindingParam("win") final Window win) {
		EmArchiveDatum_OperateBll bll = new EmArchiveDatum_OperateBll();
		EmArchiveDatumModel model = new EmArchiveDatumModel();
		model.setGid(gid);
		model.setCid(cid);
		model.setEada_id(Integer.parseInt(id));
		model.setEada_type(tmodel.getWfno_name());
		model.setEada_tapr_id(Integer.parseInt(tperid));
		model.setEada_addname(UserInfo.getUsername());
		if (tmodel.getWfno_step() == 5) {
			DocumentsInfo_OperationController docOC = new DocumentsInfo_OperationController();
			try {

				String sql = ",eada_final=3,eada_backdate='" + getStringDate()
						+ "'";
				// 新增业务数据，并返回业务表ID
				String[] str = bll.EmArchiveDataFile(model, "", "1", sql);
				// 判断业务id是否为空
				if (str[0].equals("1")) {
					// 调用内联页方法submitDoc(Grid gd)
					String[] message = docOC.UpsubmitDocout(docGrid, id);
					if (message[0] == "1") {
						Messagebox.show(str[1], "操作提示", Messagebox.OK,
								Messagebox.INFORMATION);
						win.detach();
					} else {
						Messagebox.show("提交材料出错", "操作提示", Messagebox.OK,
								Messagebox.ERROR);
					}
				} else {
					Messagebox.show(str[1], "操作提示", Messagebox.OK,
							Messagebox.ERROR);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (tmodel.getWfno_step() == 6) {
			String sql = ",eada_final=3";
			// 新增业务数据，并返回业务表ID
			String[] str = bll.EmArchiveDataFile(model, "", "1", sql);
			if (str[0].equals("1")) {
				// 调用内联页方法submitDoc(Grid gd)
				Messagebox.show(str[1], "操作提示", Messagebox.OK,
						Messagebox.INFORMATION);
				win.detach();
			} else {
				Messagebox
						.show(str[1], "操作提示", Messagebox.OK, Messagebox.ERROR);
			}
		}
	}

	// 退回
	@Command
	public void back(@BindingParam("win") final Window win) {
		Map map = new HashMap<>();
		map.put("id", id);
		map.put("ta", tperid);
		map.put("model", models);
		map.put("flag", "0");
		Window window = (Window) Executions.createComponents(
				"../Archives/Archive_back.zul", null, map);
		window.doModal();
		if (map.get("flag") == "1") {
			win.detach();
		}
	}

	// 弹出备注
	@Command
	public void addremark(@BindingParam("win") final Window win) {
		Map map = new HashMap<>();
		map.put("id", id.toString());
		map.put("typeid", "2");
		map.put("gid",models.getGid());
		Window window = (Window) Executions.createComponents(
				"../Archives/Remark_AddList.zul", null, map);
		window.doModal();
	}

	@Command
	public void checkbox(@BindingParam("ck") Checkbox ck) {
		if (ck.isChecked()) {
			ck.setChecked(false);
		} else {
			ck.setChecked(true);
		}
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTperid() {
		return tperid;
	}

	public void setTperid(String tperid) {
		this.tperid = tperid;
	}

	public EmArchiveRemarkModel getModelr() {
		return modelr;
	}

	public void setModelr(EmArchiveRemarkModel modelr) {
		this.modelr = modelr;
	}

	public EmArchiveDatumModel getModels() {
		return models;
	}

	public void setModels(EmArchiveDatumModel models) {
		this.models = models;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
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

	public List<EmDhFileModel> getFilelist() {
		return filelist;
	}

	public void setFilelist(List<EmDhFileModel> filelist) {
		this.filelist = filelist;
	}

	public static String getStringDate() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(currentTime);
		return dateString;
	}

}
