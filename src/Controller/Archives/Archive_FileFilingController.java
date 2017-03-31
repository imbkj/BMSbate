package Controller.Archives;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Path;
import org.zkoss.zul.Cell;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Window;

import bll.Archives.EmArchiveDatum_OperateBll;
import bll.Archives.EmArchive_SelectBll;
import bll.CoLatencyClient.CoLatencyClient_AddBll;
import bll.EmBodyCheck.EmBcInfo_SelectBll;
import bll.EmCensus.EmDh.EmDh_SelectBll;
import bll.Embase.EmbaseListBll;

import Model.EmArchiveDatumModel;
import Model.EmArchiveRemarkModel;
import Model.EmDhFileModel;
import Model.EmbaseModel;
import Model.TaskProcessViewModel;
import Util.RedirectUtil;
import Util.UserInfo;

public class Archive_FileFilingController {
	List<EmArchiveRemarkModel> listr;
	String id = Executions.getCurrent().getArg().get("daid") + "";
	String tperid =null;
	EmArchive_SelectBll bll = new EmArchive_SelectBll();
	EmArchiveRemarkModel modelr = new EmArchiveRemarkModel();
	EmArchiveDatumModel models = new EmArchiveDatumModel();
	String remarks = "";
	List<TaskProcessViewModel> tlist =new ArrayList<TaskProcessViewModel>();
	TaskProcessViewModel tmodel = new TaskProcessViewModel();
	CoLatencyClient_AddBll bll2 = new CoLatencyClient_AddBll();
	List<String> modell = bll2.getLoginInfo();
	private String gid = Executions.getCurrent().getArg().get("gid") + "";
	private String cid = Executions.getCurrent().getArg().get("cid") + "";
	EmBcInfo_SelectBll selectbll = new EmBcInfo_SelectBll();
	List<EmbaseModel> embaselist = selectbll
			.getEmBaseInfo(" and emba_state=1 and gid=" + gid);
	EmbaseModel emmodel = new EmbaseModel();
	private EmDh_SelectBll filebll = new EmDh_SelectBll();
	private List<EmDhFileModel> filelist = filebll.getFileing();

	@Command
	public void addwin(@BindingParam("win") Window winf) {
		if (gid != null && !gid.equals("")) {
			EmArchive_SelectBll bll = new EmArchive_SelectBll();
			int k = bll.getIfEmArchiveInfo(Integer.parseInt(gid));
			if (k <= 0) {
				Messagebox.show("该员工没有档案信息，不能做任何操作", "提示", Messagebox.OK,
						Messagebox.ERROR);
				reUrl();
			}
		} else {
			Messagebox.show("没有该员工信息，不能做任何操作", "提示", Messagebox.OK,
					Messagebox.ERROR);
			reUrl();
		}
	}

	private void reUrl() {
		RedirectUtil util = new RedirectUtil();
		util.refreshEmUrl("/EmCensus/EmRs_FileServerInfo.zul");
	}

	public Archive_FileFilingController() {
		if(Executions.getCurrent().getArg().get("id")!=null)
		{
			tperid=Executions.getCurrent().getArg().get("id").toString();
			tlist=bll.getLastId(tperid);
		}
		if (embaselist.size() > 0) {
			emmodel = embaselist.get(0);
			gid = emmodel.getGid() + "";
			cid = emmodel.getCid() + "";
		}
		if (id != null && id != "" && !id.equals("")) {
			List<EmArchiveDatumModel> ls = bll
					.getEmArchiveDatumInfo(" and eada_id=" + id);
			if (!ls.isEmpty()) {
				models = ls.get(0);
				gid = models.getGid() + "";
				cid = models.getCid() + "";
				listr = bll.getEmArchiveRemarkInfo(" and eare_trid=" + id
						+ " and eare_tid=1 order by eare_id desc");
				if (!listr.isEmpty()) {
					remarks = listr.get(0).getEare_content();
				}
				if (!tlist.isEmpty()) {
					tmodel = tlist.get(0);
				}
			}
		}
	}

