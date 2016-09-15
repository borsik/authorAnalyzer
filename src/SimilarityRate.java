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
                    diff += (words[i].get(key) - words[k].get(key))*(words[i].get(key) - words[k].get(key));
                }
            }

        }
        return Math.sqrt((double)2 / (double)(n * m * (n - 1)) * (diff));
    }

    public static HashMap<String, Integer> averageWord(HashMap<String, Integer>[] words) {
        Set<String> keys = words[0].keySet();
        HashMap<String, Integer> averageWordCount = new HashMap<>();
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

    public static double similarRatioOther(HashMap<String, Integer> average, HashMap<String, Integer> other) {
        Set<String> keys = average.keySet();
        int sum = 0;
        for (String key : keys) {
            sum += Math.pow(average.get(key) - other.get(key), 2);
        }
        return Math.sqrt((double)1 / (double)keys.size() * (sum));
    }

}
