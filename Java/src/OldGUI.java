import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.file.*;
import javax.swing.event.*;

/**
 *
 * @author NikichXP
 * @version 1.1.1d
 * @since 6 may 2014
 *
 */

/*
 Изменения
 5/05/14
 v1.0.1:	
  		*Демонстрация в виде курсовой работы.
  		
 6/05
 v1.0.2:	
 		*Модули обработки событий вынесены в отдельные методы
 		*Программа запускается в fullscreen-mode
 		*Подгрузка конфигуратора
 		*Реорганизация кода, количество строк уменьшено на 20%
 		*Теперь кнопки реагируют на выбранный файл
 
 9/05
 v1.0.3:	
 	*Исправлен баг, из-за которого отображался размер папки на диске вместо <DIR>
 
 12/5 
 v1.1.0:	
 		*Теперь в строку адреса можно "вбивать" адрес.
  		*Удалена кнопка "Открыть адрес".
  		*Код стал более читабелен.
  		*Добавлены кнопки дисков для быстрого доступа

 13/5
 v1.1.1:	
  		*Появился пакет локализации, настраиваемый
  		*При наведении на диск отображается свободное место и общий объём диска
  		*Возвращена кнопка панели "Открыть адрес"
  		*Добавлена кнопка меню "Открыть адрес"
  		*Удалена кнопка панели "Открыть адрес"
 14/5
 v1.1.1b-d:
 		*Добавлено окно опций
 		*Изменено окно информации
 		*Исправлена ошибка из-за которой кнопка "Назад" становилась неактивной после открытия одной папки на диске.
 		*Исправлена ошибка, из-за которой некоторые файлы имели размер в 0 террабайт
 		*Увеличен размер кнопок дисков
 16/5
 v1.2.0
 		*Реализован дабл-клик
 		*Теперь все файлы с расширением *.exe запускаются как Win32-приложения
 		*Все файлы с любым расширением запускаются как обычные файлы, вызывая Win32API для запуска файла
 		
 	TODO	2 окна
 	TODO 	Пункт "вверх" на 1-й позиции в листе
 	 
*/

public class OldGUI extends JFrame {
	private static final long serialVersionUID = 0x4ffd62a;
	public String status = new String ("iSyncOS XFile Manager");
	public String version = new String (" ver1.2.0.2");
	public JTable table1;
	public static FileList tableModel;
	public File loc1;
	public Path copyPath;
	public JLabel lPath;
	public JTextField jtf;
	public JButton quit, delete, copy, paste, back, fwd;
	public JPanel pathPanel;
	public JPanel mainFilePanel;
	public JPanel buttonPanel;
	public Locale l;



	public OldGUI () {
		this.setTitle(status + version);
		this.init();
	}

	protected void init() {
		@SuppressWarnings("unused")
		Configurator c = Configurator.getConfigurator();
//		c.load("D:/config.pce");		
//		this.setUndecorated(true); 
//		if (c.getProperty("fullscreen") == "true") {
		this.setExtendedState(Frame.MAXIMIZED_BOTH);
//		} else {
//			this.setSize (1080, 984);   //Это для случая оконного режима.
//		}
		l = new Locale ();
		l.setLocale("ruRU");
		this.pathPanel = new JPanel ();
		this.pathPanel.setLayout(new BoxLayout (pathPanel, BoxLayout.X_AXIS));
		addRootButtons();

		this.setJMenuBar(getJMenuBar());

		this.lPath = new JLabel ();
		this.lPath.setText("C:");
		this.jtf = new JTextField ();
		this.jtf.setVisible(false);
		this.pathPanel.add(this.lPath);
		this.pathPanel.add(this.jtf);
		OldGUI.tableModel = new FileList ();
		this.table1 = new JTable(tableModel);

		this.table1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		this.mainFilePanel = new JPanel ();
		this.mainFilePanel.setBorder(BorderFactory.createTitledBorder("Файлы"));
		this.mainFilePanel.setLayout(new BorderLayout());
		this.mainFilePanel.add(new JScrollPane(table1), BorderLayout.CENTER);



		this.buttonPanel = getButtonPanel();
		addListeners();

		this.getContentPane().setLayout(new BorderLayout());
		this.getContentPane().add(this.pathPanel, BorderLayout.NORTH);
		this.getContentPane().add(this.mainFilePanel, BorderLayout.CENTER);
		this.getContentPane().add(this.buttonPanel, BorderLayout.SOUTH);
		this.lPath.setText("C:\\");
		File file = new File(this.lPath.getText());
		String newFiles[] = file.list();
		for (int i =0; i <newFiles.length; i++) {
			OldGUI.tableModel.addFile(new File(this.lPath.getText()+newFiles[i]));
		}
	}