	// 提交新增材料鉴别归档数据
	@Command
	public void summit(@BindingParam("dataclass") String dataclass,
			@BindingParam("winf") Window winf,
			@BindingParam("yorn") final Radio yorn,
			@BindingParam("remark") final String remark,
			@BindingParam("gd") Grid gd) {
		List<EmDhFileModel> chechlist = getCheckList(gd);
		if (chechlist.size() > 0) {
			for(EmDhFileModel m:chechlist)
			{
				if(dataclass==null||dataclass.equals(""))
				{
					dataclass=dataclass+m.getDhfl_file_content();
				}
				else
				{
					dataclass=dataclass+","+m.getDhfl_file_content();
				}
			}
			try {
				// 调用内联页方法chkHaveTo(Grid gd)
				// String[] message = docOC.AddchkHaveTo(docGrid);
				EmArchiveDatum_OperateBll bll = new EmArchiveDatum_OperateBll();
				// 判断材料必选项是否已选
				EmArchiveDatumModel model = new EmArchiveDatumModel();
				EmbaseModel emmodel = bll.getEmbaseinfo(" and gid=" + gid);
				model.setGid(Integer.parseInt(gid));
				model.setCid(Integer.parseInt(cid));
				model.setEada_file(dataclass);
				model.setEada_type(emmodel.getEmba_name() + "("
						+ emmodel.getGid() + ")材料鉴别归档申请");
				model.setEada_addname(UserInfo.getUsername());
				// 新增业务数据，并返回业务表ID
				String[] str = bll.EmArchiveFilingAdd(model, remark,
						yorn.getValue() + "");
				// 判断业务id是否为空
				if (str[0].equals("1")) {
					if (str[3] != null && !str[3].equals("")) {
						Integer dhid = Integer.parseInt(str[3]);
						AddFile(dhid,chechlist);
					}
					Messagebox.show(str[1], "操作提示", Messagebox.OK,
							Messagebox.INFORMATION);
					reUrl();
				} else {
					Messagebox.show(str[1], "操作提示", Messagebox.OK,
							Messagebox.ERROR);
				}
				// }

			} catch (Exception e) {
				System.out.println("错误：" + e.getMessage());
				Messagebox.show("操作失败。", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}
		} else {
			Messagebox
					.show("请选择拟归档材料", "提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	// 提交修改材料鉴别归档数据
	@Command
	public void summitupdate(@BindingParam("dataclass") final String dataclass,
			@BindingParam("win") final Window win,
			@BindingParam("yorn") final Radio yorn,
			@BindingParam("remark") final String remark) {
		if (dataclass != "" && !dataclass.equals("")) {
			try {
				// 调用内联页方法chkHaveTo(Grid gd)
				// String[] message = docOC.AddchkHaveTo(docGrid);
				EmArchiveDatum_OperateBll bll = new EmArchiveDatum_OperateBll();
				// 判断材料必选项是否已选
				// if (message[0].equals("1")) {
				EmArchiveDatumModel model = new EmArchiveDatumModel();
				model.setGid(Integer.parseInt(gid));
				model.setCid(Integer.parseInt(cid));
				model.setEada_id(Integer.parseInt(id));
				model.setEada_file(dataclass);
				model.setEada_type("确认数据");
				model.setEada_tapr_id(Integer.parseInt(tperid));
				model.setEada_addname(UserInfo.getUsername());
				String sql = ",eada_file='" + dataclass + "',eada_final="
						+ yorn.getValue();
				// 新增业务数据，并返回业务表ID
				String[] str = bll.EmArchiveDataFile(model, remark,
						yorn.getValue() + "", sql);
				// 判断业务id是否为空
				if (str[0].equals("1")) {

					Messagebox.show(str[1], "操作提示", Messagebox.OK,
							Messagebox.INFORMATION);
					win.detach();
				} else {
					Messagebox.show(str[1], "操作提示", Messagebox.OK,
							Messagebox.ERROR);
				}
				// }
			} catch (Exception e) {
				System.out.println("错误：" + e.getMessage());
				Messagebox.show("操作失败。", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}
		} else {
			Messagebox
					.show("请选择拟归档材料名称", "提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	// 全选
	@Command
	public void checkall(@BindingParam("ck") Checkbox ck,
			@BindingParam("gd") Grid gd) {
		for (int i = 0; i < filelist.size(); i++) {
			Cell cell = (Cell) gd.getCell(i, 0);
			if (cell.getChildren().size() > 0) {
				Checkbox cb = (Checkbox) cell.getChildren().get(0);
				cb.setChecked(ck.isChecked());
			}
		}
	}

	private List<EmDhFileModel> getCheckList(Grid gd) {
		List<EmDhFileModel> checklist = new ArrayList<EmDhFileModel>();
		for (int i = 0; i < filelist.size(); i++) {
			Cell cell = (Cell) gd.getCell(i, 0);
			if (cell.getChildren().size() > 0) {
				Checkbox cb = (Checkbox) cell.getChildren().get(0);
				EmDhFileModel m = cb.getValue();
				if (cb.isChecked()) {
					m.setIschecked(true);
					m.setDhfl_checked(1);
					if (!m.getFile_finame().equals("其他")) {
						m.setDhfl_file_content(m.getFile_finame());
					}
				} else {
					m.setIschecked(false);
					m.setDhfl_checked(0);
					if (!m.getFile_finame().equals("其他")) {
						m.setDhfl_file_content(m.getFile_finame());
					}
				}
				checklist.add(m);
			}
		}
		return checklist;
	}

	private Integer AddFile(Integer emdh_id,List<EmDhFileModel> slist) {
		Integer k=0;
		EmArchiveDatum_OperateBll oobll = new EmArchiveDatum_OperateBll();
		for (EmDhFileModel m : slist) {
			m.setDhfl_addname(UserInfo.getUsername());
			m.setDhfl_dh_id(emdh_id);
			m.setDhfl_file_id(m.getFile_id());
			k=k+oobll.FilingAdd(m);
		}
		return k;
	}

	public String getGid() {
		return gid;
	}

	public void setGid(String gid) {
		this.gid = gid;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public TaskProcessViewModel getTmodel() {
		return tmodel;
	}

	public void setTmodel(TaskProcessViewModel tmodel) {
		this.tmodel = tmodel;
	}

	public List<String> getModell() {
		return modell;
	}

	public void setModell(List<String> modell) {
		this.modell = modell;
	}

	public EmbaseModel getEmmodel() {
		return emmodel;
	}

	public void setEmmodel(EmbaseModel emmodel) {
		this.emmodel = emmodel;
	}

	public List<EmDhFileModel> getFilelist() {
		return filelist;
	}

	public void setFilelist(List<EmDhFileModel> filelist) {
		this.filelist = filelist;
	}

}
