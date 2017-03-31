package Controller.EmBenefit;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import Model.EmActyContactContentInfoModel;
import Model.EmActySuppilerGiftInfoModel;
import Model.EmActyWarehouseModel;
import Model.EmWelfareModel;
import Model.EmactyUseHouseLogModel;
import Util.UserInfo;
import bll.EmBenefit.EmActy_GiftInfoOperateBll;
import bll.EmBenefit.EmActy_NewOperateBll;
import bll.EmBenefit.EmActy_NewOperateFactory;
import bll.EmBenefit.EmActy_NewOperateFactoryImpl;
import bll.EmBenefit.EmActy_NewOperateService;
import bll.EmBenefit.EmActy_NewSelectBll;

public class EmActy_GiftBuysController {
	private Integer id = Integer.valueOf(Executions.getCurrent().getArg()
			.get("daid").toString());
	private Integer tapr_id = Integer.valueOf(Executions.getCurrent().getArg()
			.get("id").toString());
	private EmActy_NewSelectBll bll = new EmActy_NewSelectBll();
	private List<EmWelfareModel> list = new ArrayList<EmWelfareModel>();
	private EmActySuppilerGiftInfoModel gm = new EmActySuppilerGiftInfoModel();
	private String username = UserInfo.getUsername();
	private String remark;
	private Date buydate;
	private List<EmactyUseHouseLogModel> ulist;

	public EmActy_GiftBuysController() {
		gm = bll.getGiftInfo(id);
		if (gm != null && gm.getGift_sortid() != null) {
			String str=" and (emwf_state=5 or emwf_state=10)";
			list = bll.getEmWelfareInfoListBySortId(gm.getGift_sortid(),str);
			ulist=bll.getEmactyUseHouseLogModel(gm.getGift_sortid());
			sum();
		}
	}

	// 计算金额
	private void sum() {
		for (EmWelfareModel m : list) {
			if(m.getProd_discount()==null)
			{
				m.setProd_discount(BigDecimal.ZERO);
			}
			BigDecimal buy_num=BigDecimal.ZERO;
			if(m.getEmwf_producenum()!=null)
			{
				buy_num=new BigDecimal(m.getEmwf_producenum());
			}
			if(m.getProd_unit()!=null&&m.getProd_unit().equals("张"))
			{
				m.setEmwf_price(m.getProd_discount().multiply(buy_num));
			}
			else
			{
				m.setEmwf_price(buy_num);
			}
			
			//减去已使用库存金额
			for(EmactyUseHouseLogModel um:ulist)
			{
				boolean flag=false;
				if(m.getEmwf_prod_id().equals(um.getUseh_prod_id()))
				{
					if(m.getEmwf_prty_id()!=null&&um.getUseh_prty_id()!=null)
					{
						if(m.getEmwf_prty_id().equals(um.getUseh_prty_id()))
						{
							flag=true;
						}
					}else if((m.getEmwf_prty_id()==null || m.getEmwf_prty_id()==0)
							&&(um.getUseh_prty_id()==null || um.getUseh_prty_id()==0))
					{
						flag=true;
					}
				}
				if(flag)
				{
					if(um.getUseh_price()==null)
					{
						um.setUseh_price(BigDecimal.ZERO);
					}
					m.setEmwf_buyprice(m.getEmwf_price().subtract(um.getUseh_price()));
					m.setBuy_num(m.getEmwf_producenum()-um.getUseh_num());
					m.setUseh_num(um.getUseh_num());
					m.setUseh_price(um.getUseh_price());
					break;
				}
			}
		}
	}

	// 更新采购申请信息并更新任务单
	@Command
	public void EditGift(@BindingParam("win") Window win) {
		EmActy_NewOperateBll bybll = new EmActy_NewOperateBll();
		if (buydate == null) {
			Messagebox.show("请选择采购时间", "提示", Messagebox.OK, Messagebox.ERROR);
			return;
		}
		int k = bybll.buyEmWelfareInfo(gm.getGift_sortid());
		if (k > 0) {
			//添加库存
			EmActy_GiftInfoOperateBll obll = new EmActy_GiftInfoOperateBll();
			for (EmWelfareModel m : list) {
				EmActyWarehouseModel wamodel = new EmActyWarehouseModel();
				wamodel.setWase_name(m.getProd_name());
				if(m.getPrty_name()!=null&&!m.getPrty_name().equals(""))
				{
					wamodel.setWase_name(wamodel.getWase_name()+"("+m.getPrty_name()+")");
				}
				wamodel.setWase_addname(username);
				wamodel.setWase_totalnum(m.getEmwf_producenum());
				wamodel.setWase_prod_id(m.getEmwf_prod_id());
				wamodel.setWase_prty_id(m.getEmwf_prty_id());
				wamodel.setWase_unit(m.getProd_unit());
				/*****暂时取消库存的添加******/
				//Integer hsry_wase_id = obll.EmActyWarehouse(wamodel);
			}
			String sql = "";
			sql = ",gift_buytime='" + datechange(buydate) + "',gift_buyname='"
					+ UserInfo.getUsername() + "',gift_state=4,gift_remark='"
					+ remark + "'";
			EmActySuppilerGiftInfoModel m = new EmActySuppilerGiftInfoModel();
			m.setGift_id(gm.getGift_id());
			m.setGift_remark("采购礼品");
			m.setGift_tarpid(gm.getGift_tarpid());
			// 任务单处理
			EmActy_NewOperateFactory factory = new EmActy_NewOperateFactoryImpl();
			EmActy_NewOperateService service = factory
					.operateFactory("下一步");
			String[] str = service.edit(m, sql);
			if (str[0] == "1") {
				Messagebox.show("提交成功", "提示", Messagebox.OK,
						Messagebox.INFORMATION);
				win.detach();
			} else {
				Messagebox.show("发放失败", "提示", Messagebox.OK,
						Messagebox.ERROR);
			}
		} else {
			Messagebox.show("提交时报", "提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	public List<EmWelfareModel> getList() {
		return list;
	}

	public void setList(List<EmWelfareModel> list) {
		this.list = list;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getBuydate() {
		return buydate;
	}

	public void setBuydate(Date buydate) {
		this.buydate = buydate;
	}

	private String datechange(Date d) {
		String date = "";
		if (d != null && !d.equals("")) {
			SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			date = time.format(d);
		}
		return date;
	}
}
