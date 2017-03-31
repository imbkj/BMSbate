package bll.CoQuotation;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.zkoss.zul.ListModelList;

import Model.CoAgencyBaseModel;
import Model.CoCompactModel;
import Model.CoOLModeModel;
import Model.CoOfferListModel;
import Model.CoOfferModel;
import Model.CoPFeeclassModel;
import Model.CoPclassModel;
import Model.CoProductModel;
import Model.CopCompactModel;
import Model.PubProCityModel;
import Util.UserInfo;
import dal.CoCompact.CoCompactBaseDal;
import dal.CoQuotation.CoQuotation_AddDal;
import dal.CoQuotation.CoofferDal;

public class CoQuotation_AddBll {

	// 按ID查询合同信息
	public List<CoCompactModel> getcompact(Integer coco_id) {
		List<CoCompactModel> list = new ListModelList<>();
		CoCompactBaseDal dal = new CoCompactBaseDal();
		list = dal.getcocompactList(coco_id);
		return list;
	}

	// 获取产品类型列表
	public List<String> getClassList() throws SQLException {
		List<String> list = new ArrayList<String>();
		CoQuotation_AddDal dal = new CoQuotation_AddDal();
		list = dal.getClassList();
		return list;
	}

	// 根据产品id获取收费单位列表
	public List<CoPFeeclassModel> getFeeClassList(int copr_id)
			throws SQLException {
		List<CoPFeeclassModel> list = new ArrayList<CoPFeeclassModel>();
		CoQuotation_AddDal dal = new CoQuotation_AddDal();
		list = dal.getFeeClassList(copr_id);
		return list;
	}

	// 获取城市列表
	public ListModelList<PubProCityModel> getCityList() throws SQLException {
		ListModelList<PubProCityModel> list = new ListModelList<PubProCityModel>();
		CoQuotation_AddDal dal = new CoQuotation_AddDal();
		list = dal.getCityList();
		return list;
	}

	// 根据产品类型和城市获取产品信息列表
	public List<CoOfferListModel> getCoProductList(String pclass, String city)
			throws SQLException {
		List<CoOfferListModel> list = new ArrayList<CoOfferListModel>();
		CoQuotation_AddDal dal = new CoQuotation_AddDal();
		list = dal.getCoProductList(pclass, city);
		return list;
	}

	// 根据产品id，收费单位id查询默认收费金额
	public CoProductModel getFee(int copr_id, int cpfc_id) throws SQLException {
		CoProductModel model = new CoProductModel();
		CoQuotation_AddDal dal = new CoQuotation_AddDal();
		model = dal.getFee(copr_id, cpfc_id);
		return model;
	}

	// 根据产品id获取享受方式列表
	public List<CoOfferListModel> getStandardList(int copr_id)
			throws SQLException {
		List<CoOfferListModel> list = new ArrayList<CoOfferListModel>();
		CoQuotation_AddDal dal = new CoQuotation_AddDal();
		list = dal.getStandardList(copr_id);
		return list;
	}

	// 查询报价单列表
	public List<CoOfferModel> coofferList(Integer cocoId) {
		List<CoOfferModel> list = new ListModelList<>();
		CoofferDal dal = new CoofferDal();

		list = dal.getCoofferByCoid(cocoId);

		return list;
	}

	// 新增报价单
	public CoOfferModel CoOfferAdd(CoOfferModel model) throws SQLException {
		CoOfferModel model1 = new CoOfferModel();
		CoQuotation_AddDal dal = new CoQuotation_AddDal();
		model1 = dal.CoOfferAdd(model);
		return model1;
	}

	// 新增报价单项目
	public void CoOfferListAdd(List<CoOfferListModel> list) throws SQLException {

		CoQuotation_AddDal dal = new CoQuotation_AddDal();
		for (CoOfferListModel m : list) {
			dal.CoOfferListAdd(m);
		}
	}

	// 获取产品列表
	public List<CoProductModel> getCoproductList(String str)
			throws SQLException {
		List<CoProductModel> list = new ArrayList<CoProductModel>();
		CoQuotation_AddDal dal = new CoQuotation_AddDal();
		list = dal.getCoproductList(str);
		return list;
	}

	// 获取产品列表 --修改显示外地产品金额
	public List<CoProductModel> getCoproductListzmj(String str)
			throws SQLException {
		List<CoProductModel> list = new ArrayList<CoProductModel>();
		CoQuotation_AddDal dal = new CoQuotation_AddDal();
		list = dal.getCoproductListzmj(str);
		return list;
	}

	// 获取产品默认金额/单位
	public List<CoProductModel> getCoproductFee(Integer id) {
		List<CoProductModel> list = new ArrayList<CoProductModel>();
		CoQuotation_AddDal dal = new CoQuotation_AddDal();
		list = dal.getCoproductFee(id);
		return list;
	}

