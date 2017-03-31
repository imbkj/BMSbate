package Controller.CoSocialInsurance;

import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import bll.CoSocialInsurance.CoSocialInsurance_ListBll;
import bll.CoSocialInsurance.CoSocialInsurance_OperateBll;

import Model.CoCompactModel;
import Model.CoShebaoChangeModel;

public class CoSocialInsurance_UpdateCompactController {

	private List<CoCompactModel> compactList = new ListModelList<>();
	private CoCompactModel compactModel = new CoCompactModel();
	private CoShebaoChangeModel m = new CoShebaoChangeModel();
	Integer daid;

	private Double per1;
	private Double per2;

	public CoSocialInsurance_UpdateCompactController() {
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
	 * 数据初始化
	 * 
	 */
	public void init() {
		CoSocialInsurance_ListBll bll = new CoSocialInsurance_ListBll();
		try {
			setM(bll.getCoShebaoChangeInfo(daid));
			setCompactList(bll.getCompactList(m.getCid()));
			compactList.add(0, null);
			setCompactModel(compactList.get(0));

			setPer1(m.getCsbc_unemployment_per() * 100);
			setPer2(m.getCsbc_business_per() * 100);

			if_submit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 判断是否可以提交
	 * 
	 */
	@Command
	@NotifyChange({ "m" })
	public void if_submit() {
		try {
			if (compactModel == null) {
				m.setIf_submit(true);
			} else {
				m.setIf_submit(false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 提交
	 * 
	 * @param win
	 */
	@Command
	public void submit(@BindingParam("win") Window win) {
		CoSocialInsurance_OperateBll bll = new CoSocialInsurance_OperateBll();
		try {
			m.setCoco_id(compactModel.getCoco_id());
			m.setCoco_compactid(compactModel.getCoco_compactid());

			String[] str = bll.UpdateCompact(m);

			if (str[0].equals("1")) {
				Messagebox.show(str[1], "INFORMATION", Messagebox.OK,
						Messagebox.INFORMATION);
				win.detach();
			} else {
				Messagebox.show(str[1], "ERROR", Messagebox.OK,
						Messagebox.ERROR);
			}

		} catch (Exception e) {
			Messagebox.show("提交出错!", "ERROR", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}

	public List<CoCompactModel> getCompactList() {
		return compactList;
	}

	public void setCompactList(List<CoCompactModel> compactList) {
		this.compactList = compactList;
	}

	public CoShebaoChangeModel getM() {
		return m;
	}

	public void setM(CoShebaoChangeModel m) {
		this.m = m;
	}

	public CoCompactModel getCompactModel() {
		return compactModel;
	}

	public void setCompactModel(CoCompactModel compactModel) {
		this.compactModel = compactModel;
	}

	public Double getPer1() {
		return per1;
	}

	public void setPer1(Double per1) {
		this.per1 = per1;
	}

	public Double getPer2() {
		return per2;
	}

	public void setPer2(Double per2) {
		this.per2 = per2;
	}
}
