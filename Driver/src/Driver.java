import javafx.scene.control.Tab;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Stack;

import static java.lang.Math.abs;

public class Driver {

    public static class Points
    {
        int x;
        int y;
        int z;
    }

    //только выделяем место, объекты не создаем
    static final int nmax = 700;
    static tSynTable[] Table = new tSynTable[nmax];          //таблица


    static java.util.Stack<Integer> stack = new Stack<>();
    //static tStack Stack = new tStack();                     //стек
    static char Ch;                                         //текущий символ
    static int i;                                           //номер состояния
    static int Err;                                         //признако ошибки

    static final char EOT = (char)10;
    static int iterator = 0;                                //номер символа в строке ввода
    static int ErrorPos = 0;

    //для семантических процедур
    static Points coords = new Points();
    static int bufD = 0;
    static int D = 0;
    static int Denominator = 0;
    static int Number = 0;

    public static void main(String[] args) {
        fillTable.getTable(Table);
/*
        for (int i = 1; i < 31; i++){
            System.out.println("i:" + i + " Ch:" + Table[i].Ch + " Переход:" + Table[i].Go + " Err:"
                    + Table[i].Err + " Call:" + Table[i].Call + " Read:"
                    + Table[i].Read + " Proc:" + Table[i].Proc);
        }
*/


        //Драйвер табличного LL(1) анализатора

        //начальная инициализация глобальных переменных
        D = 0;
        bufD = 0;
        Denominator = 0;
        Number = 0;


        ResetText();
        stack.push(0);
        //пушим в стек ноль для того, чтобы в конце прохода по таблице остановить анализ на последней строчке
        //стек нужен для вызова с возвратом (если есть несколько таких вызов, чтобы они выполнялись в нужном порядке)
        Err = 0;
        //счетчик ошибок
        i = 1;

        do {
            if ( Ch == Table[i].Ch
                    || Table[i].Ch == 'L'
                    || (Table[i].Ch == 'D' && (Ch >= '0' && Ch <= '9'))
                    || (Table[i].Ch == 'd' && (Ch >= '1' && Ch <= '9'))
                    || (Ch == EOT && Table[i].Ch == '#')) {
                if (Table[i].Proc != 0) {
                    Proc(Table[i].Proc);
                    //условие для отлова ошибок в сематнической процедуре (моя реализация, сюда не смотреть)
                    if (Err == - 1) {
                        Error(Err);
                        Err = 0;
                        return;
                    }
                }
                if (Table[i].Read)
                    NextCh();
                if (Table[i].Go == 0)
                    i = stack.pop();
                else {
                    if (Table[i].Call)
                        stack.push(i + 1);
                    i = Table[i].Go;
                }
            } else if (Table[i].Err) {
                Err = i;
                ErrorPos = iterator;
                Error(Err);
                i = 0;
                Err = 0;
                return;
            }
            else
                i = i + 1;
        } while (i != 0 || Err > 0);

    }

    //выполнение семантической процедуры
    static void Proc(int i) {

        if (i == 1) {
            D = 1;
            return;
        }

        if (i == 2) {
            Denominator = D;
            return;
        }

        if (i == 3) {
            bufD = 0;
            D = 0;
            return;
        }

        if (i == 4) {

            bufD = (int) Ch - (int) '0';
            D = 10 * D + bufD;

            return;
        }

        if (i == 5) {

            bufD = (int) Ch - (int) '0';
            if (D <= (Integer.MAX_VALUE - bufD) / 10)
                D = 10 * D + bufD;
            else {
                Err = -1;
            }

            return;
        }

        if (i == 6) {
            coords.x = Denominator;
            return;
        }

        if (i == 7) {
            coords.y = Denominator;
            return;
        }

        if (i == 8) {
            coords.z = Denominator;
            return;
        }

        if (i == 9) {
            if (Err != -1 || Err != 0) {
                System.out.println("Правильно");
                System.out.println();


                if (coords.x == 0)
                    System.out.println("Плоскость параллельна оси X");
                else if (coords.y == 0)
                    System.out.println("Плоскость параллельна оси Y");
                else if (coords.z == 0)
                    System.out.println("Плоскость параллельна оси Z");
                else {
                    System.out.print("Объем тетраэдра: ");
                    System.out.println(abs(coords.x * coords.y * coords.z) / 6.0);
                }
            } else
                System.out.println("Ошибка в семантической процедуре");


            return;

        }

        System.out.println("Никакой семантической процедуры не выполнено");
    }

    //------------------------------------------------------------------


    static void Error(int Err)
    {
        while( Ch != EOT )
            NextCh();
        //System.out.println();

        for(int j = 1; j < ErrorPos; j++ )
            System.out.print(" ");

        if (Err < 0) {
            System.out.println("^\nошибка в семантической процедуре");
            return;
        }
        else if (Table[Err].Ch == 'd')
            System.out.println("^\nОжидается цифра, отличная от нуля");
        else if (Table[Err].Ch == 'D')
            System.out.println("^\nОжидается цифра");
        else if (Table[Err].Ch == '#')
            System.out.println("^\nОжидается конец текста");
        else
            System.out.println("^\nОжидается " + Table[Err].Ch);
    }

    static void ResetText()
    {
        iterator = 0;
        NextCh();
    }

    static void NextCh()
    {
        do {
            try {
                Ch = (char)System.in.read();
            } catch (Exception e) {
                System.out.println("правильный ввод");
            }
            System.out.print(Ch);
            iterator++;
        } while( Ch == ' ');
    }

    //------------------------------------------------------------------

}
