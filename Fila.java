import java.util.ArrayList;
import java.util.Iterator;

// Represents uma fila (ED) no cenário, respeitando a lógica FIFO
public class Fila {
    private Localizacao origem;
    private Localizacao posicaoEntrada;
    private ArrayList<Cliente> arrayFila;

    // Fila recebe apenas sua localização de origem
    public Fila(Localizacao origem){
        this.origem = origem;
        posicaoEntrada = origem;
        arrayFila = new ArrayList<Cliente>();
    }

    // Método que retorna o primeiro cliente da fila
    public Cliente getPrimeiro(){
        if(arrayFila.isEmpty()){
            return null;
        }else{
           return arrayFila.get(0);
        }
    }

    // Método que retorna o tamanho da fila
    public int getTamanho(){
        return arrayFila.size();
    }

    // Método que retorna a posição de entrada na fila
    public Localizacao getPosicaoEntrada(){
        return posicaoEntrada;
    }

    // Método que remove o primeiro cliente da fila
    public void removerPrimeiro(){
        // Caso a fila não esteja vazia
        if (!arrayFila.isEmpty()) {
            // Remove o primeiro cliente
            Iterator<Cliente> iterator = arrayFila.iterator();
            if (iterator.hasNext()) {
                iterator.next();
                iterator.remove();
            }
            // Atualiza a posicao de entrada
            updateFila();
        }
    }

    // Método que adiciona um cliente na fila
    public void adicionarCliente(Cliente cliente){
        arrayFila.add(cliente);
        // Atualiza a posicao de entrada
        updateFila();
    }

    // Método que atualiza a posição de entrada
    private void updateFila() {
        // Se a fila não estiver vazia
        if (!arrayFila.isEmpty()) {
            // Posição de entrada é abaixo do ultimo cliente
            Cliente ultimoCliente = arrayFila.get(arrayFila.size() - 1);
            posicaoEntrada = new Localizacao(origem.getX(), ultimoCliente.getLocalizacaoAtual().getY() + 1);
            // Atualiza a posição de cada cliente
            for (int i = 0; i < arrayFila.size(); i++) {
                Cliente cliente = arrayFila.get(i);
                cliente.setLocalizacaoDestino(new Localizacao(origem.getX(), origem.getY() + i));
            }
        }
        else {
            // Posicao é a mesma da origem
            posicaoEntrada = origem;
        }
    }
}