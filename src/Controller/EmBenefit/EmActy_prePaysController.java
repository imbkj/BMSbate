package Controller.EmBenefit;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import Model.EmActyProduceModel;
import Model.EmActySuppilerGiftInfoModel;
import Model.EmActyWarehouseModel;
import Model.EmWelfareModel;
import Model.EmactyUseHouseLogModel;
import bll.EmBenefit.EmActy_GiftInfoOperateBll;
import bll.EmBenefit.EmActy_NewOperateFactory;
import bll.EmBenefit.EmActy_NewOperateFactoryImpl;
import bll.EmBenefit.EmActy_NewOperateService;
import bll.EmBenefit.EmActy_NewSelectBll;
import bll.EmPay.EmPay_OperateBll;

/**
 * @Author:陈耀家
 * @Descript:预付款
 * 
 * */
public class EmActy_prePaysController {
	private Integer id = Integer.valueOf(Executions.getCurrent().getArg()
			.get("daid").toString());
	private Integer tapr_id = Integer.valueOf(Executions.getCurrent().getArg()
			.get("id").toString());
	private BigDecimal prePrice = new BigDecimal(0.0);// 预支付金额
	private BigDecimal allPrice = new BigDecimal(0.0);
	private BigDecimal toPrice = new BigDecimal(0.0);
	private BigDecimal evpay = new BigDecimal(0.0);
	private int nowHouseNum = 0;
	private int useHouseNum = 0;
	private List<EmWelfareModel> list = new ListModelList<>();
	private EmActy_NewSelectBll bll = new EmActy_NewSelectBll();
	private EmActySuppilerGiftInfoModel model = new EmActySuppilerGiftInfoModel();
	private String type = "福利费";
	private boolean hvis = false,vislab=false;
	private List<EmActyWarehouseModel> hlist = new ArrayList<EmActyWarehouseModel>();
	private String id_str = "",paytype="";
	private int user_num;

	public EmActy_prePaysController() {
		model = bll.getGiftInfo(id);
		list = bll.getEmWelfareList(model.getGift_sortid());
		princeInit();
	}

	private void princeInit() {
		if (list.size() > 0) {
			for (EmWelfareModel m : list) {
				id_str = id_str + m.getEmwf_id() + ",";
				if (m.getEmwf_price() != null) {
					allPrice = allPrice.add(m.getEmwf_price());
				}
			}
		}
		if (!id_str.equals("")) {
			id_str = id_str.substring(0, id_str.length() - 1);
		}
		hlist = bll.getEmWelfareHouse(id_str);
		user_num=hlist.size();
		toPrice = allPrice;
	}

	@Command
	public void housechange() {
		
	}
	
	@Command
	@NotifyChange("vislab")
	public void selType()
	{
		if(paytype!=null&&paytype.equals("预付款"))
		{
			vislab=true;
		}
		else
		{
			prePrice=BigDecimal.ZERO;
			vislab=false;
		}
	}

	@Command
	@NotifyChange("hvis")
	public void openHouse() {
		/*
		 * Map map=new HashMap<>(); map.put("list", list); Window
		 * window=(Window) Executions.createComponents("", null, map);
		 * window.doModal();
		 */
		hvis = !hvis;
	}

	@Command
	@NotifyChange("allPrice")
	public void useHouse(@BindingParam("hm") EmActyWarehouseModel hm) {
		allPrice = toPrice;
		if (hm.getUse_num() > hm.getWase_nownum()) {
			hm.setUse_num(hm.getWase_nownum());
			subHousePrice(hm);
			Messagebox.show("最多可使用" + hm.getWase_nownum() + hm.getWase_unit()
					+ hm.getWase_name(), "提示", Messagebox.OK, Messagebox.ERROR);
			return;
		}
		String h_sql = "";
		if (hm.getWase_prod_id() != null) {
			h_sql = h_sql + " and emwf_prod_id=" + hm.getWase_prod_id();
		}
		if (hm.getWase_prty_id() != null) {
			h_sql = h_sql + " and emwf_prty_id=" + hm.getWase_prty_id();
		}
		h_sql = h_sql + " and emwf_id in(" + id_str + ")";
		// 查询本次采购该产品的总数量(金额)
		int h_num = bll.getEmwfProdNum(h_sql);
		if (hm.getUse_num() > h_num) {
			hm.setUse_num(h_num);
			subHousePrice(hm);
			Messagebox.show("使用库存数超了采购数。", "提示", Messagebox.OK,
					Messagebox.ERROR);
			return;
		}
		subHousePrice(hm);
	}

