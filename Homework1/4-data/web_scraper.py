from selenium import webdriver
from selenium.webdriver.common.keys import Keys
import time
import pandas as pd

PATH = "C:\Program Files (x86)\chromedriver.exe"
driver = webdriver.Chrome(PATH)

driver.get("https://www.google.com/maps")

search = driver.find_element_by_id("searchboxinput")

cities = ["Skopje", "Bitola", "Ohrid", "Struga", "Tetovo", "Veles", "Kumanovo", "Prilep", "Strumica", "Gostivar",
          "Kicevo",
          "Kochani", "Radovis", "Gevgelija", "Kavadarci", "Debar", "Krusevo", "Stip", "Kriva Palanka", "Delcevo",
          "Demir Kapija",
          "Negotino", "Vinica", "Berovo", "Resen", "Probistip", "Dojran"]

matrix = []
ratings = []
locations = []
for city in cities:
    search.clear()
    search.send_keys("parking lots in " + city)
    search.send_keys(Keys.RETURN)
    time.sleep(10)
    names_parking_lot = driver.find_elements_by_xpath("//h3[contains(@class,'section-result-title')]")
    while True:
        for i in range(0, len(names_parking_lot)):
            try:
                location_parking_lot = driver.find_elements_by_xpath("//span[contains(@class,'section-result-location')]")[i]
                if not location_parking_lot.text:
                    locations.append("Not known location")
                else:
                    locations.append(location_parking_lot.text)
            except:
                locations.append("Not known location")
            try:
                rating_parking_lot = driver.find_elements_by_xpath("//span[contains(@class,'cards-rating-score')]")[i]
                if not rating_parking_lot.text:
                    ratings.append("Not known rating")
                else:
                    ratings.append(rating_parking_lot.text)
            except:
                ratings.append("Not known rating")

        for j in range(0, len(names_parking_lot)):
            node = {}
            node['City'] = city
            node['Name'] = names_parking_lot[j].text
            node['Location'] = locations[j]
            node['Rating'] = ratings[j]
            matrix.append(node)
        try:
            time.sleep(5)
            driver.find_element_by_id('n7lv7yjyC35__section-pagination-button-next').click()
            ratings = []
            locations = []
            time.sleep(5)
            names_parking_lot = driver.find_elements_by_xpath("//h3[contains(@class,'section-result-title')]")
            if len(names_parking_lot) == 0:
                break
            else:
                time.sleep(10)
        except:
            break

df = pd.DataFrame(matrix)
df.to_csv("C:\\Users\\Karolina\\Desktop\\Dizajn_i_arhitektura_na_softver_proekt\\parking_lots_all_cities.csv",
          index=False)
driver.quit()
