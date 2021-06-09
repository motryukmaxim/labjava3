    import java.io.*;
    import java.io.IOException;
    import java.io.OutputStream;
    import java.text.DateFormat;
    import java.text.ParseException;
    import java.text.SimpleDateFormat;
    import java.time.LocalDate;
    import java.util.*;
    import  java.util.Scanner;


    public class Main {

        static ArrayList<MeteorologyInfo> metInfo = new ArrayList<MeteorologyInfo>();

        static Scanner scanner = new Scanner(System.in);
        static String metFileName = "Meteorology.txt";


        public static void main(String[] args) {

            LoadFromFile();

            AddMeteorologyInfo();

            ShowInfo();


        }

        private static void AddMeteorologyInfo() {
            byte day;
            byte month;
            float airTemper;
            do {
                System.out.print("Enter number of month: ");
                month = scanner.nextByte();
            } while (month > 12 || month < 1);
            Calendar calendar = new GregorianCalendar(LocalDate.now().getYear(), month - 1, 1);
            do {
                System.out.println("Maximum DAY:" + calendar.getActualMaximum(calendar.DAY_OF_MONTH));
                System.out.print("Enter day: ");
                day = scanner.nextByte();

            } while (day > calendar.getActualMaximum(calendar.DAY_OF_MONTH) || day < 1);
            System.out.print("Enter air temperature: ");
            airTemper = scanner.nextFloat();


            try {
                FileOutputStream fos = new FileOutputStream(metFileName, true);
                DataOutputStream dos = new DataOutputStream(fos);
                dos.write(day);
                dos.write(month);
                dos.writeFloat(airTemper);
                dos.close();
                MeteorologyInfo temp = new MeteorologyInfo();
                temp.day = day;
                temp.month = month;
                temp.airTemperature = airTemper;
                metInfo.add(temp);
            } catch (IOException e) {
                System.out.println("Error");
            }
        }
        static void ShowInfo () {
            float[][] averageTemp = new float[12][2];
            for (MeteorologyInfo met : metInfo) {
                Calendar calendar = new GregorianCalendar(LocalDate.now().getYear(), met.month - 1, met.day);
                averageTemp[met.month - 1][0] += met.airTemperature;
                averageTemp[met.month - 1][1]++;
            }
            int maxIndex = 0;
            for (int i = 0; i < 12; i++) {
                if ((averageTemp[i][0] / averageTemp[i][1]) > (averageTemp[maxIndex][0] / averageTemp[maxIndex][1])) {
                    maxIndex = i;
                }
                Calendar calendar = new GregorianCalendar(LocalDate.now().getYear(), i, 1);
                System.out.println(new SimpleDateFormat("MMMM", Locale.ENGLISH).format(calendar.getTime()) + ": " + averageTemp[i][0] / averageTemp[i][1]);

            }
            Calendar calendar = new GregorianCalendar(LocalDate.now().getYear(), maxIndex, 1);
            System.out.println("Maximum average temperature in " + new SimpleDateFormat("MMMM", Locale.ENGLISH).format(calendar.getTime()) + "\n");

        }


        static void LoadFromFile() {

            metInfo.clear();

            try {
                FileInputStream fis = new FileInputStream(metFileName);
                DataInputStream dis = new DataInputStream(fis);
                MeteorologyInfo temp;
                while (dis.available() > 0) {
                    for (int i = 0; i < metInfo.size(); i++) {
                        System.out.println("Surname: " + metInfo.get(i).airTemperature);
                    }
                    System.out.println();
                    temp = new MeteorologyInfo();
                    temp.day = dis.read();
                    temp.month = dis.read();
                    temp.airTemperature = dis.readFloat();
                    metInfo.add(temp);
                }
                dis.close();


            } catch (FileNotFoundException e) {
                e.printStackTrace();

            } catch (IOException e) {

            }





        }
    }
