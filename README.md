# SpringMVC_1128_JPA
桃認 1128 SSH SpringMVC JPA

Gmail:
vincenttuan@gmail.com

Github 程式碼
https://github.com/vincenttuan/SpringMVC_1128
https://github.com/vincenttuan/SpringMVC_1128_JDBC
https://github.com/vincenttuan/SpringMVC_1128_JPA

Netbeans 11.2
https://netbeans.apache.org/download/nb112/nb112.html

Eclipse EE
https://www.eclipse.org/downloads/packages/release/mars/r/eclipse-ide-java-ee-developers

Pure CSS
https://purecss.io/

SOLID (物件導向設計)
https://zh.wikipedia.org/wiki/SOLID_(%E9%9D%A2%E5%90%91%E5%AF%B9%E8%B1%A1%E8%AE%BE%E8%AE%A1)

OpenWeather
https://openweathermap.org/current#name
https://openweathermap.org/data/2.5/weather?q=Taoyuan,tw&appid=b6907d289e10d714a6e88b30761fae22
https://openweathermap.org/weather-conditions

w3schools SQL 語法教學
https://www.w3schools.com/sql/sql_join_left.asp

SQL Sample
1.
SELECT pu.ORDER_NUM, pu.CUSTOMER_ID,
        pu.PRODUCT_ID,
        pu.QUANTITY,
        0 as SUBTOTAL
FROM APP.PURCHASE_ORDER pu

2.LEFT JOIN
SELECT pu.ORDER_NUM, pu.CUSTOMER_ID, cu."NAME" as CUSTOMER_NAME,
        pu.PRODUCT_ID,
        pu.QUANTITY,
        0 as SUBTOTAL
FROM APP.PURCHASE_ORDER pu
LEFT JOIN APP.CUSTOMER cu ON pu.CUSTOMER_ID = cu.CUSTOMER_ID

3.
SELECT pu.ORDER_NUM, pu.CUSTOMER_ID, cu."NAME" as CUSTOMER_NAME,
        pu.PRODUCT_ID, pr.DESCRIPTION as PRODUCT_NAME, pc.DESCRIPTION as PRODUCT_CODE_NAME,
        pu.QUANTITY,
        0 as SUBTOTAL
FROM APP.PURCHASE_ORDER pu
LEFT JOIN APP.CUSTOMER cu ON pu.CUSTOMER_ID = cu.CUSTOMER_ID
LEFT JOIN APP.PRODUCT pr ON pu.PRODUCT_ID = pr.PRODUCT_ID
LEFT JOIN APP.PRODUCT_CODE pc ON pr.PRODUCT_CODE = pc.PROD_CODE

4.
SELECT pu.ORDER_NUM, pu.CUSTOMER_ID, cu."NAME" as CUSTOMER_NAME,
        pu.PRODUCT_ID, pr.DESCRIPTION as PRODUCT_NAME, pc.DESCRIPTION as PRODUCT_CODE_NAME,
        pu.QUANTITY, pr.PURCHASE_COST, di.RATE,
        CAST((pu.QUANTITY * pr.PURCHASE_COST) * ((100 - di.RATE) / 100) as DOUBLE) as SUBTOTAL
FROM APP.PURCHASE_ORDER pu
LEFT JOIN APP.CUSTOMER cu ON pu.CUSTOMER_ID = cu.CUSTOMER_ID
LEFT JOIN APP.PRODUCT pr ON pu.PRODUCT_ID = pr.PRODUCT_ID
LEFT JOIN APP.PRODUCT_CODE pc ON pr.PRODUCT_CODE = pc.PROD_CODE
LEFT JOIN APP.DISCOUNT_CODE di ON cu.DISCOUNT_CODE = di.DISCOUNT_CODE

建立 VIEW : PU_VIEW

5.
SELECT SUM(p.SUBTOTAL) TOTAL FROM APP.PU_VIEW p

建立 VIEW : PU_TOTAL 

6.
SELECT p.CUSTOMER_ID, p.CUSTOMER_NAME, SUM(p.SUBTOTAL) as TOTAL
FROM APP.PU_VIEW p 
GROUP BY p.CUSTOMER_ID, p.CUSTOMER_NAME
ORDER BY TOTAL DESC
FETCH FIRST 10 ROWS ONLY

建立 VIEW : PU_TOP10

好用的 icon
https://www.iconfinder.com/free_icons

FB Login
https://developers.facebook.com/docs/javascript

Asset Chart 1
SELECT c.name, SUM(p.amount * s.price) as subtotal 
FROM Classify c, Portfolio p, TStock s 
WHERE p.investor_id=101 AND p.tStock_id = s.id AND s.classify_id = c.id 
GROUP BY c.name

Asset Chart 2
SELECT c.name, SUM(p.amount * (s.price-p.cost)) as subtotal 
FROM Classify c, Portfolio p, TStock s 
WHERE p.investor_id=101 AND p.tStock_id = s.id AND s.classify_id = c.id 
GROUP BY c.name

Maven dependencies are failing with a 501 error
https://stackoverflow.com/questions/59763531/maven-dependencies-are-failing-with-a-501-error
