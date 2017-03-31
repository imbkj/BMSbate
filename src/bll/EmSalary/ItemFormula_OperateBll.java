package bll.EmSalary;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import impl.WorkflowCore.WfOperateImpl;
import service.WorkflowCore.WfOperateService;
import Model.CoFormulaInfoModel;
import Model.CoSalaryItemFormulaModel;
import Model.CoSalaryItemIDInfoModel;
import Model.EmBaseESInCFInModel;
import Model.EmSalaryBaseSel_viewModel;
import Model.EmSalaryInfoModel;
import Model.EmTXTFileInfoModel;
import Model.EmTXTFileSetModel;
import Model.PubEmailModel;
import Util.UserInfo;
import dal.PubEmailDal;
import dal.EmSalary.EmSalary_SalarySelDal;
import dal.EmSalary.ItemFormula_OperateDal;

public class ItemFormula_OperateBll {
	private ItemFormula_OperateDal ifDal = new ItemFormula_OperateDal();

	// 更改工资项目顺序
	public String[] changeItemsSequence(String type, int csii_id) {
		String[] message = new String[3];
		int result = 0;
		try {

			result = ifDal.changeItemsSequence(type, csii_id);

			if (result == 0) {
				message[0] = "1";
				message[1] = "操作成功!";
				message[2] = String.valueOf(result);
			} else {
				message[0] = "0";
				message[1] = "操作失败!";
				message[2] = "0";
			}
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "操作出错。";
			message[2] = "0";
		}
		return message;
	}

	// 新增工资项目
	public String[] addSalaryItems(int cid, String csii_itemid,
			String csii_item_name, int csia_id, String cfin_id,
			int csii_fd_state, int ciin_id, int csii_ifzero,
			String csii_addname, String ownmonth) {
		String[] message = new String[2];
		int result = 0;
		try {
			CoSalaryItemFormulaModel m = new CoSalaryItemFormulaModel();
			m.setCid(cid);
			m.setCsii_itemid(csii_itemid);
			m.setCsii_item_name(csii_item_name);
			m.setCsia_id(csia_id);
			m.setCfin_id(cfin_id);
			m.setCsii_fd_state(csii_fd_state);
			m.setCiin_id(ciin_id);
			m.setCsii_ifzero(csii_ifzero);
			result = ifDal.addSalaryItem(m, csii_addname);

			// 重新排序
			changeItemSeq(cid, Integer.parseInt(ownmonth), csii_itemid);

			if (result == 0) {
				message[0] = "1";
				message[1] = "操作成功!";
			} else {
				message[0] = "0";
				message[1] = "操作失败!";
			}
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "操作出错。";
		}
		return message;
	}

	// 修改工资项目
	public String[] updateSalaryItems(int csii_id, String csii_item_name,
			int csia_id, String cfin_id, int csii_fd_state, int ciin_id,
			int csii_ifzero, int ownmonth, String addname, int cid,
			String itemid) {
		String[] message = new String[2];
		int result = 0;
		try {
			CoSalaryItemFormulaModel m = new CoSalaryItemFormulaModel();
			m.setCsii_id(csii_id);
			m.setCsii_item_name(csii_item_name);
			m.setCsia_id(csia_id);
			m.setCfin_id(cfin_id);
			m.setCsii_fd_state(csii_fd_state);
			m.setCiin_id(ciin_id);
			m.setCsii_ifzero(csii_ifzero);
			m.setOwnmonth(ownmonth);
			m.setCsii_addname(addname);
			result = ifDal.updateSalaryItem(m);

			// 重新排序
			changeItemSeq(cid, ownmonth, itemid);

			if (result == 0) {
				message[0] = "1";
				message[1] = "操作成功!";
			} else {
				message[0] = "0";
				message[1] = "操作失败!";
			}
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "操作出错。";
		}
		return message;
	}

