import java.util.HashMap;
import java.util.Set;

/**
 * Created by laptop on 13.09.2016.
 */
public class SimilarityRate {
    HashMap<String, Integer>[] ubiquityWords;

    public static double similarRatio(HashMap<String, Integer>[] words) {
        Set<String> keys = words[0].keySet();
        int diff = 0;
        int n = words.length;
        int m = words[0].size();
        for (String key : keys) {
            for (int i = 0; i < n - 1; i++) {
                for (int k = i + 1; k < n; k++) {
                    diff += words[i].get(key) - words[k].get(key);
                }
            }

        }
        return 2 / (n * m * (n - 1)) * Math.sqrt(diff * diff);
    }

    public static HashMap<String, Integer> averageWord(HashMap<String, Integer>[] words) {
        Set<String> keys = words[0].keySet();
        HashMap<String, Integer> averageWordCount = new HashMap<>();
        int m = words[0].size();
        int n = words.length;
        for (String key: keys) {
            int sum = 0;
            for (int i = 0; i < n; i++) {
                sum += words[i].get(key);
            }
            averageWordCount.put(key, sum / n);
        }
        return averageWordCount;
    }




}
