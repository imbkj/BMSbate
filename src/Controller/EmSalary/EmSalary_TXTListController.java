package Controller.EmSalary;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import bll.EmSalary.EmSalary_HisSelBll;
import bll.EmSalary.ItemFormula_SelectBll;

import Model.EmPayModel;
import Model.EmTXTFileInfoModel;
import Util.CheckString;
import Util.DateStringChange;

public class EmSalary_TXTListController {
	private List<EmTXTFileInfoModel> txtDateList;
	private List<EmTXTFileInfoModel> txtBatchList;
	private List<EmTXTFileInfoModel> dataList;
	private List<EmTXTFileInfoModel> sameDataList;
	private ItemFormula_SelectBll ifSBll = new ItemFormula_SelectBll();
	private EmSalary_HisSelBll hisSbll = new EmSalary_HisSelBll();
	// 判断选择的txtdate是否为当日
	private boolean chkdate = false;
	private EmTXTFileInfoModel nowDate = new EmTXTFileInfoModel();

	private int total_count = 0;
	private BigDecimal total_pay = new BigDecimal(0);
	private int total_ep_count = 0;
	private BigDecimal total_empay = new BigDecimal(0);
	private int all_total_count = 0;
	private BigDecimal all_total_pay = new BigDecimal(0);
	private List<EmPayModel> empaList;

	private BigDecimal balance = new BigDecimal(0);
	private BigDecimal remaining = new BigDecimal(0);
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	private Date date = new Date();
	private String nowtime = sdf.format(date);

	public EmSalary_TXTListController() {
		txtDateList = ifSBll.getTXTDateList();

		if (txtDateList.size() > 0) {
			// 获取当天日期
			// nowDate = txtDateList.get(0);
			for (int i = 0; i < txtDateList.size(); i++) {
				if (nowtime.equals(txtDateList.get(i).getEtfi_txt_date())) {
					nowDate = txtDateList.get(i);
					break;
				}
			}
		}
		// 刷新数据
		select(nowDate.getEtfi_txt_date(), "", "", "");
	}

	// 查询
	@Command("search")
	@NotifyChange({ "txtBatchList", "dataList", "sameDataList", "total_pay",
			"total_count", "total_empay", "total_ep_count", "all_total_count",
			"chkdate", "all_total_pay" })
	public void search(@BindingParam("txt_date") Combobox txt_date,
			@BindingParam("txt_batch") Combobox txt_batch,
			@BindingParam("etfi_state") Combobox etfi_state,
			@BindingParam("emba_name") Textbox emba_name) {
		if (txt_date.getSelectedItem() != null) {
			String batch = "";
			String state = "";
			String emName = "";

			if (txt_batch.getSelectedItem() != null) {
				batch = txt_batch.getSelectedItem().getValue().toString();
			}
			if (etfi_state.getSelectedItem() != null) {
				state = String.valueOf(etfi_state.getSelectedItem().getValue());
			}
			if (!"".equals(emba_name.getValue())) {
				emName = emba_name.getValue();
			}
			// 调用方法
			select(txt_date.getSelectedItem().getLabel().toString(), batch,
					state, emName);
		}
	}

	// 修改账户名 或 删除
	@Command("editTXT")
	@NotifyChange({ "dataList", "sameDataList", "total_pay", "total_count",
			"total_empay", "total_ep_count", "all_total_count", "all_total_pay" })
	public void editTXT(@BindingParam("url") String url,
			@BindingParam("etfiM") EmTXTFileInfoModel etfiM,
			@BindingParam("txt_date") Combobox txt_date,
			@BindingParam("txt_batch") Combobox txt_batch,
			@BindingParam("etfi_state") Combobox etfi_state,
			@BindingParam("emba_name") Textbox emba_name) {
		// 专递csiiM
		Map map = new HashMap();
		map.put("etfiM", etfiM);
		Window window = (Window) Executions.createComponents(url, null, map);
		window.doModal();

		// 刷新数据
		String batch = "";
		String state = "";
		String emName = "";

		if (txt_batch.getSelectedItem() != null) {
			batch = txt_batch.getSelectedItem().getValue().toString();
		}
		if (etfi_state.getSelectedItem() != null) {
			state = String.valueOf(etfi_state.getSelectedItem().getValue());
		}
		if (!"".equals(emba_name.getValue())) {
			emName = emba_name.getValue();
		}
		select(txt_date.getSelectedItem().getLabel().toString(), batch, state,
				emName);

	}

