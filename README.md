# API para Cadastro de Campanhas

## Inicio

O projeto foi dividido em módulos para facilitar a execução e a escalabilidade do desenvolvimento.
Os módulos são:
 - avaliacao-core: Módulo responsável por conter todas classes que são comuns entre os sistemas, como as exceções. É composto pelo módulo 'avaliacao-commons'
 - avaliacao-ws: É o módulo que contém os serviços rest.
  - avaliacao-service-campaign: Serviço de manutenção de campanhas.
  - avaliacao-service-fan: Serviço de socio-torcedor. É a aplicação cliente, reponsavel por acessar a api de cadastro de campanhas para associação de usuários.

O módulo 'avaliacao-commons', possui a classe 'RestExceptionHandler', responsavel por capturar todas as exceções lançadas pelo sistema e enviar como resposta para o client de maneira amigável, montando uma mensagem usnado opadrão 'build', informando o motivo da exceção, o timestamp, o status http, entre outras informações úteis.
  
Já os módulos 'avaliacao-service-campaign' e 'avaliacao-service-fan', foram construidos de forma que sejam totalmente independentes. Cada um possui suas próprias configurações de segurança e acesso a base de dados.

Para parte de segurança usei o spring-security. As configurações se encontram na classe 'SecurityConfig'.
Para simplificar a configuração, criei dois usuarios em memória, seguem as credenciais:
 - user: user password: user role:USER (possui os privilegios de acessar os serviços)
 - user: admin password: admin role:USER,ADMIN (Alem de acesso aos serviços, pode acessar a console do banco de dados)

O banco de dados utilizado foi o 'H2 Database'. Foi criado uma base para cada serviço.
Para o módulo 'avaliacao-service-campaign' é usado a base 'campaign_service', com as tabelas CAMPAIGN E USER_CAMPAIGN.
Para o módulo 'avaliacao-service-fan' é usado a base 'fan_service', com as tabelas TEAM E USER.

Para acesso aos dados, utilizei o spring-data.

### Pre-Requisitos

- Utilizar o maven para baixar as dependencias
- Importar o projeto no eclipse
- Ter instalado a versão 8 do java.

### Exemplo de utilização
Utilizando o postman, fazer uma requisição POST na URI http://localhost:8082/v1/customers
passando os seguintes dados de autorização.
  - authorization: basic auth
  - username: admin
  - password: admin
  
  No corpo da requisição passar um json como o exemplo abaixo:<br/>
  {  <br/>
   &nbsp;"name":"Júlio",<br/>
   &nbsp;"email":"juliodasilv@hotmail.com",<br/>
   &nbsp;"birth":"1985-10-22",<br/>
   &nbsp;"team":{  <br/>
      &nbsp;&nbsp;&nbsp;"name":"palmeiras"<br/>
   },<br/>
   &nbsp;"campaigns":[  <br/>
    &nbsp;&nbsp;{<br/>
        &nbsp;&nbsp;&nbsp;"name": "campanha44",<br/>
        &nbsp;&nbsp;&nbsp;"start": "2018-10-01",<br/>
        &nbsp;&nbsp;&nbsp;"end": "2018-10-04"<br/>
    &nbsp;},<br/>
    &nbsp;{<br/>
        &nbsp;&nbsp;&nbsp;"name": "campanha47",<br/>
        &nbsp;&nbsp;&nbsp;"start": "2018-10-01",<br/>
        &nbsp;&nbsp;&nbsp;"end": "2018-10-05"<br/>
    &nbsp;&nbsp;}<br/>
   &nbsp;]<br/>
  }	<br/>
 
 A resposta vem do serviço de cadastro de campainha com http status igual a 201 (CREATED):<br/>
 {<br/>
   &nbsp; "name": "Júlio",<br/>
    &nbsp;"email": "juliodasilv@hotmail.com",<br/>
    &nbsp;"birth": "1985-10-22",<br/>
    &nbsp;"team": {<br/>
        &nbsp;&nbsp;"name": "PALMEIRAS"<br/>
    &nbsp;},<br/>
    &nbsp;"campaigns": [<br/>
        &nbsp;{<br/>
            &nbsp;&nbsp;"name": "campanha44",<br/>
            &nbsp;&nbsp;"idTeam": 1,<br/>
            &nbsp;&nbsp;"start": "2018-10-01",<br/>
            &nbsp;&nbsp;"end": "2018-10-04"<br/>
        &nbsp;},<br/>
        &nbsp;{<br/>
            &nbsp;&nbsp;"name": "campanha47",<br/>
            &nbsp;&nbsp;"idTeam": 1,<br/>
            &nbsp;&nbsp;"start": "2018-10-01",<br/>
            &nbsp;&nbsp;"end": "2018-10-05"<br/>
       &nbsp; &nbsp;}<br/>
    &nbsp;]<br/>
  } <br/>

![alt tag](https://raw.githubusercontent.com/juliodasilv/campaign-service/master/files/architecture_diagram.jpg)

## Resumo das tecnologias utilizadas

* [Spring Boot](https://spring.io/projects/spring-boot) - Framework Web usado
* [Spring Data](https://spring.io/projects/spring-data) - Framework para auxiliar na persistencia dos dados
* [Spring Security](https://spring.io/projects/spring-security) - Framework para parte de segurança da aplicação
* [H2 Database](http://www.h2database.com/html/main.html) - Banco de dados relacional.
* [Maven](https://maven.apache.org/) - Gerenciador de dependencias
* [Java 8](https://www.java.com/pt_BR/download/faq/java8.xml)  - Versão do Java

## Autor

* **Julio Oliveira da Silva** - [juliodasilv](https://github.com/juliodasilv)