	// 修改工资项目名
	public String[] updateSalaryItemName(int csii_id, String csii_item_name,
			String old_item_name, String cfin_id, int ownmonth, String addname,
			int cid, String itemid) {
		String[] message = new String[2];
		int result = 0;
		try {
			CoSalaryItemFormulaModel m = new CoSalaryItemFormulaModel();
			m.setCsii_id(csii_id);
			m.setCsii_item_name(csii_item_name);
			m.setCfin_id(cfin_id);
			m.setOwnmonth(ownmonth);
			m.setCsii_addname(addname);
			result = ifDal.updateSalaryItemName(m);

			// 更新算法内容对应的项目名称
			ifDal.updateSalaryFormula(csii_item_name, old_item_name,
					Integer.parseInt(cfin_id));

			if (result == 0) {
				message[0] = "1";
				message[1] = "操作成功!";
			} else {
				message[0] = "0";
				message[1] = "操作失败!";
			}
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "操作出错。";
		}
		return message;
	}

	// 删除工资项目
	public String[] delSalaryItems(int csii_id, int ownmonth, String addname) {
		String[] message = new String[2];
		int result = 0;
		try {
			CoSalaryItemFormulaModel m = new CoSalaryItemFormulaModel();
			m.setCsii_id(csii_id);
			m.setOwnmonth(ownmonth);
			m.setCsii_addname(addname);
			result = ifDal.delSalaryItem(m);

			if (result == 0) {
				message[0] = "1";
				message[1] = "操作成功!";
			} else {
				message[0] = "0";
				message[1] = "操作失败!";
			}
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "操作出错。";
		}
		return message;
	}

	// 新增指定月份的默认项目
	public String[] addSalaryItemId(int cid, int ownmonth, String addname) {
		String[] message = new String[2];
		String[] result = new String[2];
		result[0] = "0";

		try {
			CoSalaryItemFormulaModel m = new CoSalaryItemFormulaModel();
			m.setCid(cid);
			m.setOwnmonth(ownmonth);
			m.setCsii_addname(addname);

			result = ifDal.addSalaryItemId(m);

			if (result[0].equals("1")) {
				message[0] = "0";
				message[1] = "操作失败!";
			} else {
				message[0] = "1";
				message[1] = "操作成功!";
			}
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "操作出错。";
		}
		return message;
	}

	// 复制项目算法组合
	public String[] copySalaryFormulaItem(int cid, int ownmonth, int c_cid,
			int c_ownmonth, String addname) {
		String[] message = new String[2];
		int result = 0;
		try {
			CoSalaryItemFormulaModel m = new CoSalaryItemFormulaModel();
			m.setCid(cid);
			m.setOwnmonth(ownmonth);
			m.setCsii_addname(addname);
			result = ifDal.copySalaryFormulaItem(m, c_cid, c_ownmonth);

			if (result == 0) {
				message[0] = "1";
				message[1] = "操作成功!";
			} else {
				message[0] = "0";
				message[1] = "操作失败!";
			}
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "操作出错。";
		}
		return message;
	}

	// 检查工资项目算法组合是否可以修改
	public String[] chkSalaryFormulaItem(int cid, int ownmonth) {
		String[] message = new String[2];
		int result = 0;
		try {
			CoSalaryItemFormulaModel m = new CoSalaryItemFormulaModel();
			m.setCid(cid);
			m.setOwnmonth(ownmonth);

			result = ifDal.chkSalaryFormulaItem(m);

			if (result == 0) {
				message[0] = "0";
				message[1] = "可以操作修改!";
			} else if (result == 1) {
				message[0] = "1";
				message[1] = "已存在工资数据，不能修改!";
			} else {
				message[0] = "2";
				message[1] = "已存在工资算法，不能修改!";
			}
		} catch (Exception e) {
			message[0] = "3";
			message[1] = "操作出错。";
		}
		return message;
	}

