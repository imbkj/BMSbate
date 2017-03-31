/**
 * @Classname CoBase_SelectBll
 * @ClassInfo 公司基本信息数据库查询类
 * @author 林少斌、陈耀家
 * @Date 2013-11-27
 */
package bll.CoBase;

import impl.CheckStringImpl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.zkoss.zul.ListModelList;

import service.CheckStringService;

import dal.CoBase.CoBase_SelectDal;
import dal.CoCompact.CoCompactBaseDal;
import dal.CoHousingFund.CoHousingFund_ListDal;
import dal.CoQuotation.CoofferDal;
import dal.CoSocialInsurance.CoSocialInsurance_ListDal;
import dal.Embase.CoglistDal;

import Model.CoBaseModel;
import Model.CoCompactModel;
import Model.CoHousingFundModel;
import Model.CoOfferModel;
import Model.CoShebaoModel;
import Model.CoglistModel;
import Model.EmHouseUpdateModel;
import Model.EmShebaoUpdateModel;
import Util.UserInfo;

public class CoBase_SelectBll {
	private static CoBase_SelectDal dal = new CoBase_SelectDal();

	// 根据传入的查询条件查询公司信息，查询条件有cid请表明确cid所在的表
	public List<CoBaseModel> getCobaseinfo(String str) {
		// String sql = checkEmbaseSearchstr(str);
		return dal.getCobaseinfo(str);
	}

	// 无权限限制查询
	public List<CoBaseModel> getCobaseinfoforall(String str) {
		String sql = checkEmbaseSearchstr(str);
		return dal.getCobaseinfoforall(sql);
	}

	// 根据传入的查询条件查询可修改的公司信息，查询条件有cid请表明确cid所在的表
	public List<CoBaseModel> getCobaseeditinfo(String str) {
		// String sql = checkEmbaseSearchstr(str);
		return dal.getCobaseeditinfo(str);
	}

	// 根据cid查询公司信息
	public CoBaseModel getCobaseByCid(int cid) {
		return dal.getCobaseByCid(cid);
	}

	// 获取客服列表
	public List<CoBaseModel> getCobaseClient(String str) {
		return dal.getCobaseClient(str);
	}

	/**
	 * 根据cid查询社保信息
	 * 
	 * @param cid
	 * @return List<EmShebaoUpdateModel>
	 */
	public List<EmShebaoUpdateModel> getShebaoInfoByCid(int cid,int type) {
		return dal.getShebaoInfoByCid(cid,type);
	}
	
	/**
	 * 根据cid查询社保信息AllinOne页面
	 * 
	 * @param cid
	 * @return List<EmShebaoUpdateModel>
	 */
	public List<EmShebaoUpdateModel> getShebaoInfoByCidAllinOne(int cid,Integer type) {
		return dal.getShebaoInfoByCidAllinOne(cid,type);
	}

	/**
	 * 根据cid查询公积金信息
	 * 
	 * @param cid
	 * @return List<EmHouseUpdateModel>
	 */
	public List<EmHouseUpdateModel> getGjjInfoByCid(int cid) {
		return dal.getGjjInfoByCid(cid);
	}
	
	/**
	 * 根据cid查询公积金信息
	 * 
	 * @param cid
	 * @return List<EmHouseUpdateModel>
	 */
	public List<EmHouseUpdateModel> getGjjInfoByCid(int cid,int type) {
		return dal.getGjjInfoByCid(cid,type);
	}
	
	/**
	 * 根据cid查询公积金信息AllinOne
	 * 
	 * @param cid
	 * @return List<EmHouseUpdateModel>
	 */
	public List<EmHouseUpdateModel> getGjjInfoByCidAllinOne(int cid,Integer type) {
		return dal.getGjjInfoByCidAllinOne(cid,type);
	}
	

	// 根据传入的查询条件查询公司信息，查询条件有cid请表明确cid所在的表
	public List<CoBaseModel> getshCobaseinfo(String str) {
		String sql = checkEmbaseSearchstr(str);
		return dal.getCobaseinfo(sql);
	}

	// 员工列表查询拼接SQL
	private static String checkEmbaseSearchstr(String str) {
		CheckStringService ch = new CheckStringImpl();
		StringBuilder sql = new StringBuilder();
		str=str.trim();
		if (!str.equals("") & str != null) {

			if (ch.isNum(str)) {

				sql.append(" and ( a.cid=");
				sql.append(str);
				sql.append(")");

			} else {
				sql.append(" and (a.coba_company like '%");
				sql.append(str);
				sql.append("%' or a.coba_shortname like '%");
				sql.append(str);
				sql.append("%')");

			}
		}
		return sql.toString();

	}

	// 根据cid查询合同的信
	public List searchCoCoBase(String sql) {
		List cobaseinfo = new ArrayList<CoCompactModel>();
		CoCompactBaseDal codal = new CoCompactBaseDal();
		cobaseinfo = codal.getCoCoBaseList(sql);
		return cobaseinfo;
	}

	// 按条件查询公司列表(综合性查询)
	public static String[] searchcobaselists() {
		String[] dictionary = dal.getcobaList();
		return dictionary;
	}

	/**
	 * 根据cid获取合同列表
	 * 
	 * @param cid
	 * @return
	 */
	public List<CoCompactModel> getcompactList(Integer cid) {
		return dal.getcompactList(cid);
	}
	
	/**
	 * 根据cid获取合同列表
	 * 
	 * @param cid
	 * @return
	 */
	public List<CoCompactModel> getcompactLists(Integer cid) {
		return dal.getcompactList(cid);
	}

	/**
	 * 获取报价单列表
	 * 
	 * @param str
	 * @return
	 */
	public List<CoOfferModel> getcoofferList(String str) {
		return dal.getcoofferList(str);
	}

	/**
	 * 获取员工列表
	 * 
	 * @param str
	 * @return
	 */
	public List<CoOfferModel> getEmbaseList(String str) {
		return dal.getEmbaseList(str);
	}

	// 根据查询客户的第一份合同生效时间
	public Date getCocoInuredate(Integer cid) {
		return dal.getCocoInuredate(cid);
	}

	// 获取社保独立账户列表
	public List<CoShebaoModel> getCoShebaoList(String wherestr) {
		CoSocialInsurance_ListDal dal2 = new CoSocialInsurance_ListDal();
		return dal2.getCoShebaoList(wherestr);
	}
	
	/**
	 * 获取单位公积金在册列表
	 * 
	 * @param str
	 * @return
	 */
	public List<CoHousingFundModel> getCoHoList(String str, boolean mod) {
		CoHousingFund_ListDal dal2 = new CoHousingFund_ListDal();
		return dal2.getCoHoList(str, mod);
	}
	
	
	//查询是否有员工选择报价单内项目
	public boolean HaveItem(Integer id){
		boolean b= false;
		List<CoglistModel> list = new ListModelList<>();
		CoglistDal dal2 = new CoglistDal();

		list=dal2.haveItem(id);
		if (list.size()>0) {
			b=true;
		}
		return b;
	}
	
	//删除报价单
	public boolean delCooffer(Integer id){
		CoofferDal dal2 = new CoofferDal();
		CoOfferModel m = new CoOfferModel();
		m.setCoof_state(6);
		m.setCoof_modname(UserInfo.getUsername());
		Integer i =dal2.mod(m, id);
		if (i>0) {
			return true;
		}else {
			return false;
		}
	}
}
