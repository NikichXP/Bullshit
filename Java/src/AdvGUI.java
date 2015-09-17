import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.io.*;

import javax.swing.event.*;

public class AdvGUI extends JFrame {
	private static final long serialVersionUID = 8743287957299739410L;
	public String status = new String("iSyncOS XFile Manager");
	public String version = new String(" ver1.9.0.0beta");

	public JTable rTable; // Правая таблица
	public FileList rTableModel;
	public JTable lTable; // Левая таблица
	public FileList lTableModel;
	public JLabel lPath; // Левый путь
	public JTextField lJTF;
	public JLabel rPath; // Правый путь
	public JTextField rJTF;
	public JPanel pathPanel; // Панель с путями
	public JPanel mainFilesPanel; // Базовый фрейм с двумя таблицами
	public JPanel lFilesPanel; // Левый список
	public JPanel rFilesPanel; // Правый список
	public JPanel buttonsPanel; // Кнопки внизу (пока что)

	public JButton quit, delete, copy, move, back, fwd; // Сами кнопки

	public String bufPath; // Буффер (хотя нафига?)
	public int select; // Выбор. 1-левый, 2-правый, 0-без такого
	private boolean doubleClickPossible;
	private long clickTime;

	public AdvGUI() {
	}

	public void init() {
		this.setJMenuBar(getJMenuBar());
		this.pathPanel = getPathPanel();
		this.pathPanel.setLayout(new BoxLayout(pathPanel, BoxLayout.X_AXIS));
		this.lFilesPanel = getFilePanel(1);
		this.rFilesPanel = getFilePanel(2);
		this.mainFilesPanel = getMainFilesPanel();

		this.buttonsPanel = getBtnPanel();

		this.getContentPane().add(this.pathPanel, BorderLayout.NORTH);
		this.getContentPane().add(this.mainFilesPanel, BorderLayout.CENTER);
		this.getContentPane().add(this.buttonsPanel, BorderLayout.SOUTH);
		this.pack();
		this.addListeners();

	}

	public JPanel getBtnPanel() { // Наши кнопочки снизу
		JPanel ret = new JPanel();
		quit = new JButton("Выход");
		delete = new JButton("Удалить");
		copy = new JButton("Копировать");
		move = new JButton("Вставить");
		back = new JButton("Назад");
		fwd = new JButton("Вперед");
		return ret;
	}

