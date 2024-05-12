DELETE FROM cliente;
DELETE FROM endereco;

INSERT INTO endereco (id, bairro, cep, cidade, estado, logradouro)
VALUES (1, 'Centro', '01153-000', 'São Paulo', 'SAO_PAULO', 'Avenida Paulista, 253'),
       (2, 'Barra Funda', '05001-200', 'São Paulo', 'SAO_PAULO', 'Avenida Francisco Matarazzo, 1705');

INSERT INTO cliente (id, cpf, data_cadastro, data_nascimento, email, nome, telefone, id_endereco)
VALUES (1, '412.138.278-17', '2024-05-04 15:33', '1992-07-11', 'ricardo@gmail.com', 'Ricardo Silva', '(15) 99611-3355', 2),
       (2, '593.944.800-30', '2024-05-05 07:12', '1989-01-20', 'fulano@gmail.com', 'Fulano da Silva', '(11) 98911-3355', 1);