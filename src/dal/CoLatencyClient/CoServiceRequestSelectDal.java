package dal.CoLatencyClient;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.sun.rmi.rmid.ExecPermission;

import B.DB;
import Conn.dbconn;
import Model.CoBaseModel;
import Model.CoLatencyClientModel;
import Model.CoServiceRequestModel;
import Model.EmArchiveDatumModel;

public class CoServiceRequestSelectDal {
	// 根据cid查询公司的合同信息表id
	public Integer getCocoId(Integer cid) {
		Integer cocoId = null;
		dbconn db = new dbconn();
		ResultSet rs = null;
		String sql = "select * from CoCompact where cid=" + cid;
		try {
			rs = db.GRS(sql);
			while (rs.next()) {
				cocoId = rs.getInt("coco_id");
			}
		} catch (Exception e) {

		}
		return cocoId;
	}

	// 获取公司信息
	public List<CoBaseModel> getCobaseInfo(String str) {
		List<CoBaseModel> list = new ArrayList<CoBaseModel>();
		dbconn db = new dbconn();
		String sql = "select convert(varchar(10),coba_addtime,120) as coba_addtime,* from cobase where 1=1"
				+ str;
		try {
			list = db.find(sql, CoBaseModel.class,
					dbconn.parseSmap(CoBaseModel.class));
		} catch (Exception e) {

		}
		System.out.println("num=" + list.size());
		return list;
	}

