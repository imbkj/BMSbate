package Controller.EmCensus;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Row;
import org.zkoss.zul.Window;

import bll.EmCensus.EmCensus_OperateBll;
import bll.EmCensus.EmCensus_SelectBll;
import Model.EmCensusModel;
import Model.EmbaseModel;
import Util.UserInfo;
import Util.pinyin4jUtil;

public class EmCensus_BorrowCardController {
	private String id = Executions.getCurrent().getArg().get("daid").toString();
	private String tperid = Executions.getCurrent().getArg().get("id")
			.toString();
	private EmCensus_SelectBll bll = new EmCensus_SelectBll();
	private List<EmCensusModel> emcensus = bll.getEmCensusInfo(" and emhj_id="
			+ id);
	private boolean visable = false, idcardvis = false, tyvis = false;
	private EmCensusModel hjmodel = new EmCensusModel();
	private String daaddress;// 落户后档案存档地址
	private String hjaddress;// 户口落户地址
	private String hjno = "", logname = UserInfo.getUsername(), idcard = "";
	private EmCensusModel emba = new EmCensusModel();

	private String emcensustype = "", emcensustypename = "";

	public EmCensus_BorrowCardController() {
		if (emcensus.size() > 0) {
			hjmodel = emcensus.get(0);
			hjno = bll.getEmHjNo(hjmodel.getCid(), hjmodel.getGid());
			emcensustype = hjmodel.getEmhj_type();
			if (hjmodel.getGid() != null && hjmodel.getGid() > 0) {
				emba = bll.getEmCensusId(hjmodel.getGid());
			}
		}
		if (hjmodel.getEmhj_in_class() != null
				&& hjmodel.getEmhj_in_class().equals("出生入户")) {
			idcardvis = true;
		}
		if (hjmodel != null && hjmodel.getEmhj_state().equals("7")) {
			hjno = hjno();
		}
		typechange();
	}

	// 确认
	@Command
	public void addconfirm(@BindingParam("win") Window win) {
		if (hjmodel.getEmhj_type() == null || hjmodel.getEmhj_type().equals("")) {
			Messagebox.show("请选择账户类型", "提示", Messagebox.OK, Messagebox.ERROR);
		} else if (hjmodel.getEmhj_place() == null
				|| hjmodel.getEmhj_place().equals("")) {
			Messagebox.show("请选择派出所", "提示", Messagebox.OK, Messagebox.ERROR);
		} else if (hjmodel.getEmhj_in_class() == null
				|| hjmodel.getEmhj_in_class().equals("")) {
			Messagebox.show("请选择入户方式", "提示", Messagebox.OK, Messagebox.ERROR);
		} else {
			String sql = ",emhj_type='" + hjmodel.getEmhj_type()
					+ "',emhj_place='" + hjmodel.getEmhj_place()
					+ "',emhj_state=1,emhj_in_class='"
					+ hjmodel.getEmhj_in_class() + "'";
			EmCensusModel model = new EmCensusModel();
			model.setEmhj_id(Integer.parseInt(id));
			if(tperid!=null&&!tperid.equals(""))
			{
				model.setEmhj_taprid(Integer.parseInt(tperid));
			}
			model.setOperateinfo("确认落户信息");
			EmCensus_OperateBll bll = new EmCensus_OperateBll();
			String[] str = bll.EmCensusUpdate(model, sql, 0);
			if (str[0] == "1") {
				Messagebox.show("提交成功", "提示", Messagebox.OK,
						Messagebox.INFORMATION);
				win.detach();
			} else {
				Messagebox.show(str[1], "提示", Messagebox.OK, Messagebox.ERROR);
			}
		}
	}

