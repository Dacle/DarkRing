package cn.driver.showWave;
/*
 * Spectrum.java
 * Ƶ����ʾ
 * http://jmp123.sf.net/
 */
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;

public class Spectrum extends JComponent implements Runnable {
	private static final long serialVersionUID = 1L;
	private static final int maxColums = 128;
	private static final int Y0 = 1 << ((FFT.FFT_N_LOG + 3) << 1);
	private static final double logY0 = Math.log10(Y0); //lg((8*FFT_N)^2)
	private int band;
	private int width, height;
	private int[] xplot, lastPeak, lastY;
	private int deltax;
	private long lastTimeMillis;
	private BufferedImage spectrumImage, barImage;
	private Graphics spectrumGraphics;
	private boolean isAlive;

	public Spectrum() {
		isAlive = true;
		band = 64;		//64��
		width = 383;	//Ƶ�״��� 383x124
		height = 124;
		lastTimeMillis = System.currentTimeMillis();
		xplot = new int[maxColums + 1];
		lastPeak = new int[maxColums];
		lastY = new int[maxColums];
		spectrumImage = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
		spectrumGraphics = spectrumImage.getGraphics();
		setPreferredSize(new Dimension(width, height));
		setPlot();
		barImage = new BufferedImage(deltax - 1, height, BufferedImage.TYPE_3BYTE_BGR);

		setColor(0x7f7f7f, 0xff0000, 0xffff00, 0x7f7fff);
	}

	public void setColor(int rgbPeak, int rgbTop, int rgbMid, int rgbBot) {
		Color crPeak = new Color(rgbPeak);
		spectrumGraphics.setColor(crPeak);

		spectrumGraphics.setColor(Color.gray);
		Graphics2D g = (Graphics2D)barImage.getGraphics();
		Color crTop = new Color(rgbTop);
		Color crMid = new Color(rgbMid);
		Color crBot = new Color(rgbBot);
		GradientPaint gp1 = new GradientPaint(0, 0, crTop,deltax - 1,height/2,crMid);
		g.setPaint(gp1);
		g.fillRect(0, 0, deltax - 1, height/2);
		GradientPaint gp2 = new GradientPaint(0, height/2, crMid,deltax - 1,height,crBot);
		g.setPaint(gp2);
		g.fillRect(0, height/2, deltax - 1, height);
		gp1 = gp2 = null;
		crPeak = crTop = crMid = crBot = null;
	}

	private void setPlot() {
		deltax = (width - band + 1) / band + 1;

		// 0-16kHz�ֻ�Ϊband��Ƶ�Σ���Ƶ�ο�ȷ����Ի��֡�
		for (int i = 0; i <= band; i++) {
			xplot[i] = 0;
			xplot[i] = (int) (0.5 + Math.pow(FFT.FFT_N >> 1, (double) i	/ band));
			if (i > 0 && xplot[i] <= xplot[i - 1])
				xplot[i] = xplot[i - 1] + 1;
		}
	}

	/**
	 * ����"Ƶ��-��ֵ"ֱ��ͼ����ʾ����Ļ��
	 * @param amp amp[0..FFT.FFT_N/2-1]ΪƵ��"��ֵ"(�ø���ģ��ƽ��)��
	 */
	private void drawHistogram(float[] amp) {
		spectrumGraphics.clearRect(0, 0, width, height);

		long t = System.currentTimeMillis();
		int speed = (int)(t - lastTimeMillis) / 30;	//��ֵ�����ٶ�
		lastTimeMillis = t;

		int i = 0, x = 0, y, xi, peaki, w = deltax - 1;
		float maxAmp;
		for (; i != band; i++, x += deltax) {
			// ���ҵ�ǰƵ�ε����"��ֵ"
			maxAmp = 0; xi = xplot[i]; y = xplot[i + 1];
			for (; xi < y; xi++) {
				if (amp[xi] > maxAmp)
					maxAmp = amp[xi];
			}

			/**
			 * maxAmpת��Ϊ�ö�����ʾ��"�ֱ���"y:
			 * y = (int) Math.sqrt(maxAmp);
			 * y /= FFT.FFT_N; //��ֵ
			 * y /= 8;	//����
			 * if(y > 0) y = (int)(Math.log10(y) * 20 * 2);
			 * 
			 * Ϊ��ͻ����ֵy��ʾʱǿ����"�Աȶ�"������ʱ���˵�����δ�������������
			 */
			y = (maxAmp > Y0) ? (int) ((Math.log10(maxAmp) - logY0) * 20) : 0;

			// ʹ��ֵ���ٶ�����
			lastY[i] -= speed << 2;
			if(y < lastY[i]) {
				y = lastY[i];
				if(y < 0) y = 0;
			}
			lastY[i] = y;

			if(y >= lastPeak[i]) {
				lastPeak[i] = y;
			} else {
				// ʹ��ֵ���ٶ�����
				peaki = lastPeak[i] - speed;
				if(peaki < 0)
					peaki = 0;
				lastPeak[i] = peaki;
				peaki = height - peaki;
				spectrumGraphics.drawLine(x, peaki, x + w - 1, peaki);
			}

			// ����ǰƵ�ε�ֱ��ͼ
			y = height - y;
			spectrumGraphics.drawImage(barImage, x, y, x+w, height, 0, y, w, height, null);
		}

		// ˢ�µ���Ļ
		repaint(0, 0, width, height);
	}

	public void paintComponent(Graphics g) {
		g.drawImage(spectrumImage, 0, 0, null);
	}

	public void run() {
		WaveIn wi = new WaveIn();
		wi.open();
		wi.start();

		FFT fft = new FFT();
		byte[] b = new byte[FFT.FFT_N << 1];
		float realIO[] = new float[FFT.FFT_N];
		int i, j;

		try {
			while (isAlive) {
				Thread.sleep(80);// ��ʱ��׼ȷ,�ⲻ��Ҫ

				// �ӻ�����¼�����ݲ�ת��Ϊshort���͵�PCM
				wi.write(b, FFT.FFT_N << 1);
				//wi.getWave264(b, FFT.FFT_N << 1);//debug
				for (i = j = 0; i != FFT.FFT_N; i++, j += 2)
					realIO[i] = (b[j + 1] << 8) | (b[j] & 0xff); //signed short

				// ʱ��PCM���ݱ任��Ƶ��,ȡ��Ƶ���ֵ
				fft.calculate(realIO);

				// ����
				drawHistogram(realIO);
			}

			wi.close();
		} catch (InterruptedException e) {
			// e.printStackTrace();
		}
	}

	public void stop() {
		isAlive = false;
	}
}