	// 检查算法是否可以修改
	public String[] chkFormula(int cfin_id) {
		String[] message = new String[2];
		int result = 0;
		try {
			CoFormulaInfoModel m = new CoFormulaInfoModel();
			m.setCfin_id(cfin_id);

			result = ifDal.chkFormula(m);

			if (result == 0) {
				message[0] = "0";
				message[1] = "可以操作修改!";
			} else if (result == 1) {
				message[0] = "1";
				message[1] = "已存在工资数据，不能修改!";
			} else {
				message[0] = "2";
				message[1] = "已存在工资算法，不能修改!";
			}
		} catch (Exception e) {
			message[0] = "3";
			message[1] = "操作出错。";
		}
		return message;
	}

	// 更改工资项目顺序
	public String[] changeForSequence(String type, int cfda_id) {
		String[] message = new String[3];
		int result = 0;
		try {

			result = ifDal.changeForSequence(type, cfda_id);

			if (result != 0) {
				message[0] = "1";
				message[1] = "操作成功!";
				message[2] = String.valueOf(result);
			} else {
				message[0] = "0";
				message[1] = "操作失败!";
				message[2] = "0";
			}
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "操作出错。";
			message[2] = "0";
		}
		return message;
	}

	// 删除算法内容
	public String[] delFormulaData(String cfda_id, String addname) {
		String[] message = new String[2];
		int result = 0;
		try {
			CoSalaryItemFormulaModel m = new CoSalaryItemFormulaModel();
			m.setCfda_id(cfda_id);
			m.setCsii_addname(addname);
			result = ifDal.delFormulaData(m);

			if (result == 0) {
				message[0] = "0";
				message[1] = "操作成功!";
			} else if (result == 1) {
				message[0] = "1";
				message[1] = "已存在工资数据，不能修改!";
			} else {
				message[0] = "2";
				message[1] = "已存在工资算法，不能修改!";
			}
		} catch (Exception e) {
			message[0] = "3";
			message[1] = "操作出错。";
		}
		return message;
	}

	// 删除算法组合
	public String[] delFormulaInfo(int cfin_id, String addname) {
		String[] message = new String[2];
		int result = 0;
		try {
			CoFormulaInfoModel m = new CoFormulaInfoModel();
			m.setCfin_id(cfin_id);
			m.setCfin_addname(addname);
			result = ifDal.delFormulaInfo(m);

			if (result == 0) {
				message[0] = "0";
				message[1] = "操作成功!";
			} else if (result == 1) {
				message[0] = "1";
				message[1] = "已存在工资数据，不能修改!";
			} else {
				message[0] = "2";
				message[1] = "已存在工资算法，不能修改!";
			}
		} catch (Exception e) {
			message[0] = "3";
			message[1] = "操作出错。";
		}
		return message;
	}

	// 新增算法合作
	public String[] addFormulaInfo(int cid, String cfin_name,
			String cfin_remark, int c_cfin_id, String addname) {
		String[] message = new String[2];
		int result = 0;
		try {
			result = ifDal.addFormulaInfo(cid, cfin_name, cfin_remark,
					c_cfin_id, addname);

			if (result == 0) {
				message[0] = "0";
				message[1] = "操作成功!";

			} else {
				message[0] = "1";
				message[1] = "操作失败!";

			}
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "操作出错。";

		}
		return message;
	}

	// 新增算法内容
	public String[] addFormulaData(String cfin_id, int csii_id,
			String cfda_formula, String cfda_t_formula, String cfda_range,
			String cfda_t_range, String addname) {
		String[] message = new String[2];
		int result = 0;
		try {
			CoSalaryItemFormulaModel m = new CoSalaryItemFormulaModel();
			m.setCfin_id(cfin_id);
			m.setCsii_id(csii_id);
			m.setCfda_formula(cfda_formula);
			m.setCfda_t_formula(cfda_t_formula);
			m.setCfda_range(cfda_range);
			m.setCfda_t_range(cfda_t_range);
			m.setCsii_addname(addname);
			result = ifDal.addFormulaData(m);

			if (result == 0) {
				message[0] = "0";
				message[1] = "操作成功!";

			} else {
				message[0] = "1";
				message[1] = "操作失败!";

			}
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "操作出错。";

		}
		return message;
	}