	private JPanel getButtonPanel() {
		JPanel ret = new JPanel ();

//		this.open = new JButton ("open temp..");
		this.fwd = new JButton (l.fwd());
		this.back = new JButton ("Назад");
		this.quit = new JButton ("Выйти");
		this.delete = new JButton ("Удалить");
		this.copy = new JButton ("Копировать");
		this.paste = new JButton ("Вставить");

//		ret.add(this.open);
		ret.add(this.fwd);
		ret.add(this.back);
		ret.add(this.quit);
		ret.add(this.delete);
		ret.add(this.copy);
		ret.add(this.paste);

		return ret;
	}

	private void infoView() {
		JPanel infoPanel = new JPanel ();
		final JFrame infoFrame = new JFrame ();

		infoPanel.add (new JLabel ("Автор: NikichXP aka iSyncOS   Заказчик: Абу-Усбах Алексей Нидальевич"));
		infoPanel.add (new JLabel (""));
		infoPanel.add (new JLabel ("(с) Май 2014"));
		JButton ok = new JButton ("ok");
		ok.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent arg0) {
				infoFrame.setVisible(false);
			}
		});
		infoPanel.add (ok);
		infoFrame.add(infoPanel);
//		infoFrame.pack();
		infoFrame.setVisible(true);
	}

	public void addRootButtons() {
		File [] disks = File.listRoots();
		final JButton [] diskBtn = new JButton [disks.length];
		JLabel[] nullLabel = new JLabel [disks.length];
		for (int i = 0; i < disks.length; i++) {
			diskBtn[i] = new JButton ("  " + disks[i].getAbsolutePath() + "  ");
			this.pathPanel.add(diskBtn[i]);
			nullLabel[i] = new JLabel (" ");
			this.pathPanel.add(nullLabel[i]);
			diskBtn[i].addActionListener(new ActionListener() {
				@Override public void actionPerformed(ActionEvent arg0) {
					openButtonEvent (arg0.getActionCommand().charAt(2) + ":\\");
				}
			});
			diskBtn[i].setToolTipText(l.free() + disks[i].getFreeSpace()/1073741824 + "GB/" +
					+ disks[i].getTotalSpace()/1073741824 + "GB");
			diskBtn[i].setAlignmentX(LEFT_ALIGNMENT);

		}
	}
	//МНОГО СЛУШАТЕЛЕЙ
	private void addListeners() {
		this.table1.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override	public void valueChanged(ListSelectionEvent arg0) {	tableSelectionChanged(arg0); }
		});
		this.table1.addMouseListener(new MouseListener() {
			@Override public void mouseReleased(MouseEvent arg0) { }
			@Override public void mousePressed(MouseEvent arg0) { }
			@Override public void mouseExited(MouseEvent arg0) { }
			@Override public void mouseEntered(MouseEvent arg0) { }
			@Override public void mouseClicked(MouseEvent arg0) { tableClickedEvent(arg0); }});
		this.addWindowListener(new WindowListener() {
			@Override	public void windowClosing(WindowEvent arg0) { System.exit(0); }
			@Override	public void windowActivated(WindowEvent arg0) {}
			@Override	public void windowClosed(WindowEvent arg0) {}
			@Override	public void windowDeactivated(WindowEvent arg0) {}
			@Override	public void windowDeiconified(WindowEvent arg0) {}
			@Override	public void windowIconified(WindowEvent arg0) {}
			@Override	public void windowOpened(WindowEvent arg0) {}
		});
		this.table1.addKeyListener(new KeyListener() {
			@Override public void keyTyped(KeyEvent arg0) {	}
			@Override public void keyReleased(KeyEvent arg0) { }
			@Override public void keyPressed(KeyEvent arg0) { keyBoardEvent (arg0); }
		});
		this.lPath.setToolTipText("Введите путь сюда");
		this.lPath.addMouseListener(new MouseListener() {
			@Override public void mouseReleased(MouseEvent arg0) { }
			@Override public void mousePressed(MouseEvent arg0) { }
			@Override public void mouseExited(MouseEvent arg0) { }
			@Override public void mouseEntered(MouseEvent arg0) { }
			@Override public void mouseClicked(MouseEvent arg0) {
				lPath.setVisible(false);
				jtf.setText(lPath.getText());
				jtf.setVisible(true);
			}});
		this.jtf.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				jtf.setVisible (false);
				lPath.setText(arg0.getActionCommand());
				lPath.setText(jtf.getText());
				lPath.setVisible (true);
				openButtonEvent(arg0.getActionCommand());
			}});
