package Controller.EmSheBao;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import Model.EmShebaoBJModel;
import Model.EmShebaoUpdateModel;
import Model.EmbaseModel;
import Model.emmonthModel;
import Util.DateStringChange;
import Util.UserInfo;
import bll.EmSheBao.Emsi_OperateBll;
import bll.EmSheBao.Emsi_SelBll;

public class Emsi_BjStep3Controller {
	private final int gid = Integer.parseInt(Executions.getCurrent().getArg()
			.get("gid").toString());
	private Emsi_SelBll bll = new Emsi_SelBll();
	private boolean ifStop;
	private EmShebaoUpdateModel sbModel;
	private List<EmShebaoBJModel> bjList;
	private EmbaseModel emModel;
	private emmonthModel emmonthmodel = new emmonthModel();
	// 页面获取值
	private String ownmonth;
	private String feemonth;

	// 判断是否存在社保信息
	private boolean existsShebao = false;
	private String existsMessage;

	public Emsi_BjStep3Controller() {

		if (Executions.getCurrent().getArg().get("emmonthmodel") != null) {
			emmonthmodel = (emmonthModel) Executions.getCurrent().getArg()
					.get("emmonthmodel");
		}

		try {
			sbModel = bll.getShebaoUpdateByGid(gid);
			// 检测是否存在社保信息
			if (sbModel == null) {
				existsShebao = true;
				existsMessage = "未找到该员工社保信息，不能操作补缴!";
				return;
			}
			// 根据GID获取员工补缴信息
			emModel = bll.getEmBaseBj(gid);
			// 获取当前月份
			int nowMonth = Integer.parseInt(DateStringChange
					.Datestringnow("yyyyMM"));

			// 判断是否停止当月操作社保
			ifStop = bll.ifStop();

			ownmonth = emModel.getEmba_emsb_ownmonth().toString();

			// 收费月份小于当前月份的转为当前月份
			//System.out.print(emModel.getEmba_emsb_feeownmonth());

			if (Integer.parseInt(emModel.getEmba_emsb_feeownmonth()) - nowMonth < 0) {
				feemonth = String.valueOf(nowMonth);
			} else {
				feemonth = emModel.getEmba_emsb_feeownmonth().toString();
			}

			// 获取预录的员工补缴信息列表
			getEmBaseBjList();
		} catch (Exception e) {
			e.printStackTrace();
			existsShebao = true;
			existsMessage = "未找到该员工预录的补缴信息!";
		}
	}

	// 待确认选项
	@Command("checkDeclare")
	public void checkDeclare(@BindingParam("declare") String declare,
			@BindingParam("m") EmShebaoBJModel m) {
		m.setEmsb_ifdeclare(Integer.parseInt(declare));
	}

