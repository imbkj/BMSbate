package Controller.EmSheBao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import dal.LoginDal;

import bll.EmSheBao.Emsi_SelBll;
import bll.SocialInsurance.Algorithm_InfoBll;

import Model.EmSheBaoChangeModel;
import Model.EmShebaoUpdateModel;
import Model.LoginModel;

public class Emsi_IndexController {
	private final int gid = Integer.parseInt(Executions.getCurrent().getArg()
			.get("gid").toString());

	private EmShebaoUpdateModel upModel;
	private List<EmSheBaoChangeModel> changeList;
	private boolean existsShebao = true;

	private String computerid = "没有";
	private Emsi_SelBll bll = new Emsi_SelBll();

	private String tips1 = "";
	private String tips2 = "";
	private boolean tipvis = false;

	public Emsi_IndexController() {
		select();
	}

	public void select() {
		try {
			// Emsi_SelBll bll = new Emsi_SelBll();
			// String computerid = "没有";
			upModel = bll.getShebaoUpdateByGid2(gid);
			if (upModel == null) {
				upModel = bll.getShebaoByGid(gid);
				if (upModel == null) {
					existsShebao = false;
				} else {
					// 提示最近一个月参保月份
					tips1 = "该员工社会保险已终止，截止最近一个月社保所属期为"
							+ String.valueOf(upModel.getOwnmonth());
					tipvis = true;
				}
			} else {
				// 提示被调走、当月停保情况
				if (upModel.getEsiu_ifstop() == 2
						|| upModel.getEsiu_ifstop() == 3) {
					tips2 = "该员工当月社会保险服务无效，造成原因可能是被调走或者调入、新增请求被退回，而尚未作出操作！";
					tipvis = true;
				}
			}
			if (upModel != null && upModel.getEsiu_computerid() != null) {
				computerid = upModel.getEsiu_computerid();
			}
			changeList = bll.getAllChangListByGid(gid, computerid);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 小信封
	@Command("msg")
	@NotifyChange("sbDataList")
	public void msg(@BindingParam("id") int id,
			@BindingParam("addname") String addname,
			@BindingParam("type") int type) {
		LoginDal d = new LoginDal();

		if (type != 0) {
			String tablename = "";
			switch (type) {
			case 1:
				tablename = "EmShebaoChange";
				break;
			case 2:
				tablename = "EmShebaoChangeSZSI";
				break;
			case 3:
				tablename = "EmShebaoChangeForeigner";
				break;
			case 4:
				tablename = "EmShebaoBJ";
				break;
			case 5:
				tablename = "EmShebaoBJJL";
				break;
			case 6:
				tablename = "EmShebaoChangeMA";
				break;
			}

			Map map = new HashMap<>();
			map.put("type", "社会保险");// 业务类型:来自WfClass的wfcl_name
			map.put("id", id);// 业务表id
			map.put("tablename", tablename);// 业务表名
			List<LoginModel> mlist = new ArrayList<LoginModel>();
			LoginModel m = new LoginModel();
			m.setLog_name(addname);
			m.setLog_id(d.getUserIDByname(addname));
			// 收件人姓名和收件人id至少要填一个
			mlist.add(m);
			map.put("list", mlist);// 默认收件人list

			Window window = (Window) Executions.createComponents(
					"../SysMessage/Message_Add.zul", null, map);
			window.doModal();
			// 刷新
			changeList = bll.getAllChangListByGid(gid, computerid);
		} else {
			// 弹出提示
			Messagebox.show("小信封出错，请联系IT部！", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
		}

	}

	// 重新计算社保在册数据
	@Command("countShebao")
	@NotifyChange({ "upModel", "existsShebao", "tips1", "tipvis", "computerid",
			"changeList" })
	public void countShebao() {
		Algorithm_InfoBll aiBll = new Algorithm_InfoBll();
		aiBll.upLocalShebaoUpdate(0, gid);
		// 刷新
		select();
		// 弹出提示
		Messagebox.show("ok！", "操作提示", Messagebox.OK, Messagebox.INFORMATION);
	}

	@Command("allShebao")
	public void allShebao() {
		Map map = new HashMap<>();
		map.put("gid", gid);
		Window window = (Window) Executions.createComponents(
				"../EmSheBao/Emsi_AllRecord.zul", null, map);
		window.doModal();
	}

	@Command("allShebaoChange")
	public void allShebaoChange() {
		Map map = new HashMap<>();
		map.put("gid", gid);
		Window window = (Window) Executions.createComponents(
				"../EmSheBao/Emsi_AllRecordChange.zul", null, map);
		window.doModal();
	}

	@Command("zyt")
	public void zyt() {
		Map map = new HashMap<>();
		map.put("gid", gid);
		Window window = (Window) Executions.createComponents(
				"../EMZYT/EmZYT_DetailAllInOne.zul", null, map);
		window.doModal();
	}

	public EmShebaoUpdateModel getUpModel() {
		return upModel;
	}

	public boolean isExistsShebao() {
		return existsShebao;
	}

	public List<EmSheBaoChangeModel> getChangeList() {
		return changeList;
	}

	public String getTips1() {
		return tips1;
	}

	public void setTips1(String tips1) {
		this.tips1 = tips1;
	}

	public String getTips2() {
		return tips2;
	}

	public void setTips2(String tips2) {
		this.tips2 = tips2;
	}

	public boolean isTipvis() {
		return tipvis;
	}

	public void setTipvis(boolean tipvis) {
		this.tipvis = tipvis;
	}

}
