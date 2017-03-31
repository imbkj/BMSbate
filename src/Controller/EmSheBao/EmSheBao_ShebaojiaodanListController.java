package Controller.EmSheBao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import org.zkoss.zul.Messagebox.ClickEvent;

import dal.LoginDal;

import Model.EmShebaoChangeSZSIModel;
import Model.LoginModel;
import Util.UserInfo;
import bll.CoBase.CoBase_SelectBll;
import bll.EmSheBao.EmSheBao_DOperateBll;
import bll.EmSheBao.EmSheBao_DSelectBll;
import bll.SystemControl.PubRegUserBll;

public class EmSheBao_ShebaojiaodanListController {

	private EmSheBao_DSelectBll dsbll = new EmSheBao_DSelectBll();
	private EmSheBao_DOperateBll dobll = new EmSheBao_DOperateBll();
	private List<EmShebaoChangeSZSIModel> sbDataList;
	private PubRegUserBll prBll = new PubRegUserBll();
	private CoBase_SelectBll csbll = new CoBase_SelectBll();

	private String sql = Executions.getCurrent().getArg().get("sql").toString();
	private String top = Executions.getCurrent().getArg().get("top").toString();
	private String order = Executions.getCurrent().getArg().get("order")
			.toString();

	private int count = 0; // 多少条数据

	// 获取用户名
	String username = UserInfo.getUsername();
	String userid = UserInfo.getUserid();

	public EmSheBao_ShebaojiaodanListController() {
		// 获取页面数据
		sbDataList = dsbll.getEmSCSZSIData(top, sql, order);

		try {
			count = sbDataList.size();
		} catch (Exception e) {
			count = 0;
		}
	}

	// 返回
	@Command("pageback")
	public void pageback(@BindingParam("win") Window win) {
		win.detach();
	}

	// 表格每行checkbox全选
	@Command("gdallselect")
	public void gdallselect(@BindingParam("grid") Grid gd,
			@BindingParam("check") boolean check) {
		for (int i = 0; i < gd.getRows().getChildren().size(); i++) {
			try {
				// 判断是否可以选中
				Row row = (Row) gd.getRows().getChildren().get(i); // 获取Gird的行

				int escs_ifdeclare = ((EmShebaoChangeSZSIModel) row.getValue())
						.getEscs_ifdeclare();

				Checkbox ckb = (Checkbox) gd.getCell(i, 13).getChildren()
						.get(0);

				if (escs_ifdeclare == 0 || escs_ifdeclare == 2) {

					ckb.setChecked(check);
				} else {
					ckb.setChecked(false);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public List<EmShebaoChangeSZSIModel> getSbDataList() {
		return sbDataList;
	}

	public void setSbDataList(List<EmShebaoChangeSZSIModel> sbDataList) {
		this.sbDataList = sbDataList;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

}
