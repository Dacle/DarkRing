package cn.driver.model;
/**
 * Lyricʵ����
 * @author Dacle
 * @since 2017-5-1
 * @modify by Dacle
 */
public class Lyric {
	/**
	 * LyricΨһ��ʾ
	 */
	private int LyricId;
	/**
	 * Lyric���ƣ������Ը���ļ�Ŀ¼
	 */
	private String lyricName;
	/**
	 * Lyric���ߣ������Ը���ļ�
	 */
	private String author;
	/**
	 * Lyric����·��������ļ���Ӳ���ϵ�λ��
	 */
	private String lyricPath;
	
	public int getLyricId() {
		return LyricId;
	}
	public void setLyricId(int lyricId) {
		LyricId = lyricId;
	}
	public String getLyricName() {
		return lyricName;
	}
	public void setLyricName(String lyricName) {
		this.lyricName = lyricName;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getLyricPath() {
		return lyricPath;
	}
	public void setLyricPath(String lyricPath) {
		this.lyricPath = lyricPath;
	}
}
	