# 🛍️ Ávila Acessórios API

API RESTful para gerenciamento completo de um e-commerce de semi-joias, desenvolvida em **Java com Spring Boot** e **PostgreSQL**. O sistema inclui funcionalidades de cadastro de produtos, gerenciamento de usuários, carrinho de compras, pedidos e avaliações.

---

## 🚀 Tecnologias Utilizadas

- **Java 21**
- **Spring Boot**
- **PostgreSQL**
- **Spring Security (JWT)**
- **JUnit & Mockito**
- **Maven**

---
## 📋 Funcionalidades

### 🔐 Usuários
- Cadastro de usuários
- Login com autenticação JWT
- Atualização e visualização de perfil
- CRUD completo para administradores

### 🛒 Produtos
- Cadastro, atualização, remoção e listagem de produtos
- Filtros por categoria, material e preço
- Detalhamento de cada produto com imagens

### 📦 Carrinho de Compras
- Adicionar, remover e atualizar itens no carrinho
- Visualização do carrinho por usuário

### 📝 Pedidos
- Criação de pedidos a partir do carrinho
- Atualização de status do pedido (Ex: PROCESSANDO, ENVIADO)
- Visualização detalhada de pedidos

### 🏷️ Avaliações
- Avaliação e comentários de produtos por usuários
- Listagem de avaliações por produto

---

## 🗄️ Banco de Dados

Banco de dados relacional **PostgreSQL** modelado para suportar todas as entidades:

- Usuário
- Endereço
- Produto
- Imagem Produto
- Carrinho
- Itens do Carrinho
- Pedido
- Itens do Pedido
- Avaliação

---

## 📄 **Modelo do banco disponível:**  
[Notion - Estrutura do Banco de Dados](https://impartial-mulberry-4be.notion.site/Projeto-vila-Acess-rios-1a60c8057cd78098b1fbc7f5b821aa26?pvs=74)

---

## 🔑 Autenticação

O projeto utiliza **JWT (JSON Web Tokens)** para autenticação e autorização. Apenas administradores têm acesso a endpoints de gerenciamento de produtos e usuários.

---



---

## ⚙️ Como Executar o Projeto

1. **Clone o repositório:**

```bash
    git clone https://github.com/allanaavila/avila_acessorios.git
```

2. **Configure o banco PostgreSQL:**
   * Crie um banco de dados avila_acessorios
   * Ajuste as credenciais no arquivo application.properties:
```bash
    spring.datasource.url=jdbc:postgresql://localhost:5432/avila_acessorios
    spring.datasource.username=SEU_USUARIO
    spring.datasource.password=SUA_SENHA
```
3. **Execute a aplicação:**
```bash
    ./mvnw spring-boot:run
```

---

## 🧪 Testes
O projeto conta com testes unitários utilizando JUnit e Mockito, garantindo a qualidade e funcionamento das principais funcionalidades da aplicação.

--- 

## 📚 Referências & Links Úteis
Configuração Inicial do Projeto
Notion com Controllers e Endpoints

---

## 📩 Contato
Desenvolvido por Allana Ávila

- [LinkedIn](https://www.linkedin.com/in/allanaavila/)
- [GitHub](https://github.com/allanaavila)

