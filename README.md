# WLP_Tools
Tools library for whole WLP projects usage.

# Current version
Last released version of WLP_Tools jar is 0.0.2

# Features included

* Files operation
  - JSON file loader (JsonLoader.java)
  - CSV file loader (CsvLoader.java)

* Maven tools
  - Pom.xml model reader (VersionReceiverService.java)\
  
* HTTP request support
  - POST request with body parsed as String
  - POST request with body parsed as Map<String, String>
  - GET request with parameters parsed as Map<String, String>
  - GET request without parameters

* Serialization
  - LocalDateTime serializer/deserializer