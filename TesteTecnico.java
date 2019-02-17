/*
Autor: Henrique Fernandes Viana Mendes
Data:  15/02/2019 - 16/02/2019
*/
import java.io.*;

public class TesteTecnico{

   public static void main(String[] args){
      try{
         encode();
         decode();
      }catch(Exception e){
         e.printStackTrace();
      }
   }//end main

   public static void encode() throws Exception{
      organizadorEncode("encode-2.in","encode-2.out");
      organizadorEncode("encode-3.in","encode-3.out");
      organizadorEncode("encode-4.in","encode-4.out");
   }  
   
   public static void decode()throws Exception{
      organizadorDecode("decode-5.in", "decode-5.out");
      organizadorDecode("decode-6.in", "decode-6.out");
      organizadorDecode("decode-7.in", "decode-7.out");
   }


   //metodo para conseguir resolver o problema para cada arquivo decode(mandado como parametro)
   public static void organizadorDecode(String nome_leitura, String nome_escrita)throws Exception{
         String str = "";
         if(nome_leitura.equals("decode-6.in")){ 
            str = ler2(nome_leitura);                                       //ler a string de um arquivo(decode-6)
         }else{ 
            str  = ler(nome_leitura);                                       //ler a string de um arquivo
         }                                                                    
         int pos               = lerPos(nome_leitura);                       //ler o valor int do mesmo arquivo
         String[] vetorString  = new String[str.length()];                   //declarar um vetor de strings
         vetorString           = preencher(vetorString, str.length());       //preencher a matriz com ""
         vetorString           = cria(str, vetorString, str.length());       //criar a matriz final desejada
         escreverDecode(vetorString, pos, nome_escrita);                     //ler a String na posicao desejada e escreve-la em um novo arquivo
   }//end organizador

   //metodo para conseguir resolver o problema para cada arquivo encode(mandado como parametro)
   public static void organizadorEncode(String nome_leitura, String nome_escrita)throws Exception{

      String   str            = lerString(nome_leitura);                       //ler a string de um arquivo
      char[][] matrizChar     = new char[str.length()][str.length()];          //declarar uma matriz de caracteres 
      String[] matrizString   = completaMatriz(matrizChar, str.length(), str); //inicializar a matriz e transforma-la em um vetor de Strings
      matrizString            = selectionSort(matrizString, str.length());     //ordenar o vetor em ordem alfabetica
      escreverEncode(matrizString, str, nome_escrita);                         //escrever em um arquivo a resposta desejada

   }//end organizador

   //metodo para ler a string do arquivo decode.in
   public static String ler(String nome_leitura)throws Exception{
      RandomAccessFile raf = new RandomAccessFile(nome_leitura, "r");          //abrir o arquivo desejado
      raf.seek(2);                                                             //ir para a posicao 2 da linha no arquivo
      String str = "";
      int tamString = (int)raf.length()-8;                                     //tamanho da palavra = tamanho total - caracteres extras
      for(byte i = 0; i < tamString; i++){
         str += (char)raf.read();                                              //ler caracter a caracter do arquivo e colocar em uma string
      }
      raf.close();                                                             //fechar o arquivo
      return str;                                                              //retornar a nova string
   }//end ler


   //metodo para ler a string do arquivo decode.in
   public static String ler2(String nome_leitura)throws Exception{             //metodo para o decode-6.in (nao possui um espaco entre a virgula e o numero)
      RandomAccessFile raf = new RandomAccessFile(nome_leitura, "r");
      raf.seek(2);
      String str = "";
      int tamString = (int)raf.length()-7;
      for(byte i = 0; i < tamString; i++){
         str += (char)raf.read();
      }
      raf.close();
      return str;
   }//end ler


   //metodo para ler o valor int nos arquivos decode.in
   public static int lerPos(String nome_leitura) throws Exception{ 
      RandomAccessFile raf = new RandomAccessFile(nome_leitura, "r");            //abrir o arquivo desejado
      int posicao = (int)raf.length()-3;                                     
      raf.seek(posicao);                                                         //ir para a posicao do numero =  tamanho - "\n", "]" e '
      String pos = "";
      pos += (char)raf.read();                                                   //colocar o byte lido em uma string
      int resp = Integer.parseInt(pos);                                          //transformar a string em int
      raf.close();                                                               //fechar arquivo
      return resp;                                                               //retornar o valor int
   }//end lerPos

   //Metodo para ler a string no arquivo
   public static String lerString(String nome_leitura) throws Exception{

      BufferedReader BR = new BufferedReader(new FileReader(nome_leitura));    //abrir o arquivo desejado
      String str = BR.readLine();                                              //ler a string existente no arquivo
      BR.close();                                                              //fechar o arquivo

   return str;                                                                 //retorna a string lida
   }//end lerString


