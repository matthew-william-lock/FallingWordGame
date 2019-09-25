import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;


import java.util.Scanner;
import java.util.concurrent.*;
//model is separate from the view.

public class WordApp {
//shared variables
	static int noWords=4;
	static int totalWords;

   	static int frameX=1000;
	static int frameY=600;
	static int yLimit=480;

	static WordDictionary dict = new WordDictionary(); //use default dictionary, to read from file eventually

	static WordRecord[] words;
	static volatile boolean done;  //must be volatile
	static 	Score score = new Score();

	static WordPanel w;	

	//Jlabels
	static JLabel missed;
	static JLabel scr;
	
	public static void setupGUI(int frameX,int frameY,int yLimit) {
		// Frame init and dimensions
    	JFrame frame = new JFrame("WordGame"); 
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	frame.setSize(frameX, frameY);
    	
      	JPanel g = new JPanel();
        g.setLayout(new BoxLayout(g, BoxLayout.PAGE_AXIS)); 
      	g.setSize(frameX,frameY);
 
    	
		w = new WordPanel(words,yLimit,score);
		w.setSize(frameX,yLimit+100);
	    g.add(w);
	    
	    
	    JPanel txt = new JPanel();
	    txt.setLayout(new BoxLayout(txt, BoxLayout.LINE_AXIS)); 
	    JLabel caught =new JLabel("Caught: " + score.getCaught() + "    ");
	    missed =new JLabel("Missed:" + score.getMissed()+ "    ");
	    scr =new JLabel("Score:" + score.getScore()+ "    ");    
	    txt.add(caught);
	    txt.add(missed);
	    txt.add(scr);
    
	    //[snip]
  
	    final JTextField textEntry = new JTextField("",20);
	   textEntry.addActionListener(new ActionListener()
	    {                  
	      public void actionPerformed(ActionEvent evt) {
	          String text = textEntry.getText();
	          //[snip]
	          textEntry.setText("");
			  textEntry.requestFocus();
			  System.out.println(text); //Testing

			  for (int i=0;i<noWords;i++){	    	
				if (text.equals(words[i].getWord())){
					score.caughtWord(words[i].getWord().length());
					caught.setText("Caught: " + score.getCaught() + "    ");
					scr.setText("Score:" + score.getScore()+ "    "); 
					if ((totalWords - score.getTotal()-noWords) > -1) words[i].resetWord(); 
					else {
						words[i].resetWord();
						words[i].setWord("");
						words[i].setSpeed(0);
					}
				}
			}
	      }
	    });
	   
	   txt.add(textEntry);
	   txt.setMaximumSize( txt.getPreferredSize() );
	   g.add(txt);
	    
	    JPanel b = new JPanel();
        b.setLayout(new BoxLayout(b, BoxLayout.LINE_AXIS)); 
	   	JButton startB = new JButton("Start");;
		
			// add the listener to the jbutton to handle the "pressed" event
			startB.addActionListener(new ActionListener()
		    {
		      public void actionPerformed(ActionEvent e)
		      {
				//Start GameThread 
				
				
				//System.out.println("press");
				//System.out.println(done);

				if(done==true){
					caught.setText("Caught: " + score.getCaught() + "    ");
					scr.setText("Score:" + score.getScore()+ "    "); 
					for (int i=0;i<noWords;i++){	    	
						words[i].resetWord();
					}
					score.resetScore();
					missed.setText("Missed:" + score.getMissed()+ "    ");
					caught.setText("Caught: " + score.getCaught() + "    ");
					scr.setText("Score:" + score.getScore()+ "    "); 
					done=false;
				}

				textEntry.requestFocus();  //return focus to the text entry field
		      }
		    });
		JButton endB = new JButton("End");;
			
				// add the listener to the jbutton to handle the "pressed" event
				endB.addActionListener(new ActionListener()
			    {
			      public void actionPerformed(ActionEvent e)
			      {
					  //[snip]
					score.resetScore();
					missed.setText("Missed:" + score.getMissed()+ "    ");
					caught.setText("Caught: " + score.getCaught() + "    ");
					scr.setText("Score:" + score.getScore()+ "    "); 
					done=true;	

					  // System.out.println("Press"); //Testing
			      }
				});

		JButton quitB = new JButton("Quit");;
			
				// add the listener to the jbutton to handle the "pressed" event
				quitB.addActionListener(new ActionListener()
			    {
			      public void actionPerformed(ActionEvent e)
			      {
					System.exit(0);
			      }
				});
				
				
		
		b.add(startB);
		b.add(endB);
		b.add(quitB);
		
		g.add(b);
    	
      	frame.setLocationRelativeTo(null);  // Center window on screen.
      	frame.add(g); //add contents to window
        frame.setContentPane(g);     
       	//frame.pack();  // don't do this - packs it into small space
        frame.setVisible(true);

		
	}

	
public static String[] getDictFromFile(String filename) {
		String [] dictStr = null;
		try {
			Scanner dictReader = new Scanner(new FileInputStream(filename));
			int dictLength = dictReader.nextInt();
			//System.out.println("read '" + dictLength+"'");

			dictStr=new String[dictLength];
			for (int i=0;i<dictLength;i++) {
				dictStr[i]=new String(dictReader.next());
				//System.out.println(i+ " read '" + dictStr[i]+"'"); //for checking
			}
			dictReader.close();
		} catch (IOException e) {
	        System.err.println("Problem reading file " + filename + " default dictionary will be used");
	    }
		return dictStr;

	}

	public static void main(String[] args) {
    	
		//deal with command line arguments
		totalWords=Integer.parseInt(args[0]);  //total words to fall
		noWords=Integer.parseInt(args[1]); // total words falling at any point
		assert(totalWords>=noWords); // this could be done more neatly
		String[] tmpDict=getDictFromFile(args[2]); //file of words
		if (tmpDict!=null)
			dict= new WordDictionary(tmpDict);
		
		WordRecord.dict=dict; //set the class dictionary for the words.
		
		words = new WordRecord[noWords];  //shared array of current words
		
		done=true; //Game is started in stopped state
		
		setupGUI(frameX, frameY, yLimit);  
		
		int x_inc=(int)frameX/noWords;
		  //initialize shared array of current words

		for (int i=0;i<noWords;i++) {
			words[i]=new WordRecord(dict.getNewWord(),i*x_inc,yLimit);
			System.out.println(i+" "+words[i].getWord()+" "+words[i].getSpeed());
		}

		//Start WordPanel thread - for redrawing animation
		Thread wordPanelThread = new Thread(w);
		wordPanelThread.start();

		//Start dropped thread
		DroppedThread dThread = new DroppedThread(words,w,score,missed,totalWords);
		Thread droppedThread = new Thread(dThread);
		droppedThread.start();

		//Start GameThread 
		Thread gameThread = new Thread(new GameThread(words,w,score,totalWords));
		gameThread.start(); 			
		


	}

}
