package br.com.zup.desafio.forca;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;
import java.text.Normalizer;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Pattern;

public class ForcaEliasRefatorada {

      public static final String DESEJA_TENTAR_NOVAMENTE = "\n\t\t\tDeseja Jogar Novamente? S/N : ";
      public static final String CONTINUAR_OU_SAIR = "\n\t\t\t(1) - Continuar" + "\n\t\t\t(0) - Sair";
      public static final String OPCAO = "\n\t\t\tOpção: ";
      public static final String MUITO_OBRIGADO = "\t\t\tMuito Obrigado!";
      public static final String PULA_UMA_LINHA = "\n";

      public static int contarPalavrasArquivo() throws IOException {

            LineNumberReader contaLinhas = new LineNumberReader(new FileReader("palavras.txt"));
            contaLinhas.skip(Long.MAX_VALUE);
            int quantidadePalavras = contaLinhas.getLineNumber() + 1;
            contaLinhas.close();

            return quantidadePalavras;
      }

      public static String buscarPalavraAleatoriaArquivo() throws IOException {

            Random random = new Random();
            int quantidadePalavrasDoArquivo = contarPalavrasArquivo();

            // troquei o nome do array aqui pois estava apenas "palavra"
            // e não era muito auto-explicativo
            String[] palavrasDoArquivo = new String[quantidadePalavrasDoArquivo];

            palavrasDoArquivo = LePavarasArquivo(palavrasDoArquivo);

            int indiceAleatorio = random.nextInt(quantidadePalavrasDoArquivo);
            String palavraSorteada = palavrasDoArquivo[indiceAleatorio];

            return normalizaCaracteresEspeciais(palavraSorteada);

      }

      public static String[] LePavarasArquivo(String[] palavrasDoArquivo) throws IOException {

            BufferedReader leitor = new BufferedReader(new FileReader("palavras.txt"));
            String linhaLida;
            int linha = 0;
            while ((linhaLida = leitor.readLine()) != null) {
                  palavrasDoArquivo[linha] = linhaLida;
                  linha++;
            }
            leitor.close();
            return palavrasDoArquivo;
      }

      public static String escreverPalavraAoGanhar(String palavraPremio) throws IOException {

            FileWriter escrita = new FileWriter("palavras.txt", true);

            escrita.append("\n" + palavraPremio);
            escrita.close();

            return "Palavra adicionada com sucesso!";
      }

      public static String normalizaCaracteresEspeciais(String normalizarPalavra) {
            String normalizarTextoNfd = Normalizer.normalize(normalizarPalavra, Normalizer.Form.NFD);
            Pattern padrao = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
            return padrao.matcher(normalizarTextoNfd).replaceAll("");
      }

      public static void interacaoInicialComUsuario() {

            // Retirei essa interação do mais por questão de estética do código;
            System.out.println("Bem Vindo A Forca Temática de Harry Potter!");
            System.out.println("Apenas Palavras Voltadas Ao Mundo Bruxo!\n");
            System.out.println("Obs: Se digitar mais de uma letra será considerado chute.\n");
      }

      public static void verificaEspacosNaPalavra(char[] verificaLetrasAcertadas, String palavraSorteada) {

            for (int i = 0; i < verificaLetrasAcertadas.length; i++) {
                  verificaLetrasAcertadas[i] = 0;

                  if (palavraSorteada.charAt(i) == ' ') {
                        verificaLetrasAcertadas[i] = 1;
                        System.out.print(" ");
                  } else
                        System.out.print(" _ ");
            }
      }

      public static void statusDeVidasRestantesELetrasUtilizadas(int chances, char[] letrasUtilizadas) {

            // Aqui optei por tirar essa parte do maain pois melhorava a vizualização;
            System.out.printf("\n\n\nVocê tem  %d  vidas: ", chances);
            System.out.println("\nLetras utilizadas: " + new String(letrasUtilizadas));
            System.out.printf("\n\nDigite Uma Letra: \n" + "Ou Digite a Palavra Se Souber: \n");
      }

      public static char[] verificaAcertos(String palavraSorteada, char letra, char[] verificarLetrasAcertadas) {

            for (int j = 0; j < palavraSorteada.length(); j++) {
                  if (letra == palavraSorteada.charAt(j)) {
                        verificarLetrasAcertadas[j] = 1;
                  }
            }

            return verificarLetrasAcertadas;
      }

      public static boolean verificaChances(String palavraSorteada, char letra) {

            boolean perderchances = true;

            if (palavraSorteada.contains("" + letra)) {
                  perderchances = false;
            }
            return perderchances;
      }

