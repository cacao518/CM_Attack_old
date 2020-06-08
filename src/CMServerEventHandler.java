import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import kr.ac.konkuk.ccslab.cm.entity.CMUser;
import kr.ac.konkuk.ccslab.cm.event.CMDummyEvent;
import kr.ac.konkuk.ccslab.cm.event.CMEvent;
import kr.ac.konkuk.ccslab.cm.event.CMSessionEvent;
import kr.ac.konkuk.ccslab.cm.event.CMUserEvent;
import kr.ac.konkuk.ccslab.cm.event.handler.CMAppEventHandler;
import kr.ac.konkuk.ccslab.cm.info.CMConfigurationInfo;
import kr.ac.konkuk.ccslab.cm.info.CMInfo;
import kr.ac.konkuk.ccslab.cm.manager.CMDBManager;
import kr.ac.konkuk.ccslab.cm.stub.CMServerStub;

public class CMServerEventHandler implements CMAppEventHandler {
	private CMServerStub m_serverStub;
	private ArrayList<GameManager> GM;
	public CMServerEventHandler(CMServerStub serverStub, ArrayList<GameManager> GM, int playerCount)
	{
		this.m_serverStub = serverStub;
		this.GM = GM;
	}
	
	@Override
	public void processEvent(CMEvent cme) {
		// TODO Auto-generated method stub
		switch(cme.getType())
		{
		case CMInfo.CM_DUMMY_EVENT:
			processDummyEvent(cme);
			break;
		default:
			return;
		}
	}
	private void joinPlayer(CMEvent cme) {
		CMUserEvent ue = (CMUserEvent) cme;
		ue.getAllEventFields().firstElement();
//		PlayerManager pm = new PlayerManager();
	}
	private void processDummyEvent(CMEvent cme)
	{
		CMDummyEvent due = (CMDummyEvent) cme;
		System.out.println("[Server] 클라이언트에게 요청이 도착했습니다 : "+due.getDummyInfo());
		
		if(due.getDummyInfo().equals("CurrentTime"))
		{
			Date time = new Date();
			SimpleDateFormat format1 = new SimpleDateFormat();
			CMDummyEvent due2 = new CMDummyEvent();
			due2.setDummyInfo("현재시간->" + format1.format(time));
			m_serverStub.send(due2, due.getSender());
			System.out.println("[Server] 클라이언트에게 메세지를 전송했습니다 : "+due2.getDummyInfo());
			
		}
		return;
	}
	
}