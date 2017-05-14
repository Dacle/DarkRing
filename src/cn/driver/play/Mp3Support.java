package cn.driver.play;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

import cn.gui.MainView;
import javazoom.jl.player.Player;
/**
 * Mp3格式支持
 * @author Dacle
 * @since 2017-5-14
 * @modify by Dacle
 */
public class Mp3Support implements Play{
	/**
	 * 音乐文件路径
	 */
	String filepath;
	/**
	 * 适用于当前音乐格式的player对象
	 */
	Player play;
	/**
	 * 暂停标记，用于进程通信控制
	 */
	boolean paused = false;
	/**
	 * 停止标记
	 */
	boolean over = true;
	private long time = 0;
	FloatControl volume = null;
	Object lock = new Object();
	
	public Mp3Support(String filepath) {
		this.filepath = filepath;
	}

	public void run() {
		play(this.filepath);
	}
	
	public void play(String filePath) {  

		AudioInputStream input = null;
		System.out.println("filePath   "+filePath);
		File file = new File(filePath);
		try {
			input = AudioSystem.getAudioInputStream(file);
		} catch (UnsupportedAudioFileException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		//获取歌曲长度
    	AudioFormat format = input.getFormat();
    	AudioFormat decodedFormat = new AudioFormat(
				AudioFormat.Encoding.PCM_SIGNED,
				format.getSampleRate(), 16, format.getChannels(),
				format.getChannels() * 2, format.getSampleRate(),
				false);
    	if(format.getEncoding()==AudioFormat.Encoding.PCM_UNSIGNED || format.getEncoding()==AudioFormat.Encoding.ULAW ||format.getEncoding()==AudioFormat.Encoding.ALAW || format.getEncoding()==AudioFormat.Encoding.PCM_SIGNED){
             time=(file.length()*8000000)/((int)(decodedFormat.getSampleRate()*format.getSampleSizeInBits()));
             System.out.println("时间"+time);
        }else{
            int bitrate=0;
            if(format.properties().get("bitrate")!=null){
                //取得播放速度(单位位每秒)
                bitrate=(int)((Integer)(format.properties().get("bitrate")));
                if(bitrate!=0)
                time=(file.length()*8000000)/bitrate;
                System.out.println("时间2"+time);
            }
        }
    	AudioInputStream audioInput = AudioSystem.getAudioInputStream(decodedFormat, input);
    	DataLine.Info info = new DataLine.Info(SourceDataLine.class, decodedFormat);
    	
    	SourceDataLine dataline = null;
    	try {
			dataline =  (SourceDataLine) AudioSystem.getLine(info);
			dataline.open();
		} catch (LineUnavailableException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	
    	//关联音量调节
    	if(dataline!=null){
    		if(dataline.isControlSupported(FloatControl.Type.MASTER_GAIN)){
        		MainView.volumeSlider.setEnabled(true);
        		volume= (FloatControl)dataline.getControl( FloatControl.Type.MASTER_GAIN );
        		MainView.volumeSlider.setMinimum((int)volume.getMinimum());
        		MainView.volumeSlider.setMaximum((int)volume.getMaximum());
            	//jSliderVolume.setValue((int)(volume.getMinimum()+(4*(volume.getMaximum()-volume.getMinimum()))/5));
            	volume.setValue((float)(volume.getMinimum()+(4*(volume.getMaximum()-volume.getMinimum()))/5));
         	}
    		System.out.println("1");
        }else{
         	volume=null;
         	MainView.volumeSlider.setEnabled(false);
         	System.out.println("2");
        }
    	//设置进度条初始值
    	MainView.musicSlider.setMaximum((int)time);
    	MainView.musicSlider.setValue(0);
    	
    	if(dataline!=null){
    		try {
				dataline.open(decodedFormat);
			} catch (LineUnavailableException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			byte[] abData = new byte[4096];
	    	int nBytesRead = 0;
    		synchronized (lock){
    			while(nBytesRead != -1){
    				while (paused) {
						if(dataline.isRunning()){
							dataline.stop();
							System.out.println("暂停");
						}
						try {
							lock.wait();//在其他线程调用此对象的notify()方法之前，使当前线程等待
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						System.out.println("等待");
					}
    				if(!dataline.isRunning() && !over) {
						System.out.println("开始播放");
						dataline.start();
					}
					if (over && dataline.isRunning()) {
						System.out.println("停止播放");
						MainView.musicSlider.setValue(0);
						dataline.drain();
						dataline.stop();
						dataline.close();
					}
					MainView.musicSlider.setValue((int)dataline.getMicrosecondPosition());
    				try {
						nBytesRead = audioInput.read(abData, 0, abData.length);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
    				if (nBytesRead >= 0)
    					dataline.write(abData, 0, nBytesRead);
    			}
    		}
    	}

	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		over = true;
	}

	@Override
	public void carryOn() {
		// TODO Auto-generated method stub
		synchronized(lock) {
			  paused = false;
			  lock.notifyAll();
		}
	}

	@Override
	public void paused() {
		// TODO Auto-generated method stub
		 paused = true;
	}
}
