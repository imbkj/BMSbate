package bll.EmBenefit;

import java.util.List;

import org.zkoss.zul.ListModelList;

import Model.CoBaseModel;
import Model.EmActyProduceModel;
import Model.EmActyProducetypeModel;
import Model.EmBenefitModel;
import Model.EmWelfareModel;
import Model.EmbaseModel;
import dal.CoBase.CoBase_SelectDal;
import dal.EmBenefit.EmBenefitDal;
import dal.EmBenefit.EmWelfareDal;
import dal.Embase.Embasedal;

public class EmBenefit_comitEmListBll {

	// 获取公司列表
	public List<EmWelfareModel> getCompanyList(String name) {
		List<EmWelfareModel> list = new ListModelList<>();
		EmWelfareDal dal = new EmWelfareDal();

		list = dal.getCompanyList(name);

		return list;
	}

	// 获取客服列表
	public List<EmWelfareModel> getClientList(String name) {
		List<EmWelfareModel> list = new ListModelList<>();
		EmWelfareDal dal = new EmWelfareDal();
		list = dal.getClientList(name);

		return list;
	}

	// 获取员工列表
	public List<EmWelfareModel> getNameList(String name) {
		List<EmWelfareModel> list = new ListModelList<>();
		EmWelfareDal dal = new EmWelfareDal();
		list = dal.getNameList(name);

		return list;
	}

	// 获取福利项目列表
	public List<EmBenefitModel> getItemList(String name) {
		List<EmBenefitModel> list = new ListModelList<>();
		EmBenefitDal dal = new EmBenefitDal();
		list = dal.getNamelist(name);

		return list;
	}

	// 获取福利享受列表
	public List<EmWelfareModel> getStandardList() {
		List<EmWelfareModel> list = new ListModelList<>();
		EmWelfareDal dal = new EmWelfareDal();
		list = dal.getstandardlist();

		return list;
	}

	// 获取享受福利员工列表
	public List<EmWelfareModel> getList(EmWelfareModel ewfm) {
		List<EmWelfareModel> list = new ListModelList<>();
		EmWelfareDal dal = new EmWelfareDal();
		list = dal.getList(ewfm);

		return list;
	}

	// 确认数据
	public Integer commit(EmWelfareModel ewfm) {
		Integer i = 0;
		EmWelfareDal dal = new EmWelfareDal();
		i = dal.mod(ewfm);
		return i;
	}

	// 获取公司基本信息列表
	public List<CoBaseModel> companylist(String client) {
		List<CoBaseModel> list = new ListModelList<>();
		CoBase_SelectDal dal = new CoBase_SelectDal();
		list = dal.getcobaseFlList(client);
		return list;
	}

	// 获取公司客服列表
	public List<CoBaseModel> clientlist() {
		List<CoBaseModel> list = new ListModelList<>();
		CoBase_SelectDal dal = new CoBase_SelectDal();
		list = dal.getClientFlList();
		return list;
	}

	// 获取员工下拉列表
	public List<EmbaseModel> nameList(Integer cid, String client, String name) {
		List<EmbaseModel> list = new ListModelList<>();
		Embasedal dal = new Embasedal();
		list = dal.getnameFlList(cid, client, name);
		return list;
	}

	// 获取福利项目列表
	public List<EmBenefitModel> itemList() {
		List<EmBenefitModel> list = new ListModelList<>();
		EmBenefitDal dal = new EmBenefitDal();
		list = dal.getlist(null);
		return list;
	}

	// 获取员工列表
	public List<EmbaseModel> empList(Integer cid, String client, String name,
			Integer item) {
		List<EmbaseModel> list = new ListModelList<>();
		Embasedal dal = new Embasedal();
		list = dal.getflList(cid, client, name, item);
		return list;
	}

	// 新增福利确认人员名单
	public Integer add(EmbaseModel m) {

		EmWelfareDal dal = new EmWelfareDal();
		Integer i = dal.add(m);

		return i;
	}

	// 新增福利报销
	public Integer bx(EmWelfareModel em) {
		Integer i = 0;
		EmWelfareDal dal = new EmWelfareDal();
		i = dal.mod(em);
		return i;
	}

	public int isSameEmbenefit(String idList) {
		EmWelfareDal dal = new EmWelfareDal();
		return dal.isSameEmbenefit(idList);
	}

	// 分类统计产品信息
	public List<EmWelfareModel> getEmWelfareCount(String strs) {
		EmWelfareDal dal = new EmWelfareDal();
		return dal.getEmWelfareCount(strs);
	}

	// 删除数据
	public int deleteEmbenefit(int id) {
		EmWelfareDal dal = new EmWelfareDal();
		return dal.deleteEmbenefit(id);
	}

	// 查询是否是活动
	public boolean isGift(String idStr) {
		EmWelfareDal dal = new EmWelfareDal();
		return dal.isGift(idStr);
	}
}
