import { throttle, OK, TOO_MANY } from './solution';

test('worked example', () => {
  const requests = [
    'www.xyz.com', 'www.abc.com', 'www.xyz.com', 'www.pqr.com',
    'www.abc.com', 'www.xyz.com', 'www.xyz.com', 'www.abc.com',
    'www.xyz.com',
  ];
  const expected = [OK, OK, OK, OK, OK, OK, TOO_MANY, OK, OK];
  expect(throttle(requests)).toEqual(expected);
});
