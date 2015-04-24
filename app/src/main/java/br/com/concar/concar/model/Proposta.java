package br.com.concar.concar.model;

/**
 * Created by mdamaceno on 18/04/15.
 */
public class Proposta {
    private int id;
    private int tipo_pagamento;
    private int num_parcelas;
    private double valor_entrada;
    private double valor_carro;
    private double valor_parcela;
    private boolean confirmacao;
    private int idCarro;
    private int idCliente;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTipo_pagamento() {
        return tipo_pagamento;
    }

    public void setTipo_pagamento(int tipo_pagamento) {
        this.tipo_pagamento = tipo_pagamento;
    }

    public int getNum_parcelas() {
        return num_parcelas;
    }

    public void setNum_parcelas(int num_parcelas) {
        this.num_parcelas = num_parcelas;
    }

    public double getValor_entrada() {
        return valor_entrada;
    }

    public void setValor_entrada(double valor_entrada) {
        this.valor_entrada = valor_entrada;
    }

    public double getValor_carro() {
        return valor_carro;
    }

    public void setValor_carro(double valor_carro) {
        this.valor_carro = valor_carro;
    }

    public double getValor_parcela() {
        return valor_parcela;
    }

    public void setValor_parcela(double valor_parcela) {
        this.valor_parcela = valor_parcela;
    }

    public boolean isConfirmacao() {
        return confirmacao;
    }

    public void setConfirmacao(boolean confirmacao) {
        this.confirmacao = confirmacao;
    }

    public int getIdCarro() {
        return idCarro;
    }

    public void setIdCarro(int idCarro) {
        this.idCarro = idCarro;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    @Override
    public String toString() {
        return this.id + ". " + this.idCarro;
    }
}
