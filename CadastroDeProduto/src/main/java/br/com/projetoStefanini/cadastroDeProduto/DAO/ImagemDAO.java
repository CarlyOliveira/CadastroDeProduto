package br.com.projetoStefanini.cadastroDeProduto.DAO;


//import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import br.com.projetoStefanini.cadastroDeProduto.MODEL.Imagem;

public class ImagemDAO {
	
	
	 private EntityManagerFactory factory = Persistence
             .createEntityManagerFactory("persistence_unit_stefanini");
	 private EntityManager em = factory.createEntityManager();
 
 
 
 
 

 	public Imagem get(Long id){
		
		try {					
			Imagem imagem =  (Imagem) em.createQuery("SELECT i from Imagem i where i.id = :id ")
														.setParameter("id", id).getSingleResult();		
			return imagem;			
			
		} catch (Exception e) {
			e.printStackTrace();		
			return null;
		}
	}
	
 
 
	public void save(Imagem img){
		
		try {
			em.getTransaction().begin();
			img = em.merge(img);
			em.merge(img);
			em.getTransaction().commit();
						
		} catch (Exception e) {
			e.printStackTrace();
			em.getTransaction().rollback();			
		}		
	}
	
	public boolean removeImagem (Imagem img){
		
		try {
			em.getTransaction().begin();
			em.remove(img);
			em.getTransaction().commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			em.getTransaction().rollback();
			return false;
		}		
	}
 

}
