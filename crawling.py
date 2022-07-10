import requests
import json
url = "https://priceline-com-provider.p.rapidapi.com/v2/flight/departures"

querystring = {"departure_date":"2022-02-06","sid":"iSiX639","adults":"1","origin_airport_code":"IST","cabin_class":"first","destination_city_id":"NYC"}

headers = {
    'x-rapidapi-host': "priceline-com-provider.p.rapidapi.com",
    'x-rapidapi-key': "a17d6697f9msh416ba0f0266c920p1a2803jsne3d403bd5310"
    }

response = requests.request("GET", url, headers=headers, params=querystring)


data = json.loads(response.content)  
with open('flights.json', 'w', encoding='utf-8') as f:
    json.dump(data, f, ensure_ascii=False, indent=4)