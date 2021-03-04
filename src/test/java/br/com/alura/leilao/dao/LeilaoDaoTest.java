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
import br.com.alura.leilao.util.builder.LeilaoBuilder;
import br.com.alura.leilao.util.builder.UsuarioBuilder;

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
		Usuario usuario = new UsuarioBuilder()
								.comNome("Fulano")
								.comEmail("fulano@email.com")
								.comSenha("1234")
								.criar();
		
		em.persist(usuario);
		
		Leilao leilao = new LeilaoBuilder()
								.comNome("Galaxy S20")
								.comValorInicial("5000")
								.comData(LocalDate.now())
								.comUsuario(usuario)
								.criar();
		
		leilao = leilaoDao.salvar(leilao);
		
		Leilao leilaoEncontrado = leilaoDao.buscarPorId(leilao.getId());
		
		Assert.assertNotNull(leilaoEncontrado);
	}
	
	@Test
	void deveAtualizarUmLeilao() {
		Usuario usuario = new UsuarioBuilder()
								.comNome("Fulano")
								.comEmail("fulano@email.com")
								.comSenha("1234")
								.criar();
		
		em.persist(usuario);
		
		Leilao leilao = new LeilaoBuilder()
								.comNome("Galaxy S20")
								.comValorInicial("5000")
								.comData(LocalDate.now())
								.comUsuario(usuario)
								.criar();
		
		leilao = leilaoDao.salvar(leilao);
		
		leilao.setNome("I Phone 12");
		leilao.setValorInicial(new BigDecimal("7000"));
		
		leilao = leilaoDao.salvar(leilao);
		
		Leilao leilaoEncontrado = leilaoDao.buscarPorId(leilao.getId());
		
		Assert.assertEquals("I Phone 12", leilaoEncontrado.getNome());
		Assert.assertEquals(new BigDecimal("7000"), leilaoEncontrado.getValorInicial());
	}
}
