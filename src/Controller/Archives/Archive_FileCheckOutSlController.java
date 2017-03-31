package Controller.Archives;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;
import org.zkoss.zul.Messagebox.ClickEvent;

import Model.EmArchiveDatumModel;
import Model.EmArchiveModel;
import Model.EmGatheringModel;
import Util.UserInfo;
import bll.Archives.EmArchiveDatum_OperateBll;
import bll.Archives.EmArchive_SelectBll;
import bll.EmFinanceManage.emgt_selectBll;

public class Archive_FileCheckOutSlController {
	private String id = (String) Executions.getCurrent().getArg().get("daid");
	private String tarpid =null;
	private EmArchive_SelectBll bll = new EmArchive_SelectBll();
	private List<EmArchiveDatumModel> list = bll
			.getEmArchiveDatumInfo(" and eada_id=" + id);
	private EmArchiveDatumModel model = new EmArchiveDatumModel();
	private EmArchiveModel amodel = new EmArchiveModel();
	private List<EmArchiveModel> archivelist = new ArrayList<EmArchiveModel>();
	private double hkfee = 30, dafee = 10, jyfee = 0;
	private Integer monthnum = 0;
	private boolean jyfeevis = false;
	private Date feeenddate;
	private EmArchiveDatumModel ml = new EmArchiveDatumModel();
	// 获取个人收费记录
	private emgt_selectBll gtbll = new emgt_selectBll();
	private List<EmGatheringModel> gtlist = new ArrayList<EmGatheringModel>();

	public Archive_FileCheckOutSlController() {
		if(Executions.getCurrent().getArg().get("id")!=null)
		{
			tarpid=Executions.getCurrent().getArg().get("id").toString();
		}
		if (list.size() > 0) {
			model = list.get(0);
		}
		archivelist = bll.getEmArchiveInfo("  and emar_state=1 and a.gid="
				+ model.getGid());
		if (archivelist.size() > 0) {
			amodel = archivelist.get(0);
			setAmodel(amodel);
		}

		// 计算月份
		monthnum = monthSpace(model.getEada_chargedate(), new Date());
		hkfee = hkfee * monthnum;
		dafee = dafee * monthnum;
		gtlist = gtbll.getEmGatheringList(" and a.gid=" + model.getGid());
		ml = bll.getEmArchiveDatumModel(" and eada_fid='" + model.getEada_fid()
				+ "' and eada_type=0");
		if (model.getEada_savefiledate() == null) {
			model.setEada_savefiledate(ml.getEada_savefiledate());
		}
		if (model.getEada_deadline() == null) {
			model.setEada_deadline(ml.getEada_deadline());
		}
		if (model.getEada_fileplace() == null
				|| model.getEada_fileplace().equals("")) {
			model.setEada_fileplace(ml.getEada_fileplace());
		}
		if (model.getEada_colhj() != null)// 户口挂靠字段显示”否“或者”无此服务“的，户口费用栏金额默认是”0“。
		{
			if (model.getEada_colhj() != 1) {
				hkfee = 0;
			}
		} else {
			hkfee = 0;
		}
	}

	// 提交更新
	@Command
	public void summitupdate(@BindingParam("detail") final Window detail) {
		if (feeenddate != null) {
			Messagebox.show("确认提交数据?", "操作提示", new Messagebox.Button[] {
					Messagebox.Button.OK, Messagebox.Button.CANCEL },
					Messagebox.QUESTION,
					new EventListener<Messagebox.ClickEvent>() {
						@Override
						public void onEvent(ClickEvent event) throws Exception {
							// TODO Auto-generated method stub
							if (Messagebox.Button.OK.equals(event.getButton())) {
								EmArchiveDatum_OperateBll bllo = new EmArchiveDatum_OperateBll();
								EmArchiveDatumModel models = new EmArchiveDatumModel();
								models.setEada_id(model.getEada_id());
								models.setEada_addname(UserInfo.getUsername());
								models.setEada_tapr_id(model.getEada_tapr_id());
								models.setEada_type("受理申请");
								String sql = ",eada_final=2,eada_dnfee='"
										+ dafee + "'," + "eada_hkfee='" + hkfee
										+ "',eada_jyfee='" + jyfee + "',"
										+ "eada_checkoutmode='"
										+ model.getEada_checkoutmode() + "'";
								if (feeenddate != null) {
									sql = sql + ",eada_feeenddate='"
											+ DatetoStings(feeenddate) + "'";
								}
								if (model.getEada_chargedate() != null) {
									sql = sql
											+ ",eada_chargedate='"
											+ DatetoStings(model
													.getEada_chargedate())
											+ "'";
								}
								String[] str = bllo.Accepted(models, sql);
								if (str[0] == "1" || str[0].equals("1")) {
									if(hkfee<=0&&dafee<=0&&jyfee<=0)//如果不需要缴费则跳过财务结算步骤
									{
										Integer nextTarpId=Integer.parseInt(str[2].toString());
										String nextsql=",eada_final=2";
										bllo.skiptonext(models, nextsql, nextTarpId);
									}
									
									Messagebox.show(str[1], "提示",
											Messagebox.OK,
											Messagebox.INFORMATION);
									detail.detach();
								} else {
									Messagebox.show(str[1], "提示",
											Messagebox.OK, Messagebox.ERROR);
								}
							}
						}
					});
		} else {
			Messagebox.show("请选择收费终止日", "提示", Messagebox.OK, Messagebox.ERROR);
		}

	}

