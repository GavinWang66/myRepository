package com.mr.draw;

import javax.swing.JFrame; //引入窗体类
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
 * 画图主窗体
 */
public class DrawPictureFrame extends JFrame implements FrameGetShape {
	BufferedImage image = new BufferedImage(570, 390, java.awt.image.BufferedImage.TYPE_INT_RGB);
	Graphics gs = image.getGraphics();
	Graphics2D g = (Graphics2D) gs; // 将会图对象转换为Graphics2D类
	DrawPictureCanvas canvas = new DrawPictureCanvas();
	Color foreColor = Color.black;
	Color backgroundColor = Color.white;
	int x = -1;// 上一次鼠标绘制点的横坐标
	int y = -1;// 上一次鼠标绘制点的纵坐标
	boolean rubber = false;// 橡皮标识变量
	private JToolBar toolBar;// 工具欄
	private JButton eraserButton;
	private JToggleButton strokeButton1;// 细线按钮
	private JToggleButton strokeButton2;// 粗线按钮
	private JToggleButton strokeButton3;// 较粗线按钮
	private JButton backgroundButton;// 背景色按钮
	private JButton foregroundButton;// 前景色按钮
	private JButton clearButton;// 清除按钮
	private JButton saveButton;// 保存按钮
	private JButton shapeButton;// 图形按钮
	boolean drawShape = false;// 画图形的标识变量
	Shapes shape;// 绘画的图形
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
	 * 构造方法
	 * 
	 */
	public DrawPictureFrame() {
		setResizable(false); // 窗体不能改变大小
		setTitle("画图程序(水印内容：【" + shuiyin + "】)"); // 设置标题
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// 窗体关闭则停止程序
		setBounds(500, 100, 574, 460); // 设置窗口位置和宽高
		init();
		addListener();// 添加组件监听
	} // 构造方法结束

	/**
	 * 组件初始化
	 */
	private void init() {
		g.setColor(backgroundColor);
		g.fillRect(0, 0, 570, 390);
		g.setColor(foreColor);
		canvas.setImage(image);
		getContentPane().add(canvas);

		toolBar = new JToolBar();// 初始化工具栏
		getContentPane().add(toolBar, BorderLayout.NORTH);// 工具栏添加到窗体最北边
		showPicButton = new JButton("展开简笔画");
		toolBar.add(showPicButton);
		saveButton = new JButton("保存");// 添加保存按钮
		toolBar.add(saveButton);
		toolBar.addSeparator();
		// 初始化按钮对象，并添加文本内容
		strokeButton1 = new JToggleButton("细线");
		strokeButton1.setSelected(true);
		toolBar.add(strokeButton1);

		strokeButton2 = new JToggleButton("粗线");
		toolBar.add(strokeButton2);

		strokeButton3 = new JToggleButton("较粗线");
		toolBar.add(strokeButton3);
		ButtonGroup strokeGroup = new ButtonGroup();
		strokeGroup.add(strokeButton1);
		strokeGroup.add(strokeButton2);
		strokeGroup.add(strokeButton3);
		toolBar.addSeparator();
		backgroundButton = new JButton("背景颜色");
		toolBar.add(backgroundButton);
		foregroundButton = new JButton("前景颜色");
		toolBar.add(foregroundButton);
		toolBar.addSeparator();
		shapeButton = new JButton("图形");
		toolBar.add(shapeButton);
		clearButton = new JButton("清除");
		toolBar.add(clearButton);
		eraserButton = new JButton("橡皮");
		toolBar.add(eraserButton);
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);// 窗体载入菜单栏

		JMenu systemMenu = new JMenu("系统");
		menuBar.add(systemMenu);
		shuiyinMenuItem = new JMenuItem("设置水印");
		systemMenu.add(shuiyinMenuItem);// 水印菜单加入系统菜单栏
		saveMenuItem = new JMenuItem("保存");
		systemMenu.add(saveMenuItem);
		systemMenu.addSeparator();
		exitMenuItem = new JMenuItem("退出");
		systemMenu.add(exitMenuItem);
		JMenu strokeMenu = new JMenu("线型");
		menuBar.add(strokeMenu);
		strokeMenuItem1 = new JMenuItem("细线");
		strokeMenu.add(strokeMenuItem1);
		strokeMenuItem2 = new JMenuItem("粗线");
		strokeMenu.add(strokeMenuItem2);
		strokeMenuItem3 = new JMenuItem("较粗");
		strokeMenu.add(strokeMenuItem3);

