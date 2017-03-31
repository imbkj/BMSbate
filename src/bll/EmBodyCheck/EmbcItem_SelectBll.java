package bll.EmBodyCheck;

import java.io.File;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;
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

import org.zkoss.zul.ListModelList;

import dal.LoginDal;
import dal.CoBase.CoBase_SelectDal;
import dal.EmBodyCheck.EmBcSetup_SelectDal;
import dal.EmBodyCheck.EmbcItem_OperateDal;
import dal.EmBodyCheck.EmbcItem_SelectDal;
import dal.SystemControl.UserListDal;

import Conn.dbconn;
import Controller.EmSheBaocard.newExcelImpl;
import Model.CoBaseModel;
import Model.EmBcItemGroupModel;
import Model.EmBcSetupModel;
import Model.EmBodyCheckItemModel;
import Model.LoginModel;
import Model.loginroleModel;
import Util.FileOperate;

public class EmbcItem_SelectBll {

	// 查询体检项目信息
	public List<EmBodyCheckItemModel> getEmBcItemInfo(String str) {
		EmbcItem_SelectDal dal = new EmbcItem_SelectDal();
		return dal.getEmBcItemInfo(str);
	}

	// 查询体检项目
	public List<EmBodyCheckItemModel> getEmbcItem(EmBodyCheckItemModel em) {
		List<EmBodyCheckItemModel> list = new ListModelList<>();
		EmbcItem_SelectDal dal = new EmbcItem_SelectDal();
		list = dal.getItemList(em);
		return list;
	}

	// 检查体检项目是否存在
	public boolean haveItem(String name, Integer ebit_id, Integer ebcs_id) {
		EmbcItem_SelectDal dal = new EmbcItem_SelectDal();
		return dal.ifExistItem(ebit_id, name, ebcs_id);
	}

	// 查询体检项目组合信息
	public List<EmBcItemGroupModel> getEmBcItemGroupInfo(String str) {
		EmbcItem_SelectDal dal = new EmbcItem_SelectDal();
		return dal.getEmBcItemGroupInfo(str);
	}

	// 查询体检组合信息
	public List<EmBcItemGroupModel> getEmbcItemGroupInfoList(
			EmBcItemGroupModel em) {
		List<EmBcItemGroupModel> list = new ListModelList<>();
		EmbcItem_SelectDal dal = new EmbcItem_SelectDal();
		list = dal.getGroupList(em);
		return list;
	}

	// 查询体检项目组合信息
	public List<EmBodyCheckItemModel> getEmBcItemGruopInfo(String str) {
		EmbcItem_SelectDal dal = new EmbcItem_SelectDal();
		return dal.getEmBcItemGruopInfo(str);
	}

	// 查询体检组合项目明细
	public List<EmBodyCheckItemModel> getEbItemList(EmBodyCheckItemModel em) {
		List<EmBodyCheckItemModel> list = new ListModelList<>();
		EmbcItem_SelectDal dal = new EmbcItem_SelectDal();
		list = dal.getEbItemList(em);
		return list;
	}

	// 查询公司信息
	public List<CoBaseModel> getCobaseInfo(CoBaseModel cm, String columns,
			Boolean distinct) {

		List<CoBaseModel> list = new ListModelList<>();
		CoBase_SelectDal dal = new CoBase_SelectDal();
		list = dal.getCoBaseInfo(cm, columns, distinct, 100, null);
		return list;
	}

	// 查询客服列表
	public List<loginroleModel> getClientList(loginroleModel lm) {
		List<loginroleModel> list = new ListModelList<>();
		UserListDal dal = new UserListDal();
		list = dal.getClientList(lm);
		return list;

	}

	// 查询公司列表
	public List<CoBaseModel> getCobaseList(String client) {
		List<CoBaseModel> list = new ListModelList<>();
		CoBase_SelectDal dal = new CoBase_SelectDal();
		list = dal.getcompanyList(client);
		return list;
	}

	// 查询机构信息
	public List<EmBcSetupModel> getEmbcSetUp(EmBcSetupModel em) {
		List<EmBcSetupModel> list = new ListModelList<>();
		EmBcSetup_SelectDal dal = new EmBcSetup_SelectDal();
		list = dal.getSetUpList(em);
		return list;
	}

