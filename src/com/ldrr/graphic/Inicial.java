package com.ldrr.graphic;

import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.ldrr.server.custom.ServerChat;
import com.ldrr.server.custom.ServerGame;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Color;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * All source code and required libraries are found at the following link:
 * https://github.com/lucasdiegorr/mastermind-socket 
 * branch: beta
 */

/**
 * @author Lucas Diego
 * 
 */

public class Inicial {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(ch.randelshofer.quaqua.QuaquaManager
					.getLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e1) {
			e1.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					Inicial window = new Inicial();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Inicial() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 560, 250);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblBemVindoAo = new JLabel("Bem vindo ao Jogo MasterMind");
		lblBemVindoAo.setForeground(Color.WHITE);
		lblBemVindoAo.setFont(new Font("Arial Black", Font.BOLD | Font.ITALIC,
				22));
		lblBemVindoAo.setBounds(61, 17, 422, 66);
		frame.getContentPane().add(lblBemVindoAo);

		JButton btnDesafiante = new JButton("Descobridor de Enigmas");
		btnDesafiante.setFont(new Font("Arial Black", Font.BOLD | Font.ITALIC,
				13));
		btnDesafiante.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				EventQueue.invokeLater(new Runnable() {
					@Override
					public void run() {
						try {
							new GameFrame(true);
							frame.setVisible(false);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}
		});
		btnDesafiante.setBounds(143, 159, 258, 30);
		frame.getContentPane().add(btnDesafiante);

		JButton btnDesafiado = new JButton("Mestre das Senhas");
		btnDesafiado.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				EventQueue.invokeLater(new Runnable() {
					@Override
					public void run() {
						try {
							
							//ServerChat chat = new ServerChat();
							ServerGame game = new ServerGame();
//							Naming.rebind("//127.0.0.1:5000/Chat", chat);
//							new Thread(chat).start();
							new Thread(game).start();
							new GameFrame(false);
							frame.setVisible(false);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}
		});
		btnDesafiado.setFont(new Font("Arial Black", Font.BOLD | Font.ITALIC,
				13));
		btnDesafiado.setBounds(174, 117, 196, 30);
		frame.getContentPane().add(btnDesafiado);

		JLabel lblQuemGostariaDe = new JLabel("Quem gostaria de ser?");
		lblQuemGostariaDe.setForeground(Color.WHITE);
		lblQuemGostariaDe.setFont(new Font("Arial Black", Font.PLAIN, 13));
		lblQuemGostariaDe.setBounds(187, 81, 169, 24);
		frame.getContentPane().add(lblQuemGostariaDe);
		
		JLabel label = new JLabel();
		label.setBounds(0, 0, 545, 210);
		label.setIcon(new ImageIcon(getClass().getResource("/gif-balls.gif")));
		frame.getContentPane().add(label);
	}
}
