package Controller.Archives;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import Model.EmArchiveDatumModel;
import Util.UserInfo;
import bll.Archives.EmArchiveDatum_OperateBll;
import bll.Archives.EmArchive_SelectBll;

public class Archive_FileConsultFeeController {
	private String id = (String) Executions.getCurrent().getArg().get("daid");
	private String tarpid = null;
	private EmArchive_SelectBll bll = new EmArchive_SelectBll();
	private List<EmArchiveDatumModel> list = bll
			.getEmArchiveDatumInfo(" and eada_id=" + id);
	private EmArchiveDatumModel model = new EmArchiveDatumModel();
	private String username = UserInfo.getUsername();

	public Archive_FileConsultFeeController() {
		if(Executions.getCurrent().getArg().get("id")!=null)
		{
			tarpid= Executions.getCurrent().getArg().get("id").toString();
		}
		if (list.size() > 0) {
			model = list.get(0);
		}
	}

	@Command
	public void summitfee(@BindingParam("win") Window win,
			@BindingParam("ftime") Date ftime) {
		if (ftime != null) {
			model.setEada_arrearageinfo("");
			String sql = ",eada_paydate='" + getStringDate(ftime)
					+ "',eada_finaname='" + username + "'";
			EmArchiveDatum_OperateBll bll = new EmArchiveDatum_OperateBll();
			// 新增业务数据，并返回业务表ID
			model.setEada_type("财务结算费用");
			String[] str = bll.Accepted(model, sql);
			// 判断业务id是否为空
			if (str[0].equals("1")) {
				// 调用内联页方法submitDoc(Grid gd)
				Messagebox.show("提交成功", "操作提示", Messagebox.OK,
						Messagebox.INFORMATION);
				win.detach();
			} else {
				Messagebox
						.show("提交失败", "操作提示", Messagebox.OK, Messagebox.ERROR);
			}
		} else {
			Messagebox.show("请选择费用结算时间", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
		}
	}

	// 退回
	@Command
	public void back(@BindingParam("win") final Window win) {
		Map map = new HashMap<>();
		map.put("id", id);
		map.put("ta", tarpid);
		map.put("model", model);
		map.put("flag", "0");
		Window window = (Window) Executions.createComponents(
				"../Archives/Archive_backtopre.zul", null, map);
		window.doModal();
		if (map.get("flag") == "1") {
			win.detach();
		}
	}

	// 弹出个人收款页面
	@Command
	public void emfinace(@BindingParam("win") final Window win) {
		Map map = new HashMap<>();
		map.put("gid", model.getGid());
		Window window = (Window) Executions.createComponents(
				"../EmFinanceManage/Emgt_EmFinaceList.zul", null, map);
		window.doModal();
	}

	// 弹出备注
	@Command
	public void addremark(@BindingParam("win") final Window win) {
		Map map = new HashMap<>();
		map.put("id", id.toString());
		map.put("typeid", "2");
		map.put("gid",model.getGid());
		Window window = (Window) Executions.createComponents(
				"../Archives/Remark_AddList.zul", null, map);
		window.doModal();
	}

	public EmArchiveDatumModel getModel() {
		return model;
	}

	public void setModel(EmArchiveDatumModel model) {
		this.model = model;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public static String getStringDate(Date currentTime) {
		String dateString = "";
		if (currentTime != null) {
			SimpleDateFormat formatter = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			dateString = formatter.format(currentTime);
		}
		return dateString;
	}

}
