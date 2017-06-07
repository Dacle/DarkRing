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
		System.out.println("��ʼ��MP3PlayTest��");
		this.path = filepath;
	}

	@Override
	public AudioInputStream getAudioInputStream() {
		// TODO Auto-generated method stub

		File file = new File(path);
		FileInputStream fis = null;
		
		if(file.exists()&&file.canRead()){
			System.out.println("����·����   "+path);
		}else{
			System.out.println("�ļ�������");
		}
		AudioInputStream is =null;
		try {
			fis = new FileInputStream(path);
			System.out.println("��ǰ��Ƶ����ʽ:   "+fis.toString());  
			is = AudioSystem.getAudioInputStream(fis);
		} catch (UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		decodeFormat = is.getFormat();
		System.out.println("��ǰ��Ƶ����ʽ:   "+fis.toString());  
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
		System.out.println("ʱ����   "+time);
		return time;
	}


}
