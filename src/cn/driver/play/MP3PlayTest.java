package cn.driver.play;

import java.io.File;
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
		if(file.exists()&&file.canRead()){
			System.out.println("音乐路径：   "+path);
		}else{
			System.out.println("文件有问题");
		}
		AudioInputStream is=null;
		AudioInputStream rs=null;
		try {
			is = AudioSystem.getAudioInputStream(file);
		} catch (UnsupportedAudioFileException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	
		decodeFormat = is.getFormat();
		
		System.out.println("format:   "+decodeFormat.toString());
		if(decodeFormat.getEncoding()!=AudioFormat.Encoding.PCM_SIGNED){
			decodeFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,
					decodeFormat.getSampleRate(), 16, 
					decodeFormat.getChannels(), decodeFormat.getChannels() * 2,
					decodeFormat.getSampleRate(), false);
		}
		rs = AudioSystem.getAudioInputStream(decodeFormat, is);
		try {
			System.out.println("new format:   "+AudioSystem.getAudioFileFormat(file).hashCode());
		} catch (UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