	// 借卡
	@Command
	public void borrowemCensus(@BindingParam("home") Checkbox home,
			@BindingParam("homecopy") Checkbox homecopy,
			@BindingParam("hjcard") Checkbox hjcard,
			@BindingParam("borrowtime") Date borrowtime,
			@BindingParam("inhjtime") Date inhjtime,
			@BindingParam("cash") String cash,
			@BindingParam("borrowhand") String borrowhand,
			@BindingParam("cashtype") String cashtype,
			@BindingParam("borrowreason") String borrowreason,
			@BindingParam("flremark") String flremark,
			@BindingParam("win") Window win) {
		String sql = "";
		if (!home.isChecked() && !homecopy.isChecked() && !hjcard.isChecked()) {
			Messagebox.show("请至少选择一种借卡类型", "提示", Messagebox.OK,
					Messagebox.ERROR);
		} else {
			if (home.isChecked()) {
				sql = sql + ",emhj_sy=1";
			}
			if (homecopy.isChecked()) {
				sql = sql + ",emhj_syfy=1";
			}
			if (hjcard.isChecked()) {
				sql = sql + ",emhj_grhk=1";
			}
			if (borrowtime != null) {
				sql = sql + ",emhj_botime='" + timechange(borrowtime) + "'";
			}
			if (inhjtime != null) {
				sql = sql + ",emhj_confertime='" + timechange(inhjtime) + "'";
			}
			if (cash != null && !cash.equals("") && cash != "") {
				sql = sql + ",emhj_fee='" + cash + "'";
			}
			if (borrowhand != null && !borrowhand.equals("")
					&& borrowhand != "") {
				sql = sql + ",emhj_statename2='" + borrowhand + "'";
			}
			if (cashtype != null && !cashtype.equals("") && cashtype != "") {
				sql = sql + ",emhj_feetype='" + cashtype + "'";
			}
			if (borrowreason != null && !borrowreason.equals("")
					&& borrowreason != "") {
				sql = sql + ",emhj_case='" + borrowreason + "'";
			}
			if (flremark != null && !flremark.equals("") && flremark != "") {
				sql = sql + ",emhj_remark='" + flremark + "'";
			}
			sql = sql + ",emhj_state = 2";
			EmCensusModel model = new EmCensusModel();
			if (tperid != null && !tperid.equals("") && tperid != "") {
				model.setEmhj_taprid(Integer.parseInt(tperid));
			}
			if (id != null && !id.equals("") && id != "") {
				model.setEmhj_id(Integer.parseInt(id));
			}
			model.setOperateinfo("落户借卡");
			EmCensus_OperateBll bll = new EmCensus_OperateBll();
			String[] str = bll.EmCensusUpdate(model, sql, 0);
			if (str[0] == "1") {
				Messagebox.show("提交成功", "提示", Messagebox.OK,
						Messagebox.INFORMATION);
				win.detach();
			} else {
				Messagebox.show(str[1], "提示", Messagebox.OK, Messagebox.ERROR);
			}
		}
	}

