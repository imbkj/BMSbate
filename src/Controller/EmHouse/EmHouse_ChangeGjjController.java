package Controller.EmHouse;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Grid;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;
import org.zkoss.zul.Messagebox.ClickEvent;
import bll.EmHouse.EmHouseChangeGjjBll;
import bll.EmHouse.EmHouseSetBll;
import Controller.DocumentsInfo.DocumentsInfo_OperationController;
import Model.EmHouseChangeGJJModel;
import Model.EmHouseUpdateModel;
import Model.PubCodeConversionModel;
import Util.UserInfo;

public class EmHouse_ChangeGjjController {
	private EmHouseChangeGJJModel ehg = new EmHouseChangeGJJModel();
	private List<EmHouseUpdateModel> euList = new ListModelList<>();
	private List<PubCodeConversionModel> idcardclassList = new ListModelList<>();
	private List<PubCodeConversionModel> hjList = new ListModelList<>();
	private List<PubCodeConversionModel> titleList = new ListModelList<>();
	private List<PubCodeConversionModel> degreeList = new ListModelList<>();
	private List<PubCodeConversionModel> marryList = new ListModelList<>();

	private EmHouseChangeGjjBll bll = new EmHouseChangeGjjBll();
	private EmHouseSetBll sbll = new EmHouseSetBll();
	private String userid = UserInfo.getUserid();
	private String username = UserInfo.getUsername();
	private List<String> ownmonthList = new ArrayList<>();
	private DocumentsInfo_OperationController docOC = new DocumentsInfo_OperationController();

	private Integer embaid = Integer.valueOf(Executions.getCurrent().getArg()
			.get("embaId").toString());
	private Window winC;

	// private Integer embaid = 10017;

	// private Window win = (Window) Path.getComponent("/windata");

	public EmHouse_ChangeGjjController() {
		Integer gid = bll.getembaseInfo(embaid).get(0).getGid();
		euList = bll.getEmhouseupdateInfoByGid(gid);
		if (euList.size() > 0) {
			ehg.setCid(euList.get(0).getCid());
			ehg.setGid(euList.get(0).getGid());
			ehg.setEhcg_name(euList.get(0).getEmhu_name());
			ehg.setEhcg_company(euList.get(0).getEmhu_company());
			ehg.setEmhu_companyid(euList.get(0).getEmhu_companyid());
			ehg.setEmhu_houseid(euList.get(0).getEmhu_houseid());
			ehg.setEhcg_name_p(euList.get(0).getEmhu_name());
			ehg.setEhcg_idcard_p(euList.get(0).getEmhu_idcard());
			ehg.setEhcg_idcardclass_p(euList.get(0).getEmhu_idcardclass());
			ehg.setEhcg_sex_p(euList.get(0).getEmba_sex());
			ehg.setEhcg_hj_p(euList.get(0).getEmhu_hj());
			ehg.setEhcg_sbid_p(euList.get(0).getEmhu_computerid());
			if (euList.get(0).getEmhu_wifename() != null
					&& !euList.get(0).getEmhu_wifename().equals("")) {
				ehg.setEhcg_marry_p("已婚");
				ehg.setEhcg_wifename_p(euList.get(0).getEmhu_wifename());
				if (euList.get(0).getEmhu_wifeidcard() != null
						&& !euList.get(0).getEmhu_wifeidcard().equals("")) {
					ehg.setEhcg_wifeidcard_p(euList.get(0).getEmhu_wifeidcard());
				}
			} else {
				ehg.setEhcg_marry_p("未婚");
			}
			ehg.setEhcg_title_p(euList.get(0).getEmhu_title());
			ehg.setEhcg_degree_p(euList.get(0).getEmhu_degree());
			ehg.setEhcg_email_p(euList.get(0).getEmba_email());
			ehg.setEhcg_phone_p(euList.get(0).getEmhu_mobile());

			ehg.setEhcg_addname(username);

			setOwnmonthList(ehg.getGid());
			ehg.setOwnmonth(sbll.nowmonth2(ehg.getGid()));
			// ehg.setOwnmonth(Integer.valueOf(ownmonthList.get(0)));
			setDegreeList(35, "最高学历");
			setHjList(35, "户籍类型");
			setIdcardclassList(35, "证件类型");
			setMarryList(35, "婚姻状况");
			setTitleList(35, "职称代码");
		}

	}

	@Command("winC")
	public void winC(@BindingParam("a") Window winD) {
		winC = winD;
		if (euList.size() == 0) {
			Messagebox.show("该员工没有在册数据,无法添加档案变更!", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
			winC.detach();
		}
		
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
							Integer i = bll.addData(ehg);

							if (i > 0) {
								Grid gd = (Grid) winC.getFellow("docGrid");

								docOC.AddsubmitDoc(gd, i.toString());
								Messagebox.show("添加成功!", "操作提示", Messagebox.OK,
										Messagebox.INFORMATION);
							}

							winC.detach();

						}
					}
				});

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

	public EmHouseChangeGJJModel getEhg() {
		return ehg;
	}

	public void setEhg(EmHouseChangeGJJModel ehg) {
		this.ehg = ehg;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