	// 提交
	@Command("bj")
	public void bj(@BindingParam("win") Window win) {
		try {
			if (checkPage()) {
				int bjCount = bjList.size();
				int addCount = 0;
				int addCountJL = 0;
				int bjCountJL = 0;
				String jlstr="";
				if (bjCount > 0) {
					for (EmShebaoBJModel m : bjList) {
						// 调用补缴方法
						Emsi_OperateBll opbll = new Emsi_OperateBll();
						String[] message = opbll.addbj(m);
						if (message[0].equals("1")) {
							addCount++;

							// 插入医疗补交数据
							if (m.isChk_jlbj()) {
								bjCountJL++;
								String[] msg2 = opbll.addJLbj(m);
								if (msg2[0].equals("1")) {
									addCountJL++;
								}
							}
						}
					}
					if (addCountJL != bjCountJL) {
						jlstr="，但是医疗补交有问题，请与计算机部门联系";
					}
					// 判断数据录入成功与否
					if (addCount == bjCount) {
						Messagebox.show("新增补缴成功"+jlstr+"。", "操作提示", Messagebox.OK,
								Messagebox.NONE);
						win.detach();
					} else if (addCount == 0) {
						Messagebox.show("新增补缴失败。", "操作提示", Messagebox.OK,
								Messagebox.NONE);
					} else {
						Messagebox.show("新增补缴录入有部分数据失败，请与计算机部门联系。", "操作提示",
								Messagebox.OK, Messagebox.NONE);
						win.detach();
					}
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

	// 获取预录的员工补缴信息列表
	private String getEmBaseBjList() {
		String str = "";
		bjList = new ArrayList<EmShebaoBJModel>();
		EmShebaoBJModel bjModel = null;
		Boolean sbsy = false;

		// //判断是不是顺延申报补缴
		// if (emModel.getEmba_emsb_m3() != null
		// && !"".equals(emModel.getEmba_emsb_m3())
		// && emModel.getEmba_emsb_r3() != 0) {
		//
		// if(emmonthmodel.getSbbj3ownmont()!=null )
		// {
		//
		// if
		// (emmonthmodel.getSbbj3ownmont()-Integer.parseInt(emModel.getEmba_emsb_m3())>0)
		//
		// {
		// sbsy=true;
		// }
		// }
		//
		//
		// }
		if (emmonthmodel.getSbbj1ownmont() != null
				&& !"".equals(emmonthmodel.getSbbj1ownmont())
				&& emModel.getEmba_emsb_r1() != 0) {
			bjModel = new EmShebaoBJModel();

			bjModel.setEmsb_radix(emModel.getEmba_emsb_r1());
			bjModel.setChk_jlbj(emModel.isChk_jlbj1());

			bjModel.setEmsb_startmonth(emmonthmodel.getSbbj1ownmont());
			bjModel.setEmsb_stopmonth(emmonthmodel.getSbbj1ownmont());
			bjModel.setEmsb_yltype(sbModel.getEsiu_yltype());
			// 写入补缴公用字段
			writeBjModel(bjModel);
			bjList.add(bjModel);
		}
		if (emmonthmodel.getSbbj2ownmont() != null
				&& !"".equals(emmonthmodel.getSbbj2ownmont())
				&& emModel.getEmba_emsb_r2() != 0) {
			bjModel = new EmShebaoBJModel();

			bjModel.setEmsb_radix(emModel.getEmba_emsb_r2());
			bjModel.setChk_jlbj(emModel.isChk_jlbj2());

			bjModel.setEmsb_startmonth(emmonthmodel.getSbbj2ownmont());
			bjModel.setEmsb_stopmonth(emmonthmodel.getSbbj2ownmont());
			bjModel.setEmsb_yltype(sbModel.getEsiu_yltype());
			// 写入补缴公用字段
			writeBjModel(bjModel);
			bjList.add(bjModel);
		}
		if (emmonthmodel.getSbbj3ownmont() != null
				&& !"".equals(emmonthmodel.getSbbj3ownmont())
				&& emModel.getEmba_emsb_r3() != 0) {
			bjModel = new EmShebaoBJModel();

			bjModel.setEmsb_radix(emModel.getEmba_emsb_r3());
			bjModel.setChk_jlbj(emModel.isChk_jlbj3());
			// bjModel.setEmsb_radix(emModel.getEmba_emsb_r3());
			bjModel.setEmsb_startmonth(emmonthmodel.getSbbj3ownmont());
			bjModel.setEmsb_stopmonth(emmonthmodel.getSbbj3ownmont());
			bjModel.setEmsb_yltype(sbModel.getEsiu_yltype());
			// 写入补缴公用字段
			writeBjModel(bjModel);
			bjList.add(bjModel);
		}

		return str;
	}

	// 写入补缴公用字段
	private void writeBjModel(EmShebaoBJModel m) {
		m.setGid(emModel.getGid());
		m.setEmsb_name(emModel.getEmba_name());
		m.setOwnmonth(Integer.parseInt(ownmonth));
		m.setEmsb_feeownmonth(Integer.parseInt(feemonth));
		m.setEmsb_computerid(sbModel.getEsiu_computerid());
		m.setEmsb_hj(sbModel.getEsiu_hj());
		m.setEmsb_addname(UserInfo.getUsername());
		m.setEmsb_ifdeclare(0);
		m.setSoin_title(sbModel.getEmsf_soin_title());
	}

	public boolean isExistsShebao() {
		return existsShebao;
	}

	public String getExistsMessage() {
		return existsMessage;
	}

	public List<EmShebaoBJModel> getBjList() {
		return bjList;
	}

	public EmShebaoUpdateModel getSbModel() {
		return sbModel;
	}

	public String getOwnmonth() {
		return ownmonth;
	}

	public String getFeemonth() {
		return feemonth;
	}

	public boolean isIfStop() {
		return ifStop;
	}

	public int getGid() {
		return gid;
	}

}
