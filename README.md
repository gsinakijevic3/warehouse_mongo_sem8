# MongoDB + Docker

```bash
docker pull mongo
docker run -d -p 27017:27017 --name mongo mongo
docker exec -it mongo bash
mongosh
```

Mongo-Shell commands: 

```bash
use warehouse #erstellt neue DB, bzw. benutzt eine alte, falls diese schon existiert
show dbs #zeigt alle DBs an
```

# Code

Wie in der vorigen Aufgabe die Klassen `WarehouseData`und  `ProductData` , sowie die Interfaces `WarehouseRepository` und `ProductRepository` erstellen. Außerdem werden noch ein `MainController`,  und eine `Application`-Klasse benötigt. 

Die `application.properties` müssen angepasst werden: 

```
spring.application.name=MongoDB
spring.data.mongodb.host=localhost
spring.data.mongodb.port=27017
spring.data.mongodb.database=warehouse
```

## Shell

```bash
db.product.updateOne(
    { _id: "ddb10657-1c35-44e0-89aa-857d8cca346a" },
    { $set: {"productQuantity":1000} }
)

db.product.insertOne(
    {
        _id:"ajdfljaf-7890-789-adfsaadsf",
        productName:"Flasche",
        productQuantity:432
    }
)

db.product.deleteOne({ _id:"ajdfljaf-7890-789-adfsaadsf" })

db.product.find()

db.product.findOne({_id:"0d0a90d9-290b-4ecc-af9f-04ad5abb6f10"});

```

## EK

Um random-Werte zu generieren, hab ich einen Service erstellt, der vordefinierte Werte aus Arrays holt. 

### Fragestellungen

Wie viele Warehouses gibt es in einem Land?

`db.warehouse.countDocuments({ warehouseCountry: "Austria" })`

Wie viele gibt es von einem Produkt in allen Warehouses?

`db.product.findOne({_id:"0d0a90d9-290b-4ecc-af9f-04ad5abb6f10"}**,** {productQuantity:1})**;**`

Welche Produkte haben in einem Warehouse weniger als 2?

`db.warehouse.findOne({ _id: "e5d39f1a-5f22-4859-9ee0-420d1c1e2737" }**,**{ products: { $elemMatch: { productQuantity: { $lt: 500 } } } })`

# Fragen

Nennen Sie 4 Vorteile eines NoSQL Repository im Gegensatz zu einem relationalen DBMS

- Skalierbar
- Flexibel
- Hohe Geschwindigkeit
- Untstrukturierte Daten

Nennen Sie 4 Nachteile eines NoSQL Repository im Gegensatz zu einem relationalen DBMS

- Fehlen von Standardisierung
- Weniger Sicherheit bei Transaktionen
- Komplexe Abfragen werden schwieriger
- Einschränkungen bei Komplexität

Welche Schwierigkeiten ergeben sich bei der Zusammenführung der Daten?

Meist keine `JOIN`s und inkonsistente Datenmodelle

Welche Arten von NoSQL Datenbanken gibt es? Nennen Sie einen Vertreter für jede Art?

- Schlüssel/Wert-Paar - Amazon Dynamo DB
- spaltenorientiert - Apache Cassandra
- graphorientiert - Amazon Neputune
- dokumentorientiert - MongoDB

Beschreiben Sie die Abkürzungen CA, CP und AP in Bezug auf das CAP Theorem

- CA - Schnittmenge zwischen Consistency und Availability
- CP - Schnittmenge zwischen Consistency und Partition Tolerance
- AP - Schnittmenge zwischen Availability und Partition Tolerance

Mit welchem Befehl koennen Sie den Lagerstand eines Produktes aller Lagerstandorte anzeigen.

`db.product.find({_id:<id>**,** {productQuantity:1**,** _id:0})`

Mit welchem Befehl koennen Sie den Lagerstand eines Produktes eines bestimmten Lagerstandortes anzeigen.

```bash
db.warehouse.findOne(
    {
        _id: "e9c4a085-1965-4b58-ae48-a7086d408b6f",
        "products._id": "0d0a90d9-290b-4ecc-af9f-04ad5abb6f10"
    },
    {"products.productQuantity":1}
)
```

# Quellen

https://www.baeldung.com/spring-data-mongodb-connection

https://www.geeksforgeeks.org/mongodb-create-database-using-mongoshell/

https://datascientest.com/de/no-sql-nicht-relationale-datenbanken
