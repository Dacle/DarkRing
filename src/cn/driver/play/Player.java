package cn.driver.play;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

import cn.gui.MainView;
/**
 * Mp3��ʽ֧��
 * @author Dacle
 * @since 2017-5-14
 * @modify by Dacle
 */
public abstract class Player implements Play{
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
	SourceDataLine sourceDataline = null;
	
	FloatControl volume = null;
	Object lock = new Object();

	@Override
	public void run() {
		// TODO Auto-generated method stub
		play();
	}
	
	private void play() {
		AudioInputStream input = getAudioInputStream();
		System.out.println("run play()  ");
		
    	AudioFormat format = input.getFormat();
		System.out.println("format "+format.toString());
    	DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
		try {
			sourceDataline =  (SourceDataLine) AudioSystem.getLine(info);
			sourceDataline.open();
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	setVolume();
    	
    	MainView.musicSlider.setMaximum((int)getTimeLength());
    	MainView.musicSlider.setValue(0);
    	if(sourceDataline!=null){
    		try {
    			System.out.println("��ǰ��Ƶ����ʽ:   "+format.toString());  
				sourceDataline.open(format);
    		} catch (LineUnavailableException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			byte[] abData = new byte[4096];
	    	int nBytesRead;
	    	System.out.println("���ſ�ʼ");
    		synchronized (lock){
					try {
						while((nBytesRead = input.read(abData, 0, abData.length))>0){
							while (paused) {
								if(sourceDataline.isRunning()){
									sourceDataline.stop();
									System.out.println("��ͣ");
								}
								lock.wait();//�������̵߳��ô˶����notify()����֮ǰ��ʹ��ǰ�̵߳ȴ�
								System.out.println("�ȴ�");
							}
							if(!sourceDataline.isRunning() && !over) {
								System.out.println("��ʼ����");
								sourceDataline.start();
							}
							if (over && sourceDataline.isRunning()) {
								System.out.println("ֹͣ����");
								MainView.musicSlider.setValue(0);
								sourceDataline.drain();
								sourceDataline.stop();
								sourceDataline.close();
							}
							MainView.musicSlider.setValue((int)sourceDataline.getMicrosecondPosition()/1000);
							sourceDataline.write(abData, 0, nBytesRead);
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
    		}
    		
    	}

	}

	public abstract AudioInputStream getAudioInputStream();
	
	public abstract double getTimeLength();
	
	private void setVolume(){

    	//������������
    	if(sourceDataline!=null){
    		if(sourceDataline.isControlSupported(FloatControl.Type.MASTER_GAIN)){
        		MainView.volumeSlider.setEnabled(true);
        		volume= (FloatControl)sourceDataline.getControl( FloatControl.Type.MASTER_GAIN );
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
