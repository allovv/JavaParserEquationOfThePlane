public class tSynTable {
    public char Ch;        //символ
    public int Go;         //переход
    public boolean Err;    //ошибка
    public boolean Call;   //вызов
    public boolean Read;   //читать
    public int Proc;       //процедура

    tSynTable() {
        this.Ch = 0;
        this.Proc = 0;
        this.Go = 0;
        this.Err = false;
        this.Call = false;
        this.Read = false;
    }
}
