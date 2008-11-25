import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

public class Frame extends JFrame 
{

	private static final long serialVersionUID = 1L;
	private static final int WIDTH=725;
	private static final int HEIGHT=720;
	private static final String newline = "\n";
	
	//Menu 
	private JMenuBar menuBar;
	private JMenu fileMenu;
	private JMenuItem newMenuItem,exitMenuItem;
	private JCheckBoxMenuItem verboseMenuItem;
	
	//Hold Player Info/Check Info
	private JPanel topPanel;
	
	//Hold Tiles
	private JPanel tilePanel;
	
	//Hold log info.
	private JPanel verbosePanel;
	
	//Verbose TestBox
	private JTextArea verboseBox; 
	
	//Scroll Box
	private JScrollPane verboseScroll;
	  
	//Tile Buttons
	private JButton [][]tilebutton;
	
	//Icon
	private Icon tileIcon[];
	
	//Radio/Group For Players
	private JRadioButton whitePlayer;
	private JRadioButton blackPlayer;
	private ButtonGroup groupPlayer;
	
	//Use to Decide Tile Color
	private int colorFlag;
	
	//Board Object
	private Board b;
	
	private Piece srcPiece;
	private Piece destPiece;
	
	private Piece.Color turn;
	
	public Frame()
	{
		//Set the FrameUp,
		super();
		setSize(WIDTH,HEIGHT);
		
		//Default Close Method
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Deny Resize Frame, Set BorderLayout
		this.setResizable(false);
		this.setLayout(new BorderLayout());
		
		//Create Menu Bar
		menuBar = new JMenuBar();
		
		//Create First DropDown Menu
		fileMenu = new JMenu("File");
		
			//Add the New Game Item and Listener
			newMenuItem  = new JMenuItem("New");
			newMenuItem.addActionListener(new NewGameButtonListener());
			
			//Add the Verbose Option and Listener
			verboseMenuItem = new JCheckBoxMenuItem("Verbose", false);
			verboseMenuItem.addActionListener(new VerboseButtonListener());
			   
			//Add the Exit Item And Listener
			exitMenuItem = new JMenuItem("Exit");
			exitMenuItem.addActionListener(new ExitGameButtonListener());

			//Add all Menu Items to Menu
			fileMenu.add(newMenuItem);
			fileMenu.add(verboseMenuItem);
			fileMenu.add(exitMenuItem);
			 
			//Add Menu To Menu Bar
			menuBar.add(fileMenu);
			
			//Add to Frame
			this.setJMenuBar(menuBar);

		//Create TopPanel For Player/Check Info
		topPanel = new JPanel();
		topPanel.setLayout(new FlowLayout());
		
			//Create the Player Radio/Group
			groupPlayer = new ButtonGroup();
			whitePlayer = new JRadioButton("White");
			blackPlayer = new JRadioButton("Black");
			
			///Player 1 turn
			whitePlayer.setSelected(true);
		
			//Disable Selecting
			whitePlayer.setEnabled(false);
			blackPlayer.setEnabled(false);
			
			//Add Players to Group
			groupPlayer.add(whitePlayer);
			groupPlayer.add(blackPlayer);
		
			//Add Radio Buttons to North Panel
			topPanel.add(whitePlayer);
			topPanel.add(blackPlayer);
		
		//Create the Verbose Panel for Logs
		verbosePanel = new JPanel();
		verbosePanel.setLayout(new FlowLayout());
		
			//Create the verbose box
			verboseBox = new JTextArea("[VERBOSE]"+newline,5,63);
		
			//Add Black border
			verboseBox.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.black));
		
			//Don't let user enter text
			verboseBox.setEditable(false);
			
			//Create scroll area from the textarea
			verboseScroll = new JScrollPane(verboseBox);
		
			//Never Show the Horizontal scrollbar
			verboseScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
			//Add Scrollbox to verbose panel
			verbosePanel.add(verboseScroll);
		
			//Hide scrollbox at startup
			verbosePanel.setVisible(false);
		
	
		//Panel For Chess Title w/Grid Layout
		tilePanel =new JPanel();
		tilePanel.setLayout(new GridLayout(8,8,3,3));
			
			//Add Black Border to Tiles
			tilePanel.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.black));
		
			//Create the Title Buttons	
			tilebutton = new JButton[8][8];
		
			//Set Color Tile
			colorFlag=1;
		
			//Init the Buttons
			for (int i=0;i<8;i++)
			{
				for(int j=0;j<8;j++)
				{
					//Figure out the Color Begin of Row
					if (j==0)
					{
						if (colorFlag ==0)
						{
							colorFlag = 1;
						}
						else
						{
							colorFlag = 0;
						}
					}
				
					//Create Button
					tilebutton[i][j] = new JButton();
					//tilebutton[i][j].setName("i"+"j");
					tilebutton[i][j].setActionCommand(""+i+j);
		
					//Alternate Color in Rows
					if (colorFlag == 0)
					{
						tilebutton[i][j].setBackground(Color.white);
						colorFlag = 1;
					}
					else
					{
						tilebutton[i][j].setBackground(Color.gray);
						colorFlag = 0;
					}

					//Setup Listener, and Add to Panel
					tilebutton[i][j].addActionListener(new TileButtonListener());
					tilePanel.add(tilebutton[i][j]);
				}
			}

		
		//Add the PlayerInfo To North Panel
		this.add(topPanel,BorderLayout.NORTH);
		
		//Add ChessBoard to Center Panel
		this.add(tilePanel,BorderLayout.CENTER);
		
		//Add Verbose Panel to South
		this.add(verbosePanel,BorderLayout.SOUTH);
		
		//FillIconArray
		tileIcon = new ImageIcon[12];

		tileIcon[0] = new ImageIcon("src/pic/white/bishop.png");
		tileIcon[1] = new ImageIcon("src/pic/white/king.png");
		tileIcon[2] = new ImageIcon("src/pic/white/knight.png");
		tileIcon[3] = new ImageIcon("src/pic/white/pawn.png");
		tileIcon[4] = new ImageIcon("src/pic/white/queen.png");
		tileIcon[5] = new ImageIcon("src/pic/white/rook.png");
		
		tileIcon[6] = new ImageIcon("src/pic/black/bishop.png");
		tileIcon[7] = new ImageIcon("src/pic/black/king.png");
		tileIcon[8] = new ImageIcon("src/pic/black/knight.png");
		tileIcon[9] = new ImageIcon("src/pic/black/pawn.png");
		tileIcon[10] = new ImageIcon("src/pic/black/queen.png");
		tileIcon[11] = new ImageIcon("src/pic/black/rook.png");
				
		//SetIcons
		this.setInitIcon();
		
		this.srcPiece = null;
		this.destPiece = null;
		
		turn = Piece.Color.white;
		
		//Set the Game Up
		b = new Board();
		
	}
	
