# e-permit-internal-api
e-permit internal api java implementation

Spring Boot
Hibernate
PostgreSql
Graylog

B T 3RD
TR-UA-2021-1-12582

## Flow

- TR creates a key and expose with e-permit-configuration/jwks.json(same for UA)
- TR creates an authority(UA) (code, title, uri, verify-uri) (same for UA)
- TR API gets UA jwks from uri(same for UA)
- TR creates a issuer quota and send it to UA 
- UA approves the quota 
- TR creates a permit and send it to UA(qr code verify-uri/jwt)
- 

## Generating key

```keytool -genkeypair -alias <kid> -keyalg EC -keysize 256 -sigalg SHA256withECDSA  -validity 365 -storetype JKS -keystore permitec.jks -storepass <pwd>```

core: resource services and dtos
data -> repositories: in order to access database
data -> services: convertion entity <-> dto(transactions is private method)
data -> utils: common helpers

## TO-DO

- Validation of jwt
- Permit config controller and service impl
- Integration test for services
- Public api integration
- Frontend app integration
- Offline Verify integration
