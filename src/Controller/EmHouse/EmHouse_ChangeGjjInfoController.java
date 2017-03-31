package Controller.EmHouse;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;
import org.zkoss.zul.Messagebox.ClickEvent;

import bll.EmHouse.EmHouseChangeGjjBll;
import bll.EmHouse.EmHouse_EditBll;

import Model.EmHouseChangeGJJModel;
import Model.EmHouseUpdateModel;
import Model.PubCodeConversionModel;

public class EmHouse_ChangeGjjInfoController {
	private List<String> ownmonthList = new ArrayList<>();
	private List<PubCodeConversionModel> idcardclassList = new ListModelList<>();
	private List<PubCodeConversionModel> hjList = new ListModelList<>();
	private List<PubCodeConversionModel> titleList = new ListModelList<>();
	private List<PubCodeConversionModel> degreeList = new ListModelList<>();
	private List<PubCodeConversionModel> marryList = new ListModelList<>();
	private List<EmHouseUpdateModel> euList = new ListModelList<>();

	private boolean deadline = false;
	private EmHouseChangeGJJModel ehg = (EmHouseChangeGJJModel) Executions
			.getCurrent().getArg().get("em");

	private EmHouseChangeGjjBll bll = new EmHouseChangeGjjBll();
	
	private Window winC;

	public EmHouse_ChangeGjjInfoController() {
		setOwnmonthList(ehg.getGid());
		setDegreeList(35, "最高学历");
		setHjList(35, "户籍类型");
		setIdcardclassList(35, "证件类型");
		setMarryList(35, "婚姻状况");
		setTitleList(35, "职称代码");

		euList = bll.getEmhouseupdateInfoByGid(ehg.getGid());
		if (euList.size() > 0) {
			
			ehg.setEhcg_company(euList.get(0).getCoba_shortname());
			ehg.setEmhu_companyid(euList.get(0).getEmhu_companyid());
			ehg.setEmhu_houseid(euList.get(0).getEmhu_houseid());

			if (ehg.getEhcg_name_p() == null || ehg.getEhcg_name_p().equals("")) {
				if (euList.get(0).getEmhu_name() != null
						&& !euList.get(0).getEmhu_name().equals("")) {
					ehg.setEhcg_name_p(euList.get(0).getEmhu_name());
				}
			}
			if (ehg.getEhcg_idcard_p() == null
					|| ehg.getEhcg_idcard_p().equals("")) {
				if (euList.get(0).getEmhu_idcard() != null
						&& !euList.get(0).getEmhu_idcard().equals("")) {
					ehg.setEhcg_idcard_p(euList.get(0).getEmhu_idcard());
				}
			}

			if (ehg.getEhcg_idcardclass_p() == null
					|| ehg.getEhcg_idcardclass_p().equals("")) {
				if (euList.get(0).getEmhu_idcardclass() != null
						&& !euList.get(0).getEmhu_idcardclass().equals("")) {
					ehg.setEhcg_idcardclass_p(euList.get(0)
							.getEmhu_idcardclass());
				}
			}
			if (ehg.getEhcg_sex_p() == null || ehg.getEhcg_sex_p().equals("")) {
				if (euList.get(0).getEmba_sex() != null
						&& !euList.get(0).getEmba_sex().equals("")) {
					ehg.setEhcg_sex_p(euList.get(0).getEmba_sex());
				}
			}
			if (ehg.getEhcg_hj_p() == null || ehg.getEhcg_hj_p().equals("")) {
				if (euList.get(0).getEmhu_hj() != null
						&& !euList.get(0).getEmhu_hj().equals("")) {
					ehg.setEhcg_hj_p(euList.get(0).getEmhu_hj());
				}
			}
			if (ehg.getEhcg_sbid_p() == null || ehg.getEhcg_sbid_p().equals("")) {
				if (euList.get(0).getEmhu_computerid() != null
						&& !euList.get(0).getEmhu_computerid().equals("")) {
					ehg.setEhcg_sbid_p(euList.get(0).getEmhu_computerid());
				}
			}
			if (ehg.getEhcg_marry_p() == null
					|| ehg.getEhcg_marry_p().equals("")) {
				if (euList.get(0).getEmhu_wifename() != null
						&& !euList.get(0).getEmhu_wifename().equals("")) {
					ehg.setEhcg_marry_p("已婚");

				} else {
					ehg.setEhcg_marry_p("未婚");
				}
			}
			if (ehg.getEhcg_wifename_p() == null
					|| ehg.getEhcg_wifename_p().equals("")) {
				if (euList.get(0).getEmhu_wifename() != null
						&& !euList.get(0).getEmhu_wifename().equals("")) {
					ehg.setEhcg_wifename_p(euList.get(0).getEmhu_wifename());
				}
			}
			if (ehg.getEhcg_wifeidcard_p() == null
					|| ehg.getEhcg_wifeidcard_p().equals("")) {
				if (euList.get(0).getEmhu_wifeidcard() != null
						&& !euList.get(0).getEmhu_wifeidcard().equals("")) {
					ehg.setEhcg_wifeidcard_p(euList.get(0).getEmhu_wifeidcard());
				}

			}

			if (ehg.getEhcg_title_p() == null
					|| ehg.getEhcg_title_p().equals("")) {
				if (euList.get(0).getEmhu_title() != null
						&& !euList.get(0).getEmhu_title().equals("")) {
					ehg.setEhcg_title_p(euList.get(0).getEmhu_title());
				}
			}
			if (ehg.getEhcg_degree_p() == null
					|| ehg.getEhcg_degree_p().equals("")) {
				if (euList.get(0).getEmhu_degree() != null
						&& !euList.get(0).getEmhu_degree().equals("")) {
					ehg.setEhcg_degree_p(euList.get(0).getEmhu_degree());
				}
			}
			if (ehg.getEhcg_email_p() == null
					|| ehg.getEhcg_email_p().equals("")) {
				if (euList.get(0).getEmba_email() != null
						&& !euList.get(0).getEmba_email().equals("")) {
					ehg.setEhcg_email_p(euList.get(0).getEmba_email());
				}
			}
			if (ehg.getEhcg_phone_p() == null
					|| ehg.getEhcg_phone_p().equals("")) {
				if (euList.get(0).getEmhu_mobile() != null
						&& !euList.get(0).getEmhu_mobile().equals("")) {
					ehg.setEhcg_phone_p(euList.get(0).getEmhu_mobile());
				}
			}
		}
	}

