package Controller.Archives;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.UnderlineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.commons.collections.map.HashedMap;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Include;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Window;

import bll.Archives.EmArchive_SelectBll;

import Model.EmArchiveModel;
import Model.EmBodyCheckModel;
import Util.FileOperate;
import Util.UserInfo;
import Util.pinyin4jUtil;
import Util.plyUtil;

/*
 * 创建人：陈耀家
 * 创建时间：2013-12-3
 * 用途：档案信息显示控制文件
 * 
 */

public class Archives_InfoListController {
	EmArchive_SelectBll bll = new EmArchive_SelectBll();
	private List<EmArchiveModel> archivelist;
	private String flag = "";
	private String url = "Archives_InfoList.zul";

	private String title1 = "原档案地";
	private String title2 = "承诺书签订日期";
	private String title3 = "协议书签订日期";

	public Archives_InfoListController() {
		setArchivelist(bll.getEmArchiveInfo(" and emar_archivetype='中智保管' and emar_fid like 'A%'"));
	}

	// 存档方式的onChange事件,当选择"委托人才"的时候显示档案所在地（市内or市外）
	@Command
	public void changecity(@BindingParam("acclass") String acclass,
			@BindingParam("ifcity") Combobox ifcity) {
		if (acclass != null) {
			if (acclass == "委托人才" || acclass.equals("委托人才")) {
				ifcity.setVisible(true);
			} else {
				ifcity.setVisible(false);
			}
		}
	}

	// 查询按钮的点击事件
	@Command
	@NotifyChange({ "archivelist", "title1", "title2", "title3" })
	public void search(@BindingParam("acclass") final String acclass,
			@BindingParam("company") final String company,
			@BindingParam("name") final String name,
			@BindingParam("acid") final String acid,
			@BindingParam("acstate") final String acstate,
			@BindingParam("ifcity") Combobox ifcity) {
		String str = "";
		if (company != null && !company.equals("") && company != "") {
			str = str + " and emar_company like '%" + company + "%'";
		}
		if (name != null && !name.equals("") && name != "") {
			str = str + " and emar_name='" + name + "'";
		}
		if (acid != null && !acid.equals("") && acid != "") {
			str = str + " and emar_fid like '%" + acid + "%'";
		}
		if (acstate != null && !acstate.equals("") && acstate != "") {
			if (acstate == "在册" || acstate.equals("在册")) {
				str = str + " and emar_state =1";
			} else {
				str = str + " and emar_state =0";
			}
		}
		if (ifcity.getValue() != null && !ifcity.getValue().equals("")
				&& ifcity.getValue() != "") {
			if (ifcity.getValue() != "全部" && !ifcity.getValue().equals("全部")) {
				str = str + " and emar_archivearea='" + ifcity.getValue() + "'";
			}
		}

		// 当存档方式不同时显示不同的表头
		if (acclass == "中智保管" || acclass.equals("中智保管")) {
			str = str + " and emar_archivetype='中智保管'";
			title1 = "原档案地";
			title2 = "承诺书签订日期";
			title3 = "协议书签订日期";
			archivelist = bll.getEmArchiveInfo(str);
		} else {
			title1 = "档案所在地";
			title2 = "初次协议到期日";
			title3 = "续签协议到期日";
			str = str + " and emar_archivetype='委托人才'";
			archivelist = bll.getEmArchiveInfo(str);
		}
	}

	// 修改页面的弹出窗口事件
	@Command
	@NotifyChange({ "archivelist", "title1", "title2", "title3" })
	public void openZUL(@BindingParam("url") String url,
			@BindingParam("archivemodel") EmArchiveModel model,
			@BindingParam("acclass") String acclass) {
		Map map = new HashMap<>();
		map.put("model", model);
		map.put("id", model.getEmar_id());
		map.put("gid", model.getGid());
		map.put("typeid", "2");
		Window window = (Window) Executions.createComponents(url, null, map);
		window.doModal();
		// 当存档方式不同时显示不同的表头
		if (acclass == "中智保管" || acclass.equals("中智保管")) {
			title1 = "原档案地";
			title2 = "承诺书签订日期";
			title3 = "协议书签订日期";
			archivelist = bll.getEmArchiveInfo(" and emar_archivetype='中智保管'");
		} else {
			title1 = "档案所在地";
			title2 = "初次协议到期日";
			title3 = "续签协议到期日";
			archivelist = bll.getEmArchiveInfo(" and emar_archivetype='委托人才'");
		}
	}

	// 打开详细页面
	@Command
	public void detail(@BindingParam("model") EmArchiveModel model) {
		// System.out.println("公司:"+model.getEmar_company());
		System.out.println(1);
		Map map = new HashMap<>();
		map.put("model", model);
		Window window = (Window) Executions.createComponents(
				"EmArchives_DetailInfo.zul", null, map);
		window.doModal();
	}

