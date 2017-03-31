package Controller.CoHousingFund;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Iframe;
import org.zkoss.zul.Include;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;
import org.zkoss.zul.Messagebox.ClickEvent;

import bll.CoHousingFund.CoHouseFileBll;

import Model.CoHousingFundFileModel;
import Util.FileOperate;
import Util.UserInfo;

public class CoHouseFileController {
	private List<CoHousingFundFileModel> filelist;
	private String FileUrl;
	private Integer pid;
	private String searchName = "";
	private boolean modState = false;

	private CoHouseFileBll bll = new CoHouseFileBll();

	private Window w = (Window) Path.getComponent("/winCF");

	public CoHouseFileController() {
		// FileUrl="../OfficeFile/UpLoad/CoHousingFund/workbook/index.pdf";
		filelist = bll.getFilelistShow(0);
		pid = 0;
		if (UserInfo.getDepID().equals("10")) {
			modState = true;
		}
		if (filelist.size() > 0) {
		
			if (filelist.get(0).getChff_url() != null) {
				/*
				FileUrl = "../CoHousingFund/CoHouseFile.jsp?filename="
						+ filelist.get(0).getChff_url() + "&t="
						+ filelist.get(0).getChff_name();
						*/
			}
		}

	}

	@Command
	@NotifyChange({ "filelist" })
	public void search() {
		filelist = bll.getFilelist(searchName);
	}

	@Command
	@NotifyChange({ "filelist", "FileUrl" })
	public void add() {
		CoHousingFundFileModel cfm = new CoHousingFundFileModel();
		cfm.setChff_pid(pid);
		Map map = new HashMap();
		map.put("m", cfm);
		Window window = (Window) Executions.createComponents(
				"CoHouseFile_add.zul", null, map);
		window.doModal();

		if (cfm.getUploadState() != null) {
			if (cfm.getUploadState()) {

				filelist = bll.getFilelistShow(pid);
				if (filelist.size() > 0) {
					FileUrl = "../CoHousingFund/CoHouseFile.jsp?filename="
							+ filelist.get(0).getChff_url() + "&t="
							+ filelist.get(0).getChff_name();
					Include ic = (Include) w.getFellow("ifr");
					ic.setSrc(FileUrl);
				}
			}
		}
	}

	@Command
	@NotifyChange({ "filelist", "FileUrl" })
	public void mod(@BindingParam("a") final CoHousingFundFileModel m) {
		Map map = new HashMap();
		map.put("m", m);
		Window window = (Window) Executions.createComponents(
				"CoHouseFile_mod.zul", null, map);
		window.doModal();
	}

	@Command
	@NotifyChange({ "filelist", "FileUrl" })
	public void del(@BindingParam("a") final CoHousingFundFileModel m) {

		Messagebox.show("确认删除数据?", "操作提示", new Messagebox.Button[] {
				Messagebox.Button.OK, Messagebox.Button.CANCEL },
				Messagebox.QUESTION,
				new EventListener<Messagebox.ClickEvent>() {
					@Override
					public void onEvent(ClickEvent event) throws Exception {
						// TODO Auto-generated method stub
						if (Messagebox.Button.OK.equals(event.getButton())) {
							Integer i = bll.del(m.getChff_id());
							if (i > 0) {
								filelist = bll.getFilelistShow(pid);
								if (filelist.size() > 0) {
									FileUrl = "../CoHousingFund/CoHouseFile.jsp?filename="
											+ filelist.get(0).getChff_url()
											+ "&t="
											+ filelist.get(0).getChff_name();
									Include ic = (Include) w.getFellow("ifr");
									ic.setSrc(FileUrl);
								} else {
									pid = bll.getPid(pid);
									filelist = bll.getFilelistShow(pid);
									if (filelist.size() > 0) {
										FileUrl = "../CoHousingFund/CoHouseFile.jsp?filename="
												+ filelist.get(0).getChff_url()
												+ "&t="
												+ filelist.get(0)
														.getChff_name();
										Include ic = (Include) w
												.getFellow("ifr");
										ic.setSrc(FileUrl);
									}
								}
								Messagebox.show("已删除", "ERROR", Messagebox.OK,
										Messagebox.INFORMATION);
							} else {
								Messagebox.show("操作失败.请联系IT", "ERROR",
										Messagebox.OK, Messagebox.ERROR);
							}

						}
					}
				});
	}

	@Command
	@NotifyChange({ "filelist", "FileUrl" })
	public void info(@BindingParam("a") CoHousingFundFileModel m) {
		pid = m.getChff_id();

		filelist = bll.getFilelistShow(pid);
		if (filelist.size() > 0) {
			FileUrl = "../CoHousingFund/CoHouseFile.jsp?filename="
					+ filelist.get(0).getChff_url() + "&t="
					+ filelist.get(0).getChff_name();
			Include ic = (Include) w.getFellow("ifr");
			ic.setSrc(FileUrl);
		}
	}

	@Command
	@NotifyChange({ "filelist", "FileUrl" })
	public void back() {
		if (pid > 0) {
			pid = bll.getPid(pid);
			filelist = bll.getFilelistShow(pid);
			if (filelist.size() > 0) {
				/*
				FileUrl = "../CoHousingFund/CoHouseFile.jsp?filename="
						+ filelist.get(0).getChff_url() + "&t="
						+ filelist.get(0).getChff_name();
				Include ic = (Include) w.getFellow("ifr");
				ic.setSrc(FileUrl);
				*/
			}
		}
	}

	@Command
	public void download(@BindingParam("a") CoHousingFundFileModel m)
			throws FileNotFoundException {
		String absolutePath = FileOperate.getAbsolutePath();
		Filedownload.save(new File(absolutePath + m.getChff_url()), null);
	}

	public String getSearchName() {
		return searchName;
	}

	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}

	public List<CoHousingFundFileModel> getFilelist() {
		return filelist;
	}

	public void setFilelist(List<CoHousingFundFileModel> filelist) {
		this.filelist = filelist;
	}

	public String getFileUrl() {
		return FileUrl;
	}

	public void setFileUrl(String fileUrl) {
		FileUrl = fileUrl;
	}

	public boolean isModState() {
		return modState;
	}

	public void setModState(boolean modState) {
		this.modState = modState;
	}

}
