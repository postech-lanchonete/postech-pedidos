# coding: utf-8
# language: pt

Funcionalidade: Pedidos

  Cenário: Criação de um novo pedido
    Dado que eu queira enviar um pedido para producao
    E tenha um produto com nome "Hamburguer" com o preco de R$ 20.00
    E tenha um produto com nome "Salada: com o preco de R$ 10.00
    E o cliente tenha o id igual a 1, com o nome de "Andre"
    Quando for feita a requisicao
    Entao deve retornar o pedido criado
    E o status do pedido deve ser "RECEBIDO"
    E o status da requisicao deve ser igual a 201