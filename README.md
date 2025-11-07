# Sistema de Gerenciamento de Seguros de Veículos

## 📋 Sobre o Projeto

Este repositório contém um sistema completo de gerenciamento de seguros de veículos desenvolvido em Java. O sistema permite o cadastro e gerenciamento de segurados (pessoas físicas e jurídicas), veículos, apólices de seguro e sinistros, implementando regras de negócio específicas do setor de seguros.

## 🚀 Funcionalidades

O sistema oferece as seguintes funcionalidades principais:

### Gerenciamento de Segurados
- **Cadastro de Pessoas Físicas**: Registro de segurados com CPF, renda e dados pessoais
- **Cadastro de Empresas**: Registro de segurados jurídicos com CNPJ, faturamento e indicação se é locadora de veículos
- **Validação de CPF/CNPJ**: Sistema de validação automática de documentos
- **Controle de Bônus**: Gerenciamento de bônus por segurado

### Gerenciamento de Veículos
- **Cadastro de Veículos**: Registro com placa, ano, categoria e proprietário
- **Vinculação com Segurados**: Associação de veículos a pessoas físicas ou jurídicas
- **Categorias de Veículos**: Classificação por tipo de veículo

### Gerenciamento de Apólices
- **Criação de Apólices**: Geração automática de número de apólice
- **Cálculo de Prêmio**: Cálculo automático baseado em:
  - Categoria do veículo
  - Ano do veículo
  - Tipo de segurado (pessoa física ou jurídica)
  - Bônus do segurado
- **Controle de Vigência**: Gerenciamento de data de início de vigência
- **Valores de Cobertura**: Definição de franquia e valor máximo segurado

### Gerenciamento de Sinistros
- **Registro de Sinistros**: Cadastro de ocorrências com:
  - Data e hora do sinistro
  - Tipo de sinistro
  - Valor do sinistro
  - Usuário responsável pelo registro
- **Vinculação com Apólices**: Associação de sinistros a apólices específicas
- **Controle Sequencial**: Numeração sequencial de sinistros por apólice
- **Validações**: Verificação de limites e valores máximos segurados

### Persistência de Dados
- Sistema de persistência com DAOs genéricos
- Operações CRUD completas (Criar, Ler, Atualizar, Deletar)
- Serialização de objetos para armazenamento

## 🛠️ Tecnologias Utilizadas

### Linguagem e Framework
- **Java**: Linguagem principal do projeto
- **Lombok**: Biblioteca para redução de código boilerplate
  - `@Getter` e `@Setter`: Geração automática de getters e setters
  - `@AllArgsConstructor` e `@NoArgsConstructor`: Construtores automáticos
  - `@EqualsAndHashCode`: Implementação de equals e hashCode

### APIs Java
- **Java Time API**: Manipulação de datas e horas (`LocalDate`, `LocalDateTime`)
- **Java Math**: Operações com `BigDecimal` para valores monetários
- **Java Serialization**: Interface `Serializable` para persistência de objetos
- **Collections Framework**: `ArrayList`, `List` para manipulação de coleções

### Padrões e Arquitetura
- **DAO (Data Access Object)**: Camada de acesso a dados
- **Mediator Pattern**: Camada de lógica de negócio
- **Generics**: Implementação de classes genéricas para reutilização

## 🎓 Conceitos de Programação Orientada a Objetos

O projeto demonstra diversos conceitos fundamentais de POO:

### 1. **Encapsulamento**
- Atributos privados com controle de acesso via getters e setters
- Uso de modificadores de acesso (`private`, `protected`, `public`)
- Exemplo nas classes `Segurado`, `Apolice`, `Veiculo`

### 2. **Herança**
- Classe abstrata `Segurado` como base para:
  - `SeguradoPessoa`: Herda características de segurado pessoa física
  - `SeguradoEmpresa`: Herda características de segurado pessoa jurídica
- `DAOGenerico<T>`: Classe genérica base para todos os DAOs específicos
- Reutilização de código e hierarquia de classes

### 3. **Polimorfismo**
- Método abstrato `isEmpresa()` implementado diferentemente em cada subclasse
- Método `getIdUnico()` da interface `Registro` implementado por diversas classes
- Tratamento polimórfico de segurados (pessoa física ou jurídica)

### 4. **Abstração**
- Classe abstrata `Segurado`: Define estrutura comum sem implementação completa
- Interface `Registro`: Define contrato para objetos persistíveis
- Interface `Serializable`: Marca classes que podem ser serializadas

### 5. **Classes e Objetos**
- Entidades bem definidas: `Apolice`, `Veiculo`, `Sinistro`, `Endereco`
- Enums para tipos específicos: `CategoriaVeiculo`, `TipoSinistro`
- Classes de dados: `DadosVeiculo`, `DadosSinistro`

