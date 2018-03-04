package br.com.projetoStefanini.cadastroDeProduto.DAO;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;




import br.com.projetoStefanini.cadastroDeProduto.MODEL.Produto;




public class ProdutoDAO {
	
	
	  private EntityManagerFactory factory = Persistence
              .createEntityManagerFactory("persistence_unit_stefanini");
  private EntityManager em = factory.createEntityManager();

  
  @SuppressWarnings("unchecked")
  public List<Produto> getList(){
	   try {
		   List<Produto> produtos  = (List<Produto>) em.createQuery("SELECT p from Produto p").getResultList();
           return produtos;
     } catch (NoResultException e) {
           return null;
     }
  }
  
  public Produto get(Long id) {

        try {
              Produto produto = (Produto) em
                         .createQuery(
                                     "SELECT p from Produto p where p.id = :id")
                         .setParameter("id", id).getSingleResult();
              
              return produto;
        } catch (NoResultException e) {
              return null;
        }
  }

  
  
public boolean save(Produto produto, boolean incluir) {
        try {
        	  em.getTransaction().begin();
        	  if(incluir){
        		  em.merge(produto);
                  em.persist(produto);
        	  }else{
                  em.merge(produto);
        	  }
              em.getTransaction().commit();
              return true;
        } catch (Exception e) {
              e.printStackTrace();
              em.getTransaction().rollback();
              return false;
        }
  }
  
  public boolean remove(Produto produto) {
        try {
        	  em.getTransaction().begin();
        	  produto = em.merge(produto);
              em.remove(produto);
              em.getTransaction().commit();
              return true;
        } catch (Exception e) {
        	em.getTransaction().rollback();
              e.printStackTrace();
              return false;
        }
  }
	

}
