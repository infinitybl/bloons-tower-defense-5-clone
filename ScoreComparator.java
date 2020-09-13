/* ScoreComparator.java
   Phillip Pham
   ScoreComparator class used to help sort the scoresList arrayList in MainGame using
   Collections.sort() 
   Got help from http://forum.codecall.net/topic/50071-making-a-simple-high-score-system/
 */
 
import java.util.*;
import java.util.Comparator;

public class ScoreComparator implements Comparator<Score> {
	@Override
    public int compare(Score firstScore, Score otherScore) {
		//return -1 if the otherScore is less (firstScore put last)
        if (otherScore.highestRound < firstScore.highestRound) {
    		return -1;
    	}
    	//return 1 if the otherScore is greater (firstScore put first)
    	if (otherScore.highestRound > firstScore.highestRound) {
    		return 1;
   		}
   		//return 0 if the scores are the same
    	return 0; 
    }
}