	// 修改算法内容
	public String[] updateFormulaData(String cfda_id, String cfin_id,
			String cfda_formula, String cfda_t_formula, String cfda_range,
			String cfda_t_range, String addname) {
		String[] message = new String[2];
		int result = 0;
		try {
			CoSalaryItemFormulaModel m = new CoSalaryItemFormulaModel();
			m.setCfda_id(cfda_id);
			m.setCfin_id(cfin_id);
			m.setCfda_formula(cfda_formula);
			m.setCfda_t_formula(cfda_t_formula);
			m.setCfda_range(cfda_range);
			m.setCfda_t_range(cfda_t_range);
			m.setCsii_addname(addname);
			result = ifDal.updateFormulaData(m);

			if (result == 0) {
				message[0] = "0";
				message[1] = "操作成功!";

			} else {
				message[0] = "1";
				message[1] = "操作失败!";

			}
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "操作出错。";

		}
		return message;
	}

	// 分配员工算法
	public String[] assFormulaInfo(int cid, int gid, String cfin_id,
			String addname) {
		String[] message = new String[2];
		int result = 0;
		try {
			EmBaseESInCFInModel m = new EmBaseESInCFInModel();
			m.setCid(cid);
			m.setGid(gid);
			m.setCfin_id(cfin_id);
			m.setEsin_addname(addname);
			result = ifDal.assFormulaInfo(m);

			if (result == 0) {
				message[0] = "0";
				message[1] = "操作成功!";

			} else {
				message[0] = "1";
				message[1] = "操作失败!";

			}
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "操作出错。";

		}
		return message;
	}

	// 删除txt数据
	public String[] delTXT(int etfi_id, String doName) {
		String[] message = new String[2];
		int result = 0;
		try {
			EmTXTFileInfoModel m = new EmTXTFileInfoModel();
			m.setEtfi_id(etfi_id);
			m.setEtfi_txt_people(doName);
			result = ifDal.delTXT(m);

			if (result == 0) {
				message[0] = "0";
				message[1] = "操作成功!";

			} else {
				message[0] = "1";
				message[1] = "操作失败!";
			}
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "操作出错。";
		}
		return message;
	}

	// 修改账户名
	public String[] updateTXTBN(int etfi_id, String ba_name, String doName) {
		String[] message = new String[2];
		int result = 0;
		try {
			EmTXTFileInfoModel m = new EmTXTFileInfoModel();
			m.setEtfi_id(etfi_id);
			m.setEtfi_ba_name(ba_name);
			m.setEtfi_txt_people(doName);
			result = ifDal.updateTXTBN(m);

			if (result == 0) {
				message[0] = "0";
				message[1] = "操作成功!";

			} else {
				message[0] = "1";
				message[1] = "操作失败!";
			}
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "操作出错。";
		}
		return message;
	}

	// 修改账户名(未报盘)
	public String[] updateESDABN(EmSalaryBaseSel_viewModel m) {
		String[] message = new String[2];
		int result = 0;
		try {
			result = ifDal.updateESDABN(m);

			if (result == 0) {
				message[0] = "0";
				message[1] = "操作成功!";

			} else {
				message[0] = "1";
				message[1] = "操作失败!";
			}
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "操作出错。";
		}
		return message;
	}

	// 生成历史数据
	public String[] addHistoryData(int esda_id, String payment_batch,
			String eshd_addname) {
		String[] message = new String[2];
		int result = 0;
		try {
			EmTXTFileInfoModel m = new EmTXTFileInfoModel();
			m.setEsda_id(esda_id);
			m.setEtfi_payment_batch(payment_batch);
			m.setEtfi_txt_people(eshd_addname);
			result = ifDal.addHistoryData(m);

			if (result == 0) {
				message[0] = "0";
				message[1] = "操作成功!";

			} else {
				message[0] = "1";
				message[1] = "操作失败!";
			}
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "操作出错。";
		}
		return message;
	}

