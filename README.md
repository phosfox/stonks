# stonks

## Installation

### Build image with Docker
```
docker build -t stonks .
```

### Run container
```
docker run --rm -p 8080:8080 \
       -e ALPHAVANTAGE_KEY=$(echo $ALPHAVANTAGE_KEY) \
       --name stonks-running stonks
```
## Usage

Start ring server
```
lein ring server
```

Watch cljs source code
```
shadow-cljs watch app
``` 

