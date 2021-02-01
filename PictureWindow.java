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
	 * ���췽��
	 * 
	 * @param frame-������
	 */
	public PictureWindow(DrawPictureFrame frame) {
		this.frame = frame;// ���������ֵ����������
		setSize(400, 460);
		init();
		addListener();
	}// PictureWindow()����

	/*
	 * �����ʼ������
	 */
	private void init() {
		Container c = getContentPane();// ��ȡ����������
		File dir = new File("src/img/picture");// ������ʻ��ļ��ж���
		list = dir.listFiles();// ��ȡ�ļ�����������ļ�
		centerPanel = new BackgroundPanel(getListImage());// ��ʼ��������壬���ͼƬ�ļ������һ�ż�ʻ�
		c.add(centerPanel, BorderLayout.CENTER);// �������ŵ��������в�
		FlowLayout flow = new FlowLayout(FlowLayout.RIGHT);
		flow.setHgap(20);
		JPanel southPanel = new JPanel();
		southPanel.setLayout(flow);
		changeButton = new JButton("����ͼƬ");
		southPanel.add(changeButton);
		hiddenButton = new JButton("����");
		southPanel.add(hiddenButton);
		c.add(southPanel, BorderLayout.SOUTH);
	}// init()����

	/**
	 * ��Ӽ���
	 */
	private void addListener() {
		hiddenButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				frame.initShowPicButton();//���ര�廹ԭ��ʻ���ť���ı�����
			}
		});// hiddenButton.addActionListener()����
		changeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {// ����ʱ
				centerPanel.setImage(getListImage());// �����������ͼƬ
			}
		});// changeButton.addActionListener()����
	}// addListener()��������

	/**
	 * ��ȡͼƬ�ļ����µ�ͼƬ��ÿ�ε��ô˷���������õ���ͬ���ļ�����
	 * 
	 * @return �����ļ�����
	 */
	private Image getListImage() {
		String imgPath = list[index].getAbsolutePath();// ��õ�ǰ�����µ�ͼƬ�ļ�·��
		ImageIcon image = new ImageIcon(imgPath);// ��ȡ��ͼƬ�ļ���ͼ�����
		index++;
		if (index >= list.length) {
			index = 0;
		}
		return image.getImage();

	}

}
