# Camel Google Cloud Messaging

This project implements a [Google Cloud Messaging](https://developers.google.com/cloud-messaging/) component for [Apache Camel](http://camel.apache.org/).

## Usage

```java
from("direct:foo").to("gcm:/topics/bar?apiKey=AIz...");
```

## Configuration

* `apiKey`
* `collapseKey`
* `timeToLive` - defaults to `3`
* `delayWhileIdle`
* `restrictedPackageName`
* `dryRun`
* `retries` - defaults to `0` (no retries)
* `stringBodyDataKey` - data key for string message bodies, defaults to `message`

## Supported headers

* `to` - recipient of the message, optional. If absent, defaults to the name of the endpoint (`/topics/bar` for `gcm:/topics/bar?apiKey=AIz...`).

## Supported body types

* If message body is a string, it will be sent in the `data` as `<stringBodyDataKey>:<body>`.
* Otherwise message body will be interpreted as a map and sent in the `data` as key-value pair, with both keys and values converted to strings.

### Body examples

Body `"myMessage"` will be sent as:

```javascript
{
  "data" : {
    "message" : "myMessage"
  }
}
```

The following body:

```java
final Map<String, String> body = new HashMap<String, String>();
body.put("facilityEquipmentnumber", "10213788");
body.put("stationName", "Arnstadt Hbf");
body.put("facilityDescription", "Aufzug zu Bstg 2/3");
body.put("facilityState", "INACTIVE");
```

Will be sent as:

```javascript
{
  "data" : {
    "facilityEquipmentnumber" : "10213788",
    "stationName" : "Arnstadt",
    "facilityDescription" : "Aufzug zu Bstg 2/3",
    "facilityState" : "INACTIVE"
  }
}
```
