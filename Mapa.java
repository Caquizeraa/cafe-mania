/**
 * Representa um mapa com todos os itens que participam da simulacao
 * @author David J. Barnes and Michael Kolling and Luiz Merschmann and Isac Cunha
 */
public class Mapa {
    private EntidadesCenario[][] itens;
    private int largura;
    private int altura;
    
    private static final int LARGURA_PADRAO = 35;
    private static final int ALTURA_PADRAO = 35;
    
    /**
     * Cria mapa para alocar itens da simulacao.
     * @param largura: largura da área de simulacao.
     * @param altura: altura da área de simulação.
     */
    public Mapa(int largura, int altura) {
        this.largura = largura;
        this.altura = altura;
        itens = new EntidadesCenario[altura][largura];
    }
    /**
     * Cria mapa com tamanho padrao.
     */
    public Mapa(){
        this(LARGURA_PADRAO,ALTURA_PADRAO);
    }
    
    /**
     * Adiciona uma entidade ao mapa na posicao especificada
     * 
     * @param e - entidade a ser adicionada ao mapa
     */
    public void adicionarItem(EntidadesCenario e) {
        itens[e.getLocalizacaoAtual().getX()][e.getLocalizacaoAtual().getY()] = e;
    }

    /**
     * Remove uma entidade do mapa
     * 
     * @param e - entidade a ser removida do mapa
     */
    public void removerItem(EntidadesCenario e) {
        itens[e.getLocalizacaoAtual().getX()][e.getLocalizacaoAtual().getY()] = null;
    }

    /**
     * Retorna a entidade localizada na posicao especifica do mapa (dada pelos
     * eixos x e y)
     * 
     * @param x coordenada X da posicao desejada
     * @param y coordenada Y da posicao desejada
     * @return a entidade na posição dada
     */
    public EntidadesCenario getItem(int x, int y) {
        return itens[x][y];
    }

    /**
     * Retorna a largura total do mapa
     * 
     * @return - largura do mapa em unidades
     */
    public int getLargura() {
        return largura;
    }

    /**
     * Retorna a altura total do mapa
     * 
     * @return - altura do mapa em unidades
     */
    public int getAltura() {
        return altura;
    }
    
}
