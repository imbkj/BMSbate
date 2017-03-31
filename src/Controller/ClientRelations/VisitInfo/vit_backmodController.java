package Controller.ClientRelations.VisitInfo;

import impl.UserInfoImpl;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zkmax.zul.Chosenbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import service.UserInfoService;

import bll.ClientRelations.VisitInfo.vit_backBll;
import bll.ClientRelations.VisitInfo.vit_backmodBll;

import Model.VisitFollowModel;
import Model.VisitInfoModel;
import Util.plyUtil;

public class vit_backmodController extends SelectorComposer<Component> {

	private List<VisitFollowModel> followList = new ListModelList<VisitFollowModel>();
	private ListModelList<String> aimList = new ListModelList<String>();
	private List<VisitFollowModel> deptList = new ListModelList<VisitFollowModel>();
	private ListModelList<String> costList = new ListModelList<String>();
	private ListModelList<String> lintmanList = new ListModelList<String>();
	private int a;
	private int viin_tapr_id;
	private int viin_id;
	private int viin_state;

//	VisitInfoModel vim = (VisitInfoModel) Executions.getCurrent().getArg()
//			.get("vim");
	VisitInfoModel vim1 = new VisitInfoModel();
//	int viin_id = vim.getViin_id();
//	int viin_state = vim.getViin_state();

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
	
	public vit_backmodController()
	{
		try{
			
			viin_tapr_id=Integer.parseInt(Executions.getCurrent().getArg().get("id").toString());

			viin_id=Integer.parseInt(Executions.getCurrent().getArg().get("daid").toString());
			
			System.out.println(viin_id);
			
			vit_backmodBll vit_backmodB = new vit_backmodBll();
			viin_state=vit_backmodB.getvisitbackDetail(viin_id).getViin_state();
			

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

			//根据viin_id获取拜访计划详情
			vim1 = vit_backmodBll.getvisitbackDetail(viin_id);
			
			// 根据viin_id获取跟进事项
			setFollowList(vit_backmodBll.getvisitfollows(viin_id));
			a = followList.size();
			
			}
			catch(Exception e)
			{
//				System.out.println("000");
//				VisitInfoModel vim = (VisitInfoModel) Executions.getCurrent().getArg()
//						.get("vim");
//				 viin_id = vim.getViin_id();
//				//任务单节点ID
//				  viin_tapr_id =vim.getViin_tapr_id();
//				  
//				  viin_state = vim.getViin_state();
				  

				
			}
		
	}
	

	// 成本类型初始化
	@Command("costinit")
	public void costinit() {
		if (!vim1.getViin_cost().isEmpty()) {
			String coststr[] = vim1.getViin_cost().split(",");
			for (String str : coststr) {
				if (!costList.contains(str)) {
					costList.add(str);
				}
				costList.addToSelection(str);
			}
		}
	}

	// 被访人员初始化
	@Command("personedinit")
	public void personedinit() {
		if (!vim1.getViin_personed().isEmpty() && !vim1.getViin_personed().equals("null")) {
			String personed[] = vim1.getViin_personed().split(",");
			for (String str : personed) {
				if (!lintmanList.contains(str)) {
					lintmanList.add(str);
				}
				lintmanList.addToSelection(str);
			}
		}
	}

	// 主要目的初始化
	@Command("aiminit")
	public void aiminit() {
		if (!vim1.getViin_aim().isEmpty() && vim1.getViin_aim()!= null) {
			String aim[] = vim1.getViin_aim().split(";");
			for (String str : aim) {
				aimList.addToSelection(str+";");
			}
		}
	}
	
	// 新增被访人员
	@Command("newpersoned")
	public void newpersoned(@BindingParam("person") String person) {
		lintmanList.add(person);
		lintmanList.addToSelection(person);
	}

	@Command("iffollow")
	public void iffollow(@BindingParam("radio") Radio radio,
			@BindingParam("gbgj") Groupbox gbgj) {
		if (radio.getLabel().equals("是")) {
			gbgj.setVisible(true);
		} else {
			gbgj.setVisible(false);
		}
	}

	// 跟进事项动态生成
	@Command("followchange")
	@NotifyChange("followList")
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
	