	public BigDecimal[] getfee(String stritem) {
		EmbcItem_SelectDal dal = new EmbcItem_SelectDal();
		return dal.getfee(stritem);
	}

	// 查询项目是否在某个组合中
	public List<Integer> getitemid(String str) {
		EmbcItem_SelectDal dal = new EmbcItem_SelectDal();
		return dal.getitemid(str);
	}

	// 判断所选项目是否包含抽血项目限制
	public boolean getblood(EmBodyCheckItemModel em,
			List<EmBodyCheckItemModel> list) {
		boolean b = false;
		if (em.getEbit_package() != null && em.getEbit_package().equals(1)) {
			b = true;
		} else {

			if (em.getEbit_blood() != null && em.getEbit_blood().equals(1)) {
				for (int i = 0; i < list.size(); i++) {

					if (list.get(i).getEbit_bmain() != null
							&& list.get(i).getEbit_bmain().equals(1)) {
						b = true;
						break;
					}
					if (list.get(i).getEbit_package() != null
							&& list.get(i).getEbit_package().equals(1)) {
						b = true;
						break;
					}
				}
			} else {
				b = true;
			}
		}
		return b;

	}

	// 计算新建组费用
	public BigDecimal[] getfeeStr(String str) {
		EmbcItem_SelectDal dal = new EmbcItem_SelectDal();
		return dal.getfeeStr(str);
	}

	// 获取组合的费用
	public BigDecimal[] getgroupfee(String str) {
		EmbcItem_SelectDal dal = new EmbcItem_SelectDal();
		return dal.getgroupfee(str);
	}

	// 查询查看项目费用权限
	public boolean feePermission(Integer userid) {
		List<LoginModel> list = new ListModelList<>();
		LoginDal dal = new LoginDal();
		list = dal.fpList(userid);
		if (list.size() > 0) {
			return true;
		} else {
			return false;
		}

	}

