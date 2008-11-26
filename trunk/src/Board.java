 import java.util.ArrayList;
import java.util.Iterator;
	
public class Board
{
	private Piece chessBoard[][];

	
	public Board()
	{	
		//Create Board With Pieces On It
		chessBoard = new Piece[8][8];
		
		//Black
		chessBoard[0][0] = new Rook(Piece.Color.black,0,0);
		chessBoard[0][1] = new Knight(Piece.Color.black,0,1);
		chessBoard[0][2] = new Bishop(Piece.Color.black,0,2);
		chessBoard[0][3] = new Queen(Piece.Color.black,0,3);
		chessBoard[0][4] = new King(Piece.Color.black,0,4);
		chessBoard[0][5] = new Bishop(Piece.Color.black,0,5);
		chessBoard[0][6] = new Knight(Piece.Color.black,0,6);
		chessBoard[0][7] = new Rook(Piece.Color.black,0,7);
		
		//White
		chessBoard[7][0] = new Rook(Piece.Color.white,7,0);
		chessBoard[7][1] = new Knight(Piece.Color.white,7,1);
		chessBoard[7][2] = new Bishop(Piece.Color.white,7,2);
		chessBoard[7][3] = new Queen(Piece.Color.white,7,3);
		chessBoard[7][4] = new King(Piece.Color.white,7,4);
		chessBoard[7][5] = new Bishop(Piece.Color.white,7,5);
		chessBoard[7][6] = new Knight(Piece.Color.white,7,6);
		chessBoard[7][7] = new Rook(Piece.Color.white,7,7);

		//Set pawns up and blank spaces
		for (int i = 0; i < 8; i++)
		{	
			chessBoard[6][i] = new Pawn(Piece.Color.white,6,i);
			chessBoard[1][i] = new Pawn(Piece.Color.black,1,i);
			chessBoard[2][i] = null;
			chessBoard[3][i] = null;
			chessBoard[4][i] = null;
			chessBoard[5][i] = null;
		}
	}
	
	//Copy Constructor
	public Board(Board b)
	{
		this();
		
		Piece.Name name;
		Piece p;

		//Go through the board
		for(int i=0;i<8;i++)
		{
			for(int j=0;j<8;j++)
			{
				//Clear square because of constructor setup
				chessBoard[i][j] = null;
				
				//Get piece at position
				p = b.getPiece(i,j);
				
				//If nothing there, try next tile
				if( p == null)
					continue;
				
				//Get name of piece
				name = p.getName();

				//Create a copy of piece and put in copy chessBoard
				if(name == Piece.Name.king)
					chessBoard[i][j] = new King(p);
				else if (name == Piece.Name.bishop)
					chessBoard[i][j] = new Bishop(p);
				else if (name == Piece.Name.rook)
					chessBoard[i][j] = new Rook(p);
				else if (name == Piece.Name.knight)
					chessBoard[i][j] = new Knight(p);
				else if (name == Piece.Name.queen)
					chessBoard[i][j] = new Queen(p);
				else if (name == Piece.Name.pawn)
					chessBoard[i][j] = new Pawn(p);
			}
		}
	}
	
	//Return piece by position
	public Piece getPiece(int r, int c)
	{
		if (chessBoard[r][c] == null)
			return null;
		else
			return chessBoard[r][c];
	}
	
	//Return piece by name and color and side
	public King getKing(Piece.Color color)
	{
		ArrayList<Piece> pieceList;
		Iterator<Piece> itPiece;
		Piece p;
		
		pieceList = getPieceList(color);
		
		itPiece = pieceList.listIterator();
		while (itPiece.hasNext())
		{
			 p = itPiece.next();
			if(p.getName() == Piece.Name.king && p.getColor() == color)
			{
				return (King)p;
			}
		}
		
		return null;
	}
	
