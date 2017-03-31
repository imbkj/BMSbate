package dal.CoLatencyClient;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import Conn.dbconn;
import Model.CoServiceRequestModel;
import Model.EmBcItemGroupModel;
import Model.EmBodyCheckItemModel;
import Util.UserInfo;

public class CoServiceRequestOperateDal {
//	// 添加服务要求信息
//	public Integer CoServiceRequest_Add(CoServiceRequestModel model) {
//		try {
//			dbconn conn = new dbconn();
//			CallableStatement c = conn
//			.getcall("CoServiceRequest_Add_P_cyj(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
//			c.setInt(1, model.getCid());
//			c.setInt(2, model.getCsqe_cola_id());//潜在客户信息表id
//			c.setInt(3, model.getCoco_id());//合同id
//			c.setInt(4, model.getCsqe_isenddate());//发票寄送日
//			c.setInt(5, model.getCsqe_sbtype());//社会保险缴纳情况 1：非深户一档医保；2：非深户二档医保；3：委托外地缴纳
//			c.setString(6, model.getCsqe_sbtype_remark());//社保类型备注
//			c.setInt(7, model.getCsqe_cardpay());//各种证件的办理和费用收取 1：个人付；2：随付款；3：公司付
//			c.setString(8, model.getCsqe_cardpay_remark());//各种证件的办理和费用收取 备注
//			c.setInt(9, model.getCsqe_dtdservice());//
//			c.setString(10, model.getCsqe_dtdservice_remark());//
//			c.setInt(11, model.getCsqe_wt());//
//			c.setString(12, model.getCsqe_wt_remark());//
//			c.setInt(13, model.getCsqe_fservice());//
//			c.setString(14, model.getCsqe_fservice_remark());//
//			c.setString(15, model.getCsqe_other());//
//			c.setString(16, model.getCsqe_ispa());//
//			c.setString(17, model.getCsqe_ws());//工作中曾需解决过的事情
//			c.setString(18,model.getCsqe_todo());//待办事宜
//			c.setInt(19,model.getCsqe_gz_paydate());//工资个税付款日
//			c.setInt(20,model.getCsqe_houseover());//公积金是否有超标员工 1：有；0：无；2：不确定；
//			c.setString(21, model.getCsqe_houseover_remark());
//			c.setInt(22, model.getCsqe_actype());//考勤计算约定 1：需要计算考勤；2：无需计算考勤；3：审核客户计算；
//			c.setString(23,model.getCsqe_actype_remark());
//			c.setInt(24,model.getCsqe_report());//报表汇总约定 1：需要按部门汇总；2：不需要；3：不确定；4：需要特殊报表；
//			c.setString(25, model.getCsqe_report_remark());
//			c.setInt(26, model.getCsqe_taxctype());//个税计算约定 1：我司计算明细扣缴；2：客户提供明细扣缴；3：仅发放税后金额；
//			c.setString(27, model.getCsqe_taxctype_remark());
//			c.setInt(28, model.getCsqe_taxdetype());//个税申报约定 1：委托我司大户申报；2：委托我司客户独立户申报；3：不委托；
//			c.setString(29, model.getCsqe_taxdetype_remark());
//			c.setInt(30, model.getCsqe_taxpay());//个税转账约定 1：客户独立户扣个税无需我司代转；2：客户独立户扣个税需要我司代转；3：中智大户扣缴；4：无个税服务；
//			c.setString(31, model.getCsqe_taxpay_remark());
//			c.setInt(32, model.getCsqe_wt());//委托外地申报约定 1：有；2：不确定；0：无；
//			c.setString(33, model.getCsqe_taxwt_place());//委托外地申报约定 委托地
//			c.setInt(34,model.getCsqe_taxde_date());//个税申报时间 1：工资发放当月申报；2：工资发放次月申报；
//			c.setInt(35,model.getCsqe_gzpayroll_type());//工资单形式 1：无需工资单；2：E-mail工资单；3：密封工资单；4：网上中智工资单；
//			c.setString(36,model.getCsqe_gzpayroll_people());
//			c.setInt(37,model.getCsqe_gzpayroll_b());//密封工资单是否需要底单 1：是；0：否；
//			c.setString(38, UserInfo.getUsername());
//			c.setString(39,model.getCsqe_sbhousetype());//社保公积金扣缴约定
//			c.setString(40, model.getCsqe_sbhouse_remark());
//			c.setString(41,model.getCsqe_sbhouse_trans());//社保公积金转账约定
//			c.setString(42, model.getCsqe_sbhouse_trans_renark());
//			c.registerOutParameter(43, java.sql.Types.INTEGER);//
//			c.execute();
//			return c.getInt(18);
//		} catch (SQLException e) {
//			return -1;
//		}
//	}
//	
	// 服务要求信息新增
	public Integer CoServiceRequest_Add(CoServiceRequestModel model) {
		Integer i = 0;
		dbconn db = new dbconn();
		try {
			i = Integer.valueOf(db.callWithReturn(
				"{?=call CoServiceRequest_Add_P_cyj(?,?,?,?,?,?," +
				"?,?,?,?,?,?,?,?,?,?" +
				",?,?,?,?,?,?,?,?,?,?" +
				",?,?,?,?,?,?,?,?,?,?" +
				",?,?,?,?,?,?,?,?,?,?" +
				",?,?,?,?,?,?,?,?,?,?,?,?)}",
				Types.INTEGER,model.getCid(),
				 model.getCsqe_cola_id(),//潜在客户信息表id
				 model.getCoco_id(),//合同id
				 model.getCsqe_isenddate(),//发票寄送日
				 model.getCsqe_sbtype(),//社会保险缴纳情况 1：非深户一档医保；2：非深户二档医保；3：委托外地缴纳
				 model.getCsqe_sbtype_remark(),//社保类型备注
				model.getCsqe_cardpay(),//各种证件的办理和费用收取 1：个人付；2：随付款；3：公司付
				 model.getCsqe_cardpay_remark(),//各种证件的办理和费用收取 备注
				 model.getCsqe_dtdservice(),//
				 model.getCsqe_dtdservice_remark(),//
				 model.getCsqe_wt(),//
				model.getCsqe_wt_remark(),//
				model.getCsqe_fservice(),//
				model.getCsqe_fservice_remark(),//
				model.getCsqe_other(),//
				model.getCsqe_ispa(),//
				 model.getCsqe_ws(),//工作中曾需解决过的事情
				model.getCsqe_todo(),//待办事宜
				model.getCsqe_gz_paydate(),//工资个税付款日
				model.getCsqe_houseover(),//公积金是否有超标员工 1：有；0：无；2：不确定；
				 model.getCsqe_houseover_remark(),
				model.getCsqe_actype(),//考勤计算约定 1：需要计算考勤；2：无需计算考勤；3：审核客户计算；
				model.getCsqe_actype_remark(),
				model.getCsqe_report(),//报表汇总约定 1：需要按部门汇总；2：不需要；3：不确定；4：需要特殊报表；
				 model.getCsqe_report_remark(),
				 model.getCsqe_taxctype(),//个税计算约定 1：我司计算明细扣缴；2：客户提供明细扣缴；3：仅发放税后金额；
				 model.getCsqe_taxctype_remark(),
				 model.getCsqe_taxdetype(),//个税申报约定 1：委托我司大户申报；2：委托我司客户独立户申报；3：不委托；
				 model.getCsqe_taxdetype_remark(),
				 model.getCsqe_taxpay(),//个税转账约定 1：客户独立户扣个税无需我司代转；2：客户独立户扣个税需要我司代转；3：中智大户扣缴；4：无个税服务；
				 model.getCsqe_taxpay_remark(),
				 model.getCsqe_taxwt(),//委托外地申报约定 1：有；2：不确定；0：无；
				 model.getCsqe_taxwt_place(),//委托外地申报约定 委托地
				model.getCsqe_taxde_date(),//个税申报时间 1：工资发放当月申报；2：工资发放次月申报；
				model.getCsqe_gzpayroll_type(),//工资单形式 1：无需工资单；2：E-mail工资单；3：密封工资单；4：网上中智工资单；
				model.getCsqe_gzpayroll_people(),
				model.getCsqe_gzpayroll_b(),//密封工资单是否需要底单 1：是；0：否；
				 UserInfo.getUsername(),
				model.getCsqe_sbhousetype(),//社保公积金扣缴约定
				model.getCsqe_sbhouse_remark(),
				model.getCsqe_sbhouse_trans(),//社保公积金转账约定
				model.getCsqe_sbhouse_trans_renark(),
				model.getCsqe_house(),
				model.getCsqe_house_remark(),
				model.getCsqe_regist(),
				model.getCsqe_regist_remark(),
				model.getCoba_emfi_backdate(),
				model.getCoba_emfics_backdate(),
				model.getCoba_emfi_senddate(),
				model.getCoba_papergz_paydate(),
				model.getCsqe_request(),
				model.getCsqe_salary(),
				model.getCoba_xctzzzsj(model.getCoba_xctzzzsjstr()),
				model.getCoba_fktzffsj(model.getCoba_fktzffsjstr()),
				model.getCoba_xchksj(model.getCoba_xchksjstr()),
				model.getCsqe_firstmonthservernumber(),model.getCsqe_forecastservernumber(),
				model.getCsqe_salaryconfirmenddate()).toString());
	} catch (NumberFormatException | SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return i;
}
	
	// 服务要求信息修改
		public Integer CoServiceRequest_update(CoServiceRequestModel model) {
			Integer i = 0;
			dbconn db = new dbconn();
			try {
				i = Integer.valueOf(db.callWithReturn(
					"{?=call CoServiceRequest_update_P_cyj(?,?,?,?,?,?,?,?,?,?," +
					"?,?,?,?,?,?,?,?,?,?," +
					"?,?,?,?,?,?,?,?,?,?," +
					"?,?,?,?,?,?,?,?,?,?" +
					",?,?,?,?,?,?,?,?,?,?" +
					",?,?,?,?,?,?,?,?,?,?)}",
					Types.INTEGER,model.getCid(),
					 model.getCsqe_cola_id(),//潜在客户信息表id
					 model.getCoco_id(),//合同id
					 model.getCsqe_isenddate(),//发票寄送日
					 model.getCsqe_sbtype(),//社会保险缴纳情况 1：非深户一档医保；2：非深户二档医保；3：委托外地缴纳
					 model.getCsqe_sbtype_remark(),//社保类型备注
					model.getCsqe_cardpay(),//各种证件的办理和费用收取 1：个人付；2：随付款；3：公司付
					 model.getCsqe_cardpay_remark(),//各种证件的办理和费用收取 备注
					 model.getCsqe_dtdservice(),//
					 model.getCsqe_dtdservice_remark(),//
					 model.getCsqe_wt(),//
					model.getCsqe_wt_remark(),//
					model.getCsqe_fservice(),//
					model.getCsqe_fservice_remark(),//
					model.getCsqe_other(),//
					model.getCsqe_ispa(),//
					 model.getCsqe_ws(),//工作中曾需解决过的事情
					model.getCsqe_todo(),//待办事宜
					model.getCsqe_gz_paydate(),//工资个税付款日
					model.getCsqe_houseover(),//公积金是否有超标员工 1：有；0：无；2：不确定；
					 model.getCsqe_houseover_remark(),
					model.getCsqe_actype(),//考勤计算约定 1：需要计算考勤；2：无需计算考勤；3：审核客户计算；
					model.getCsqe_actype_remark(),
					model.getCsqe_report(),//报表汇总约定 1：需要按部门汇总；2：不需要；3：不确定；4：需要特殊报表；
					 model.getCsqe_report_remark(),
					 model.getCsqe_taxctype(),//个税计算约定 1：我司计算明细扣缴；2：客户提供明细扣缴；3：仅发放税后金额；
					 model.getCsqe_taxctype_remark(),
					 model.getCsqe_taxdetype(),//个税申报约定 1：委托我司大户申报；2：委托我司客户独立户申报；3：不委托；
					 model.getCsqe_taxdetype_remark(),
					 model.getCsqe_taxpay(),//个税转账约定 1：客户独立户扣个税无需我司代转；2：客户独立户扣个税需要我司代转；3：中智大户扣缴；4：无个税服务；
					 model.getCsqe_taxpay_remark(),
					 model.getCsqe_taxwt(),//委托外地申报约定 1：有；2：不确定；0：无；
					 model.getCsqe_taxwt_place(),//委托外地申报约定 委托地
					model.getCsqe_taxde_date(),//个税申报时间 1：工资发放当月申报；2：工资发放次月申报；
					model.getCsqe_gzpayroll_type(),//工资单形式 1：无需工资单；2：E-mail工资单；3：密封工资单；4：网上中智工资单；
					model.getCsqe_gzpayroll_people(),
					model.getCsqe_gzpayroll_b(),//密封工资单是否需要底单 1：是；0：否；
					 UserInfo.getUsername(),
					model.getCsqe_sbhousetype(),//社保公积金扣缴约定
					model.getCsqe_sbhouse_remark(),
					model.getCsqe_sbhouse_trans(),//社保公积金转账约定
					model.getCsqe_sbhouse_trans_renark(),
					model.getCsqe_house(),
					model.getCsqe_house_remark(),
					model.getCsqe_regist(),
					model.getCsqe_regist_remark(),
					model.getCoba_emfi_backdate(),
					model.getCoba_emfics_backdate(),
					model.getCoba_emfi_senddate(),
					model.getCoba_papergz_paydate(),
					model.getCsqe_auditname(),
					model.getCsqe_id(),model.getCsqe_salary(),model.getCsqe_request(),
					model.getCoba_xctzzzsj(model.getCoba_xctzzzsjstr()),
					model.getCoba_fktzffsj(model.getCoba_fktzffsjstr()),
					model.getCoba_xchksj(model.getCoba_xchksjstr()),
					model.getCsqe_firstmonthservernumber(),
					model.getCsqe_forecastservernumber(),
					model.getCsqe_salaryconfirmenddate()).toString());
					 
		} catch (NumberFormatException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return i;
	}
	
	//更新薪酬服务要求信息任务单id
	public boolean updateCoServiceRequestTaprid( int taprid,int id) {
		dbconn db = new dbconn();
		PreparedStatement psmt = null;
		int row = 0;
		String sql = "update CoServiceRequest set csqe_tarpId=? where csqe_id=?";
		try {
				psmt = db.getpre(sql);
				psmt.setInt(1, taprid);
				psmt.setInt(2, id);
				row = psmt.executeUpdate();
			} catch (Exception e) {
			// TODO: handle exception
		}
		return row > 0 ? true : false;
	}
	
	//更新公司薪酬负责人和薪酬审核人
	public boolean updateCobase(String coba_gzaddname,String coba_gzaudname,int cid) {
		dbconn db = new dbconn();
		PreparedStatement psmt = null;
		int row = 0;
		String sql = "update cobase set coba_gzaddname=?,coba_gzaudname=? where cid=?";
		try {
				psmt = db.getpre(sql);
				psmt.setString(1, coba_gzaddname);
				psmt.setString(2, coba_gzaudname);
				psmt.setInt(3, cid);
				row = psmt.executeUpdate();
			} catch (Exception e) {
			// TODO: handle exception
		}
		return row > 0 ? true : false;
	}
		
		//更新薪酬服务要求信息任务单id
		public Integer updateCoServiceRequestInfo( String sqls,int id) {
			dbconn db = new dbconn();
			int row = 0;
			String sql = "update CoServiceRequest set csqe_modname='"+UserInfo.getUsername()+"'"+sqls+" where csqe_id="+id;
			try {
				row = db.execQuery(sql);
				} catch (Exception e) {
					// TODO: handle exception
			}
			return row ;
		}
	
}
