package com.example.semanal.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.example.semanal.entity.Pedido;
import com.example.semanal.enums.Status;
import com.example.semanal.service.PedidoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class PedidoLambdaHandler implements RequestHandler<Map<String, Object>, String> {

    @Autowired
    private PedidoService pedidoService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String handleRequest(Map<String, Object> input, Context context) {
        try {
            String httpMethod = (String) input.get("httpMethod");
            Map<String, Object> body = input.get("body") != null ? objectMapper.readValue((String) input.get("body"), Map.class) : null;
            String pedidoId = (input.get("pathParameters") != null) ? (String) ((Map) input.get("pathParameters")).get("id") : null;

            switch (httpMethod) {
                case "POST":
                    Pedido pedido = objectMapper.convertValue(body, Pedido.class);
                    Pedido savedPedido = pedidoService.save(pedido);
                    return objectMapper.writeValueAsString(savedPedido);

                case "GET":
                    if (pedidoId != null) {
                        Pedido foundPedido = pedidoService.findById(pedidoId);
                        return objectMapper.writeValueAsString(foundPedido);
                    }
                    return objectMapper.writeValueAsString(pedidoService.listAll());

                case "PATCH":
                    if (pedidoId != null && body != null && body.containsKey("status")) {
                        // A linha abaixo não precisa do @Autowired para Status
                        return objectMapper.writeValueAsString(pedidoService.updateStatus(pedidoId, Status.valueOf((String) body.get("status"))));
                    }
                    return "Pedido não encontrado ou status inválido";

                case "DELETE":
                    if (pedidoId != null) {
                        pedidoService.deletarPedido(pedidoId);
                        return "Pedido deletado com sucesso";
                    }
                    return "ID do pedido não fornecido";

                default:
                    return "Método não suportado";
            }
        } catch (Exception e) {
            return "Erro: " + e.getMessage();
        }
    }
}
