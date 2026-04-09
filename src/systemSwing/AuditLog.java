package systemSwing;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.security.Key;
import java.util.Arrays;

import javax.swing.*;

import SwingCards.TeacherPanelView;
import control.PromptsService;
import databaseAccess.SystemSettingsModule;
import databaseConstants.DatabaseUtilities;

import static util.SwingConstants.MainGray;

public class AuditLog extends Panel {
	
	int leftMarginX;
	int rightMarginX;
	
	int topMarginY;
	int bottomMarginY;
	
	LogScroller auditLog;
	
	Button ProfileSort;
	boolean profileSort = false;
	Button ProductionSort;
	boolean productionSort = false;
	Button AdminSort;
	boolean adminSort = false;
	Input searchSort;
	String query = "";
	
	Button submitButton;
	JTextArea TeachersNote;
	DropDownMenu TeacherSelector;
	
	private String ID;
	
	public AuditLog ( TeacherPanelView parent ) {
		
		super(true, true, true, true);
		
		this.setLayout(null);
		
		ProfileSort = new Button("Profile", (e -> {
			
			profileSort = !profileSort;
			productionSort = false;
			adminSort = false;
			Update();
			
		}), new Dimension(this.getWidth() / 10, this.getHeight() / 7), 500, 20, (!profileSort) ?
			   MainGray.brighter() : MainGray, 40, 11);
		ProductionSort = new Button("Production", (e -> {
			
			profileSort = false;
			productionSort = !productionSort;
			adminSort = false;
			Update();
			
		}), new Dimension(this.getWidth() / 10, this.getHeight() / 7), 400, 20, (!productionSort) ?
			   MainGray.brighter() : MainGray, 40, 11);
		AdminSort = new Button("Admin", (e -> {
			
			profileSort = false;
			productionSort = false;
			adminSort = !adminSort;
			Update();
			
		}), new Dimension(this.getWidth() / 10, this.getHeight() / 7), 0, 20, (!adminSort) ? MainGray.brighter() :
			   MainGray, 40, 11);
		
		searchSort = new Input(20, 20, this.getWidth() / 3, this.getHeight() / 9, 15, Color.white, 40);
		
		searchSort.setVisible(true);
		
		TeacherSelector =
			   new DropDownMenu(databaseAccess.SystemSettingsModule.getAllOfSystemDetail(SystemSettingsModule.TEACHERS));
		
		TeacherSelector.setVisible(true);
		this.add(TeacherSelector);
		
		submitButton = new Button("Submit", (e -> {
			
			databaseAccess.StudentsModule.addTeachersNote((databaseAccess.StudentsModule.getStudentDetail(ID,
				   DatabaseUtilities.FIRST_NAME) + " " + databaseAccess.StudentsModule.getStudentDetail(ID,
				   DatabaseUtilities.LAST_NAME)), databaseAccess.StudentsModule.getStudentDetail(ID,
				   DatabaseUtilities.DATE_OF_BIRTH), TeacherSelector.getSelectedItem().toString(),
				   TeachersNote.getText());
			
			TeachersNote.setText("");
			
			PromptsService.SuccessPrompt("Note added!");
			Update();
			
		}), new Dimension(0, 0), 0, 0, Color.lightGray, 40, 15);
		
		this.add(submitButton);
		
		TeachersNote = new JTextArea(1, 20) {
			{
				setOpaque(false);
				setFocusable(true);
				setBorder(null);
				setSelectionColor(new Color(0, 0, 0, 0));
				setFocusTraversalKeysEnabled(false);
				
			}
			
			@Override
			public void setText ( String text ) {
				
				super.setText(text);
				this.setForeground(Color.black);
				
			}
			
			@Override
			protected void paintComponent ( Graphics g ) {
				
				Graphics2D g2 = (Graphics2D) g;
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g2.setColor(getBackground());
				g2.fillRoundRect(0, 0, getWidth(), getHeight(), 50, 50);
				
				super.paintComponent(g);
				
			}
			
		};
		TeachersNote.setLineWrap(true);
		TeachersNote.setWrapStyleWord(true);
		
		JTextField referenceField = new JTextField();
		TeachersNote.setBorder(referenceField.getBorder());
		TeachersNote.setFont(referenceField.getFont());
		TeachersNote.setBackground(referenceField.getBackground());
		TeachersNote.setForeground(referenceField.getForeground());
		TeachersNote.setFont(new Font(Font.SANS_SERIF, 0, 20));
		
		TeachersNote.getInputMap().put(KeyStroke.getKeyStroke("ENTER"), "none");
		
		TeachersNote.setVisible(true);
		
		this.add(TeachersNote);
		
		
		this.add(searchSort);
		
		ProfileSort.setVisible(true);
		ProductionSort.setVisible(true);
		AdminSort.setVisible(true);
		
		
		auditLog = new LogScroller();
		auditLog.setLogHeight(this.getHeight() / 30);
		this.add(auditLog);
		this.add(ProfileSort);
		this.add(ProductionSort);
		this.add(AdminSort);
		
		KeyListener searchUpdate = new KeyListener() {
			@Override
			public void keyTyped ( KeyEvent e ) {
				//DO NOTHING
			}
			
			@Override
			public void keyPressed ( KeyEvent e ) {
				//DO NOTHING
			}
			
			@Override
			public void keyReleased ( KeyEvent e ) {
				if (searchSort.getText().length() >= 3) {
					
					query = searchSort.getText();
					UpdateSearch();
					
				} else if (searchSort.getText().isEmpty() || searchSort.getText().isBlank()) {
					
					query = "";
					UpdateSearch();
					
				}
			}
		};
		
		searchSort.addKeyListener(searchUpdate);
		
	}
	
