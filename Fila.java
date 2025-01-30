import java.util.ArrayList;
import java.util.Iterator;

/**
 * Representa uma fila no cafe-mania.
 * A fila segue a lógica de atendimento primeiro a entrar, primeiro a sair
 * (FIFO).
 * 
 * @author Isac Cunha e Otávio Sbampato
 * @version 1.0
 * @see Cliente
 * @see Localizacao
 */
public class Fila {
    private Localizacao origem;
    private Localizacao posicaoEntrada;
    private ArrayList<Cliente> arrayFila;

    /**
     * Cria uma fila em uma determinada localização de origem.
     *
     * @param origem - localizacao inicial da fila
     */
    public Fila(Localizacao origem) {
        this.origem = origem;
        posicaoEntrada = origem;
        arrayFila = new ArrayList<>();
    }

    /**
     * Retorna o primeiro cliente da fila.
     *
     * @return o primeiro cliente da fila ou null se a fila estiver vazia
     */
    public Cliente getPrimeiro() {
        if (arrayFila.isEmpty()) {
            return null;
        } else {
            return arrayFila.get(0);
        }
    }

    /**
     * Retorna o tamanho atual da fila
     *
     * @return numero de clientes na fila
     */
    public int getTamanho() {
        return arrayFila.size();
    }

    /**
     * Retorna a posição de entrada na fila
     *
     * @return localizacao onde novos clientes entram na fila
     */
    public Localizacao getPosicaoEntrada() {
        return posicaoEntrada;
    }

    /**
     * Remove o primeiro cliente da fila, se houver
     * Após a remoção, a posição de entrada é atualizada
     */
    public void removerPrimeiro() {
        // Caso a fila nao esteja vazia
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

    /**
     * Adiciona um cliente ao final da fila e atualiza a posição de entrada
     *
     * @param cliente - cliente a ser adicionado na fila
     */
    public void adicionarCliente(Cliente cliente) {
        arrayFila.add(cliente);
        // Atualiza a posicao de entrada
        updateFila();
    }

    /**
     * Atualiza a posicao de entrada da fila e de cada cliente.
     * Se a fila estiver vazia, a posição de entrada volta à origem.
     */
    private void updateFila() {
        // Se a fila nao estiver vazia
        if (!arrayFila.isEmpty()) {
            // Posicao de entrada e abaixo do ultimo cliente
            Cliente ultimoCliente = arrayFila.get(arrayFila.size() - 1);
            posicaoEntrada = new Localizacao(origem.getX(), ultimoCliente.getLocalizacaoAtual().getY() + 1);
            // Atualiza a posicao de cada cliente
            for (int i = 0; i < arrayFila.size(); i++) {
                Cliente cliente = arrayFila.get(i);
                cliente.setLocalizacaoDestino(new Localizacao(origem.getX(), origem.getY() + i));
            }
        } else {
            // Posicao e a mesma da origem
            posicaoEntrada = origem;
        }
    }
}
