import java.awt.Image;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.swing.ImageIcon;

/**
 * Responsavel pela simulacao.
 * 
 * No cafe-mania, clientes preferenciais são diferenciados por serem sempre um
 * homem
 * de terno e chapéuzinho, enquanto clientes comuns são representados por 3
 * imagens
 * distintas de pessoas. O atendente preferencial é diferenciado por um fundo
 * amarelo.
 * 
 * @author David J. Barnes, Michael Kolling, Luiz Merschmann, Isac Cunha and
 *         Otávio Sbampato
 */
public class Simulacao {
    // Simulacao tem clientes e atendentes, janela simulacao e mapa
    private ArrayList<Cliente> clientes;
    private ArrayList<Atendente> atendentes;
    private JanelaSimulacao janelaSimulacao;
    private Mapa mapa;

    /**
     * Construtor da simulacao. Inicializa o mapa, os atendentes, os clientes e a
     * interface grafica.
     */
    public Simulacao() {
        mapa = new Mapa();
        atendentes = new ArrayList<>();
        criarAtendentes();
        clientes = new ArrayList<>();
        janelaSimulacao = new JanelaSimulacao(mapa);
    }

    /**
     * Executa a simulacao por um determinado numero de passos.
     * 
     * @param numPassos numero de iteracoes da simulacao
     */
    public void executarSimulacao(int numPassos) {
        janelaSimulacao.executarAcao();
        for (int i = 0; i < numPassos; i++) {
            executarUmPasso();
            esperar(100);
        }
    }

    /**
     * Executa um unico passo da simulacao, incluindo a movimentacao de clientes e
     * acoes dos atendentes.
     */
    private void executarUmPasso() {
        Random rand = new Random();
        int criarClienteOdd = rand.nextInt(10);
        if (criarClienteOdd == 9) {
            criarCliente(rand, mapa.getLargura(), mapa.getAltura());
        }

        for (Atendente atendente : atendentes) {
            atendente.executarAcao();
        }

        Iterator<Cliente> iterator = clientes.iterator();
        while (iterator.hasNext()) {
            Cliente cliente = iterator.next();
            mapa.removerItem(cliente);
            cliente.executarAcao();

            if (cliente.getLocalizacaoAtual().getY() > 0) {
                mapa.adicionarItem(cliente);
            } else if (cliente.getLocalizacaoAtual().getY() == 0 && !cliente.getNaFila()) {
                iterator.remove();
                for (Cliente clienteRestante : clientes) {
                    clienteRestante.setCientesExistentes(getClientes());
                }
            }
        }
        janelaSimulacao.executarAcao();
    }

    /**
     * Pausa a execucao da simulacao por um determinado tempo.
     * 
     * @param milisegundos tempo de espera em milissegundos
     */
    private void esperar(int milisegundos) {
        try {
            Thread.sleep(milisegundos);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Cria e adiciona os atendentes ao mapa, incluindo um atendente preferencial.
     */
    private void criarAtendentes() {
        Random rand = new Random();
        int atendentePreferencialOdd = rand.nextInt(4);

        for (int i = 0; i < 4; i++) {
            Atendente atendente;
            Image imagem;
            String tipo;

            if (i != atendentePreferencialOdd) {
                tipo = "Comum";
                imagem = new ImageIcon(getClass().getResource("Imagens/atendente.png")).getImage();
            } else {
                tipo = "Preferencial";
                imagem = new ImageIcon(getClass().getResource("Imagens/atendentePreferencial.png")).getImage();
            }

            atendente = new Atendente(new Localizacao(4 + i * 6, 4), imagem, tipo);
            atendentes.add(atendente);
            mapa.adicionarItem(atendente);
        }
    }

    /**
     * Cria um novo cliente e o adiciona ao mapa e à fila correspondentes.
     * 
     * @param rand    objeto Random para gerar valores aleatorios
     * @param largura largura maxima do mapa
     * @param altura  altura maxima do mapa
     */
    private void criarCliente(Random rand, int largura, int altura) {
        int clientePreferencialOdd = rand.nextInt(4);
        Cliente cliente;
        Image imagem;
        Localizacao loc = new Localizacao(rand.nextInt(largura), altura - 1);

        switch (clientePreferencialOdd) {
            case 0:
                imagem = new ImageIcon(getClass().getResource("Imagens/cliente1.png")).getImage();
                cliente = new ClienteComum(loc, imagem, getAtendentes(), getClientes());
                break;
            case 1:
                imagem = new ImageIcon(getClass().getResource("Imagens/cliente2.png")).getImage();
                cliente = new ClienteComum(loc, imagem, getAtendentes(), getClientes());
                break;
            case 2:
                imagem = new ImageIcon(getClass().getResource("Imagens/cliente3.png")).getImage();
                cliente = new ClienteComum(loc, imagem, getAtendentes(), getClientes());
                break;
            case 3:
                imagem = new ImageIcon(getClass().getResource("Imagens/clientePreferencial.png")).getImage();
                cliente = new ClientePreferencial(loc, imagem, getAtendentes(), getClientes());
                break;
            default:
                imagem = new ImageIcon(getClass().getResource("Imagens/cliente1.png")).getImage();
                cliente = new ClienteComum(loc, imagem, getAtendentes(), getClientes());
                break;
        }

        clientes.add(cliente);
        mapa.adicionarItem(cliente);
        cliente.entrarEmFila();
    }

    /**
     * Retorna uma lista imutavel de atendentes cadastrados na simulacao.
     * 
     * @return lista de atendentes
     */
    public List<Atendente> getAtendentes() {
        return Collections.unmodifiableList(atendentes);
    }

    /**
     * Retorna uma lista imutavel de clientes cadastrados na simulacao.
     * 
     * @return lista de clientes
     */
    public List<Cliente> getClientes() {
        return Collections.unmodifiableList(clientes);
    }
}
