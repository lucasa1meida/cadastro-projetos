# Cadastro de Projetos

## Api onde podemos cadastrar projetos e funcionários, onde um funcionário pode estar em vários projetos e um projeto pode ter vários funcionários.

### Tecnologias utilizadas 💻:

* Jdk 21
* Spring boot
* Docker-compose com imagem MySql
* RESTful
* Testes unitários/persistência com Junit/Mockito
* Swagger

### Guia para inicialização 💡:

- Clone do projeto para uma pasta do seu computador;
- Navegue até a pasta do seu navegador via terminal;
- Realize o comando abaixo para subir o mysql na sua máquina (para essa etapa é necessário ter o docker instalado na maquina);
```shell 
docker-compose up 
``` 
- Após a configuração do docker-compose devemos usar a instrução do maven abaixo para garantir que teremos o build correto e atualizado da aplicação:
```shell
mvn clean install
```

- Para rodar o projeto, você pode utilizar o comando abaixo dentro do terminal na pasta raiz ou rodar na sua IDE de preferência(lembrando que para isso dar certo o docker precisa estar rodando):
```shell
./mvnw spring-boot:run
```
- No application properties inicialmente teremos a configuração abaixo que será utilizada para criar as tabelas automáticas para você:
```shell
  spring.jpa.hibernate.ddl-auto=create
  spring.datasource.url=jdbc:mysql://localhost:3306/cadastro_projetos?createDatabaseItNotExist=true
 ```
- Após essa criação aconselho a alterar as configurações no properties para não perder os dados que estão sendo testados:
```shell
  spring.jpa.hibernate.ddl-auto=none
  spring.datasource.url=jdbc:mysql://localhost:3306/cadastro_projetos
 ```

- Com o projeto de pé aida temos uma documentação de swagger para ajudar nas requisições http, para isso é só ir no navegador com o seguinte endereço:
  http://localhost:8080/swagger-ui/index.html

### Observabilidade🔎:

Podemos verificar métricas da aplicação utilizando dos caminhos que o spring actuator nos fornece.

- Para visualizar o health check do servidor:
    - ***localhost:8080/actuator/health***
- Para visualizar as possíveis métricas:
    - ***localhost:8080/actuator/metrics***
- Para visualizar as informações da aplicação:
    - ***localhost:8080/actuator/info***

### Considerações finais:
- No ambiente produtivo poderíamos colocar as chaves de acesso ao banco de dados em variáveis de ambiente e seguras em ferramentas como por exemplo no KEY_VAULT do Azure ou até mesmo KMS da AWS.
