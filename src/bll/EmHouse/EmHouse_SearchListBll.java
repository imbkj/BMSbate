package bll.EmHouse;

import impl.WorkflowCore.WfOperateImpl;

import java.io.File;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import jxl.Workbook;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import org.zkoss.zul.Filedownload;
import org.zkoss.zul.ListModelList;

import service.WorkflowCore.WfBusinessService;
import service.WorkflowCore.WfOperateService;

import Model.EmHouseChangeModel;
import Model.SysMessageModel;
import Model.loginroleModel;
import Util.FileOperate;
import Util.UserInfo;
import Util.pinyin4jUtil;
import dal.LoginDal;
import dal.EmHouse.EmHouseChangeDal;
import dal.SysMessage.SysMessage_EditDal;

public class EmHouse_SearchListBll {
	// 福利查询列表
	public List<EmHouseChangeModel> getList(EmHouseChangeModel em) {
		List<EmHouseChangeModel> list = new ListModelList<>();
		EmHouseChangeDal dal = new EmHouseChangeDal();

		if (em.getEmhc_ifdeclare2() != null
				&& !em.getEmhc_ifdeclare2().equals("")) {
			switch (em.getEmhc_ifdeclare2()) {
			case "未确认":
				em.setEmhc_ifdeclare(4);
				break;
			case "未申报":
				em.setEmhc_ifdeclare(0);
				break;
			case "申报中":
				em.setEmhc_ifdeclare(2);
				break;
			case "已申报":
				em.setEmhc_ifdeclare(1);
				break;
			case "退回":
				em.setEmhc_ifdeclare(3);
				break;
			case "审核中":
				em.setEmhc_ifdeclare(5);
				break;

			}

		}

		if (em.getEmhc_single2() != null && !em.getEmhc_single2().equals("")) {
			switch (em.getEmhc_single2()) {
			case "中智开户":
				em.setSingleState(0);
				break;

			case "独立开户":
				em.setSingleState(1);
				break;
			}

		}

		if (em.getEmhc_ifprogress2() != null
				&& !em.getEmhc_ifprogress2().equals("")) {
			switch (em.getEmhc_ifprogress2()) {
			case "新增(等待设立)":
				em.setEmhc_ifprogress(11);
				break;
			case "新增(设立完成)":
				em.setEmhc_ifprogress(12);
				break;
			case "调入(等待转移)":
				em.setEmhc_ifprogress(21);
				break;
			case "调入(等待启封)":
				em.setEmhc_ifprogress(22);
				break;
			case "调入(调入完成)":
				em.setEmhc_ifprogress(23);
				break;
			case "停交(等待封存)":
				em.setEmhc_ifprogress(31);
				break;
			case "停交(封存完成)":
				em.setEmhc_ifprogress(32);
				break;
			case "调基(等待调整)":
				em.setEmhc_ifprogress(41);
				break;
			case "调基(调整完成)":
				em.setEmhc_ifprogress(42);
				break;
			}
		}

		if (em.getEmhc_cpp2() != null && !em.getEmhc_cpp2().equals("")) {
			em.setEmhc_cpp(new BigDecimal(em.getEmhc_cpp2().replace("%", ""))
					.divide(new BigDecimal(100)).doubleValue());
		}

		list = dal.getDeclareListByParams(em);
		em.setEmhc_ifdeclare(null);
		em.setSingleState(null);
		em.setEmhc_ifprogress(null);
		em.setEmhc_cpp(null);
		return list;
	}

	// 查询任务单操作人

