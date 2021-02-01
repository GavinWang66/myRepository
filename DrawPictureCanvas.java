

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

	/**
	 *
	 *
	 * @description:
	 * @param null
	 * @return:
	 * @author: Gavin Wang
	 * @time: 2021/2/1 12:52
	 */
	public static void main(String[] args) {
		System.out.println("hello");
	}
}