	// 根据城市id获取委托机构列表
	public List<CoAgencyBaseModel> getCoAgencyList(int id) throws Exception {
		List<CoAgencyBaseModel> list = new ArrayList<CoAgencyBaseModel>();
		CoQuotation_AddDal dal = new CoQuotation_AddDal();
		list = dal.getCoAgencyList(id);
		return list;
	}

	// 获取产品临时表的大类列表
	public List<CoOfferListModel> getCoOfferListTemList(int coli_coof_id)
			throws Exception {
		List<CoOfferListModel> list = new ArrayList<CoOfferListModel>();
		CoQuotation_AddDal dal = new CoQuotation_AddDal();
		list = dal.getCoOfferListTemList(coli_coof_id);
		return list;
	}

	// 产品详情更新
	public int CoOfferListUpdate(List<CoOfferListModel> list, Integer ty) {
		int su = 0;
		int row = 0;
		int a = 0;
		int k=0;
		CoQuotation_AddDal dal = new CoQuotation_AddDal();

		try {
			for (int i = 0; i < list.size(); i++) {

				k= dal.CoOfferListUpdate(list.get(i));
				row=row+k;
				a++;
				if (row != a && k==0) {
					System.out.print("更新出错:列表行数" + list.size() + ",出错行:" + i);
					System.out.print("Coli_id:" + list.get(i).getColi_id());
					System.out.print("Coli_standard:"
							+ list.get(i).getColi_standard());
					System.out.print("Coli_remark:"
							+ list.get(i).getColi_remark());
					System.out.print("Coli_amount:"
							+ list.get(i).getColi_amount());
					System.out.print("Coli_fee:" + list.get(i).getColi_fee());
					System.out.print("Coli_Coli_cpfc_name:"
							+ list.get(i).getColi_cpfc_name());
					System.out.print("Coli_group_id:"
							+ list.get(i).getColi_group_id());
					System.out.print("Coli_group_count:"
							+ list.get(i).getColi_group_count());
					System.out.print("Coli_Coli_feetype:"
							+ list.get(i).getColi_feetype());
				}
				if (list.get(i).getInfoList().size() > 0) {
					for (int j = 0; j < list.get(i).getInfoList().size(); j++) {
						k= dal.CoOfferListUpdate(list.get(i).getInfoList()
								.get(j));
						row=row+k;
						a++;
						if (row != a && k==0) {
							System.out.print("更新出错:列表行数"
									+ list.get(i).getInfoList().size()
									+ ",出错行:[" + i + "," + j + "]");
							System.out.print("Coli_id:"
									+ list.get(i).getInfoList().get(j)
											.getColi_id());
							System.out.print("Coli_standard:"
									+ list.get(i).getInfoList().get(j)
											.getColi_standard());
							System.out.print("Coli_remark:"
									+ list.get(i).getInfoList().get(j)
											.getColi_remark());
							System.out.print("Coli_amount:"
									+ list.get(i).getInfoList().get(j)
											.getColi_amount());
							System.out.print("Coli_fee:"
									+ list.get(i).getInfoList().get(j)
											.getColi_fee());
							System.out.print("Coli_Coli_cpfc_name:"
									+ list.get(i).getInfoList().get(j)
											.getColi_cpfc_name());
							System.out.print("Coli_group_id:"
									+ list.get(i).getInfoList().get(j)
											.getColi_group_id());
							System.out.print("Coli_group_count:"
									+ list.get(i).getInfoList().get(j)
											.getColi_group_count());
							System.out.print("Coli_Coli_feetype:"
									+ list.get(i).getInfoList().get(j)
											.getColi_feetype());
						}
						if (list.get(i).getInfoList().get(j).getCoolmodel() != null) {
							addcoolm(list.get(i).getInfoList().get(j), ty);
						}

					}
				}

			}
			if (row == a) {
				su = 1;// 成功
			} else {
				su = 2;// 失败
			}
		} catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
		}

