public class GameThread implements Runnable{

    private WordRecord[] words;
    private WordPanel w;
    private int noWords;
    private Score score;
    private int totalWords;

    GameThread(WordRecord[] words,WordPanel w,Score score, int totalWords){
        this.words=words;
        this.w = w;
        noWords = words.length;
        this.score=score;
        this.totalWords = totalWords;
    }

    public void run() {
        while (!w.done) {   
            //System.out.println("Game thread running");  
            //System.out.println("Total: " + score.getTotal()+" "+totalWords); //Testing
            if (score.getTotal()>=totalWords) {
                System.out.println("GameOver");
            }

            for (int i=0;i<noWords;i++){	    	
                words[i].drop(words[i].getSpeed());
                //words[i].drop(3); //Testing
            }
            
            
            try {
                Thread.sleep(100);
            } catch (Exception e) {
                System.out.println("Error caught!");
            }				
        }
    }

}