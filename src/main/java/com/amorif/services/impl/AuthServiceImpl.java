package com.amorif.services.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amorif.config.security.JWTTokenProvider;
import com.amorif.dto.request.RegisterDtoRequest;
import com.amorif.dto.request.SUAPUserDtoRequest;
import com.amorif.dto.response.AuthenticationDtoResponse;
import com.amorif.entities.Role;
import com.amorif.entities.RoleEnum;
import com.amorif.entities.Token;
import com.amorif.entities.User;
import com.amorif.exceptions.InvalidJWTAuthenticationException;
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

	@Value("${SUAP_TOKEN_URL}")
	private String SUAP_TOKEN_URL;

	@Value("${SUAP_CLIENT_ID}")
	private String SUAP_CLIENT_ID;

	@Value("${SUAP_CLIENT_SECRET}")
	private String SUAP_CLIENT_SECRET;

	@Value("${SUAP_REDIRECT_URI}")
	private String SUAP_REDIRECT_URI;

	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final TokenRepository tokenRepository;
	private final JWTTokenProvider jwtTokenProvider;

	public AuthServiceImpl(UserRepository userRepository, RoleRepository roleRepository,
			TokenRepository tokenRepository, JWTTokenProvider jwtTokenProvider) {
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
		String token = jwtTokenProvider.getStringAccessToken(user.getMatricula(),
				user.getFuncoes().stream().map(Role::getName).collect(Collectors.toList()));
		String refToken = jwtTokenProvider.getStringRefreshToken(user.getMatricula(),
				user.getFuncoes().stream().map(Role::getName).collect(Collectors.toList()));
		saveUserToken(user, token);

		return AuthenticationDtoResponse.builder().accessToken(token).refreshToken(refToken)
				.matricula(user.getMatricula()).nome(user.getNome()).build();
	}

	@Override
	public RegisterDtoRequest getTokenFromCode(String code) {
		
//		Get the suap token from the code
		CloseableHttpClient httpclient = HttpClients.createDefault();
		RegisterDtoRequest dto = null;

		try {
			HttpPost post = new HttpPost(this.SUAP_TOKEN_URL);
			post.addHeader("Content-Type", "application/x-www-form-urlencoded");
			post.addHeader("Accept", "application/json");

			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("client_id", this.SUAP_CLIENT_ID));
			params.add(new BasicNameValuePair("client_secret", this.SUAP_CLIENT_SECRET));
			params.add(new BasicNameValuePair("code", code));
			params.add(new BasicNameValuePair("grant_type", "authorization_code"));
			post.setEntity(new UrlEncodedFormEntity(params));

			ResponseHandler<String> responseHandler = new ResponseHandler<String>() {

				@Override
				public String handleResponse(final HttpResponse response) throws ClientProtocolException, IOException {
					int status = response.getStatusLine().getStatusCode();
					if (status >= 200 && status < 300) {
						HttpEntity entity = response.getEntity();
						return entity != null ? EntityUtils.toString(entity) : null;
					} else {
						throw new InvalidJWTAuthenticationException("Credenciais inválidas!");
					}
				}
			};

			String response = httpclient.execute(post, responseHandler);

			ObjectMapper mapper = new ObjectMapper();
			dto = mapper.readValue(response, RegisterDtoRequest.class);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return dto;
	}

	@Override
	public SUAPUserDtoRequest getSuapUser(String token) {
		
//		Get the user info from the token
		
		CloseableHttpClient httpclient = HttpClients.createDefault();
		SUAPUserDtoRequest dto = null;

		try {
			HttpGet get = new HttpGet(SUAP_USER_INFO_URL);
			get.addHeader("Accept", "application/json");
			get.addHeader("Authorization", "Bearer " + token);

			ResponseHandler<String> responseHandler = new ResponseHandler<String>() {

				@Override
				public String handleResponse(final HttpResponse response) throws ClientProtocolException, IOException {
					int status = response.getStatusLine().getStatusCode();
					if (status >= 200 && status < 300) {
						HttpEntity entity = response.getEntity();
						return entity != null ? EntityUtils.toString(entity) : null;
					} else {
						throw new InvalidJWTAuthenticationException("Credenciais inválidas!");
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

	@Override
	public AuthenticationDtoResponse authenticate(SUAPUserDtoRequest dto) {
		User user = this.userRepository.findByMatricula(dto.getMatricula()).orElseThrow();
		String token = jwtTokenProvider.getStringAccessToken(user.getMatricula(),
				user.getFuncoes().stream().map(Role::getName).collect(Collectors.toList()));
		String refToken = jwtTokenProvider.getStringRefreshToken(user.getMatricula(),
				user.getFuncoes().stream().map(Role::getName).collect(Collectors.toList()));

//		Revoke all other tokens
		revokeAllUserTokens(user);
		saveUserToken(user, token);

		return AuthenticationDtoResponse.builder().accessToken(token).refreshToken(refToken)
				.matricula(user.getMatricula()).nome(user.getNome()).build();
	}

	private void saveUserToken(User user, String jwtToken) {
		Token token = Token.builder().user(user).token(jwtToken).tokenType("BEARER").expired(false).revoked(false)
				.build();
		tokenRepository.save(token);
	}

	private void revokeAllUserTokens(User user) {
		List<Token> validTokens = tokenRepository.findAllValidTokensByUser(user.getId());
		if (validTokens.isEmpty()) {
			return;
		}
		for (Token token : validTokens) {
			token.setExpired(true);
			token.setRevoked(true);
		}
		tokenRepository.saveAll(validTokens);
	}

}
