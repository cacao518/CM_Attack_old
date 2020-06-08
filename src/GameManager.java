
public class GameManager {

	PlayerManager[] PM = new PlayerManager[2];
	int m_gameStatus;
	int m_gameCount;
	public GameManager(PlayerManager PM, int index) {
		this.PM[index] = PM;
		this.m_gameStatus = 0;

	}
	public void removeGM() {
		this.PM = null;
	}
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
