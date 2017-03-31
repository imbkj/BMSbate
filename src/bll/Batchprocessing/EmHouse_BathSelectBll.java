package bll.Batchprocessing;

import java.util.List;

import org.zkoss.zul.ListModelList;

import Model.EmHouseChangeModel;
import Model.EmHouseUpdateModel;
import Model.EmHouseUploadModel;
import Util.IdcardUtil;
import Util.UserInfo;
import dal.Batchprocessing.EmHouse_BathSelectDal;

public class EmHouse_BathSelectBll {
	private EmHouse_BathSelectDal dal = new EmHouse_BathSelectDal();

	// 根据gid获取员工的基本信息
	public EmHouseChangeModel getEmbaInfo(EmHouseChangeModel m, Integer type) {
		m = dal.getEmbaInfo(m, type);
		if (m.getErrorMsg() != null && !m.getErrorMsg().equals("")) {
			m.setMessage(false);
		}
		return m;
	}

	// 根据GID获取员工公积金在册数据
	public EmHouseChangeModel getgjjinfo(EmHouseChangeModel m) {
		List<EmHouseUpdateModel> list = new ListModelList<>();
		list = dal.getupdateList(m.getGid(), m.getEmhc_cpp());
		if (list.size() > 0) {
			m.setEmhu_id(list.get(0).getEmhu_id());
			m.setCid(list.get(0).getCid());
			if (!UserInfo.getDepID().equals("6")) {
				if (!m.getEmhc_name().equals(list.get(0).getEmhu_name())) {
					m.setErrorMsg("姓名匹配不正确");
					m.setMessage(false);
				}
				String idcard = IdcardUtil.conver15CardTo18(m.getEmhc_idcard());

				if (idcard == null
						|| !idcard.equals(list.get(0).getEmhu_idcard2())) {
					m.setErrorMsg("身份证匹配不正确");
					m.setMessage(false);
				}
			} else {
				m.setEmhc_name(list.get(0).getEmhu_name());
				m.setEmhc_idcard(list.get(0).getEmhu_idcard());
			}

			m.setCoba_company(list.get(0).getEmhu_company());
			m.setEmhc_ifprogress(41);
			m.setEmhc_type(3);
			m.setEmhc_single(list.get(0).getEmhu_single());
			m.setEmhc_houseid(list.get(0).getEmhu_houseid());
			if (list.get(0).getEmhu_single().equals(1)
					&& !list.get(0).getEmhu_companyid()
							.equals(list.get(0).getCohf_houseid())) {
				m.setErrorMsg("独立户不允许修改比例");
				m.setMessage(false);
			} else {
				m.setEmhc_companyid(list.get(0).getCohf_houseid());
			}
			if (list.get(0).getCoco_cpp()!=null && !list.get(0).getCoco_cpp().equals("浮动比例")) {
				if (!list.get(0).getCoco_cpp().equals(m.getEmhc_cpp().toString())) {
					m.setErrorMsg("公司比例("+list.get(0).getCoco_cpp()+")与员工参保比例不一致");
				}
			}
			
			if (m.getEmhc_radix().equals(list.get(0).getEmhu_radix())
					&& m.getEmhc_cpp().equals(list.get(0).getEmhu_cpp())
					&& m.getEmhc_single().equals(list.get(0).getEmhu_single())) {
				m.setErrorMsg("比例基数与在册数据相同.");
				m.setMessage(false);
			}

		}
		return m;
	}

	public List<EmHouseUploadModel> getEmHouseUploadList(String str) {
		return dal.getEmHouseUploadList(str);
	}

	// 获取最后一个批量号
	public String getLastBatchNumber(String type) {
		return dal.getLastBatchNumber(type);
	}
}
