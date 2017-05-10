package cn.driver.model;
/**
 * Music实体类
 * @author Dacle
 * @since 2017-5-1
 * @modify by Dacle
 */
public class Music {
	/**
	 * Music唯一标示
	 */
	private int musicId;
	/**
	 * Music名称，解析自音乐文件目录
	 */
	private String musicName;
	/**
	 * Music艺术家，一般指歌手，解析自音乐文件
	 */
	private String artist;
	/**
	 * Music绝对路径，在硬盘上的位置
	 */
	private String musicPath;
	/**
	 * Music大小，解析自音乐文件
	 */
	private String MusicLength;
	
	public String getAuthor() {
		return artist;
	}
	public void setAuthor(String author) {
		this.artist = author;
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
