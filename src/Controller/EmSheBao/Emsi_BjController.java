package Controller.EmSheBao;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Window;
import Model.EmShebaoBJModel;
import Model.EmShebaoUpdateModel;
import Util.UserInfo;
import bll.EmSheBao.Emsi_OperateBll;
import bll.EmSheBao.Emsi_SelBll;

public class Emsi_BjController {
	private final int gid = Integer.parseInt(Executions.getCurrent().getArg()
			.get("gid").toString());
	private Emsi_SelBll bll = new Emsi_SelBll();
	private String[] ownmonthList;
	private String[] bjmonthList;
	private boolean ifStop;
	private EmShebaoUpdateModel sbModel;
	// 页面获取值
	private String ownmonth;
	private String startmonth;
	private String stopmonth;
	private String feemonth;
	private int radix;

	// 判断是否存在社保信息
	private boolean existsShebao = false;
	private String existsMessage;

	private boolean visJL=false;
	public Emsi_BjController() {
		sbModel = bll.getShebaoUpdateByGid(gid);
		// 检测是否存在社保信息
		if (sbModel == null) {
			existsShebao = true;
			existsMessage = "未找到该员工社保信息，不能操作补缴!";
			return;
		} else if (!bll.existsCoOfferList(gid)) {
			existsShebao = true;
			existsMessage = "该员工未分配“社会保险服务”的报价单项目，请分配该项目后再操作此步骤！";
			return;
		}
		ifStop = bll.ifStop();
		// 判断是否停止当月操作社保
		if (ifStop) {
			ownmonthList = bll.getOwnmonthByNow(false);
			bjmonthList = bll.getLastOwnmonthByNow1(true);
		} else {
			ownmonthList = bll.getOwnmonthByNow(true);
			bjmonthList = bll.getLastOwnmonthByNow1(false);
		}
	}

	// 提交
	@Command("bj")
	public void bj(@BindingParam("win") Window win,
			@BindingParam("rg") Radiogroup rg, @BindingParam("gd") Grid gd) {
		try {
			if (checkPage()) {
				String jlstr="";
				EmShebaoBJModel m = new EmShebaoBJModel();
				m.setGid(gid);
				m.setEmsb_name(sbModel.getEsiu_name());
				m.setOwnmonth(Integer.parseInt(ownmonth));
				m.setEmsb_radix(radix);
				m.setEmsb_computerid(sbModel.getEsiu_computerid());
				m.setEmsb_hj(sbModel.getEsiu_hj());
				m.setEmsb_startmonth(Integer.parseInt(startmonth));
				m.setEmsb_stopmonth(Integer.parseInt(startmonth));
				m.setEmsb_feeownmonth(Integer.parseInt(feemonth));
				m.setEmsb_addname(UserInfo.getUsername());
				// m.setEmsb_ifdeclare(Integer.parseInt(rg.getSelectedItem().getValue().toString()));
				m.setEmsb_ifdeclare(4);
				m.setSoin_title(sbModel.getEmsf_soin_title());
				// 调用补缴方法
				Emsi_OperateBll opbll = new Emsi_OperateBll();
				String[] message = opbll.addbj(m);
				if ("1".equals(message[0])) {
					
					// 插入医疗补交数据
					if (visJL==true) {
						m.setEmsb_yltype(sbModel.getEsiu_yltype());
						String[] msg2 = opbll.addJLbj(m);
						if (!msg2[0].equals("1")) {
							jlstr="，但是医疗补交添加不成功。";
						}
					}
					
					// 弹出提示
					Messagebox.show(message[1]+jlstr, "操作提示", Messagebox.OK,
							Messagebox.NONE);
					win.detach();
				} else {
					// 弹出提示
					Messagebox.show(message[1], "操作提示", Messagebox.OK,
							Messagebox.ERROR);
				}
			}
		} catch (Exception e) {
			Messagebox.show("新增补缴出错。", "操作提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	// 检测表单
	private boolean checkPage() {
		boolean b = true;
		if (ownmonth == null) {
			b = false;
			Messagebox.show("请选择所属月份!", "操作提示", Messagebox.OK, Messagebox.NONE);
		} else if (startmonth == null) {
			b = false;
			Messagebox.show("请输入补缴月份!", "操作提示", Messagebox.OK, Messagebox.NONE);
		} else if (radix == 0) {
			b = false;
			Messagebox.show("请输入补缴基数!", "操作提示", Messagebox.OK, Messagebox.NONE);
		} else if (radix > 99999) {
			b = false;
			Messagebox.show("月工资总额不能大于99999元,如需申报十万元以上基数,请联系福利组!", "操作提示",
					Messagebox.OK, Messagebox.NONE);
		} else if (sbModel.getEsiu_hj() == null
				|| "".equals(sbModel.getEsiu_hj())) {
			b = false;
			Messagebox.show("户籍不能为空!", "操作提示", Messagebox.OK, Messagebox.NONE);
		} else if (feemonth == null) {
			b = false;
			Messagebox.show("请选择收费月份!", "操作提示", Messagebox.OK, Messagebox.NONE);
		}
		return b;
	}
	
	@Command("visJlLabel")
	@NotifyChange("visJL")
	public void visJlLabel(@BindingParam("chk") Checkbox chk){
		if (chk.isChecked()) {
			visJL=true;
		}
		else {
			visJL=false;
		}
	}

	public int getGid() {
		return gid;
	}

	public String[] getOwnmonthList() {
		return ownmonthList;
	}

	public String getOwnmonth() {
		return ownmonth;
	}

	public void setOwnmonth(String ownmonth) {
		this.ownmonth = ownmonth;
	}

	public boolean isIfStop() {
		return ifStop;
	}

	public EmShebaoUpdateModel getSbModel() {
		return sbModel;
	}

	public String getStartmonth() {
		return startmonth;
	}

	public void setStartmonth(String startmonth) {
		this.startmonth = startmonth;
	}

	public String getStopmonth() {
		return stopmonth;
	}

	public void setStopmonth(String stopmonth) {
		this.stopmonth = stopmonth;
	}

	public String getFeemonth() {
		return feemonth;
	}

	public void setFeemonth(String feemonth) {
		this.feemonth = feemonth;
	}

	public int getRadix() {
		return radix;
	}

	public void setRadix(int radix) {
		this.radix = radix;
	}

	public String[] getBjmonthList() {
		return bjmonthList;
	}

	public boolean isExistsShebao() {
		return existsShebao;
	}

	public String getExistsMessage() {
		return existsMessage;
	}

	public boolean isVisJL() {
		return visJL;
	}

	public void setVisJL(boolean visJL) {
		this.visJL = visJL;
	}

}
