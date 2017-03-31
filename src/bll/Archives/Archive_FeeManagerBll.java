package bll.Archives;

import java.io.File;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import org.zkoss.zul.ListModelList;

import dal.Archives.EmArchiveDal;
import dal.Archives.EmArchivePaymentDal;
import dal.Archives.EmArchiveRemakDal;
import dal.Archives.EmArchiveSetupDal;
import dal.CoBase.CoBase_SelectDal;
import dal.EmCAFFeeInfo.EmCAFFeeInfo_OperateDal;
import dal.SysMessage.SysMessage_EditDal;

import Model.CoBaseModel;
import Model.EmArchiveModel;
import Model.EmArchivePaymentModel;
import Model.EmArchiveRemarkModel;
import Model.EmArchiveSetupModel;
import Model.EmCAFFeeInfoModel;
import Model.SysMessageModel;
import Util.DateUtil;
import Util.FileOperate;
import Util.UserInfo;
import Util.pinyin4jUtil;

public class Archive_FeeManagerBll {
	public List<EmArchivePaymentModel> getBaseList(EmArchivePaymentModel em) {
		List<EmArchivePaymentModel> list = new ListModelList<EmArchivePaymentModel>();
		EmArchivePaymentDal dal = new EmArchivePaymentDal();
		try {
			list = dal.getBaseList(em);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 获取短信list表
	public List<SysMessageModel> getMsgList(String tbName, String username,
			Integer userid) {
		List<SysMessageModel> list = new ListModelList<>();
		SysMessage_EditDal dal = new SysMessage_EditDal();
		list = dal.getlist(tbName, username, userid);
		return list;
	}

	// 获取所属月份列表
	public List<EmArchivePaymentModel> getOwnmonthList() {
		List<EmArchivePaymentModel> list = new ListModelList<EmArchivePaymentModel>();
		EmArchivePaymentDal dal = new EmArchivePaymentDal();

		try {
			list = dal.getOwnmonth();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 获取借款人列表
	public List<EmArchivePaymentModel> getLoanManList() {
		List<EmArchivePaymentModel> list = new ListModelList<EmArchivePaymentModel>();
		EmArchivePaymentDal dal = new EmArchivePaymentDal();

		try {
			list = dal.getLoanMan();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 查询客服列表
	public List<CoBaseModel> getClientList(CoBaseModel cm) {
		List<CoBaseModel> list = new ListModelList<>();
		CoBase_SelectDal dal = new CoBase_SelectDal();
		list = dal.getCoBaseInfo(cm, "coba_client", true, null, "coba_client");
		return list;
	}

	// 查询公司列表
	public List<CoBaseModel> getComapnyList(CoBaseModel cm) {
		List<CoBaseModel> list = new ListModelList<>();
		CoBase_SelectDal dal = new CoBase_SelectDal();
		list = dal.getCoBaseInfo(cm, "cid,coba_shortname,coba_company", true,
				null, "coba_company");
		return list;
	}

	// 查询档案机构列表
	public List<EmArchiveSetupModel> getSetUpList(EmArchiveSetupModel em) {
		List<EmArchiveSetupModel> list = new ListModelList<>();
		EmArchiveSetupDal dal = new EmArchiveSetupDal();
		list = dal.getSetUpList(em);
		return list;
	}

	// 查询档案委托人才列表
	public List<EmArchivePaymentModel> getArchiveList(EmArchivePaymentModel em) {
		List<EmArchivePaymentModel> list = new ListModelList<>();
		EmArchivePaymentDal dal = new EmArchivePaymentDal();
		list = dal.getEmarchiveList(em, null, null, null);
		return list;
	}

	// 新增续费信息
	public Integer addpayment(EmArchivePaymentModel em) {
		Integer i = 0;
		EmArchivePaymentDal dal = new EmArchivePaymentDal();
		i = dal.add(em);
		return i;
	}

	// 修改续费信息
	public Integer modpayment(EmArchivePaymentModel em) {
		Integer i = 0;
		EmArchivePaymentDal dal = new EmArchivePaymentDal();
		i = dal.mod(em);
		return i;
	}

	// 删除续费信息
	public Integer delpayment(EmArchivePaymentModel em) {
		Integer i = 0;
		EmArchivePaymentDal dal = new EmArchivePaymentDal();
		i = dal.del(em.getEmap_id());
		return i;
	}

	// 删除续费财务费用信息
	public Integer delpaymentinfo(EmArchivePaymentModel em) {
		Integer i = 0;
		EmCAFFeeInfo_OperateDal dal = new EmCAFFeeInfo_OperateDal();
		i = dal.ecfiDelByTabId(em.getEmap_id(), "档案");
		return i;
	}

	// 续费信息导入财务表
	public Integer movePayment(EmArchivePaymentModel em) {
		Integer i = 0;
		EmCAFFeeInfoModel ecm = new EmCAFFeeInfoModel();
		EmCAFFeeInfo_OperateDal dal = new EmCAFFeeInfo_OperateDal();
		ecm.setCid(em.getCid());
		ecm.setGid(em.getGid());
		ecm.setOwnmonth(em.getOwnmonth());
		ecm.setEcfi_caf_id(em.getEmap_id());
		ecm.setEcfi_class("档案");
		ecm.setEcfi_cl_number(em.getEmap_idlist());
		ecm.setEcfi_payment_kind(em.getEmap_fpay());
		ecm.setEcfi_payment_state("未付");
		ecm.setEcfi_fee(em.getEmap_file().add(em.getEmap_hj())
				.add(em.getEmap_op()).intValue());
		ecm.setEcfi_addname(UserInfo.getUsername());

		i = dal.ecfiAdd(ecm);
		return i;
	}

	// 续费信息更新财务表
	public Integer movePaymentMod(EmArchivePaymentModel em) {
		EmCAFFeeInfo_OperateDal dal = new EmCAFFeeInfo_OperateDal();
		Integer i = dal.paymentmod(em, em.getEmap_id(), "档案");
		return i;

	}

	// 查询续费备注
	public List<EmArchiveRemarkModel> remarklist(Integer id) {
		List<EmArchiveRemarkModel> list = new ListModelList<>();
		EmArchiveRemakDal dal = new EmArchiveRemakDal();
		EmArchiveRemarkModel em = new EmArchiveRemarkModel();
		em.setEare_tid(3);
		em.setEare_trid(id);
		em.setEare_state(1);
		list = dal.getList(em, "order by eare_addtime desc");
		return list;
	}

	// 新增续费备注
	public Integer addRemark(Integer tid, Integer trid, String content,int gid) {
		Integer i = 0;
		EmArchiveRemakDal dal = new EmArchiveRemakDal();
		i = dal.add(tid, trid, content,gid);
		return i;
	}

	// 生成单号
	public String listId(List<EmArchivePaymentModel> list) {

		String absolutePath = FileOperate.getAbsolutePath();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd");
		Date trialTime = new Date();
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(trialTime);
		String exfileName = sdf.format(trialTime)
				+ pinyin4jUtil.getPinYinHeadChar(UserInfo.getUsername());
		Integer num = 0;
		BigDecimal bd = new BigDecimal(0);
		for (EmArchivePaymentModel em : list) {
			if (em.isChecked()) {
				num++;
				bd = bd.add(em.getEmap_file());
				bd = bd.add(em.getEmap_hj());
				bd = bd.add(em.getEmap_op());
			}
		}

		// 创建Excel文件
		WritableWorkbook wb;

		try {
			wb = Workbook.createWorkbook(new File(absolutePath
					+ "OfficeFile/DownLoad/EmArchivePayment/" + exfileName
					+ ".xls"));

			// 创建Excel工作表
			WritableSheet ws = wb.createSheet("payment", 0);
			Integer j = 11;
			jxl.write.Label lbl = null;
			WritableCellFormat wcf = getBodyCellStyle();
			WritableCellFormat wcf2 = getBodyCellStyle2();

			// 表头
			ws.insertRow(2);

			lbl = new jxl.write.Label(0, 2, "档案续费费用申请单", wcf2);
			ws.addCell(lbl);
			lbl = new jxl.write.Label(0, 3, calendar.get(Calendar.YEAR) + "年"
					+ (calendar.get(Calendar.MONTH) + 1) + "月"
					+ calendar.get(Calendar.DATE) + "日费用明细", wcf2);
			ws.addCell(lbl);
			lbl = new jxl.write.Label(0, 4, "单号:RS" + sdf2.format(trialTime)
					+ pinyin4jUtil.getPinYinHeadChar(UserInfo.getUsername()),
					wcf2);
			ws.addCell(lbl);
			lbl = new jxl.write.Label(0, 6, "本批续费费用总额为：", wcf2);
			ws.addCell(lbl);
			lbl = new jxl.write.Label(0, 7, "本次办理续费" + num + "人,预借档案/户口费用" + bd
					+ "元。", wcf2);
			ws.addCell(lbl);
			lbl = new jxl.write.Label(0, 10, "序号", wcf2);
			ws.addCell(lbl);
			lbl = new jxl.write.Label(1, 10, "客服代表", wcf2);
			ws.addCell(lbl);
			lbl = new jxl.write.Label(2, 10, "公司", wcf2);
			ws.addCell(lbl);
			lbl = new jxl.write.Label(3, 10, "姓名", wcf2);
			ws.addCell(lbl);
			lbl = new jxl.write.Label(4, 10, "档案付款性质", wcf2);
			ws.addCell(lbl);
			lbl = new jxl.write.Label(5, 10, "档案费用", wcf2);
			ws.addCell(lbl);
			lbl = new jxl.write.Label(6, 10, "户口付款性质", wcf2);
			ws.addCell(lbl);
			lbl = new jxl.write.Label(7, 10, "户口费用", wcf2);
			ws.addCell(lbl);
			lbl = new jxl.write.Label(8, 10, "滞纳金", wcf2);
			ws.addCell(lbl);
			// 数据
			Integer i = 1;
			for (EmArchivePaymentModel em : list) {
				if (em.isChecked()) {
					ws.insertRow(j);
					ws.setRowView(j, 375, false);

					Integer k = 0;
					lbl = new jxl.write.Label(k, j, i.toString(), wcf);
					ws.setColumnView(k, 5);
					ws.addCell(lbl);
					k++;
					lbl = new jxl.write.Label(k, j, em.getEmap_client(), wcf);

					ws.addCell(lbl);
					k++;
					lbl = new jxl.write.Label(k, j, em.getEmap_company(), wcf);
					ws.setColumnView(k, 20);
					ws.addCell(lbl);
					k++;
					lbl = new jxl.write.Label(k, j, em.getEmap_name(), wcf);
					ws.addCell(lbl);
					k++;
					lbl = new jxl.write.Label(k, j, em.getEmap_fpay(), wcf);
					ws.setColumnView(k, 20);
					ws.addCell(lbl);
					k++;
					lbl = new jxl.write.Label(k, j, em.getEmap_file()
							.toString(), wcf);
					ws.addCell(lbl);
					k++;
					lbl = new jxl.write.Label(k, j, em.getEmap_hpay(), wcf);
					ws.setColumnView(k, 20);
					ws.addCell(lbl);
					k++;
					lbl = new jxl.write.Label(k, j, em.getEmap_hj().toString(),
							wcf);
					ws.addCell(lbl);
					k++;
					lbl = new jxl.write.Label(k, j, em.getEmap_op().toString(),
							wcf);
					ws.addCell(lbl);

					j++;
					i++;
				}
			}
			// 写入数据
			wb.write();
			// 关闭文件
			wb.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}

		return sdf.format(trialTime);
	}

	// 导出报表
	public String report(List<EmArchivePaymentModel> list) {
		String absolutePath = FileOperate.getAbsolutePath();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		Date trialTime = new Date();
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(trialTime);
		String exfileName = sdf.format(trialTime) + "report";

		// 创建Excel文件
		WritableWorkbook wb;

		try {
			wb = Workbook.createWorkbook(new File(absolutePath
					+ "OfficeFile/DownLoad/EmArchivePayment/" + exfileName
					+ ".xls"));

			// 创建Excel工作表
			WritableSheet ws = wb.createSheet("report", 0);

			jxl.write.Label lbl = null;
			WritableCellFormat wcf = getBodyCellStyle3();
			String[] str = { "客服", "公司", "姓名", "身份证号", "档案所在地", "续费时段",
					"档案付款性质", "档案费用", "户口付款性质", "户口费用", "滞纳金" };
			// 表头
			ws.insertRow(0);
			ws.setRowView(0, 375, false);
			Integer i = 0;
			for (String s : str) {
				lbl = new jxl.write.Label(i, 0, s, wcf);
				ws.setColumnView(i, 13);
				ws.addCell(lbl);
				i++;
			}

			// 导入数据
			i = 1;
			for (EmArchivePaymentModel em : list) {
				if (em.isChecked()) {
					lbl = new jxl.write.Label(0, i, em.getEmap_client(), wcf);
					ws.addCell(lbl);
					lbl = new jxl.write.Label(1, i, em.getEmap_company(), wcf);
					ws.addCell(lbl);
					lbl = new jxl.write.Label(2, i, em.getEmap_name(), wcf);
					ws.addCell(lbl);
					lbl = new jxl.write.Label(3, i, em.getEmap_idcard(), wcf);
					ws.addCell(lbl);
					lbl = new jxl.write.Label(4, i, em.getEmap_fileplace(), wcf);
					ws.addCell(lbl);
					lbl = new jxl.write.Label(5, i, em.getCddate(), wcf);
					ws.addCell(lbl);
					lbl = new jxl.write.Label(6, i, em.getEmap_fpay(), wcf);
					ws.addCell(lbl);
					lbl = new jxl.write.Label(7, i, em.getEmap_file()
							.toString(), wcf);
					ws.addCell(lbl);
					lbl = new jxl.write.Label(8, i, em.getEmap_hpay(), wcf);
					ws.addCell(lbl);
					lbl = new jxl.write.Label(9, i, em.getEmap_hj().toString(),
							wcf);
					ws.addCell(lbl);
					lbl = new jxl.write.Label(10, i,
							em.getEmap_op().toString(), wcf);
					ws.addCell(lbl);
					i++;
				}
			}

			// 写入数据
			wb.write();
			// 关闭文件
			wb.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return exfileName;
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

	public WritableCellFormat getBodyCellStyle2() {

		/*
		 * WritableFont.createFont("宋体")：设置字体为宋体 10：设置字体大小
		 * WritableFont.NO_BOLD:设置字体非加粗（BOLD：加粗 NO_BOLD：不加粗） false：设置非斜体
		 * UnderlineStyle.NO_UNDERLINE：没有下划线
		 */
		WritableFont font = new WritableFont(WritableFont.createFont("宋体"), 10,
				WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE);

		WritableCellFormat bodyFormat = new WritableCellFormat(font);

		return bodyFormat;
	}

	public WritableCellFormat getBodyCellStyle3() {

		/*
		 * WritableFont.createFont("宋体")：设置字体为宋体 10：设置字体大小
		 * WritableFont.NO_BOLD:设置字体非加粗（BOLD：加粗 NO_BOLD：不加粗） false：设置非斜体
		 * UnderlineStyle.NO_UNDERLINE：没有下划线
		 */
		WritableFont font = new WritableFont(WritableFont.createFont("宋体"), 10,
				WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE);

		WritableCellFormat bodyFormat = new WritableCellFormat(font);
		try {
			bodyFormat.setAlignment(Alignment.CENTRE);
		} catch (WriteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bodyFormat;
	}

	// 计算费用
	public BigDecimal[] sumTotal(EmArchivePaymentModel em,
			EmArchiveSetupModel esm) {

		String indate = em.getEmap_itime();// 入职如期
		String cdDate = em.getEmap_cdate();// 新续费到期日
		// Date now = new Date();
		Integer d1 = 0; // 入职前独立计费时间段
		Integer d2 = 0; // 续费时间段
		Integer d3 = 0; // 滞纳金时间段
		BigDecimal rsFee = new BigDecimal(0);
		BigDecimal hjFee = new BigDecimal(0);
		BigDecimal znjFee = new BigDecimal(0);
		if (indate != null) {
			d1 = DateUtil.datediff(indate, em.getEmap_sdate(), "M");
		}

		if (cdDate != null) {

			d2 = DateUtil.datediff(cdDate, em.getEmap_sdate(), "M");
			d2 = d2 > 0 ? d2 : 0;

			d3 = d2 - esm.getEase_latedate();
			d3 = d3 > 0 ? (d3 + 1) : 0;

			if (em.getEmap_colhj() != null && !em.getEmap_colhj().equals(0)) {
				if (esm.getEase_rshjfee() != null) {
					// 档案费
					rsFee = rsFee.add(esm.getEase_rshjfee().multiply(
							new BigDecimal((d2 - (d1 > d2 ? 0 : d1)) / 12)));
					// 没有按年计算费用
					if (esm.getEase_rshjfee().compareTo(new BigDecimal(0)) == 0) {
						rsFee = rsFee.add(esm.getEase_rsfee().multiply(
								new BigDecimal(d2 - (d1 > d2 ? 0 : d1))));
					} else {
						rsFee = rsFee
								.add(esm.getEase_rsfee()
										.multiply(
												new BigDecimal(
														(d2 - (d1 > d2 ? 0 : d1)) % 12)));
					}
					// 入职前费用
					rsFee = rsFee.add(esm.getEase_rshjloan().multiply(
							new BigDecimal(d1 > d2 ? d2 : d1)));
					// 户口费
					if (em.getEmap_colhj().equals(1)) {
						if (esm.getEase_rshjfee().compareTo(new BigDecimal(0)) == 0) {
							hjFee = hjFee.add(esm.getEase_hjfee().multiply(
									new BigDecimal(d2 - (d1 > d2 ? 0 : d1))));
						}
						hjFee = hjFee
								.add(esm.getEase_hjfee()
										.multiply(
												new BigDecimal(
														(d2 - (d1 > d2 ? 0 : d1)) % 12)));
					}

					// 滞纳金
					znjFee = znjFee.add(esm.getEase_latefee().multiply(
							new BigDecimal(d3)));
				} else {
					rsFee = rsFee.add(esm.getEase_rsfee().multiply(
							new BigDecimal(d2)));
					hjFee = hjFee.add(esm.getEase_hjfee().multiply(
							new BigDecimal(d2)));
					znjFee = znjFee.add(esm.getEase_latefee().multiply(
							new BigDecimal(d3)));
				}
			} else {
				rsFee = rsFee.add(esm.getEase_rsfee().multiply(
						new BigDecimal(d2)));
				znjFee = znjFee.add(esm.getEase_latefee().multiply(
						new BigDecimal(d3)));
			}
		}
		BigDecimal[] charge = { rsFee, hjFee, znjFee };

		return charge;
	}

}
