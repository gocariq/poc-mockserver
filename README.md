# poc-mockserver
This PoC was developed to test the Forward Carllback feature of the Mock-Server (https://mock-server.com/).

### Follow the steps below to run the PoC:

1. Build the poc-webhook


The poc-webhook is a simple application to simulate an application that needs to receive a request from Mock-Server, it will start at port 9191
```bash
$ ./gradlew build

$ java -jar build/libs/poc-webhook-0.1-all.jar
```

2. Build the poc-mockserver

The poc-mockserver is the project that has the classes to run the callbacks for Mock-Server, the build task will copy the generated jar to lib folder for Mock-Server
```bash
$ ./gradlew build
```

3. Run MockServer as docker container

```bash
$ docker run --rm --network=host -v $PWD/config:/config -v $PWD/libs:/libs -p 1080:1080 mockserver/mockserver -serverPort 1080
```

### For testing use this curl command
```bash
curl --location --request POST 'http://localhost:1080/payment/register' \
--header 'Content-Type: application/json' \
--data-raw '{
    "card": "1111222233334444"
}'
```
The Mock-Server will generate an ID and Date, send the ID, Date and Card to webhook and returns the ID and Date as response for curl command
