import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingWorker;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.awt.event.ActionEvent;

public class MainWindow {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
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
	public MainWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblBrojac = new JLabel("10");
		lblBrojac.setFont(new Font("Tahoma", Font.PLAIN, 40));
		lblBrojac.setBounds(176, 11, 89, 84);
		frame.getContentPane().add(lblBrojac);

		JLabel lblNewLabel = new JLabel("Status:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel.setBounds(23, 152, 128, 36);
		frame.getContentPane().add(lblNewLabel);

		JLabel lblStatus = new JLabel("Nije startorvano");
		lblStatus.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblStatus.setBounds(219, 149, 205, 42);
		frame.getContentPane().add(lblStatus);

		JButton btnBroj = new JButton("Broj");
		btnBroj.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				//Suvi poziv ka metodi sa sleep(), zamrzne
				//interfejs
				//odbrojavanje(lblStatus, lblBrojac);
				
				SwingWorker<String, Integer> radnik = new SwingWorker<String, Integer>() 
				{
					@Override
					protected String doInBackground() throws Exception 
					{   //Odavde nikada ne menjamo resurse koji su zajednicki
						for (int i = 10; i > 0; i--)
						{
							Thread.sleep(1000);
							System.out.println(String.valueOf(i));
							publish(i);
						}
						return "Zavrseno!";
					}
					
					@Override
					protected void process(List<Integer> x)
					{
						String broj = (x.get(x.size()-1)).toString();
						lblBrojac.setText(broj);
						lblStatus.setText("U toku");
					}
					
					
					protected void done()
					{
						String naKraju = "Nisam jos dobio nista :(";
						try 
						{             //get() ovde kupi vrednost koju smo dobili
							         //iz doInBackGround()
							naKraju = get();
						} catch (InterruptedException | ExecutionException e) {
							e.printStackTrace();
						}
						lblStatus.setText(naKraju);
					}
				};
				
				radnik.execute();
			}
		});
		btnBroj.setBounds(176, 228, 89, 23);
		frame.getContentPane().add(btnBroj);
	}

	public void odbrojavanje(JLabel stat, JLabel br)
	{
		stat.setText("U toku");
		for (int i = 10; i > 0; i--)
		{   
			try 
			{
				Thread.sleep(1000);
			} catch (InterruptedException e) 
			{

				e.printStackTrace();
			}
			br.setText(String.valueOf(i));
			System.out.println(i);
		}
		stat.setText("Zavrseno!");
	}
}
