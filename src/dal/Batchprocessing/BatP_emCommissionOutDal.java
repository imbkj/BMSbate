package dal.Batchprocessing;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import Util.DateStringChange;

import org.zkoss.zul.ListModelList;

import Conn.dbconn;
//import Model.EmShebaoChangeUploadModel;
import Model.EmbaseModel;
import Util.IdcardUtil;

public class BatP_emCommissionOutDal {
	private dbconn conn;

	public BatP_emCommissionOutDal() {
		conn = new dbconn();
	}

	// 社保批量导入数据库
	public int addBatchUpload(EmbaseModel em) {
		Integer i = 0;
		dbconn db = new dbconn();
		try {
			i = Integer
					.valueOf(db
							.callWithReturn(
									"{?=call EmBasebatchadd_P_zmj(?,?,?,?,?,?,?,?,?" +
									",?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?" +
									",?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?" +
									",?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?," +
									"?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?," +
									"?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?," +
									"?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}",
									Types.INTEGER, em.getCid(),
									em.getEmba_type(), em.getEmba_name(),
									em.getEmba_sex(), em.getEmba_idcard().toUpperCase(),
									em.getEmba_idcardclass(),
									em.getEmba_hjadd(), em.getEmba_native(),
									em.getEmba_hjtype(),
									em.getEmba_nationality(),
									em.getEmba_folk(), DateStringChange.StringtoDate(em.getEmba_birth(), "yyyy_MM-dd"),
									em.getEmba_marital(), em.getEmba_phone(),
									em.getEmba_mobile(), em.getEmba_epname(),
									em.getEmba_epmobile(),
									em.getEmba_address(), em.getEmba_email(),
									em.getEmba_privateemail(),
									em.getEmba_party(), em.getEmba_status(),
									em.getEmba_degree(), em.getEmba_school(),
									em.getEmba_specialty(),
									em.getEmba_graduation(),
									em.getEmba_fileplace(),
									em.getEmba_fileinclass(),
									em.getEmba_filereason(),
									em.getEmba_filedebts(),
									em.getEmba_filedebtsmonth(),
									em.getEmba_filehj(),
									em.getEmba_computerid(), em.getEmba_hand(),
									em.getEmba_sbcard(), em.getEmba_houseid(),
									em.getEmba_excompanystate(),
									em.getEmba_title(), em.getEmba_wifename(),
									em.getEmba_wifeidcard(),
									em.getEmba_gz_email(),
									em.getEmba_gz_cemail(),
									em.getEmba_gz_account(),
									em.getEmba_gz_bank(),
									em.getEmba_gz_bxbank(),
									em.getEmba_gz_bxaccount(),
									em.getEmba_housecode(),
									em.getEmba_housetime(),
									em.getEmba_housetype(),
									em.getEmba_houseclass(),
									em.getEmba_skilllevel(),
									em.getEmba_worktime(), em.getEmba_sztime(),
									em.getEmba_hjtime(), em.getEmba_regtype(),
									em.getEmba_compactlimit(),
									em.getEmba_compactstart(),
									em.getEmba_compactend(),
									em.getEmba_companystart(),
									em.getEmba_station(),
									em.getEmba_birthcontrol(),
									em.getEmba_photonum(),
									em.getEmba_sy_account(),
									em.getEmba_sy_bank(), em.getEmba_sbname1(),
									em.getEmba_sbname2(), em.getEmba_sbname3(),
									em.getEmba_sbname4(), em.getEmba_sbage1(),
									em.getEmba_sbage2(), em.getEmba_sbage3(),
									em.getEmba_sbage4(),
									em.getEmba_sbidcard1(),
									em.getEmba_sbidcard2(),
									em.getEmba_sbidcard3(),
									em.getEmba_sbidcard4(),
									em.getEmba_sbrelation1(),
									em.getEmba_sbrelation2(),
									em.getEmba_sbrelation3(),
									em.getEmba_sbrelation4(),
									em.getEmba_hospital(),
									em.getEmba_bcaddress(),
									em.getEmba_bctime(),
									em.getEmba_worktime1(),
									em.getEmba_worktime2(),
									em.getEmba_worktime3(),
									em.getEmba_worktime4(),
									em.getEmba_worktime5(),
									em.getEmba_worktime6(),
									em.getEmba_workcompany1(),
									em.getEmba_workcompany2(),
									em.getEmba_workcompany3(),
									em.getEmba_workcompany4(),
									em.getEmba_workcompany5(),
									em.getEmba_workcompany6(),
									em.getEmba_workjob1(),
									em.getEmba_workjob2(),
									em.getEmba_workjob3(),
									em.getEmba_workjob4(),
									em.getEmba_workjob5(),
									em.getEmba_workjob6(), em.getEmba_f1(),
									em.getEmba_f2(), em.getEmba_f3(),
									em.getEmba_f4(), em.getEmba_f5(),
									em.getEmba_f6(), em.getEmba_fn1(),
									em.getEmba_fn2(), em.getEmba_fn3(),
									em.getEmba_fn4(), em.getEmba_fn5(),
									em.getEmba_fn6(), em.getEmba_fag1(),
									em.getEmba_fag2(), em.getEmba_fag3(),
									em.getEmba_fag4(), em.getEmba_fag5(),
									em.getEmba_fag6(), em.getEmba_fw1(),
									em.getEmba_fw2(), em.getEmba_fw3(),
									em.getEmba_fw4(), em.getEmba_fw5(),
									em.getEmba_fw6(), em.getEmba_fr1(),
									em.getEmba_fr2(), em.getEmba_fr3(),
									em.getEmba_fr4(), em.getEmba_fr5(),
									em.getEmba_fr6(), em.getEmba_remark(),
									em.getEmba_addname(), em.getEmba_state(),
									em.getEmba_excompanyid(),
									em.getEmba_excompany(), em.getEmba_zytid(),
									em.getEmba_zytwtgid(),
									em.getEmba_ifcomputerid(),
									em.getEmba_sbcard_notice(),
									em.getEmba_service_place(),
									em.getEmba_ifhouse(),
									em.getEmba_form(),
									em.getEmba_excelfile(),
									em.getEmba_err(),
									em.getEmba_statebatchstr(),
									em.getEmba_batchtype(),
									em.getEmba_indate(),em.getEmba_writeoff_bank(),em.getEmba_writeoff_account()
									).toString()
									
									);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return i;
	}
	
	//批量导入embase
	public int insertembasebatch(EmbaseModel em)
	{
		Integer i = 0;
		dbconn db = new dbconn();
		try {
			i = Integer
					.valueOf(db
							.callWithReturn(
									"{?=call EmBasebatchin_P_zmj(?,?,?,?,?,?,?,?,?,?" +
									",?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?" +
									",?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?" +
									",?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?," +
									"?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?," +
									"?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?," +
									"?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}",
									Types.INTEGER,em.getEmba_id(), em.getCid(),
									em.getEmba_type(), em.getEmba_name(),
									em.getEmba_sex(), em.getEmba_idcard().toUpperCase(),
									em.getEmba_idcardclass(),
									em.getEmba_hjadd(), em.getEmba_native(),
									em.getEmba_hjtype(),
									em.getEmba_nationality(),
									em.getEmba_folk(), DateStringChange.StringtoDate(em.getEmba_birth(), "yyyy_MM-dd"),
									em.getEmba_marital(), em.getEmba_phone(),
									em.getEmba_mobile(), em.getEmba_epname(),
									em.getEmba_epmobile(),
									em.getEmba_address(), em.getEmba_email(),
									em.getEmba_privateemail(),
									em.getEmba_party(), em.getEmba_status(),
									em.getEmba_degree(), em.getEmba_school(),
									em.getEmba_specialty(),
									em.getEmba_graduation(),
									em.getEmba_fileplace(),
									em.getEmba_fileinclass(),
									em.getEmba_filereason(),
									em.getEmba_filedebts(),
									em.getEmba_filedebtsmonth(),
									em.getEmba_filehj(),
									em.getEmba_computerid(), em.getEmba_hand(),
									em.getEmba_sbcard(), em.getEmba_houseid(),
									em.getEmba_excompanystate(),
									em.getEmba_title(), em.getEmba_wifename(),
									em.getEmba_wifeidcard(),
									em.getEmba_gz_email(),
									em.getEmba_gz_cemail(),
									em.getEmba_gz_account(),
									em.getEmba_gz_bank(),
									em.getEmba_gz_bxbank(),
									em.getEmba_gz_bxaccount(),
									em.getEmba_housecode(),
									em.getEmba_housetime(),
									em.getEmba_housetype(),
									em.getEmba_houseclass(),
									em.getEmba_skilllevel(),
									em.getEmba_worktime(), em.getEmba_sztime(),
									em.getEmba_hjtime(), em.getEmba_regtype(),
									em.getEmba_compactlimit(),
									em.getEmba_compactstart(),
									em.getEmba_compactend(),
									em.getEmba_companystart(),
									em.getEmba_station(),
									em.getEmba_birthcontrol(),
									em.getEmba_photonum(),
									em.getEmba_sy_account(),
									em.getEmba_sy_bank(), em.getEmba_sbname1(),
									em.getEmba_sbname2(), em.getEmba_sbname3(),
									em.getEmba_sbname4(), em.getEmba_sbage1(),
									em.getEmba_sbage2(), em.getEmba_sbage3(),
									em.getEmba_sbage4(),
									em.getEmba_sbidcard1(),
									em.getEmba_sbidcard2(),
									em.getEmba_sbidcard3(),
									em.getEmba_sbidcard4(),
									em.getEmba_sbrelation1(),
									em.getEmba_sbrelation2(),
									em.getEmba_sbrelation3(),
									em.getEmba_sbrelation4(),
									em.getEmba_hospital(),
									em.getEmba_bcaddress(),
									em.getEmba_bctime(),
									em.getEmba_worktime1(),
									em.getEmba_worktime2(),
									em.getEmba_worktime3(),
									em.getEmba_worktime4(),
									em.getEmba_worktime5(),
									em.getEmba_worktime6(),
									em.getEmba_workcompany1(),
									em.getEmba_workcompany2(),
									em.getEmba_workcompany3(),
									em.getEmba_workcompany4(),
									em.getEmba_workcompany5(),
									em.getEmba_workcompany6(),
									em.getEmba_workjob1(),
									em.getEmba_workjob2(),
									em.getEmba_workjob3(),
									em.getEmba_workjob4(),
									em.getEmba_workjob5(),
									em.getEmba_workjob6(), em.getEmba_f1(),
									em.getEmba_f2(), em.getEmba_f3(),
									em.getEmba_f4(), em.getEmba_f5(),
									em.getEmba_f6(), em.getEmba_fn1(),
									em.getEmba_fn2(), em.getEmba_fn3(),
									em.getEmba_fn4(), em.getEmba_fn5(),
									em.getEmba_fn6(), em.getEmba_fag1(),
									em.getEmba_fag2(), em.getEmba_fag3(),
									em.getEmba_fag4(), em.getEmba_fag5(),
									em.getEmba_fag6(), em.getEmba_fw1(),
									em.getEmba_fw2(), em.getEmba_fw3(),
									em.getEmba_fw4(), em.getEmba_fw5(),
									em.getEmba_fw6(), em.getEmba_fr1(),
									em.getEmba_fr2(), em.getEmba_fr3(),
									em.getEmba_fr4(), em.getEmba_fr5(),
									em.getEmba_fr6(), em.getEmba_remark(),
									em.getEmba_addname(), em.getEmba_state(),
									em.getEmba_excompanyid(),
									em.getEmba_excompany(), em.getEmba_zytid(),
									em.getEmba_zytwtgid(),
									em.getEmba_ifcomputerid(),
									em.getEmba_sbcard_notice(),
									em.getEmba_service_place(),
									em.getEmba_ifhouse(),
									em.getEmba_form(),
									em.getEmba_excelfile(),
									em.getEmba_err()
									).toString()
									
									);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return i;
	}
	
	
	//修改导入的数据 0：未导入；1：已导入；2：导入出错；3：删除；
	public int updateEmbaseBatch(EmbaseModel em) {
		Integer i = 0;
		dbconn db = new dbconn();
		try {
			i = Integer
					.valueOf(db
							.callWithReturn(
									"{?=call EmBasebatchupdate_P_zmj(?,?,?,?)}",
									Types.INTEGER, em.getEmba_id(),
									em.getEmba_state(),
									em.getEmba_err(),
									em.getEmba_modname()
									).toString()
									
									);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return i;
	}
	
	
	
	// 根据用户名获取员工信息
		public List<EmbaseModel> getEmBaseById(String username) {
			List<EmbaseModel> list = new ListModelList<EmbaseModel>();
			dbconn db = new dbconn();
			String sql = "select emba_gjjuname,emba_gjjidcard,emba_sbuname,emba_sbidcard,emba_id,a.cid,a.gid,coba_company,coba_shortname,coba_client,"
					+ "emba_name,emba_idcard,emba_idcardclass,emba_sex,emba_phone,emba_mobile,emba_email,emba_englishname,"
					+ "convert(varchar(10),emba_indate,120)emba_indate,convert(varchar(10),emba_birth,120)emba_birth,"
					+ "emba_wifename,emba_wifeidcard,emba_sb_place,emba_house_place,"
					+ "emba_excompany,emba_excompanyid,emba_houseid,emba_house_radix,emba_emsb_foreigner,emba_nationality,"
					+ "emba_emhb_startdate,emba_emhb_stopdate,emba_emhb_ownmonth,emba_emhb_feeownmonth,emba_emhb_reason,emba_emhb_total,"
					+ "emba_sbuname ,emba_sbidcard,emba_sb_radix,emba_gjjuname,emba_gjjidcard,emba_house_radix,emba_house_cpp,emba_state,"
					+ " emba_tapr_id,emba_excelfile,emba_err,emba_folk ,emba_statebatchstr,emba_addname,emba_addtime "
					+ "from EmBasebatchadd a "
					+ "inner join cobase b on a.cid=b.cid " 
					+" where emba_state in(0,1) and (emba_addname=? or coba_client=?) and emba_addtime>dateadd(MM,-1,getdate()) order by emba_id desc";
			try {
				list = db.find(sql, EmbaseModel.class,
						dbconn.parseSmap(EmbaseModel.class), username,username);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return list;
		}

//		//导入前去掉空格
//		public EmbaseModel trimmodel(EmbaseModel em)
//		{
//			//EmbaseModel newem = new EmbaseModel();
//			
//			//身份证，姓名 去掉前后空格
//			em.setEmba_name(em.getEmba_name().trim());
//			em.setEmba_idcard(em.getEmba_idcard().trim());
//			em.setEmba_gz_account(em.getEmba_gz_account().replace(" ", ""));
//			em.setEmba_house_account(em.getEmba_house_account().replace(" ", ""));
//			em.setEmba_writeoff_account(em.getEmba_writeoff_account().replace(" ", ""));
//			em.setEmba_sy_account(em.getEmba_sy_account().replace(" ", ""));
//			return em;
//		}

		public EmbaseModel checkdata(EmbaseModel em)
		{
			StringBuilder err = new StringBuilder();
		
		
			
			 
		
			if (!"".equals(em.getEmba_gz_account())&&em.getEmba_gz_account()!=null){
			em.setEmba_gz_account(em.getEmba_gz_account().trim());
			em.setEmba_gz_account(em.getEmba_gz_account().replace(" ", ""));
			
			}
			if (!"".equals(em.getEmba_house_account())&&em.getEmba_house_account()!=null){
				em.setEmba_house_account(em.getEmba_house_account().trim());
			em.setEmba_house_account(em.getEmba_house_account().replace(" ", ""));
			
			}
			if (!"".equals(em.getEmba_writeoff_account())&&em.getEmba_writeoff_account()!=null){
				em.setEmba_writeoff_account(em.getEmba_writeoff_account().trim());
			em.setEmba_writeoff_account(em.getEmba_writeoff_account().replace(" ", ""));
			}
			if (!"".equals(em.getEmba_sy_account())&&em.getEmba_sy_account()!=null){
				em.setEmba_sy_account(em.getEmba_sy_account().trim());
			em.setEmba_sy_account(em.getEmba_sy_account().replace(" ", ""));
			}
			
			if (!"".equals(em.getEmba_gz_bank())&&em.getEmba_gz_bank()!=null){
				em.setEmba_gz_bank(em.getEmba_gz_bank().trim());
				em.setEmba_gz_bank(em.getEmba_gz_bank().replace(" ", ""));
				
				}
				if (!"".equals(em.getEmba_house_bank())&&em.getEmba_house_bank()!=null){
					em.setEmba_house_bank(em.getEmba_house_bank().trim());
				em.setEmba_house_bank(em.getEmba_house_bank().replace(" ", ""));
				
				}
				if (!"".equals(em.getEmba_writeoff_bank())&&em.getEmba_writeoff_bank()!=null){
					em.setEmba_writeoff_bank(em.getEmba_writeoff_bank().trim());
				em.setEmba_writeoff_bank(em.getEmba_writeoff_bank().replace(" ", ""));
				}
				if (!"".equals(em.getEmba_sy_bank())&&em.getEmba_sy_bank()!=null){
					em.setEmba_sy_bank(em.getEmba_sy_bank().trim());
				em.setEmba_sy_bank(em.getEmba_sy_bank().replace(" ", ""));
				}
			
			
			//为空项判断
			if(em.getEmba_name()==null || em.getEmba_name().equals("") )
			{
				err.append("导入姓名不能为空！");
			}
			else
			{
				em.setEmba_name(em.getEmba_name().trim());
			}
			if(em.getEmba_idcard()==null || em.getEmba_idcard().equals("") )
			{
				err.append("导入身份证不能为空！");
			}
			else
			{
				em.setEmba_idcard(em.getEmba_idcard().trim());
			}
			if(em.getCid()==null || em.getCid().equals(0) ||em.getCid().equals("") )
			{
				err.append("导入公司编号有误！");
			}
		
			if(em.getEmba_indate()==null || em.getEmba_indate().equals(""))
			{
				err.append("导入入职时间不能为空！");
			}
			if(em.getEmba_mobile()==null || em.getEmba_mobile().equals(""))
			{
				err.append("导入手机不能为空！");
			}
			
			if(em.getEmba_nationality()==null || em.getEmba_nationality().equals(""))
			{
				err.append("国籍不能为空！");
			}
	
	
			//身份证号码有误，国籍
			
			if (em.getEmba_nationality().equals("中国"))
			{
				// 中国身份证，计算性别。生日
				//if (em.getEmba_idcardclass().equals("身份证")) {
					if (em.getEmba_idcard() != null
							&& IdcardUtil.validateCard(em.getEmba_idcard())) {
						em.setEmba_idcard(em.getEmba_idcard().trim());
						String sex = IdcardUtil.getGenderByIdCard(em.getEmba_idcard());
						em.setEmba_sex(sex.equals("未知") ? "" : sex);
						em.setEmba_birth(IdcardUtil.getBirthByIdCard2(em
								.getEmba_idcard()).toString());

						
					
					} else {
						err.append("身份证，不合法。");
					}

				//}
				
			}
			//身份证，姓名 去掉前后空格
			
			//银行账号去掉空格
		
		
			//emmail有且只有一个@
			
			//该公司存在此员工
			err.append(ifExists(em));
			
			em.setEmba_err(err.toString());
			
			
			return em;
		}
		
	 

public String ifExists(EmbaseModel em)
{
	String str="";
	 
	dbconn db = new dbconn();
	String sql="select *  from embase where emba_state>0 and emba_idcard='"+em.getEmba_idcard()+"' ";
	try {
		ResultSet rs = db.GRS(sql);
		while (rs.next()) {
			str="该员工已入职";
		}
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

	return str;
}
}



 