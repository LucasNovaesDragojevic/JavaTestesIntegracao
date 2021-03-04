package br.com.alura.leilao.dao;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.alura.leilao.model.Usuario;
import br.com.alura.leilao.util.JPAUtil;
import br.com.alura.leilao.util.builder.UsuarioBuilder;

class UsuarioDaoTest {

	private UsuarioDao usuarioDao;
	
	private EntityManager em;
	
	@BeforeEach
	void beforeEach() {
		em = JPAUtil.getEntityManager();
		usuarioDao = new UsuarioDao(em);
		em.getTransaction().begin();
	}
	
	@AfterEach
	void afterEach() {
		em.getTransaction().rollback();
	}
	
	@Test
	void buscaUsuarioPeloUsername() {
		Usuario usuario = new UsuarioBuilder()
								.comNome("Fulano")
								.comEmail("fulano@email.com")
								.comSenha("1234")
								.criar();
		
		em.persist(usuario);
		
		Usuario usuarioEncontrado = this.usuarioDao.buscarPorUsername(usuario.getNome());
		
		Assert.assertNotNull(usuarioEncontrado);
	}

	@Test
	void naoDeveEncontrarUsuarioNaoCadastrado() {
		Usuario usuario = new UsuarioBuilder()
								.comNome("Fulano")
								.comEmail("fulano@email.com")
								.comSenha("1234")
								.criar();

		em.persist(usuario);
		
		Assert.assertThrows(NoResultException.class, () -> this.usuarioDao.buscarPorUsername("beltrano"));
	}

	@Test
	void deveRemoverUsuario() {
		Usuario usuario = new UsuarioBuilder()
								.comNome("Fulano")
								.comEmail("fulano@email.com")
								.comSenha("1234")
								.criar();
		
		em.persist(usuario);
		
		usuarioDao.deletar(usuario);
		
		Assert.assertThrows(NoResultException.class, () -> this.usuarioDao.buscarPorUsername("fulano"));
	}
}
