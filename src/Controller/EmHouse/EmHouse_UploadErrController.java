package Controller.EmHouse;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.zkoss.bind.BindContext;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zul.Button;
import org.zkoss.zul.Cell;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Progressmeter;
import org.zkoss.zul.Row;
import org.zkoss.zul.Window;
import org.zkoss.zul.Messagebox.ClickEvent;

import bll.EmHouse.EmHouse_EditBll;
import bll.EmHouse.EmHouse_UpLoadErrBll;

import Model.EmHouseChangeModel;
import Model.EmHouseUploadErrModel;
import Util.DateStringChange;
import Util.FileOperate;
import Util.MonthListUtil;
import Util.UserInfo;

public class EmHouse_UploadErrController {

	private String change;
	private Media[] media;
	private String uploadFlieName;
	private String absolutePath;
	private String username = UserInfo.getUsername();

	private MonthListUtil mlu = new MonthListUtil();

	private DateStringChange dsc = new DateStringChange();
	// 获取当前所属月份
	Date nowDate = new Date(); // 获取当前时间
	private String nowmonth = dsc.DatetoSting(nowDate, "yyyyMM");
	// 所属月份下拉框
	private String[] s_ownmonth = mlu.getMonthList(true, nowmonth, 2, 9);

	private Window win = (Window) Path.getComponent("/wue");

	private List<String> fileList = new ArrayList<>();
	private List<EmHouseUploadErrModel> errlist = new ListModelList<>();

	private boolean btnSubmit;
	private boolean checkall;

	private EmHouse_UpLoadErrBll bll = new EmHouse_UpLoadErrBll();
	private EmHouse_EditBll ebll = new EmHouse_EditBll();

	public EmHouse_UploadErrController() {
		change = "新增";
		btnSubmit = false;
		errlist = bll.search(Integer.valueOf(nowmonth), change, username);
	}

	@Command
	public void checkAll() {
		for (EmHouseUploadErrModel m : errlist) {
			m.setChecked(checkall);
			BindUtils.postNotifyChange(null, null, m, "checked");

		}
	}

	@Command
	@NotifyChange("errlist")
	public void search() {
		errlist = bll.search(Integer.valueOf(nowmonth), change, username);
	}

	// 文件检查
	@Command("browse")
	@NotifyChange("uploadFlieName")
	public void browse(@ContextParam(ContextType.BIND_CONTEXT) BindContext ul) {
		UploadEvent upEvent = (UploadEvent) ul.getTriggerEvent();
		media = upEvent.getMedias();
		fileList.clear();
		for (int i = 0; i < media.length; i++) {

			if ("xls".equals(media[i].getFormat())
					|| "xlsx".equals(media[i].getFormat())) {
				uploadFlieName = media[i].getName();

				absolutePath = FileOperate.getAbsolutePath();
				String uploadfolder = "EmHouse/File/UpLoad/ErrFile/";
				String uploadName = mosaicFileName();

				if (FileOperate.upload(media[i], uploadfolder + uploadName)) {
					fileList.add(uploadName);
					Grid gd = (Grid) win.getFellow("grid");
					final Row rw = new Row();
					Cell cell1 = new Cell();
					Cell cell2 = new Cell();
					Cell cell3 = new Cell();
					cell1.appendChild(new Label(media[i].getName()));
					Label lb = new Label();
					lb.setValue(uploadName);
					lb.setStyle("display:none");
					cell1.appendChild(lb);
					cell2.setColspan(2);

					Button btn = new Button();
					btn.setLabel("删除");

					btn.addEventListener(Events.ON_CLICK,
							new org.zkoss.zk.ui.event.EventListener() {
								public void onEvent(Event event)
										throws Exception {
									rw.detach();
								}
							});
					cell2.appendChild(btn);
					Progressmeter p = new Progressmeter();
					cell3.appendChild(p);
					rw.appendChild(cell1);
					rw.appendChild(cell3);
					rw.appendChild(cell2);
					for (int j = 2; j < gd.getRows().getChildren().size(); j++) {

						if (media[i].getName().equals(
								((Label) gd.getCell(j, 0).getChildren().get(0))
										.getValue())) {

							gd.getRows().removeChild(
									gd.getRows().getChildren().get(j));
						}

					}

					gd.getRows().appendChild(rw);
				}
			} else {
				media = null;
				uploadFlieName = "";
				Messagebox.show("上传的文件格式有误。", "操作提示", Messagebox.OK,
						Messagebox.NONE);
				return;
			}
		}
	}

	// 读取文件数据并写入数据库
	@Command
	@NotifyChange({ "errlist" })
	public void UpLoadFile() {
		File file = null;
		Label lbl = null;
		boolean n = false;
		Combobox ownmonth = (Combobox) win.getFellow("ownmonth");

		if (ownmonth.getSelectedItem() != null) {
			if (media != null) {
				Grid gd = (Grid) win.getFellow("grid");
				if (gd.getRows().getChildren().size() > 2) {
					String uploadfolder = "EmHouse/File/UpLoad/ErrFile/";
					for (int i = 2; i < gd.getRows().getChildren().size(); i++) {
						lbl = (Label) gd.getCell(i, 0).getChildren().get(1);
						Progressmeter p = (Progressmeter) gd.getCell(i, 1)
								.getChildren().get(0);
						file = new File(absolutePath + uploadfolder
								+ lbl.getValue());
						p.setValue(10);
						/*
						 * for (int j = 0; j < 90; j++) { p.setValue(j); }
						 */

						n = bll.addData(file, Integer.valueOf(nowmonth),
								change, username);

						if (!n) {

							p.setValue(0);
							continue;

						} else {
							p.setValue(100);

						}
					}
					errlist = bll.search(Integer.valueOf(nowmonth), change,
							username);
				} else {
					Messagebox.show("请选择要上传的文件", "操作提示", Messagebox.OK,
							Messagebox.ERROR);
				}

			}

		} else {
			Messagebox.show("请选择所属月份。", "操作提示", Messagebox.OK, Messagebox.NONE);
		}

	}

