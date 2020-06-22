import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

//считываем все из текстового документа и заносим в таблицу
public class fillTable {

    static char Ch;
    static int D = 0;
    static char[] charsArray;
    static int iterChars;


    //можем использовать не создавая экземпляра класса
    public static void getTable(tSynTable[] Table) {

        BufferedReader br = null;

        try {

            br = new BufferedReader(new FileReader("Table.txt"));
            String line;

            while ((line = br.readLine()) != null) {

                //какую строку считали System.out.println(line);
                charsArray = line.toCharArray();
                iterChars = 0;

                Ch = ' ';
                int D1 = 0;
                int D2 = 0;
                int D3 = 0;

                //получаем первый элемент строки
                NextChFile();
                D1 = Number();

                //создаем ячейку массива
                Table[D1] = new tSynTable();

                NextChFile();
                Table[D1].Ch = Ch;

                NextChFile();
                D2 = Number();
                Table[D1].Go = D2;

                NextChFile();
                Table[D1].Err = Ch == '+';

                NextChFile();
                Table[D1].Call = Ch == '+';

                NextChFile();
                Table[D1].Read = Ch == '+';

                NextChFile();
                D3 = Number();
                Table[D1].Proc = D3;

            }

        } catch (IOException e) {
            System.out.println("Error: " + e);
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                System.out.println("Error: " + e);
            }
        }

    }


    static void NextChFile()
    {
        if (Ch == ';')
            return;
        do {
            try {
                if (iterChars < charsArray.length) {
                    Ch = charsArray[iterChars];
                    iterChars++;
                } else {
                    System.out.println("Выход ха границы массива");
                    return;
                }
            } catch (Exception e) {
                System.out.println("Правильный текстовый файл");
            }
        } while( Ch == ' ' );
    }

    static void NextDigit()
    {
        if (Ch == ';')
            return;
        if (iterChars < charsArray.length) {
            Ch = charsArray[iterChars];
            iterChars++;
        } else {
            System.out.println("Выход ха границы массива");
            return;
        }
    }

    static int Number()
    {
        //изначальная инициализация
        int bufD = 0;
        int D = 0;

        while (Ch >= '0' && Ch <= '9') {
            bufD = (int) Ch - (int) '0';

            if (D <= (Integer.MAX_VALUE - bufD) / 10)
                D = 10 * D + bufD;
            else {
                System.out.println("меньшее число");
                return 0;
            }
            NextDigit();
        }


        return D;
    }



}
