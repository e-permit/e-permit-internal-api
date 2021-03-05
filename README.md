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

## Brain Strom

- Messaging is not async because receiver should immediately handle and send a result
- A possible way would be separating message and response. In this case message handling would be async
- Created Message Handling: When a message is created it persisted to db with a lock. Also an event is fired to send message. 
  For message which is not handled, a scheduler can be used(1 hour interval).
- Message event handler tries to send message to audience and gets result. If result is handled, message entity should be set handled.
- Receiver Message Handling: When a message is received 
- Key rotation 
- Quota sync 
- Message Result()

## TO-DO

- Validation of jwt
- Permit config controller and service impl
- Integration test for services
- Public api integration
- Frontend app integration
- Offline Verify integration
