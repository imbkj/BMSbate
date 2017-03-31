package Controller.EmSalary;

import impl.MessageImpl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jxl.Workbook;
import jxl.format.UnderlineStyle;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import dal.LoginDal;

import service.MessageService;

import Model.CoBaseModel;
import Model.EmSalaryBaseAddItemModel;
import Model.EmSalaryDataModel;
import Model.SysMessageModel;
import Util.FileOperate;
import Util.RegexUtil;
import Util.UserInfo;

import bll.LoginBll;
import bll.CoBase.CoBase_SelectBll;
import bll.EmSalary.EmSalary_NonZeroOperateBll;
import bll.EmSalary.EmSalary_SalarySelBll;

public class EmSalary_AudtingNonZeroController {
	private final int taba_id = Integer.parseInt(Executions.getCurrent()
			.getArg().get("daid").toString());
	private final int taba_tapr_id = Integer.parseInt(Executions.getCurrent()
			.getArg().get("id").toString());

	private List<EmSalaryDataModel> salaryList;
	private List<EmSalaryDataModel> salaryWinList;
	private List<EmSalaryBaseAddItemModel> itemList;
	private EmSalary_SalarySelBll bll;
	private String filename;
	private String downfolder;
	private int ownmonth;
	private int cid;

	public EmSalary_AudtingNonZeroController() {
		bll = new EmSalary_SalarySelBll();
		salaryList = bll.getSalaryDataByTabaIdToNonZero(taba_id);
		if (salaryList.size() > 0) {
			EmSalaryDataModel m = (EmSalaryDataModel) salaryList.get(0);
			cid = m.getCid();
			ownmonth = m.getOwnmonth();
			itemList = bll.getCSIIInfo(cid, ownmonth, 2);
			setEmSalaryDataModelOfItemList(salaryList, itemList);
			salaryWinList = salaryList;
		}

	}

