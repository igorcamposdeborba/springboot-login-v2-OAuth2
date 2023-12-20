package com.devsuperior.bds04.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableAuthorizationServer // Esta classe representa o servidor de autenticação do OAuth2
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

	@Value("${security.oauth2.client.client-id}")
	private String clientId;
	@Value("${security.oauth2.client.client-secret}")
	private String clientSecret;
	@Value("${jwt.duration}")
	private Integer jwtDuration;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder; // @Bean está no AppConfig.  			   Codifica senha
	
	@Autowired
	private JwtAccessTokenConverter accessTokenConverter; // @Bean está no AppConfig.java.     Pegar chave do TokenJWT
	
	@Autowired
	private JwtTokenStore tokenStore; // @Bean está no AppConfig.java.  				       Codifica e decodifica token
	
	@Autowired
	private AuthenticationManager authenticationManager; // @Bean está no SecurityConfig.java  Gerencia a autenticação
	
	
	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		security.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
		super.configure(security);
	}
	
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception { // credenciais da Aplicação
		clients.inMemory()
		.withClient(clientId) // nome da Aplicação, tem que ser o mesmo nome no front-end
		.secret(passwordEncoder.encode(clientSecret)) // senha da aplicação, tem que ser o mesmo nome no front-end 
		.scopes("read", "write") // operações permitidas para ler e gerar o token
		.authorizedGrantTypes("password") // identifica que o tipo é password
		.accessTokenValiditySeconds(jwtDuration); // expira a autenticação em 1 dia
	}
	
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception { // define quem autoriza e o formato do token		
		endpoints.authenticationManager(authenticationManager) // Gerencia a autenticação
				 .tokenStore(tokenStore) 					   // Codifica e decodifica token
				 .accessTokenConverter(accessTokenConverter);  // Pegar chave do TokenJWT
	}
}