//Listeners
	
	//When each button is pressed
	private class TileButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{			
			int r,c;
			
			String s = e.getActionCommand();
			r = Integer.parseInt(s.substring(0, 1));
			c = Integer.parseInt(s.substring(1));
			
			if(srcPiece ==null)
				srcSelected(r,c);
			else
				destSelected(r,c);	
		}	
	}
	
	//When new game is pressed
	private class NewGameButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			clearAllHighlights();
			b = new Board();
 
			setInitIcon();
			
			enableButtons();
			
			srcPiece = null;
			destPiece = null;
			
			turn = Piece.Color.white;
			whitePlayer.setSelected(true);

			verboseBox.setText("");
			addMessage("[VERBOSE]");
		}
	}
	
	//When exit is clicked
	private class ExitGameButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			System.exit(0);
		}
	}
	
	//Show the text box
	private class VerboseButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if (verboseMenuItem.isSelected())
				verbosePanel.setVisible(true);
			else
				verbosePanel.setVisible(false);
		}
	}
	
	
//Methods
	
	private void srcSelected(int srcR, int srcC)
	{
		srcPiece = b.getPiece(srcR, srcC);
		
		if(validateSrc())
		{
			this.setHighlight(srcR, srcC);
		}
		else
		{
			this.clearHighlight(srcR,srcC);
			this.resetMove();
		}
	}
	
	private void destSelected(int destR, int destC)
	{
		destPiece = b.getPiece(destR, destC);
		
		int srcR = srcPiece.getSrcR();
		int srcC = srcPiece.getSrcC();
		
		if(validateDest(destR,destC))
		{
			b.makeMove(srcR, srcC, destR, destC);
			this.updateIcons(srcR,srcC,destR,destC);
			this.clearHighlight(srcR,srcC);
			
			if (this.isCheckmate())
			{
				//Show dialog box
				Container c = getContentPane();
				JOptionPane.showMessageDialog(c, turn.name().toUpperCase() + " WINS!", "WINNER!", 1);
					
				//Disable all buttons
				this.disableButtons();
					
				//Add Message
				this.addMessage(turn.name().toUpperCase() + " WINS!");
				return;
			}
			
			if(this.isStalemate())
			{
				//Show dialog box
				Container c = getContentPane();
				JOptionPane.showMessageDialog(c, "Draw", "Draw", 1);
					
				//Disable all buttons
				this.disableButtons();
					
				//Add Message
				this.addMessage("The game is a draw!");
				return;
			}
			
			this.resetMove();
			this.toggleTurn();
			AI ai = new AI(AI.Strategy.Bastille,1);
			ai.makeMove(b);
		}
		else
		{
			this.clearHighlight(srcR,srcC);
			this.resetMove();
		}
		
	}
	
	public void showDialog(String message, String title)
	{
		Container c = getContentPane();
		JOptionPane.showMessageDialog(c, message, title, 1);
	}
	

	private boolean validateSrc()
	{
		if ( srcPiece == null)
		{
			this.addMessage("No one home! Try clicking an occupied piece!");
			return false;
		}
		
		if (srcPiece.getColor() != turn)
		{
			this.addMessage("Bad color! Try clicking your own color!");
			return false;
		}
		
		return true;
	}
	
	private boolean validateDest(int destR, int destC)
	{
		Board testBoard;
		King realKing;
		King testKing;
		boolean checkStatusReal,checkStatusTest;
	
		//Is trying to capture own piece?
		if ( (destPiece != null) && (destPiece.getColor() == turn) )
		{
			this.addMessage("No cannibalizm! Try capturing your enemy's pieces!");
			return false;
		}

		//Is legal move?
		if( !(srcPiece.legalMove(destR,destC, b)))
		{
			this.addMessage("This is chess! Try a legal move!");
			return false;
		}
		
		//Create a test board 
 		testBoard = new Board(b);
 		
 		//Make move on this board
 		testBoard.makeMove(srcPiece.getSrcR(), srcPiece.getSrcC(), destR, destC);
 		
 		//Get the kings
 		testKing = (King) testBoard.getKing(turn);
 		realKing = (King) b.getKing(turn);
 		
 		//Get check status
 		checkStatusReal = realKing.inCheck(b);
 		checkStatusTest = testKing.inCheck(testBoard);
 		
 		
		//If in check, does this move take me out of check?
		if(checkStatusReal)
		{
			if(checkStatusTest)
			{
				this.addMessage("Still in Check! This move doesn't save the KING!");
				return false;
			}	
		}
		
		//If not in check, Does this move put ME in check?
		if( !(checkStatusReal))
		{
			if(checkStatusTest)
			{
				this.addMessage("Don't put your King in Danger! This move will put YOU in check!");
				return false;
			}	
		}	
		
		return true;
		
	}
	
	//Is the enemy is in check mate
	public boolean isCheckmate()
	{
		King myKing;
		King enemyKing;
		Piece.Color enemyColor;
		
		if(turn == Piece.Color.white)
			enemyColor = Piece.Color.black;
		else
			enemyColor = Piece.Color.white;
		
		enemyKing = (King) b.getKing(enemyColor);
		
		if(enemyKing.inCheckMate(b))
		{
			return true;
		}
		
		return false;
	}
	
	public boolean isStalemate()
	{
		ArrayList<Piece> list1;
		ArrayList<Piece> list2;
		
		list1 = b.getPieceList(Piece.Color.black);
		list2 = b.getPieceList(Piece.Color.white);
		
		if(list1.size() == 1 && list2.size() == 1)
			return true;
		else
			return false;
	}
	
	public void addMessage(String s)
	{
		verboseBox.append(s + newline);
		
		//Move ScrollBox with Added Text
		verboseBox.setCaretPosition(verboseBox.getDocument().getLength());
	}
	
	public void setHighlight(int srcR, int srcC)
	{
			tilebutton[srcR][srcC].setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.blue));
	}
	
	public void clearHighlight(int srcR, int srcC)
	{
			tilebutton[srcR][srcC].setBorder(null);	
	}
	
	public void clearAllHighlights()
	{
		for (int i=0;i<8;i++)
		{
			for(int j=0;j<8;j++)
			{
				tilebutton[i][j].setBorder(null);	
			}
		}
	}
	
	
	private void resetMove()
	{
		srcPiece = null;
		destPiece = null;
	}
	
	public void toggleTurn()
	{
		if(whitePlayer.isSelected())
		{
			blackPlayer.setSelected(true);
			turn = Piece.Color.black;
		}
		else
		{
			whitePlayer.setSelected(true);
			turn = Piece.Color.white;
		}
	}
	
	public void updateIcons(int srcR, int srcC, int destR, int destC)
	{	

			//Set desintation icon to source icon
			tilebutton[destR][destC].setIcon(tilebutton[srcR][srcC].getIcon());
		
			//Set source icon to nothing
			tilebutton[srcR][srcC].setIcon(null);
	}
	
	public void disableButtons()
	{
		for (int i=0;i<8;i++)
		{
			for(int j=0;j<8;j++)
			{
				tilebutton[i][j].setEnabled(false);
			}
		}
	}
	
	public void enableButtons()
	{
		for (int i=0;i<8;i++)
		{
			for(int j=0;j<8;j++)
			{
				tilebutton[i][j].setEnabled(true);
			}
		}
	}

	
	//Set icons to starting positions
	private void setInitIcon()
	{

		tileIcon[0] = new ImageIcon("src/pic/white/bishop.png");
		tileIcon[1] = new ImageIcon("src/pic/white/king.png");
		tileIcon[2] = new ImageIcon("src/pic/white/knight.png");
		tileIcon[3] = new ImageIcon("src/pic/white/pawn.png");
		tileIcon[4] = new ImageIcon("src/pic/white/queen.png");
		tileIcon[5] = new ImageIcon("src/pic/white/rook.png");
		
		tileIcon[6] = new ImageIcon("src/pic/black/bishop.png");
		tileIcon[7] = new ImageIcon("src/pic/black/king.png");
		tileIcon[8] = new ImageIcon("src/pic/black/knight.png");
		tileIcon[9] = new ImageIcon("src/pic/black/pawn.png");
		tileIcon[10] = new ImageIcon("src/pic/black/queen.png");
		tileIcon[11] = new ImageIcon("src/pic/black/rook.png");
		
		//Rook
		tilebutton[0][0].setIcon(tileIcon[11]);
		tilebutton[0][7].setIcon(tileIcon[11]);
		tilebutton[7][0].setIcon(tileIcon[5]);
		tilebutton[7][7].setIcon(tileIcon[5]);
		
		//Knight
		tilebutton[0][1].setIcon(tileIcon[8]);
		tilebutton[0][6].setIcon(tileIcon[8]);
		tilebutton[7][1].setIcon(tileIcon[2]);
		tilebutton[7][6].setIcon(tileIcon[2]);
		
		//Bishop
		tilebutton[0][2].setIcon(tileIcon[6]);
		tilebutton[0][5].setIcon(tileIcon[6]);
		tilebutton[7][2].setIcon(tileIcon[0]);
		tilebutton[7][5].setIcon(tileIcon[0]);
		
		//Queen
		tilebutton[0][3].setIcon(tileIcon[10]);
		tilebutton[7][3].setIcon(tileIcon[4]);
		
		//King
		tilebutton[0][4].setIcon(tileIcon[7]);
		tilebutton[7][4].setIcon(tileIcon[1]);
	
		//Pawn & blanks.
		for(int i=0;i<8;i++)
		{
			tilebutton[6][i].setIcon(tileIcon[3]);
			tilebutton[1][i].setIcon(tileIcon[9]);
			tilebutton[2][i].setIcon(null);
			tilebutton[3][i].setIcon(null);
			tilebutton[4][i].setIcon(null);
			tilebutton[5][i].setIcon(null);
		}
		
	}
	
} 