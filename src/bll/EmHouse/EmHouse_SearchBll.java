package bll.EmHouse;

import java.sql.SQLException;
import java.util.List;

import org.zkoss.zul.ListModelList;

import Model.CoBaseModel;
import Model.CoHousingFundModel;
import Model.EmHouseChangeModel;
import Model.EmHouseCpp;
import Model.EmbaseModel;
import Model.loginroleModel;
import Util.ConstantsUtil;
import dal.CoBase.CoBase_SelectDal;
import dal.CoHousingFund.CoHousingFund_ListDal;
import dal.EmHouse.EmHouseChangeDal;
import dal.Embase.Embasedal;
import dal.SystemControl.UserListDal;

public class EmHouse_SearchBll {
	String nowmonth = ConstantsUtil.ownmonth;

	// 查询公司信息
	public List<CoBaseModel> getcompanylist(String name) {
		List<CoBaseModel> list = new ListModelList<>();
		CoBase_SelectDal dal = new CoBase_SelectDal();
		list = dal.getInfoListByName("%" + name + "%");
		return list;
	}

	// 查询独立户信息
	public List<CoBaseModel> getsingleList(String name) {
		List<CoBaseModel> list = new ListModelList<>();
		CoBase_SelectDal dal = new CoBase_SelectDal();
		list = dal.getsingleListByName("%" + name + "%");
		return list;
	}
	//查询缴存银行列表
	public List<CoHousingFundModel> getjcList(){
		List<CoHousingFundModel> list = new ListModelList<>();
		CoHousingFund_ListDal dal = new CoHousingFund_ListDal();
		list=dal.getjcList();
		return list;
	}

	// 查询员工信息
	public List<EmbaseModel> getembaseList(String cid, String name) {
		List<EmbaseModel> list = new ListModelList<>();
		Embasedal dal = new Embasedal();
		try {
			list = dal.getInfoListByName("%" + cid + "%", "%" + name + "%");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	// 获取比例列表
	public List<EmHouseCpp> getcppList() {
		List<EmHouseCpp> list = new ListModelList<>();

		for (int i = 5; i < 13; i++) {
			EmHouseCpp ec = new EmHouseCpp();
			ec.setCp(((double) i) / 100);
			ec.setCpName(i + "%");
			list.add(ec);
		}

		return list;
	}

	// 获取所属月份列表
	public List<EmHouseChangeModel> getownmontList() {
		List<EmHouseChangeModel> list = new ListModelList<>();
		EmHouseChangeDal dal = new EmHouseChangeDal();
		try {
			list = dal.getDistinList("ownmonth", "desc");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	// 获取客服列表
	public List<loginroleModel> getclientList(String dept) {
		List<loginroleModel> list = new ListModelList<>();
		UserListDal dal = new UserListDal();
		try {
			list = dal.getdistinctColumn("log_name", "", dept);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	// 获取添加人
	public List<EmHouseChangeModel> getaddnamelist() {
		List<EmHouseChangeModel> list = new ListModelList<>();
		EmHouseChangeDal dal = new EmHouseChangeDal();
		try {
			list = dal.getDistinList("emhc_addname", "");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	// 获取当月变更数
	public Integer getchangeNum() {
		Integer i = 0;
		EmHouseChangeDal dal = new EmHouseChangeDal();

		i = dal.getNum(nowmonth, "0", null).get(0).getNum()
				+ dal.getNum(nowmonth, "1", null).get(0).getNum()
				+ dal.getNum(nowmonth, "2", null).get(0).getNum();

		return i;
	}

	// 获取当月已处理数
	public Integer getfinishNum() {

		Integer i = 0;
		EmHouseChangeDal dal = new EmHouseChangeDal();

		i = dal.getNum(nowmonth, "1", null).get(0).getNum();

		return i;
	}

	// 获取当月中智大户变更数
	public Integer getzzNum() {

		Integer i = 0;
		EmHouseChangeDal dal = new EmHouseChangeDal();

		i = dal.getNum(nowmonth, "0", "0").get(0).getNum()
				+ dal.getNum(nowmonth, "1", "0").get(0).getNum()
				+ dal.getNum(nowmonth, "2", "0").get(0).getNum();

		return i;
	}

	// 获取当月委托中智户变更数
	public Integer getzzwtNum() {

		Integer i = 0;
		EmHouseChangeDal dal = new EmHouseChangeDal();

		i = dal.getNum(nowmonth, "0", "2").get(0).getNum()
				+ dal.getNum(nowmonth, "1", "2").get(0).getNum()
				+ dal.getNum(nowmonth, "2", "2").get(0).getNum();

		return i;
	}

	// 获取当月独立户变更数
	public Integer getsingleNum() {
		Integer i = 0;
		EmHouseChangeDal dal = new EmHouseChangeDal();

		i = dal.getNum(nowmonth, "0", "1").get(0).getNum()
				+ dal.getNum(nowmonth, "1", "1").get(0).getNum()
				+ dal.getNum(nowmonth, "2", "1").get(0).getNum();

		return i;
	}
}
