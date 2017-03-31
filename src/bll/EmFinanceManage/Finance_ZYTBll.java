package bll.EmFinanceManage;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import jxl.Sheet;
import jxl.Workbook;
import jxl.format.UnderlineStyle;
import jxl.read.biff.BiffException;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;

import Model.FinanceZYTModel;
import Util.FileOperate;
import Util.StringFormat;
import Util.pinyin4jUtil;
import dal.EmFinanceManage.Finance_ZYTDal;

public class Finance_ZYTBll {

	// 加载EXCEL文件
	public List<FinanceZYTModel> initData(String fileURL, String addname) {

		Workbook wb;
		List<FinanceZYTModel> list = new ListModelList<>();
		File file = new File(fileURL);
		List<FinanceZYTModel> flist = new ListModelList<>();
		/*
		 * String[] title = { "付款序号", "执行城市", "机构名称", "公司编号", "公司名称", "帐单年月",
		 * "台账日期", "收费类型", "雇员编号", "雇员姓名", "养老个人基数", "养老公司基数", "医疗个人基数",
		 * "医疗公司基数", "工伤个人基数", "工伤公司基数", "失业个人基数", "失业公司基数", "生育个人基数", "生育公司基数",
		 * "公积金个人基数", "公积金公司基数", "补充公积金个人基数", "补充公积金公司基数", "养老公司缴费", "养老个人缴费",
		 * "养老总计", "医疗公司缴费", "医疗个人缴费", "医疗总计", "失业公司缴费", "失业个人缴费", "失业总计",
		 * "工伤公司缴费", "工伤总计", "生育公司缴费", "生育总计", "公积金公司缴费", "公积金个人缴费", "公积金总计",
		 * "补充公积金公司缴费", "补充公积金个人缴费", "补充公积金总计", "支付福利费用总计", "额外费用总计", "档案费用总计",
		 * "服务费", "应收其中社保费", "应收其中公积金", "支付总计费用", "备注", "证件号码", "录入操作员编号",
		 * "智翼通雇员编号", "智翼通公司编号", "执行服务产品名称", "执行服务产品金额" };
		 */
		String[] title = { "付款序号", "执行城市", "机构名称", "公司编号", "公司名称", "帐单年月",
				"台账日期", "收费类型", "雇员编号", "雇员姓名", "养老个人基数", "养老公司基数", "医疗个人基数",
				"医疗公司基数", "工伤个人基数", "工伤公司基数", "失业个人基数", "失业公司基数", "生育个人基数",
				"生育公司基数", "公积金个人基数", "公积金公司基数", "补充公积金个人基数", "补充公积金公司基数",
				"养老公司缴费", "养老个人缴费", "养老总计", "医疗公司缴费", "医疗个人缴费", "医疗总计",
				"失业公司缴费", "失业个人缴费", "失业总计", "工伤公司缴费", "工伤总计", "生育公司缴费", "生育总计",
				"公积金公司缴费", "公积金个人缴费", "公积金总计", "补充公积金公司缴费", "补充公积金个人缴费",
				"补充公积金总计", "支付福利费用总计", "额外费用总计", "档案费用总计", "服务费", "应收其中社保费",
				"应收其中公积金", "支付总计费用" };
		try {
			wb = Workbook.getWorkbook(file);
			Sheet st = wb.getSheet(0);
			if (st.getRows() > 1) {
				/*
				 * if (st.getColumns()<title.length) {
				 * Messagebox.show("表头列数不匹配,请确认文件是否正确或核对表头.", "操作提示",
				 * Messagebox.OK, Messagebox.ERROR); //return list; }
				 */
				for (int i = 0; i < title.length; i++) {
					if (!st.getCell(i, 0).getContents().equals(title[i])) {
						Messagebox.show("导入数据表头不匹配,第" + (i + 1) + "列是["
								+ title[i] + "],EXCEL里是["
								+ st.getCell(i, 0).getContents() + "]", "操作提示",
								Messagebox.OK, Messagebox.ERROR);
						// return list;
					}
				}

				for (int j = 1; j < st.getRows(); j++) {
					if (st.getCell(0, j).getContents() != null
							&& !st.getCell(0, j).getContents().equals("")) {
						for (int k = 1; k < 50; k++) {

							if (st.getCell(k, j).getContents() == null
									|| st.getCell(k, j).getContents()
											.equals("")) {
								Messagebox.show("第" + (j + 1) + "行数据["
										+ st.getCell(k, 0).getContents()
										+ "]为空", "操作提示", Messagebox.OK,
										Messagebox.ERROR);
								return list;
							}
						}
					}
				}
				for (int j = 1; j < st.getRows(); j++) {
					if (st.getCell(1, j).getContents() != null
							&& !st.getCell(1, j).getContents().equals("")) {
						
						FinanceZYTModel m = new FinanceZYTModel();
						m.setPayid(st.getCell(0, j).getContents());
						m.setCity(st.getCell(1, j).getContents());
						m.setInstitution(st.getCell(2, j).getContents());
						m.setCid(StringFormat.replaceSpace(st.getCell(3, j)
								.getContents()));
						m.setCompany(st.getCell(4, j).getContents());
						m.setPayOwnmonth(st.getCell(5, j).getContents());
						m.setFinanceOwnmonth(st.getCell(6, j).getContents());
						m.setPayType(st.getCell(7, j).getContents());
						m.setGid(st.getCell(8, j).getContents());
						m.setName(st.getCell(9, j).getContents());

						m.setYlopRadix(new BigDecimal(st.getCell(10, j)
								.getContents()));
						m.setYlcpRadix(new BigDecimal(st.getCell(11, j)
								.getContents()));
						m.setJlopRadix(new BigDecimal(st.getCell(12, j)
								.getContents()));
						m.setJlcpRadix(new BigDecimal(st.getCell(13, j)
								.getContents()));
						m.setGsopRadix(new BigDecimal(st.getCell(14, j)
								.getContents()));
						m.setGscpRadix(new BigDecimal(st.getCell(15, j)
								.getContents()));
						m.setSyopRadix(new BigDecimal(st.getCell(16, j)
								.getContents()));
						m.setSycpRadix(new BigDecimal(st.getCell(17, j)
								.getContents()));
						m.setSyuopRadix(new BigDecimal(st.getCell(18, j)
								.getContents()));
						m.setSyucpRadix(new BigDecimal(st.getCell(19, j)
								.getContents()));
						m.setGjjopRadix(new BigDecimal(st.getCell(20, j)
								.getContents()));
						m.setGjjcpRadix(new BigDecimal(st.getCell(21, j)
								.getContents()));
						m.setGbcopRadix(new BigDecimal(st.getCell(22, j)
								.getContents()));
						m.setGbccpRadix(new BigDecimal(st.getCell(23, j)
								.getContents()));

						m.setYlcp(new BigDecimal(st.getCell(24, j)
								.getContents()));
						m.setYlop(new BigDecimal(st.getCell(25, j)
								.getContents()));
						m.setYltotal(new BigDecimal(st.getCell(26, j)
								.getContents()));
						m.setJlcp(new BigDecimal(st.getCell(27, j)
								.getContents()));
						m.setJlop(new BigDecimal(st.getCell(28, j)
								.getContents()));
						m.setJltotal(new BigDecimal(st.getCell(29, j)
								.getContents()));
						m.setSycp(new BigDecimal(st.getCell(30, j)
								.getContents()));
						m.setSyop(new BigDecimal(st.getCell(31, j)
								.getContents()));
						m.setSytotal(new BigDecimal(st.getCell(32, j)
								.getContents()));
						m.setGscp(new BigDecimal(st.getCell(33, j)
								.getContents()));
						m.setGstotal(new BigDecimal(st.getCell(34, j)
								.getContents()));
						m.setSyucp(new BigDecimal(st.getCell(35, j)
								.getContents()));
						m.setSyutotal(new BigDecimal(st.getCell(36, j)
								.getContents()));
						m.setGjjcp(new BigDecimal(st.getCell(37, j)
								.getContents()));
						m.setGjjop(new BigDecimal(st.getCell(38, j)
								.getContents()));
						m.setGjjtotal(new BigDecimal(st.getCell(49, j)
								.getContents()));
						m.setGbccp(new BigDecimal(st.getCell(40, j)
								.getContents()));
						m.setGbcop(new BigDecimal(st.getCell(41, j)
								.getContents()));
						m.setGbctotal(new BigDecimal(st.getCell(42, j)
								.getContents()));
						m.setFlTotal(new BigDecimal(st.getCell(43, j)
								.getContents()));
						m.setOtherTotal(new BigDecimal(st.getCell(44, j)
								.getContents()));
						m.setFileTotal(new BigDecimal(st.getCell(45, j)
								.getContents()));
						m.setFeeTotal(new BigDecimal(st.getCell(46, j)
								.getContents()));
						m.setSbTotal(new BigDecimal(st.getCell(47, j)
								.getContents()));
						m.setGjjTotal(new BigDecimal(st.getCell(48, j)
								.getContents()));

						m.setTotal(new BigDecimal(st.getCell(49, j)
								.getContents()));

						m.setBz(st.getCell(50, j).getContents());
						if (st.getColumns() > 51) {
							m.setIdcard(st.getCell(51, j).getContents());
						}
						if (st.getColumns() > 53) {
							m.setZytId(st.getCell(53, j).getContents());
						}
						if (st.getColumns() > 54) {
							m.setZytCid(st.getCell(54, j).getContents());
						}
						if (st.getColumns() > 55) {
							m.setProductName(st.getCell(55, j).getContents());
						}
						if (st.getColumns() > 56) {
							m.setProductFee(st.getCell(56, j).getContents());
						}

						m.setAddname(addname);
						flist.add(m);
					}
				}

			}
			wb.close();
		} catch (BiffException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (flist.size() > 0) {
			for (FinanceZYTModel m : flist) {
				boolean b = false;
				for (FinanceZYTModel m2 : list) {
					if (m.getCid().equals(m2.getCid())) {
						b = true;
						m2.setFlTotal(m2.getFlTotal().add(m.getFlTotal()));
						m2.setOtherTotal(m2.getOtherTotal().add(
								m.getOtherTotal()));
						m2.setFileTotal(m2.getFileTotal().add(m.getFileTotal()));
						m2.setFeeTotal(m2.getFeeTotal().add(m.getFeeTotal()));
						m2.setSbTotal(m2.getSbTotal().add(m.getSbTotal()));
						m2.setGjjTotal(m2.getGjjTotal().add(m.getGjjTotal()));
						m2.setTotal(m2.getTotal().add(m.getTotal()));

					}
				}
				if (!b) {

					FinanceZYTModel m3 = new FinanceZYTModel();
					m3.setCid(m.getCid());
					m3.setPayid(m.getPayid());
					m3.setCity(m.getCity());
					m3.setInstitution(m.getInstitution());
					m3.setCompany(m.getCompany());
					m3.setPayOwnmonth(m.getPayOwnmonth());

					m3.setFlTotal(m.getFlTotal());
					m3.setOtherTotal(m.getOtherTotal());
					m3.setFileTotal(m.getFileTotal());
					m3.setFeeTotal(m.getFeeTotal());
					m3.setSbTotal(m.getSbTotal());
					m3.setGjjTotal(m.getGjjTotal());
					m3.setTotal(m.getTotal());
					m3.setZytCid(m.getZytCid());
					m3.setAddname(m.getAddname());
					list.add(m3);
				}
			}

			// 删除未生成TXT数据
			Finance_ZYTDal dal = new Finance_ZYTDal();
			dal.delData(addname);
			// 添加数据
			addData(list);

		}
		return search(addname);
	}

	public void mod(Integer id, String uid, String compactType) {
		Finance_ZYTDal dal = new Finance_ZYTDal();
		dal.modData(id, uid, compactType);
	}

	public String txtFile(String city, String addname) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		Date trialTime = new Date();
		String exfileName = sdf.format(trialTime) + "_txt_"
				+ pinyin4jUtil.getPinYinHeadChar(addname) + ".xls";
		List<FinanceZYTModel> list = new ListModelList<>();
		Workbook workBook = null;
		WritableWorkbook wb = null;
		String absolutePath = FileOperate.getAbsolutePath();
		String url = absolutePath + "OfficeFile/DownLoad/CoFinanceZYT/"
				+ exfileName;
		try {
			workBook = Workbook.getWorkbook(new File(absolutePath
					+ "OfficeFile/Templet/CoFinanceZYT/txt.xls"));
			wb = Workbook.createWorkbook(new File(url), workBook);

			WritableSheet sheet = wb.getSheet(0);
			WritableCellFormat wcf = getBodyCellStyle();
			jxl.write.Label lbl = null;
			Finance_ZYTDal dal = new Finance_ZYTDal();
			list = dal.txt(city, addname);
			Integer i = 2;
			for (FinanceZYTModel m : list) {
				sheet.insertRow(i);
				lbl = new jxl.write.Label(3, i, m.getContent(), wcf);
				sheet.addCell(lbl);
				lbl = new jxl.write.Label(4, i, m.getKmId(), wcf);
				sheet.addCell(lbl);
				BigDecimal b = new BigDecimal(m.getFee());
				b = b.setScale(2, BigDecimal.ROUND_HALF_UP);
				lbl = new jxl.write.Label(5, i, b.toString(), wcf);
				sheet.addCell(lbl);
				lbl = new jxl.write.Label(6, i, "0.00", wcf);
				sheet.addCell(lbl);

				lbl = new jxl.write.Label(10, i, addname, wcf);
				sheet.addCell(lbl);
				lbl = new jxl.write.Label(16, i, m.getUid(), wcf);
				sheet.addCell(lbl);
				i++;
			}
			wb.write();
			wb.close();
			workBook.close();
		} catch (IOException | BiffException | WriteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return url;
	}

	public void addData(List<FinanceZYTModel> list) {
		Finance_ZYTDal dal = new Finance_ZYTDal();
		for (FinanceZYTModel m : list) {
			dal.add(m);
		}
	}

	public List<FinanceZYTModel> search(String addname) {
		List<FinanceZYTModel> list = new ListModelList<>();
		Finance_ZYTDal dal = new Finance_ZYTDal();
		list = dal.search(addname);
		return list;
	}

	public List<FinanceZYTModel> total(String addname) {
		List<FinanceZYTModel> list = new ListModelList<>();
		Finance_ZYTDal dal = new Finance_ZYTDal();
		list = dal.getTotal(addname);
		return list;
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
		/*
		 * try { // 设置单元格背景色：表体为白色 //bodyFormat.setBackground(Colour.WHITE); //
		 * 设置表头表格边框样式 // 整个表格线为细线、黑色 //bodyFormat // .setBorder(Border.ALL,
		 * BorderLineStyle., Colour.BLACK);
		 * 
		 * } catch (WriteException e) { System.out.println("表体单元格样式设置失败！"); }
		 */
		return bodyFormat;
	}
}