	@Command
	public void export() throws Exception {
		if (archivelist.size() > 0) {

			String absolutePath = FileOperate.getAbsolutePath();
			String path = absolutePath + "OfficeFile/DownLoad/archive/";
			// 创建当前日子
			Date date = new Date();
			// 格式化日期
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
			// 格式化日期(产生文件名)
			String filename = "员工档案" + sdf.format(date)
					+ pinyin4jUtil.getHeadCharPinYin(UserInfo.getUsername())
					+ ".xls";

			// 创建文件
			File file = new File(path + filename);
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			String sheetName = "档案信息";
			String[] title;
			if (archivelist.get(0).getEmar_archivetype().equals("中智保管")) {
				title = new String[] { "序号", "公司名称", "姓名", "存档方式", "档案号",
						"原档案地", "承诺书签订日期", "协议书签订日期", "状态", "在职状态", "备注" };
			} else {
				title = new String[] { "序号", "公司名称", "姓名", "存档方式", "档案号",
						"原档案地", "初次协议到期日", "续签协议到期日", "状态", "在职状态", "备注" };
			}
			// TODO Auto-generated method stub
			WritableWorkbook wwb = Workbook
					.createWorkbook(new FileOutputStream(file));
			WritableSheet sheet = wwb.createSheet(sheetName, 0);
			// 设置标题的文字格式
			WritableFont wf = new WritableFont(WritableFont.ARIAL, 12,
					WritableFont.BOLD, true, UnderlineStyle.NO_UNDERLINE);// 字体、粗体、斜体、下划线
			WritableCellFormat wcf = new WritableCellFormat(wf);
			wcf.setVerticalAlignment(VerticalAlignment.CENTRE);
			wcf.setAlignment(Alignment.CENTRE);
			// 行高设置行高,参数(行数,高度)
			sheet.setRowView(0, 500);
			// 开始写入第一行(即标题栏)
			for (int i = 0; i < title.length; i++) {
				// 用于写入文本内容到工作表中去
				Label label = null;
				// 在Label对象的构造中指明单元格位置(参数依次代表列数、行数、内容 )
				label = new Label(i, 0, title[i], wcf);
				// 将定义好的单元格添加到工作表中
				sheet.addCell(label);
			}
			// 开始写入内容
			int row=0;
			for (int i = 0; i < archivelist.size(); i++) {
				row=i+1;
				
				// 将每列数据写入工作表中
				Label label = null;
				label = new Label(0, row, String.valueOf(row));
				sheet.addCell(label);
				label = new Label(1, row, archivelist.get(i).getEmar_company());
				sheet.addCell(label);
				label = new Label(2, row, archivelist.get(i).getEmar_name());
				sheet.addCell(label);
				label = new Label(3, row, archivelist.get(i)
						.getEmar_archivetype());
				sheet.addCell(label);
				String fid = "";
				String address = "";
				String d1 = "";
				String d2 = "";
				if (archivelist.get(i).getEmar_archivetype().equals("中智保管")) {
					fid = archivelist.get(i).getEmar_fid();
					address = archivelist.get(i).getEmar_archivesource();
					d1 = archivelist.get(i).getEmar_promisesdate();
					d2 = archivelist.get(i).getEmar_prodate();
				} else {
					fid = archivelist.get(i).getEmar_archivearea()
							+ archivelist.get(i).getEmar_fid();
					address = archivelist.get(i).getEmar_archiveplace();
					d1 = archivelist.get(i).getEmar_firstdeadline();
					d2 = archivelist.get(i).getEmar_continuedeadline();
				}
				label = new Label(4, row, fid);
				sheet.addCell(label);
				label = new Label(5, row, address);
				sheet.addCell(label);
				label = new Label(6, row, d1);
				sheet.addCell(label);
				label = new Label(7, row, d2);
				sheet.addCell(label);
				label = new Label(8, row, archivelist.get(i).getState());
				sheet.addCell(label);
				label = new Label(9, row, archivelist.get(i).getEmba_state()==1?"在职":"离职");
				sheet.addCell(label);
				label = new Label(10, row, archivelist.get(i).getEmar_remark());
				sheet.addCell(label);

			}
			// 写入数据
			wwb.write();
			// 关闭文件
			wwb.close();
			Filedownload.save(file, "xlsx");
		}
	}

	public List<EmArchiveModel> getArchivelist() {
		return archivelist;
	}

	public void setArchivelist(List<EmArchiveModel> archivelist) {
		this.archivelist = archivelist;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTitle1() {
		return title1;
	}

	public void setTitle1(String title1) {
		this.title1 = title1;
	}

	public String getTitle2() {
		return title2;
	}

	public void setTitle2(String title2) {
		this.title2 = title2;
	}

	public String getTitle3() {
		return title3;
	}

	public void setTitle3(String title3) {
		this.title3 = title3;
	}
}
