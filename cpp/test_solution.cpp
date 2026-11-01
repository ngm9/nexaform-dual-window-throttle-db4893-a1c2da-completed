#include <cassert>
#include <string>
#include <vector>

#include "solution.h"

static void test_worked_example() {
    std::vector<std::string> requests = {
        "www.xyz.com", "www.abc.com", "www.xyz.com", "www.pqr.com",
        "www.abc.com", "www.xyz.com", "www.xyz.com", "www.abc.com",
        "www.xyz.com"};
    std::vector<std::string> expected = {
        kOk, kOk, kOk, kOk, kOk, kOk, kTooMany, kOk, kOk};
    assert(throttle(requests) == expected);
}

int main() {
    test_worked_example();
    return 0;
}
