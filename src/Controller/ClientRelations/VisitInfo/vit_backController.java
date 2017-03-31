package Controller.ClientRelations.VisitInfo;

import impl.UserInfoImpl;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Execution;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zkmax.zul.Chosenbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Messagebox.ClickEvent;
import org.zkoss.zul.Window;

import service.UserInfoService;

import bll.ClientRelations.VisitInfo.vit_backBll;

import Model.VisitFollowModel;
import Model.VisitInfoModel;
import Util.plyUtil;

public class vit_backController extends SelectorComposer<Component> {

	private List<VisitFollowModel> followList = new ListModelList<VisitFollowModel>();
	private ListModelList<String> aimList = new ListModelList<String>();
	private List<VisitFollowModel> deptList = new ListModelList<VisitFollowModel>();
	private ListModelList<String> costList = new ListModelList<String>();
	private ListModelList<String> lintmanList = new ListModelList<String>();
	private int a;
	 int viin_tapr_id;
	 int viin_id;
	

//	//节点ID
//	int Viin_tapr_id=Integer.parseInt(Executions.getCurrent().getArg().get("id").toString());
//
//	//表ID
//	int viin_id=Integer.parseInt(Executions.getCurrent().getArg().get("daid").toString());

	plyUtil ply = new plyUtil();
	VisitFollowModel vfm = new VisitFollowModel();
	
	// 获取session，实例化UserInfoService接口
	Session session = Executions.getCurrent().getDesktop().getSession();
	UserInfoService user = new UserInfoImpl(session);

	@Wire
	private Chosenbox cost;// 成本类型
	@Wire
	private Textbox costremark;// 具体说明
	@Wire
	private Chosenbox personed;// 被访人员
	@Wire
	private Textbox job;// 被访职位
	@Wire
	private Chosenbox aim;// 主要目的
	@Wire
	private Datebox truetime;// 实际拜访时间
	@Wire
	private Textbox summary;// 纪要
	@Wire
	private Textbox feedback;// 客户反馈
	@Wire
	private Radiogroup iffollow;// 是否跟进
	@Wire
	private Grid gdfollows;// 跟进事项grid
	@Wire
	private Window win;

	
	public vit_backController()
	{
		try{
			
			viin_tapr_id=Integer.parseInt(Executions.getCurrent().getArg().get("id").toString());

			viin_id=Integer.parseInt(Executions.getCurrent().getArg().get("daid").toString());
			System.out.println(viin_id);
			
			a = 1;
			// 跟进事项
			vfm.setVifo_order(1);
			followList.add(vfm);

			// 跟进部门
			setDeptList(vit_backBll.getDept());

			// 拜访/回访主要目的
			aimList.add("联络感情接近客户关系,建立情感账户;");
			aimList.add("了解客户的情况(经营状况、财务状况、行业动向等);");
			aimList.add("拓展服务(及时掌握客户需求,推荐福利产品,让客户介绍信客户等);");
			aimList.add("解决客户不满(解决服务当中的问题、处理客户投诉等);");
			aimList.add("了解客户对服务质量的评价、提高客户满意度;");
			aimList.add("业务资料交接;");

			// 成本类型
			costList.add("礼品");
			costList.add("宴请");
			costList.add("其他");
			costList.add("无");
			
			

			// 联系人姓名
			setLintmanList(vit_backBll.getLinkmanList(viin_id));
	
			}
			catch(Exception e)
			{
				System.out.println(e.toString());
			}
	
	}
	
	// 新增被访人员
	@Command("newpersoned")
	public void newpersoned(@BindingParam("person") String person) {
		lintmanList.add(person);
		lintmanList.addToSelection(person);
	}

	// 新增成本类型
	@Command("newcost")
	public void newcost(@BindingParam("cost") String cost) {
		costList.add(cost);
		costList.addToSelection(cost);
	}

	@Command("iffollow")
	public void iffollow(@BindingParam("radio") Radio radio,
			@BindingParam("gbgj") Groupbox gbgj) {
		if (radio.getLabel().equals("是")) {
			gbgj.setVisible(true);
		} else {
			gbgj.setVisible(false);
			followList.clear();
			a = 1;
			vfm.setVifo_order(1);
			followList.add(vfm);
		}
	}

	// 跟进事项动态生成
	@Command("followchange")
	public void followchange(@BindingParam("gdfollows") Grid gdfollows) {
		int count = ply.getGridCount(gdfollows);
		if (a <= 0) {
			a = count;
			Messagebox.show("跟进事项数错误!", "ERROR", Messagebox.OK,
					Messagebox.ERROR);
		} else if (a > count) {
			for (int i = 0; i < a - count; i++) {
				VisitFollowModel model = new VisitFollowModel();
				model.setVifo_order(count + 1 + i);
				followList.add(model);
			}
		} else if (count > a) {
			for (int i = 0; i < count - a; i++) {
				followList.remove(count - 1 - i);
			}
		}
	}

