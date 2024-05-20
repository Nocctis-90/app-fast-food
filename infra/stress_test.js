import http from "k6/http";
import { check, sleep } from "k6";

// Configurações do teste
export let options = {
  stages: [
    { duration: "1m", target: 1000 },  // Aumento gradual para 50 usuários em 1 minuto
    { duration: "10m", target: 1000 },  // Manter 50 usuários por 3 minutos
    { duration: "1m", target: 0 }    // Diminuir gradualmente para 0 usuários em 1 minuto
  ],
  thresholds: {
    http_req_duration: ["p(95)<500"] // Define o limite de duração da requisição em 95% das respostas em menos de 500ms
  }
};

// Função de teste
export default function() {
  // Realiza uma requisição GET para o endpoint de healthCheck
  let response = http.get("http://localhost:8080/healthCheck");

  // Verifica se a resposta está OK (código 200) usando a função de verificação
  check(response, {
    "is status 200": (r) => r.status === 200
  });

  // Aguarda por um curto período entre as requisições
  sleep(1);
}