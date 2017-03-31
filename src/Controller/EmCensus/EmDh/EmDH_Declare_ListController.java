package Controller.EmCensus.EmDh;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.map.HashedMap;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Window;

import Model.EmDhModel;
import Model.EmbaseModel;
import Model.TaskProcessViewModel;
import bll.Archives.Archive_addBll;
import bll.Archives.EmArchive_SelectBll;
import bll.CoLatencyClient.CoLatencyClient_AddBll;
import bll.EmCensus.EmCensus_SelectBll;
import bll.EmCensus.EmDh.EmDh_SelectBll;

public class EmDH_Declare_ListController {
	private String name = "";
	private String cid = "";
	private String gid = "";
	private String idcard = "";
	private String company = "";
	private String dhtype = "";
	private String dhstate = "";
	private String client = "";
	EmDh_SelectBll bll = new EmDh_SelectBll();
	private String str = "";
	List<EmDhModel> dhlist = bll.getEmDhInfo(str);

	List<String> loginlist = bll.getLoginInfo();

	// 查询
	@Command
	@NotifyChange("dhlist")
	public void search(@BindingParam("dhstate") final String dhstates) {
		str = "";
		if (name != null && name != "" && !name.equals("")) {
			str = str + " and emdh_name like '%" + name + "%'";
		}
		if (cid != null && cid != "" && !cid.equals("")) {
			str = str + " and a.cid ='" + cid + "'";
		}
		if (gid != null && gid != "" && !gid.equals("")) {
			str = str + " and a.gid ='" + gid + "'";
		}
		if (idcard != null && idcard != "" && !idcard.equals("")) {
			str = str + " and emdh_idcard ='" + idcard + "'";
		}
		if (company != null && company != "" && !company.equals("")) {
			str = str + " and (b.coba_shortname like '%" + company
					+ "%' or coba_company like '%" + company + "%')";
		}
		if (dhtype != null && dhtype != "" && !dhtype.equals("")) {
			if (!dhtype.equals("调干、招调工") && dhtype != "调干、招调工") {
				str = str + " and emdh_dhtype ='" + dhtype + "'";
			} else {
				str = str + " and emdh_dhtype !='毕业生接收'";
			}
		}
		if (dhstates != null && dhstates != "" && !dhstates.equals("")) {
			if (dhstates != "-1" && !dhstates.equals("-1")) {
				str = str + " and emdh_state=" + dhstates;
			}
		}
		if (client != null && client != "" && !client.equals("")) {
			str = str + " and b.coba_client='" + client + "'";
		}
		dhlist = bll.getEmDhInfo(str);
	}

	// 详细信息
	@Command
	public void datail(@BindingParam("model") final EmDhModel model) {
		Map map = new HashMap<>();
		map.put("model", model);
		Window window = (Window) Executions.createComponents(
				"Emdh_InfoDetail.zul", null, map);
		window.doModal();
	}

	// 弹出页面
	@Command
	public void openZUL(@BindingParam("model") final EmDhModel model,
			@BindingParam("url") final String url) {
		Map map = new HashMap<>();
		map.put("daid", model.getId() + "");
		map.put("id", model.getEmdh_taprid() + "");
		map.put("model", model);
		map.put("gid", model.getGid());
		Window window = (Window) Executions.createComponents(url, null, map);
		window.doModal();
	}

	// 打开录入档案页面
	@Command
	@NotifyChange("dhlist")
	public void adddn(@BindingParam("model") final EmDhModel model) {
		Archive_addBll blldn = new Archive_addBll();
		Integer num = blldn.checkDatum(model.getGid(), 0);
		if (num == 0) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("gid", String.valueOf(model.getGid()));

			Window window = (Window) Executions.createComponents(
					"../Archives/Archive_FileImport.zul", null, map);
			window.doModal();
			dhlist = bll.getEmDhInfo(str);
		} else {
			Messagebox.show("已有调入业务,请勿重复提交!", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
		}
	}

