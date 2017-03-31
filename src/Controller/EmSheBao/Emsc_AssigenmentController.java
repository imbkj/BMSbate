package Controller.EmSheBao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zul.Messagebox;

import bll.EmSheBao.Emsc_DeclareOperateBll;
import bll.EmSheBao.Emsc_DeclareSelBll;

public class Emsc_AssigenmentController {
	private List<String[]> asList;
	private List<String> flList;
	private Map<String, String> upMap;
	private Emsc_DeclareSelBll bll;

	public Emsc_AssigenmentController() {
		bll = new Emsc_DeclareSelBll();
		asList = bll.getAssigenment();
		flList = bll.getGyflbUser();
		upMap = new HashMap<String, String>();
	}

	@Command("change")
	public void change(@BindingParam("client") String client,
			@BindingParam("shebaodeclare") String shebaodeclare) {
		upMap.remove(client);
		upMap.put(client, shebaodeclare);
	}

	@Command("submit")
	@NotifyChange("asList")
	public void submit() {
		try {
			if (upMap.size() > 0) {
				Emsc_DeclareOperateBll opbll = new Emsc_DeclareOperateBll();
				int i = opbll.Assignment(upMap);
				if (i > 0) {
					Messagebox.show("数据提交成功。", "操作提示", Messagebox.OK,
							Messagebox.NONE);
					asList = bll.getAssigenment();
				} else {
					Messagebox.show("数据提交失败。", "操作提示", Messagebox.OK,
							Messagebox.ERROR);
				}
			} else {
				Messagebox.show("您并未修改任何内容，无需提交。", "操作提示", Messagebox.OK,
						Messagebox.NONE);
			}
		} catch (Exception e) {
			Messagebox.show("更新数据出错。", "操作提示", Messagebox.OK, Messagebox.ERROR);
		} finally {
			upMap.clear();
		}
	}

	public List<String[]> getAsList() {
		return asList;
	}

	public List<String> getFlList() {
		return flList;
	}

}