		JMenu colorMenu = new JMenu("颜色");
		menuBar.add(colorMenu);
		foregroundMenuItem = new JMenuItem("前景颜色");
		colorMenu.add(foregroundMenuItem);
		backgroundMenuItem = new JMenuItem("背景颜色");
		colorMenu.add(backgroundMenuItem);

		JMenu editMenu = new JMenu("编辑");
		menuBar.add(editMenu);
		clearMenuItem = new JMenuItem("清除");
		editMenu.add(clearMenuItem);
		eraserMenuItem = new JMenuItem("橡皮");
		editMenu.add(eraserMenuItem);
		// 创建简笔画展示面板，并将本类当作他的父窗体
		picWindow = new PictureWindow(DrawPictureFrame.this);

	}

	/**
	 * 组件添加动态监听
	 */
	private void addListener() {
		// 为画板添加鼠标移动事件监听
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
			}// mouseDragged()结束

			public void mouseMoved(final MouseEvent arg0) {
				if (rubber) {
					Toolkit kit = Toolkit.getDefaultToolkit();
					Image img = kit.createImage("src/img/icon/鼠标橡皮.png");
					Cursor c = kit.createCustomCursor(img, new Point(0, 0), "clear");
					setCursor(c);
				} else {
					setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
				}
			}// mousemoved()结束
		});// canvas.addMouseMotionListener()结束
		toolBar.addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseMoved(final MouseEvent arg0) {
				setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});// toolBar.addMouseMotionListener()方法结束

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
					drawShape = false;// 图形画笔结束
				}
			}
		});
		strokeButton1.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent arg0) {
				BasicStroke bs = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER);
				g.setStroke(bs);
			}
		});// 细线监听方法结束
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
				Color bgColor = JColorChooser.showDialog(DrawPictureFrame.this, "选择颜色对话框", Color.CYAN);
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
				Color fColor = JColorChooser.showDialog(DrawPictureFrame.this, "选择颜色对话框", Color.CYAN);
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
		});// clearButton.addActionListener()结束
		eraserButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent arg0) {
				if (eraserButton.getText().equals("橡皮")) {
					rubber = true;
					eraserButton.setText("画图");
				} else {
					rubber = false;
					eraserButton.setText("橡皮");
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
		});// shapeButton.addActionListener()结束
		saveButton.addActionListener(new ActionListener() {// 保存按钮添加动作监听
			public void actionPerformed(final ActionEvent arg0) {
				addWatermark();
				DrawImageUtil.saveImage(DrawPictureFrame.this, image);
			}
		});
		exitMenuItem.addActionListener(new ActionListener() {// 退出菜单栏添加动作监听
			public void actionPerformed(final ActionEvent arg0) {
				System.exit(0);
			}
		});
		eraserMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent arg0) {
				if (eraserButton.getText().equals("橡皮")) {
					rubber = true;
					eraserButton.setText("画图");
					eraserMenuItem.setText("画图");
				} else {
					rubber = false;
					eraserButton.setText("橡皮");
					eraserMenuItem.setText("橡皮");
					g.setColor(foreColor);
				}
			}
		});// eraserMenuItem.addActionListener()结束
		clearMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent arg0) {
				g.setColor(backgroundColor);
				g.fillRect(0, 0, 570, 390);
				g.setColor(foreColor);
				canvas.repaint();
			}
		});// clearMenuItem.addActionListener()结束
		strokeMenuItem1.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent arg0) {
				BasicStroke bs = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER);
				g.setStroke(bs);
				strokeButton1.setSelected(true);
			}
		});// strokeMenuItem1.addActionListener()结束
		strokeMenuItem2.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent arg0) {
				BasicStroke bs = new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER);
				g.setStroke(bs);
				strokeButton2.setSelected(true);
			}
		});// strokeMenuItem2.addActionListener()结束
		strokeMenuItem3.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent arg0) {
				BasicStroke bs = new BasicStroke(4, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER);
				g.setStroke(bs);
				strokeButton3.setSelected(true);
			}
		});// strokeMenuItem3.addActionListener()结束
		backgroundMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent arg0) {
				Color bgColor = JColorChooser.showDialog(DrawPictureFrame.this, "选择颜色对话框", Color.CYAN);
				if (bgColor != null) {
					backgroundColor = bgColor;
				}
				backgroundButton.setBackground(backgroundColor);
				g.setColor(backgroundColor);
				g.fillRect(0, 0, 570, 390);
				g.setColor(foreColor);
				canvas.repaint();
			}
		});// backgroundMenuItem.addActionListener()结束
		foregroundMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent arg0) {
				Color fColor = JColorChooser.showDialog(DrawPictureFrame.this, "选择颜色对话框", Color.CYAN);
				if (fColor != null) {
					foreColor = fColor;
				}
				foregroundButton.setForeground(foreColor);
				g.setColor(foreColor);
			}
		});// foregroundMenuItem.addActionListener()结束
		saveMenuItem.addActionListener(new ActionListener() {// 保存菜单添加动作监听
			public void actionPerformed(final ActionEvent arg0) {
				addWatermark();
				DrawImageUtil.saveImage(DrawPictureFrame.this, image);
			}
		});// saveMenuItem.addActionListener()结束
		shuiyinMenuItem.addActionListener(new ActionListener() {// 保存菜单添加动作监听
			public void actionPerformed(final ActionEvent arg0) {
				shuiyin = JOptionPane.showInputDialog(DrawPictureFrame.this, "你想要添加什么水印？");
				if (null == shuiyin) {
					shuiyin = "";
				} else {
					setTitle("画图程序(水印内容：【" + shuiyin + "】)");
				}
			}
		});// shuiyinMenuItem.addActionListener()结束
		showPicButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean isVisible = picWindow.isVisible();
				if (isVisible) {
					showPicButton.setText("展开简笔画");
					picWindow.setVisible(false);
				} else {
					showPicButton.setText("隐藏简笔画");
					picWindow.setLocation(getX() - picWindow.getWidth() - 5, getY());
					picWindow.setVisible(true);
				}
			}
		});// showPicButton.addActionListener()结束

	}// addlistener()结束

	/**
	 * 恢复展开简笔画按钮的文本内容，此方法供简笔画面板的"隐藏"按钮调用
	 */
	public void initShowPicButton() {
		showPicButton.setText("展开简笔画");
	}// initShowPicButton()结束

	/**
	 * 添加水印
	 */
	private void addWatermark() {
		if (!"".equals(shuiyin.trim())) {
			g.rotate(Math.toRadians(-30));
			Font font = new Font("楷体", Font.BOLD, 72);
			g.setFont(font);
			g.setColor(Color.GRAY);
			AlphaComposite alpha = AlphaComposite.SrcOver.derive(0.4f);// 设置透明度
			g.setComposite(alpha);// 使用透明效果
			g.drawString(shuiyin, 150, 500);
			canvas.repaint();
			g.rotate(Math.toRadians(30));
			alpha = AlphaComposite.SrcOver.derive(1f);
			g.setComposite(alpha);
			g.setColor(foreColor);

		}
	}

	/**
	 * FrameGetShape接口实现类，用于获得图形空间返回的被选中的图形
	 */
	public void getShape(Shapes shape) {
		this.shape = shape;
		drawShape = true;
	}

	/**
	 * 程序运行主方法
	 * 
	 * @param args -运行时的参数，本程序用不到
	 */
	public static void main(String[] args) {
		DrawPictureFrame frame = new DrawPictureFrame();
		frame.setVisible(true);
	}
}
