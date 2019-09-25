import java.awt.Color;
import java.awt.Graphics;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.CountDownLatch;

import javax.swing.JButton;
import javax.swing.JPanel;

public class WordPanel extends JPanel implements Runnable {
		public static volatile boolean done;
		private WordRecord[] words;
		private int noWords;
		private int maxY;
		private Score score;

		
		public void paintComponent(Graphics g) {
		    int width = getWidth();
		    int height = getHeight();
		    g.clearRect(0,0,width,height);
		    g.setColor(Color.red);
		    g.fillRect(0,maxY-10,width,height);

		    g.setColor(Color.black);
		    g.setFont(new Font("Helvetica", Font.PLAIN, 26));
		   //draw the words
		   //animation must be added 
			if (WordApp.done && !score.getGameStatus()){
				g.drawString("",0,0);
			}

		   else if (!score.getGameStatus()&&!WordApp.done) {
			for (int i=0;i<noWords;i++){	    	
		    	//g.drawString(words[i].getWord(),words[i].getX(),words[i].getY());	
				g.drawString(words[i].getWord(),words[i].getX(),words[i].getY()+20);  //y-offset for skeleton so that you can see the words	
				//System.out.println(words[i].getSpeed());
				//words[i].drop(words[i].getSpeed());
			}			   
		   }
		   else if (score.getGameStatus()){
			if(score.getMissed()==0){
				g.drawString("WINNER!",450,250);
				g.drawString("score : "+score.getScore(),450,270);

			}
			else{
				g.drawString("GAME OVER",450,250);
				g.drawString("score : "+score.getScore(),450,270);
			}
			
		   }		
		   
		  }
		
		WordPanel(WordRecord[] words, int maxY, Score score) {
			this.words=words; //will this work?
			noWords = words.length;
			done=false;
			this.maxY=maxY;	
			this.score = score;	
		}
		
		public void run() {
			while (true){
				while (!done) {
					//System.out.println("Running");
					repaint();
					try {
						Thread.sleep(10);
					} catch (Exception e) {
						System.out.println("Error caught!");
					}				
				}
			}
			
		}

	}


