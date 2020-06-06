import kr.ac.konkuk.ccslab.cm.sns.CMSNSUserAccessSimulator;
import kr.ac.konkuk.ccslab.cm.stub.CMServerStub;

public class Server {
	
	CMServerStub m_serverStub;
	CMServerEventHandler m_eventHandler;
	CMClientEventHandler m_eventHandler2;
	boolean m_bRun;
	
	public Server()
	{
		m_serverStub = new CMServerStub();
		m_eventHandler = new CMServerEventHandler(m_serverStub);
		m_eventHandler2 = new CMClientEventHandler();
		m_bRun = true;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Server server = new Server();
		server.m_serverStub.setAppEventHandler(server.m_eventHandler);
		server.m_serverStub.setAppEventHandler(server.m_eventHandler2);
		server.m_serverStub.startCM();

	}
}
