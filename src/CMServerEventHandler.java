import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

import kr.ac.konkuk.ccslab.cm.entity.CMUser;
import kr.ac.konkuk.ccslab.cm.event.*;
import kr.ac.konkuk.ccslab.cm.event.handler.CMAppEventHandler;
import kr.ac.konkuk.ccslab.cm.info.CMConfigurationInfo;
import kr.ac.konkuk.ccslab.cm.info.CMInfo;
import kr.ac.konkuk.ccslab.cm.manager.CMDBManager;
import kr.ac.konkuk.ccslab.cm.stub.CMServerStub;
import kr.ac.konkuk.ccslab.cm.stub.CMStub;

public class CMServerEventHandler implements CMAppEventHandler {
	static int HIT_RANGE = 5;

	private Server m_server;
	private CMServerStub m_serverStub;
	private ArrayList<GameManager> GM;
	private int playerCount;
	public CMServerEventHandler(CMServerStub serverStub, ArrayList<GameManager> GM, int playerCount, Server server)
	{
		this.m_serverStub = serverStub;
		this.GM = GM;
		this.playerCount = playerCount;
		this.m_server = server;
	}

	@Override
	public void processEvent(CMEvent cme) {
		// TODO Auto-generated method stub
		switch(cme.getType())
		{
			case CMInfo.CM_SESSION_EVENT:
				m_server.plusPlayerCount();
				break;
			case CMInfo.CM_DUMMY_EVENT:
				processDummyEvent(cme);
				break;
			case CMInfo.CM_USER_EVENT:
				CMUserEvent ue = (CMUserEvent) cme;
				if(Integer.parseInt(ue.getEventField(CMInfo.CM_INT, "group")) == -1)
					joinPlayer(cme);
				else
					playGame(cme);
				break;
			default:
		}
	}
	private void joinPlayer(CMEvent cme) {
		CMUserEvent ue = (CMUserEvent) cme;
		int GMIndex = (playerCount - 1) / 2;
		int PMIndex = (playerCount - 1) % 2;
		String ip = ue.getEventField(CMInfo.CM_STR, "ip");
		String name = ue.getEventField(CMInfo.CM_STR, "name");
		int gunType = Integer.parseInt(ue.getEventField(CMInfo.CM_INT, "guntype"));
		PlayerManager pm = new PlayerManager(ip, name, GMIndex,gunType);
		GM.set(GMIndex, new GameManager(pm, PMIndex));
		if((playerCount - 1) % 2 == 0) {
			GM.get(GMIndex).m_gameStatus = 1;
			CMUserEvent use = new CMUserEvent();
			use.setStringID("joinComplete");
			use.setEventField(CMInfo.CM_INT,"group",String.valueOf(GMIndex));
			use.setEventField(CMInfo.CM_STR,"ip",ip);
			use.setEventField(CMInfo.CM_STR,"name",name);
			use.setEventField(CMInfo.CM_INT,"group",String.valueOf(gunType));
			m_serverStub.send(use, String.valueOf(GM.get(GMIndex).PM[PMIndex].m_name));
			m_serverStub.send(use, String.valueOf(GM.get(GMIndex).PM[PMIndex - 1].m_name));
		}
	}
	private void playGame(CMEvent cme){
		CMUserEvent ue = (CMUserEvent) cme;
		int gIndex = Integer.parseInt(ue.getEventField(CMInfo.CM_INT,"group"));
		String userName = ue.getEventField(CMInfo.CM_STR,"name");
		int pIndex1 = -1;
		int pIndex2;
		int gunType = -1;
		GameManager gm = GM.get(gIndex);
		for(int i = 0; i < 2 ; i++){
			if(gm.PM[i].m_name.equals(userName)) {
				pIndex1 = i;
				gunType = gm.PM[pIndex1].m_gunType;
				break;
			}
		}
		if(pIndex1==0)
			pIndex2 = 1;
		else
			pIndex2 = 0;
		if(ue.getStringID().equals("click")){
			//총쏘는 액션
			int aimX = Integer.parseInt(ue.getEventField(CMInfo.CM_INT,"aimX"));
			int aimY = Integer.parseInt(ue.getEventField(CMInfo.CM_INT,"aimY"));
			//TODO x,y좌표 계산하여 히트되었는지 체크 후 hp변환정보 send
			if(aimX <= gm.PM[pIndex2].m_playerPosX + HIT_RANGE && aimX >= gm.PM[pIndex2].m_playerPosX - HIT_RANGE){
				if(aimY <= gm.PM[pIndex2].m_playerPosY + HIT_RANGE && aimY >= gm.PM[pIndex2].m_playerPosY - HIT_RANGE) {
					CMUserEvent use = new CMUserEvent();
					use.setStringID("hit");
					switch (gunType){
						case 0:	//라플
							int dmg1 = (int)(Math.random() * 20)+10;
							use.setEventField(CMInfo.CM_INT,"damage",String.valueOf(dmg1));
							gm.PM[pIndex2].m_hp -= dmg1;
							break;
						case 1:	//스나이퍼
							int dmg2 = (int)(Math.random() * 50)+50;
							use.setEventField(CMInfo.CM_INT,"damage",String.valueOf(dmg2));
							gm.PM[pIndex2].m_hp -= dmg2;
							break;
					}
				}
			}
			if(gm.PM[pIndex2].m_hp <= 0){
				endGame(gm.PM[pIndex2].m_name, gm.PM[pIndex1].m_name, gIndex);
			}
		}
		else if(ue.getStringID().equals("move")){
			//움직이는 액션
			boolean LR = true;
			if(ue.getEventField(CMInfo.CM_STR, "x").equals("a"))	//a키 입력은 true
				LR = true;
			else if(ue.getEventField(CMInfo.CM_STR, "x").equals("d"))		//d키 입력은 false
				LR = false;
			if(LR) {
				if(gm.PM[pIndex1].m_playerPosX - 1 >= 0)
					gm.PM[pIndex1].m_playerPosX--;
			}
			else {
				if(gm.PM[pIndex1].m_playerPosX + 1 <= 600)
					gm.PM[pIndex1].m_playerPosX++;
			}
		}
	}
	private void endGame(String loserName, String winner, int index){
		CMUserEvent use = new CMUserEvent();
		use.setStringID("gameover");
		use.setEventField(CMInfo.CM_STR,"winner", winner);
		use.setEventField(CMInfo.CM_STR,"loser", loserName);
		m_serverStub.send(use,loserName);
		m_serverStub.send(use,winner);
		GM.get(index).removeGM();
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