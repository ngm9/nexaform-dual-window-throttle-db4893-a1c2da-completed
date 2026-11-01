"""Nexaform per-domain dual-window request throttle.

Each request arrives one per second; requests[i] is the domain of the
request at time i. Enforce PER DOMAIN:
  * at most 2 accepted requests in any trailing 5-second window
  * at most 5 accepted requests in any trailing 30-second window

Return, for each request in order:
  * "{status: 200, message: OK}"                if it can be processed
  * "{status: 429, message: Too many requests}" if it would violate a limit

Only ACCEPTED requests count toward the limits. A rejected request is never
recorded and must not affect any later decision. The returned list has the
same length as `requests`.
"""
from typing import List

OK = "{status: 200, message: OK}"
TOO_MANY = "{status: 429, message: Too many requests}"


def throttle(requests: List[str]) -> List[str]:
    """Return the per-request status list. NOT YET IMPLEMENTED."""
    raise NotImplementedError("throttle is not implemented yet")
