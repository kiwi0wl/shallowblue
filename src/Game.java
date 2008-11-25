import java.util.ArrayList;

public class Game {
	
	private Board board;
	private Piece.Color turn;
	private Frame frame;
	private Move move;
	
	public Game(Frame f)
	{
		board = new Board();
		turn = Piece.Color.white;
		this.frame = f;
		this.move = null;
	}
	
	public void click(int r, int c)
	{
		int srcR,srcC,destR,destC;
		
		if( move == null)
		{
			move = new Move();
			move.setSrcR(r);
			move.setSrcC(c);
			srcR = r;
			srcC = c;
			
			if(validateSrc(srcR,srcC))
			{
				frame.setHighlight(srcR, srcC);
			}
			else
			{
				frame.clearHighlight(srcR,srcC);
				move = null;
			}
		}
		else
		{
			move.setDestR(r);
			move.setDestC(c);
			destR = r;
			destC = c;
			srcR = move.getSrcR();
			srcC = move.getSrcC();
			
			if(validateDest(destR,destC))
			{
				board.makeMove(move);
				frame.updateIcons(srcR,srcC,destR,destC);
				frame.clearHighlight(srcR,srcC);
				this.toggleTurn();
				frame.toggleTurn();
				move = null;
			}
			else
			{
				frame.clearHighlight(srcR,srcC);
				move =null;
			}
		}
	}
	private boolean validateSrc(int srcR,int srcC)
	{
		Piece p;
		
		if ( (p = board.getPiece(srcR, srcC)) == null)
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
	
	private boolean validateDest(int destR,int destC)
	{
		Piece p;
		int srcR,srcC;
		
		srcR = move.getSrcR();
		srcC = move.getSrcC();
		
		//Is dest occupied?
		if ( (p = board.getPiece(destR, destC)) != null)
		{
			//Is trying to capture own piece?
			if ( p.getColor() == turn )
			{
				frame.addMessage("No cannibalizm! Try capturing your enemy's pieces!");
				return false;
			}
		}
		
		//Get src Piece
		p = board.getPiece(srcR,srcC);
		
		//Is legal move?
		if( !(p.legalMove(destR,destC, board)))
		{
			frame.addMessage("This is chess! Try a legal move!");
			return false;
		}
		
		//If in check, does this move take me out of check?
		if(this.inCheck(board))
		{
			if(this.inCheck(board, move))
			{
				frame.addMessage("Still in Check! This move doesn't save the KING!");
				return false;
			}

		}
		//If not in check, Does this move put ME in check?
		else
		{
			if(this.inCheck(board, move))
			{
				frame.addMessage("Don't put your King in Danger! This move will put YOU in check!");
				return false;
			}
		}
		
		
		
		if (this.isCheckmate(board,move))
		{
			//Show dialog box
			frame.showDialog(turn.name().toUpperCase() + " WINS!", "WINNER!");	
			
			//Disable all buttons
			frame.disableButtons();
				
			//Add Message
			frame.addMessage(turn.name().toUpperCase() + " WINS!");
			return true;
		}
		
		if(this.isStalemate(board,move))
		{
			//Show dialog box
			frame.showDialog("Draw", "Draw");	
				
			//Disable all buttons
			frame.disableButtons();
				
			//Add Message
			frame.addMessage("The game is a draw!");
			return true;
		}
		
		return true;
	}
	
	private boolean inCheck(Board b)
	{
		King king;
		
		king = (King) b.getKing(turn);
		
 		if ( king.inCheck(b) == true)
 			return true;
 		else
 			return false;
	}
	
	private boolean inCheck(Board b, Move m)
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
