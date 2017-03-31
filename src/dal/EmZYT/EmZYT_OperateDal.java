/*
 * 创建人：林少斌
 * 创建时间：2014-3-10
 * 用途：智翼通接口数据处理Dal
 */
package dal.EmZYT;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.NumberFormat;

import Conn.dbconn;
import Model.EmZYTFeedbackFileModel;
import Model.EmZYTFeedbackModel;
import Model.EmZYTModel;

public class EmZYT_OperateDal {
	private static dbconn conn = new dbconn();

	// 插入智翼通委托单的文件
	public int uploadZYT(String sheetname, String filename, String ownmonth,
			String username, String fileAllname) {
		int result;

		String sql = "insert into EmZYT (ownmonth,emzt_zytid,emzt_company,emzt_name,emzt_class,emzt_uptime,emzt_mobile,emzt_fee,emzt_filefee,emzt_idcard,emzt_sname,emzt_scity,emzt_scompany,emzt_total,emzt_sbtotal,emzt_housetotal,emzt_filename,emzt_addname,emzt_ylradix,emzt_jlradix,emzt_gsradix,emzt_syeradix,emzt_syuradix,emzt_houseradix,emzt_remark,emzt_ylstart,emzt_ylstop,emzt_jlstart,emzt_jlstop,emzt_gsstart,emzt_gsstop,emzt_syestart,emzt_syestop,emzt_syustart,emzt_syustop,emzt_housestart,emzt_housestop,emzt_sbtitle,emzt_housetitle,emzt_compactstart,emzt_compactstop,emzt_zcid,emzt_zgid,emzt_flfee,emzt_flcontent,emzt_phone,emzt_idcardclass,emzt_bchousestart,emzt_bchousestop,emzt_bchouseradix,emzt_flstart,emzt_flstop,emzt_flfeeinfo,emzt_elsefee,emzt_elsefeestart,emzt_elsefeestop,emzt_feestart,emzt_feestop,emzt_filefeestart,emzt_filefeestop,emzt_managefee,emzt_cityremark,emzt_agreement,emzt_iffile,emzt_rdate,emzt_ifsingle,emzt_ifunlimited,emzt_outdate,emzt_ylcpp,emzt_ylopp,emzt_ylcp,emzt_ylop,emzt_jlcpp,emzt_jlopp,emzt_jlcp,emzt_jlop,emzt_gscpp,emzt_gsopp,emzt_gscp,emzt_gsop,emzt_syecpp,emzt_syeopp,emzt_syecp,emzt_syeop,emzt_syucpp,emzt_syuopp,emzt_syucp,emzt_syuop,emzt_housecpp,emzt_houseopp,emzt_housecp,emzt_houseop,emzt_sbsingle,emzt_housesingle,emzt_filesingle,emzt_rsingle,emzt_yltotal,emzt_syetotal,emzt_gstotal,emzt_jltotal,emzt_syutotal,emzt_wtgid,emzt_tel,emzt_wtcid,emzt_outreason,emzt_sex,emzt_iffeefile) select "
				+ ownmonth
				+ ",[序号], [公司名称],[雇员姓名],[委托类型],[委托确认日期],isnull([手机号码],0),convert(money,[执行服务费]),convert(money,[执行档案保管费]),[证件号码],[客户负责人],right([机构名称],2),[机构名称],convert(money,[执行管理费总计]),convert(money,[执行其中社保费]),(convert(money,[执行公积金个人缴费])+convert(money,[执行公积金企业缴费])),'"
				+ filename
				+ "','"
				+ username
				+ "',[执行养老企业基数],[执行医疗企业基数],[执行工伤企业基数],[执行失业企业基数],[执行生育企业基数],convert(varchar(50),cast(round([执行公积金企业基数],2) as numeric(20,2))),[委托方备注],case when [执行养老保险执行年月]='0.00' then null else convert(smalldatetime,replace([执行养老保险执行年月],'.','-')+'-01') end,case when [执行养老保险结束年月]='0.00' then null else convert(smalldatetime,replace([执行养老保险结束年月],'.','-')+'-01') end,case when [执行医疗保险执行年月]='0.00' then null else convert(smalldatetime,replace([执行医疗保险执行年月],'.','-')+'-01') end,case when [执行医疗保险结束年月]='0.00' then null else convert(smalldatetime,replace([执行医疗保险结束年月],'.','-')+'-01') end,case when [执行工伤保险执行年月]='0.00' then null else convert(smalldatetime,replace([执行工伤保险执行年月],'.','-')+'-01') end,case when [执行工伤保险结束年月]='0.00' then null else convert(smalldatetime,replace([执行工伤保险结束年月],'.','-')+'-01') end,case when [执行失业保险执行年月]='0.00' then null else convert(smalldatetime,replace([执行失业保险执行年月],'.','-')+'-01') end,case when [执行失业保险结束年月]='0.00' then null else convert(smalldatetime,replace([执行失业保险结束年月],'.','-')+'-01') end,case when [执行生育保险执行年月]='0.00' then null else convert(smalldatetime,replace([执行生育保险执行年月],'.','-')+'-01') end,case when [执行生育保险结束年月]='0.00' then null else convert(smalldatetime,replace([执行生育保险结束年月],'.','-')+'-01') end,case when [执行公积金执行年月]='0.00' then null else convert(smalldatetime,replace([执行公积金执行年月],'.','-')+'-01') end,case when [执行公积金结束年月]='0.00' then null else convert(smalldatetime,replace([执行公积金结束年月],'.','-')+'-01') end,[社保标准],[公积金标准],case when [合同开始日期]='' then null else [合同开始日期] end,case when 合同结束日期='' then null when 合同结束日期='NULL' then null else 合同结束日期 end,[智翼通公司编号],[智翼通雇员编号],convert(money,[执行服务产品合计]),[执行服务产品名称],[公司电话],[证件类型],case when [执行补充公积金执行年月]='0.00' then null else convert(smalldatetime,replace([执行补充公积金执行年月],'.','-')+'-01') end,case when [执行补充公积金结束年月]='0.00' then null else convert(smalldatetime,replace([执行补充公积金结束年月],'.','-')+'-01') end,[执行补充公积金企业基数],case when [执行服务产品执行年月]='0.00' then null else convert(smalldatetime,replace([执行服务产品执行年月],'.','-')+'-01') end,case when [执行服务产品结束年月]='0.00' then null else convert(smalldatetime,replace([执行服务产品结束年月],'.','-')+'-01') end,[执行服务产品金额],convert(money,[执行额外费用]),case when [执行额外费用执行年月]='0.00' then null else convert(smalldatetime,replace([执行额外费用执行年月],'.','-')+'-01') end,case when [执行额外费用结束年月]='0.00' then null else convert(smalldatetime,replace([执行额外费用结束年月],'.','-')+'-01') end,case when [执行服务费执行年月]='0.00' then null else convert(smalldatetime,replace([执行服务费执行年月],'.','-')+'-01') end,case when [执行服务费结束年月]='0.00' then null else convert(smalldatetime,replace([执行服务费结束年月],'.','-')+'-01') end,case when [执行档案保管费执行年月]='0.00' then null else convert(smalldatetime,replace([执行档案保管费执行年月],'.','-')+'-01') end,case when [执行档案保管费结束年月]='0.00' then null else convert(smalldatetime,replace([执行档案保管费结束年月],'.','-')+'-01') end,convert(money,[执行其中管理费]),[城市备注],[服务约定],[是否保管档案],case when [办理用工日期]='' then null when [办理用工日期]='NULL' then null else [办理用工日期] end,[是否独立户],[无期限合同],case when [退工日期]='' then null when [退工日期]='NULL' then null else [退工日期] end,[执行养老企业比例],[执行养老个人比例],convert(money,[执行养老企业缴费]),convert(money,[执行养老个人缴费]),[执行医疗企业比例],[执行医疗个人比例],convert(money,[执行医疗企业缴费]),convert(money,[执行医疗个人缴费]),[执行工伤企业比例],null,convert(money,[执行工伤企业缴费]),0,[执行失业企业比例],[执行失业个人比例],convert(money,[执行失业企业缴费]),convert(money,[执行失业个人缴费]),[执行生育企业比例],null,convert(money,[执行生育企业缴费]),0,[执行公积金企业比例],[执行公积金个人比例],convert(money,[执行公积金企业缴费]),convert(money,[执行公积金个人缴费]),[社保情况],[公积金情况],[档案情况],[办理用工情况],(convert(money,[执行养老企业缴费])+convert(money,[执行养老个人缴费])),(convert(money,[执行失业企业缴费])+convert(money,[执行失业个人缴费])),convert(money,[执行工伤企业缴费]),(convert(money,[执行医疗企业缴费])+convert(money,[执行医疗个人缴费])),convert(money,[执行生育企业缴费]),[委托方雇员编号],[家庭电话],[委托方公司编号],[终止原因],[性别],convert(varchar(10),[执行服档费]) from opendatasource ('Microsoft.ACE.OLEDB.12.0','data source="
				+ fileAllname
				+ ";Extended properties=excel 5.0;')...["
				+ sheetname
				+ "$] where [公司名称] is not null and [委托类型] in('新增','社保基数调整','户籍调整','特殊调整','服务项目调整','服务费调整','年度调整','终止','一次性费用') and [序号] not in(select emzt_zytid from emzyt where emzt_state in(0,10,2,1,4,5,6,7,8,9) and ownmonth="
				+ ownmonth + ")";
		// System.out.println(sql);
		try {

			conn.execQuery(sql);
			result = 0;

			EmZYTModel m = new EmZYTModel();
			m.setOwnmonth(Integer.parseInt(ownmonth));
			m.setEmzt_filename(filename);

			// 调用上传文件后的更新方法
			updateUploadFile(m);
			updateUploadFileBJ(m);

		} catch (Exception e) {
			e.printStackTrace();
			result = 1;
		}

		return result;
	}

