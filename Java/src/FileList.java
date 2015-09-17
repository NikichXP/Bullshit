import java.io.File;
import java.util.*;

public class FileList extends javax.swing.table.AbstractTableModel {
	private final long serialVersionUID = 935843842185535018L;  //��� ���������� ���� ��������������!
	private final String[] columnNames = {"��� �����", "������", "�������", "������"};//, "����������"}; //��� ����������
	@SuppressWarnings("rawtypes")
	private final Class[] columnClasses = {String.class, String.class, String.class, String.class, String.class};
	public ArrayList<File> files = new ArrayList<File>();
	public String path = "C:\\";
	
	//���������� �����
	public void addFile(File file) {
		files.add(file);
		fireTableRowsInserted(getRowCount() - 1, getRowCount() - 1);
	}
	//��������� �����
	public File getFile(int row) {
		if (row < 0 | row>files.size()) {
			return null;
		} else {
			return files.get(row);
		}
	}
	//������� �������
	public void clearTable() {
		if (files.size() != 0) {
			fireTableRowsDeleted(0,files.size()-1);
			files.clear();
		}
	}
	//�������� ����������� ������ 
	public void clearFile(int row) {
		files.remove(row);
		fireTableRowsDeleted(row,row);
	}
	//��������� ��� ��������
	public String getColumnName(int col) {
		return columnNames[col];
	}
	//��������� ������� ��������
	public Class getColumnClass(int col) {
		return columnClasses[col];
	}
	//��������� ���������� ��������
	public int getColumnCount() {
		return columnNames.length;
	}
	//��������� ���������� �����
	public int getRowCount() {
		return files.size();
	}
	//��������� �������� �� ������ [row,col]
	public Object getValueAt(int row, int col) {
		File f1 = files.get(row);
		switch (col) {
			case 0: 
				return f1.getName();
			case 1: 
				long size = f1.length();
				if (!(f1.isFile())) {
					return "<DIR>";
				}
				String res;
				if (size >= 1024) {
					if (size >= 1024*1024) {
						if (size >= 1024*1024*1024) {
							size=(long) size/1024;
							size=(long) size/1024;
							size=(long) size/1024;
							res = Long.toString(size) + " GB";
							return res;
						}
						size=(long) size/1024;
						size=(long) size/1024;
						res = Long.toString(size) + " MB";
						return res;
					}
					size=(long) size/1024;
					res = Long.toString(size) + " KB";
				} 
				 if (size == -1) {
					 return "";
				 } else {
					 res = Long.toString(size) + " Bytes";
				 }
				 return res;
			case 2: 
				Date date = new Date(f1.lastModified());
				Formatter fmt = new Formatter();
				fmt.format("%tH:%tM:%tS %td %tB %tY", date,date,date,date,date,date);
				return fmt;
			case 3:
				String access = "";
				if (f1.canRead()) { access+="r"; } else { access+="-"; }
				if (f1.canWrite()) { access+="w"; } else { access+="-"; }
				if (f1.canExecute()) { access+="x"; } else {access += "-"; }
				return access;
			case 4: 
				if (f1.isFile()) {
					String name = f1.getName();
					if (name.contains(".")) {
						name = name.substring(name.lastIndexOf('.')+1, name.length());
						return name;
					} else {
						return "-";
					}
				} else {
					return "-";
			}
				default:
					return "(\\/)_<0.o>_(\\/) ����-����!! (��� ������)";
		}
	}
}