	// 生成报盘文件
	@Command("createTXT")
	@NotifyChange({ "dataList", "sameDataList" })
	public void createTXT(@BindingParam("txt_date") Combobox txt_date,
			@BindingParam("txt_batch") Combobox txt_batch,
			@BindingParam("etfi_state") Combobox etfi_state,
			@BindingParam("emba_name") Textbox emba_name) {

		if (balance.compareTo(BigDecimal.valueOf(0)) == 1
				&& balance.compareTo(all_total_pay) >= 0) {

			// 专递csiiM
			Map map = new HashMap();
			map.put("txt_date", txt_date.getSelectedItem().getLabel());
			Window window = (Window) Executions.createComponents(
					"EmSalary_CreateTXT.zul", null, map);
			window.doModal();

			// 刷新数据
			String batch = "";
			String state = "";
			String emName = "";

			if (txt_batch.getSelectedItem() != null) {
				batch = txt_batch.getSelectedItem().getValue().toString();
			}
			if (etfi_state.getSelectedItem() != null) {
				state = String.valueOf(etfi_state.getSelectedItem().getValue());
			}
			if (!"".equals(emba_name.getValue())) {
				emName = emba_name.getValue();
			}
			select(txt_date.getSelectedItem().getLabel().toString(), batch,
					state, emName);
		} else {
			// 弹出提示
			Messagebox.show("当前报盘总金额超过今日银行余额！", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
		}
	}

	// 页面加载
	@NotifyChange({ "txtBatchList", "dataList", "sameDataList", "total_pay",
			"total_count", "total_empay", "total_ep_count", "all_total_count",
			"chkdate", "all_total_pay" })
	public void select(String txt_date, String txt_batch, String etfi_state,
			String emba_name) {
		if (!"".equals(txt_date) && txt_date != null) {
			// 银行余额数据
			try {
				balance = ifSBll.getTXTBalance().getEtfs_balance()
						.setScale(2, BigDecimal.ROUND_HALF_UP);
				remaining = ifSBll.getTXTBalance().getEtfs_remaining()
						.setScale(2, BigDecimal.ROUND_HALF_UP);
			} catch (Exception e) {
				balance = new BigDecimal(0).setScale(2,
						BigDecimal.ROUND_HALF_UP);
				remaining = new BigDecimal(0).setScale(2,
						BigDecimal.ROUND_HALF_UP);
			}

			// 报盘数据统计
			String[] pay = ifSBll.getTXTCountPay(txt_date);
			total_pay = new BigDecimal(pay[0]).setScale(2,
					BigDecimal.ROUND_HALF_UP);
			total_count = Integer.parseInt(pay[1]);

			// 支付模块数据
			empaList = hisSbll.getEmPayList(txt_date);
			if (empaList.size() > 0) {
				total_ep_count = empaList.size();
			}
			total_empay = hisSbll.getEmPayTotal(txt_date).getEmpa_fee();

			all_total_count = total_count + total_ep_count;
			all_total_pay = total_pay.add(total_empay).setScale(2,
					BigDecimal.ROUND_HALF_UP);

			// 获取txtBatchList下拉框列表
			txtBatchList = ifSBll.getTXTBatchList(txt_date);

			// 获取dataList下拉框列表
			String sql = "";
			sql = sql + " AND DATEDIFF(d,etfi_txt_date,'" + txt_date + "')=0";
			if (txt_batch != null && !"".equals(txt_batch)) {
				sql = sql + " AND etfi_txt_batch=" + txt_batch;
			}

			if (etfi_state != null && !"".equals(etfi_state)) {
				sql = sql + " AND etfi_state=" + String.valueOf(etfi_state);
			}

			if (emba_name != null && !"".equals(emba_name)) {
				if (CheckString.isNum(emba_name)) {
					sql = sql + " AND gid=" + emba_name;
				} else if (CheckString.isChinese(emba_name)) {
					sql = sql + " AND emba_name like '%" + emba_name + "%'";
				}
			}

			dataList = ifSBll.getTXTDataList(sql);
			sameDataList = ifSBll.getTXTDataList(sql
					+ " AND (etfi_same_ban<>0 OR etfi_same_ba<>0)");

			if (dataList.size() > 0) {
				if (DateStringChange
						.StringtoDate(nowDate.getEtfi_txt_date(), "yyyy-MM-dd")
						.toString()
						.equals(DateStringChange.StringtoDate(
								dataList.get(0).getEtfi_txt_date(),
								"yyyy-MM-dd").toString())) {
					chkdate = true;
				} else {
					chkdate = false;
				}
			}
		}
	}

	public EmTXTFileInfoModel getNowDate() {
		return nowDate;
	}

	public void setNowDate(EmTXTFileInfoModel nowDate) {
		this.nowDate = nowDate;
	}

	public boolean getChkdate() {
		return chkdate;
	}

	public void setChkdate(boolean chkdate) {
		this.chkdate = chkdate;
	}

	public List<EmTXTFileInfoModel> getTxtDateList() {
		return txtDateList;
	}

	public void setTxtDateList(List<EmTXTFileInfoModel> txtDateList) {
		this.txtDateList = txtDateList;
	}

	public List<EmTXTFileInfoModel> getTxtBatchList() {
		return txtBatchList;
	}

	public void setTxtBatchList(List<EmTXTFileInfoModel> txtBatchList) {
		this.txtBatchList = txtBatchList;
	}

	public List<EmTXTFileInfoModel> getDataList() {
		return dataList;
	}

	public void setDataList(List<EmTXTFileInfoModel> dataList) {
		this.dataList = dataList;
	}

	public List<EmTXTFileInfoModel> getSameDataList() {
		return sameDataList;
	}

	public void setSameDataList(List<EmTXTFileInfoModel> sameDataList) {
		this.sameDataList = sameDataList;
	}

	public int getTotal_count() {
		return total_count;
	}

	public void setTotal_count(int total_count) {
		this.total_count = total_count;
	}

	public BigDecimal getTotal_pay() {
		return total_pay;
	}

	public void setTotal_pay(BigDecimal total_pay) {
		this.total_pay = total_pay;
	}

	public int getTotal_ep_count() {
		return total_ep_count;
	}

	public void setTotal_ep_count(int total_ep_count) {
		this.total_ep_count = total_ep_count;
	}

	public BigDecimal getTotal_empay() {
		return total_empay;
	}

	public void setTotal_empay(BigDecimal total_empay) {
		this.total_empay = total_empay;
	}

	public int getAll_total_count() {
		return all_total_count;
	}

	public void setAll_total_count(int all_total_count) {
		this.all_total_count = all_total_count;
	}

	public BigDecimal getAll_total_pay() {
		return all_total_pay;
	}

	public void setAll_total_pay(BigDecimal all_total_pay) {
		this.all_total_pay = all_total_pay;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public BigDecimal getRemaining() {
		return remaining;
	}

	public void setRemaining(BigDecimal remaining) {
		this.remaining = remaining;
	}

}
