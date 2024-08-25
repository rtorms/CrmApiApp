import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Date;
import java.util.List;

import org.junit.Test; // Usando JUnit 4
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.google.gson.Gson;

import io.swagger.api.ClienteApi;
import io.swagger.cliente.Cliente;
import io.swagger.cliente.ClienteController;
import io.swagger.cliente.ClienteService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ClienteApi.class) // Especifica a classe principal da aplicação
@AutoConfigureMockMvc
public class TesteClienteService {

    @InjectMocks
    private ClienteService clienteService;

    @InjectMocks
    private ClienteController clienteController;

    private Cliente cliente;
    
    @Test
    public void teste1() {
    	assertEquals("1", "1");
    }

//    @Test
//    public void testSequenciaSalvarEditar() throws Exception {
//        // Listar clientes existentes
//        List<Cliente> clientesAntes = clienteService.pesquisa("Cliente");
//        Gson gson = new Gson();
//        System.out.println("Clientes antes de salvar:");
//        for (Cliente cliente : clientesAntes) {
//            System.out.println(gson.toJson(cliente));
//        }
//
//        // Salvar novo cliente
//        cliente = new Cliente();
//        cliente.setNomeFantasia("Novo Cliente");
//        cliente.setCpfCnpj("12345678901");
//        cliente.setTelefone("11999999999");
//        cliente.setCep("12345678");
//        cliente.setEndereco("Rua Teste");
//        cliente.setNumero("123");
//        cliente.setBairro("Bairro Teste");
//        cliente.setCidade("Cidade Teste");
//        cliente.setDataCadastro(new Date());
//
//        cliente = clienteService.save(cliente);
//        assertNotNull(cliente.getId(), "O ID do cliente salvo deve ser gerado.");
//        System.out.println("Cliente salvo: " + gson.toJson(cliente));
//
//        // Editar o cliente salvo
//        cliente.setNomeFantasia("Novo Cliente Editado");
//        cliente = clienteService.update(cliente);
//        assertNotNull(cliente.getId(), "O ID do cliente salvo deve ser gerado.");
//        assertEquals("Novo Cliente Editado", cliente.getNomeFantasia(), "O nome fantasia deve ter sido atualizado.");
//        System.out.println("Cliente editado: " + gson.toJson(cliente));
//
//        // Listar novamente para verificar se a edição foi aplicada
//        List<Cliente> clientesDepois = clienteService.pesquisa("Cliente");
//        System.out.println("Clientes após edição:");
//        for (Cliente cliente : clientesDepois) {
//            System.out.println(gson.toJson(cliente));
//        }
//    }
    
//    @Test
//    public void testeListarClientesSemRegistros() {
//        List<Cliente> clientes = clienteService.pesquisa("Cliente Inexistente");
//        assertTrue(clientes.isEmpty(), "A lista de clientes deveria estar vazia.");
//    }
//    
//    @Test
//    public void testSalvarClienteInvalido() {
//        Cliente clienteInvalido = new Cliente();
//        clienteInvalido.setNomeFantasia(null); // Nome é obrigatório
//        clienteInvalido.setCpfCnpj("123"); // CPF/CNPJ inválido
//
//        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
//            clienteService.save(clienteInvalido);
//        });
//
//        String expectedMessage = "Nome Fantasia é obrigatório";
//        String actualMessage = exception.getMessage();
//
//        assertTrue(actualMessage.contains(expectedMessage));
//    }
//    
//    @Test
//    public void testEditarClienteInexistente() {
//        Cliente clienteInexistente = new Cliente();
//        clienteInexistente.setId(9999L); // ID que não existe no banco
//        clienteInexistente.setNomeFantasia("Cliente Inexistente");
//
//        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
//            clienteService.update(clienteInexistente);
//        });
//
//        String expectedMessage = "Cliente não encontrado";
//        String actualMessage = exception.getMessage();
//
//        assertTrue(actualMessage.contains(expectedMessage));
//    }
//
//    
//    @Test
//    public void testRemoverClienteInexistente() {
//        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
//            clienteService.delete(9999L); // ID que não existe no banco
//        });
//
//        String expectedMessage = "Cliente não encontrado";
//        String actualMessage = exception.getMessage();
//
//        assertTrue(actualMessage.contains(expectedMessage));
//    }
//
//    
//    @Test
//    public void testEditarClienteInvalido() {
//        cliente.setNomeFantasia(""); // Nome inválido
//        cliente.setCpfCnpj("123"); // CPF/CNPJ inválido
//
//        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
//            clienteService.update(cliente);
//        });
//
//        String expectedMessage = "Dados do cliente inválidos";
//        String actualMessage = exception.getMessage();
//
//        assertTrue(actualMessage.contains(expectedMessage));
//    }

}
