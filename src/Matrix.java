
public class Matrix {

	private  int protect[][];
	private  int kill[][];
	private  int die[][];
	private  int position[][];
	
	public Matrix()
	{
		protect = new int[6][6];
		
		//Pawn = 0
		protect[0][0] = 0;
		protect[0][1] = 0;
		protect[0][2] = 0;
		protect[0][3] = 0;
		protect[0][4] = 0;
		protect[0][5] = 0;
		
		//Knight =1
		protect[1][0] = 0;
		protect[1][1] = 0;
		protect[1][2] = 0;
		protect[1][3] = 0;
		protect[1][4] = 0;
		protect[1][5] = 0;
		
		//Bishop =2
		protect[2][0] = 0;
		protect[2][1] = 0;
		protect[2][2] = 0;
		protect[2][3] = 0;
		protect[2][4] = 0;
		protect[2][5] = 0;
		
		//Rook =3
		protect[3][0] = 0;
		protect[3][1] = 0;
		protect[3][2] = 0;
		protect[3][3] = 0;
		protect[3][4] = 0;
		protect[3][5] = 0;
		
		//Queen =4
		protect[4][0] = 0;
		protect[4][1] = 0;
		protect[4][2] = 0;
		protect[4][3] = 0;
		protect[4][4] = 0;
		protect[4][5] = 0;
		
		//King =5
		protect[5][0] = 0;
		protect[5][1] = 0;
		protect[5][2] = 0;
		protect[5][3] = 0;
		protect[5][4] = 0;
		protect[5][5] = 0;
		
		kill = new int[6][6];
		
		kill[0][0] = 0;
		kill[0][1] = 0;
		kill[0][2] = 0;
		kill[0][3] = 0;
		kill[0][4] = 0;
		kill[0][5] = 0;
	
		//Knight =1
		kill[1][0] = 0;
		kill[1][1] = 0;
		kill[1][2] = 0;
		kill[1][3] = 0;
		kill[1][4] = 0;
		kill[1][5] = 0;
	
		//Bishop =2
		kill[2][0] = 0;
		kill[2][1] = 0;
		kill[2][2] = 0;
		kill[2][3] = 0;
		kill[2][4] = 0;
		kill[2][5] = 0;
	
		//Rook =3
		kill[3][0] = 0;
		kill[3][1] = 0;
		kill[3][2] = 0;
		kill[3][3] = 0;
		kill[3][4] = 0;
		kill[3][5] = 0;
		
		//Queen =4
		kill[4][0] = 0;
		kill[4][1] = 0;
		kill[4][2] = 0;
		kill[4][3] = 0;
		kill[4][4] = 0;
		kill[4][5] = 0;
		
		//King =5
		kill[5][0] = 0;
		kill[5][1] = 0;
		kill[5][2] = 0;
		kill[5][3] = 0;
		kill[5][4] = 0;
		kill[5][5] = 0;
		
		die = new int[6][6];
		
		die[0][0] = 0;
		die[0][1] = 0;
		die[0][2] = 0;
		die[0][3] = 0;
		die[0][4] = 0;
		die[0][5] = 0;
	
		//Knight =1
		die[1][0] = 0;
		die[1][1] = 0;
		die[1][2] = 0;
		die[1][3] = 0;
		die[1][4] = 0;
		die[1][5] = 0;
	
		//Bishop =2
		die[2][0] = 0;
		die[2][1] = 0;
		die[2][2] = 0;
		die[2][3] = 0;
		die[2][4] = 0;
		die[2][5] = 0;
	
		//Rook =3
		die[3][0] = 0;
		die[3][1] = 0;
		die[3][2] = 0;
		die[3][3] = 0;
		die[3][4] = 0;
		die[3][5] = 0;
		
		//Queen =4
		die[4][0] = 0;
		die[4][1] = 0;
		die[4][2] = 0;
		die[4][3] = 0;
		die[4][4] = 0;
		die[4][5] = 0;
		
		//King =5
		die[5][0] = 0;
		die[5][1] = 0;
		die[5][2] = 0;
		die[5][3] = 0;
		die[5][4] = 0;
		die[5][5] = 0;
		
		position = new int[8][8];
		
		position[0][0] = 0;
		position[0][1] = 0;
		position[0][2] = 0;
		position[0][3] = 0;
		position[0][4] = 0;
		position[0][5] = 0;
		position[0][6] = 0;
		position[0][7] = 0;

		position[1][0] = 0;
		position[1][1] = 0;
		position[1][2] = 0;
		position[1][3] = 0;
		position[1][4] = 0;
		position[1][5] = 0;
		position[1][6] = 0;
		position[1][7] = 0;
	
		position[2][0] = 0;
		position[2][1] = 0;
		position[2][2] = 0;
		position[2][3] = 0;
		position[2][4] = 0;
		position[2][5] = 0;
		position[2][6] = 0;
		position[2][7] = 0;
	
		position[3][0] = 0;
		position[3][1] = 0;
		position[3][2] = 0;
		position[3][3] = 0;
		position[3][4] = 0;
		position[3][5] = 0;
		position[3][6] = 0;
		position[3][7] = 0;
		
		position[4][0] = 0;
		position[4][1] = 0;
		position[4][2] = 0;
		position[4][3] = 0;
		position[4][4] = 0;
		position[4][5] = 0;
		position[4][6] = 0;
		position[4][7] = 0;
		
		position[5][0] = 0;
		position[5][1] = 0;
		position[5][2] = 0;
		position[5][3] = 0;
		position[5][4] = 0;
		position[5][5] = 0;
		position[5][6] = 0;
		position[5][7] = 0;

		position[6][0] = 0;
		position[6][1] = 0;
		position[6][2] = 0;
		position[6][3] = 0;
		position[6][4] = 0;
		position[6][5] = 0;
		position[6][6] = 0;
		position[6][7] = 0;

		position[7][0] = 0;
		position[7][1] = 0;
		position[7][2] = 0;
		position[7][3] = 0;
		position[7][4] = 0;
		position[7][5] = 0;
		position[7][6] = 0;
		position[7][7] = 0;
	}
	
	public  int getKillValue(Piece.Name n1, Piece.Name n2)
	{
		int i = n1.ordinal();
		int j = n2.ordinal();
		
		return kill[i][j];
	}
	
	public  int getProtectValue(Piece.Name n1, Piece.Name n2)
	{
		int i = n1.ordinal();
		int j = n2.ordinal();
		
		return protect[i][j];
	}
	
	public  int getDieValue(Piece.Name n1, Piece.Name n2)
	{
		int i = n1.ordinal();
		int j = n2.ordinal();
		
		return die[i][j];
	}
	
	public  int getPositionValue(Piece.Name n1, int r, int c)
	{
		return position[r][c];
	}
}