	// 保存
	@Listen("onClick = #submit")
	public void submit() throws Exception {
		int count = 0;
		boolean viin_iffollow = false;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		VisitInfoModel model = new VisitInfoModel();
		if (iffollow.getSelectedItem().getLabel().equals("是")) {
			viin_iffollow = true;
		}
		if (viin_state != 2) {
			viin_state = 3;
		}
		
		model.setViin_id(viin_id);
		model.setViin_cost(ply.SetToString(cost.getSelectedObjects()));
		model.setViin_costremark(costremark.getValue());
		model.setViin_personed(ply.SetToString(personed.getSelectedObjects()));
		model.setViin_job(job.getValue());
		model.setViin_aim(ply.SetToString(aim.getSelectedObjects()).replace(
				";,", ";"));
		model.setViin_truetime(truetime.getValue() == null ? null : sdf
				.parse(sdf.format(truetime.getValue())));
		model.setViin_summary(summary.getValue());
		model.setViin_feedback(feedback.getValue());
		model.setViin_iffolow(viin_iffollow);
		model.setViin_state(viin_state);
		model.setViin_tapr_id(viin_tapr_id);
		model.setViin_addname(user.getUsername());

		int row = vit_backBll.vitbackMod(model,1,0,user.getUsername());

		if (row > 0 && viin_iffollow) {
			count = gdfollows.getRows().getChildren().size();
			row += vit_backmodBll.followsDel(viin_id);
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
				vfm1.setVifo_order(Integer.parseInt(((Label) gd.getCell(1, 1)
						.getChildren().get(0)).getValue()));
				vfm1.setVifo_addname(user.getUsername());
				row += vit_backBll.vitfollowAdd(vfm1);
			}
		}
		if (row >= count + 1) {
			if (Messagebox.show("保存成功,是否关闭窗口!", "INFORMATION", Messagebox.YES
					| Messagebox.NO, Messagebox.INFORMATION) == Messagebox.YES) {
				win.detach();
			}
		} else if (row == 1) {
			Messagebox.show("跟进事项保存失败!", "ERROR", Messagebox.YES,
					Messagebox.ERROR);
		} else {
			Messagebox.show("提交失败!", "ERROR", Messagebox.YES, Messagebox.ERROR);
		}
	}
	
	// 完结
	@Listen("onClick = #complete")
	public void complete() throws Exception {
		if (Messagebox.show("完结后无法修改此项计划,是否确认完结?", "CONFIRM", Messagebox.YES
				| Messagebox.NO | Messagebox.NO, Messagebox.QUESTION) == Messagebox.YES) {
			int count = 0;
			boolean viin_iffollow = false;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			VisitInfoModel model = new VisitInfoModel();
			model.setViin_id(viin_id);
			model.setViin_cost(ply.SetToString(cost.getSelectedObjects()));
			model.setViin_costremark(costremark.getValue());
			model.setViin_personed(ply.SetToString(personed
					.getSelectedObjects()));
			model.setViin_job(job.getValue());
			model.setViin_aim(ply.SetToString(aim.getSelectedObjects())
					.replace(";,", ";"));
			model.setViin_truetime(truetime.getValue() == null ? null : sdf
					.parse(sdf.format(truetime.getValue())));
			model.setViin_summary(summary.getValue());
			model.setViin_feedback(feedback.getValue());
			if (iffollow.getSelectedItem().getLabel().equals("是")) {
				viin_iffollow = true;
			}
			model.setViin_iffolow(viin_iffollow);
			model.setViin_state(5);
			model.setViin_tapr_id(viin_tapr_id);
			model.setViin_addname(user.getUsername());


			int row = vit_backBll.vitbackMod(model,0,1,user.getUsername());

			if (row > 0 && viin_iffollow) {
				count = gdfollows.getRows().getChildren().size();
				row += vit_backmodBll.followsDel(viin_id);
				for (int i = 0; i < count; i++) {
					VisitFollowModel vfm1 = new VisitFollowModel();
					Grid gd = (Grid) gdfollows.getCell(i, 0).getChildren()
							.get(0);
					Date disposetime = ((Datebox) gd.getCell(3, 3)
							.getChildren().get(0)).getValue();
					vfm1.setVifo_content(((Textbox) gd.getCell(1, 1)
							.getChildren().get(1)).getValue());
					vfm1.setVifo_name(((Textbox) gd.getCell(2, 1).getChildren()
							.get(0)).getValue());
					vfm1.setVifo_dept_id(((VisitFollowModel) ((Combobox) gd
							.getCell(3, 1).getChildren().get(0))
							.getSelectedItem().getValue()).getVifo_dept_id());
					vfm1.setVifo_disposetime(disposetime == null ? null : sdf
							.parse(sdf.format(disposetime)));
					vfm1.setVifo_disposecontent(((Textbox) gd.getCell(4, 1)
							.getChildren().get(0)).getValue());
					vfm1.setVire_viin_id(viin_id);
					vfm1.setVifo_order(Integer.parseInt(((Label) gd
							.getCell(1, 1).getChildren().get(0)).getValue()));
					vfm1.setVifo_addname(user.getUsername());
					row += vit_backBll.vitfollowAdd(vfm1);
				}
			}
			if (row >= count + 1) {
				if (Messagebox.show("提交成功!", "INFORMATION", Messagebox.YES,
						Messagebox.INFORMATION) == Messagebox.YES) {
					win.detach();
				}
			} else if (row == 1) {
				Messagebox.show("跟进事项保存失败!", "ERROR", Messagebox.YES,
						Messagebox.ERROR);
			} else {
				Messagebox.show("提交失败!", "ERROR", Messagebox.YES,
						Messagebox.ERROR);
			}
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

	public int getA() {
		return a;
	}

	public void setA(int a) {
		this.a = a;
	}

	public VisitInfoModel getVim1() {
		return vim1;
	}

	public void setVim1(VisitInfoModel vim1) {
		this.vim1 = vim1;
	}

}
