package Controller.EmCommissionOut.Template.Import;

import java.io.File;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import bll.EmCommissionOut.EmCommissionOutListBll;
import bll.EmCommissionOut.EmCommissionOut_OperateBll;

import Model.EmCommissionOutPayUpdateCRTModel;
import Util.DateStringChange;
import Util.FileOperate;
import Util.UserInfo;
import Util.plyUtil;

public class EmOut_Tem_Import_ModController {

	private EmCommissionOutPayUpdateCRTModel m = new EmCommissionOutPayUpdateCRTModel();
	private List<EmCommissionOutPayUpdateCRTModel> fieldList;

	Integer daid = 0;
	Integer oldtitlerow = 0;
	String oldecutname = "";

	private Boolean ifsub = false;
	private String statename;

	public EmOut_Tem_Import_ModController() {
		try {
			daid = Integer.parseInt(Executions.getCurrent().getArg()
					.get("daid").toString());

			init();
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox
					.show("页面加载出错!", "ERROR", Messagebox.OK, Messagebox.ERROR);
		}
	}

	/**
	 * 页面初始化
	 * 
	 */
	public void init() {
		try {
			EmCommissionOutListBll bll = new EmCommissionOutListBll();
			setFieldList(bll.getFieldList(""));
			setM(bll.getEmOutPayUpdateT(" and ecut_id=" + daid).get(0));
			m.setcList(bll.getEmOutPayUpdateC(" and ecuc_ecut_id=" + daid));
			setStatename(m.getEcut_state() == 1 ? "可用" : "禁用");

			for (EmCommissionOutPayUpdateCRTModel ecm : m.getcList()) {
				ecm.setFieldList(fieldListCopy(ecm, null));
				if (ecm.getEcuc_ecpr_id() != null) {
					for (EmCommissionOutPayUpdateCRTModel fm : ecm
							.getFieldList()) {
						if (fm != null) {
							if (ecm.getEcuc_ecpr_id().equals(fm.getEcpr_id())) {
								ecm.setFieldModel(fm);
								break;
							}
						}
					}
				}
			}
			// fieldSelect();

			oldtitlerow = m.getEcut_titlerow();
			oldecutname = m.getEcut_name();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 检查模板名称是否存在
	 * 
	 */
	@Command("checkname")
	@NotifyChange({ "m" })
	public void isexist_name() {
		EmCommissionOut_OperateBll bll = new EmCommissionOut_OperateBll();

		if (bll.isExist_Ecut_name(m)) {
			Messagebox.show("该模板名称已存在!", "ERROR", Messagebox.OK,
					Messagebox.ERROR);
			m.setEcut_name(oldecutname);
		} else if (m.getEcut_name() == null || m.getEcut_name().isEmpty()) {
			Messagebox.show("请输入模板名称!", "ERROR", Messagebox.OK,
					Messagebox.ERROR);
		} else {
			oldecutname = m.getEcut_name();
		}
	}

	/**
	 * 上传excel
	 * 
	 * @param media
	 */
	@SuppressWarnings("unchecked")
	@Command("browse")
	@NotifyChange({ "filename", "ifsub", "m" })
	public void browse(@BindingParam("media") Media media) {
		String format = media.getFormat();
		String realfilename = "";
		if (format.equals("xls")) {
			m.getcList().clear();
			m.setEcut_excelname(media.getName());

			// 上传excel文件
			try {
				m.setEcut_localexcelname("Pay"
						+ DateStringChange.Datestringnow("yyyyMMddhhmmss")
						+ UserInfo.getUserid() + ".xls");
				realfilename = "EmCommissionOut/File/Upload/Pay/"
						+ m.getEcut_localexcelname();
				FileOperate.upload(media, realfilename);

			} catch (Exception e) {
				e.printStackTrace();
				Messagebox.show("错误：" + e.toString(), "ERROR", Messagebox.OK,
						Messagebox.ERROR);
			}

			// 读取上传的excel文件
			File file = new File(getAbsolutePath() + realfilename);
			Object[] obj = plyUtil.readExcel(file, m.getEcut_titlerow());

			if (obj[0].equals("0")) {
				Messagebox.show("错误：" + obj[1], "ERROR", Messagebox.OK,
						Messagebox.ERROR);
				setIfsub(true);
			} else if (obj[0].equals("1")) {
				List<String> exceltitleList = (List<String>) obj[2];
				for (int i = 0; i < exceltitleList.size(); i++) {
					EmCommissionOutPayUpdateCRTModel ecm = new EmCommissionOutPayUpdateCRTModel();
					ecm.setEcuc_excel_title(exceltitleList.get(i));
					ecm.setFieldList(fieldListCopy(ecm, null));

					m.getcList().add(ecm);
				}
				setIfsub(false);
			}
		} else if (format.equals("xlsx")) {
			Messagebox.show("请将此excel文件另存为*.xls后，再尝试进行导入", "ERROR",
					Messagebox.OK, Messagebox.ERROR);
		} else {
			Messagebox.show("此文件不是excel,請檢查!", "ERROR", Messagebox.OK,
					Messagebox.ERROR);
		}
	}

	@SuppressWarnings("unchecked")
	@Command("titlerowchange")
	@NotifyChange({ "ifsub", "m" })
	public void titlerowchange() {

		while (true) {
			// 读取上传的excel文件
			File file = new File(getAbsolutePath()
					+ "EmCommissionOut/File/Upload/Pay/"
					+ m.getEcut_localexcelname());
			Object[] obj = plyUtil.readExcel(file, m.getEcut_titlerow());

			if (obj[0].equals("0")) {
				Messagebox.show("错误：" + obj[1], "ERROR", Messagebox.OK,
						Messagebox.ERROR);
				setIfsub(true);
				m.setEcut_titlerow(oldtitlerow);
			} else if (obj[0].equals("1")) {
				m.getcList().clear();
				List<String> exceltitleList = (List<String>) obj[2];
				for (int i = 0; i < exceltitleList.size(); i++) {
					EmCommissionOutPayUpdateCRTModel ecm = new EmCommissionOutPayUpdateCRTModel();
					ecm.setEcuc_excel_title(exceltitleList.get(i));
					ecm.setFieldList(fieldListCopy(ecm, null));

					m.getcList().add(ecm);
				}
				setIfsub(false);
				oldtitlerow = m.getEcut_titlerow();

				break;
			}
		}
	}

	/**
	 * 在字段下拉框中去除已选过的项目
	 * 
	 */
	@Command("fieldSelect")
	@NotifyChange({ "m" })
	public void fieldSelect() {
		try {
			for (EmCommissionOutPayUpdateCRTModel ecm : m.getcList()) {
				ecm.setFieldList(fieldListCopy(ecm, getSelectedList()));

				if (ecm.getFieldModel() != null) {
					for (EmCommissionOutPayUpdateCRTModel fm : ecm
							.getFieldList()) {
						if (fm != null) {
							if (ecm.getFieldModel().getEcpr_id()
									.equals(fm.getEcpr_id())) {
								ecm.setFieldModel(fm);
								break;
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 项目绝对路径
	 * 
	 * @return
	 */

	public String getAbsolutePath() {
		return Executions.getCurrent().getDesktop().getWebApp()
				.getRealPath("/").replace("\\", "/");
	}

	/**
	 * 获取fieldList
	 * 
	 * @param m
	 * @param selectedList
	 *            已选项目列表
	 * @return
	 */
	public List<EmCommissionOutPayUpdateCRTModel> fieldListCopy(
			EmCommissionOutPayUpdateCRTModel m, List<Integer> selectedList) {
		List<EmCommissionOutPayUpdateCRTModel> list = new ListModelList<>();

		try {
			if (m.getFieldModel() != null) {
				selectedList.remove(m.getFieldModel().getEcpr_id());
			}

			for (EmCommissionOutPayUpdateCRTModel fm : fieldList) {
				if (selectedList == null
						|| !selectedList.contains(fm.getEcpr_id())) {
					EmCommissionOutPayUpdateCRTModel fm1 = new EmCommissionOutPayUpdateCRTModel();

					fm1.setEcpr_id(fm.getEcpr_id());
					fm1.setEcpr_ecpu_field(fm.getEcpr_ecpu_field());
					fm1.setEcpr_sicl_id(fm.getEcpr_sicl_id());
					fm1.setEcpr_ecpu_fieldstr(fm.getEcpr_ecpu_fieldstr());

					list.add(fm1);
				}
			}

			if (list.size() > 0) {
				list.add(0, null);

				EmCommissionOutPayUpdateCRTModel fm1 = new EmCommissionOutPayUpdateCRTModel();
				fm1.setEcpr_id(0);
				fm1.setEcpr_ecpu_fieldstr("(表头)" + m.getEcuc_excel_title());
				list.add(1, fm1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	/**
	 * 获取已选的项目
	 * 
	 * @return
	 */
	public List<Integer> getSelectedList() {
		List<Integer> selectedList = new ListModelList<>();

		try {
			for (EmCommissionOutPayUpdateCRTModel fm : m.getcList()) {
				if (fm.getFieldModel() != null) {
					selectedList.add(fm.getFieldModel().getEcpr_id());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return selectedList;
	}

	/**
	 * 提交
	 * 
	 * @param window
	 */
	@Command("submit")
	public void submit(@BindingParam("win") Window window) {
		EmCommissionOut_OperateBll bll = new EmCommissionOut_OperateBll();

		if (m.getcList().size() == 0) {
			Messagebox.show("请导入模板excel!", "ERROR", Messagebox.OK,
					Messagebox.ERROR);
		} else {
			try {
				setcList();
				if (checkSubmit(m.getcList())) {
					m.setEcut_addname(UserInfo.getUsername());
					m.setEcut_state(statename.equals("可用") ? 1 : 0);

					String[] str = bll.EmOutPayUpdateTCMod(m);

					if (str[0].equals("1")) {
						Messagebox.show(str[1], "INFORMATION", Messagebox.OK,
								Messagebox.INFORMATION);
						window.detach();
					} else {
						Messagebox.show(str[1], "ERROR", Messagebox.OK,
								Messagebox.ERROR);
					}
				} else {
					Messagebox.show("身份证或付款起始月份未匹配!", "ERROR", Messagebox.OK,
							Messagebox.ERROR);
				}
			} catch (Exception e) {
				e.printStackTrace();
				Messagebox.show("提交出错!", "ERROR", Messagebox.OK,
						Messagebox.ERROR);
			}
		}
	}

	/**
	 * 处理cList
	 * 
	 */
	public void setcList() {
		List<EmCommissionOutPayUpdateCRTModel> cList = m.getcList();

		for (int i = 0; i < cList.size(); i++) {
			EmCommissionOutPayUpdateCRTModel ecm = cList.get(i);

			ecm.setEcuc_ecpr_id(ecm.getFieldModel() == null ? null : ecm
					.getFieldModel().getEcpr_id());
			ecm.setEcuc_addname(UserInfo.getUsername());
		}
	}

	/**
	 * 检查模板必须匹配项是否匹配(身份证、起始月)
	 * 
	 * @param list
	 *            匹配列表
	 * @return
	 */
	public boolean checkSubmit(List<EmCommissionOutPayUpdateCRTModel> list) {
		boolean if_idcard = false;
		boolean if_ownmonth = false;

		for (EmCommissionOutPayUpdateCRTModel crtM : list) {
			if (crtM.getEcuc_ecpr_id() != null
					&& crtM.getEcuc_ecpr_id().equals(4)) {
				if_idcard = true;
			}
			if (crtM.getEcuc_ecpr_id() != null
					&& crtM.getEcuc_ecpr_id().equals(6)) {
				if_ownmonth = true;
			}
		}

		if (if_idcard && if_ownmonth) {
			return true;
		} else {
			return false;
		}
	}

	public final EmCommissionOutPayUpdateCRTModel getM() {
		return m;
	}

	public final List<EmCommissionOutPayUpdateCRTModel> getFieldList() {
		return fieldList;
	}

	public final void setM(EmCommissionOutPayUpdateCRTModel m) {
		this.m = m;
	}

	public final void setFieldList(
			List<EmCommissionOutPayUpdateCRTModel> fieldList) {
		this.fieldList = fieldList;
	}

	public Boolean getIfsub() {
		return ifsub;
	}

	public void setIfsub(Boolean ifsub) {
		this.ifsub = ifsub;
	}

	public String getStatename() {
		return statename;
	}

	public void setStatename(String statename) {
		this.statename = statename;
	}
}
