package org.estudos.br;
 
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
 
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import static org.junit.jupiter.api.Assertions.assertEquals;
 
public class ConsultaIBGETest {
    @Mock
    private HttpURLConnection connectionMock;
    private static final String JSON_RESPONSE = "{\"id\":16,\"sigla\":\"AP\",\"nome\":\"Amapá\",\"regiao\":{\"id\":1,\"sigla\":\"N\",\"nome\":\"Norte\"}}";
    private static final String DISTRITOS_API_URL = "https://servicodados.ibge.gov.br/api/v1/localidades/distritos/";
    private static final String ESTADOS_API_URL = "https://servicodados.ibge.gov.br/api/v1/localidades/estados/";
 
 
    @Test
    @DisplayName("Teste para consulta única de um estado")
    public void testConsultarEstado() throws IOException {
        // Arrange
        String uf = "SP"; // Define o estado a ser consultado
 
        // Act
        String resposta = ConsultaIBGE.consultarEstado(uf); // Chama o método a ser testado
 
        // Assert
        // Verifica se a resposta não está vazia
        assert !resposta.isEmpty();
 
        // Verifica se o status code é 200 (OK)
        HttpURLConnection connection = (HttpURLConnection) new URL(ESTADOS_API_URL + uf).openConnection();
        int statusCode = connection.getResponseCode();
        assertEquals(200, statusCode, "O status code da resposta da API deve ser 200 (OK)");
    }
 
    @Test
    @CsvSource({"RO", "AC", "AM", "RR", "PA", "AP", "TO", "MA", "PI", "CE", "RN", "PB", "PE", "AL", "SE", "BA", "MG", "ES", "RJ", "SP", "PR", "SC", "RS", "MS", "MT", "GO", "DF"})
    @DisplayName("Teste para consulta de estados com CSV")
    public void testConsultarEstados(String sigla) throws IOException {
       
        String uf = sigla;
 
        String resposta = ConsultaIBGE.consultarEstado(uf); // Chama o método a ser testado
 
        assert !resposta.isEmpty();
 
        HttpURLConnection connection = (HttpURLConnection) new URL(ESTADOS_API_URL + uf).openConnection();
        int statusCode = connection.getResponseCode();
        assertEquals(200, statusCode, "O status code da resposta da API deve ser 200 (OK)");
    }
 
 
    @Test
    @CsvSource({"520005005", "310010405", "520010005"})
    @DisplayName("Teste para consulta de distritos com CsvSource")
    public void testConsultarDistrito(int identificador) throws IOException {
       
        int id = identificador;
 
        String resposta = ConsultaIBGE.consultarDistrito(id); // Chama o método a ser testado
 
        assert !resposta.isEmpty();
 
        HttpURLConnection connection = (HttpURLConnection) new URL(DISTRITOS_API_URL + id).openConnection();
        int statusCode = connection.getResponseCode();
        assertEquals(200, statusCode, "O status code da resposta da API deve ser 200 (OK)");
    }
 
    @Test
    @DisplayName("Consulta usando o Estado com Mock")
    public void testConsultarEstadoComMock() throws IOException {
 
        String uf = "RJ";
 
        String response = ConsultaIBGE.consultarEstado(uf);
 
        assertEquals(JSON_RESPONSE, response, "O JSON retornado não corresponde ao esperado.");
    }
 
 
}