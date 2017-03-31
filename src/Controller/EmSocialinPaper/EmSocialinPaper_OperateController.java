package Controller.EmSocialinPaper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import bll.EmSocialinPaper.EmSocialinPaperListBll;
import bll.EmSocialinPaper.EmSocialinPaperOperateBll;

import Model.EmSocialinPaperModel;
import Util.UserInfo;

public class EmSocialinPaper_OperateController {

	private List<EmSocialinPaperModel> list = new ListModelList<>();
	private List<EmSocialinPaperModel> comboList = new ListModelList<>();
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	private String names = "";
	private String title = "";
	private String timestr = "";
	private Date time = new Date();
	private boolean qyVis = false;
	private boolean fkVis = false;
	private String area = "";
	private String sendtype = "";
	Integer daid;
	Integer taprid;

	@SuppressWarnings("unchecked")
	public EmSocialinPaper_OperateController() {
		try {
			EmSocialinPaperListBll bll = new EmSocialinPaperListBll();
			setList((List<EmSocialinPaperModel>) Executions.getCurrent()
					.getArg().get("list"));
			if (list == null) {
				list = new ListModelList<>();
				daid = Integer.parseInt(Executions.getCurrent().getArg()
						.get("daid").toString());
				taprid = Integer.parseInt(Executions.getCurrent().getArg()
						.get("id").toString());
				System.out.println(taprid);
				list.add(bll.getEmSocailinPaperInfo(daid));
			}

			for (EmSocialinPaperModel m : list) {
				names += m.getName() + "、";
			}
			names = names.substring(0, names.length() - 2);
			setTitle("状态变更 - " + list.get(0).getStatename());
			timestrInit();
		} catch (Exception e) {
			Messagebox.show("页面加载出错,请联系IT部门!", "ERROR", Messagebox.OK,
					Messagebox.ERROR);
			System.out.println(e.toString());
		}
	}

	public void timestrInit() {
		switch (list.get(0).getEspa_state()) {
		case 2:
			timestr = "交后道处理日期";
			break;
		case 3:
			timestr = "福利部确认日期";
			break;
		case 4:
			timestr = "打单日期";
			break;
		case 5:
			timestr = "社保局受理日期";
			setComboList(new EmSocialinPaperListBll()
					.getEmSocialinPaperState(" and typename='办理区域'"));
			area = comboList.get(0).getStatename();
			setQyVis(true);
			break;
		case 6:
			timestr = "办理完成日期";
			break;
		case 7:
			timestr = "前道签收日期";
			break;
		case 8:
			timestr = "发卡日期";
			setComboList(new EmSocialinPaperListBll()
					.getEmSocialinPaperState(" and typename='发卡方式'"));
			sendtype = comboList.get(0).getStatename();
			setFkVis(true);
			break;

		default:
			break;
		}
	}

	@Command("next")
	public void next(@BindingParam("win") Window win) {
		if (time == null) {
			Messagebox.show("请输入时间!", "ERROR", Messagebox.OK, Messagebox.ERROR);
		} else if (list.get(0).getEspa_state() == 5 && area.isEmpty()) {
			Messagebox.show("请选择办理区域!", "ERROR", Messagebox.OK,
					Messagebox.ERROR);
		} else if (list.get(0).getEspa_state() == 8 && sendtype.isEmpty()) {
			Messagebox.show("请选择发卡方式!", "ERROR", Messagebox.OK,
					Messagebox.ERROR);
		} else {
			try {
				int i = 0;
				EmSocialinPaperOperateBll bll = new EmSocialinPaperOperateBll();
				for (EmSocialinPaperModel m : list) {

					m.setEspa_addname(UserInfo.getUsername());
					m.setEspa_finaltime(sdf.format(time));
					if (m.getEspa_tapr_id() == 0) {
						m.setEspa_tapr_id(taprid);
					}
					if (m.getEspa_id() == 0) {
						m.setEspa_id(daid);
					}
					if (m.getEspa_state() == 5) {
						m.setStr(area);
					}
					if (m.getEspa_state() == 8) {
						m.setStr(sendtype);
					}

					String[] str = bll.next(m);
					if (str[0].equals("1")) {
						i++;
					}
				}

				if (i > 0) {
					Messagebox.show("成功提交了" + i + "条办卡数据!", "INFORMATION",
							Messagebox.OK, Messagebox.INFORMATION);
					win.detach();
				} else {
					Messagebox.show("成功提交了0条数据!", "ERROR", Messagebox.OK,
							Messagebox.ERROR);
				}
			} catch (Exception e) {
				Messagebox.show("提交出错,请联系IT部门!", "ERROR", Messagebox.OK,
						Messagebox.ERROR);
				System.out.println(e.toString());
			}

		}
	}

	public List<EmSocialinPaperModel> getList() {
		return list;
	}

	public void setList(List<EmSocialinPaperModel> list) {
		this.list = list;
	}

	public String getNames() {
		return names;
	}

	public void setNames(String names) {
		this.names = names;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTimestr() {
		return timestr;
	}

	public void setTimestr(String timestr) {
		this.timestr = timestr;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public boolean isQyVis() {
		return qyVis;
	}

	public void setQyVis(boolean qyVis) {
		this.qyVis = qyVis;
	}

	public boolean isFkVis() {
		return fkVis;
	}

	public void setFkVis(boolean fkVis) {
		this.fkVis = fkVis;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getSendtype() {
		return sendtype;
	}

	public void setSendtype(String sendtype) {
		this.sendtype = sendtype;
	}

	public List<EmSocialinPaperModel> getComboList() {
		return comboList;
	}

	public void setComboList(List<EmSocialinPaperModel> comboList) {
		this.comboList = comboList;
	}
}
