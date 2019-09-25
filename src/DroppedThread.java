import javax.swing.*;

public class DroppedThread implements Runnable{

    private WordRecord[] words;
    private WordPanel w;
    private int noWords;
    private Score score;
    private JLabel dropped;
    private int totalWords;


    DroppedThread(WordRecord[] words,WordPanel w, Score score,JLabel dropped, int totalWords){
        this.words=words;
        this.w = w;
        noWords = words.length;
        this.score=score;
        this.dropped=dropped;
        this.totalWords = totalWords;
    }

    public void run() {
        while (true){
            while (!w.done) { 
                if (score.getTotal()>=totalWords) {
                    score.setGameStatus(true);
                    WordApp.done = true; //Add protected method                    
                }           
                for (int i=0;i<noWords;i++){	    	
                    if (words[i].dropped()) {
                        System.out.println(words[i].getWord()+" dropped");
                        score.missedWord();
                        dropped.setText("Missed:" + score.getMissed()+ "    ");
                        //words[i].resetWord();

                        //Set new word value
                        if ((totalWords - score.getTotal()-noWords) > -1) words[i].resetWord(); 
					    else {
                            words[i].resetWord();
                            words[i].setWord("");
                            words[i].setSpeed(0);

                        }
                            

                        if (score.getTotal()>=totalWords) {
                            score.setGameStatus(true);
                            WordApp.done = true; //Add protected method
                            //System.out.println("GameOver"); //Testing
                            break;
                        }
                    }
                }
    
                try {
                    Thread.sleep(10);
                } catch (Exception e) {
                    System.out.println("Error caught!");
                }				
            }
        }
        }
        

}