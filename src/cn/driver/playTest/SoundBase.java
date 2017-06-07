package cn.driver.playTest;

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
import javax.swing.JOptionPane;

import cn.gui.MainView;

public class SoundBase implements Runnable{
	
	private static final int BUFFER_SIZE = 1024;
	private String fileToPlay = null;
	
	//ͨѶ����
	public static boolean threadExit = false;
	public static boolean stopped = true;
	private static boolean paused = false;
	private static boolean playing = false;
	
	//�߳�ͬ������
	private static Object synch = new Object();
	
	private Thread playerThread = null;
	

	private AudioInputStream audioInputStream = null;
	private SourceDataLine line = null;
	private AudioFormat baseFormat = null;
	
	/**
	 * ���캯��
	 * @param filePath ��Ƶ�ļ�·��
	 */
	public SoundBase(String filePath){
		this.fileToPlay = filePath;
	}
	
	public SoundBase(){
		this.fileToPlay = "default.wav";
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(!threadExit){
			waitforSignal();
			if(!stopped)
				playMusic();
		}
	}

	public void play(){
		//�����ǰ����ͣ���߲���ֹͣ״̬
		if(!stopped || paused)
			return;
		//����̲߳����ڣ������̲߳�����
		if(this.playerThread == null){
			this.playerThread = new Thread(this);
			playerThread.start();
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//����״̬Ϊ����ͣ�ͷ�ֹͣ
		synchronized (synch) {
			stopped = false;
			paused = false;
			synch.notifyAll();
		}
		
	}
	
	public void setFileToPlay(String filePath){
		this.fileToPlay = filePath;
	}
	
	public void playFile(String f){
		setFileToPlay(f);
		play();
	}
	
	private void playMusic() {
		// TODO Auto-generated method stub
		byte[] audioData = new byte[BUFFER_SIZE];
		
		//��ȡ��Ƶ������
		try {
			audioInputStream = AudioSystem.getAudioInputStream(new File(this.fileToPlay));
		} catch (UnsupportedAudioFileException | IOException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, this.fileToPlay+"���ļ�ʧ�ܣ�");
		}
		System.out.println("���ڽ���");
		if(audioInputStream != null){
			//��ȡ��Ƶ�ļ���ʽ
			baseFormat = audioInputStream.getFormat();
			//���ļ��л�ȡ����
			line = getLine(baseFormat);
			//������ǿɽ������ͣ������Ƿ��ܹ����ⲿ��ȡ������
			if(line == null){
				AudioFormat decodedFormat = new AudioFormat(
						AudioFormat.Encoding.PCM_SIGNED,
						baseFormat.getSampleRate(),
						16,
						baseFormat.getChannels(),
						baseFormat.getChannels()*2,
						baseFormat.getSampleRate(),
						false);
				audioInputStream = AudioSystem.getAudioInputStream(decodedFormat, audioInputStream);
				line = getLine(decodedFormat);
			}
		}
		//���Ǵ򲻿������ļ����ܲ���
		if(line == null)
			return;
		
		playing = true;
		//׼�������ļ�
		line.start();
		System.out.println("׼������");
		
		setVolume();
		int inBytes = 0;
		//ѭ����������
		while((inBytes!= -1) && (!stopped) && (!threadExit)){
			try {
				inBytes = audioInputStream.read(audioData, 0, BUFFER_SIZE);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(null, "�޷���ȡ�ļ����ݣ�");
			}
			//����ȡ������д���Ƶ��
			if(inBytes > 0)
				line.write(audioData, 0, inBytes);
			if(paused)
				waitforSignal();
		}
		line.drain();
		line.stop();
		line.close();
		playing = false;
		stopped = true;
		paused = false;
	}

	private SourceDataLine getLine(AudioFormat baseFormat) {
		// TODO Auto-generated method stub
		SourceDataLine res = null;
		DataLine.Info info = new DataLine.Info(SourceDataLine.class, baseFormat);
		try {
			res = (SourceDataLine) AudioSystem.getLine(info);
			res.open();
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			res = null;
		}
		return res;
	}

	public void setVolume()
    {
    	FloatControl volume;
		if(line!=null)
    	{
    		if(line.isControlSupported(FloatControl.Type.MASTER_GAIN))
         	{
        		MainView.volumeSlider.setEnabled(true);
        		volume= (FloatControl)line.getControl( FloatControl.Type.MASTER_GAIN );
        		MainView.volumeSlider.setMinimum((int)volume.getMinimum());
            	MainView.volumeSlider.setMaximum((int)volume.getMaximum());
            	//jSliderVolume.setValue((int)(volume.getMinimum()+(4*(volume.getMaximum()-volume.getMinimum()))/5));
            	volume.setValue((float)(volume.getMinimum()+(4*(volume.getMaximum()-volume.getMinimum()))/5));
         	}
    		System.out.println("1");
        }
        else
        {
         	volume=null;
         	MainView.volumeSlider.setEnabled(false);
        System.out.println("2");
        }	
    }
	
	private void waitforSignal() {
		// TODO Auto-generated method stub
		try{
			synchronized (synch) {
				synch.wait();
			}
		}catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void endThread(){
		threadExit = true;
		synchronized (synch) {
			synch.notifyAll();
		}
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void stop(){
		paused = false;
		stopped = true;
		waitForPlayToStop();
	}

	private void waitForPlayToStop() {
		// TODO Auto-generated method stub
		while(playing)
			try {
				Thread.sleep(500);
				synchronized (synch) {
					synch.notifyAll();
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	public void pause(){
		if(stopped)
			return;
		synchronized (synch) {
			paused = true;
			synch.notifyAll();
		}
	}
	
	public void resume(){
		if(stopped)
			return;
		synchronized (synch) {
			paused = false;
			synch.notifyAll();
		}
	}
	
	
}
