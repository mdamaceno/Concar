package br.com.concar.concar.model;

/**
 * Created by mdamaceno on 18/04/15.
 */
public class Carro {
    private int id;
    private String marca;
    private String modelo;
    private int ano;
    private double quilometragem;
    private int airbag, ar_condicionado;
    private String cor;
    private double preco;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public double getQuilometragem() {
        return quilometragem;
    }

    public void setQuilometragem(double quilometragem) {
        this.quilometragem = quilometragem;
    }

    public int getAirbag() {
        return airbag;
    }

    public void setAirbag(int airbag) {
        this.airbag = airbag;
    }

    public int getAr_condicionado() {
        return ar_condicionado;
    }

    public void setAr_condicionado(int ar_condicionado) {
        this.ar_condicionado = ar_condicionado;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

}