	// 导出excel
	@Command
	public void excel() {
		downfolder = "EmSalary/File/Download/SalaryChange/";
		filename = "工资非清零数据" + System.currentTimeMillis() + ".xls";
		String path = downfolder + filename;
		try {
			File f = new File(FileOperate.getAbsolutePath() + path);
			if (f.isFile()) {
				f.delete();
			}
			int i = createTemplet();
			if (i == 1) {
				FileOperate.download(path);
			} else {
				Messagebox.show("模板生成失败。", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox.show("模板生成出错。", "操作提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	// 生成模板文件
	private int createTemplet() {
		String templet = "EmSalary/File/Templet/SalaryUpdate.xls";
		int success = 0;
		try {
			// 读取Excel模板
			Workbook wb = Workbook.getWorkbook(new File(FileOperate
					.getAbsolutePath() + templet));
			// 使用模板创建
			WritableWorkbook wwb = Workbook.createWorkbook(
					new File(FileOperate.getAbsolutePath() + downfolder
							+ filename), wb);
			// 生成工作表
			WritableSheet sheet = wwb.getSheet(0);
			// 设置字体格式
			WritableFont wf = new WritableFont(WritableFont.ARIAL, 12,
					WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE);// 字体、粗体、斜体、下划线
			WritableCellFormat wcf = new WritableCellFormat(wf);
			// 插入第一行
			Label label = null;
			label = new Label(1, 0, String.valueOf(ownmonth), wcf);
			sheet.addCell(label);
			label = new Label(4, 0, bll.getCoShortName(cid) + "工资非清零数据修改审核",
					wcf);
			sheet.addCell(label);
			// 插入表头
			try {
				for (int i = 0; i < itemList.size(); i++) {
					label = new Label(i + 4, 2 - 1, itemList.get(i)
							.getCsii_item_name(), wcf);
					sheet.addCell(label);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			// 插入数据
			try {
				for (int i = 0; i < salaryList.size(); i++) {
					label = new Label(0, 2 + i, String.valueOf(salaryList
							.get(i).getGid()));
					sheet.addCell(label);
					label = new Label(1, 2 + i, salaryList.get(i).getName());
					sheet.addCell(label);
					label = new Label(2, 2 + i, salaryList.get(i)
							.getEsda_usage_typestr());
					sheet.addCell(label);
					label = new Label(3, 2 + i, salaryList.get(i)
							.getEsda_remark());
					sheet.addCell(label);
					for (int j = 0; j < salaryList.get(i).getItemList().size(); j++) {
						label = new Label(j + 4, 2 + i, salaryList.get(i)
								.getItemList().get(j).getAmount().toString());
						sheet.addCell(label);
					}

				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			// 写入数据
			wwb.write();
			// 关闭文件
			wwb.close();
			success = 1;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (BiffException e) {
			e.printStackTrace();
		} catch (RowsExceededException e) {
			e.printStackTrace();
		} catch (WriteException e) {
			e.printStackTrace();
		}
		return success;
	}

	// 审核通过
	@Command("audtingSalary")
	public void audtingSalary(@BindingParam("win") Window win) {
		if (Messagebox.show("确认审核通过吗？", "操作提示", Messagebox.YES | Messagebox.NO,
				Messagebox.QUESTION) == Messagebox.YES) {
			try {
				if (salaryList.size() > 0) {
					operateSalary(win, 1);
				} else {
					Messagebox.show("数据有误。", "操作提示", Messagebox.OK,
							Messagebox.ERROR);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	// 退回
	@Command("returnSalary")
	public void returnSalary(@BindingParam("win") Window win) {
		if (Messagebox.show("确认退回数据吗？", "操作提示", Messagebox.YES | Messagebox.NO,
				Messagebox.QUESTION) == Messagebox.YES) {
			operateSalary(win, 2);
		}
	}

	// 终止
	@Command("stopTask")
	public void stopTask(@BindingParam("win") Window win) {
		if (Messagebox.show("确认终止该任务单吗？", "操作提示", Messagebox.YES
				| Messagebox.NO, Messagebox.QUESTION) == Messagebox.YES) {
			operateSalary(win, 3);
		}
	}

	// 工资数据审核、退回操作(type: 1审核通过 2退回)
	private void operateSalary(Window win, int type) {
		EmSalary_NonZeroOperateBll opBll = new EmSalary_NonZeroOperateBll();
		try {
			String[] message = opBll.audtingOperate(taba_id, taba_tapr_id,
					UserInfo.getUsername(), type, salaryList);

			if ("1".equals(message[0])) {
				if (type==1) {
					CoBase_SelectBll coBll=new CoBase_SelectBll();
					String gzaddname="";
					CoBaseModel cobaM;
					cobaM=coBll.getCobaseinfo(" AND a.cid="+String.valueOf(cid)).get(0);
					gzaddname=cobaM.getCoba_gzaddname();
					//发系统短信通知负责人
					MessageService msgservice;
					msgservice = new MessageImpl("", null);
					SysMessageModel model = new SysMessageModel();
					model.setSyme_content(cobaM.getCoba_shortname()+"的非清零工资项目审核已通过，请知悉。");// 短信内容
					model.setSyme_log_id(Integer.parseInt(UserInfo.getUserid()));// 发件人id
					model.setSymr_name(gzaddname);// 收件人姓名
					model.setSymr_log_id(opBll.getUserIDByname(gzaddname));
					model.setEmail(0);
					model.setSyme_title(cobaM.getCoba_shortname()+"的非清零工资项目审核已通过");
					// 调用方法
					msgservice.Add(model);
				}
				
				// 弹出提示
				Messagebox.show(message[1], "操作提示", Messagebox.OK,
						Messagebox.NONE);
				win.detach();
			} else {
				// 弹出提示
				Messagebox.show(message[1], "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox.show("操作出错。", "操作提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	// 检索
	@Command("changeList")
	@NotifyChange("salaryWinList")
	public void changeList(@BindingParam("ibGid") Intbox ibGid,
			@BindingParam("txtName") Textbox txtName) {
		if (!"".equals(txtName.getValue()) || ibGid.getValue() != null) {
			if (ibGid.getValue() == null) {
				salaryWinList = getNewList(0, txtName.getValue());
			} else {
				salaryWinList = getNewList(ibGid.getValue(), txtName.getValue());
			}
		} else {
			salaryWinList = salaryList;
		}
	}

	// 检索数据
	private List<EmSalaryDataModel> getNewList(int gid, String name) {
		List<EmSalaryDataModel> list = new ArrayList<EmSalaryDataModel>();
		int i = 2;
		for (EmSalaryDataModel m : salaryList) {
			i = 2;
			if (gid == 0) {
				i = i - 1;
			} else if (gid == m.getGid()) {
				i = i - 1;
			}
			try {
				if (name == null || "".equals(name)) {
					i = i - 1;
				} else if (RegexUtil.isExists(name, m.getName())) {
					i = i - 1;
				}
			} catch (Exception e) {

			}
			if (i == 0) {
				list.add(m);
			}
		}
		return list;
	}

	// 点击姓名查看工资的个人信息
	@Command("SelEmbase")
	public void SelEmbase(@BindingParam("lbl") org.zkoss.zul.Label lbl) {
		int esda_id = ((EmSalaryDataModel) ((Row) lbl.getParent()).getValue())
				.getEsda_id();
		Map<String, String> map = new HashMap<String, String>();
		map.put("esda_id", String.valueOf(esda_id));
		Window window = (Window) Executions.createComponents(
				"../EmSalary/EmSalary_Embase.zul", null, map);
		window.doModal();
	}

	// 初始化EmSalaryDataModel的itemList
	private void setEmSalaryDataModelOfItemList(List<EmSalaryDataModel> sdList,
			List<EmSalaryBaseAddItemModel> itList) {
		for (EmSalaryDataModel m : sdList) {
			try {
				m.setItemList(itList);
			} catch (Exception e) {

			}
		}
	}

	public List<EmSalaryDataModel> getSalaryWinList() {
		return salaryWinList;
	}

	public List<EmSalaryBaseAddItemModel> getItemList() {
		return itemList;
	}
}
