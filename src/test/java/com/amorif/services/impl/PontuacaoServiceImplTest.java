package com.amorif.services.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.amorif.config.security.JWTTokenProviderMock;
import com.amorif.config.security.TestSecurityConfig;
import com.amorif.dto.request.PontuacaoDtoRequest;
import com.amorif.dto.response.PontuacaoDtoResponse;
import com.amorif.entities.AnoLetivo;
import com.amorif.entities.FrequenciaRegraEnum;
import com.amorif.entities.Pontuacao;
import com.amorif.entities.Regra;
import com.amorif.entities.Role;
import com.amorif.entities.TipoRegra;
import com.amorif.entities.Turma;
import com.amorif.entities.TurnoEnum;
import com.amorif.exceptions.AnnualRuleException;
import com.amorif.exceptions.BimonthlyRuleException;
import com.amorif.exceptions.InvalidBimesterException;
import com.amorif.exceptions.InvalidExtraBimesterException;
import com.amorif.exceptions.InvalidFixedValueException;
import com.amorif.exceptions.InvalidTurnException;
import com.amorif.exceptions.UserHasNoPermitedRoleException;
import com.amorif.repository.AnoLetivoRepository;
import com.amorif.repository.PontuacaoRepository;
import com.amorif.repository.RegraRepository;
import com.amorif.repository.TokenRepository;
import com.amorif.repository.TurmaRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@ActiveProfiles("test")
@WebMvcTest(PontuacaoServiceImpl.class)
@Import({TestSecurityConfig.class, JWTTokenProviderMock.class})
public class PontuacaoServiceImplTest {

    @InjectMocks
    private PontuacaoServiceImpl pontuacaoService;

    @MockBean
    private TurmaRepository turmaRepository;

    @MockBean
    private RegraRepository regraRepository;
    
    @MockBean
    private TokenRepository tokenRepository;

    @MockBean
    private PontuacaoRepository pontuacaoRepository;
    
    @MockBean
    private AnoLetivoRepository anoLetivoRepository;

    private Turma turma;
    private Turma turma2;
    private Turma turma3;
    private Turma turma4;
    private Regra regra;
    private PontuacaoDtoRequest dtoRequest;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        turma = new Turma();
        turma.setId(1L);
        turma.setTurno(TurnoEnum.MATUTINO.ordinal());
        
        turma2 = new Turma();
        turma2.setId(1L);
        turma2.setTurno(TurnoEnum.MATUTINO.ordinal());
        
        turma3 = new Turma();
        turma3.setId(1L);
        turma3.setTurno(TurnoEnum.VESPERTINO.ordinal());
        
        turma4 = new Turma();
        turma4.setId(1L);
        turma4.setTurno(TurnoEnum.VESPERTINO.ordinal());    
        
        

        Role roleWithPermission = new Role();
        roleWithPermission.setName("ROLE_APOIO_ACADEMICO");

        regra = new Regra();
        regra.setId(1L);
        regra.setValorMinimo(0);
        regra.setValorMaximo(500);
        regra.setRoles(Arrays.asList(roleWithPermission));
        
        TipoRegra tr1 = TipoRegra.builder().id(1L).frequencia(FrequenciaRegraEnum.AVULSO.ordinal()).bimestreExtra(false).build();
        
        regra.setTipoRegra(tr1);
        
        AnoLetivo anoAtual = AnoLetivo.builder().aberto(true).ano(2023).id(1L).build();
        
        dtoRequest = new PontuacaoDtoRequest();
        dtoRequest.setIdTurma(1L);
        dtoRequest.setIdRegra(1L);
        dtoRequest.setPontos(10);
        dtoRequest.setMotivacao("Motivação Teste");
        dtoRequest.setBimestre(0);

