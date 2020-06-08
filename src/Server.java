import java.util.ArrayList;

import kr.ac.konkuk.ccslab.cm.sns.CMSNSUserAccessSimulator;
import kr.ac.konkuk.ccslab.cm.stub.CMServerStub;

public class Server {
	
	CMServerStub m_serverStub;
	CMServerEventHandler m_eventHandler;
	boolean m_bRun;
	private ArrayList<GameManager> GM;
	public Server()
	{
		m_serverStub = new CMServerStub();
		m_eventHandler = new CMServerEventHandler(m_serverStub, GM);
		m_bRun = true;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Server server = new Server();
		server.m_serverStub.setAppEventHandler(server.m_eventHandler);
		server.m_serverStub.startCM();

	}
}
