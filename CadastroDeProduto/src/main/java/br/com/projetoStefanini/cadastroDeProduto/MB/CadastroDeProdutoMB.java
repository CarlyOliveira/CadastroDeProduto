package br.com.projetoStefanini.cadastroDeProduto.MB;

import java.util.LinkedList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.com.projetoStefanini.cadastroDeProduto.DAO.ImagemDAO;
import br.com.projetoStefanini.cadastroDeProduto.DAO.ProdutoDAO;
import br.com.projetoStefanini.cadastroDeProduto.MODEL.Imagem;
import br.com.projetoStefanini.cadastroDeProduto.MODEL.Produto;



@ManagedBean(name = "CadastroDeProdutoMB")
@ViewScoped
public class CadastroDeProdutoMB {

	private Produto produto = new Produto();
	private ProdutoDAO produtoDAO = new ProdutoDAO();

	private Produto produtoPai = new Produto();
	
	private Imagem imagem = new Imagem();
	private ImagemDAO imagemDAO = new ImagemDAO();
	
	
	private List<Imagem> imagens = new LinkedList<Imagem>();
	
	
	
	public CadastroDeProdutoMB(){
		
	}
	
	public void get(){
		
		if(produto.getId() >= 1){			
		
			produto = (Produto) produtoDAO.get(produto.getId());
			if(produto != null){
				
				if(produto.getProduto() != null){
					produtoPai = produto.getProduto();
				}			
				if(imagens != null){
					imagens = produto.getImagem();
				}
					
			}else{
				produto = new Produto();
				produtoPai = new Produto();
				imagens = new LinkedList<Imagem>();
				
				FacesContext.getCurrentInstance().addMessage(
	                    null,
	                    new FacesMessage(FacesMessage.SEVERITY_WARN, "Atenção",
	                                "Produto não encontrado!"));
			}
		}else {
			FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "Atenção",
                                "Informe o código do produto!"));
			return;
		}
		
	}	
	
	public void save(){		
		
		
		if(produtoPai.getId() >= 1){
			produtoPai = (Produto) produtoDAO.get(produtoPai.getId());
			
			if(produtoPai != null){
				
				produto.setProduto_pai(produtoPai);				
				
			}else{
				
				produtoPai = new Produto();
				
				FacesContext.getCurrentInstance().addMessage(
	                    null,
	                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Atenção",
	                                "Produto pai não encontrado!"));
				return;
			}
			
		}
		
		//vericar se é uma inclusão ou edição
		
		
		
		Produto editProduto = (Produto) produtoDAO.get(produto.getId());
				boolean incluir = editProduto == null;
				String msgSucesso = "Produto editado com sucesso!";
				if(incluir){
					 produto.setId(null);		
					 msgSucesso = "Produto cadastrado com sucesso!";
				}	
				
				
				
				
				
				
		if(produtoDAO.save(produto, incluir)){
			
			for (Imagem imagem : imagens) {
				imagem.setProduto(produto);
				imagemDAO.save(imagem);
			}
    		produto.setImagem(imagens);
    
    		FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso",
                    		msgSucesso));
    		
    	}else{
    		  produto = new Produto();
    		  imagem  = new Imagem();
    		  produtoPai = new Produto();
              FacesContext.getCurrentInstance().addMessage(
                         null,
                         new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro",
                                     "Erro no cadastro do produto!"));
    	}
		
		
		
		
		
	}
	
	
	public void removeProduto(){
		if(produtoDAO.remove(produto)){
			FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso",
                                "Produto removido com sucesso!"));
		}else{
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro",
                                "Erro ao tentar remover o produto!"));
		}
	}
	
	
	public void removeImagem(Imagem imagem){
		if(imagemDAO.removeImagem(imagem)){
			FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso",
                                "Imagem removida com sucesso!"));
		}else{
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro",
                                "Erro ao tentar remover imagem!"));
		}
	}
	
	

	
	
	public void adcionarImagem(){
		    imagem.setId((long)0);
			imagens.add(imagem);
			imagem = new Imagem();
			
	}
	public void removerImagem(){
		
		int index = 0;
		for (Imagem img : imagens) {
				if(img.getId() == imagem.getId()){
					index = imagens.indexOf(img);
				}
			}
		
			imagens.remove(index);
		
		
	}
	

	public Produto getProdutoPai() {
		return produtoPai;
	}

	public void setProdutoPai(Produto produtoPai) {
		this.produtoPai = produtoPai;
	}

	public List<Imagem> getImagens() {
		return imagens;
	}

	public void setImagens(List<Imagem> imagens) {
		this.imagens = imagens;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public ProdutoDAO getProdutoDAO() {
		return produtoDAO;
	}

	public void setProdutoDAO(ProdutoDAO produtoDAO) {
		this.produtoDAO = produtoDAO;
	}

	public Imagem getImagem() {
		return imagem;
	}

	public void setImagem(Imagem imagem) {
		this.imagem = imagem;
	}

	public ImagemDAO getImagemDAO() {
		return imagemDAO;
	}

	public void setImagemDAO(ImagemDAO imagemDAO) {
		this.imagemDAO = imagemDAO;
	}

	
	
	
	
	
	
	
	

}
