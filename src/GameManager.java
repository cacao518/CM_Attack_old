
public class GameManager {

	PlayerManager[] PM = new PlayerManager[2];
	int m_gameStatus;
	int m_gameCount;
<<<<<<< HEAD
	public GameManager(PlayerManager PM, int index) {
		this.PM[index] = PM;
		this.m_gameStatus = 0;
	}
	public void removeGM() {
		this.PM = null;
	}
=======
	public GameManager() {
		this.PM[0] = new PlayerManager("0","0",0,0);
		this.PM[1] = new PlayerManager("0","0",0,0);
		this.m_gameStatus = 0;
	}
	public void removeGM() {
		this.PM = null;
	}

	public PlayerManager[] getPM() {
		return PM;
	}

>>>>>>> branch 'master' of https://github.com/cacao518/CM_Attack.git
	public void GameReady()
	{
		
	}
	public void GameStart()
	{
		
	}
	public void Game()
	{
		
	}
	public void GameOver()
	{
		
	}
}
