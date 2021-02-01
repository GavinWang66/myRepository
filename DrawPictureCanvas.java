package com.mr.draw;//类所在的包名

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Image;

public class DrawPictureCanvas extends Canvas {
	private Image image = null;// 创建画板中展示的图片对象

	/**
	 * 设置画板中的图片
	 * 
	 * @param image -画板中展示的图片对象
	 */
	public void setImage(Image image) {
		this.image = image;
	}

	/**
	 * 重写paint()方法,在画布上绘制图像
	 */
	public void paint(Graphics g) {
		g.drawImage(image, 0, 0, null);
	}// paint(Graphics g)结束

	/**
	 * 重写update方法，解决屏幕闪烁的问题
	 */
	public void update(Graphics g) {
		paint(g);
	}
}
