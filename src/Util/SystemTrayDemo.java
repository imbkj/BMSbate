package Util;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import org.zkoss.zk.ui.Session;

public class SystemTrayDemo implements Runnable, ActionListener {
	private String absolutePath = FileOperate.getAbsolutePath();
	private TrayIcon trayIcon; // 托盘图标
	private SystemTray tray = SystemTray.getSystemTray(); // 本操作系统托盘的实例
	private MenuItem open, close;
	private String tips = "BMS系统提示";
	private String toolTips = "";
	private String image = "images/1.gif";
	private ImageIcon icon = null;
	private TextArea ta = null;

	private static int count = 1; // 记录消息闪动的次数
	private boolean flag = false; // 是否有新消息
	private static int times = 1; // 接收消息次数

	private Session ss;

	public SystemTrayDemo() {

	}

	public SystemTrayDemo(String str, Session session) {
		this.toolTips = str;
		this.ss = session;
		if (session.getAttribute("trayIcon") == null) {

			this.createTray();// 创建托盘对象
			session.setAttribute("trayIcon", trayIcon);
			init();
		}
	}

	public void init() {

		addTrayIcon();

	}

	public void createTray() {

		icon = new ImageIcon(absolutePath + image); // 将要显示到托盘中的图标
		PopupMenu pop = new PopupMenu(); // 构造一个右键弹出式菜单

		close = new MenuItem("Exit");
		pop.add(close);
		close.addActionListener(this);

		trayIcon = new TrayIcon(icon.getImage(), tips, pop);// 实例化托盘图标

		trayIcon.setImageAutoSize(true);

	}

	/**
	 * 添加托盘的方法
	 */
	public void addTrayIcon() {
		try {
			tray.add(trayIcon);// 将托盘添加到操作系统的托盘

			new Thread(this).start();
		} catch (AWTException e1) {
			e1.printStackTrace();
		}
	}

	// 为右击托盘菜单添加事件
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == open) {

			flag = false; // 消息打开了
			count = 0;
			times++;
		} else if (e.getSource() == close) {
			closetray();
		} else {
			System.out.println(times);
		}
	}

	public void closetray() {
		flag = false; // 消息打开了
		count = 0;
		tray.remove(trayIcon);
		ss.removeAttribute("trayIcon");

	}
	
	

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (true) {
			if (flag) { // 有新消息
				try {
					if (count == 1) {
						trayIcon.displayMessage("BMS系统信息", "你有一条新的待办事宜",
								TrayIcon.MessageType.INFO);
					}
					// 闪动消息的空白时间
					this.trayIcon.setImage(new ImageIcon("").getImage());
					Thread.sleep(500);
					// 闪动消息的提示图片
					this.trayIcon.setImage(icon.getImage());
					Thread.sleep(500);
				} catch (Exception e) {
					e.printStackTrace();
				}
				count++;
			} else { // 无消息或是消息已经打开过
				count = 0;
				this.trayIcon.setImage(icon.getImage());
				/*
				try {
					Thread.sleep(2000);
					flag = true;
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				*/
			}
		}

	}

}
