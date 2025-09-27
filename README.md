# 📝 Task Manager

Gerenciador de tarefas simples desenvolvido em **Java + Spring Boot**, com **PostgreSQL**, **Redis** para cache e **RabbitMQ** para mensageria.  
Cada vez que uma nova tarefa é criada, uma mensagem é enviada para a fila do RabbitMQ e consumida por um listener.

## 🚀 Tecnologias
- Java 17
- Spring Boot 3 (Web, JPA, Cache, AMQP)
- PostgreSQL
- Redis
- RabbitMQ
- Docker e Docker Compose
- Maven
- Lombok

---

## ⚙️ Funcionalidades
- Criar tarefa  
- Listar todas as tarefas (com cache Redis)  
- Marcar tarefa como concluída  
- Excluir tarefa  
- Enviar notificação para RabbitMQ ao criar tarefa  
- Consumer imprime no console as mensagens recebidas da fila  

---

## 📂 Estrutura do projeto

task-manager/
│── docker-compose.yml
│── backend/
│ ├── src/main/java/com/example/taskmanager/
│ │ ├── model/Task.java
│ │ ├── repository/TaskRepository.java
│ │ ├── service/TaskService.java
│ │ ├── controller/TaskController.java
│ │ ├── config/RabbitConfig.java
│ │ └── consumer/TaskConsumer.java
│ └── resources/application.yml


---

## 🐳 Como rodar o projeto

1. Clonar o repositório
```bash
git clone https://github.com/seu-usuario/task-manager.git
cd task-manager


2. Subir dependências com Docker
docker-compose up -d

Isso vai subir:
PostgreSQL → porta 5432
Redis → porta 6379
RabbitMQ → porta 5672 (management UI em http://localhost:15672, user/pass = guest/guest)


3. Rodar o backend
cd backend
./mvnw spring-boot:run

🔗 Endpoints da API
Criar tarefa
curl -X POST http://localhost:8080/tasks \
-H "Content-Type: application/json" \
-d '{"title":"Estudar Spring Boot","description":"Implementar cache + rabbit"}'

Listar tarefas
curl http://localhost:8080/tasks

Concluir tarefa
curl -X PUT http://localhost:8080/tasks/1

Excluir tarefa
curl -X DELETE http://localhost:8080/tasks/1

📩 Mensageria (RabbitMQ)

Ao criar uma tarefa, uma mensagem é enviada para a fila task.queue.

O consumer (TaskConsumer) escuta a fila e imprime no console:

📩 Mensagem recebida da fila: Nova tarefa criada: Estudar Spring Boot


Você também pode acessar o RabbitMQ Management UI em:
👉 http://localhost:15672
 (usuário: guest / senha: guest)

 📌 Objetivo do projeto

Este projeto foi desenvolvido para estudos de backend, explorando:
Integração de diferentes tecnologias (Postgres, Redis, RabbitMQ)
Cache de dados com Redis
Comunicação assíncrona com RabbitMQ
Estruturação de projetos Spring Boot para escalabilidade
Containers e orquestração com Docker Compose

👨‍💻 Desenvolvido por Davimendonca7