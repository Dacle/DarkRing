package cn.driver.showWave;
     
import javax.sound.sampled.AudioFormat;  
import javax.sound.sampled.AudioSystem;  
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;  
/* 
 * WaveIn.java 
 * ������Ƶ��� 
 */ 
public class WaveIn {  
    private AudioFormat af;  
    private DataLine.Info dli;  
    private TargetDataLine tdl;  
  
    /** 
     * ����ƵĿ�������С����ж�ȡ��Ƶ���ݸ�ʽΪ��������32kHz��ÿ������16λ�����������з��ŵģ�little-endian�� 
     * @return �ɹ��򿪷���true������false�� 
     */  
    public boolean open() {  
        af = new AudioFormat(32000, 16, 1, true, false);  
        dli = new DataLine.Info(SourceDataLine.class, af);  
        try {  
            tdl = (TargetDataLine) AudioSystem.getLine(dli);  
            tdl.open(af, FFT.FFT_N << 1);  
        } catch (Exception e) {  
            e.printStackTrace();  
            return false;  
        }  
      
        return true;  
   }  
      
    public void close() {  
        tdl.close();  
    }  
      
    public void start() {  
        tdl.start();  
    }  
      
    public void stop() {  
        tdl.stop();  
    }  
      
    public int read(byte[] b, int len) {  
        return tdl.read(b, 0, len);  
    }  
      
    private double phase0 = 0;  
    /** 
     * ����Ƶ��264Hz��������Ϊ44.1kHz����ֵΪ0x7fff��ÿ������16λ��PCM�� 
     * @param b ����PCM������ 
     * @param len PCM�����ֽ����� 
     */  
    public void getWave264(byte[] b, int len) {  
    	double dt = 2 * 3.14159265358979323846 * 264 / 44100;  
        int i, pcmi;  
        len >>= 1;  
        for (i = 0; i < len; i++) {  
            pcmi = (short) (0x7fff * Math.sin(i * dt + phase0));  
            b[2 * i] = (byte) pcmi;  
            b[2 * i + 1] = (byte) (pcmi >>> 8);  
        }  
        phase0 += i * dt;  
    }  
}  