	//Move pieces on the board array
	public void makeMove(Move m)
	{
		int srcR,srcC,destR,destC;
		srcR = m.getSrcR();
		srcC = m.getSrcC();
		destR = m.getDestR();
		destC = m.getDestC();
		
		//Move piece on board array
		chessBoard[destR][destC] = chessBoard[srcR][srcC];
		
		//Update piece position
		chessBoard[destR][destC].setSrcC(destC);
		chessBoard[destR][destC].setSrcR(destR);
		
		//Set board source to empty
		chessBoard[srcR][srcC] = null;
	}
	
	//Return list of all pieces, or by color
	public ArrayList<Piece> getPieceList(Piece.Color color)
	{
		ArrayList<Piece> pieceList = new ArrayList<Piece>();
		
		for (int i=0;i<8;i++)
		{
			for(int j=0;j<8;j++)
			{
				//Skip Null Tiles
				if (chessBoard[i][j] == null)
					continue;
				else if ( color == Piece.Color.invalid )
				{
					pieceList.add(chessBoard[i][j]);
				}
				else if(chessBoard[i][j].getColor() == color)
				{
					pieceList.add(chessBoard[i][j]);
				}
			}
		}
		
		return pieceList;
	}
	
	//In check on board from this color
	public boolean inCheck(Piece.Color t)
	{
		King king;
		
		king = (King) this.getKing(t);
		
 		if ( king.inCheck(this) == true)
 			return true;
 		else
 			return false;
	}
	
	//In check on board after move from this color
	public boolean inCheck(Move m, Piece.Color t)
	{
		King king;
		Board B;
		
 		B = new Board(this);
 				
		B.makeMove(m);
		
		king = (King) B.getKing(t);
		
 		if ( king.inCheck(B) == true)
 			return true;
 		else
 			return false;
	}
	
	//Is checkmate after this move 
	public boolean isCheckmate(Move m)
	{
		King whiteKing,blackKing;
		Board B;
		
		B = new Board(this);
		
		B.makeMove(m);
		
		blackKing = (King) B.getKing(Piece.Color.black);
		whiteKing = (King) B.getKing(Piece.Color.white);
		
		if(whiteKing.inCheckMate(B) || blackKing.inCheckMate(B))
		{
			return true;
		}
		
		return false;
	}
	
	//Is stalemate after this move for this color
	public boolean isStalemate(Move m)
	{
		ArrayList<Move> whiteMoveList,blackMoveList;
		ArrayList<Piece> whitePieceList,blackPieceList;
		Iterator<Piece> itPiece;
		Board B;
		Piece p;

		//Initialize
		whiteMoveList = new ArrayList<Move>();
		blackMoveList = new ArrayList<Move>();

		//Copy board
		B = new Board(this);
		
		//Make move on copy board
		B.makeMove(m);
	
		
		//Get piece lists
		whitePieceList = B.getPieceList(Piece.Color.white);
		blackPieceList = B.getPieceList(Piece.Color.black);
		
		//Get all moves for white
		itPiece = whitePieceList.listIterator();
		while (itPiece.hasNext())
		{
			 p = itPiece.next();
			 
			 	 whiteMoveList.addAll( p.genMoves(B));
		}
		
		//Get all moves for black
		itPiece = blackPieceList.listIterator();
		while (itPiece.hasNext())
		{
			 p = itPiece.next();
			 
			 	 blackMoveList.addAll( p.genMoves(B));
		}
		
		//Check if only kings left
		if(whitePieceList.size() == 1 && blackPieceList.size() == 1)
		{
			return true;
		}
		
		//Check White
		//Not in Check
		if( !(this.inCheck(Piece.Color.white)))
		{
			//No moves
			if(whiteMoveList.size() == 0)
			{
				return true;
			}
		}
	
		//Check Black
		//Not in Check
		if( !(this.inCheck(Piece.Color.black)))
		{
			//No moves
			if(blackMoveList.size() == 0)
			{
				return true;
			}
		}
	
		return false;
	}
}