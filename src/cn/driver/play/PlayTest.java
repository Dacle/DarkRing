package cn.driver.play;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

import org.kc7bfi.jflac.apps.SeekTablePlayer;
import org.kc7bfi.jflac.util.ByteData;

import cn.gui.MainView;
/**
 * Mp3��ʽ֧��
 * @author Dacle
 * @since 2017-5-14
 * @modify by Dacle
 */
public abstract class PlayTest implements Runnable{
	/**
	 * ��ͣ��ǣ����ڽ���ͨ�ſ���
	 */
	boolean paused = false;
	/**
	 * ֹͣ���
	 */
	boolean over = true;
	/**
	 * �������ݿ���
	 */
	SourceDataLine dataline = null;
	
	FloatControl volume = null;
	Object lock = new Object();

	@Override
	public void run() {
		// TODO Auto-generated method stub
		play();
	}
	
	private void play() {
		AudioInputStream input = getAudioInputStream();
		System.out.println(input.getFormat().toString());
		
    	AudioFormat format = input.getFormat();
    	DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
		try {
			dataline =  (SourceDataLine) AudioSystem.getLine(info);
		
		dataline.open();
    	
    	setVolume();
    	
    	MainView.musicSlider.setMaximum((int)getTimeLength());
    	MainView.musicSlider.setValue(0);
    	SeekTablePlayer stp = new SeekTablePlayer();
    	if(dataline!=null){
			dataline.open(format);
			byte[] abData = new byte[4096];
			ByteData bd = new ByteData(4);
	    	int nBytesRead = 0;
	    	System.out.println("���ſ�ʼ");
    		synchronized (lock){
    			while((nBytesRead = input.read(abData, 0, abData.length)) != -1){
    				while (paused) {
						if(dataline.isRunning()){
							dataline.stop();
							System.out.println("��ͣ");
						}
						lock.wait();//�������̵߳��ô˶����notify()����֮ǰ��ʹ��ǰ�̵߳ȴ�
						System.out.println("�ȴ�");
					}
    				if(!dataline.isRunning() && !over) {
						System.out.println("��ʼ����");
						dataline.start();
					}
					if (over && dataline.isRunning()) {
						System.out.println("ֹͣ����");
						MainView.musicSlider.setValue(0);
						dataline.drain();
						dataline.stop();
						dataline.close();
					}
					MainView.musicSlider.setValue((int)dataline.getMicrosecondPosition());
    				if (nBytesRead >= 0)
    					stp.processPCM(bd);;
    			}
    		}
    	}} catch (LineUnavailableException | IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public abstract AudioInputStream getAudioInputStream();
	
	public abstract double getTimeLength();
	
	private void setVolume(){

    	//������������
    	if(dataline!=null){
    		if(dataline.isControlSupported(FloatControl.Type.MASTER_GAIN)){
        		MainView.volumeSlider.setEnabled(true);
        		volume= (FloatControl)dataline.getControl( FloatControl.Type.MASTER_GAIN );
        		MainView.volumeSlider.setMinimum((int)volume.getMinimum());
        		MainView.volumeSlider.setMaximum((int)volume.getMaximum());
            	volume.setValue((float)(volume.getMinimum()+(4*(volume.getMaximum()-volume.getMinimum()))/5));
         	}
    		System.out.println("1");
        }else{
         	volume=null;
         	MainView.volumeSlider.setEnabled(false);
         	System.out.println("2");
        }
    	//���ý�������ʼֵ
    	
	}
	
	public void stop() {
		// TODO Auto-generated method stub
		over = true;
	}

	public void carryOn() {
		// TODO Auto-generated method stub
		synchronized(lock) {
			  paused = false;
			  lock.notifyAll();
		}
	}

	public void paused() {
		// TODO Auto-generated method stub
		 paused = true;
	}

}