	// 更新txt状态
	public String[] updateTXTState(String etfi_txt_date, String payment_batch,
			String doName) {
		String[] message = new String[2];
		int result = 0;
		try {
			EmTXTFileInfoModel m = new EmTXTFileInfoModel();
			m.setEtfi_txt_date(etfi_txt_date);
			m.setEtfi_payment_batch(payment_batch);
			m.setEtfi_txt_people(doName);
			result = ifDal.updateTXTState(m);

			if (result == 0) {
				message[0] = "0";
				message[1] = "操作成功!";

			} else {
				message[0] = "1";
				message[1] = "操作失败!";
			}
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "操作出错。";
		}
		return message;
	}

	// 转成功客户后新增工资项目任务单
	public String[] clcAddSalaryItemId(int cid, int ownmonth, String addname) {
		String[] message = new String[5];
		ItemFormula_OperateImpl impl = new ItemFormula_OperateImpl();
		WfOperateService wf = new WfOperateImpl(impl);
		EmSalary_SalarySelDal selBll = new EmSalary_SalarySelDal();
		try {
			CoSalaryItemFormulaModel m = new CoSalaryItemFormulaModel();
			m.setCid(cid);
			m.setOwnmonth(ownmonth);
			m.setCsii_addname(addname);

			String CoShortName = selBll.getCoShortName(cid);
			Object[] obj = { "1", m, addname };
			message = wf.AddTaskToNext(obj, "工资项目算法设置", CoShortName
					+ "工资项目算法设置", 81, addname, "", cid, "");

		} catch (Exception e) {
			message[0] = "2";
			message[1] = "新增工资项目算法设置出错";
		}
		return message;
	}

	// 转成功客户后新增工资项目任务单完成并结束
	public String[] clcFinishSalaryItemId(int daid, int tapr_id,
			String username, int cid) {
		String[] message = new String[5];
		try {

			CoSalaryItemIDInfoModel m = new CoSalaryItemIDInfoModel();
			m.setCsii_id(daid);
			m.setCsii_tapr_id(tapr_id);
			m.setCsii_addname(username);
			Object[] obj = { "2", m };
			// 执行工作流
			WfOperateService wf = new WfOperateImpl(
					new ItemFormula_OperateImpl());
			message = wf.PassToNext(obj, tapr_id, username, "", cid, "");
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "完成工资项目算法设置出错";
		}
		return message;
	}

	// 修改工资项目清零状态为临时清零
	public String[] upZeroItemState(String str) {
		String[] message = new String[2];
		int result = 0;
		try {

			result = ifDal.upZeroItemState(str);

			if (result == 0) {
				message[0] = "0";
				message[1] = "操作成功!";

			} else {
				message[0] = "1";
				message[1] = "操作失败!";

			}
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "操作出错。";

		}
		return message;
	}

