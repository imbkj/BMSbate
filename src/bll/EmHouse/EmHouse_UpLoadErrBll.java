package bll.EmHouse;

import java.io.File;
import java.sql.SQLException;
import java.util.List;

import jxl.Sheet;
import jxl.Workbook;

import org.zkoss.zul.ListModelList;
import dal.EmHouse.EmHouseUploadErrDal;
import dal.SysMessage.Message_OperateDal;

import Model.EmHouseUploadErrModel;
import Model.SysMessageModel;

public class EmHouse_UpLoadErrBll {

	public boolean addData(File file, Integer ownmonth, String change,
			String addname) {
		boolean b = true;
		Integer i = 0;
		Workbook wb;
		try {
			wb = Workbook.getWorkbook(file);
			Sheet st = wb.getSheet(0);
			if (st.getRows() > 1) {
				EmHouseUploadErrDal dal = new EmHouseUploadErrDal();
				for (int j = 1; j < st.getRows(); j++) {
					if (st.getCell(0, j).getContents() != null
							&& !st.getCell(0, j).getContents().equals("")) {

						EmHouseUploadErrModel m = new EmHouseUploadErrModel();

						if (change.equals("新增")) {
							m.setEhle_idcard(st.getCell(0, j).getContents());
						} else if (change.equals("调入")) {
							m.setEhle_houseid(st.getCell(0, j).getContents());
						} else {
							m.setEhle_houseid(st.getCell(0, j).getContents());
						}
						m.setEhle_change(change);
						m.setEhle_errMessage(st.getCell(1, j).getContents());
						m.setEhle_addname(addname);
						i = dal.add(m);
						if (i.equals(0)) {
							b = false;
							break;
						}
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return b;
	}

	public Integer sendMessage( String title,String content, Integer sendId, String sendName,
			Integer rid,Integer dataId) {
		SysMessageModel model = new SysMessageModel();
		model.setSyme_content(content);// 短信内容
		model.setSyme_url("");
		model.setSyme_reply_id(0);
		model.setSyme_log_id(sendId);// 发件人id
		model.setSyme_addname(sendName);
		model.setSyme_para("");
		model.setSmwr_type("");
		//model.setSymr_name(sendName);// 收件人姓名
		model.setSymr_log_id(rid);// ;收件人id
		model.setEmail(1);
		model.setSyme_title(title);
		model.setEmailtitle(title);
		model.setSmwr_tid(dataId);
		model.setSmwr_table("emhousechange");
		Message_OperateDal dal = new Message_OperateDal();
		Integer i = 0;
		try {
			i = dal.SysMessageAdd(model);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return i;
	}

	public List<EmHouseUploadErrModel> search(Integer ownmonth, String change,
			String addname) {
		List<EmHouseUploadErrModel> list = new ListModelList<>();
		EmHouseUploadErrModel m = new EmHouseUploadErrModel();
		m.setOwnmonth(ownmonth);
		m.setEhle_change(change);
		m.setEhle_addname(addname);
		m.setEhle_state(1);

		EmHouseUploadErrDal dal = new EmHouseUploadErrDal();
		list = dal.getlist(m);
		return list;
	}

	// 删除数据
	public boolean delData(String name) {
		Integer i = 0;
		EmHouseUploadErrDal dal = new EmHouseUploadErrDal();
		i = dal.del(name);
		if (i > 0) {
			return true;
		} else {
			return false;
		}
	}

	// 批量删除
	public Integer delId(List<EmHouseUploadErrModel> list) {
		Integer i = 0;
		EmHouseUploadErrDal dal = new EmHouseUploadErrDal();
		for (EmHouseUploadErrModel m : list) {
			if (m.getChecked()) {
				Integer j = dal.delId(m.getEhle_id());
				if (!(j > 0)) {
					i = -1;
					break;
				}
			}
		}
		return i;
	}
}
