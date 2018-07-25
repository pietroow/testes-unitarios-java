package entidades;

public class Filme {

    private String nome;
    private Integer estoque;
    private Double precoLocacao;

    public Filme(){

    }

    public Filme(String nome, Integer estoque, Double precoLocacao){
        this.nome = nome;
        this.estoque = estoque;
        this.precoLocacao = precoLocacao;
    }

    public String getNome(){
        return this.nome;
    }

    public Integer getEstoque(){
        return this.estoque;
    }

    public Double getPrecoLocacao(){
        return this.precoLocacao;
    }

    public void setNome(String nome){
        this.nome = nome;
    }

    public void setEstoque(Integer estoque){
        this.estoque = estoque;
    }

    public void setPrecoLocacao(Double precoLocacao){
        this.precoLocacao = precoLocacao;
    }


}
