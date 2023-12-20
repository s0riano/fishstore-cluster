from typing import List, Dict


def get_user_data(userid: int) -> List[Dict]:
    # Test data with location for each date
    test_data = {
        1: [
            {'date': '2023-11-24', 'openTime': '09:00', 'closeTime': '17:00', 'latitude': 59.91, 'longitude': 10.75},
            {'date': '2023-11-25', 'openTime': '09:00', 'closeTime': '17:00', 'latitude': 59.92, 'longitude': 10.76}
        ],
        2: [
            {'date': '2023-11-24', 'openTime': '10:00', 'closeTime': '18:00', 'latitude': 60.00, 'longitude': 11.00},
            {'date': '2023-11-29', 'openTime': '10:00', 'closeTime': '18:00', 'latitude': 60.01, 'longitude': 11.01}
        ],
        3: [
            {'date': '2023-11-24', 'openTime': '08:00', 'closeTime': '16:00', 'latitude': 59.50, 'longitude': 10.40},
            {'date': '2023-11-30', 'openTime': '08:00', 'closeTime': '16:00', 'latitude': 59.51, 'longitude': 10.41}
        ]
    }

    return test_data.get(userid, [])



'''
     change to an api call for the real shop details for the seller
'''