	@Command("winC")
	public void winC(@BindingParam("a") Window winD) {
		winC = winD;

	}

	@Command("submit")
	public void submit() {
		if (ehg.getOwnmonth() == null || ehg.getOwnmonth().equals("")) {
			Messagebox.show("请选择所属月份.", "操作提示", Messagebox.OK,
					Messagebox.INFORMATION);
			return;
		}

		if ((ehg.getEhcg_name_n() == null || ehg.getEhcg_name_n().equals(""))
				&& (ehg.getEhcg_idcardclass_n() == null || ehg
						.getEhcg_idcardclass_n().equals(""))
				&& (ehg.getEhcg_idcard_n() == null || ehg.getEhcg_idcard_n()
						.equals(""))
				&& (ehg.getEhcg_sex_n() == null || ehg.getEhcg_sex_n().equals(
						""))
				&& (ehg.getEhcg_hj_n() == null || ehg.getEhcg_hj_n().equals(""))
				&& (ehg.getEhcg_sbid_n() == null || ehg.getEhcg_sbid_n()
						.equals(""))
				&& (ehg.getEhcg_marry_n() == null || ehg.getEhcg_marry_n()
						.equals(""))
				&& (ehg.getEhcg_wifename_n() == null || ehg
						.getEhcg_wifename_n().equals(""))
				&& (ehg.getEhcg_wifeidcard_n() == null || ehg
						.getEhcg_wifeidcard_n().equals(""))
				&& (ehg.getEhcg_title_n() == null || ehg.getEhcg_title_n()
						.equals(""))
				&& (ehg.getEhcg_degree_n() == null || ehg.getEhcg_degree_n()
						.equals(""))
				&& (ehg.getEhcg_email_n() == null || ehg.getEhcg_email_n()
						.equals(""))
				&& (ehg.getEhcg_phone_n() == null || ehg.getEhcg_phone_n()
						.equals(""))) {
			Messagebox.show("请输入需要变更的信息", "操作提示", Messagebox.OK,
					Messagebox.INFORMATION);
			return;
		}

		if (ehg.getEhcg_remark() == null || ehg.getEhcg_remark().equals("")) {
			Messagebox.show("请输入出错原因", "操作提示", Messagebox.OK,
					Messagebox.INFORMATION);
			return;
		}

		Messagebox.show("确认提交数据?", "操作提示", new Messagebox.Button[] {
				Messagebox.Button.OK, Messagebox.Button.CANCEL },
				Messagebox.QUESTION,
				new EventListener<Messagebox.ClickEvent>() {
					@Override
					public void onEvent(ClickEvent event) throws Exception {
						// TODO Auto-generated method stub

						if (Messagebox.Button.OK.equals(event.getButton())) {
							Integer i=bll.mod(ehg);
							if (i>0) {
								Messagebox.show("修改成功", "操作提示", Messagebox.OK,
										Messagebox.INFORMATION);
								winC.detach();
							}else {
								Messagebox.show("提交失败", "操作提示", Messagebox.OK,
										Messagebox.ERROR);
							}
						}
					}
				});
	}

	public EmHouseChangeGJJModel getEhg() {
		return ehg;
	}

	public void setEhg(EmHouseChangeGJJModel ehg) {
		this.ehg = ehg;
	}

	public List<PubCodeConversionModel> getIdcardclassList() {
		return idcardclassList;
	}

	public void setIdcardclassList(Integer id, String name) {
		this.idcardclassList = bll.getchangeModel(id, name);
	}

	public List<PubCodeConversionModel> getHjList() {
		return hjList;
	}

	public void setHjList(Integer id, String name) {
		this.hjList = bll.getchangeModel(id, name);
	}

	public List<PubCodeConversionModel> getTitleList() {
		return titleList;
	}

	public void setTitleList(Integer id, String name) {
		this.titleList = bll.getchangeModel(id, name);
	}

	public List<PubCodeConversionModel> getDegreeList() {
		return degreeList;
	}

	public void setDegreeList(Integer id, String name) {
		this.degreeList = bll.getchangeModel(id, name);
	}

	public List<PubCodeConversionModel> getMarryList() {
		return marryList;
	}

	public void setMarryList(Integer id, String name) {
		this.marryList = bll.getchangeModel(id, name);
	}

	public List<String> getOwnmonthList() {
		return ownmonthList;
	}

	public void setOwnmonthList(Integer gid) {
		this.ownmonthList = bll.getOwnmonthList(gid);
	}
}
