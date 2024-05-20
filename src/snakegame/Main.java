/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package snakegame;
import java.awt.Color;
import javax.swing.JFrame;
public class Main{

public static void main (String[] args)
{
	JFrame frame = new JFrame();
	frame.setBounds(10,10,910,700);
	frame.setResizable(false);
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	
	GamePanel panel=new GamePanel();
	panel.setBackground(Color.DARK_GRAY);
	frame.add(panel);
		
		frame.setVisible(true);
	}
}
