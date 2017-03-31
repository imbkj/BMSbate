package bll.EmSalary;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dal.EmSalary.EmSalary_SalaryOperateDal;
import dal.EmSalary.EmSalary_SalarySelDal;
import Model.EmSalaryBaseAddItemModel;
import Model.EmSalaryBaseSel_viewModel;
import Model.EmSalaryDataModel;
import Model.EmSalaryInfoModel;
import service.WorkflowCore.WfOperateService;
import impl.WorkflowCore.WfOperateImpl;

public class EmSalary_SalaryOperateBll {
	private EmSalary_SalaryOperateDal dal = new EmSalary_SalaryOperateDal();

	// 工资信息新增
	public String[] AddSalary(EmSalaryDataModel m,
			List<EmSalaryBaseAddItemModel> list) {
		for (EmSalaryBaseAddItemModel ebM : list) {
			setField(m, ebM.getCsii_col(), ebM.getAmount());
		}
		m.sumItemInfo();
		String[] message = AddSalary(m);
		return message;
	}

	// 新增工资插入数据库
	private String[] AddSalary(EmSalaryDataModel m) {
		String[] message = new String[2];
		try {
			int i = dal.AddSalary(m);
			if (i == 0) {
				message[0] = "1";
				message[1] = "新增工资成功。";
			} else {
				message[0] = "0";
				message[1] = "新增工资失败。";
			}
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "新增工资出错。";
		}

		return message;
	}

