package com.mr.draw;//�����ڵİ���

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Image;

public class DrawPictureCanvas extends Canvas {
	private Image image = null;// ����������չʾ��ͼƬ����

	/**
	 * ���û����е�ͼƬ
	 * 
	 * @param image -������չʾ��ͼƬ����
	 */
	public void setImage(Image image) {
		this.image = image;
	}

	/**
	 * ��дpaint()����,�ڻ����ϻ���ͼ��
	 */
	public void paint(Graphics g) {
		g.drawImage(image, 0, 0, null);
	}// paint(Graphics g)����

	/**
	 * ��дupdate�����������Ļ��˸������
	 */
	public void update(Graphics g) {
		paint(g);
	}
}
