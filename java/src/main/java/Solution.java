import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Nexaform per-domain dual-window request throttle.
 *
 * <p>Each request arrives one per second; requests.get(i) is the domain of the
 * request at time i. Enforce PER DOMAIN:
 * <ul>
 *   <li>at most 2 accepted requests in any trailing 5-second window</li>
 *   <li>at most 5 accepted requests in any trailing 30-second window</li>
 * </ul>
 *
 * <p>For each request in order return:
 * <ul>
 *   <li>{@link #OK} ("{status: 200, message: OK}") if it can be processed</li>
 *   <li>{@link #TOO_MANY} ("{status: 429, message: Too many requests}") if it
 *       would violate a limit</li>
 * </ul>
 *
 * <p>Only ACCEPTED requests count toward the limits. A rejected request is never
 * recorded and must not affect any later decision. The returned list has the
 * same length as {@code requests}.
 */
public class Solution {

    public static final String OK = "{status: 200, message: OK}";
    public static final String TOO_MANY = "{status: 429, message: Too many requests}";

    public List<String> throttle(List<String> requests) {
        Map<String, Deque<Integer>> acceptedByDomain = new HashMap<>();
        List<String> responses = new ArrayList<>(requests.size());

        for (int currentTime = 0; currentTime < requests.size(); currentTime++) {
            String domain = requests.get(currentTime);
            Deque<Integer> acceptedTimes = acceptedByDomain.computeIfAbsent(domain, ignored -> new ArrayDeque<>());

            int thirtySecondWindowStart = currentTime - 29;
            while (!acceptedTimes.isEmpty() && acceptedTimes.peekFirst() < thirtySecondWindowStart) {
                acceptedTimes.pollFirst();
            }

            int acceptedInLastThirtySeconds = acceptedTimes.size();
            int acceptedInLastFiveSeconds = 0;
            int fiveSecondWindowStart = currentTime - 4;
            for (int acceptedTime : acceptedTimes) {
                if (acceptedTime >= fiveSecondWindowStart) {
                    acceptedInLastFiveSeconds++;
                }
            }

            boolean exceedsFiveSecondLimit = acceptedInLastFiveSeconds >= 2;
            boolean exceedsThirtySecondLimit = acceptedInLastThirtySeconds >= 5;

            if (exceedsFiveSecondLimit || exceedsThirtySecondLimit) {
                responses.add(TOO_MANY);
                continue;
            }

            acceptedTimes.addLast(currentTime);
            responses.add(OK);
        }

        return responses;
    }
}
