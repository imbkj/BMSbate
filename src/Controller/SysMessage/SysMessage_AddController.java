package Controller.SysMessage;

import impl.MessageImpl;
import impl.UserInfoImpl;

import java.util.List;
import java.util.Set;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import service.MessageService;
import service.UserInfoService;

import bll.SysMessage.SysMessageTem_AddBll;
import bll.SysMessage.SysMessageTem_ManageBll;
import bll.SysMessage.SysMessage_AddBll;

import Model.DepartmentListModel;
import Model.LoginModel;
import Model.SysMessageModel;
import Model.WfClassModel;

public class SysMessage_AddController {

	private List<LoginModel> loginList = new ListModelList<LoginModel>();
	private List<LoginModel> selectloginliList = new ListModelList<LoginModel>();
	private List<DepartmentListModel> deptList = new ListModelList<DepartmentListModel>();

	private DepartmentListModel depModel = new DepartmentListModel();

	private ListModelList<SysMessageModel> temList = new ListModelList<SysMessageModel>();
	private SysMessageModel temModel = new SysMessageModel();
	private List<WfClassModel> classList = new ListModelList<WfClassModel>();

	private String username;
	private String content = "";
	private String name = "";
	int userid = 0;
	private String title = "";
	private SysMessageTem_ManageBll tbll = new SysMessageTem_ManageBll();
	// 获取session，实例化UserInfoService接口
	Session session = Executions.getCurrent().getDesktop().getSession();
	UserInfoService user = new UserInfoImpl(session);

	// 初始化
	public SysMessage_AddController() throws Exception {
		SysMessage_AddBll bll = new SysMessage_AddBll();
		
		userid = user.getUserid().equals("err") ? 0 : Integer.parseInt(user
				.getUserid());
		setLoginList(bll.getLoginList(" and log_id<>" + userid));
		setDeptList(bll.getDeptList());
		setUsername(user.getUsername());
		setTemList(tbll.gettemList(""));
		temList.add(0, new SysMessageModel());
		setClassList(new SysMessageTem_AddBll().getwfclassList());
	}

	// 接收人单个添加
	@Command("addsingleselect")
	@NotifyChange({ "selectloginliList", "loginList" })
	public void addsingleselect(@BindingParam("selectitem") LoginModel item) {
		if (!selectloginliList.contains(item)) {
			selectloginliList.add(item);
			loginList.remove(item);
		}
	}

	// 接收人单个移除
	@Command("removesingleselect")
	@NotifyChange({ "selectloginliList", "loginList" })
	public void removesingleselect(@BindingParam("selectitem") LoginModel item) {
		loginList.add(item);
		selectloginliList.remove(item);
	}

	// 接收人多个添加
	@Command("addselect")
	@NotifyChange({ "selectloginliList", "loginList" })
	public void addselect(@BindingParam("selectitems") Set<Listitem> items) {
		if (items.size() > 0) {
			for (Listitem item : items) {
				if (!selectloginliList.contains((LoginModel) item.getValue())) {
					selectloginliList.add((LoginModel) item.getValue());
					loginList.remove((LoginModel) item.getValue());
				}
			}
		} else {
			Messagebox.show("请至少选择一人!", "ERROR", Messagebox.OK,
					Messagebox.ERROR);
		}
	}

	// 接收人多个移除
	@Command("removeselect")
	@NotifyChange({ "selectloginliList", "loginList" })
	public void removeselect(@BindingParam("selectitems") Set<Listitem> items) {
		if (items.size() > 0) {
			List<LoginModel> temList = new ListModelList<LoginModel>();
			for (Listitem item : items) {
				loginList.add((LoginModel) item.getValue());
				temList.add((LoginModel) item.getValue());
			}
			selectloginliList.removeAll(temList);
		} else {
			Messagebox.show("请至少选择一人!", "ERROR", Messagebox.OK,
					Messagebox.ERROR);
		}
	}

	// 接收人全部添加
	@Command("allselect")
	@NotifyChange({ "selectloginliList", "loginList" })
	public void allselect() {
		if (loginList.size() > 0) {
			selectloginliList.addAll(loginList);
			loginList.clear();
		}
	}

	// 接收人全部移除
	@Command("allremove")
	@NotifyChange({ "selectloginliList", "loginList" })
	public void allremove() {
		if (selectloginliList.size() > 0) {
			loginList.addAll(selectloginliList);
			selectloginliList.clear();
		}
	}

	// 选择模板
	@Command("temselect")
	@NotifyChange({ "content", "title" })
	public void temselect(@BindingParam("cbbClass") Combobox cbbClass) {
		content = "";
		try {
			content = temModel.getPmte_content();
		} catch (Exception e) {
			content = "";
		}
	}

