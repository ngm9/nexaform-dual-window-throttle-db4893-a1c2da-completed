import static org.junit.jupiter.api.Assertions.assertEquals;

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
}
