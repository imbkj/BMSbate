package dal.EmBodyCheck;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.zkoss.zul.ListModelList;

import Conn.dbconn;
import Model.EmBcSetupAddressModel;
import Model.EmBcSetupModel;

public class EmBcSetup_SelectDal {
	// 查询体检机构信息
	public List<EmBcSetupModel> getEmBcSetupInfo(String str) {
		List<EmBcSetupModel> fidlist = new ArrayList<EmBcSetupModel>();
		dbconn db = new dbconn();
		String sql = "select ebcs_addtime,"
				+ "convert(decimal(18,1),ebcs_limit) as ebcs_limit,ebcs_inuredate,"
				+ "ebcs_indate,* from EmBcSetup where 1=1";
		sql = sql + str;
		sql = sql + " order by ebcs_state desc";
		try {
			fidlist = db.find(sql, EmBcSetupModel.class,
					dbconn.parseSmap(EmBcSetupModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fidlist;
	}

	// 根据机构id查询机构的地址
	public List<EmBcSetupAddressModel> getEmBcSetupAddressInfo(int id) {
		List<EmBcSetupAddressModel> fidlist = new ArrayList<EmBcSetupAddressModel>();
		dbconn db = new dbconn();
		String sql = "select case ebsa_state when 1 then '有效' else '取消' end stateName,"
				+ "* from EmBcSetupAddress"
				+ " where  ebsa_ebcs_id=?"
				+ " order by ebsa_state desc";
		try {
			fidlist = db.find(sql, EmBcSetupAddressModel.class, null, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fidlist;
	}

	// 查询机构的地址
	public List<EmBcSetupAddressModel> getEmBcSetupAddress(String str) {
		List<EmBcSetupAddressModel> fidlist = new ArrayList<EmBcSetupAddressModel>();
		dbconn db = new dbconn();
		String sql = "select convert(varchar(10),ebsa_addtime,120) as ebsa_addtime,"
				+ "convert(varchar(10),ebsa_modtime,120) as ebsa_modtime,"
				+ "convert(bit,isnull(ebsa_istate,0)) ebsa_ichecked,"
				+ "convert(bit,isnull(ebsa_ystate,0)) ebsa_ychecked,"
				+ "* from EmBcSetupAddress" + " where ebsa_state=1 " + str;
		try {
			fidlist = db.find(sql, EmBcSetupAddressModel.class,
					dbconn.parseSmap(EmBcSetupAddressModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fidlist;
	}

	// 查询机构不重复的名称
	public List<EmBcSetupModel> getEmBcSetupname(String str) {
		List<EmBcSetupModel> fidlist = new ArrayList<EmBcSetupModel>();
		dbconn db = new dbconn();
		String sql = "select distinct(ebcs_hospital) as ebcs_hospital,ebcs_id from EmBcSetup where 1=1";
		sql = sql + str;
		try {
			fidlist = db.find(sql, EmBcSetupModel.class,
					dbconn.parseSmap(EmBcSetupModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fidlist;
	}

	// 查询体检机构列表
	public List<EmBcSetupModel> getSetUpList(EmBcSetupModel ebsm) {
		List<EmBcSetupModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql = "select ebcs_id,ebcs_hospital,ebcs_name,ebcs_phone,ebcs_indate,ebcs_inuredate,ebcs_flow,ebcs_balance,ebcs_info,ebcs_remark,ebcs_formula,ebcs_limit,ebcs_tips,ebcs_state,ebcs_pstate,ebcs_addtime,ebcs_addname"
				+ " from EmBcSetup where 1=1";

		if (ebsm.getEbcs_name() != null) {
			if (!ebsm.getEbcs_name().equals("")) {
				sql = sql + " and ebcs_name = '" + ebsm.getEbcs_name() + "'";
			}
		}
		if (ebsm.getEbcs_hospital() != null) {
			if (!ebsm.getEbcs_hospital().equals("")) {
				sql = sql + " and ebcs_hospital = '" + ebsm.getEbcs_hospital()
						+ "'";
			}
		}

		if (ebsm.getEbcs_state() != null) {
			if (!ebsm.getEbcs_state().equals("")) {
				sql = sql + " and ebcs_state=" + ebsm.getEbcs_state();
			}
		}
		if (ebsm.getEbcs_id() != null) {
			if (!ebsm.getEbcs_id().equals("")) {
				if (ebsm.getCheckName() != null && ebsm.getCheckName()) {
					sql = sql + " and ebcs_id!=" + ebsm.getEbcs_id();
				} else {
					sql = sql + " and ebcs_id=" + ebsm.getEbcs_id();
				}

			}
		}

		sql = sql + " order by ebcs_hospital";
		System.out.println(sql);
		try {
			list = db.find(sql, EmBcSetupModel.class, null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public List<EmBcSetupAddressModel> getAddressList(EmBcSetupAddressModel ebam) {
		List<EmBcSetupAddressModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql = " select ebsa_id,ebsa_ebcs_id,ebsa_address,ebsa_istate,ebsa_ystate,ebsa_state,ebsa_addname,convert(varchar(19),ebsa_addtime,120)ebsa_addtime,"
				+ "ebsa_w1,ebsa_w2,ebsa_w3,ebsa_w4,ebsa_w5,ebsa_w6,ebsa_w7"
				+ " from EmBcSetupAddress where 1=1";

		if (ebam.getEbsa_id() != null) {
			if (!ebam.getEbsa_id().equals("")) {
				sql = sql + " and ebsa_id=" + ebam.getEbsa_id();
			}
		}

		if (ebam.getEbsa_ebcs_id() != null) {
			if (!ebam.getEbsa_ebcs_id().equals("")) {
				sql = sql + " and ebsa_ebcs_id=" + ebam.getEbsa_ebcs_id();
			}
		}
		if (ebam.getEbsa_state() != null) {
			if (!ebam.getEbsa_state().equals("")) {
				sql = sql + " and ebsa_state=" + ebam.getEbsa_state();
			}
		}

		if (ebam.getEmbc_confirm() != null && ebam.getEmbc_confirm().equals(1)) {
			if (ebam.getEbsa_ystate() != null
					&& ebam.getEbsa_ystate().equals(1)) {
				sql = sql + " and ebsa_ystate=" + ebam.getEbsa_ystate();
			} else if (ebam.getEbsa_istate() != null
					&& ebam.getEbsa_istate().equals(1)) {
				sql = sql + " and ebsa_istate=" + ebam.getEbsa_istate();
			}
		}
		try {
			list = db.find(sql, EmBcSetupAddressModel.class, null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
}
