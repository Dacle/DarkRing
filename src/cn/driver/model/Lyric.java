package cn.driver.model;
/**
 * Lyric实体类
 * @author Dacle
 * @since 2017-5-1
 * @modify by Dacle
 */
public class Lyric {
	/**
	 * Lyric唯一标示
	 */
	private int LyricId;
	/**
	 * Lyric名称，解析自歌词文件目录
	 */
	private String lyricName;
	/**
	 * Lyric作者，解析自歌词文件
	 */
	private String author;
	/**
	 * Lyric绝对路径，歌词文件在硬盘上的位置
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
	