	// 查询公积金变更记录
	public List<EmHouseChangeModel> getInfoById(Integer id) {
		List<EmHouseChangeModel> list = new ListModelList<>();
		EmHouseChangeDal dal = new EmHouseChangeDal();

		try {
			list = dal.getInfoById(id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

	// 申报数据
	public String[] declareSingle(EmHouseChangeModel ehm) {
		WfBusinessService wfbs = new EmHouse_SearchListImpl();
		WfOperateService wfs = new WfOperateImpl(wfbs);

		Object[] obj = { ehm.getEmhc_id(), ehm.getEmhc_declarename(),
				ehm.getEmhc_remark(), ehm.getEmhc_ifprogress(),
				ehm.getEmhc_ifdeclare() };
		String[] str = null;
		if (ehm.getEmhc_tapr_id() != null) {
			str = wfs.PassToNext(obj, ehm.getEmhc_tapr_id(),
					ehm.getEmhc_declarename(), "", ehm.getCid(), "");
		} else {
			str = wfbs.Operate(obj);
		}

		return str;
	}

	// 创建导出EXCEL文件
	public boolean exportExcel(List<EmHouseChangeModel> list) {
		boolean b = false;

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		Date trialTime = new Date();

		String exfileName = sdf.format(trialTime) + "_"
				+ pinyin4jUtil.getPinYinHeadChar(UserInfo.getUsername())
				+ ".xls";

		Workbook workBook = null;
		WritableWorkbook wb = null;
		String absolutePath = FileOperate.getAbsolutePath();

		try {
			workBook = Workbook.getWorkbook(new File(absolutePath
					+ "OfficeFile/Templet/Emhouse/gjjexport.xls"));
			wb = Workbook.createWorkbook(new File(absolutePath
					+ "OfficeFile/DownLoad/Emhouse/" + exfileName), workBook);
			WritableSheet sheet = wb.getSheet(0);
			WritableCellFormat wcf = getBodyCellStyle();
			jxl.write.Label lbl = null;
			jxl.write.Number num =null;
			for (Integer i = 0; i < list.size(); i++) {
				sheet.insertRow(i+1);
				lbl = new jxl.write.Label(0, i+1, String.valueOf((i+1)), wcf);
				sheet.addCell(lbl);
				lbl = new jxl.write.Label(1, i+1, list.get(i)
						.getCoba_shortname(), wcf);
				sheet.addCell(lbl);
				lbl = new jxl.write.Label(2, i+1, list.get(i)
						.getOwnmonth().toString(), wcf);
				sheet.addCell(lbl);
				lbl = new jxl.write.Label(3, i+1, list.get(i)
						.getEmhc_name(), wcf);
				sheet.addCell(lbl);
				lbl = new jxl.write.Label(4, i+1, list.get(i)
						.getEmhc_companyid(), wcf);
				sheet.addCell(lbl);
				lbl = new jxl.write.Label(5, i+1, list.get(i)
						.getEmhc_houseid(), wcf);
				sheet.addCell(lbl);
				lbl = new jxl.write.Label(6, i+1, list.get(i)
						.getEmhc_idcard(), wcf);
				sheet.addCell(lbl);
				num = new jxl.write.Number(7, i+1, list.get(i)
						.getEmhc_radix(), wcf);
				sheet.addCell(num);
				lbl = new jxl.write.Label(8, i+1, list.get(i)
						.getEmhc_hj(), wcf);
				sheet.addCell(lbl);
				num = new jxl.write.Number(9, i+1, list.get(i)
						.getEmhc_cpp(), wcf);
				sheet.addCell(num);
				lbl = new jxl.write.Label(10, i+1, list.get(i)
						.getEmhc_change(), wcf);
				sheet.addCell(lbl);
				lbl = new jxl.write.Label(11, i+1, list.get(i)
						.getEmhc_confirmtime(), wcf);
				sheet.addCell(lbl);
				lbl = new jxl.write.Label(12, i+1, list.get(i)
						.getEmhc_declaretime(), wcf);
				sheet.addCell(lbl);
				lbl = new jxl.write.Label(13, i+1, list.get(i)
						.getCoba_client(), wcf);
				sheet.addCell(lbl);
				lbl = new jxl.write.Label(14, i+1, list.get(i)
						.getEmhc_statename(), wcf);
				sheet.addCell(lbl);
				lbl = new jxl.write.Label(15, i+1, list.get(i)
						.getEmhc_progressname(), wcf);
				sheet.addCell(lbl);
				lbl = new jxl.write.Label(16, i+1, list.get(i)
						.getEmhc_addname(), wcf);
				sheet.addCell(lbl);
			}
			
			wb.write();
			wb.close();
			workBook.close();
			Filedownload.save(new File(absolutePath
					+ "OfficeFile/DownLoad/Emhouse/" + exfileName), null);
			b = true;
		} catch (Exception e) {

		}

		return b;
	}
	

	public WritableCellFormat getBodyCellStyle() {

		/*
		 * WritableFont.createFont("宋体")：设置字体为宋体 10：设置字体大小
		 * WritableFont.NO_BOLD:设置字体非加粗（BOLD：加粗 NO_BOLD：不加粗） false：设置非斜体
		 * UnderlineStyle.NO_UNDERLINE：没有下划线
		 */
		WritableFont font = new WritableFont(WritableFont.createFont("宋体"), 10,
				WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE);

		WritableCellFormat bodyFormat = new WritableCellFormat(font);
		try {
			// 设置单元格背景色：表体为白色
			bodyFormat.setBackground(Colour.WHITE);
			// 设置表头表格边框样式
			// 整个表格线为细线、黑色
			bodyFormat
					.setBorder(Border.ALL, BorderLineStyle.THIN, Colour.BLACK);

		} catch (WriteException e) {
			System.out.println("表体单元格样式设置失败！");
		}
		return bodyFormat;
	}

	// 更新EXCEL文件
	public Integer updateExcel(Integer id, String filename, String className,
			Integer recordNum, String addname) {
		Integer i = 0;
		EmHouseChangeDal dal = new EmHouseChangeDal();
		try {
			i = dal.updateExcelFile(id, filename, className, recordNum, addname);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return i;
	}

	// 获取短信list表
	public List<SysMessageModel> getMsgList(String tbName, String username,
			Integer userid) {
		List<SysMessageModel> list = new ListModelList<>();
		SysMessage_EditDal dal = new SysMessage_EditDal();
		list = dal.getlist(tbName, username, userid);
		return list;
	}
}
