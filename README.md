### Midas Case Assigment

The challenge is about integrating with a third-party data provider (Robinhood in this case).
In this repo, market and instrument data are extracted and the database is updated using the robinhood api.


## Part I: Market Sync

A request is sent to this endointe to pull market data. The information of the existing markets is being updated, and the ones that are not are added.

Robinhood endpoint : GET https://api.robinhood.com/markets

Below is the REST endpoint used to update the markets. As a result, it returns the updated markets in a response entity.

GET http://localhost:8080/markets/sync


## Part II: Instrument Sync

When the application is started, data for instruments is initialized with seed.sql. Afterwards, a request for each instrument symbol is sent to the robinhood endpoint below to update each instrument.

Robinhood endpoint : GET https://api.robinhood.com/instruments/?symbol=AAPL

For the market_id's of the instruments, the market codes obtained with the parse from the resulting market URL returned from the Robinhood Enpoint are used. If the relevant market is not found, that field returns -1.

Below is the REST endpoint used to update the instruments. As a result, it returns the updated instruments in a response entity.

### Caution!!! When a request is sent to this endpoint, the response time varies between 1.5 minutes and 5 minutes, depending on the internet speed.

GET http://localhost:8080/instruments/sync


## Part III: Instrument Retrieve

For all instruments, a request should be sent to the following endpoint. As a result, all insturments in the database will return in a response entity.

GET http://localhost:8080/instruments

In order to get instrument information with a symbol, a request should be sent to the following endpoint. The requested instrument will return in a responseentity.
Below is an example endpoint and a pattern.

GET http://localhost:8080/instruments/?symbol=AAPL

GET http://localhost:8080/instruments/?symbol={symbol}