	public void addListeners() {
		this.rTable.getSelectionModel().addListSelectionListener(
				new ListSelectionListener() {
					@Override
					public void valueChanged(ListSelectionEvent arg0) {
						tableSelectionChanged(arg0, 1);
					}
				});
		this.lTable.getSelectionModel().addListSelectionListener(
				new ListSelectionListener() {
					@Override
					public void valueChanged(ListSelectionEvent arg0) {
						tableSelectionChanged(arg0, 2);
					}
				});
		this.quit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		this.delete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				deleteActionEvent();
			}
		});
		this.copy.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				copyActionEvent();
			}
		});
		this.move.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				moveActionEvent();
			}
		});
		this.back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
			}
		});
		this.fwd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
			}
		});
	}

	protected void moveActionEvent() {
		// TODO Auto-generated method stub

	}

	protected void copyActionEvent() {
		// TODO Auto-generated method stub

	}

	protected void deleteActionEvent() {
		// TODO Auto-generated method stub

	}

	private JPanel getMainFilesPanel() { // Содержит 2 панели с левой и правой
		// таблицей
		JPanel ret = new JPanel();
		ret.setLayout(new GridLayout(1, 2));
		ret.add(lFilesPanel);
		ret.add(rFilesPanel);
		return ret;
	}

	public JPanel getFilePanel(int side) { // Содержит таблицу с данными
		JPanel ret = new JPanel();
		ret.setLayout(new BorderLayout());
		if (side == 1) { // left
			this.lTableModel = new FileList();
			this.lTable = new JTable(lTableModel);
			this.lTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			ret.add(new JScrollPane(lTable), BorderLayout.CENTER);
			File[] dir = new File("E:\\").listFiles();
			for (File file : dir) {
				lTableModel.addFile(file);
			}
			ret.setLayout(new GridLayout(1, 1));
		} else if (side == 2) { // right
			this.rTableModel = new FileList();
			this.rTable = new JTable(rTableModel);
			this.rTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			ret.add(new JScrollPane(rTable), BorderLayout.CENTER);
			File[] dir = new File("E:\\").listFiles();
			for (int i = 0; i < dir.length; i++) {
				rTableModel.addFile(dir[i]);
			}
			ret.setLayout(new GridLayout(1, 1));
		} else {
			// LOLWUT??
		}
		return ret;
	}

	public JPanel getPathPanel() { // Панель сверху
		JPanel ret = new JPanel();
		lPath = new JLabel();
		lPath.setText("C:\\");
		ret.add(lPath);
		File[] disks = File.listRoots();
		final JButton[] diskBtn = new JButton[disks.length];
		JLabel[] nullLabel = new JLabel[disks.length];
		for (int i = 0; i < disks.length; i++) {
			diskBtn[i] = new JButton("  " + disks[i].getAbsolutePath() + "  ");
			ret.add(diskBtn[i]);
			nullLabel[i] = new JLabel(" ");
			ret.add(nullLabel[i]);
			diskBtn[i].addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					// diskButtonEvent (arg0.getActionCommand().charAt(2) +
					// ":\\");
				}
			});
			diskBtn[i].setToolTipText("Free: " + disks[i].getFreeSpace()
					/ 1073741824 + "GB/" + +disks[i].getTotalSpace()
					/ 1073741824 + "GB");
			diskBtn[i].setAlignmentX(CENTER_ALIGNMENT);

		}
		rPath = new JLabel();
		rPath.setText("D:\\");
		ret.add(rPath);
		// TODO 2 строки
		return ret;
	}

	protected void tableSelectionChanged(ListSelectionEvent arg0, int i) {
		doubleClickPossible = false;
		this.select = i;
		System.out.println("Выбор таблицы"+i);
		if (System.currentTimeMillis()-this.clickTime < 500 & System.currentTimeMillis()-this.clickTime > 50) {
			System.out.println("double-click!");
		}
		this.clickTime = System.currentTimeMillis();
	}

	// Методы завершены на данный этап.

	public JMenuBar getJMenuBar() {
		JMenu fileMenu = new JMenu("Файл");
		JMenu helpMenu = new JMenu("Помощь");
		JMenuItem openLoc = new JMenuItem("Открыть", KeyEvent.VK_O);
		JMenuItem close = new JMenuItem("Выход", KeyEvent.VK_Q);
		JMenuItem help = new JMenuItem("Об авторе", KeyEvent.VK_H);
		JMenuItem options = new JMenuItem("Настройки", KeyEvent.VK_C);
		fileMenu.add(openLoc);
		fileMenu.addSeparator();
		fileMenu.add(close);
		openLoc.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) { /*
															 * openButtonEvent();
															 */
			}
		});
		close.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});

		helpMenu.add(help);
		helpMenu.add(options);
		help.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) { /* infoView (); */
			}
		});
		options.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFrame optfrm = new JFrame();
				JPanel optpan = new JPanel();
				JPanel optPanel1 = new JPanel();
				optPanel1.add(new JLabel("gg1"));
				JPanel optPanel2 = new JPanel();
				optPanel2.add(new JLabel("gg2"));
				JTabbedPane tabs = new JTabbedPane();
				tabs.addTab("Test1", optPanel1);
				tabs.addTab("Test2", optPanel2);
				optpan.add(tabs);
				optfrm.add(optpan);
				// optfrm.pack();
				optfrm.setSize(400, 200);
				optfrm.setVisible(true);
			}
		});
		JMenuBar menuBar = new JMenuBar();
		menuBar.add(fileMenu);
		menuBar.add(helpMenu);
		return menuBar;
	}

	public void setTitle() {
		this.setTitle(status + version);
	}
}
