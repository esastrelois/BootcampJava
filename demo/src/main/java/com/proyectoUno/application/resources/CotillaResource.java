package com.proyectoUno.application.resources;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.proyectoUno.application.proxies.CatalogoProxy;
import com.proyectoUno.application.proxies.FilmProxy;
import com.proyectoUno.domains.entities.models.FilmShortDTO;

import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.micrometer.observation.annotation.Observed;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@Observed
@RefreshScope
@RestController
@RequestMapping(path = "/cotilla")
public class CotillaResource {
	@Autowired
	private EurekaClient discoveryEurekaClient;

	@GetMapping(path = "/descubre/eureka/{nombre}")
	public InstanceInfo serviceEurekaUrl(String nombre) {
	    InstanceInfo instance = discoveryEurekaClient.getNextServerFromEureka(nombre, false);
	    return instance; //.getHomePageUrl();
	}
	
	@Autowired
	private DiscoveryClient discoveryClient;

	@GetMapping(path = "/descubre/cloud/{nombre}")
	public List<ServiceInstance> serviceUrl(String nombre) {
	    return discoveryClient.getInstances(nombre);
	}
	
	@Autowired
	RestTemplate restTemplate;

	@Autowired
	@LoadBalanced
	RestTemplate restTemplateLB;

	@GetMapping(path = "/consulta/pelis/rt")
	public List<FilmShortDTO> getPelisRT(@RequestParam(defaultValue = "http")
	@Parameter(in = ParameterIn.QUERY, name = "mode", required = false, description = "http: Directo a URL, lb: Balanceo de carga", 
	schema = @Schema(type = "string", allowableValues = {"http", "lb" }, defaultValue = "http")) String mode) {
		ResponseEntity<List<FilmShortDTO>> response = ("http".equals(mode)?restTemplate:restTemplateLB).exchange(
				("http".equals(mode)?"http://localhost:8010":"lb://CATALOGO-SERVICE") + "/peliculas/v1?mode=short", 
				HttpMethod.GET,
				HttpEntity.EMPTY, 
				new ParameterizedTypeReference<List<FilmShortDTO>>() {}
		);
		return response.getBody();
	}
	@GetMapping(path = "/consulta/pelis/{id}/rt")
	public FilmShortDTO getPelisRT(@PathVariable int id, @RequestParam(defaultValue = "http")
	@Parameter(in = ParameterIn.QUERY, name = "mode", required = false, description = "http: Directo a URL, lb: Balanceo de carga", 
			schema = @Schema(type = "string", allowableValues = {"http", "lb" }, defaultValue = "http")) String mode) {
		if("http".equals(mode))
			return restTemplate.getForObject("http://localhost:8010/peliculas/v1/{key}?mode=short", FilmShortDTO.class, id);
		else
			return restTemplateLB.getForObject("lb://CATALOGO-SERVICE/peliculas/v1/{key}?mode=short", FilmShortDTO.class, id);
	}
	@GetMapping(path = "/balancea/rt")
	@SecurityRequirement(name = "bearerAuth")
	public List<String> getBalanceoRT() {
		List<String> rslt = new ArrayList<>();
		LocalDateTime inicio = LocalDateTime.now();
		rslt.add("Inicio: " + inicio);
		for(int i = 0; i < 11; i++)
			try {
				LocalTime ini = LocalTime.now();
				rslt.add(restTemplateLB.getForObject("lb://CATALOGO-SERVICE/actuator/info", String.class)
						+ " (" + ini.until(LocalTime.now(), ChronoUnit.MILLIS) + " ms)" );
			} catch (Exception e) {
				rslt.add(e.getMessage());
			}
		LocalDateTime fin = LocalDateTime.now();
		rslt.add("Final: " + fin + " (" + inicio.until(fin, ChronoUnit.MILLIS) + " ms)");		
		return rslt;
	}
	
	@Autowired
	CatalogoProxy proxy;