	// 总金额减去库存价格
	private void subHousePrice(EmActyWarehouseModel hm) {
		// 计算除去库存后的总金额
		EmActyProduceModel pm = new EmActyProduceModel();
		BigDecimal prod_discount = BigDecimal.ZERO;
		BigDecimal subPrice = BigDecimal.ZERO;
		pm = bll.getEmwfProdPrice(hm.getWase_prod_id());
		if (pm.getProd_unit().equals("张")) {
			subPrice = pm.getProd_discount().multiply(
					new BigDecimal(hm.getUse_num()));
		} else {
			subPrice = new BigDecimal(hm.getUse_num());
		}
		hm.setPrice(subPrice);
		allPrice = allPrice.subtract(subPrice);
	}

	// 填写预付金额时候限制不能大于总金额
	@Command
	public void paychange() {
		if(prePrice==null)
			prePrice=BigDecimal.ZERO;
		if(allPrice==null)
			allPrice=BigDecimal.ZERO;
		Integer pare = prePrice.compareTo(allPrice);
		if (pare == 1) {
			prePrice = allPrice;
			Messagebox
					.show("预付款不能大于总金额", "提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	@Command
	public void addpayinfo(@BindingParam("win") Window win) {
		if(paytype==null||paytype.equals(""))
		{
			Messagebox.show("请选择付款方式", "提示", Messagebox.OK, Messagebox.ERROR);
			return;
		}
		if (paytype.equals("预付款")&&(prePrice == null||prePrice.compareTo(BigDecimal.ZERO)<=0)) {
			Messagebox.show("预付款金额必须大于0", "提示", Messagebox.OK, Messagebox.ERROR);
			return;
		}
		Integer pare = prePrice.compareTo(allPrice);
		if (pare > 0) {
			Messagebox
					.show("预付款不能大于总金额", "提示", Messagebox.OK, Messagebox.ERROR);
			return;
		}
		BigDecimal totalSize = new BigDecimal(list.size());// 总人数
		evpay = prePrice.divide(totalSize, 2, BigDecimal.ROUND_HALF_UP);// 计算每个人预支付多少钱
		/************** 生成支付通知 **********************/
		EmPay_OperateBll payBll = new EmPay_OperateBll();
		EmActy_GiftInfoOperateBll emwfbll = new EmActy_GiftInfoOperateBll();
		int add_message = 0;
		SimpleDateFormat form = new SimpleDateFormat("yyyyMM");
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddhhmmss");
		Date date = new Date();
		String nowtime = formatter.format(date);
		String paynum = "FL" + nowtime; // 支付单号，
		String ownmonth = form.format(date);
		String[] message = new String[5];
		for (EmWelfareModel m : list) {
			try {
				String emwfsql = "";
				if(paytype.equals("预付款"))
				{
				// 添加支付明细
				add_message = add_message
						+ payBll.add_pay(m.getGid().toString(), m.getCid()
								.toString(), paynum, ownmonth, type, evpay
								.toString(), m.getEmwf_id().toString());
				emwfsql=emwfsql+",emwf_preprice='" + evpay
						+ "',emwf_prepaynum='" + paynum + "'";
				}
				else
				{
					add_message++;
				}
				
				emwfsql =emwfsql+ ",emwf_paytype='"+paytype+"',emwf_state=10";
				// 更新福利信息表的费用
				emwfbll.updateEmWelfareInfo(emwfsql, m.getEmwf_id());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (add_message > 0) {
			try {
				
				String gsql=",gift_totalprice=" + allPrice+",gift_state=4,gift_paytype='"+paytype+"'";
				if(paytype.equals("预付款"))
				{
					// 生成支付通知
					message = payBll.up_pay(paynum, ownmonth, type);
					gsql=gsql+ ",gift_prepay=" + prePrice;
				}
				emwfbll.updateGiftInfo(gsql
						, id);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		/************** 生成支付通知End ***************/
		// 任务单处理
		EmActy_NewOperateFactory factory = new EmActy_NewOperateFactoryImpl();
		EmActy_NewOperateService service = factory.operateFactory("下一步");
		model.setGift_remark("确认付款方式");
		String[] str = service.edit(model, "");
		if (str[0] == "1") {
			//如有使用库存需记录
			for(EmActyWarehouseModel uhm:hlist)
			{
				if(uhm.getUse_num()>0)
				{
					EmactyUseHouseLogModel um=new EmactyUseHouseLogModel();
					um.setUseh_num(uhm.getUse_num());
					um.setUseh_prod_id(uhm.getWase_prod_id());
					um.setUseh_sortid(model.getGift_sortid());
					if(uhm.getWase_prty_id()!=null)
					{
						um.setUseh_prty_id(uhm.getWase_prty_id());
					}
					um.setUseh_price(uhm.getPrice()==null?BigDecimal.ZERO:uhm.getPrice());
					um.setUseh_wase_id(uhm.getWase_id());
					emwfbll.addEmactyUseHouseLog(um);
					
					//减去已使用的库存数
					int now_house_num=uhm.getWase_nownum()-uhm.getUse_num();
					emwfbll.delEmactyUseHouse(uhm.getWase_id(), now_house_num);
				}
			}
			Messagebox
					.show("提交成功", "提示", Messagebox.OK, Messagebox.INFORMATION);
			win.detach();
		} else {
			Messagebox.show("发放失败", "提示", Messagebox.OK, Messagebox.ERROR);
		}
		
	}

	public BigDecimal getPrePrice() {
		return prePrice;
	}

	public void setPrePrice(BigDecimal prePrice) {
		this.prePrice = prePrice;
	}

	public BigDecimal getAllPrice() {
		return allPrice;
	}

	public void setAllPrice(BigDecimal allPrice) {
		this.allPrice = allPrice;
	}

	public int getNowHouseNum() {
		return nowHouseNum;
	}

	public void setNowHouseNum(int nowHouseNum) {
		this.nowHouseNum = nowHouseNum;
	}

	public int getUseHouseNum() {
		return useHouseNum;
	}

	public void setUseHouseNum(int useHouseNum) {
		this.useHouseNum = useHouseNum;
	}

	public List<EmWelfareModel> getList() {
		return list;
	}

	public void setList(List<EmWelfareModel> list) {
		this.list = list;
	}

	public EmActySuppilerGiftInfoModel getModel() {
		return model;
	}

	public void setModel(EmActySuppilerGiftInfoModel model) {
		this.model = model;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isHvis() {
		return hvis;
	}

	public void setHvis(boolean hvis) {
		this.hvis = hvis;
	}

	public List<EmActyWarehouseModel> getHlist() {
		return hlist;
	}

	public void setHlist(List<EmActyWarehouseModel> hlist) {
		this.hlist = hlist;
	}

	public String getPaytype() {
		return paytype;
	}

	public void setPaytype(String paytype) {
		this.paytype = paytype;
	}

	public boolean isVislab() {
		return vislab;
	}

	public void setVislab(boolean vislab) {
		this.vislab = vislab;
	}

	public int getUser_num() {
		return user_num;
	}

	public void setUser_num(int user_num) {
		this.user_num = user_num;
	}
	
}
