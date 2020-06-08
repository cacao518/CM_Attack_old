import java.util.ArrayList;

import kr.ac.konkuk.ccslab.cm.sns.CMSNSUserAccessSimulator;
import kr.ac.konkuk.ccslab.cm.stub.CMServerStub;

public class Server {
	
	private CMServerStub m_serverStub;
	private CMServerEventHandler m_eventHandler;
	private boolean m_bRun;
	private int playerCount;
	private GameManager[] GM;
	
	public Server()
	{
		m_serverStub = new CMServerStub();
		m_eventHandler = new CMServerEventHandler(m_serverStub);
		m_bRun = true;
		playerCount = 0;
	}
	public void CreateGM() {
		
	}
	public void DeleteGM() {
		
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Server server = new Server();
		server.m_serverStub.setAppEventHandler(server.m_eventHandler);
		server.m_serverStub.setAppEventHandler(server.m_eventHandler2);
		server.m_serverStub.startCM();

	}
}
