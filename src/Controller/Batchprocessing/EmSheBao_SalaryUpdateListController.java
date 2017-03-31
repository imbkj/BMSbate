package Controller.Batchprocessing;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Messagebox;

import bll.Batchprocessing.EmSheBao_BatchBll;

import Model.EmShebaoChangeUploadModel;
import Util.DateStringChange;
import Util.UserInfo;

public class EmSheBao_SalaryUpdateListController {
	private List<EmShebaoChangeUploadModel> ulList;
	private List<EmShebaoChangeUploadModel> winUlList;
	private EmSheBao_BatchBll bll;
	private boolean checkAll = false;
	private int gid = -1;
	private int cid = -1;
	private String state = "";
	private String addtime = "";
	private String addname = "";
	private int ownmonth = 0;
	private String computerid = "";
	private String idcard = "";
	private String name = "";
	private String err = "";
	private List<EmShebaoChangeUploadModel> errlist = null;

	public EmSheBao_SalaryUpdateListController() {
		bll = new EmSheBao_BatchBll();
		winUlList = ulList = bll.getShebaoUploadSalaryUpdateByUser(UserInfo
				.getUsername());
		errlist = bll.getShebaoUploadSalaryUpdateErr(UserInfo.getUsername());
	}

	// 检索
	@Command("search")
	public void search() {
		List<EmShebaoChangeUploadModel> list = new ArrayList<EmShebaoChangeUploadModel>();
		try {
			for (EmShebaoChangeUploadModel m : ulList) {
				try {
					if (gid != -1) {
						if (m.getGid() != gid)
							continue;
					}
					if (cid != -1) {
						if (m.getCid() != cid)
							continue;
					}
					if (ownmonth != 0) {
						if (m.getOwnmonth() != ownmonth)
							continue;
					}
					if (name != null && !"".equals(name)) {
						if (!name.equals(m.getEmsu_name()))
							continue;
					}
					if (idcard != null && !"".equals(idcard)) {
						if (!idcard.equals(m.getEmsu_idcard()))
							continue;
					}
					if (computerid != null && !"".equals(computerid)) {
						if (!computerid.equals(m.getEmsu_computerid()))
							continue;
					}
					if (addname != null && !"".equals(addname)) {
						if (!addname.equals(m.getEmsu_addname()))
							continue;
					}
					if (state != null && !"".equals(state)) {
						if (!state.equals(m.getEmsu_stateStr()))
							continue;
					}
					if (err != null && !"".equals(err)) {
						if (!err.equals(m.getEmsu_err()))
							continue;
					}
					if (addtime != null && !"".equals(addtime)) {
						if (!m.getEmsu_addtime().contains(addtime))
							continue;
					}
					list.add(m);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			winUlList = list;
			BindUtils.postNotifyChange(null, null, this, "winUlList");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 全选
	@Command("checkAll")
	public void checkAll() {
		try {
			for (EmShebaoChangeUploadModel m : winUlList) {
				if (m.getEmsu_state() == 0) {
					m.setCheck(checkAll);
				}
			}
			BindUtils.postNotifyChange(null, null, this, "winUlList");
		} catch (Exception e) {
			Messagebox.show("全选操作出錯。", "操作提示", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}

	// 提交数据
	@Command("submit")
	public void submit() {
		try {
			String[] message = bll.handleShebao(winUlList);
			if ("1".equals(message[0])) {
				// 弹出提示
				Messagebox.show(message[1], "操作提示", Messagebox.OK,
						Messagebox.NONE);
				ulList = bll.getShebaoUploadSalaryUpdateByUser(UserInfo
						.getUsername());
				checkAll = false;
				BindUtils.postNotifyChange(null, null, this, "checkAll");
				errlist = bll.getShebaoUploadSalaryUpdateErr(UserInfo
						.getUsername());
				BindUtils.postNotifyChange(null, null, this, "errlist");
				search();
			} else {
				// 弹出提示
				Messagebox.show(message[1], "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}
		} catch (Exception e) {
			Messagebox.show("提交数据出錯。", "操作提示", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}

	// 删除数据
	@Command("delete")
	public void delete() {
		try {
			String[] message = bll.delBatchUpload(winUlList);
			if ("1".equals(message[0])) {
				// 弹出提示
				Messagebox.show(message[1], "操作提示", Messagebox.OK,
						Messagebox.NONE);
				winUlList = ulList = bll
						.getShebaoUploadSalaryUpdateByUser(UserInfo
								.getUsername());
				BindUtils.postNotifyChange(null, null, this, "winUlList");
				errlist = bll.getShebaoUploadSalaryUpdateErr(UserInfo
						.getUsername());
				BindUtils.postNotifyChange(null, null, this, "errlist");
			} else {
				// 弹出提示
				Messagebox.show(message[1], "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}
		} catch (Exception e) {
			Messagebox.show("删除数据出錯。", "操作提示", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}

	@Command("setCid")
	public void setCid(@BindingParam("ibCid") Intbox ibCid) {
		try {
			if (ibCid.getValue() != null) {
				this.cid = ibCid.getValue();
			} else {
				this.cid = -1;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		search();
	}

	@Command("setGid")
	public void setGid(@BindingParam("ibGid") Intbox ibGid) {
		try {
			if (ibGid.getValue() != null) {
				this.gid = ibGid.getValue();
			} else {
				this.gid = -1;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		search();
	}

	@Command("setOwnmonth")
	public void setOwnmonth(@BindingParam("ibOwnmonth") Intbox ibOwnmonth) {
		try {
			if (ibOwnmonth.getValue() != null && ibOwnmonth.getValue() != 0) {
				this.ownmonth = ibOwnmonth.getValue();
			} else {
				this.ownmonth = 0;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		search();
	}

	@Command("setAddtime")
	public void setAddtime(@BindingParam("dbAddtime") Datebox dbAddtime) {
		try {
			this.addtime = dbAddtime.getValue() != null ? DateStringChange
					.DatetoSting(dbAddtime.getValue(), "yyyy-MM-dd") : "";
		} catch (Exception e) {
			e.printStackTrace();
		}
		search();
	}

	public List<EmShebaoChangeUploadModel> getWinUlList() {
		return winUlList;
	}

	public boolean isCheckAll() {
		return checkAll;
	}

	public void setCheckAll(boolean checkAll) {
		this.checkAll = checkAll;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getAddname() {
		return addname;
	}

	public void setAddname(String addname) {
		this.addname = addname;
	}

	public String getComputerid() {
		return computerid;
	}

	public void setComputerid(String computerid) {
		this.computerid = computerid;
	}

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getErr() {
		return err;
	}

	public void setErr(String err) {
		this.err = err;
	}

	public List<EmShebaoChangeUploadModel> getErrlist() {
		return errlist;
	}

	public void setErrlist(List<EmShebaoChangeUploadModel> errlist) {
		this.errlist = errlist;
	}

}
