# e-permit-internal-api
e-permit internal api java implementation

Spring Boot
Hibernate
PostgreSql
Graylog

TR-UA-2021-12582

## Generating key

```keytool -genkeypair -alias <kid> -keyalg EC -keysize 256 -sigalg SHA256withECDSA  -validity 365 -storetype JKS -keystore permitec.jks -storepass <pwd>```
