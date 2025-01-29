import java.awt.Image;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.swing.ImageIcon;

/**
 * Responsavel pela simulacao.
 * @author David J. Barnes and Michael Kolling and Luiz Merschmann
 */
public class Simulacao {
    // Simulacao tem clientes e atendentes, janela simulacao e mapa
    private ArrayList<Cliente> clientes;
    private ArrayList<Atendente> atendentes;    
    private JanelaSimulacao janelaSimulacao;
    private Mapa mapa;
    
    // Construtor da Simulacao
    public Simulacao() {
        // Inicia mapa
        mapa = new Mapa();

        // Inicia atendentes
        atendentes = new ArrayList<Atendente>();
        criarAtendentes();

        //Inicia clientes
        clientes = new ArrayList<Cliente>();

        // Inicia janela de simulacao
        janelaSimulacao = new JanelaSimulacao(mapa);
    }
    
    // Executar simulacao
    public void executarSimulacao(int numPassos){
        // Executa acao da janela de simulacao
        janelaSimulacao.executarAcao();
        // Espera 100 milisegundos entre cada passo
        for (int i = 0; i < numPassos; i++) {
            executarUmPasso();
            esperar(100);
        }        
    }

    // Executar um passo
    private void executarUmPasso() {
        // Gera um valor aleatório que caso = 9, cria um novo cliente
        Random rand = new Random();
        int criarClienteOdd = rand.nextInt(10);
        if(criarClienteOdd==9){
            criarCliente(rand, mapa.getLargura(), mapa.getAltura());
        }

        // Executa acao de cada atendente
        for(Atendente atendente: atendentes){
            atendente.executarAcao();
        }
    
        // Percorre os clientes
        Iterator<Cliente> iterator = clientes.iterator();
        while (iterator.hasNext()) {
            Cliente cliente = iterator.next();
            // Remove o cliente do mapa
            mapa.removerItem(cliente);
            // Executa acao de cada cliente
            cliente.executarAcao();
            
            // Caso o cliente esteja no mapa, readiciona ele ao mapa
            if (cliente.getLocalizacaoAtual().getY() > 0) {
                mapa.adicionarItem(cliente);
            } 
            // Caso não, remove ele do array de clientes
            else if (cliente.getLocalizacaoAtual().getY() == 0 && cliente.getNaFila() == false) {
                iterator.remove();
                // Após remover, passa a lista de clientes atualizada, para todo cliente
                for(Cliente clienteRestante : clientes){
                    clienteRestante.setCientesExistentes(getClientes());
                }
            }
        }
        // Executa acao da janela de simulacao
        janelaSimulacao.executarAcao();
    }
    
    // Espera um tempo
    private void esperar(int milisegundos){
        try{
            Thread.sleep(milisegundos);
        }catch(InterruptedException e){
            System.out.println(e.getMessage());
        }
    }
    
    // Método que gera os 4 atendentes
    private void criarAtendentes(){
        // Gera um número aletarório para ser o preferencial
        Random rand = new Random();
        int atendentePreferencialOdd = rand.nextInt(4);
        // Gera os atendentes
        for(int i = 0; i<4; i++){
            Atendente atendente;
            Image imagem;
            String tipo;
            // Se não for o valor preferencial, gera comum
            if(i != atendentePreferencialOdd){
                tipo = "Comum";
                imagem = new ImageIcon(getClass().getResource("Imagens/atendente.png")).getImage();
            }
            // Se for o valor preferencial, gera preferencial
            else{
                tipo = "Preferencial";
                imagem = new ImageIcon(getClass().getResource("Imagens/atendentePreferencial.png")).getImage();
            }
            // Cria o atendente
            atendente = new Atendente(new Localizacao(4+i*6, 4), imagem, tipo);
            // Adiciona ao array de atendentes e ao mapa
            atendentes.add(atendente);
            mapa.adicionarItem(atendente);
        }
    }

    // Método que gera um cliente
    private void criarCliente(Random rand, int largura, int altura){
        // Sorteia um valor para o tipo de cliente
        int clientePreferencialOdd = rand.nextInt(4);
        Cliente cliente;
        Image imagem;
        // Sorteia uma localizacao para o eixo x
        Localizacao loc = new Localizacao(rand.nextInt(largura),altura-1);
        // Analisa o valor caso a caso
        switch (clientePreferencialOdd) {
            case 0:
                // Cria um cliente comum com imagem 1
                imagem = new ImageIcon(getClass().getResource("Imagens/cliente1.png")).getImage();
                cliente = new ClienteComum(loc, imagem, getAtendentes(), getClientes());
                break;
            case 1:
                // Cria um cliente comum com imagem 2
                imagem = new ImageIcon(getClass().getResource("Imagens/cliente2.png")).getImage();
                cliente = new ClienteComum(loc, imagem, getAtendentes(), getClientes());
                break;
            case 2:
                // Cria um cliente comum com imagem 3
                imagem = new ImageIcon(getClass().getResource("Imagens/cliente3.png")).getImage();
                cliente = new ClienteComum(loc, imagem, getAtendentes(), getClientes());
                break;
            case 3:
                // Cria um cliente prefetencial com imagem preferencial
                imagem = new ImageIcon(getClass().getResource("Imagens/clientePreferencial.png")).getImage();
                cliente = new ClientePreferencial(loc, imagem, getAtendentes(), getClientes());
                break;
            default:
                // Cria um cliente comum com imagem 1
                imagem = new ImageIcon(getClass().getResource("Imagens/cliente1.png")).getImage();
                cliente = new ClienteComum(loc, imagem, getAtendentes(), getClientes());
                break;
        }
        // Adiciona o cliente ao array de clientes e ao mapa
        clientes.add(cliente);
        mapa.adicionarItem(cliente);
        // Faz o cliente já buscar uma fila
        cliente.entrarEmFila();
    }

    // Retorna a lista de atendentes sem quebrar o encapsulamento
    public List<Atendente> getAtendentes(){
        return Collections.unmodifiableList(atendentes);
    }

    // Retorna a lista de clientes sem quebrar o encapsulamento
    public List<Cliente> getClientes(){
        return Collections.unmodifiableList(clientes);
    }
}
