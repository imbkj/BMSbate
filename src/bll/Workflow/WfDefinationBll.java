package bll.Workflow;

import java.text.SimpleDateFormat;
import java.util.List;
import org.zkoss.zul.ListModelList;
import dal.Workflow.WfDefinationDal;
import Model.WfDefinationModel;


public class WfDefinationBll {

	/**
	 * 获取任务列表
	 * 
	 * @param wfdm
	 * @return list
	 */
	public List<WfDefinationModel> getDefinationList(WfDefinationModel wfdm) {
		List<WfDefinationModel> list = new ListModelList<WfDefinationModel>();
		WfDefinationDal dal = new WfDefinationDal();
		StringBuilder sql = new StringBuilder();

		String wfclname;
		String wfdename;
		sql.append("select *,case wfde_state when 1 then '生效' else '取消' end wfde_stateName from WfDefination a inner join WfClass b on a.wfde_wfcl_id=b.wfcl_id where 1=1");
		if (wfdm.getWfcl_name() == null || wfdm.getWfcl_name() == "") {
			sql.append(" and '1' = ?");
			wfclname = "1";

		} else {
			if (wfdm.getWfcl_name().matches(",")) {
				sql.append(" and wfcl_name in (?)");
				wfclname = wfdm.getWfcl_name().replace(",", "','");
			} else {
				sql.append(" and wfcl_name like ?");
				wfclname = wfdm.getWfcl_name() + "%";
			}

		}

		if (wfdm.getWfde_name() == null || wfdm.getWfde_name() == "") {
			sql.append(" and '1' = ?");
			wfdename = "1";
		} else {
			if (wfdm.getWfde_name().matches(",")) {
				sql.append(" and wfde_name in (?)");
				wfdename = wfdm.getWfde_name().replace(",", "','");
			} else {
				sql.append(" and wfde_name like ?");
				wfdename = wfdm.getWfde_name() + "%";
			}

		}

		sql.append(" and wfde_code like ?");
		sql.append(" and wfde_state like ?");
		sql.append(" and wfde_addname like ?");
		sql.append(" and convert(varchar(10),wfde_addtime,120) like ?");
		sql.append(" and case wfde_state when 1 then '生效' else '取消' end like ?");
		sql.append(" order by wfde_state desc,wfde_wfcl_id,wfde_id");

		String wfdecode = wfdm.getWfde_code() == null ? "%" : wfdm
				.getWfde_code() + "%";
		String wfdestate = wfdm.getWfde_state() == null ? "%" : wfdm
				.getWfde_state() + "%";
		String wfdeaddname = wfdm.getWfde_addname() == null ? "%" : wfdm
				.getWfde_addname() + "%";

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String wfdeaddtime = wfdm.getWfde_addtime() == null ? "%" : sdf
				.format(wfdm.getWfde_addtime()) + "%";
		String wfdestateName = wfdm.getWfde_stateName() == null ? "%" : wfdm
				.getWfde_stateName() + "%";

		try {
			list = dal.getDefinationModelsBySQL(sql.toString(), wfclname,
					wfdename, wfdecode, wfdestate, wfdeaddname, wfdeaddtime,wfdestateName);
		} catch (Exception e) {
			e.printStackTrace();

		}
		return list;
	}

