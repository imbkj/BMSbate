/**
 * @Classname CoBase_SelectDal
 * @ClassInfo 公司基本信息数据库查询类
 * @author 林少斌、陈耀家
 * @Date 2013-11-27
 */
package dal.CoBase;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.zkoss.zul.ListModelList;

import Conn.dbconn;
import Model.CoBaseModel;
import Model.CoCompactModel;
import Model.CoOfferModel;
import Model.EmHouseUpdateModel;
import Model.EmShebaoUpdateModel;
import Util.UserInfo;

public class CoBase_SelectDal extends dbconn {

	/**
	 * 根据cid查询公积金信息
	 * 
	 * @param cid
	 * @return
	 */
	public List<EmHouseUpdateModel> getGjjInfoByCid(int cid) {
		List<EmHouseUpdateModel> list = new ArrayList<EmHouseUpdateModel>();
		String sql = " select *,case emhu_ifstop when 0 then '在册' when 1 then '终止' when 2 then '核查失败' end state from emhouseupdate where cid=? ";
		PreparedStatement pstmt = getpre(sql);
		try {
			pstmt.setInt(1, cid);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				EmHouseUpdateModel m = new EmHouseUpdateModel();
				m.setGid(rs.getInt("gid"));
				m.setOwnmonth(rs.getInt("Ownmonth"));
				m.setEmhu_company(rs.getString("emhu_company"));
				m.setEmhu_companyid(rs.getString("emhu_companyid"));
				m.setEmhu_name(rs.getString("emhu_name"));
				m.setEmhu_idcard(rs.getString("emhu_idcard"));
				m.setEmhu_idcardclass(rs.getString("emhu_idcardclass"));
				m.setEmhu_hj(rs.getString("emhu_hj"));
				m.setEmhu_computerid(rs.getString("emhu_computerid"));
				m.setEmhu_mobile(rs.getString("emhu_mobile"));
				m.setEmhu_title(rs.getString("emhu_title"));
				m.setEmhu_wifename(rs.getString("emhu_wifename"));
				m.setEmhu_wifeidcard(rs.getString("emhu_wifeidcard"));
				m.setEmhu_degree(rs.getString("emhu_degree"));
				m.setEmhu_radix(rs.getDouble("emhu_radix"));
				m.setEmhu_bonus(rs.getBigDecimal("emhu_bonus"));
				m.setEmhu_cpp(rs.getDouble("emhu_cpp"));
				m.setEmhu_opp(rs.getDouble("emhu_opp"));
				m.setEmhu_cp(rs.getDouble("emhu_cp"));
				m.setEmhu_op(rs.getDouble("emhu_op"));
				m.setState(rs.getString("state"));
				list.add(m);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 根据cid查询公积金信息
	 * 
	 * @param cid
	 * @return
	 */
	public List<EmHouseUpdateModel> getGjjInfoByCid(int cid, int type) {
		List<EmHouseUpdateModel> list = new ArrayList<EmHouseUpdateModel>();
		String sql = " select *,case emhu_ifstop when 0 then '在册' when 1 then '终止' when 2 then '核查失败' "
				+ "end state from emhouseupdate where cid=?  AND emhu_single=? order by emhu_name";
		PreparedStatement pstmt = getpre(sql);
		try {
			pstmt.setInt(1, cid);
			pstmt.setInt(2, type);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				EmHouseUpdateModel m = new EmHouseUpdateModel();
				m.setGid(rs.getInt("gid"));
				m.setOwnmonth(rs.getInt("Ownmonth"));
				m.setEmhu_company(rs.getString("emhu_company"));
				m.setEmhu_companyid(rs.getString("emhu_companyid"));
				m.setEmhu_name(rs.getString("emhu_name"));
				m.setEmhu_idcard(rs.getString("emhu_idcard"));
				m.setEmhu_idcardclass(rs.getString("emhu_idcardclass"));
				m.setEmhu_hj(rs.getString("emhu_hj"));
				m.setEmhu_computerid(rs.getString("emhu_computerid"));
				m.setEmhu_mobile(rs.getString("emhu_mobile"));
				m.setEmhu_title(rs.getString("emhu_title"));
				m.setEmhu_wifename(rs.getString("emhu_wifename"));
				m.setEmhu_wifeidcard(rs.getString("emhu_wifeidcard"));
				m.setEmhu_degree(rs.getString("emhu_degree"));
				m.setEmhu_radix(rs.getDouble("emhu_radix"));
				m.setEmhu_bonus(rs.getBigDecimal("emhu_bonus"));
				m.setEmhu_cpp(rs.getDouble("emhu_cpp"));
				m.setEmhu_opp(rs.getDouble("emhu_opp"));
				m.setEmhu_cp(rs.getDouble("emhu_cp"));
				m.setEmhu_op(rs.getDouble("emhu_op"));
				m.setState(rs.getString("state"));
				list.add(m);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 根据cid查询公积金信息AllinOne
	 * 
	 * @param cid
	 * @return
	 */
	public List<EmHouseUpdateModel> getGjjInfoByCidAllinOne(int cid,
			Integer type) {
		List<EmHouseUpdateModel> list = new ArrayList<EmHouseUpdateModel>();

		String str = "";
		if (type != null && type != 3) {
			str = " and emhu_single=" + type;
		}

		String sql = " select eu.*,state=(case when ec.emhc_change is not null then ec.emhc_change when emhu_ifstop=0 then '正常在册' when emhu_ifstop=1 then '终止' when emhu_ifstop=2 then '核查失败' end)  from emhouseupdate eu left join (select * from emhousechange a where exists (select emhc_id from (select gid,cid,MAX(emhc_id)as emhc_id from emhousechange b where b.ownmonth=(select top 1 ownmonth from EmHouseUpdate) group by GID,CID)b where a.emhc_id=b.emhc_id))ec on eu.ownmonth=ec.ownmonth and eu.gid=ec.gid where eu.ownmonth=(select top 1 ownmonth from EmHouseUpdate) and eu.cid=?  "
				+ str + " order by emhu_name";
		PreparedStatement pstmt = getpre(sql);
		try {
			pstmt.setInt(1, cid);
			// pstmt.setInt(2, type);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				EmHouseUpdateModel m = new EmHouseUpdateModel();
				m.setGid(rs.getInt("gid"));
				m.setOwnmonth(rs.getInt("Ownmonth"));
				m.setEmhu_company(rs.getString("emhu_company"));
				m.setEmhu_companyid(rs.getString("emhu_companyid"));
				m.setEmhu_houseid(rs.getString("emhu_houseid"));
				m.setEmhu_name(rs.getString("emhu_name"));
				m.setEmhu_idcard(rs.getString("emhu_idcard"));
				m.setEmhu_idcardclass(rs.getString("emhu_idcardclass"));
				m.setEmhu_hj(rs.getString("emhu_hj"));
				m.setEmhu_computerid(rs.getString("emhu_computerid"));
				m.setEmhu_mobile(rs.getString("emhu_mobile"));
				m.setEmhu_title(rs.getString("emhu_title"));
				m.setEmhu_wifename(rs.getString("emhu_wifename"));
				m.setEmhu_wifeidcard(rs.getString("emhu_wifeidcard"));
				m.setEmhu_degree(rs.getString("emhu_degree"));
				m.setEmhu_radix(rs.getDouble("emhu_radix"));
				m.setEmhu_bonus(rs.getBigDecimal("emhu_bonus"));
				m.setEmhu_cpp(rs.getDouble("emhu_cpp"));
				m.setEmhu_opp(rs.getDouble("emhu_opp"));
				m.setEmhu_cp(rs.getDouble("emhu_cp"));
				m.setEmhu_op(rs.getDouble("emhu_op"));
				m.setState(rs.getString("state"));
				m.setEmhu_ifstop(rs.getInt("emhu_ifstop"));
				list.add(m);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 根据cid查询社保信息
	 * 
	 * @param cid
	 * @return
	 */
	public List<EmShebaoUpdateModel> getShebaoInfoByCid(int cid) {
		List<EmShebaoUpdateModel> list = new ArrayList<EmShebaoUpdateModel>();
		String sql = " select * from emshebaoupdate where cid=? ";
		PreparedStatement pstmt = getpre(sql);
		try {
			pstmt.setInt(1, cid);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				EmShebaoUpdateModel m = new EmShebaoUpdateModel();
				m.setGid(rs.getInt("gid"));
				m.setOwnmonth(rs.getInt("Ownmonth"));
				m.setEsiu_company(rs.getString("esiu_company"));
				m.setEsiu_name(rs.getString("esiu_name"));
				m.setEsiu_computerid(rs.getString("esiu_computerid"));
				m.setEsiu_idcard(rs.getString("esiu_idcard"));
				m.setEsiu_hj(rs.getString("esiu_hj"));
				m.setEsiu_radix(rs.getInt("esiu_radix"));
				m.setEsiu_yl(rs.getString("esiu_yl"));
				m.setEsiu_gs(rs.getString("esiu_gs"));
				m.setEsiu_sye(rs.getString("esiu_sye"));
				m.setEsiu_syu(rs.getString("esiu_syu"));
				m.setEsiu_yltype(rs.getString("esiu_yltype"));
				m.setEsiu_house(rs.getString("esiu_house"));
				m.setEsiu_ylop(rs.getBigDecimal("esiu_ylop"));
				m.setEsiu_ylcp(rs.getBigDecimal("esiu_ylcp"));
				m.setEsiu_jlop(rs.getBigDecimal("esiu_jlop"));
				m.setEsiu_jlcp(rs.getBigDecimal("esiu_jlcp"));
				m.setEsiu_syuop(rs.getBigDecimal("esiu_syuop"));
				m.setEsiu_syucp(rs.getBigDecimal("esiu_syucp"));
				m.setEsiu_syeop(rs.getBigDecimal("esiu_syeop"));
				m.setEsiu_syecp(rs.getBigDecimal("esiu_syecp"));
				m.setEsiu_gsop(rs.getBigDecimal("esiu_gsop"));
				m.setEsiu_gscp(rs.getBigDecimal("esiu_gscp"));
				m.setEsiu_houseop(rs.getBigDecimal("esiu_houseop"));
				m.setEsiu_housecp(rs.getBigDecimal("esiu_housecp"));
				m.setEsiu_totalop(rs.getBigDecimal("esiu_totalop"));
				m.setEsiu_totalcp(rs.getBigDecimal("esiu_totalcp"));
				list.add(m);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 根据cid查询社保信息
	 * 
	 * @param cid
	 * @return
	 */
	public List<EmShebaoUpdateModel> getShebaoInfoByCid(int cid, int type) {
		List<EmShebaoUpdateModel> list = new ArrayList<EmShebaoUpdateModel>();
		String sql = " select * from emshebaoupdate where cid=? and Esiu_single=?  order by esiu_name";
		PreparedStatement pstmt = getpre(sql);
		try {
			pstmt.setInt(1, cid);
			pstmt.setInt(2, type);

			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				EmShebaoUpdateModel m = new EmShebaoUpdateModel();
				m.setGid(rs.getInt("gid"));
				m.setOwnmonth(rs.getInt("Ownmonth"));
				m.setEsiu_company(rs.getString("esiu_company"));
				m.setEsiu_name(rs.getString("esiu_name"));
				m.setEsiu_computerid(rs.getString("esiu_computerid"));
				m.setEsiu_idcard(rs.getString("esiu_idcard"));
				m.setEsiu_hj(rs.getString("esiu_hj"));
				m.setEsiu_radix(rs.getInt("esiu_radix"));
				m.setEsiu_yl(rs.getString("esiu_yl"));
				m.setEsiu_gs(rs.getString("esiu_gs"));
				m.setEsiu_sye(rs.getString("esiu_sye"));
				m.setEsiu_syu(rs.getString("esiu_syu"));
				m.setEsiu_yltype(rs.getString("esiu_yltype"));
				m.setEsiu_house(rs.getString("esiu_house"));
				m.setEsiu_ylop(rs.getBigDecimal("esiu_ylop"));
				m.setEsiu_ylcp(rs.getBigDecimal("esiu_ylcp"));
				m.setEsiu_jlop(rs.getBigDecimal("esiu_jlop"));
				m.setEsiu_jlcp(rs.getBigDecimal("esiu_jlcp"));
				m.setEsiu_syuop(rs.getBigDecimal("esiu_syuop"));
				m.setEsiu_syucp(rs.getBigDecimal("esiu_syucp"));
				m.setEsiu_syeop(rs.getBigDecimal("esiu_syeop"));
				m.setEsiu_syecp(rs.getBigDecimal("esiu_syecp"));
				m.setEsiu_gsop(rs.getBigDecimal("esiu_gsop"));
				m.setEsiu_gscp(rs.getBigDecimal("esiu_gscp"));
				m.setEsiu_houseop(rs.getBigDecimal("esiu_houseop"));
				m.setEsiu_housecp(rs.getBigDecimal("esiu_housecp"));
				m.setEsiu_totalop(rs.getBigDecimal("esiu_totalop"));
				m.setEsiu_totalcp(rs.getBigDecimal("esiu_totalcp"));
				m.setEsiu_ifstop(rs.getInt("esiu_ifstop"));
				list.add(m);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 根据cid查询社保信息AllinOne
	 * 
	 * @param cid
	 * @return
	 */
	public List<EmShebaoUpdateModel> getShebaoInfoByCidAllinOne(int cid,
			Integer type) {
		List<EmShebaoUpdateModel> list = new ArrayList<EmShebaoUpdateModel>();
		String str = "";
		if (type != null && type != 3) {
			str = " and Esiu_single=" + type;
		}
		String sql = " select es.*,state=(case when ec.emsc_change is not null then ec.emsc_change when efc.emsc_change is not null then efc.emsc_change when esiu_ifstop=2 then '被调走' when esiu_ifstop=3 then '新增调入退回' when esiu_ifstop=1 then '不在册' else '正常在册' end) from EmShebaoUpdate es "
				+ " left join (select * from emshebaochange a where exists (select id from (select gid,cid,MAX(id)as id from EmShebaoChange where emsc_ifdeclare<>3 and ownmonth=(select top 1 ownmonth from EmShebaoUpdate) group by GID,CID)b where a.ID=b.id)) ec on es.Ownmonth=ec.Ownmonth and es.GID=ec.GID"
				+ " left join (select * from EmShebaoChangeForeigner a where exists (select id from (select gid,cid,MAX(id)as id from EmShebaoChangeForeigner where emsc_ifdeclare<>3 and ownmonth=(select top 1 ownmonth from EmShebaoUpdate) group by GID,CID)b where a.ID=b.id)) efc on es.Ownmonth=efc.Ownmonth and es.GID=efc.GID "
				+ " where es.ownmonth=(select top 1 ownmonth from EmShebaoUpdate) and es.cid=? "
				+ str + "  order by esiu_name";
		PreparedStatement pstmt = getpre(sql);
		try {
			pstmt.setInt(1, cid);
			// pstmt.setInt(2, type);

			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				EmShebaoUpdateModel m = new EmShebaoUpdateModel();
				m.setGid(rs.getInt("gid"));
				m.setOwnmonth(rs.getInt("Ownmonth"));
				m.setEsiu_company(rs.getString("esiu_company"));
				m.setEsiu_name(rs.getString("esiu_name"));
				m.setEsiu_computerid(rs.getString("esiu_computerid"));
				m.setEsiu_idcard(rs.getString("esiu_idcard"));
				m.setEsiu_hj(rs.getString("esiu_hj"));
				m.setEsiu_radix(rs.getInt("esiu_radix"));
				m.setEsiu_yl(rs.getString("esiu_yl"));
				m.setEsiu_gs(rs.getString("esiu_gs"));
				m.setEsiu_sye(rs.getString("esiu_sye"));
				m.setEsiu_syu(rs.getString("esiu_syu"));
				m.setEsiu_yltype(rs.getString("esiu_yltype"));
				m.setEsiu_house(rs.getString("esiu_house"));
				m.setEsiu_ylop(rs.getBigDecimal("esiu_ylop"));
				m.setEsiu_ylcp(rs.getBigDecimal("esiu_ylcp"));
				m.setEsiu_jlop(rs.getBigDecimal("esiu_jlop"));
				m.setEsiu_jlcp(rs.getBigDecimal("esiu_jlcp"));
				m.setEsiu_syuop(rs.getBigDecimal("esiu_syuop"));
				m.setEsiu_syucp(rs.getBigDecimal("esiu_syucp"));
				m.setEsiu_syeop(rs.getBigDecimal("esiu_syeop"));
				m.setEsiu_syecp(rs.getBigDecimal("esiu_syecp"));
				m.setEsiu_gsop(rs.getBigDecimal("esiu_gsop"));
				m.setEsiu_gscp(rs.getBigDecimal("esiu_gscp"));
				m.setEsiu_houseop(rs.getBigDecimal("esiu_houseop"));
				m.setEsiu_housecp(rs.getBigDecimal("esiu_housecp"));
				m.setEsiu_totalop(rs.getBigDecimal("esiu_totalop"));
				m.setEsiu_totalcp(rs.getBigDecimal("esiu_totalcp"));
				m.setEmsc_s8(rs.getString("state"));
				m.setEsiu_ifstop(rs.getInt("esiu_ifstop"));
				list.add(m);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	// 根据cid查询公司信息
	public CoBaseModel getCobaseByCid(int cid) {
		CoBaseModel cbm = new CoBaseModel();
		String sql = " SELECT * FROM CoBase WHERE cid = ? ";
		try {
			PreparedStatement pstmt = getpre(sql);
			pstmt.setInt(1, cid);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				cbm.setCid(cid);
				cbm.setCoba_company(rs.getString("coba_company"));
				cbm.setCoba_client(rs.getString("coba_client"));

				cbm.setCid(rs.getInt("cid"));
				// cbm.setPronum(rs.getInt("pronum"));
				cbm.setCoba_spell(rs.getString("coba_spell"));// 公司全称首字母
				cbm.setCoba_company(rs.getString("coba_company"));// 公司全称
				cbm.setCoba_shortspell(rs.getString("coba_shortspell"));// 简称首字母
				cbm.setCoba_shortname(rs.getString("coba_shortname"));// 公司简称
				cbm.setCoba_namespell(rs.getString("coba_namespell"));// 全称每个文字的首字母
				cbm.setCoba_englishname(rs.getString("coba_englishname"));// 英文名
				cbm.setCoba_country(rs.getString("coba_country"));// 所在国家
				cbm.setCoba_setuptype(rs.getString("coba_setuptype"));// 公司类型
				cbm.setCoba_servicearea(rs.getString("coba_servicearea"));// 服务区域
				cbm.setCoba_servicestate(rs.getInt("coba_servicestate"));// 服务状态
				cbm.setCoba_stopreason(rs.getString("coba_stopreason"));// 解约原因
				cbm.setCoba_stopname(rs.getString("coba_stopname"));// 解约客服
				cbm.setCoba_stopremark(rs.getString("coba_stopremark"));// 解约备注
				cbm.setCoba_stoptime(rs.getString("coba_stoptime"));// 解约时间
				cbm.setCoba_industytype(rs.getString("coba_industytype"));// 所属行业
				cbm.setCoba_clientsize(rs.getString("coba_clientsize"));// 客户规模
				cbm.setCoba_compacttype(rs.getString("coba_compacttype"));// 合同类型
				if (rs.getInt("coba_vip") == 1) {
					cbm.setCoba_vip("vip");
				}
				cbm.setCoba_addname(rs.getString("coba_addname"));
				cbm.setCoba_address(rs.getString("Coba_address"));// 注册地址/办公地点
				cbm.setCoba_area(rs.getString("coba_area"));// 公司位置/所在区域
				cbm.setCoba_companymanager(rs.getString("coba_companymanager"));// 法定代表人
				cbm.setCoba_manageraddress(rs.getString("coba_manageraddress"));// 法定代表人地址
				cbm.setCoba_postcode(rs.getString("coba_postcode"));// 邮政编码
				cbm.setCoba_website(rs.getString("coba_website"));// 公司网址
				cbm.setCoba_clientsource(rs.getString("coba_clientsource"));// 客户来源
				cbm.setCoba_client(rs.getString("coba_client"));// 客服
				cbm.setCoba_manager(rs.getString("coba_manager"));// 客服经理
				cbm.setCoba_clientmanager(rs.getString("coba_clientmanager"));// 客服部门经理
				cbm.setCoba_hzqsr(rs.getString("coba_hzqsr"));// 合作起始日
				cbm.setCoba_remark(rs.getString("coba_remark"));// 备注
				cbm.setCoba_regagent(rs.getString("coba_regagent"));// 注册代理方
				cbm.setCoba_reg_fund(rs.getString("coba_reg_fund"));// 注册资金
				cbm.setCoba_certificate(rs.getString("coba_certificate"));// 证件类型
				cbm.setCoba_bregdeadline(rs.getString("coba_bregdeadline"));// 工商登记到期日
				cbm.setCoba_bregid(rs.getString("coba_bregid"));// 工商登记号
				cbm.setCoba_regexpires(rs.getString("coba_regexpires"));// 驻在期限
				cbm.setCoba_regtime(rs.getString("coba_regtime"));// 工商局登记时间
				cbm.setCoba_reglimit(rs.getString("coba_reglimit"));// 工商登记证有效期
				cbm.setCoba_organdeadline(rs.getString("coba_organdeadline"));// 组织机构到期日
				cbm.setCoba_orregtime(rs.getString("coba_orregtime"));// 组织机构代码登记日期
				cbm.setCoba_organcode(rs.getString("coba_organcode"));// 组织结构代码
				cbm.setCoba_ntbank(rs.getString("coba_ntbank"));// 国税开户银行
				cbm.setCoba_ntaccount(rs.getString("coba_ntaccount"));// 国税账号
				cbm.setCoba_ntid(rs.getString("coba_ntid"));// 国税深字号
				cbm.setCoba_ntregtime(rs.getString("coba_ntregtime"));// 国税登记日期
				cbm.setCoba_ntlimit(rs.getString("coba_ntlimit"));// 国税有效期
				cbm.setCoba_ntdeadline(rs.getString("coba_ntdeadline"));// 国税纳税有效期
				cbm.setCoba_ltregid(rs.getString("coba_ltregid"));// 地税登记号
				cbm.setCoba_ltregtime(rs.getString("coba_ltregtime"));// 地税登记时间
				cbm.setCoba_ltlimit(rs.getString("coba_ltlimit"));// 地税有效期
				cbm.setCoba_ltstate(rs.getString("coba_ltstate"));// 地税开户状态
				cbm.setCoba_ltid(rs.getString("coba_ltid"));// 地税电脑号
				cbm.setCoba_ltdeadline(rs.getString("coba_ltdeadline"));// 地税纳税期限
				cbm.setCoba_ltbank(rs.getString("coba_ltbank"));// 地税开户银行
				cbm.setCoba_ltaccount(rs.getString("coba_ltaccount"));// 地税账号
				cbm.setCoba_gtstate(rs.getString("coba_gtstate"));// 个税开户状态
				cbm.setCoba_gtbank(rs.getString("coba_gtbank"));// 个税开户行
				cbm.setCoba_gtacc(rs.getString("coba_gtacc"));// 个税纳税账号
				cbm.setCoba_gtto(rs.getString("coba_gtto"));// 所属税局
				cbm.setCoba_gtdeadline(rs.getString("coba_gtdeadline"));// 个税申报截止日
				cbm.setCoba_sistate(rs.getString("coba_sistate"));// 开户状态
				cbm.setCoba_siiilimit(rs.getString("coba_siiilimit"));// 工伤比例
				cbm.setCoba_sicoding(rs.getString("coba_sicoding"));// 单位编码
				cbm.setCoba_sibank(rs.getString("coba_sibank"));// 开户行
				cbm.setCoba_siaccount(rs.getString("coba_siaccount"));// 账户
				cbm.setCoba_sihospital(rs.getString("coba_sihospital"));// 社康医疗机构
				cbm.setCoba_sihosphone(rs.getString("coba_sihosphone"));// 社康电话
				cbm.setCoba_sihosadd(rs.getString("coba_sihosadd"));// 社康地址
				cbm.setCoba_dept(rs.getString("coba_dept"));
				cbm.setCoba_regremark(rs.getString("coba_regremark"));// 公司注册信息备注
				cbm.setCoba_addtime(rs.getString("coba_addtime"));// 公司信息添加时间
				cbm.setCoba_addname(rs.getString("coba_addname"));// 公司信息添加人
				cbm.setCoba_modtime(rs.getString("coba_modtime"));// 公司信息修改时间
				cbm.setCoba_modname(rs.getString("coba_modname"));// 公司信息修改人
				cbm.setCoba_shebaodeclare(rs.getString("coba_shebaodeclare"));
				cbm.setCoba_ufid(rs.getString("coba_ufid"));// 收款中用到的用友编号
				cbm.setCoba_ufid2(rs.getString("coba_ufid2"));// 社保和公积金台帐中用到的用友编号
				cbm.setCoba_ufclass(rs.getString("coba_ufclass"));
				cbm.setCoba_invoicerule(rs.getString("coba_invoicerule"));
				cbm.setCoba_invoiceaddress(rs.getString("coba_invoiceaddress"));// 发票邮寄地址
				cbm.setCoba_wtco(rs.getString("coba_wtco"));
				cbm.setCoba_regdataList(rs.getString("coba_regdataList"));// 注册信息-递交材料
				cbm.setCoba_assistant(rs.getString("coba_assistant"));// 客服文员
				cbm.setCoba_zytcid(rs.getString("coba_zytcid"));// 智翼通公司编号
				cbm.setCoba_zytwtcid(rs.getString("coba_zytwtcid"));// 智翼通委托公司编号
				cbm.setCoba_developer(rs.getString("coba_developer"));// 开发人员
				cbm.setCoba_gzautoemail(rs.getInt("coba_gzautoemail"));// 系统自动发送Email工资单(是or否)
				cbm.setCoba_sign(rs.getInt("coba_sign"));// 是否国内注册
				cbm.setCoba_invoice(rs.getString("coba_invoice"));
				cbm.setCoba_taxmonth(rs.getString("coba_taxmonth"));// 是否上月个税(个税报表月份)
				cbm.setCoba_gzaddname(rs.getString("coba_gzaddname"));// 薪酬负责人(添加人)
				cbm.setCoba_gzaudname(rs.getString("coba_gzaudname"));// 薪酬审核人
				cbm.setCoba_gzemailtype(rs.getInt("coba_gzemailtype"));// 判断电子工资单发放格式
				cbm.setCoba_emfi_paydate(rs.getString("coba_emfi_paydate"));// 付款通知发放时间
				cbm.setCoba_emfi_backdate(rs.getString("coba_emfi_backdate"));// 回款时间
				cbm.setCoba_gz_paydate(rs.getString("coba_gz_paydate"));// 工资发放时间
				cbm.setCoba_emailgz_paydate(rs
						.getString("coba_emailgz_paydate"));// 电子工资单发放时间
				cbm.setCoba_papergz_paydate(rs
						.getString("coba_papergz_paydate"));// 纸质工资单发放时间
				cbm.setCoba_emfics_backdate(rs
						.getString("coba_emfics_backdate"));// 薪酬回款时间
				cbm.setCoba_emfics_deldate(rs.getString("coba_emfics_deldate"));// 薪酬台账明细制作时间
				cbm.setCoba_emfics_paydate(rs.getString("coba_emfics_paydate"));// 薪酬付款通知发放时间
				cbm.setCoba_gzautoemaildays(rs.getInt("coba_gzautoemaildays"));// 工资发放后几天后自动发送Email工资单
				// cbm.setPeopnum(rs.getInt("peopnum")); // 公司人数
				cbm.setCoba_fpremark(rs.getString("coba_fpremark"));// 发票开票规则
				// if (rs.getString("pactnum") == null
				// || rs.getString("pactnum") == ""
				// || rs.getString("pactnum").equals("")) {
				// cbm.setCoconum("0");
				// } else {
				// cbm.setCoconum(rs.getString("pactnum")); // 合同数
				// }
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cbm;
	}

	// 查询拥有福利项目的公司名单
	public List<CoBaseModel> getcobaseFlList(String client) {
		List<CoBaseModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql = "select cid,coba_company"
				+ " from CoBase"
				+ " where CID in ("
				+ "select CID from CoCompact where coco_id in ("
				+ "select coof_coco_id from CoOffer where coof_id in ("
				+ "select coli_coof_id from CoOfferList where coli_state=1 and coli_pclass ='员工福利'))"
				+ "and CID in ( select distinct cid from DataPopedom where log_id="
				+ UserInfo.getUserid() + " and  Dat_edited=1 )" + ")";
		if (client != null && !client.equals("")) {
			sql += " and coba_client='" + client + "'";
		}

		sql += " order by coba_company";
		try {
			list = db.find(sql, CoBaseModel.class, null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;

	}

	// 查询拥有福利项目的公司客服名单
	public List<CoBaseModel> getClientFlList() {
		List<CoBaseModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql = "select distinct coba_client"
				+ " from CoBase"
				+ " where CID in ("
				+ "select CID from CoCompact where coco_id in ("
				+ "select coof_coco_id from CoOffer where coof_id in ("
				+ "select coli_coof_id from CoOfferList where coli_state=1 and coli_pclass ='员工福利'))"
				+ "and CID in ( select cid from DataPopedom where log_id="
				+ UserInfo.getUserid() + " and  Dat_edited=1 )" + ")";
		sql += " order by coba_client";
		try {
			list = db.find(sql, CoBaseModel.class, null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;

	}

	// 根据传入的查询条件查询公司,查询条件有cid请表明确cid所在的表
	public List<CoBaseModel> getCobaseinfoforall(String sql) {
		ResultSet rs = null;
		List<CoBaseModel> cobaseinfo = new ArrayList<CoBaseModel>();
		if (!cobaseinfo.isEmpty())
			cobaseinfo.clear();
		try {
			dbconn db = new dbconn();
			// String sqlstr = "SELECT  * from cobase where 1=1 "+sql+"";
			// sqlstr=sqlstr+" order by coba_servicestate desc,cid desc";
			// String
			// sqlstr="select a.*,peopnum,pactnum from CoBase a,(select cid,COUNT(*) peopnum from EmBase group by cid) b,";
			// sqlstr=sqlstr+" (select cid ,count(*)pactnum from cocompact  where cid >0 group by cid) c where a.CID=b.cid and a.CID=c.cid";
			// sqlstr=sqlstr+sql;
			String sqls = "select top 100  convert(nvarchar(10),coba_hzqsr,120) coba_hzqsr,"
					+ "a.*,peopnum,pactnum,pronum from CoBase a left join (select cid,COUNT(*) peopnum "
					+ "from EmBase where emba_state=1 group by cid) b";
			sqls = sqls
					+ " on a.cid=b.cid left join (select cid ,count(*) pactnum from cocompact  where  coco_state>3 and cid >0 group by cid) c on a.cid=c.cid"
					+ " left join (select cid ,COUNT(*) pronum from CoBaseServePromise group by cid) d on a.CID=d.cid ";
			sqls = sqls + " where 1=1" + sql;

			sqls = sqls + " order by coba_servicestate desc,a.cid desc";

			rs = db.GRS(sqls);
			while (rs.next()) {
				CoBaseModel model = new CoBaseModel();
				model.setCid(rs.getInt("cid"));
				model.setPronum(rs.getInt("pronum"));
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
				model.setPeopnum(rs.getInt("peopnum")); // 公司人数
				model.setCoba_fpremark(rs.getString("coba_fpremark"));// 发票开票规则
				if (rs.getString("pactnum") == null
						|| rs.getString("pactnum") == ""
						|| rs.getString("pactnum").equals("")) {
					model.setCoconum("0");
				} else {
					model.setCoconum(rs.getString("pactnum")); // 合同数
				}
				cobaseinfo.add(model);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cobaseinfo;
	}

	// 根据传入的查询条件查询公司,查询条件有cid请表明确cid所在的表
	public List<CoBaseModel> getCobaseinfo(String sql) {// all
		ResultSet rs = null;
		List<CoBaseModel> cobaseinfo = new ArrayList<CoBaseModel>();
		if (!cobaseinfo.isEmpty())
			cobaseinfo.clear();
		try {
			dbconn db = new dbconn();
			// String sqlstr = "SELECT  * from cobase where 1=1 "+sql+"";
			// sqlstr=sqlstr+" order by coba_servicestate desc,cid desc";
			// String
			// sqlstr="select a.*,peopnum,pactnum from CoBase a,(select cid,COUNT(*) peopnum from EmBase group by cid) b,";
			// sqlstr=sqlstr+" (select cid ,count(*)pactnum from cocompact  where cid >0 group by cid) c where a.CID=b.cid and a.CID=c.cid";
			// sqlstr=sqlstr+sql;
			String sqls = "select  convert(nvarchar(10),coba_hzqsr,120) coba_hzqsr,"
					+ "a.*,peopnum,pactnum,pronum from CoBase a left join (select cid,COUNT(*) peopnum "
					+ "from EmBase where emba_state=1 group by cid) b";
			sqls = sqls
					+ " on a.cid=b.cid left join (select cid ,count(*) pactnum from cocompact  where  coco_state>3 and cid >0 group by cid) c on a.cid=c.cid"
					+ " left join (select cid ,COUNT(*) pronum from CoBaseServePromise group by cid) d on a.CID=d.cid ";
			sqls = sqls + " where 1=1" + sql;
			sqls = sqls
					+ " and a.CID in ( select cid from DataPopedom where log_id="
					+ UserInfo.getUserid() + " and dat_selected=1 )";
			sqls = sqls + " order by coba_servicestate desc,a.cid desc";
			System.out.println(sqls);
			rs = db.GRS(sqls);
			while (rs.next()) {
				CoBaseModel model = new CoBaseModel();
				model.setCid(rs.getInt("cid"));
				model.setPronum(rs.getInt("pronum"));
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
				model.setPeopnum(rs.getInt("peopnum")); // 公司人数
				model.setCoba_fpremark(rs.getString("coba_fpremark"));// 发票开票规则
				if (rs.getString("pactnum") == null
						|| rs.getString("pactnum") == ""
						|| rs.getString("pactnum").equals("")) {
					model.setCoconum("0");
				} else {
					model.setCoconum(rs.getString("pactnum")); // 合同数
				}
				model.setCoba_kind(rs.getString("coba_kind"));
				model.setCoba_ifhasbribery(rs.getString("coba_ifhasbribery"));
				cobaseinfo.add(model);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cobaseinfo;
	}

	// 获取客服列表
	public List<CoBaseModel> getCobaseClient(String sql) {
		ResultSet rs = null;
		List<CoBaseModel> cobaseinfo = new ArrayList<CoBaseModel>();
		if (!cobaseinfo.isEmpty())
			cobaseinfo.clear();
		try {
			dbconn db = new dbconn();

			String sqls = "select coba_client from cobase where coba_servicestate=1 and CID in(select cid from DataPopedom where log_id="
					+ UserInfo.getUserid()
					+ " and dat_selected=1)  group by coba_client order by coba_client";

			rs = db.GRS(sqls);
			while (rs.next()) {
				CoBaseModel model = new CoBaseModel();
				model.setCoba_client(rs.getString("coba_client"));
				cobaseinfo.add(model);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cobaseinfo;
	}

	// 根据传入的查询条件查询公司,查询条件有cid请表明确cid所在的表
	public List<CoBaseModel> getCobaseeditinfo(String sql) {// 所有
		ResultSet rs = null;
		List<CoBaseModel> cobaseinfo = new ArrayList<CoBaseModel>();
		if (!cobaseinfo.isEmpty())
			cobaseinfo.clear();
		try {
			dbconn db = new dbconn();

			String sqls = "select  a.*,peopnum,pactnum,pronum from CoBase a left join (select cid,COUNT(*) peopnum from EmBase where emba_state=1 group by cid) b";
			sqls = sqls
					+ " on a.cid=b.cid left join (select cid ,count(*)pactnum from cocompact  where cid >0 group by cid) c on a.cid=c.cid"
					+ " left join (select cid ,COUNT(*) pronum from CoBaseServePromise group by cid) d on a.CID=d.cid ";
			sqls = sqls + " where 1=1" + sql;
			sqls = sqls
					+ " and a.CID in ( select cid from DataPopedom where log_id="
					+ UserInfo.getUserid() + " and Dat_edited=1 )";
			sqls = sqls + " order by coba_servicestate desc,a.cid desc";

			rs = db.GRS(sqls);
			while (rs.next()) {
				CoBaseModel model = new CoBaseModel();
				model.setCid(rs.getInt("cid"));
				model.setPronum(rs.getInt("pronum"));
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
				model.setCoba_assistant(rs.getString("coba_assistant"));//员服
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
				model.setPeopnum(rs.getInt("peopnum")); // 公司人数
				if (rs.getString("pactnum") == null
						|| rs.getString("pactnum") == ""
						|| rs.getString("pactnum").equals("")) {
					model.setCoconum("0");
				} else {
					model.setCoconum(rs.getString("pactnum")); // 合同数
				}
				model.setCoba_kind(rs.getString("coba_kind"));
				model.setCoba_ifhasbribery(rs.getString("coba_ifhasbribery"));
				model.setCoba_fpremark(rs.getString("coba_fpremark"));// 发票开票规则
				model.setCoba_regaccount(rs.getString("coba_regaccount"));
				model.setCoba_regaccountpwd(rs.getString("coba_regaccountpwd"));
				cobaseinfo.add(model);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cobaseinfo;
	}

	/**
	 * @Title: getCobaseByClient
	 * @Description: TODO(根据客服查询公司列表)
	 * @param client
	 * @return
	 * @throws SQLException
	 * @return List<CoBaseModel> 返回类型
	 * @throws
	 */
	public List<CoBaseModel> getCobaseByClient(String client, String name)
			throws SQLException {
		List<CoBaseModel> list = new ListModelList<CoBaseModel>();
		dbconn db = new dbconn();
		name = name.equals("") ? "%" : name + "%";
		client = client.equals("") ? "%" : client.equals("全部") ? "%" : client;
		String sql = "select distinct top 10 cid ,'[ '+coba_spell+' ]' + coba_company coba_shortname, coba_company from cobase where coba_serviceState=1 and cid in (select distinct cid from embase where emba_state=1) and coba_client like ? and (coba_company like ? or coba_shortname like ? or cid like ? or coba_namespell like ?) order by coba_company";
		list = db.find(sql, CoBaseModel.class,
				dbconn.parseSmap(CoBaseModel.class), client, name, name, name,
				name);

		return list;

	}

	public List<CoBaseModel> getCoBaseInfo(CoBaseModel cm, String columns,
			Boolean disinct, Integer topNum, String order) {
		List<CoBaseModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql = "select ";
		if (disinct != null && disinct) {
			sql = sql + "distinct ";
		}
		if (topNum != null && !topNum.equals("")) {
			sql = sql + "top " + topNum + " ";
		}
		if (columns != null && !columns.equals("")) {
			sql = sql + columns + " ";
		} else {
			sql = sql
					+ " cid,coba_company,coba_shortname,coba_client,coba_invoicerule,coba_industytype";
		}
		sql = sql + " from cobase where 1=1";

		if (cm != null) {

			if (cm.isFuzzy()) {
				sql = sql + " and (";

				if (cm.getCoba_company() != null) {
					sql = sql + "cid like '" + cm.getCoba_company()
							+ "%' or coba_shortname like '%"
							+ cm.getCoba_company()
							+ "%' or coba_company like '%"
							+ cm.getCoba_company() + "%'"
							+ " or coba_namespell like '%"
							+ cm.getCoba_company() + "%'";
				}

				sql = sql + ")";
			} else {
				if (cm.getCid() != null) {
					sql = sql + " and cid=" + cm.getCid();
				}
			}

			if (cm.getCoba_client() != null && !cm.getCoba_client().equals("")) {
				sql = sql + " and coba_client='" + cm.getCoba_client() + "'";
			}

			if (cm.getCoba_servicestate() > 0) {
				sql = sql + " and coba_servicestate="
						+ cm.getCoba_servicestate();
			}
		}
		if (order != null && !order.equals("")) {
			sql += " order by " + order;
		} else {
			sql = sql + " order by coba_client,coba_company";
		}

		try {
			list = db.find(sql, CoBaseModel.class, null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public String[] getcobaList() {

		StringBuilder spstr = new StringBuilder();
		dbconn db = new dbconn();
		try {

			ResultSet rs = db.GRS("SELECT top 500 * from  cobase where 1=1");
			while (rs.next()) {

				spstr.append(rs.getString("coba_namespell") + "|"
						+ rs.getInt("cid") + "|"
						+ rs.getString("coba_shortname") + "|"
						+ rs.getString("coba_client"));
				spstr.append(",");
			}

		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return spstr.toString().split(",");

	}

	/**
	 * @Title: getClient
	 * @Description: TODO(查询客服列表)
	 * @return
	 * @throws SQLException
	 * @return List<CoBaseModel> 返回类型
	 * @throws
	 */
	public List<CoBaseModel> getClient() {
		List<CoBaseModel> list = new ListModelList<CoBaseModel>();
		dbconn db = new dbconn();
		String sql = "select '全部' coba_client,0 sort union all select distinct coba_client,1 from cobase where coba_serviceState=1 and cid in (select distinct cid from embase where emba_state=1) order by sort,coba_client";
		try {
			list = db.find(sql, CoBaseModel.class,
					dbconn.parseSmap(CoBaseModel.class));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;

	}

	public List<CoBaseModel> getCobase(String str) throws SQLException {
		ResultSet rs = null;
		List<CoBaseModel> cobaseinfo = new ArrayList<CoBaseModel>();
		if (!cobaseinfo.isEmpty())
			cobaseinfo.clear();
		try {
			dbconn db = new dbconn();

			String sqls = "select * FROM cobase WHERE 1=1 " + str
					+ " order by coba_servicestate desc,coba_shortname";

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
				model.setCoba_client(rs.getString("coba_client"));// 客服
				model.setCoba_manager(rs.getString("coba_manager"));// 客服经理
				model.setCoba_clientmanager(rs.getString("coba_clientmanager"));// 客服部门经理
				model.setCoba_gzautoemail(rs.getInt("coba_gzautoemail"));// 系统自动发送Email工资单(是or否)
				model.setCoba_taxmonth(rs.getString("coba_taxmonth"));// 是否上月个税(个税报表月份)
				model.setCoba_gzaddname(rs.getString("coba_gzaddname"));// 薪酬负责人(添加人)
				model.setCoba_gzaudname(rs.getString("coba_gzaudname"));// 薪酬审核人
				model.setCoba_gzemailtype(rs.getInt("coba_gzemailtype"));// 判断电子工资单发放格式
				model.setCoba_emfi_paydate(rs.getString("coba_emfi_paydate"));// 付款通知发放时间
				model.setCoba_emfi_backdate(rs.getString("coba_emfi_backdate"));// 回款时间
				model.setCoba_gz_paydate(rs.getString("coba_gz_paydate"));// 工资发放时间
				model.setCoba_kind(rs.getString("coba_kind"));
				model.setCoba_ifhasbribery(rs.getString("coba_ifhasbribery"));
				model.setCoba_fpremark(rs.getString("coba_fpremark"));// 发票开票规则
				cobaseinfo.add(model);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cobaseinfo;

	}

	// 根据CID查询客服
	public List<CoBaseModel> getInfoByCid(Integer cid) throws SQLException {
		List<CoBaseModel> list = new ListModelList<CoBaseModel>();
		dbconn db = new dbconn();
		String sql = "select log_id,coba_client from cobase a inner join login b on a.coba_client=b.log_name where cid=?";
		list = db.find(sql, CoBaseModel.class,
				dbconn.parseSmap(CoBaseModel.class), cid);
		return list;
	}

	// 根据GID查询客服
	public List<CoBaseModel> getInfoByGid(Integer gid) throws SQLException {
		List<CoBaseModel> list = new ListModelList<CoBaseModel>();
		dbconn db = new dbconn();
		String sql = "select log_id,coba_client from cobase a inner join login b on a.coba_client=b.log_name where cid in (select cid from embase where gid=?)";
		list = db.find(sql, CoBaseModel.class,
				dbconn.parseSmap(CoBaseModel.class), gid);
		return list;
	}

	// 查询公司列表
	public List<CoBaseModel> getInfoListByName(String name) {
		List<CoBaseModel> list = new ListModelList<CoBaseModel>();
		dbconn db = new dbconn();
		StringBuilder sql = new StringBuilder();
		name = name.equals("") ? "%" + name + "%" : name;
		sql.append("select top 10 cid,UPPER(coba_spell)coba_spell,coba_company,coba_shortname,coba_client from cobase");
		sql.append(" where cid like ? or coba_company like ? or coba_shortname like ? or coba_englishname like ? or coba_namespell like ?");
		sql.append(" order by coba_company");
		try {
			list = db.find(sql.toString(), CoBaseModel.class,
					dbconn.parseSmap(CoBaseModel.class), name, name, name,
					name, name);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;

	}

	// 查询独立户公司列表
	public List<CoBaseModel> getsingleListByName(String name) {
		List<CoBaseModel> list = new ListModelList<CoBaseModel>();
		dbconn db = new dbconn();
		String sql = "select a.cid,UPPER(coba_spell)coba_spell,coba_company,coba_shortname,coba_client,cohf_houseid "
				+ "from cobase a "
				+ "inner join CoHousingFund b on a.cid=b.cid "
				+ "where (a.cid like ? or coba_company like ? or coba_shortname like ? or coba_englishname like ? or coba_namespell like ?) "
				+ "and coba_serviceState=1 and cohf_state=1 "
				+ "order by coba_company";
		System.out.println(sql);
		System.out.println(name);
		try {
			list = db.find(sql, CoBaseModel.class, null, name, name, name,
					name, name);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	// 查询公司列表(体检)
	public List<CoBaseModel> getcompanyList(String name) {
		List<CoBaseModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql = "select a.cid,'['+UPPER(coba_spell)+']'+coba_company coba_company,coba_shortname "
				+ "from cobase a "
				+ "inner join ("
				+ " select distinct a.cid from cocompact a"
				+ " inner join cooffer b on a.coco_id=b.coof_coco_id"
				+ " inner join coofferlist c on b.coof_id=c.coli_coof_id"
				+ " where (coli_name like '%体检%' or coli_name like '中智_类' or coli_pclass like '%体检%') and coco_state>3 and coof_state=3 and coli_stoptime is null"
				+ ")b on a.cid=b.cid"
				+ " where coba_servicestate=1 and a.cid in ("
				+ "select cid from DataPopedom a inner join login b on a.log_id=b.log_id"
				+ " where b.log_name=?" + ") ";
		sql = sql + " order by coba_company";
		System.out.println(sql);
		try {
			list = db.find(sql, CoBaseModel.class, null, name);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

	// 查询公司列表(体检)
	public List<CoBaseModel> getcompanyEmbodyList(String name) {
		List<CoBaseModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql = "select a.cid,'['+UPPER(coba_spell)+']'+coba_company coba_company,coba_shortname "
				+ "from cobase a "
				+ " where coba_servicestate=1 and a.cid in ("
				+ "select cid from DataPopedom a inner join login b on a.log_id=b.log_id"
				+ " where b.log_name=?" + ") and coba_client=? ";
		sql = sql + " order by coba_company";
		try {
			list = db.find(sql, CoBaseModel.class, null, name, name);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

	public List<CoCompactModel> getcompactList(Integer cid) {
		List<CoCompactModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql = "select coco_id,cid cid2,coco_compactid,coco_compacttype"
				+ " from CoCompact where cid=? and coco_state>3";
		try {
			list = db.find(sql, CoCompactModel.class, null, cid);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<CoCompactModel> getcompactLists(Integer cid) {
		List<CoCompactModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql = "select coco_id,cid cid2,coco_compactid,coco_compacttype"
				+ " from CoCompact where cid=?";
		try {
			list = db.find(sql, CoCompactModel.class, null, cid);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<CoOfferModel> getcoofferList(String str) {
		List<CoOfferModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql = "select coof_id,coof_name,coof_cpct_id,coof_quotemode,coof_quotetime,"
				+ "coof_auditing_name,coof_auditing_time,coof_state,coof_filename,coof_addname,"
				+ "coof_addtime,coof_modname,coof_modtime,coof_remark,coof_tali_id,coof_register,"
				+ "coof_cola_id,b.cid,coof_coco_id,coof_min,coof_max,coof_gm,coof_sum,"
				+ "convert(nvarchar(10),coof_addtime,120)addtime,coco_compactid"
				+ " from CoOffer a left join (select cid,coco_id,coco_compactid from CoCompact)b"
				+ " on a.coof_coco_id=b.coco_id where coof_state=3" + str;
		System.out.print(sql);

		try {
			list = db.find(sql, CoOfferModel.class, null);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<CoOfferModel> getEmbaseList(String str) {
		List<CoOfferModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql = "select distinct a.gid,b.emba_name from CoGList a left join (select gid,emba_name from EmBase)b on a.gid=b.gid"
				+ " left join (select coli_id,coli_coof_id from CoOfferList)c on a.cgli_coli_id=c.coli_id"
				+ " left join (select coof_id from CoOffer)d on c.coli_coof_id=d.coof_id"
				+ " where cgli_state=1 and cgli_stopdate is null" + str;
		try {
			list = db.find(sql, CoOfferModel.class, null);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	// 根据查询客户的第一份合同生效时间
	public Date getCocoInuredate(Integer cid) {
		String sql = "select coco_inuredate,* from CoCompact where cid=" + cid
				+ " order by coco_id desc";
		Date date = null;
		try {
			dbconn db = new dbconn();
			ResultSet rs = db.GRS(sql);
			while (rs.next()) {
				date = rs.getDate("coco_inuredate");
			}
		} catch (Exception e) {

		}

		return date;
	}

	// 查询用友编号
	public List<CoBaseModel> getUid(Integer cid) {
		List<CoBaseModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql = "select cid,coba_ufid,"
				+ "case when b.cpac_id>0 then 'AF' when C.cpac_id>0 then 'FS' END compacttype "
				+ " from cobase a"
				+ " left join CoPAccount b on a.coba_ufclass=b.cpac_af "
				+ " left join CoPAccount c on a.coba_ufclass=c.cpac_fs"
				+ " where a.cid=?";
		try {
			list = db.find(sql, CoBaseModel.class, null, cid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
}
