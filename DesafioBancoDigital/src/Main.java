public class Main {

    public static void main(String[] args) {

        Banco Inter = new Banco();
        Inter.setNome("Inter");

        Cliente Thiago = new Cliente();
        Thiago.setNome("Thiago");

        Cliente Junior = new Cliente();
        Junior.setNome("Junior");

        Cliente Neymar = new Cliente();
        Neymar.setNome("Neymar");



        Conta ct = new ContaCorrente(Inter, Thiago);
        Conta poupanca = new ContaPoupanca(Inter,Thiago);

        Conta cj = new ContaCorrente(Inter, Junior);
        Conta cn = new ContaPoupanca(Inter , Neymar);


        ct.depositar(100);
        cj.depositar(300);
        cn.depositar(500);

        ct.imprimirExtrato();
        poupanca.imprimirExtrato();

        Inter.listarClientes();;
        Inter.retornaMaiorConta();
        Inter.ordenaContasSaldo();
    }
}
