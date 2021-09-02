Istio Microservices

## Basis for tests

https://github.com/ewolff/microservice-istio/tree/master/microservice-istio-demo

## Deploying Services to Docker

```
$ cd k8s-deploy

$ docker compose up -d
```

## Testing on Docker

```
$ curl http://localhost:10000/service1/hello
$ curl http://localhost:10000/service1/other

$ curl http://localhost:11000/service2/hello
$ curl http://localhost:11000/service2/other
```


## Stopping Docker

```
$ docker compose down
```


## Install Istio

```
https://istio.io/latest/docs/setup/getting-started/
```

## Install Istio Add-ons

```
$ cd ~/Downloads/istio-1.11.1

$ kc apply -f samples/addons
```

## Deploying to Kubernetes

```
$ cd k8s-deploy

$ kc apply -f k8s-infrastructure.yml

$ kc apply -f k8s-microservices.yml
```

## Testing on K8s

The istio **ingressgateway** is listening on localhost port 80

```
$ curl http://localhost/service1/hello
$ curl http://localhost/service1/other

$ curl http://localhost/service2/hello
$ curl http://localhost/service2/other
```


## Applying load to the Services

```
$ cd k8s-deploy

$ sh load_service1.sh

$ sh load_service2.sh
```

## Istio Dashboards

```
$ istioctl dashboard kiali

$ istioctl dashboard jaeger

$ istioctl dashboard envoy deployment/service1

$ istioctl dashboard grafana
```

For **grafana** click the Dashboards icon (the four squares) and choose Manage

## Applying a JWT required policy

JWT taken from Auth0 server; call is from Postman

```
$ curl --location --request POST 'https://dev-mbof2xy6.us.auth0.com/oauth/token' \
--header 'Content-Type: application/json' \
--data-raw '{
    "client_id": "i6r1QdVUzw3vq2izd5VcgqiD46h5qySp",
    "client_secret": "L5WHs135sD2MknMwDdL1m6iPuTY_XXUD-Fq5TkhRji202fIA06VXnb_faBfCLgil",
    "audience": "https://steve.amido.com/identifier",
    "grant_type": "password",
    "username": "user1@amido.com",
    "password": "Pass1234",
    "scope": "openid offline_access"
}'
```

```
$ kc apply -f k8s-jwt.yml

$ curl http://localhost/service1/hello
Hello from service1

$ curl http://localhost/service1/other
RBAC: access denied

$ curl -H "Authorization: Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6IndianE2YW9vRDg2OUt1NkhEOFVFZSJ9.eyJpc3MiOiJodHRwczovL2Rldi1tYm9mMnh5Ni51cy5hdXRoMC5jb20vIiwic3ViIjoiYXV0aDB8NjEyZTI2OWMxOWY4MGYwMDY5ZDQxY2NhIiwiYXVkIjpbImh0dHBzOi8vc3RldmUuYW1pZG8uY29tL2lkZW50aWZpZXIiLCJodHRwczovL2Rldi1tYm9mMnh5Ni51cy5hdXRoMC5jb20vdXNlcmluZm8iXSwiaWF0IjoxNjMwNTk0MzkwLCJleHAiOjE2MzA2ODA4OTAsImF6cCI6Imk2cjFRZFZVenczdnEyaXpkNVZjZ3FpRDQ2aDVxeVNwIiwic2NvcGUiOiJvcGVuaWQgcHJvZmlsZSBlbWFpbCBhZGRyZXNzIHBob25lIHN0ZXZlQUJDIHN0ZXZlREVGIGNuIHNjb3BlMSBvZmZsaW5lX2FjY2VzcyIsImd0eSI6InBhc3N3b3JkIn0.FkljO6nRiKf5hxeX6Xbuzn9cAOox6-_p4bqtbUATbRluh-rD6JduqMZ6alg6ENEAx42IPNn9wgjWUc5_dCSql8WZOLz5m3bUfj88dm0XPtP4TBT5rtek4_rgikGpY48gtwjfGmeBYj9MZ3VD8NB0Mfx4ilZryU9qXBdNYSxrlHCygo5qjEauWX0XgXFk6HVwA6G7qbQgFDDx0hpEWXA_DPvXgIHX0PWljXZG9U8PWj5CrIzoA2XAlN9kivMbDdjqh_sDYxzdhP1cPWMuiDlBvoy4Dw-5X-ySkVr_l0G-HSQVAZWtbiBi4cGtTilUssbeFrQSoUcVfarmWNW8oZ9h-Q" http://localhost/service1/other
service1 called other service, response was : Hello from service2
```

## Tidying up K8s

```
$ cd k8s-deploy

$ kc delete -f k8s-jwt.yml

$ kc delete -f k8s-microservices.yml

$ kc delete -f k8s-infrastructure.yml

$ cd ~/Downloads/istio-1.11.1

$ kc delete -f samples/addons
```