   //metodo para escrever no arquivo a string na posicao desejada
   public static void escreverDecode(String[] vetorString, int posicao, String nome_escrita)throws Exception{
      BufferedWriter BW = new BufferedWriter( new FileWriter(nome_escrita)); //criar o arquivo
      String str        = vetorString[posicao];                                     //pegar a string desejada
      BW.write(str);                                                         //escrever no arquivo
      BW.close();                                                            //fechar o arquivo
   }//end escreverDecode

   //metodo para escrever no arquivo uma string contendo a ultima letra de cada string no vetor ordenado + o numero da linha contendo a palavra original
   public static void escreverEncode(String[] vetorString, String str, String nome_escrita)throws Exception{
      String   str2     = novaString(vetorString);                           //conseguir a string com as ultimas letras
      byte     resp     = posicaoDoOriginal(vetorString, str);               //conseguir o numero da linha da palavra original
      BufferedWriter BW = new BufferedWriter(new FileWriter(nome_escrita));  //criar o arquivo
      BW.write("['" + str2 + "', " + resp + "]\n");                          //escrevendo a resposta no arquivo criado
      BW.close();                                                            //fechar o arquivo
   }//end escreverEncode
   
   
   //metodo para completar a matriz de char, com os caracteres desejados na ordem certa, e chamada para o metodo para transformar a matriz em um vetor
   public static String[] completaMatriz(char[][] matrizChar, int tamanho, String str){
         int temp = 0;
         for(byte i = 0; i < tamanho; i++){
            for(byte j = 0; j < tamanho; j++){               
                  matrizChar[i][j] = str.charAt(temp);
                  temp = (temp + 1) % tamanho;
            }
            temp = (temp + (tamanho-1)) % tamanho;
         }//end for 
         return TranformaMatriz(matrizChar, tamanho, new String[tamanho]);
   }//end completaMatriz
   

   //metodo para transformar a matriz de char em um vetor de String
   public static String[] TranformaMatriz(char[][] matrizChar, int tamanho, String[] vetorString){
      vetorString = preencher(vetorString, tamanho);
      for(byte i = 0; i < matrizChar.length; i++){
         for(byte j = 0; j < matrizChar[i].length; j++){
             vetorString[i] += matrizChar[i][j]; 
         }//end for
      }//end for
      return vetorString;
   }//end TransformaMatriz
   

   //metodo SelectionSort para ordenar o vetor em ordem alfabética
   public static String[] selectionSort(String[] vetorString, int tam) {  
      for (int i = 0; i < tam-1; i++){ 
          int actual = i; 
          for (int j = i+1; j < tam; j++)
            if ((vetorString[j]).compareTo((vetorString[actual])) < 0) 
              actual = j;  
              vetorString = swap(actual, i, vetorString);  
      }//end for 
      return vetorString;
   }//end SelectionSort
   

   //metodo SWAP para fazer a troca de Strings no vetor
   public static String[] swap(int a, int b, String[] vetorString){ 
      String tmp     = vetorString[a]; 
      vetorString[a] = vetorString[b]; 
      vetorString[b] = tmp;
      return vetorString; 
   }//end Swap 
   

   //metodo para retornar a string contendo os ultimos caracteres de cada string existente no vetor
   public static String novaString(String[] vetorString){
      String str = "";
      for(byte i = 0; i < vetorString.length; i++){                        //for percorrendo o vetor completo
            str += vetorString[i].charAt(vetorString.length-1);            //nova string adicionando os ultimos caracteres
      }//end for
      return str;                                                          //retorna a nova string
   }//end novaString


   //metodo para retornar a posicao no vetor que contem a string original
   public static byte posicaoDoOriginal(String[] vetorString, String str){
      byte i = -1;
      for(byte a = 0; (a < vetorString.length || i == a) ; a++){           //for que para quando chegar no fim do vetor ou quando achar a posicao desejada
            if(vetorString[a].equals(str)){                                //if para comparar as strings com a string original
                  i = a;
            }//end if
      }//end for
      return i;                                                            //retorna a posicao da string desejada
   }//end posicaoDoOriginal


   //metodo para preencher a matriz com "" para evitar problemas com o null
   public static String[] preencher(String[] vetorString, int tamanho){
      for(byte i = 0; i < tamanho; i++){
         vetorString[i] = "";
      }
   return vetorString;                                                       //retorna a nova matriz
   }//end preencher


   //metodo para criar a matriz de Strings a partir da string contida nos arquivos decode.in
   public static String[] cria(String str, String[] vetorString, int tamanho){
      for(byte j = 0; j < tamanho; j++){
         for(byte i = 0; i < tamanho; i++){
            vetorString[i] = str.charAt(i) + vetorString[i];                  //escrevendo os caracteres na amtriz
         }
         vetorString = selectionSort(vetorString, tamanho);                   //ordenando a matriz em ordem alfabetica apos a insercao de todos os elementos em uma "coluna"
      }   
   return vetorString;                                                        //retorna a nova matriz
   }//end cria
   

}//end class