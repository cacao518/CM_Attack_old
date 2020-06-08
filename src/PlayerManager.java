
public class PlayerManager {
	String m_ip;
	String m_name;
	int m_group;
	int m_gunType;
	int m_hp;
	int m_bullet;
	int m_aimX;
	int m_aimY;
	int m_playerPosX;
	int m_playerPosY;
	boolean m_isDie = false;
	
	public PlayerManager(String m_ip, String m_name, int m_group, int m_gunType) {
		super();
		this.m_ip = m_ip;
		this.m_name = m_name;
		this.m_group = m_group;
		this.m_gunType = m_gunType;
		this.m_hp = 100;
		this.m_bullet = 30;
		this.m_aimX = 0;
		this.m_aimY = 0;
		this.m_playerPosX = 300;
		this.m_playerPosY = 300;
		this.m_isDie = false;
	}

	public String getM_ip() {
		return m_ip;
	}

	public void setM_ip(String m_ip) {
		this.m_ip = m_ip;
	}

	public String getM_name() {
		return m_name;
	}

	public void setM_name(String m_name) {
		this.m_name = m_name;
	}

	public int getM_group() {
		return m_group;
	}

	public void setM_group(int m_group) {
		this.m_group = m_group;
	}

	public int getM_gunType() {
		return m_gunType;
	}

	public void setM_gunType(int m_gunType) {
		this.m_gunType = m_gunType;
	}

	public int getM_hp() {
		return m_hp;
	}

	public void setM_hp(int m_hp) {
		this.m_hp = m_hp;
	}

	public int getM_bullet() {
		return m_bullet;
	}

	public void setM_bullet(int m_bullet) {
		this.m_bullet = m_bullet;
	}

	public float getM_aimX() {
		return m_aimX;
	}

	public void setM_aimX(int m_aimX) {
		this.m_aimX = m_aimX;
	}

	public float getM_aimY() {
		return m_aimY;
	}

	public void setM_aimY(int m_aimY) {
		this.m_aimY = m_aimY;
	}

	public float getM_playerPosX() {
		return m_playerPosX;
	}

	public void setM_playerPosX(int m_playerPosX) {
		this.m_playerPosX = m_playerPosX;
	}

	public boolean isM_isDie() {
		return m_isDie;
	}

	public void setM_isDie(boolean m_isDie) {
		this.m_isDie = m_isDie;
	}
	
	
}
