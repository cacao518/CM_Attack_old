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
				switch (use.getStringID()){
					case "joinComplete":
						updateGroupInfo(use);
						break;
					case "hit":
						//TODO 클라이언트 체력정보 갱신.
						updateHPInfo(use);
						break;
					case "gameover":
						//TODO 클라이언트 게임종료 실행.
						gameOver(use);
						break;
				}
				processUserEvent(cme);
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
	private void processUserEvent(CMEvent cme)
	{
		CMUserEvent ue = (CMUserEvent) cme; //click move
		String mode = ue.getStringID();
		//String mode = ue.getEventField(CMInfo.CM_STR, "game");
		switch(mode)
		{
			case "Join":
				client.Game();
				break;
			case "click":
				String Hp = ue.getEventField(CMInfo.CM_INT, "Hp");
				client.PlayerHit(Integer.parseInt(Hp));
				break;
			case "move":
				String x = ue.getEventField(CMInfo.CM_INT, "x");
				String y = ue.getEventField(CMInfo.CM_INT, "y");
				client.moveOtherPlayer(Integer.parseInt(x), Integer.parseInt(y));
				break;
			default:
				return;
		}
		return;
	}

}