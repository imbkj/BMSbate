package Controller.CoBase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Detail;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;
import org.zkoss.zul.Messagebox.ClickEvent;

import bll.CoBase.CoBase_SelectBll;

import Model.CoCompactModel;
import Model.CoOfferModel;

public class CoBase_SelectCoOfferqgController {

	Integer cid = 0;
	Integer coco_id = 0;
	String coco_compactid = "";
	String h = "";

	private List<CoCompactModel> compactList = new ListModelList<>();
	private CoCompactModel compactModel = new CoCompactModel();
	private List<CoOfferModel> coofferList;
	private List<CoOfferModel> scoofferList = new ListModelList<>();

	public CoBase_SelectCoOfferqgController() {
		try {
			// cid = Integer.valueOf(Executions.getCurrent().getArg().get("cid")
			// .toString());
			if (Executions.getCurrent().getArg()
					.get("coco_compactid")!=null) {
				coco_compactid = Executions.getCurrent().getArg()
						.get("coco_compactid").toString();
			}
			
			// coco_compactid="SZ2015065FS";
			//
			if (Executions.getCurrent().getArg().get("coco_id") != null) {
				coco_id = Integer.valueOf(Executions.getCurrent().getArg()
						.get("coco_id").toString());
			}
			init();
		} catch (Exception e) {
			// e.printStackTrace();
			// Messagebox
			// .show("页面加载出错!", "ERROR", Messagebox.OK, Messagebox.ERROR);
		}
	}

	/**
	 * 初始化
	 * 
	 */
	public void init() {
		CoBase_SelectBll bll = new CoBase_SelectBll();
		try {
			setCompactList(bll.getcompactList(cid));
			compactList.add(0, new CoCompactModel());
			if (cid > 0) {

				setCoofferList(bll.getcoofferList(" and b.cid=" + cid));
			}
			if (coco_compactid.length() > 0) {
				setCoofferList(bll.getcoofferList(" and b.coco_compactid='"
						+ coco_compactid + "'"));
			}
			if (coco_id>0) {
				setCoofferList(bll.getcoofferList(" and b.coco_id="
						+ coco_id));
			}

			search();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Command
	@NotifyChange("h")
	public void test(@BindingParam("a") Detail d,
			@BindingParam("b") CoOfferModel cm) {
		if (d.isOpen()) {
			h = "600px";
		} else {

			h = coofferList.size() * 40 + 55 + "px";
		}

	}

	/**
	 * 检索
	 * 
	 */
	@Command
	@NotifyChange({ "scoofferList" })
	public void search() {
		try {
			scoofferList.clear();

			for (CoOfferModel m : coofferList) {
				if (compactModel.getCoco_id() != null) {
					if (!m.getCoof_coco_id().equals(compactModel.getCoco_id())) {
						continue;
					}
				}

				scoofferList.add(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Command
	// @NotifyChange("cocoBaseList")
	public void itemAdd(@BindingParam("a") CoCompactModel cocoM) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cid", cocoM.getCid());
		map.put("coco_id", cocoM.getCoco_id());
		map.put("company", cocoM.getCompany());
		Window window = (Window) Executions.createComponents(
				"/CoQuotation/Coquotation_ItemAdd.zul", null, map);
		window.doModal();
		// cocoBll.getCoCoBaseAll();
	}

	// 弹出查看页面
	@Command("chakan")
	public void chakan(@BindingParam("model") CoOfferModel model) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("coofid", model.getCoof_id());
		Window window = (Window) Executions.createComponents(
				"/CoQuotation/CoQuotationInfoSelectqg.zul", null, map);
		window.doModal();
	}

	// 删除报价单
	@Command
	@NotifyChange("coofferList")
	public void del(@BindingParam("model") final CoOfferModel m) {
		final CoBase_SelectBll sbll = new CoBase_SelectBll();
		if (sbll.HaveItem(m.getCoof_id())) {
			Messagebox.show("请先终止包含该报价单项目的员工项目.", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
			return;
		} else {
			Messagebox.show("确认删除报价单?", "操作提示", new Messagebox.Button[] {
					Messagebox.Button.OK, Messagebox.Button.CANCEL },
					Messagebox.QUESTION,
					new EventListener<Messagebox.ClickEvent>() {
						@Override
						public void onEvent(ClickEvent event) throws Exception {
							// TODO Auto-generated method stub

							if (Messagebox.Button.OK.equals(event.getButton())) {
								if (sbll.delCooffer(m.getCoof_id())) {
									Messagebox.show("删除成功.", "操作提示",
											Messagebox.OK,
											Messagebox.INFORMATION);
									init();
								} else {
									Messagebox.show("删除失败,请联系IT部.", "操作提示",
											Messagebox.OK, Messagebox.ERROR);
								}
							}
						}
					});

		}

	}

	public List<CoCompactModel> getCompactList() {
		return compactList;
	}

	public void setCompactList(List<CoCompactModel> compactList) {
		this.compactList = compactList;
	}

	public List<CoOfferModel> getCoofferList() {
		return coofferList;
	}

	public void setCoofferList(List<CoOfferModel> coofferList) {
		this.coofferList = coofferList;
	}

	public CoCompactModel getCompactModel() {
		return compactModel;
	}

	public void setCompactModel(CoCompactModel compactModel) {
		this.compactModel = compactModel;
	}

	public List<CoOfferModel> getScoofferList() {
		return scoofferList;
	}

	public void setScoofferList(List<CoOfferModel> scoofferList) {
		this.scoofferList = scoofferList;
	}

	public String getH() {
		return h;
	}

	public void setH(String h) {
		this.h = h;
	}

}
