import static java.lang.Math.abs;

public class ParserMain {
    public static class Points
    {
        int x;
        int y;
        int z;
    }


    static final char EOT = (char)10;
    static char Ch;
    static int i, ErrPos;
    static Points coords = new Points();

    //static int[][] matrix;
    //static Vector<Point> Points = new Vector();

    public static void main (String[] args)
    {
        System.out.println("Уравнение плоскости в отрезках");
        ResetText();
        ErrPos = 0;
        Equation();
        if (Ch != EOT)
            Error("конец текста");
        else if(ErrPos == 0)
        {
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

            //System.out.println(Integer.MAX_VALUE);

            //System.out.println(coords.x);
            //System.out.println(coords.y);
            //System.out.println(coords.z);
        }
    }

    static void ResetText()
    {
        i = 0;
        NextCh();
    }

    static void Equation()
    {
        //+ - ...
        if(Ch == '+' || Ch == '-')
            NextCh();
        if(Ch == 'x')
        {
            NextCh();
            coords.x = Denominator();
            if (Ch == '+' || Ch == '-')
            {
                NextCh();
                if (Ch == 'y')
                {
                    NextCh();
                    coords.y = Denominator();
                    if (Ch == '+' || Ch == '-')
                    {
                        NextCh();
                        if (Ch == 'z')
                        {
                            NextCh();
                        } else
                            Error("\"z\"");
                        coords.z = Denominator();
                    }
                } else if (Ch == 'z')
                {
                    NextCh();
                    coords.z = Denominator();
                } else
                    Error("\"y\" или \"z\"");
            }
        } else if (Ch == 'y')
        {
            NextCh();
            coords.y = Denominator();
            if (Ch == '+' || Ch == '-')
            {
                NextCh();
                if (Ch == 'z')
                {
                    NextCh();
                } else
                    Error("\"z\"");
                coords.z = Denominator();
            }
        } else if (Ch == 'z')
        {
            NextCh();
            coords.z = Denominator();
        } else
            Error("\"x\" или \"y\" или \"z\"");
        if (Ch == '=')
        {
            NextCh();
        } else
            Error("=");
        if (Ch == '1')
        {
            NextCh();
        } else
            Error("1");
    }

    static int Denominator()
    {
        int D = 1;
        if (Ch == '/')
        {
            NextCh();
            D = Number();
        }

        return D;
    }

    static int Number()
    {
        //изначальная инициализация
        int bufD = 0;
        int D = 0;

        if( Ch >= '1' && Ch <= '9' )
        {
            bufD = (int) Ch - (int) '0';
            D = 10 * D + bufD;
            NextCh();
        }
        else
            Error("цифра, отличная от нуля");

        while(Ch >= '0' && Ch <= '9')
        {
            bufD = (int) Ch - (int) '0';

            if (D <= (Integer.MAX_VALUE - bufD) / 10)
                D = 10 * D + bufD;
            else {
                Error("меньшее число");
                return 0;
            }
            NextCh();
        }

        return D;
    }

    static void NextCh()
    {
        do {
            try {
                Ch = (char)System.in.read();
            } catch (Exception e) {
                Error("правильный ввод");
            }
            System.out.print(Ch);
            i++;
        } while( Ch == ' ');
    }

    static void Error(String msg)
    {
        if( ErrPos == 0 )
        {
            ErrPos = i;
            while( Ch != EOT )
                NextCh();
            //System.out.println();
            for(int j = 1; j < ErrPos; j++ )
                System.out.print(" ");
            System.out.println("^\nОжидается " + msg);
        }
    }
}
