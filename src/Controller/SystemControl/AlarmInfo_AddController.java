package Controller.SystemControl;

import impl.UserInfoImpl;

import java.sql.SQLException;
import java.util.List;

import org.zkoss.bind.annotation.Init;
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
import org.zkoss.zul.Window;
import org.zkoss.zul.Messagebox.ClickEvent;

import service.UserInfoService;

import bll.SystemControl.AlarmClassBll;
import bll.SystemControl.AlarmInfoBll;
import Model.AlarmClassModel;
import Model.AlarmInfoModel;

public class AlarmInfo_AddController extends SelectorComposer<Component> {
	private List<AlarmClassModel> classList;

	private Integer id;
	private String name;
	private String content;
	private String url;
	private Integer warning;
	private String sql;

	AlarmClassBll bll = new AlarmClassBll();
	AlarmInfoBll aibll = new AlarmInfoBll();

	@Wire
	Combobox cbclassName;
	@Wire
	Textbox tbitemName;
	@Wire
	Combobox cbwarning;
	@Wire
	Textbox tburl;
	@Wire
	Textbox tbsql;
	@Wire
	Textbox tbcontent;

	@Init
	public void init() throws SQLException {
		this.classList = new ListModelList<AlarmClassModel>(
				bll.getAlarmClassName());

	}

	//新增项目信息
	@Listen("onClick = #btnSubmit")
	public void AddAlarmClass() {
		Session session = Executions.getCurrent().getDesktop().getSession();
		UserInfoService user = new UserInfoImpl(session);

		if (cbclassName.getSelectedIndex() >= 0
				&& !tbitemName.getValue().equals("")) {
			AlarmInfoBll bll = new AlarmInfoBll();

			id = Integer.valueOf(cbclassName.getSelectedItem().getValue()
					.toString());
			name = tbitemName.getValue();
			warning = cbwarning.getSelectedIndex() >= 0 ? Integer
					.valueOf(cbwarning.getSelectedItem().getValue().toString())
					: Integer.valueOf(0);
			content = tbcontent.getValue();
			sql = tbsql.getValue();
			url = tburl.getValue();

			String[] message = bll.addAlarmInfo(id, name, warning, url, sql,
					content, user.getUsername());

			if (message[0].equals("1")) {
				EventListener<ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
					public void onEvent(ClickEvent event) throws Exception {
						if (Messagebox.Button.OK.equals(event.getButton())) {
							Executions.sendRedirect("AlarmClass_Manager.zul"); // 跳转页面
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

	public void setClassList(List<AlarmClassModel> classList) {
		this.classList = classList;
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

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

}
