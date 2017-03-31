package bll.EmCommercialInsurance;

import impl.WorkflowCore.WfOperateImpl;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import service.WorkflowCore.WfOperateService;

import Model.CI_Insurant_ListModel;
import dal.EmCommercialInsurance.CI_Insurant_ListDal;

public class CI_Insurant_ListBll {
	// 获取该员工分配的商业类型
	public List<CI_Insurant_ListModel> ci_insurant_castsort(String gid)
			throws SQLException {
		CI_Insurant_ListDal dal = new CI_Insurant_ListDal();
		List<CI_Insurant_ListModel> list = dal.getci_insurant_castsort(gid);
		return list;
	}

	// 获取该员工分配的商业类型
	public List<CI_Insurant_ListModel> ci_insurant_applycastsort(String gid)
			throws SQLException {
		CI_Insurant_ListDal dal = new CI_Insurant_ListDal();
		List<CI_Insurant_ListModel> list = dal
				.getci_insurant_applycastsort(gid);
		return list;
	}

	// 获取该员工分配的商业连带人类型
	public List<CI_Insurant_ListModel> ci_insurant_linkcastsort(String gid)
			throws SQLException {
		CI_Insurant_ListDal dal = new CI_Insurant_ListDal();
		List<CI_Insurant_ListModel> list = dal.getci_insurant_linkcastsort(gid);
		return list;
	}

	// 获取该员工分配的商业连带人类型
	public List<CI_Insurant_ListModel> ci_insurant_linkcastsortadd(String gid)
			throws SQLException {
		CI_Insurant_ListDal dal = new CI_Insurant_ListDal();
		List<CI_Insurant_ListModel> list = dal
				.getci_insurant_linkcastsortadd(gid);
		return list;
	}

	// 获取该员工基本信息
	public List<CI_Insurant_ListModel> embaselist(String gid)
			throws SQLException {
		CI_Insurant_ListDal dal = new CI_Insurant_ListDal();
		List<CI_Insurant_ListModel> list = dal.getembaselist(gid);
		return list;
	}

	// 获取未审核数据
	public List<CI_Insurant_ListModel> ci_insurant_list(String castsort)
			throws SQLException {
		String str1 = " and ecic_title='泰康-医疗类'";
		if (!castsort.equals("全部") && !castsort.equals("")) {
			str1 = " and d.ecic_title='" + castsort + "'";
		}
		CI_Insurant_ListDal dal = new CI_Insurant_ListDal();
		List<CI_Insurant_ListModel> list = dal.getci_insurant_list(str1);
		return list;
	}

	// 获取未处理任务单数据
	public List<CI_Insurant_ListModel> ci_insurant_rlist(String castsort,
			String client, String wyclient, String name, String idcard,
			String company, String state, String gid)
			throws SQLException {
		String str1 = "";
		if (!castsort.equals("全部") && !castsort.equals("")) {
			str1 = " and d.ecic_title='" + castsort + "'";
		}
		if (!state.equals("全部") && !state.equals("")) {
			str1 =str1+ " and ecia_state='" + state + "'";
		}
		if (!client.equals("全部") && !client.equals("")) {
			str1 =str1+ " and coba_client='" + client + "'";
		}
		if (!wyclient.equals("全部") && !wyclient.equals("")) {
			str1 = str1+" and coba_assistant='" + wyclient + "'";
		}
		if (!name.equals("")) {
			str1 = str1+" and emba_name like '%" + name + "%'";
		}
		if (!idcard.equals("")) {
			str1 = str1+" and emba_idcard='" + idcard + "'";
		}
		if (!company.equals("")) {
			str1 = str1+" and coba_company like '%" + company + "%'";
		}
		if (!gid.equals("")) {
			str1 = str1+" and a.gid='" + gid + "'";
		}
		if (!state.equals("全部") && !state.equals("")) {
			str1 = str1+" and ecia_state='" + state + "'";
		}else{
			str1 = str1+" and ecia_state=0";
		}
		CI_Insurant_ListDal dal = new CI_Insurant_ListDal();
		List<CI_Insurant_ListModel> list = dal.getci_insurant_rlist(str1);
		return list;
	}

	// 获取未审核数据
	public List<CI_Insurant_ListModel> ci_insurant_editlist(String gid)
			throws SQLException {

		String str1 = " and a.gid=" + gid;

		CI_Insurant_ListDal dal = new CI_Insurant_ListDal();
		List<CI_Insurant_ListModel> list = dal.getci_insurant_list(str1);
		return list;
	}

	// 获取信息变更未审核数据
	public List<CI_Insurant_ListModel> ci_insurant_bchlist()
			throws SQLException {
		CI_Insurant_ListDal dal = new CI_Insurant_ListDal();
		List<CI_Insurant_ListModel> list = dal.getci_insurant_bchlist();
		return list;
	}

