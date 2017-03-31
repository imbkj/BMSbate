package bll.CoFinanceManage;

import java.math.BigDecimal;
import java.util.List;

import org.xbill.DNS.CNAMERecord;
import org.zkoss.zul.ListModelList;

import dal.CoFinanceManage.CoInvoiceOperateDal;
import dal.CoFinanceManage.CoInvoiceSelectDal;
import Model.CoFinanceCollectModel;
import Model.CoFinanceMonthlyBillSortAccountModel;
import Model.CoInvoiceInfoModel;
import Model.CoInvoiceModel;
import Model.CoInvoiceRelationModel;
import Util.DateStringChange;
import Util.UserInfo;
import service.InvoiceService;

public class CoInvoiceImpl implements InvoiceService {

	@Override
	public List<CoInvoiceModel> search(Integer id) {
		List<CoInvoiceModel> list = new ListModelList<>();
		// TODO Auto-generated method stub
		CoInvoiceSelectDal dal = new CoInvoiceSelectDal();
		CoInvoiceModel cm = new CoInvoiceModel();
		cm.setCoin_id(id);
		list = dal.SearchList(cm);
		return list;
	}

	@Override
	public List<CoInvoiceModel> search(CoInvoiceModel cm) {
		// TODO Auto-generated method stub
		List<CoInvoiceModel> list = new ListModelList<>();
		CoInvoiceSelectDal dal = new CoInvoiceSelectDal();
		list = dal.SearchList(cm);
		return list;
	}

	@Override
	public List<CoInvoiceInfoModel> searchDetail(Integer id) {
		// TODO Auto-generated method stub
		List<CoInvoiceInfoModel> list = new ListModelList<>();
		CoInvoiceSelectDal dal = new CoInvoiceSelectDal();
		CoInvoiceInfoModel cm = new CoInvoiceInfoModel();
		cm.setCoii_coin_id(id);
		list = dal.SearchInfoList(cm);
		return list;
	}

	@Override
	public List<CoInvoiceRelationModel> searchRelation(Integer id) {
		// TODO Auto-generated method stub
		List<CoInvoiceRelationModel> list = new ListModelList<>();
		CoInvoiceSelectDal dal = new CoInvoiceSelectDal();
		CoInvoiceRelationModel cm = new CoInvoiceRelationModel();
		cm.setCire_coin_id(id);
		list = dal.SearchRelationList(cm);
		return list;
	}

	@Override
	public String createInvoiceId() {
		// TODO Auto-generated method stub
		String str = "";
		List<CoInvoiceModel> list = new ListModelList<>();
		CoInvoiceSelectDal dal = new CoInvoiceSelectDal();
		list = dal.maxId();
		if (list.size() > 0) {
			str = list.get(0).getCoin_invoiceid();
		}
		return str;
	}

	@Override
	public Integer add(CoInvoiceModel cm, List<CoInvoiceInfoModel> list,
			List<CoFinanceCollectModel> list2) {
		// TODO Auto-generated method stub
		Integer i = 0;
		Integer j = 0;// 发票和明细
		Integer j2 = 0;// 发票和收款
		Integer id = 0;
		CoInvoiceOperateDal dal = new CoInvoiceOperateDal();
		id = dal.add(cm);
		if (id > 0) {
			for (int k = 0; k < list2.size(); k++) {
				if (list2.get(k).isCheck()) {
					j2 = add(id, list2.get(k).getCfco_id());
				}
			}

			for (int k = 0; k < list.size(); k++) {
				if (list.get(k).getCoii_feetype() != null
						&& !list.get(k).getCoii_feetype().equals("")) {
					j = add(id, list.get(k));

					if (j.equals(0)) {
						i = -1;
						break;
					}
				}

			}
			if (i < 0) {
				i = delInvoice(id);
				if (i < 0) {
					i = -2;
				}
			} else {
				i = id;
			}
		}
		return i;
	}

	@Override
	public Integer add(Integer id, List<CoInvoiceInfoModel> list) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer add(Integer id, CoInvoiceInfoModel cm) {
		// TODO Auto-generated method stub
		CoInvoiceOperateDal dal = new CoInvoiceOperateDal();
		cm.setCoii_coin_id(id);
		Integer i = dal.addInfo(cm);
		return i;
	}

