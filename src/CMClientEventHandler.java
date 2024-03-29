<<<<<<< HEAD
import kr.ac.konkuk.ccslab.cm.event.CMDummyEvent;
import kr.ac.konkuk.ccslab.cm.event.CMEvent;
import kr.ac.konkuk.ccslab.cm.event.CMUserEvent;
import kr.ac.konkuk.ccslab.cm.event.handler.CMAppEventHandler;
import kr.ac.konkuk.ccslab.cm.info.CMInfo;

public class CMClientEventHandler implements CMAppEventHandler {

	Client client;

	public CMClientEventHandler(Client client) {
		super();
		this.client = client;
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
				CMUserEvent use = (CMUserEvent) cme;
				switch (use.getStringID()) {
					case "joinComplete":
						updateGroupInfo(use);
						break;
					case "hit":
						updateHPInfo(use);
						break;
					case "gameover":
						gameOver(use);
						break;
				}
				break;
			default:
				return;
		}
	}
	//client 그룹정보 갱신
	private void updateGroupInfo(CMUserEvent use){
		int group = Integer.parseInt(use.getEventField(CMInfo.CM_INT,"group"));
		client.player.m_group = group;
		String ip = use.getEventField(CMInfo.CM_STR, "ip");
		String name = use.getEventField(CMInfo.CM_STR, "name");
		int gunType = Integer.parseInt(use.getEventField(CMInfo.CM_INT, "guntype"));
		client.otherPlayer = new Player(ip,name,group,gunType);
		client.Game();
	}
	private void updateHPInfo(CMUserEvent use){
		int damage = Integer.parseInt(use.getEventField(CMInfo.CM_INT, "damage"));
		client.player.m_hp -= damage;
	}
	private void gameOver(CMUserEvent use){

	}
	private void processDummyEvent(CMEvent cme)
	{
		CMDummyEvent due = (CMDummyEvent) cme;
		System.out.println("[Client] 서버로부터 답장을 받았습니다 : "+due.getDummyInfo());
		String gm = due.getDummyInfo();
		if (gm.equals("start"))
			client.Game();
		return;
	}
=======
import kr.ac.konkuk.ccslab.cm.event.CMDummyEvent;
import kr.ac.konkuk.ccslab.cm.event.CMEvent;
import kr.ac.konkuk.ccslab.cm.event.CMUserEvent;
import kr.ac.konkuk.ccslab.cm.event.handler.CMAppEventHandler;
import kr.ac.konkuk.ccslab.cm.info.CMInfo;

public class CMClientEventHandler implements CMAppEventHandler {

	Client client;

	public CMClientEventHandler(Client client) {
		super();
		this.client = client;
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
				CMUserEvent use = (CMUserEvent) cme;
				switch (use.getStringID()) {
					case "joinComplete":
						updateGroupInfo(use);
						break;
					case "hit":
						updateHPInfo(use);
						break;
					case "gameover":
						gameOver(use);
						break;
				}
				break;
			default:
				return;
		}
	}
	//client 그룹정보 갱신
	private void updateGroupInfo(CMUserEvent use){
		int group = Integer.parseInt(use.getEventField(CMInfo.CM_INT,"group"));
		client.player.m_group = group;
		String ip = use.getEventField(CMInfo.CM_STR, "ip");
		String name = use.getEventField(CMInfo.CM_STR, "name");
		int gunType = Integer.parseInt(use.getEventField(CMInfo.CM_INT, "guntype"));
		client.otherPlayer = new Player(ip,name,group,gunType);
		client.Game();
	}
	private void updateHPInfo(CMUserEvent use){
		int damage = Integer.parseInt(use.getEventField(CMInfo.CM_INT, "damage"));
		client.player.m_hp -= damage;
	}
	private void gameOver(CMUserEvent use){

	}
	private void processDummyEvent(CMEvent cme)
	{
		CMDummyEvent due = (CMDummyEvent) cme;
		System.out.println("[Client] 서버로부터 답장을 받았습니다 : "+due.getDummyInfo());
		String gm = due.getDummyInfo();
		if (gm.equals("start"))
			client.Game();
		return;
	}
>>>>>>> branch 'master' of https://github.com/cacao518/CM_Attack.git
}