	public void setSize ( int X, int Y ) {
		
		leftMarginX = this.getSize().width / 20;
		rightMarginX = this.getSize().width - this.getSize().width / 20;
		
		topMarginY = (int) (this.getSize().height / 5.0);
		bottomMarginY = this.getSize().height - (int) (this.getSize().height / 10.0);
		
		this.setBounds(this.getBounds().x, this.getBounds().y, X, Y);
		
		this.auditLog.setSize(new Dimension((int) ((X - 40) * (2.0 / 3.0)), Y - (Y / 6)));
		this.auditLog.ScrollPanel.setBackground(MainGray.brighter());
		
		this.auditLog.setLocation(20, (Y - 90) / 6);
		
		auditLog.setLogHeight(this.getHeight() / 10);
		
		ProfileSort.setBounds(650, 10, this.getWidth() / 11, this.getHeight() / 11);
		ProductionSort.setBounds(530, 10, this.getWidth() / 11, this.getHeight() / 11);
		AdminSort.setBounds(410, 10, this.getWidth() / 11, this.getHeight() / 11);
		
		searchSort.setBounds(20, 10, this.getWidth() / 3, this.getHeight() / 11);
		
		TeacherSelector.setBounds((int) ((X - 40) * (2.0 / 3.0) + 30), Y - (Y / 6), 180, 40);
		
		submitButton.setBounds((int) ((X - 40) * (2.0 / 3.0) + 30) + 190, Y - (Y / 6), 160, 40);
		
		TeachersNote.setBounds((int) ((X - 40) * (2.0 / 3.0) + 30), Y / 7, 350, (int)(Y * (2.0/3.0)));
		
	}
	
	public void setPosition ( int X, int Y ) {
		
		this.setBounds(X, Y, this.getSize().width, this.getSize().height);
		
	}
	
	public void setStudent ( String studentID ) {
		
		this.ID = studentID;
		
		String[] studentLogs = {};
		
		try {
			
			if (profileSort) {
				studentLogs = databaseAccess.StudentsModule.returnAllProfile(this.ID);
			} else if (productionSort) {
				
				studentLogs = databaseAccess.StudentsModule.returnAllProduction(this.ID);
			} else if (adminSort) {
				studentLogs = databaseAccess.StudentsModule.returnAllAdmin(this.ID);
			} else {
				studentLogs = databaseAccess.StudentsModule.getAllRecords(this.ID);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		this.auditLog.receiveLog(studentLogs, query);
		
	}
	
	public void Update () {
		
		setStudent(ID);
		
		ProfileSort.setBackground((!profileSort) ? MainGray.brighter() : MainGray);
		ProductionSort.setBackground((!productionSort) ? MainGray.brighter() : MainGray);
		AdminSort.setBackground((!adminSort) ? MainGray.brighter() : MainGray);
		
		revalidate();
		repaint();
		
	}
	
	public void UpdateSearch () {
		
		this.auditLog.narrowResults(query);
		revalidate();
		repaint();
		
	}
	
	
	@Override
	
	public void paint ( Graphics g ) {
		
		Graphics2D g2 = (Graphics2D) g;
		
		super.paint(g);
		
	}
	
}
