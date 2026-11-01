/*
 * Nexaform per-domain dual-window request throttle.
 *
 * Each request arrives one per second; requests[i] is the domain of the
 * request at time i. Enforce PER DOMAIN:
 *   - at most 2 accepted requests in any trailing 5-second window
 *   - at most 5 accepted requests in any trailing 30-second window
 *
 * For each request in order return:
 *   - OK       ("{status: 200, message: OK}")                if processable
 *   - TOO_MANY ("{status: 429, message: Too many requests}") if it violates a limit
 *
 * Only ACCEPTED requests count toward the limits. A rejected request is never
 * recorded and must not affect any later decision. The returned array has the
 * same length as `requests`.
 */

export const OK = '{status: 200, message: OK}';
export const TOO_MANY = '{status: 429, message: Too many requests}';

export function throttle(requests: string[]): string[] {
  throw new Error('throttle is not implemented yet');
}
