package s25.cs151.application;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.time.LocalDate;
import java.time.YearMonth;

/**
 * This class extends the StackPane class to create a calendar to display
 */
public class Calendar extends StackPane {

    Calendar() {
        super();
        this.setPrefSize(250, 340);

        // Get Current Date
        LocalDate today = LocalDate.now();
        YearMonth yearMonth = YearMonth.from(today);

        // Create Days of the Week
        GridPane dayOfTheWeekGrid = new GridPane();
        dayOfTheWeekGrid.setHgap(0);
        dayOfTheWeekGrid.setVgap(20);
        dayOfTheWeekGrid.setAlignment(Pos.TOP_CENTER);

        String[] daysOfTheWeek = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};

        // Add day headers
        for (int i = 0; i < daysOfTheWeek.length; i++) {
            Label dayLabel = new Label(daysOfTheWeek[i]);
            dayLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: white;");

            // Using StackPane to make label in the middle -> asthetic purposes
            StackPane dayCell = new StackPane();
            dayCell.setPrefSize(35,30);
            dayCell.setAlignment(Pos.CENTER);
            dayCell.getChildren().addAll(dayLabel);

            // Add the days of the week to the top of the calendar
            dayOfTheWeekGrid.add(dayCell, i, 0);
        }

        // First day of month and number of days
        LocalDate firstDay = yearMonth.atDay(1);
        int daysInMonth = yearMonth.lengthOfMonth();
        int startDayOfWeek = firstDay.getDayOfWeek().getValue() % 7; // Adjust to start on Sunday

        // Fill the calendar grid with days of that month
        int day = 1;
        for (int row = 1; row <= 6; row++) {
            for (int col = 0; col < 7; col++) {
                if (row == 1 && col < startDayOfWeek) {
                    continue; // Skip empty cells before first day of the month appear
                }
                if (day > daysInMonth) {
                    break; // Stop when displayed all of the days in the month
                }

                // Create a StackPane to center the day in the cell
                StackPane dayCell = new StackPane();
                dayCell.setPrefSize(35,30);
                dayCell.setAlignment(Pos.CENTER);

                Label dayLabel = new Label(String.valueOf(day));
                dayLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: white;");

                dayCell.getChildren().addAll(dayLabel);

                // Add the days to the calendar
                dayOfTheWeekGrid.add(dayCell, col, row);
                day++;
            }
        }
        // Creating the background for the calendar
        Rectangle calendarBackground = new Rectangle(250, 340);
        // Set the color for the background of the calendar
        calendarBackground.setFill(Color.valueOf("#CAA8F5"));

        // Add the layouts
        this.getChildren().addAll(calendarBackground, dayOfTheWeekGrid);
    }
}