      public static boolean mostraLetraAoAcertarEVerificaVitoria(char[] verificaLetrasAcertadas,
                  boolean verificaVitoria, String palavraSorteada) {

            for (int i = 0; i < palavraSorteada.length(); i++) {

                  if (verificaLetrasAcertadas[i] == 0) {
                        System.out.print(" _ ");
                        verificaVitoria = false;

                  } else {
                        System.out.print(" " + palavraSorteada.charAt(i) + " ");
                  }
            }
            return verificaVitoria;
      }

      public static void voceGanhou(Scanner teclado) throws IOException {

            // aqui eu criei um metodo que mostra a mensagem de vitoria ao jogador
            // a fim de diminuir a complexidade do main;
            System.out.println("\n\t !!!Parabéns Você Ganhou, Você Manja Muito De Harry Potter!!!");
            System.out.println("\n\tComo Premio Você Pode Adicionar Uma Palavra De Seu Gosto No Jogo!");
            System.out.print("\tPalavra: ");
            String palavraPremio = teclado.nextLine();
            System.out.println("\t" + escreverPalavraAoGanhar(palavraPremio));
            System.out.println("\n\t\t -> Sinta-se Avontade Para Jogar De Novo! <-");
      }

      public static void vocePerdeu(String palavraSorteada) {

            // aqui eu optei por seguir o padrao de retirar os textos do main;
            System.out.printf("\tA palavra era: -> %s <-", palavraSorteada);
            System.out.println("\n\tInfelizmente Você Perdeu, Mas Não Fique Triste, Jogue De Novo!");

      }

      public static void main(String[] args) throws Exception {

            Scanner teclado = new Scanner(System.in);
            int jogarNovamente;

            do {

                  String palavraSorteada = buscarPalavraAleatoriaArquivo();
                  char[] verificarLetrasAcertadas = new char[palavraSorteada.length()];
                  int indice = 0;
                  char[] letrasUtilizadas = new char[20];
                  int chances = 6;
                  boolean verificarVitoria = false;

                  // Função nova;
                  interacaoInicialComUsuario();

                  // Função nova;
                  verificaEspacosNaPalavra(verificarLetrasAcertadas, palavraSorteada);

                  do {
                        // Função nova;
                        statusDeVidasRestantesELetrasUtilizadas(chances, letrasUtilizadas);

                        System.out.print("\nOpcão: ");
                        String letraDigitada = teclado.nextLine().toLowerCase();
                        String letraDigitadaSemAcento = normalizaCaracteresEspeciais(letraDigitada);

                        if (letraDigitadaSemAcento.length() > 1) {
                              if (letraDigitadaSemAcento.equals(palavraSorteada)) {
                                    verificarVitoria = true;
                                    break;
                              } else {
                                    chances = 0;
                                    break;
                              }

                        } else {
                              char letra = letraDigitadaSemAcento.charAt(0);

                              letrasUtilizadas[indice] = letra;
                              indice++;

                              // aqui eu transformei uma funcao em duas

                              // essa verifica as letras acertadas ou erradas
                              // e popula o array de char;
                              verificarLetrasAcertadas = verificaAcertos(palavraSorteada, letra,
                                          verificarLetrasAcertadas);

                              // essa verifica se a letra digitada existe na palavra,
                              // se não, perde vida;
                              if (verificaChances(palavraSorteada, letra)) {
                                    chances--;
                              }
                        }

                        System.out.println(PULA_UMA_LINHA);

                        // Função nova;
                        verificarVitoria = true;
                        verificarVitoria = mostraLetraAoAcertarEVerificaVitoria(verificarLetrasAcertadas,
                                    verificarVitoria, palavraSorteada);

                        System.out.println(PULA_UMA_LINHA);

                  } while (!verificarVitoria && chances > 0);

                  if (chances != 0) {

                        // Função nova;
                        voceGanhou(teclado);

                  } else {

                        // Função nova;
                        vocePerdeu(palavraSorteada);

                  }

                  // optei por usar CONSTANTES aqui pois acredito que é
                  // compreensível elas estarem aqui em tempo de compreenssão;
                  System.out.print(DESEJA_TENTAR_NOVAMENTE);
                  System.out.println(CONTINUAR_OU_SAIR);
                  System.out.print(OPCAO);
                  jogarNovamente = teclado.nextInt();

                  teclado.nextLine();

            } while (jogarNovamente == 1 || jogarNovamente != 0);

            System.out.println(MUITO_OBRIGADO);

            teclado.close();
      }
}