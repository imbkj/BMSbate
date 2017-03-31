package bll.EmCommercialInsurance;

import java.sql.SQLException;
import java.util.List;

import Model.CI_InsurantClaimModel;
import Model.CI_InsurantClaimVistModel;
import Util.UserInfo;
import dal.EmCommercialInsurance.CI_InsurantClaim_ListDal;
import dal.EmCommercialInsurance.CI_InsurantClaim_OperateDal;

public class CI_InsurantClaim_ListBll {
	String username = UserInfo.getUsername();

	// 获取商保基本信息
	public CI_InsurantClaimModel ci_base(String gid) throws SQLException {
		CI_InsurantClaim_ListDal dal = new CI_InsurantClaim_ListDal();
		//List<CI_InsurantClaimModel> list = dal.getci_base();
		return dal.getci_base(gid);
	}
	
	// 获取商保理赔统计
		public CI_InsurantClaimModel ci_state() throws SQLException {
			CI_InsurantClaim_ListDal dal = new CI_InsurantClaim_ListDal();
			//List<CI_InsurantClaimModel> list = dal.getci_base();
			return dal.getci_state();
		}

	// 获取商保在保类型
	public List<CI_InsurantClaimModel> ci_castsort_inbase() throws SQLException {
		CI_InsurantClaim_ListDal dal = new CI_InsurantClaim_ListDal();
		List<CI_InsurantClaimModel> list = dal.getci_castsort_inbase();
		return list;
	}

	// 获取商保历史类型
	public List<CI_InsurantClaimModel> ci_name(String gid, String castsort)
			throws SQLException {
		CI_InsurantClaim_ListDal dal = new CI_InsurantClaim_ListDal();
		List<CI_InsurantClaimModel> list = dal.getci_name(gid, castsort);
		return list;
	}

	// 获取商保索赔类型
	public List<CI_InsurantClaimModel> ci_class_l(String eccc_id)
			throws SQLException {
		CI_InsurantClaim_ListDal dal = new CI_InsurantClaim_ListDal();
		List<CI_InsurantClaimModel> list = dal.getci_class_l(eccc_id);
		return list;
	}

	// 获取商保在保类型
	public List<CI_InsurantClaimModel> ci_claim_Wtlist(String name) throws SQLException {
		CI_InsurantClaim_ListDal dal = new CI_InsurantClaim_ListDal();
		List<CI_InsurantClaimModel> list = dal.getci_claim_Wtlist(name);
		return list;
	}

	// 获取商保在保类型
	public List<CI_InsurantClaimModel> ci_claim_Wtoutlist(String name) throws SQLException {
		CI_InsurantClaim_ListDal dal = new CI_InsurantClaim_ListDal();
		List<CI_InsurantClaimModel> list = dal.getci_claim_Wtoutlist(name);
		return list;
	}
	
	// 获取商保在保类型
		public List<CI_InsurantClaimModel> ci_claim_Wtoutoverlist(String name) throws SQLException {
			CI_InsurantClaim_ListDal dal = new CI_InsurantClaim_ListDal();
			List<CI_InsurantClaimModel> list = dal.getci_claim_Wtoutoverlist(name);
			return list;
		}

	// 获取商保索赔处理中数据
	public List<CI_InsurantClaimModel> ci_claim_autlist(String name) throws SQLException {
		CI_InsurantClaim_ListDal dal = new CI_InsurantClaim_ListDal();
		List<CI_InsurantClaimModel> list = dal.getci_claim_autlist(name);
		return list;
	}

	// 获取商保索赔备注
	public List<CI_InsurantClaimModel> ci_claim_remarklist(String eccl_id)
			throws SQLException {
		CI_InsurantClaim_ListDal dal = new CI_InsurantClaim_ListDal();
		List<CI_InsurantClaimModel> list = dal.getci_claim_remarklist(eccl_id);
		return list;
	}

	// 获取商保索赔备注
	public List<CI_InsurantClaimModel> ci_castsort_class(String eccl_id)
			throws SQLException {
		CI_InsurantClaim_ListDal dal = new CI_InsurantClaim_ListDal();
		List<CI_InsurantClaimModel> list = dal.getci_castsort_class(eccl_id);
		return list;
	}

	// 获取商保索赔备注
	public List<CI_InsurantClaimModel> ci_claim_down(String eccl_id)
			throws SQLException {
		CI_InsurantClaim_ListDal dal = new CI_InsurantClaim_ListDal();
		List<CI_InsurantClaimModel> list = dal.getci_claim_down(eccl_id);
		return list;
	}

	// 获取商保索赔备注
	public List<CI_InsurantClaimVistModel> ci_claim_vistlist(String eccl_id)
			throws SQLException {
		CI_InsurantClaim_ListDal dal = new CI_InsurantClaim_ListDal();
		List<CI_InsurantClaimVistModel> list = dal
				.getci_claim_vistlist(eccl_id);
		return list;
	}

	// 获取商保索赔已理赔数据
	public List<CI_InsurantClaimModel> ci_claim_autoverlist(String name)
			throws SQLException {
		CI_InsurantClaim_ListDal dal = new CI_InsurantClaim_ListDal();
		List<CI_InsurantClaimModel> list = dal.getci_claim_autoverlist(name);
		return list;
	}

