package com.slickedit.color.scheme.converter;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.dnd.DropTarget;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;

import com.slickedit.color.scheme.converter.dialog.AboutDialog;
import com.slickedit.color.scheme.converter.handler.ColorGenerationHandler;
import com.slickedit.color.scheme.converter.listener.DragDropListener;

import java.awt.Toolkit;

public class Main {

	private JFrame frmColorSchemeConverter;
	
	private Shell s;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main();
					window.frmColorSchemeConverter.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Main() {
		s = new Shell();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmColorSchemeConverter = new JFrame();
		frmColorSchemeConverter.setIconImage(Toolkit.getDefaultToolkit().getImage(Main.class.getResource("/com/slickedit/color/scheme/converter/icon/slickedit.png")));
		frmColorSchemeConverter.setTitle("Color Scheme Converter");
		frmColorSchemeConverter.setResizable(false);
		frmColorSchemeConverter.setBounds(100, 100, 255, 214);
		frmColorSchemeConverter.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmColorSchemeConverter.getContentPane().setLayout(null);

		JLabel lblDragXmlFile = new JLabel();
		lblDragXmlFile.setText("Drag XML File Here !");
		lblDragXmlFile.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblDragXmlFile.setBounds(50, 74, 149, 20);
		frmColorSchemeConverter.getContentPane().add(lblDragXmlFile);

		JMenuBar menuBar = new JMenuBar();
		frmColorSchemeConverter.setJMenuBar(menuBar);

		JMenu mnFile = new JMenu("File");
		mnFile.setMnemonic('F');
		menuBar.add(mnFile);

		JMenuItem mntmOpen = new JMenuItem("Open");
		mntmOpen.addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				FileDialog fd = new FileDialog(s, SWT.OPEN);
				fd.setText("Open XML File");
				fd.setFilterPath("C:/");
				String[] filterExt = { "*.xml", "*.*" };
				fd.setFilterExtensions(filterExt);
				String selected = fd.open();
				System.out.println(selected);
				if (selected != null) {
					File file = new File(selected);
					try {
						new ColorGenerationHandler(file.getAbsolutePath(), file.getParent()).execute();
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}	
			}
		});
		mnFile.add(mntmOpen);

		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				System.exit(0);
			}
		});
		mnFile.add(mntmExit);

		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);

		JMenuItem mntmAbout = new JMenuItem("About");
		mntmAbout.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				new AboutDialog(s).open();
			}
		});
		mnHelp.add(mntmAbout);

		// Create the drag and drop listener
		DragDropListener myDragDropListener = new DragDropListener();

		// Connect the JFrame with a drag and drop listener
		new DropTarget(frmColorSchemeConverter, myDragDropListener);
		new DropTarget(lblDragXmlFile, myDragDropListener);
	}
}
