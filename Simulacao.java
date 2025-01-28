import java.awt.Image;
import java.util.ArrayList;
import java.util.Collection;
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
    private ArrayList<Cliente> clientes;
    private ArrayList<Atendente> atendentes;    
    private JanelaSimulacao janelaSimulacao;
    private Mapa mapa;
    
    public Simulacao() {
        // MAPA
        Random rand = new Random(1234);
        mapa = new Mapa();
        int largura = mapa.getLargura();
        int altura = mapa.getAltura();

        // atendentes
        atendentes = new ArrayList<Atendente>();
        criarAtendentes(rand);

        //cliente
        clientes = new ArrayList<Cliente>();
        criarCliente(rand, largura, altura);



        janelaSimulacao = new JanelaSimulacao(mapa);
    }
    
    public void executarSimulacao(int numPassos){
        janelaSimulacao.executarAcao();
        for (int i = 0; i < numPassos; i++) {
            executarUmPasso();
            esperar(100);
        }        
    }

    private void executarUmPasso() {
        Random rand = new Random();
        int criarClienteOdd = rand.nextInt(10);
        if(criarClienteOdd==9){
            criarCliente(rand, mapa.getLargura(), mapa.getAltura());
        }

        for(Atendente atendente: atendentes){
            atendente.executarAcao();
        }
    
        Iterator<Cliente> iterator = clientes.iterator();
        while (iterator.hasNext()) {
            Cliente cliente = iterator.next();
            mapa.removerItem(cliente);
            cliente.executarAcao();
            
            if (cliente.getLocalizacaoAtual().getY() > 0) {
                mapa.adicionarItem(cliente);
            } else if (cliente.getLocalizacaoAtual().getY() == 0 && cliente.getNaFila() == false) {
                iterator.remove(); // Remove the client from the list
            }
        }
        janelaSimulacao.executarAcao();
    }
    
    private void esperar(int milisegundos){
        try{
            Thread.sleep(milisegundos);
        }catch(InterruptedException e){
            System.out.println(e.getMessage());
        }
    }
    
    private void criarAtendentes(Random rand){
        int atendentePreferencialOdd = rand.nextInt(4);
        for(int i = 0; i<4; i++){
            Atendente atendente;
            Image imagem;
            String tipo;
            if(i != atendentePreferencialOdd){
                tipo = "Comum";
                imagem = new ImageIcon(getClass().getResource("Imagens/atendente.png")).getImage();
            }else{
                tipo = "Preferencial";
                imagem = new ImageIcon(getClass().getResource("Imagens/atendentePreferencial.png")).getImage();
            }
            atendente = new Atendente(new Localizacao(4+i*6, 4), imagem, tipo);
            atendentes.add(atendente);
            mapa.adicionarItem(atendente);
        }
    }

    private void criarCliente(Random rand, int largura, int altura){
        int clientePreferencialOdd = rand.nextInt(4);
        Cliente cliente;
        Image imagem;

        Localizacao loc = new Localizacao(rand.nextInt(largura),altura-1);
        switch (clientePreferencialOdd) {
            case 0:
                imagem = new ImageIcon(getClass().getResource("Imagens/cliente1.png")).getImage();
                cliente = new ClienteComum(loc, imagem, atendentes);
                break;
            case 1:
                imagem = new ImageIcon(getClass().getResource("Imagens/cliente2.png")).getImage();
                cliente = new ClienteComum(loc, imagem, atendentes);
                break;
            case 2:
                imagem = new ImageIcon(getClass().getResource("Imagens/cliente3.png")).getImage();
                cliente = new ClienteComum(loc, imagem, atendentes);
                break;
            case 3:
                imagem = new ImageIcon(getClass().getResource("Imagens/clientePreferencial.png")).getImage();
                cliente = new ClienteComum(loc, imagem, atendentes);
                break;
            default:
                imagem = new ImageIcon(getClass().getResource("Imagens/cliente1.png")).getImage();
                cliente = new ClienteComum(loc, imagem, atendentes);
                break;
        }
        clientes.add(cliente);
        mapa.adicionarItem(cliente);
        cliente.entrarEmFila();
    }

    public List<Atendente> getAtendentes(){
        return Collections.unmodifiableList(atendentes);
    }
}
