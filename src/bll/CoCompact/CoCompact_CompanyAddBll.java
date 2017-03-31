package bll.CoCompact;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import Model.CoBaseModel;
import Model.CoCompactModel;
import Model.RoleListModel;
import dal.CoBase.CoBase_OperateDal;
import dal.CoBase.CoBase_SelectDal;
import dal.CoCompact.CoCompact_CompanyAddDal;
import dal.CoQuotation.CoofferDal;
import dal.CoQuotation.CoofferOperateDal;

public class CoCompact_CompanyAddBll {
	private Integer y;
	private CoCompactModel reg;
	private CoCompact_CompanyAddDal data;

	public void Compact_CompanyAdd(String cid, int coco_wttype,
			String coco_compacttype, String coco_class, String inuredate,
			String indate, String coco_delay, String signdate,
			String coco_money, String coco_invoice, String coco_compactid,
			String city, String agency, String coco_remark, String username,
			String coco_shebao, String coco_house, String cpp,
			String coco_gzmonth, String coco_gsmonth, int coco_sbfee,
			int coco_housefee, int coco_sbperfee, int coco_gsfee,
			int coco_gzpay, String fileid) {

		reg = new CoCompactModel(cid, coco_wttype, coco_compacttype,
				coco_class, inuredate, indate, coco_delay, signdate,
				coco_money, coco_invoice, coco_compactid, city, agency,
				coco_remark, username, coco_shebao, coco_house, cpp,
				coco_gzmonth, coco_gsmonth, coco_sbfee, coco_housefee,
				coco_sbperfee, coco_gsfee, coco_gzpay, fileid);
		
	}
	
	//获取公司信息
	public CoBaseModel SearchCobase(Integer cid){
		CoBase_SelectDal dal = new CoBase_SelectDal();
		CoBaseModel cm = new CoBaseModel();
		cm=dal.getCobaseByCid(cid);
		return cm;
		
	}
	
	//独立户生成任务需要的报价单数据
	public boolean addCoofferData(Integer cid,Integer coid,String sb,String house){
		boolean b= false;
		CoofferOperateDal dal = new CoofferOperateDal();
		if ((sb!=null && sb.equals("独立开户")) || (house!=null && house.equals("独立开户"))) {
			boolean s=sb.equals("独立开户")?true:false;
			boolean h=house.equals("独立开户")?true:false;
			Integer i=dal.addData(cid, coid, s, h);
			b=i>0?true:false;
		}
		
		return b;
	}

	// Date类型转换String
	public String DatetoSting(Date d) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String Date = sdf.format(d);
		return Date;
	}

	// 查询城市
	public List<RoleListModel> deplist(String city_name) throws SQLException {
		CoCompact_CompanyAddDal dal = new CoCompact_CompanyAddDal();
		List<RoleListModel> list = dal.deplist(city_name);
	//	System.out.println("234234");
	//	System.out.println(city_name);
		return list;
	}

	public Integer Dochek() {
		data = new CoCompact_CompanyAddDal();

		Integer y = 0;
		try {
			y = data.AddReg(reg);
		} catch (Exception e) {

			e.printStackTrace();
		}
		return y;
	}

	public void setY(Integer y) {
		this.y = y;
	}

	// 判断是否已存在合同号
	public int checkCompactID(String compactID) throws SQLException {
		int result = 0;
		data = new CoCompact_CompanyAddDal();
		
		try {
			result = data.checkCompactID(compactID);
		} catch (Exception e) {
			e.printStackTrace();
			result = 2;
		}
		return result;
	}
}
