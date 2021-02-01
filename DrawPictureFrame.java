package com.mr.draw;

import javax.swing.JFrame; //���봰����
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.BorderLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import java.awt.BasicStroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JColorChooser;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import com.mr.util.FrameGetShape;
import com.mr.util.ShapeWindow;
import com.mr.util.Shapes;
import com.mr.util.DrawImageUtil;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import java.awt.AlphaComposite;
import java.awt.Font;
import javax.swing.JOptionPane;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.Cursor;

/**
 * ��ͼ������
 */
public class DrawPictureFrame extends JFrame implements FrameGetShape {
	BufferedImage image = new BufferedImage(570, 390, java.awt.image.BufferedImage.TYPE_INT_RGB);
	Graphics gs = image.getGraphics();
	Graphics2D g = (Graphics2D) gs; // ����ͼ����ת��ΪGraphics2D��
	DrawPictureCanvas canvas = new DrawPictureCanvas();
	Color foreColor = Color.black;
	Color backgroundColor = Color.white;
	int x = -1;// ��һ�������Ƶ�ĺ�����
	int y = -1;// ��һ�������Ƶ��������
	boolean rubber = false;// ��Ƥ��ʶ����
	private JToolBar toolBar;// ���ߙ�
	private JButton eraserButton;
	private JToggleButton strokeButton1;// ϸ�߰�ť
	private JToggleButton strokeButton2;// ���߰�ť
	private JToggleButton strokeButton3;// �ϴ��߰�ť
	private JButton backgroundButton;// ����ɫ��ť
	private JButton foregroundButton;// ǰ��ɫ��ť
	private JButton clearButton;// �����ť
	private JButton saveButton;// ���水ť
	private JButton shapeButton;// ͼ�ΰ�ť
	boolean drawShape = false;// ��ͼ�εı�ʶ����
	Shapes shape;// �滭��ͼ��
	private JMenuItem strokeMenuItem1;
	private JMenuItem strokeMenuItem2;
	private JMenuItem strokeMenuItem3;
	private JMenuItem clearMenuItem;
	private JMenuItem foregroundMenuItem;
	private JMenuItem backgroundMenuItem;
	private JMenuItem eraserMenuItem;
	private JMenuItem exitMenuItem;
	private JMenuItem saveMenuItem;
	private JMenuItem shuiyinMenuItem;
	private String shuiyin = "";
	private PictureWindow picWindow;
	private JButton showPicButton;

	/**
	 * ���췽��
	 * 
	 */
	public DrawPictureFrame() {
		setResizable(false); // ���岻�ܸı��С
		setTitle("��ͼ����(ˮӡ���ݣ���" + shuiyin + "��)"); // ���ñ���
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// ����ر���ֹͣ����
		setBounds(500, 100, 574, 460); // ���ô���λ�úͿ��
		init();
		addListener();// ����������
	} // ���췽������

	/**
	 * �����ʼ��
	 */
	private void init() {
		g.setColor(backgroundColor);
		g.fillRect(0, 0, 570, 390);
		g.setColor(foreColor);
		canvas.setImage(image);
		getContentPane().add(canvas);

		toolBar = new JToolBar();// ��ʼ��������
		getContentPane().add(toolBar, BorderLayout.NORTH);// ��������ӵ��������
		showPicButton = new JButton("չ����ʻ�");
		toolBar.add(showPicButton);
		saveButton = new JButton("����");// ��ӱ��水ť
		toolBar.add(saveButton);
		toolBar.addSeparator();
		// ��ʼ����ť���󣬲�����ı�����
		strokeButton1 = new JToggleButton("ϸ��");
		strokeButton1.setSelected(true);
		toolBar.add(strokeButton1);

		strokeButton2 = new JToggleButton("����");
		toolBar.add(strokeButton2);

		strokeButton3 = new JToggleButton("�ϴ���");
		toolBar.add(strokeButton3);
		ButtonGroup strokeGroup = new ButtonGroup();
		strokeGroup.add(strokeButton1);
		strokeGroup.add(strokeButton2);
		strokeGroup.add(strokeButton3);
		toolBar.addSeparator();
		backgroundButton = new JButton("������ɫ");
		toolBar.add(backgroundButton);
		foregroundButton = new JButton("ǰ����ɫ");
		toolBar.add(foregroundButton);
		toolBar.addSeparator();
		shapeButton = new JButton("ͼ��");
		toolBar.add(shapeButton);
		clearButton = new JButton("���");
		toolBar.add(clearButton);
		eraserButton = new JButton("��Ƥ");
		toolBar.add(eraserButton);
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);// ��������˵���

