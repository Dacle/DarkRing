package cn.driver.model;
/**
 * Musicʵ����
 * @author Dacle
 * @since 2017-5-1
 * @modify by Dacle
 */
public class Music {
	/**
	 * MusicΨһ��ʾ
	 */
	private int musicId;
	/**
	 * Music���ƣ������������ļ�Ŀ¼
	 */
	private String musicName;
	/**
	 * Music���ߣ�һ��ָ���֣������������ļ�
	 */
	private String author;
	/**
	 * Music����·������Ӳ���ϵ�λ��
	 */
	private String musicPath;
	/**
	 * Music��С�������������ļ�
	 */
	private String MusicLength;
	
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public int getMusicId() {
		return musicId;
	}
	public void setMusicId(int musicId) {
		this.musicId = musicId;
	}
	public String getMusicName() {
		return musicName;
	}
	public void setMusicName(String musicName) {
		this.musicName = musicName;
	}
	public String getMusicPath() {
		return musicPath;
	}
	public void setMusicPath(String musicPath) {
		this.musicPath = musicPath;
	}
	public String getMusicLength() {
		return MusicLength;
	}
	public void setMusicLength(String musicLength) {
		MusicLength = musicLength;
	}
	
}
