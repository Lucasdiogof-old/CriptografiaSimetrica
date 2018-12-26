
/*
 * / O AES tem como principais caracter�sticas seguran�a, desempenho, facilidade de implementa��o, flexibilidade e exige
 * pouca mem�ria,
 * Lucas Diogo Fran�a
 * 2016.2.0120.0023-5
 */

import javax.crypto.spec.SecretKeySpec;
import javax.crypto.spec.IvParameterSpec;

import java.io.BufferedReader;
import java.io.FileReader;

import javax.crypto.Cipher;

public class AES {

  static String bytes = "CMP1025SegTI2018"; // 16 caracteres
  static String chave = "2018456789xwyzef"; // 16 caracteres

  public static void main(String[] args) {

    try {

      FileReader arq = new FileReader("D:\\desenv\\workspace\\Criptografia\\src\\texto.txt"); // Endere�o do arquivo de
      // texto que est� no meu
      // computador
      BufferedReader lerArq = new BufferedReader(arq);

      String linha = lerArq.readLine(); // L� a primeira linha
      String txtArquivo = linha;

      while (linha != null) {
        linha = lerArq.readLine(); // l� da segunda at� a �ltima linha
        if (linha != null) {
          txtArquivo = txtArquivo + linha; // L� o arquivo e monta a string que vai ser criptografada

        }
      }

      arq.close();

      System.out.println("Texto: " + txtArquivo);

      // Passo o texto e a chave como par�metro e recebo o texto criptografado
      byte[] textoencriptado = criptografar(txtArquivo, chave);

      System.out.print("Texto Criptografado: ");

      for (int i = 0; i < textoencriptado.length; i++)
        System.out.print(new Integer(textoencriptado[i]) + " "); // Imprimo cada caractere

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static byte[] criptografar(String txt, String chave) throws Exception {
    Cipher encripta = Cipher.getInstance("AES/CBC/PKCS5Padding", "SunJCE");
    SecretKeySpec key = new SecretKeySpec(chave.getBytes("UTF-8"), "AES");
    encripta.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(bytes.getBytes("UTF-8")));
    return encripta.doFinal(txt.getBytes("UTF-8")); // Retorno o texto criptografado
  }

}