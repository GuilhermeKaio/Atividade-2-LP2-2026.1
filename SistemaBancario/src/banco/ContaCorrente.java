package banco;

public class ContaCorrente extends Conta implements ITributavel {

    public ContaCorrente(int numero, String cliente) {
        super(numero, cliente);
    }

    @Override
    public boolean sacar(double valor) {
        double taxa = valor * 0.05;
        double valorTotal = valor + taxa;

        if (this.saldo >= valorTotal) {
            this.saldo -= valorTotal;
            return true;
        }
        return false;
    }

    @Override
    public boolean transferir(double valor, Conta destino) {
        if (this.sacar(valor)) {
            destino.depositar(valor);
            return true;
        }
        return false;
    }

    @Override
    public double calculaTributos() {
        return this.saldo * 0.01;
    }
}