	// 生成体检项目EXCEL表（前道）
	public boolean precreatefile(List<EmBodyCheckItemModel> ilist,
			Integer hospital, String exfilename, boolean fee) {
		boolean b = false;
		List<EmBodyCheckItemModel> list = new ListModelList<>();
		EmbcItem_SelectDal dal = new EmbcItem_SelectDal();
		EmBodyCheckItemModel em = new EmBodyCheckItemModel();
		em.setEbit_hospital(hospital);
		em.setEbit_state(1);
		em.setEbcs_state(1);
		if (ilist.size() <= 0) {
			list = dal.getItemList(em);
		} else {
			list = ilist;
		}

		Workbook workBook = null;
		WritableWorkbook wb = null;
		String absolutePath = FileOperate.getAbsolutePath();
		try {
			workBook = Workbook.getWorkbook(new File(absolutePath
					+ "/OfficeFile/Templet/EmBodyCheck/Item/preembcItem.xls"));
			wb = Workbook.createWorkbook(new File(absolutePath
					+ "/OfficeFile/DownLoad/EmBodyCheck/Item/" + exfilename),
					workBook);
			WritableSheet sheet = wb.getSheet(0);
			WritableCellFormat wcf = getBodyCellStyle();
			jxl.write.Label lbl = null;
			double d = 0;
			String marry="";
			for (int i = 0; i < list.size(); i++) {
				sheet.insertRow(i + 1);
				sheet.setRowView(i + 1, 375, false);

				// 序号
				lbl = new jxl.write.Label(0, i + 1, String.valueOf((i + 1)),
						wcf);
				sheet.addCell(lbl);
				// 医院
				lbl = new jxl.write.Label(1, i + 1, list.get(i)
						.getEbcs_hospital(), wcf);
				sheet.addCell(lbl);
				// 项目名称
				lbl = new jxl.write.Label(2, i + 1, list.get(i).getEbit_name(),
						wcf);
				sheet.addCell(lbl);
				// 内容
				lbl = new jxl.write.Label(3, i + 1, list.get(i).getEbit_info(),
						wcf);
				sheet.addCell(lbl);
				d = d + list.get(i).getEbit_charge().doubleValue();
				
				//项目全额
				/*String itemfee = "";
				if (list.get(i).getEbit_charge() != null) {
					itemfee = list.get(i).getEbit_charge().toString();
				}
				lbl = new jxl.write.Label(4, i + 1, itemfee, wcf);
				sheet.addCell(lbl);
				
				//项目折扣价
				String itemdisfee = "";
				if (list.get(i).getEbit_discount() != null) {
					itemdisfee = list.get(i).getEbit_discount().toString();
				}
				lbl = new jxl.write.Label(5, i + 1, itemdisfee, wcf);
				sheet.addCell(lbl);
				*/
				// 抽血项目
				if (list.get(i).getEbit_blood() != null) {
					lbl = new jxl.write.Label(4, i + 1, list.get(i)
							.getEbit_blood().equals(1) ? "是" : "否", wcf);
				} else {
					lbl = new jxl.write.Label(4, i + 1, "", wcf);
				}
				sheet.addCell(lbl);

				// 性别
				if (list.get(i).getEbit_sex() == null) {
					lbl = new jxl.write.Label(5, i + 1, "", wcf);
				} else if (list.get(i).getEbit_sex().equals(1)) {
					lbl = new jxl.write.Label(5, i + 1, "男性", wcf);
				} else if (list.get(i).getEbit_sex().equals(2)) {
					lbl = new jxl.write.Label(5, i + 1, "女性", wcf);
				} else {
					lbl = new jxl.write.Label(5, i + 1, "", wcf);
				}
				sheet.addCell(lbl);
				// 婚否
				if (list.get(i).getEbit_marry()!=null && !list.get(i).getEbit_marry().equals("")) {
					marry=list.get(i).getEbit_marry()
							.equals(1) ? "是" : "";
				}else {
					marry="";
				}
				lbl = new jxl.write.Label(6, i + 1, marry, wcf);
				sheet.addCell(lbl);
				// 系统ID
				//lbl = new jxl.write.Label(7, i + 1, list.get(i).getEbit_id()
				//		.toString(), wcf);
				//sheet.addCell(lbl);

			}
			/*lbl = new jxl.write.Label(10, 0, "总额:", wcf);
			sheet.addCell(lbl);
			lbl = new jxl.write.Label(11, 0, String.valueOf(d), wcf);
			sheet.addCell(lbl);*/
			wb.write();
			wb.close();
			workBook.close();
			b = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return b;
	}

	// 生成体检项目EXCEL表(后道)
	public boolean pixcreatefile(List<EmBodyCheckItemModel> ilist,
			Integer hospital, String exfilename, boolean fee) {
		boolean b = false;
		List<EmBodyCheckItemModel> list = new ListModelList<>();
		EmbcItem_SelectDal dal = new EmbcItem_SelectDal();
		EmBodyCheckItemModel em = new EmBodyCheckItemModel();
		em.setEbit_hospital(hospital);
		em.setEbit_state(1);
		em.setEbcs_state(1);
		if (ilist.size() <= 0) {
			list = dal.getItemList(em);
		} else {
			list = ilist;
		}

		Workbook workBook = null;
		WritableWorkbook wb = null;
		String absolutePath = FileOperate.getAbsolutePath();
		try {
			workBook = Workbook.getWorkbook(new File(absolutePath
					+ "OfficeFile/Templet/EmBodyCheck/Item/embcItem.xls"));
			wb = Workbook.createWorkbook(new File(absolutePath
					+ "OfficeFile/DownLoad/EmBodyCheck/Item/" + exfilename),
					workBook);
			WritableSheet sheet = wb.getSheet(0);
			WritableCellFormat wcf = getBodyCellStyle();
			jxl.write.Label lbl = null;
			double d = 0;
			String marry="";
			for (int i = 0; i < list.size(); i++) {
				marry="";
				sheet.insertRow(i + 1);
				sheet.setRowView(i + 1, 375, false);

				// 序号
				lbl = new jxl.write.Label(0, i + 1, String.valueOf((i + 1)),
						wcf);
				sheet.addCell(lbl);
				// 医院
				lbl = new jxl.write.Label(1, i + 1, list.get(i)
						.getEbcs_hospital(), wcf);
				sheet.addCell(lbl);
				// 项目名称
				lbl = new jxl.write.Label(2, i + 1, list.get(i).getEbit_name(),
						wcf);
				sheet.addCell(lbl);
				// 内容
				lbl = new jxl.write.Label(3, i + 1, list.get(i).getEbit_info(),
						wcf);
				sheet.addCell(lbl);
				d = d + list.get(i).getEbit_charge().doubleValue();

				String itemfee = "";
				if (list.get(i).getEbit_charge() != null) {
					itemfee = list.get(i).getEbit_charge().toString();
				}
				lbl = new jxl.write.Label(4, i + 1, itemfee, wcf);
				sheet.addCell(lbl);

				String itemdisfee = "";
				if (list.get(i).getEbit_discount() != null) {
					itemdisfee = list.get(i).getEbit_discount().toString();
				}
				lbl = new jxl.write.Label(5, i + 1, itemdisfee, wcf);
				sheet.addCell(lbl);

				// 抽血项目
				if (list.get(i).getEbit_blood() != null) {
					lbl = new jxl.write.Label(6, i + 1, list.get(i)
							.getEbit_blood().equals(1) ? "是" : "否", wcf);
				} else {
					lbl = new jxl.write.Label(6, i + 1, "", wcf);
				}
				sheet.addCell(lbl);

				// 性别
				if (list.get(i).getEbit_sex() == null) {
					lbl = new jxl.write.Label(7, i + 1, "", wcf);
				} else if (list.get(i).getEbit_sex().equals(1)) {
					lbl = new jxl.write.Label(7, i + 1, "男性", wcf);
				} else if (list.get(i).getEbit_sex().equals(2)) {
					lbl = new jxl.write.Label(7, i + 1, "女性", wcf);
				} else {
					lbl = new jxl.write.Label(7, i + 1, "", wcf);
				}
				sheet.addCell(lbl);
				// 婚否
				if (list.get(i).getEbit_marry()!=null) {
					marry=list.get(i).getEbit_marry()
							.equals(1) ? "是" : "";
				}
				lbl = new jxl.write.Label(8, i + 1, marry, wcf);
				sheet.addCell(lbl);
				// 系统ID
				lbl = new jxl.write.Label(9, i + 1, list.get(i).getEbit_id()
						.toString(), wcf);
				sheet.addCell(lbl);

			}
			lbl = new jxl.write.Label(10, 0, "总额:", wcf);
			sheet.addCell(lbl);
			lbl = new jxl.write.Label(11, 0, String.valueOf(d), wcf);
			sheet.addCell(lbl);
			wb.write();
			wb.close();
			workBook.close();
			b = true;
		} catch (Exception e) {
			e.printStackTrace();
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

	// 获取客服
	public List<String> getClientList() {
		EmbcItem_SelectDal dal = new EmbcItem_SelectDal();
		return dal.getClientList();
	}

	// 根据客服获取公司
	public List<CoBaseModel> getCobaseByClientList(String client) {
		EmbcItem_SelectDal dal = new EmbcItem_SelectDal();
		List<CoBaseModel> list = new ArrayList<CoBaseModel>();
		String str = "";
		if (client != null && !client.equals("")) {
			str = " and coba_client='" + client + "'";
			list = dal.getCobaseList(str);
		}
		return list;
	}

	// 查询是否已有员工使用组合
	public boolean getEmbaGroup(Integer groupid) {
		EmbcItem_SelectDal dal = new EmbcItem_SelectDal();
		return dal.getEmbaGroup(groupid);
	}

	// 查询EmBodyCheckCommit是否已有员工使用组合
	public boolean getCommitGroup(Integer groupid) {
		EmbcItem_SelectDal dal = new EmbcItem_SelectDal();
		return dal.getCommitGroup(groupid);
	}

	public boolean ifUseGroup(Integer groupid) {
		boolean flag = false;
		if (getEmbaGroup(groupid) && getCommitGroup(groupid)) {
			flag = true;
		}
		return flag;
	}
}