		JMenu systemMenu = new JMenu("ϵͳ");
		menuBar.add(systemMenu);
		shuiyinMenuItem = new JMenuItem("����ˮӡ");
		systemMenu.add(shuiyinMenuItem);// ˮӡ�˵�����ϵͳ�˵���
		saveMenuItem = new JMenuItem("����");
		systemMenu.add(saveMenuItem);
		systemMenu.addSeparator();
		exitMenuItem = new JMenuItem("�˳�");
		systemMenu.add(exitMenuItem);
		JMenu strokeMenu = new JMenu("����");
		menuBar.add(strokeMenu);
		strokeMenuItem1 = new JMenuItem("ϸ��");
		strokeMenu.add(strokeMenuItem1);
		strokeMenuItem2 = new JMenuItem("����");
		strokeMenu.add(strokeMenuItem2);
		strokeMenuItem3 = new JMenuItem("�ϴ�");
		strokeMenu.add(strokeMenuItem3);

		JMenu colorMenu = new JMenu("��ɫ");
		menuBar.add(colorMenu);
		foregroundMenuItem = new JMenuItem("ǰ����ɫ");
		colorMenu.add(foregroundMenuItem);
		backgroundMenuItem = new JMenuItem("������ɫ");
		colorMenu.add(backgroundMenuItem);

