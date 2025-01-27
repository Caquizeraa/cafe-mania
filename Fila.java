import java.util.ArrayList;

public class Fila {
    private Localizacao origem;
    private Localizacao posicaoEntrada;
    private int tamanho;
    private ArrayList<Cliente> arrayFila;

    public Fila(Localizacao origem){
        this.origem = origem;
        posicaoEntrada = origem;
        tamanho = 0;
        arrayFila = new ArrayList<Cliente>();
    }

    public Cliente getPrimeiro(){
        return arrayFila.get(0);
    }

    public int getTamanho(){
        return arrayFila.size();
    }

    public Localizacao getPosicaoEntrada(){
        return posicaoEntrada;
    }

    public void removerPrimeiro(){
        arrayFila.remove(0);
        tamanho--;
    }

    public void adicionarCliente(Cliente cliente){
        arrayFila.add(cliente);
        tamanho++;
        posicaoEntrada = new Localizacao(origem.getX(),cliente.getLocalizacaoAtual().getY()-1);
    }

}
