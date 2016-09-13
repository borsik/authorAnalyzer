import java.util.HashMap;
import java.util.Set;

/**
 * Created by laptop on 13.09.2016.
 */
public class SimilarityRate {
    HashMap<String, Integer>[] ubiquityWords;

    public static double similarityRate(HashMap<String, Integer>[] ubiquityWords) {
        Set<String> uniqueKeys = ubiquityWords[0].keySet();
        int diff = 0;
        double g;
        int n = ubiquityWords.length;
        int m = ubiquityWords[0].size();
        for (String uniqueKey : uniqueKeys) {
            for (int i = 0; i < n - 1; i++) {
                for (int k = i + 1; k < n; k++) {
                    diff = ubiquityWords[i].get(uniqueKey) - ubiquityWords[k].get(uniqueKey);
                }
            }

        }
        g = 2 / (n * m * (n - 1)) * Math.sqrt(diff * diff);
        return g;
    }
}
