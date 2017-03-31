package bll.EmHouse;

import java.io.File;
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

import dal.CoHousingFund.CoHousingFund_ListDal;
import dal.EmHouse.EmHouseErrDal;
import dal.EmHouse.EmHouseErrMonthDal;
import dal.EmHouse.EmHouseGJJDal;
import dal.EmHouse.EmHouseGJJMonthDal;

import Model.CoHousingFundModel;
import Model.EmHouseChangeModel;
import Model.EmHouseErrModel;
import Model.EmHouseErrMonthModel;
import Model.EmHouseGJJModel;
import Model.EmHouseGJJMonthModel;
import Util.FileOperate;
import Util.UserInfo;
import Util.pinyin4jUtil;

public class EmHouse_FinanceBll {
	// 查询截单日列表
	public List<CoHousingFundModel> tsList() {
		List<CoHousingFundModel> list = new ListModelList<>();
		CoHousingFund_ListDal dal = new CoHousingFund_ListDal();
		list = dal.tsDateList();
		return list;
	}

	// 查询台前台帐列表
	public List<EmHouseGJJMonthModel> getEmhousegjjMonth(String name) {
		List<EmHouseGJJMonthModel> list = new ListModelList<>();
		EmHouseGJJMonthDal dal = new EmHouseGJJMonthDal();
		list = dal.getList(name);
		return list;
	}

	// 查询台前逻辑检查数据
	public List<EmHouseErrMonthModel> getEmhouseErrMonthList(Integer ownmoth,
			Integer single, String err, String client, String company,
			String name, String idcard, String houseid, Integer tsday,
			boolean distinct, String columns) {
		EmHouseErrMonthDal dal = new EmHouseErrMonthDal();
		EmHouseErrMonthModel em = new EmHouseErrMonthModel();
		em.setOwnmonth(ownmoth);
		em.setEmhe_single(single);
		em.setEmhe_err(err);
		em.setCoba_client(client);
		em.setCoba_shortname(company);
		em.setEmhe_name(name);
		em.setEmhe_idcard(idcard);
		em.setEmhe_houseid(houseid);
		em.setCohf_tsday(tsday);
		List<EmHouseErrMonthModel> list = dal.getList(em, distinct, columns);
		return list;
	}

	// 查询当前月份台前逻辑检查表是否有数据
	public boolean getEmhouseMonthErr() {
		boolean b = false;
		List<EmHouseErrMonthModel> list = new ListModelList<>();
		EmHouseErrMonthDal dal = new EmHouseErrMonthDal();
		list = dal.getListByOwnmonth();
		b = list.size() > 0 ? true : false;
		return b;
	}

	// 查询当前月份台前是否有上传数据
	public boolean getEmHouseMonthGjjNum() {
		boolean b = false;
		EmHouseGJJMonthDal dal = new EmHouseGJJMonthDal();
		Integer i = dal.getRecordByOwnmonth();
		if (i > 0) {
			b = true;
		}
		return b;
	}

	// 查询台后台帐列表
	public List<EmHouseGJJModel> getEmHousegjj(String name) {
		List<EmHouseGJJModel> list = new ListModelList<>();
		EmHouseGJJDal dal = new EmHouseGJJDal();
		list = dal.getList(name);
		return list;
	}

	// 查询台后逻辑检查数据
	public List<EmHouseErrModel> getEmhouseErrList(Integer ownmoth,
			Integer single, String err, String client, String company,
			String name, String idcard, String houseid, Integer tsday,
			boolean distinct, String columns) {
		EmHouseErrDal dal = new EmHouseErrDal();
		EmHouseErrModel em = new EmHouseErrModel();
		em.setOwnmonth(ownmoth);
		em.setEmhe_single(single);
		em.setEmhe_err(err);
		em.setCoba_client(client);
		em.setCoba_shortname(company);
		em.setEmhe_name(name);
		em.setEmhe_idcard(idcard);
		em.setEmhe_houseid(houseid);
		em.setCohf_tsday(tsday);
		List<EmHouseErrModel> list = dal.getList(em, distinct, columns);
		return list;
	}

	// 查询台后逻辑检查表是否有数据
	public boolean getEmhouseErr() {
		boolean b = false;
		List<EmHouseErrModel> list = new ListModelList<>();
		EmHouseErrDal dal = new EmHouseErrDal();
		list = dal.getListByOwnmonth();
		b = list.size() > 0 ? true : false;
		return b;
	}

	// 查询台后当前月份是否有上传数据
	public boolean getEmHouseGjjNum() {
		boolean b = false;

		EmHouseGJJDal dal = new EmHouseGJJDal();
		Integer i = dal.getRecordByOwnmonth();
		if (i > 0) {
			b = true;
		}
		return b;
	}

