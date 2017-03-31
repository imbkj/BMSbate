package Model;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import Util.EmSalaryFunctions;
import bsh.EvalError;
import bsh.Interpreter;
import dal.EmSalary.EmSalary_SalaryOperateDal;

public class EmSalaryDataModel implements Cloneable {
	private final DecimalFormat df = new DecimalFormat("#.00");
	private BigDecimal zero = BigDecimal.ZERO;
	private int esda_id;
	private int cid;
	private int gid;
	private int ownmonth;
	private int esda_if_bms;
	private String esda_addname;
	private String esda_addtime;
	private String esda_remark;
	private String esda_fd_remark;
	private int cfin_id;
	private String csii_itemid;
	private String esda_o_bank;
	private String esda_bank;
	private String esda_ba_name;
	private String esda_bank_account;
	private String esda_nationality;
	private int esda_usage_type;
	private String esda_email;
	private String esda_bcc_email;
	private String esda_email_sender;
	private int esda_email_state;
	private String esda_email_date;
	private int esda_oof_state;
	private int esda_confirm_state;
	private int esda_payment_state;
	private String esda_payment_date;
	private int esda_payment_batch;
	private int esda_data_type;
	private int esda_rp_reason;
	private int esda_history_state;
	private String esda_history_date;
	private int esda_ttv_state;
	private String esda_ttv_date;
	private String esda_ttv_people;
	private BigDecimal esda_base_radix = zero;
	private BigDecimal esda_a_base_radix = zero;
	private BigDecimal esda_a_workdays = zero;
	private BigDecimal esda_a_base_salary = zero;
	private BigDecimal esda_days = zero;
	private BigDecimal esda_cqdays = zero;
	private BigDecimal esda_b_cqdays = zero;
	private BigDecimal esda_qqdays = zero;
	private BigDecimal esda_b_qqdays = zero;
	private BigDecimal esda_aded = zero;
	private BigDecimal esda_a_aded = zero;
	private BigDecimal esda_base_salary = zero;
	private BigDecimal esda_siop = zero;
	private BigDecimal esda_hafop = zero;
	private BigDecimal esda_haf = zero;
	private BigDecimal esda_house_radix = zero;
	private BigDecimal esda_house_percent = zero;
	private BigDecimal esda_house_ntl = zero;
	private BigDecimal esda_total_pretax = zero;
	private BigDecimal esda_tax_base = zero;
	private BigDecimal esda_tax = zero;
	private BigDecimal esda_dc = zero;
	private BigDecimal esda_dc_tax = zero;
	private BigDecimal esda_db = zero;
	private BigDecimal esda_db_tax_base = zero;
	private BigDecimal esda_db_tax = zero;
	private BigDecimal esda_stock = zero;
	private BigDecimal esda_stock_tax = zero;
	private BigDecimal esda_other = zero;
	private BigDecimal esda_other_tax = zero;
	private BigDecimal esda_write_off = zero;
	private BigDecimal esda_house_bf = zero;
	private BigDecimal esda_pay = zero;
	private BigDecimal esda_col_1 = zero;
	private BigDecimal esda_col_2 = zero;
	private BigDecimal esda_col_3 = zero;
	private BigDecimal esda_col_4 = zero;
	private BigDecimal esda_col_5 = zero;
	private BigDecimal esda_col_6 = zero;
	private BigDecimal esda_col_7 = zero;
	private BigDecimal esda_col_8 = zero;
	private BigDecimal esda_col_9 = zero;
	private BigDecimal esda_col_10 = zero;
	private BigDecimal esda_col_11 = zero;
	private BigDecimal esda_col_12 = zero;
	private BigDecimal esda_col_13 = zero;
	private BigDecimal esda_col_14 = zero;
	private BigDecimal esda_col_15 = zero;
	private BigDecimal esda_col_16 = zero;
	private BigDecimal esda_col_17 = zero;
	private BigDecimal esda_col_18 = zero;
	private BigDecimal esda_col_19 = zero;
	private BigDecimal esda_col_20 = zero;
	private BigDecimal esda_col_21 = zero;
	private BigDecimal esda_col_22 = zero;
	private BigDecimal esda_col_23 = zero;
	private BigDecimal esda_col_24 = zero;
	private BigDecimal esda_col_25 = zero;
	private BigDecimal esda_col_26 = zero;
	private BigDecimal esda_col_27 = zero;
	private BigDecimal esda_col_28 = zero;
	private BigDecimal esda_col_29 = zero;
	private BigDecimal esda_col_30 = zero;
	private BigDecimal esda_col_31 = zero;
	private BigDecimal esda_col_32 = zero;
	private BigDecimal esda_col_33 = zero;
	private BigDecimal esda_col_34 = zero;
	private BigDecimal esda_col_35 = zero;
	private BigDecimal esda_col_36 = zero;
	private BigDecimal esda_col_37 = zero;
	private BigDecimal esda_col_38 = zero;
	private BigDecimal esda_col_39 = zero;
	private BigDecimal esda_col_40 = zero;
	private BigDecimal esda_col_41 = zero;
	private BigDecimal esda_col_42 = zero;
	private BigDecimal esda_col_43 = zero;
	private BigDecimal esda_col_44 = zero;
	private BigDecimal esda_col_45 = zero;
	private BigDecimal esda_col_46 = zero;
	private BigDecimal esda_col_47 = zero;
	private BigDecimal esda_col_48 = zero;
	private BigDecimal esda_col_49 = zero;
	private BigDecimal esda_col_50 = zero;
	private BigDecimal esda_col_51 = zero;
	private BigDecimal esda_col_52 = zero;
	private BigDecimal esda_col_53 = zero;
	private BigDecimal esda_col_54 = zero;
	private BigDecimal esda_col_55 = zero;
	private BigDecimal esda_col_56 = zero;
	private BigDecimal esda_col_57 = zero;
	private BigDecimal esda_col_58 = zero;
	private BigDecimal esda_col_59 = zero;
	private BigDecimal esda_col_60 = zero;
	private BigDecimal esda_col_61 = zero;
	private BigDecimal esda_col_62 = zero;
	private BigDecimal esda_col_63 = zero;
	private BigDecimal esda_col_64 = zero;
	private BigDecimal esda_col_65 = zero;
	private BigDecimal esda_col_66 = zero;
	private BigDecimal esda_col_67 = zero;
	private BigDecimal esda_col_68 = zero;
	private BigDecimal esda_col_69 = zero;
	private BigDecimal esda_col_70 = zero;
	private BigDecimal esda_col_71 = zero;
	private BigDecimal esda_col_72 = zero;
	private BigDecimal esda_col_73 = zero;
	private BigDecimal esda_col_74 = zero;
	private BigDecimal esda_col_75 = zero;
	private BigDecimal esda_col_76 = zero;
	private BigDecimal esda_col_77 = zero;
	private BigDecimal esda_col_78 = zero;
	private BigDecimal esda_col_79 = zero;
	private BigDecimal esda_col_80 = zero;
	// 劳务报酬
	private BigDecimal esda_lw_tax_base = zero;
	private BigDecimal esda_lw_tax = zero;
	// 纳税调整合计
	private BigDecimal esda_total_adjtax = zero;
	// 税后合计
	private BigDecimal esda_total_afttax = zero;

	// 住房公积金公司部分
	private BigDecimal esda_hafcp = zero;
	// 住房公积金公司基数
	private BigDecimal esda_house_cradix = zero;
	// 住房公积金公司比例
	private BigDecimal esda_house_cpercent = zero;

	private int esda_ifhold;
	private int esdt_id;
	private String esda_usage_typestr;
	private String esda_oof_statestr;
	private String esda_if_bmsstr;
	private String esda_remarkstr;
	private String name;
	private String esda_ifholdstr;
	private String coba_shortname;
	private String coba_namespell;
	private String emba_spell;

	private String esda_payment_statestr;
	private String esda_email_statestr;
	private String esda_data_typestr;
	// 雇佣性质
	private String esda_hpro;
	// 个税申报地
	private String esda_taxplace;

	private List<EmSalaryBaseAddItemModel> itemList;
	private List<EmSalaryBaseAddItemModel> itemListSel;
	private List<EmSalaryBaseAddItemModel> itemListSUM;
	private List<EmSalaryBaseAddItemModel> itemListSelSUM;

	// 批量处理表字段
	private int tbrb_id;
	private int tbrb_customInt;

	private int taba_id;
	private int tapr_id;

	// 工资单发送
	private int esin_id;
	private String esin_cost_type;
	private String esin_attachpassword;
	private String esin_cosm_model;

	private List<String> sendType;
	private int coss_sendstate;
	private int coss_carbonCopy;
	private String coss_ccaddress;
	private int coss_secretsend;
	private String coss_secretsendaddress;

	// 银行信息是否完整
	private boolean ifBank;
	private String ifBankCon;

	// 电汇
	private Integer ttv_state;
	private Integer cou;

	// 收款
	private BigDecimal gz_paidin = zero;// 工资
	private BigDecimal gs_paidin = zero;// 个税
	private BigDecimal csfee_paidin = zero;// 财税服务费

	private String coba_ufclass;
	private String coba_ufid;

	private String gzaddname_email;

	private String client_email;

	private Integer emba_state;
	private String emba_statestr;

	private String d_type;//重发页面使用 数据所属模块
	
