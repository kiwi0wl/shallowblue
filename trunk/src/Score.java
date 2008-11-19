
public class Score {
	private int protect;
	private int kill;
	private int die;
	private int position;
	private int escape;

	public Score()
	{
		protect=0;
		kill=0;
		die=0;
		position=0;
		escape=0;
	}
	
//Mutator	
	public void setProtect(int p)
	{
		protect = p;
	}
	
	public void setKill(int k)
	{
		kill = k;
	}
	
	public void setDie(int d)
	{
		die = d;
	}
	
	public void setPosition(int p)
	{
		position = p;
	}
	
	public void setEscape(int e)
	{
		escape = e;
	}
	
//Accessor
	public int getProtect()
	{
		return protect;
	}
	
	public int getKill()
	{
		return kill;
	}
	
	public int getDie()
	{
		return die;
	}
	
	public int getPosition()
	{
		return position;
	}
	
	public int getEscape()
	{
		return escape;
	}
	
//Methods	
	public void add(Score s)
	{
		protect += s.getProtect();
		kill += s.getKill();
		die += s.getDie();
		position += s.getPosition();
		escape +=s.getEscape();
	}
	
/*	public boolean better(Score s, AI.strategy x)
	{

		if (x = AI.strategy.aggresive) //kill
		else if (x = AI.strategy.defensive) //protect
		else if (x = AI.strategy.safe) //die
		else if (x = AI.strategy.suicide) 
	
		return false;
	}
*/	
}