		return su;
	}

	// 报价单正式提交
	public int coOfferSubmit(List<CoOfferListModel> list, CoOfferModel cfModel) {
		int su = 0;
		// int num = 0;
		int row = 0;
		CoQuotation_AddDal dal = new CoQuotation_AddDal();

		su = CoOfferListUpdate(list, 1);

		try {
			if (su == 1) {
			//	CoOfferListTemfwfDelete(cfModel.getCoof_id());
				row = dal.CoOfferSubmit(cfModel);

				if (row != 1) {
					su = 3;// 失败
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return su;
	}

	private void addcoolm(CoOfferListModel mk, Integer ty) {
		CoQuotation_AddDal sdal = new CoQuotation_AddDal();
		CoOfferListModel mo = mk;
		List<CoOLModeModel> li = sdal.getcoolmodeinfo(" and colm_coli_id="
				+ mo.getColi_id());
		Integer jnum = li.size();
		CoOLModeModel m = mk.getCoolmodel();
		if (m.getColm_selectid() != null) {
			CoOLModeModel ml = new CoOLModeModel();
			ml.setColm_coli_id(mo.getColi_id());
			ml.setColm_name(mo.getColi_standard());
			ml.setColm_enjoytype(m.getColm_enjoytype());
			ml.setColm_selectid(m.getColm_selectid());
			ml.setColm_state(ty);
			ml.setColm_addname(UserInfo.getUsername());
			ml.setColm_id(m.getColm_id());
			if (m.getColm_selectid() == 1) {
				ml.setColm_startmonth(0);
				ml.setColm_stopmonth(0);
				ml.setColm_stargivemonth(m.getInt11());
				ml.setColm_giveonemonth(m.getInt12());
				if (jnum <= 0) {
					CoOLModeAdd(ml, ty);
				} else {
					CoOLModeupdate(ml, ty);
				}
			} else if (m.getColm_selectid() == 2) {
				ml.setColm_startmonth(0);
				ml.setColm_stopmonth(0);
				ml.setColm_stargivemonth(m.getInt22());
				ml.setColm_giveonemonth(m.getInt23());
				if (m.getInt21() != null) {
					BigDecimal bdd = new BigDecimal(m.getInt21());
					ml.setColm_fee(bdd);
				}
				if (jnum <= 0) {
					CoOLModeAdd(ml, ty);
				} else {
					CoOLModeupdate(ml, ty);
				}
			} else if (m.getColm_selectid() == 3) {
				ml.setColm_startmonth(m.getInt31());
				ml.setColm_stopmonth(0);
				ml.setColm_stargivemonth(m.getInt32());
				ml.setColm_giveonemonth(m.getInt33());
				BigDecimal bdd = new BigDecimal(0);
				ml.setColm_fee(bdd);
				if (jnum <= 0) {
					CoOLModeAdd(ml, ty);
				} else {
					CoOLModeupdate(ml, ty);
				}
			} else if (m.getColm_selectid() == 4) {
				ml.setColm_startmonth(m.getInt41());
				ml.setColm_stopmonth(0);
				ml.setColm_stargivemonth(m.getInt43());
				ml.setColm_giveonemonth(m.getInt44());
				if (m.getInt42() != null) {
					BigDecimal bdd = new BigDecimal(m.getInt42());
					ml.setColm_fee(bdd);
				}
				if (jnum <= 0) {
					CoOLModeAdd(ml, ty);
				} else {
					CoOLModeupdate(ml, ty);
				}
			} else if (m.getColm_selectid() == 5) {
				CoOLModeModel mx = mk.getCoolmodel1();
				CoOLModeModel m2 = new CoOLModeModel();
				ml.setColm_startmonth(0);
				ml.setColm_stopmonth(m.getInt51());
				ml.setColm_stargivemonth(m.getInt53());
				ml.setColm_giveonemonth(m.getInt54());
				ml.setColm_id(m.getColm_id());
				if (m.getInt52() != null) {
					BigDecimal bdd = new BigDecimal(m.getInt52());
					ml.setColm_fee(bdd);
				}
				m2.setColm_coli_id(mo.getColi_id());
				m2.setColm_name(mo.getColi_standard());
				m2.setColm_enjoytype(m.getColm_enjoytype());
				m2.setColm_selectid(m.getColm_selectid());
				m2.setColm_state(ty);
				m2.setColm_addname(UserInfo.getUsername());
				m2.setColm_startmonth(mx.getInt55());
				m2.setColm_stopmonth(0);
				m2.setColm_stargivemonth(mx.getInt57());
				m2.setColm_giveonemonth(mx.getInt58());
				m2.setColm_id(mx.getColm_id());
				if (mx.getInt56() != null) {
					BigDecimal bj = new BigDecimal(mx.getInt56());
					m2.setColm_fee(bj);
				}
				if (jnum <= 0) {
					CoOLModeAdd(ml, ty);
					CoOLModeAdd(m2, ty);
				} else {
					CoOLModeupdate(ml, ty);
					CoOLModeupdate(m2, ty);
				}
			}
		}
	}

	// 改变临时表中的状态
	public int CoOfferListTemModState(int coof_id, int coli_state)
			throws Exception {
		int row = 0;
		CoQuotation_AddDal dal = new CoQuotation_AddDal();
		row = dal.CoOfferListTemModState(coof_id, coli_state);
		return row;
	}

	// 删除临时表中状态为0的数据
	public int CoOfferListTemDelete(int coof_id) throws Exception {
		int row = 0;
		CoQuotation_AddDal dal = new CoQuotation_AddDal();
		row = dal.CoOfferListTemDelete(coof_id);
		return row;
	}

	// 删除报价单项目表中没有项目的大类及服务费
	public Integer CoOfferListNoPclassDelete(Integer coli_coof_id) {
		return new CoQuotation_AddDal().CoOfferListNoPclassDelete(coli_coof_id);
	}

	// 删除临时表中金额为0的服务费项目
	public int CoOfferListTemfwfDelete(int coof_id) throws Exception {
		int row = 0;
		CoQuotation_AddDal dal = new CoQuotation_AddDal();
		row = dal.CoOfferListTemfwfDelete(coof_id);
		return row;
	}

	// 清除无效的报价单数据
	public void ClearCoOffer() throws SQLException {
		CoQuotation_AddDal dal = new CoQuotation_AddDal();
		dal.ClearCoOffer();
	}

	// 获取产品类型列表
	public List<CoPclassModel> getclass(String str) throws SQLException {
		CoQuotation_AddDal dal = new CoQuotation_AddDal();
		List<CoPclassModel> list = dal.getclassList(str);
		return list;
	}

	// 获取产品类型内未分组的产品列表
	public List<CoOfferListModel> getSpanList(Integer coli_id) {
		return new CoQuotation_AddDal().getSpanList(coli_id);
	}

	// 添加享受条件
	public Integer CoOLModeAdd(CoOLModeModel m, Integer ty) {
		CoQuotation_AddDal dal = new CoQuotation_AddDal();
		return dal.CoOLModeAdd(m, ty);
	}

	// 更新享受条件
	public Integer CoOLModeupdate(CoOLModeModel m, Integer f) {
		CoQuotation_AddDal dal = new CoQuotation_AddDal();
		return dal.CoOLModeupdate(m, f);
	}

	// 获取该客户公司已有的报价单列表
	public List<CoOfferModel> getcoofList(Integer cola_id, Integer cid) {
		CoQuotation_AddDal dal = new CoQuotation_AddDal();
		List<CoOfferModel> list = new ListModelList<>();
		list = dal.getcoofList(cola_id, cid);
		for (CoOfferModel m : list) {
			String gm = m.getCoof_gm();
			if (gm == null || gm.isEmpty()) {
				m.setCoof_name1(m.getCoof_name());
			} else {
				m.setCoof_name1(m.getCoof_name() + " -- " + m.getCoof_gm());
			}
		}
		return list;
	}
	
	
	// 获取该 报价单列表
	public List<CoOfferModel> getcoofListcoofid(Integer coco_id, Integer cid) {
		CoQuotation_AddDal dal = new CoQuotation_AddDal();
		List<CoOfferModel> list = new ListModelList<>();
		list = dal.getcoofListcoofid(coco_id, cid);
		for (CoOfferModel m : list) {
			String gm = m.getCoof_gm();
			if (gm == null || gm.isEmpty()) {
				m.setCoof_name1(m.getCoof_name());
			} else {
				m.setCoof_name1(m.getCoof_name() + " -- " + m.getCoof_gm());
			}
		}
		return list;
	}
	
	
	
	// 获取该 报价单列表
	public List<CoOfferModel> getcoofListcoofidth(Integer coco_id, Integer cid) {
		CoQuotation_AddDal dal = new CoQuotation_AddDal();
		List<CoOfferModel> list = new ListModelList<>();
		list = dal.getcoofListcoofidth(coco_id, cid);
		for (CoOfferModel m : list) {
			String gm = m.getCoof_gm();
			if (gm == null || gm.isEmpty()) {
				m.setCoof_name1(m.getCoof_name());
			} else {
				m.setCoof_name1(m.getCoof_name() + " -- " + m.getCoof_gm());
			}
		}
		return list;
	}
	

	// 获取报价单产品列表
	public List<CoOfferListModel> getCoOfferListList(Integer coof_id) {
		CoQuotation_AddDal dal = new CoQuotation_AddDal();
		return dal.getCoOfferListList(coof_id);
	}

	// 报价单新增下一步--合并数据处理
	public Integer NextDataHandle(Integer new_coof_id, Integer old_coof_id) {
		CoQuotation_AddDal dal = new CoQuotation_AddDal();
		return dal.NextDataHandle(new_coof_id, old_coof_id);
	}

	// 单独添加报价单项目
	public Integer addCoofferlist(CoOfferListModel cm) {
		CoQuotation_AddDal dal = new CoQuotation_AddDal();
		return dal.coofferlistAdd(cm);
	}

	/**
	 * 根据合同主键id获取合同类型
	 * 
	 * @param coco_id
	 * @return
	 */
	public CopCompactModel getCompactByCocoid(Integer coco_id) {
		CoQuotation_AddDal dal = new CoQuotation_AddDal();
		return dal.getCompactByCocoid(coco_id);
	}
}
