package cn.controller.tool;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

/**
 * 继承自JPanel，画布类
 * @author Dacle
 * 
 */
public class PaintJPanel extends JPanel{
	
	private static final long serialVersionUID = 1L;
	
	private Image im;  
	
    public PaintJPanel(Image im)  
    {  
        this.im=im;  
        this.setOpaque(true);  
    }
    
    public PaintJPanel(){
    	this.setOpaque(true);
    } 
    /**
     * 为当前JPanel对象设置背景图片
     * @param im
     */
    public void setBGP(Image im){
    	this.im=im;
    }
    public void paintComponent(Graphics g)
    {  
        super.paintComponents(g);  
        g.drawImage(im,0,0,this.getWidth(),this.getHeight(),this);  
          
    }  
}
