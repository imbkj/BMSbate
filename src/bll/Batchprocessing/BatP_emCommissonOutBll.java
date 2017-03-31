package bll.Batchprocessing;

import java.util.List;

import org.zkoss.zul.Messagebox;

import bll.EmSheBao.Emsi_OperateBll;
import bll.EmSheBao.Emsi_SelBll;

import Model.CoFinanceCollectImportModel;
import Model.EmShebaoChangeUploadModel;
import Model.EmbaseModel;
import Util.UserInfo;
import dal.Batchprocessing.BatP_emCommissionOutDal;
import dal.Batchprocessing.BatP_embaseDal;
import dal.Batchprocessing.EmSheBao_BatchDal;

public class BatP_emCommissonOutBll {
	private BatP_emCommissionOutDal dal;

	public BatP_emCommissonOutBll() {
		dal = new BatP_emCommissionOutDal();
	}

	// 批量导入数据库
	public String[] addBatchUpload(List<EmbaseModel> ciList, String username,
			String uploadfilename) {
		String[] message = new String[3];
		int sum = ciList.size();
		int success = 0;
		int up = 0;
		for (EmbaseModel m : ciList) {
			try {
				m.setEmba_excelfile(uploadfilename);
				m.setEmba_addname(username);
				m.setEmba_statebatchstr("未提交");
				m.setEmba_batchtype("新增");
				m.setEmba_state(0);
				up = dal.addBatchUpload(m);
				if (up > 0) {
					success = success + 1;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (success == sum) {
			message[0] = "1";
			message[1] = "共导入数据" + sum + "条，全部成功。";
		} else {
			message[0] = "0";
			message[1] = "共导入数据" + sum + "条，其中有" + (sum - success) + "条数据导入失败。";
		}
		return message;
	}

	//批量导入基本信息
	public String[] addtoembase(List<EmbaseModel> list) {
		int sum = 0;
		int success = 0;
		String[] message = new String[2];

		try {

			for (EmbaseModel m : list) {
				m = checkdata(m);

				if (m.isCheck()) {
					m.setEmba_state(1);
					sum++;
					if ("".equals(m.getEmba_err())) {

						if (dal.insertembasebatch(m) > 0) {
							success++;
						}

					} else {
						m.setEmba_state(0);
						dal.updateEmbaseBatch(m);
					}
				} 
			}
			if (sum == 0) {
				message[0] = "0";
				message[1] = "请先勾选需要提交的数据。";
			} else if (sum == success) {
				message[0] = "1";
				message[1] = "共提交数据" + sum + "条，全部成功。";
			} else {
				message[0] = "1";
				message[1] = "共提交数据" + sum + "条，其中有" + (sum - success)
						+ "条数据提交失败。";
			}
		} catch (Exception e) {
			e.printStackTrace();
			message[0] = "2";
			message[1] = "处理数据时出错。";
		}
		return message;
	}

	
	// 社保批量导入embase
	public int insertembasebatch(EmbaseModel em) {
		return dal.insertembasebatch(em);

	}

	// 修改导入的数据 0：未提交；1：已提交；2：提交出错；3：删除；
	public String[]  updateEmbaseBatch(List<EmbaseModel> list) {
		//return dal.updateEmbaseBatch(em);
		int sum = 0;
		int success = 0;
		String[] message = new String[2];

		try {

			for (EmbaseModel m : list) {
				m = checkdata(m);

				if (m.isCheck()) {
					m.setEmba_state(3);
					sum++;
					if (dal.updateEmbaseBatch(m)>0)
					{
						success++;
					}
					}
				} 
		
			if (sum == 0) {
				message[0] = "0";
				message[1] = "请先勾选需要提交的数据。";
			} else if (sum == success) {
				message[0] = "1";
				message[1] = "共删除数据" + sum + "条，全部成功。";
			} else {
				message[0] = "1";
				message[1] = "共删除数据" + sum + "条，其中有" + (sum - success)
						+ "条数据删除失败。";
			}
		} catch (Exception e) {
			e.printStackTrace();
			message[0] = "2";
			message[1] = "处理数据时出错。";
		}
		return message;
		
	}

	// 根据用户名获取员工信息
	public List<EmbaseModel> getEmBaseById(String username) {
		return dal.getEmBaseById(username);
	}

	// 检查导入逻辑
	public EmbaseModel checkdata(EmbaseModel em) {

		return dal.checkdata(em);
	}

}
