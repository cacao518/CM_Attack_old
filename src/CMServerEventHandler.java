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
	private int playerCount;
	public CMServerEventHandler(CMServerStub serverStub, ArrayList<GameManager> GM, int playerCount)
	{
		this.m_serverStub = serverStub;
		this.GM = GM;
		this.playerCount = playerCount;
	}
	
	@Override
	public void processEvent(CMEvent cme) {
		// TODO Auto-generated method stub
		switch(cme.getType())
		{
		case CMInfo.CM_DUMMY_EVENT:
			processDummyEvent(cme);
			break;
		case CMInfo.CM_USER_EVENT:
			CMUserEvent ue = (CMUserEvent) cme;
			if(Integer.valueOf(ue.getEventField(CMInfo.CM_INT, "group")) == -1)
				joinPlayer(cme);
			else
				//게임중
			break;
		default:
			return;
		}
	}
	private void joinPlayer(CMEvent cme) {
		CMUserEvent ue = (CMUserEvent) cme;
		PlayerManager pm = new PlayerManager(ue.getEventField(CMInfo.CM_STR, "ip"), ue.getEventField(CMInfo.CM_STR, "name"), Integer.valueOf(ue.getEventField(CMInfo.CM_INT, "group")),Integer.valueOf(ue.getEventField(CMInfo.CM_INT, "guntype")));
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