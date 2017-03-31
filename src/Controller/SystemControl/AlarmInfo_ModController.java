package Controller.SystemControl;

import impl.UserInfoImpl;

import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Messagebox.ClickEvent;
import org.zkoss.zul.Window;

import service.UserInfoService;
import Model.AlarmClassModel;
import Model.AlarmInfoModel;
import bll.SystemControl.AlarmClassBll;
import bll.SystemControl.AlarmInfoBll;

public class AlarmInfo_ModController extends SelectorComposer<Component> {
	private List<AlarmClassModel> classList;
	private List<AlarmInfoModel> aiList;

	//获取项目ID
	private final Integer alin_id = Integer.parseInt(Executions.getCurrent()
			.getArg().get("id").toString());

	private Integer id;
	private String name;
	private String content;
	private String url;
	private Integer warning;
	private Integer state;
	private String sql;

	AlarmClassBll acBll = new AlarmClassBll();
	AlarmInfoBll aiBll = new AlarmInfoBll();

	@Wire
	Combobox cbclassName;
	@Wire
	Textbox tbitemName;
	@Wire
	Combobox cbwarning;
	@Wire
	Combobox cbState;
	@Wire
	Textbox tburl;
	@Wire
	Textbox tbsql;
	@Wire
	Textbox tbcontent;
	@Wire
	Window winInfoMod;

	//初始化
	public AlarmInfo_ModController(){
		this.classList = new ListModelList<AlarmClassModel>(acBll.getAlarmClassName());
		this.aiList = new ListModelList<AlarmInfoModel>(aiBll.getAlarmInfoListById(alin_id));
	}

	//修改项目信息
	@Listen("onClick = #btnSubmit")
	public void AddAlarmClass() {
		Session session = Executions.getCurrent().getDesktop().getSession();
		UserInfoService user = new UserInfoImpl(session);

		if (cbclassName.getSelectedIndex() >= 0
				&& !tbitemName.getValue().equals("")) {

			id = Integer.valueOf(cbclassName.getSelectedItem().getValue()
					.toString());
			name = tbitemName.getValue();
			warning = cbwarning.getSelectedIndex() >= 0 ? Integer
					.valueOf(cbwarning.getSelectedItem().getValue().toString())
					: Integer.valueOf(0);
			state = cbState.getSelectedIndex() >= 0 ? Integer.valueOf(cbState
					.getSelectedItem().getValue().toString()) : Integer
					.valueOf(0);
			content = tbcontent.getValue();
			sql = tbsql.getValue();
			url = tburl.getValue();

			String[] message = aiBll.modAlarmInfo(alin_id, id, name, warning,
					url, sql, content, state, user.getUsername());

			if (message[0].equals("1")) {
				EventListener<ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
					public void onEvent(ClickEvent event) throws Exception {
						if (Messagebox.Button.OK.equals(event.getButton())) {
							winInfoMod.detach();
						}
					}
				};
				// 弹出提示
				Messagebox.show(message[1], "操作提示",
						new Messagebox.Button[] { Messagebox.Button.OK },
						Messagebox.INFORMATION, clickListener);
			} else {
				// 弹出提示
				Messagebox.show(message[1], "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}
		} else {
			if (cbclassName.getSelectedIndex() <0) {
				Messagebox.show("请选择预警类型", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			} else if (tbitemName.getValue().equals("")) {
				Messagebox.show("请输入预警项目名称", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}
		}

	}

	public List<AlarmClassModel> getClassList() {
		return classList;
	}

	public void setClassList() {
		this.classList = acBll.getAlarmClassName();
	}

	public List<AlarmInfoModel> getAiList() {
		return aiList;
	}

	public void setAiList() {
		this.aiList = aiBll.getAlarmInfoListById(alin_id);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getWarning() {
		return warning;
	}

	public void setWarning(Integer warning) {
		this.warning = warning;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public Integer getAlin_id() {
		return alin_id;
	}
	
}