	// 弹出落户申请页面
	@Command
	public void addhk(@BindingParam("model") final EmDhModel model) {
		EmCensus_SelectBll bll = new EmCensus_SelectBll();
		List<EmbaseModel> embaselist = bll.getEmbaseInfo(" and  gid="
				+ model.getGid());
		EmbaseModel embamodel = new EmbaseModel();
		if (embaselist.size() > 0) {
			embamodel = embaselist.get(0);
		}
		Map map = new HashMap<>();
		map.put("model", embamodel);
		map.put("dhmodel", model);
		Window window = (Window) Executions.createComponents(
				"EmCensusList_Add.zul", null, map);
		window.doModal();
	}

	// 批量处理
	@Command
	@NotifyChange("dhlist")
	public void allEdit(@BindingParam("list") Set<Listitem> set) {
		if (set.size() > 0) {
			int k = 0;
			int y = 0;
			String strssql = "";
			EmDhModel dhm = new EmDhModel();
			List<EmDhModel> molist = new ArrayList<EmDhModel>();
			for (Listitem lt : set) {
				EmDhModel m = (EmDhModel) lt.getValue();
				molist.add(m);
				if (m.getEmdh_dhtype() == "毕业生接收"
						|| m.getEmdh_dhtype().equals("毕业生接收")) {
					k = k + 1;
				}
				if (y == 0) {
					strssql = m.getId() + "";
					dhm = m;
				} else {
					strssql = strssql + "," + m.getId();
				}
				y = y + 1;
			}
			Map map = new HashMap<>();
			map.put("molist", molist);
			map.put("model", dhm);
			String url = "";
			// 判断调户类型是否相同,k>1表示调户类型是"毕业生接收"
			if (k > 0) {
				if (k < y) {
					Messagebox.show("'毕业生接收'的调户类型不能和其他调户类型一起做批量处理", "错误提示",
							Messagebox.OK, Messagebox.ERROR);
				}
				// 毕业生接收处理
				else {
					// 判断选择的数据的流程步骤是否一样
					List<String> liststr = bll.getStateId(strssql);
					if (liststr.size() > 1) {
						Messagebox.show("选择的数据流程步骤不同，不能做批量处理", "错误提示",
								Messagebox.OK, Messagebox.ERROR);
					} else {
						String step = liststr.get(0);
						// 第二步
						if (step == "2" || step.equals("2")) {
							url = "Emdh_StateChangeAll.zul";
						}
						// 前五步除了第二步外更新内容一样
						else if (Integer.parseInt(step) < 6) {
							url = "Emdh_UpdateStateAll.zul";
						} else if (Integer.parseInt(step) == 10) {
							Messagebox.show("该步骤不能做批量处理", "错误提示",
									Messagebox.OK, Messagebox.ERROR);
						} else {
							url = "Emdh_Bys_StateUpdateAll.zul";
						}
					}
				}
			} else {
				// 判断选择的数据的流程步骤是否一样
				List<String> liststr = bll.getStateId(strssql);
				if (liststr.size() > 1) {
					Messagebox.show("选择的数据流程步骤不同，不能做批量处理", "错误提示",
							Messagebox.OK, Messagebox.ERROR);
				} else {
					String step = liststr.get(0);
					// 第二步
					if (step == "2" || step.equals("2")) {
						url = "Emdh_StateChangeAll.zul";
					}
					// 前五步除了第二步外更新内容一样
					else if (Integer.parseInt(step) < 6) {
						url = "Emdh_UpdateStateAll.zul";
					} else {
						if (step == "7" || step.equals("7")) {
							Messagebox.show("该步骤不能做批量处理", "错误提示",
									Messagebox.OK, Messagebox.ERROR);
						} else {
							url = "Emdh_UpdateStateAll.zul";
						}
					}
				}
			}
			if (url != "") {
				Window window = (Window) Executions.createComponents(url, null,
						map);
				window.doModal();
				dhlist = bll.getEmDhInfo(str);
			}
		} else {
			Messagebox.show("请选择数据", "错误提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	// 打开编辑
	@Command
	@NotifyChange("dhlist")
	public void openEdit(@BindingParam("model") final EmDhModel model) {

		if (model.getEmdh_taprid() != null && model.getEmdh_taprid() > 0) {
			Map map = new HashMap<>();
			map.put("model", model);
			map.put("daid", model.getId() + "");
			String tarpid = "";
			if (model.getEmdh_taprid() != null
					&& !model.getEmdh_taprid().equals("")) {
				tarpid = model.getEmdh_taprid().toString();
			}
			map.put("id", tarpid);
			String url = "";
			if (!model.getEmdh_dhtype().equals("留学生接收")) {
				// 先获取流程的步骤
				EmArchive_SelectBll abll = new EmArchive_SelectBll();
				List<TaskProcessViewModel> tlist = abll.getLastId(model
						.getEmdh_taprid() + "");
				TaskProcessViewModel tmodel = new TaskProcessViewModel();
				if (!tlist.isEmpty()) {
					tmodel = tlist.get(0);
				}
				// 判断业务处理到了那个步骤并弹出相应的页面
				// 联系员工
				if (tmodel.getWfno_step() == 2) {
					url = "Emdh_UpdateLinkInfo.zul";
				}
				// 第二步交接材料
				else if (tmodel.getWfno_step() == 3) {
					url = "Emdh_StateChange.zul";
				}
				// 前六步除了第二步外更新内容一样
				else if (tmodel.getWfno_step() <= 6) {
					url = "Emdh_UpdateState.zul";
				}
				// 第六步之后毕业生接收和调干（招调工）不同
				else {
					if (model.getEmdh_dhtype() == "毕业生接收"
							|| model.getEmdh_dhtype().equals("毕业生接收")) {
						if (tmodel.getWfno_step() != 11) {
							url = "Emdh_Bys_StateUpdate.zul";
						} else {
							url = "Emdh_Bys_UpData.zul";
						}
					} else {
						if (tmodel.getWfno_step() == 8) {
							url = "Emdh_TakeBackData.zul";
						} else {
							url = "Emdh_UpdateState.zul";
						}
					}
				}
			} else {
				if (model.getEmdh_state() == 1&& model.getEmdh_time2() == null) {
					url = "EmDh_LxHandOverData.zul";
				} else if (model.getEmdh_state() == 1
						&& model.getEmdh_time2() != null
						&& model.getEmdh_time3() == null) {
					url = "EmDh_LxConditionAudit.zul";
				}else if (model.getEmdh_state() ==2
						&& model.getEmdh_time3() != null
						&& model.getEmdh_time4() == null) {
					url = "EmDh_LxInfoAudit.zul";
				}else if (model.getEmdh_state() ==3
						&& model.getEmdh_time4() != null
						&& model.getEmdh_time5() == null) {
					url = "EmDh_LxAuditComfirm.zul";
				}else if (model.getEmdh_state() ==4
						&& model.getEmdh_time5() != null
						&& model.getEmdh_proxytime() == null) {
					url = "EmDh_LxProxyConfirm.zul";//代理部受理
				}else if (model.getEmdh_state() ==4
						&& model.getEmdh_proxytime() != null
						&& model.getEmdh_time7() == null) {
					url = "EmDh_LxReportData.zul";//上报材料
				}else if (model.getEmdh_state() ==4
						&& model.getEmdh_time7() != null
						&& model.getEmdh_time8() == null) {
					url = "EmdH_LxIntroduction.zul";
				}
				else if (model.getEmdh_state() ==4
						&& model.getEmdh_time8() != null
						&& model.getEmdh_time12() == null) {
					url = "EmdH_LxTakeIntroduction.zul";
				}
			}
			Window window = (Window) Executions
					.createComponents(url, null, map);
			window.doModal();
			dhlist = bll.getEmDhInfo(str);
		} else {
			Messagebox.show("由于该数据没有任务单，所以不能处理", "系统出错", Messagebox.OK,
					Messagebox.ERROR);
		}
	}

	// 全选
	@Command
	public void checkall(@BindingParam("gd") Grid gd,
			@BindingParam("cb") Checkbox cb) {
		for (int i = 0; i < gd.getPageSize(); i++) {
			if (gd.getCell(i, 11) != null) {
				if (gd.getCell(i, 11).getChildren().get(0) != null) {
					Checkbox ck = (Checkbox) gd.getCell(i, 11).getChildren()
							.get(0);
					ck.setChecked(cb.isChecked());
				}
			}
		}
	}

	@Command
	public void dealall(@BindingParam("gd") Grid gd) {
		List<EmDhModel> molist = new ArrayList<EmDhModel>();
		for (int i = 0; i < gd.getPageSize(); i++) {
			if (gd.getCell(i, 11) != null) {
				if (gd.getCell(i, 11).getChildren().get(0) != null) {
					Checkbox ck = (Checkbox) gd.getCell(i, 11).getChildren()
							.get(0);
					if (ck.isChecked()) {
						EmDhModel m = ck.getValue();
						molist.add(m);
					}
				}
			}
		}

		if (molist.size() > 0) {
			int k = 0;
			int y = 0;
			String strssql = "";
			EmDhModel dhm = new EmDhModel();
			for (EmDhModel ml : molist) {
				if (ml.getEmdh_dhtype() == "毕业生接收"
						|| ml.getEmdh_dhtype().equals("毕业生接收")) {
					k = k + 1;
				}
				if (y == 0) {
					strssql = ml.getId() + "";
				} else {
					strssql = strssql + "," + ml.getId();
				}
				y = y + 1;
			}
			Map map = new HashMap<>();
			map.put("molist", molist);
			map.put("model", dhm);
			String url = "";
			// 判断调户类型是否相同,k>1表示调户类型是"毕业生接收"
			if (k > 0) {
				if (k < y) {
					Messagebox.show("调户类型不同不能一起做批量处理", "错误提示", Messagebox.OK,
							Messagebox.ERROR);
				}
				// 毕业生接收处理
				else {
					// 判断选择的数据的流程步骤是否一样
					List<String> liststr = bll.getStateId(strssql);
					if (liststr.size() > 1) {
						Messagebox.show("选择的数据流程进度不同，不能做批量处理", "错误提示",
								Messagebox.OK, Messagebox.ERROR);
					} else {
						String step = liststr.get(0);
						// 第二步
						if (step == "2" || step.equals("2")) {
							url = "Emdh_StateChangeAll.zul";
						}
						// 前五步除了第二步外更新内容一样
						else if (Integer.parseInt(step) < 6) {
							url = "Emdh_UpdateStateAll.zul";
						} else if (Integer.parseInt(step) == 10) {
							Messagebox.show("该步骤不能做批量处理", "错误提示",
									Messagebox.OK, Messagebox.ERROR);
						} else {
							url = "Emdh_Bys_StateUpdateAll.zul";
						}
					}
				}
			} else {
				// 判断选择的数据的流程步骤是否一样
				List<String> liststr = bll.getStateId(strssql);
				if (liststr.size() > 1) {
					Messagebox.show("选择的数据流程进度不同，不能做批量处理", "错误提示",
							Messagebox.OK, Messagebox.ERROR);
				} else {
					String step = liststr.get(0);
					// 第二步
					if (step == "2" || step.equals("2")) {
						url = "Emdh_StateChangeAll.zul";
					}
					// 前五步除了第二步外更新内容一样
					else if (Integer.parseInt(step) < 6) {
						url = "Emdh_UpdateStateAll.zul";
					} else {
						if (step == "7" || step.equals("7")) {
							Messagebox.show("该步骤不能做批量处理", "错误提示",
									Messagebox.OK, Messagebox.ERROR);
						} else {
							url = "Emdh_UpdateStateAll.zul";
						}
					}
				}
			}
			if (url != "") {
				Window window = (Window) Executions.createComponents(url, null,
						map);
				window.doModal();
				dhlist = bll.getEmDhInfo(str);
			}
		} else {
			Messagebox.show("请选择数据", "错误提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	public List<EmDhModel> getDhlist() {
		return dhlist;
	}

	public void setDhlist(List<EmDhModel> dhlist) {
		this.dhlist = dhlist;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getGid() {
		return gid;
	}

	public void setGid(String gid) {
		this.gid = gid;
	}

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getDhtype() {
		return dhtype;
	}

	public void setDhtype(String dhtype) {
		this.dhtype = dhtype;
	}

	public String getDhstate() {
		return dhstate;
	}

	public void setDhstate(String dhstate) {
		this.dhstate = dhstate;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public List<String> getLoginlist() {
		return loginlist;
	}

	public void setLoginlist(List<String> loginlist) {
		this.loginlist = loginlist;
	}

}
