package br.com.fiap.pedidos.service;

import br.com.fiap.pedidos.api.model.PedidoDto;
import br.com.fiap.pedidos.config.MessageConfig;
import br.com.fiap.pedidos.dados.PedidoDados;
import br.com.fiap.pedidos.domain.exception.PedidoNaoEncontradoException;
import br.com.fiap.pedidos.domain.model.Pedido;
import br.com.fiap.pedidos.domain.repository.PedidoRepository;
import br.com.fiap.pedidos.domain.service.PedidoService;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class PedidoServiceTest extends PedidoDados {

    private AutoCloseable closeable;

    @Mock
    private PedidoRepository pedidoRepository;

    @InjectMocks
    private PedidoService pedidoService;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private MessageConfig messageConfig;

    @BeforeEach
    void setup() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void closeService() throws Exception {
        closeable.close();
    }

    @Nested
    class adicionarPedido {

        @Test
        void deveAdicionarPedido_ComSucesso() {
            var pedido = criarPedido1();
            var pedidoDto = criarPedidoDto1();

            when(modelMapper.map(pedidoDto, Pedido.class)).thenReturn(pedido);

            pedidoService.add(pedidoDto);

            verify(pedidoRepository, times(1)).save(pedido);
            verify(modelMapper, times(1)).map(pedidoDto, Pedido.class);
        }
    }

    @Nested
    class buscarPedidoPorId {
        @Test
        void deveBuscarPedidoPorId_ComSucesso() {
            Long id = 1L;
            Pedido pedido = criarPedido1();
            PedidoDto pedidoDto = criarPedidoDto1();

            when(pedidoRepository.findById(id)).thenReturn(Optional.of(pedido));
            when(modelMapper.map(pedido, PedidoDto.class)).thenReturn(pedidoDto);

            PedidoDto resultDto = pedidoService.getPedidoById(id);

            assertEquals(pedidoDto, resultDto);
            verify(pedidoRepository, times(1)).findById(id);
            verify(modelMapper, times(1)).map(pedido, PedidoDto.class);
        }

        @Test
        void aoBuscarPedidoQueNaoExiste_DeveLancarThrowException() {
            Long id = 1L;

            when(pedidoRepository.findById(id)).thenReturn(Optional.empty());
            when(messageConfig.getPedidoNaoEncontrado()).thenReturn("Pedido não encontrado");

            assertThrows(PedidoNaoEncontradoException.class, () -> pedidoService.getPedidoById(id));
            verify(pedidoRepository, times(1)).findById(id);
            verifyNoMoreInteractions(modelMapper);
        }
    }

    @Nested
    class buscarTodosPedidos {

        @Test
        void deveBuscarTodosPedidos_ComSucesso() {
            Pedido pedido1 = criarPedido1();
            Pedido pedido2 = criarPedido2();
            List<Pedido> pedidos = Arrays.asList(pedido1, pedido2);

            PedidoDto pedidoDto1 = criarPedidoDto1();
            PedidoDto pedidoDto2 = criarPedidoDto2();
            List<PedidoDto> expectedDtos = Arrays.asList(pedidoDto1, pedidoDto2);

            when(pedidoRepository.findAll()).thenReturn(pedidos);
            when(modelMapper.map(pedido1, PedidoDto.class)).thenReturn(pedidoDto1);
            when(modelMapper.map(pedido2, PedidoDto.class)).thenReturn(pedidoDto2);

            List<PedidoDto> resultDtos = pedidoService.findAll();

            // Assert
            assertEquals(expectedDtos.size(), resultDtos.size());
            for (int i = 0; i < expectedDtos.size(); i++) {
                assertEquals(expectedDtos.get(i), resultDtos.get(i));
            }
            verify(pedidoRepository, times(1)).findAll();
        }
    }
}
