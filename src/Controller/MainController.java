package Controller;

import impl.UserInfoImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Desktop;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zkmax.zul.Portallayout;
import org.zkoss.zul.Include;
import org.zkoss.zul.Panel;
import org.zkoss.zul.Window;

import bll.MainPageBll;
import bll.SystemControl.AlarmAllotBll;

import service.UserInfoService;

import Model.PubCallModel;
import Util.CookieUtil;
import Util.Log4jInit;
import Util.UserInfo;

public class MainController {

	private List<PubCallModel> pubcallList;
	private boolean alarmflash=false;
	private Desktop _desktop;
	
	Session session = Executions.getCurrent().getDesktop().getSession();
	UserInfoService user = new UserInfoImpl(session);
	
	

	public MainController() throws Exception {
		pubcallList = MainPageBll.getpubcallList(user.getUsername());
		_desktop = Executions.getCurrent().getDesktop();
		_desktop.enableServerPush(true);
		final Integer userid=Integer.valueOf(UserInfo.getUserid());
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub

				AlarmAllotBll bll = new AlarmAllotBll();
				bll.updateAlarm(userid);
				
				alarmflash=true;
				
			}
		}).start();
		Log4jInit.toLog(user.getUsername() + "登陆系统");

	}

	@Command
	public void frashFrame(@BindingParam("a") final Include ic)  {
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
			
				while (!alarmflash) {
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				try {
					Executions.activate(_desktop);
					ic.setSrc("/SystemControl/AlarmMain.zul");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally{
					Executions.deactivate(_desktop);
				}
				
				
			}
		}).start();
	}

	// 保存页面排列顺序
	@Command("saveStatus")
	public void saveStatus(
			@BindingParam("portallayout") Portallayout portalLayout) {
		int i = 0;
		HttpServletResponse response = (HttpServletResponse) Executions
				.getCurrent().getNativeResponse();
		for (Component portalChild : portalLayout.getChildren()) {
			List<String> portletIds = new ArrayList<String>();
			for (Component portlet : portalChild.getChildren()) {
				portletIds.add(portlet.getId());
			}
			// 使用session储存
			/*
			 * Executions.getCurrent().getSession().setAttribute("PortalChildren"
			 * + i++, portletIds);
			 */

			// 使用cookie储存,时限个1年
			CookieUtil.addCookie(response, "PortalChildren" + i++,
					portletIds.toString(), 365 * 24 * 60);
		}
	}

	// 初始化页面排列顺序
	@Command("initStatus")
	public void initStatus(
			@BindingParam("portallayout") Portallayout portalLayout) {
		List<Component> panelchildren = portalLayout.getChildren();
		HttpServletRequest request = (HttpServletRequest) Executions
				.getCurrent().getNativeRequest();
		for (int i = 0; i < panelchildren.size(); i++) {
			// 读取session
			/*
			 * List<String> panelIds = (List<String>) Executions.getCurrent()
			 * .getSession().getAttribute("PortalChildren" + i);
			 */

			// 读取cookie
			try {
				String cookiestr = CookieUtil.getCookieByName(request,
						"PortalChildren" + i).getValue();
				cookiestr = cookiestr.replace("[", "").replace("]", "");
				List<String> panelIds = Arrays.asList(cookiestr.split(","));

				if (panelIds != null) {
					for (String panelId : panelIds) {
						Panel newPanel = (Panel) portalLayout.getFellow(panelId
								.trim());
						if (panelchildren.size() > 0)
							panelchildren.get(i).insertBefore(newPanel,
									panelchildren.get(0));
						else
							newPanel.setParent(panelchildren.get(i));
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
				// System.out.println(e);
			}
		}
	}

	// 弹出发送新短信窗口
	@Command("sysmessageadd")
	public void sysmessageadd() {
		Window window = (Window) Executions.createComponents(
				"/SysMessage/SysMessage_Add.zul", null, null);
		window.doModal();
	}

	// 弹出草稿列表窗口
	@Command("draft")
	public void draft() {
		Window window = (Window) Executions.createComponents(
				"/SysMessage/SysMessage_DraftList.zul", null, null);
		window.doModal();
	}

	// 弹出已发送窗口
	@Command("sended")
	public void sended() {
		Window window = (Window) Executions.createComponents(
				"/SysMessage/SysMessage_SendList.zul", null, null);
		window.doModal();
	}

	// 弹出收件箱窗口
	@Command("reci")
	public void reci() {
		Window window = (Window) Executions.createComponents(
				"/SysMessage/SysMessage_ReciList.zul", null, null);
		window.doModal();
	}

	// 弹出新增系统提醒窗口
	@Command("addRemind")
	public void addRemind() {
		Window window = (Window) Executions.createComponents(
				"/SysRemind/SysRemind_Add.zul", null, null);
		window.doModal();
		Executions.sendRedirect("main.zul");
	}

	public List<PubCallModel> getPubcallList() {
		return pubcallList;
	}

	public void setPubcallList(List<PubCallModel> pubcallList) {
		this.pubcallList = pubcallList;
	}

	

}