### 6. **Composição**
- `Apolice` contém `Veiculo`
- `Veiculo` contém `Segurado`
- `Segurado` contém `Endereco`
- Relacionamento "tem-um" entre objetos

### 7. **Sobrescrita de Métodos (Override)**
- Implementação de métodos abstratos nas classes filhas
- Sobrescrita de `getIdUnico()` em cada entidade
- Customização de comportamento em subclasses

### 8. **Generics (Tipos Genéricos)**
- `DAOGenerico<T extends Registro>`: DAO reutilizável para qualquer entidade
- Type safety e reutilização de código
- Eliminação de casting desnecessário

### 9. **Tratamento de Exceções**
- Classe customizada `ExcecaoValidacaoDados`
- Validações com retorno de mensagens de erro
- Controle de fluxo baseado em exceções

### 10. **Singleton Pattern**
- `ApoliceMediator.getInstancia()`: Única instância da classe
- Controle centralizado de operações de apólices

### 11. **Imutabilidade e Serialização**
- Uso de `serialVersionUID` para controle de versão
- Classes serializáveis para persistência
- Atributos `final` quando apropriado

### 12. **Comparadores Personalizados**
- `ComparadorSinistroSequencial`: Implementação de lógica de comparação customizada
- Ordenação de objetos baseada em critérios específicos

## 📁 Estrutura do Projeto

```
src/
├── Main.java                          # Classe principal
└── br/edu/cs/poo/ac/seguro/
    ├── daos/                          # Camada de Acesso a Dados
    │   ├── DAOGenerico.java          # DAO base genérico
    │   ├── ApoliceDAO.java
    │   ├── SeguradoDAO.java
    │   ├── SeguradoEmpresaDAO.java
    │   ├── SeguradoPessoaDAO.java
    │   ├── SinistroDAO.java
    │   └── VeiculoDAO.java
    ├── entidades/                     # Modelos de Domínio
    │   ├── Apolice.java
    │   ├── CategoriaVeiculo.java
    │   ├── Endereco.java
    │   ├── PrecoAno.java
    │   ├── PrecosAnosCategoria.java
    │   ├── Registro.java             # Interface para entidades
    │   ├── Segurado.java             # Classe abstrata
    │   ├── SeguradoEmpresa.java
    │   ├── SeguradoPessoa.java
    │   ├── Sinistro.java
    │   ├── TipoSinistro.java
    │   └── Veiculo.java
    ├── excecoes/                      # Exceções Customizadas
    │   └── ExcecaoValidacaoDados.java
    ├── mediators/                     # Lógica de Negócio
    │   ├── ApoliceMediator.java
    │   ├── ComparadorSinistroSequencial.java
    │   ├── DadosSinistro.java
    │   ├── DadosVeiculo.java
    │   ├── RetornoInclusaoApolice.java
    │   ├── SeguradoEmpresaMediator.java
    │   ├── SeguradoMediator.java
    │   ├── SeguradoPessoaMediator.java
    │   ├── SinistroMediator.java
    │   ├── StringUtils.java
    │   └── ValidadorCpfCnpj.java
    └── testes/                        # Classes de Teste
        ├── ComparadorObjetosSerial.java
        ├── FileUtils.java
        ├── TesteAbstrato.java
        ├── TesteApoliceMediator.java
        ├── TesteDAO.java
        ├── TesteDAOGenerico.java
        ├── TesteMediator.java
        ├── TesteSeguradoEmpresaDAO.java
        ├── TesteSeguradoEmpresaMediator.java
        ├── TesteSeguradoMediator.java
        ├── TesteSeguradoPessoaMediator.java
        ├── TestesEntidades.java
        ├── TesteSinistroMediator.java
        └── TesteVeiculoDAO.java
```

## 💡 Conceitos Avançados Aplicados

- **Separation of Concerns**: Separação clara entre camadas (DAO, Entidades, Mediators)
- **DRY (Don't Repeat Yourself)**: Uso de classes genéricas e herança para evitar duplicação
- **SOLID Principles**:
  - Single Responsibility: Cada classe tem uma responsabilidade bem definida
  - Open/Closed: Extensível através de herança sem modificar classes base
  - Liskov Substitution: Subclasses podem substituir suas classes pai
  - Interface Segregation: Interfaces pequenas e específicas
  - Dependency Inversion: Dependência de abstrações (interfaces e classes abstratas)

## 🧪 Testes

O projeto inclui uma suíte completa de testes unitários para validação de:
- Funcionalidades dos DAOs
- Lógica de negócio dos Mediators
- Comportamento das entidades
- Validações e cálculos

## 👨‍💻 Autor

Desenvolvido como projeto acadêmico para a disciplina de Programação Orientada a Objetos.

---

**Instituição**: CESAR School - Curso de Ciência da Computação  
**Período**: 3º Período  
**Ano**: 2025
