package dal.EmBenefit;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.zkoss.zul.ListModelList;

import Conn.dbconn;
import Model.EmActySupContactManInfoModel;
import Model.EmActySupProductInfoModel;
import Model.EmActySupplierInfoModel;
import Model.EmBenefitRelationModel;

public class EmActy_SupplierSelectDal {
	// 查询员工活动——供应商信息
	public List<EmActySupplierInfoModel> getEmActySupplierInfo(String str) {
		List<EmActySupplierInfoModel> list = new ArrayList<EmActySupplierInfoModel>();
		dbconn db = new dbconn();
		String sql = "select supp_id,supp_name,supp_type,supp_address,supp_mobile,supp_addname,"
				+ "convert(char(10),supp_addtime,120) as supp_addtime,supp_remark,"
				+ "supp_website,connum,pronum from EmActySupplierInfo a left join (select coct_supId,count(*) connum "
				+ " from EmActySupContactManInfo group by coct_supId) b on a.supp_id=b.coct_supId left join "
				+ " (select prod_supId,count(*) pronum from EmActySupProductInfo  group by prod_supId) c "
				+ " on a.supp_id=c.prod_supId where 1=1 " + str;
		sql = sql + " order by supp_id desc";
		try {
			list = db.find(sql, EmActySupplierInfoModel.class,
					dbconn.parseSmap(EmActySupplierInfoModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<EmActySupplierInfoModel> getNameList(String name, Boolean type) {
		List<EmActySupplierInfoModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql = "select distinct top 10 supp_id,supp_name from EmActySupplierInfo where 1=1";

		if (type) {
			name = "%" + name + "%";
			sql = sql + " and supp_name like ?";
		} else {
			sql = sql + " and supp_name = ?";
		}
		sql = sql + " order by supp_name";
		try {
			list = db.find(sql, EmActySupplierInfoModel.class, null, name);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	// 查询员工活动——供应商联系人信息
	public List<EmActySupContactManInfoModel> getEmActySupContactManInfo(
			String str) {
		List<EmActySupContactManInfoModel> list = new ArrayList<EmActySupContactManInfoModel>();
		dbconn db = new dbconn();
		String sql = "select coct_id,coct_supid,coct_name,coct_sex,coct_mobile,coct_phone,coct_address,coct_addname,coct_remark,"
				+ "convert(char(10),coct_addtime,120) as coct_addtime,coct_Email,coct_state "
				+ "from EmActySupContactManInfo where 1=1 " + str;
		sql = sql + " order by coct_id desc";
		try {
			list = db.find(sql, EmActySupContactManInfoModel.class,
					dbconn.parseSmap(EmActySupContactManInfoModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 查询员工活动——供应商产品信息
	public List<EmActySupProductInfoModel> getEmActySupProductInfo(String str) {
		List<EmActySupProductInfoModel> list = new ArrayList<EmActySupProductInfoModel>();
		dbconn db = new dbconn();
		String sql = "select prod_id,prod_supid,prod_name,convert(decimal(18,1),prod_price) as prod_price,prod_remark,"
				+ "convert(decimal(18,1),prod_discountprice) as prod_discountprice,prod_state,"
				+ "prod_discount,prod_totalnum,prod_addname,"
				+ "convert(char(10),prod_addtime,120) as prod_addtime "
				+ "from EmActySupProductInfo where 1=1 " + str;
		sql = sql + " order by prod_id desc";
		try {
			list = db.find(sql, EmActySupProductInfoModel.class,
					dbconn.parseSmap(EmActySupProductInfoModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<EmActySupProductInfoModel> getEmActySupProductInfo(
			EmActySupProductInfoModel em) {
		List<EmActySupProductInfoModel> list = new ArrayList<EmActySupProductInfoModel>();
		dbconn db = new dbconn();
		String sql = "select prod_id,prod_supid,prod_eaco_id,prod_name,prod_price,prod_discountprice,"
				+ "prod_discount,prod_totalnum,prod_addname,prod_addname,prod_addtime,prod_state,"
				+ "prod_remark,prod_modtime,prod_modname "
				+ "from EmActySupProductInfo " + "where 1=1";
		if (em.getProd_id() != null && !em.getProd_id().equals("")) {
			sql += " and prod_id=" + em.getProd_id();
		}

		if (em.getProd_eaco_id() != null && !em.getProd_eaco_id().equals("")) {
			sql += " and prod_eaco_id=" + em.getProd_eaco_id();
		}
		if (em.getProd_state() != null && !em.getProd_state().equals("")) {
			sql += " and prod_state=" + em.getProd_state();
		}

		try {
			list = db.find(sql, EmActySupProductInfoModel.class, null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	// 查询员工活动——查询已经关联了合同的产品
	public List<EmActySupProductInfoModel> getEmProductInfoList(String str,
			String embf_name) {
		List<EmActySupProductInfoModel> list = new ArrayList<EmActySupProductInfoModel>();
		dbconn db = new dbconn();
		String sql = "select prod_id,prod_supid,prod_name,supp_name "
				+ "from EmActySupProductInfo a,EmActySupplierInfo b ,"
				+ "EmActyCompact c where a.prod_supid=b.supp_id and b.supp_id=c.eaco_supp_id "
				+ "and a.prod_eaco_id=c.eaco_id and prod_id not in(select rela_prod_id "
				+ "from EmBenefitAndproduct c,EmBenefit d where c.rela_embe_id=d.embf_id "
				+ "and rela_state=1 and embf_name='" + embf_name + "')" + str
				+ "order by prod_name desc";
		try {
			list = db.find(sql, EmActySupProductInfoModel.class,
					dbconn.parseSmap(EmActySupProductInfoModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 查询员工活动——根据系那个亩名称选择已经被关联了的产品
	public List<EmActySupProductInfoModel> getEmProductInfo(String embf_name) {
		List<EmActySupProductInfoModel> list = new ArrayList<EmActySupProductInfoModel>();
		dbconn db = new dbconn();
		String sql = "select prod_id,prod_supid,prod_name,supp_name "
				+ " from EmActySupProductInfo a,EmActySupplierInfo b where a.prod_supid=b.supp_id "
				+ " and prod_id in(select rela_prod_id from EmBenefitAndproduct c,EmBenefit d "
				+ "where c.rela_embe_id=d.embf_id and rela_state=1 and embf_name='"
				+ embf_name + "')";
		try {
			list = db.find(sql, EmActySupProductInfoModel.class,
					dbconn.parseSmap(EmActySupProductInfoModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 查询供应商报价产品
	public List<EmActySupProductInfoModel> getProductList(
			EmActySupProductInfoModel easm) {
		List<EmActySupProductInfoModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql = "select prod_supid,supp_name,prod_id,prod_name,prod_price,prod_discountprice "
				+ "from EmActySupplierInfo a inner join EmActySupProductInfo b on a.supp_id=b.prod_supid "
				+ " inner join EmActyCompact c on a.supp_id=c.eaco_supp_id"
				+ " where supp_state=1 and prod_state=1";
		if (easm.getProd_supid() != null)
			if (easm.getProd_supid() > 0) {
				sql = sql + " and prod_supid=" + easm.getProd_supid();
			}
		if (easm.getProd_id() != null) {
			if (easm.getProd_id() > 0) {
				sql = sql + " and prod_id=" + easm.getProd_id();
			}
		}

		if (easm.getEaco_id() != null && !easm.getEaco_id().equals("")) {
			sql = sql + " and eaco_id=" + easm.getEaco_id();
		}

		if (easm.getProd_eaco_id() != null
				&& !easm.getProd_eaco_id().equals("")) {
			sql = sql + " and prod_eaco_id=" + easm.getProd_eaco_id();
		}

		if (easm.isBstate() != null) {
			if (easm.isBstate()) {
				sql += " and prod_eaco_id>0 and eaco_id=prod_eaco_id";
			} else {
				sql += " and prod_eaco_id is null";
			}
		}

		System.out.println(sql);

		try {
			list = db.find(sql, EmActySupProductInfoModel.class, null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

	// 查询供应商列表
	public List<EmActySupplierInfoModel> getSIList(EmActySupplierInfoModel em) {
		List<EmActySupplierInfoModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql = "select supp_id, supp_name, supp_address, supp_mobile, "
				+ "supp_addname,supp_addtime, supp_website, supp_remark, supp_state"
				+ " from EmActySupplierInfo where 1=1";
		if (em.getSupp_state() != null) {
			sql = sql + " and supp_state=" + em.getSupp_state();
		}
		if (em.getTypes() != null) {
			sql += " and supp_type like '%" + em.getTypes() + "%'";
		}

		sql = sql + " order by supp_name";

		System.out.println(sql);
		try {
			list = db.find(sql, EmActySupplierInfoModel.class, null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;

	}

	// 查询产品对应供应商
	public List<EmBenefitRelationModel> getSupplyListById(Integer id) {
		List<EmBenefitRelationModel> list = new ListModelList<>();
		dbconn db = new dbconn();

		String sql = "select ebre_id,embf_name,supp_name "
				+ " from EmBenefitRelation a "
				+ " inner join EmBenefit b on a.ebre_embf_id=b.embf_id"
				+ " inner join EmActySupplierInfo c on a.ebre_supp_id=c.supp_id"
				+ " where ebre_state=1 and embf_state=1 and supp_state=1 and embf_id=?";
		try {
			list = db.find(sql, EmBenefitRelationModel.class, null, id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	// 查询福利对应供应商产品列表
	public List<EmActySupProductInfoModel> getProductList(Integer embfId) {
		List<EmActySupProductInfoModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql = "select prod_id, prod_supid, prod_eaco_id, prod_name, prod_price, "
				+ "prod_discountprice, prod_discount, prod_totalnum, prod_addname, "
				+ "prod_addtime, prod_state, prod_remark, prod_modtime, prod_modname"
				+ " from EmBenefitRelation a"
				+ " inner join EmActySupProductInfo b on a.ebre_supp_id=b.prod_supid"
				+ " where prod_state=1 and prod_eaco_id>0 and ebre_state=1 and ebre_embf_id=?";

		sql = sql + " order by prod_name";
		System.out.println("sql=" + sql);
		System.out.println("embfId=" + embfId);
		try {
			list = db.find(sql, EmActySupProductInfoModel.class, null, embfId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	// 查询供应商合同产品列表
	public List<EmActySupProductInfoModel> getSPList(
			EmActySupProductInfoModel em) {
		List<EmActySupProductInfoModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql = "select prod_id, prod_supid, prod_eaco_id, prod_name, prod_price, "
				+ "prod_discountprice, prod_discount, prod_totalnum, prod_addname, "
				+ "prod_addtime, prod_state, prod_remark, prod_modtime, prod_modname"
				+ " FROM EmActySupProductInfo where 1=1";
		if (em.getProd_id() != null) {
			sql = sql + " and prod_id=" + em.getProd_id();
		}

		if (em.getProd_supid() != null) {
			sql = sql + " and prod_supid=" + em.getProd_supid();
		}
		if (em.getProd_state() != null) {
			sql = sql + " and prod_state=" + em.getProd_state();
		}

		if (em.isBstate()) {
			sql = sql + " and prod_eaco_id>0";
		}

		sql = sql + " order by prod_name";
		System.out.println("sql=" + sql);
		try {
			list = db.find(sql, EmActySupProductInfoModel.class, null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

}
