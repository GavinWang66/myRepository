package com.mr.draw;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JWindow;
import com.mr.util.BackgroundPanel;

public class PictureWindow extends JWindow {
	private JButton changeButton;
	private JButton hiddenButton;
	private BackgroundPanel centerPanel;
	File list[];
	int index;
	DrawPictureFrame frame;

	/*
	 * 构造方法
	 * 
	 * @param frame-父窗体
	 */
	public PictureWindow(DrawPictureFrame frame) {
		this.frame = frame;// 构造参数的值传给父窗体
		setSize(400, 460);
		init();
		addListener();
	}// PictureWindow()结束

	/*
	 * 组件初始化方法
	 */
	private void init() {
		Container c = getContentPane();// 获取窗体主容器
		File dir = new File("src/img/picture");// 创建简笔画文件夹对象
		list = dir.listFiles();// 获取文件夹里的所有文件
		centerPanel = new BackgroundPanel(getListImage());// 初始化背景面板，获得图片文件夹里第一张简笔画
		c.add(centerPanel, BorderLayout.CENTER);// 背景面板放到主容器中部
		FlowLayout flow = new FlowLayout(FlowLayout.RIGHT);
		flow.setHgap(20);
		JPanel southPanel = new JPanel();
		southPanel.setLayout(flow);
		changeButton = new JButton("更换图片");
		southPanel.add(changeButton);
		hiddenButton = new JButton("隐藏");
		southPanel.add(hiddenButton);
		c.add(southPanel, BorderLayout.SOUTH);
	}// init()结束

	/**
	 * 添加监听
	 */
	private void addListener() {
		hiddenButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				frame.initShowPicButton();//父类窗体还原简笔画按钮的文本内容
			}
		});// hiddenButton.addActionListener()结束
		changeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {// 单击时
				centerPanel.setImage(getListImage());// 面板重新载入图片
			}
		});// changeButton.addActionListener()结束
	}// addListener()方法结束

	/**
	 * 获取图片文件夹下的图片，每次调用此方法，都会得到不同的文件对象
	 * 
	 * @return 返回文件对象
	 */
	private Image getListImage() {
		String imgPath = list[index].getAbsolutePath();// 获得当前索引下的图片文件路径
		ImageIcon image = new ImageIcon(imgPath);// 获取此图片文件的图标对象
		index++;
		if (index >= list.length) {
			index = 0;
		}
		return image.getImage();

	}

}
