# Краток опис за инструкции во цевка

#### Ова е кратко видео за активирање и употреба на pipeline креирано од нас
https://youtu.be/EBqiIrxe7XQ
<br>

### Напомена: 
Употребата на PowerShell мора да е со привилегија админ (Run as administrator; за Windows)
### Напомена: 
За да може да го импортирате .csv фајл-от во датабазата тој мора да е во bin фолдер-от (<b><code>C: -> Program Files -> MongoDB -> Server -> 4.4(version) -> bin</code></b>) 

### Команди:
Команда 1
```shell
cat parking_lots_selenium.csv | Select-String -Pattern "Not known location" -NotMatch > new_parking_lots.csv | mongoimport --type csv -d gradovi -c grad --headerline --drop new_parking_lots.csv
```
- Објаснување на команда 1
  - со <code>cat</code> ја печатиме содржината на фајл-от
  - со <code>Select-String</code> ги филтрираме сите паркинзи кои имаат позната локација, односно ги исклучуваме тие чија што локација е "Not known location" и ги запишуваме во new_parking_lots.csv 
  - со <code>mongoimport</code> го внесуваме csv file-от
    - со --type специфицариме каков тип ќе биде file-от
    - со -d ја одбираме датабазата, по default најчесто тука се користи *test* доколку немате претходно креирана база.
    - со -c се креира collection во MongoDB со тоа име.
    - со --headerline се специфицира дека првата редица од csv file-от ќе биде имињата на полињата.
    
Команда 2
```shell
cat parking_lots_selenium.csv | Select-String -Pattern "[CityName]" > city_filtering.csv | mongoimport --type csv -d gradovi -c grad2 --headerline --drop new_parking_lots.csv
```
- Овојпат има разлика само во командата <code>Select-String</code> со тоа што овојпат креираме датабаза конкретно за даден град проследен како параметар наместо [CityName], на пример Skopje (без '[]')