	// 获取支付数据
	public List<CI_Insurant_ListModel> ci_insurant_paylist(String castsort)
			throws SQLException {
		String str1 = " and ecic_title='泰康-医疗类'";
		if (!castsort.equals("全部") && !castsort.equals("")) {
			str1 = " and d.ecic_title='" + castsort + "'";
		}
		CI_Insurant_ListDal dal = new CI_Insurant_ListDal();
		List<CI_Insurant_ListModel> list = dal.getci_insurant_paylist(str1);
		return list;
	}

	// 获取已审核数据
	public List<CI_Insurant_ListModel> ci_insurant_autlist(String castsort,
			String de_date) throws SQLException {
		String str1 = "";
		if (!castsort.equals("全部") && !castsort.equals("")) {
			str1 = " and d.ecic_title='" + castsort + "'";
		}
		if (!de_date.equals("全部") && !de_date.equals("")) {
			str1 = str1 + " and (a.ecic_de_date='" + de_date
					+ "' or a.ecic_st_dedate='" + de_date + "')";
		}
		CI_Insurant_ListDal dal = new CI_Insurant_ListDal();
		List<CI_Insurant_ListModel> list = dal.getci_insurant_autlist(str1);
		return list;
	}

	// 商业保险支付
	public int pay_insurant(int ecin_id, String paynum, String ownmonth)
			throws Exception {
		int message = 0;
		try {
			CI_Insurant_ListDal dal = new CI_Insurant_ListDal();
			message = dal.pay_ci(ecin_id, paynum, ownmonth);
		} catch (Exception e) {
			message = 1;
		}
		return message;
	}

	// 商业保险支付更新
	public int payaut_insurant(String paynum, String ownmonth) throws Exception {
		int message = 0;
		try {
			CI_Insurant_ListDal dal = new CI_Insurant_ListDal();
			message = dal.payaut_ci(paynum, ownmonth);
		} catch (Exception e) {
			message = 1;
		}
		return message;
	}

	// 获取保险类型
	public List<CI_Insurant_ListModel> castsortlist() throws SQLException {
		CI_Insurant_ListDal dal = new CI_Insurant_ListDal();
		List<CI_Insurant_ListModel> list = dal.getcastsortlist();
		return list;
	}

	// 获取客服
	public List<CI_Insurant_ListModel> clientlist() throws SQLException {
		CI_Insurant_ListDal dal = new CI_Insurant_ListDal();
		List<CI_Insurant_ListModel> list = dal.clientlist();
		return list;
	}

	// 获取中心文员
	public List<CI_Insurant_ListModel> rclientlist() throws SQLException {
		CI_Insurant_ListDal dal = new CI_Insurant_ListDal();
		List<CI_Insurant_ListModel> list = dal.rclientlist();
		return list;
	}

	// 获取保险审核时间
	public List<CI_Insurant_ListModel> castsortdatelist() throws SQLException {
		CI_Insurant_ListDal dal = new CI_Insurant_ListDal();
		List<CI_Insurant_ListModel> list = dal.getcastsortdatelist();
		return list;
	}

	// 获取保险在保类型
	public List<CI_Insurant_ListModel> ci_insurant_castsortout(String gid)
			throws SQLException {
		CI_Insurant_ListDal dal = new CI_Insurant_ListDal();
		List<CI_Insurant_ListModel> list = dal.getci_insurant_castsortout(gid);
		return list;
	}

	// 获取保险在保类型
	public List<CI_Insurant_ListModel> ci_link_list(String gid)
			throws SQLException {
		CI_Insurant_ListDal dal = new CI_Insurant_ListDal();
		List<CI_Insurant_ListModel> list = dal.getci_link_list(gid);
		return list;
	}

	// 获取保险信息
	public List<CI_Insurant_ListModel> ci_insurant_blist(int gid)
			throws SQLException {
		CI_Insurant_ListDal dal = new CI_Insurant_ListDal();
		List<CI_Insurant_ListModel> list = dal.getci_insurant_blist(gid);
		return list;
	}

	// 获取保险信息
	public List<CI_Insurant_ListModel> ci_insurant_lblist(int gid)
			throws SQLException {
		CI_Insurant_ListDal dal = new CI_Insurant_ListDal();
		List<CI_Insurant_ListModel> list = dal.getci_insurant_lblist(gid);
		return list;
	}

	// 获取excel保险在保数据
	public List<CI_Insurant_ListModel> ci_excel(String id) throws SQLException {
		CI_Insurant_ListDal dal = new CI_Insurant_ListDal();
		List<CI_Insurant_ListModel> list = dal.getci_excel(id);
		return list;
	}

	// 删除商保数据
	public List<CI_Insurant_ListModel> ecin_del(String id) throws SQLException {
		CI_Insurant_ListDal dal = new CI_Insurant_ListDal();
		List<CI_Insurant_ListModel> list = dal.ecin_del1(id);
		return list;
	}

