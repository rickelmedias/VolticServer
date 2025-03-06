## Execução
Como rodar na sua maquina `quarkus dev`.

## Documentação

### Parte 1 - Conectar MQTT
Conexão para permitir o Dispotivo Inteligente conversar com a Plataforma

##### Comandos utilizados

##### A configuração do mosquitto

Faça como o meu tree (apenas não terá o *pass*, vamos fazer depois):

```
r1ddax@r1ddax: /mosquitto$ tree
.
├── config
│   ├── mosquitto
│   │   └── config
│   ├── mosquitto.conf
│   └── pass
├── data
└── log
```

**/mosquitto/config$ cat mosquitto.conf**
```
bind_address 0.0.0.0
port 1883
allow_anonymous false
```

Você ainda não irá ter o arquivo pass, mas assim que tiver rodando o mosquitto vamos criar ele dentro do mosquitto.

##### Como iniciar container do mosquitto.

`docker run -it -p 1883:1883 -v "$PWD/mosquitto/config:/mosquitto/config" eclipse-mosquitto`

Apos iniciar/criar o mosquitto, é necessário criar um usuário, no meu caso eu fiz o r1ddax com senha 123456.

##### Como acessar o container do mosquitto e configurar senha.

`docker exec -it <nome_container> /bin/sh`

Ao entrar nele precisamos criar o nosso usuário:

```
mosquitto_passwd -c /mosquitto/config/pass user
```

Depois que der enter ele vai pedir uma senha. Você digita sua senha e da enter novamente.

Ao criar o usuário precisamos finalizar o processo do mosquitto e editar novamente o **mosquito.conf**, o resultado final será:

**/mosquitto/config$ cat mosquitto.conf**
```
bind_address 0.0.0.0
port 1883
allow_anonymous false
password_file /mosquitto/config/pass
```

Se quiser rodar o mosquitto também com **docker-compose.yml**, podemos criar:

```
version: '3.8'

services:
  mosquitto:
    image: eclipse-mosquitto:latest
    container_name: mosquitto
    restart: always
    ports:
      - "1883:1883"
    volumes:
      - ./mosquitto/config:/mosquitto/config
      - ./mosquitto/data:/mosquitto/data
      - ./mosquitto/log:/mosquitto/log
```

##### Como mandar uma mensagem de pub dentro do container do mosquitto.

`mosquitto_pub -h localhost -t source -m "Teste" -u r1ddax -P 123456`

Refs:

- https://hub.docker.com/_/eclipse-mosquitto
- https://quarkus.io/guides/messaging
- https://smallrye.io/smallrye-reactive-messaging/smallrye-reactive-messaging/3.4/mqtt/mqtt.html
- https://quarkus.io/extensions/io.quarkus/quarkus-messaging-mqtt/

### Parte 2 - RestAPI e Acesso ao Banco

O RestAPI foi configurado com jackson. Pode necessitar de mais coisas no futuro.

Banco utilizado será o CouchDB, que irá possibilitar armazenar diversos dados de dispositivos.
Estamos rodando ele em Docker.

Teremos um `.env` e um `docker-compose.yml`

**.env**
```
POWER_PASSWORD=AB123456
APP_NAME=couchdb
APP_VERSION=latest
APP_HTTP_PORT=5984
APP_USER=admin
APP_PASSWORD=$POWER_PASSWORD
APP_NETWORK=network_couchdb
```

**docker-compose.yaml**
```
services:
  couchdb:
    image: couchdb:${APP_VERSION}
    container_name: ${APP_NAME}
    restart: always
    ports:
      - "${APP_HTTP_PORT}:5984"
    volumes:
      - couchdb_data:/opt/couchdb/data
      - couchdb_config:/opt/couchdb/etc/local.d
      - couchdb_log:/opt/couchdb/var/log
    environment:
      COUCHDB_USER: ${APP_USER}
      COUCHDB_PASSWORD: ${APP_PASSWORD}

networks:
  default:
    name: ${APP_NETWORK}

volumes:
  couchdb_data:
  couchdb_config:
  couchdb_log:
```

Assim que o banco estiver rodando precisamos acessar o `http://127.0.0.1:5984/_utils/#login` e criar um database.

Refs:

- https://quarkus.io/extensions/org.apache.camel.quarkus/camel-quarkus-couchdb/
- https://github.com/apache/camel-quarkus
- https://camel.apache.org/components/4.10.x/couchdb-component.html
- https://dzone.com/articles/camel-quarkus-couchdb