package com.amorif.services.impl;

import java.io.IOException;
import java.util.stream.Collectors;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amorif.config.security.JWTTokenProvider;
import com.amorif.dto.request.SUAPUserDtoRequest;
import com.amorif.dto.response.AuthenticationDtoResponse;
import com.amorif.entities.Role;
import com.amorif.entities.RoleEnum;
import com.amorif.entities.Token;
import com.amorif.entities.User;
import com.amorif.repository.RoleRepository;
import com.amorif.repository.TokenRepository;
import com.amorif.repository.UserRepository;
import com.amorif.services.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author osailton
 */

@Service
public class AuthServiceImpl implements AuthService {

	@Value("${SUAP_USER_INFO_URL}")
	private String SUAP_USER_INFO_URL;

	private UserRepository userRepository;
	private RoleRepository roleRepository;
	private TokenRepository tokenRepository;
	private JWTTokenProvider jwtTokenProvider;

	public AuthServiceImpl(UserRepository userRepository, RoleRepository roleRepository, TokenRepository tokenRepository, JWTTokenProvider jwtTokenProvider) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.tokenRepository = tokenRepository;
		this.jwtTokenProvider = jwtTokenProvider;
	}

	@Override
	public AuthenticationDtoResponse register(SUAPUserDtoRequest dto) {
		User user = new User().builder().matricula(dto.getMatricula()).nome(dto.getNomeUsual()).build();
		
		switch (dto.getTipoVinculo()) {
		case "Servidor": {
			user.getFuncoes().add(this.roleRepository.getByName(RoleEnum.ROLE_SERV.toString()));
			break;
		}
		case "Aluno": {
			user.getFuncoes().add(this.roleRepository.getByName(RoleEnum.ROLE_ALUNO.toString()));
			break;
		}
		case "Prestador de Serviço": {
			user.getFuncoes().add(this.roleRepository.getByName(RoleEnum.ROLE_PSERV.toString()));
			break;
		}
		default:
			user.getFuncoes().add(this.roleRepository.getByName(RoleEnum.ROLE_PSERV.toString()));
			break;
		}
		
		this.userRepository.save(user);
		
		// Generate token
		String token = jwtTokenProvider.getStringAccessToken(user.getMatricula(), user.getFuncoes().stream().map(Role::getName).collect(Collectors.toList()));
		String refToken = jwtTokenProvider.getStringRefreshToken(user.getMatricula(), user.getFuncoes().stream().map(Role::getName).collect(Collectors.toList()));
		saveUserToken(user, token);
		
		return AuthenticationDtoResponse.builder()
				.accessToken(token)
				.refreshToken(refToken)
				.matricula(user.getMatricula())
				.nome(user.getNome())
				.build();
	}

	@Override
	public SUAPUserDtoRequest getSuapUser(String token) {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		SUAPUserDtoRequest dto = null;

		try {
			HttpGet get = new HttpGet(SUAP_USER_INFO_URL);
			get.addHeader("Accept", "application/json");
			get.addHeader("Authorization", "Bearer " + token);

			System.out.println("Executing request " + get.getRequestLine());

			ResponseHandler<String> responseHandler = new ResponseHandler<String>() {

				@Override
				public String handleResponse(final HttpResponse response) throws ClientProtocolException, IOException {
					int status = response.getStatusLine().getStatusCode();
					if (status >= 200 && status < 300) {
						HttpEntity entity = response.getEntity();
						return entity != null ? EntityUtils.toString(entity) : null;
					} else {
						throw new ClientProtocolException("Unexpected response status: " + status);
					}
				}

			};
			String response = httpclient.execute(get, responseHandler);

			ObjectMapper mapper = new ObjectMapper();
			dto = mapper.readValue(response, SUAPUserDtoRequest.class);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				httpclient.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return dto;
	}

	@Override
	public boolean existsByMatricula(String matricula) {
		return !this.userRepository.findByMatricula(matricula).isEmpty();
	}
	
	private void saveUserToken(User user, String jwtToken) {
		Token token = Token.builder()
				.user(user)
				.token(jwtToken)
				.tokenType("BEARER")
				.expired(false)
				.revoked(false)
				.build();
		tokenRepository.save(token);
	}

}
