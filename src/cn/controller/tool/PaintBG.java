package cn.controller.tool;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

/**
 * ¼Ì³Ð×ÔJPanel£¬»­²¼Àà
 * @author Dacle
 * 
 */
public class PaintBG extends JPanel{
	
	private static final long serialVersionUID = 1L;
	
	Image im;  
	
    public PaintBG(Image im)  
    {  
        this.im=im;  
        this.setOpaque(true);  
    }  
    //Draw the back ground.  
    public void paintComponent(Graphics g)
    {  
        super.paintComponents(g);  
        g.drawImage(im,0,0,this.getWidth(),this.getHeight(),this);  
          
    }  
}
