package dal.CIICNET;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import org.zkoss.zul.ListModelList;

import Model.EmbaseModel;
import Util.IdcardUtil;
import Util.UserInfo;
import Util.MD532;

import Conn.dbconn;

public class createuserdal {

	/**
	 * @param args
	 */
 

	/**
	 * 查询是否已经存在账号
	 * 0 不存在
	 * 1 存在
	 */
	 
	public int checkuser(String idcard)
	{
	 
		
		String sql ="select COUNT(*) con from wz.netciic_erp.dbo.enrollment  where et_agree<3 and et_idcard='"+ idcard +"'";
		int i=0;
		dbconn db = new dbconn();
		
			try{
			
			ResultSet rs=db.GRS(sql);
			while(rs.next())
			{
				i=rs.getInt("con");
			}
		}catch(Exception e)
		{
			
		}
			return i > 0 ? 0 : 1;
	}
	
	
	/**
	 *新增账号
	 * 0失败
	 * >1 成功
	 * @throws Exception 
	 */
	 
	public int createuser(EmbaseModel m) throws Exception
	{
	 
		
		String pwd=MD532.Bit32("pwdreload8888");
				
		
		dbconn db = new dbconn();
		Integer row = 0;
		
		try {
			row = Integer.parseInt(db.callWithReturn(
					"{?=call P_Wz_enrollmentUser_ADD" +
					"(?,?,?,?,?,?,?," +
					"?,?,?,?,?,?,?,?,?,?,?,?)}",
					Types.INTEGER,
					m.getEmba_name(),
					m.getGid(),
					m.getEmba_wt(),
					MD532.Bit32(m.getEmba_idcard())
					,
					m.getEmba_name(),
					m.getEmba_idcard(),
					UserInfo.getUsername(),
					m.getCoba_company(),
					m.getCid(),
					m.getEmba_mobile(),
					m.getCoba_client(),
					m.getEmba_pinyin(),
					m.getEmba_spell(),
					0,
					0,
					m.getEmba_birth(),
					m.getEmba_sex(),
					m.getEmba_computerid(),
					0).toString()							
					);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return row;
		
	}
	
	/**
	 * 获取网上中智员工model
	 *  
	 */
	
	// 根据ID获取员工信息
	public List<EmbaseModel> getEmBaseById(int gid) {
		List<EmbaseModel> list = new ListModelList<EmbaseModel>();
		dbconn db = new dbconn();
		String sql = "SELECT  gid, et_name  emba_name,et_sex   emba_sex ,et_idcard   emba_idcard" +
				",et_brithdate emba_birth,et_nation emba_Nationality ,et_native  emba_native" +
				"  ,et_nativeplace emba_hjadd ,et_diploma emba_education ,et_school emba_school" +
				",et_speciality emba_specialty,et_politics  emba_party,et_email emba_email" +
				",et_mobile  emba_mobile,et_officepho  emba_phone ,et_ComputerID emba_computerid" +
				",et_marry emba_marital,et_archivespalce emba_fileplace,et_addresssz emba_address" +
				" ,et_appellation1 emba_f1 ,et_appellation2 emba_f2,et_appellation3 emba_f3" +
				",et_appellation4 emba_f4,et_appellation5 emba_f5,et_jtname1 emba_fn1,et_jtname2 emba_fn2" +
				" ,et_jtname3 emba_fn3,et_jtname4 emba_fn4,et_jtname5 emba_fn5,et_hj1 emba_fa1" +
				",et_hj2 emba_fa2,et_hj3 emba_fa3,et_hj4 emba_fa4,et_hj5 emba_fa5 ,et_jtpho1 emba_fp1" +
				",et_jtpho2 emba_fp2,et_jtpho3 emba_fp3,et_jtpho4 emba_fp4,et_jtpho5 emba_fp5" +
				",et_bank emba_gz_account ,et_bankname  emba_gz_bank ,et_addname emba_addname" +
				",et_comname coba_company ,et_client coba_client ,et_cid cid ,et_py emba_pinyin" +
				",et_spell emba_spell,et_exmobile  emba_epmobile ,et_wifeidcard emba_wifeidcard" +
				" ,et_wifename  emba_wifename ,et_perAcc emba_houseid,et_hand emba_hand ," +
				" et_metier emba_title ,et_experson emba_epname,et_exmobile emba_epmobile  " +
				"FROM wz.netciic_erp.dbo.enrollment  where " +
				"gid =?";
		System.out.println(sql);
		try {
			list = db.find(sql, EmbaseModel.class,
					dbconn.parseSmap(EmbaseModel.class), gid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
		
	}
	/**
	 * 导入员工信息
	 *  
	 */
	
	// 修改员工基本信息
	public Integer modInfo(EmbaseModel em) {
		Integer i = 0;
		dbconn db = new dbconn();
		String sql = "update embase set emba_modname='" + em.getEmba_modname()
				+ "',emba_modtime=getdate()";
		if (em.getEmba_name() != null) {
			if (em.getEmba_name() != "") {
				sql = sql + ",emba_name='" + em.getEmba_name() + "'";
			}
		}

		if (em.getEmba_sex() != null) {
			if (em.getEmba_sex() != "") {
				sql = sql + ",emba_sex='" + em.getEmba_sex() + "'";
			}
		}
		if (em.getEmba_folk() != null) {
			if (em.getEmba_folk() != "") {
				sql = sql + ",emba_folk='" + em.getEmba_folk() + "'";
			}
		}
		if (em.getEmba_idcard() != null) {
			if (em.getEmba_idcard() != "") {
				sql = sql + ",emba_idcard='" + em.getEmba_idcard() + "'";
			}
		}
		if (em.getEmba_idcardclass() != null) {
			if (em.getEmba_idcardclass() != "") {
				sql = sql + ",emba_idcardclass='" + em.getEmba_idcardclass()
						+ "'";
			}
		}
		if (em.getEmba_birth() != null) {
			if (em.getEmba_birth() != "") {
				sql = sql + ",emba_birth='" + em.getEmba_birth() + "'";
			}
		}
		if (em.getEmba_phone() != null) {
			if (em.getEmba_phone() != "") {
				sql = sql + ",emba_phone='" + em.getEmba_phone() + "'";
			}
		}
		if (em.getEmba_mobile() != null) {
			if (em.getEmba_mobile() != "") {
				sql = sql + ",emba_mobile='" + em.getEmba_mobile() + "'";
			}
		}
		if (em.getEmba_email() != null) {
			if (em.getEmba_email() != "") {
				sql = sql + ",emba_email='" + em.getEmba_email() + "'";
			}
		}

		if (em.getEmba_sb_radix() != null) {
			sql = sql + ",emba_sb_radix=" + em.getEmba_sb_radix();
		}

		if (em.getEmba_computerid() != null) {
			sql = sql + ",emba_computerid='" + em.getEmba_computerid() + "'";
		}

		if (em.getEmba_formula() != null) {
			if (em.getEmba_formula() != "") {
				sql = sql + ",emba_formula='" + em.getEmba_formula() + "'";
			}
		}
		if (em.getEmba_sb_hj() != null) {
			if (em.getEmba_sb_hj() != "") {
				sql = sql + ",emba_sb_hj='" + em.getEmba_sb_hj() + "'";
			}
		}

		if (em.getEmba_house_radix() != null) {
			sql = sql
					+ ",emba_house_radix="
					+ em.getEmba_house_radix().setScale(2,
							BigDecimal.ROUND_HALF_UP);
		}

		if (em.getEmba_houseid() != null) {
			sql = sql + ",emba_houseid='" + em.getEmba_houseid() + "'";
		}

		if (em.getEmba_house_cpp() != null) {
			if (em.getEmba_house_cpp() > 0) {
				sql = sql + ",emba_house_cpp=" + em.getEmba_house_cpp();
			}
		}

		if (em.getEmba_emsb_ownmonth() != null) {
			if (!em.getEmba_emsb_ownmonth().equals("")) {
				sql = sql + ",emba_emsb_ownmonth='"
						+ em.getEmba_emsb_ownmonth() + "'";
			}
		}

		if (em.getEmba_emsb_feeownmonth() != null) {
			if (!em.getEmba_emsb_feeownmonth().equals("")) {
				sql = sql + ",emba_emsb_feeownmonth='"
						+ em.getEmba_emsb_feeownmonth() + "'";
			}
		}

		if (em.getEmba_emsb_m1() != null) {
			if (!em.getEmba_emsb_m1().equals("")) {
				sql = sql + ",emba_emsb_m1='" + em.getEmba_emsb_m1() + "'";
			}
		}
		if (em.getEmba_emsb_r1() != null) {
			if (!em.getEmba_emsb_r1().equals("")) {
				sql = sql + ",emba_emsb_r1=" + em.getEmba_emsb_r1();
			}
		}
		if (em.getEmba_emsb_m2() != null) {
			if (!em.getEmba_emsb_m2().equals("")) {
				sql = sql + ",emba_emsb_m2='" + em.getEmba_emsb_m2() + "'";
			}
		}
		if (em.getEmba_emsb_r2() != null) {
			if (!em.getEmba_emsb_r2().equals("")) {
				sql = sql + ",emba_emsb_r2=" + em.getEmba_emsb_r2();
			}
		}
		if (em.getEmba_emsb_m3() != null) {
			if (!em.getEmba_emsb_m3().equals("")) {
				sql = sql + ",emba_emsb_m3='" + em.getEmba_emsb_m3() + "'";
			}
		}
		if (em.getEmba_emsb_r3() != null) {
			if (!em.getEmba_emsb_r3().equals("")) {
				sql = sql + ",emba_emsb_r3=" + em.getEmba_emsb_r3();
			}
		}
		if (em.getEmba_emhb_ownmonth() != null) {
			if (!em.getEmba_emhb_ownmonth().equals("")) {
				sql = sql + ",emba_emhb_ownmonth='"
						+ em.getEmba_emhb_ownmonth() + "'";
			}
		}
		if (em.getEmba_emhb_feeownmonth() != null) {
			if (!em.getEmba_emhb_feeownmonth().equals("")) {
				sql = sql + ",emba_emhb_feeownmonth='"
						+ em.getEmba_emhb_feeownmonth() + "'";
			}
		}

		if (em.getEmba_emhb_startdate() != null) {
			if (!em.getEmba_emhb_startdate().equals("")) {
				sql = sql + ",emba_emhb_startdate='"
						+ em.getEmba_emhb_startdate() + "'";
			}
		}
		if (em.getEmba_emhb_stopdate() != null) {
			if (!em.getEmba_emhb_stopdate().equals("")) {
				sql = sql + ",emba_emhb_stopdate='"
						+ em.getEmba_emhb_stopdate() + "'";
			}
		}
		if (em.getEmba_emhb_reason() != null) {
			if (!em.getEmba_emhb_reason().equals("")) {
				sql = sql + ",emba_emhb_reason='" + em.getEmba_emhb_reason()
						+ "'";
			}
		}
		if (em.getEmba_emhb_radix() != null) {
			sql = sql + ",emba_emhb_radix=" + em.getEmba_emhb_radix();

		}
		if (em.getEmba_emhb_total() != null) {
			if (!em.getEmba_emhb_total().equals("")) {
				sql = sql + ",emba_emhb_total=" + em.getEmba_emhb_total();
			}

		}

		if (em.getEmba_state() != null) {
			// if (em.getEmba_state() > 0) {
			sql = sql + ",emba_state=" + em.getEmba_state();
			// }
		}
		if (em.getEmba_sbuname() != null) {
			if (!em.getEmba_sbuname().equals("")) {
				sql = sql + ",emba_sbuname='" + em.getEmba_sbuname() + "'";
			}
		}
		if (em.getEmba_sbidcard() != null) {
			if (!em.getEmba_sbidcard().equals("")) {
				sql = sql + ",emba_sbidcard='" + em.getEmba_sbidcard() + "'";
			}
		}
		if (em.getEmba_gjjuname() != null) {
			if (!em.getEmba_gjjuname().equals("")) {
				sql = sql + ",emba_gjjuname='" + em.getEmba_gjjuname() + "'";
			}
		}
		if (em.getEmba_gjjidcard() != null) {
			if (!em.getEmba_gjjidcard().equals("")) {
				sql = sql + ",emba_gjjidcard='" + em.getEmba_gjjidcard() + "'";
			}
		}
		if (em.getEmba_remark() != null) {
			if (!em.getEmba_remark().equals("")) {
				sql = sql + ",emba_remark='" + em.getEmba_remark() + "'";
			}
		}
		if(em.getEmba_sbname1()!=null)
		{
			if (!em.getEmba_sbname1().equals("")) {
				sql = sql + ",Emba_sbname1='" + em.getEmba_sbname1() + "'";
			}
		}
		if(em.getEmba_sbname2()!=null)
		{
			if (!em.getEmba_sbname2().equals("")) {
				sql = sql + ",Emba_sbname2='" + em.getEmba_sbname2() + "'";
			}
		}
		if(em.getEmba_sbname3()!=null)
		{
			if (!em.getEmba_sbname3().equals("")) {
				sql = sql + ",Emba_sbname3='" + em.getEmba_sbname3() + "'";
			}
		}
		if(em.getEmba_sbname4()!=null)
		{
			if (!em.getEmba_sbname4().equals("")) {
				sql = sql + ",Emba_sbname4='" + em.getEmba_sbname4() + "'";
			}
		}
		
		if(em.getEmba_sbage1()!=null)
		{
			if (!em.getEmba_sbage1().equals("")) {
				sql = sql + ",Emba_sbage1='" + em.getEmba_sbage1() + "'";
			}
		}
		
		if(em.getEmba_sbage2()!=null)
		{
			if (!em.getEmba_sbage2().equals("")) {
				sql = sql + ",Emba_sbage2='" + em.getEmba_sbage2() + "'";
			}
		}
		
		if(em.getEmba_sbage3()!=null)
		{
			if (!em.getEmba_sbage3().equals("")) {
				sql = sql + ",Emba_sbage3='" + em.getEmba_sbage3() + "'";
			}
		}
		
		if(em.getEmba_sbage4()!=null)
		{
			if (!em.getEmba_sbage4().equals("")) {
				sql = sql + ",Emba_sbage4='" + em.getEmba_sbage4() + "'";
			}
		}
		
		if(em.getEmba_sbidcard1()!=null)
		{
			if (!em.getEmba_sbidcard1().equals("")) {
				sql = sql + ",Emba_sbidcard1='" + em.getEmba_sbidcard1() + "'";
				 
				sql = sql + ",Emba_sbbirth1='" +  IdcardUtil.getBirthByIdCard(em.getEmba_sbidcard1())+ "'";
			}
		}
 
		
		if(em.getEmba_sbidcard2()!=null)
		{
			if (!em.getEmba_sbidcard2().equals("")) {
				sql = sql + ",Emba_sbidcard2='" + em.getEmba_sbidcard2() + "'";
				sql = sql + ",Emba_sbbirth2='" +  IdcardUtil.getBirthByIdCard(em.getEmba_sbidcard2())+ "'";
			}
		}
		
		if(em.getEmba_sbidcard3()!=null)
		{
			if (!em.getEmba_sbidcard3().equals("")) {
				sql = sql + ",Emba_sbidcard3='" + em.getEmba_sbidcard3() + "'";
				sql = sql + ",Emba_sbbirth3='" +  IdcardUtil.getBirthByIdCard(em.getEmba_sbidcard3())+ "'";
			}
		}
		
		if(em.getEmba_sbidcard4()!=null)
		{
			if (!em.getEmba_sbidcard4().equals("")) {
				sql = sql + ",Emba_sbidcard4='" + em.getEmba_sbidcard4() + "'";
				sql = sql + ",Emba_sbbirth4='" +  IdcardUtil.getBirthByIdCard(em.getEmba_sbidcard4())+ "'";
			}
		}
		
		
		if(em.getEmba_sbrelation2()!=null)
		{
			if (!em.getEmba_sbrelation2().equals("")) {
				sql = sql + ",Emba_sbrelation2='" + em.getEmba_sbrelation2() + "'";
			}
		}
		
		if(em.getEmba_sbrelation1()!=null)
		{
			if (!em.getEmba_sbrelation1().equals("")) {
				sql = sql + ",Emba_sbrelation1='" + em.getEmba_sbrelation1() + "'";
			}
		}
		
		
		if(em.getEmba_sbrelation3()!=null)
		{
			if (!em.getEmba_sbrelation3().equals("")) {
				sql = sql + ",Emba_sbrelation3='" + em.getEmba_sbrelation3() + "'";
			}
		}
		
		
		if(em.getEmba_sbrelation4()!=null)
		{
			if (!em.getEmba_sbrelation4().equals("")) {
				sql = sql + ",Emba_sbrelation4='" + em.getEmba_sbrelation4() + "'";
			}
		}
		
		
		
		 
		

		sql = sql + " where gid=" + em.getEmba_id();
		if (em.getGid() != null && em.getGid() > 0) {
			try {
				i = db.updatePrepareSQL(sql);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return i;
	}
 

}
