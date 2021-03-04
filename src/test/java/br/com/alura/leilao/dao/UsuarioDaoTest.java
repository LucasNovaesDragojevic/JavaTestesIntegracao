package br.com.alura.leilao.dao;

import javax.persistence.EntityManager;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import br.com.alura.leilao.model.Usuario;
import br.com.alura.leilao.util.JPAUtil;

class UsuarioDaoTest {

	private UsuarioDao usuarioDao;
	
	private EntityManager em;
	
	@Test
	void buscaUsuarioPeloUsername() {
		em = JPAUtil.getEntityManager();
		this.usuarioDao = new UsuarioDao(em);
		
		Usuario usuario = new Usuario("fulano", "fulano@email.com", "1234");
		
		em.getTransaction().begin();
		em.persist(usuario);
		em.getTransaction().commit();
		
		Usuario usuarioEncontrado = this.usuarioDao.buscarPorUsername(usuario.getNome());
		Assert.assertNotNull(usuarioEncontrado);
	}

}