	// 插入智翼通委托单的文件
	public int uploadZYTbyModel(EmZYTModel m) {
		int result;

		String sql = "insert into emzyt(ownmonth,emzt_zytid,emzt_company,emzt_name,emzt_class,emzt_uptime,emzt_mobile,"
				+ "emzt_fee,emzt_filefee,emzt_idcard,emzt_sname,emzt_scity,emzt_scompany,emzt_total,emzt_sbtotal,emzt_housetotal,"
				+ "emzt_filename,emzt_addname,emzt_ylradix,emzt_jlradix,emzt_gsradix,emzt_syeradix,emzt_syuradix,emzt_houseradix,"
				+ "emzt_remark,emzt_ylstart,emzt_ylstop,emzt_jlstart,emzt_jlstop,emzt_gsstart,emzt_gsstop,emzt_syestart,emzt_syestop,emzt_syustart,"
				+ "emzt_syustop,emzt_housestart,emzt_housestop,emzt_sbtitle,emzt_housetitle,emzt_compactstart,emzt_compactstop,emzt_zcid,"
				+ "emzt_zgid,emzt_flfee,emzt_flcontent,emzt_phone,emzt_idcardclass,emzt_bchousestart,emzt_bchousestop,emzt_bchouseradix,"
				+ "emzt_flstart,emzt_flstop,emzt_flfeeinfo,emzt_elsefee,emzt_elsefeestart,emzt_elsefeestop,emzt_feestart,emzt_feestop,emzt_filefeestart,"
				+ "emzt_filefeestop,emzt_managefee,emzt_cityremark,emzt_agreement,emzt_iffile,emzt_rdate,emzt_ifsingle,emzt_ifunlimited,emzt_outdate,"
				+ "emzt_ylcpp,emzt_ylopp,emzt_ylcp,emzt_ylop,emzt_jlcpp,emzt_jlopp,emzt_jlcp,emzt_jlop,emzt_gscpp,emzt_gsopp,emzt_gscp,emzt_gsop,"
				+ "emzt_syecpp,emzt_syeopp,emzt_syecp,emzt_syeop,emzt_syucpp,emzt_syuopp,emzt_syucp,emzt_syuop,emzt_housecpp,emzt_houseopp,"
				+ "emzt_housecp,emzt_houseop,emzt_sbsingle,emzt_housesingle,emzt_filesingle,emzt_rsingle,emzt_yltotal,emzt_syetotal,emzt_gstotal,emzt_jltotal,"
				+ "emzt_syutotal,emzt_wtgid,emzt_tel,emzt_wtcid,emzt_outreason,emzt_sex,emzt_iffeefile) "
				+ "select "
				+ String.valueOf(m.getOwnmonth())
				+ ","
				+ String.valueOf(m.getEmzt_zytid())
				+ ",'"
				+ m.getEmzt_company()
				+ "','"
				+ m.getEmzt_name()
				+ "','"
				+ m.getEmzt_class()
				+ "','"
				+ m.getEmzt_uptime()
				+ "','"
				+ m.getEmzt_mobile()
				+ "', convert(money,'"
				+ String.valueOf(m.getEmzt_fee())
				+ "'), convert(money,'"
				+ String.valueOf(m.getEmzt_filefee())
				+ "'),"
				+ "'"
				+ m.getEmzt_idcard()
				+ "','"
				+ m.getEmzt_sname()
				+ "','"
				+ m.getEmzt_scity()
				+ "','"
				+ m.getEmzt_scompany()
				+ "', convert(money,'"
				+ String.valueOf(m.getEmzt_total())
				+ "'),"
				+ " convert(money,'"
				+ String.valueOf(m.getEmzt_sbtotal())
				+ "'), convert(money,'"
				+ String.valueOf(m.getEmzt_housetotal())
				+ "'), '"
				+ m.getEmzt_filename()
				+ "','"
				+ m.getEmzt_addname()
				+ "','"
				+ m.getEmzt_ylradix()
				+ "','"
				+ m.getEmzt_jlradix()
				+ "','"
				+ m.getEmzt_gsradix()
				+ "','"
				+ m.getEmzt_syeradix()
				+ "','"
				+ m.getEmzt_syuradix()
				+ "',  convert(varchar(50),cast(round('"
				+ m.getEmzt_houseradix()
				+ "',2) as numeric(20,2))), "
				+ " '"
				+ m.getEmzt_remark()
				+ "',  case when '"
				+ m.getEmzt_ylstart()
				+ "'='0.00' then null when '"
				+ m.getEmzt_ylstart()
				+ "'='null' then null else convert(smalldatetime,replace('"
				+ m.getEmzt_ylstart()
				+ "','.','-')+'-01') end,"
				+ "  case when '"
				+ m.getEmzt_ylstop()
				+ "'='0.00' then null when '"
				+ m.getEmzt_ylstop()
				+ "'='null' then null else convert(smalldatetime,replace('"
				+ m.getEmzt_ylstop()
				+ "','.','-')+'-01') end, "
				+ " case when '"
				+ m.getEmzt_jlstart()
				+ "'='0.00' then null when '"
				+ m.getEmzt_jlstart()
				+ "'='null' then null  else convert(smalldatetime,replace('"
				+ m.getEmzt_jlstart()
				+ "','.','-')+'-01') end, "
				+ " case when '"
				+ m.getEmzt_jlstop()
				+ "'='0.00' then null when '"
				+ m.getEmzt_jlstop()
				+ "'='null' then null  else convert(smalldatetime,replace('"
				+ m.getEmzt_jlstop()
				+ "','.','-')+'-01') end,  "
				+ "case when '"
				+ m.getEmzt_gsstart()
				+ "'='0.00' then null when '"
				+ m.getEmzt_gsstart()
				+ "'='null' then null  else convert(smalldatetime,replace('"
				+ m.getEmzt_gsstart()
				+ "','.','-')+'-01') end,  "
				+ "case when '"
				+ m.getEmzt_gsstop()
				+ "'='0.00' then null when '"
				+ m.getEmzt_gsstop()
				+ "'='null' then null  else convert(smalldatetime,replace('"
				+ m.getEmzt_gsstop()
				+ "','.','-')+'-01') end,  "
				+ "case when '"
				+ m.getEmzt_syestart()
				+ "'='0.00' then null when '"
				+ m.getEmzt_syestart()
				+ "'='null' then null  else convert(smalldatetime,replace('"
				+ m.getEmzt_syestart()
				+ "','.','-')+'-01') end,  "
				+ "case when '"
				+ m.getEmzt_syestop()
				+ "'='0.00' then null when '"
				+ m.getEmzt_syestop()
				+ "'='null' then null  else convert(smalldatetime,replace('"
				+ m.getEmzt_syestop()
				+ "','.','-')+'-01') end,  "
				+ "case when '"
				+ m.getEmzt_syustart()
				+ "'='0.00' then null when '"
				+ m.getEmzt_syustart()
				+ "'='null' then null  else convert(smalldatetime,replace('"
				+ m.getEmzt_syustart()
				+ "','.','-')+'-01') end,  "
				+ "case when '"
				+ m.getEmzt_syustop()
				+ "'='0.00' then null when '"
				+ m.getEmzt_syustop()
				+ "'='null' then null  else convert(smalldatetime,replace('"
				+ m.getEmzt_syustop()
				+ "','.','-')+'-01') end,  "
				+ "case when '"
				+ m.getEmzt_housestart()
				+ "'='0.00' then null when '"
				+ m.getEmzt_housestart()
				+ "'='null' then null  else convert(smalldatetime,replace('"
				+ m.getEmzt_housestart()
				+ "','.','-')+'-01') end,  "
				+ "case when '"
				+ m.getEmzt_housestop()
				+ "'='0.00' then null when '"
				+ m.getEmzt_housestop()
				+ "'='null' then null  else convert(smalldatetime,replace('"
				+ m.getEmzt_housestop()
				+ "','.','-')+'-01') end, "
				+ " '"
				+ m.getEmzt_sbtitle()
				+ "', '"
				+ m.getEmzt_housetitle()
				+ "',  case when '"
				+ m.getEmzt_compactstart()
				+ "'='' then null  when '"
				+ m.getEmzt_compactstart()
				+ "'='null' then null else '"
				+ m.getEmzt_compactstart()
				+ "' end,  "
				+ "case when '"
				+ m.getEmzt_compactstop()
				+ "'='' then null when '"
				+ m.getEmzt_compactstop()
				+ "'='NULL' then null  when '"
				+ m.getEmzt_compactstop()
				+ "'='null' then null else '"
				+ m.getEmzt_compactstop()
				+ "' end, "
				+ " '"
				+ m.getEmzt_zcid()
				+ "',  '"
				+ m.getEmzt_zgid()
				+ "',  convert(money,'"
				+ String.valueOf(m.getEmzt_flfee())
				+ "'),'"
				+ m.getEmzt_flcontent()
				+ "','"
				+ m.getEmzt_phone()
				+ "','"
				+ m.getEmzt_idcardclass()
				+ "',"
				+ "  case when '"
				+ m.getEmzt_bchousestart()
				+ "'='0.00' then null  when '"
				+ m.getEmzt_bchousestart()
				+ "'='null' then null else convert(smalldatetime,replace('"
				+ m.getEmzt_bchousestart()
				+ "','.','-')+'-01') end,  "
				+ "case when '"
				+ m.getEmzt_bchousestop()
				+ "'='0.00' then null  when '"
				+ m.getEmzt_bchousestop()
				+ "'='null' then null else convert(smalldatetime,replace('"
				+ m.getEmzt_bchousestop()
				+ "','.','-')+'-01') end,  "
				+ "'"
				+ m.getEmzt_bchouseradix()
				+ "',  case when '"
				+ m.getEmzt_flstart()
				+ "'='0.00' then null  when '"
				+ m.getEmzt_flstart()
				+ "'='null' then null else convert(smalldatetime,replace('"
				+ m.getEmzt_flstart()
				+ "','.','-')+'-01') end,  "
				+ "case when '"
				+ m.getEmzt_flstop()
				+ "'='0.00' then null  when '"
				+ m.getEmzt_flstop()
				+ "'='null' then null else convert(smalldatetime,replace('"
				+ m.getEmzt_flstop()
				+ "','.','-')+'-01') end,  "
				+ "'"
				+ m.getEmzt_flfeeinfo()
				+ "',  convert(money,'"
				+ String.valueOf(m.getEmzt_elsefee())
				+ "'), "
				+ " case when '"
				+ m.getEmzt_elsefeestart()
				+ "'='0.00' then null  when '"
				+ m.getEmzt_elsefeestart()
				+ "'='null' then null else convert(smalldatetime,replace('"
				+ m.getEmzt_elsefeestart()
				+ "','.','-')+'-01') end,  "
				+ "case when '"
				+ m.getEmzt_elsefeestop()
				+ "'='0.00' then null  when '"
				+ m.getEmzt_elsefeestop()
				+ "'='null' then null else convert(smalldatetime,replace('"
				+ m.getEmzt_elsefeestop()
				+ "','.','-')+'-01') end,  "
				+ "case when '"
				+ m.getEmzt_feestart()
				+ "'='0.00' then null  when '"
				+ m.getEmzt_feestart()
				+ "'='null' then null else convert(smalldatetime,replace('"
				+ m.getEmzt_feestart()
				+ "','.','-')+'-01') end, "
				+ " case when '"
				+ m.getEmzt_feestop()
				+ "'='0.00' then null  when '"
				+ m.getEmzt_feestop()
				+ "'='null' then null else convert(smalldatetime,replace('"
				+ m.getEmzt_feestop()
				+ "','.','-')+'-01') end, "
				+ " case when '"
				+ m.getEmzt_filefeestart()
				+ "'='0.00' then null  when '"
				+ m.getEmzt_filefeestart()
				+ "'='null' then null else convert(smalldatetime,replace('"
				+ m.getEmzt_filefeestart()
				+ "','.','-')+'-01') end,  "
				+ "case when '"
				+ m.getEmzt_filefeestop()
				+ "'='0.00' then null  when '"
				+ m.getEmzt_filefeestop()
				+ "'='null' then null else convert(smalldatetime,replace('"
				+ m.getEmzt_filefeestop()
				+ "','.','-')+'-01') end,  "
				+ "convert(money,'"
				+ String.valueOf(m.getEmzt_managefee())
				+ "'),  '"
				+ m.getEmzt_cityremark()
				+ "', '"
				+ m.getEmzt_agreement()
				+ "','"
				+ m.getEmzt_iffile()
				+ "', "
				+ " case when '"
				+ m.getEmzt_rdate()
				+ "'='' then null when '"
				+ m.getEmzt_rdate()
				+ "'='NULL' then null else '"
				+ m.getEmzt_rdate()
				+ "' end, "
				+ " '"
				+ m.getEmzt_ifsingle()
				+ "',case when '"
				+ m.getEmzt_ifunlimited()
				+ "'='TRUE' then '1' when '"
				+ m.getEmzt_ifunlimited()
				+ "'='true' then '1' when '"
				+ m.getEmzt_ifunlimited()
				+ "'='FALSE' then '0' when '"
				+ m.getEmzt_ifunlimited()
				+ "'='false' then '0' else '"
				+ m.getEmzt_ifunlimited()
				+ "' end, "
				+ " case when '"
				+ m.getEmzt_outdate()
				+ "'='' then null when '"
				+ m.getEmzt_outdate()
				+ "'='NULL' then null  when '"
				+ m.getEmzt_outdate()
				+ "'='null' then null else '"
				+ m.getEmzt_outdate()
				+ "' end,"
				+ "'"
				+ percentChange(m.getEmzt_ylcpp())
				+ "','"
				+ percentChange(m.getEmzt_ylopp())
				+ "',  convert(money,'"
				+ String.valueOf(m.getEmzt_ylcp())
				+ "'),  convert(money,'"
				+ String.valueOf(m.getEmzt_ylop())
				+ "'),'"
				+ percentChange(m.getEmzt_jlcpp())
				+ "',"
				+ "'"
				+ percentChange(m.getEmzt_jlopp())
				+ "',  convert(money,'"
				+ String.valueOf(m.getEmzt_jlcp())
				+ "'),  convert(money,'"
				+ String.valueOf(m.getEmzt_jlop())
				+ "'), '"
				+ percentChange(m.getEmzt_gscpp())
				+ "',  null, "
				+ " convert(money,'"
				+ String.valueOf(m.getEmzt_gscp())
				+ "'),  0,'"
				+ percentChange(m.getEmzt_syecpp())
				+ "','"
				+ percentChange(m.getEmzt_syeopp())
				+ "',  convert(money,'"
				+ String.valueOf(m.getEmzt_syecp())
				+ "'), "
				+ "convert(money,'"
				+ String.valueOf(m.getEmzt_syeop())
				+ "'), '"
				+ percentChange(m.getEmzt_syucpp())
				+ "',  null,  convert(money, '"
				+ String.valueOf(m.getEmzt_syucp())
				+ "'),  0, '"
				+ percentChange(m.getEmzt_housecpp())
				+ "', "
				+ " '"
				+ percentChange(m.getEmzt_houseopp())
				+ "',  convert(money,'"
				+ String.valueOf(m.getEmzt_housecp())
				+ "'),  convert(money,'"
				+ String.valueOf(m.getEmzt_houseop())
				+ "'),  "
				+ "'"
				+ m.getEmzt_sbsingle()
				+ "', '"
				+ m.getEmzt_housesingle()
				+ "', '"
				+ m.getEmzt_filesingle()
				+ "', '"
				+ m.getEmzt_rsingle()
				+ "', "
				+ " convert(money,'"
				+ String.valueOf(m.getEmzt_yltotal())
				+ "'),  convert(money,'"
				+ String.valueOf(m.getEmzt_syetotal())
				+ "'),  convert(money,'"
				+ String.valueOf(m.getEmzt_gstotal())
				+ "'), "
				+ " convert(money,'"
				+ String.valueOf(m.getEmzt_jltotal())
				+ "'),  convert(money,'"
				+ String.valueOf(m.getEmzt_syutotal())
				+ "'), '"
				+ m.getEmzt_wtgid()
				+ "', "
				+ "  '"
				+ m.getEmzt_tel()
				+ "', '"
				+ m.getEmzt_wtcid()
				+ "', '"
				+ m.getEmzt_outreason()
				+ "',  '"
				+ m.getEmzt_sex()
				+ "', case when '"
				+ m.getEmzt_iffeefile()
				+ "'='TRUE' then '1' when '"
				+ m.getEmzt_iffeefile()
				+ "'='true' then '1' when '"
				+ m.getEmzt_iffeefile()
				+ "'='FALSE' then '0' when '"
				+ m.getEmzt_iffeefile()
				+ "'='false' then '0' else '"
				+ m.getEmzt_iffeefile()
				+ "' end"
				+ " where "
				+ String.valueOf(m.getEmzt_zytid())
				+ " not in(select emzt_zytid from emzyt with(nolock) where emzt_state in(0,10,2,1,4,5,6,7,8,9) and ownmonth="
				+ String.valueOf(m.getOwnmonth()) + ")";
		// System.out.println(sql);
		try {

			// conn.execQuery(sql);
			// result = 0;
			// 调用上传文件后的更新方法
			// updateUploadFile(m);
			// updateUploadFileBJ(m);
			CallableStatement c = conn.getcall("EmZYT_Add_P_lsb(?,?,?)");
			c.setString(1, sql);
			c.setString(2, m.getEmzt_zytid());
			c.registerOutParameter(3, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(3);
		} catch (Exception e) {
			e.printStackTrace();
			result = 1;
		}

		return result;
	}

	// 插入智翼通反馈文件的数据
	public int uploadFeedbackByModel(EmZYTFeedbackModel m) {
		int result;

		String sql = "insert into EmZYTFeedback (ezfb_billmonth,ezfb_zytid,ezfb_listid,ezfb_gid,ezfb_name,ezfb_cid,ezfb_company,ezfb_idcardclass,ezfb_idcard,ezfb_class,ezfb_hbstart,ezfb_hbstop,ezfb_hbclass,ezfb_yl_cradix,ezfb_yl_oradix,ezfb_ylstart,ezfb_ylstop,ezfb_ylcity,ezfb_house_cradix,ezfb_house_oradix,ezfb_housecpp,ezfb_houseopp,ezfb_housestart,ezfb_housestop,ezfb_housecity,ezfb_compactstart,ezfb_compactstop,ezfb_feestart,ezfb_feestop,ezfb_filestart,ezfb_filestop,ezfb_elsestart,ezfb_elsestop,ezfb_cremark,ezfb_cremark_class,ezfb_cremark_content,ezfb_zytgid,ezfb_zytcid,ezfb_ct_gid,ezfb_ct_cid,ezfb_city,ezfb_submittime,ezfb_ylop_pm,ezfb_ylcp_pm,ezfb_houseop_pm,ezfb_housecp_pm,ezfb_feetotal,ezfb_sb_remark,ezfb_house_remark,ezfb_addname,ezfb_addfilename,ezfb_wtremark,ezfb_qz_fee,ezfb_qz_housecp,ezfb_qz_houseop,ezfb_qz_sbcp,ezfb_qz_sbop,ezfb_qz_file,ezfb_qz_product,ezfb_wt_agency) "
				+ "values('"
				+ m.getEzfb_billmonth()
				+ "','"
				+ m.getEzfb_zytid()
				+ "','"
				+ m.getEzfb_listid()
				+ "','"
				+ m.getEzfb_gid()
				+ "','"
				+ m.getEzfb_name()
				+ "','"
				+ m.getEzfb_cid()
				+ "','"
				+ m.getEzfb_company()
				+ "','"
				+ m.getEzfb_idcardclass()
				+ "','"
				+ m.getEzfb_idcard()
				+ "','"
				+ m.getEzfb_class()
				+ "','"
				+ m.getEzfb_hbstart()
				+ "','"
				+ m.getEzfb_hbstop()
				+ "','"
				+ m.getEzfb_hbclass()
				+ "','"
				+ m.getEzfb_yl_cradix()
				+ "','"
				+ m.getEzfb_yl_oradix()
				+ "','"
				+ m.getEzfb_ylstart()
				+ "','"
				+ m.getEzfb_ylstop()
				+ "','"
				+ m.getEzfb_ylcity()
				+ "','"
				+ m.getEzfb_house_cradix()
				+ "','"
				+ m.getEzfb_house_oradix()
				+ "','"
				+ m.getEzfb_housecpp()
				+ "','"
				+ m.getEzfb_houseopp()
				+ "','"
				+ m.getEzfb_housestart()
				+ "','"
				+ m.getEzfb_housestop()
				+ "','"
				+ m.getEzfb_housecity()
				+ "','"
				+ m.getEzfb_compactstart()
				+ "','"
				+ m.getEzfb_compactstop()
				+ "','"
				+ m.getEzfb_feestart()
				+ "','"
				+ m.getEzfb_feestop()
				+ "','"
				+ m.getEzfb_filestart()
				+ "','"
				+ m.getEzfb_filestop()
				+ "','"
				+ m.getEzfb_elsestart()
				+ "','"
				+ m.getEzfb_elsestop()
				+ "','"
				+ m.getEzfb_cremark()
				+ "','"
				+ m.getEzfb_cremark_class()
				+ "','"
				+ m.getEzfb_cremark_content()
				+ "','"
				+ m.getEzfb_zytgid()
				+ "','"
				+ m.getEzfb_zytcid()
				+ "','"
				+ m.getEzfb_ct_gid()
				+ "','"
				+ m.getEzfb_ct_cid()
				+ "','"
				+ m.getEzfb_city()
				+ "','"
				+ m.getEzfb_submittime()
				+ "','"
				+ m.getEzfb_ylop_pm()
				+ "','"
				+ m.getEzfb_ylcp_pm()
				+ "','"
				+ m.getEzfb_houseop_pm()
				+ "','"
				+ m.getEzfb_housecp_pm()
				+ "','"
				+ m.getEzfb_feetotal()
				+ "','"
				+ m.getEzfb_sb_remark()
				+ "','"
				+ m.getEzfb_house_remark()
				+ "','"
				+ m.getEzfb_addname()
				+ "','"
				+ m.getEzfb_addfilename()
				+ "','"
				+ m.getEzfb_wtremark()
				+ "','"
				+ m.getEzfb_qz_fee()
				+ "','"
				+ m.getEzfb_qz_housecp()
				+ "','"
				+ m.getEzfb_qz_houseop()
				+ "','"
				+ m.getEzfb_qz_sbcp()
				+ "','"
				+ m.getEzfb_qz_sbop()
				+ "','"
				+ m.getEzfb_qz_file()
				+ "','"
				+ m.getEzfb_qz_product() + "','" + m.getEzfb_wt_agency() + "')";
		try {
			conn.execQuery(sql);
			result = 0;
		} catch (Exception e) {
			e.printStackTrace();
			result = 1;
		}

		return result;
	}

	// 插入智翼通反馈文件的数据
	public int uploadFeedbackExcel(String sheetname, String filename,
			String username, String fileAllname) {
		int result;

		String sql = "insert into EmZYTFeedback (ezfb_billmonth,ezfb_zytid,ezfb_listid,ezfb_gid,ezfb_name,ezfb_cid,ezfb_company,ezfb_idcardclass,ezfb_idcard,ezfb_class,ezfb_hbstart,ezfb_hbstop,ezfb_hbclass,ezfb_yl_cradix,ezfb_yl_oradix,ezfb_ylstart,ezfb_ylstop,ezfb_ylcity,ezfb_house_cradix,ezfb_house_oradix,ezfb_housecpp,ezfb_houseopp,ezfb_housestart,ezfb_housestop,ezfb_housecity,ezfb_compactstart,ezfb_compactstop,ezfb_feestart,ezfb_feestop,ezfb_filestart,ezfb_filestop,ezfb_elsestart,ezfb_elsestop,ezfb_cremark,ezfb_cremark_class,ezfb_cremark_content,ezfb_zytgid,ezfb_zytcid,ezfb_ct_gid,ezfb_ct_cid,ezfb_city,ezfb_submittime,ezfb_ylop_pm,ezfb_ylcp_pm,ezfb_houseop_pm,ezfb_housecp_pm,ezfb_feetotal,ezfb_sb_remark,ezfb_house_remark,ezfb_addname,ezfb_addfilename) select [账单月份],[序号],[委托任务单序号],[雇员编号],[雇员姓名],[公司编号],[公司名称],[证件类型],[证件号码],[委托类型],[合并执行年月],[合并结束年月],[合并险种],[执行养老企业基数],[执行养老个人基数],[执行养老保险执行年月],[执行养老保险结束年月],[执行养老执行城市序号],[公积金企业基数],[公积金个人基数],[公积金企业比例],[公积金个人比例],[公积金执行年月],[公积金结束年月],[执行公积金执行城市序号],[执行服务产品执行年月],[执行服务产品结束年月],[执行服务费执行年月],[执行服务费结束年月],[执行档案保管费执行年月],[执行档案保管费结束年月],[执行额外费用执行年月],[执行额外费用结束年月],[受托方备注],[受托方备注类型],[受托方备注内容],[智翼通雇员编号],[智翼通公司编号],[委托方雇员编号],[委托方公司编号],[执行城市],[委托方提交日期],[执行养老个人收款方式],[执行养老企业收款方式],[执行公积金个人收款方式],[执行公积金企业收款方式],[执行管理费总计],[社保标准备注],[公积金标准备注],'"
				+ username
				+ "','"
				+ filename
				+ "' from opendatasource ('Microsoft.ACE.OLEDB.12.0','data source="
				+ fileAllname
				+ ";Extended properties=excel 5.0;')...["
				+ sheetname + "$] where [序号] is not null";
		try {

			conn.execQuery(sql);
			result = 0;

			EmZYTModel m = new EmZYTModel();
			m.setEmzt_filename(filename);

		} catch (Exception e) {
			e.printStackTrace();
			result = 1;
		}

		return result;
	}

	// 更新反馈数据
	public int upFeedbackData(String str) {
		int result;
		String sql;
		try {
			// 更新非终止数据
			sql = "UPDATE EmZYTFeedback SET EmZYTFeedback.ezfb_hbstartBMS=CONVERT(varchar(10),EmZYT.emzt_jlstartBMS,120),EmZYTFeedback.ezfb_ylstartBMS=CONVERT(varchar(10),EmZYT.emzt_ylstartBMS,120),EmZYTFeedback.ezfb_housestartBMS=CONVERT(varchar(10),EmZYT.emzt_housestartBMS,120),EmZYTFeedback.ezfb_compactstartBMS=CONVERT(varchar(10),EmZYT.emzt_ylstartBMS,120),EmZYTFeedback.ezfb_feestartBMS=CONVERT(varchar(10),EmZYT.emzt_ylstartBMS,120),EmZYTFeedback.ezfb_filestartBMS=CONVERT(varchar(10),EmZYT.emzt_ylstartBMS,120),EmZYTFeedback.ezfb_elsestartBMS=CONVERT(varchar(10),EmZYT.emzt_ylstartBMS,120),EmZYTFeedback.ezfb_jlradixBMS=EmZYT.emzt_jlradixBMS,EmZYTFeedback.ezfb_ylradixBMS=EmZYT.emzt_ylradixBMS,EmZYTFeedback.ezfb_houseradixBMS=EmZYT.emzt_houseradixBMS FROM EmZYTFeedback,(SELECT * FROM EmZYT WHERE emzt_state=1 AND id IN("
					+ str
					+ "))EmZYT WHERE EmZYTFeedback.ezfb_zytid=EmZYT.emzt_zytid AND EmZYTFeedback.ezfb_class NOT IN('终止') AND EmZYTFeedback.id IS NOT NULL";
			conn.execQuery(sql);

			// 更新终止数据
			sql = "UPDATE EmZYTFeedback SET EmZYTFeedback.ezfb_hbstopBMS=CONVERT(varchar(10),EmZYT.emzt_jlstopBMS,120),EmZYTFeedback.ezfb_ylstopBMS=CONVERT(varchar(10),EmZYT.emzt_ylstopBMS,120),EmZYTFeedback.ezfb_housestopBMS=CONVERT(varchar(10),EmZYT.emzt_housestopBMS,120),EmZYTFeedback.ezfb_compactstopBMS=CONVERT(varchar(10),EmZYT.emzt_ylstopBMS,120),EmZYTFeedback.ezfb_feestopBMS=CONVERT(varchar(10),EmZYT.emzt_ylstopBMS,120),EmZYTFeedback.ezfb_filestopBMS=CONVERT(varchar(10),EmZYT.emzt_ylstopBMS,120),EmZYTFeedback.ezfb_elsestopBMS=CONVERT(varchar(10),EmZYT.emzt_ylstopBMS,120) FROM EmZYTFeedback,(SELECT * FROM EmZYT WHERE emzt_state=1 AND id IN("
					+ str
					+ "))EmZYT WHERE EmZYTFeedback.ezfb_zytid=EmZYT.emzt_zytid AND EmZYTFeedback.ezfb_class='终止' AND EmZYTFeedback.id IS NOT NULL";
			conn.execQuery(sql);

			// 更新非终止数据状态为“委托成功”
			// 养老
			sql = "UPDATE EmZYTFeedback SET EmZYTFeedback.ezfb_billmonth=LEFT(CONVERT(varchar(6),EmZYT.ownmonth),4)+'.'+RIGHT(CONVERT(varchar(6),EmZYT.ownmonth),2),EmZYTFeedback.ezfb_ylstate='委托成功',EmZYTFeedback.ezfb_compactstate='委托成功',EmZYTFeedback.ezfb_feestate='委托成功',EmZYTFeedback.ezfb_filestate='委托成功',EmZYTFeedback.ezfb_elsestate='委托成功' FROM EmZYTFeedback,(SELECT * FROM EmZYT WHERE emzt_state=1 AND id IN("
					+ str
					+ "))EmZYT WHERE EmZYTFeedback.ezfb_zytid=EmZYT.emzt_zytid AND EmZYTFeedback.ezfb_ylstartBMS IS NOT NULL AND EmZYTFeedback.ezfb_ylstartBMS<>'' AND EmZYTFeedback.ezfb_class<>'终止' AND EmZYTFeedback.id IS NOT NULL";
			conn.execQuery(sql);

			// 合并
			sql = "UPDATE EmZYTFeedback SET EmZYTFeedback.ezfb_billmonth=LEFT(CONVERT(varchar(6),EmZYT.ownmonth),4)+'.'+RIGHT(CONVERT(varchar(6),EmZYT.ownmonth),2),EmZYTFeedback.ezfb_hbstate='委托成功' FROM EmZYTFeedback,(SELECT * FROM EmZYT WHERE emzt_state=1 AND id IN("
					+ str
					+ "))EmZYT WHERE EmZYTFeedback.ezfb_zytid=EmZYT.emzt_zytid AND EmZYTFeedback.ezfb_hbstartBMS IS NOT NULL AND EmZYTFeedback.ezfb_hbstartBMS<>'' AND EmZYTFeedback.ezfb_class<>'终止' AND EmZYTFeedback.id IS NOT NULL";
			conn.execQuery(sql);

			// 公积金
			sql = "UPDATE EmZYTFeedback SET EmZYTFeedback.ezfb_billmonth=LEFT(CONVERT(varchar(6),EmZYT.ownmonth),4)+'.'+RIGHT(CONVERT(varchar(6),EmZYT.ownmonth),2),EmZYTFeedback.ezfb_housestate='委托成功' FROM EmZYTFeedback,(SELECT * FROM EmZYT WHERE emzt_state=1 AND id IN("
					+ str
					+ "))EmZYT WHERE EmZYTFeedback.ezfb_zytid=EmZYT.emzt_zytid AND EmZYTFeedback.ezfb_class<>'终止' AND EmZYTFeedback.ezfb_housestartBMS IS NOT NULL AND EmZYTFeedback.ezfb_housestartBMS<>'' AND EmZYTFeedback.id IS NOT NULL";
			conn.execQuery(sql);

			// 更新终止数据状态为“委托成功”
			// 养老
			sql = "UPDATE EmZYTFeedback SET EmZYTFeedback.ezfb_billmonth=LEFT(CONVERT(varchar(6),EmZYT.ownmonth),4)+'.'+RIGHT(CONVERT(varchar(6),EmZYT.ownmonth),2),EmZYTFeedback.ezfb_ylstate='委托成功',EmZYTFeedback.ezfb_compactstate='委托成功',EmZYTFeedback.ezfb_feestate='委托成功',EmZYTFeedback.ezfb_filestate='委托成功',EmZYTFeedback.ezfb_elsestate='委托成功' FROM EmZYTFeedback,(SELECT * FROM EmZYT WHERE emzt_state=1 AND id IN("
					+ str
					+ "))EmZYT WHERE EmZYTFeedback.ezfb_zytid=EmZYT.emzt_zytid AND EmZYTFeedback.ezfb_ylstopBMS IS NOT NULL AND EmZYTFeedback.ezfb_ylstopBMS<>'' AND EmZYTFeedback.ezfb_class='终止' AND EmZYTFeedback.id IS NOT NULL";
			conn.execQuery(sql);

			// 合并
			sql = "UPDATE EmZYTFeedback SET EmZYTFeedback.ezfb_billmonth=LEFT(CONVERT(varchar(6),EmZYT.ownmonth),4)+'.'+RIGHT(CONVERT(varchar(6),EmZYT.ownmonth),2),EmZYTFeedback.ezfb_hbstate='委托成功' FROM EmZYTFeedback,(SELECT * FROM EmZYT WHERE emzt_state=1 AND id IN("
					+ str
					+ "))EmZYT WHERE EmZYTFeedback.ezfb_zytid=EmZYT.emzt_zytid AND EmZYTFeedback.ezfb_hbstopBMS IS NOT NULL AND EmZYTFeedback.ezfb_hbstopBMS<>'' AND EmZYTFeedback.ezfb_class='终止' AND EmZYTFeedback.id IS NOT NULL";
			conn.execQuery(sql);

			// 公积金
			sql = "UPDATE EmZYTFeedback SET EmZYTFeedback.ezfb_billmonth=LEFT(CONVERT(varchar(6),EmZYT.ownmonth),4)+'.'+RIGHT(CONVERT(varchar(6),EmZYT.ownmonth),2),EmZYTFeedback.ezfb_housestate='委托成功' FROM EmZYTFeedback,(SELECT * FROM EmZYT WHERE emzt_state=1 AND id IN("
					+ str
					+ "))EmZYT WHERE EmZYTFeedback.ezfb_zytid=EmZYT.emzt_zytid AND EmZYTFeedback.ezfb_class='终止' AND EmZYTFeedback.ezfb_housestopBMS IS NOT NULL AND EmZYTFeedback.ezfb_housestopBMS<>'' AND EmZYTFeedback.id IS NOT NULL";
			conn.execQuery(sql);

			result = 0;
		} catch (Exception e) {
			e.printStackTrace();
			result = 1;
		}

		return result;
	}

	// 生成反馈文件后更新反馈数据
	public int upFeedbackDataADF(String str, String username, String filename) {
		int result;
		String sql;
		try {
			// 更新EmZYTFeedback表数据
			sql = "UPDATE EmZYTFeedback SET ezfb_outname='" + username
					+ "',ezfb_outtime=GETDATE(),ezfb_outfilename='" + filename
					+ "' WHERE id IN(SELECT ezfb_id FROM (" + str + ")a)";
			conn.execQuery(sql);

			// 更新EmZYT表数据
			sql = "UPDATE EmZYT SET emzt_od_name='"
					+ username
					+ "',emzt_od_time=GETDATE(),emzt_outstate=2,emzt_outfilename='"
					+ filename
					+ "' WHERE emzt_outstate<>1 AND id IN(SELECT emzt_id FROM ("
					+ str + ")a)";
			conn.execQuery(sql);

			// 更新各反馈状态
			sql = "UPDATE EmZYT SET emzt_ylod_name='"
					+ username
					+ "',emzt_ylod_time=GETDATE(),emzt_yl_outstate=2 WHERE emzt_yl_outstate<>1 AND (emzt_ylstartBMS IS NOT NULL OR emzt_ylstopBMS IS NOT NULL) AND id IN(SELECT emzt_id FROM ("
					+ str + ")a)";
			conn.execQuery(sql);

			sql = "UPDATE EmZYT SET emzt_jlod_name='"
					+ username
					+ "',emzt_jlod_time=GETDATE(),emzt_jl_outstate=2 WHERE emzt_jl_outstate<>1 AND (emzt_jlstartBMS IS NOT NULL OR emzt_jlstopBMS IS NOT NULL) AND id IN(SELECT emzt_id FROM ("
					+ str + ")a)";
			conn.execQuery(sql);

			sql = "UPDATE EmZYT SET emzt_houseod_name='"
					+ username
					+ "',emzt_houseod_time=GETDATE(),emzt_house_outstate=2 WHERE emzt_house_outstate<>1 AND (emzt_housestartBMS IS NOT NULL OR emzt_housestopBMS IS NOT NULL) AND id IN(SELECT emzt_id FROM ("
					+ str + ")a)";
			conn.execQuery(sql);

			result = 0;
		} catch (Exception e) {
			e.printStackTrace();
			result = 1;
		}

		return result;
	}

	// 更新反馈数据为已反馈
	public int upFBStateSuccess(String str, String username) {
		int result;
		String sql;
		try {
			// 更新各反馈状态
			sql = "UPDATE EmZYT SET emzt_yloc_name='"
					+ username
					+ "',emzt_yloc_time=GETDATE(),emzt_yl_outstate=1 WHERE emzt_yl_outstate=2 AND (emzt_ylstartBMS IS NOT NULL OR emzt_ylstopBMS IS NOT NULL) AND id IN(SELECT id FROM ("
					+ str + ")a)";
			result = conn.execQuery(sql);
			sql = "UPDATE EmZYT SET emzt_jloc_name='"
					+ username
					+ "',emzt_jloc_time=GETDATE(),emzt_jl_outstate=1 WHERE emzt_jl_outstate=2 AND (emzt_jlstartBMS IS NOT NULL OR emzt_jlstopBMS IS NOT NULL) AND id IN(SELECT id FROM ("
					+ str + ")a)";
			result = conn.execQuery(sql);
			sql = "UPDATE EmZYT SET emzt_houseoc_name='"
					+ username
					+ "',emzt_houseoc_time=GETDATE(),emzt_house_outstate=1 WHERE emzt_house_outstate=2 AND (emzt_housestartBMS IS NOT NULL OR emzt_housestopBMS IS NOT NULL) AND id IN(SELECT id FROM ("
					+ str + ")a)";
			result = conn.execQuery(sql);
			sql = "UPDATE EmZYT SET emzt_oc_name='"
					+ username
					+ "',emzt_oc_time=GETDATE(),emzt_outstate=1 WHERE emzt_outstate=2 AND emzt_yl_outstate=1 AND emzt_jl_outstate=1 AND emzt_house_outstate=1 AND id IN(SELECT id FROM ("
					+ str + ")a)";
			result = conn.execQuery(sql);
			result = 0;
		} catch (Exception e) {
			e.printStackTrace();
			result = 1;
		}

		return result;
	}

	// 更新导入的委托单数据的公司编号和员工编号
	public int updateUploadFile(EmZYTModel m) {
		try {

			CallableStatement c = conn.getcall("EMZYT_P_lsb(?)");
			c.setString(1, m.getEmzt_filename());
			c.execute();
			return 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return 1;
		}
	}

	// 更新导入的委托单数据的补交费用字段
	public int updateUploadFileBJ(EmZYTModel m) {
		try {

			CallableStatement c = conn.getcall("EmZYT_BJFee_P_lsb(?,?)");
			c.setString(1, m.getEmzt_filename());
			c.setInt(2, m.getOwnmonth());
			c.execute();
			return 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return 1;
		}
	}

	// 更新EmZYT表流程ID
	public boolean upEmZYTTaprId(int tapr_id, int dataid) {
		String sql = "update EmZYT  set emzt_tapr_id=? where ID=?";
		PreparedStatement psmt = conn.getpre(sql);
		boolean b = false;
		try {
			psmt.setInt(1, tapr_id);
			psmt.setInt(2, dataid);
			psmt.execute();
			b = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return b;
	}

	// 更新EmZYT数据GID字段
	public int upEmZYTGid(EmZYTModel m) {
		try {
			CallableStatement c = conn.getcall("EmZYT_UpGid_P_lsb(?,?,?,?)");
			c.setInt(1, m.getId());
			c.setInt(2, m.getEmzt_state());
			c.setString(3, m.getEmzt_declarename());
			c.registerOutParameter(4, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(4);
		} catch (SQLException e) {
			e.printStackTrace();
			return 1;
		}
	}

	// 更新EmZYT新增数据状态
	public int upEmZYTGid(int emba_id) {
		String sql = "update EmZYT  set emzt_state=1 where emzt_state not in(10,11,3) and emzt_class='新增' and gid=?";
		PreparedStatement psmt = conn.getpre(sql);
		int b = 1;
		try {
			psmt.setInt(1, emba_id);
			psmt.execute();
			b = 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return b;
	}

	// 更新EmZYT数据emba_id
	public int upEmZYTembaId(EmZYTModel m) {
		try {
			CallableStatement c = conn
					.getcall("EmZYT_UpEmbaId_P_lsb(?,?,?,?,?,?,?)");
			c.setInt(1, m.getId());
			c.setInt(2, m.getEmba_id());
			c.setString(3, m.getEmzt_ylradix());
			c.setString(4, m.getEmzt_houseradix());
			c.setString(5, m.getHouse_cpp());
			c.setString(6, m.getHouse_opp());
			c.registerOutParameter(7, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(7);
		} catch (SQLException e) {
			e.printStackTrace();
			return 1;
		}
	}

	// 更新EmZYT数据emba_id
	public int upEmZYTFeedback(Integer ownmonth) {
		try {
			CallableStatement c = conn
					.getcall("EmZYTUpdateFeedback_P_lsb(?)");
			c.setInt(1, ownmonth);
			c.execute();
			return 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return 1;
		}
	}

	// 更新EmZYT数据状态
	public int upEmZYTState(EmZYTModel m) {
		String sql = "update EmZYT  set emzt_state=?,emzt_declarename=?,emzt_declaretime=getdate() where ID=?";
		PreparedStatement psmt = conn.getpre(sql);
		int b = 1;
		try {
			psmt.setInt(1, m.getEmzt_state());
			psmt.setString(2, m.getEmzt_declarename());
			psmt.setInt(3, m.getId());
			psmt.execute();
			b = 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return b;
	}

	// 更新EmZYT核对状态
	public int upEmZYTChkState(EmZYTModel m) {
		String sql = "update EmZYT  set emzt_chkstate=? where ID=?";
		PreparedStatement psmt = conn.getpre(sql);
		int b = 1;
		try {
			psmt.setInt(1, m.getEmzt_chkstate());
			psmt.setInt(2, m.getId());
			psmt.execute();
			b = 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return b;
	}

	// 更新EmZYT调整单
	public int upAdjust(EmZYTModel m) {
		String sql = "UPDATE EmZYT SET emzt_state=2,emzt_declarename=?,emzt_declaretime=getdate() WHERE ID=?";
		PreparedStatement psmt = conn.getpre(sql);
		int b = 1;
		try {
			// psmt.setString(1, m.getEmzt_adtype());
			psmt.setString(1, m.getEmzt_declarename());
			psmt.setInt(2, m.getId());
			psmt.execute();
			b = 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return b;
	}

	// 更新EmZYT委托事件
	public int upEmZYTAdType(EmZYTModel m) {
		String str = "";
		String sql = "UPDATE EmZYT SET emzt_class=? WHERE ID=?";
		PreparedStatement psmt = conn.getpre(sql);
		int b = 1;
		try {
			psmt.setString(1, m.getEmzt_class());
			psmt.setInt(2, m.getId());
			psmt.execute();
			b = 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return b;
	}

	// 更新EmZYT所属月份
	public int upEmZYTOwnmonth(EmZYTModel m) {
		String str = "";
		String sql = "UPDATE EmZYT SET ownmonth=? WHERE ID=?";
		PreparedStatement psmt = conn.getpre(sql);
		int b = 1;
		try {
			psmt.setInt(1, m.getOwnmonth());
			psmt.setInt(2, m.getId());
			psmt.execute();
			b = 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return b;
	}

	// 补充编号
	public int upEmZYTCidGid(EmZYTModel m) {
		try {

			CallableStatement c = conn
					.getcall("EmZYT_UpCidGid_P_lsb(?,?,?,?,?,?,?,?,?)");
			c.setInt(1, m.getId());
			c.setInt(2, m.getCid());
			c.setInt(3, m.getGid());
			c.setString(4, m.getEmzt_name());
			c.setString(5, m.getEmzt_idcard());
			c.setString(6, m.getEmzt_mobile());
			c.setString(7, m.getEmzt_company());
			c.setString(8, m.getEmzt_addname());
			c.registerOutParameter(9, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(9);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	// 选择报价单
	public int addEmZYTCoGlist(int emzt_id, String intime, String coliIdList,
			String contactType, String addname) {
		try {
			CallableStatement c = conn
					.getcall("EmZYTCoGlistAdd_P_lsb(?,?,?,?,?,?)");
			c.setInt(1, emzt_id);
			c.setString(2, intime);
			c.setString(3, coliIdList);
			c.setString(4, contactType);
			c.setString(5, addname);
			c.registerOutParameter(6, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(6);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	public int upEmZYTContact(EmZYTModel m) {
		try {
			CallableStatement c = conn
					.getcall("EmZYT_UpContact_P_lsb(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			c.setInt(1, m.getId());
			c.setString(2, m.getEmzt_t_name());
			c.setString(3, m.getEmzt_t_idcard());
			c.setString(4, m.getEmzt_hjadd());
			c.setString(5, m.getEmzt_education());
			c.setString(6, m.getEmzt_folk());
			c.setString(7, m.getEmzt_email());
			c.setString(8, m.getEmzt_ifshebao());
			c.setString(9, m.getEmzt_computerid());
			c.setString(10, m.getEmzt_hand());
			c.setString(11, m.getEmzt_ifsbcard());
			c.setString(12, m.getEmzt_sbc_notice());
			c.setString(13, m.getEmzt_ifhouse());
			c.setString(14, m.getEmzt_houseid());
			c.setString(15, m.getEmzt_ifhouseseal());
			c.setString(16, m.getEmzt_marital());
			c.setString(17, m.getEmzt_m_name());
			c.setString(18, m.getEmzt_m_idcard());
			c.setString(19, m.getEmzt_title());
			c.setString(20, m.getEmzt_fileplace());
			c.setString(21, m.getEmzt_ofileplace());
			c.setString(22, m.getEmzt_iffileservice());
			c.setString(23, m.getEmzt_ifda());
			c.setString(24, m.getEmzt_iffilechange());
			c.setString(25, m.getEmzt_nifc_reason());
			c.setString(26, m.getEmzt_ifowed());
			c.setInt(27, m.getEmzt_fileendmonth());
			c.setString(28, m.getEmzt_ifrc());
			c.setInt(29, m.getEmzt_datastate());
			c.setString(30, m.getEmzt_data_notice());
			c.setString(31, m.getEmzt_r_record());
			c.setInt(32, m.getEmzt_contactstate());
			c.setInt(33, m.getEmzt_state());
			c.registerOutParameter(34, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(34);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	// 服务费调整
	public int upEmZYTFeeAdjust(EmZYTModel m) {
		String sql = "update EmZYT  set emzt_ylstartBMS=?,emzt_jlstartBMS=?,emzt_housestartBMS=?,emzt_state=?,emzt_declarename=?,emzt_declaretime=getdate() where emzt_state=10 AND ID=?";
		PreparedStatement psmt = conn.getpre(sql);
		int b = 1;
		try {
			psmt.setString(1, m.getEmzt_ylstartBMS());
			psmt.setString(2, m.getEmzt_jlstartBMS());
			psmt.setString(3, m.getEmzt_housestartBMS());
			psmt.setInt(4, m.getEmzt_state());
			psmt.setString(5, m.getEmzt_declarename());
			psmt.setInt(6, m.getId());
			psmt.execute();
			b = 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return b;
	}

	// 新增"生成反馈文件"记录
	public int addEmZYTFeedbackFile(EmZYTFeedbackFileModel m) {
		String sql = "INSERT INTO EmZYTFeedbackFile(ezff_filename,ezff_fileurl,ezff_addname) VALUES(?,?,?)";

		PreparedStatement psmt = conn.getpre(sql);
		int b = 1;
		try {
			psmt.setString(1, m.getEzff_filename());
			psmt.setString(2, m.getEzff_fileurl());
			psmt.setString(3, m.getEzff_addname());
			psmt.execute();
			b = 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return b;
	}

	// 百分比转小数
	public String percentChange(String num) {
		String percent;

		try {
			percent = String.valueOf(new Float(num.substring(0,
					num.indexOf("%"))) / 100);
		} catch (Exception e) {
			percent = "";
		}

		return percent;
	}
}