	@Override
	public Integer add(Integer coinId, Integer cfcoId) {
		// TODO Auto-generated method stub
		CoInvoiceOperateDal dal = new CoInvoiceOperateDal();
		Integer i = dal.addRelation(coinId, cfcoId);
		return i;
	}

	@Override
	public Integer mod(Integer id, CoInvoiceModel cm) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer mod(Integer id, CoInvoiceModel cm,
			List<CoInvoiceInfoModel> list) {
		// TODO Auto-generated method stub

		return null;
	}

	@Override
	public Integer mod(Integer id, CoInvoiceInfoModel cm) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer mod(Integer id, CoInvoiceModel cm,
			List<CoInvoiceInfoModel> list, List<CoFinanceCollectModel> list2) {
		// TODO Auto-generated method stub
		Integer i = 0;
		Integer j = 0;
		Integer k = 0;
		CoInvoiceOperateDal dal = new CoInvoiceOperateDal();
		i = dal.mod(id, cm);
		if (i > 0) {
			// 删除发票明细
			CoInvoiceInfoModel cm2 = new CoInvoiceInfoModel();
			cm2.setCoii_coin_id(id);
			delDetail(cm2);
			for (int l = 0; l < list.size(); l++) {

				// 添加明细
				if (list.get(l).getCoii_feetype() != null) {
					j = add(id, list.get(l));
					if (j < 0) {
						i = -1;
						break;
					}
				}

			}
			// 删除收款关系
			CoInvoiceRelationModel cm3 = new CoInvoiceRelationModel();
			cm3.setCire_coin_id(id);
			DelRelation(cm3);
			for (int l = 0; l < list2.size(); l++) {
				if (list2.get(l).isCheck()) {
					// 添加收款明细
					k = add(id, list2.get(l).getCfco_id());
					if (k < 0) {
						i = -2;
						break;
					}
				}
			}
		}

		return i;
	}

	@Override
	public Integer delInvoice(Integer id) {
		// TODO Auto-generated method stub
		Integer i = 0;
		Integer j = 0;
		CoInvoiceOperateDal dal = new CoInvoiceOperateDal();
		i = dal.del(id);
		if (i > 0) {
			CoInvoiceRelationModel cm = new CoInvoiceRelationModel();
			cm.setCire_coin_id(id);
			DelRelation(cm);

			CoInvoiceInfoModel cim = new CoInvoiceInfoModel();
			cim.setCoii_coin_id(id);
			j = delDetail(cim);
			if (j < 0) {
				i = -1;
			}
		}

		return i;
	}

