package Controller.EmSheBao;

import java.io.File;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Row;
import org.zkoss.zul.Messagebox.ClickEvent;
import org.zkoss.zul.Window;

import bll.EmSheBao.EmSheBao_DOperateBll;
import bll.EmSheBao.EmSheBao_DSelectBll;

import Model.EmSheBaoChangeModel;
import Util.FileOperate;
import Util.UserInfo;

public class Emsc_Declare_ExcelListController {
	private EmSheBao_DSelectBll dsbll = new EmSheBao_DSelectBll();
	private EmSheBao_DOperateBll dobll = new EmSheBao_DOperateBll();
	private List<EmSheBaoChangeModel> excelDataList;

	private String filename = Executions.getCurrent().getArg().get("filename")
			.toString();
	private String filetype = Executions.getCurrent().getArg().get("filetype")
			.toString();
	private String change = Executions.getCurrent().getArg().get("change")
			.toString();

	// 获取用户名
	String username = UserInfo.getUsername();
	String userid = UserInfo.getUserid();

	public Emsc_Declare_ExcelListController() {
		excelDataList = dsbll.getEmSCExcelData(filename);
	}

	// 下载
	@Command("download")
	public void download() {
		String path = "EmSheBao/File/Download/Declare/" + filename + filetype;

		try {
			File f = new File(path);

			FileOperate.download(path);

		} catch (Exception e) {
			e.printStackTrace();
			Messagebox.show("模板下载出錯。", "操作提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	// 申报
	@Command("submit")
	public void submit(@BindingParam("excelDataGrid") Grid excelDataGrid,@BindingParam("winExcelData") final Window winExcelData) {
		String[] message;
		int j = 0;
		int k = 0;
		String msg;

		try {
			for (int i = 0; i < excelDataGrid.getRows().getChildren().size(); i++) {
				Radiogroup rg = (Radiogroup) excelDataGrid.getCell(i, 3)
						.getChildren().get(0);
				k = k + 1;
				// 判断是否选中
				Row row = (Row) excelDataGrid.getRows().getChildren().get(i); // 获取Gird的行
				int id = ((EmSheBaoChangeModel) row.getValue()).getId();
				int emsc_tapr_id = ((EmSheBaoChangeModel) row.getValue())
						.getEmsc_tapr_id();

				if (rg.getSelectedItem().getValue().equals("2")) {
					// 申报社保
					//message = dobll.declareSingle(id, emsc_tapr_id, username);
					// 判断是否成功
					//if (message[0].equals("1")) {
						j = j + 1;
					//}
				} else if (rg.getSelectedItem().getValue().equals("0")) {
					// 变更社保数据为 未申报
					message = dobll
							.declareFirstStep(id, emsc_tapr_id, username);
					// 判断是否成功
					if (message[0].equals("1")) {
						j = j + 1;
					}
				} else if (rg.getSelectedItem().getValue().equals("3")) {
					// 退回社保变更数据
					message = dobll.declareReturn(id, emsc_tapr_id, username);
					// 判断是否成功
					if (message[0].equals("1")) {
						j = j + 1;
					}
				}
			}
			
			if (j == k) {
				msg = "操作成功！";
			} else {
				msg = "部分数据操作不成功，请检查！";
			}
			
			EventListener<ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
				public void onEvent(ClickEvent event) throws Exception {
					if (Messagebox.Button.OK.equals(event.getButton())) {
						winExcelData.detach();
					}
				}
			};
			// 弹出提示
			Messagebox.show(msg, "操作提示",
					new Messagebox.Button[] { Messagebox.Button.OK },
					Messagebox.INFORMATION, clickListener);
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	// 撤销
	@Command("cancel")
	public void cancel(@BindingParam("excelDataGrid") Grid excelDataGrid,@BindingParam("winExcelData") final Window winExcelData) {
		String[] message;
		int j = 0;
		int k = 0;
		String msg;

		try {
			for (int i = 0; i < excelDataGrid.getRows().getChildren().size(); i++) {
				k = k + 1;
				// 判断是否选中
				Row row = (Row) excelDataGrid.getRows().getChildren().get(i); // 获取Gird的行
				int id = ((EmSheBaoChangeModel) row.getValue()).getId();
				int emsc_tapr_id = ((EmSheBaoChangeModel) row.getValue())
						.getEmsc_tapr_id();

					// 申报社保
					message = dobll.declareCancel(id, emsc_tapr_id, username);
					// 判断是否成功
					if (message[0].equals("1")) {
						j = j + 1;
					}
			}
			
			if (j == k) {
				msg = "操作成功！";
			} else {
				msg = "部分数据操作不成功，请检查！";
			}
			
			EventListener<ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
				public void onEvent(ClickEvent event) throws Exception {
					if (Messagebox.Button.OK.equals(event.getButton())) {
						winExcelData.detach();
					}
				}
			};
			// 弹出提示
			Messagebox.show(msg, "操作提示",
					new Messagebox.Button[] { Messagebox.Button.OK },
					Messagebox.INFORMATION, clickListener);
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getFiletype() {
		return filetype;
	}

	public void setFiletype(String filetype) {
		this.filetype = filetype;
	}

	public String getChange() {
		return change;
	}

	public void setChange(String change) {
		this.change = change;
	}

	public List<EmSheBaoChangeModel> getExcelDataList() {
		return excelDataList;
	}

	public void setExcelDataList(List<EmSheBaoChangeModel> excelDataList) {
		this.excelDataList = excelDataList;
	}

}