	/**
	 * 获取任务列表
	 * 
	 * @param id
	 * @return list
	 */
	public List<WfDefinationModel> getDefinationListById(Integer id) {
		List<WfDefinationModel> list = new ListModelList<WfDefinationModel>();
		WfDefinationDal dal = new WfDefinationDal();
		String sql = "select *,case wfde_state when 1 then '生效' else '取消' end wfde_stateName from WfDefination a inner join WfClass b on a.wfde_wfcl_id=b.wfcl_id where wfde_id=?";
		try {
			list = dal.getDefinationModelsBySQL(sql, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 返回任务名称
	 * 
	 * @param name
	 * @return list
	 */
	public List<WfDefinationModel> getDefinationNameList(String name) {
		List<WfDefinationModel> list = new ListModelList<WfDefinationModel>();
		WfDefinationDal dal = new WfDefinationDal();
		String sql = "select distinct wfde_name from WfDefination where wfde_name like ?";
		try {
			list = dal.getName(sql, name + "%");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 返回任务单标识码
	 * 
	 * @param code
	 * @return list
	 */
	public List<WfDefinationModel> getDefinationCodeList(String code) {
		List<WfDefinationModel> list = new ListModelList<WfDefinationModel>();
		WfDefinationDal dal = new WfDefinationDal();
		String sql = "select distinct wfde_code from WfDefination where wfde_code like ?";
		try {
			list = dal.getCode(sql, code + "%");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 返回添加人列表
	 * 
	 * @param addname
	 * @return list
	 */
	public List<WfDefinationModel> getDefinationAddNameList(String addname) {
		List<WfDefinationModel> list = new ListModelList<WfDefinationModel>();
		WfDefinationDal dal = new WfDefinationDal();
		String sql = "select distinct wfde_addname from WfDefination where wfde_addname like ?";
		try {
			list = dal.getAddName(sql, addname + "%");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	

	/**
	 * 添加数据
	 * 
	 * @param wfde_wfcl_id
	 * @param wfde_code
	 * @param wfde_name
	 * @param wfde_remark
	 * @param wfde_addname
	 * @return message
	 */
	public String[] AddWfDefination(Integer wfde_wfcl_id, String wfde_code,
			String wfde_name, String wfde_remark, String wfde_addname) {

		String[] message = new String[2];
		try {
			message = judgeName(wfde_wfcl_id, wfde_code, wfde_name);

			if (message[0] == null) {
				int result = 0;
				WfDefinationModel wfdm = new WfDefinationModel();
				WfDefinationDal dal = new WfDefinationDal();
				wfdm.setWfde_wfcl_id(wfde_wfcl_id);
				wfdm.setWfde_code(wfde_code);
				wfdm.setWfde_name(wfde_name);
				wfdm.setWfde_remark(wfde_remark);
				wfdm.setWfde_addname(wfde_addname);
				result = dal.insertWfDefination(wfdm);

				if (result > 0) {
					message[0] = "1";
					message[1] = "新增任务成功!";
				} else {
					message[0] = "0";
					message[1] = "新增任务失败!";
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
			message[0] = "2";
			message[1] = "任务单，新增任务出错。";
		}
		return message;
	}

	/**
	 * 修改任务信息
	 * 
	 * @param wfde_id
	 * @param wfde_wfcl_id
	 * @param wfde_code
	 * @param wfde_name
	 * @param wfde_remark
	 * @param wfde_Modname
	 * @param wfde_state
	 * @return
	 */
	public String[] ModWfDefination(Integer wfde_id, Integer wfde_wfcl_id,
			String wfde_code, String wfde_name, String wfde_remark,
			String wfde_Modname,Integer wfde_state) {
		String[] message = new String[2];
		try {
			message = judgeName2(wfde_id,wfde_wfcl_id, wfde_code, wfde_name);
			if (message[0] == null) {
				int result = 0;
				WfDefinationModel wfdm = new WfDefinationModel();
				WfDefinationDal dal = new WfDefinationDal();
				wfdm.setWfde_id(wfde_id);
				wfdm.setWfde_wfcl_id(wfde_wfcl_id);
				wfdm.setWfde_code(wfde_code);
				wfdm.setWfde_name(wfde_name);
				wfdm.setWfde_remark(wfde_remark);
				wfdm.setWfde_updatename(wfde_Modname);
				wfdm.setWfde_state(wfde_state);
				result = dal.UpdateWfDefination(wfdm);

				if (result > 0) {
					message[0] = "1";
					message[1] = "更新任务成功!";
				} else {
					message[0] = "0";
					message[1] = "更新任务失败!";
				}
			}
				
		} catch (Exception e) {
			e.printStackTrace();
			message[0] = "2";
			message[1] = "任务单，更新任务出错。";
		}

		return message;
	}

	/**
	 * 校验新增录入数据唯一性
	 * 
	 * @param wfcl_id
	 * @param wfde_code
	 * @param wfde_name
	 * @return message
	 */
	public String[] judgeName(Integer wfcl_id, String wfde_code,
			String wfde_name) {
		String[] message = new String[2];
		WfDefinationDal dal = new WfDefinationDal();
		List<WfDefinationModel> list = new ListModelList<WfDefinationModel>();
		String sql;
		try {
			if (wfcl_id < 0 || wfcl_id == null) {
				message[0] = "3";
				message[1] = "请选择所属任务类型。";
			} else {

				if (wfde_code != "" || wfde_code != null) {
					sql = "select * from WfDefination where wfde_code=? and wfde_state=1";
					list = dal.getDefinationModelsBySQL2(sql, wfde_code);

					if (!list.isEmpty()) {
						message[0] = "4";
						message[1] = "任务标识码已存在，无法重复新增。";
					}
				} else {
					message[0] = "4";
					message[1] = "请输入任务标识码。";
				}

				if (wfde_name != "" || wfde_name != null) {
					sql = "select * from WfDefination where wfde_name=? and wfde_wfcl_id=? and wfde_state=1";
					list = dal
							.getDefinationModelsBySQL(sql, wfde_name, wfcl_id);
					if (!list.isEmpty()) {
						message[0] = "5";
						message[1] = "任务名称已存在，无法重复新增。";
					}
				}

			}

		} catch (Exception e) {
			message[0] = "2";
			message[1] = "任务单，新增任务出错。";
			e.printStackTrace();
		}

		return message;
	}
	
	/**
	 * 校验修改数据唯一性
	 * 
	 * @param wfcl_id
	 * @param wfde_code
	 * @param wfde_name
	 * @return message
	 */
	public String[] judgeName2(Integer wfde_id,Integer wfcl_id, String wfde_code,
			String wfde_name) {
		String[] message = new String[2];
		WfDefinationDal dal = new WfDefinationDal();
		List<WfDefinationModel> list = new ListModelList<WfDefinationModel>();
		String sql;
		try {
			if (wfcl_id < 0 || wfcl_id == null) {
				message[0] = "3";
				message[1] = "请选择所属任务类型。";
			} else {

				if (wfde_code != "" || wfde_code != null) {
					sql = "select * from WfDefination where wfde_state=1 and wfde_code=? and wfde_id!=?";
					list = dal.getDefinationModelsBySQL2(sql, wfde_code,wfde_id);

					if (!list.isEmpty()) {
						message[0] = "4";
						message[1] = "任务标识码已存在，无法重复新增。";
					}
				} else {
					message[0] = "4";
					message[1] = "请输入任务标识码。";
				}

				if (wfde_name != "" || wfde_name != null) {
					sql = "select * from WfDefination where wfde_state=1 and wfde_name=? and wfde_wfcl_id=? and wfde_id!=?";
					list = dal
							.getDefinationModelsBySQL(sql, wfde_name, wfcl_id,wfde_id);
					if (!list.isEmpty()) {
						message[0] = "5";
						message[1] = "任务名称已存在，无法重复新增。";
					}
				}

			}

		} catch (Exception e) {
			message[0] = "2";
			message[1] = "任务单，新增任务出错。";
			e.printStackTrace();
		}

		return message;
	}

}
