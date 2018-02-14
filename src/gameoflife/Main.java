package gameoflife;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
public class Main extends JFrame{
	public static void main(String[] args) {
		new Main();
	}
	private static final long serialVersionUID = 1L;
	public Paint canvas;
	public Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	public float width = screenSize.width;
	public float height = screenSize.height;
	public int size = 100;
	public ArrayList<ArrayList<Boolean>> list = new ArrayList<ArrayList<Boolean>>();
	public ArrayList<ArrayList<Boolean>> temp_list = new ArrayList<ArrayList<Boolean>>();
	public double percentRnd = 0.15;
	public Main() {
		canvas = new Paint();
		Container c = getContentPane();
		c.add(canvas);
		for (int i = 0; i < width/size; i++) {
			list.add(new ArrayList<Boolean>());
			for (int j = 0; j < height/size; j++) {
				list.get(i).add(false);	
			}
		}
		for (int i = 0; i < width/size; i++) {
			temp_list.add(new ArrayList<Boolean>());
			for (int j = 0; j < height/size; j++) {
				temp_list.get(i).add(false);	
			}
		}
		addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent ke) {
				if (ke.getKeyCode() == KeyEvent.VK_ESCAPE) {
					dispose();
				}
			}
		});
		addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent ke) {
				if (ke.getKeyCode() == KeyEvent.VK_R) {
					//create random field
					double rndDec;
					for (int i = 0; i < list.size(); i++) {
						for (int j = 0; j < list.get(i).size(); j++) {
							rndDec = Math.random();
							if (rndDec < percentRnd) {
								list.get(i).set(j, true);
							}
						}
					}
					canvas.repaint();
				}
			}
		});
		addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent ke) {
				if (ke.getKeyCode() == KeyEvent.VK_C) {
					//clear field
					for (int i = 0; i < list.size(); i++) {
						for (int j = 0; j < list.get(i).size(); j++) {
							list.get(i).set(j, false);
						}
					}	
					canvas.repaint();
				}
			}
		});

		addKeyListener(new KeyAdapter() {
			@SuppressWarnings("unchecked")
			public void keyPressed(KeyEvent ke) {
				if (ke.getKeyCode() == KeyEvent.VK_SPACE) {
					int neighbours = 0;
					int x = 0;
					int y = 0;		
					for (int i = 0; i < list.size(); i++) {
						temp_list.set(i, (ArrayList<Boolean>) list.get(i).clone());
					}
					for (int i = 0; i < list.size(); i++) {
						for (int j = 0; j < list.get(i).size(); j++) {
							neighbours = 0;
							for (int a = -1; a < 2; a++) {
								for (int b = -1; b < 2; b++) {
									x = i+a;
									y = j+b;	
									if (x < 0 || x >= list.size() || y < 0 || y >= list.get(i).size()) {
										
									}
									else {
										if (list.get(x).get(y)) {
											
											neighbours++;
										}
									}
								}
							}
							if (list.get(i).get(j)) {
								neighbours--;
							}
							if (list.get(i).get(j) && neighbours < 2) {
								//if alive and less than 2 neighbours, die
								temp_list.get(i).set(j, false);
								
							}
							else if (list.get(i).get(j) && neighbours > 3) {
								//if alive and greater than 3 neighbours, die
								temp_list.get(i).set(j, false);
								
							}
							else if (list.get(i).get(j) == false && neighbours == 3) {
								//if dead and exactly 3 neighbours, live
								temp_list.get(i).set(j, true);
							}
						}
					}
					for (int i = 0; i < list.size(); i++) {
						list.set(i, (ArrayList<Boolean>) temp_list.get(i).clone());
					}
					canvas.repaint();	
				}
			}
		});
		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent me) {
				if (me.getButton() == MouseEvent.BUTTON1) {
					int x = me.getX();
					int y = me.getY();
					int cellX = Math.floorDiv(x + size, size);
					int cellY = Math.floorDiv(y, size);
					if (list.get(cellX - 1).get(cellY)) {
						list.get(cellX - 1).set(cellY, false);
					}
					else {
						list.get(cellX - 1).set(cellY, true);
					}
					canvas.repaint();
				}
				else if (me.getButton() == MouseEvent.BUTTON3) {
					JPanel panel = new JPanel(new GridLayout(3,0));
					panel.add(new JLabel("Size: "));
					JTextField new_size = new JTextField(size);
					panel.add(new_size);
					
					if(JOptionPane.showConfirmDialog(c,panel,"Set new size",JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE) == JOptionPane.OK_OPTION) {
						String text = new_size.getText();
						try {
							size = Integer.parseInt(text);
							list.clear();
							for (int i = 0; i < width/size; i++) {
								list.add(new ArrayList<Boolean>());
								for (int j = 0; j < height/size; j++) {
									list.get(i).add(false);	
								}
							}
							for (int i = 0; i < width/size; i++) {
								temp_list.add(new ArrayList<Boolean>());
								for (int j = 0; j < height/size; j++) {
									temp_list.get(i).add(false);	
								}
							}
						}catch(NumberFormatException e){
							System.out.println(e);
							JOptionPane.showMessageDialog(new JLabel(e.toString()), "Invalid input.");
						}
					}
				}
			}
		});
		
		setExtendedState(JFrame.MAXIMIZED_BOTH); 
		setUndecorated(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE); 
		setVisible(true);
	}
	
	
	
	public class Paint extends JPanel{
		private static final long serialVersionUID = 1L;

		public void paintComponent(Graphics g) {
			g.clearRect(0, 0,(int) width,(int) height);
			for (int i = 0; i < list.size(); i++) {
				for (int j = 0; j < list.get(i).size(); j++) {
					if (list.get(i).get(j)) {
						g.fillRect(i*size,j*size,size,size);
					}
				}
			}
			
		}
		
	}
	
}
