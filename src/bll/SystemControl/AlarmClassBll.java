package bll.SystemControl;

import java.util.List;

import org.zkoss.zul.ListModelList;

import Model.AlarmClassModel;

import dal.SystemControl.AlarmClassDal;

public class AlarmClassBll {

	/**
	 * @Title: getAlarmClassList
	 * @Description: TODO(获取预警类型列表)
	 * @return
	 * @return List<AlarmClassModel> 返回类型
	 * @throws
	 */
	public List<AlarmClassModel> getAlarmClassNameList() {
		List<AlarmClassModel> list = new ListModelList<AlarmClassModel>();
		AlarmClassDal dal = new AlarmClassDal();
		try {
			list = dal.getAlarmClassNameList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	/** 
	* @Title: getAlarmClassName 
	* @Description: TODO(获取有效预警类型名称) 
	* @return
	* @return List<AlarmClassModel>    返回类型 
	* @throws 
	*/
	public List<AlarmClassModel> getAlarmClassName(){
		List<AlarmClassModel> list = new ListModelList<AlarmClassModel>();
		AlarmClassDal dal = new AlarmClassDal();
		String sql = "select alcl_id,alcl_name from AlarmClass where alcl_state=1";
		try {
			list = dal.getAlarmClass(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * @Title: getAlarmAddNameList
	 * @Description: TODO(获取预警类型添加人列表)
	 * @return
	 * @return List<AlarmClassModel> 返回类型
	 * @throws
	 */
	public List<AlarmClassModel> getAlarmAddNameList() {
		List<AlarmClassModel> list = new ListModelList<AlarmClassModel>();
		AlarmClassDal dal = new AlarmClassDal();
		try {
			list = dal.getAlarmClassAddNameList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * @Title: getAlarmClassById
	 * @Description: TODO(通过ID获取列表)
	 * @param id
	 * @return
	 * @return List<AlarmClassModel> 返回类型
	 * @throws
	 */
	public List<AlarmClassModel> getAlarmClassById(Integer id) {
		List<AlarmClassModel> list = new ListModelList<AlarmClassModel>();
		AlarmClassDal dal = new AlarmClassDal();
		String sql = "select *,case alcl_state when 1 then '生效' else '取消' end alcl_stateName from AlarmClass where alcl_id = ?";
		try {
			list = dal.getAlarmClassList(sql, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;

	}

	/**
	 * @Title: getAlarmClassList
	 * @Description: TODO(获取预警类型信息列表)
	 * @param name
	 * @param addname
	 * @param state
	 * @return
	 * @return List<AlarmClassModel> 返回类型
	 * @throws
	 */
	public List<AlarmClassModel> getAlarmClassList(String name, String addname,
			String state) {
		List<AlarmClassModel> list = new ListModelList<AlarmClassModel>();
		AlarmClassDal dal = new AlarmClassDal();
		String sql = "select *,case alcl_state when 1 then '生效' else '取消' end alcl_stateName from AlarmClass where alcl_name like ? and alcl_addname like ? and alcl_state like ?";
		try {
			list = dal.getAlarmClassList(sql, name, addname, state);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;

	}

	/**
	 * @Title: AddAlarmClass
	 * @Description: TODO(添加预警类型)
	 * @param name
	 * @param addname
	 * @return
	 * @return String[] 返回类型
	 * @throws
	 */
	public String[] AddAlarmClass(String name, String addname) {
		String[] message = new String[2];

		message = judgeName(name);
		if (message[0] == null) {
			int result = 0;
			AlarmClassModel am = new AlarmClassModel();
			AlarmClassDal dal = new AlarmClassDal();
			am.setAlcl_name(name);
			am.setAlcl_addname(addname);
			result = dal.AddAlarmClass(am);
			if (result > 0) {
				message[0] = "1";
				message[1] = "新增类型成功!";
			} else {
				message[0] = "0";
				message[1] = "新增类型失败!";
			}

		}
		return message;
	}

	/**
	 * @Title: ModAlarmClass
	 * @Description: TODO(修改预警信息)
	 * @param id
	 * @param name
	 * @param addname
	 * @return
	 * @return String[] 返回类型
	 * @throws
	 */
	public String[] ModAlarmClass(Integer id, String name, String addname,
			String stateName) {
		String[] message = new String[2];

		message = judgeName2(id, name);
		if (message[0] == null) {
			int result = 0;
			AlarmClassModel am = new AlarmClassModel();
			AlarmClassDal dal = new AlarmClassDal();
			am.setAlcl_id(id);
			am.setAlcl_name(name);
			am.setAlcl_addname(addname);
			am.setAlcl_state(Integer.valueOf(stateName));
			result = dal.ModAlarmClass(am);
			if (result > 0) {
				message[0] = "1";
				message[1] = "修改类型成功!";
			} else {
				message[0] = "0";
				message[1] = "修改类型失败!";
			}

		}
		return message;
	}

	/**
	 * @Title: judgeName
	 * @Description: TODO(判断预警信息唯一名称)
	 * @param name
	 * @return
	 * @return String[] 返回类型
	 * @throws
	 */
	public String[] judgeName(String name) {
		String[] message = new String[2];
		AlarmClassDal dal = new AlarmClassDal();
		List<AlarmClassModel> list = new ListModelList<AlarmClassModel>();
		try {
			if (!name.equals("")) {

				list = dal.getAlarmClassByName(name);

				if (!list.isEmpty()) {
					message[0] = "4";
					message[1] = "预警类型已存在，无法重复新增。";
				}
			} else {

				message[0] = "3";
				message[1] = "请输入预警类型名称。";
			}
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "系统预警，新增类型出错。";
			e.printStackTrace();
		}

		return message;
	}

	/**
	 * @Title: judgeName2
	 * @Description: TODO(修改信息时判断预警信息唯一性)
	 * @param id
	 * @param name
	 * @return
	 * @return String[] 返回类型
	 * @throws
	 */
	public String[] judgeName2(Integer id, String name) {
		String[] message = new String[2];
		AlarmClassDal dal = new AlarmClassDal();
		List<AlarmClassModel> list = new ListModelList<AlarmClassModel>();
		try {
			if (!name.equals("")) {
				String sql = "select * from AlarmClass where alcl_id!=? and alcl_name=?";
				list = dal.getAlarmClassList(sql, id, name);

				if (!list.isEmpty()) {
					message[0] = "4";
					message[1] = "预警类型已存在，无法重复新增。";
				}
			} else {

				message[0] = "3";
				message[1] = "请输入预警类型名称。";
			}
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "系统预警，修改类型出错。";
			e.printStackTrace();
		}

		return message;
	}

}
