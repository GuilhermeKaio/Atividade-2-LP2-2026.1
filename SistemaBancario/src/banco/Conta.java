package banco;

public abstract class Conta {
    protected int numero;
    protected String cliente;
    protected double saldo;

    public Conta(int numero, String cliente) {
        this.numero = numero;
        this.cliente = cliente;
        this.saldo = 0.0;
    }
    
    public void depositar(double valor) {
        if (valor > 0) {
            this.saldo += valor;
        }
    }

    public abstract boolean sacar(double valor);
    public abstract boolean transferir(double valor, Conta destino);

    public int getNumero() {
        return numero;
    }

    public String getCliente() {
        return cliente;
    }

    public double getSaldo() {
        return saldo;
    }
}