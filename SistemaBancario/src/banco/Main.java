package banco;

import java.util.ArrayList;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Conta> listaDeContas = new ArrayList<>();
        
        int opcao = 0;
        int proximoNumeroConta = 101;

        System.out.println("Bem-vindo ao Banco Digital!");
        
        while (opcao != 7) {
            System.out.println("\n--- MENU PRINCIPAL ---");
            System.out.println("1. Criar Conta");
            System.out.println("2. Realizar Depósito");
            System.out.println("3. Realizar Saque");
            System.out.println("4. Realizar Transferência");
            System.out.println("5. Listar Contas");
            System.out.println("6. Calcular Total de Tributos");
            System.out.println("7. Sair");
            System.out.print("Escolha uma opção: ");

            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    System.out.println("\n-- Criar Conta --");
                    System.out.print("Digite o nome do cliente: ");
                    String nomeCliente = scanner.nextLine();

                    System.out.println("Qual o tipo de conta?");
                    System.out.println("1. Conta Corrente");
                    System.out.println("2. Conta Poupança");
                    System.out.print("Escolha (1 ou 2): ");
                    int tipoConta = scanner.nextInt();
                    scanner.nextLine();

                    Conta novaConta = null;

                    if (tipoConta == 1) {
                        novaConta = new ContaCorrente(proximoNumeroConta, nomeCliente);
                    } else if (tipoConta == 2) {
                        novaConta = new ContaPoupanca(proximoNumeroConta, nomeCliente);
                    } else {
                        System.out.println("Erro: Tipo de conta inválido. Operação cancelada.");
                    }

                    if (novaConta != null) {
                        listaDeContas.add(novaConta);
                        System.out.println("Sucesso! Conta criada para " + nomeCliente + ".");
                        System.out.println("O número da sua conta é: " + proximoNumeroConta);
                        proximoNumeroConta++;
                    }
                    break;
                case 2:
                    System.out.println("\n-- Realizar Depósito --");
                    System.out.print("Digite o número da conta: ");
                    int numeroContaDeposito = scanner.nextInt();

                    boolean contaEncontrada = false;
                    for (Conta conta : listaDeContas) {
                        if (conta.getNumero() == numeroContaDeposito) {
                            System.out.print("Digite o valor do depósito para conta de " + conta.getCliente() + ": R$ ");
                            double valorDeposito = scanner.nextDouble();
                            conta.depositar(valorDeposito);
                            System.out.println("Depósito de R$ " + valorDeposito + " realizado com sucesso!");
                            System.out.println("Novo saldo da conta de " + conta.getCliente() + ": R$ " + conta.getSaldo());
                            contaEncontrada = true;
                            break;
                        }
                    }

                    if (!contaEncontrada) {
                        System.out.println("Erro: Conta número " + numeroContaDeposito + " não encontrada na base de dados.");
                    }
                    break;
                case 3:
                    System.out.println("\n-- Realizar Saque --");
                    System.out.print("Digite o número da conta: ");
                    int numeroContaSaque = scanner.nextInt();

                    boolean contaEncontradaSaque = false;

                    for (Conta conta : listaDeContas) {
                        if (conta.getNumero() == numeroContaSaque) {
                            contaEncontradaSaque = true;
                            System.out.print("Digite o valor do saque para conta de " + conta.getCliente() + ": R$ ");
                            double valorSaque = scanner.nextDouble();
                            if (conta.sacar(valorSaque)) {
                                System.out.println("Saque de R$ " + valorSaque + " realizado com sucesso!");
                                System.out.println("Novo saldo da conta: R$ " + conta.getSaldo());
                            } else {
                                System.out.println("Erro: Saldo insuficiente para realizar este saque.");
                            }
                            break;
                        }
                    }

                    if (!contaEncontradaSaque) {
                        System.out.println("Erro: Conta número " + numeroContaSaque + " não encontrada na base de dados.");
                    }
                    break;
                case 4:
                    System.out.println("\n-- Realizar Transferência --");
                    System.out.print("Digite o número da conta de origem: ");
                    int numOrigem = scanner.nextInt();
                    System.out.print("Digite o número da conta de destino: ");
                    int numDestino = scanner.nextInt();
                    System.out.print("Digite o valor a ser transferido: R$ ");
                    double valorTransferencia = scanner.nextDouble();

                    Conta contaOrigem = null;
                    Conta contaDestino = null;
                    
                    for (Conta conta : listaDeContas) {
                        if (conta.getNumero() == numOrigem) {
                            contaOrigem = conta;
                        }
                        if (conta.getNumero() == numDestino) {
                            contaDestino = conta;
                        }
                    }

                    if (contaOrigem != null && contaDestino != null) {
                        if (contaOrigem.transferir(valorTransferencia, contaDestino)) {
                            System.out.println("Transferência de R$ " + valorTransferencia + " realizada com sucesso!");
                            System.out.println("Novo saldo da conta origem (" + contaOrigem.getCliente() + "): R$ " + contaOrigem.getSaldo());
                            System.out.println("Novo saldo da conta destino (" + contaDestino.getCliente() + "): R$ " + contaDestino.getSaldo());
                        } else {
                            System.out.println("Erro: Saldo insuficiente na conta de origem para esta transferência.");
                        }
                    } else {
                        System.out.println("Erro: Conta de origem ou de destino não encontrada na base de dados.");
                    }
                    break;
                case 5:
                    System.out.println("\n-- Listar Contas --");
                    
                    if (listaDeContas.isEmpty()) {
                        System.out.println("Nenhuma conta cadastrada no momento.");
                    } else {
                        System.out.println("=============================================================================================");
                        for (Conta conta : listaDeContas) {
                            String tipoContaStr = "";
                            if (conta instanceof ContaCorrente) {
                                tipoContaStr = "Corrente";
                            } else if (conta instanceof ContaPoupanca) {
                                tipoContaStr = "Poupança";
                            }
                            System.out.println("Número: " + conta.getNumero() +
                                               " | Cliente: " + conta.getCliente() + 
                                               " | Tipo: " + tipoContaStr + 
                                               " | Saldo: R$ " + conta.getSaldo());
                        }
                        System.out.println("=============================================================================================");
                    }
                    break;
                case 6:
                    System.out.println("\n-- Calcular Tributos --");
                    double totalTributos = 0.0;
                    
                    for (Conta conta : listaDeContas) {
                    	
                        if (conta instanceof ITributavel) {
                            
                            
                            ITributavel contaTributavel = (ITributavel) conta;
                            totalTributos += contaTributavel.calculaTributos();
                        }
                    }
                    BigDecimal totalTributosTruncado = new BigDecimal(totalTributos).setScale(2, RoundingMode.DOWN);

                    System.out.println("\n=============================================================================================");
                    System.out.println("Total de tributos a recolher: R$ " + totalTributosTruncado);
                    System.out.println("=============================================================================================");
                    break;
                case 7:
                    System.out.println("Encerrando o sistema... Obrigado por usar nosso banco!"); 
                    break;
                default:
                    System.out.println("Opção inválida! Tente novamente.");
            }
            if (opcao != 7) {
                System.out.println("\nPressione [ENTER] para continuar...");
                try {
                    System.in.read();
                    while (System.in.available() > 0) {
                        System.in.read();
                    }
                } catch (Exception e) {
                }
            }
        }
        
        scanner.close();
    }
}