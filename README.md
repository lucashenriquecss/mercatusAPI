# Mercatus API - Jogo de Leilão e Economia Virtual

Mercatus API é uma plataforma para gerenciar a vendas e leilões com itens que os usuários podem comprar, vender ou negociar entre si. 

Ela também suporta leilões de itens específicos, onde os usuário podem comprar ingressos para salas de leilão agendadas e fazer lances com moedas virtuais. A API suporta transações em tempo real, utilizando WebSocket para comunicação entre usuário durante o leilão.

## Funcionalidades

- **Inventário de Itens**: Gerenciamento de itens como armas, skins e outros objetos no inventário dos jogadores.
- **Sistema de Moedas Virtuais**: Cada jogador tem um saldo (balance) para gerenciar suas moedas virtuais, que podem ser usadas para comprar itens.
- **Mercado ou Leilão**: Os usuários podem negociar itens entre si ou participar de leilões programados para itens específicos.
- **Transações e Registros**: A API armazena as transações de compra/venda e negociações de itens entre os usuários.
- **Leilões em Tempo Real**: Leilões programados onde os jogadores podem comprar ingressos e fazer lances com moedas virtuais.
  
## Tecnologias Utilizadas

- **Backend**: 
  - [Spring Boot] - Framework para construção do backend.
  - [Swagger] - Documentação
  - [Spring Security] - Controle de acesso e segurança nas transações.
  - [WebSocket] - Comunicação em tempo real entre usuários durante os leilões.
  - [Docker]
  
- **Banco de Dados**:
  - [PostgreSql] - Armazenamento de dados de inventário, transações e usuários.

  
- **Testes**:
  - [JUnit] e [Mockito] - Testes automatizados para garantir o funcionamento correto da aplicação.

## Estrutura do Projeto

- `com.example.mercatusAPI`: Pacote principal contendo os componentes da API.
  - `controller`: Contém os controladores que lidam com as requisições HTTP e a comunicação WebSocket.
  - `entity`: Classes que representam as entidades e dados do sistema, como itens, transações e usuários.
  - `dto`: Objetos de Transferência de Dados (DTO) usados para transportar dados entre camadas da aplicação, como itens, transações e usuários.
  - `exception`: Classes responsáveis pelo tratamento de exceções e erros personalizados da aplicação.
  - `service`: Lógica de negócios e serviços de manipulação de dados.
  - `repository`: Repositórios responsáveis pelo acesso e manipulação dos dados no banco de dados.
  - `infra`: Configurações de infraestrutura, como WebSocket, segurança e outras configurações externas (e.g., Spring Security).



## Comunicação em Tempo Real com WebSocket

A aplicação utiliza WebSocket para comunicação em tempo real, principalmente para os leilões. A configuração WebSocket está disponível em `com.example.mercatusAPI.infra.websocket.WebSocketConfig`.


## Como Rodar o Projeto

1. **Clone o Repositório**

   Clone este repositório para sua máquina local:
   ```bash
   git clone https://github.com/usuario/mercatus-api.git
