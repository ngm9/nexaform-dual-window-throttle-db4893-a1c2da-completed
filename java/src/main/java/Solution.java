import java.util.List;

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
        throw new UnsupportedOperationException("throttle is not implemented yet");
    }
}
