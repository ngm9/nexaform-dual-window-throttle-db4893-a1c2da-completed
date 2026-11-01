#include "solution.h"

#include <stdexcept>

const std::string kOk = "{status: 200, message: OK}";
const std::string kTooMany = "{status: 429, message: Too many requests}";

std::vector<std::string> throttle(const std::vector<std::string>& requests) {
    throw std::logic_error("throttle is not implemented yet");
}