	// 获取商保索赔备注
	public String[] remark_add(String eccl_id, String email, String mobile,
			String content, String aa1, String aa2, String aa3) {
		int result = 0;
		String[] message = new String[5];
		CI_InsurantClaim_ListDal dal = new CI_InsurantClaim_ListDal();
		result = dal.getremark_add(eccl_id, email, mobile, content, aa1, aa2,
				aa3, username);
		if (result != 0) {
			message[0] = "1";
			message[1] = "备注成功!";
			message[2] = String.valueOf(result);
			message[3] = "claim_remark";
			message[4] = "备注";
		} else {
			message[0] = "0";
			message[1] = "备注失败!";
		}
		return message;
	}

	// 获取商保索赔就诊
	public String[] vist_add(String eccl_id, String cl_castsort,
			String jz_date, String jz_host, String jz_content,
			String sk_remark, String cl_class_la, String cl_class) {
		int result = 0;
		String[] message = new String[5];
		CI_InsurantClaim_ListDal dal = new CI_InsurantClaim_ListDal();
		result = dal.getvist_add(eccl_id, cl_castsort, jz_date, jz_host,
				jz_content, sk_remark, cl_class, cl_class_la, username);
		if (result != 0) {
			message[0] = "1";
			message[1] = "添加成功!";
			message[2] = String.valueOf(result);
			message[3] = "claim_remark";
			message[4] = "就诊";
		} else {
			message[0] = "0";
			message[1] = "添加失败!";
		}
		return message;
	}

	// 获取商保索赔就诊
	public String[] pid_add(String eccl_id, String eccv_id, String em1,
			String em2, String em3, String em4, String em5, String em6,
			String em7, String em8, String em9, String em10, String em11,
			String em12, String em13, String em14, String em15, String em16,
			String em17) {
		int result = 0;
		String[] message = new String[5];
		CI_InsurantClaim_ListDal dal = new CI_InsurantClaim_ListDal();
		result = dal.getpid_add(eccl_id, eccv_id, em1, em2, em3, em4, em5, em6,
				em7, em8, em9, em10, em11, em12, em13, em14, em15, em16, em17,
				username);
		if (result != 0) {
			message[0] = "1";
			message[1] = "添加成功!";
			message[2] = String.valueOf(result);
			message[3] = "claim_remark";
			message[4] = "就诊";
		} else {
			message[0] = "0";
			message[1] = "添加失败!";
		}
		return message;
	}

	// 获取商保索赔就诊
	public String[] ci_overadd(String eccv_id, String em1, String em2,
			String em3, String em4, String em5, String em6, String em7,
			String em8, String em9, String em10, String em11, String em12,
			String em13, String em14, String em15, String em16, String em17,
			String em18, String eccl_id) {
		int result = 0;
		String[] message = new String[5];
		CI_InsurantClaim_ListDal dal = new CI_InsurantClaim_ListDal();
		result = dal.getci_overadd(eccv_id, em1, em2, em3, em4, em5, em6, em7,
				em8, em9, em10, em11, em12, em13, em14, em15, em16, em17, em18,
				eccl_id);
		if (result != 0) {
			message[0] = "1";
			message[1] = "添加成功!";
			message[2] = String.valueOf(result);
			message[3] = "claim_remark";
			message[4] = "就诊";
		} else {
			message[0] = "0";
			message[1] = "添加失败!";
		}
		return message;
	}

	// 获取商保赔付时间
	public List<CI_InsurantClaimModel> pf_list() throws SQLException {
		CI_InsurantClaim_ListDal dal = new CI_InsurantClaim_ListDal();
		List<CI_InsurantClaimModel> list = dal.getpf_list();
		return list;
	}

	// 获取商保索赔查询
	public List<CI_InsurantClaimModel> ci_claim_chlist(String pf_date,
			String state, String name, String pd_id) throws SQLException {
		CI_InsurantClaim_ListDal dal = new CI_InsurantClaim_ListDal();
		String str1 = "";
		String zstate="1";
		if (!pf_date.equals("全部") && !pf_date.equals("")) {
			str1 = " and eccl_sign_date='" + pf_date + "'";
		}
		String str2 = "";
		if (!state.equals("全部") && !state.equals("")) {
			if(state.equals("待处理")){
				zstate="1";
			}
			if(state.equals("处理中")){
				zstate="2";
			}
			if(state.equals("已理赔")){
				zstate="3";
			}
			str2 = " and eccl_state='" + zstate + "'";
		}
		String str3 = "";
		if (!name.equals("全部") && !name.equals("")) {
			str3 = " and (eccl_insurant='" + name + "' or eccl_insurer='"
					+ name + "')";
		}
		String str4 = "";
		if (!pd_id.equals("全部") && !pd_id.equals("")) {
			str4 = " and eccl_state='" + pd_id + "'";
		}
		List<CI_InsurantClaimModel> list = dal.getci_claim_chlist(str1, str2,
				str3, str4);
		return list;
	}

	// 获取商保索赔查询
	public List<CI_InsurantClaimModel> ci_insurant_claddlist(String name,String idcard) throws SQLException {
		CI_InsurantClaim_ListDal dal = new CI_InsurantClaim_ListDal();
		String str1 = "";
		String str2 = "";
		if (!name.equals("全部") && !name.equals("")) {
			str1 = " and (ecin_insurant like '%" + name + "%' or ecin_insurer like '%" + name + "%')";
		}
		if (!idcard.equals("全部") && !idcard.equals("")) {
			str2 = " and ecin_idcard like '%" + idcard + "%'";
		}
		if (idcard.equals("") && name.equals("")) {
			str2 = " and ecin_state=10";
		}
		List<CI_InsurantClaimModel> list = dal.getci_insurant_claddlist(str1,str2);
		return list;
	}
}
