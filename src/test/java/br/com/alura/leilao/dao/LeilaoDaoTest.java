package br.com.alura.leilao.dao;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.EntityManager;

import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.alura.leilao.model.Leilao;
import br.com.alura.leilao.model.Usuario;
import br.com.alura.leilao.util.JPAUtil;

class LeilaoDaoTest {

	private LeilaoDao leilaoDao;
	
	private EntityManager em;
	
	@BeforeEach
	void beforeEach() {
		em = JPAUtil.getEntityManager();
		leilaoDao = new LeilaoDao(em);
		em.getTransaction().begin();
	}
	
	@AfterEach
	void afterEach() {
		em.getTransaction().rollback();
	}

	@Test
	void deveCadastrarUmLeilao() {
		Usuario usuario = criaUsuario();
		Leilao leilao = new Leilao("Galaxy S20", new BigDecimal("5000"), LocalDate.now(), usuario);
		
		leilao = leilaoDao.salvar(leilao);
		
		Leilao leilaoEncontrado = leilaoDao.buscarPorId(leilao.getId());
		
		Assert.assertNotNull(leilaoEncontrado);
	}
	
	@Test
	void deveAtualizarUmLeilao() {
		Usuario usuario = criaUsuario();
		Leilao leilao = new Leilao("Galaxy S20", new BigDecimal("5000"), LocalDate.now(), usuario);
		
		leilao = leilaoDao.salvar(leilao);
		
		leilao.setNome("I Phone 12");
		leilao.setValorInicial(new BigDecimal("7000"));
		
		leilao = leilaoDao.salvar(leilao);
		
		Leilao leilaoEncontrado = leilaoDao.buscarPorId(leilao.getId());
		
		Assert.assertEquals("I Phone 12", leilaoEncontrado.getNome());
		Assert.assertEquals(new BigDecimal("7000"), leilaoEncontrado.getValorInicial());
	}

	private Usuario criaUsuario() {
		Usuario usuario = new Usuario("fulano", "fulano@email.com", "1234");
		em.persist(usuario);
		return usuario;
	}

}
