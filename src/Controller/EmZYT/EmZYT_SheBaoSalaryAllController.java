package Controller.EmZYT;

import impl.CheckStringImpl;

import java.util.List;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;
import org.zkoss.zul.Messagebox.ClickEvent;

import service.CheckStringService;

import Model.EmShebaoUpdateModel;
import Model.EmZYTModel;
import Util.UserInfo;
import bll.EmSheBao.Emsi_SelBll;
import bll.EmZYT.EmZYT_OperateBll;
import bll.EmZYT.EmZYT_SelectBll;

public class EmZYT_SheBaoSalaryAllController {
	private EmZYT_SelectBll sbll = new EmZYT_SelectBll();
	private Emsi_SelBll bll = new Emsi_SelBll();
	private EmZYT_OperateBll oBll = new EmZYT_OperateBll();
	private String[] ownmonthList;
	private boolean ifStop;
	private String ownmonth;

	private List<EmZYTModel> dataList;

	private String emzt_id = Executions.getCurrent().getArg().get("emzt_id")
			.toString();
	private String sql = "";

	public EmZYT_SheBaoSalaryAllController() {
		sql = " AND id in(" + emzt_id + ")";
		dataList = sbll.getEmZYTList(sql);

		ifStop = bll.ifStop();
		// 判断是否停止当月操作社保
		if (ifStop) {
			ownmonthList = bll.getOwnmonthByNow(false);
		} else {
			ownmonthList = bll.getOwnmonthByNow(true);
		}

	}

	// 提交
	@Command("salaryUp")
	public void salaryUp(@BindingParam("win") final Window win) {
		if (!"".equals(ownmonth) && ownmonth != null && dataList.size() > 0) {
			String[] message;
			int j = 0;
			CheckStringService ch = new CheckStringImpl();

			for (int i = 0; i < dataList.size(); i++) {

				EmZYTModel m = dataList.get(i);
				m.setEmzt_declarename(UserInfo.getUsername());
				m.setEmzt_state(1);

				if (bll.getShebaoUpdateByGid(m.getGid()) != null) {

					// 将接口数据插入社保model
					EmShebaoUpdateModel sbModel = bll.getShebaoUpdateByGid(m
							.getGid());
					sbModel.setOwnmonth(Integer.parseInt(ownmonth));
					sbModel.setEsiu_remark(m.getEmzt_remark());
					sbModel.setEsiu_addname(UserInfo.getUsername());
					sbModel.setIfdeclare(0);

					if (ch.isNum(m.getEmzt_ylradix())) {// 检查是否为小数
						String[] result = checkPage(sbModel,
								Integer.parseInt(m.getEmzt_ylradix()));
						if ("1".equals(result[0])) {// 检查数据准确性
							sbModel.setEsiu_radix(Integer.parseInt(m
									.getEmzt_ylradix()));
							// 调用方法更新数据
							message = oBll.upEmZYTSheBaoSalary(m, sbModel);
							if ("1".equals(message[0])) {
								j = j + 1;
							}
						} else {
							dataList.get(i).setEmzt_err(result[1]);
							BindUtils.postNotifyChange(null, null, dataList.get(i), "emzt_err");
						}
					} else {
						dataList.get(i).setEmzt_err("基数不能存在小数位");
						BindUtils.postNotifyChange(null, null, dataList.get(i), "emzt_err");
					}
				}
			}

			String msg;
			if (j == dataList.size()) {
				msg = "操作成功！";
			} else {
				msg = "部分数据操作不成功，请检查！";
			}
		
			if (j == dataList.size()) {
				win.detach();
			}

			// 弹出提示
			Messagebox.show(msg, "操作提示",
					new Messagebox.Button[] { Messagebox.Button.OK },
					Messagebox.INFORMATION, null);
			/*EventListener<ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
				public void onEvent(ClickEvent event) throws Exception {
					if (Messagebox.Button.OK.equals(event.getButton())) {
							win.detach();
					}
				}
			};
			// 弹出提示
			Messagebox.show(msg, "操作提示",
					new Messagebox.Button[] { Messagebox.Button.OK },
					Messagebox.INFORMATION, clickListener);*/
		} else {
			if ("".equals(ownmonth) || ownmonth == null) {
				// 弹出提示
				Messagebox.show("请选择所属月份！", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			} else if (dataList.size() < 1) {
				// 弹出提示
				Messagebox.show("请选择数据！", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}
		}
	}

	// 检测表单
	private String[] checkPage(EmShebaoUpdateModel sbModel, Integer nRadix) {
		String[] result = new String[2];
		if (ownmonth == null) {
			result[0] = "0";
			result[1] = "所属月份不能为空";
		} else if (sbModel.getEsiu_computerid() == null
				|| "".equals(sbModel.getEsiu_computerid())) {
			result[0] = "0";
			result[1] = "电脑号不能为空";
		} else if (nRadix == sbModel.getEsiu_radix()) {
			result[0] = "0";
			result[1] = "基数与在册表数据一致";
		} else if (nRadix == 0) {
			result[0] = "0";
			result[1] = "基数不能为0";
		} else if (nRadix > 99999) {
			result[0] = "0";
			result[1] = "基数不能大于99999";
		} else {// 通过
			result[0] = "1";
		}
		return result;
	}

	public String[] getOwnmonthList() {
		return ownmonthList;
	}

	public boolean isIfStop() {
		return ifStop;
	}

	public String getOwnmonth() {
		return ownmonth;
	}

	public void setOwnmonth(String ownmonth) {
		this.ownmonth = ownmonth;
	}

	public List<EmZYTModel> getDataList() {
		return dataList;
	}

	public void setDataList(List<EmZYTModel> dataList) {
		this.dataList = dataList;
	}

}
