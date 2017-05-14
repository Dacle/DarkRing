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
 * Mp3��ʽ֧��
 * @author Dacle
 * @since 2017-5-14
 * @modify by Dacle
 */
public class Mp3Support implements Play{
	/**
	 * �����ļ�·��
	 */
	String filepath;
	/**
	 * �����ڵ�ǰ���ָ�ʽ��player����
	 */
	Player play;
	/**
	 * ��ͣ��ǣ����ڽ���ͨ�ſ���
	 */
	boolean paused = false;
	/**
	 * ֹͣ���
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
		
		//��ȡ��������
    	AudioFormat format = input.getFormat();
    	AudioFormat decodedFormat = new AudioFormat(
				AudioFormat.Encoding.PCM_SIGNED,
				format.getSampleRate(), 16, format.getChannels(),
				format.getChannels() * 2, format.getSampleRate(),
				false);
    	if(format.getEncoding()==AudioFormat.Encoding.PCM_UNSIGNED || format.getEncoding()==AudioFormat.Encoding.ULAW ||format.getEncoding()==AudioFormat.Encoding.ALAW || format.getEncoding()==AudioFormat.Encoding.PCM_SIGNED){
             time=(file.length()*8000000)/((int)(decodedFormat.getSampleRate()*format.getSampleSizeInBits()));
             System.out.println("ʱ��"+time);
        }else{
            int bitrate=0;
            if(format.properties().get("bitrate")!=null){
                //ȡ�ò����ٶ�(��λλÿ��)
                bitrate=(int)((Integer)(format.properties().get("bitrate")));
                if(bitrate!=0)
                time=(file.length()*8000000)/bitrate;
                System.out.println("ʱ��2"+time);
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
    	
    	//������������
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
    	//���ý�������ʼֵ
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
							System.out.println("��ͣ");
						}
						try {
							lock.wait();//�������̵߳��ô˶����notify()����֮ǰ��ʹ��ǰ�̵߳ȴ�
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
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