	// 组合算法
	public String[] createFormula(Integer cid, Integer ownmonth, Integer cfin_id) {
		String[] message = new String[2];
		int result = 0;
		ItemFormula_SelectBll ifSBll = new ItemFormula_SelectBll();
		List<CoSalaryItemFormulaModel> itemFormulaList;
		List<CoSalaryItemFormulaModel> forDataList;
		itemFormulaList = ifSBll.getItemFormulaCSIF(String.valueOf(cid),
				String.valueOf(ownmonth), "", " AND cfin_id=" + cfin_id);

		String bFormula = "";// 税前合计算法
		String bFormulaT = "";
		String mFormula = "";// 应纳税工资算法
		String mFormulaT = "";
		String aFormula = "";// 实发工资算法
		String aFormulaT = "";

		try {

			// 循环
			for (int i = 0; i < itemFormulaList.size(); i++) {
				if (itemFormulaList.get(i).getCsia_attribute().contains("税前")) {
					/*
					 * if ("".equals(bFormula)) { bFormula = bFormula +
					 * itemFormulaList.get(i).getCsii_col(); bFormulaT =
					 * bFormulaT + "[" +
					 * itemFormulaList.get(i).getCsii_item_name() + "]"; } else
					 * {
					 */
					bFormula = bFormula + itemFormulaList.get(i).getCsia_ms()
							+ itemFormulaList.get(i).getCsii_col();
					bFormulaT = bFormulaT + itemFormulaList.get(i).getCsia_ms()
							+ "[" + itemFormulaList.get(i).getCsii_item_name()
							+ "]";
					// }

				} else if (itemFormulaList.get(i).getCsia_attribute()
						.contains("纳税调整")) {
					/*
					 * if ("".equals(mFormula)) { mFormula = mFormula +
					 * itemFormulaList.get(i).getCsii_col(); mFormulaT =
					 * mFormulaT + "[" +
					 * itemFormulaList.get(i).getCsii_item_name() + "]"; } else
					 * {
					 */
					mFormula = mFormula + itemFormulaList.get(i).getCsia_ms()
							+ itemFormulaList.get(i).getCsii_col();
					mFormulaT = mFormulaT + itemFormulaList.get(i).getCsia_ms()
							+ "[" + itemFormulaList.get(i).getCsii_item_name()
							+ "]";
					// }
				} else if (itemFormulaList.get(i).getCsia_attribute()
						.contains("税后")) {
					/*
					 * if ("".equals(aFormula)) { aFormula = aFormula +
					 * itemFormulaList.get(i).getCsii_col(); aFormulaT =
					 * aFormulaT + "[" +
					 * itemFormulaList.get(i).getCsii_item_name() + "]"; } else
					 * {
					 */
					aFormula = aFormula + itemFormulaList.get(i).getCsia_ms()
							+ itemFormulaList.get(i).getCsii_col();
					aFormulaT = aFormulaT + itemFormulaList.get(i).getCsia_ms()
							+ "[" + itemFormulaList.get(i).getCsii_item_name()
							+ "]";
					// }
				}
			}

			// 按项目顺序获取对应的项目算法内容
			int j = 0;//
			int tax_base_id = 0;
			for (int i = 0; i < itemFormulaList.size(); i++) {

				// 判断项目是否有算法内容
				if (itemFormulaList.get(i).getChk_cfd() != null
						&& !"".equals(itemFormulaList.get(i).getChk_cfd())
						&& !"NULL".equals(itemFormulaList.get(i).getChk_cfd())
						&& !"0".equals(itemFormulaList.get(i).getChk_cfd())) {

					// 判断特定项目是否已有算法内容(esda_total_pretax(税前合计)、esda_total_adjtax(纳税调整合计)、esda_total_afttax(税后合计))
					if (itemFormulaList.get(i).getCsii_col()
							.equals("esda_total_pretax")
							|| itemFormulaList.get(i).getCsii_col()
									.equals("esda_total_adjtax")
							|| itemFormulaList.get(i).getCsii_col()
									.equals("esda_total_afttax")) {

						chkFormulaData(itemFormulaList.get(i).getCsii_id(),
								cfin_id);
					}

					// 获取项目的算法内容
					forDataList = ifSBll.getFormulaData(" AND cfin_id="
							+ cfin_id + " AND csii_id="
							+ itemFormulaList.get(i).getCsii_id());
					for (int k = 0; k < forDataList.size(); k++) {
						j++;

						// 判断是否为特定项目(esda_total_pretax(税前合计)、esda_total_adjtax(纳税调整合计)、esda_total_afttax(税后合计))
						if (itemFormulaList.get(i).getCsii_col()
								.equals("esda_total_pretax")) {
							// 更新算法内容和顺序
							ifDal.upFormulaSeq(Integer.parseInt(forDataList
									.get(k).getCfda_id()), j, bFormula,
									bFormulaT);
						} else if (itemFormulaList.get(i).getCsii_col()
								.equals("esda_total_adjtax")) {
							// 更新算法内容和顺序
							ifDal.upFormulaSeq(Integer.parseInt(forDataList
									.get(k).getCfda_id()), j, mFormula,
									mFormulaT);
						} else if (itemFormulaList.get(i).getCsii_col()
								.equals("esda_total_afttax")) {
							// 更新算法内容和顺序
							ifDal.upFormulaSeq(Integer.parseInt(forDataList
									.get(k).getCfda_id()), j, aFormula,
									aFormulaT);
						} else {
							// 更新算法顺序
							// 判断是否为应税工资算法内容为：esda_tax_base<0的数据
							if (forDataList.get(k).getCfda_range() == null) {
								forDataList.get(k).setCfda_range("");
							}
							if (forDataList.get(k).getCfda_range()
									.equals("esda_tax_base<0")
									&& itemFormulaList.get(i).getCsii_col()
											.equals("esda_tax_base")) {
								tax_base_id = Integer.parseInt(forDataList.get(
										k).getCfda_id());
								j--;
							} else if (forDataList.get(k).getCfda_range()
									.equals("esda_db_tax_base<0")
									&& itemFormulaList.get(i).getCsii_col()
											.equals("esda_db_tax_base")) {
								ifDal.upFormulaSeq(Integer.parseInt(forDataList
										.get(k).getCfda_id()), j);
								j++;
								// 特殊修改应税工资算法内容为：esda_tax_base<0的数据的顺序
								ifDal.upFormulaSeq(tax_base_id, j);
							} else {
								ifDal.upFormulaSeq(Integer.parseInt(forDataList
										.get(k).getCfda_id()), j);
							}

						}
					}
				}
			}

			if (result == 0) {
				message[0] = "0";
				message[1] = "操作成功!";

			} else {
				message[0] = "1";
				message[1] = "操作失败!";

			}
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "操作出错。";

		}
		return message;
	}

