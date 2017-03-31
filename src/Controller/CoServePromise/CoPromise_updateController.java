package Controller.CoServePromise;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import bll.CoBase.CoBaseLinkMan_SelectBll;
import bll.CoBase.CoBase_SelectBll;
import bll.CoServePromise.CoServePromiseOperateBll;
import bll.CoServePromise.CoServePromiseSelectBll;

import Controller.DocumentsInfo.DocumentsInfo_OperationController;
import Model.CoAgencyLinkmanModel;
import Model.CoBaseServePromiseModel;
import Model.CoHousingFundModel;
import Model.CoShebaoModel;
import Util.RedirectUtil;

public class CoPromise_updateController {
	private String cid = Executions.getCurrent().getArg().get("cid").toString();
	private CoBaseServePromiseModel model = new CoBaseServePromiseModel();
	private CoServePromiseSelectBll bll = new CoServePromiseSelectBll();
	private List<CoBaseServePromiseModel> list = new ArrayList<CoBaseServePromiseModel>();
	private CoAgencyLinkmanModel lm = new CoAgencyLinkmanModel();
	private boolean lkvisbile = false, lkenvisbile = false,
			lkhsvisbile = false, lkrgvisbile = false, lkcdvisbile = false,
			lkrpvisbile = false, lkblvisbile = false, lkiovisbile = false,
			lkdivisbile = false, lkpedivisbile = false, lksgvisbile = false;
	CoBase_SelectBll cobaSBll = new CoBase_SelectBll();
	private boolean visno = true, vis = false;
	private List<CoShebaoModel> scsbList = new ListModelList<>();
	private List<CoHousingFundModel> scohfList = new ListModelList<>();

	private List<String> datelist = getDateList();

	private String cocodoType = "a";// 不能为空，必须填 a 或 u;--合同
	private String entydoType = "a";// 不能为空，必须填 a 或 u--入职
	private String rgdoType = "a";// 不能为空，必须填 a 或 u--就业登记
	private String sbdoType = "a";// 不能为空，必须填 a 或 u--企业社保
	private String hsdoType = "a";// 不能为空，必须填 a 或 u--企业公积金

	private CoAgencyLinkmanModel acml = new CoAgencyLinkmanModel();

	public CoPromise_updateController() {
		list = bll.getPromiseList("and cid=" + cid);

		if (list.size() > 0) {
			model = list.get(0);
			Integer cocok = bll.ifHasDoc(132, model.getCosp_id());// 合同
			if (cocok > 0) {
				cocodoType = "u";
			}
			Integer entyk = bll.ifHasDoc(133, model.getCosp_id());// 入职
			if (entyk > 0) {
				entydoType = "u";
			}
			Integer rgk = bll.ifHasDoc(134, model.getCosp_id());// 就业登记
			if (rgk > 0) {
				rgdoType = "u";
			}
		} else {
			visno = true;
			vis = false;
		}
		ifvisible();
		try {
			setScsbList(cobaSBll.getCoShebaoList(" and a.cid=" + cid + " "));
			setScohfList(cobaSBll.getCoHoList(" and a.cid=" + cid + "", false));
		} catch (Exception e) {

		}
	}

	// 创建Window时检查该公司是否已有约束
	@Command
	public void createwin(@BindingParam("win") Window win) {
		if (list.size() <= 0) {
			Messagebox.show("该公司还没有服务约束信息", "提示", Messagebox.OK,
					Messagebox.ERROR);
			RedirectUtil util = new RedirectUtil();
			util.refreshCoUrl("../CoServePromise/CoPromise_Info.zul");
		}
	}

