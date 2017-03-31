package dal.EmCommissionOut;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.zkoss.zul.ListModelList;

import Conn.dbconn;
import Model.EmCommissionOutChangeModel;
import Model.EmCommissionOutFeeDetailModel;
import Model.EmCommissionOutModel;
import Model.EmCommissionOutStandardModel;
import Model.SocialInsuranceClassInfoViewModel;

public class EmCommissionOutDal {

	public List<SocialInsuranceClassInfoViewModel> getSoClassList(
			Integer sial_id) {
		List<SocialInsuranceClassInfoViewModel> list = new ArrayList<>();
		dbconn db = new dbconn();
		String sql = "SELECT sicl_id,sicl_class,sicl_name,sicl_payunit,sicl_ifclass,sicl_ifname,"
				+ "sicl_order,siai_id,siai_sial_id,siai_sicl_id,siai_side_id,"
				+ "siai_proportion,siai_algorithm,siai_remark,side_decimal"
				+ " FROM SocialInsuranceClassInfo_view where sicl_id<>17 and siai_sial_id="
				+ sial_id;

		try {
			list = db.find(sql, SocialInsuranceClassInfoViewModel.class,
					dbconn.parseSmap(SocialInsuranceClassInfoViewModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 获取该员工基本信息
	public EmCommissionOutChangeModel getEmbase(Integer gid) {
		dbconn db = new dbconn();
		List<EmCommissionOutChangeModel> list = new ArrayList<>();
		String sql = "select cid,emba_idcard ecoc_idcard,emba_phone ecoc_phone,emba_mobile ecoc_mobile,"
				+ "emba_hjadd ecoc_domicile,emba_email ecoc_email"
				+ " from embase where gid=" + gid;

		try {
			list = db.find(sql, EmCommissionOutChangeModel.class,
					dbconn.parseSmap(EmCommissionOutChangeModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list.size() > 0 ? list.get(0) : null;
	}

	// 获取该公司标准信息
	public List<EmCommissionOutStandardModel> getStardList(Integer cid)
			throws SQLException {
		dbconn db = new dbconn();
		List<EmCommissionOutStandardModel> list = new ArrayList<>();
		String sql = "select ecos_id,ecos_name,ecos_service_fee,ecos_archvie_fee,ecos_cabc_id"
				+ " from EmCommissionOutStandard where ecos_usestate=1 and cid="
				+ cid;

		try {
			list = db.find(sql, EmCommissionOutStandardModel.class,
					dbconn.parseSmap(EmCommissionOutStandardModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 获取该标准信息
	public List<EmCommissionOutChangeModel> getTitleList(Integer ecos_id)
			throws SQLException {
		dbconn db = new dbconn();
		List<EmCommissionOutChangeModel> list = new ArrayList<>();
		String sql = "select sial_id,soin_title+'('+CAST(sial_execdate as nvarchar(7))+')' soin_title,"
				+ "soin_id,convert(nvarchar(10),sial_execdate,120) ecoc_title_date"
				+ " from SocialInsurance a inner join SocialInsuranceAlgorithm b"
				+ " on a.soin_id=b.sial_soin_id inner join"
				+ " EmCommissionOutStandard c on a.soin_cabc_id=c.ecos_cabc_id"
				+ " where soin_state=1 and ecos_usestate=1 and ecos_id="
				+ ecos_id + " order by soin_title desc";

		try {
			list = db.find(sql, EmCommissionOutChangeModel.class,
					dbconn.parseSmap(EmCommissionOutChangeModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 获取福利信息
	public List<EmCommissionOutFeeDetailModel> getflList(Integer ecos_id) {
		dbconn db = new dbconn();
		List<EmCommissionOutFeeDetailModel> list = new ArrayList<>();
		String sql = "select coli_name eofd_name,coli_content eofd_content,coli_fee eofd_month_sum"
				+ " from EmCommissionOutStandard a inner join EmCommissionOutCoOfferListRel b"
				+ " on a.ecos_id=b.ecop_ecos_id inner join CoOfferList c on b.ecop_coli_id=c.coli_id"
				+ " where ecos_id=" + ecos_id;

		try {
			list = db.find(sql, EmCommissionOutFeeDetailModel.class,
					dbconn.parseSmap(EmCommissionOutFeeDetailModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public boolean getupdateInfo(Integer gid) {
		boolean b = false;
		List<EmCommissionOutModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql = "select gid from EmCommissionOut where ecou_addtype !='离职' and gid=? ";
		try {
			list = db.find(sql, EmCommissionOutModel.class, null, gid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (list.size() > 0) {
			b = true;
		}
		return b;
	}

	// // 获取该标准详细信息
	// public List<EmCommissionOutModel> getfl_base(String ecop_id)
	// throws SQLException {
	// dbconn db = new dbconn();
	// ResultSet rs = null;
	// List<EmCommissionOutModel> list = new ArrayList<EmCommissionOutModel>();
	// String sqlstr =
	// "select * from EmCommissionOutCoOfferListRel a left join CoOfferList b on b.coli_id=a.ecop_coli_id  where ecop_ecos_id="
	// + ecop_id;
	//
	// try {
	// rs = db.GRS(sqlstr);
	//
	// while (rs.next()) {
	// EmCommissionOutModel model = new EmCommissionOutModel();
	// model.setEcoc_id(rs.getString("coli_id"));
	// model.setEcoc_name(rs.getString("coli_name"));
	// model.setEcoc_total(rs.getString("coli_fee"));
	// list.add(model);
	// }
	// } catch (Exception e) {
	// System.out.print(e.toString());
	// } finally {
	// db.Close();
	// }
	// return list;
	// }
}
