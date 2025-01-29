import java.awt.Image;

/**
 * Classe abstrata que servirá como superclasse de Cliente e Atendente.
 * 
 * Como cada entidade do cenário possui uma localização que determina sua posição
 * no ambiente e uma imagem, é uma boa prática criar uma superclasse que contenha
 * esses atributos.
 * 
 * @author Otávio Sbampato e Isac Cunha
 * @version 1.0
 * @see Localizacao
 * @see Image
 */
public abstract class EntidadesCenario {
    /** Armazena a localização atual da entidade no cenario */
    private Localizacao localizacaoAtual;
    
    /** Imagem da entidade */
    private Image imagem;

    /**
     * Cria uma nova entidade no cenario com localização e imagem.
     * 
     * @param localizacaoAtual - Localização inicial da entidade no cenário
     * @param imagem - Imagem que representa a entidade
     */
    public EntidadesCenario(Localizacao localizacaoAtual, Image imagem) {
        this.localizacaoAtual = localizacaoAtual;
        this.imagem = imagem;
    }

    /**
     * Getter de localizacaoo atual da entidade
     * 
     * @return a localizacao atual da entidade
     */
    public Localizacao getLocalizacaoAtual() {
        return localizacaoAtual;
    }

    /**
     * Atualiza a posicao da entidade
     * 
     * @param localizacaoNova a nova posicao da entidade
     */
    public void setLocalizacaoAtual(Localizacao localizacaoNova) {
        localizacaoAtual = localizacaoNova;
    }

    /**
     * Retorna a imagem que representa a entidade
     * 
     * @return a imagem da entidade
     */
    public Image getImagem() {
        return imagem;
    }
}