	@Override
	public Integer delDetail(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer delDetail(CoInvoiceInfoModel cm) {
		// TODO Auto-generated method stub
		CoInvoiceOperateDal dal = new CoInvoiceOperateDal();
		Integer i = dal.DelInfoByCoinId(cm.getCoii_coin_id());
		return i;
	}

	@Override
	public Integer DelRelation(CoInvoiceRelationModel cm) {
		// TODO Auto-generated method stub
		CoInvoiceOperateDal dal = new CoInvoiceOperateDal();
		Integer i = dal.DelRelationByCoinId(cm.getCire_coin_id());
		return i;
	}

	@Override
	public List<CoInvoiceInfoModel> createInvoice(Integer cid) {
		// TODO Auto-generated method stub
		List<CoInvoiceInfoModel> list = new ListModelList<>();

		return null;
	}

	@Override
	public List<CoInvoiceInfoModel> createInvoice(List<String> list) {
		// TODO Auto-generated method stub
		return null;
	}

	private List<CoInvoiceInfoModel> sumList(
			List<CoFinanceMonthlyBillSortAccountModel> list) {
		List<CoInvoiceInfoModel> list2 = new ListModelList<>();

		BigDecimal[] fee = new BigDecimal[20];
		for (int i = 0; i < fee.length; i++) {
			fee[i] = new BigDecimal(0);
		}
		for (CoFinanceMonthlyBillSortAccountModel cm : list) {
			switch (cm.getCfsa_cpac_name()) {
			// t1
			case "服务费":
				fee[9] = fee[9].add(cm.getCfsa_PaidIn());
				fee[1] = fee[1].add(cm.getCfsa_PaidIn());
				fee[3] = fee[3].add(cm.getCfsa_PaidIn());

				break;
			// t2
			case "社保费":
				fee[0] = fee[0].add(cm.getCfsa_PaidIn());
				fee[1] = fee[1].add(cm.getCfsa_PaidIn());
				fee[3] = fee[3].add(cm.getCfsa_PaidIn());
				fee[5] = fee[5].add(cm.getCfsa_PaidIn());
				fee[6] = fee[6].add(cm.getCfsa_PaidIn());
				fee[7] = fee[7].add(cm.getCfsa_PaidIn());
				fee[11] = fee[11].add(cm.getCfsa_PaidIn());
				break;
			// t3
			case "商保费":
				fee[0] = fee[0].add(cm.getCfsa_PaidIn());
				fee[1] = fee[1].add(cm.getCfsa_PaidIn());
				fee[3] = fee[3].add(cm.getCfsa_PaidIn());
				fee[7] = fee[7].add(cm.getCfsa_PaidIn());
				fee[14] = fee[14].add(cm.getCfsa_PaidIn());
				break;
			// t4
			case "残保金":
				fee[0] = fee[0].add(cm.getCfsa_PaidIn());
				fee[1] = fee[1].add(cm.getCfsa_PaidIn());
				fee[3] = fee[3].add(cm.getCfsa_PaidIn());
				fee[5] = fee[5].add(cm.getCfsa_PaidIn());
				break;
			// t5
			case "税后工资":
				fee[0] = fee[0].add(cm.getCfsa_PaidIn());
				fee[1] = fee[1].add(cm.getCfsa_PaidIn());
				fee[2] = fee[2].add(cm.getCfsa_PaidIn());
				fee[4] = fee[4].add(cm.getCfsa_PaidIn());
				break;
			// t6
			case "个调税":
				fee[0] = fee[0].add(cm.getCfsa_PaidIn());
				if (fee[2] != null && !fee[2].equals(new BigDecimal(0))) {
					fee[1] = fee[1].add(cm.getCfsa_PaidIn());
					fee[4] = fee[4].add(cm.getCfsa_PaidIn());
				}
				break;
			// t7
			case "财务服务费":
				fee[10] = fee[10].add(cm.getCfsa_PaidIn());
				fee[0] = fee[0].add(cm.getCfsa_PaidIn());
				fee[7] = fee[7].add(cm.getCfsa_PaidIn());
				fee[8] = fee[8].add(cm.getCfsa_PaidIn());

				break;
			// t8
			case "其它":
				fee[0] = fee[0].add(cm.getCfsa_PaidIn());
				fee[1] = fee[1].add(cm.getCfsa_PaidIn());
				fee[3] = fee[3].add(cm.getCfsa_PaidIn());
				fee[15] = fee[15].add(cm.getCfsa_PaidIn());
				break;
			// t9
			case "住房返还":
				fee[1] = fee[1].add(cm.getCfsa_PaidIn());
				fee[3] = fee[3].add(cm.getCfsa_PaidIn());
				fee[13] = fee[13].add(cm.getCfsa_PaidIn());

				break;
			// t10
			case "体检费":
				fee[0] = fee[0].add(cm.getCfsa_PaidIn());
				fee[1] = fee[1].add(cm.getCfsa_PaidIn());
				fee[3] = fee[3].add(cm.getCfsa_PaidIn());
				fee[7] = fee[7].add(cm.getCfsa_PaidIn());
				fee[14] = fee[14].add(cm.getCfsa_PaidIn());
				break;
			// t11
			case "活动费":
				fee[0] = fee[0].add(cm.getCfsa_PaidIn());
				fee[1] = fee[1].add(cm.getCfsa_PaidIn());
				fee[3] = fee[3].add(cm.getCfsa_PaidIn());
				fee[7] = fee[7].add(cm.getCfsa_PaidIn());
				fee[14] = fee[14].add(cm.getCfsa_PaidIn());
				break;
			// t12
			case "书报费":
				fee[0] = fee[0].add(cm.getCfsa_PaidIn());
				fee[1] = fee[1].add(cm.getCfsa_PaidIn());
				fee[3] = fee[3].add(cm.getCfsa_PaidIn());
				fee[14] = fee[14].add(cm.getCfsa_PaidIn());
				break;
			// t13
			case "商务服务费":
				fee[0] = fee[0].add(cm.getCfsa_PaidIn());
				fee[3] = fee[3].add(cm.getCfsa_PaidIn());
				fee[8] = fee[8].add(cm.getCfsa_PaidIn());
				break;
			// t14
			case "招聘服务费":
				fee[0] = fee[0].add(cm.getCfsa_PaidIn());
				fee[3] = fee[3].add(cm.getCfsa_PaidIn());
				fee[8] = fee[8].add(cm.getCfsa_PaidIn());
				break;
			// t15
			case "劳动保障卡":
				fee[1] = fee[1].add(cm.getCfsa_PaidIn());
				fee[3] = fee[3].add(cm.getCfsa_PaidIn());
				break;
			// t16
			case "居住证":
				fee[0] = fee[0].add(cm.getCfsa_PaidIn());
				fee[1] = fee[1].add(cm.getCfsa_PaidIn());
				fee[3] = fee[3].add(cm.getCfsa_PaidIn());
				break;
			// t17
			case "档案保管费":
				fee[0] = fee[0].add(cm.getCfsa_PaidIn());
				fee[1] = fee[1].add(cm.getCfsa_PaidIn());
				fee[3] = fee[3].add(cm.getCfsa_PaidIn());
				fee[14] = fee[14].add(cm.getCfsa_PaidIn());
				break;
			// t19
			case "住房公积金":
				fee[0] = fee[0].add(cm.getCfsa_PaidIn());
				fee[1] = fee[1].add(cm.getCfsa_PaidIn());
				fee[3] = fee[3].add(cm.getCfsa_PaidIn());
				fee[5] = fee[5].add(cm.getCfsa_PaidIn());
				fee[12] = fee[12].add(cm.getCfsa_PaidIn());
			}
		}

		CoInvoiceInfoModel cim = new CoInvoiceInfoModel();
		if (!fee[0].equals(new BigDecimal(0))) {
			if (!fee[1].equals(new BigDecimal(0))) {
				if (fee[2].equals(new BigDecimal(0))) {
					cim.setCoii_fee(fee[10]);// 财税代理费
				} else {
					cim.setCoii_fee(fee[9]);// 人事代理费
				}
				cim.setCoii_owmonth(Integer.valueOf(DateStringChange
						.getOwnmonth()));
				cim.setCoii_content("代理费");
				list2.add(cim);

				if (!fee[3].equals(new BigDecimal(0))) {
					if (!fee[8].equals(new BigDecimal(0))) {
						cim.setCoii_fee(fee[8]);
						cim.setCoii_content("服务费");
						list2.add(cim);
					}
				}

			} else {
				if (!fee[8].equals(new BigDecimal(0))) {
					cim.setCoii_fee(fee[8]);
					cim.setCoii_content("服务费");
					list2.add(cim);
				}
			}

			if (!fee[4].equals(new BigDecimal(0))) {
				cim.setCoii_fee(fee[4]);
				cim.setCoii_content("代收代付工资");
				list2.add(cim);
			}

		} else {
			if (!fee[9].equals(new BigDecimal(0))) {
				cim.setCoii_fee(fee[9]);
				cim.setCoii_content("服务费");
				list2.add(cim);
			}

		}

		if (!fee[11].equals(new BigDecimal(0))) {
			if (!fee[5].equals(new BigDecimal(0))) {
				cim.setCoii_fee(fee[5]);
				cim.setCoii_content("代收代缴保险费");
				list2.add(cim);
			}
			if (!fee[13].equals(new BigDecimal(0))) {
				cim.setCoii_fee(fee[13]);
				cim.setCoii_content("代收代缴住房公积金");
				list2.add(cim);
			}

		} else {
			if (!fee[12].equals(new BigDecimal(0))) {
				cim.setCoii_fee(fee[12]);
				cim.setCoii_content("代收代缴住房公积金");
				list2.add(cim);
			}

		}

		if (!fee[14].equals(new BigDecimal(0))) {
			cim.setCoii_fee(fee[14]);
			cim.setCoii_content("代收代付福利费");
			list2.add(cim);
		}

		if (!fee[15].equals(new BigDecimal(0))) {
			cim.setCoii_fee(fee[15]);
			cim.setCoii_content("其他费用");
			list2.add(cim);
		}

		return list2;
	}

	@Override
	public List<CoInvoiceInfoModel> createInvoice2(
			List<CoFinanceCollectModel> cclist) {
		// TODO Auto-generated method stub
		List<CoInvoiceInfoModel> list = new ListModelList<>();
		if (cclist.size() > 0) {
			// Map<String, BigDecimal> map = new HashMap<>();
			BigDecimal dl = new BigDecimal(0);
			BigDecimal dl2 = new BigDecimal(0);
			BigDecimal dl3 = new BigDecimal(0);
			BigDecimal dl4 = new BigDecimal(0);
			BigDecimal gz = new BigDecimal(0);
			BigDecimal gz2 = new BigDecimal(0);
			BigDecimal gz3 = new BigDecimal(0);
			BigDecimal bx = new BigDecimal(0);
			BigDecimal bx2 = new BigDecimal(0);
			BigDecimal gjj = new BigDecimal(0);
			BigDecimal gjj2 = new BigDecimal(0);
			BigDecimal fl = new BigDecimal(0);
			// BigDecimal fw = new BigDecimal(0);
			BigDecimal fw2 = new BigDecimal(0);
			BigDecimal fw3 = new BigDecimal(0);
			BigDecimal other = new BigDecimal(0);
			
			Integer ownmonth=0;
			for (CoFinanceCollectModel cm : cclist) {
				if (cm.getCfco_cfmb_number() != null
						&& !cm.getCfco_cfmb_number().equals("") && cm.isCheck()) {
					if (ownmonth.equals(0)) {
						ownmonth=cm.getOwnmonth();
					}
					
					List<CoFinanceMonthlyBillSortAccountModel> l = new ListModelList<>();
					CoInvoiceBll bll = new CoInvoiceBll();
					l = bll.getbillInfo(cm.getCfco_cfmb_number());
					if (l.size() > 0) {
						for (CoFinanceMonthlyBillSortAccountModel m : l) {
							switch (m.getCfsa_cpac_name()) {
							case "服务费":
								dl2 = dl2.add(m.getCfsa_PaidIn());
								dl4 = dl4.add(m.getCfsa_PaidIn());
								// fw = fw.add(m.getCfsa_PaidIn());
								fw2 = fw2.add(m.getCfsa_PaidIn());
								break;
							case "社保费":
								dl = dl.add(m.getCfsa_PaidIn());
								dl2 = dl2.add(m.getCfsa_PaidIn());
								gz = gz.add(m.getCfsa_PaidIn());
								bx = bx.add(m.getCfsa_PaidIn());
								bx2 = bx2.add(m.getCfsa_PaidIn());
								fw2 = fw2.add(m.getCfsa_PaidIn());
								break;
							case "商保费":
								dl = dl.add(m.getCfsa_PaidIn());
								dl2 = dl2.add(m.getCfsa_PaidIn());
								gz = gz.add(m.getCfsa_PaidIn());
								fl = fl.add(m.getCfsa_PaidIn());
								fw2 = fw2.add(m.getCfsa_PaidIn());
								break;
							case "残疾人保障金":
								dl = dl.add(m.getCfsa_PaidIn());
								dl2 = dl2.add(m.getCfsa_PaidIn());
								gz = gz.add(m.getCfsa_PaidIn());
								bx2 = bx2.add(m.getCfsa_PaidIn());
								fw2 = fw2.add(m.getCfsa_PaidIn());
								break;
							case "税后工资":
								dl = dl.add(m.getCfsa_PaidIn());
								gz = gz.add(m.getCfsa_PaidIn());
								gz2 = gz2.add(m.getCfsa_PaidIn());
								//gz3 = gz3.add(m.getCfsa_PaidIn());
								break;
							case "个调税":
								gz = gz.add(m.getCfsa_PaidIn());
								gz3 = gz3.add(m.getCfsa_PaidIn());
								break;
							case "财务服务费":
								dl = dl.add(m.getCfsa_PaidIn());
								dl3 = dl3.add(m.getCfsa_PaidIn());
								gz = gz.add(m.getCfsa_PaidIn());
								fw3 = fw3.add(m.getCfsa_PaidIn());
								break;
							case "其它":
								dl = dl.add(m.getCfsa_PaidIn());
								dl2 = dl2.add(m.getCfsa_PaidIn());
								gz = gz.add(m.getCfsa_PaidIn());
								fw2 = fw2.add(m.getCfsa_PaidIn());
								other = other.add(m.getCfsa_PaidIn());
								break;
							case "住房返还":
								dl2 = dl2.add(m.getCfsa_PaidIn());
								gjj2 = gjj2.add(m.getCfsa_PaidIn());
								fw2 = fw2.add(m.getCfsa_PaidIn());
								break;
							case "体检费":
								dl = dl.add(m.getCfsa_PaidIn());
								dl2 = dl2.add(m.getCfsa_PaidIn());
								gz = gz.add(m.getCfsa_PaidIn());
								fl = fl.add(m.getCfsa_PaidIn());
								fw2 = fw2.add(m.getCfsa_PaidIn());
								break;
							case "活动费":
								dl = dl.add(m.getCfsa_PaidIn());
								dl2 = dl2.add(m.getCfsa_PaidIn());
								gz = gz.add(m.getCfsa_PaidIn());
								fl = fl.add(m.getCfsa_PaidIn());
								fw2 = fw2.add(m.getCfsa_PaidIn());
								break;
							case "书报费":
								dl = dl.add(m.getCfsa_PaidIn());
								dl2 = dl2.add(m.getCfsa_PaidIn());
								gz = gz.add(m.getCfsa_PaidIn());
								fl = fl.add(m.getCfsa_PaidIn());
								fw2 = fw2.add(m.getCfsa_PaidIn());
								break;
							case "商务服务费":
								dl = dl.add(m.getCfsa_PaidIn());
								dl2 = dl2.add(m.getCfsa_PaidIn());
								gz = gz.add(m.getCfsa_PaidIn());
								fw3 = fw3.add(m.getCfsa_PaidIn());
								break;
							case "招聘服务费":
								dl = dl.add(m.getCfsa_PaidIn());
								dl2 = dl2.add(m.getCfsa_PaidIn());
								gz = gz.add(m.getCfsa_PaidIn());
								fw3 = fw3.add(m.getCfsa_PaidIn());
								break;
							case "社保卡":
								dl2 = dl2.add(m.getCfsa_PaidIn());
								fw2 = fw2.add(m.getCfsa_PaidIn());
								break;
							case "居住证":
								dl = dl.add(m.getCfsa_PaidIn());
								dl2 = dl2.add(m.getCfsa_PaidIn());
								gz = gz.add(m.getCfsa_PaidIn());
								fw2 = fw2.add(m.getCfsa_PaidIn());
								break;
							case "档案保管费":
								dl = dl.add(m.getCfsa_PaidIn());
								dl2 = dl2.add(m.getCfsa_PaidIn());
								gz = gz.add(m.getCfsa_PaidIn());
								fl = fl.add(m.getCfsa_PaidIn());
								fw2 = fw2.add(m.getCfsa_PaidIn());
								break;
							case "公积金":
								dl = dl.add(m.getCfsa_PaidIn());
								dl2 = dl2.add(m.getCfsa_PaidIn());
								gz = gz.add(m.getCfsa_PaidIn());
								bx2 = bx2.add(m.getCfsa_PaidIn());
								gjj = gjj.add(m.getCfsa_PaidIn());
								fw2 = fw2.add(m.getCfsa_PaidIn());
								break;
							case "户口":
								dl = dl.add(m.getCfsa_PaidIn());
								dl2 = dl2.add(m.getCfsa_PaidIn());
								gz = gz.add(m.getCfsa_PaidIn());
								fl = fl.add(m.getCfsa_PaidIn());
								fw2 = fw2.add(m.getCfsa_PaidIn());
								break;
							default:
								break;
							}

						}

					}
				}
			}
			if (gz.equals(new BigDecimal(0))) {
				fw2 = fw2.add(gz2);
			}
			/*
			Integer ownmonth = Integer.valueOf(DateStringChange
					.Datestringnow("yyyyMM"));*/
			

			// 代理费用
			CoInvoiceInfoModel m = new CoInvoiceInfoModel();
			m.setCoii_owmonth(ownmonth);
			if (dl.compareTo(BigDecimal.ZERO) > 0) {
				
				if (dl2.compareTo(BigDecimal.ZERO) > 0) {
					m.setCoii_fee(dl4);
				} else {
					m.setCoii_fee(dl3);
				}
				if (m.getCoii_fee().compareTo(BigDecimal.ZERO)>0) {
					m.setCoii_content("代理费");
					m.setCoii_feetype("代理费");
					m.setCoii_addname(UserInfo.getUsername());
					list.add(m);
				}
				
			}
			// 工资
			m = new CoInvoiceInfoModel();
			m.setCoii_owmonth(ownmonth);
			if (gz.compareTo(BigDecimal.ZERO) > 0) {
				if (gz2.compareTo(BigDecimal.ZERO) > 0) {
					if (gz2.add(gz3).compareTo(BigDecimal.ZERO) > 0) {
						m.setCoii_content("代收代付工资");
						m.setCoii_feetype("代收代付工资");
						m.setCoii_fee(gz2.add(gz3));
						m.setCoii_addname(UserInfo.getUsername());
						list.add(m);
					}
				}
			}
			// 保险
			m = new CoInvoiceInfoModel();
			m.setCoii_owmonth(ownmonth);
			if (bx.compareTo(BigDecimal.ZERO) > 0) {
				if (bx2.compareTo(BigDecimal.ZERO) > 0) {
					m.setCoii_content("代收代缴保险费");
					m.setCoii_feetype("代收代缴保险费");
					m.setCoii_fee(bx2);
					m.setCoii_addname(UserInfo.getUsername());
					list.add(m);
				}
			}
			// 住房
			m = new CoInvoiceInfoModel();
			m.setCoii_owmonth(ownmonth);
			
			if (bx.compareTo(BigDecimal.ZERO) > 0) {
				if (gjj2.compareTo(BigDecimal.ZERO) > 0) {
					m.setCoii_content("代收代缴住房公积金");
					m.setCoii_feetype("代收代缴住房公积金");
					m.setCoii_fee(gjj2);
					m.setCoii_addname(UserInfo.getUsername());
					list.add(m);
				}
			} else {
				if (gjj.compareTo(BigDecimal.ZERO) > 0) {
					m.setCoii_content("代收代缴住房公积金");
					m.setCoii_feetype("代收代缴住房公积金");
					m.setCoii_fee(gjj);
					m.setCoii_addname(UserInfo.getUsername());
					list.add(m);
				}
			}

			// 福利
			m = new CoInvoiceInfoModel();
			m.setCoii_owmonth(ownmonth);
			if (fl.compareTo(BigDecimal.ZERO) > 0) {
				m.setCoii_content("代收代付福利费");
				m.setCoii_feetype("代收代付福利费");
				m.setCoii_fee(fl);
				m.setCoii_addname(UserInfo.getUsername());
				list.add(m);
			}

			// 服务费
			m = new CoInvoiceInfoModel();
			m.setCoii_owmonth(ownmonth);
			if (gz.compareTo(BigDecimal.ZERO) > 0) {
				if (fw2.compareTo(BigDecimal.ZERO) > 0) {
					if (dl2.compareTo(BigDecimal.ZERO) > 0) {
						if (fw3.compareTo(BigDecimal.ZERO) > 0) {
							m.setCoii_content("服务费");
							m.setCoii_feetype("服务费");
							m.setCoii_fee(fw3);
							m.setCoii_addname(UserInfo.getUsername());
							list.add(m);
						}
					}
				} else {
					if (fw3.compareTo(BigDecimal.ZERO) > 0) {
						m.setCoii_content("服务费");
						m.setCoii_feetype("服务费");
						m.setCoii_fee(fw3);
						m.setCoii_addname(UserInfo.getUsername());
						list.add(m);
					}
				}
			} else {
				if (dl4.compareTo(BigDecimal.ZERO) > 0) {
					m.setCoii_content("服务费");
					m.setCoii_feetype("服务费");
					m.setCoii_fee(dl4);
					m.setCoii_addname(UserInfo.getUsername());
					list.add(m);
				}
			}

			// 其他
			m = new CoInvoiceInfoModel();
			m.setCoii_owmonth(ownmonth);
			if (other.compareTo(BigDecimal.ZERO) > 0) {
				m.setCoii_content("其他");
				m.setCoii_feetype("其他");
				m.setCoii_fee(other);
				m.setCoii_addname(UserInfo.getUsername());
				list.add(m);
			}

		}
		return list;
	}
}