//		this.open.addActionListener(new ActionListener() {
//			@Override public void actionPerformed(ActionEvent arg0) { openButtonEvent (); }});
		this.fwd.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent arg0) { fwdButtonEvent (); }});
		this.back.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent e) { backButtonEvent(); }});
		this.quit.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent e) { quitButtonEvent (); }});
		this.delete.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent arg0) { deleteButtonEvent (); }});
		this.copy.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent e) { copyButtonEvent (); }});
		this.paste.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent e) { pasteButtonEvent (); }});
	}
	protected void tableClickedEvent(MouseEvent arg0) {
		if ((arg0.getWhen() - clickTime) < 500 && doubleClickPossible) {
			fwdButtonEvent();
		} else {
			clickTime = arg0.getWhen();
			doubleClickPossible = true;
		}

	}
	protected void pathChangedEvent(String path) {
		lPath.setText(path);
		tableModel.clearTable();
		path+=" ";
		if (path.contains(":\\ ")) {
			File f2 = new File(path);
			String s[] = f2.list();
//			back.setEnabled(false);
			for (int i = 1; i<s.length; i++) {
				String target = path.trim().concat(s[i]);
				tableModel.addFile(new File(target));
			}
		} else {
			File f2 = new File(path);
			String s[] = f2.list();
			back.setEnabled(true);
			for (int i = 1; i<s.length; i++) {
				String target = path.trim().concat("\\".concat(s[i]));
				tableModel.addFile(new File(target));
			}
		}
	}
	protected void openButtonEvent () {
		lPath.setText(JOptionPane.showInputDialog("Введите путь:"));
		tableModel.clearTable();
		String path = lPath.getText();
		path+=" ";
		if (path.contains(":\\ ")) {
			File f2 = new File(path);
			String s[] = f2.list();
//			back.setEnabled(false);
			for (int i = 1; i<s.length; i++) {
				String target = path.trim().concat(s[i]);
				tableModel.addFile(new File(target));
			}
		} else {
			File f2 = new File(path);
			String s[] = f2.list();
			back.setEnabled(true);
			for (int i = 1; i<s.length; i++) {
				String target = path.trim().concat("\\".concat(s[i]));
				tableModel.addFile(new File(target));
			}
		}
	}
	protected void openButtonEvent (String pat) {
		lPath.setText(pat);
		tableModel.clearTable();
		String path = lPath.getText();
		path+=" ";
		if (path.contains(":\\ ")) {
			File f2 = new File(path);
			String s[] = f2.list();
//			back.setEnabled(false);
			for (int i = 1; i<s.length; i++) {
				String target = path.trim().concat(s[i]);
				tableModel.addFile(new File(target));
			}
		} else {
			File f2 = new File(path);
			String s[] = f2.list();
			back.setEnabled(true);
			for (int i = 1; i<s.length; i++) {
				String target = path.trim().concat("\\".concat(s[i]));
				tableModel.addFile(new File(target));
			}
		}
	}
	protected void fwdButtonEvent () {
		String path = lPath.getText();
		path+=" ";
		String target;
		String newPath;
		if (path.contains(":\\ ")) {
			path = path.trim();
		}
		target = path.trim().concat("\\".concat(tableModel.getFile(table1.getSelectedRow()).getName()));
		File loc = new File(target);
		if (target.endsWith(".exe")) {
			try {
				Runtime.getRuntime().exec(target);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (loc.isFile()){
			try {
				Desktop.getDesktop().edit(loc);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			lPath.setText(target);
			String s[] = loc.list();
			tableModel.clearTable();
			back.setEnabled(true);
			for (int i = 0; i<s.length; i++) {
				newPath=target.trim()+"\\"+s[i];
				tableModel.addFile(new File(newPath));
			}
		}
	}
	protected void backButtonEvent () {
		File loc = new File(lPath.getText());
		String path = loc.getParent();
		File f2 = new File(loc.getParent());
		String s[] = f2.list();
		tableModel.clearTable();
		lPath.setText(path);
		for (int i = 0;i<s.length; i++) {
			tableModel.addFile(new File(lPath.getText()+"\\"+s[i]));
		}
		path+=" ";
		if (path.contains(":\\ ")) {
//			back.setEnabled(false);
		} else {
			back.setEnabled(true);
		}
	}
	protected void quitButtonEvent () {
		System.exit(0);
	}
	protected void deleteButtonEvent () {
		deleteProc();
	}
	protected void copyButtonEvent () {
		if (table1.getSelectedRow() == -1) {
			JOptionPane.showMessageDialog(null, "Файл не выбран");
		} else {
			copyPath = tableModel.getFile(table1.getSelectedRow()).toPath();
		}
	}
	protected void pasteButtonEvent () {
		File loc = copyPath.toFile();
		String path = lPath.getText();
		path+=" ";
		File f2;
		if (path.contains(":\\ ")) {
			path=path.trim();
			path+=loc.getName();
			f2 = new File(path);
		} else {
			path=path.trim();
			path=path+"\\"+loc.getName();
			f2 = new File(path);
		}
		try {
			Files.copy(loc.toPath(), f2.toPath(), StandardCopyOption.REPLACE_EXISTING);
			tableModel.addFile(f2);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	protected void keyBoardEvent (KeyEvent arg0) {
		if (arg0.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
			backButtonEvent();
		} else {
			if (table1.getSelectedRow() == -1) {
				JOptionPane.showMessageDialog(null, "Файл не выбран");
			} else {
				switch (arg0.getKeyCode()) {
					case KeyEvent.VK_ENTER:
						fwdButtonEvent();
						break;
					case KeyEvent.VK_C:
						copyPath = tableModel.getFile(table1.getSelectedRow()).toPath();
						break;
					case KeyEvent.VK_V:
						pasteButtonEvent();
						break;
					case KeyEvent.VK_DELETE:
						deleteProc();
						break;
				}
			}
		}
	}
	private void deleteProc () {
		int q = JOptionPane.showConfirmDialog(null, "Удалить файл?");
		if (q == 0) {
			File loc = tableModel.getFile(table1.getSelectedRow());
			if (loc.isDirectory()) {
				deleteDir(loc);
			} else {
				if (loc.delete()) {
					JOptionPane.showMessageDialog(null, "Файл успешно удалён.");
					tableModel.clearFile(table1.getSelectedRow());
				} else {
					JOptionPane.showMessageDialog(null, "Удалить файл не удалось.");
				}
			}
		}
	}
	@SuppressWarnings("unused")
	private void deleteProc (File loc) {
		int q = JOptionPane.showConfirmDialog(null, "Удалить файл?");
		if (q == 0) {
			loc = tableModel.getFile(table1.getSelectedRow());
			if (loc.isDirectory()) {
				deleteDir(loc);
			} else {
				if (loc.delete()) {
					JOptionPane.showMessageDialog(null, "Файл успешно удалён.");
					tableModel.clearFile(table1.getSelectedRow());
				} else {
					JOptionPane.showMessageDialog(null, "Удалить файл не удалось.");
				}
			}
		}
	}
	protected void deleteDir(File f) {
		String s[] = f.list();
		File f2;
		if (s == null) {
			try {
				Files.delete(f.toPath());
			} catch (IOException e) { e.printStackTrace(); }
		}
		for (int i=0; i<s.length;i++) {
			f2 = new File(f.getPath()+"\\"+s[i]);
			if (f2.isFile()) {
				try {
					Files.delete(f2.toPath());
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null, "Не удалось удалить файл");
				}
			} else {
				del(f2);
				try {
					Files.delete(f2.toPath());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		try {
			Files.delete(f.toPath());
		} catch (IOException e) {
			e.printStackTrace();
		}
		tableModel.clearFile(table1.getSelectedRow());
		JOptionPane.showMessageDialog(null, "Файл успешно удалён");
	}
	private void del(File f) {
		String s[] = f.list();
		File f2;
		for (int i=0; i<s.length;i++) {
			f2 = new File(f.getPath()+"\\"+s[i]);
			if (f2.isFile()) {
				try {
					Files.delete(f2.toPath());
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null, "Не удалось удалить файл");
				}
			} else {
				del(f2);
				try {
					Files.delete(f2.toPath());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}


	//Реализация дабл-клика
	protected long clickTime;
	public boolean doubleClickPossible;
	protected void tableSelectionChanged(ListSelectionEvent arg0) {
		doubleClickPossible = false;
	}
	public JMenuBar getJMenuBar () {
		JMenu fileMenu = new JMenu ("Файл");
		JMenu helpMenu = new JMenu ("Помощь");
		JMenuItem openLoc = new JMenuItem ("Открыть", KeyEvent.VK_O);
		JMenuItem close = new JMenuItem ("Выход", KeyEvent.VK_Q);
		JMenuItem help = new JMenuItem ("Об авторе", KeyEvent.VK_H);
		JMenuItem options = new JMenuItem ("Настройки", KeyEvent.VK_C);
		fileMenu.add(openLoc);
		fileMenu.addSeparator();
		fileMenu.add(close);
		openLoc.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent arg0) { openButtonEvent(); }});
		close.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent arg0) { System.exit(0); }});

		helpMenu.add(help);
		helpMenu.add(options);
		help.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent arg0) { infoView (); }});
		options.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent arg0) {
				JFrame optfrm = new JFrame ();
				JPanel optpan = new JPanel();
				JPanel optPanel1 = new JPanel ();
				optPanel1.add(new JLabel ("gg1"));
				JPanel optPanel2 = new JPanel ();
				optPanel2.add(new JLabel ("gg2"));
				JTabbedPane tabs = new JTabbedPane ();
				tabs.addTab("Test1", optPanel1);
				tabs.addTab("Test2", optPanel2);
				optpan.add(tabs);
				optfrm.add(optpan);
//				optfrm.pack();
				optfrm.setSize(400, 200);
				optfrm.setVisible(true);
			}
		});
		JMenuBar menuBar = new JMenuBar();
		menuBar.add(fileMenu);
		menuBar.add(helpMenu);
		return menuBar;
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				OldGUI manager = new OldGUI();
				manager.setVisible(true);
			}
		});
	}
}
