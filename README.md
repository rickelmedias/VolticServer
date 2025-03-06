## Execução
Como rodar na sua maquina `quarkus dev`.

## Documentação

### Parte 1 - Conectar MQTT
Conexão para permitir o Dispotivo Inteligente conversar com a Plataforma

- https://hub.docker.com/_/eclipse-mosquitto
- https://quarkus.io/guides/messaging
- https://smallrye.io/smallrye-reactive-messaging/smallrye-reactive-messaging/3.4/mqtt/mqtt.html
- https://quarkus.io/extensions/io.quarkus/quarkus-messaging-mqtt/

##### Comandos utilizados

Como iniciar container do mosquitto.

`docker run -it -p 1883:1883 -v "$PWD/mosquitto/config:/mosquitto/config" eclipse-mosquitto`

Como acessar o container do mosquitto.

`docker exec -it <nome_container> /bin/sh`

Como mandar uma mensagem de pub dentro do container do mosquitto.

`mosquitto_pub -h localhost -t source -m "Teste" -u r1ddax -P 123456`

### Parte 2 - RestAPI com Autenticação e Autorização