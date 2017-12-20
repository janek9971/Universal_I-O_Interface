import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class Window {

	private JFrame frame;
	// private StringBuilder temp = RS232Example.PORTS;
	private StringBuilder portsDirs = RS232Example.PORTS_DIRS_ALL;
	private StringBuilder portDir = RS232Example.PORT_DIR_ONE;
	private JComboBox<String> comboBox;
	private JToggleButton[] tglbtn1, tglbtn2;
	private JToggleButton pinSwitch;
	private JButton[] btnstate1, btnstate2;
	private JButton reset, meas, saveEprom, readEprom, resetClearEprom, directionButton, stateButton, autoButton;
	private String selectedPort;
	private JTextArea selectOption;
	private JComboBox<String> menu;
	private String option;
	private String pName, pNumber;
	private int number;
	private String time;
	private int state;
	private int[] buforek = new int[28];
	private int z = 0;
	private JLabel lblimage, lblimage2;
	private final String pinsTab[] = { "23", "26", "27", "28", "29", "30", "14", "15", "31", "32", "33", "8", "22",
			"21", "20", "19", "18", "17", "13", "12", "11", "10", "9" };
	private final String pinTabName[] = { "C", "D", "D", "D", "D", "D", "B", "B", "D", "D", "D", "B", "C", "C", "C",
			"C", "C", "C", "B", "B", "B", "B", "B" };
	private final String pinTabNumber[] = { "6", "0", "1", "2", "3", "4", "6", "7", "5", "6", "7", "0", "5", "4", "3",
			"2", "1", "0", "5", "4", "3", "2", "1" };;
	private static final String NOT_SELECTABLE_OPTION = " - Select an Option - ", PORT_DIR_ONE = "PORT\\DIR_ONE",
			PORTS_DIRS_ALL = "PORT\\DIR_ALL", MEAS = "MEASURMENT", NOT_SELECTABLE_SERIAL = " Select Serial Port ";
	private JTextField errorlabel;

	public Window() {
		initialize();

	}

	private void initialize() {
		frame = new JFrame("USART_Communication v0.7");
		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setBounds(0, 0, dim.width / 2, dim.height - 100);
		ImageIcon img = new ImageIcon(
				"C:\\Users\\Janek\\Desktop\\USARTWindowedApp-master\\USARTWindowedApp-master\\logo.png");
		frame.setLocationRelativeTo(null);
		frame.setIconImage(img.getImage());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		errorlabel = new JTextField("");
		errorlabel.setBounds(400, 747, 153, 30);
		errorlabel.setEnabled(false);
		// errorlabel.setFont(new Font("Serif", Font.PLAIN, 14));
		// s errorlabel.setHorizontalAlignment(SwingConstants.CENTER);
		frame.getContentPane().add(errorlabel);

		selectOption = new JTextArea();
		initializeSelectOption();

		menu = new JComboBox<String>();
		initializeMenu();

		comboBox = new JComboBox<String>();
		initializeComboBox();

		tglbtn1 = new JToggleButton[12];
		initializeTglbutton1();

		tglbtn2 = new JToggleButton[11];
		initializeTglbutton2();

		btnstate1 = new JButton[12];
		initializeBtnstate1();

		btnstate2 = new JButton[11];
		initializeBtnstate2();

		reset = new JButton();
		initializeResetButton();

		saveEprom = new JButton();
		initializeSaveEpromButton();

		readEprom = new JButton();
		initializeReadEpromButton();

		resetClearEprom = new JButton();
		initializeResetClearEprom();

		meas = new JButton();
		initializeMeas();

		pinSwitch = new JToggleButton();
		initializePinSwitch();

		directionButton = new JButton();
		initializeDirectionButton();

		stateButton = new JButton();
		initializeStateButton();

		autoButton = new JButton();
		initializeAutoButton();
		lblimage = new JLabel("");
		lblimage.setBounds(frame.getWidth() / 2 - 441, frame.getHeight() / 2 - 480, 242, 646);
		lblimage.setIcon(new ImageIcon(
				"C:\\Users\\Janek\\Desktop\\USARTWindowedApp-master\\USARTWindowedApp-master\\janusz.jpg"));
		frame.getContentPane().add(lblimage);

		lblimage2 = new JLabel("");
		lblimage2.setBounds(frame.getWidth() / 2 - 301, frame.getHeight() / 2 - 480, 242, 646);
		lblimage2.setIcon(new ImageIcon(
				"C:\\Users\\Janek\\Desktop\\USARTWindowedApp-master\\USARTWindowedApp-master\\onoff.jpg"));
		frame.getContentPane().add(lblimage2);

		// frame.getContentPane().add(new JLabel(new
		// ImageIcon("C:\\Users\\MaTEO\\Desktop\\USART_Communication\\atmega.png")));
	}

	private void initializeComboBox() {
		comboBox.setModel(new DefaultComboBoxModel<String>() {

			boolean selectionAllowed = true;

			@Override
			public void setSelectedItem(Object anObject) {
				if (!NOT_SELECTABLE_SERIAL.equals(anObject)) {
					super.setSelectedItem(anObject);
				} else if (selectionAllowed) {
					selectionAllowed = false;
					super.setSelectedItem(anObject);
				}
			}
		});

		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				comboBox = (JComboBox) e.getSource();
				if (comboBox.getSelectedItem().toString() != NOT_SELECTABLE_SERIAL) {
					selectedPort = comboBox.getSelectedItem().toString();
					// parsedSelectedPort = selectedPort.substring(1, selectedPort.length() - 1);

					try {
						RS232Example ex = new RS232Example();
						ex.connect(selectedPort);
					} catch (Exception exc) {
						exc.printStackTrace();
					}
				} else {

				}
			}
		});
		comboBox.setBounds(557, 839, 161, 40);
		frame.getContentPane().add(comboBox);
		ArrayList lista = new ListAvailablePorts().listSerialPorts();
		String portArray[] = null;
		ArrayList lista2 = lista;
		portArray = (String[]) lista2.toArray(new String[lista2.size()]);
		comboBox.addItem(NOT_SELECTABLE_SERIAL);
		comboBox.addItem(lista2.toString().replace("[", "").replace("]", ""));
	}

	private void initializeMenu() {
		menu.setModel(new DefaultComboBoxModel<String>() {

			boolean selectionAllowed = true;

			@Override
			public void setSelectedItem(Object anObject) {
				if (!NOT_SELECTABLE_OPTION.equals(anObject)) {
					super.setSelectedItem(anObject);
				} else if (selectionAllowed) {
					selectionAllowed = false;
					super.setSelectedItem(anObject);
				}
			}
		});

		menu.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				menu = (JComboBox) e.getSource();

				if (menu.getSelectedItem().toString() == NOT_SELECTABLE_OPTION) {
					selectOption.setText(null);
					state = 0;

				} else if (menu.getSelectedItem().toString() == PORT_DIR_ONE) {
					selectOption.setText(null);
					state = 1;

				} else if (menu.getSelectedItem().toString() == PORTS_DIRS_ALL) {
					selectOption.setText(null);
					state = 2;

				} else if (menu.getSelectedItem().toString() == MEAS) {
					selectOption.setText(null);
					state = 3;
				}

				try {
					if (state == 1) {
						directionButton.setEnabled(true);
						stateButton.setEnabled(true);
					}

					else if (state == 2) {
						directionButton.setEnabled(true);
						stateButton.setEnabled(true);
					} else {
						directionButton.setEnabled(false);
						stateButton.setEnabled(false);
					}
					if (state == 2) {
						pinSwitch.setEnabled(true);
					}

					else {
						pinSwitch.setEnabled(false);
					}

					if (state == 3) {
						meas.setEnabled(true);
					} else {
						meas.setEnabled(false);
					}
				} catch (NullPointerException exc) {

				}

			}
		});
		menu.setBounds(124, 110, 145, 40);
		frame.getContentPane().add(menu);

		menu.addItem(NOT_SELECTABLE_OPTION);
		menu.addItem(PORT_DIR_ONE);
		menu.addItem(PORTS_DIRS_ALL);
		menu.addItem(MEAS);
	}

	private void initializeTglbutton1() {
		for (int i = 0; i < tglbtn1.length; i++) {
			tglbtn1[i] = new JToggleButton("P" + (i + 1));
			tglbtn1[i].setBounds((frame.getWidth() / 2 - 318) + i * 47, (frame.getHeight() / 2 - 48), 50, 45);
			frame.getContentPane().add(tglbtn1[i]);
			tglbtn1[i].addItemListener(new ItemListener() {
				private int anonVar;

				@Override
				public void itemStateChanged(ItemEvent e) {
					switch (state) {
					case 1:
						if (getOption().equals("P")) {
							portDir.setCharAt(4, 'P');
						} else if (getOption().equals("D")) {
							portDir.setCharAt(4, 'D');
						}
						if (e.getStateChange() == ItemEvent.SELECTED) {

							portDir.setCharAt(5, pinTabName[anonVar].toString().charAt(0));
							portDir.setCharAt(7, pinTabNumber[anonVar].toString().charAt(0));
							portDir.setCharAt(9, '1');
							CommPortSender.send(new ProtocolImpl().getMessage(portDir.toString()));
							btnstate1[anonVar].setBackground(Color.GREEN);
							// System.out.println("i: " + anonVar);
						} else if (e.getStateChange() == ItemEvent.DESELECTED) {
							portDir.setCharAt(5, pinTabName[anonVar].toString().charAt(0));
							portDir.setCharAt(7, pinTabNumber[anonVar].toString().charAt(0));
							portDir.setCharAt(9, '0');

							CommPortSender.send(new ProtocolImpl().getMessage(portDir.toString()));
							btnstate1[anonVar].setBackground(Color.RED);
						}

						break;
					case 2:
						if (getNumber() == 0) {
							portsDirs.setCharAt(6, '0');
						} else if (getNumber() == 1) {
							portsDirs.setCharAt(6, '1');
						} else if (getNumber() == 2) {
							portsDirs.setCharAt(6, '2');
						} else {

						}

						if (e.getStateChange() == ItemEvent.SELECTED) {

							portsDirs.setCharAt(Integer.parseInt(pinsTab[anonVar]), '1');

							// CommPortSender.send(new ProtocolImpl().getMessage(portsDirs.toString()));
							btnstate1[anonVar].setBackground(Color.GREEN);

						} else if (e.getStateChange() == ItemEvent.DESELECTED) {
							buforek[anonVar] = 0;
							portsDirs.setCharAt(Integer.parseInt(pinsTab[anonVar]), '0');

							// CommPortSender.send(new ProtocolImpl().getMessage(portsDirs.toString()));
							btnstate1[anonVar].setBackground(Color.RED);
						}
						break;
					case 3:
						if (e.getStateChange() == ItemEvent.SELECTED) {

							if (z == 0) {

								pName = pinTabName[anonVar];
								pNumber = pinTabNumber[anonVar];
								z = 1;
							} else {
								JOptionPane.showMessageDialog(frame, "ONE port!", "Error", JOptionPane.ERROR_MESSAGE);
								z = 1;
							}
						} else if (e.getStateChange() == ItemEvent.DESELECTED) {

							z = 0;
						}

						break;
					}

				}

				private ItemListener init(int var) {
					anonVar = var;
					return this;
				}
			}.init(i));

		}
	}

	private void initializeTglbutton2() {
		int j = 23;
		for (int i = 0; i < tglbtn2.length; i++) {

			tglbtn2[i] = new JToggleButton("P" + j);
			tglbtn2[i].setBounds((frame.getWidth() / 2 - 273) + i * 47, (frame.getHeight() / 2 + 48), 50, 45);
			frame.getContentPane().add(tglbtn2[i]);
			j--;
			tglbtn2[i].addItemListener(new ItemListener() {
				private int anonVar;

				@Override
				public void itemStateChanged(ItemEvent e) {
					switch (state) {
					case 1:
						if (getOption().equals("P")) {
							portDir.setCharAt(4, 'P');
						} else if (getOption().equals("D")) {
							portDir.setCharAt(4, 'D');
						}
						if (e.getStateChange() == ItemEvent.SELECTED) {

							portDir.setCharAt(5, pinTabName[anonVar + 12].toString().charAt(0));
							portDir.setCharAt(7, pinTabNumber[anonVar + 12].toString().charAt(0));
							portDir.setCharAt(9, '1');
							CommPortSender.send(new ProtocolImpl().getMessage(portDir.toString()));
							btnstate2[anonVar].setBackground(Color.GREEN);
							// System.out.println("i: " + anonVar);
						} else if (e.getStateChange() == ItemEvent.DESELECTED) {
							portDir.setCharAt(5, pinTabName[anonVar + 12].toString().charAt(0));
							portDir.setCharAt(7, pinTabNumber[anonVar + 12].toString().charAt(0));
							portDir.setCharAt(9, '0');

							CommPortSender.send(new ProtocolImpl().getMessage(portDir.toString()));
							btnstate2[anonVar].setBackground(Color.RED);
						}

						break;
					case 2:
						if (getNumber() == 0) {
							portsDirs.setCharAt(6, '0');
						} else if (getNumber() == 1) {
							portsDirs.setCharAt(6, '1');
						} else if (getNumber() == 2) {
							portsDirs.setCharAt(6, '2');
						} else {

						}
						if (time != null) {
							portsDirs.setCharAt(35, time.charAt(0));
						}
						if (e.getStateChange() == ItemEvent.SELECTED) {

							portsDirs.setCharAt(Integer.parseInt(pinsTab[anonVar + 12]), '1');

							// CommPortSender.send(new ProtocolImpl().getMessage(portsDirs.toString()));
							btnstate2[anonVar].setBackground(Color.GREEN);
							// System.out.println("i: " + anonVar);
						} else if (e.getStateChange() == ItemEvent.DESELECTED) {
							buforek[anonVar] = 0;
							portsDirs.setCharAt(Integer.parseInt(pinsTab[anonVar + 12]), '0');

							// CommPortSender.send(new ProtocolImpl().getMessage(portsDirs.toString()));
							btnstate2[anonVar].setBackground(Color.RED);
						}
						break;
					case 3:
						if (e.getStateChange() == ItemEvent.SELECTED) {

							if (z == 0) {
								pName = pinTabName[anonVar + 12];
								pNumber = pinTabNumber[anonVar + 12];
								z = 1;
							} else {
								JOptionPane.showMessageDialog(frame, "ONE port!", "Error", JOptionPane.ERROR_MESSAGE);
								z = 1;
							}
						} else if (e.getStateChange() == ItemEvent.DESELECTED) {

							z = 0;
						}

						break;
					}

				}

				private ItemListener init(int var) {
					anonVar = var;
					return this;
				}
			}.init(i));
		}
	}

	private void initializeSelectOption() {
		// selectOption.setBounds(14, 110, 100, 30);
		// frame.getContentPane().add(selectOption);

		selectOption.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void insertUpdate(DocumentEvent e) {

				option = selectOption.getText();
				try {
					time = selectOption.getText();
					number = Integer.parseInt(selectOption.getText());
				} catch (NullPointerException exc) {

				} catch (NumberFormatException exc) {

				}
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				option = selectOption.getText();
				try {
					number = Integer.parseInt(selectOption.getText());
				} catch (NullPointerException exc) {

				} catch (NumberFormatException exc) {

				}

			}
		});

	}

	private void initializeBtnstate1() {
		for (int i = 0; i < tglbtn1.length; i++) {
			btnstate1[i] = new JButton();
			btnstate1[i].setBounds((frame.getWidth() / 2 - 300) + i * 47, (frame.getHeight() / 2) - 65, 15, 15);
			// btnstate1[i].setSelected(false);
			// btnstate1[i].setDisabledIcon(btnstate1[i].getIcon());
			btnstate1[i].setBackground(Color.RED);
			frame.getContentPane().add(btnstate1[i]);
			btnstate1[i].addItemListener(new ItemListener() {

				@Override
				public void itemStateChanged(ItemEvent e) {

				}
			});
		}
	}

	private void initializeBtnstate2() {
		for (int i = 0; i < btnstate2.length; i++) {
			btnstate2[i] = new JButton();
			btnstate2[i].setBounds((frame.getWidth() / 2 - 255) + i * 47, (frame.getHeight() / 2) + 35, 15, 15);
			btnstate2[i].setSelected(false);
			btnstate2[i].setDisabledIcon(btnstate2[i].getIcon());
			btnstate2[i].setBackground(Color.RED);
			frame.getContentPane().add(btnstate2[i]);
			btnstate2[i].addItemListener(new ItemListener() {

				@Override
				public void itemStateChanged(ItemEvent e) {

				}
			});
		}
	}

	private void initializeResetButton() {
		reset.setBounds((frame.getWidth() / 2 - 480), (frame.getHeight() / 2 + 300), 145, 40);
		frame.getContentPane().add(reset);
		reset.setText("RESET");

		reset.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				CommPortSender.send(new ProtocolImpl().getMessage("RST?\r\n"));
			}
		});
	}

	private void initializeSaveEpromButton() {
		saveEprom.setBounds((frame.getWidth() / 2 - 320), (frame.getHeight() / 2 + 300), 145, 40);
		frame.getContentPane().add(saveEprom);
		saveEprom.setText("SAVE_EPROM");

		saveEprom.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				CommPortSender.send(new ProtocolImpl().getMessage("SAVEM?\r\n"));
			}
		});
	}

	private void initializeReadEpromButton() {
		readEprom.setBounds((frame.getWidth() / 2 - 480), (frame.getHeight() / 2 + 350), 145, 40);
		frame.getContentPane().add(readEprom);
		readEprom.setText("READ_EPROM");

		readEprom.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				CommPortSender.send(new ProtocolImpl().getMessage("READM?\r\n"));
			}
		});
	}

	private void initializeResetClearEprom() {
		resetClearEprom.setBounds((frame.getWidth() / 2 - 320), (frame.getHeight() / 2 + 350), 145, 40);
		frame.getContentPane().add(resetClearEprom);
		resetClearEprom.setText("RST+CLEAR");

		resetClearEprom.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				CommPortSender.send(new ProtocolImpl().getMessage("RST+MEM?\r\n"));
			}
		});
	}

	private void initializeMeas() {
		meas.setBounds((frame.getWidth() / 2 - 400), (frame.getHeight() / 2 + 390), 145, 40);
		frame.getContentPane().add(meas);
		meas.setText("MEAS");
		meas.setEnabled(false);

		meas.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (state == 3) {
					if (pName != null || pNumber != null) {
						CommPortSender.send(new ProtocolImpl().getMessage("PIN=D" + pName + "," + pNumber + ",1\r\n"));
						CommPortSender.send(new ProtocolImpl().getMessage("PIN=D" + pName + "," + pNumber + ",0\r\n"));
						CommPortSender.send(
								new ProtocolImpl().getMessage("SENDBYTE=0xCC,DDR" + pName + "," + pNumber + "\r\n"));
						CommPortSender.send(
								new ProtocolImpl().getMessage("SENDBYTE=0x44,DDR" + pName + "," + pNumber + "\r\n"));
						CommPortSender.send(new ProtocolImpl().getMessage("WAIT=0,750\r\n"));
						CommPortSender.send(new ProtocolImpl().getMessage("PIN=D" + pName + "," + pNumber + ",1\r\n"));
						CommPortSender.send(new ProtocolImpl().getMessage("PIN=D" + pName + "," + pNumber + ",0\r\n"));
						CommPortSender.send(
								new ProtocolImpl().getMessage("SENDBYTE=0xCC,DDR" + pName + "," + pNumber + "\r\n"));
						CommPortSender.send(
								new ProtocolImpl().getMessage("SENDBYTE=0xBE,DDR" + pName + "," + pNumber + "\r\n"));
						CommPortSender
								.send(new ProtocolImpl().getMessage("READBYTE=DDR" + pName + "," + pNumber + "\r\n"));
						CommPortSender
								.send(new ProtocolImpl().getMessage("READBYTE=DDR" + pName + "," + pNumber + "\r\n"));

					} else {
						JOptionPane.showMessageDialog(frame, "Choose port!", "Error", JOptionPane.ERROR_MESSAGE);
					}
				} else {
					JOptionPane.showMessageDialog(frame, "Choose from menu Measurment option!", "Error",
							JOptionPane.ERROR_MESSAGE);
				}

			}
		});
	}

	private void initializePinSwitch() {
		pinSwitch.setBounds((frame.getWidth() / 2 - 80), (frame.getHeight() / 2 - 450), 145, 40);
		frame.getContentPane().add(pinSwitch);
		pinSwitch.setText("SWITCH");
		pinSwitch.setEnabled(false);

		pinSwitch.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				if (state == 2) {
					if (getNumber() == 0) {
						portsDirs.setCharAt(6, '0');
					} else if (getNumber() == 1) {
						portsDirs.setCharAt(6, '1');

					} else if (getNumber() == 2) {
						portsDirs.setCharAt(6, '2');

					}
					if (time != null) {
						portsDirs.setCharAt(35, time.charAt(0));
						portsDirs.setCharAt(36, time.charAt(1));
					}

					else {

					}
					if (e.getStateChange() == ItemEvent.SELECTED) {
						if (buforek != null && buforek[0] != 0) {

							for (int i = 0; i < buforek.length; i++) {
								try {
									if (buforek[i] != 0) {
										portsDirs.setCharAt(buforek[i], '1');

									}
								} catch (NullPointerException exc) {

								} catch (NumberFormatException exc) {

								}
							}
						}

						try {
							CommPortSender.send(new ProtocolImpl().getMessage(portsDirs.toString()));
						} catch (NullPointerException exc) {
							// JOptionPane.showMessageDialog(frame, "Type an option at text area!", "Error",
							// JOptionPane.ERROR_MESSAGE);

						} catch (NumberFormatException exc) {

						}
						Arrays.fill(buforek, 0);

					} else if (e.getStateChange() == ItemEvent.DESELECTED) {

						change1to0(portsDirs.toString());

						try {
							CommPortSender.send(new ProtocolImpl().getMessage(portsDirs.toString()));
						} catch (NullPointerException exc) {
							// JOptionPane.showMessageDialog(frame, "Type an option at text area!", "Error",
							// JOptionPane.ERROR_MESSAGE);

						} catch (NumberFormatException exc) {

						}

					}

				} else {
					JOptionPane.showMessageDialog(frame, "Choose from menu ALLPorts option!", "Error",
							JOptionPane.ERROR_MESSAGE);
				}

			}

		});
	}

	private void initializeDirectionButton() {
		directionButton.setBounds((frame.getWidth() / 2 - 470), (frame.getHeight() / 2 - 250), 145, 40);
		frame.getContentPane().add(directionButton);
		directionButton.setText("Control Direction");
		directionButton.setEnabled(false);

		directionButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (state == 1) {

					option = "D";
				} else if (state == 2) {
					number = 1;
				} else {
					JOptionPane.showMessageDialog(frame, "Choose from menu proper option!", "Error",
							JOptionPane.ERROR_MESSAGE);
				}

			}
		});
	}

	private void initializeStateButton() {

		stateButton.setBounds((frame.getWidth() / 2 - 320), (frame.getHeight() / 2 - 250), 145, 40);
		frame.getContentPane().add(stateButton);
		stateButton.setText("Control State");
		stateButton.setEnabled(false);

		stateButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (state == 1) {

					option = "P";
				} else if (state == 2) {
					number = 0;
				} else {
					JOptionPane.showMessageDialog(frame, "Choose from menu proper option!", "Error",
							JOptionPane.ERROR_MESSAGE);
				}

			}
		});

	}

	private void initializeAutoButton() {
		autoButton.setBounds((frame.getWidth() / 2 - 320), (frame.getHeight() / 2 - 200), 145, 40);
		// frame.getContentPane().add(autoButton);
		// autoButton.setText("Automatic");

		autoButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (state == 1) {

					option = "P";
				} else if (state == 2) {
					number = 2;
				} else {
					JOptionPane.showMessageDialog(frame, "Choose from menu proper option!", "Error",
							JOptionPane.ERROR_MESSAGE);
				}

			}
		});

	}

	public String extractDigits(String src) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < src.length(); i++) {
			char c = src.charAt(i);
			if (Character.isDigit(c)) {
				builder.append(c);
			}
		}
		return builder.toString();
	}

	public void change1to0(String src) {

		int j = 0;
		for (int i = 0; i < src.length(); i++) {
			char c = src.charAt(i);
			if (Character.isDigit(c)) {
				if (Character.toString(c).equals("1")) {
					if (i != 6) {
						buforek[j] = i;
						portsDirs.setCharAt(i, '0');
						j++;
					}
				}

			}
		}

	}

	public void setT2Text(String text) {
		errorlabel.setText(text); ///Mateusz
		System.out.println("Odczytana temperatura: " + text);

	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getOption() {
		return option;
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Window window = new Window();
					window.frame.setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
