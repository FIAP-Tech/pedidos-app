package br.com.fiap.pedidos.domain.model;

import lombok.Data;

@Data
public class MensagemEmail {

    private String emailDestinatario;

    private String assunto;

    private String corpoEmail;

    public void setCorpoEmail(String nomeCliente){
        this.corpoEmail = criarCorpoEmailBoasVindas(nomeCliente);
    }

    private String criarCorpoEmailBoasVindas(String nomeCliente) {
        return "<!DOCTYPE html>\n" +
                "<html lang=\"pt-BR\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <title>Email de Boas-Vindas ao FIAP E-commerce</title>\n" +
                "    <style>\n" +
                "        body {\n" +
                "            font-family: 'Arial', sans-serif;\n" +
                "            background-color: #F8F8F8;\n" +
                "            color: #333;\n" +
                "            margin: 0;\n" +
                "            padding: 0;\n" +
                "        }\n" +
                "        .container {\n" +
                "            max-width: 600px;\n" +
                "            margin: 0 auto;\n" +
                "            padding: 20px;\n" +
                "            background-color: #FFFFFF;\n" +
                "            border-radius: 8px;\n" +
                "            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);\n" +
                "        }\n" +
                "        h1, h2, p {\n" +
                "            margin-top: 0;\n" +
                "            margin-bottom: 20px;\n" +
                "        }\n" +
                "        h1 {\n" +
                "            color: #00215E;\n" +
                "            font-size: 24px;\n" +
                "            font-weight: bold;\n" +
                "        }\n" +
                "        p {\n" +
                "            font-size: 16px;\n" +
                "            line-height: 1.5;\n" +
                "        }\n" +
                "        ul {\n" +
                "            margin-bottom: 20px;\n" +
                "            padding-left: 20px;\n" +
                "        }\n" +
                "        li {\n" +
                "            font-size: 16px;\n" +
                "            line-height: 1.5;\n" +
                "        }\n" +
                "        .footer {\n" +
                "            margin-top: 20px;\n" +
                "            font-size: 14px;\n" +
                "            color: #888;\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <div class=\"container\">\n" +
                "        <h1>Bem-vindo ao FIAP E-commerce!</h1>\n" +
                "        <p>Oi " + nomeCliente + ",</p>\n" +
                "        <p>Seja bem-vindo ao FIAP E-commerce! Agradecemos por se cadastrar em nossa plataforma. Estamos muito felizes em tê-lo como nosso cliente.</p>\n" +
                "        <p>Aqui estão alguns dos benefícios que você pode aproveitar:</p>\n" +
                "        <ul>\n" +
                "            <li>Facilidade de navegação e busca de produtos.</li>\n" +
                "            <li>Produtos de alta qualidade e preços competitivos.</li>\n" +
                "            <li>Atendimento ao cliente excepcional.</li>\n" +
                "        </ul>\n" +
                "        <p>Seja bem-vindo e aproveite ao máximo a sua experiência de compra conosco!</p>\n" +
                "        <div class=\"footer\">\n" +
                "            <p>Atenciosamente,<br>Equipe FIAP E-commerce</p>\n" +
                "        </div>\n" +
                "    </div>\n" +
                "</body>\n" +
                "</html>";
    }
}
