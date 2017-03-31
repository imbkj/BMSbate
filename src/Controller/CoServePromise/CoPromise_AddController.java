package Controller.CoServePromise;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zhtml.Link;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import bll.CoBase.CoBaseLinkMan_SelectBll;
import bll.CoServePromise.CoServePromiseOperateBll;
import bll.CoServePromise.CoServePromiseSelectBll;

import Controller.DocumentsInfo.DocumentsInfo_OperationController;
import Model.CoAgencyLinkmanModel;
import Model.CoBaseModel;
import Model.CoBaseServePromiseModel;
import Util.RedirectUtil;

public class CoPromise_AddController {
	String cid = Executions.getCurrent().getArg().get("cid").toString();
	private CoBaseServePromiseModel model = new CoBaseServePromiseModel();
	private CoAgencyLinkmanModel lm = new CoAgencyLinkmanModel();
	private List<String> datelist = getDateList();
	private CoServePromiseSelectBll bll = new CoServePromiseSelectBll();
	private List<CoBaseServePromiseModel> list = bll.getPromiseList("and cid="
			+ cid);
	private CoAgencyLinkmanModel acml = new CoAgencyLinkmanModel();
	private List<String> causeList = new ListModelList<>();

	// 创建Window时检查该公司是否已有约束
	@Command
	public void createwin(@BindingParam("win") Window win) {
		if (list.size() > 0) {
			Messagebox.show("该公司已有服务约束信息，不能在添加", "提示", Messagebox.OK,
					Messagebox.ERROR);
			RedirectUtil util = new RedirectUtil();
			util.refreshCoUrl("../CoServePromise/CoPromise_Info.zul");
		}
	}