	// 打印
	@Command
	public void borrowcardprint(@BindingParam("win") Window win) {
		String sql = ",emhj_state = 7";
		EmCensusModel model = new EmCensusModel();
		model.setEmhj_id(Integer.parseInt(id));
		if(tperid!=null&&!tperid.equals(""))
		{
			model.setEmhj_taprid(Integer.parseInt(tperid));
		}
		model.setOperateinfo("打印");
		EmCensus_OperateBll bll = new EmCensus_OperateBll();
		String[] str = bll.EmCensusUpdate(model, sql, 0);
		if (str[0] == "1") {
			win.detach();
			Messagebox
					.show("提交成功", "提示", Messagebox.OK, Messagebox.INFORMATION);
		} else {
			win.detach();
			Messagebox.show(str[1], "提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	// 还卡
	@Command
	public void returnemCensus(@BindingParam("home") Checkbox home,
			@BindingParam("homecopy") Checkbox homecopy,
			@BindingParam("hjcard") Checkbox hjcard,
			@BindingParam("returman") String returman,
			@BindingParam("emhjno") String emhjno,
			@BindingParam("returncash") String returncash,
			@BindingParam("returntime") Date returntime,
			@BindingParam("inhjtime") Date inhjtime,
			@BindingParam("win") Window win) {
		String sql = "";
		int statesid = 0;
		int sy = 0;
		int syfy = 0;
		int hk = 0;
		if (!home.isChecked() && !homecopy.isChecked() && !hjcard.isChecked()) {
			Messagebox.show("请至少选择一种借卡类型", "提示", Messagebox.OK,
					Messagebox.ERROR);
		} else {
			if (emhjno == null || emhjno.equals("")) {
				Messagebox.show("请填写户口卡编号", "提示", Messagebox.OK,
						Messagebox.ERROR);
			} else if (hjmodel.getEmhj_in_class() != null
					&& hjmodel.getEmhj_in_class().equals("出生入户")
					&& idcard.equals("")) {
				Messagebox.show("请填写身份证号吗", "提示", Messagebox.OK,
						Messagebox.ERROR);
			} else {
				if (hjmodel.getEmhj_in_class() != null
						&& hjmodel.getEmhj_in_class().equals("出生入户")) {
					sql = sql + ",emhj_idcard='" + idcard + "'";
				}
				if (home.isChecked()) {
					sql = sql + ",emhj_sy=2";
					sy = 1;
				}
				if (homecopy.isChecked()) {
					sql = sql + ",emhj_syfy=2";
					syfy = 1;
				}
				if (hjcard.isChecked()) {
					sql = sql + ",emhj_grhk=2";
					hk = 1;
				}
				if (returman != null && !returman.equals("") && returman != "") {
					sql = sql + ",emhj_statename3='" + returman + "'";
				}
				if (emhjno != null && !emhjno.equals("") && emhjno != "") {
					sql = sql + ",emhj_no='" + emhjno + "'";
				}
				if (returncash != null && !returncash.equals("")
						&& returncash != "") {
					int emhj_backfee = 0;
					if (hjmodel.getEmhj_backfee().longValue() > 0) {
						emhj_backfee = (int) (hjmodel.getEmhj_backfee()
								.longValue() + Integer.parseInt(returncash));
					} else {
						emhj_backfee = Integer.parseInt(returncash);
					}
					sql = sql + ",emhj_backfee='" + emhj_backfee + "'";
				}
				if (returntime != null) {
					sql = sql + ",emhj_backtime='" + timechange(returntime)
							+ "'";
				}
				if (inhjtime != null) {
					sql = sql + ",emhj_in_time='" + timechange(inhjtime) + "'";
				}
				if (hjmodel.getEmhj_sy() == 1) {
					if (sy == 0) {
						statesid = 1;
					}
				}
				if (hjmodel.getEmhj_syfy() == 1) {
					if (syfy == 0) {
						statesid = 1;
					}
				}
				if (hjmodel.getEmhj_grhk() == 1) {
					if (hk == 0) {
						statesid = 1;
					}
				}
				if (statesid == 1) {
					sql = sql + ",emhj_state =3";
				} else {
					sql = sql + ",emhj_state =5";
				}
				if (emcensustype != null && !emcensustype.equals("")) {
					sql = sql + ",emhj_intype='" + emcensustype + "'";
				}
				if (emcensustypename != null) {
					sql = sql + ",emhj_intypename='" + emcensustypename + "'";
				}
				EmCensusModel model = new EmCensusModel();
				model.setEmhj_id(Integer.parseInt(id));
				if(tperid!=null&&!tperid.equals(""))
				{
					model.setEmhj_taprid(Integer.parseInt(tperid));
				}
				model.setOperateinfo("落户还卡");
				EmCensus_OperateBll bll = new EmCensus_OperateBll();
				sql = sql + ",emhj_dnaddress='" + daaddress
						+ "',emhj_address='" + hjaddress + "'";
				String[] str = bll.EmCensusUpdate(model, sql, statesid);
				if (str[0] == "1") {
					String strs = "";
					if (hjmodel.getGid() != null) {
						// 户籍变更
						// strs = addtask(hjmodel.getCid(), hjmodel.getGid());
					}
					if (strs == "") {
						strs = "提交成功";
					}
					Messagebox.show("提交成功", "提示", Messagebox.OK,
							Messagebox.INFORMATION);
					win.detach();
				} else {
					Messagebox.show(str[1], "提示", Messagebox.OK,
							Messagebox.ERROR);
				}
			}
		}
	}

	private String ifChecked(Radiogroup rp, String newhj) {
		String strif = "";
		String newhjtxt = newhj;
		// 如果选择修改户籍
		if (rp.getSelectedItem().getValue().equals("1")
				|| rp.getSelectedItem().getValue() == "1") {
			if (newhjtxt != null && !newhjtxt.equals("") && newhjtxt != "") {
				strif = newhjtxt;
			}
		}
		return strif;
	}

	// 选择是否改户籍
	@Command
	@NotifyChange("visable")
	public void checked(@BindingParam("rp") Radiogroup rp) {
		if (rp.getSelectedItem().getValue().equals("1")) {
			visable = true;
		} else {
			visable = false;
		}
	}

	// 添加身份证号码
	@Command
	public void addIdcard(@BindingParam("idcard") String idcard,
			@BindingParam("win") Window win) {
		String sqls = "";
		if (idcard != null && !idcard.equals("") && idcard != ""
				&& !idcard.equals("请填写家属身份证号") && idcard != "请填写家属身份证号") {
			sqls = ",emhj_idcard='" + idcard + "'";
		}
		EmCensusModel model = new EmCensusModel();
		model.setEmhj_id(Integer.parseInt(id));
		model.setEmhj_taprid(0);
		EmCensus_OperateBll bll = new EmCensus_OperateBll();
		int ky = bll.UpdateEmCensusInfo(model, sqls);
		if (ky > 0) {
			Messagebox
					.show("提交成功", "提示", Messagebox.OK, Messagebox.INFORMATION);
			win.detach();
		} else {
			Messagebox.show("提交失败", "提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	// 迁出
	@Command
	public void CensusOut(@BindingParam("outseasion") String outseasion,
			@BindingParam("outaddress") String outaddress,
			@BindingParam("outtime") Date outtime,
			@BindingParam("win") Window win) {
		String sqls = ",emhj_state =6";
		if (outseasion == null || outseasion.equals("") || outseasion == "") {
			Messagebox.show("请输入迁出理由", "提示", Messagebox.OK, Messagebox.ERROR);
		} else {
			sqls = sqls + ",emhj_outcase='" + outseasion + "'";
			sqls = sqls + ",emhj_outplace='" + outaddress + "'";
			sqls = sqls + ",emhj_outtime='" + timechange(outtime) + "'";
			EmCensusModel model = new EmCensusModel();
			model.setEmhj_id(Integer.parseInt(id));
			if (tperid != null && !tperid.equals("") && !tperid.equals("null")) {
				model.setEmhj_taprid(Integer.parseInt(tperid));
			}
			EmCensus_OperateBll bll = new EmCensus_OperateBll();

			int ky = bll.UpdateEmCensusInfo(model, sqls);
			if (ky > 0) {
				Messagebox.show("提交成功", "提示", Messagebox.OK,
						Messagebox.INFORMATION);
				win.detach();
			} else {
				Messagebox.show("提交失败", "提示", Messagebox.OK, Messagebox.ERROR);
			}
		}
	}

	// 确认
	@Command
	public void ifvisible(@BindingParam("val") String val,
			@BindingParam("rw") Row rw) {
		if (val.equals("随迁入户") || val == "随迁入户") {
			rw.setVisible(true);
		} else {
			rw.setVisible(false);
		}
	}

	// // 落户完成后自动生成社保和公积金户籍变更的任务单
	// private String addtask(Integer cid, Integer gid) {
	// String strnewhj = "深户", sbstr = "", gjjstr = "", strs = "";
	// Integer gjjk = 0, sbk = 0;
	// // 新户籍不等于空
	// if (strnewhj != null && !strnewhj.equals("") && strnewhj != "") {
	// EmDh_SelectBll bl = new EmDh_SelectBll();
	// String gjjname = "", sbname = "";
	// // 查询是否有社保服务
	// sbname = bl.ishaveService(hjmodel.getGid(), "社会保险服务");
	//
	// if (sbname != null && !sbname.equals("")) {
	// Emsi_SelBll sbbll = new Emsi_SelBll();
	// EmShebaoUpdateModel sbModel = sbbll.getShebaoUpdateByGid(gid);
	// boolean ifupdate = false;
	// if (sbModel.getEsiu_hj() != null) {
	// if (!sbModel.getEsiu_hj().equals("市内城镇")) {
	// ifupdate = true;
	// }
	// } else {
	// ifupdate = true;
	// }
	// if (ifupdate) {
	// String changecon = "市内城镇";
	// String ownmonth = ConstantsUtil.ownmonth;
	// EmShebaoChangeSZSIModel ecModel = new EmShebaoChangeSZSIModel();
	// String con = "户籍由“" + sbModel.getEsiu_hj() + "”改为“"
	// + changecon + "”";
	// ecModel.setGid(gid);
	// ecModel.setOwnmonth(Integer.parseInt(ownmonth));
	// ecModel.setEscs_change("变更户籍");
	// ecModel.setEscs_content(con);
	// ecModel.setEscs_s8("");
	// ecModel.setEscs_addname(UserInfo.getUsername());
	// ecModel.setEscs_remark("");
	// ecModel.setEscs_name(sbModel.getEsiu_name());
	// Emsi_OperateBll opbll = new Emsi_OperateBll();
	// String[] message = opbll.changeSZSI(ecModel);
	// }
	// }
	// // 查询是否有公积金服务
	// gjjname = bl.ishaveService(hjmodel.getGid(), "住房公积金服务");
	// if (gjjname != null && !gjjname.equals("")) {
	// gjjstr = "test";
	// // 新增公积金户籍变更，新户籍为strnewhj;
	// // 变更调用彭耀提供的公积金户籍变更的方法
	// EmHouseChangeGjjBll gjjbll = new EmHouseChangeGjjBll();
	// gjjk = gjjbll.addData(cid, gid, "变更户籍", "户籍变更为‘" + strnewhj
	// + "’", strnewhj, "", UserInfo.getUsername());
	// }
	// }
	// if (gjjk > 0 && sbk > 0) {
	// strs = "提交成功，并且新增了社保和公积金的户籍变更";
	// } else if (gjjk > 0 && sbk <= 0 && sbstr != "") {
	// strs = "提交成功，并且新增了公积金的户籍变更,社保户籍变更新增失败";
	// } else if (gjjk > 0 && sbk <= 0 && sbstr == "") {
	// strs = "提交成功，并且新增了公积金的户籍变更";
	// } else if (gjjk <= 0 && sbk > 0 && gjjstr != "") {
	// strs = "提交成功，并且新增了社保的户籍变更,公积金户籍biang新增失败";
	// } else if (gjjk <= 0 && sbk > 0 && gjjstr == "") {
	// strs = "提交成功，并且新增了社保的户籍变更";
	// } else if (gjjk <= 0 && sbk <= 0 && sbstr != "" && gjjstr != "") {
	// strs = "提交成功，社保和公积金的户籍变更新增失败";
	// } else if (sbstr == "" && gjjstr == "") {
	// strs = "提交成功，没有社保和公积金服务";
	// }
	// return strs;
	// }

	// 弹出退回页面
	@Command
	public void back(@BindingParam("win") Window win) {
		Map map = new HashMap<>();
		map.put("id", id);
		map.put("tarpid", tperid);
		Window window = (Window) Executions.createComponents(
				"../EmCensus/EmCensus_Back.zul", null, map);
		window.doModal();
		win.detach();
	}

	public EmCensusModel getHjmodel() {
		return hjmodel;
	}

	public void setHjmodel(EmCensusModel hjmodel) {
		this.hjmodel = hjmodel;
	}

	// 时间格式转换
	private java.sql.Date timechange(java.util.Date d) {
		java.sql.Date da = null;
		if (d != null && !d.equals("")) {
			java.sql.Date date = new java.sql.Date(d.getTime());
			da = date;
		}
		return da;
	}

	// 公司的简称（首字母）+所属派出所简称（首字母）+数字（从01开始按顺序编排）自动生成户口编号
	private String getEmHjNo() {
		String hjno = "";
		if (hjmodel.getEmhj_in_class() != null) {
			if (!hjmodel.getEmhj_in_class().equals("出生入户")
					&& !hjmodel.getEmhj_in_class().equals("夫妻投靠")
					&& !hjmodel.getEmhj_type().contains("随迁")) {
				hjno = initHjno();
			} else// 自动生成家属的户口编号
			{
				String emhjno = bll.getHjNoByGid(hjmodel.getGid());
				if (emhjno != null && !emhjno.equals("")) {
					emhjno = emhjno + "C";
					boolean flag = bll.ifexist(emhjno);
					if (flag) {
						emhjno = emhjno + "C";
						boolean flagnb = bll.ifexist(emhjno);
						if (flagnb) {
							emhjno = emhjno + "C";
						}
					}
					hjno = emhjno;
				}
			}
		} else {
			hjno = initHjno();
		}
		return hjno;
	}

	private String hjno() {
		String hjno = "";
		if (hjmodel.getEmhj_in_class() != null) {
			if (hjmodel.getEmhj_in_class().equals("出生入户")
					|| hjmodel.getEmhj_in_class().equals("夫妻投靠")
					||hjmodel.getEmhj_type().contains("随迁")) {
				String emhjno = bll.getHjNoByGid(hjmodel.getGid());
				if (emhjno != null && !emhjno.equals("")) {
					emhjno = emhjno + "C";
					boolean flag = bll.ifexist(emhjno);
					if (flag) {
						emhjno = emhjno + "C";
						boolean flagnb = bll.ifexist(emhjno);
						if (flagnb) {
							emhjno = emhjno + "C";
						}
					}
					hjno = emhjno;
				}
			} else// 自动生成家属的户口编号
			{
				if (hjmodel.getEmhj_type() != null
						&& !hjmodel.getEmhj_type().contains("中智")) {
					if (hjmodel.getEmhj_place() != null) {
						String place = hjmodel.getEmhj_place();
						if (place.contains("意法")) {
							if (place.contains("研发")) {
								hjno = hjno + "STGX";
								hjno = maxNo(place, hjno);
							} else {
								hjno = hjno + "STFB";
								// 数字（从01开始按顺序编排）
								hjno = maxNo(place, hjno);
							}
						} else {
							if (place.contains("纽威")) {
								hjno = "NWGY";
								hjno = maxNo(place, hjno);
							} else if (place.contains("环仪")) {
								hjno = "HYZS";
								hjno = maxNo(place, hjno);
							} else if (place.contains("戈尔")) {
								hjno = "GEFB";
								hjno = maxNo(place, hjno);
							} else if (place.contains("星巴克")) {
								hjno = "XBKFT";
								hjno = maxNo(place, hjno);
							} else if (place.contains("人才高新")) {
								hjno = "RCGX";
								hjno = maxNo(place, hjno);
							} else if (place.contains("人才笋岗")) {
								hjno = "RCSG";
								hjno = maxNo(place, hjno);
							} else if (place.contains("西部")) {
								hjno = "XBNT";
								hjno = maxNo(place, hjno);
							} else if (place.contains("南山")) {
								hjno = "NSNT";
								hjno = maxNo(place, hjno);
							} else if (place.contains("外企")) {
								hjno = "WFNY";
								hjno = maxNo(place, hjno);
							} else if (place.contains("福田")) {
								hjno = "FTST";
								hjno = maxNo(place, hjno);
							}
						}
					}

				} else {
					// 中智集体户
					String maxhjno = bll.getMaxHjNo();
					if (maxhjno == null || maxhjno == "") {
						hjno = "0001";
					} else {
						Integer no = Integer.parseInt(maxhjno) + 1;
						if (no < 10) {
							maxhjno = "000" + no;
						} else if (no < 100 && no > 9) {
							maxhjno = "00" + no;
						} else if (no < 1000 && no > 99) {
							maxhjno = "0" + no;
						} else {
							maxhjno = "" + no;
						}
						hjno = maxhjno + "";
					}
				}
			}
		}
		return hjno;
	}

	private String maxNo(String place, String hjno) {
		String rehjno = "";
		if (place != null && !place.equals("")) {
			String maxhjno = bll.getMaxHjNos(hjno, hjmodel.getCid());
			if (maxhjno == null || maxhjno == "") {
				rehjno = hjno = hjno + "01";
			} else {
				Integer no = Integer.parseInt(maxhjno) + 1;
				if (no < 10) {
					maxhjno = "0" + no;
				} else {
					maxhjno = "" + no;
				}
				rehjno = hjno + maxhjno + "";
			}
		}
		return rehjno;
	}

	private String initHjno() {
		String hjno = "";
		if (hjmodel.getEmhj_type() != null
				&& !hjmodel.getEmhj_type().contains("中智")) {
			String place = "";
			// 独立户
			// 获取公司简称首字母的大写
			if (hjmodel.getCoba_shortname() != null
					&& !hjmodel.getCoba_shortname().equals("")) {
				pinyin4jUtil ul = new pinyin4jUtil();
				// 获取公司简称的首字母并转为大写字母
				hjno = hjno
						+ ul.getPinYinHeadChar(hjmodel.getCoba_shortname())
								.toUpperCase();
			}
			// 获取派出所首字母的大写字母
			if (hjmodel.getEmhj_place() != null
					&& !hjmodel.getEmhj_place().equals("")) {
				pinyin4jUtil ul = new pinyin4jUtil();
				place = hjmodel.getEmhj_place();
				// String
				// place=hjmodel.getEmhj_place().replace("派出所","");
				// place=place.replace("所","");
				hjno = hjno + ul.getPinYinHeadChar(place).toUpperCase();
			}
			// 数字（从01开始按顺序编排）
			if (place != null && !place.equals("")) {
				String maxhjno = bll.getMaxHjNos(hjno, hjmodel.getCid());
				if (maxhjno == null || maxhjno == "") {
					hjno = hjno + "01";
				} else {
					Integer no = Integer.parseInt(maxhjno) + 1;
					if (no < 10) {
						maxhjno = "0" + no;
					} else {
						maxhjno = "" + no;
					}

					hjno = hjno + maxhjno + "";
				}
			}
		} else {
			// 中智集体户
			String maxhjno = bll.getMaxHjNo();
			if (maxhjno == null || maxhjno == "") {
				hjno = "0001";
			} else {
				Integer no = Integer.parseInt(maxhjno) + 1;
				if (no < 10) {
					maxhjno = "000" + no;
				} else if (no < 100 && no > 9) {
					maxhjno = "00" + no;
				} else if (no < 1000 && no > 99) {
					maxhjno = "0" + no;
				} else {
					maxhjno = "" + no;
				}
				hjno = maxhjno + "";
			}
		}
		return hjno;
	}

	// 账户类型选择事项
	@Command
	@NotifyChange("tyvis")
	public void typechange() {
		if (emcensustype != null && !emcensustype.equals("")) {
			if (emcensustype.contains("客户")) {
				tyvis = true;
			} else {
				tyvis = false;
			}
		}
	}

	public boolean isVisable() {
		return visable;
	}

	public void setVisable(boolean visable) {
		this.visable = visable;
	}

	public String getDaaddress() {
		return daaddress;
	}

	public void setDaaddress(String daaddress) {
		this.daaddress = daaddress;
	}

	public String getHjaddress() {
		return hjaddress;
	}

	public void setHjaddress(String hjaddress) {
		this.hjaddress = hjaddress;
	}

	public String getHjno() {
		return hjno;
	}

	public void setHjno(String hjno) {
		this.hjno = hjno;
	}

	public String getLogname() {
		return logname;
	}

	public void setLogname(String logname) {
		this.logname = logname;
	}

	public boolean isIdcardvis() {
		return idcardvis;
	}

	public void setIdcardvis(boolean idcardvis) {
		this.idcardvis = idcardvis;
	}

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public String getEmcensustype() {
		return emcensustype;
	}

	public void setEmcensustype(String emcensustype) {
		this.emcensustype = emcensustype;
	}

	public String getEmcensustypename() {
		return emcensustypename;
	}

	public void setEmcensustypename(String emcensustypename) {
		this.emcensustypename = emcensustypename;
	}

	public boolean isTyvis() {
		return tyvis;
	}

	public void setTyvis(boolean tyvis) {
		this.tyvis = tyvis;
	}

	public EmCensusModel getEmba() {
		return emba;
	}

	public void setEmba(EmCensusModel emba) {
		this.emba = emba;
	}

	// 判断字符串是否都是数字
	public static boolean isNum(String str) {
		return str.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");
	}
}