	// 批量退回数据
	@Command
	@NotifyChange({ "errlist" })
	public void submit() {
		boolean b = false;
		for (EmHouseUploadErrModel m : errlist) {
			if (m.getChecked() && m.getGid() != null) {
				b = true;
			}
		}
		if (b) {
			Messagebox.show("确认退回数据?", "操作提示", new Messagebox.Button[] {
					Messagebox.Button.OK, Messagebox.Button.CANCEL },
					Messagebox.QUESTION,
					new EventListener<Messagebox.ClickEvent>() {
						@Override
						public void onEvent(ClickEvent event) throws Exception {
							// TODO Auto-generated method stub
							if (Messagebox.Button.OK.equals(event.getButton())) {
								boolean t = true;
								boolean s = true;
								for (EmHouseUploadErrModel m : errlist) {
									if (m.getChecked()) {
										if (m.getEmhc_id() > 0) {

											EmHouseChangeModel m1 = new EmHouseChangeModel();
											m1.setEmhc_id(m.getEmhc_id());
											m1.setGid(m.getGid());
											m1.setEmhc_ifprogress(m
													.getEmhc_ifprogress());
											m1.setEmhc_tapr_id(m
													.getEmhc_tapr_id());
											Integer j = ebll.returnBack(m1);
											if (j > 0) {
												Integer i = bll.sendMessage(
														"员工公积金数据被退回",
														"("
																+ m.getCid()
																+ ","
																+ m.getGid()
																+ ")"
																+ m.getCompany()
																+ ","
																+ m.getName()
																+ "公积金数据被退回,退回原因:"
																+ m.getEhle_errMessage(),
														Integer.valueOf(UserInfo
																.getUserid()),
														UserInfo.getUsername(),
														m.getLogId(), m
																.getEmhc_id());
												if (i < 1) {
													s = false;
												}
											} else {
												t = false;
											}
										}
									}
								}
								if (t && s) {
									bll.delData(username);
									errlist = bll.search(
											Integer.valueOf(nowmonth), change,
											username);
									Messagebox.show("操作成功", "操作提示",
											Messagebox.OK, Messagebox.NONE);
								} else {
									if (!s) {
										Messagebox.show("邮件发送失败.", "操作提示",
												Messagebox.OK, Messagebox.NONE);
									}
									if (!t) {
										Messagebox.show("数据退回失败.", "操作提示",
												Messagebox.OK, Messagebox.NONE);
									}
								}
							}
						}
					});
		} else {
			Messagebox.show("请选择要退回数据的人员.", "操作提示", Messagebox.OK,
					Messagebox.NONE);
		}
	}

	// 批量删除
	@Command
	@NotifyChange("errlist")
	public void del() {
		boolean b = false;
		for (EmHouseUploadErrModel m : errlist) {
			if (m.getChecked()) {
				b = true;
			}
		}
		if (b) {
			bll.delId(errlist);
			errlist = bll.search(Integer.valueOf(nowmonth), change, username);
		} else {
			Messagebox.show("请选择要删除的人员.", "操作提示", Messagebox.OK,
					Messagebox.NONE);
		}
	}

	// 拼接上传文件的名称
	private String mosaicFileName() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date();
		String nowtime = sdf.format(date);
		String name = nowtime + uploadFlieName;
		return name;
	}

	public String getNowmonth() {
		return nowmonth;
	}

	public void setNowmonth(String nowmonth) {
		this.nowmonth = nowmonth;
	}

	public String[] getS_ownmonth() {
		return s_ownmonth;
	}

	public void setS_ownmonth(String[] s_ownmonth) {
		this.s_ownmonth = s_ownmonth;
	}

	public Media[] getMedia() {
		return media;
	}

	public void setMedia(Media[] media) {
		this.media = media;
	}

	public String getUploadFlieName() {
		return uploadFlieName;
	}

	public void setUploadFlieName(String uploadFlieName) {
		this.uploadFlieName = uploadFlieName;
	}

	public String getChange() {
		return change;
	}

	public void setChange(String change) {
		this.change = change;
	}

	public boolean isBtnSubmit() {
		return btnSubmit;
	}

	public void setBtnSubmit(boolean btnSubmit) {
		this.btnSubmit = btnSubmit;
	}

	public List<EmHouseUploadErrModel> getErrlist() {
		return errlist;
	}

	public void setErrlist(List<EmHouseUploadErrModel> errlist) {
		this.errlist = errlist;
	}

	public boolean isCheckall() {
		return checkall;
	}

	public void setCheckall(boolean checkall) {
		this.checkall = checkall;
	}

}