	// 工资信息批量新增
	public String[] AddSalary(List<EmSalaryDataModel> list,
			List<EmSalaryBaseAddItemModel> itemlist) {
		int sum = list.size();
		int success = 0;
		String[] message = new String[3];
		try {
			for (EmSalaryDataModel m : list) {
				try {
					m.sumItemInfo();
					if (dal.AddSalary(m) == 0) {
						success = success + 1;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (success == sum) {
			message[0] = "1";
			message[1] = "共修改工资数据" + sum + "条，全部成功。";
		} else {
			message[0] = "0";
			message[1] = "共修改工资数据" + sum + "条，有" + (sum - success) + "条数据修改失败。";
			message[2] = String.valueOf(success);
		}
		return message;
	}

	// 工资信息修改
	public String[] UpSalary(List<EmSalaryDataModel> list) {
		int sum = list.size();
		int success = 0;
		int up = 1;
		String[] message = new String[3];
		try {
			for (EmSalaryDataModel m : list) {
				try {
					m.sumItemInfo();
					up = UpSalaryData(m);
					if (up == 0) {
						success = success + 1;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		if (success == sum) {
			message[0] = "1";
			message[1] = "共修改工资数据" + sum + "条，全部成功。";
		} else {
			message[0] = "0";
			message[1] = "共修改工资数据" + sum + "条，有" + (sum - success) + "条数据修改失败。";
			message[2] = String.valueOf(success);
		}
		return message;
	}

	// 修改工资插入数据库
	private int UpSalaryData(EmSalaryDataModel m) {
		try {
			int i = dal.UpSalary(m);
			return i;
		} catch (Exception e) {
			return 1;
		}
	}

	// 刪除工资
	public int DelSalary(int esda_id) {
		try {
			int i = dal.DelSalary(esda_id);
			if (i == 0) {
				return 1;
			} else {
				return 0;
			}
		} catch (Exception e) {
			return 0;
		}
	}

	// 确认工资信息
	public String[] confirmSalary(List<Integer> emSalaryList, String username,
			int cid, int ownmonth) {
		String[] message = new String[2];
		EmSalary_SalaryOperateImpl impl = new EmSalary_SalaryOperateImpl();
		WfOperateService wf = new WfOperateImpl(impl);
		EmSalary_SalarySelDal selBll = new EmSalary_SalarySelDal();
		Map<String, String> map = new HashMap<String, String>();
		map.put("cid", String.valueOf(cid));
		map.put("ownmonth", String.valueOf(ownmonth));
		try {
			String CoShortName = selBll.getCoShortName(cid);
			Object[] obj = { "1", emSalaryList, username };
			message = wf.AddTaskToNext(obj, ownmonth + "工资发放", String.valueOf(cid)+CoShortName
					+ "工资发放", 19, username, ownmonth + CoShortName + "工资确认",
					cid, "", map);
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "确认工资出错";
		}
		return message;
	}

	// 生成工资信息
	public String[] createSalary(List<EmSalaryBaseSel_viewModel> emList,
			int ownmonth, int createmonth, int cid, String username,
			boolean ifzero) {
		String[] message = new String[2];
		try {
			// 获取itemid
			String itemid = createSalaryToAddItemId(cid, createmonth, username);
			// 拼接esda_id
			String esdaId = createSalaryTomontageId(emList);
			// 获取员工工资数据
			List<EmSalaryDataModel> dataList = createSalaryToGetSalaryData(esdaId);
			// 获取需要清零的字段
			List<String> ifzerolist = createSalaryToGetIfZero(cid, ownmonth);
			int ifzerosize = ifzerolist.size();
			int success = 0;
			int sum = emList.size();
			// 插入数据
			for (EmSalaryDataModel m : dataList) {
				try {
					if (ifzerosize > 0 && ifzero) {
						m.setDataZero(ifzerolist);
					}
					m.setEsda_addname(username);
					m.setCsii_itemid(itemid);
					m.setOwnmonth(createmonth);
					m.setEsda_payment_state(3);
					success = success + createSalaryToAddData(m);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (success == sum) {
				message[0] = "1";
				message[1] = "共生成工资数据" + sum + "条，全部成功。";
			} else {
				message[0] = "0";
				message[1] = "共生成工资数据" + sum + "条，其中有" + (sum - success)
						+ "条数据生成失败。";
			}
		} catch (Exception e) {
			e.printStackTrace();
			message[0] = "2";
			message[1] = "生成工资出错";
		}
		return message;
	}

	// 批量新增工资信息(初始员工工资)
	public String[] createSalaryFirst(List<EmSalaryDataModel> emList,
			int createmonth, String username) {
		String[] message = new String[2];
		try {
			int success = 0;
			int sum = emList.size();
			// 插入数据
			for (EmSalaryDataModel m : emList) {
				try {
					m.setEsda_addname(username);
					m.setOwnmonth(createmonth);
					success = success + createSalaryToAddData(m);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (success == sum) {
				message[0] = "1";
				message[1] = "共生成工资数据" + sum + "条，全部成功。";
			} else {
				message[0] = "0";
				message[1] = "共生成工资数据" + sum + "条，其中有" + (sum - success)
						+ "条数据生成失败。";
			}
		} catch (Exception e) {
			e.printStackTrace();
			message[0] = "2";
			message[1] = "生成工资出错";
		}
		return message;
	}

	// 薪酬负责人设置
	public String[] setGzUser(int cid, String gzUser, String gzAudUser) {
		String[] message = new String[2];
		try {
			int i = dal.setGzUser(cid, gzUser, gzAudUser);
			if (i == 1) {
				message[0] = "1";
				message[1] = "设置薪酬负责人成功。";
			} else {
				message[0] = "0";
				message[1] = "设置薪酬负责人失败。";
			}
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "设置薪酬负责人出错。";
		}
		return message;
	}

	// 生成数据时调用插入存储过程
	private int createSalaryToAddData(EmSalaryDataModel m) {
		try {
			if (dal.AddSalary(m) == 0) {
				return 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	// 拼接esda_id
	private String createSalaryTomontageId(
			List<EmSalaryBaseSel_viewModel> emList) {
		StringBuilder sb = new StringBuilder();
		try {
			sb.append("0");
			for (EmSalaryBaseSel_viewModel m : emList) {
				sb.append(",");
				sb.append(m.getEsda_id());
			}
		} catch (Exception e) {
			return "";
		}
		return sb.toString();
	}

	// 返回指定月份的默认项目ID
	private String createSalaryToAddItemId(int cid, int ownmonth,
			String username) {
		EmSalary_SalarySelBll selBll = new EmSalary_SalarySelBll();
		return selBll.AddItemId(cid, ownmonth, username);
	}

	// 根据主键获取工资信息
	private List<EmSalaryDataModel> createSalaryToGetSalaryData(String esdaid) {
		EmSalary_SalarySelDal selDal = new EmSalary_SalarySelDal();
		List<EmSalaryDataModel> dataList = null;
		try {
			dataList = selDal.getSalaryDataByIdList(esdaid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dataList;
	}

	// 获取需要清零的字段
	private List<String> createSalaryToGetIfZero(int cid, int ownmonth) {
		EmSalary_SalarySelDal selDal = new EmSalary_SalarySelDal();
		List<String> list = null;
		try {
			list = selDal.getIfZero(cid, ownmonth);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
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

	// 薪酬负责人设置
	public String[] addEmSalaryInfo(EmSalaryInfoModel m) {
		String[] message = new String[2];
		try {
			int i = dal.addEmSalaryInfo(m);
			if (i == 0) {
				message[0] = "1";
				message[1] = "操作成功。";
			} else {
				message[0] = "0";
				message[1] = "操作失败。";
			}
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "操作出错。";
		}
		return message;
	}
}
