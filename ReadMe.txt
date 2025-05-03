# Name of application: Faculty Reminder
# Version: 0.8

# who did what:
1. Le Duy Nguyen - Create ModelControllerInt, implement Polymorphism
2. Lima Ibrahimkhail - Reorganize the files into MVC design pattern, delete redundant files/codes
3. Billy Porras-Molina - Implement Edit Office Hour Schedule function

Our use of polymorphism is located in:
- CreateSchedulePage.java (line 30, 31, 32)
    - Parent Interface: DAOInt
    - Concrete Child Classes: CourseDAO, OfficeHourScheduleDAO, TimeSlotDAO
- CreateTimeSLotPage.java (line 22)
    - Parent Interface: DAOInt
    - Concrete Child Classes: TimeSlotDAO
- EditSchedulePage.java (line 32, 34)
    - Parent Interface: DAOInt
    - Concrete Child Classes: CourseDAO, TimeSlotDAO
- ViewCoursePage.java (line 35)
    - Parent Interface: DAOInt
    - Concrete Child Classes: CourseDAO
- ViewOfficeHourPage.java (line 35)
    - Parent Interface: DAOInt
    - Concrete Child Classes: OfficeHourDAO
- ViewTimeSlotPage.java (line 34)
    - Parent Interface: DAOInt
    - Concrete Child Classes: TimeSlotDAO