	// 收费终止日选择事件——计算与欠费起始日相隔的月份
	@Command
	@NotifyChange({ "hkfee", "dafee" })
	public void changeendmoetd(@BindingParam("enddate") Date enddate) {
		if (enddate != null) {
			hkfee = 30;
			dafee = 10;
			monthnum = monthSpace(model.getEada_chargedate(), enddate);
			hkfee = hkfee * monthnum;
			dafee = dafee * monthnum;
		}
	}

	// 收费终止日选择事件——计算与欠费起始日相隔的月份
	@Command
	@NotifyChange({ "jyfeevis", "jyfee" })
	public void changetype(@BindingParam("outtype") String outtype) {
		if (outtype != null && outtype.equals("凭函机要")) {
			jyfeevis = true;
			jyfee = 30;
		} else {
			jyfeevis = false;
			jyfee = 0;
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
				"../Archives/Archive_back.zul", null, map);
		window.doModal();
		if (map.get("flag") == "1") {
			win.detach();
		}
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

	public EmArchiveModel getAmodel() {
		return amodel;
	}

	public void setAmodel(EmArchiveModel amodel) {
		this.amodel = amodel;
	}

	public EmArchiveModel getamodel() {
		return amodel;
	}

	public void setamodel(EmArchiveModel amodel) {
		this.amodel = amodel;
	}

	// 计算两个日期相隔月份数
	private Integer monthSpace(Date startdate, Date enddate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		Calendar bef = Calendar.getInstance();
		Calendar aft = Calendar.getInstance();
		Calendar aftdate = Calendar.getInstance();
		Calendar befdates = Calendar.getInstance();
		Integer date = 0;
		Integer befdate = 0;
		int result = 0;
		try {
			bef.setTime(sdf.parse(DatetoSting(startdate)));
			aft.setTime(sdf.parse(DatetoSting(enddate)));
			aftdate.setTime(enddate);
			befdates.setTime(startdate);
			date = aftdate.get(Calendar.DAY_OF_MONTH);// 获取收费终止日
			befdate = befdates.get(Calendar.DAY_OF_MONTH);// 获取欠费起始日

			Integer aftmonth = aft.get(Calendar.MONTH) + 1;// 结束月
			Integer befmonth = bef.get(Calendar.MONTH) + 1;// 开始月
			// 首先判断年份是否相同
			Integer aftyear = aft.get(Calendar.YEAR);
			Integer befyear = bef.get(Calendar.YEAR);
			if (!aftyear.equals(befyear))// 如果不相等
			{
				if (aftyear > befyear) {
					result = 12 - befmonth + 1;// 计算第一年的月数
					Integer y = (aftyear - befyear) - 1;// 计算除了第一年两个年份相差年数
					result = result + 12 * y;
					if (date < 15) {
						aftmonth = aftmonth - 1;
					}
					result = result + aftmonth;
				}
			} else {
				if (date >= 15) {
					if (aftmonth == befmonth) {

						if (befdate <= 15) {
							result = 1;
						} else {
							result = 0;
						}
					} else {
						if (befdate < 15) {
							result = aftmonth - befmonth;
							result = result + 1;
						} else {
							result = aftmonth - befmonth;
						}
					}

				} else {
					if (aftmonth == befmonth) {
						result = 0;
					} else {
						if (befdate > 15) {
							result = aftmonth - befmonth;
							result = result - 1;
						} else {
							result = aftmonth - befmonth;
						}

					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	public double getHkfee() {
		return hkfee;
	}

	public void setHkfee(double hkfee) {
		this.hkfee = hkfee;
	}

	public double getDafee() {
		return dafee;
	}

	public void setDafee(double dafee) {
		this.dafee = dafee;
	}

	public double getJyfee() {
		return jyfee;
	}

	public void setJyfee(double jyfee) {
		this.jyfee = jyfee;
	}

	public boolean isJyfeevis() {
		return jyfeevis;
	}

	public void setJyfeevis(boolean jyfeevis) {
		this.jyfeevis = jyfeevis;
	}

	public Date getFeeenddate() {
		return feeenddate;
	}

	public void setFeeenddate(Date feeenddate) {
		this.feeenddate = feeenddate;
	}

	public List<EmGatheringModel> getGtlist() {
		return gtlist;
	}

	public void setGtlist(List<EmGatheringModel> gtlist) {
		this.gtlist = gtlist;
	}

	// Date类型转换String
	public static String DatetoSting(Date d) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		String Date = sdf.format(d);
		return Date;
	}

	// Date类型转换String
	public String DatetoStings(Date d) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String Date = sdf.format(d);
		return Date;
	}

	private Date strTodate(String str) {
		Date date = null;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		if (str != null) {
			try {
				date = format.parse(str);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return date;
	}
}