	// 检索
	@Command("search")
	@NotifyChange("loginList")
	public void search() {
		String str = " and log_id<>" + userid;
		String str1 = "";
		if (selectloginliList.size() > 0) {
			for (int i = 0; i < selectloginliList.size(); i++) {
				if (i == selectloginliList.size() - 1) {
					str1 += selectloginliList.get(i).getLog_id();
				} else {
					str1 += selectloginliList.get(i).getLog_id() + ",";
				}
			}
			str += " and log_id not in(" + str1 + ")";
		}
		if (depModel.getDep_id() != 0) {
			str += " and b.dep_id=" + depModel.getDep_id() + "";
		}
		if (!name.isEmpty()) {
			str += " and log_name like '%" + name + "%'";
		}
		SysMessage_AddBll bll = new SysMessage_AddBll();
		setLoginList(bll.getLoginList(str));
	}

	@Command("initltb")
	public void initltb(@BindingParam("ltb") Listbox ltb) {
		ltb.setMultiple(true);
	}

	// 发送
	@Command("send")
	public void send(@BindingParam("win") Window win,@BindingParam("cb") Checkbox cb) throws Exception {
		if (selectloginliList.size() <= 0) {
			Messagebox.show("至少选择一个接收人!", "ERROR", Messagebox.OK,
					Messagebox.ERROR);
		} else if (content.isEmpty()) {
			Messagebox
					.show("内容不能为空!", "ERROR", Messagebox.OK, Messagebox.ERROR);
		} else {
			Integer email=0;
			if(cb.isChecked())
			{
				email=1;
			}
			int issuccess = submit(1,email);
			if (issuccess == 1) {
				if (Messagebox.show("发送成功!", "INFORMATION", Messagebox.YES,
						Messagebox.INFORMATION) == Messagebox.YES) {
					win.detach();
				}
			} else {
				Messagebox.show("发送失败,请联系IT部门!", "ERROR", Messagebox.YES,
						Messagebox.ERROR);
			}
		}
	}

	// 暂存
	@Command("save")
	public void save(@BindingParam("win") Window win) throws Exception {
		if (content.isEmpty()) {
			Messagebox
					.show("内容不能为空!", "ERROR", Messagebox.OK, Messagebox.ERROR);
		} else {

			int issuccess = submit(0,0);
			if (issuccess == 1) {
				if (Messagebox.show("保存成功!", "INFORMATION", Messagebox.YES,
						Messagebox.INFORMATION) == Messagebox.YES) {
					//win.detach();
				}
			} else {
				Messagebox.show("保存失败,请联系IT部门!", "ERROR", Messagebox.YES,
						Messagebox.ERROR);
			}
		}
	}

	public int submit(int state,Integer email) throws Exception {
		int issuccess = 0;
		SysMessage_AddBll bll = new SysMessage_AddBll();
		SysMessageModel model = new SysMessageModel();
		List<SysMessageModel> list = new ListModelList<SysMessageModel>();
		// 传参数
		model.setSyme_content(content);
		model.setSyme_title(title);
		model.setSyme_state(state);
		model.setEmail(email);
		model.setEmailtitle(title);
		MessageService msgservice=new MessageImpl();
		for (LoginModel lmModel : selectloginliList) {
			//SysMessageModel model1 = new SysMessageModel();
			model.setSymr_log_id(lmModel.getLog_id());
			model.setSymr_name(lmModel.getLog_name());
			model.setSymr_state(1);
			list.add(model);
			String[] str=msgservice.Add(model);
			if(str[0]=="1")
			{
				issuccess=1;
			}
		}
		//issuccess = bll.SysMessageAdd(model, list);
		 
		return issuccess;
	}
	
	//模板类型选择事件
	@Command
	@NotifyChange("temList")
	public void typeselect()
	{
		if(title!=null&&!title.equals(""))
		{
			if(!temList.isEmpty())
			temList.clear();
			temList.add(new SysMessageModel());
			temList.addAll(tbll.gettemList(" and pmte_class='"+title+"'"));
		}
	}

	public List<LoginModel> getLoginList() {
		return loginList;
	}

	public void setLoginList(List<LoginModel> loginList) {
		this.loginList = loginList;
	}

	public List<LoginModel> getSelectloginliList() {
		return selectloginliList;
	}

	public void setSelectloginliList(List<LoginModel> selectloginliList) {
		this.selectloginliList = selectloginliList;
	}

	public List<DepartmentListModel> getDeptList() {
		return deptList;
	}

	public void setDeptList(List<DepartmentListModel> deptList) {
		this.deptList = deptList;
	}

	public DepartmentListModel getDepModel() {
		return depModel;
	}

	public void setDepModel(DepartmentListModel depModel) {
		this.depModel = depModel;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ListModelList<SysMessageModel> getTemList() {
		return temList;
	}

	public void setTemList(ListModelList<SysMessageModel> temList) {
		this.temList = temList;
	}

	public SysMessageModel getTemModel() {
		return temModel;
	}

	public void setTemModel(SysMessageModel temModel) {
		this.temModel = temModel;
	}

	public List<WfClassModel> getClassList() {
		return classList;
	}

	public void setClassList(List<WfClassModel> classList) {
		this.classList = classList;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