        when(anoLetivoRepository.getLastActiveAnoLetivo()).thenReturn(anoAtual);
        when(turmaRepository.getReferenceById(dtoRequest.getIdTurma())).thenReturn(turma);
        when(turmaRepository.findAllByTurno(TurnoEnum.MATUTINO.ordinal())).thenReturn(Arrays.asList(turma, turma2));
        when(regraRepository.getReferenceById(dtoRequest.getIdRegra())).thenReturn(regra);
        when(pontuacaoRepository.save(any(Pontuacao.class))).thenAnswer(invocation -> invocation.getArgument(0));
    }

    @Test
    public void testThrowPoints_UserWithPermission_ShouldRegisterPoints() {
        // Mockando o contexto de segurança com uma role que permite o lançamento
        User userWithPermission = new User("user", "password", 
                Collections.singleton(new SimpleGrantedAuthority("ROLE_APOIO_ACADEMICO")));
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(userWithPermission, null, userWithPermission.getAuthorities())
        );

        PontuacaoDtoResponse response = pontuacaoService.throwPoints(dtoRequest).getFirst();

        // Verifica se o lançamento foi feito com sucesso
        assertNotNull(response);
        assertEquals(dtoRequest.getPontos(), response.getPontos());
    }

    @Test
    public void testThrowPoints_UserWithoutPermission_ShouldNotRegisterPoints() {
        // Mockando o contexto de segurança com uma role que NÃO permite o lançamento
        User userWithoutPermission = new User("user", "password", 
                Collections.singleton(new SimpleGrantedAuthority("ROLE_BIBLIOTECARIO")));
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(userWithoutPermission, null, userWithoutPermission.getAuthorities())
        );

        // Verifica se o lançamento falha ao usuário não ter permissão
        assertThrows(UserHasNoPermitedRoleException.class, () -> {
            pontuacaoService.throwPoints(dtoRequest);
        });
    }
    
    @Test
    public void testThrowPoints_BimesterValid_ShouldRegisterPoints() {
        // Mockando o contexto de segurança com uma role que permite o lançamento
        User userWithPermission = new User("user", "password", 
                Collections.singleton(new SimpleGrantedAuthority("ROLE_APOIO_ACADEMICO")));
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(userWithPermission, null, userWithPermission.getAuthorities())
        );

        PontuacaoDtoResponse response = pontuacaoService.throwPoints(dtoRequest).getFirst();

        // Verifica se o lançamento foi feito com sucesso
        assertNotNull(response);
        assertEquals(dtoRequest.getBimestre(), response.getBimestre());
    }
    
    @Test
    public void testThrowPoints_BimesterInvalid_ShouldNotRegisterPoints() {
        // Mockando o contexto de segurança com uma role que permite o lançamento
        User userWithPermission = new User("user", "password", 
                Collections.singleton(new SimpleGrantedAuthority("ROLE_APOIO_ACADEMICO")));
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(userWithPermission, null, userWithPermission.getAuthorities())
        );
        
        dtoRequest.setBimestre(10);

        // Verifica se o lançamento falha com bimestre inválido
        assertThrows(InvalidBimesterException.class, () -> {
            pontuacaoService.throwPoints(dtoRequest);
        });
    }
    
    @Test
    public void testThrowPoints_ExtraBimesterValid_ShouldRegisterPoints() {
        // Mockando o contexto de segurança com uma role que permite o lançamento
        User userWithPermission = new User("user", "password", 
                Collections.singleton(new SimpleGrantedAuthority("ROLE_APOIO_ACADEMICO")));
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(userWithPermission, null, userWithPermission.getAuthorities())
        );
        
        TipoRegra tr2 = TipoRegra.builder().id(1L).bimestreExtra(true).frequencia(FrequenciaRegraEnum.AVULSO.ordinal()).build();
        regra.setTipoRegra(tr2);
        dtoRequest.setBimestre(4);

        PontuacaoDtoResponse response = pontuacaoService.throwPoints(dtoRequest).getFirst();;

        // Verifica se o lançamento foi feito com sucesso
        assertNotNull(response);
        assertEquals(dtoRequest.getBimestre(), response.getBimestre());
    }
    
    @Test
    public void testThrowPoints_ExtraBimesterInvalid_ShouldNotRegisterPoints() {
        // Mockando o contexto de segurança com uma role que permite o lançamento
        User userWithPermission = new User("user", "password", 
                Collections.singleton(new SimpleGrantedAuthority("ROLE_APOIO_ACADEMICO")));
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(userWithPermission, null, userWithPermission.getAuthorities())
        );
        
        TipoRegra tr2 = TipoRegra.builder().id(1L).bimestreExtra(true).build();
        regra.setTipoRegra(tr2);
        dtoRequest.setBimestre(0);

        // Verifica se o lançamento falha com bimestre inválido
        assertThrows(InvalidExtraBimesterException.class, () -> {
            pontuacaoService.throwPoints(dtoRequest);
        });
    }
    
    @Test
    public void testThrowPoints_FixedValueValid_ShouldRegisterPoints() {
        // Mockando o contexto de segurança com uma role que permite o lançamento
        User userWithPermission = new User("user", "password", 
                Collections.singleton(new SimpleGrantedAuthority("ROLE_APOIO_ACADEMICO")));
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(userWithPermission, null, userWithPermission.getAuthorities())
        );
        
        TipoRegra tr2 = TipoRegra.builder().id(1L).fixo(true).frequencia(FrequenciaRegraEnum.AVULSO.ordinal()).build();
        regra.setTipoRegra(tr2);
        regra.setValorMinimo(10);
        dtoRequest.setBimestre(0);
        dtoRequest.setPontos(10);

        PontuacaoDtoResponse response = pontuacaoService.throwPoints(dtoRequest).getFirst();

        // Verifica se o lançamento foi feito com sucesso
        assertNotNull(response);
        assertEquals(dtoRequest.getPontos(), response.getPontos());
    }
    
    @Test
    public void testThrowPoints_FixedValueInvalid_ShouldNotRegisterPoints() {
    	// Mockando o contexto de segurança com uma role que permite o lançamento
        User userWithPermission = new User("user", "password", 
                Collections.singleton(new SimpleGrantedAuthority("ROLE_APOIO_ACADEMICO")));
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(userWithPermission, null, userWithPermission.getAuthorities())
        );
        
        TipoRegra tr2 = TipoRegra.builder().id(1L).fixo(true).build();
        regra.setTipoRegra(tr2);
        regra.setValorMinimo(10);
        dtoRequest.setBimestre(0);
        dtoRequest.setPontos(8);

        // Verifica se o lançamento falha com pontos inválidos
        assertThrows(InvalidFixedValueException.class, () -> {
            pontuacaoService.throwPoints(dtoRequest);
        });
    }
    
    @Test
    public void testThrowPoints_PerBimester_ShouldRegisterPoints() {
        // Mockando o contexto de segurança com uma role que permite o lançamento
        User userWithPermission = new User("user", "password", 
                Collections.singleton(new SimpleGrantedAuthority("ROLE_APOIO_ACADEMICO")));
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(userWithPermission, null, userWithPermission.getAuthorities())
        );
        
        // Mockando o comportamento do repository
        when(pontuacaoRepository.existsByBimesterAndRule(0, regra.getId(), turma.getId())).thenReturn(false);

        // Configurando a regra e o DTO
        TipoRegra tr2 = TipoRegra.builder().id(1L).fixo(true).frequencia(FrequenciaRegraEnum.BIMESTRAL.ordinal()).build();
        regra.setTipoRegra(tr2);
        regra.setValorMinimo(10);
        dtoRequest.setBimestre(0);
        dtoRequest.setPontos(10);

        PontuacaoDtoResponse response = pontuacaoService.throwPoints(dtoRequest).getFirst();

        // Verifica se o lançamento foi feito com sucesso
        assertNotNull(response);
        assertEquals(dtoRequest.getBimestre(), response.getBimestre());
    }
    
    @Test
    public void testThrowPoints_PerBimester_ShouldNotRegisterPoints() {
        // Mockando o contexto de segurança com uma role que permite o lançamento
        User userWithPermission = new User("user", "password", 
                Collections.singleton(new SimpleGrantedAuthority("ROLE_APOIO_ACADEMICO")));
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(userWithPermission, null, userWithPermission.getAuthorities())
        );

        // Mockando o comportamento do repository
        when(pontuacaoRepository.existsByBimesterAndRule(0, regra.getId(), turma.getId())).thenReturn(true);

        // Configurando a regra e o DTO
        TipoRegra tr2 = TipoRegra.builder().id(1L).fixo(true).frequencia(FrequenciaRegraEnum.BIMESTRAL.ordinal()).build();
        regra.setTipoRegra(tr2);
        regra.setValorMinimo(10);
        dtoRequest.setBimestre(0);
        dtoRequest.setPontos(10);

        // Verifica se a exceção BimonthlyRuleException é lançada
        assertThrows(BimonthlyRuleException.class, () -> {
            pontuacaoService.throwPoints(dtoRequest);
        });

        // Verifica se o método do repositório foi chamado com os parâmetros corretos
        verify(pontuacaoRepository).existsByBimesterAndRule(0, regra.getId(), turma.getId());
    }

    @Test
    public void testThrowPoints_PerYear_ShouldRegisterPoints() {
        // Mockando o contexto de segurança com uma role que permite o lançamento
        User userWithPermission = new User("user", "password", 
                Collections.singleton(new SimpleGrantedAuthority("ROLE_APOIO_ACADEMICO")));
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(userWithPermission, null, userWithPermission.getAuthorities())
        );
        
        // Mockando o comportamento do repository
        when(pontuacaoRepository.existsByYearAndRule(1L, regra.getId(), turma.getId())).thenReturn(false);

        // Configurando a regra e o DTO
        TipoRegra tr2 = TipoRegra.builder().id(1L).fixo(true).frequencia(FrequenciaRegraEnum.ANUAL.ordinal()).build();
        regra.setTipoRegra(tr2);
        regra.setValorMinimo(10);
        dtoRequest.setBimestre(0);
        dtoRequest.setPontos(10);

        PontuacaoDtoResponse response = pontuacaoService.throwPoints(dtoRequest).getFirst();

        // Verifica se o lançamento foi feito com sucesso
        assertNotNull(response);
        assertEquals(dtoRequest.getBimestre(), response.getBimestre());
    }
    
    @Test
    public void testThrowPoints_PerYear_ShouldNotRegisterPoints() {
        // Mockando o contexto de segurança com uma role que permite o lançamento
        User userWithPermission = new User("user", "password", 
                Collections.singleton(new SimpleGrantedAuthority("ROLE_APOIO_ACADEMICO")));
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(userWithPermission, null, userWithPermission.getAuthorities())
        );

        // Mockando o comportamento do repository
        when(pontuacaoRepository.existsByYearAndRule(1L, regra.getId(), turma.getId())).thenReturn(true);

        // Configurando a regra e o DTO
        TipoRegra tr2 = TipoRegra.builder().id(1L).fixo(true).frequencia(FrequenciaRegraEnum.ANUAL.ordinal()).build();
        regra.setTipoRegra(tr2);
        regra.setValorMinimo(10);
        dtoRequest.setBimestre(0);
        dtoRequest.setPontos(10);

        // Verifica se a exceção BimonthlyRuleException é lançada
        assertThrows(AnnualRuleException.class, () -> {
            pontuacaoService.throwPoints(dtoRequest);
        });

        // Verifica se o método do repositório foi chamado com os parâmetros corretos
        verify(pontuacaoRepository).existsByYearAndRule(1L, regra.getId(), turma.getId());
    }
    
    @Test
    public void testThrowPoints_PerTurn_ShouldRegisterPoints() {
        // Mockando o contexto de segurança com uma role que permite o lançamento
        User userWithPermission = new User("user", "password", 
                Collections.singleton(new SimpleGrantedAuthority("ROLE_APOIO_ACADEMICO")));
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(userWithPermission, null, userWithPermission.getAuthorities())
        );

        // Mockando o comportamento do repository
        when(pontuacaoRepository.existsByYearAndRule(1L, regra.getId(), turma.getId())).thenReturn(true);

        // Configurando a regra e o DTO
        TipoRegra tr2 = TipoRegra.builder().id(1L).fixo(true).frequencia(FrequenciaRegraEnum.AVULSO.ordinal()).porTurno(true).build();
        regra.setTipoRegra(tr2);
        regra.setValorMinimo(10);
        dtoRequest.setBimestre(0);
        dtoRequest.setPontos(10);
        dtoRequest.setTurno(TurnoEnum.MATUTINO.ordinal());

        List<PontuacaoDtoResponse> response = pontuacaoService.throwPoints(dtoRequest);

        // Verifica se o lançamento foi feito com sucesso
        assertNotNull(response);
        assertEquals(2, response.size());
    }
    
    @Test
    public void testThrowPoints_PerTurn_ShouldNotRegisterPoints() {
        // Mockando o contexto de segurança com uma role que permite o lançamento
        User userWithPermission = new User("user", "password", 
                Collections.singleton(new SimpleGrantedAuthority("ROLE_APOIO_ACADEMICO")));
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(userWithPermission, null, userWithPermission.getAuthorities())
        );

        // Mockando o comportamento do repository
        when(pontuacaoRepository.existsByYearAndRule(1L, regra.getId(), turma.getId())).thenReturn(true);

        // Configurando a regra e o DTO
        TipoRegra tr2 = TipoRegra.builder().id(1L).fixo(true).frequencia(FrequenciaRegraEnum.AVULSO.ordinal()).porTurno(true).build();
        regra.setTipoRegra(tr2);
        regra.setValorMinimo(10);
        dtoRequest.setBimestre(0);
        dtoRequest.setPontos(10);
        dtoRequest.setTurno(98);

        // Verifica se a exceção BimonthlyRuleException é lançada
        assertThrows(InvalidTurnException.class, () -> {
            pontuacaoService.throwPoints(dtoRequest);
        });
    }

}