		JMenu editMenu = new JMenu("�༭");
		menuBar.add(editMenu);
		clearMenuItem = new JMenuItem("���");
		editMenu.add(clearMenuItem);
		eraserMenuItem = new JMenuItem("��Ƥ");
		editMenu.add(eraserMenuItem);
		// ������ʻ�չʾ��壬�������൱�����ĸ�����
		picWindow = new PictureWindow(DrawPictureFrame.this);

	}

	/**
	 * �����Ӷ�̬����
	 */
	private void addListener() {
		// Ϊ�����������ƶ��¼�����
		canvas.addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(final MouseEvent e) {
				if (x > 0 && y > 0) {
					if (rubber) {
						g.setColor(backgroundColor);
						g.fillRect(x, y, 10, 10);
					} else {
						g.drawLine(x, y, e.getX(), e.getY());
					}
				}
				x = e.getX();
				y = e.getY();
				canvas.repaint();
			}// mouseDragged()����

			public void mouseMoved(final MouseEvent arg0) {
				if (rubber) {
					Toolkit kit = Toolkit.getDefaultToolkit();
					Image img = kit.createImage("src/img/icon/�����Ƥ.png");
					Cursor c = kit.createCustomCursor(img, new Point(0, 0), "clear");
					setCursor(c);
				} else {
					setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
				}
			}// mousemoved()����
		});// canvas.addMouseMotionListener()����
		toolBar.addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseMoved(final MouseEvent arg0) {
				setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});// toolBar.addMouseMotionListener()��������

		canvas.addMouseListener(new MouseAdapter() {
			public void mouseReleased(final MouseEvent arg0) {
				x = -1;
				y = -1;
			}

			public void mousePressed(MouseEvent e) {
				if (drawShape) {
					switch (shape.getType()) {
					case Shapes.YUAN:
						int yuanX = e.getX() - shape.getWidth() / 2;
						int yuanY = e.getY() - shape.getHeigth() / 2;
						Ellipse2D yuan = new Ellipse2D.Double(yuanX, yuanY, shape.getWidth(), shape.getHeigth());
						g.draw(yuan);
						break;
					case Shapes.FANG:
						int fangX = e.getX() - shape.getWidth() / 2;
						int fangY = e.getY() - shape.getHeigth() / 2;
						Rectangle2D fang = new Rectangle2D.Double(fangX, fangY, shape.getWidth(), shape.getHeigth());
						g.draw(fang);
						break;
					}
					canvas.repaint();
					drawShape = false;// ͼ�λ��ʽ���
				}
			}
		});
		strokeButton1.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent arg0) {
				BasicStroke bs = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER);
				g.setStroke(bs);
			}
		});// ϸ�߼�����������
		strokeButton2.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent arg0) {
				BasicStroke bs = new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER);
				g.setStroke(bs);
			}
		});
		strokeButton3.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent arg0) {
				BasicStroke bs = new BasicStroke(4, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER);
				g.setStroke(bs);
			}
		});

		backgroundButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent arg0) {
				Color bgColor = JColorChooser.showDialog(DrawPictureFrame.this, "ѡ����ɫ�Ի���", Color.CYAN);
				if (bgColor != null) {
					backgroundColor = bgColor;
				}
				backgroundButton.setBackground(backgroundColor);
				g.setColor(backgroundColor);
				g.fillRect(0, 0, 570, 390);
				g.setColor(foreColor);
				canvas.repaint();
			}
		});
		foregroundButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent arg0) {
				Color fColor = JColorChooser.showDialog(DrawPictureFrame.this, "ѡ����ɫ�Ի���", Color.CYAN);
				if (fColor != null) {
					foreColor = fColor;
				}
				foregroundButton.setForeground(foreColor);
				g.setColor(foreColor);
			}
		});
		clearButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent arg0) {
				g.setColor(backgroundColor);
				g.fillRect(0, 0, 570, 390);
				g.setColor(foreColor);
				canvas.repaint();
			}
		});// clearButton.addActionListener()����
		eraserButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent arg0) {
				if (eraserButton.getText().equals("��Ƥ")) {
					rubber = true;
					eraserButton.setText("��ͼ");
				} else {
					rubber = false;
					eraserButton.setText("��Ƥ");
					g.setColor(foreColor);
				}
			}
		});
		shapeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ShapeWindow shapeWindow = new ShapeWindow(DrawPictureFrame.this);
				int shapeButtonWidth = shapeButton.getWidth();
				int shapeWindowWidth = shapeWindow.getWidth();
				int shapeButtonX = shapeButton.getX();
				int shapeButtonY = shapeButton.getY();
				int shapeWindowX = getX() + shapeButtonX - (shapeWindowWidth - shapeButtonWidth) / 2;
				int shapeWindowY = getY() + shapeButtonY + 80;
				shapeWindow.setLocation(shapeWindowX, shapeWindowY);
				shapeWindow.setVisible(true);
			}
		});// shapeButton.addActionListener()����
		saveButton.addActionListener(new ActionListener() {// ���水ť��Ӷ�������
			public void actionPerformed(final ActionEvent arg0) {
				addWatermark();
				DrawImageUtil.saveImage(DrawPictureFrame.this, image);
			}
		});
		exitMenuItem.addActionListener(new ActionListener() {// �˳��˵�����Ӷ�������
			public void actionPerformed(final ActionEvent arg0) {
				System.exit(0);
			}
		});
		eraserMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent arg0) {
				if (eraserButton.getText().equals("��Ƥ")) {
					rubber = true;
					eraserButton.setText("��ͼ");
					eraserMenuItem.setText("��ͼ");
				} else {
					rubber = false;
					eraserButton.setText("��Ƥ");
					eraserMenuItem.setText("��Ƥ");
					g.setColor(foreColor);
				}
			}
		});// eraserMenuItem.addActionListener()����
		clearMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent arg0) {
				g.setColor(backgroundColor);
				g.fillRect(0, 0, 570, 390);
				g.setColor(foreColor);
				canvas.repaint();
			}
		});// clearMenuItem.addActionListener()����
		strokeMenuItem1.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent arg0) {
				BasicStroke bs = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER);
				g.setStroke(bs);
				strokeButton1.setSelected(true);
			}
		});// strokeMenuItem1.addActionListener()����
		strokeMenuItem2.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent arg0) {
				BasicStroke bs = new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER);
				g.setStroke(bs);
				strokeButton2.setSelected(true);
			}
		});// strokeMenuItem2.addActionListener()����
		strokeMenuItem3.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent arg0) {
				BasicStroke bs = new BasicStroke(4, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER);
				g.setStroke(bs);
				strokeButton3.setSelected(true);
			}
		});// strokeMenuItem3.addActionListener()����
		backgroundMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent arg0) {
				Color bgColor = JColorChooser.showDialog(DrawPictureFrame.this, "ѡ����ɫ�Ի���", Color.CYAN);
				if (bgColor != null) {
					backgroundColor = bgColor;
				}
				backgroundButton.setBackground(backgroundColor);
				g.setColor(backgroundColor);
				g.fillRect(0, 0, 570, 390);
				g.setColor(foreColor);
				canvas.repaint();
			}
		});// backgroundMenuItem.addActionListener()����
		foregroundMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent arg0) {
				Color fColor = JColorChooser.showDialog(DrawPictureFrame.this, "ѡ����ɫ�Ի���", Color.CYAN);
				if (fColor != null) {
					foreColor = fColor;
				}
				foregroundButton.setForeground(foreColor);
				g.setColor(foreColor);
			}
		});// foregroundMenuItem.addActionListener()����
		saveMenuItem.addActionListener(new ActionListener() {// ����˵���Ӷ�������
			public void actionPerformed(final ActionEvent arg0) {
				addWatermark();
				DrawImageUtil.saveImage(DrawPictureFrame.this, image);
			}
		});// saveMenuItem.addActionListener()����
		shuiyinMenuItem.addActionListener(new ActionListener() {// ����˵���Ӷ�������
			public void actionPerformed(final ActionEvent arg0) {
				shuiyin = JOptionPane.showInputDialog(DrawPictureFrame.this, "����Ҫ���ʲôˮӡ��");
				if (null == shuiyin) {
					shuiyin = "";
				} else {
					setTitle("��ͼ����(ˮӡ���ݣ���" + shuiyin + "��)");
				}
			}
		});// shuiyinMenuItem.addActionListener()����
		showPicButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean isVisible = picWindow.isVisible();
				if (isVisible) {
					showPicButton.setText("չ����ʻ�");
					picWindow.setVisible(false);
				} else {
					showPicButton.setText("���ؼ�ʻ�");
					picWindow.setLocation(getX() - picWindow.getWidth() - 5, getY());
					picWindow.setVisible(true);
				}
			}
		});// showPicButton.addActionListener()����

	}// addlistener()����

	/**
	 * �ָ�չ����ʻ���ť���ı����ݣ��˷�������ʻ�����"����"��ť����
	 */
	public void initShowPicButton() {
		showPicButton.setText("չ����ʻ�");
	}// initShowPicButton()����

	/**
	 * ���ˮӡ
	 */
	private void addWatermark() {
		if (!"".equals(shuiyin.trim())) {
			g.rotate(Math.toRadians(-30));
			Font font = new Font("����", Font.BOLD, 72);
			g.setFont(font);
			g.setColor(Color.GRAY);
			AlphaComposite alpha = AlphaComposite.SrcOver.derive(0.4f);// ����͸����
			g.setComposite(alpha);// ʹ��͸��Ч��
			g.drawString(shuiyin, 150, 500);
			canvas.repaint();
			g.rotate(Math.toRadians(30));
			alpha = AlphaComposite.SrcOver.derive(1f);
			g.setComposite(alpha);
			g.setColor(foreColor);

		}
	}

	/**
	 * FrameGetShape�ӿ�ʵ���࣬���ڻ��ͼ�οռ䷵�صı�ѡ�е�ͼ��
	 */
	public void getShape(Shapes shape) {
		this.shape = shape;
		drawShape = true;
	}

	/**
	 * ��������������
	 * 
	 * @param args -����ʱ�Ĳ������������ò���
	 */
	public static void main(String[] args) {
		DrawPictureFrame frame = new DrawPictureFrame();
		frame.setVisible(true);
	}
}
