# Nexaform Dual-Window Per-Domain Throttle

## Task Overview

Nexaform's public API gateway receives up to 10,000 requests, one per second, and must throttle them **per domain** before they reach downstream services. The gateway has no throttle implemented yet — this repository ships a function stub in each of five languages plus a single failing worked-example test.

You are given a list `requests`, where one request arrives each second. The request at time `i` comes from the domain `requests[i]`. Enforce the following limits **per domain**:

- at most 2 successful requests in any 5-second window
- at most 5 successful requests in any 30-second window

For each request, return:

- `"{status: 200, message: OK}"` if the request can be processed
- `"{status: 429, message: Too many requests}"` if processing it would violate either limit

Only **accepted** requests are counted when checking the limits. A rejected request is never recorded and must not affect any later decision. The returned list has the same length as the input.

Both limits are trailing windows measured back from the current request, not fixed calendar buckets. A naive rescan of a domain's whole history on every request will not meet the time budget on a 10,000-request replay.

Implement the throttle in your chosen language.

## Examples

**Example 1**

**Input:**

```
requests = ["www.xyz.com", "www.abc.com", "www.xyz.com", "www.pqr.com",
            "www.abc.com", "www.xyz.com", "www.xyz.com", "www.abc.com",
            "www.xyz.com"]
```

**Output:**

```
[200, 200, 200, 200, 200, 200, 429, 200, 200]
```

(using the full status strings above)

**Explanation:**

| t | domain | result | why |
|---|--------|--------|-----|
| 0 | www.xyz.com | accepted | |
| 1 | www.abc.com | accepted | |
| 2 | www.xyz.com | accepted | |
| 3 | www.pqr.com | accepted | |
| 4 | www.abc.com | accepted | |
| 5 | www.xyz.com | accepted | |
| 6 | www.xyz.com | **rejected** | 2 requests from `www.xyz.com` were already accepted within the preceding 5 seconds |
| 7 | www.abc.com | accepted | |
| 8 | www.xyz.com | accepted | the second-2 accept has aged out of the 5-second window |

**Example 2**

**Input:** a single domain `a.com` requesting at seconds 0, 6, 12, 18, 24 and again at second 29 (all other seconds carry a different domain).

**Output:** the first five requests from `a.com` are accepted; the request at second 29 returns `429`.

**Explanation:** the requests are more than 5 seconds apart, so the 5-second limit is never reached. But by second 29 there are already 5 accepted requests from `a.com` inside the trailing 30-second window, so the sixth violates the 30-second limit. A further request at second 31 would be accepted again, because the second-0 accept has by then aged out of the 30-second window.

## Constraints

- `1 <= size of requests[] <= 10^4`
- `1 <= length of requests[i] <= 15`
- `requests[i]` consists of lowercase English letters and `.`

**Test Case Input Format**

The first line contains the integer `n`, the size of `requests[]`. The next `n` lines each contain one string element of `requests[]`.

## How to Verify

- Pick **one** language folder and edit only the `solution.*` stub inside it. Each folder is self-contained:

```
python/       pytest.ini, solution.py, test_solution.py
javascript/   package.json, solution.js, solution.test.js
typescript/   package.json, tsconfig.json, solution.ts, solution.test.ts
java/         pom.xml, src/main/java/Solution.java, src/test/java/SolutionTest.java
cpp/          Makefile, solution.h, solution.cpp, test_solution.cpp
```

- Run the tests for your chosen folder — the committed worked-example test fails until the throttle is implemented:
  - Python: `pytest` (from `python/`)
  - JavaScript: `npm install && npm test` (from `javascript/`)
  - TypeScript: `npm install && npm test` (from `typescript/`)
  - Java: `mvn test` (from `java/`)
  - C++: `make test` (from `cpp/`)
- Add a test where one domain trips the 30-second limit while never exceeding 2 accepts in any 5-second span, and confirm that request returns `429`.
- Add a test where a domain's rejected request is followed by an accept that must land as soon as the window frees up — verify the rejection did not delay it.
- Confirm empty and single-request inputs never raise and produce a correctly sized output list.
- Use only the standard library plus your folder's test framework — no third-party data-structure libraries.
- All tests in your chosen folder must pass with no failures.
