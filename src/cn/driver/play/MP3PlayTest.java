package cn.driver.play;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.sound.sampled.*;

public class MP3PlayTest extends Player{
	String path;
	AudioFormat decodeFormat;
	
	public MP3PlayTest(String filepath) {
		// TODO Auto-generated constructor stub
		System.out.println("初始化MP3PlayTest类");
		this.path = filepath;
	}

	@Override
	public AudioInputStream getAudioInputStream() {
		// TODO Auto-generated method stub

		File file = new File(path);
		FileInputStream fis = null;
		
		if(file.exists()&&file.canRead()){
			System.out.println("音乐路径：   "+path);
		}else{
			System.out.println("文件有问题");
		}
		AudioInputStream is =null;
		try {
			fis = new FileInputStream(path);
			System.out.println("当前音频流格式:   "+fis.toString());  
			is = AudioSystem.getAudioInputStream(fis);
		} catch (UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		decodeFormat = is.getFormat();
		System.out.println("当前音频流格式:   "+fis.toString());  
		decodeFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,
				(float) 44100.0, 16, 
				2, 2 * 2,
				(float) 44100.0, false);
		AudioInputStream rs= new AudioInputStream(fis, decodeFormat, decodeFormat.getChannels() * 2);
		
		return rs;
	}

	@Override
	public double getTimeLength() {
		// TODO Auto-generated method stub
		double time = new File(path).length()/(decodeFormat.getFrameRate()*decodeFormat.getFrameSize());
		System.out.println("时长：   "+time);
		return time;
	}


}