	// 获取excel保险在保数据
	public List<CI_Insurant_ListModel> ci_zc_excel(String castsort,
			String change, String state, String bm, String bx, String sb_date,
			String name, String client) throws SQLException {
		String str1 = "";
		if (!castsort.equals("全部") && !castsort.equals("")) {
			str1 = " and ecic_title='" + castsort + "'";
		}
		String str2 = "";
		int state1 = 0;
		if (!change.equals("全部") && !change.equals("")) {
			if (change.equals("新增")) {
				state1 = 1;
			} else {
				state1 = 2;
			}

			str2 = " and a.ecin_state='" + state1 + "'";

		}
		String str3 = "";
		int state2 = 0;
		if (!state.equals("全部") && !state.equals("")) {
			if (change.equals("未审核")) {
				state2 = 0;
			}
			if (change.equals("已审核")) {
				state2 = 1;
			}
			if (change.equals("已申报")) {
				state2 = 2;
			}

			str3 = " and a.ecin_state2='" + state2 + "'";
		}
		String str4 = "";
		if (!bm.equals("全部") && !bm.equals("")) {
			str4 = " and c.dept='" + bm + "'";
		}
		String str5 = "";
		if (!bx.equals("全部") && !bx.equals("")) {
			str5 = " and ecic_bx='" + bx + "'";
		}
		String str6 = "";
		if (!sb_date.equals("全部") && !sb_date.equals("")) {
			str6 = " and a.ecin_de_date<='" + sb_date
					+ "' and (ecin_st_date>='" + sb_date
					+ "' or ecin_st_date is null)";
		}
		String str7 = "";
		if (!name.equals("全部") && !name.equals("")) {
			str7 = " and (a.ecin_insurant='" + name + "' or a.ecin_insurer='"
					+ name + "')";
		}
		String str8 = "";
		if (!client.equals("全部") && !client.equals("")) {
			str7 = " and coba_client='" + client + "'";
		}
		CI_Insurant_ListDal dal = new CI_Insurant_ListDal();
		List<CI_Insurant_ListModel> list = dal.getci_zc_excel(str1, str2, str3,
				str4, str5, str6, str7, str8);
		return list;
	}

	// 获取excel保险在保数据
	public List<CI_Insurant_ListModel> bci_excel(String id) throws SQLException {
		CI_Insurant_ListDal dal = new CI_Insurant_ListDal();
		List<CI_Insurant_ListModel> list = dal.getbci_excel(id);
		return list;
	}

	// 获取查询信息
	public List<CI_Insurant_ListModel> ci_insurant_chlist(String castsort,
			String change, String state, String bm, String bx, String sb_date,
			String name, String client) throws SQLException {
		String str1 = "";
		if (!castsort.equals("全部") && !castsort.equals("")) {
			str1 = " and ecic_title='" + castsort + "'";
		}
		String str2 = "";
		int state1 = 0;
		if (!change.equals("全部") && !change.equals("")) {
			if (change.equals("新增")) {
				state1 = 1;
			} else {
				state1 = 2;
			}

			str2 = " and a.ecin_state='" + state1 + "'";

		}
		String str3 = "";
		int state2 = 0;
		if (!state.equals("全部") && !state.equals("")) {
			if (change.equals("未审核")) {
				state2 = 0;
			}
			if (change.equals("已审核")) {
				state2 = 1;
			}
			if (change.equals("已申报")) {
				state2 = 2;
			}

			str3 = " and a.ecin_state2='" + state2 + "'";
		}
		String str4 = "";
		if (!bm.equals("全部") && !bm.equals("")) {
			str4 = " and e.dept='" + bm + "'";
		}
		String str5 = "";
		if (!bx.equals("全部") && !bx.equals("")) {
			str5 = " and d.ecic_bx='" + bx + "'";
		}
		String str6 = "";
		if (!sb_date.equals("全部") && !sb_date.equals("")) {
			str6 = " and (a.ecin_de_date<='" + sb_date
					+ "' or a.ecin_st_dedate<='" + sb_date + "')";
		}
		String str7 = "";
		if (!name.equals("全部") && !name.equals("")) {
			str7 = " and (a.ecin_insurant='" + name + "' or a.ecin_insurer='"
					+ name + "')";
		}
		String str8 = "";
		if (!client.equals("全部") && !client.equals("")) {
			str7 = " and coba_client='" + client + "'";
		}
		CI_Insurant_ListDal dal = new CI_Insurant_ListDal();
		List<CI_Insurant_ListModel> list = dal.getci_insurant_chlist(str1,
				str2, str3, str4, str5, str6, str7, str8);
		return list;
	}

	// 查询该员工是否已有社保信息
	public boolean check_ciin(int gid) {
		CI_Insurant_ListDal dal = new CI_Insurant_ListDal();
		return dal.check_ciin(gid);
	}
}
