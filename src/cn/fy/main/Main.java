package cn.fy.main;

import java.awt.Dimension;

import javax.swing.JFrame;

import cn.fy.view.Window;

/**
 * @author 锋宇
 * @contact QQ群399643539
 * @website http://www.wolfbe.com
 * @copyright 版权归朗度云所有
 */
public class Main {

	public static void main(String[] args) {
		Window win=new Window();
		win.initView();
		win.setPreferredSize(new Dimension(300 , 360));
		win.setTitle("俄罗斯方块[©版本朗度云所有]");
		win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		win.setResizable(false); //去掉最大化按钮
		win.pack();	//获得最佳大小
		win.setVisible(true);
	}
}
