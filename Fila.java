import java.util.ArrayList;
import java.util.Iterator;

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
        if(arrayFila.isEmpty()){
            return null;
        }else{
           return arrayFila.get(0);
        }
    }

    public int getTamanho(){
        return arrayFila.size();
    }

    public Localizacao getPosicaoEntrada(){
        return posicaoEntrada;
    }

    public void removerPrimeiro(){
        if (!arrayFila.isEmpty()) {
            Iterator<Cliente> iterator = arrayFila.iterator();
            if (iterator.hasNext()) {
                iterator.next();
                iterator.remove();
            }
            tamanho--;
            updatePosicaoEntrada();
        }
    }

    public void adicionarCliente(Cliente cliente){
        arrayFila.add(cliente);
        tamanho++;
        updatePosicaoEntrada();
    }

    private void updatePosicaoEntrada() {
        if (!arrayFila.isEmpty()) {
            Cliente ultimoCliente = arrayFila.get(arrayFila.size() - 1);
            posicaoEntrada = new Localizacao(origem.getX(), ultimoCliente.getLocalizacaoAtual().getY() + 1);
        } else {
            posicaoEntrada = origem;
        }
    }
}