
/*
 * De uma forma geral, o algoritmo RC4 consiste em utilizar um array que a cada utilização tem os seus valores
 * permutados, e misturados com a chave, o que provoca uma dependência muito forte com esta chave.
 * Lucas Diogo França
 * 2016.2.0120.0023-5
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.security.InvalidKeyException;

public class RC4 {

  private char[] key;
  private int[] sbox;
  private static final int SBOX_LENGTH = 256;
  private static final int TAM_MIN_CHAVE = 5;

  public static void main(String[] args) throws IOException {
    try {

      FileReader arq = new FileReader("D:\\desenv\\workspace\\Criptografia\\src\\texto.txt"); // Endereço do arquivo de
                                                                                              // texto que está no meu
                                                                                              // computador
      BufferedReader lerArq = new BufferedReader(arq);

      String linha = lerArq.readLine(); // Lê a primeira linha
      String txtArquivo = linha;

      while (linha != null) {
        linha = lerArq.readLine(); // lê da segunda até a última linha
        if (linha != null) {
          txtArquivo = txtArquivo + linha; // Lê o arquivo e monta a string que vai ser criptografada

        }
      }

      arq.close();
      System.out.println("Texto: " + txtArquivo);
      RC4 rc4 = new RC4("2018456789xwyzef");
      // Passo por parâmetro o texto a ser criptografado
      char[] textoCriptografado = rc4.criptografarTexto(txtArquivo.toCharArray());
      System.out.println("Texto Criptografado: " + new String(textoCriptografado));

    } catch (InvalidKeyException e) {
      e.printStackTrace();
    }
  }

  public RC4(String chave) throws InvalidKeyException {
    setChave(chave);
  }

  public char[] criptografarTexto(final char[] msg) {
    sbox = inicializarSBox(key);
    char[] code = new char[msg.length];
    int i = 0;
    int j = 0;
    for (int n = 0; n < msg.length; n++) {
      i = (i + 1) % SBOX_LENGTH;
      j = (j + sbox[i]) % SBOX_LENGTH;
      troca(i, j, sbox);
      int rand = sbox[(sbox[i] + sbox[j]) % SBOX_LENGTH];
      code[n] = (char) (rand ^ (int) msg[n]);
    }
    return code;
  }

  private int[] inicializarSBox(char[] key) {
    int[] sbox = new int[SBOX_LENGTH];
    int j = 0;

    for (int i = 0; i < SBOX_LENGTH; i++) {
      sbox[i] = i;
    }

    for (int i = 0; i < SBOX_LENGTH; i++) {
      j = (j + sbox[i] + key[i % key.length]) % SBOX_LENGTH;
      int temp = sbox[i];
      sbox[i] = sbox[j];
      sbox[j] = temp;
    }
    return sbox;
  }

  private void troca(int i, int j, int[] sbox) {
    // Usa uma posição auxiliar para trocar os caracteres
    int temp = sbox[i];
    sbox[i] = sbox[j];
    sbox[j] = temp;
  }

  public void setChave(String chave) throws InvalidKeyException {
    // Seta a chave que será usada para criptografar a string de texto
    if (!(chave.length() >= TAM_MIN_CHAVE && chave.length() < SBOX_LENGTH)) {

      // Valido o tamanho da chave
      throw new InvalidKeyException("O tamanho da chave deve ser entre " + TAM_MIN_CHAVE + " e " + (SBOX_LENGTH - 1));
    }

    this.key = chave.toCharArray();
  }

}