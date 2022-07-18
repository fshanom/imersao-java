import java.io.IOException;
import java.net.StandardSocketOptions;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.lang.Double.parseDouble;

public class App {
    public static void main(String[] args) throws IOException, InterruptedException {
        // fazer uma conexão HTTP com a api do IMDB e get dos dados guardando em uma string
        String url = "https://mocki.io/v1/9a7c1ca9-29b4-4eb3-8306-1adb9d159060";
        URI endereco = URI.create(url);
        var client = HttpClient.newHttpClient();
        var request = HttpRequest.newBuilder(endereco).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String body = response.body();

        // parse dos dados para um Map List para iteração
        var parser = new br.com.alura.omnistream.service.json.JsonParser();
        List<Map<String, String>> listaDeFilmes = parser.parse(body);

        // exibir e manipular os dados que interessam pra nós: nome, imagem e classificação
        for (Map<String, String> filme : listaDeFilmes) {
            System.out.println("\u001b[30m \u001b[44m " + "Nome: " + filme.get("title") + " \u001b[m");
            System.out.println("Poster: " + filme.get("image"));
            var nota = Math.round(parseDouble(filme.get("imDbRating")));

            //construindo a string com estrelas
            StringBuilder estrelasBuilder = new StringBuilder();
            for (int i = 0; i < nota; ++i) {
                estrelasBuilder.append("\uD83C\uDF1F");
            }
            String estrelas = estrelasBuilder.toString();

            System.out.println("Classificação: " + estrelas + " " + filme.get("imDbRating"));
            System.out.println();
        }
    }
}