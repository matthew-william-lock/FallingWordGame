import javax.swing.*;

public class DroppedThread implements Runnable{

    private WordRecord[] words;
    private WordPanel w;
    private int noWords;
    private Score score;
    JLabel dropped;


    DroppedThread(WordRecord[] words,WordPanel w, Score score,JLabel dropped){
        this.words=words;
        this.w = w;
        noWords = words.length;
        this.score=score;
        this.dropped=dropped;
    }

    public void run() {
        while (!w.done) {
            
            for (int i=0;i<noWords;i++){	    	
		    	if (words[i].dropped()) {
                    System.out.println(words[i].getWord()+" dropped");
                    score.missedWord();
                    dropped.setText("Missed:" + score.getMissed()+ "    ");
                    words[i].resetWord();
                }
			}

            try {
                Thread.sleep(100);
            } catch (Exception e) {
                System.out.println("Error caught!");
            }				
        }
    }

}