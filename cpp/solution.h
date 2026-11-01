#ifndef SOLUTION_H
#define SOLUTION_H

#include <string>
#include <vector>

// Nexaform per-domain dual-window request throttle.
//
// Each request arrives one per second; requests[i] is the domain of the
// request at time i. Enforce PER DOMAIN:
//   - at most 2 accepted requests in any trailing 5-second window
//   - at most 5 accepted requests in any trailing 30-second window
//
// For each request in order return:
//   - kOk       ("{status: 200, message: OK}")                if processable
//   - kTooMany  ("{status: 429, message: Too many requests}") if it violates a limit
//
// Only ACCEPTED requests count toward the limits. A rejected request is never
// recorded and must not affect any later decision. The returned vector has the
// same length as `requests`.

extern const std::string kOk;
extern const std::string kTooMany;

std::vector<std::string> throttle(const std::vector<std::string>& requests);

#endif  // SOLUTION_H