	// 计算台帐前人数
	public Integer getEmhousegjjMonthNum(String name, Integer type) {
		Integer i = 0;
		EmHouseGJJMonthDal dal = new EmHouseGJJMonthDal();
		i = dal.getNum(name, type);

		return i;
	}

	// 计算台帐后人数
	public Integer getEmhousegjjNum(String name, Integer type) {
		Integer i = 0;
		EmHouseGJJDal dal = new EmHouseGJJDal();
		i = dal.getNum(name, type);

		return i;
	}

	// 清空台帐前名单
	public Integer DelGjjmonthList() {
		Integer i = 0;
		EmHouseGJJMonthDal dal = new EmHouseGJJMonthDal();
		i = dal.clear();
		return i;
	}

	// 清空台帐后名单
	public Integer DelGjjList() {
		Integer i = 0;
		EmHouseGJJDal dal = new EmHouseGJJDal();
		i = dal.clear();
		return i;
	}

	// 删除台帐前名单
	public Integer DelGjjmonthList(String companyid) {
		Integer i = 0;
		EmHouseGJJMonthDal dal = new EmHouseGJJMonthDal();
		i = dal.Del(companyid);
		return i;
	}

	// 删除台帐后名单
	public Integer DelGjjList(String companyid) {
		Integer i = 0;
		EmHouseGJJDal dal = new EmHouseGJJDal();
		i = dal.Del(companyid);
		return i;
	}

	// 当前月份台前逻辑检查
	public Integer checkMonthErr() {
		Integer i = 0;
		EmHouseErrMonthDal dal = new EmHouseErrMonthDal();
		i = dal.checkerr();

		return i;
	}

	// 删除当月台前逻辑检查数据
	public Integer DelMonthErr() {
		Integer i = 0;
		EmHouseErrMonthDal dal = new EmHouseErrMonthDal();
		i = dal.Del();

		return i;
	}

	// 当前月份台后逻辑检查
	public Integer checkErr() {
		Integer i = 0;
		EmHouseErrDal dal = new EmHouseErrDal();
		i = dal.checkerr();

		return i;
	}

	// 删除当月台后逻辑检查数据
	public Integer DelErr() {
		Integer i = 0;
		EmHouseErrDal dal = new EmHouseErrDal();
		i = dal.Del();

		return i;
	}

