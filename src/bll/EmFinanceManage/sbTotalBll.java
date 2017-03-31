package bll.EmFinanceManage;

import java.io.File;
import java.math.BigDecimal;
import java.util.List;

import jxl.Sheet;
import jxl.Workbook;

import org.zkoss.zul.ListModelList;

import Model.CoBaseModel;
import Model.sbTotalModel;
import Util.ExcelHelper;
import Util.HssfExcelHelper;
import dal.CoBase.CoBase_SelectDal;

public class sbTotalBll {

	// 加载EXCEL文件
	public List<sbTotalModel> initData(String fileURL, String addname) {

		Workbook wb = null;
		List<sbTotalModel> list = new ListModelList<>();
		List<sbTotalModel> flist = new ListModelList<>();
		File file = new File(fileURL);

		try {
			wb = Workbook.getWorkbook(file);
			Sheet st = wb.getSheet(0);
			if (st.getRows() > 1) {
				for (int j = 1; j < st.getRows(); j++) {
					if (st.getCell(1, j).getContents() != null
							&& !st.getCell(1, j).getContents().equals("")) {
						sbTotalModel m = new sbTotalModel();

						m.setFee(new BigDecimal(st.getCell(0, j).getContents()));
						m.setCompany(st.getCell(1, j).getContents());
						m.setClient(st.getCell(2, j).getContents());
						m.setCid(Integer
								.valueOf(st.getCell(3, j).getContents()));
						flist.add(m);
					}
				}
				wb.close();
			}
			for (sbTotalModel m1 : flist) {
				boolean b = false;
				for (sbTotalModel m2 : list) {
					if (m1.getCid().equals(m2.getCid())) {
						b = true;
						m2.setFee(m2.getFee().add(m1.getFee()));
					}
				}
				if (!b) {
					list.add(m1);
				}
			}
		} catch (Exception e) {
			if (wb != null) {
				wb.close();
			}
			e.printStackTrace();
		}

		return list;
	}

	public List<CoBaseModel> getuid(Integer cid) {
		String ufid = "";
		CoBase_SelectDal dal = new CoBase_SelectDal();
		List<CoBaseModel> list = dal.getUid(cid);

		return list;
	}

	public String export(String name, String url, List<sbTotalModel> list) {
		String[] titles = new String[] { "公司编号", "客服", "公司名称", "金额", "用友编号",
				"合同类型" };
		String[] fieldNames = new String[] { "cid", "client", "company", "fee",
				"uid", "compacttype" };
		File file = new File(url);
		try {
			ExcelHelper eh = HssfExcelHelper.getInstance(file);

			eh.writeExcel(sbTotalModel.class, list, fieldNames, titles);
		} catch (Exception e) {
		}
		return url;
	}
}
