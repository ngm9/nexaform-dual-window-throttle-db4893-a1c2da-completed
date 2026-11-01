import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class SolutionTest {

    @Test
    void workedExample() {
        Solution solution = new Solution();
        List<String> requests = List.of(
            "www.xyz.com", "www.abc.com", "www.xyz.com", "www.pqr.com",
            "www.abc.com", "www.xyz.com", "www.xyz.com", "www.abc.com",
            "www.xyz.com");
        List<String> expected = List.of(
            Solution.OK, Solution.OK, Solution.OK, Solution.OK, Solution.OK,
            Solution.OK, Solution.TOO_MANY, Solution.OK, Solution.OK);
        assertEquals(expected, solution.throttle(requests));
    }

    @Test
    void tripsOnlyThirtySecondLimit() {
        Solution solution = new Solution();
        List<String> requests = new ArrayList<>();
        for (int second = 0; second < 30; second++) {
            if (second == 0 || second == 6 || second == 12 || second == 18 || second == 24 || second == 29) {
                requests.add("www.xyz.com");
            } else {
                requests.add("other-" + second);
            }
        }

        List<String> expected = new ArrayList<>();
        for (int second = 0; second < 30; second++) {
            if (second == 29) {
                expected.add(Solution.TOO_MANY);
            } else {
                expected.add(Solution.OK);
            }
        }

        assertEquals(expected, solution.throttle(requests));
    }

    @Test
    void rejectedRequestDoesNotDelayLaterAccept() {
        Solution solution = new Solution();
        List<String> requests = List.of(
            "www.xyz.com", // t=0 accept
            "www.xyz.com", // t=1 accept
            "www.xyz.com", // t=2 reject (5-second limit)
            "other-3",     // t=3 accept
            "other-4",     // t=4 accept
            "www.xyz.com"  // t=5 should accept because t=2 was rejected and not recorded
        );

        List<String> expected = List.of(
            Solution.OK,
            Solution.OK,
            Solution.TOO_MANY,
            Solution.OK,
            Solution.OK,
            Solution.OK
        );

        assertEquals(expected, solution.throttle(requests));
    }

    @Test
    void handlesEmptyInput() {
        Solution solution = new Solution();
        assertEquals(List.of(), solution.throttle(List.of()));
    }

    @Test
    void handlesSingleRequestInput() {
        Solution solution = new Solution();
        assertEquals(List.of(Solution.OK), solution.throttle(List.of("www.xyz.com")));
    }
}
