package br.com.alura.leilao.dao;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.alura.leilao.model.Usuario;
import br.com.alura.leilao.util.JPAUtil;

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
		Usuario usuario = criaUsuario();
		
		Usuario usuarioEncontrado = this.usuarioDao.buscarPorUsername(usuario.getNome());
		
		Assert.assertNotNull(usuarioEncontrado);
	}

	@Test
	void naoDeveEncontrarUsuarioNaoCadastrado() {
		criaUsuario();
		Assert.assertThrows(NoResultException.class, () -> this.usuarioDao.buscarPorUsername("beltrano"));
	}

	@Test
	void deveRemoverUsuario() {
		Usuario usuario = criaUsuario();
		usuarioDao.deletar(usuario);
		Assert.assertThrows(NoResultException.class, () -> this.usuarioDao.buscarPorUsername("fulano"));
	}
	
	private Usuario criaUsuario() {
		Usuario usuario = new Usuario("fulano", "fulano@email.com", "1234");
		em.persist(usuario);
		return usuario;
	}

}