	// 根据传入的查询条件查询公司,查询条件有cid请表明确cid所在的表
	public List<CoBaseModel> getCobaseInfoList(String sql) {
		ResultSet rs = null;
		List<CoBaseModel> cobaseinfo = new ArrayList<CoBaseModel>();
		if (!cobaseinfo.isEmpty())
			cobaseinfo.clear();
		try {
			dbconn db = new dbconn();
			String sqls = "select * from CoBase where 1=1 " + sql;
			sqls = sqls + " order by coba_servicestate desc,cid desc";

			rs = db.GRS(sqls);
			while (rs.next()) {
				CoBaseModel model = new CoBaseModel();
				model.setCid(rs.getInt("cid"));
				model.setCoba_spell(rs.getString("coba_spell"));// 公司全称首字母
				model.setCoba_company(rs.getString("coba_company"));// 公司全称
				model.setCoba_shortspell(rs.getString("coba_shortspell"));// 简称首字母
				model.setCoba_shortname(rs.getString("coba_shortname"));// 公司简称
				model.setCoba_namespell(rs.getString("coba_namespell"));// 全称每个文字的首字母
				model.setCoba_englishname(rs.getString("coba_englishname"));// 英文名
				model.setCoba_country(rs.getString("coba_country"));// 所在国家
				model.setCoba_setuptype(rs.getString("coba_setuptype"));// 公司类型
				model.setCoba_servicearea(rs.getString("coba_servicearea"));// 服务区域
				model.setCoba_servicestate(rs.getInt("coba_servicestate"));// 服务状态
				model.setCoba_stopreason(rs.getString("coba_stopreason"));// 解约原因
				model.setCoba_stopname(rs.getString("coba_stopname"));// 解约客服
				model.setCoba_stopremark(rs.getString("coba_stopremark"));// 解约备注
				model.setCoba_stoptime(rs.getString("coba_stoptime"));// 解约时间
				model.setCoba_industytype(rs.getString("coba_industytype"));// 所属行业
				model.setCoba_clientsize(rs.getString("coba_clientsize"));// 客户规模
				model.setCoba_compacttype(rs.getString("coba_compacttype"));// 合同类型
				if (rs.getInt("coba_vip") == 1) {
					model.setCoba_vip("vip");
				}
				model.setCoba_addname(rs.getString("coba_addname"));
				model.setCoba_address(rs.getString("Coba_address"));// 注册地址/办公地点
				model.setCoba_area(rs.getString("coba_area"));// 公司位置/所在区域
				model.setCoba_companymanager(rs
						.getString("coba_companymanager"));// 法定代表人
				model.setCoba_manageraddress(rs
						.getString("coba_manageraddress"));// 法定代表人地址
				model.setCoba_postcode(rs.getString("coba_postcode"));// 邮政编码
				model.setCoba_website(rs.getString("coba_website"));// 公司网址
				model.setCoba_clientsource(rs.getString("coba_clientsource"));// 客户来源
				model.setCoba_client(rs.getString("coba_client"));// 客服
				model.setCoba_manager(rs.getString("coba_manager"));// 客服经理
				model.setCoba_clientmanager(rs.getString("coba_clientmanager"));// 客服部门经理
				model.setCoba_hzqsr(rs.getString("coba_hzqsr"));// 合作起始日
				model.setCoba_remark(rs.getString("coba_remark"));// 备注
				model.setCoba_regagent(rs.getString("coba_regagent"));// 注册代理方
				model.setCoba_reg_fund(rs.getString("coba_reg_fund"));// 注册资金
				model.setCoba_certificate(rs.getString("coba_certificate"));// 证件类型
				model.setCoba_bregdeadline(rs.getString("coba_bregdeadline"));// 工商登记到期日
				model.setCoba_bregid(rs.getString("coba_bregid"));// 工商登记号
				model.setCoba_regexpires(rs.getString("coba_regexpires"));// 驻在期限
				model.setCoba_regtime(rs.getString("coba_regtime"));// 工商局登记时间
				model.setCoba_reglimit(rs.getString("coba_reglimit"));// 工商登记证有效期
				model.setCoba_organdeadline(rs.getString("coba_organdeadline"));// 组织机构到期日
				model.setCoba_orregtime(rs.getString("coba_orregtime"));// 组织机构代码登记日期
				model.setCoba_organcode(rs.getString("coba_organcode"));// 组织结构代码
				model.setCoba_ntbank(rs.getString("coba_ntbank"));// 国税开户银行
				model.setCoba_ntaccount(rs.getString("coba_ntaccount"));// 国税账号
				model.setCoba_ntid(rs.getString("coba_ntid"));// 国税深字号
				model.setCoba_ntregtime(rs.getString("coba_ntregtime"));// 国税登记日期
				model.setCoba_ntlimit(rs.getString("coba_ntlimit"));// 国税有效期
				model.setCoba_ntdeadline(rs.getString("coba_ntdeadline"));// 国税纳税有效期
				model.setCoba_ltregid(rs.getString("coba_ltregid"));// 地税登记号
				model.setCoba_ltregtime(rs.getString("coba_ltregtime"));// 地税登记时间
				model.setCoba_ltlimit(rs.getString("coba_ltlimit"));// 地税有效期
				model.setCoba_ltstate(rs.getString("coba_ltstate"));// 地税开户状态
				model.setCoba_ltid(rs.getString("coba_ltid"));// 地税电脑号
				model.setCoba_ltdeadline(rs.getString("coba_ltdeadline"));// 地税纳税期限
				model.setCoba_ltbank(rs.getString("coba_ltbank"));// 地税开户银行
				model.setCoba_ltaccount(rs.getString("coba_ltaccount"));// 地税账号
				model.setCoba_gtstate(rs.getString("coba_gtstate"));// 个税开户状态
				model.setCoba_gtbank(rs.getString("coba_gtbank"));// 个税开户行
				model.setCoba_gtacc(rs.getString("coba_gtacc"));// 个税纳税账号
				model.setCoba_gtto(rs.getString("coba_gtto"));// 所属税局
				model.setCoba_gtdeadline(rs.getString("coba_gtdeadline"));// 个税申报截止日
				model.setCoba_sistate(rs.getString("coba_sistate"));// 开户状态
				model.setCoba_siiilimit(rs.getString("coba_siiilimit"));// 工伤比例
				model.setCoba_sicoding(rs.getString("coba_sicoding"));// 单位编码
				model.setCoba_sibank(rs.getString("coba_sibank"));// 开户行
				model.setCoba_siaccount(rs.getString("coba_siaccount"));// 账户
				model.setCoba_sihospital(rs.getString("coba_sihospital"));// 社康医疗机构
				model.setCoba_sihosphone(rs.getString("coba_sihosphone"));// 社康电话
				model.setCoba_sihosadd(rs.getString("coba_sihosadd"));// 社康地址
				model.setCoba_dept(rs.getString("coba_dept"));
				model.setCoba_regremark(rs.getString("coba_regremark"));// 公司注册信息备注
				model.setCoba_addtime(rs.getString("coba_addtime"));// 公司信息添加时间
				model.setCoba_addname(rs.getString("coba_addname"));// 公司信息添加人
				model.setCoba_modtime(rs.getString("coba_modtime"));// 公司信息修改时间
				model.setCoba_modname(rs.getString("coba_modname"));// 公司信息修改人
				model.setCoba_shebaodeclare(rs.getString("coba_shebaodeclare"));
				model.setCoba_ufid(rs.getString("coba_ufid"));// 收款中用到的用友编号
				model.setCoba_ufid2(rs.getString("coba_ufid2"));// 社保和公积金台帐中用到的用友编号
				model.setCoba_ufclass(rs.getString("coba_ufclass"));
				model.setCoba_invoicerule(rs.getString("coba_invoicerule"));
				model.setCoba_invoiceaddress(rs
						.getString("coba_invoiceaddress"));// 发票邮寄地址
				model.setCoba_wtco(rs.getString("coba_wtco"));
				model.setCoba_regdataList(rs.getString("coba_regdataList"));// 注册信息-递交材料
				model.setCoba_assistant(rs.getString("coba_assistant"));// 客服文员
				model.setCoba_zytcid(rs.getString("coba_zytcid"));// 智翼通公司编号
				model.setCoba_zytwtcid(rs.getString("coba_zytwtcid"));// 智翼通委托公司编号
				model.setCoba_developer(rs.getString("coba_developer"));// 开发人员
				model.setCoba_gzautoemail(rs.getInt("coba_gzautoemail"));// 系统自动发送Email工资单(是or否)
				model.setCoba_sign(rs.getInt("coba_sign"));// 是否国内注册
				model.setCoba_invoice(rs.getString("coba_invoice"));
				model.setCoba_taxmonth(rs.getString("coba_taxmonth"));// 是否上月个税(个税报表月份)
				model.setCoba_gzaddname(rs.getString("coba_gzaddname"));// 薪酬负责人(添加人)
				model.setCoba_gzaudname(rs.getString("coba_gzaudname"));// 薪酬审核人
				model.setCoba_gzemailtype(rs.getInt("coba_gzemailtype"));// 判断电子工资单发放格式
				model.setCoba_emfi_paydate(rs.getString("coba_emfi_paydate"));// 付款通知发放时间
				model.setCoba_emfi_backdate(rs.getString("coba_emfi_backdate"));// 回款时间
				model.setCoba_gz_paydate(rs.getString("coba_gz_paydate"));// 工资发放时间
				model.setCoba_emailgz_paydate(rs
						.getString("coba_emailgz_paydate"));// 电子工资单发放时间
				model.setCoba_papergz_paydate(rs
						.getString("coba_papergz_paydate"));// 纸质工资单发放时间
				model.setCoba_emfics_backdate(rs
						.getString("coba_emfics_backdate"));// 薪酬回款时间
				model.setCoba_emfics_deldate(rs
						.getString("coba_emfics_deldate"));// 薪酬台账明细制作时间
				model.setCoba_emfics_paydate(rs
						.getString("coba_emfics_paydate"));// 薪酬付款通知发放时间
				model.setCoba_gzautoemaildays(rs.getInt("coba_gzautoemaildays"));// 工资发放后几天后自动发送Email工资单

				cobaseinfo.add(model);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cobaseinfo;
	}

	public List<String> getLoginlist() {
		List<String> list = new ArrayList<String>();
		dbconn db = new dbconn();
		ResultSet rs = null;
		// String
		// sql="select LEFT(log_spell,1)+log_name as log_name from Login a,department b where a.dep_id=b.dep_id and "+
		// "(b.dep_name='客户服务部' or b.dep_name='全国项目部') and log_inure=1 and log_name is not null and log_spell is not"
		// +
		// " null order by log_spell";

		String sql = "select LEFT(log_spell,1)+log_name as log_name from Login a,department b where a.dep_id=b.dep_id and "
				+ "  log_inure=1 and log_name is not null and log_spell is not"
				+ " null order by log_spell";
		try {
			rs = db.GRS(sql);
			while (rs.next()) {
				list.add(rs.getString("log_name"));
			}
		} catch (Exception e) {

		}
		return list;
	}

	public List<String> getLoginlistByCid(Integer cid) {
		List<String> list = new ArrayList<String>();
		dbconn db = new dbconn();
		ResultSet rs = null;
		String sql = " select distinct(log_name) log_name from View_loginrole where  dep_name=(select dep_name "
				+ "from department where dep_id=(select dep_id from Login where log_name=( "
				+ "select coba_manager from CoBase where CID="
				+ cid
				+ ")))  order by log_name";
		try {
			rs = db.GRS(sql);
			while (rs.next()) {
				list.add(rs.getString("log_name"));
			}
		} catch (Exception e) {

		}
		return list;
	}

	// 查询客服经理
	public List<String> getClientManagerlist(Integer cid) {
		List<String> list = new ArrayList<String>();
		dbconn db = new dbconn();
		ResultSet rs = null;
		// String
		// sql="select * from View_loginrole where rol_name like '%经理%' and dep_id in(2,6)";

		String sql = " select distinct(log_name) log_name from View_loginrole where (rol_name like '%经理%' or dep_name in('雇员人事部','财务部')) and"
				+ " dep_name=(select dep_name from department where dep_id=(select dep_id from Login "
				+ "where log_name=( select coba_manager from CoBase where CID="
				+ cid + "))) order by log_name";
		try {
			rs = db.GRS(sql);
			while (rs.next()) {
				list.add(rs.getString("log_name"));
			}
		} catch (Exception e) {

		}
		return list;
	}

	// 查询各部门经理
	public List<String> getManagerlist() {
		List<String> list = new ArrayList<String>();
		dbconn db = new dbconn();
		ResultSet rs = null;
		String sql = "select * from Login a, logingroup  b,role c where a.log_id=b.log_id "
				+ "and b.rol_id=c.rol_id and log_inure=1 and  ( rol_name like '%部门经理%' or rol_name like '%部门副经理%' or rol_name like '%副经理%')";
		try {
			rs = db.GRS(sql);
			while (rs.next()) {
				list.add(rs.getString("log_name"));
			}
		} catch (Exception e) {

		}
		return list;
	}

	// 获取财务部人员
	public List<String> getSalarylist() {
		List<String> list = new ArrayList<String>();
		dbconn db = new dbconn();
		ResultSet rs = null;
		String sql = "select LEFT(log_spell,1)+log_name as log_name from Login a " +
				" where dep_id=13 and log_inure=1 and log_name is not null and log_spell is not " +
				" null order by log_spell";
		try {
			rs = db.GRS(sql);
			while (rs.next()) {
				list.add(rs.getString("log_name"));
			}
		} catch (Exception e) {

		}
		return list;
	}

	// 获取服务要求表信息
	public List<CoServiceRequestModel> getRequestInfoList(String str) {//
		// 服务要求
		List<CoServiceRequestModel> list = new ArrayList<CoServiceRequestModel>();
		dbconn db = new dbconn();
		// String
		// sql="select convert(varchar(10),csqe_addtime,120) as csqe_addtime,* from CoServiceRequest where 1=1"+str;
		String sql = "select CAST(coba_emfi_backdate AS nvarchar(2))+'日' as emfi_backdate,"
				+ "CAST(coba_emfics_backdate AS nvarchar(2))+'日' as emfics_backdate,"
				+

				"CAST(coba_xctzzzsj AS nvarchar(2))+'日' as coba_xctzzzsjstr,"
				+ "CAST(coba_fktzffsj AS nvarchar(2))+'日' as coba_fktzffsjstr,"
				+ "CAST(coba_xchksj AS nvarchar(2))+'日' as coba_xchksjstr,"
				+

				"CAST(coba_emfi_senddate AS nvarchar(2))+'日' as coba_emfi_senddatestr,"
				+ "CAST(coba_papergz_paydate AS nvarchar(2))+'日' as papergz_paydate ,"
				+ "convert(varchar(10),csqe_addtime,120) as csqe_addtime, CAST(csqe_isenddate AS nvarchar(2))+'日' as isenddate,"
				+ "case csqe_taxde_date when 1 then '工资发放当月申报' when 2 then '工资发放次月申报' end taxde_date,"
				+ " CAST(csqe_gz_paydate AS nvarchar(2))+'日' as paydate,case csqe_sbtype when 1 then '非深户一档医保'"
				+ " when 2 then '非深户二档医保' when 3 then '委托外地缴纳' end as sbtype,case csqe_cardpay when 1 then '个人付'"
				+ " when 2 then '随付款' when 3 then '公司付' end as cardpay,case csqe_houseover when 1 then '有' when 2 then '不确定'"
				+ " when 3 then '无' end as houseover,case csqe_actype when 1 then '需要计算考勤' when 2 then '无需计算考勤' "
				+ " when 3 then '审核客户计算' end as actype,case csqe_report when 1 then '需要按部门汇总' when 2 then '不需要'"
				+ " when 3 then '不确定' when 4 then '需要特殊报表' end report,case csqe_taxctype when 1 then '我司计算明细扣缴' "
				+ "when 2 then '客户提供明细扣缴' when 3 then '仅发放税后金额' end as taxctype,case csqe_taxdetype when 1 then '委托我司大户申报'"
				+ " when 2 then '委托我司客户独立户申报' when 3 then '不委托' end taxdetype,case csqe_taxpay when 1 then '客户独立户扣个税无需我司代转'"
				+ " when 2 then '客户独立户扣个税需要我司代转' when 3 then '中智大户扣缴' when 4 then '无个税服务' end taxpay,case csqe_taxwt when 0 then '无'"
				+ " when 1 then '有' when 2 then '不确定' end as taxwt,case csqe_gzpayroll_type when 1 then '无需工资单' when 2 then 'E-mail工资单'"
				+ " when 3 then '密封工资单' when 4 then '网上中智工资单' end as gzpayroll_type,"
				+ " case csqe_dtdservice  when 0 then '无' when 1 then '有' else '' end dtdservice,"
				+ "case csqe_wt  when 0 then '无' when 1 then '有' else '' end wt,case csqe_fservice "
				+ "when 0 then '无' when 1 then '有' else '' end fservice,"
				+ " case csqe_houseover when 0 then '无' when 1 then '有' else '' end houseover,"
				+ " case csqe_gzpayroll_b when 0 then '是' when 1 then '否' else '' end gzpayroll_b,"
				+ " a.* from CoServiceRequest a left join CoBase b "
				+ " on a.cid=b.cid where 1=1 " + str;
		try {
			list = db.find(sql, CoServiceRequestModel.class,
					dbconn.parseSmap(CoServiceRequestModel.class));
		} catch (Exception e) {
		}
		return list;
	}

	// 查询公司全称是否已存在
	public boolean ifExist(String company) {
		boolean flag = false;
		Integer num = 0;
		String sql = "select count(*) num from cobase where coba_company='"
				+ company + "'";
		try {
			dbconn db = new dbconn();
			ResultSet rs = db.GRS(sql);
			while (rs.next()) {
				num = num + rs.getInt("num");
			}
		} catch (Exception e) {

		}
		if (num > 0) {
			flag = true;
		}
		return flag;
	}

	// 查询公司简称是否已存在
	public boolean ifExistShortName(String company) {
		boolean flag = false;
		Integer num = 0;
		String sql = "select count(*) num from cobase where coba_shortname='"
				+ company + "'";
		try {
			dbconn db = new dbconn();
			ResultSet rs = db.GRS(sql);
			while (rs.next()) {
				num = num + rs.getInt("num");
			}
		} catch (Exception e) {

		}
		if (num > 0) {
			flag = true;
		}
		return flag;
	}

	// 根据cid获取潜在客户信息
	public CoLatencyClientModel getCoLatencyClient(Integer cid) {
		CoLatencyClientModel model = new CoLatencyClientModel();
		String sql = "select * from CoLatencyClient where cid=" + cid;
		try {
			dbconn dbconn = new dbconn();
			ResultSet rs = dbconn.GRS(sql);
			while (rs.next()) {
				model.setCola_id(rs.getInt("cola_id"));
				model.setCid(cid);
			}
		} catch (Exception e) {

		}
		return model;
	}
}
