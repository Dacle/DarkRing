package cn.driver.play;

/**
 * 歌曲播放线程接口
 * @author Dacle
 * @since 2017-5-10
 * @modify by Dacle
 */
public interface Play extends Runnable{
	@Override
	public void run();
	
	public boolean play(String filePath);
	public void stop();
	public void carryOn();
	public void paused();
}