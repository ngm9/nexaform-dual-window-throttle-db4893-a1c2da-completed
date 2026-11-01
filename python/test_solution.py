from solution import throttle, OK, TOO_MANY


def test_worked_example():
    requests = [
        "www.xyz.com", "www.abc.com", "www.xyz.com", "www.pqr.com",
        "www.abc.com", "www.xyz.com", "www.xyz.com", "www.abc.com",
        "www.xyz.com",
    ]
    expected = [OK, OK, OK, OK, OK, OK, TOO_MANY, OK, OK]
    assert throttle(requests) == expected
