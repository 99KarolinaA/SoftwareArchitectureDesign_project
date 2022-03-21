# Инсталација на MongoDB
 Брза инсталација на MongoDB.

#### <b><code>Чекор 1:</code></b> Инсталација на MongoDB МSI Installer Package <br>

На официјалната страна на MongoDB, проследена на следниов [линк](https://www.mongodb.com/try/download/community), се презема моменталната стабилна верзија на MongoDB, за соодветниот оперативен систем. Од особена важност е да се осигураме во делот Package, да е избрана msi опцијата.

 <p align="center">
     <img src="https://raw.githubusercontent.com/1vanjordanov/images/main/Picture1.png" style="width:30%;"></img> <br>
  </p>
  
#### <b><code>Чекор 2:</code></b> Инсталација на MongoDB со помош на Installation Wizard <br>

Потребно е да преминеме во Downloads фолдерот, и да го стартуваме .msi пакетот, кој штотуку го инсталиравме. Со ова ќе го стартуваме installation wizard.


#### <b><code>Чекор 3:</code></b> Потребно е да се следат чекорите и насоките кои ни се дадени од installation wizard-от на MongoDB <br>

- Избор на **Setup Type**:
Moже да го избереме Complete setup type, или Custom setup type. При избор на Complete Setup Type, ја инсталираме MongoDB и сите потребни MongoDB алатки на default-натa локација, додека пак при избор на Custom setup type, ни се дава можност да одбереме што ќе инсталираме. Нашиот избор беше Complete setup type.
- Koнфигурација на сервис

 <p align="center">
     <img src="https://raw.githubusercontent.com/1vanjordanov/images/main/Picture2.png" style="width:30%;"></img> <br>
  </p>
  
- Инсталацијата на MongoDB Compass e oпционална.
- На крајот, кликаме Install

#### <b><code>Чекор 4:</code></b> Стартување на MongoDB
По извршената инсталација, преминуваме во локацијата дадена со следната патека: <br>
<b><code>C: -> Program Files -> MongoDB -> Server -> 4.4(version) -> bin</code></b>.
Во bin, се наоѓаат два фајлови, mongod и mongo. Она што беше од наш интерес, е стартувањето на серверот, па за таа цел во C, креиравме два нови фолдери, со помош на следната команда: <b><code>C:\> mkdir data/db</code></b>. Целта на креирањето на овие два фолдери е поради тоа што MongoDB, има потреба од фолдери за чување на податоците. Default-ната патека на фолдерот во кој се чуваат податоците е токму таа, data/bin па и поради тоа ги креиравме овие два фолдери. Без нив, се соочивме со следниот проблем:

 <p align="center">
     <img src="https://raw.githubusercontent.com/1vanjordanov/images/main/Picture5.png" style="width:30%;"></img> <br>
  </p>

 
Со ова MongoDB серверот е стартуван. Но, за да може да работиме со овој сервер, потребен ни е посредник. Најпрво ги поставивме environment variables. Во windows, тие се сместени на следната локација: <b><code>Advanced System Settings -> Environment Variables -> Path(Under System Variables) -> Edit</code></b>, каде едноставно се поставува патеката до bin фолдерот, во нашиот случај тоа е: <b><code>C:\Program Files\MongoDB\Server\4.4\bin</code></b>. Последниот чекор е отвoрање на нов прозорец во Command Prompt, и извршување на командата: <b><code>mongo</code></b>, со што можеме да започнеме со работа.

#### <b><code>Чекор 5:</code></b> Инсталација на дополнителни алатки
Потребно е и да се симнат официјалните алатки дадени на страната на MongoDB преку следниов [линк](https://www.mongodb.com/try/download/database-tools). Откако ќе го симнете .zip фајл-от, треба алатките што се наоѓаат во bin папката да ги копирате и да ги ставите во патеката на bin фолдер-от на MongoDB, во нашиот случај: <b><code>C: -> Program Files -> MongoDB -> Server -> 4.4(version) -> bin</code></b>. Без овие алатки, внесувањето на податоците во дата базата нема да биде <b>успешно</b>.

 <p align="center">
     <img src="https://raw.githubusercontent.com/1vanjordanov/images/main/Picture6.png" style="width:30%;"></img> <br>
  </p>