	public Object clone() {
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			return null;
		}

	}

	// 根据cfin_id获取算法
	private List<String[]> getAlgorithmInfo() {
		EmSalary_SalaryOperateDal dal = new EmSalary_SalaryOperateDal();
		List<String[]> list = null;
		try {
			list = dal.getAlgorithmInfo(getCfin_id());
		} catch (Exception e) {

		}
		return list;
	}

	// 计算工资项目
	public void sumItemInfo() {
		Interpreter i = new Interpreter();
		try {
			// 项目赋值
			i = setValue(i);
			// 自定义函数赋值
			try {
				i.set("ef", new EmSalaryFunctions());
			} catch (EvalError e1) {
				e1.printStackTrace();
			}
			List<String[]> list = getAlgorithmInfo();
			if (list.size() > 0) {
				BigDecimal bd = new BigDecimal(0);
				for (String[] item : list) {
					if (item[0] != null) {
						if (item[2] != null && !"".equals(item[2])) {
							try {
								if ((Boolean) i.eval(item[2])) {
									bd = new BigDecimal(i.eval(item[1])
											.toString());
									bd.setScale(2, BigDecimal.ROUND_HALF_UP);
									setField(this, item[0], bd);
									i.set(item[0], bd.doubleValue());
								}
							} catch (EvalError e) {
								e.printStackTrace();
							} catch (Exception e) {
								e.printStackTrace();
							}
						} else {
							try {
								bd = new BigDecimal(i.eval(item[1]).toString());
								bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
								setField(this, item[0], bd);
								i.set(item[0], bd.doubleValue());
							} catch (EvalError e) {
								e.printStackTrace();
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			i = null;
		}
	}

	// 计算工资项目赋值至ItemList(校验算法)
	public void sumItemInfoToItemList() {
		Interpreter i = new Interpreter();
		// 项目赋值
		i = setItemListValue(i);
		// 自定义函数赋值
		try {
			i.set("ef", new EmSalaryFunctions());
		} catch (EvalError e1) {
			e1.printStackTrace();
		}
		List<String[]> list = getAlgorithmInfo();
		if (list.size() > 0) {
			BigDecimal bd = new BigDecimal(0);
			for (String[] item : list) {
				if (item[2] != null && !"".equals(item[2])) {
					try {
						if ((Boolean) i.eval(item[2])) {
							bd = new BigDecimal(i.eval(item[1]).toString());
							bd.setScale(2, BigDecimal.ROUND_HALF_UP);
							setItemListSumValue(item[0], bd);
							i.set(item[0], bd.doubleValue());
						}
					} catch (EvalError e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					try {
						bd = new BigDecimal(i.eval(item[1]).toString());
						bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
						setItemListSumValue(item[0], bd);
						i.set(item[0], bd.doubleValue());
					} catch (EvalError e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}

		}
	}

	// ItemList计算后重新赋值(校验算法)
	private void setItemListSumValue(String item, BigDecimal amount) {
		for (EmSalaryBaseAddItemModel m : itemList) {
			try {
				if (item.equals(m.getCsii_col())) {
					m.setAmount(amount);
					return;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	// ItemList字段赋值(校验算法)
	private Interpreter setItemListValue(Interpreter i) {
		try {
			for (EmSalaryBaseAddItemModel m : itemList) {
				i.set(m.getCsii_col(), m.getAmount().doubleValue());
			}
			i.set("esda_nationality", esda_nationality);
			i.set("esda_hpro", esda_hpro);
			i.set("esda_taxplace", esda_taxplace);
		} catch (EvalError e) {
			e.printStackTrace();
		}
		return i;
	}

	// 调用Set方法
	private void setField(Object obj, String fieldname, Object value) {
		try {
			Method method = obj.getClass().getMethod(setMethod(fieldname),
					BigDecimal.class);
			method.invoke(obj, value);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 根据字段名获取SET方法名；
	private static String setMethod(String name) {
		return "set"
				+ name.replaceFirst(name.substring(0, 1), name.substring(0, 1)
						.toUpperCase());
	}

	// 项目赋值
	private Interpreter setValue(Interpreter i) {
		try {
			i.set("esda_id", esda_id);
			i.set("cid", cid);
			i.set("gid", gid);
			i.set("ownmonth", ownmonth);
			i.set("esda_if_bms", esda_if_bms);
			i.set("cfin_id", cfin_id);
			i.set("csii_itemid", csii_itemid);
			i.set("esda_nationality", esda_nationality);
			i.set("esda_base_radix", esda_base_radix.doubleValue());
			i.set("esda_a_base_radix", esda_a_base_radix.doubleValue());
			i.set("esda_a_workdays", esda_a_workdays.doubleValue());
			i.set("esda_a_base_salary", esda_a_base_salary.doubleValue());
			i.set("esda_days", esda_days.doubleValue());
			i.set("esda_cqdays", esda_cqdays.doubleValue());
			i.set("esda_b_cqdays", esda_b_cqdays.doubleValue());
			i.set("esda_qqdays", esda_qqdays.doubleValue());
			i.set("esda_b_qqdays", esda_b_qqdays.doubleValue());
			i.set("esda_aded", esda_aded.doubleValue());
			i.set("esda_a_aded", esda_a_aded.doubleValue());
			i.set("esda_base_salary", esda_base_salary.doubleValue());
			i.set("esda_siop", esda_siop.doubleValue());
			i.set("esda_hafop", esda_hafop.doubleValue());
			i.set("esda_haf", esda_haf.doubleValue());
			i.set("esda_house_radix", esda_house_radix.doubleValue());
			i.set("esda_house_percent", esda_house_percent.doubleValue());
			i.set("esda_house_ntl", esda_house_ntl.doubleValue());
			i.set("esda_total_pretax", esda_total_pretax.doubleValue());
			i.set("esda_tax_base", esda_tax_base.doubleValue());
			i.set("esda_tax", esda_tax.doubleValue());
			i.set("esda_dc", esda_dc.doubleValue());
			i.set("esda_dc_tax", esda_dc_tax.doubleValue());
			i.set("esda_db", esda_db.doubleValue());
			i.set("esda_db_tax_base", esda_db_tax_base.doubleValue());
			i.set("esda_db_tax", esda_db_tax.doubleValue());
			i.set("esda_stock", esda_stock.doubleValue());
			i.set("esda_stock_tax", esda_stock_tax.doubleValue());
			i.set("esda_other", esda_other.doubleValue());
			i.set("esda_other_tax", esda_other_tax.doubleValue());
			i.set("esda_write_off", esda_write_off.doubleValue());
			i.set("esda_house_bf", esda_house_bf.doubleValue());
			i.set("esda_pay", esda_pay.doubleValue());
			i.set("esda_lw_tax_base", esda_lw_tax_base.doubleValue());
			i.set("esda_lw_tax", esda_lw_tax.doubleValue());
			i.set("esda_total_adjtax", esda_total_adjtax.doubleValue());
			i.set("esda_total_afttax", esda_total_afttax.doubleValue());
			i.set("esda_col_1", esda_col_1.doubleValue());
			i.set("esda_col_2", esda_col_2.doubleValue());
			i.set("esda_col_3", esda_col_3.doubleValue());
			i.set("esda_col_4", esda_col_4.doubleValue());
			i.set("esda_col_5", esda_col_5.doubleValue());
			i.set("esda_col_6", esda_col_6.doubleValue());
			i.set("esda_col_7", esda_col_7.doubleValue());
			i.set("esda_col_8", esda_col_8.doubleValue());
			i.set("esda_col_9", esda_col_9.doubleValue());
			i.set("esda_col_10", esda_col_10.doubleValue());
			i.set("esda_col_11", esda_col_11.doubleValue());
			i.set("esda_col_12", esda_col_12.doubleValue());
			i.set("esda_col_13", esda_col_13.doubleValue());
			i.set("esda_col_14", esda_col_14.doubleValue());
			i.set("esda_col_15", esda_col_15.doubleValue());
			i.set("esda_col_16", esda_col_16.doubleValue());
			i.set("esda_col_17", esda_col_17.doubleValue());
			i.set("esda_col_18", esda_col_18.doubleValue());
			i.set("esda_col_19", esda_col_19.doubleValue());
			i.set("esda_col_20", esda_col_20.doubleValue());
			i.set("esda_col_21", esda_col_21.doubleValue());
			i.set("esda_col_22", esda_col_22.doubleValue());
			i.set("esda_col_23", esda_col_23.doubleValue());
			i.set("esda_col_24", esda_col_24.doubleValue());
			i.set("esda_col_25", esda_col_25.doubleValue());
			i.set("esda_col_26", esda_col_26.doubleValue());
			i.set("esda_col_27", esda_col_27.doubleValue());
			i.set("esda_col_28", esda_col_28.doubleValue());
			i.set("esda_col_29", esda_col_29.doubleValue());
			i.set("esda_col_30", esda_col_30.doubleValue());
			i.set("esda_col_31", esda_col_31.doubleValue());
			i.set("esda_col_32", esda_col_32.doubleValue());
			i.set("esda_col_33", esda_col_33.doubleValue());
			i.set("esda_col_34", esda_col_34.doubleValue());
			i.set("esda_col_35", esda_col_35.doubleValue());
			i.set("esda_col_36", esda_col_36.doubleValue());
			i.set("esda_col_37", esda_col_37.doubleValue());
			i.set("esda_col_38", esda_col_38.doubleValue());
			i.set("esda_col_39", esda_col_39.doubleValue());
			i.set("esda_col_40", esda_col_40.doubleValue());
			i.set("esda_col_41", esda_col_41.doubleValue());
			i.set("esda_col_42", esda_col_42.doubleValue());
			i.set("esda_col_43", esda_col_43.doubleValue());
			i.set("esda_col_44", esda_col_44.doubleValue());
			i.set("esda_col_45", esda_col_45.doubleValue());
			i.set("esda_col_46", esda_col_46.doubleValue());
			i.set("esda_col_47", esda_col_47.doubleValue());
			i.set("esda_col_48", esda_col_48.doubleValue());
			i.set("esda_col_49", esda_col_49.doubleValue());
			i.set("esda_col_50", esda_col_50.doubleValue());
			i.set("esda_col_51", esda_col_51.doubleValue());
			i.set("esda_col_52", esda_col_52.doubleValue());
			i.set("esda_col_53", esda_col_53.doubleValue());
			i.set("esda_col_54", esda_col_54.doubleValue());
			i.set("esda_col_55", esda_col_55.doubleValue());
			i.set("esda_col_56", esda_col_56.doubleValue());
			i.set("esda_col_57", esda_col_57.doubleValue());
			i.set("esda_col_58", esda_col_58.doubleValue());
			i.set("esda_col_59", esda_col_59.doubleValue());
			i.set("esda_col_60", esda_col_60.doubleValue());
			i.set("esda_col_61", esda_col_61.doubleValue());
			i.set("esda_col_62", esda_col_62.doubleValue());
			i.set("esda_col_63", esda_col_63.doubleValue());
			i.set("esda_col_64", esda_col_64.doubleValue());
			i.set("esda_col_65", esda_col_65.doubleValue());
			i.set("esda_col_66", esda_col_66.doubleValue());
			i.set("esda_col_67", esda_col_67.doubleValue());
			i.set("esda_col_68", esda_col_68.doubleValue());
			i.set("esda_col_69", esda_col_69.doubleValue());
			i.set("esda_col_70", esda_col_70.doubleValue());
			i.set("esda_col_71", esda_col_71.doubleValue());
			i.set("esda_col_72", esda_col_72.doubleValue());
			i.set("esda_col_73", esda_col_73.doubleValue());
			i.set("esda_col_74", esda_col_74.doubleValue());
			i.set("esda_col_75", esda_col_75.doubleValue());
			i.set("esda_col_76", esda_col_76.doubleValue());
			i.set("esda_col_77", esda_col_77.doubleValue());
			i.set("esda_col_78", esda_col_78.doubleValue());
			i.set("esda_col_79", esda_col_79.doubleValue());
			i.set("esda_col_80", esda_col_80.doubleValue());
			i.set("esda_hpro", esda_hpro);
			i.set("esda_taxplace", esda_taxplace);
			i.set("esda_hafcp", esda_hafcp.doubleValue());
			i.set("esda_house_cradix", esda_house_cradix.doubleValue());
			i.set("esda_house_cpercent", esda_house_cpercent.doubleValue());
		} catch (EvalError e) {
			e.printStackTrace();
		}
		return i;
	}

	// 调用Get方法
	private BigDecimal getField(String fieldname) {
		Object bd = null;
		try {
			Method method = this.getClass().getMethod(getMethod(fieldname));
			bd = method.invoke(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new BigDecimal(bd.toString());
	}

	// 根据字段名获取GET方法名；
	private static String getMethod(String name) {
		return "get"
				+ name.replaceFirst(name.substring(0, 1), name.substring(0, 1)
						.toUpperCase());
	}

	// 初始化itemList(将该MODEL的值赋予itemList)
	public void setItemList(List<EmSalaryBaseAddItemModel> itemList) {
		List<EmSalaryBaseAddItemModel> list = new ArrayList<EmSalaryBaseAddItemModel>();
		EmSalaryBaseAddItemModel model = null;
		for (EmSalaryBaseAddItemModel m : itemList) {
			model = new EmSalaryBaseAddItemModel();
			model.setCsii_item_name(m.getCsii_item_name());
			model.setCsii_col(m.getCsii_col());
			model.setCsii_fd_state(m.getCsii_fd_state());
			model.setCfda_id(m.getCfda_id());
			setField(model, "amount", getField(m.getCsii_col()));
			list.add(model);
		}
		this.itemList = list;
	}

	// 初始化itemListSel(将该MODEL的值赋予itemListSel)
	public void setItemListSel(List<EmSalaryBaseAddItemModel> itemList) {
		List<EmSalaryBaseAddItemModel> list = new ArrayList<EmSalaryBaseAddItemModel>();
		EmSalaryBaseAddItemModel model = null;
		for (EmSalaryBaseAddItemModel m : itemList) {
			model = new EmSalaryBaseAddItemModel();
			model.setCsii_item_name(m.getCsii_item_name());
			model.setCsii_col(m.getCsii_col());
			model.setCsii_fd_state(m.getCsii_fd_state());
			model.setCfda_id(m.getCfda_id());
			setField(model, "amount", getField(m.getCsii_col()));
			list.add(model);
		}
		this.itemListSel = list;
	}

	// 初始化itemListSel(将该MODEL的值赋予itemListSelSUM)
	public void setItemListSelSUM(List<EmSalaryBaseAddItemModel> itemList) {
		List<EmSalaryBaseAddItemModel> list = new ArrayList<EmSalaryBaseAddItemModel>();
		EmSalaryBaseAddItemModel model = null;
		for (EmSalaryBaseAddItemModel m : itemList) {
			model = new EmSalaryBaseAddItemModel();
			model.setCsii_item_name(m.getCsii_item_name());
			model.setCsii_col(m.getCsii_col());
			model.setCsii_fd_state(m.getCsii_fd_state());
			model.setCfda_id(m.getCfda_id());
			setField(model, "amount", getField(m.getCsii_col()));
			list.add(model);
		}
		this.itemListSelSUM = list;
	}

	// 初始化itemListSUM(将该MODEL的值赋予itemListSUM)
	public void setItemListSUM(List<EmSalaryBaseAddItemModel> itemList) {
		List<EmSalaryBaseAddItemModel> list = new ArrayList<EmSalaryBaseAddItemModel>();
		EmSalaryBaseAddItemModel model = null;
		for (EmSalaryBaseAddItemModel m : itemList) {
			model = new EmSalaryBaseAddItemModel();
			model.setCsii_item_name(m.getCsii_item_name());
			model.setCsii_col(m.getCsii_col());
			model.setCsii_fd_state(m.getCsii_fd_state());
			model.setCfda_id(m.getCfda_id());
			setField(model, "amount", getField(m.getCsii_col()));
			list.add(model);
		}
		this.itemListSUM = list;
	}

	// 初始化itemList(将该MODEL的值赋予itemList)
	public void setGzdItemList(List<EmSalaryBaseAddItemModel> itemList) {
		List<EmSalaryBaseAddItemModel> list = new ArrayList<EmSalaryBaseAddItemModel>();
		EmSalaryBaseAddItemModel model = null;
		for (EmSalaryBaseAddItemModel m : itemList) {
			model = new EmSalaryBaseAddItemModel();
			model.setCsii_item_name(m.getCsii_item_name());
			model.setCsii_col(m.getCsii_col());
			model.setCsii_csd_state(m.getCsii_csd_state());
			model.setCfda_id(m.getCfda_id());
			setField(model, "amount", getField(m.getCsii_col()));
			if (m.getCsii_item_anothername() != null) {
				model.setCsii_item_name(m.getCsii_item_anothername());
			}
			list.add(model);
		}
		this.itemList = list;
	}

	// 将itemList的值赋予Model
	public void setItemListToModel() {
		for (EmSalaryBaseAddItemModel m : this.itemList) {
			// 从itemList反射数据到MODEL
			setField(this, m.getCsii_col(), m.getAmount());
		}
	}

	public void insertItemList(List<EmSalaryBaseAddItemModel> list) {
		this.itemList = list;
	}

	// 数据清零
	public void setDataZero(List<String> itemlist) {
		try {
			for (String item : itemlist) {
				try {
					setField(this, item, zero);
				} catch (Exception e) {

				}
			}
		} catch (Exception e) {

		}
	}

	// 判断员工银行信息是否完整
	public void checkIfBank() {
		try {
			this.ifBank = true;
			this.ifBankCon = "";
			if (this.esda_bank == null || "".equals(this.esda_bank)) {
				this.ifBank = false;
				this.ifBankCon = "工资银行名称，未录入；";
			}
			if (this.esda_bank_account == null
					|| "".equals(this.esda_bank_account)) {
				this.ifBank = false;
				this.ifBankCon = this.ifBankCon + "工资银行账号，未录入；";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<EmSalaryBaseAddItemModel> getItemList() {
		return itemList;
	}

	public EmSalaryDataModel() {
		super();
	}

	public String getEsda_payment_statestr() {
		return esda_payment_statestr;
	}

	public void setEsda_payment_statestr(String esda_payment_statestr) {
		this.esda_payment_statestr = esda_payment_statestr;
	}

	public String getName() {
		return name;
	}

	public int getCoss_sendstate() {
		return coss_sendstate;
	}

	public void setCoss_sendstate(int coss_sendstate) {
		this.coss_sendstate = coss_sendstate;
	}

	public int getCoss_carbonCopy() {
		return coss_carbonCopy;
	}

	public void setCoss_carbonCopy(int coss_carbonCopy) {
		this.coss_carbonCopy = coss_carbonCopy;
	}

	public String getCoss_ccaddress() {
		return coss_ccaddress;
	}

	public void setCoss_ccaddress(String coss_ccaddress) {
		this.coss_ccaddress = coss_ccaddress;
	}

	public int getCoss_secretsend() {
		return coss_secretsend;
	}

	public void setCoss_secretsend(int coss_secretsend) {
		this.coss_secretsend = coss_secretsend;
	}

	public String getCoss_secretsendaddress() {
		return coss_secretsendaddress;
	}

	public void setCoss_secretsendaddress(String coss_secretsendaddress) {
		this.coss_secretsendaddress = coss_secretsendaddress;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEsda_usage_typestr() {
		return esda_usage_typestr;
	}

	public void setEsda_usage_typestr(String esda_usage_typestr) {
		this.esda_usage_typestr = esda_usage_typestr;
		switch (esda_usage_typestr) {
		case "工资":
			this.esda_usage_type = 0;
			break;
		case "报销":
			this.esda_usage_type = 1;
			break;
		case "住房返还":
			this.esda_usage_type = 2;
			break;
		case "奖金":
			this.esda_usage_type = 3;
			break;
		case "离职补偿金":
			this.esda_usage_type = 4;
			break;
		case "股票分红":
			this.esda_usage_type = 5;
			break;
		default:
			break;
		}
	}

	public String getEsda_oof_statestr() {
		return esda_oof_statestr;
	}

	public void setEsda_oof_statestr(String esda_oof_statestr) {
		this.esda_oof_statestr = esda_oof_statestr;
		if ("是".equals(esda_oof_statestr)) {
			this.esda_oof_state = 1;
		} else {
			this.esda_oof_state = 0;
		}
	}

	public String getEsda_if_bmsstr() {
		return esda_if_bmsstr;
	}

	public void setEsda_if_bmsstr(String esda_if_bmsstr) {
		this.esda_if_bmsstr = esda_if_bmsstr;
	}

	public String getEsda_remarkstr() {
		return esda_remarkstr;
	}

	public void setEsda_remarkstr(String esda_remarkstr) {
		this.esda_remarkstr = esda_remarkstr;
	}

	public int getEsda_id() {
		return esda_id;
	}

	public void setEsda_id(int esda_id) {
		this.esda_id = esda_id;
	}

	public int getCid() {
		return cid;
	}

	public void setCid(int cid) {
		this.cid = cid;
	}

	public int getGid() {
		return gid;
	}

	public void setGid(int gid) {
		this.gid = gid;
	}

	public int getOwnmonth() {
		return ownmonth;
	}

	public void setOwnmonth(int ownmonth) {
		this.ownmonth = ownmonth;
	}

	public int getEsda_if_bms() {
		return esda_if_bms;
	}

	public void setEsda_if_bms(int esda_if_bms) {
		this.esda_if_bms = esda_if_bms;
	}

	public String getEsda_addname() {
		return esda_addname;
	}

	public void setEsda_addname(String esda_addname) {
		this.esda_addname = esda_addname;
	}

	public String getEsda_addtime() {
		return esda_addtime;
	}

	public void setEsda_addtime(String esda_addtime) {
		this.esda_addtime = esda_addtime;
	}

	public String getEsda_remark() {
		return esda_remark;
	}

	public void setEsda_remark(String esda_remark) {
		this.esda_remark = esda_remark;
		if (esda_remark == null || esda_remark == "") {
			this.esda_remarkstr = "";
		} else {
			this.esda_remarkstr = "已备注";
		}
	}

	public String getEsda_fd_remark() {
		return esda_fd_remark;
	}

	public void setEsda_fd_remark(String esda_fd_remark) {
		this.esda_fd_remark = esda_fd_remark;
	}

	public int getCfin_id() {
		return cfin_id;
	}

	public void setCfin_id(int cfin_id) {
		this.cfin_id = cfin_id;
	}

	public String getCsii_itemid() {
		return csii_itemid;
	}

	public void setCsii_itemid(String csii_itemid) {
		this.csii_itemid = csii_itemid;
	}

	public String getEsda_o_bank() {
		return esda_o_bank;
	}

	public void setEsda_o_bank(String esda_o_bank) {
		this.esda_o_bank = esda_o_bank;
	}

	public String getEsda_bank() {
		return esda_bank;
	}

	public void setEsda_bank(String esda_bank) {
		this.esda_bank = esda_bank;
	}

	public String getEsda_ba_name() {
		return esda_ba_name;
	}

	public void setEsda_ba_name(String esda_ba_name) {
		this.esda_ba_name = esda_ba_name;
	}

	public String getEsda_bank_account() {
		return esda_bank_account;
	}

	public void setEsda_bank_account(String esda_bank_account) {
		this.esda_bank_account = esda_bank_account;
	}

	public String getEsda_nationality() {
		return esda_nationality;
	}

	public void setEsda_nationality(String esda_nationality) {
		this.esda_nationality = esda_nationality;
	}

	public int getEsda_usage_type() {
		return esda_usage_type;
	}

	public void setEsda_usage_type(int esda_usage_type) {
		this.esda_usage_type = esda_usage_type;
		switch (esda_usage_type) {
		case 0:
			this.esda_usage_typestr = "工资";
			break;
		case 1:
			this.esda_usage_typestr = "报销";
			break;
		case 2:
			this.esda_usage_typestr = "住房返还";
			break;
		case 3:
			this.esda_usage_typestr = "奖金";
			break;
		case 4:
			this.esda_usage_typestr = "离职补偿金";
			break;
		case 5:
			this.esda_usage_typestr = "股票分红";
			break;
		default:
			this.esda_usage_typestr = "";
			break;
		}
	}

	public String getEsda_email() {
		return esda_email;
	}

	public void setEsda_email(String esda_email) {
		this.esda_email = esda_email;
	}

	public String getEsda_bcc_email() {
		return esda_bcc_email;
	}

	public void setEsda_bcc_email(String esda_bcc_email) {
		this.esda_bcc_email = esda_bcc_email;
	}

	public String getEsda_email_sender() {
		return esda_email_sender;
	}

	public void setEsda_email_sender(String esda_email_sender) {
		this.esda_email_sender = esda_email_sender;
	}

	public int getEsda_email_state() {
		return esda_email_state;
	}

	public void setEsda_email_state(int esda_email_state) {
		this.esda_email_state = esda_email_state;
		if (esda_email_state == 0) {
			esda_email_statestr = "未发";
		} else {
			esda_email_statestr = "已发";
		}
	}

	public String getEsda_email_date() {
		return esda_email_date;
	}

	public void setEsda_email_date(String esda_email_date) {
		this.esda_email_date = esda_email_date;
	}

	public int getEsda_oof_state() {
		return esda_oof_state;
	}

	public void setEsda_oof_state(int esda_oof_state) {
		this.esda_oof_state = esda_oof_state;
		switch (esda_oof_state) {
		case 0:
			this.esda_oof_statestr = "否";
			break;
		case 1:
			this.esda_oof_statestr = "是";
			break;
		default:
			this.esda_oof_statestr = "";
			break;
		}
	}

	public List<String> getSendType() {
		return sendType;
	}

	public void setSendType(List<String> sendType) {
		this.sendType = sendType;
	}

	public int getEsda_confirm_state() {
		return esda_confirm_state;
	}

	public void setEsda_confirm_state(int esda_confirm_state) {
		this.esda_confirm_state = esda_confirm_state;
	}

	public int getEsda_payment_state() {
		return esda_payment_state;
	}

	public void setEsda_payment_state(int esda_payment_state) {
		this.esda_payment_state = esda_payment_state;
		switch (esda_payment_state) {
		case 0:
			this.esda_payment_statestr = "未审核";
			break;
		case 1:
			this.esda_payment_statestr = "已审核";
			break;
		case 2:
			this.esda_payment_statestr = "已发放";
			break;
		case 3:
			this.esda_payment_statestr = "待确认";
			break;
		case 4:
			this.esda_payment_statestr = "待发放";
			break;
		default:
			this.esda_payment_statestr = "";
			break;
		}
	}

	public String getEsda_payment_date() {
		return esda_payment_date;
	}

	public void setEsda_payment_date(String esda_payment_date) {
		this.esda_payment_date = esda_payment_date;
	}

	public int getEsda_payment_batch() {
		return esda_payment_batch;
	}

	public void setEsda_payment_batch(int esda_payment_batch) {
		this.esda_payment_batch = esda_payment_batch;
	}

	public int getEsda_data_type() {
		return esda_data_type;
	}

	public void setEsda_data_type(int esda_data_type) {
		this.esda_data_type = esda_data_type;
		switch (esda_data_type) {
		case 0:
			esda_data_typestr = "正常";
			break;
		case 1:
			esda_data_typestr = "少发";
			break;
		case 2:
			esda_data_typestr = "多发";
			break;
		case 3:
			esda_data_typestr = "补发";
			break;
		}
	}

	public int getEsda_rp_reason() {
		return esda_rp_reason;
	}

	public void setEsda_rp_reason(int esda_rp_reason) {
		this.esda_rp_reason = esda_rp_reason;
	}

	public int getEsda_history_state() {
		return esda_history_state;
	}

	public void setEsda_history_state(int esda_history_state) {
		this.esda_history_state = esda_history_state;
	}

	public String getEsda_history_date() {
		return esda_history_date;
	}

	public void setEsda_history_date(String esda_history_date) {
		this.esda_history_date = esda_history_date;
	}

	public int getEsda_ttv_state() {
		return esda_ttv_state;
	}

	public void setEsda_ttv_state(int esda_ttv_state) {
		this.esda_ttv_state = esda_ttv_state;
	}

	public String getEsda_ttv_date() {
		return esda_ttv_date;
	}

	public void setEsda_ttv_date(String esda_ttv_date) {
		this.esda_ttv_date = esda_ttv_date;
	}

	public String getEsda_ttv_people() {
		return esda_ttv_people;
	}

	public void setEsda_ttv_people(String esda_ttv_people) {
		this.esda_ttv_people = esda_ttv_people;
	}

	public BigDecimal getEsda_base_radix() {
		return esda_base_radix;
	}

	public void setEsda_base_radix(BigDecimal esda_base_radix) {
		this.esda_base_radix = new BigDecimal(df.format(esda_base_radix));
	}

	public BigDecimal getEsda_a_base_radix() {
		return esda_a_base_radix;
	}

	public void setEsda_a_base_radix(BigDecimal esda_a_base_radix) {
		this.esda_a_base_radix = new BigDecimal(df.format(esda_a_base_radix));
	}

	public BigDecimal getEsda_a_workdays() {
		return esda_a_workdays;
	}

	public void setEsda_a_workdays(BigDecimal esda_a_workdays) {
		this.esda_a_workdays = new BigDecimal(df.format(esda_a_workdays));
	}

	public BigDecimal getEsda_a_base_salary() {
		return esda_a_base_salary;
	}

	public void setEsda_a_base_salary(BigDecimal esda_a_base_salary) {
		this.esda_a_base_salary = new BigDecimal(df.format(esda_a_base_salary));
	}

	public BigDecimal getEsda_days() {
		return esda_days;
	}

	public void setEsda_days(BigDecimal esda_days) {
		this.esda_days = new BigDecimal(df.format(esda_days));
	}

	public BigDecimal getEsda_cqdays() {
		return esda_cqdays;
	}

	public void setEsda_cqdays(BigDecimal esda_cqdays) {
		this.esda_cqdays = new BigDecimal(df.format(esda_cqdays));
	}

	public BigDecimal getEsda_b_cqdays() {
		return esda_b_cqdays;
	}

	public void setEsda_b_cqdays(BigDecimal esda_b_cqdays) {
		this.esda_b_cqdays = new BigDecimal(df.format(esda_b_cqdays));
	}

	public BigDecimal getEsda_qqdays() {
		return esda_qqdays;
	}

	public void setEsda_qqdays(BigDecimal esda_qqdays) {
		this.esda_qqdays = new BigDecimal(df.format(esda_qqdays));
	}

	public BigDecimal getEsda_b_qqdays() {
		return esda_b_qqdays;
	}

	public void setEsda_b_qqdays(BigDecimal esda_b_qqdays) {
		this.esda_b_qqdays = new BigDecimal(df.format(esda_b_qqdays));
	}

	public BigDecimal getEsda_aded() {
		return esda_aded;
	}

	public void setEsda_aded(BigDecimal esda_aded) {
		this.esda_aded = new BigDecimal(df.format(esda_aded));
	}

	public BigDecimal getEsda_a_aded() {
		return esda_a_aded;
	}

	public void setEsda_a_aded(BigDecimal esda_a_aded) {
		this.esda_a_aded = new BigDecimal(df.format(esda_a_aded));
	}

	public BigDecimal getEsda_base_salary() {
		return esda_base_salary;
	}

	public void setEsda_base_salary(BigDecimal esda_base_salary) {
		this.esda_base_salary = new BigDecimal(df.format(esda_base_salary));
	}

	public BigDecimal getEsda_siop() {
		return esda_siop;
	}

	public void setEsda_siop(BigDecimal esda_siop) {
		this.esda_siop = new BigDecimal(df.format(esda_siop));
	}

	public BigDecimal getEsda_hafop() {
		return esda_hafop;
	}

	public void setEsda_hafop(BigDecimal esda_hafop) {
		this.esda_hafop = new BigDecimal(df.format(esda_hafop));
	}

	public BigDecimal getEsda_haf() {
		return esda_haf;
	}

	public void setEsda_haf(BigDecimal esda_haf) {
		this.esda_haf = new BigDecimal(df.format(esda_haf));
	}

	public BigDecimal getEsda_house_radix() {
		return esda_house_radix;
	}

	public void setEsda_house_radix(BigDecimal esda_house_radix) {
		this.esda_house_radix = new BigDecimal(df.format(esda_house_radix));
	}

	public BigDecimal getEsda_house_percent() {
		return esda_house_percent;
	}

	public void setEsda_house_percent(BigDecimal esda_house_percent) {
		this.esda_house_percent = new BigDecimal(df.format(esda_house_percent));
	}

	public BigDecimal getEsda_house_ntl() {
		return esda_house_ntl;
	}

	public void setEsda_house_ntl(BigDecimal esda_house_ntl) {
		this.esda_house_ntl = new BigDecimal(df.format(esda_house_ntl));
	}

	public BigDecimal getEsda_total_pretax() {
		return esda_total_pretax;
	}

	public void setEsda_total_pretax(BigDecimal esda_total_pretax) {
		this.esda_total_pretax = new BigDecimal(df.format(esda_total_pretax));
	}

	public BigDecimal getEsda_tax_base() {
		return esda_tax_base;
	}

	public void setEsda_tax_base(BigDecimal esda_tax_base) {
		this.esda_tax_base = new BigDecimal(df.format(esda_tax_base));
	}

	public BigDecimal getEsda_tax() {
		return esda_tax;
	}

	public void setEsda_tax(BigDecimal esda_tax) {
		this.esda_tax = new BigDecimal(df.format(esda_tax));
	}

	public BigDecimal getEsda_dc() {
		return esda_dc;
	}

	public void setEsda_dc(BigDecimal esda_dc) {
		this.esda_dc = new BigDecimal(df.format(esda_dc));
	}

	public BigDecimal getEsda_dc_tax() {
		return esda_dc_tax;
	}

	public void setEsda_dc_tax(BigDecimal esda_dc_tax) {
		this.esda_dc_tax = new BigDecimal(df.format(esda_dc_tax));
	}

	public BigDecimal getEsda_db() {
		return esda_db;
	}

	public void setEsda_db(BigDecimal esda_db) {
		this.esda_db = new BigDecimal(df.format(esda_db));
	}

	public BigDecimal getEsda_db_tax_base() {
		return esda_db_tax_base;
	}

	public void setEsda_db_tax_base(BigDecimal esda_db_tax_base) {
		this.esda_db_tax_base = new BigDecimal(df.format(esda_db_tax_base));
	}

	public BigDecimal getEsda_db_tax() {
		return esda_db_tax;
	}

	public void setEsda_db_tax(BigDecimal esda_db_tax) {
		this.esda_db_tax = new BigDecimal(df.format(esda_db_tax));
	}

	public BigDecimal getEsda_stock() {
		return esda_stock;
	}

	public void setEsda_stock(BigDecimal esda_stock) {
		this.esda_stock = new BigDecimal(df.format(esda_stock));
	}

	public BigDecimal getEsda_stock_tax() {
		return esda_stock_tax;
	}

	public void setEsda_stock_tax(BigDecimal esda_stock_tax) {
		this.esda_stock_tax = new BigDecimal(df.format(esda_stock_tax));
	}

	public BigDecimal getEsda_other() {
		return esda_other;
	}

	public void setEsda_other(BigDecimal esda_other) {
		this.esda_other = new BigDecimal(df.format(esda_other));
	}

	public BigDecimal getEsda_other_tax() {
		return esda_other_tax;
	}

	public void setEsda_other_tax(BigDecimal esda_other_tax) {
		this.esda_other_tax = new BigDecimal(df.format(esda_other_tax));
	}

	public BigDecimal getEsda_write_off() {
		return esda_write_off;
	}

	public void setEsda_write_off(BigDecimal esda_write_off) {
		this.esda_write_off = new BigDecimal(df.format(esda_write_off));
	}

	public BigDecimal getEsda_house_bf() {
		return esda_house_bf;
	}

	public void setEsda_house_bf(BigDecimal esda_house_bf) {
		this.esda_house_bf = new BigDecimal(df.format(esda_house_bf));
	}

	public BigDecimal getEsda_pay() {
		return esda_pay;
	}

	public void setEsda_pay(BigDecimal esda_pay) {
		this.esda_pay = new BigDecimal(df.format(esda_pay));
	}

	public int getEsda_ifhold() {
		return esda_ifhold;
	}

	public void setEsda_ifhold(int esda_ifhold) {
		this.esda_ifhold = esda_ifhold;
		switch (esda_ifhold) {
		case 0:
			this.esda_ifholdstr = "正常";
			break;
		case 1:
			this.esda_ifholdstr = "挂起";
			break;
		default:
			this.esda_ifholdstr = "";
			break;
		}
	}

	public String getEsda_ifholdstr() {
		return esda_ifholdstr;
	}

	public void setEsda_ifholdstr(String esda_ifholdstr) {
		this.esda_ifholdstr = esda_ifholdstr;
	}

	public BigDecimal getEsda_col_1() {
		return esda_col_1;
	}

	public void setEsda_col_1(BigDecimal esda_col_1) {
		this.esda_col_1 = esda_col_1;
	}

	public BigDecimal getEsda_col_2() {
		return esda_col_2;
	}

	public void setEsda_col_2(BigDecimal esda_col_2) {
		this.esda_col_2 = esda_col_2;
	}

	public BigDecimal getEsda_col_3() {
		return esda_col_3;
	}

	public void setEsda_col_3(BigDecimal esda_col_3) {
		this.esda_col_3 = esda_col_3;
	}

	public BigDecimal getEsda_col_4() {
		return esda_col_4;
	}

	public void setEsda_col_4(BigDecimal esda_col_4) {
		this.esda_col_4 = esda_col_4;
	}

	public BigDecimal getEsda_col_5() {
		return esda_col_5;
	}

	public void setEsda_col_5(BigDecimal esda_col_5) {
		this.esda_col_5 = esda_col_5;
	}

	public BigDecimal getEsda_col_6() {
		return esda_col_6;
	}

	public void setEsda_col_6(BigDecimal esda_col_6) {
		this.esda_col_6 = esda_col_6;
	}

	public BigDecimal getEsda_col_7() {
		return esda_col_7;
	}

	public void setEsda_col_7(BigDecimal esda_col_7) {
		this.esda_col_7 = esda_col_7;
	}

	public BigDecimal getEsda_col_8() {
		return esda_col_8;
	}

	public void setEsda_col_8(BigDecimal esda_col_8) {
		this.esda_col_8 = esda_col_8;
	}

	public BigDecimal getEsda_col_9() {
		return esda_col_9;
	}

	public void setEsda_col_9(BigDecimal esda_col_9) {
		this.esda_col_9 = esda_col_9;
	}

	public BigDecimal getEsda_col_10() {
		return esda_col_10;
	}

	public void setEsda_col_10(BigDecimal esda_col_10) {
		this.esda_col_10 = esda_col_10;
	}

	public BigDecimal getEsda_col_11() {
		return esda_col_11;
	}

	public void setEsda_col_11(BigDecimal esda_col_11) {
		this.esda_col_11 = esda_col_11;
	}

	public BigDecimal getEsda_col_12() {
		return esda_col_12;
	}

	public void setEsda_col_12(BigDecimal esda_col_12) {
		this.esda_col_12 = esda_col_12;
	}

	public BigDecimal getEsda_col_13() {
		return esda_col_13;
	}

	public void setEsda_col_13(BigDecimal esda_col_13) {
		this.esda_col_13 = esda_col_13;
	}

	public BigDecimal getEsda_col_14() {
		return esda_col_14;
	}

	public void setEsda_col_14(BigDecimal esda_col_14) {
		this.esda_col_14 = esda_col_14;
	}

	public BigDecimal getEsda_col_15() {
		return esda_col_15;
	}

	public void setEsda_col_15(BigDecimal esda_col_15) {
		this.esda_col_15 = esda_col_15;
	}

	public BigDecimal getEsda_col_16() {
		return esda_col_16;
	}

	public void setEsda_col_16(BigDecimal esda_col_16) {
		this.esda_col_16 = esda_col_16;
	}

	public BigDecimal getEsda_col_17() {
		return esda_col_17;
	}

	public void setEsda_col_17(BigDecimal esda_col_17) {
		this.esda_col_17 = esda_col_17;
	}

	public BigDecimal getEsda_col_18() {
		return esda_col_18;
	}

	public void setEsda_col_18(BigDecimal esda_col_18) {
		this.esda_col_18 = esda_col_18;
	}

	public BigDecimal getEsda_col_19() {
		return esda_col_19;
	}

	public void setEsda_col_19(BigDecimal esda_col_19) {
		this.esda_col_19 = esda_col_19;
	}

	public BigDecimal getEsda_col_20() {
		return esda_col_20;
	}

	public void setEsda_col_20(BigDecimal esda_col_20) {
		this.esda_col_20 = esda_col_20;
	}

	public BigDecimal getEsda_col_21() {
		return esda_col_21;
	}

	public void setEsda_col_21(BigDecimal esda_col_21) {
		this.esda_col_21 = esda_col_21;
	}

	public BigDecimal getEsda_col_22() {
		return esda_col_22;
	}

	public void setEsda_col_22(BigDecimal esda_col_22) {
		this.esda_col_22 = esda_col_22;
	}

	public BigDecimal getEsda_col_23() {
		return esda_col_23;
	}

	public void setEsda_col_23(BigDecimal esda_col_23) {
		this.esda_col_23 = esda_col_23;
	}

	public BigDecimal getEsda_col_24() {
		return esda_col_24;
	}

	public void setEsda_col_24(BigDecimal esda_col_24) {
		this.esda_col_24 = esda_col_24;
	}

	public BigDecimal getEsda_col_25() {
		return esda_col_25;
	}

	public void setEsda_col_25(BigDecimal esda_col_25) {
		this.esda_col_25 = esda_col_25;
	}

	public BigDecimal getEsda_col_26() {
		return esda_col_26;
	}

	public void setEsda_col_26(BigDecimal esda_col_26) {
		this.esda_col_26 = esda_col_26;
	}

	public BigDecimal getEsda_col_27() {
		return esda_col_27;
	}

	public void setEsda_col_27(BigDecimal esda_col_27) {
		this.esda_col_27 = esda_col_27;
	}

	public BigDecimal getEsda_col_28() {
		return esda_col_28;
	}

	public void setEsda_col_28(BigDecimal esda_col_28) {
		this.esda_col_28 = esda_col_28;
	}

	public BigDecimal getEsda_col_29() {
		return esda_col_29;
	}

	public void setEsda_col_29(BigDecimal esda_col_29) {
		this.esda_col_29 = esda_col_29;
	}

	public BigDecimal getEsda_col_30() {
		return esda_col_30;
	}

	public void setEsda_col_30(BigDecimal esda_col_30) {
		this.esda_col_30 = esda_col_30;
	}

	public BigDecimal getEsda_col_31() {
		return esda_col_31;
	}

	public void setEsda_col_31(BigDecimal esda_col_31) {
		this.esda_col_31 = esda_col_31;
	}

	public BigDecimal getEsda_col_32() {
		return esda_col_32;
	}

	public void setEsda_col_32(BigDecimal esda_col_32) {
		this.esda_col_32 = esda_col_32;
	}

	public BigDecimal getEsda_col_33() {
		return esda_col_33;
	}

	public void setEsda_col_33(BigDecimal esda_col_33) {
		this.esda_col_33 = esda_col_33;
	}

	public BigDecimal getEsda_col_34() {
		return esda_col_34;
	}

	public void setEsda_col_34(BigDecimal esda_col_34) {
		this.esda_col_34 = esda_col_34;
	}

	public BigDecimal getEsda_col_35() {
		return esda_col_35;
	}

	public void setEsda_col_35(BigDecimal esda_col_35) {
		this.esda_col_35 = esda_col_35;
	}

	public BigDecimal getEsda_col_36() {
		return esda_col_36;
	}

	public void setEsda_col_36(BigDecimal esda_col_36) {
		this.esda_col_36 = esda_col_36;
	}

	public BigDecimal getEsda_col_37() {
		return esda_col_37;
	}

	public void setEsda_col_37(BigDecimal esda_col_37) {
		this.esda_col_37 = esda_col_37;
	}

	public BigDecimal getEsda_col_38() {
		return esda_col_38;
	}

	public void setEsda_col_38(BigDecimal esda_col_38) {
		this.esda_col_38 = esda_col_38;
	}

	public BigDecimal getEsda_col_39() {
		return esda_col_39;
	}

	public void setEsda_col_39(BigDecimal esda_col_39) {
		this.esda_col_39 = esda_col_39;
	}

	public BigDecimal getEsda_col_40() {
		return esda_col_40;
	}

	public void setEsda_col_40(BigDecimal esda_col_40) {
		this.esda_col_40 = esda_col_40;
	}

	public BigDecimal getEsda_col_41() {
		return esda_col_41;
	}

	public void setEsda_col_41(BigDecimal esda_col_41) {
		this.esda_col_41 = esda_col_41;
	}

	public BigDecimal getEsda_col_42() {
		return esda_col_42;
	}

	public void setEsda_col_42(BigDecimal esda_col_42) {
		this.esda_col_42 = esda_col_42;
	}

	public BigDecimal getEsda_col_43() {
		return esda_col_43;
	}

	public void setEsda_col_43(BigDecimal esda_col_43) {
		this.esda_col_43 = esda_col_43;
	}

	public BigDecimal getEsda_col_44() {
		return esda_col_44;
	}

	public void setEsda_col_44(BigDecimal esda_col_44) {
		this.esda_col_44 = esda_col_44;
	}

	public BigDecimal getEsda_col_45() {
		return esda_col_45;
	}

	public void setEsda_col_45(BigDecimal esda_col_45) {
		this.esda_col_45 = esda_col_45;
	}

	public BigDecimal getEsda_col_46() {
		return esda_col_46;
	}

	public void setEsda_col_46(BigDecimal esda_col_46) {
		this.esda_col_46 = esda_col_46;
	}

	public BigDecimal getEsda_col_47() {
		return esda_col_47;
	}

	public void setEsda_col_47(BigDecimal esda_col_47) {
		this.esda_col_47 = esda_col_47;
	}

	public BigDecimal getEsda_col_48() {
		return esda_col_48;
	}

	public void setEsda_col_48(BigDecimal esda_col_48) {
		this.esda_col_48 = esda_col_48;
	}

	public BigDecimal getEsda_col_49() {
		return esda_col_49;
	}

	public void setEsda_col_49(BigDecimal esda_col_49) {
		this.esda_col_49 = esda_col_49;
	}

	public BigDecimal getEsda_col_50() {
		return esda_col_50;
	}

	public void setEsda_col_50(BigDecimal esda_col_50) {
		this.esda_col_50 = esda_col_50;
	}

	public BigDecimal getEsda_col_51() {
		return esda_col_51;
	}

	public void setEsda_col_51(BigDecimal esda_col_51) {
		this.esda_col_51 = esda_col_51;
	}

	public BigDecimal getEsda_col_52() {
		return esda_col_52;
	}

	public void setEsda_col_52(BigDecimal esda_col_52) {
		this.esda_col_52 = esda_col_52;
	}

	public BigDecimal getEsda_col_53() {
		return esda_col_53;
	}

	public void setEsda_col_53(BigDecimal esda_col_53) {
		this.esda_col_53 = esda_col_53;
	}

	public BigDecimal getEsda_col_54() {
		return esda_col_54;
	}

	public void setEsda_col_54(BigDecimal esda_col_54) {
		this.esda_col_54 = esda_col_54;
	}

	public BigDecimal getEsda_col_55() {
		return esda_col_55;
	}

	public void setEsda_col_55(BigDecimal esda_col_55) {
		this.esda_col_55 = esda_col_55;
	}

	public BigDecimal getEsda_col_56() {
		return esda_col_56;
	}

	public void setEsda_col_56(BigDecimal esda_col_56) {
		this.esda_col_56 = esda_col_56;
	}

	public BigDecimal getEsda_col_57() {
		return esda_col_57;
	}

	public void setEsda_col_57(BigDecimal esda_col_57) {
		this.esda_col_57 = esda_col_57;
	}

	public BigDecimal getEsda_col_58() {
		return esda_col_58;
	}

	public void setEsda_col_58(BigDecimal esda_col_58) {
		this.esda_col_58 = esda_col_58;
	}

	public BigDecimal getEsda_col_59() {
		return esda_col_59;
	}

	public void setEsda_col_59(BigDecimal esda_col_59) {
		this.esda_col_59 = esda_col_59;
	}

	public BigDecimal getEsda_col_60() {
		return esda_col_60;
	}

	public void setEsda_col_60(BigDecimal esda_col_60) {
		this.esda_col_60 = esda_col_60;
	}

	public BigDecimal getEsda_col_61() {
		return esda_col_61;
	}

	public void setEsda_col_61(BigDecimal esda_col_61) {
		this.esda_col_61 = esda_col_61;
	}

	public BigDecimal getEsda_col_62() {
		return esda_col_62;
	}

	public void setEsda_col_62(BigDecimal esda_col_62) {
		this.esda_col_62 = esda_col_62;
	}

	public BigDecimal getEsda_col_63() {
		return esda_col_63;
	}

	public void setEsda_col_63(BigDecimal esda_col_63) {
		this.esda_col_63 = esda_col_63;
	}

	public BigDecimal getEsda_col_64() {
		return esda_col_64;
	}

	public void setEsda_col_64(BigDecimal esda_col_64) {
		this.esda_col_64 = esda_col_64;
	}

	public BigDecimal getEsda_col_65() {
		return esda_col_65;
	}

	public void setEsda_col_65(BigDecimal esda_col_65) {
		this.esda_col_65 = esda_col_65;
	}

	public BigDecimal getEsda_col_66() {
		return esda_col_66;
	}

	public void setEsda_col_66(BigDecimal esda_col_66) {
		this.esda_col_66 = esda_col_66;
	}

	public BigDecimal getEsda_col_67() {
		return esda_col_67;
	}

	public void setEsda_col_67(BigDecimal esda_col_67) {
		this.esda_col_67 = esda_col_67;
	}

	public BigDecimal getEsda_col_68() {
		return esda_col_68;
	}

	public void setEsda_col_68(BigDecimal esda_col_68) {
		this.esda_col_68 = esda_col_68;
	}

	public BigDecimal getEsda_col_69() {
		return esda_col_69;
	}

	public void setEsda_col_69(BigDecimal esda_col_69) {
		this.esda_col_69 = esda_col_69;
	}

	public BigDecimal getEsda_col_70() {
		return esda_col_70;
	}

	public void setEsda_col_70(BigDecimal esda_col_70) {
		this.esda_col_70 = esda_col_70;
	}

	public BigDecimal getEsda_col_71() {
		return esda_col_71;
	}

	public void setEsda_col_71(BigDecimal esda_col_71) {
		this.esda_col_71 = esda_col_71;
	}

	public BigDecimal getEsda_col_72() {
		return esda_col_72;
	}

	public void setEsda_col_72(BigDecimal esda_col_72) {
		this.esda_col_72 = esda_col_72;
	}

	public BigDecimal getEsda_col_73() {
		return esda_col_73;
	}

	public void setEsda_col_73(BigDecimal esda_col_73) {
		this.esda_col_73 = esda_col_73;
	}

	public BigDecimal getEsda_col_74() {
		return esda_col_74;
	}

	public void setEsda_col_74(BigDecimal esda_col_74) {
		this.esda_col_74 = esda_col_74;
	}

	public BigDecimal getEsda_col_75() {
		return esda_col_75;
	}

	public void setEsda_col_75(BigDecimal esda_col_75) {
		this.esda_col_75 = esda_col_75;
	}

	public BigDecimal getEsda_col_76() {
		return esda_col_76;
	}

	public void setEsda_col_76(BigDecimal esda_col_76) {
		this.esda_col_76 = esda_col_76;
	}

	public BigDecimal getEsda_col_77() {
		return esda_col_77;
	}

	public void setEsda_col_77(BigDecimal esda_col_77) {
		this.esda_col_77 = esda_col_77;
	}

	public BigDecimal getEsda_col_78() {
		return esda_col_78;
	}

	public void setEsda_col_78(BigDecimal esda_col_78) {
		this.esda_col_78 = esda_col_78;
	}

	public BigDecimal getEsda_col_79() {
		return esda_col_79;
	}

	public void setEsda_col_79(BigDecimal esda_col_79) {
		this.esda_col_79 = esda_col_79;
	}

	public BigDecimal getEsda_col_80() {
		return esda_col_80;
	}

	public void setEsda_col_80(BigDecimal esda_col_80) {
		this.esda_col_80 = esda_col_80;
	}

	public BigDecimal getEsda_hafcp() {
		return esda_hafcp;
	}

	public void setEsda_hafcp(BigDecimal esda_hafcp) {
		this.esda_hafcp = esda_hafcp;
	}

	public BigDecimal getEsda_house_cradix() {
		return esda_house_cradix;
	}

	public void setEsda_house_cradix(BigDecimal esda_house_cradix) {
		this.esda_house_cradix = esda_house_cradix;
	}

	public BigDecimal getEsda_house_cpercent() {
		return esda_house_cpercent;
	}

	public void setEsda_house_cpercent(BigDecimal esda_house_cpercent) {
		this.esda_house_cpercent = esda_house_cpercent;
	}

	public String getCoba_shortname() {
		return coba_shortname;
	}

	public void setCoba_shortname(String coba_shortname) {
		this.coba_shortname = coba_shortname;
	}

	public String getCoba_namespell() {
		return coba_namespell;
	}

	public void setCoba_namespell(String coba_namespell) {
		this.coba_namespell = coba_namespell;
	}

	public String getEmba_spell() {
		return emba_spell;
	}

	public void setEmba_spell(String emba_spell) {
		this.emba_spell = emba_spell;
	}

	public String getEsda_email_statestr() {
		return esda_email_statestr;
	}

	public void setEsda_email_statestr(String esda_email_statestr) {
		this.esda_email_statestr = esda_email_statestr;
	}

	public BigDecimal getEsda_lw_tax_base() {
		return esda_lw_tax_base;
	}

	public void setEsda_lw_tax_base(BigDecimal esda_lw_tax_base) {
		this.esda_lw_tax_base = new BigDecimal(df.format(esda_lw_tax_base));
	}

	public BigDecimal getEsda_lw_tax() {
		return esda_lw_tax;
	}

	public void setEsda_lw_tax(BigDecimal esda_lw_tax) {
		this.esda_lw_tax = new BigDecimal(df.format(esda_lw_tax));
	}

	public BigDecimal getEsda_total_adjtax() {
		return esda_total_adjtax;
	}

	public void setEsda_total_adjtax(BigDecimal esda_total_adjtax) {
		this.esda_total_adjtax = new BigDecimal(df.format(esda_total_adjtax));
	}

	public BigDecimal getEsda_total_afttax() {
		return esda_total_afttax;
	}

	public void setEsda_total_afttax(BigDecimal esda_total_afttax) {
		this.esda_total_afttax = new BigDecimal(df.format(esda_total_afttax));
	}

	public int getTbrb_id() {
		return tbrb_id;
	}

	public void setTbrb_id(int tbrb_id) {
		this.tbrb_id = tbrb_id;
	}

	public int getTbrb_customInt() {
		return tbrb_customInt;
	}

	public void setTbrb_customInt(int tbrb_customInt) {
		this.tbrb_customInt = tbrb_customInt;
	}

	public int getEsdt_id() {
		return esdt_id;
	}

	public void setEsdt_id(int esdt_id) {
		this.esdt_id = esdt_id;
	}

	public EmSalaryDataModel(String name, int esda_id, int cid, int gid,
			int ownmonth, int esda_if_bms, String esda_addname,
			String esda_addtime, String esda_remark, String esda_ba_name,
			int esda_oof_state, int esda_confirm_state,
			String esda_payment_date, BigDecimal esda_pay,
			String esda_usage_typestr, String esda_oof_statestr,
			String esda_if_bmsstr, String esda_remarkstr,
			String esda_payment_statestr) {
		super();
		this.name = name;
		this.esda_id = esda_id;
		this.cid = cid;
		this.gid = gid;
		this.ownmonth = ownmonth;
		this.esda_if_bms = esda_if_bms;
		this.esda_addname = esda_addname;
		this.esda_addtime = esda_addtime;
		this.esda_remark = esda_remark;
		this.esda_ba_name = esda_ba_name;
		this.esda_oof_state = esda_oof_state;
		this.esda_confirm_state = esda_confirm_state;
		this.esda_payment_date = esda_payment_date;
		this.esda_pay = esda_pay;
		this.esda_usage_typestr = esda_usage_typestr;
		this.esda_oof_statestr = esda_oof_statestr;
		this.esda_if_bmsstr = esda_if_bmsstr;
		this.esda_remarkstr = esda_remarkstr;
		this.esda_payment_statestr = esda_payment_statestr;
	}

	public EmSalaryDataModel(int esda_id, int cid, int gid, int ownmonth,
			int esda_if_bms, String esda_addname, String esda_addtime,
			String esda_remark, String esda_fd_remark, int cfin_id,
			String csii_itemid, String esda_o_bank, String esda_bank,
			String esda_ba_name, String esda_bank_account,
			String esda_nationality, int esda_usage_type, String esda_email,
			String esda_bcc_email, String esda_email_sender,
			int esda_email_state, String esda_email_date, int esda_oof_state,
			int esda_confirm_state, int esda_payment_state,
			String esda_payment_date, int esda_payment_batch,
			int esda_data_type, int esda_rp_reason, int esda_history_state,
			String esda_history_date, int esda_ttv_state, String esda_ttv_date,
			String esda_ttv_people, BigDecimal esda_base_radix,
			BigDecimal esda_a_base_radix, BigDecimal esda_a_workdays,
			BigDecimal esda_a_base_salary, BigDecimal esda_days,
			BigDecimal esda_cqdays, BigDecimal esda_b_cqdays,
			BigDecimal esda_qqdays, BigDecimal esda_b_qqdays,
			BigDecimal esda_aded, BigDecimal esda_a_aded,
			BigDecimal esda_base_salary, BigDecimal esda_siop,
			BigDecimal esda_hafop, BigDecimal esda_haf,
			BigDecimal esda_house_radix, BigDecimal esda_house_percent,
			BigDecimal esda_house_ntl, BigDecimal esda_total_pretax,
			BigDecimal esda_tax_base, BigDecimal esda_tax, BigDecimal esda_dc,
			BigDecimal esda_dc_tax, BigDecimal esda_db,
			BigDecimal esda_db_tax_base, BigDecimal esda_db_tax,
			BigDecimal esda_stock, BigDecimal esda_stock_tax,
			BigDecimal esda_other, BigDecimal esda_other_tax,
			BigDecimal esda_write_off, BigDecimal esda_house_bf,
			BigDecimal esda_pay, BigDecimal esda_col_1, BigDecimal esda_col_2,
			BigDecimal esda_col_3, BigDecimal esda_col_4,
			BigDecimal esda_col_5, BigDecimal esda_col_6,
			BigDecimal esda_col_7, BigDecimal esda_col_8,
			BigDecimal esda_col_9, BigDecimal esda_col_10,
			BigDecimal esda_col_11, BigDecimal esda_col_12,
			BigDecimal esda_col_13, BigDecimal esda_col_14,
			BigDecimal esda_col_15, BigDecimal esda_col_16,
			BigDecimal esda_col_17, BigDecimal esda_col_18,
			BigDecimal esda_col_19, BigDecimal esda_col_20,
			BigDecimal esda_col_21, BigDecimal esda_col_22,
			BigDecimal esda_col_23, BigDecimal esda_col_24,
			BigDecimal esda_col_25, BigDecimal esda_col_26,
			BigDecimal esda_col_27, BigDecimal esda_col_28,
			BigDecimal esda_col_29, BigDecimal esda_col_30,
			BigDecimal esda_col_31, BigDecimal esda_col_32,
			BigDecimal esda_col_33, BigDecimal esda_col_34,
			BigDecimal esda_col_35, BigDecimal esda_col_36,
			BigDecimal esda_col_37, BigDecimal esda_col_38,
			BigDecimal esda_col_39, BigDecimal esda_col_40,
			BigDecimal esda_col_41, BigDecimal esda_col_42,
			BigDecimal esda_col_43, BigDecimal esda_col_44,
			BigDecimal esda_col_45, BigDecimal esda_col_46,
			BigDecimal esda_col_47, BigDecimal esda_col_48,
			BigDecimal esda_col_49, BigDecimal esda_col_50,
			BigDecimal esda_col_51, BigDecimal esda_col_52,
			BigDecimal esda_col_53, BigDecimal esda_col_54,
			BigDecimal esda_col_55, BigDecimal esda_col_56,
			BigDecimal esda_col_57, BigDecimal esda_col_58,
			BigDecimal esda_col_59, BigDecimal esda_col_60,
			BigDecimal esda_col_61, BigDecimal esda_col_62,
			BigDecimal esda_col_63, BigDecimal esda_col_64,
			BigDecimal esda_col_65, BigDecimal esda_col_66,
			BigDecimal esda_col_67, BigDecimal esda_col_68,
			BigDecimal esda_col_69, BigDecimal esda_col_70,
			BigDecimal esda_col_71, BigDecimal esda_col_72,
			BigDecimal esda_col_73, BigDecimal esda_col_74,
			BigDecimal esda_col_75, BigDecimal esda_col_76,
			BigDecimal esda_col_77, BigDecimal esda_col_78,
			BigDecimal esda_col_79, BigDecimal esda_col_80, String name) {
		super();
		this.esda_id = esda_id;
		this.cid = cid;
		this.gid = gid;
		this.ownmonth = ownmonth;
		this.esda_if_bms = esda_if_bms;
		this.esda_addname = esda_addname;
		this.esda_addtime = esda_addtime;
		this.esda_remark = esda_remark;
		this.esda_fd_remark = esda_fd_remark;
		this.cfin_id = cfin_id;
		this.csii_itemid = csii_itemid;
		this.esda_o_bank = esda_o_bank;
		this.esda_bank = esda_bank;
		this.esda_ba_name = esda_ba_name;
		this.esda_bank_account = esda_bank_account;
		this.esda_nationality = esda_nationality;
		setEsda_usage_type(esda_usage_type);
		this.esda_email = esda_email;
		this.esda_bcc_email = esda_bcc_email;
		this.esda_email_sender = esda_email_sender;
		this.esda_email_state = esda_email_state;
		this.esda_email_date = esda_email_date;
		this.esda_oof_state = esda_oof_state;
		this.esda_confirm_state = esda_confirm_state;
		this.esda_payment_state = esda_payment_state;
		this.esda_payment_date = esda_payment_date;
		this.esda_payment_batch = esda_payment_batch;
		this.esda_data_type = esda_data_type;
		this.esda_rp_reason = esda_rp_reason;
		this.esda_history_state = esda_history_state;
		this.esda_history_date = esda_history_date;
		this.esda_ttv_state = esda_ttv_state;
		this.esda_ttv_date = esda_ttv_date;
		this.esda_ttv_people = esda_ttv_people;
		this.esda_base_radix = esda_base_radix;
		this.esda_a_base_radix = esda_a_base_radix;
		this.esda_a_workdays = esda_a_workdays;
		this.esda_a_base_salary = esda_a_base_salary;
		this.esda_days = esda_days;
		this.esda_cqdays = esda_cqdays;
		this.esda_b_cqdays = esda_b_cqdays;
		this.esda_qqdays = esda_qqdays;
		this.esda_b_qqdays = esda_b_qqdays;
		this.esda_aded = esda_aded;
		this.esda_a_aded = esda_a_aded;
		this.esda_base_salary = esda_base_salary;
		this.esda_siop = esda_siop;
		this.esda_hafop = esda_hafop;
		this.esda_haf = esda_haf;
		this.esda_house_radix = esda_house_radix;
		this.esda_house_percent = esda_house_percent;
		this.esda_house_ntl = esda_house_ntl;
		this.esda_total_pretax = esda_total_pretax;
		this.esda_tax_base = esda_tax_base;
		this.esda_tax = esda_tax;
		this.esda_dc = esda_dc;
		this.esda_dc_tax = esda_dc_tax;
		this.esda_db = esda_db;
		this.esda_db_tax_base = esda_db_tax_base;
		this.esda_db_tax = esda_db_tax;
		this.esda_stock = esda_stock;
		this.esda_stock_tax = esda_stock_tax;
		this.esda_other = esda_other;
		this.esda_other_tax = esda_other_tax;
		this.esda_write_off = esda_write_off;
		this.esda_house_bf = esda_house_bf;
		this.esda_pay = esda_pay;
		this.esda_col_1 = esda_col_1;
		this.esda_col_2 = esda_col_2;
		this.esda_col_3 = esda_col_3;
		this.esda_col_4 = esda_col_4;
		this.esda_col_5 = esda_col_5;
		this.esda_col_6 = esda_col_6;
		this.esda_col_7 = esda_col_7;
		this.esda_col_8 = esda_col_8;
		this.esda_col_9 = esda_col_9;
		this.esda_col_10 = esda_col_10;
		this.esda_col_11 = esda_col_11;
		this.esda_col_12 = esda_col_12;
		this.esda_col_13 = esda_col_13;
		this.esda_col_14 = esda_col_14;
		this.esda_col_15 = esda_col_15;
		this.esda_col_16 = esda_col_16;
		this.esda_col_17 = esda_col_17;
		this.esda_col_18 = esda_col_18;
		this.esda_col_19 = esda_col_19;
		this.esda_col_20 = esda_col_20;
		this.esda_col_21 = esda_col_21;
		this.esda_col_22 = esda_col_22;
		this.esda_col_23 = esda_col_23;
		this.esda_col_24 = esda_col_24;
		this.esda_col_25 = esda_col_25;
		this.esda_col_26 = esda_col_26;
		this.esda_col_27 = esda_col_27;
		this.esda_col_28 = esda_col_28;
		this.esda_col_29 = esda_col_29;
		this.esda_col_30 = esda_col_30;
		this.esda_col_31 = esda_col_31;
		this.esda_col_32 = esda_col_32;
		this.esda_col_33 = esda_col_33;
		this.esda_col_34 = esda_col_34;
		this.esda_col_35 = esda_col_35;
		this.esda_col_36 = esda_col_36;
		this.esda_col_37 = esda_col_37;
		this.esda_col_38 = esda_col_38;
		this.esda_col_39 = esda_col_39;
		this.esda_col_40 = esda_col_40;
		this.esda_col_41 = esda_col_41;
		this.esda_col_42 = esda_col_42;
		this.esda_col_43 = esda_col_43;
		this.esda_col_44 = esda_col_44;
		this.esda_col_45 = esda_col_45;
		this.esda_col_46 = esda_col_46;
		this.esda_col_47 = esda_col_47;
		this.esda_col_48 = esda_col_48;
		this.esda_col_49 = esda_col_49;
		this.esda_col_50 = esda_col_50;
		this.esda_col_51 = esda_col_51;
		this.esda_col_52 = esda_col_52;
		this.esda_col_53 = esda_col_53;
		this.esda_col_54 = esda_col_54;
		this.esda_col_55 = esda_col_55;
		this.esda_col_56 = esda_col_56;
		this.esda_col_57 = esda_col_57;
		this.esda_col_58 = esda_col_58;
		this.esda_col_59 = esda_col_59;
		this.esda_col_60 = esda_col_60;
		this.esda_col_61 = esda_col_61;
		this.esda_col_62 = esda_col_62;
		this.esda_col_63 = esda_col_63;
		this.esda_col_64 = esda_col_64;
		this.esda_col_65 = esda_col_65;
		this.esda_col_66 = esda_col_66;
		this.esda_col_67 = esda_col_67;
		this.esda_col_68 = esda_col_68;
		this.esda_col_69 = esda_col_69;
		this.esda_col_70 = esda_col_70;
		this.esda_col_71 = esda_col_71;
		this.esda_col_72 = esda_col_72;
		this.esda_col_73 = esda_col_73;
		this.esda_col_74 = esda_col_74;
		this.esda_col_75 = esda_col_75;
		this.esda_col_76 = esda_col_76;
		this.esda_col_77 = esda_col_77;
		this.esda_col_78 = esda_col_78;
		this.esda_col_79 = esda_col_79;
		this.esda_col_80 = esda_col_80;
		this.name = name;

		switch (esda_oof_state) {
		case 0:
			this.esda_oof_statestr = "台账内";
			break;
		case 1:
			this.esda_oof_statestr = "台账外";
			break;
		default:
			this.esda_oof_statestr = "";
			break;
		}

		switch (esda_payment_state) {
		case 0:
			this.esda_payment_statestr = "未审核";
			break;
		case 1:
			this.esda_payment_statestr = "已审核";
			break;
		case 2:
			this.esda_payment_statestr = "已发放";
			break;
		case 3:
			this.esda_payment_statestr = "待确认";
			break;
		case 4:
			this.esda_payment_statestr = "待发放";
			break;
		default:
			this.esda_payment_statestr = "";
			break;
		}
		switch (esda_if_bms) {
		case 0:
			this.esda_if_bmsstr = "系统外";
			break;
		case 1:
			this.esda_if_bmsstr = "系统内";
			break;
		default:
			this.esda_if_bmsstr = "";
			break;
		}

		if (esda_remark == null || esda_remark == "") {
			this.esda_remarkstr = "";
		} else {
			this.esda_remarkstr = "已备注";
		}

		if (esda_email_state == 0) {
			esda_email_statestr = "未发";
		} else {
			esda_email_statestr = "已发";
		}

	}

	public EmSalaryDataModel(int esda_id, int cid, int gid, int ownmonth,
			int esda_if_bms, String esda_addname, String esda_addtime,
			String esda_remark, String esda_fd_remark, int cfin_id,
			String csii_itemid, String esda_o_bank, String esda_bank,
			String esda_ba_name, String esda_bank_account,
			String esda_nationality, int esda_usage_type, String esda_email,
			String esda_bcc_email, String esda_email_sender,
			int esda_email_state, String esda_email_date, int esda_oof_state,
			int esda_confirm_state, int esda_payment_state,
			String esda_payment_date, int esda_payment_batch,
			int esda_data_type, int esda_rp_reason, int esda_history_state,
			String esda_history_date, int esda_ttv_state, String esda_ttv_date,
			String esda_ttv_people, BigDecimal esda_base_radix,
			BigDecimal esda_a_base_radix, BigDecimal esda_a_workdays,
			BigDecimal esda_a_base_salary, BigDecimal esda_days,
			BigDecimal esda_cqdays, BigDecimal esda_b_cqdays,
			BigDecimal esda_qqdays, BigDecimal esda_b_qqdays,
			BigDecimal esda_aded, BigDecimal esda_a_aded,
			BigDecimal esda_base_salary, BigDecimal esda_siop,
			BigDecimal esda_hafop, BigDecimal esda_haf,
			BigDecimal esda_house_radix, BigDecimal esda_house_percent,
			BigDecimal esda_house_ntl, BigDecimal esda_total_pretax,
			BigDecimal esda_tax_base, BigDecimal esda_tax, BigDecimal esda_dc,
			BigDecimal esda_dc_tax, BigDecimal esda_db,
			BigDecimal esda_db_tax_base, BigDecimal esda_db_tax,
			BigDecimal esda_stock, BigDecimal esda_stock_tax,
			BigDecimal esda_other, BigDecimal esda_other_tax,
			BigDecimal esda_write_off, BigDecimal esda_house_bf,
			BigDecimal esda_pay, BigDecimal esda_col_1, BigDecimal esda_col_2,
			BigDecimal esda_col_3, BigDecimal esda_col_4,
			BigDecimal esda_col_5, BigDecimal esda_col_6,
			BigDecimal esda_col_7, BigDecimal esda_col_8,
			BigDecimal esda_col_9, BigDecimal esda_col_10,
			BigDecimal esda_col_11, BigDecimal esda_col_12,
			BigDecimal esda_col_13, BigDecimal esda_col_14,
			BigDecimal esda_col_15, BigDecimal esda_col_16,
			BigDecimal esda_col_17, BigDecimal esda_col_18,
			BigDecimal esda_col_19, BigDecimal esda_col_20,
			BigDecimal esda_col_21, BigDecimal esda_col_22,
			BigDecimal esda_col_23, BigDecimal esda_col_24,
			BigDecimal esda_col_25, BigDecimal esda_col_26,
			BigDecimal esda_col_27, BigDecimal esda_col_28,
			BigDecimal esda_col_29, BigDecimal esda_col_30,
			BigDecimal esda_col_31, BigDecimal esda_col_32,
			BigDecimal esda_col_33, BigDecimal esda_col_34,
			BigDecimal esda_col_35, BigDecimal esda_col_36,
			BigDecimal esda_col_37, BigDecimal esda_col_38,
			BigDecimal esda_col_39, BigDecimal esda_col_40,
			BigDecimal esda_col_41, BigDecimal esda_col_42,
			BigDecimal esda_col_43, BigDecimal esda_col_44,
			BigDecimal esda_col_45, BigDecimal esda_col_46,
			BigDecimal esda_col_47, BigDecimal esda_col_48,
			BigDecimal esda_col_49, BigDecimal esda_col_50,
			BigDecimal esda_col_51, BigDecimal esda_col_52,
			BigDecimal esda_col_53, BigDecimal esda_col_54,
			BigDecimal esda_col_55, BigDecimal esda_col_56,
			BigDecimal esda_col_57, BigDecimal esda_col_58,
			BigDecimal esda_col_59, BigDecimal esda_col_60,
			BigDecimal esda_col_61, BigDecimal esda_col_62,
			BigDecimal esda_col_63, BigDecimal esda_col_64,
			BigDecimal esda_col_65, BigDecimal esda_col_66,
			BigDecimal esda_col_67, BigDecimal esda_col_68,
			BigDecimal esda_col_69, BigDecimal esda_col_70,
			BigDecimal esda_col_71, BigDecimal esda_col_72,
			BigDecimal esda_col_73, BigDecimal esda_col_74,
			BigDecimal esda_col_75, BigDecimal esda_col_76,
			BigDecimal esda_col_77, BigDecimal esda_col_78,
			BigDecimal esda_col_79, BigDecimal esda_col_80, String name,
			int esda_ifhold) {
		super();
		this.esda_id = esda_id;
		this.cid = cid;
		this.gid = gid;
		this.ownmonth = ownmonth;
		this.esda_if_bms = esda_if_bms;
		this.esda_addname = esda_addname;
		this.esda_addtime = esda_addtime;
		this.esda_remark = esda_remark;
		this.esda_fd_remark = esda_fd_remark;
		this.cfin_id = cfin_id;
		this.csii_itemid = csii_itemid;
		this.esda_o_bank = esda_o_bank;
		this.esda_bank = esda_bank;
		this.esda_ba_name = esda_ba_name;
		this.esda_bank_account = esda_bank_account;
		this.esda_nationality = esda_nationality;
		setEsda_usage_type(esda_usage_type);
		this.esda_email = esda_email;
		this.esda_bcc_email = esda_bcc_email;
		this.esda_email_sender = esda_email_sender;
		this.esda_email_state = esda_email_state;
		this.esda_email_date = esda_email_date;
		this.esda_oof_state = esda_oof_state;
		this.esda_confirm_state = esda_confirm_state;
		this.esda_payment_state = esda_payment_state;
		this.esda_payment_date = esda_payment_date;
		this.esda_payment_batch = esda_payment_batch;
		this.esda_data_type = esda_data_type;
		this.esda_rp_reason = esda_rp_reason;
		this.esda_history_state = esda_history_state;
		this.esda_history_date = esda_history_date;
		this.esda_ttv_state = esda_ttv_state;
		this.esda_ttv_date = esda_ttv_date;
		this.esda_ttv_people = esda_ttv_people;
		this.esda_base_radix = esda_base_radix;
		this.esda_a_base_radix = esda_a_base_radix;
		this.esda_a_workdays = esda_a_workdays;
		this.esda_a_base_salary = esda_a_base_salary;
		this.esda_days = esda_days;
		this.esda_cqdays = esda_cqdays;
		this.esda_b_cqdays = esda_b_cqdays;
		this.esda_qqdays = esda_qqdays;
		this.esda_b_qqdays = esda_b_qqdays;
		this.esda_aded = esda_aded;
		this.esda_a_aded = esda_a_aded;
		this.esda_base_salary = esda_base_salary;
		this.esda_siop = esda_siop;
		this.esda_hafop = esda_hafop;
		this.esda_haf = esda_haf;
		this.esda_house_radix = esda_house_radix;
		this.esda_house_percent = esda_house_percent;
		this.esda_house_ntl = esda_house_ntl;
		this.esda_total_pretax = esda_total_pretax;
		this.esda_tax_base = esda_tax_base;
		this.esda_tax = esda_tax;
		this.esda_dc = esda_dc;
		this.esda_dc_tax = esda_dc_tax;
		this.esda_db = esda_db;
		this.esda_db_tax_base = esda_db_tax_base;
		this.esda_db_tax = esda_db_tax;
		this.esda_stock = esda_stock;
		this.esda_stock_tax = esda_stock_tax;
		this.esda_other = esda_other;
		this.esda_other_tax = esda_other_tax;
		this.esda_write_off = esda_write_off;
		this.esda_house_bf = esda_house_bf;
		this.esda_pay = esda_pay;
		this.esda_col_1 = esda_col_1;
		this.esda_col_2 = esda_col_2;
		this.esda_col_3 = esda_col_3;
		this.esda_col_4 = esda_col_4;
		this.esda_col_5 = esda_col_5;
		this.esda_col_6 = esda_col_6;
		this.esda_col_7 = esda_col_7;
		this.esda_col_8 = esda_col_8;
		this.esda_col_9 = esda_col_9;
		this.esda_col_10 = esda_col_10;
		this.esda_col_11 = esda_col_11;
		this.esda_col_12 = esda_col_12;
		this.esda_col_13 = esda_col_13;
		this.esda_col_14 = esda_col_14;
		this.esda_col_15 = esda_col_15;
		this.esda_col_16 = esda_col_16;
		this.esda_col_17 = esda_col_17;
		this.esda_col_18 = esda_col_18;
		this.esda_col_19 = esda_col_19;
		this.esda_col_20 = esda_col_20;
		this.esda_col_21 = esda_col_21;
		this.esda_col_22 = esda_col_22;
		this.esda_col_23 = esda_col_23;
		this.esda_col_24 = esda_col_24;
		this.esda_col_25 = esda_col_25;
		this.esda_col_26 = esda_col_26;
		this.esda_col_27 = esda_col_27;
		this.esda_col_28 = esda_col_28;
		this.esda_col_29 = esda_col_29;
		this.esda_col_30 = esda_col_30;
		this.esda_col_31 = esda_col_31;
		this.esda_col_32 = esda_col_32;
		this.esda_col_33 = esda_col_33;
		this.esda_col_34 = esda_col_34;
		this.esda_col_35 = esda_col_35;
		this.esda_col_36 = esda_col_36;
		this.esda_col_37 = esda_col_37;
		this.esda_col_38 = esda_col_38;
		this.esda_col_39 = esda_col_39;
		this.esda_col_40 = esda_col_40;
		this.esda_col_41 = esda_col_41;
		this.esda_col_42 = esda_col_42;
		this.esda_col_43 = esda_col_43;
		this.esda_col_44 = esda_col_44;
		this.esda_col_45 = esda_col_45;
		this.esda_col_46 = esda_col_46;
		this.esda_col_47 = esda_col_47;
		this.esda_col_48 = esda_col_48;
		this.esda_col_49 = esda_col_49;
		this.esda_col_50 = esda_col_50;
		this.esda_col_51 = esda_col_51;
		this.esda_col_52 = esda_col_52;
		this.esda_col_53 = esda_col_53;
		this.esda_col_54 = esda_col_54;
		this.esda_col_55 = esda_col_55;
		this.esda_col_56 = esda_col_56;
		this.esda_col_57 = esda_col_57;
		this.esda_col_58 = esda_col_58;
		this.esda_col_59 = esda_col_59;
		this.esda_col_60 = esda_col_60;
		this.esda_col_61 = esda_col_61;
		this.esda_col_62 = esda_col_62;
		this.esda_col_63 = esda_col_63;
		this.esda_col_64 = esda_col_64;
		this.esda_col_65 = esda_col_65;
		this.esda_col_66 = esda_col_66;
		this.esda_col_67 = esda_col_67;
		this.esda_col_68 = esda_col_68;
		this.esda_col_69 = esda_col_69;
		this.esda_col_70 = esda_col_70;
		this.esda_col_71 = esda_col_71;
		this.esda_col_72 = esda_col_72;
		this.esda_col_73 = esda_col_73;
		this.esda_col_74 = esda_col_74;
		this.esda_col_75 = esda_col_75;
		this.esda_col_76 = esda_col_76;
		this.esda_col_77 = esda_col_77;
		this.esda_col_78 = esda_col_78;
		this.esda_col_79 = esda_col_79;
		this.esda_col_80 = esda_col_80;
		this.name = name;
		this.esda_ifhold = esda_ifhold;

		switch (esda_ifhold) {
		case 0:
			this.esda_ifholdstr = "正常";
			break;
		case 1:
			this.esda_ifholdstr = "挂起";
			break;
		default:
			this.esda_ifholdstr = "";
			break;
		}

		switch (esda_oof_state) {
		case 0:
			this.esda_oof_statestr = "台账内";
			break;
		case 1:
			this.esda_oof_statestr = "台账外";
			break;
		default:
			this.esda_oof_statestr = "";
			break;
		}

		switch (esda_payment_state) {
		case 0:
			this.esda_payment_statestr = "未审核";
			break;
		case 1:
			this.esda_payment_statestr = "已审核";
			break;
		case 2:
			this.esda_payment_statestr = "已发放";
			break;
		case 3:
			this.esda_payment_statestr = "待确认";
			break;
		case 4:
			this.esda_payment_statestr = "待发放";
			break;
		default:
			this.esda_payment_statestr = "";
			break;
		}
		switch (esda_if_bms) {
		case 0:
			this.esda_if_bmsstr = "系统外";
			break;
		case 1:
			this.esda_if_bmsstr = "系统内";
			break;
		default:
			this.esda_if_bmsstr = "";
			break;
		}

		if (esda_remark == null || esda_remark == "") {
			this.esda_remarkstr = "";
		} else {
			this.esda_remarkstr = "已备注";
		}

		if (esda_email_state == 0) {
			esda_email_statestr = "未发";
		} else {
			esda_email_statestr = "已发";
		}
	}

	public String getEsda_data_typestr() {
		return esda_data_typestr;
	}

	public void setEsda_data_typestr(String esda_data_typestr) {
		this.esda_data_typestr = esda_data_typestr;
	}

	public String getEsda_hpro() {
		return esda_hpro;
	}

	public void setEsda_hpro(String esda_hpro) {
		this.esda_hpro = esda_hpro;
	}

	public String getEsda_taxplace() {
		return esda_taxplace;
	}

	public void setEsda_taxplace(String esda_taxplace) {
		this.esda_taxplace = esda_taxplace;
	}

	public int getEsin_id() {
		return esin_id;
	}

	public void setEsin_id(int esin_id) {
		this.esin_id = esin_id;
	}

	public String getEsin_cost_type() {
		return esin_cost_type;
	}

	public void setEsin_cost_type(String esin_cost_type) {
		this.esin_cost_type = esin_cost_type;
	}

	public String getEsin_attachpassword() {
		return esin_attachpassword;
	}

	public void setEsin_attachpassword(String esin_attachpassword) {
		this.esin_attachpassword = esin_attachpassword;
	}

	public String getEsin_cosm_model() {
		return esin_cosm_model;
	}

	public void setEsin_cosm_model(String esin_cosm_model) {
		this.esin_cosm_model = esin_cosm_model;
	}

	public List<EmSalaryBaseAddItemModel> getItemListSel() {
		return itemListSel;
	}

	public boolean isIfBank() {
		return ifBank;
	}

	public String getIfBankCon() {
		return ifBankCon;
	}

	public int getTaba_id() {
		return taba_id;
	}

	public void setTaba_id(int taba_id) {
		this.taba_id = taba_id;
	}

	public int getTapr_id() {
		return tapr_id;
	}

	public void setTapr_id(int tapr_id) {
		this.tapr_id = tapr_id;
	}

	public Integer getTtv_state() {
		return ttv_state;
	}

	public void setTtv_state(Integer ttv_state) {
		this.ttv_state = ttv_state;
	}

	public Integer getCou() {
		return cou;
	}

	public void setCou(Integer cou) {
		this.cou = cou;
	}

	public BigDecimal getGz_paidin() {
		return gz_paidin;
	}

	public void setGz_paidin(BigDecimal gz_paidin) {
		this.gz_paidin = new BigDecimal(df.format(gz_paidin));
	}

	public BigDecimal getGs_paidin() {
		return gs_paidin;
	}

	public void setGs_paidin(BigDecimal gs_paidin) {
		this.gs_paidin = new BigDecimal(df.format(gs_paidin));
	}

	public BigDecimal getCsfee_paidin() {
		return csfee_paidin;
	}

	public void setCsfee_paidin(BigDecimal csfee_paidin) {
		this.csfee_paidin = new BigDecimal(df.format(csfee_paidin));
	}

	public String getCoba_ufclass() {
		return coba_ufclass;
	}

	public void setCoba_ufclass(String coba_ufclass) {
		this.coba_ufclass = coba_ufclass;
	}

	public String getCoba_ufid() {
		return coba_ufid;
	}

	public void setCoba_ufid(String coba_ufid) {
		this.coba_ufid = coba_ufid;
	}

	public List<EmSalaryBaseAddItemModel> getItemListSUM() {
		return itemListSUM;
	}

	public List<EmSalaryBaseAddItemModel> getItemListSelSUM() {
		return itemListSelSUM;
	}

	public String getGzaddname_email() {
		return gzaddname_email;
	}

	public void setGzaddname_email(String gzaddname_email) {
		this.gzaddname_email = gzaddname_email;
	}

	public String getClient_email() {
		return client_email;
	}

	public void setClient_email(String client_email) {
		this.client_email = client_email;
	}

	public Integer getEmba_state() {
		return emba_state;
	}

	public void setEmba_state(Integer emba_state) {
		this.emba_state = emba_state;

		if (emba_state == 0) {
			emba_statestr = "离职";
		} else if (emba_state == 1) {
			emba_statestr = "在职";
		} else if (emba_state == 2) {
			emba_statestr = "入职";
		} else if (emba_state == 5) {
			emba_statestr = "入职中";
		} else if (emba_state == 3) {
			emba_statestr = "退回";
		} else if (emba_state == 4) {
			emba_statestr = "取消入职";
		}

	}

	public String getEmba_statestr() {
		return emba_statestr;
	}

	public void setEmba_statestr(String emba_statestr) {
		this.emba_statestr = emba_statestr;
	}

	public String getD_type() {
		return d_type;
	}

	public void setD_type(String d_type) {
		this.d_type = d_type;
	}

}