	public void chkFormulaData(Integer csii_id, Integer cfin_id) {
		ItemFormula_SelectBll ifSBll = new ItemFormula_SelectBll();
		// 判断是否已存在算法
		if (ifSBll.getFormulaData(
				" AND cfin_id=" + cfin_id + " AND csii_id=" + csii_id).size() <= 0) {
			CoSalaryItemFormulaModel m = new CoSalaryItemFormulaModel();
			m.setCfin_id(String.valueOf(cfin_id));
			m.setCsii_id(csii_id);
			m.setCfda_formula("");
			m.setCfda_t_formula("");
			m.setCfda_range("");
			m.setCfda_t_range("");
			m.setCsii_addname(UserInfo.getUsername());
			ifDal.addFormulaData(m);
		}
	}

	// 对工资项目进行重新排序
	public String[] changeItemSeq(Integer cid, Integer ownmonth, String itemid) {
		ItemFormula_SelectBll ifSBll = new ItemFormula_SelectBll();
		String[] message = new String[2];
		int result = 0;
		try {

			// 获取项目列表
			List<CoSalaryItemFormulaModel> itemFormulaList;
			itemFormulaList = ifSBll.getItemFormulaCSIF(String.valueOf(cid),
					String.valueOf(ownmonth), itemid, "");

			if (itemFormulaList.size() > 0) {
				for (int i = 0; i < itemFormulaList.size(); i++) {
					result = ifDal.upItemSeq(itemFormulaList.get(i)
							.getCsii_id(), i + 1);
				}
			} else {
				result = 1;
			}

			if (result == 0) {
				message[0] = "0";
				message[1] = "操作成功!";

			} else {
				message[0] = "1";
				message[1] = "操作失败!";

			}
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "操作出错。";

		}
		return message;
	}

	// 新增员工工资算法分配任务单
	public String[] addEmSalaryInfoWF(EmSalaryInfoModel m) {
		String[] message = new String[5];
		EmSalaryInfo_OperateImpl impl = new EmSalaryInfo_OperateImpl();
		WfOperateService wf = new WfOperateImpl(impl);
		EmSalary_SalarySelDal selBll = new EmSalary_SalarySelDal();
		try {
			String embatName = selBll
					.getEmSalaryInfo(
							" AND a.cid=" + m.getCid() + " AND a.gid="
									+ m.getGid()).get(0).getName();
			Object[] obj = { "1", m };
			message = wf.AddTaskToNext(obj, "员工工资算法分配", embatName + "员工工资算法分配",
					108, m.getEsin_addname(), "", m.getCid(), "");

		} catch (Exception e) {
			message[0] = "2";
			message[1] = "新增员工工资算法分配任务单出错";
		}
		return message;
	}

