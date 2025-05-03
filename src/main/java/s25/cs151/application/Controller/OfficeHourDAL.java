package s25.cs151.application.Controller;

import s25.cs151.application.Model.OfficeHour;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class OfficeHourDAL implements ModelControllerInt<OfficeHour> {
    public OfficeHourDAL() {};

    @Override
    public ArrayList<OfficeHour> load() {
        ArrayList<OfficeHour> list = new ArrayList<>();

        File file = new File("src/data/office_hour.csv");

        try (Scanner scanner = new Scanner(file))
        {
            while (scanner.hasNextLine()) {
                String[] values = scanner.nextLine().split(",");
                String semester = values[0].trim();
                int year = Integer.parseInt(values[1].trim());
                boolean[] days = new boolean[5];
                for (int i = 0; i < 5; i++) {
                    days[i] = Boolean.parseBoolean(values[i + 2].trim());
                }
                list.add(new OfficeHour(semester, year, days));
            }
        } catch (FileNotFoundException e) {
            System.out.println("CSV file not found");
        }
        return list;
    }

    @Override
    public boolean save(OfficeHour newOfficeHour) throws IOException {
        try{
            FileWriter out = new FileWriter("src/data/office_hour.csv", true);
            // Append the OfficeHour data to the file
            out.append(newOfficeHour.toString());
            out.append("\n");
            out.close();
            return true;
        }
        catch (IOException e) {
            // Print an error message if the file cannot be accessed
            System.out.println("File not found");
            return false;
        }
    }
}
