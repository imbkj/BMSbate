package bll.EmHouse;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Progressmeter;

import Model.CoHousingFundModel;
import Model.EmHouseGJJModel;
import Model.EmHouseGJJMonthModel;
import dal.CoHousingFund.CoHousingFund_ListDal;
import dal.EmHouse.EmHouseGJJDal;
import dal.EmHouse.EmHouseGJJMonthDal;

public class EmHouse_UpLoadBll {

	public List<EmHouseGJJMonthModel> getList(String companyid) {
		List<EmHouseGJJMonthModel> list = new ListModelList<>();
		EmHouseGJJMonthDal dal = new EmHouseGJJMonthDal();
		list = dal.getListByCompanyid(companyid);

		return list;
	}

	public Integer getRows(String companyid) {
		Integer i = 0;
		EmHouseGJJMonthDal dal = new EmHouseGJJMonthDal();
		i = dal.getRecordByCompanyId(companyid);

		return i;
	}

	public Integer addFiledata(File file, Integer ownmonth, Integer m,
			String username, Progressmeter p) {
		Integer i = 0;
		Workbook wb;
		try {

			wb = Workbook.getWorkbook(file);
			Sheet st = wb.getSheet(0);

			if ((m.equals(0) && st.getRows() > 4)
					|| (m.equals(1) && st.getRows() > 6)) {
				String companyid = "";
				String company = "";
				// Integer n = 0;
				EmHouseGJJMonthDal dal = new EmHouseGJJMonthDal();
				EmHouseGJJDal dal2 = new EmHouseGJJDal();

				if (m.equals(1)) {

					companyid = st.getCell(0, 2).getContents();
					company = st.getCell(0, 3).getContents();
					companyid = companyid.substring(7, companyid.length());
					company = company.substring(5, company.length());
					// n = dal.getRecordByCompanyId(companyid);
					dal.Del(companyid);
				} else {

					if (st.getCell(4, 1).getContents() != null
							&& !st.getCell(4, 1).getContents().equals("")) {
						companyid = st.getCell(4, 1).getContents();
						company = st.getCell(4, 2).getContents();
					} else {
						companyid = st.getCell(0, 1).getContents();
						companyid = companyid
								.substring((companyid.indexOf("：") + 1));
						company = st.getCell(0, 2).getContents();
						company = company.substring((company.indexOf("：") + 1));
						company = company.substring((company.indexOf("：") + 1));
					}

					// n = dal2.getRecordByCompanyId(companyid);
					dal2.Del(companyid);
				}

				// System.out.println(st.getRows());
				CoHousingFund_ListDal chDal = new CoHousingFund_ListDal();
				List<CoHousingFundModel> list = chDal
						.getListByHouseId(companyid);
				if (list.size() > 0) {
					Integer single = list.get(0).getCohf_single();

					if (m.equals(1)) {
						for (int j = 6; j < st.getRows(); j++) {
							if (st.getCell(2, j).getContents() != null
									&& !st.getCell(2, j).getContents()
											.equals("")) {
								EmHouseGJJMonthModel ehgm = new EmHouseGJJMonthModel();
								ehgm.setEmhu_companyid(companyid);
								ehgm.setEmhu_state(st.getCell(1, j)
										.getContents());
								ehgm.setEmhu_company(company);
								ehgm.setEmhu_idcard(st.getCell(3, j)
										.getContents());
								ehgm.setOwnmonth(ownmonth);
								ehgm.setEmhu_houseid(st.getCell(0, j)
										.getContents().replace(" ", ""));
								ehgm.setEmhu_name(st.getCell(2, j)
										.getContents());
								ehgm.setEmhu_radix(Double.valueOf(st.getCell(4,
										j).getContents()));
								ehgm.setEmhu_total(new BigDecimal(st
										.getCell(6, j).getContents()
										.replace(" ", "")));
								ehgm.setEmhu_filename(file.getName());
								ehgm.setEmhu_single(single);
								ehgm.setEmhu_addname(username);
								i = dal.add(ehgm);

							}
						}

					} else {
						for (int j = 4; j < st.getRows(); j++) {
							if (st.getCell(2, j).getContents() != null
									&& !st.getCell(2, j).getContents()
											.equals("")) {
								EmHouseGJJModel ehgm = new EmHouseGJJModel();
								ehgm.setEmhu_companyid(companyid);
								ehgm.setEmhu_company(company);
								ehgm.setEmhu_idcard(st.getCell(3, j)
										.getContents());
								
								if (st.getRows() > (j + 1)) {
									if ((st.getCell(0, j + 1).getContents() == null || st
											.getCell(0, j + 1).getContents().equals(""))
											&& (st.getCell(3, j + 1).getContents() != null || !st
													.getCell(3, j + 1).getContents().equals(
															""))) {
										ehgm.setEmhu_idcard(ehgm
												.getEmhu_idcard()
												+ st.getCell(3, j + 1)
														.getContents());
									}
								}
								for (int k = 10; k < 14; k++) {
									ehgm.setEmhu_idcard(ehgm.getEmhu_idcard()
											.replaceAll(
													String.valueOf((char) k),
													""));
								}

								
								ehgm.setOwnmonth(ownmonth);
								ehgm.setEmhu_houseid(st.getCell(1, j)
										.getContents().replace(" ", ""));
								ehgm.setEmhu_name(st.getCell(2, j)
										.getContents());
								ehgm.setEmhu_radix(Double.valueOf(st.getCell(4,
										j).getContents()));
								ehgm.setEmhu_cpp(Double.valueOf(st
										.getCell(5, j).getContents()));
								ehgm.setEmhu_opp(Double.valueOf(st
										.getCell(6, j).getContents()));
								// 计算公司部分金额
								ehgm.setEmhu_cp(ehgm.getEmhu_radix()
										* ehgm.getEmhu_cpp());
								BigDecimal b1 = new BigDecimal(
										ehgm.getEmhu_cp());
								ehgm.setEmhu_cp(b1.setScale(2,
										BigDecimal.ROUND_HALF_UP).doubleValue());
								// 计算个人部分金额
								ehgm.setEmhu_op(ehgm.getEmhu_radix()
										* ehgm.getEmhu_opp());
								BigDecimal b2 = new BigDecimal(
										ehgm.getEmhu_op());
								ehgm.setEmhu_op(b2.setScale(2,
										BigDecimal.ROUND_HALF_UP).doubleValue());

								ehgm.setEmhu_total(new BigDecimal(st
										.getCell(7, j).getContents()
										.replace(" ", "")));
								ehgm.setEmhu_filename(file.getName());
								ehgm.setEmhu_single(single);
								ehgm.setEmhu_addname(username);
								i = dal2.add(ehgm);

							}
						}
						// dal2.update();
					}

				} else {
					Messagebox.show("该账户(" + companyid + ")系统状态异常", "操作提示",
							Messagebox.OK, Messagebox.ERROR);
				}

				/*
				 * } else { Messagebox.show(company + "(" + companyid +
				 * ")台帐已经上传,不能重复上传，如需更新请删除原来台帐！", "操作提示", Messagebox.OK,
				 * Messagebox.ERROR);
				 * 
				 * }
				 */
			} else {
				Messagebox.show("文件中未找到数据", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}

			// 关闭excel
			wb.close();

		} catch (BiffException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} catch (ArrayIndexOutOfBoundsException e) {
			// TODO: handle exception
			Messagebox.show(file.getName() + "文件头格式有误,请保存文件后重新提交", "操作提示",
					Messagebox.OK, Messagebox.ERROR);

		}

		return i;
	}

	public void updateData(Integer m) {
		if (m.equals(1)) {
			EmHouseGJJMonthDal dal = new EmHouseGJJMonthDal();
			dal.mod();
		} else {
			EmHouseGJJDal dal = new EmHouseGJJDal();
			dal.mod();
		}

	}
}