	// 完成员工工资算法分配任务单
	public String[] finishSalaryInfoWF(EmBaseESInCFInModel m, int tapr_id) {
		String[] message = new String[5];
		try {
			Object[] obj = { "2", m };
			// 执行工作流
			WfOperateService wf = new WfOperateImpl(
					new EmSalaryInfo_OperateImpl());
			message = wf.PassToNext(obj, tapr_id, m.getEsin_addname(), "", 0,
					"");
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "完成工资项目算法设置出错";
		}
		return message;
	}

	// 修改工资项目显示隐藏状态
	public String[] changeSalaryItemsDis(int csii_id, int csii_fd_state) {
		String[] message = new String[2];
		int result = 0;
		try {
			result = ifDal.changeSalaryItemsDis(csii_id, csii_fd_state);

			if (result == 0) {
				message[0] = "1";
				message[1] = "操作成功!";
			} else {
				message[0] = "0";
				message[1] = "操作失败!";
			}
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "操作出错。";
		}
		return message;
	}

	// 修改工资项目显示隐藏状态
	public String[] changeIfZero(int csii_id, int state) {
		String[] message = new String[2];
		int result = 0;
		try {
			result = ifDal.changeIfZero(csii_id, state);

			if (result == 0) {
				message[0] = "1";
				message[1] = "操作成功!";
			} else {
				message[0] = "0";
				message[1] = "操作失败!";
			}
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "操作出错。";
		}
		return message;
	}

	// 特殊数据邮件提醒(利盟公司修改了工资项目就邮件通知赵哥，检查网上中智工资单显示是否正常)
	public String[] chkLM(Integer cid) {
		String[] message = new String[2];
		int result = 0;
		try {
			if (cid == 2360 || cid == 3168 || cid == 3168) {
				PubEmailDal eDal = new PubEmailDal();
				PubEmailModel m = new PubEmailModel();

				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Date date = new Date();
				String nowtime = sdf.format(date);

				m.setPuem_title("利盟(" + cid + ")工资项目存在调整");
				m.setPuem_content("利盟(" + cid + ")工资项目存在调整，请检查网上中智工资单显示是否正常");
				m.setPuem_email("zmj@szciic.com");
				m.setPuem_replyto("zmj@szciic.com");
				m.setPuem_sendtime(nowtime);
				m.setPuem_addname(UserInfo.getUsername());
				m.setPuem_ifHTML(0);

				result = eDal.EmailAdd(m);
			}

			if (result == 1) {
				message[0] = "1";
				message[1] = "操作成功!";
			} else {
				message[0] = "0";
				message[1] = "操作失败!";
			}
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "操作出错。";
		}
		return message;
	}

	// 添加当日银行余额
	public String[] addTXTFileSet(BigDecimal balance) {
		String[] message = new String[2];
		int result = 0;
		try {
			result = ifDal.addTXTFileSet(balance);

			if (result == 0) {
				message[0] = "1";
				message[1] = "操作成功!";
			} else {
				message[0] = "0";
				message[1] = "操作失败!";
			}
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "操作出错。";
		}
		return message;
	}

	// 更新当日银行剩余金额
	public String[] updateTXTremaining(EmTXTFileSetModel m) {
		String[] message = new String[2];
		int result = 0;
		try {
			result = ifDal.updateTXTremaining(m);

			if (result == 0) {
				message[0] = "1";
				message[1] = "操作成功!";
			} else {
				message[0] = "0";
				message[1] = "操作失败!";
			}
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "操作出错。";
		}
		return message;
	}
}