	// 导出台后数据
	public boolean exportErrExcel(List<EmHouseErrModel> list) {
		boolean b = false;

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		Date trialTime = new Date();

		String exfileName = sdf.format(trialTime) + "_houseErr_"
				+ pinyin4jUtil.getPinYinHeadChar(UserInfo.getUsername())
				+ ".xls";

		Workbook workBook = null;
		WritableWorkbook wb = null;
		String absolutePath = FileOperate.getAbsolutePath();

		try {
			workBook = Workbook.getWorkbook(new File(absolutePath
					+ "OfficeFile/Templet/Emhouse/houseErr.xls"));
			wb = Workbook.createWorkbook(new File(absolutePath
					+ "OfficeFile/DownLoad/Emhouse/" + exfileName), workBook);
			WritableSheet sheet = wb.getSheet(0);
			WritableCellFormat wcf = getBodyCellStyle();
			jxl.write.Label lbl = null;
			jxl.write.Number num = null;
			for (Integer i = 0; i < list.size(); i++) {
				sheet.insertRow(i + 1);
				lbl = new jxl.write.Label(0, i + 1, String.valueOf((i + 1)),
						wcf);
				sheet.addCell(lbl);
				if (list.get(i).getOwnmonth() != null) {
					lbl = new jxl.write.Label(1, i + 1, list.get(i)
							.getOwnmonth().toString(), wcf);
					sheet.addCell(lbl);
				}
				if (list.get(i).getEmhe_houseid() != null) {
					lbl = new jxl.write.Label(2, i + 1, list.get(i)
							.getEmhe_houseid().toString(), wcf);
					sheet.addCell(lbl);
				}

				if (list.get(i).getEmhe_idcard() != null) {
					lbl = new jxl.write.Label(3, i + 1, list.get(i)
							.getEmhe_idcard(), wcf);
					sheet.addCell(lbl);
				}

				if (list.get(i).getSingle() != null) {
					lbl = new jxl.write.Label(4, i + 1,
							list.get(i).getSingle(), wcf);
					sheet.addCell(lbl);
				}

				if (list.get(i).getCohf_tsday() != null) {
					lbl = new jxl.write.Label(5, i + 1, list.get(i)
							.getCohf_tsday().toString(), wcf);
					sheet.addCell(lbl);
				}

				if (list.get(i).getCoba_company() != null) {
					lbl = new jxl.write.Label(6, i + 1, list.get(i)
							.getCoba_company(), wcf);
					sheet.addCell(lbl);
				}

				if (list.get(i).getEmhe_companyid() != null) {
					lbl = new jxl.write.Label(7, i + 1, list.get(i)
							.getEmhe_companyid(), wcf);
					sheet.addCell(lbl);
				}

				if (list.get(i).getCoba_client() != null) {
					lbl = new jxl.write.Label(8, i + 1, list.get(i)
							.getCoba_client(), wcf);
					sheet.addCell(lbl);
				}

				if (list.get(i).getEmhe_name() != null) {
					lbl = new jxl.write.Label(9, i + 1, list.get(i)
							.getEmhe_name(), wcf);
					sheet.addCell(lbl);
				}

				if (list.get(i).getEmhe_err() != null) {
					lbl = new jxl.write.Label(10, i + 1, list.get(i)
							.getEmhe_err(), wcf);
					sheet.addCell(lbl);
				}

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

	// 导出台前数据
	public boolean exportMonthErrExcel(List<EmHouseErrMonthModel> list) {
		boolean b = false;

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		Date trialTime = new Date();

		String exfileName = sdf.format(trialTime) + "_houseMonthErr_"
				+ pinyin4jUtil.getPinYinHeadChar(UserInfo.getUsername())
				+ ".xls";

		Workbook workBook = null;
		WritableWorkbook wb = null;
		String absolutePath = FileOperate.getAbsolutePath();

		try {
			workBook = Workbook.getWorkbook(new File(absolutePath
					+ "OfficeFile/Templet/Emhouse/houseErr.xls"));
			wb = Workbook.createWorkbook(new File(absolutePath
					+ "OfficeFile/DownLoad/Emhouse/" + exfileName), workBook);
			WritableSheet sheet = wb.getSheet(0);
			WritableCellFormat wcf = getBodyCellStyle();
			jxl.write.Label lbl = null;
			jxl.write.Number num = null;
			for (Integer i = 0; i < list.size(); i++) {
				sheet.insertRow(i + 1);
				lbl = new jxl.write.Label(0, i + 1, String.valueOf((i + 1)),
						wcf);
				sheet.addCell(lbl);
				if (list.get(i).getOwnmonth() != null) {
					lbl = new jxl.write.Label(1, i + 1, list.get(i)
							.getOwnmonth().toString(), wcf);
					sheet.addCell(lbl);
				}
				if (list.get(i).getEmhe_houseid() != null) {
					lbl = new jxl.write.Label(2, i + 1, list.get(i)
							.getEmhe_houseid().toString(), wcf);
					sheet.addCell(lbl);
				}

				if (list.get(i).getEmhe_idcard() != null) {
					lbl = new jxl.write.Label(3, i + 1, list.get(i)
							.getEmhe_idcard(), wcf);
					sheet.addCell(lbl);
				}

				if (list.get(i).getSingle() != null) {
					lbl = new jxl.write.Label(4, i + 1,
							list.get(i).getSingle(), wcf);
					sheet.addCell(lbl);
				}

				if (list.get(i).getCohf_tsday() != null) {
					lbl = new jxl.write.Label(5, i + 1, list.get(i)
							.getCohf_tsday().toString(), wcf);
					sheet.addCell(lbl);
				}

				if (list.get(i).getCoba_company() != null) {
					lbl = new jxl.write.Label(6, i + 1, list.get(i)
							.getCoba_company(), wcf);
					sheet.addCell(lbl);
				}

				if (list.get(i).getEmhe_companyid() != null) {
					lbl = new jxl.write.Label(7, i + 1, list.get(i)
							.getEmhe_companyid(), wcf);
					sheet.addCell(lbl);
				}

				if (list.get(i).getCoba_client() != null) {
					lbl = new jxl.write.Label(8, i + 1, list.get(i)
							.getCoba_client(), wcf);
					sheet.addCell(lbl);
				}

				if (list.get(i).getEmhe_name() != null) {
					lbl = new jxl.write.Label(9, i + 1, list.get(i)
							.getEmhe_name(), wcf);
					sheet.addCell(lbl);
				}

				if (list.get(i).getEmhe_err() != null) {
					lbl = new jxl.write.Label(10, i + 1, list.get(i)
							.getEmhe_err(), wcf);
					sheet.addCell(lbl);
				}

			}

			wb.write();
			wb.close();
			workBook.close();
			Filedownload.save(new File(absolutePath
					+ "OfficeFile/DownLoad/Emhouse/" + exfileName), null);
			b = true;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return b;
	}

}