	// 修改服务约束提交事件
	@Command
	public void submit(@BindingParam("sbukey") Date sbukey,
			@BindingParam("win") Window win,
			@BindingParam("hspaytime") String hspaytime,
			@BindingParam("hsukey") Date hsukey,
			@BindingParam("cocogd") Grid cocogd,
			@BindingParam("entygd") Grid entygd,
			@BindingParam("rggd") Grid rggd, @BindingParam("sbgd") Grid sbgd,
			@BindingParam("hsgd") Grid hsgd) {
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

		CoServePromiseOperateBll obll = new CoServePromiseOperateBll();
		Integer id = obll.CoBaseServePromise_Update(model);
		if (id > 0) {
			String[] message = new String[5];
			DocumentsInfo_OperationController docOC = new DocumentsInfo_OperationController();
			try {
				// 新增
				if ("a".equals(cocodoType)) {
					message = docOC.AddsubmitDoc(cocogd, model.getCosp_id()
							.toString());
				} else {
					message = docOC.UpsubmitDoc(cocogd, model.getCosp_id()
							.toString());
				}
				// 新增
				if ("a".equals(entydoType)) {
					message = docOC.AddsubmitDoc(entygd, model.getCosp_id()
							.toString());
				} else {
					message = docOC.UpsubmitDoc(entygd, model.getCosp_id()
							.toString());
				}
				// 新增
				if ("a".equals(rgdoType)) {
					message = docOC.AddsubmitDoc(rggd, model.getCosp_id()
							.toString());
				} else {
					message = docOC.UpsubmitDoc(rggd, model.getCosp_id()
							.toString());
				}
				// 新增
				if ("a".equals(sbdoType)) {
					message = docOC.AddsubmitDoc(sbgd, model.getCosp_id()
							.toString());
				} else {
					message = docOC.UpsubmitDoc(sbgd, model.getCosp_id()
							.toString());
				}
				// 新增
				if ("a".equals(hsdoType)) {
					message = docOC.AddsubmitDoc(hsgd, model.getCosp_id()
							.toString());
				} else {
					message = docOC.UpsubmitDoc(hsgd, model.getCosp_id()
							.toString());
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (message[0] == "1") {
				Messagebox.show("提交成功", "提示", Messagebox.OK,
						Messagebox.INFORMATION);
				RedirectUtil util = new RedirectUtil();
				util.refreshCoUrl("../CoServePromise/CoPromise_Info.zul");
			} else {
				Messagebox.show("基本信息更新成功，材料信息更新失败", "提示", Messagebox.OK,
						Messagebox.INFORMATION);
				win.detach();
			}
		} else {
			Messagebox.show("提交失败", "提示", Messagebox.OK, Messagebox.ERROR);
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
							"../CoBase/CoBaseLinkMan_Sel.zul", null, map);
					window.doModal();
				}
			}
		}
	}

	/**
	 * 弹出窗口
	 * 
	 * @param url
	 *            窗口链接
	 * @param m
	 */
	@Command
	@NotifyChange({ "scohfList" })
	public void openwin(@BindingParam("each") CoHousingFundModel m,
			@BindingParam("url") String url) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("daid", m.getCohf_id());
		map.put("cid", m.getCid());

		try {
			if (!url.isEmpty()) {
				Window window = (Window) Executions.createComponents(url, null,
						map);
				window.doModal();

			}
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox.show("弹出窗口出错：" + e.toString(), "ERROR", Messagebox.OK,
					Messagebox.ERROR);
		}
	}

	/**
	 * 账户详情
	 * 
	 * @param m
	 */
	@Command("detail")
	public void detail(@BindingParam("each") CoShebaoModel m) {
		String url = "/CoSocialInsurance/CoSocialInsurance_Detail.zul";
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("daid", m.getCosb_id());
		map.put("role", "qd");

		Window window = (Window) Executions.createComponents(url, null, map);
		window.doModal();
	}

	// 弹出联系人信息
	@Command
	public void addlink(@BindingParam("val") String val,
			@BindingParam("lk") Label lk, @BindingParam("flag") String flag) {
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
				ifvisible();
			}
			if (map.get("acml") != null) {
				acml = (CoAgencyLinkmanModel) map.get("acml");
				lk.setVisible(true);
			}
			addvalue(flag);
		} else {
			if (flag.equals("6")) {
				model.setCosp_bcrp_caliid(null);
			} else if (flag.equals("16")) {
				model.setCosp_bcrp_bclinkid(null);
			}
			lk.setVisible(false);
			ifvisible();
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

	// 设置查看的可见和隐藏
	private void ifvisible() {
		String vals = "指定联系人—";
		if (model.getCosp_coco_signname() != null
				&& !model.getCosp_coco_signname().equals("")) {
			if (model.getCosp_coco_signname().contains(vals)) {
				lkvisbile = true;
			} else {
				lkvisbile = false;
			}
		}
		if (model.getCosp_enty_caliname() != null
				&& !model.getCosp_enty_caliname().equals("")) {
			if (model.getCosp_enty_caliname().contains(vals)) {
				lkenvisbile = true;
			} else {
				lkenvisbile = false;
			}
		}
		if (model.getCosp_sbhs_caliname() != null
				&& !model.getCosp_sbhs_caliname().equals("")) {
			if (model.getCosp_sbhs_caliname().contains(vals)) {
				lkhsvisbile = true;
			} else {
				lkhsvisbile = false;
			}
		}
		if (model.getCosp_jbrg_caliname() != null
				&& !model.getCosp_jbrg_caliname().equals("")) {
			if (model.getCosp_jbrg_caliname().contains(vals)) {
				lkrgvisbile = true;
			} else {
				lkrgvisbile = false;
			}
		}
		if (model.getCosp_card_caliname() != null
				&& !model.getCosp_card_caliname().equals("")) {
			if (model.getCosp_card_caliname().contains(vals)) {
				lkcdvisbile = true;
			} else {
				lkcdvisbile = false;
			}
		}
		if (model.getCosp_bcrp_caliname() != null
				&& !model.getCosp_bcrp_caliname().equals("")) {
			if (model.getCosp_bcrp_caliname().contains(vals)) {
				lkrpvisbile = true;
			} else {
				lkrpvisbile = false;
			}
		}
		if (model.getCosp_bsal_caliname() != null
				&& !model.getCosp_bsal_caliname().equals("")) {
			if (model.getCosp_bsal_caliname().contains(vals)) {
				lkblvisbile = true;
			} else {
				lkblvisbile = false;
			}
		}
		if (model.getCosp_invo_caliname() != null
				&& !model.getCosp_invo_caliname().equals("")) {
			if (model.getCosp_invo_caliname().contains(vals)) {
				lkiovisbile = true;
			} else {
				lkiovisbile = false;
			}
		}
		if (model.getCosp_dali_caliname() != null
				&& !model.getCosp_dali_caliname().equals("")) {
			if (model.getCosp_dali_caliname().contains(vals)) {
				lkdivisbile = true;
			} else {
				lkdivisbile = false;
			}
		}
		if (model.getCosp_prve_caliname() != null
				&& !model.getCosp_prve_caliname().equals("")) {
			if (model.getCosp_prve_caliname().contains(vals)) {
				lkpedivisbile = true;
			} else {
				lkpedivisbile = false;
			}
		}
		if (model.getCosp_sing_caliname() != null
				&& !model.getCosp_sing_caliname().equals("")) {
			if (model.getCosp_sing_caliname().contains(vals)) {
				lksgvisbile = true;
			} else {
				lksgvisbile = false;
			}
		}
	}

	public CoBaseServePromiseModel getModel() {
		return model;
	}

	public void setModel(CoBaseServePromiseModel model) {
		this.model = model;
	}

	public boolean isLkvisbile() {
		return lkvisbile;
	}

	public void setLkvisbile(boolean lkvisbile) {
		this.lkvisbile = lkvisbile;
	}

	public boolean isLkenvisbile() {
		return lkenvisbile;
	}

	public void setLkenvisbile(boolean lkenvisbile) {
		this.lkenvisbile = lkenvisbile;
	}

	public boolean isLkhsvisbile() {
		return lkhsvisbile;
	}

	public void setLkhsvisbile(boolean lkhsvisbile) {
		this.lkhsvisbile = lkhsvisbile;
	}

	public boolean isLkrgvisbile() {
		return lkrgvisbile;
	}

	public void setLkrgvisbile(boolean lkrgvisbile) {
		this.lkrgvisbile = lkrgvisbile;
	}

	public boolean isLkcdvisbile() {
		return lkcdvisbile;
	}

	public void setLkcdvisbile(boolean lkcdvisbile) {
		this.lkcdvisbile = lkcdvisbile;
	}

	public boolean isLkrpvisbile() {
		return lkrpvisbile;
	}

	public void setLkrpvisbile(boolean lkrpvisbile) {
		this.lkrpvisbile = lkrpvisbile;
	}

	public boolean isLkblvisbile() {
		return lkblvisbile;
	}

	public void setLkblvisbile(boolean lkblvisbile) {
		this.lkblvisbile = lkblvisbile;
	}

	public boolean isLkiovisbile() {
		return lkiovisbile;
	}

	public void setLkiovisbile(boolean lkiovisbile) {
		this.lkiovisbile = lkiovisbile;
	}

	public boolean isLkdivisbile() {
		return lkdivisbile;
	}

	public void setLkdivisbile(boolean lkdivisbile) {
		this.lkdivisbile = lkdivisbile;
	}

	public boolean isLkpedivisbile() {
		return lkpedivisbile;
	}

	public void setLkpedivisbile(boolean lkpedivisbile) {
		this.lkpedivisbile = lkpedivisbile;
	}

	public boolean isLksgvisbile() {
		return lksgvisbile;
	}

	public void setLksgvisbile(boolean lksgvisbile) {
		this.lksgvisbile = lksgvisbile;
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

	public List<CoShebaoModel> getScsbList() {
		return scsbList;
	}

	public void setScsbList(List<CoShebaoModel> scsbList) {
		this.scsbList = scsbList;
	}

	public List<CoHousingFundModel> getScohfList() {
		return scohfList;
	}

	public void setScohfList(List<CoHousingFundModel> scohfList) {
		this.scohfList = scohfList;
	}

	public boolean isVisno() {
		return visno;
	}

	public void setVisno(boolean visno) {
		this.visno = visno;
	}

	public boolean isVis() {
		return vis;
	}

	public void setVis(boolean vis) {
		this.vis = vis;
	}

	public String getCocodoType() {
		return cocodoType;
	}

	public void setCocodoType(String cocodoType) {
		this.cocodoType = cocodoType;
	}

	public String getEntydoType() {
		return entydoType;
	}

	public void setEntydoType(String entydoType) {
		this.entydoType = entydoType;
	}

	public String getRgdoType() {
		return rgdoType;
	}

	public void setRgdoType(String rgdoType) {
		this.rgdoType = rgdoType;
	}

	public String getSbdoType() {
		return sbdoType;
	}

	public void setSbdoType(String sbdoType) {
		this.sbdoType = sbdoType;
	}

	public String getHsdoType() {
		return hsdoType;
	}

	public void setHsdoType(String hsdoType) {
		this.hsdoType = hsdoType;
	}

}