	// 提交
	@Listen("onClick = #submit")
	public void submit() throws Exception {
		int count = 0;
		int state = 5;
		int type=0;
		boolean viin_iffollow = false;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		VisitInfoModel model = new VisitInfoModel();
		if (iffollow.getSelectedItem().getLabel().equals("否")) {
			if (Messagebox.show("该次拜访计划是否完结?", "INFORMATION", Messagebox.YES
					| Messagebox.NO, Messagebox.INFORMATION) == Messagebox.NO) {
				state = 2;
				type=1;
			}
		} else {
			viin_iffollow = true;
			state = 3;
		}
		System.out.println(viin_id);
		model.setViin_id(viin_id);
		model.setViin_cost(ply.SetToString(cost.getSelectedObjects()));
		model.setViin_costremark(costremark.getValue());
		model.setViin_personed(ply.SetToString(personed.getSelectedObjects()));
		model.setViin_job(job.getValue());
		model.setViin_aim(ply.SetToString(aim.getSelectedObjects()).replace(";,", ";"));
		model.setViin_truetime(truetime.getValue() == null ? null : sdf
				.parse(sdf.format(truetime.getValue())));
		model.setViin_summary(summary.getValue());
		model.setViin_feedback(feedback.getValue());
		model.setViin_iffolow(viin_iffollow);
		model.setViin_state(state);
		model.setViin_addname(user.getRolName());
		
		model.setViin_tapr_id(viin_tapr_id);
		
		
		

		int row = vit_backBll.vitbackMod(model,0,type,user.getUsername());

	
		
		
		if (row > 0 && viin_iffollow) {
			count = gdfollows.getRows().getChildren().size();
			for (int i = 0; i < count; i++) {
				VisitFollowModel vfm1 = new VisitFollowModel();
				Grid gd = (Grid) gdfollows.getCell(i, 0).getChildren().get(0);
				Date disposetime = ((Datebox) gd.getCell(3, 3).getChildren()
						.get(0)).getValue();
				vfm1.setVifo_content(((Textbox) gd.getCell(1, 1).getChildren()
						.get(1)).getValue());
				vfm1.setVifo_name(((Textbox) gd.getCell(2, 1).getChildren()
						.get(0)).getValue());
				vfm1.setVifo_dept_id(((VisitFollowModel) ((Combobox) gd
						.getCell(3, 1).getChildren().get(0)).getSelectedItem()
						.getValue()).getVifo_dept_id());
				vfm1.setVifo_disposetime(disposetime == null ? null : sdf
						.parse(sdf.format(disposetime)));
				vfm1.setVifo_disposecontent(((Textbox) gd.getCell(4, 1)
						.getChildren().get(0)).getValue());
				vfm1.setVire_viin_id(viin_id);
				vfm1.setVifo_order(Integer.parseInt(((Label)gd.getCell(1, 1).getChildren().get(0)).getValue()));
				vfm1.setVifo_addname(user.getUsername());
				row += vit_backBll.vitfollowAdd(vfm1);
			}
		}
		
		if (row == count + 1) {
			if (Messagebox.show("提交成功!", "INFORMATION", Messagebox.YES,
					Messagebox.INFORMATION) == Messagebox.YES) {
				win.detach();
			}
		} else if (row == 1) {
			Messagebox.show("跟进事项提交失败!", "ERROR", Messagebox.YES,
					Messagebox.ERROR);
		} else {
			Messagebox.show("提交失败!", "ERROR", Messagebox.YES, Messagebox.ERROR);
		}
	}

	public List<VisitFollowModel> getFollowList() {
		return followList;
	}

	public void setFollowList(List<VisitFollowModel> followList) {
		this.followList = followList;
	}

	public ListModelList<String> getAimList() {
		return aimList;
	}

	public void setAimList(ListModelList<String> aimList) {
		this.aimList = aimList;
	}

	public List<VisitFollowModel> getDeptList() {
		return deptList;
	}

	public void setDeptList(List<VisitFollowModel> deptList) {
		this.deptList = deptList;
	}

	public int getA() {
		return a;
	}

	public void setA(int a) {
		this.a = a;
	}

	public ListModelList<String> getCostList() {
		return costList;
	}

	public void setCostList(ListModelList<String> costList) {
		this.costList = costList;
	}

	public ListModelList<String> getLintmanList() {
		return lintmanList;
	}

	public void setLintmanList(ListModelList<String> lintmanList) {
		this.lintmanList = lintmanList;
	}

}