	@GetMapping(path = "/consulta/pelis/proxy")
	public List<FilmShortDTO> getPelisProxy() {
		return proxy.getPelis();
	}
//	@PreAuthorize("hasRole('ADMINISTRADORES')")
	@SecurityRequirement(name = "bearerAuth")
	@GetMapping(path = "/consulta/pelis/{id}/proxy")
	public FilmShortDTO getPelisProxy(@PathVariable int id) {
		return proxy.getPeli(id);
	}
	@GetMapping(path = "/balancea/proxy")
	public List<String> getBalanceoProxy() {
		List<String> rslt = new ArrayList<>();
		for(int i = 0; i < 11; i++)
			try {
				rslt.add(proxy.getInfo());
			} catch (Exception e) {
				rslt.add(e.getMessage());
			}
		return rslt;
	}

	@Autowired
	private CircuitBreakerFactory cbFactory;
	
	@GetMapping(path = "/circuit-breaker/factory")
	public List<String> getCircuitBreakerFactory() {
		List<String> rslt = new ArrayList<>();
		LocalDateTime inicio = LocalDateTime.now();
		rslt.add("Inicio: " + inicio);
		for(int i = 0; i < 100; i++) {
				LocalTime ini = LocalTime.now();
				rslt.add(cbFactory.create("slow").run(
						() -> restTemplateLB.getForObject("lb://CATALOGO-SERVICE/actuator/info", String.class), 
						throwable -> "fallback: circuito abierto")
						+ " (" + ini.until(LocalTime.now(), ChronoUnit.MILLIS) + " .ms)" );
			}
		LocalDateTime fin = LocalDateTime.now();
		rslt.add("Final: " + fin + " (" + inicio.until(fin, ChronoUnit.MILLIS) + " ms)");		
		return rslt;
	}
	
	@GetMapping(path = "/circuit-breaker/anota")
	@CircuitBreaker(name = "default", fallbackMethod = "fallback")
	public List<String> getCircuitBreakerAnota() {
		List<String> rslt = new ArrayList<>();
		LocalDateTime inicio = LocalDateTime.now();
		rslt.add("Inicio: " + inicio);
		for(int i = 0; i < 100; i++)
			rslt.add(getInfo(LocalTime.now()));
		LocalDateTime fin = LocalDateTime.now();
		rslt.add("Final: " + fin + " (" + inicio.until(fin, ChronoUnit.MILLIS) + " ms)");		
		return rslt;
	}
	
	@CircuitBreaker(name = "default", fallbackMethod = "fallback")
	private String getInfo(LocalTime ini) {
		return restTemplateLB.getForObject("lb://CATALOGO-SERVICE/loteria", String.class)
		+ " (" + ini.until(LocalTime.now(), ChronoUnit.MILLIS) + " ms)";
	}
	private List<String> fallback(CallNotPermittedException e) {
		return List.of("CircuitBreaker is open", e.getCausingCircuitBreakerName(), e.getLocalizedMessage());
	}
	
	private String fallback(LocalTime ini, CallNotPermittedException e) {
		return "CircuitBreaker is open";
	}

	private String fallback(LocalTime ini, Exception e) {
		return "Fallback: " + e.getMessage()
						+ " (" + ini.until(LocalTime.now(), ChronoUnit.MILLIS) + " ms)";
	}
	
	@Value("${valor.ejemplo:Valor por defecto}")
	String config;
	
	@GetMapping(path = "/config", produces = {"text/plain"})
	public String getConfig() {
		return config;
	}

	@Autowired
	FilmProxy proxyExterno;
	@GetMapping(path = "/fotos")
	public List<FilmShortDTO> getFotosProxy() {
		return proxyExterno.getAll();
	}
	

	@SecurityRequirement(name = "bearerAuth")
	@PostMapping(path = "/send/pelis/{id}/like")
	public String getPelisLike(@PathVariable int id, @Parameter(hidden = true) @RequestHeader(required = false) String authorization) {
		if(authorization == null)
			return proxy.meGusta(id);
		var result = proxy.meGusta(id, authorization);
		return result;
	}
	
}