	// 新增服务约束提交事件
	@Command
	public void submit(@BindingParam("sbukey") Date sbukey,
			@BindingParam("win") Window win,
			@BindingParam("hspaytime") String hspaytime,
			@BindingParam("hsukey") Date hsukey,
			@BindingParam("cocogd") Grid cocogd,
			@BindingParam("entygd") Grid entygd,
			@BindingParam("rggd") Grid rggd, @BindingParam("sbgd") Grid sbgd,
			@BindingParam("hsgd") Grid hsgd) {
		DocumentsInfo_OperationController docOC = new DocumentsInfo_OperationController();
		model.setCid(Integer.parseInt(cid));
		if (sbukey != null) {
			model.setCosp_acnt_sbukeyreachtime(datetostr(sbukey));
		}
		if (hspaytime != null) {
			model.setCosp_acnt_housepaytime(hspaytime);
		}
		if (hsukey != null) {
			model.setCosp_anct_houseukeyreachtime(datetostr(hsukey));
		}
		CoServePromiseOperateBll bll = new CoServePromiseOperateBll();
		Integer id = bll.CoBaseServePromise_Add(model);
		if (id > 0) {
			try {
				docOC.AddsubmitDocInfo(cocogd, id.toString());
				docOC.AddsubmitDocInfo(entygd, id.toString());
				docOC.AddsubmitDocInfo(rggd, id.toString());
				docOC.AddsubmitDocInfo(sbgd, id.toString());
				docOC.AddsubmitDocInfo(hsgd, id.toString());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			Messagebox
					.show("提交成功", "提示", Messagebox.OK, Messagebox.INFORMATION);
			RedirectUtil util = new RedirectUtil();
			util.refreshCoUrl("../CoServePromise/CoPromise_Info.zul");
		} else {
			Messagebox.show("提交失败", "提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	// 弹出联系人信息
	@Command
	public void addlink(@BindingParam("val") String val,
			@BindingParam("htst") Combobox htst, @BindingParam("lk") Label lk,
			@BindingParam("flag") String flag) {
		if (val != null && val.equals("指定联系人")) {
			Map map = new HashMap<>();
			map.put("cid", cid);
			map.put("name", lm.getCali_name());
			map.put("acml", acml);
			lm.setCali_name("");
			Window winodow = (Window) Executions.createComponents(
					"../CoServePromise/Cobase_LinkList.zul", null, map);
			winodow.doModal();
			if (map.get("lm") != null) {
				lm = (CoAgencyLinkmanModel) map.get("lm");
				lk.setVisible(true);
			}
			if (map.get("acml") != null) {
				acml = (CoAgencyLinkmanModel) map.get("acml");
				lk.setVisible(true);
			}
			addvalue(flag);

			causeList.clear();
			causeList.add("邮寄指定人");
		}

		if (val != null && val.equals("员工本人")) {
			causeList.clear();
			causeList.add("员工上门");
			causeList.add("员工自行选择");
		}

		if (val != null && val.equals("客服")) {
			causeList.clear();
			causeList.add("转交客服");
		}
	}

	// 根据选择的联系人给选择框赋值
	private void addvalue(String flag) {
		String vals = "指定联系人—";
		if (lm.getCali_name() != null && !lm.getCali_name().equals("")) {
			if (flag.equals("1")) {
				model.setCosp_coco_signname(vals + lm.getCali_name());
			} else if (flag.equals("2")) {
				model.setCosp_enty_caliname(vals + lm.getCali_name());
			} else if (flag.equals("3")) {
				model.setCosp_sbhs_caliname(vals + lm.getCali_name());
			} else if (flag.equals("4")) {
				model.setCosp_jbrg_caliname(vals + lm.getCali_name());
			} else if (flag.equals("5")) {
				model.setCosp_card_caliname(vals + lm.getCali_name());
			} else if (flag.equals("6")) {
				model.setCosp_bcrp_caliname(vals + lm.getCali_name());
				model.setCosp_bcrp_caliid(acml.getCali_id());
			} else if (flag.equals("7")) {
				model.setCosp_bsal_caliname(vals + lm.getCali_name());
			} else if (flag.equals("8")) {
				model.setCosp_invo_caliname(vals + lm.getCali_name());
			} else if (flag.equals("9")) {
				model.setCosp_dali_caliname(vals + lm.getCali_name());
			} else if (flag.equals("10")) {
				model.setCosp_prve_caliname(vals + lm.getCali_name());
			} else if (flag.equals("11")) {
				model.setCosp_sing_caliname(vals + lm.getCali_name());
			} else if (flag.equals("12")) {
				model.setCosp_page_caliname(vals + lm.getCali_name());
			} else if (flag.equals("13")) {
				model.setCosp_sbhs_data_caliname(vals + lm.getCali_name());
			} else if (flag.equals("14")) {
				model.setCosp_jbrg_data_caliname(vals + lm.getCali_name());
			} else if (flag.equals("15")) {
				model.setCosp_card_data_caliname(vals + lm.getCali_name());
			} else if (flag.equals("16")) {
				model.setCosp_bcrp_bclinkname(vals + lm.getCali_name());
				model.setCosp_bcrp_bclinkid(acml.getCali_id());
			}
		} else {
			if (flag.equals("1")) {
				model.setCosp_coco_signname("");
			} else if (flag.equals("2")) {
				model.setCosp_enty_caliname("");
			} else if (flag.equals("3")) {
				model.setCosp_sbhs_caliname("");
			} else if (flag.equals("4")) {
				model.setCosp_jbrg_caliname("");
			} else if (flag.equals("5")) {
				model.setCosp_card_caliname("");
			} else if (flag.equals("6")) {
				model.setCosp_bcrp_caliname("");
				model.setCosp_bcrp_caliid(null);
			} else if (flag.equals("7")) {
				model.setCosp_bsal_caliname("");
			} else if (flag.equals("8")) {
				model.setCosp_invo_caliname("");
			} else if (flag.equals("9")) {
				model.setCosp_dali_caliname("");
			} else if (flag.equals("10")) {
				model.setCosp_prve_caliname("");
			} else if (flag.equals("11")) {
				model.setCosp_sing_caliname("");
			} else if (flag.equals("12")) {
				model.setCosp_page_caliname("");
			} else if (flag.equals("13")) {
				model.setCosp_sbhs_data_caliname("");
			} else if (flag.equals("14")) {
				model.setCosp_jbrg_data_caliname("");
			} else if (flag.equals("15")) {
				model.setCosp_card_data_caliname("");
			} else if (flag.equals("16")) {
				model.setCosp_bcrp_bclinkname("");
				model.setCosp_bcrp_bclinkid(null);
			}
		}
	}

	// 弹出联系人详细信息页面
	@Command
	public void linkinfo(@BindingParam("val") String val) {
		if (val.contains("联系人")) {
			String a[] = val.split("—");
			if (a.length > 1) {
				Integer cali_id = 0;
				CoBaseLinkMan_SelectBll lmBll = new CoBaseLinkMan_SelectBll();
				List<CoAgencyLinkmanModel> linklist = lmBll
						.getLinkmanByCid(Integer.parseInt(cid),1);
				for (int i = 0; i < linklist.size(); i++) {
					if (linklist.get(i).getCali_name() != null
							&& linklist.get(i).getCali_name().equals(a[1])) {
						cali_id = linklist.get(i).getCali_id();
					}
				}
				if (cali_id != 0) {
					Map<String, String> map = new HashMap<String, String>();
					map.put("cali_id", String.valueOf(cali_id));
					Window window = (Window) Executions.createComponents(
							"CoBaseLinkMan_Sel.zul", null, map);
					window.doModal();
				}
			}
		}
	}

	public CoBaseServePromiseModel getModel() {
		return model;
	}

	public void setModel(CoBaseServePromiseModel model) {
		this.model = model;
	}

	public List<String> getDatelist() {
		return datelist;
	}

	public void setDatelist(List<String> datelist) {
		this.datelist = datelist;
	}

	private List<String> getDateList() {
		List<String> dlist = new ArrayList<String>();
		for (int i = 1; i <= 31; i++) {
			dlist.add(i + "日");
		}
		return dlist;
	}

	// 日期转字符串
	private String datetostr(Date d) {
		String tostr = "";
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		if (d != null) {
			tostr = format.format(d);
		}
		return tostr;
	}

	public List<String> getCauseList() {
		return causeList;
	}

	public void setCauseList(List<String> causeList) {
		this.causeList = causeList;
	}

}
