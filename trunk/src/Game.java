import java.util.ArrayList;

public class Game {
	
	private Board board;
	private Piece.Color turn;
	private Frame frame;
	private Move move;
	private AI ai;
	
	public Game(Frame f,int players)
	{
		board = new Board();
		turn = Piece.Color.white;
		this.frame = f;
		this.move = null;
		
		if(players == 1)
			this.ai = new AI(AI.Strategy.Necrophobic,1);
		else
			this.ai = null;
	}
	
	public boolean click(int r, int c)
	{		
		if( move == null)
		{
			move = new Move();
			move.setSrcR(r);
			move.setSrcC(c);
			
			if(validateSrc(move,board))
			{
				frame.setHighlight(move);
				return true;
			}
			else
			{
				frame.clearHighlight(move);
				move = null;
				return false;
			}
		}
		else
		{
			move.setDestR(r);
			move.setDestC(c);

			
			if(validateDest(move,board))
			{
				board.makeMove(move);
				frame.updateIcons(move);
				frame.clearHighlight(move);
				this.toggleTurn();
				frame.toggleTurn();
				move = null;
				return true;
			}
			else
			{
				frame.clearHighlight(move);
				move =null;
				return false;
			}
		}
		
	}
	
	public void AIMove()
	{
		if(ai != null)
		{
			ai.makeMove(this,board);
		}
	}
	
	public boolean validateSrc(Move m,Board b)
	{
		Piece p;
		int srcR,srcC;
		
		srcR = m.getSrcR();
		srcC = m.getSrcC();
		
		if ( (p = b.getPiece(srcR, srcC)) == null)
		{
			frame.addMessage("No one home! Try clicking an occupied piece!");
			return false;
		}
		
		if (p.getColor() != turn)
		{
			frame.addMessage("Bad color! Try clicking your own color!");
			return false;
		}
		
		return true;
	}
	
	public boolean validateDest(Move m,Board b)
	{
		Piece p;
		int srcR,srcC,destR,destC;
		
		srcR = m.getSrcR();
		srcC = m.getSrcC();
		destR = m.getDestR();
		destC = m.getDestC();
		
		//Is dest occupied?
		if ( (p = b.getPiece(destR, destC)) != null)
		{
			//Is trying to capture own piece?
			if ( p.getColor() == turn )
			{
				frame.addMessage("No cannibalizm! Try capturing your enemy's pieces!");
				return false;
			}
		}
		
		//Get src Piece
		p = b.getPiece(srcR,srcC);
		
		//Is legal move?
		if( !(p.legalMove(destR,destC, b)))
		{
			frame.addMessage("This is chess! Try a legal move!");
			return false;
		}
		
		//If in check, does this move take me out of check?
		if(this.inCheck(b))
		{
			if(this.inCheck(b, m))
			{
				frame.addMessage("Still in Check! This move doesn't save the KING!");
				return false;
			}

		}
		//If not in check, Does this move put ME in check?
		else
		{
			if(this.inCheck(b, m))
			{
				frame.addMessage("Don't put your King in Danger! This move will put YOU in check!");
				return false;
			}
		}
		
		
		
		if (this.isCheckmate(b,m))
		{
			//Do these to show the finishing move
			board.makeMove(m);

			//Disable all buttons
			frame.disableButtons();
			
			frame.updateIcons(m);
			frame.clearAllHighlights();

			
			//Show dialog box
			frame.showDialog("        " + turn.name().toUpperCase() + " WINS!", "WINNER!");	
			
				
			//Add Message
			frame.addMessage(turn.name().toUpperCase() + " WINS!");
			return false;
		}
		
		if(this.isStalemate(b,m))
		{
			//Do these to show the finishing move
			board.makeMove(m);
		
			//Disable all buttons
			frame.disableButtons();
			
			frame.updateIcons(m);
			frame.clearAllHighlights();
			
			//Show dialog box 
			frame.showDialog("            "  + "Draw", "Draw");	
				
			//Add Message
			frame.addMessage("The game is a draw!");
			return false;
		}
		
		return true;
	}
	
	public boolean inCheck(Board b)
	{
		King king;
		
		king = (King) b.getKing(turn);
		
 		if ( king.inCheck(b) == true)
 			return true;
 		else
 			return false;
	}
	
	public boolean inCheck(Board b, Move m)
	{
		King king;
		Board B;
		
 		B = new Board(b);
 				
		B.makeMove(m);
		
		king = (King) B.getKing(turn);
		
 		if ( king.inCheck(B) == true)
 			return true;
 		else
 			return false;
	}
	
	private boolean isCheckmate(Board b,Move m )
	{
		King enemyKing;
		Piece.Color enemyColor;
		Board B;
		
		B = new Board(b);
		
		B.makeMove(m);
		
		if(turn == Piece.Color.white)
			enemyColor = Piece.Color.black;
		else
			enemyColor = Piece.Color.white;
		
		enemyKing = (King) B.getKing(enemyColor);
		
		if(enemyKing.inCheckMate(B))
		{
			return true;
		}
		
		return false;
	}
	
	private boolean isStalemate(Board b,Move m)
	{
		ArrayList<Piece> list1;
		ArrayList<Piece> list2;
		Board B;
		
		B = new Board(b);
		
		B.makeMove(m);
		
		list1 = B.getPieceList(Piece.Color.black);
		list2 = B.getPieceList(Piece.Color.white);
		
		if(list1.size() == 1 && list2.size() == 1)
			return true;
		else
			return false;
	}
	
	private void toggleTurn()
	{
		if(turn == Piece.Color.white)
			turn = Piece.Color.black;
		else
			turn = Piece.Color.white;
	}
	
	
	
}
