# e-permit-internal-api
e-permit internal api java implementation

Spring Boot
Hibernate
PostgreSql
Graylog

TR-UA-2021-1-12582

## Generating key

```keytool -genkeypair -alias <kid> -keyalg EC -keysize 256 -sigalg SHA256withECDSA  -validity 365 -storetype JKS -keystore permitec.jks -storepass <pwd>```

core: resource services and dtos
data -> repositories: in order to access database
data -> services: convertion entity <-> dto(transactions is private method)
data -> utils: common helpers
