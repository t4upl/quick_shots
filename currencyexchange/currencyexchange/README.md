# Currency Exchange Analysis

List of content

1. [Word of warning](#word-of-warning)
2. [Problem](#problem)
3. [Main takeaways](#main-takeaways)
4. [Deeper analysis](#deeper-analysis)
5. [Analysis made](#analysis-made) 
6. [Charts](#charts)
7. [Repository content](#repository-content)


## Word of warning
<details>
<summary>Word of warning</summary>

Don't use results of this project as financial advice. If you do use the following analysis for your financial decision, you are doing this on your own responsibility. I am in no way responsible for the outcome.

As I started going through the results I started to wonder if I made a good choice using data from 10 year period. Maybe 5 years would be more up-to-date.
</details>

## Problem
<details>
<summary>Problem</summary>

Polish currency PLN has been steadily losing value due to high inflation. That is the reason why I have decided to keep my saving in foreign currency. I was wondering what is the best time to exchange PLN for either EUR or USD.

This analysis is written from point of view of a Polish person trying to buy EUR or USD for PLN. If you are selling EUR or USD analysis result should be inverted.

As a source of data I have used data available from the European Central Bank [data](https://www.ecb.europa.eu/stats/policy_and_exchange_rates/euro_reference_exchange_rates/html/eurofxref-graph-pln.en.html)

European Central Bank provides data in XML format in daily intervals going as far back as 1999-01-04. Daily intervals were important to me as I wanted to find out which day of the week is the best for an exchange.
</details>

## Main takeaways
<details>
<summary>Main takeaways</summary>

1. PLN is strongly losing value over time. As a general rule if you are waiting for an exchange, you are losing money.
2. Exchange rate fluctuations don't make up for the general trend of PLN losing value. The sooner you exchange the better.
3. On a month-to-month basis:
-  February - is the best month to buy EUR/USD followed by April and January
-  March is probably good to buy EUR but results are mixed for USD
-  For the rest of the months the sooner you buy the better
-  November and December are bad times to buy. You are better off waiting for February next year or the second best month to buy: January for USD and April for EUR
4. Best day of the week to buy is Monday. Friday is ok. The rest of the week is bad. However, fluctuations within a week are very subtle. You won't be making a bank by waiting for the best day of the week.
5. Week-to-week analysis shows:
 - Confirmed what month-to-month analysis showed that the best times to buy are: February, January, and April
 - Currency exchanges on a week-to-week basis are very chaotic. This is especially visible outside of the best three months to buy. While the overall trend *the sooner the better* is visible it is of no use trying to pinpoint the best week of the year to buy. A pretty good week to buy can be between two terrible weeks to buy.
</details>

 
## Deeper analysis
<details>
<summary>Deeper analysis</summary>

1. Over the years 2017-2022 PLN lost 6.09% value to EUR and 4.09% value to USD. Based on monthly average value fluctuations are 1.33% for EUR and 4.09% for USD. This means that for EUR there is little room to trying to optimize the best time to buy. This is due to very strong downward trend in comparison to currency value fluctuations.  The strategy to buy *as soon as possible* even if not the best may be very close to optimal. There is more wiggle room for currency exchange optimization for USD. It is important to note that fluctuations are between monthly averages so if we took averages per day the fluctuations value would have been bigger. At the same time I don't believe finding best time to buy on day-to-day basis is within capacity of an individual person.
2. On a month-to-month basis:
- February, April, and January are the best times to buy. The order depends on the currency. For EUR it is February, April and January. For USD it is February, January, April. 
- I don't have a conclusive result for March. On month-to-month yearly analysis it did well but when we take a look at two-year month-to-month analysis results are mixed. For EUR March was a good time to buy in the first year but did poorly in the second year. For USD it did poorly in both years. This clashes with month-to-month for one-year period analysis where March was 4th best month to buy foreign currency. As a general rule, I would try to wait till April. 
- On the practical side it is astonishing how bad times to exchange are in November and December. For EUR you can wait till any of the best months: January, February (best) or April and be better. For USD better choices would be January or February (best). It is amazing that despite EUR going everything against it, there is still possibility to come up with some form of currency exchange strategy. This is probably because of the close time proximity of the worst and best time to buy.
3. Best day of the week to buy is Monday. Friday is ok. I have no idea why this happens this way.
4. Week-to-week analysis. Here I was trying not only the best month but week or period to buy USD or EUR.
- To find the best period we should analyze the week-to-week graph. It shows weeks ranked by the best time to buy. To find the best period we should look for a consistent group of weeks near the left side of the graph i.e. they have the highest rank. By a consistent group of weeks I mean there are little or no spaces between weeks e.g. 15,14,16.
- For EUR best periods would be weeks 5 to 9 (03.02 - 03.03) and weeks 13 to 16 (01.04 - 22.04). If we want to be more relaxed we could even expand the second period to weeks 10 to 16 (08.03 -  22.04). This is quite interesting as on month-to-month analysis January did better than March.
- For USD best periods would be weeks 1 to 10 (05.01 - 08.03) and weeks 14 to 18 (08.04 - 05.05). This is consistent with the month-to-month analysis
- For finding the best periods I didn't use any scientific measurement but was looking at excel results and tried to find a decent consistent best period. The result is ad-hoc.
- For the rest of the year the graph is very chaotic. This may be due to the *rank* operator. We are losing information by how much values between the weeks change. It may be so that the spread of currency exchange is not as drastic as this graph shows to be.
- The chaotic nature of graph in later weeks of the year could be a result of my flawed definition of 'year of the week'. Each 'week of the year' tend to get longer as year progresses. Week 1 has 6 days while the last week, week 52, has 11 days. However even this should not cause such chaotic behavior. By days in a week I mean dates that has been assigned to a n<sup>th</sup> week over analysis period (10 years).
- Another explanation for the jumping points on the week graph may be a mixture of using the *rank* operator and strong downward trend of PLN value. There will be much less value distribution around the best times to buy than in other times of the year as the dataset is limited by the start of year. Hence much more concise rank values at the start of the year.         
</details>

## Analysis made
<details>
<summary>Analysis made</summary>

Analysis was made on the period between 01.01.2012 and 31.12.2022 on daily exchange rates data provided by European Central Bank. The data set does not include entries for Saturdays and Sundays as Forex does not update currency exchange rates on those days. 

Each of the following analyses uses the average currency value in the first period (January, Monday, first week) as a reference point to track value changes over time.

1. Month-to-month - samples average by month. Due to the strong downtrend of PLN value, it is difficult to conclude about the best month to buy. Is January good because of month-to-month fluctuations or is it good because it is at the beginning of the year, so even without any fluctuations it would be the best month to buy?
2. Day of week - samples average by day of the week
3. week-to-week - samples average by the week of the year. We consider a week of a year a 7-day period from the beginning of the week - the day of the next entry after the end of last week or the first entry in the year. This means that if 02.01 is on Monday a week will follow the traditional definition of a week 02.01 - Monday to 06.01 Friday. However when 02.01 is on Friday the first week of the year will start on 02.01 Friday and end on 08.01 Thursday, the following week. Due to bank holidays in some years, the beginning of the weeks will be shifted. It is expected that if we take week 35 across all years date range will be longer than 7 days.
4. Month-to-month over two years - samples average by month but over two years. Effectively it may be thought of pseudo-year with 24 months whereas month 13 is January of the next year. This analysis is useful as it is closest to a real-life scenario. *It is July and I want to buy EUR. Should I do it now or wait until some month in the future between now and July of next year to buy EUR cheaper?*. The problem is that not all data is equal. Most months are read twice - as a normal month of the first year and (12 + a month)<sup>th</sup> of the following pseudo-year. Exceptions are months of 2012 and 2022 as they are included only once as the first and last years processed from the dataset.
</details>

## Charts
<details>
<summary>Charts</summary>

<img src="https://user-images.githubusercontent.com/16683374/226143963-13e4e038-52b5-4673-9660-7e3e050462ac.png" alt="Eur-to-pln chart" width="50%" height="50%"/>

The chart shows EUR/PLN exchange rate for years in years 2017-2022 together with the average for that period.

This chart is a visual proof of the downfall trend of the Polish economy. See how each year the PLN is losing value towards EUR and how the exchange of the following year is the above exchange rate of the previous year. As an alternative, you can just take a look at the linear graph provided by [ECB](https://www.ecb.europa.eu/stats/policy_and_exchange_rates/euro_reference_exchange_rates/html/eurofxref-graph-pln.en.html)

<img src="https://user-images.githubusercontent.com/16683374/227197210-9be29e40-3fd0-4588-bbfa-b81bcbd177c1.png" alt="Eur-to-pln chart linear chart for year 2017-2022" width="50%" height="50%"/>

<img src="https://user-images.githubusercontent.com/16683374/226143964-49ca1b0a-77c1-4841-b9c5-9b2084a0c671.png" alt="Usd-to-pln chart" width="50%" height="50%"/>


The chart shows USD/PLN exchange rate for years in years 2017-2022 together with the average for that period.

<img src="https://user-images.githubusercontent.com/16683374/226143962-19e69b3c-d1fb-453d-af3c-93d789c6b06d.png" alt="Eur-to-pln and Usd-to-pln average chart" width="50%" height="50%"/>

The chart shows EUR/PLN and USD/PLN average exchange rates in the years 2017-2022.

Note: This chart is interesting as it has some contradictions with previous conclusions:

1. January and February are still good times to buy
2. April is no longer as attractive as before when compared with other times of the year
3. Late May to early July seems to be another good period to buy
4. November and December are no longer the worst times to buy being overtaken by October as the worst time to buy
5. Looking at this graph I have strong doubts if using 10 year period was the right call

<img src="https://user-images.githubusercontent.com/16683374/226143965-51ec0dfe-4fb0-4a94-90f6-284be17fce07.png" alt="Eur-to-pln and Usd-to-pln exchange rates group by week of the year" width="50%" height="50%"/>

The chart shows weeks on the x-axis and the best time to buy week rank for EUR/PLN and USD/PLN for the years 2012-2022.

Note: This chart shows groups with little spread for the best-to-buy ranked weeks (left side of the chart). As rank progresses spread increases. It becomes impossible to find a group of weeks that are either good-to-buy or bad-to-buy as week ranks get mixed up. Weeks that are close in terms of time are far away from each other in terms of rank. Rank is being used to evaluate the best week-to-buy but it doesn't necessarily mean a big difference in values when comparing exchange rates on a week-to-week basis. When there are enough data points two weeks that are close together in terms of both time and value might be forced to be spread apart by some exchange rate that is in an unrelated part of the year just because it happens to have an exchange rate value between values of those two weeks.

tldr; You can try to find the best weeks to buy but that's it. There is no way to convert a year into periods and sort them by 'best-time-to-buy' EUR or USD as very quickly things become random. Bottom line is that if you are buying EUR outside of the best period do it without guilt as there is no way to predict if the next week's price would be better.
</details>   
     
## Repository
<details>
<summary>Repository structure</summary>

<pre>

├───src
│   ├───main
│   │   ├───java
│   │   │   └───t4upl
│   │   │       └───service
│   │   │           ├───loaddata  - load data from files to memory, convert EUR/USD and EUR/PLN to USD/PLN
│   │   │           ├───model     - common model. Result is a collection of ResultElement elements by some temporal value e.g. year. ResultElement is a collection of raw data points grouped by temporalId of temporal value e.g. year 2022 when grouping by year 
│   │   │           ├───print     - services responsible for formatting and printing results to console or file 
│   │   │           └───result    - services responsible for grouping raw data by temporal value e.g. week 
│   │   └───resources
│   │       ├───readme  - resources for readme file, images
│   │       ├───result  - results from running the program, console output and raw data extracted from xmls as csv file 
│   │       ├───spreadsheet - OpenOffice calc containing data and charts
│   │       └───(exchange rates xmls)
</pre>
</details>