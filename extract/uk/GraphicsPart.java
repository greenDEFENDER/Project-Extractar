package extract.uk;

import java.io.File;
import java.util.concurrent.CompletableFuture;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GraphicsPart extends JPanel implements ActionListener {
	
	private static final long serialVersionUID = 1L;
	static private final String newline = "\n";
	JButton openButton;
	JTextArea log;
	JFileChooser fc;
	static  GraphicsPart object=null;
	
	public static GraphicsPart getObj() {
		return object;
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					createAndShowGUI();
				} catch (Exception e) {
					object.log.append("ERROR OCURRED! please check your file size.");
					e.printStackTrace();
				}
			}
		});
		
	}

	/**
	 * Create the frame.
	 */
	public GraphicsPart() {
		super(new BorderLayout());
		log = new JTextArea(5, 20);
		log.setMargin(new Insets(5, 5, 5, 5));
		log.setEditable(false);
		
		JScrollPane logScrollPane = new JScrollPane(log);
		fc = new JFileChooser();
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		openButton = new JButton("Open a Folder");
		openButton.addActionListener(this);
	
		JPanel buttonPanel = new JPanel(); // use FlowLayout
		buttonPanel.add(openButton);
		
		
		add(buttonPanel, BorderLayout.PAGE_START);
		add(logScrollPane, BorderLayout.CENTER);
		
	}

	public	void actionPerformed(ActionEvent e) {
		
		if (e.getSource() == openButton) {
			int returnVal = fc.showOpenDialog(GraphicsPart.this);
			
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				log.append("Opening: " + file.getName() + "." + newline);
				
//				Extractar.extract(file);
				extractAsync(file);
				
			} else {
				log.append("Open command cancelled by user." + newline);
			}
		    log.setCaretPosition(log.getDocument().getLength());
		}
	}
	
	private CompletableFuture<Void> extractAsync(File file){
		return CompletableFuture.runAsync(()->Extractar.extract(file));
	}
	
	private static void createAndShowGUI() {
		//Create and set up the window.
		 JFrame frame = new JFrame("Extracter(uk)");

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//Add content to the window.
		object=new GraphicsPart();
		frame.add(object);
		//Display the window.
		frame.pack();
		frame.setVisible(true);
		
		}

}
